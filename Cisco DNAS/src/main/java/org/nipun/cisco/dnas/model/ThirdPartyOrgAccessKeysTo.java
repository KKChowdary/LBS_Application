/**
 *
 */
package org.nipun.cisco.dnas.model;

/**
 * @author himabindu.v
 *
 */
public class ThirdPartyOrgAccessKeysTo {

    private Long id;
    private String partnerTenantId;
    private Long orgId;
    private String orgPartenerTenatId;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @return the orgPartenerTenatId
     */
    public String getOrgPartenerTenatId() {
        return orgPartenerTenatId;
    }

    /**
     * @return the partnerTenantId
     */
    public String getPartnerTenantId() {
        return partnerTenantId;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param orgId
     *            the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @param orgPartenerTenatId
     *            the orgPartenerTenatId to set
     */
    public void setOrgPartenerTenatId(final String orgPartenerTenatId) {
        this.orgPartenerTenatId = orgPartenerTenatId;
    }

    /**
     * @param partnerTenantId
     *            the partnerTenantId to set
     */
    public void setPartnerTenantId(final String partnerTenantId) {
        this.partnerTenantId = partnerTenantId;
    }

}
