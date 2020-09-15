/**
 *
 */
package org.nipun.cisco.WebEx;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author manideep.s
 *
 */
/**
 * The {@code CraetingUserWebEx} class represents room creation i.e .Create a new room account for a given organization in WebEx and saving
 * the response for room.
 *
 * @see java.lang.Object#toString()
 * @author manideep.s
 *
 */
@Service
public class WebExRoomCreation {
    static Logger logger = Logger.getLogger(WebExRoomCreation.class);

    /**
     * @param args
     */
    public static void main(final String[] args) {
        try {
            /**
             * Here send the required input arguments for creating a room. Arguments like teamId, final String roomTitle, final String
             * accessToken, roomCreationWebExUrl. Url for room creation is https://webexapis.com/v1/rooms of POST method.
             *
             *
             */
            JSONObject webExRoomCreation = webExRoomCreation("YzRTYabcddddIzRTY", "TestingRoom", "abcddddIzRTY",
                    "https://webexapis.com/v1/rooms");
            logger.info("webExRoomCreation" + webExRoomCreation);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new room account for a given organization. Url for using room creation is https://webexapis.com/v1/rooms of POST method. The
     * following body parameters is required to create a new room:json object with key names . If the room created successfully then will
     * get a status code 200 with a response.
     *
     *
     */
    /**
     * @param strTeamId
     * @param strRoomTitle
     * @param strRoomCreationWebExUrl
     * @param strAccessToken
     * @return
     * @throws Exception
     */
    public static JSONObject webExRoomCreation(final String strTeamId, final String strRoomTitle, final String strAccessToken,
            final String strRoomCreationWebExUrl) throws Exception {
        logger.info("WebExRoom Method");
        JSONObject objResult = new JSONObject();
        JSONObject objRoomCreation = new JSONObject();
        objRoomCreation.put("title", strRoomTitle);
        objRoomCreation.put("teamId", strTeamId);
        HttpPost httpPostRoom = new HttpPost(strRoomCreationWebExUrl);
        httpPostRoom.setHeader("Content-type", "application/json");
        httpPostRoom.setHeader("Authorization", "Bearer " + strAccessToken);
        logger.info("strAccessToken:" + strAccessToken);
        HttpClient client = HttpClientBuilder.create().build();
        try {
            StringEntity objStringEntity = new StringEntity(objRoomCreation.toString());
            httpPostRoom.setEntity(objStringEntity);
            httpPostRoom.getRequestLine();
            httpPostRoom.setEntity(objStringEntity);
            HttpResponse objHttpResponse = client.execute(httpPostRoom);
            int intStatusCode = objHttpResponse.getStatusLine().getStatusCode();
            logger.info("intStatusCode:" + intStatusCode);
            if (intStatusCode == 200) {
                String objResponse = EntityUtils.toString(objHttpResponse.getEntity());
                objResult.put("status", intStatusCode);
                logger.info("objResponse:" + objResponse);
                objResult.put("response", objResponse);
                objResult.put("msg", "success");
            }
            else {
                objResult.put("status", intStatusCode);
                objResult.put("msg", "fail");
            }

        }
        catch (Exception e) {
            logger.info("catchException:" + e);
            throw new RuntimeException(e);
        }
        return objResult;

    }
}
