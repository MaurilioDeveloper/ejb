package com.rci.omega2.ejb.dto.simulation;

import java.util.Date;

import com.rci.omega2.ejb.dto.BankDTO;
import com.rci.omega2.ejb.dto.BaseDTO;

public class BankDetailsDTO extends BaseDTO{

    /**serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private String branch;
    private String branchDigit;
    private String accountDigit;
    private String account;
    private Date customerSince;
    private String accountType;
    private PhoneDTO phoneBranch;
    private BankDTO bank;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public String getBranchDigit() {
        return branchDigit;
    }
    public void setBranchDigit(String branchDigit) {
        this.branchDigit = branchDigit;
    }
    public String getAccountDigit() {
        return accountDigit;
    }
    public void setAccountDigit(String accountDigit) {
        this.accountDigit = accountDigit;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public Date getCustomerSince() {
        return customerSince == null ? null : new Date(customerSince.getTime());
    }
    public void setCustomerSince(Date customerSince) {
        this.customerSince = customerSince == null ? null : new Date(customerSince.getTime());
    }
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public PhoneDTO getPhoneBranch() {
        return phoneBranch;
    }
    public void setPhoneBranch(PhoneDTO phoneBranch) {
        this.phoneBranch = phoneBranch;
    }
    public BankDTO getBank() {
        return bank;
    }
    public void setBank(BankDTO bank) {
        this.bank = bank;
    }
    
}
