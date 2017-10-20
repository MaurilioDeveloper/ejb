package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CommissionEntity;

public class CommissionDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(CommissionDAO.class);
    
    @SuppressWarnings("unchecked")
    public CommissionEntity findCommissionByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionByImportCode ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    SELECT ce FROM CommissionEntity ce              ");
            sql.append("    WHERE ce.importCode = :importCode               ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
            
            List<CommissionEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findCommissionByImportCode ");
            return null;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
}
