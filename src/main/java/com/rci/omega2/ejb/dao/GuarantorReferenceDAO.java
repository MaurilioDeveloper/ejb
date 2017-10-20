package com.rci.omega2.ejb.dao;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.GuarantorEntity;

public class GuarantorReferenceDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(GuarantorReferenceDAO.class);
    
    /**
     * 
     * @param guarantor
     * @throws UnexpectedException
     */
    public void deleteAllGuarantorReference(GuarantorEntity guarantor) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT deleteAllGuarantorReference ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("     DELETE FROM GuarantorReferenceEntity gr        ");
            sql.append("     WHERE gr.guarantor.id = :guarantorId           ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("guarantorId", guarantor.getId());
            query.executeUpdate();
            
            super.getEntityManager().flush();
            
            LOGGER.debug(" >> END deleteAllGuarantorReference ");
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
            
    }

}
