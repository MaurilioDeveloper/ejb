package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.BankEntity;

public class BankDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(BankDAO.class);

    @SuppressWarnings("unchecked")
    public BankEntity findBankByImportCode(String importCode) throws UnexpectedException {
        
        try {
            
            LOGGER.debug(" >> INIT findBankByImportCode ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    SELECT bk FROM BankEntity bk          ");
            sql.append("    WHERE bk.importCode = :importCode     ");
        
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
        
            List<BankEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
        
            LOGGER.debug(" >> END findBankByImportCode ");
            return null;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
}
