package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.SantanderReturnMessageEntity;

public class SantanderReturnMessageDao extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(SantanderReturnMessageDao.class);

    @SuppressWarnings("unchecked")
    public SantanderReturnMessageEntity findSantanderReturnMessageByCode(String code) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findSantanderReturnMessageByCode ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT se FROM SantanderReturnMessageEntity se    ");
            sql.append("    WHERE se.key = :code                           ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("code", code);

            List<SantanderReturnMessageEntity> ls = query.getResultList();

            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findSantanderReturnMessageByCode ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
   
    }
    
}
