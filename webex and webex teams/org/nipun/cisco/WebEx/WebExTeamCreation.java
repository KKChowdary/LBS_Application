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
 * The {@code CraetingUserWebEx} class represents team creation for i.e .Create a new team account for a given organization in WebEx and
 * saving the response for team.
 *
 * @see java.lang.Object#toString()
 * @author manideep.s
 *
 */
@Service
public class WebExTeamCreation {
    /**
     * Create a new team account for a given organization. Url for using user creation is https://webexapis.com/v1/people of POST method.
     * The following body parameters is required to create a new team:json object with key name. If the team created successfully then will
     * get a status code 200 with a response.
     *
     *
     */
    /**
     * @param strTeamName
     * @param strTeamCreationWebExUrl
     * @param strAccessToken
     * @return
     * @throws Exception
     */
    public JSONObject webExTeamCreation(final String strTeamName, final String strTeamCreationWebExUrl, final String strAccessToken)
            throws Exception {
        System.out.println("WebExTeam Working");
        JSONObject objResult = new JSONObject();
        JSONObject objTeamCreation = new JSONObject();
        objTeamCreation.put("name", strTeamName);
        HttpPost httpPostTeam = new HttpPost(strTeamCreationWebExUrl);
        httpPostTeam.setHeader("Content-type", "application/json");
        httpPostTeam.setHeader("Authorization", "Bearer " + strAccessToken);
        HttpClient client = HttpClientBuilder.create().build();
        try {
            StringEntity objStringEntity = new StringEntity(objTeamCreation.toString());
            httpPostTeam.setEntity(objStringEntity);
            HttpResponse objHttpResponse = client.execute(httpPostTeam);
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
