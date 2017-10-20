package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.rci.omega2.entity.FinancialBrandEntity;
import com.rci.omega2.entity.UserEntity;

public class NoticeDTO extends BaseDTO{

    /**serial version */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private BigDecimal userId;
    private UserEntity user;
    private Boolean published;
    private String notice;
    private String title;
    private String name;
    private Date dtInsert;
    private Set<FinancialBrandEntity> listFinancialBrand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Set<FinancialBrandEntity> getListFinancialBrand() {
        return listFinancialBrand;
    }

    public void setListFinancialBrand(Set<FinancialBrandEntity> listFinancialBrand) {
        this.listFinancialBrand = listFinancialBrand;
    }

    public Date getDtInsert() {
        return dtInsert == null ? null : new Date(dtInsert.getTime());
    }

    public void setDtInsert(Date dtInsert) {
        this.dtInsert = dtInsert == null ? null : new Date(dtInsert.getTime());
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
