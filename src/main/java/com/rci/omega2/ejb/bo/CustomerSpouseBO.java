package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.CustomerSpouseDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CustomerSpouseEntity;

@Stateless
public class CustomerSpouseBO extends BaseBO{
    
    
    private static final Logger LOGGER = LogManager.getLogger(CustomerSpouseBO.class);
       
    /**
     * 
     * @param customerSpouse
     * @return
     * @throws UnexpectedException
     */
    public CustomerSpouseEntity update(CustomerSpouseEntity customerSpouse)throws UnexpectedException{
         
        try {
            LOGGER.debug(" >> INIT update ");
            
            CustomerSpouseDAO dao = daoFactory(CustomerSpouseDAO.class);
            CustomerSpouseEntity temp = dao.update(customerSpouse);
            
            LOGGER.debug(" >> END update ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    
    /**
     * 
     * @param customerSpouse
     * @return
     * @throws UnexpectedException
     */
    public void insert(CustomerSpouseEntity customerSpouse)throws UnexpectedException{
        
        
        try {
            LOGGER.debug(" >> INIT insert ");
            CustomerSpouseDAO dao = daoFactory(CustomerSpouseDAO.class);
            dao.save(customerSpouse);
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
     * @param customerSpouse
     * @return
     * @throws UnexpectedException
     */
    public void delete(CustomerSpouseEntity customerSpouse)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT delete ");
            CustomerSpouseDAO dao = daoFactory(CustomerSpouseDAO.class);
            dao.remove(customerSpouse);
            LOGGER.debug(" >> END delete ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
