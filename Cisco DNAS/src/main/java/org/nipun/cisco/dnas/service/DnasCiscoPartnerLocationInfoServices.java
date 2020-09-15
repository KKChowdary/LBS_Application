/**
 *
 */
package org.nipun.cisco.dnas.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.nipun.cisco.dnas.common.util.Constants;
import org.springframework.stereotype.Component;

/**
 * @author himabindu.v
 *
 */
/**
 * @author himabindu.v
 *
 */
@Component
public class DnasCiscoPartnerLocationInfoServices {
    Logger logger = Logger.getLogger(DnasCiscoPartnerLocationInfoServices.class);

    public String getLocationType(final JSONObject locationResponse) throws ParseException {
        JSONParser parser = new JSONParser();
        logger.debug("locationResponse :" + locationResponse);
        String inferredLocationTypes = locationResponse.get("inferredLocationTypes").toString();
        JSONArray locationTypes = (JSONArray) parser.parse(inferredLocationTypes);

        for (Object jsonObject : locationTypes) {
            if (jsonObject.toString().equalsIgnoreCase(Constants.CMXZONE)) {
                return Constants.CMXZONE;
            }
            else if (jsonObject.toString().equalsIgnoreCase(Constants.ZONE)) {
                return Constants.ZONE;
            }
            else if (jsonObject.toString().equalsIgnoreCase(Constants.FLOOR)) {
                return Constants.FLOOR;
            }
            else if (jsonObject.toString().equalsIgnoreCase(Constants.BUILDING)) {
                return Constants.BUILDING;
            }
            else if (jsonObject.toString().equalsIgnoreCase(Constants.CAMPUS)) {
                return Constants.CAMPUS;
            }
        }

        return null;
    }

    /**
     * @param mapId
     */
    public String getMapDetails(final String mapId, final String url, final String apiKey, final String partnerTenantId)
            throws ClientProtocolException, IOException, Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append("/api/partners/v1/maps/");
        builder.append(mapId);
        builder.append("?partnerTenantId=");
        builder.append(partnerTenantId);
        HttpGet httpGetRequest = new HttpGet(builder.toString());
        httpGetRequest.addHeader("X-API-KEY", apiKey);

        HttpResponse response = httpClient.execute(httpGetRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        String responseFromDna = EntityUtils.toString(response.getEntity());
        if (statusCode == 200) {
            logger.info("Map response from cisco dna space :" + responseFromDna);
            return responseFromDna;
        }
        return null;
    }

    public InputStream getMapImageDetails(final String mapId, final String url, final String apiKey, final String partnerTenantId)
            throws ClientProtocolException, IOException, Exception {
        logger.info("started the excecution getMapImageDetails :");
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append("/api/partners/v1/maps/");
        builder.append(mapId + "/");
        builder.append("image");
        builder.append("?partnerTenantId=");
        builder.append(partnerTenantId);
        //HttpGet httpGetRequest = new HttpGet(builder.toString());
        URL imageurl = new URL(builder.toString());
        HttpURLConnection myURLConnection = (HttpURLConnection) imageurl.openConnection();
        // URLConnection urlConnection = imageurl.openConnection();
        myURLConnection.setRequestProperty("X-API-KEY", apiKey);

        //HttpResponse response = httpClient.execute(httpGetRequest);
        // int statusCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = myURLConnection.getInputStream();
        return inputStream;
    }

    public String getResponseLocationInformation(final String locationId, final String url, final String apiKey,
            final String partnerTenantId) throws ClientProtocolException, IOException, Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append("/api/partners/v1/locations/");
        builder.append(locationId);
        builder.append("?partnerTenantId=");
        builder.append(partnerTenantId);
        HttpGet httpGetRequest = new HttpGet(builder.toString());
        httpGetRequest.addHeader("X-API-KEY", apiKey);

        HttpResponse response = httpClient.execute(httpGetRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        String responseFromDna = EntityUtils.toString(response.getEntity());
        if (statusCode == 200) {
            logger.info("Location response from cisco dna space :" + responseFromDna);
            return responseFromDna;
        }
        return null;
    }

    /**
     * @param lOrgId
     * @param campusesByCampusId
     * @param strCampusLocationId
     */
    private void saveBuildingAreaByTblCampus(final Long lOrgId, final JSONObject objBuildingArea) {
        String strLocationId = objBuildingArea.get("locationId").toString();
        String strLocationName = objBuildingArea.get("name").toString();
        String strLocationCode = strLocationName.replaceAll("\\s", "");

        logger.debug("saved building area for LocationName: " + strLocationName + " strLocationId:" + strLocationId);

    }

    public void saveBuildingBasedOnCampus(final Long tblOrgId, final String locationId, final String url, final String apiKey,
            final String partnerTenantId) throws ClientProtocolException, IOException, ParseException, Exception {

        String responseLocationInformation = getResponseLocationInformation(locationId, url, apiKey, partnerTenantId);
        if (StringUtils.isNotBlank(responseLocationInformation)) {
            JSONParser parser = new JSONParser();
            JSONObject responseObject = (JSONObject) parser.parse(responseLocationInformation);
            if (responseObject != null) {
                JSONObject objLocation = (JSONObject) responseObject.get("location");
                if (objLocation != null) {
                    JSONObject objCampus = (JSONObject) objLocation.get("parent");
                    if (objCampus != null) {
                        //check inferredLocationTypes if campus
                        String locationType = getLocationType(responseObject);
                        if (StringUtils.isNotEmpty(locationType)) {
                            if (locationType.equalsIgnoreCase(Constants.CAMPUS)) {
                                String strCampusLocationId = objCampus.get("locationId").toString();
                                if (StringUtils.isNotBlank(strCampusLocationId)) {
                                    //Check campus is in from local data base. if not exited.
                                    //saveBuildingAreaByTblCampus(tblOrgId, objCampus);
                                    // create Campus and save to your local database
                                    saveTblCampus(tblOrgId, objCampus);

                                    saveFloorByTblBuildingArea(tblOrgId, objLocation);

                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void saveCampusByLocationId(final Long tblOrgId, final String locationId, final String url, final String apiKey,
            final String partnerTenantId) throws ClientProtocolException, IOException, ParseException, Exception {
        String responseLocationInformation = getResponseLocationInformation(locationId, url, apiKey, partnerTenantId);

        if (StringUtils.isNotBlank(responseLocationInformation)) {
            JSONParser parser = new JSONParser();
            JSONObject responseObject = (JSONObject) parser.parse(responseLocationInformation);
            if (responseObject != null) {
                JSONObject objLocation = (JSONObject) responseObject.get("location");
                if (objLocation != null) {
                    String strCampusLocationId = objLocation.get("locationId").toString();
                    if (StringUtils.isNotBlank(strCampusLocationId)) {
                        //save campus
                        //save  Building
                        //save  Floor
                        /* saveTblCampus(tblOrgId, objLocation, tblCampus, tblThirdPartyApi);
                        saveBuildingAreaByTblCampus(tblOrgId, tblCampus, objLocation, tblBuildingArea, tblThirdPartyApi);
                        tblLevel = saveFloorByTblBuildingArea(tblOrgId, tblBuildingArea, objLocation, tblThirdPartyApi);*/
                    }
                }
            }
        }

    }

    public void saveFloorBasedOnBuilding(final Long tblOrgId, final String locationId, final String url, final String apiKey,
            final String partnerTenantId) throws ClientProtocolException, IOException, ParseException, Exception {
        String responseLocationInformation = getResponseLocationInformation(locationId, url, apiKey, partnerTenantId);
        Map<String, Object> response = new HashMap<String, Object>();

        Long threadNo = Thread.currentThread().getId();
        String strThreadNo = "Thread No[" + threadNo + "]:";
        if (StringUtils.isNotBlank(responseLocationInformation)) {
            logger.info(strThreadNo + "Started executing saveFloorBasedOnBuilding." + " locationId:" + locationId
                    + " responseLocationInformation: " + responseLocationInformation);
            JSONParser parser = new JSONParser();
            JSONObject responseObject = (JSONObject) parser.parse(responseLocationInformation);
            logger.info(" responseObject:" + responseObject);
            if (responseObject != null) {
                JSONObject objLocationDna = (JSONObject) responseObject.get("location");
                System.out.println("objLocationDna  " + objLocationDna);
                if (objLocationDna != null) {
                    JSONObject objJsonBuildingDna = (JSONObject) objLocationDna.get("parent");
                    logger.info("objJsonBuildingDna  :" + objJsonBuildingDna);
                    JSONObject objJsonBuildingDn1 = (JSONObject) objLocationDna.get("parent");
                    if (objJsonBuildingDna != null) {
                        //check inferredLocationTypes if building
                        String locationType = getLocationType(objJsonBuildingDna);
                        logger.info(strThreadNo + " locationType :" + locationType);
                        if (StringUtils.isNotEmpty(locationType)) {
                            if (locationType.equalsIgnoreCase(Constants.BUILDING)) {
                                String strBuildingLocationId = objJsonBuildingDna.get("locationId").toString();
                                logger.debug(strThreadNo + " strBuildingLocationId :" + strBuildingLocationId);
                                if (StringUtils.isNotBlank(strBuildingLocationId)) {

                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * @param orgId
     * @param objLocation
     */
    public void saveFloorByTblBuildingArea(final Long orgId, final JSONObject objLocation) {
        String strLocationId = objLocation.get("locationId").toString();
        String strLocationName = objLocation.get("name").toString();
        String strLocationCode = strLocationName.replaceAll("\\s", "");

        logger.debug("strLocationName :" + strLocationName + " strLocationCode: " + strLocationCode);

    }

    /**
     * @param lOrgId
     * @param objCampus
     * @param tblCampus
     */
    private void saveTblCampus(final Long lOrgId, final JSONObject objCampus) {
        Long threadNo = Thread.currentThread().getId();
        String strThreadNo = "Thread No[" + threadNo + "]:";
        logger.debug("saved campus strThreadNo : [" + strThreadNo + "]");
        String strLocationId = objCampus.get("locationId").toString();
        String strLocationName = objCampus.get("name").toString();
        String strLocationCode = strLocationName.replaceAll("\\s", "");
        // you can save in your local db

    }

    public Map<String, Object> saveZoneBasedOnFloor(final Long tblOrgId, final String locationId, final String url, final String apiKey,
            final String partnerTenantId) throws ClientProtocolException, IOException, ParseException, Exception {
        String responseLocationInformation = getResponseLocationInformation(locationId, url, apiKey, partnerTenantId);

        Map<String, Object> response = new HashMap<String, Object>();
        Long threadNo = Thread.currentThread().getId();
        String strThreadNo = "Thread No[" + threadNo + "]:";
        if (StringUtils.isNotBlank(responseLocationInformation)) {
            logger.debug(strThreadNo + "Started executing saveZoneBasedOnFloor." + " locationId:" + locationId
                    + " responseLocationInformation: " + responseLocationInformation);
            JSONParser parser = new JSONParser();
            JSONObject responseObject = (JSONObject) parser.parse(responseLocationInformation);
            logger.info(" responseObject:" + responseObject);
            if (responseObject != null) {
                JSONObject objLocationDna = (JSONObject) responseObject.get("location");
                JSONObject locationDetails = (JSONObject) responseObject.get("locationDetails");
                logger.debug("locationDetails:" + locationDetails);
                JSONObject mapDetails = null;
                if (locationDetails.containsKey("mapDetails")) {
                    mapDetails = (JSONObject) locationDetails.get("mapDetails");
                }
                logger.debug("objLocationDna  " + objLocationDna);
                JSONObject dimensionObj = null;
                if (mapDetails != null) {
                    if (mapDetails.containsKey("dimension")) {
                        dimensionObj = (JSONObject) mapDetails.get("dimension");
                    }
                }
                if (objLocationDna != null) {
                    JSONObject objJsonFloorDna = (JSONObject) objLocationDna.get("parent");

                    if (objJsonFloorDna != null) {
                        String locationType = getLocationType(objJsonFloorDna);
                        if (StringUtils.isNotEmpty(locationType)) {
                            String strFloorLocationId = objJsonFloorDna.get("locationId").toString();
                            logger.debug(strThreadNo + " strFloorLocationId :" + strFloorLocationId);
                            if (StringUtils.isNotBlank(strFloorLocationId)) {
                                logger.info("Check level already exist from your local data base.");
                                boolean bLevel = false;
                                if (bLevel) {
                                    logger.debug(strThreadNo + " Level already exist.");
                                    logger.debug("Level already exist.so we can check floor");
                                    // tblZone = saveZoneByFloor(tblOrgId, objLocationDna, level, tblThirdPartyApi);
                                    //response.put("tblZone", tblZone);
                                    //response.put("tblLevel", tblZone.getTblLevel());
                                }
                                else {
                                    //check inferredLocationTypes if building
                                    logger.debug(strThreadNo + " locationType :" + locationType);
                                    if (locationType.equalsIgnoreCase(Constants.FLOOR)) {
                                        JSONObject objJsonBuildingDna = (JSONObject) objJsonFloorDna.get("parent");
                                        logger.debug("objJsonBuildingDna  :" + objJsonBuildingDna);
                                        if (objJsonBuildingDna != null) {
                                            String strBuildingLocationId = objJsonBuildingDna.get("locationId").toString();
                                            logger.debug(strThreadNo + " strBuildingLocationId :" + strBuildingLocationId);
                                            if (StringUtils.isNotBlank(strBuildingLocationId)) {
                                                // buildingAreaService.getBuildingsByBuildingId(strBuildingLocationId, tblOrgId);

                                                logger.debug(strThreadNo + " Building not exist");

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return response;
    }

}