/**
 *
 */
package org.nipun.cisco.dnas.ciscoLoginApi.controller;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.nipun.cisco.dnas.ciscoLoginApi.service.CiscoLoginService;
import org.nipun.cisco.dnas.ciscoLoginApi.to.LoginTo;
import org.nipun.cisco.dnas.common.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The {@code CiscoLoginController} class represents validating the tenantId and authenticating the client credentials in local server.
 *
 * @see java.lang.Object#toString()
 * @author varaprasad.v
 *
 */
@RestController
public class CiscoLoginController {
    private static final Logger logger = Logger.getLogger(CiscoLoginController.class);
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String SECRET_KEY = "cisco@it123";
    private static final String CLIENT_SECRET_KEY = "cisco@it123";
    private static final String CLIENT_ID = "1234567";

    @Autowired
    CiscoLoginService ciscoLoginService;

    /**
     * @param username
     * @return
     */
    private static String createEncodedText(final String username) {
        final String pair = username;
        final byte[] encodedBytes = Base64.encodeBase64(pair.getBytes());
        return new String(encodedBytes);
    }

    /**
     * To generate the JWT key for CiscoDnaSpace
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
     * Encrypting the Password
     */
    private static String pwdEncodedText(final String pwd) {
        final String pair = pwd;
        final byte[] encodedBytes = Base64.encodeBase64(pair.getBytes());
        return new String(encodedBytes);
    }

    /**
     * Here authenticating the CiscoDnaSpace user details.
     *
     * @param request
     * @param clientId
     * @param redirectUrl
     * @param responseType
     * @param state
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "oauthLogin", method = RequestMethod.GET)
    public ModelAndView doLogin(final HttpServletRequest request,
            @RequestParam(value = "client_id", required = false) final String clientId,
            @RequestParam(value = "redirect_uri", required = false) final String redirectUrl,
            @RequestParam(value = "response_type", required = false) final String responseType,
            @RequestParam(value = "state", required = false) final String state, final Model model,
            final RedirectAttributes redirectAttributes) {

        logger.info("clientId:" + clientId + " redirectUrl:" + redirectUrl + " responseType:" + responseType + " state:" + state);

        //Here clientId is taken from DB.
        if (CLIENT_ID.equals(clientId)) {

            LoginTo loginTo = new LoginTo();
            loginTo.setClientId(clientId);
            loginTo.setRedirectUrl(redirectUrl);
            loginTo.setStateId(state);
            loginTo.setResponseType(responseType);
            model.addAttribute("loginTo", loginTo);

            return new ModelAndView("login/thirdPartyLogin");
        }
        else {
            return new ModelAndView("redirect:" + redirectUrl);
        }

    }

    /**
     * Here verifying the partnerTenantId with our local server tenantId (matching with client tenant id)
     *
     * @param bearerToken
     * @param partnerTenantId
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAppInfo", method = RequestMethod.GET)
    public JSONObject getAppInfo(@RequestHeader("Authorization") final String bearerToken,
            @RequestParam("partnerTenantId") final String partnerTenantId, final Model model) {

        JSONObject response = ciscoLoginService.getAppInfo(bearerToken, partnerTenantId);

        return response;

    }

    /**
     * Here authenticating the CiscoDnaSpace access key.
     *
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "getOAuthToken", method = RequestMethod.POST)
    public JSONObject getOAuthToken(@RequestBody final String requestBody) {
        logger.info("Request from  cisco dna spaces for access key ");
        logger.info(" Data from dna server : " + requestBody);
        return ciscoLoginService.getOAuthToken(requestBody);

    }

    /**
     * Here validating the auth credentials
     *
     * @param httpServletResponce
     * @param request
     * @param loginTo
     * @param model
     * @param result
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/oauthLogin", method = RequestMethod.POST)
    public ModelAndView oauthLogin(final HttpServletResponse httpServletResponce, final HttpServletRequest request,
            @ModelAttribute("loginTo") final LoginTo loginTo, final Model model, final BindingResult result) throws ParseException {

        /* loginValidator.validate(loginTo, result);*/
        if (result.hasErrors()) {
            model.addAttribute("loginTo", loginTo);
            return new ModelAndView("login/thirdPartyLogin");
        }

        String ipAddress = Constants.AUTHENCATION_URL;

        if (StringUtils.isNotEmpty(ipAddress)) {
            HttpGet httpGet = new HttpGet(ipAddress);
            HttpClient client = HttpClientBuilder.create().build();

            final String encodedText = createEncodedText(loginTo.getUserName());
            final String pwdText = pwdEncodedText(loginTo.getPwd());
            httpGet.setHeader("userName", encodedText);
            httpGet.setHeader("password", pwdText);
            httpGet.setHeader("organization", "");
            HttpResponse response;
            try {
                response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    // check valid ClientId from local

                    String strCode = randomAlphaNumeric(32);
                    String strTenantId = randomAlphaNumeric(32);
                    String strGrantType = "authorization_code";
                    String strStateType = loginTo.getStateId();
                    String strClientId = CLIENT_ID;
                    String strClientSecret = CLIENT_SECRET_KEY;
                    String strRedirectUri = "https://partners.dnaspaces.io/partner/OAuthValidation";

                    String strRedirectURLResponse = "redirect:" + loginTo.getRedirectUrl() + "?code=" + strCode + "&&grant_type="
                            + strGrantType + "&state=" + strStateType + "&&client_id=" + strClientId + "&&client_secret=" + strClientSecret
                            + "&&redirect_uri=" + strRedirectUri;
                    logger.info("Redirect URL/Response is: " + strRedirectURLResponse);
                    return new ModelAndView(strRedirectURLResponse);

                }
                else {
                    logger.info(" The given clientId is not correct");
                }

                // return new ModelAndView("redirect:/welcome/index");
                // return new ModelAndView("redirect:/map/iframeView");
                return new ModelAndView("redirect:" + loginTo.getRedirectUrl());

            }

            catch (ClientProtocolException e) {
                //e.printStackTrace();
                return new ModelAndView("login/thirdPartyLogin", "faliure", "Authentication Failed");
            }
            catch (IOException e) {
                //e.printStackTrace();
                return new ModelAndView("login/thirdPartyLogin", "faliure", "Authentication Failed");
            }
        }

        return new ModelAndView("login/thirdPartyLogin", "faliure", "Invalid Creditionals");

    }

    /**
     * Generating 16 digits random number
     *
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
