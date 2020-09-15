/**
 *
 */
package org.nipun.cisco.dnas.model;

/**
 * @author himabindu.v
 *
 */
public class OrgTo {
    private Long id;
    private String orgEmail;
    private String orgName;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the orgEmail
     */
    public String getOrgEmail() {
        return orgEmail;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param orgEmail
     *            the orgEmail to set
     */
    public void setOrgEmail(final String orgEmail) {
        this.orgEmail = orgEmail;
    }

    /**
     * @param orgName
     *            the orgName to set
     */
    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }
}
