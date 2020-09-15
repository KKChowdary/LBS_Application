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
 * The {@code CraetingUserWebEx} class represents adding users to room i.e .adding users to WebEx room which is created and saving
 *
 * @see java.lang.Object#toString()
 * @author manideep.s
 *
 */
@Service
public class AddingUsersToRoom {
    static Logger logger = Logger.getLogger(AddingUsersToRoom.class);

    /**
     * Adding users to room which is created in WebEx for a given organization. Url for adding users to room is
     * https://webexapis.com/v1/memberships of POST method. The following body parameters is required to adding members to room:json object
     * with key name. If the team created successfully then will get a status code 200 with a response.
     *
     */
    /**
     * @param strUserEmail
     * @param strRoomId
     * @param bIsModerator
     * @param strAccessToken
     * @param strAddingMembersToRoomWebExUrl
     * @return
     * @throws Exception
     */
    public static JSONObject addingUsersToRoom(final String strUserEmail, final String strRoomId, final Boolean bIsModerator,
            final String strAccessToken, final String strAddingMembersToRoomWebExUrl) throws Exception {
        logger.info("AddingUsersToRoomm Method");
        JSONObject objResult = new JSONObject();
        JSONObject objAddingMembersToRoom = new JSONObject();
        objAddingMembersToRoom.put("personEmail", strUserEmail);
        objAddingMembersToRoom.put("roomId", strRoomId);
        objAddingMembersToRoom.put("isModerator", bIsModerator);
        HttpPost httpPostAddingMembersToRoom = new HttpPost(strAddingMembersToRoomWebExUrl);
        HttpClient client = HttpClientBuilder.create().build();
        httpPostAddingMembersToRoom.setHeader("Content-type", "application/json");
        httpPostAddingMembersToRoom.setHeader("Authorization", "Bearer " + strAccessToken);
        logger.info("strAccessToken:" + strAccessToken);
        try {
            StringEntity stringEntity = new StringEntity(objAddingMembersToRoom.toString());
            httpPostAddingMembersToRoom.setEntity(stringEntity);
            HttpResponse objHttpResponse = client.execute(httpPostAddingMembersToRoom);
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

    /**
     * @param args
     */
    public static void main(final String[] args) {
        try {
            /**
             * Here send the required input arguments for addingusers to room. Arguments like userEmail,roomId, false or true,
             * accessToken,url. Url for adding users to room is https://webexapis.com/v1/memberships of POST method. The following body
             * parameters is required to create a new adding members to room.
             *
             */
            JSONObject addingUsersToRoom = addingUsersToRoom("abcdefgh@gmail.com", "Y2lzY29zcGFyazovL3VzL1JPT00vYjc4ZDY3NzAtZjZlNi0xMWV0",
                    false, "abcddddIzRTY", "https://webexapis.com/v1/memberships");
            logger.info("addingUsersToRoom:" + addingUsersToRoom);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
