package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.CustomerDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CustomerEntity;

@Stateless
public class CustomerBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(CustomerBO.class);

    
    /**
     * 
     * @param proposal
     * @return
     * @throws UnexpectedException
     */
    public CustomerEntity update(CustomerEntity customer)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT update ");
            
            CustomerDAO dao = daoFactory(CustomerDAO.class);
            CustomerEntity temp = dao.update(customer);
            
            LOGGER.debug(" >> END update ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
