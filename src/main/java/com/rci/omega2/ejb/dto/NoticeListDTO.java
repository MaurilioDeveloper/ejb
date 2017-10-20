package com.rci.omega2.ejb.dto;

import java.util.Date;

public class NoticeListDTO extends BaseDTO{

    /**serial version */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String title;
    private Date date;

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

    public Date getDate() {
        return date == null ? null : new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = date == null ? null : new Date(date.getTime());
    }
    
    
}

