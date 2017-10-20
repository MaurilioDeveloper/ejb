package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.GuarantorDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.GuarantorEntity;

@Stateless
public class GuarantorBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(GuarantorBO.class);


   
    /**
     * 
     * @param guarantor
     * @return
     * @throws UnexpectedException
     */
    public void insert(GuarantorEntity guarantor)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            
            GuarantorDAO dao = daoFactory(GuarantorDAO.class);
            dao.save(guarantor);
            
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
     * @param guarantor
     * @return
     * @throws UnexpectedException
     */
    public void delete(GuarantorEntity guarantor)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT delete ");
            
            GuarantorDAO dao = daoFactory(GuarantorDAO.class);
            dao.remove(guarantor);
            
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
     * @param guarantor
     * @return
     * @throws UnexpectedException
     */
    public GuarantorEntity update(GuarantorEntity guarantor)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT update ");
            
            GuarantorDAO dao = daoFactory(GuarantorDAO.class);
            GuarantorEntity temp = dao.update(guarantor);
            
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
