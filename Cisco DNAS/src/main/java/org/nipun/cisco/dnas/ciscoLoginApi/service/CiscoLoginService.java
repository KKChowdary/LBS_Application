/**
 *
 */
package org.nipun.cisco.dnas.ciscoLoginApi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The {@code CiscoLoginService} class represents validating the tenantId and authenticating the client credentials in local server.
 *
 * @author himabindu.v
 *
 */
@Service
public class CiscoLoginService {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String SECRET_KEY = "cisco@it123";
    private static final String CLIENT_ID = "cisco@it123";
    private static final String CLIENT_SECRET_ID = "cisco@it123";
    private static final String PATNER_TENANT_ID = "tanent@123456";

    Logger logger = Logger.getLogger(CiscoLoginService.class);

    /**
     * @param clamis
     * @param ttlMillis
     * @return
     */
    public static String createJWT(final Map<String, Object> clamis, final long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setIssuedAt(now).setClaims(clamis).signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * After Validating the tenantId, send email and bearerToken as response arguments
     *
     * @param bearerToken
     * @param partnerTenantId
     * @return
     */
    public JSONObject getAppInfo(final String bearerToken, final String partnerTenantId) {
        logger.info("Request from Cisco dna spaces for getAppInfo services ");

        logger.info("bearerToken value: " + bearerToken);
        logger.info("partnerTenantId value: " + partnerTenantId);

        DecodedJWT jwt = JWT.decode(bearerToken);
        Map<String, Claim> claims = jwt.getClaims();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue().asString());

        }
        //Check the PATNER_TANENT_ID  from database server.
        if (PATNER_TENANT_ID.equals(partnerTenantId)) {

            JSONArray liTelemetry = new JSONArray();
            JSONObject objTelemetry = new JSONObject();
            objTelemetry.put("type", "number");
            objTelemetry.put("name", "Feedback");
            objTelemetry.put("value", "7");
            objTelemetry.put("units", "instance of feedback");
            liTelemetry.add(objTelemetry);
            JSONObject jsonAuthTokenResponse = new JSONObject();
            jsonAuthTokenResponse.put("telemetry", liTelemetry);
            jsonAuthTokenResponse.put("partnerTenantId", PATNER_TENANT_ID);
            /* OrgTo orgTo = tblOrganisationService.getById(objThirdPartyOrgAccessKeys.getOrgId());
            if (orgTo != null) {
                jsonAuthTokenResponse.put("userEmail", orgTo.getOrgEmail());
            }*/
            jsonAuthTokenResponse.put("userEmail", "varaprasad.v@nipun.net");
            return jsonAuthTokenResponse;

        }

        JSONObject errorResponse = new JSONObject();

        errorResponse.put("status", "401");
        errorResponse.put("msg", "Unauthorized access");

        return errorResponse;
    }

    /**
     * Authenticating the token in local server
     *
     * @param strRequestBody
     * @return
     */
    public JSONObject getOAuthToken(final String strRequestBody) {

        logger.info("Request from  cisco dna spaces for access key ");
        logger.info(" Data from dna server : " + strRequestBody);

        try {

            String[] split = strRequestBody.split("&");

            String strclientSecret = "";
            String strClientId = "";
            String code = "";
            for (String string : split) {

                if (string.contains("code=")) {
                    code = string.substring(5);

                }

                else if (string.contains("client_id")) {
                    strClientId = string.substring(10);
                }
                else if (string.contains("client_secret")) {
                    strclientSecret = string.substring(14);
                }

            }

            //Check the clientId and clientSecretId from database server asure that clientId and clientSecretId should be same.
            if (CLIENT_ID.equals(strClientId) && CLIENT_SECRET_ID.equals(strclientSecret)) {
                JSONObject payload = new JSONObject();
                //Here PATNER_TANENT_ID is need to check form DB.
                String strTenantId = PATNER_TENANT_ID;
                Map<String, Object> clamis = new HashMap<String, Object>();
                payload.put("appId", randomAlphaNumeric(32));

                //Here need to set the login customer email.
                clamis.put("userEmail", "abcd@gmail.com");
                //Here  need to set the organization name of customer who is logged in.
                clamis.put("customer", "cisco");
                clamis.put("expiresIn", new Date().getTime());

                clamis.put("tenantId", strTenantId);

                String createJWT = createJWT(clamis, 10000);
                JSONObject jsonAuthTokenResponse = new JSONObject();

                logger.info("Generated JWT token is: " + createJWT);

                jsonAuthTokenResponse.put("access_token", createJWT);
                jsonAuthTokenResponse.put("token_type", "Bearer");
                jsonAuthTokenResponse.put("scope", strTenantId);
                return jsonAuthTokenResponse;
            }
            else {
                JSONObject errorResponse = new JSONObject();

                errorResponse.put("status", "401");
                errorResponse.put("msg", "Unauthorized access");
                return errorResponse;
            }

        }
        catch (Exception Ex) {

            logger.info(" Exception raised while retriving data from access token url : " + strRequestBody);
        }
        JSONObject errorResponse = new JSONObject();

        errorResponse.put("status", "401");
        errorResponse.put("msg", "Unauthorized access");

        return errorResponse;

    }

    /**
     * @param count
     * @return
     */
    public String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}