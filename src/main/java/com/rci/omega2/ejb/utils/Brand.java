package com.rci.omega2.ejb.utils;

public enum Brand {
    
    RENAULT (2),
    NISSAN  (3),
    RCI     (6); 
    
    private Integer idBrand;

    private Brand(Integer idBrand) {
        this.idBrand = idBrand;
    }
    
    public static Brand getById(String brandStr) {
        Integer cod = Integer.parseInt(brandStr);
        for (Brand brand : Brand.values()) {
           if(brand.idBrand == cod){
               return brand;
           }
        }
        return null;
    }
    
    public static Brand getByName(String name) {
        for (Brand brand : Brand.values()) {
           if(brand.name().equals(name)){
               return brand;
           }
        }
        return null;
    }

    /**
     * @return the idBrand
     */
    public Integer getIdBrand() {
        return idBrand;
    }
}
