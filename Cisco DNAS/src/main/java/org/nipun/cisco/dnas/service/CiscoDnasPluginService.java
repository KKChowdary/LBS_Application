/**
 *
 */
package org.nipun.cisco.dnas.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.nipun.cisco.dnas.common.util.Constants;
import org.nipun.cisco.dnas.common.util.DateUtil;
import org.nipun.cisco.dnas.events.enums.EventTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
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

    @Autowired
    DnasCiscoPartnerLocationInfoServices dnaCiscoPartnerLocationInfoServices;

    public static void saveCiscoDnasResponseInTextFile(final String response) {

        try {
            //logger.info("FileLength is:" + iDataLength);

            StringBuilder directoryFilePath = new StringBuilder();
            directoryFilePath.append("/home/CiscoDnas");
            directoryFilePath.append(File.separator);

            File directory = new File(directoryFilePath.toString());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            directoryFilePath.append(new Date().getTime() + ".txt");

            try {
                OutputStream osOutputStream = new FileOutputStream(directoryFilePath.toString());
                osOutputStream.write(response.getBytes());
                osOutputStream.close();
            }
            catch (IOException ex) {
                //logger.error("exception occoured", ex);
            }
        }
        catch (Exception ex) {

        }
    }

    public Map<String, Object> checkLocations(final JSONObject locationResponse, final Long tblOrgId, final String partnerTenantId,
            final String apiUrl, final String apiKey) throws ClientProtocolException, IOException, ParseException, Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        String locationType = dnaCiscoPartnerLocationInfoServices.getLocationType(locationResponse);
        String locationName = locationResponse.get("name").toString();
        String locationId = locationResponse.get("locationId").toString();
        logger.debug("locationResponse :" + locationResponse + "locationType:" + locationType);
        if (StringUtils.isNotEmpty(locationType)) {
            /* TblZone tblZone = null;*/
            switch (locationType) {
                case Constants.CMXZONE:
                    logger.debug("Check CMX Zone by locationId  is exited from your local data base.");
                    // if CMX Zone is not  existed.so we have to create zone based on location response.

                    //create Floor
                    return dnaCiscoPartnerLocationInfoServices.saveZoneBasedOnFloor(tblOrgId, locationId, apiUrl, apiKey, partnerTenantId);

                case Constants.ZONE:
                    logger.debug("Check CMX Zone by locationId  is exited from your local data base.");
                    // if CMX Zone is not  existed.so we have to create zone based on location response.
                    //create Floor
                    return dnaCiscoPartnerLocationInfoServices.saveZoneBasedOnFloor(tblOrgId, locationId, apiUrl, apiKey, partnerTenantId);

                case Constants.FLOOR:

                    //create Floor
                    dnaCiscoPartnerLocationInfoServices.saveFloorBasedOnBuilding(tblOrgId, locationId, apiUrl, apiKey, partnerTenantId);

                    return response;

                case Constants.BUILDING:
                    // if building is not there.create building in your local database.
                    dnaCiscoPartnerLocationInfoServices.saveBuildingBasedOnCampus(tblOrgId, locationId, apiUrl, apiKey, partnerTenantId);
                case Constants.CAMPUS:

                    // if campus is not there.create campus in your local database.
                    dnaCiscoPartnerLocationInfoServices.saveCampusByLocationId(tblOrgId, locationId, apiUrl, apiKey, partnerTenantId);

                default:
                    logger.info("Level type was not getting from cisco dnas.");
            }
        }
        return null;

    }

    public void checkMapInfo(final String mapId, final String url, final String apiKey, final String partnerTenantId, final Long orgId)
            throws ClientProtocolException, IOException, Exception {
        String mapResponse = dnaCiscoPartnerLocationInfoServices.getMapDetails(mapId, url, apiKey, partnerTenantId);

        if (StringUtils.isNotBlank(mapResponse)) {
            JSONParser parser = new JSONParser();
            JSONObject responseObject = (JSONObject) parser.parse(mapResponse);
            if (responseObject != null) {
                logger.debug(" responseObject :" + responseObject);

                logger.debug(" responseObject.get(\"imageWidth\").toString() : " + responseObject.get("imageWidth").toString()
                        + " responseObject.get(\"imageHeight\").toString():" + responseObject.get("imageHeight").toString());
                String strDimension = responseObject.get("dimension").toString();
                JSONObject dimensionObj = (JSONObject) parser.parse(strDimension);
                logger.info(" dimensionObj.get(\"width\").toString() :" + dimensionObj.get("width").toString()
                        + " dimensionObj.get(\"length\").toString():" + dimensionObj.get("length").toString());

                InputStream mapImageDetails = dnaCiscoPartnerLocationInfoServices.getMapImageDetails(mapId, url, apiKey, partnerTenantId);
                logger.debug(" save mapImageDetails to your local server to retrive map in your application.");

            }
        }
        else {
            logger.info("From mapId,apiKey map image is not found");
        }
        //return tblMaps;
    }

    public String geDnasCiscoPluginResponse(final String jsonStr, final Map<String, Long> activeTenantsMap, final String apiUrl,
            final String apiKey) {
        ModelMap map = new ModelMap();
        try {
            logger.debug("getting response from cisco fire house:" + jsonStr);
            JSONObject resultObj = prepareCiscoDnasJSONString(jsonStr, activeTenantsMap, apiUrl, apiKey);
            logger.debug("Prepared JSON format  : " + resultObj.toString());
            String CISCODNASFIREHOUSE = "http://183.83.217.148:4020/alertListener.ashx";

            if (!resultObj.isEmpty()) {
                DnasCiscoFireHouseListenerService.sendDataToAlertListener(CISCODNASFIREHOUSE, resultObj.toString());
                //beconListenerService.sendDataToBeconListener(tblUrl.getUrl(), resultObj.toString());
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

    public JSONObject getDevicePresenceEventResponse(final String jsonStr, final Map<String, Long> activeTenantsMap) {
        // logger.debug("jsonStr: " + jsonStr);
        JSONObject resultObj = new JSONObject();
        JSONParser parser = new JSONParser();
        JSONObject responseObject;
        try {
            responseObject = (JSONObject) parser.parse(jsonStr);
            if (responseObject.containsKey("partnerTenantId")) {
                String partnerTenantId = responseObject.get("partnerTenantId").toString();
                //  logger.debug("partnerTenantId: " + partnerTenantId);
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

    public JSONObject prepareCiscoDnasJSONString(final String jsonStr, final Map<String, Long> activeTenantsMap, final String apiUrl,
            final String apiKey) throws ClientProtocolException, IOException, ParseException, Exception {
        JSONObject resultObj = new JSONObject();
        JSONParser parser = new JSONParser();
        JSONObject responseObject = (JSONObject) parser.parse(jsonStr);
        JSONArray result = new JSONArray();

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
                                /* TblSourceType tblSourceType = tblSourceTypeService.getBySourceName(Constants.DNA);
                                if (tblSourceType != null) {*/
                                // Get Matched tentantId from local dataabase.Present we are by passing
                                /* TblThirdPartyApi tblThirdPartyApi = tblThirdPartyApiService.getBySourceIdOrgId(tblSourceType.getId(),
                                        entry.getValue());*/

                                if (jsonDataObj.containsKey("location")) {
                                    String locationString = jsonDataObj.get("location").toString();

                                    try {
                                        JSONObject locationResponse = (JSONObject) parser.parse(locationString);

                                        if (!locationResponse.isEmpty()) {
                                            /*Runnable runnable = new Runnable() {*/
                                            /*@Override
                                            public void run() {
                                            try {*/

                                            String mapId = jsonDataObj.get("mapId").toString();

                                            Map<String, Object> locations = checkLocations(locationResponse, entry.getValue(),
                                                    partnerTenantId, apiUrl, apiKey);

                                            //  logger.debug(" locations:" + locations);
                                            if (locations != null) {

                                                setDataToResponse(jsonDataObj, jsonObject, result, parser, entry.getValue());
                                            }
                                        }
                                        else {
                                            logger.info("Location details are not getting from response.");
                                        }
                                        /*catch (Exception ex) {
                                        logger.error("Unknown exception occurred.");
                                        logger.debug(ex);
                                        }
                                        }
                                        };
                                        Thread checkLocationsThread = new Thread(runnable);
                                        checkLocationsThread.start();
                                        }
                                        else {
                                        logger.info("Location details are not getting from response.");
                                        }*/
                                    }
                                    catch (ParseException pex) {
                                        logger.error("Unknown exception occurred.");
                                        logger.debug(pex);
                                    }
                                }
                                else {
                                    logger.error("Location details are not getting from response.");
                                }

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
        // TblUrls tblUrls = urlService.getByUrlCode(Constants.TRIGGER_EVENT);
        String url = "http://192.168.15.252:9082/nview/event/triggerEvent";
        //    logger.debug("devicePresenceEventResponse: " + devicePresenceEventResponse);
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