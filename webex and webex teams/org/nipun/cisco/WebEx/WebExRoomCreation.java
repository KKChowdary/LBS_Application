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
    /**
     * Create a new room account for a given organization. Url for using user creation is https://webexapis.com/v1/people of POST method.
     * The following body parameters is required to create a new room:json object with key names . If the room created successfully then
     * will get a status code 200 with a response.
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
    public JSONObject webExRoomCreation(final String strTeamId, final String strRoomTitle, final String strAccessToken,
            final String strRoomCreationWebExUrl) throws Exception {
        System.out.println("WebExRoom Working");
        JSONObject objResult = new JSONObject();
        JSONObject objRoomCreation = new JSONObject();
        objRoomCreation.put("title", strRoomTitle);
        objRoomCreation.put("teamId", strTeamId);
        HttpPost httpPostRoom = new HttpPost(strRoomCreationWebExUrl);
        httpPostRoom.setHeader("Content-type", "application/json");
        httpPostRoom.setHeader("Authorization", "Bearer " + strAccessToken);

        HttpClient client = HttpClientBuilder.create().build();
        try {
            StringEntity objStringEntity = new StringEntity(objRoomCreation.toString());
            httpPostRoom.setEntity(objStringEntity);
            httpPostRoom.getRequestLine();
            httpPostRoom.setEntity(objStringEntity);
            HttpResponse objHttpResponse = client.execute(httpPostRoom);
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
