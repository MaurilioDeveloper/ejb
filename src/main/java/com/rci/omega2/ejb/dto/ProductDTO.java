package com.rci.omega2.ejb.dto;

import java.util.List;

public class ProductDTO extends BaseDTO{
    
    private static final long serialVersionUID = -8697395598806256185L;
    private String productId;
    private String description;
    private Boolean promotional;
    private String productInformation;
    private List<ProductPromotionalDTO> listProductPromotional;
    
    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }
    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
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
    /**
     * 
     * @return boolean if product is promotional
     */
    public Boolean getPromotional() {
        return promotional;
    }
    /**
     * 
     * @param promotional
     */
    public void setPromotional(Boolean promotional) {
        this.promotional = promotional;
    }
    public String getProductInformation() {
        return productInformation;
    }
    public void setProductInformation(String productInformation) {
        this.productInformation = productInformation;
    }
    public List<ProductPromotionalDTO> getListProductPromotional() {
        return listProductPromotional;
    }
    public void setListProductPromotional(List<ProductPromotionalDTO> listProductPromotional) {
        this.listProductPromotional = listProductPromotional;
    }
    
}
