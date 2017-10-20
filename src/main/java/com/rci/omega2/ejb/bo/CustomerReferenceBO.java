package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.CustomerReferenceDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CustomerEntity;
import com.rci.omega2.entity.CustomerReferenceEntity;

@Stateless
public class CustomerReferenceBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(CustomerReferenceBO.class);


   
    /**
     * 
     * @param phone
     * @return
     * @throws UnexpectedException
     */
    public void insert(CustomerReferenceEntity customerReference)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT insert ");
            CustomerReferenceDAO dao = daoFactory(CustomerReferenceDAO.class);
            dao.save(customerReference);
            LOGGER.debug(" >> END insert ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    /**
     * 
     * @param phone
     * @return
     * @throws UnexpectedException
     */
    public void delete(CustomerReferenceEntity customerReference)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT delete ");
            CustomerReferenceDAO dao = daoFactory(CustomerReferenceDAO.class);
            dao.remove(customerReference);
            LOGGER.debug(" >> END delete ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    /**
     * 
     * @param customer
     * @throws UnexpectedException
     */
    public void deleteAllCustomerReference(CustomerEntity customer)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT deleteAllCustomerReference ");
            CustomerReferenceDAO dao = daoFactory(CustomerReferenceDAO.class);
            dao.deleteAllCustomerReference(customer);
            LOGGER.debug(" >> END deleteAllCustomerReference ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
