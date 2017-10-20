package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CountryEntity;

public class CountryDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(CountryDAO.class);

    @SuppressWarnings("unchecked")
    public CountryEntity findCountryByAcronym(String acronym) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionByImportCode ");
            
            StringBuilder sql = new  StringBuilder();
        
            sql.append("    SELECT ct FROM CountryEntity ct    ");
            sql.append("    WHERE ct.importCode = :importCode  ");
        
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", acronym);
        
            List<CountryEntity > ls = query.getResultList();
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
