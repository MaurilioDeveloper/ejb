package com.rci.omega2.ejb.dto;

public class FinanceTypeDTO extends BaseDTO{
    
    private static final long serialVersionUID = -1251132504431143020L;
    private String financeTypeId;
    private String description;
    /**
     * @return the financeTypeId
     */
    public String getFinanceTypeId() {
        return financeTypeId;
    }
    /**
     * @param financeTypeId the financeTypeId to set
     */
    public void setFinanceTypeId(String financeTypeId) {
        this.financeTypeId = financeTypeId;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
