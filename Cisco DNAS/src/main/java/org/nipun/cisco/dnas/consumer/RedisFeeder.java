
package org.nipun.cisco.dnas.consumer;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.nipun.cisco.dnas.events.enums.EventTypes;
import org.nipun.cisco.dnas.service.CiscoDnasPluginService;

import redis.clients.jedis.Jedis;

public class RedisFeeder {

    private static final Logger log = LogManager.getLogger(RedisFeeder.class);
    private static Jedis jedis;
    //private CiscoDnasPluginService dnasCiscoPluginService;
    //    private IUrlsService urlService;

    public static Jedis getJedis() {
        init();
        return jedis;
    }

    private static void init() {
        if (jedis == null) {
            jedis = new Jedis("localhost");
        }
    }

    public void accept(final JSONObject eventData, final Map<String, Long> activeTenantsMap, final String apiUrl, final String apiKey) {
        log.debug("Start executing the RedisFeder accept method.");
        init();
        /*JSONObject json = new JSONObject();
        raiseAlert(json);*/
        String eventType = eventData.getString("eventType");
        String macAddress = "";
        log.debug("Getting the event type from event data: " + eventData);
        CiscoDnasPluginService ciscoDnasPluginService = new CiscoDnasPluginService();
        switch (eventType) {
            case "DEVICE_LOCATION_UPDATE":
                macAddress = eventData.getJSONObject("deviceLocationUpdate").getJSONObject("device").getString("macAddress");
                log.debug("published event to :: DEVICE_LOCATION_UPDATE::" + macAddress);

                //Processing the cisco fire hose data to update device location on map.
                ciscoDnasPluginService.geDnasCiscoPluginResponse(eventData.toString(), activeTenantsMap, apiUrl, apiKey);
                break;
            case "DEVICE_ENTRY":
                log.debug("Published the event to :: DEVICE_ENTRY::");
                org.json.simple.JSONObject entryEventResponse;
                try {
                    //Processed fire hose data and raise the alert when new device was entered in location.
                    entryEventResponse = ciscoDnasPluginService.getEntryEventResponse(eventData.toString(), activeTenantsMap,
                            EventTypes.ENTRY.toString(), "deviceEntry");

                    if (entryEventResponse.containsKey("tagMacId")) {
                        log.debug("Raising the alert when entryEventResponse contains macId.");
                        macAddress = entryEventResponse.get("tagMacId").toString();
                    }
                }
                catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;
            case "DEVICE_EXIT":
                log.debug("Published the event to :: DEVICE_EXIT::");
                org.json.simple.JSONObject deviceExityEventResponse;
                try {
                    //Processed fire hose data and raise the alert when device was exit in location.
                    deviceExityEventResponse = ciscoDnasPluginService.getEntryEventResponse(eventData.toString(), activeTenantsMap,
                            EventTypes.EXIT.toString(), "deviceExit");
                    if (deviceExityEventResponse.containsKey("tagMacId")) {
                        log.debug("Raising the alert when entryEventResponse contains macId.");
                        macAddress = deviceExityEventResponse.get("tagMacId").toString();
                    }

                }
                catch (ParseException parseEx) {
                    // TODO Auto-generated catch block
                    log.error("Parse Exception");
                    log.debug(parseEx);
                }

                break;

            case "USER_PRESENCE":
                log.debug("Published the event to :: USER_PRESENCE::");

                //Processed fire hose data and raise the alert when new user presence in location.
                org.json.simple.JSONObject userPresenceEventResponse = ciscoDnasPluginService
                        .getUserPresenceEventResponse(eventData.toString(), activeTenantsMap);
                log.debug("Getting response from getUserPresenceEventResponse method: " + userPresenceEventResponse);
                if (userPresenceEventResponse.containsKey("tagMacId")) {
                    macAddress = userPresenceEventResponse.get("tagMacId").toString();

                }
                break;
            case "DEVICE_PRESENCE":
                log.debug("Published the event to :: DEVICE_PRESENCE::");

                //Processed fire hose data and raise the alert when new device presence in location.
                org.json.simple.JSONObject devicePresenceEventResponse = ciscoDnasPluginService
                        .getDevicePresenceEventResponse(eventData.toString(), activeTenantsMap);
                log.debug("devicePresenceEventResponse: " + devicePresenceEventResponse);
                if (devicePresenceEventResponse.containsKey("tagMacId")) {
                    macAddress = devicePresenceEventResponse.get("tagMacId").toString();
                }
                break;
            default:
                log.debug("Event type was not matched with any switch case.");

        }
    }
}