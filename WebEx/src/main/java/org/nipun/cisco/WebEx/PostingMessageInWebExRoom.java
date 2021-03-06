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
 * The {@code CraetingUserWebEx} class represents posting message to room i.e .posting the message to WebEx room which is created.
 *
 * @see java.lang.Object#toString()
 * @author manideep.s
 *
 */
@Service
public class PostingMessageInWebExRoom {
    static Logger logger = Logger.getLogger(PostingMessageInWebExRoom.class);

    /**
     * @param args
     */
    public static void main(final String[] args) {
        try {
            /**
             * Here send the required input arguments for addingusers to room. Arguments like roomId,message, accessToken,url. Url for
             * posting message in room is https://webexapis.com/v1/messages of POST method. The following body parameters is required to
             * posting message in room.
             *
             */
            JSONObject postingMessageInWebExRoom = postingMessageInWebExRoom("Y2lzY29zcGFyazovL3VzL1JPT00vYjc4ZDY3NzAtZjZlNi0xMWVhL",
                    "Hello Testing", "abcddddIzRTY", "https://webexapis.com/v1/messages");
            logger.info("postingMessageInWebExRoom:" + postingMessageInWebExRoom);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

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
    public static JSONObject postingMessageInWebExRoom(final String strRoomId, final String strMessage, final String strAccessToken,
            final String strPostingWebExMessageInRoom) throws Exception {
        logger.info("postingMessageInWebExRoom Method");
        JSONObject objResult = new JSONObject();
        JSONObject objWebExPostMsg = new JSONObject();
        objWebExPostMsg.put("roomId", strRoomId);
        objWebExPostMsg.put("text", strMessage);
        HttpPost httpPostMessageInRoom = new HttpPost(strPostingWebExMessageInRoom);
        HttpClient client = HttpClientBuilder.create().build();
        httpPostMessageInRoom.setHeader("Content-type", "application/json");
        httpPostMessageInRoom.setHeader("Authorization", "Bearer " + strAccessToken);
        logger.info("strAccessToken:" + strAccessToken);
        try {
            StringEntity objStringEntity = new StringEntity(objWebExPostMsg.toString());
            httpPostMessageInRoom.setEntity(objStringEntity);
            HttpResponse response = client.execute(httpPostMessageInRoom);
            String objResponse = EntityUtils.toString(response.getEntity());
            int intStatusCode = response.getStatusLine().getStatusCode();
            logger.info("intStatusCode:" + intStatusCode);
            if (intStatusCode == 200) {
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
