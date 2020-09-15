/**
 *
 */
package org.nipun.cisco.dnas.model;

/**
 * @author himabindu.v
 *
 */
public class ThirdPartyOauthCredentialsTo {
    private String source;
    private String clientId;
    private String clientSecret;
    private String apiKey;
    private String apiUrl;

    /**
     * @return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @return the apiUrl
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @return the clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param apiKey
     *            the apiKey to set
     */
    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @param apiUrl
     *            the apiUrl to set
     */
    public void setApiUrl(final String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * @param clientId
     *            the clientId to set
     */
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    /**
     * @param clientSecret
     *            the clientSecret to set
     */
    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource(final String source) {
        this.source = source;
    }
}
