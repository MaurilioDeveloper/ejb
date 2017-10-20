package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.PhoneDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CustomerEntity;
import com.rci.omega2.entity.GuarantorEntity;
import com.rci.omega2.entity.PhoneEntity;

@Stateless
public class PhoneBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(PhoneBO.class);


   
    /**
     * 
     * @param phone
     * @return
     * @throws UnexpectedException
     */
    public void insert(PhoneEntity phone)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            PhoneDAO dao = daoFactory(PhoneDAO.class);
            dao.save(phone);
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
    public void delete(PhoneEntity phone)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT delete ");
            PhoneDAO dao = daoFactory(PhoneDAO.class);
            dao.remove(phone);
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
    public void deleteAllPhoneCustomer(CustomerEntity customer)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteAllPhoneCustomer ");
            PhoneDAO dao = daoFactory(PhoneDAO.class);
            dao.deleteAllPhoneCustomer(customer);
            LOGGER.debug(" >> END deleteAllPhoneCustomer ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * 
     * @param guarantor
     * @throws UnexpectedException
     */
    public void deleteAllPhoneGuarantor(GuarantorEntity guarantor)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteAllPhoneGuarantor ");
            PhoneDAO dao = daoFactory(PhoneDAO.class);
            dao.deleteAllPhoneGuarantor(guarantor);
            LOGGER.debug(" >> END deleteAllPhoneGuarantor ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    
}
