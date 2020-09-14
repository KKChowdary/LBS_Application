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
 * The {@code CraetingUserWebEx} class represents user creation for i.e .Create a new user account for a given organization in WebEx and
 * saving the response for user.
 *
 * @see java.lang.Object#toString()
 * @author manideep.s
 *
 */
@Service
public class WebExUserCreation {
    Logger logger = Logger.getLogger(WebExUserCreation.class);

    /**
     * Create a new user account for a given organization. Url for using user creation is https://webexapis.com/v1/people The following body
     * parameters is required to create a new user:emails,displayName,firstName,lastName. Currently, users may have only one email address
     * associated with their account.The email's parameter is an array, which accepts multiple values to allow for future expansion, but
     * currently only one email address will be used for the new user.If the user created successfully then will get a response code 200
     * with a response.
     *
     *
     */

    /**
     * @param emailArray
     * @param orgId
     * @param displayName
     * @param firstName
     * @param lastName
     * @return
     * @throws Exception
     */
    public JSONObject webExUserCreation(final String[] arrEmails, final String strUserDisplayName, final String strUserFirstName,
            final String strUserLastName, final String strHostUserCreationWebExUrl, final String strAccessToken) throws Exception {
        JSONObject objResult = new JSONObject();
        JSONObject objWebExuserCreation = new JSONObject();
        objWebExuserCreation.put("emails", arrEmails);
        objWebExuserCreation.put("displayName", strUserDisplayName);
        objWebExuserCreation.put("firstName", strUserFirstName);
        objWebExuserCreation.put("lastName", strUserLastName);
        HttpPost httpPost = new HttpPost(strHostUserCreationWebExUrl);
        HttpClient client = HttpClientBuilder.create().build();
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + strAccessToken);

        try {
            StringEntity objStringEntity = new StringEntity(objWebExuserCreation.toString());
            httpPost.setEntity(objStringEntity);
            HttpResponse objHttpResponse = client.execute(httpPost);
            String objResponse = EntityUtils.toString(objHttpResponse.getEntity());
            int intStatusCode = objHttpResponse.getStatusLine().getStatusCode();
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
