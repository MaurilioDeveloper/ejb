package com.rci.omega2.ejb.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CustomerEntity;
import com.rci.omega2.entity.PhoneEntity;

public class CustomerDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(CustomerDAO.class);
    
    public Long saveCustomer(CustomerEntity customer) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT saveCustomer ");
            
            super.save(customer);
            Long temp = customer.getId();
            
            LOGGER.debug(" >> END saveCustomer ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void saveCustomerPhone(PhoneEntity phone) throws UnexpectedException {
        
        try{
            LOGGER.debug(" >> INIT saveCustomerPhone ");
            super.save(phone);
            LOGGER.debug(" >> END saveCustomerPhone ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
