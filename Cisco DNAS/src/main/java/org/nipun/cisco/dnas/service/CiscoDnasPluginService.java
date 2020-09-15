/**
 *
 */
package org.nipun.cisco.dnas.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.nipun.cisco.dnas.common.util.DateUtil;
import org.nipun.cisco.dnas.events.enums.EventTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 * The {@code CiscoDnasPluginService} class represents for processing CiscoFireHose data.
 *
 * @see java.lang.Object#toString()
 * @author himabindu.v
 *
 */
@Service
public class CiscoDnasPluginService {
    Logger logger = Logger.getLogger(CiscoDnasPluginService.class);

    @Autowired
    DnasCiscoFireHouseListenerService DnasCiscoFireHouseListenerService;
    @Autowired
    ServletContext servletContext;

    /**
     * @param jsonStr
     * @param activeTenantsMap
     * @param apiUrl
     * @param apiKey
     * @return
     */
    public String geDnasCiscoPluginResponse(final String jsonStr, final Map<String, Long> activeTenantsMap, final String apiUrl,
            final String apiKey) {
        ModelMap map = new ModelMap();
        try {
            logger.debug("getting response from cisco fire house:" + jsonStr);
            JSONObject resultObj = prepareCiscoDnasJSONString(jsonStr, activeTenantsMap, apiUrl, apiKey);
            logger.debug("Prepared JSON format  : " + resultObj.toString());

            if (!resultObj.isEmpty()) {
                //Here send process data to service, then save tag data in to local server.
                return "Success";
            }
            else {
                return "Fail";
            }

        }
        catch (ParseException pex) {

            logger.error("Parse exception occurred.");
            logger.debug(pex);

            return "Fail";
        }
        catch (Exception ex) {
            logger.error(map.get("logError").toString());
            logger.error("Parse exception occurred.");
            logger.debug(ex);
            return "Fail";
        }
    }

    /**
     * @param jsonStr
     * @param activeTenantsMap
     * @return
     */
    public JSONObject getDevicePresenceEventResponse(final String jsonStr, final Map<String, Long> activeTenantsMap) {
        JSONObject resultObj = new JSONObject();
        JSONParser parser = new JSONParser();
        JSONObject responseObject;
        try {
            responseObject = (JSONObject) parser.parse(jsonStr);
            if (responseObject.containsKey("partnerTenantId")) {
                String partnerTenantId = responseObject.get("partnerTenantId").toString();
                if (activeTenantsMap.containsKey(partnerTenantId)) {
                    Long orgId = activeTenantsMap.get(partnerTenantId);
                    if (responseObject.containsKey("devicePresence")) {
                        JSONObject presence = (JSONObject) responseObject.get("devicePresence");
                        if (presence.containsKey("location")) {
                            JSONObject location = (JSONObject) presence.get("location");
                            if (location.containsKey("locationId")) {
                                String locationId = location.get("locationId").toString();
                                /*   TblZone tblZone = iTblZoneService.getZonesByZoneId(locationId, orgId);*/
                                if (presence.containsKey("device")) {
                                    JSONObject device = (JSONObject) presence.get("device");
                                    if (device.containsKey("macAddress")) {
                                        String macAddress = device.get("macAddress").toString();
                                        logger.info("Device presence for macAddress:" + macAddress);

                                    }
                                }
                            }
                            else {
                                logger.error("Zone details are not available in this packet.");

                            }
                        }
                        else {
                            logger.info("Location details are not getting from response.");
                        }
                    }
                }
            }
        }
        catch (ParseException e) {
            logger.debug(e);
        }
        catch (Exception e) {
            logger.debug(e);
        }
        if (!resultObj.isEmpty()) {
            raiseAlert(resultObj);
        }

        return resultObj;
    }

    /**
     * @param jsonStr
     * @param activeTenantsMap
     * @param eventType
     * @param deviceEventType
     * @return
     * @throws ParseException
     */
    public JSONObject getEntryEventResponse(final String jsonStr, final Map<String, Long> activeTenantsMap, final String eventType,
            final String deviceEventType) throws ParseException {
        JSONObject resultObj = new JSONObject();

        JSONParser parser = new JSONParser();
        JSONObject responseObject = (JSONObject) parser.parse(jsonStr);
        logger.debug("responseObject: " + responseObject);
        if (responseObject.containsKey(deviceEventType)) {
            JSONObject jsonDataObj = (JSONObject) responseObject.get(deviceEventType);
            if (responseObject.containsKey("partnerTenantId")) {
                String partnerTenantId = responseObject.get("partnerTenantId").toString();
                if (StringUtils.isNotEmpty(partnerTenantId)) {
                    if (activeTenantsMap.containsKey(partnerTenantId)) {
                        Long orgId = activeTenantsMap.get(partnerTenantId);

                        if (jsonDataObj.containsKey("location")) {
                            JSONObject locationResponse = (JSONObject) jsonDataObj.get("location");
                            if (locationResponse.containsKey("locationId")) {
                                String locationId = locationResponse.get("locationId").toString();
                                if (StringUtils.isNotEmpty(locationId)) {

                                    if (jsonDataObj.containsKey("device")) {
                                        JSONObject device = (JSONObject) jsonDataObj.get("device");
                                        if (device.containsKey("macAddress")) {
                                            String macAddress = device.get("macAddress").toString();
                                            String entryTimeStamp = jsonDataObj.get("entryDateTime").toString();
                                            logger.debug(entryTimeStamp + " :entryTimeStamp");
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                            try {
                                                Date format = sdf.parse(entryTimeStamp);
                                                String timestamp = DateUtil.formatDateToStrYMDHHMMSEC(format);
                                                logger.debug(timestamp + " :timestamp");
                                                resultObj.put("timestamp", timestamp);
                                            }
                                            catch (java.text.ParseException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                }
                                else {
                                    logger.debug("Zone not found");
                                }
                            }
                        }
                    }
                }
            }
        }

        return resultObj;

    }

    /**
     * @param jsonStr
     * @param activeTenantsMap
     * @return
     */
    public JSONObject getUserPresenceEventResponse(final String jsonStr, final Map<String, Long> activeTenantsMap) {

        JSONObject resultObj = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            JSONObject responseObject = (JSONObject) parser.parse(jsonStr);
            if (responseObject.containsKey("partnerTenantId")) {
                String partnerTenantId = responseObject.get("partnerTenantId").toString();
                if (activeTenantsMap.containsKey(partnerTenantId)) {
                    Long orgId = activeTenantsMap.get(partnerTenantId);
                    if (responseObject.containsKey("userPresence")) {
                        JSONObject presence = (JSONObject) responseObject.get("userPresence");
                        if (presence.containsKey("location")) {
                            JSONObject location = (JSONObject) presence.get("location");
                            if (location.containsKey("locationId")) {
                                String locationId = location.get("locationId").toString();

                                resultObj.put("entityId", "");
                                resultObj.put("mapId", "");
                                resultObj.put("tagMacId", "");
                                resultObj.put("eventType", EventTypes.ENTRY);
                                resultObj.put("zoneId", "");
                                resultObj.put("ruleId", "");
                                resultObj.put("orgId", orgId);
                                resultObj.put("X_Coordinate", "");
                                resultObj.put("Y_Coordinate", "");
                                String entryTimeStamp = presence.get("entryDateTime").toString();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                Date format;
                                try {
                                    format = sdf.parse(entryTimeStamp);
                                    String timestamp = DateUtil.formatDateToStrYMDHHMMSEC(format);
                                    resultObj.put("timestamp", timestamp);
                                }
                                catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                }
            }
        }
        catch (ParseException e) {
            //            e.printStackTrace();
            logger.debug(e);
        }
        catch (Exception e) {
            //            e.printStackTrace();
            logger.debug(e);
        }
        return resultObj;
    }

    /**
     * Here we sending the parameters like jsonStr,activeTenantsMap,apiUrl,apiKey
     */

    /**
     * @param jsonStr
     * @param activeTenantsMap
     * @param apiUrl
     * @param apiKey
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ParseException
     * @throws Exception
     */
    public JSONObject prepareCiscoDnasJSONString(final String jsonStr, final Map<String, Long> activeTenantsMap, final String apiUrl,
            final String apiKey) throws ClientProtocolException, IOException, ParseException, Exception {
        JSONObject resultObj = new JSONObject();
        JSONParser parser = new JSONParser();
        JSONObject responseObject = (JSONObject) parser.parse(jsonStr);
        JSONArray result = new JSONArray();

        logger.debug("responseObject: " + responseObject);
        if (responseObject.containsKey("deviceLocationUpdate")) {
            JSONObject jsonDataObj = (JSONObject) responseObject.get("deviceLocationUpdate");
            // logger.debug("jsonDataObj: " + jsonDataObj);

            JSONObject jsonObject = new JSONObject();
            if (responseObject.containsKey("partnerTenantId")) {
                String partnerTenantId = responseObject.get("partnerTenantId").toString();
                // check wether   partnerTenantId is exiting  from data base from list of activeTenantsMap.

                if (StringUtils.isNotEmpty(partnerTenantId)) {

                    for (Map.Entry<String, Long> entry : activeTenantsMap.entrySet()) {
                        if (entry.getKey().equals(partnerTenantId)) {
                            if (entry.getValue() != null) {
                                //Here we have to process the CiscoFireHose data and use data according to process which you required.
                                setDataToResponse(jsonDataObj, jsonObject, result, parser, entry.getValue());
                            }
                            else {
                                logger.error("This packet is not from valid tenantId");
                            }
                            break;
                        }
                        /*else {
                            logger.error("Null parentTenantId from response.");
                        }*/
                    }
                }
                else {
                    logger.error("Null parentTenantId from response.");
                }
            }
            else {
                logger.error("parentTenantId obj not found.");
            }
        }

        if (result.size() > 0) {
            resultObj.put("notifications", result);
        }
        return resultObj;
    }

    /**
     * @param devicePresenceEventResponse
     */
    private void raiseAlert(final org.json.simple.JSONObject devicePresenceEventResponse) {
        //Here we are referring URL from local server to raise the alerts.
        String url = "";

        if (url != null) {
            HttpPost postRequest = new HttpPost(url);
            HttpClient client = HttpClientBuilder.create().build();
            StringEntity input;
            try {

                input = new StringEntity(devicePresenceEventResponse.toJSONString());

                input.setContentType("application/json");
                postRequest.setEntity(input);
                HttpResponse response = client.execute(postRequest);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    logger.debug("Raise alert success :");
                }
                else {
                    logger.debug("Failed : HTTP error code : " + statusCode);
                }
            }
            catch (ClientProtocolException e) {
                //                e.printStackTrace();
                logger.debug(e);
            }
            catch (IOException e) {
                //                e.printStackTrace();
                logger.debug(e);
            }
        }
    }

    /**
     * @param jsonDataObj
     * @param jsonObject
     * @param result
     * @param parser
     * @param orgId
     * @throws ParseException
     */
    public void setDataToResponse(final JSONObject jsonDataObj, final JSONObject jsonObject, final JSONArray result,
            final JSONParser parser, final Long orgId) throws ParseException {
        logger.debug("Formated JsonObject Packet from cisco dnas." + jsonObject);

        jsonObject.put("orgId", orgId);

        String device = jsonDataObj.get("device").toString();
        JSONObject deviceObj = (JSONObject) parser.parse(device);
        if (deviceObj.containsKey("macAddress")) {
            logger.info("Received data from Cisco DNAS firehose for macAddress: " + deviceObj.get("macAddress").toString());
            jsonObject.put("macAddress", deviceObj.get("macAddress").toString());
        }

        String convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ = DateUtil
                .convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ(new Date(Long.valueOf(jsonDataObj.get("lastSeen").toString())));
        if (jsonDataObj.containsKey("lastSeen")) {
            logger.debug("lastseen recieved from firehouse: " + jsonDataObj.get("lastSeen").toString() + " after converting lastseen:: "
                    + convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ);
            jsonObject.put("lastBeaconTime", convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ);

        }

        jsonObject.put("currentServerTime", DateUtil.convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ(new Date()));

        JSONObject locationObj = new JSONObject();
        locationObj.put("unit", "FEET");

        Double xVal = Double.valueOf(jsonDataObj.get("xPos").toString());
        locationObj.put("x", xVal);

        Double yVal = Double.valueOf(jsonDataObj.get("yPos").toString());
        locationObj.put("y", yVal);

        locationObj.put("z", 0);

        jsonObject.put("locationCoordinate", locationObj);
        jsonObject.put("percentRemaining", null);
        jsonObject.put("teleType", "");
        result.add(jsonObject);
    }

    /**
     * @param jsonDataObj
     * @param jsonObject
     * @param result
     * @param strLevelId
     * @param strMapId
     * @param parser
     * @param orgId
     * @throws ParseException
     */
    public void setDataToResponse(final JSONObject jsonDataObj, final JSONObject jsonObject, final JSONArray result,
            final String strLevelId, final String strMapId, final JSONParser parser, final Long orgId) throws ParseException {
        logger.debug("Formated JsonObject Packet from cisco dnas." + jsonObject);
        if (strLevelId != null) {
            jsonObject.put("floorRefId", strLevelId);
        }

        jsonObject.put("orgId", orgId);

        String device = jsonDataObj.get("device").toString();
        JSONObject deviceObj = (JSONObject) parser.parse(device);
        if (deviceObj.containsKey("macAddress")) {
            logger.info("Received data from Cisco DNAS firehose for macAddress: " + deviceObj.get("macAddress").toString());
            jsonObject.put("macAddress", deviceObj.get("macAddress").toString());
        }

        String convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ = DateUtil
                .convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ(new Date(Long.valueOf(jsonDataObj.get("lastSeen").toString())));
        if (jsonDataObj.containsKey("lastSeen")) {
            logger.debug("lastseen recieved from firehouse: " + jsonDataObj.get("lastSeen").toString() + " after converting lastseen:: "
                    + convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ);
            jsonObject.put("lastBeaconTime", convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ);

        }

        jsonObject.put("currentServerTime", DateUtil.convertDATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SSSZ(new Date()));

        JSONObject locationObj = new JSONObject();
        locationObj.put("unit", "FEET");

        Double xVal = Double.valueOf(jsonDataObj.get("xPos").toString());
        locationObj.put("x", xVal);

        Double yVal = Double.valueOf(jsonDataObj.get("yPos").toString());
        locationObj.put("y", yVal);

        locationObj.put("z", 0);

        jsonObject.put("locationCoordinate", locationObj);
        jsonObject.put("percentRemaining", null);
        jsonObject.put("teleType", "");
        result.add(jsonObject);
    }
}