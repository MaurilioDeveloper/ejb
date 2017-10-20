package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.GuarantorSpouseDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.GuarantorSpouseEntity;

@Stateless
public class GuarantorSpouseBO extends BaseBO{
    
    
    private static final Logger LOGGER = LogManager.getLogger(GuarantorSpouseBO.class);
       
    /**
     * 
     * @param guarantorSpouse
     * @return
     * @throws UnexpectedException
     */
    public void insert(GuarantorSpouseEntity guarantorSpouse)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            GuarantorSpouseDAO dao = daoFactory(GuarantorSpouseDAO.class);
            dao.save(guarantorSpouse);
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
     * @param guarantorSpouse
     * @return
     * @throws UnexpectedException
     */
    public GuarantorSpouseEntity update(GuarantorSpouseEntity guarantorSpouse)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT update ");
            
            GuarantorSpouseDAO dao = daoFactory(GuarantorSpouseDAO.class);
            GuarantorSpouseEntity temp = dao.update(guarantorSpouse);
            
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
     * @param guarantorSpouse
     * @return
     * @throws UnexpectedException
     */
    public void delete(GuarantorSpouseEntity guarantorSpouse)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT delete ");
            
            GuarantorSpouseDAO dao = daoFactory(GuarantorSpouseDAO.class);
            dao.remove(guarantorSpouse);
            
            LOGGER.debug(" >> END delete ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
