package com.rci.omega2.ejb.dto.nimble;

import java.util.HashMap;
import java.util.Map;

public class NimbleBaseResponse {
    private Integer codeMessage;
    private String messageEx;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public Integer getCodeMessage() {
        return codeMessage;
    }
    public void setCodeMessage(Integer codeMessage) {
        this.codeMessage = codeMessage;
    }
    public String getMessageEx() {
        return messageEx;
    }
    public void setMessageEx(String messageEx) {
        this.messageEx = messageEx;
    }
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
