/**
 *
 */
package org.nipun.cisco.dnas.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.nipun.cisco.dnas.client.FireHoseAPIClient;
import org.nipun.cisco.dnas.consumer.JsonEventConsumer;
import org.nipun.cisco.dnas.exceptions.FireHoseAPIException;
import org.nipun.cisco.dnas.server.HTTPVerticle;
import org.nipun.cisco.dnas.utils.ConfigUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.vertx.core.Vertx;

@Controller
@RequestMapping("dnasCiscoFireHoseListener")
public class CiscoDnaPluginListener {

    private static Logger logger = Logger.getLogger(CiscoDnaPluginListener.class);

    static String API_URL = "https://partners.dnaspaces.io";
    // use test example we have to connect to your local server

    // use  authorized api key
    static String API_KEY = "";

    /*  @Autowired
    static CiscoDnasPluginService ciscoDnasPluginService;*/

    public static boolean canRetry(final FireHoseAPIException e) {
        if ((e.getStatusCode() <= 0) || (e.getStatusCode() <= 599 && e.getStatusCode() >= 500)) {
            return true;
        }
        else if (e.getStatusCode() <= 499 && e.getStatusCode() >= 400) {
            return false;
        }
        else {
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/executionStart", method = RequestMethod.GET)
    public static String ciscoDnasPluginListener() {
        logger.debug("Started the execution of ciscoDnasPluginListener method.");
        logger.debug("Executing the code, if firehose auth credentials exists logic.");
        final Properties config = ConfigUtil.getConfig();
        final Integer retryCutoff = Integer.parseInt(config.getProperty("api.retrylimit.cutoff"));
        final long fromTimeStamp = (!config.getProperty("api.initialfromtimestamp").equals("-1"))
                ? Long.getLong(config.getProperty("api.initialfromtimestamp"))
                : 0;
        final int fromTimeStampAdvanceWindow = Integer.parseInt(config.getProperty("api.initialfromtimestampadvancewindow"));
        // create FireHoseAPIClient to communicate to API and receive stream of events

        //Get all TenetId from local server
        logger.debug("Active tenantIds of the cisco fireHose retrived from  database.");

        Map<String, Long> activeTenantsMap = new HashMap<String, Long>();
        //used authorized active tenant id, to test the service
        activeTenantsMap.put("asdfgfgfgfhfhfhghgfhghghfghghggh", 1l);

        logger.debug("Active tenantIds of the cisco fireHose:" + activeTenantsMap.size());
        if (activeTenantsMap.size() > 0) {

            logger.debug("Preparing the active tenant map with activeTenantId and orgId " + activeTenantsMap.size());

            FireHoseAPIClient client = new FireHoseAPIClient(API_URL, API_KEY);

            logger.debug("Creating instance for FireHoseAPIClient class.");

            // consumer to handle the event json objects from API
            JsonEventConsumer consumer = new JsonEventConsumer();
            consumer.setLastSuccessTimeStamp(fromTimeStamp);

            // set consumer in API client
            client.setConsumer(consumer);
            client.setFromTimeStampAdvanceWindow(fromTimeStampAdvanceWindow);

            Vertx vertx = Vertx.vertx();
            vertx.deployVerticle(new HTTPVerticle());

            Integer executionCount = 0;
            // loop indefinitely to reconnect
            while (true) {
                // exponential backoff time to retry
                waitBeforeRetry(executionCount++ % retryCutoff);
                try {
                    logger.debug("Caling the FireHoseAPIClient startConsumeEvents method:");
                    client.startConsumeEvents(activeTenantsMap, API_URL, API_KEY);
                }
                catch (FireHoseAPIException fae) {
                    logger.error("Couldn't connect to API.");
                    logger.debug(fae);
                    if (canRetry(fae)) {
                        continue;
                    }
                    break;
                }
            }
        }
        else {
            logger.info("Therse is no tenantId available.");
        }
        return "Success";
    }

    public static void main(final String[] args) {
        ciscoDnasPluginListener();
    }

    public static void waitBeforeRetry(final Integer executionCount) {
        try {
            TimeUnit.MILLISECONDS.sleep(((int) Math.round(Math.pow(2, executionCount)) * 1000));
        }
        catch (InterruptedException e) {
            logger.error("Thread interrupted");
        }
    }

}