package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.SantanderEmployeeTypeEntity;

public class SantanderEmployeeTypeDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(SantanderEmployeeTypeDAO.class);

    @SuppressWarnings("unchecked")
    public SantanderEmployeeTypeEntity findSantanderEmployeeTypeByImportCode(String importCode) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findSantanderEmployeeTypeByImportCode ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT se FROM SantanderEmployeeTypeEntity se    ");
            sql.append("    WHERE se.importCode = :importCode             ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);

            List<SantanderEmployeeTypeEntity> ls = query.getResultList();

            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findSantanderEmployeeTypeByImportCode ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}
