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
 * The {@code CraetingUserWebEx} class represents posting message to room i.e .posting the message to WebEx room which is created.
 *
 * @see java.lang.Object#toString()
 * @author manideep.s
 *
 */
@Service
public class PostingMessageInWebExRoom {
    /**
     * In this method we are posting the message in WebEx room for a given organization. Url for using post message in room is
     * https://webexapis.com/v1/messages of POST method.The following body is required to post a new message in room:json object with key
     * name
     *
     *
     */
    /**
     * @param strRoomId
     * @param strMessage
     * @param strAccessToken
     * @param strPostingWebExMessageInRoom
     * @return
     * @throws Exception
     */
    public JSONObject postingMessageInWebExRoom(final String strRoomId, final String strMessage, final String strAccessToken,
            final String strPostingWebExMessageInRoom) throws Exception {
        System.out.println("postingMessageInWebExRoom Working");
        JSONObject objResult = new JSONObject();
        JSONObject objWebExPostMsg = new JSONObject();
        objWebExPostMsg.put("roomId", strRoomId);
        objWebExPostMsg.put("text", strMessage);
        HttpPost httpPostMessageInRoom = new HttpPost(strPostingWebExMessageInRoom);
        HttpClient client = HttpClientBuilder.create().build();
        httpPostMessageInRoom.setHeader("Content-type", "application/json");
        httpPostMessageInRoom.setHeader("Authorization", "Bearer " + strAccessToken);
        try {
            StringEntity objStringEntity = new StringEntity(objWebExPostMsg.toString());
            httpPostMessageInRoom.setEntity(objStringEntity);
            HttpResponse response = client.execute(httpPostMessageInRoom);
            String objResponse = EntityUtils.toString(response.getEntity());
            int intStatusCode = response.getStatusLine().getStatusCode();
            if (intStatusCode == 200) {
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
