package com.rci.omega2.ejb.dto;

import java.util.Date;

public class EventDTO {

    private String status;
    private String userName;
    private Date eventDate;
    private String details;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEventDate() {
        return eventDate == null ? null : new Date(eventDate.getTime());
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate == null ? null : new Date(eventDate.getTime());
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    
}
