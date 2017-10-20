package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.KinshipTypeEntity;

public class KinshipTypeDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(KinshipTypeDAO.class);

    @SuppressWarnings("unchecked")
    public KinshipTypeEntity findKinshipTypeByImportCode(String importCode) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findKinshipTypeByImportCode ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    SELECT kt FROM KinshipTypeEntity kt            ");
            sql.append("    WHERE kt.importCode = :importCode              ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
            
            List<KinshipTypeEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findKinshipTypeByImportCode ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }  
        
    }

}
