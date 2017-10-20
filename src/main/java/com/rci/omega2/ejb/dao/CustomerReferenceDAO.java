package com.rci.omega2.ejb.dao;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CustomerEntity;

public class CustomerReferenceDAO extends BaseDAO{    
    
    private static final Logger LOGGER = LogManager.getLogger(CustomerReferenceDAO.class);
    
    /**
     * 
     * @param customer
     * @throws UnexpectedException
     */
    public void deleteAllCustomerReference(CustomerEntity customer) throws UnexpectedException {
       
        try {
            LOGGER.debug(" >> INIT deleteAllCustomerReference ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    DELETE FROM CustomerReferenceEntity ct             ");
            sql.append("    WHERE ct.customer.id = :customerId                 ");
        
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("customerId", customer.getId());
        
            query.executeUpdate();
            super.getEntityManager().flush();
            
            LOGGER.debug(" >> END deleteAllCustomerReference ");
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
}
