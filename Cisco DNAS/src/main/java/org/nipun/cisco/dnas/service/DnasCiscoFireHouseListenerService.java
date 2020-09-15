/**
 *
 */
package org.nipun.cisco.dnas.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author himabindu.v
 *
 */
@Component
public class DnasCiscoFireHouseListenerService {
    Logger logger = Logger.getLogger(DnasCiscoFireHouseListenerService.class);

    public void sendDataToAlertListener(final String url, final String data) throws ClientProtocolException, IOException {
        if (StringUtils.isNotBlank(url)) {

            //String ipAddress = "http://45.112.252.124:4020/beaconListener.ashx";

            HttpPost httpPost = new HttpPost(url);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            logger.debug("data :" + data);
            StringEntity params = new StringEntity(data);
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(params);

            HttpResponse response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            logger.info("statusCode :" + statusCode);
            if (statusCode == 200) {

            }
            else {

            }
            //logger.info("Response status from becon listener: " + statusCode);

        }
        else {
            logger.error("Beacon url not defined in database.");
        }
    }
}