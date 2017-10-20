package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.LegalNatureEntity;

public class LegalNatureDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(LegalNatureDAO.class);

    @SuppressWarnings("unchecked")
    public LegalNatureEntity findLegalNatureByImportCode(String importCode) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findLegalNatureByImportCode ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    SELECT ln FROM LegalNatureEntity ln             ");
            sql.append("    WHERE ln.importCode = :importCode               ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
            
            List<LegalNatureEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findLegalNatureByImportCode ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
            
    }

}
