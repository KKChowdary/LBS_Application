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
    /**
     * Adding users to room which is created in WebEx for a given organization. Url for adding users to room is
     * https://webexapis.com/v1/memberships of POST method. The following body parameters is required to create a new team:json object with
     * key name. If the team created successfully then will get a status code 200 with a response.
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
    public JSONObject addingUsersToRoom(final String strUserEmail, final String strRoomId, final Boolean bIsModerator,
            final String strAccessToken, final String strAddingMembersToRoomWebExUrl) throws Exception {
        System.out.println("WebExAddingUsersToRoom Working");
        JSONObject objResult = new JSONObject();
        JSONObject objAddingMembersToRoom = new JSONObject();
        objAddingMembersToRoom.put("personEmail", strUserEmail);
        objAddingMembersToRoom.put("roomId", strRoomId);
        objAddingMembersToRoom.put("isModerator", bIsModerator);
        HttpPost httpPostAddingMembersToRoom = new HttpPost(strAddingMembersToRoomWebExUrl);
        HttpClient client = HttpClientBuilder.create().build();
        httpPostAddingMembersToRoom.setHeader("Content-type", "application/json");
        httpPostAddingMembersToRoom.setHeader("Authorization", "Bearer " + strAccessToken);
        try {
            StringEntity stringEntity = new StringEntity(objAddingMembersToRoom.toString());
            httpPostAddingMembersToRoom.setEntity(stringEntity);
            HttpResponse objHttpResponse = client.execute(httpPostAddingMembersToRoom);
            int intStatusCode = objHttpResponse.getStatusLine().getStatusCode();
            if (intStatusCode == 200) {
                String objResponse = EntityUtils.toString(objHttpResponse.getEntity());
                objResult.put("status", intStatusCode);
                objResult.put("response", objResponse);
                objResult.put("msg", "success");
            }
            else {
                objResult.put("status", intStatusCode);
                objResult.put("msg", "fail");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return objResult;

    }
}
