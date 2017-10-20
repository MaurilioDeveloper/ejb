package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.IncomeTypeEntity;

public class IncomeTypeDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(IncomeTypeDAO.class);

    /**
     * 
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public IncomeTypeEntity findIncomeTypeByImportCode(String importCode) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findIncomeTypeByImportCode ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    SELECT it FROM IncomeTypeEntity it               ");
            sql.append("    WHERE it.importCode = :importCode                ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
            
            List<IncomeTypeEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findIncomeTypeByImportCode ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
            
    }
    
}
