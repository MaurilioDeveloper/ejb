package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.EmployerDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.EmployerEntity;

@Stateless
public class EmployerBO extends BaseBO{
    
    
    private static final Logger LOGGER = LogManager.getLogger(EmployerBO.class);
       
    
    /**
     * 
     * @param employer
     * @return
     * @throws UnexpectedException
     */
    public void insert(EmployerEntity employer)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            
            EmployerDAO dao = daoFactory(EmployerDAO.class);
            dao.save(employer);
            
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
     * @param employer
     * @return
     * @throws UnexpectedException
     */
    public EmployerEntity update(EmployerEntity employer)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT update ");
            
            EmployerDAO dao = daoFactory(EmployerDAO.class);
            EmployerEntity temp = dao.update(employer);
            
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
     * @param employer
     * @return
     * @throws UnexpectedException
     */
    public void delete(EmployerEntity employer)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT delete ");
            
            EmployerDAO dao = daoFactory(EmployerDAO.class);
            dao.remove(employer);
            
            LOGGER.debug(" >> END delete ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
}
