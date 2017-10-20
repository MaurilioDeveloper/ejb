package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.AddressDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.AddressEntity;

@Stateless
public class AddressBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(AddressBO.class);

    /**
     * 
     * @param address
     * @return
     * @throws UnexpectedException
     */
    public AddressEntity update(AddressEntity address) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT update ");
            
            AddressDAO dao = daoFactory(AddressDAO.class);
            AddressEntity temp =  dao.update(address);  
            
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
     * @param address
     * @return
     * @throws UnexpectedException
     */
    public void insert(AddressEntity address)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT insert ");
            
            AddressDAO dao = daoFactory(AddressDAO.class);
            dao.save(address);
            
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
     * @param address
     * @return
     * @throws UnexpectedException
     */
    public void delete(AddressEntity address)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT delete ");
            
            AddressDAO dao = daoFactory(AddressDAO.class);
            dao.remove(address);
            
            LOGGER.debug(" >> END delete ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
