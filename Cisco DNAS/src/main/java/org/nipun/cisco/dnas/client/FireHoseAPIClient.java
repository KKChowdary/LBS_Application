
package org.nipun.cisco.dnas.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Map;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.nipun.cisco.dnas.consumer.JsonEventConsumer;
import org.nipun.cisco.dnas.exceptions.FireHoseAPIException;
import org.nipun.cisco.dnas.service.CiscoDnasPluginService;
import org.nipun.cisco.dnas.utils.ConfigUtil;

public class FireHoseAPIClient implements Closeable {

    private static final Logger log = LogManager.getLogger(FireHoseAPIClient.class);
    private static final Properties config = ConfigUtil.getConfig();
    CiscoDnasPluginService dnasCiscoPluginService;
    final String API_URL;
    final String API_KEY;
    long fromTimeStampAdvanceWindow;
    CloseableHttpClient httpclient;
    JsonEventConsumer consumer;

    public FireHoseAPIClient(final String API_URL, final String API_KEY) {
        this.API_KEY = API_KEY;
        this.API_URL = API_URL;
        init();
    }

    @Override
    public void close() {

    }

    public long getFromTimeStamp() {
        if (consumer == null) {
            return Instant.now().toEpochMilli();
        }
        long lastSuccessTimeStamp = consumer.getLastSuccessTimeStamp();
        lastSuccessTimeStamp -= getFromTimeStampAdvanceWindow() * 1000;
        return lastSuccessTimeStamp;
    }

    public long getFromTimeStampAdvanceWindow() {
        return fromTimeStampAdvanceWindow;
    }

    private HttpGet getRequest(final String url) throws URISyntaxException {
        log.info("URL is:" + url);

        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.setPath("/api/partners/v1/firehose/events");
        if (consumer.getLastSuccessTimeStamp() > 0) {
            uriBuilder.setParameter("fromTimestamp", String.valueOf(getFromTimeStamp()));
        }
        HttpGet request = new HttpGet(uriBuilder.build());
        request.addHeader("X-API-Key", API_KEY);
        return request;
    }

    private void init() {
        log.info("Initializing FireHoseAPIClient");
        RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(true).build();
        httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    }

    private String redirectHandler(final HttpResponse response) throws FireHoseAPIException {
        if (response.getHeaders(HttpHeaders.LOCATION) != null) {
            Header[] headers = response.getHeaders(HttpHeaders.LOCATION);
            if (headers.length == 1) {
                String location = headers[0].getValue();
                return location;
            }
        }
        throw new FireHoseAPIException("Invalid redirect. Location header not found.");
    }

    public void setConsumer(final JsonEventConsumer consumer2) {
        consumer = consumer2;
    }

    public void setFromTimeStampAdvanceWindow(final long fromTimeStampAdvanceWindow) {
        this.fromTimeStampAdvanceWindow = fromTimeStampAdvanceWindow;
    }

    public void startConsumeEvents(final Map<String, Long> activeTenantsMap, final String apiUrl, final String apiKey)
            throws FireHoseAPIException {
        log.debug("Started executing the FireHouse startConsumeEvents method.");
        if (consumer == null) {
            log.debug("When consumer getting null throwing the FireHoseAPIException.");
            throw new FireHoseAPIException("Event Data consumer not set");
        }
        HttpGet request = null;
        KafkaProducer<String, String> producer = null;
        // read kafka related configurations
        final Boolean isKafkaEnabled = Boolean.valueOf(config.getProperty("kafka.enabled"));
        final String topicNameProperty = config.getProperty("kafka.topic.name.property");
        final String eventKeyProperty = config.getProperty("kafka.event.key.property");
        // create Kafka producer only if kafka is enabled
        log.debug("Checking the kafka variable exist or not.");
        if (isKafkaEnabled) {
            log.debug("Executing the logic when kafka variable exist.");
            Properties options = new Properties();
            options.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getProperty("kafka.bootstrap.servers"));
            options.put(ProducerConfig.ACKS_CONFIG, config.getProperty("kafka.acks"));
            options.put(ProducerConfig.RETRIES_CONFIG, config.getProperty("kafka.retries"));
            options.put(ProducerConfig.BATCH_SIZE_CONFIG, config.getProperty("kafka.batch.size"));
            options.put(ProducerConfig.LINGER_MS_CONFIG, config.getProperty("kafka.linger.ms"));
            options.put(ProducerConfig.BUFFER_MEMORY_CONFIG, config.getProperty("kafka.buffer.memory"));
            options.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.getProperty("kafka.key.serializer"));
            options.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.getProperty("kafka.value.serializer"));
            producer = new KafkaProducer(options);
        }
        try {
            request = getRequest(API_URL);
            //log.debug("Executing GET request over http client. URL :: " + request.getURI().toString());
            log.info("Executing GET request over http client. URL :: " + request.getURI().toString());

            HttpResponse response = httpclient.execute(request);
            // log.debug("GET request executed. Received status code :: " + response.getStatusLine().getStatusCode());
            log.info("GET request executed. Received status code :: " + response.getStatusLine().getStatusCode());

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300 && statusCode == 399) {
                String location = redirectHandler(response);
                log.debug("Raise the FireHoseAPIException when status code was between 300 to 399.");
                throw new FireHoseAPIException("Couldn't startConsumeEvents", response.getStatusLine().getStatusCode());
            }
            else if (statusCode >= 400 && statusCode == 499) {
                log.debug("Raise the FireHoseAPIException when status code was between 400 to 499.");
                throw new FireHoseAPIException("Couldn't startConsumeEvents", response.getStatusLine().getStatusCode());
            }
            else if (statusCode >= 500 && statusCode == 599) {
                log.debug("Raise the FireHoseAPIException when status code was between 500 to 599.");
                throw new FireHoseAPIException("Couldn't startConsumeEvents", response.getStatusLine().getStatusCode());
            }
            else if (response.getStatusLine().getStatusCode() != 200) {
                log.error("Response status code :: " + response.getStatusLine().getStatusCode());
                throw new FireHoseAPIException("Couldn't startConsumeEvents", response.getStatusLine().getStatusCode());
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = rd.readLine()) != null) {
                JSONObject eventData = new JSONObject(line);
                log.debug("[0]:Received 1st packet");
                consumer.accept(eventData, activeTenantsMap, apiUrl, apiKey);
                log.debug("[2]: Succesfully received the packet.");
                if (isKafkaEnabled && producer != null) {
                    log.debug("[3-1]:Sucessfully sent..");
                    producer.send(new ProducerRecord(eventData.getString(topicNameProperty), eventData.getString(eventKeyProperty),
                            eventData.toString()));
                    log.debug("[3-2]:Sucessfully sent..");
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            log.error("IO | URISyntax exception occurred.");
            log.debug(ex);
            throw new FireHoseAPIException(ex);
        }
        catch (NullPointerException ex) {
            log.error("Unknown exception occurred.");
            log.debug(ex);
            throw new FireHoseAPIException(ex);
        }
        finally {
            log.info("request has been ended");
            request.releaseConnection();
            // flush and close Kafka Producer
            if (isKafkaEnabled && producer != null) {
                producer.flush();
                producer.close();
            }

        }
    }
}