package com.rci.omega2.ejb.dto;

import java.util.Date;

public class NotificationListDTO extends BaseDTO{

    /**serial version */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private Date changeData;
    private String proposal;
    private Integer proposalAdpNumber;
    private String cpfCnpj;
    private String clientName;
    private String status;
    private String salesman;
    private Integer read;
    private String userId;
    private String dossierId;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Date getChangeData() {
        return changeData == null ? null : new Date(changeData.getTime());
    }
    public void setChangeData(Date changeData) {
        this.changeData = changeData == null ? null : new Date(changeData.getTime());
    }
    public String getProposal() {
        return proposal;
    }
    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
    public Integer getProposalAdpNumber() {
        return proposalAdpNumber;
    }
    public void setProposalAdpNumber(Integer proposalAdpNumber) {
        this.proposalAdpNumber = proposalAdpNumber;
    }
    public String getCpfCnpj() {
        return cpfCnpj;
    }
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getSalesman() {
        return salesman;
    }
    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }
    public Integer getRead() {
        return read;
    }
    public void setRead(Integer read) {
        this.read = read;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDossierId() {
        return dossierId;
    }
    public void setDossierId(String dossierId) {
        this.dossierId = dossierId;
    }
   

}
