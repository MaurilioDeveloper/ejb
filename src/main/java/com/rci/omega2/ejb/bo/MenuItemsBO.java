package com.rci.omega2.ejb.bo;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.RoleDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.menu.MenuEnum;
import com.rci.omega2.ejb.menu.RuleEnum;

@Stateless
public class MenuItemsBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(MenuItemsBO.class);
    
    public Map<MenuEnum, Map<String, String>>getRoutesByUserId(Long userId) throws UnexpectedException{
        LOGGER.debug(" >> INIT getRoutesByUserId ");
        RoleDAO roleDao = daoFactory(RoleDAO.class);
        String roleDescritpion = roleDao.getRoleNameByUserId(userId);
        MenuEnum[] menus = getRoutes(roleDescritpion);
        Map<MenuEnum, Map<String, String>> result = new HashMap<>();
        for(MenuEnum menu:menus){
            Map<String, String> mapContaint = new HashMap<String, String>();
            mapContaint.put("component", menu.getComponent());
            mapContaint.put("path", menu.getPath());
            result.put(menu, mapContaint);
        }
        LOGGER.debug(" >> END getRoutesByUserId ");
        return result;
    }
    
    private MenuEnum[] getRoutes(String rule){
        LOGGER.debug(" >> INIT getRoutes ");
        if(rule.equals(String.valueOf(RuleEnum.PROF_SYSTEM_ADMINISTRATOR))){
            return getSystemAdministratorRote();
        }
        if(rule.equals(String.valueOf(RuleEnum.PROF_MARKET))){
            return getMarketingRote();
        }
        if(rule.equals(String.valueOf(RuleEnum.PROF_RCI_REGIONAL_MANAGER))){
            return getRCIRegionaManagerRote();
        }
        if(rule.equals(String.valueOf(RuleEnum.PROF_RCI_REGIONAL_CONSULTANT))){
            return getRCIRegionalConsultantRote();
        }
        if(rule.equals(String.valueOf(RuleEnum.PROF_CONSULTOR_FI))){
            return getConsultoFIRote();
        }
        if(rule.equals(String.valueOf(RuleEnum.PROF_DEALER_ADMINISTRATOR))){
            return getDealerAdministratorRote();
        }
        if(rule.equals(String.valueOf(RuleEnum.PROF_BUSINESS_MANAGER))){
            return getBusinessManagerRote();
        }
        if(rule.equals(String.valueOf(RuleEnum.PROF_SALES_EXECUTIVE))){
            return getSalesExecutiveRote();
        }
        if(rule.equals(String.valueOf(RuleEnum.PROF_UNDERWRITER))){
            return getUnderwriterPayoutRote();
        }        
        LOGGER.debug(" >> END getRoutes ");
        return null;
    }
    
    private MenuEnum[] getSystemAdministratorRote(){
        MenuEnum[] itemsSystemAdministrator = {MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL,MenuEnum.MODELS,MenuEnum.NEWS_ADMNISTRATION,MenuEnum.PROMOTIONAL_IMAGES
                ,MenuEnum.PROPOSAL_UPDATE};
        return itemsSystemAdministrator;
    }
    
    private MenuEnum[] getMarketingRote(){
        MenuEnum[]itemsMarketing = {MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL,MenuEnum.MODELS, MenuEnum.NEWS_ADMNISTRATION, MenuEnum.PROMOTIONAL_IMAGES};
        return itemsMarketing;
    }
    
    private MenuEnum[] getRCIRegionaManagerRote(){
        MenuEnum[]itemsRCIRegionalManager = {MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL, MenuEnum.SERVICES_DISABLE};
        return itemsRCIRegionalManager;
    }
    
    private MenuEnum[]getRCIRegionalConsultantRote(){
        MenuEnum[]itemsRCIRegionalConsultant = {MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL,MenuEnum.PROPOSAL_WITH_DEALERSHIP_CHANGES, MenuEnum.SERVICES_DISABLE};
        return itemsRCIRegionalConsultant;
    }
    
    private MenuEnum[] getConsultoFIRote(){
        MenuEnum[]itemsConsultorFI = {MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL,MenuEnum.PROPOSAL_WITH_DEALERSHIP_CHANGES};
        return itemsConsultorFI;
    }
    
    private MenuEnum[]getDealerAdministratorRote(){
        MenuEnum[]itemsDealerAdministrator ={MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL,MenuEnum.SUBMISSION_PROCESS_CONFIG, MenuEnum.RELEASE_RETURN
                ,MenuEnum.SERVICE_CONFIG, MenuEnum.PROPOSAL_WITH_DEALERSHIP_CHANGES};
        return itemsDealerAdministrator;
    }
    
    private MenuEnum[] getBusinessManagerRote(){
        MenuEnum[]itemsUnderwriterPayout = {MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL, MenuEnum.RELEASE_RETURN};
        return itemsUnderwriterPayout;
    }
    
    private MenuEnum[] getSalesExecutiveRote(){
        MenuEnum[]itemsSalesExecutive = {MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL};
        return itemsSalesExecutive;
    }
    
    private MenuEnum[] getUnderwriterPayoutRote(){
        MenuEnum[]itemsUnderwriterPayout = {MenuEnum.NEWS,MenuEnum.NEW_VEHICLE,MenuEnum.OLD_VEHICLE,MenuEnum.PROFILE
                ,MenuEnum.MY_PROPOSAL};
        return itemsUnderwriterPayout;
    }

}
