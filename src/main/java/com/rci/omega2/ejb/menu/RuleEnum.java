package com.rci.omega2.ejb.menu;

public enum RuleEnum {
    PROF_SALES_EXECUTIVE("Vendedor"),
    PROF_UNDERWRITER("Underwriter / Payout"),
    PROF_BUSINESS_MANAGER("Gerente de vendas"),
    PROF_SYSTEM_ADMINISTRATOR("Administrador do sistema"),
    PROF_MARKET("Marketing"),
    PROF_DEALER_ADMINISTRATOR("Administrador da concessionária"),
    PROF_CONSULTOR_FI("Consultor F&I"),
    PROF_RCI_REGIONAL_CONSULTANT("RCI Regional Consultant"),
    PROF_RCI_REGIONAL_MANAGER("RCI Regional Manager");
    
    private String descritpion;
    
    private RuleEnum (String description){
        this.descritpion =description;
    }

    public String getDescritpion() {
        return descritpion;
    }
    
}
