package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.EconomicActivityEntity;

public class EconomicActivityDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(EconomicActivityDAO.class);

    /**
     * 
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public EconomicActivityEntity findEconomicActivityByImportCode(String importCode) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findEconomicActivityByImportCode ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    SELECT ea FROM EconomicActivityEntity ea       ");
            sql.append("    WHERE ea.importCode = :importCode              ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
            
            List<EconomicActivityEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findEconomicActivityByImportCode ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}
