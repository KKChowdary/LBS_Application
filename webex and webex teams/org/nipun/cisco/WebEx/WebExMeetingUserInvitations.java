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
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

/**
 * The {@code CraetingUserWebEx} class represents WebEx invites i.e. Inviting the members for webEx meeting
 *
 * @see java.lang.Object#toString()
 * @author manideep.s
 *
 */
/**
 * @author manideep.s
 *
 */
@Service
public class WebExMeetingUserInvitations {
    /**
     * In this method we are inviting members to join in WebEx meeting by scheduling the meeting and sending mails to members.Url for
     * inviting members to join in meeting is https://webexapis.com/v1/meetings of POST method.The following body parameters is required to
     * invite members for meeting:title,agenda,start,end,timezone,enabledAutoRecordMeeting, allowAnyUserToBeCoHost,invitees
     *
     *
     */

    /**
     * "arrInvitees": [ { "email": "john.andersen@example.com", "displayName": "John Andersen", "coHost": false }, { "email":
     * "brenda.song@example.com", "displayName": "Brenda Song", "coHost": false } ]
     */
    /**
     * strStartTime:2019-11-01 20:00:00 strEndTime:2019-11-01 21:00:00
     */
    /**
     * @param strTitle
     * @param strAgenda
     * @param strPassword
     * @param strStartTime
     * @param strEndTime
     * @param strTimezone
     * @param bEnabledAutoRecordMeeting
     * @param bAllowAnyUserToBeCoHost
     * @param arrInvitees
     * @param strAccessToken
     * @param strHostUserCreationWebExUrl
     * @return
     * @throws Exception
     */
    public JSONObject webExMeetingUserInvitations(final String strTitle, final String strAgenda, final String strPassword,
            final String strStartTime, final String strEndTime, final String strTimezone, final boolean bEnabledAutoRecordMeeting,
            final boolean bAllowAnyUserToBeCoHost, final JSONArray arrInvitees, final String strAccessToken,
            final String strHostUserCreationWebExUrl) throws Exception {
        System.out.println("webExMeetingUserInvitations Working");
        JSONObject objResult = new JSONObject();
        JSONObject objWebExMeetingInvitations = new JSONObject();
        objWebExMeetingInvitations.put("title", "Code Red Evacuation");
        objWebExMeetingInvitations.put("agenda", "Code Red");
        objWebExMeetingInvitations.put("password", "CodRed@995");
        objWebExMeetingInvitations.put("start", strStartTime);
        objWebExMeetingInvitations.put("end", strEndTime);
        objWebExMeetingInvitations.put("timezone", strTimezone);
        objWebExMeetingInvitations.put("enabledAutoRecordMeeting", bEnabledAutoRecordMeeting);
        objWebExMeetingInvitations.put("allowAnyUserToBeCoHost", bAllowAnyUserToBeCoHost);
        objWebExMeetingInvitations.put("invitees", arrInvitees);
        HttpPost httpPost = new HttpPost(strHostUserCreationWebExUrl);
        HttpClient client = HttpClientBuilder.create().build();
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + strAccessToken);

        try {
            StringEntity objStringEntity = new StringEntity(objWebExMeetingInvitations.toString());
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
