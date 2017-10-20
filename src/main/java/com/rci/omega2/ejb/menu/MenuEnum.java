package com.rci.omega2.ejb.menu;

public enum MenuEnum {
    NEWS("",""), 
    NEW_VEHICLE ("",""), 
    OLD_VEHICLE ("",""), 
    PROFILE("",""),
    MY_PROPOSAL("",""), 
    SIGHT_SALE ("",""),
    MODELS("",""), 
    NEWS_ADMNISTRATION ("",""), 
    PROMOTIONAL_IMAGES ("",""), 
    SUBMISSION_PROCESS_CONFIG ("",""),
    RELEASE_RETURN ("",""), 
    SERVICE_CONFIG ("",""), 
    PROPOSAL_WITH_DEALERSHIP_CHANGES ("",""), 
    PROPOSAL_UPDATE ("",""),
    SERVICES_DISABLE ("","");
    
    private String path;
    private String component;
    
    private MenuEnum(String path, String component){
        this.path = path;
        this.component = component;
    }

    public String getPath() {
        return path;
    }

    public String getComponent() {
        return component;
    }
    
}
