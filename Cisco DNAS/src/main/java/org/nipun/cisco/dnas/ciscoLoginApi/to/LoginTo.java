/**
 *
 */
package org.nipun.cisco.dnas.ciscoLoginApi.to;

/**
 * @author himabindu.v
 *
 */
public class LoginTo {
    private String userName;
    private String pwd;
    private String newPwd;
    private String reTypePwd;
    private String clientId;
    private String redirectUrl;
    private String responseType;
    private String stateId;
    private String code;
    private Long orgId;

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the newPwd
     */
    public String getNewPwd() {
        return newPwd;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * @return the responseType
     */
    public String getResponseType() {
        return responseType;
    }

    /**
     * @return the reTypePwd
     */
    public String getReTypePwd() {
        return reTypePwd;
    }

    /**
     * @return the stateId
     */
    public String getStateId() {
        return stateId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param clientId
     *            the clientId to set
     */
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * @param newPwd
     *            the newPwd to set
     */
    public void setNewPwd(final String newPwd) {
        this.newPwd = newPwd;
    }

    /**
     * @param orgId
     *            the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @param pwd
     *            the pwd to set
     */
    public void setPwd(final String pwd) {
        this.pwd = pwd;
    }

    /**
     * @param redirectUrl
     *            the redirectUrl to set
     */
    public void setRedirectUrl(final String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * @param responseType
     *            the responseType to set
     */
    public void setResponseType(final String responseType) {
        this.responseType = responseType;
    }

    /**
     * @param reTypePwd
     *            the reTypePwd to set
     */
    public void setReTypePwd(final String reTypePwd) {
        this.reTypePwd = reTypePwd;
    }

    /**
     * @param stateId
     *            the stateId to set
     */
    public void setStateId(final String stateId) {
        this.stateId = stateId;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

}
