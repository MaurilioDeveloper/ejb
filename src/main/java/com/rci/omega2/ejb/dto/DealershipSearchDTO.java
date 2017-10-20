package com.rci.omega2.ejb.dto;

public class DealershipSearchDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private Long userId;
    private boolean regionalView;
    private String dealershipName;
    private String dealershipCode;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isRegionalView() {
        return regionalView;
    }

    public void setRegionalView(boolean regionalView) {
        this.regionalView = regionalView;
    }

    public String getDealershipName() {
        return dealershipName;
    }

    public void setDealershipName(String dealershipName) {
        this.dealershipName = dealershipName;
    }

    public String getDealershipCode() {
        return dealershipCode;
    }

    public void setDealershipCode(String dealershipCode) {
        this.dealershipCode = dealershipCode;
    }

}
