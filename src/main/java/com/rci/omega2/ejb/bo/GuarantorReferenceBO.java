package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.GuarantorReferenceDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.GuarantorEntity;
import com.rci.omega2.entity.GuarantorReferenceEntity;

@Stateless
public class GuarantorReferenceBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(GuarantorReferenceBO.class);


   
    /**
     * 
     * @param guarantorReference
     * @return
     * @throws UnexpectedException
     */
    public void insert(GuarantorReferenceEntity guarantorReference)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            
            GuarantorReferenceDAO dao = daoFactory(GuarantorReferenceDAO.class);
            dao.save(guarantorReference);
            
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
     * @param guarantorReference
     * @return
     * @throws UnexpectedException
     */
    public void delete(GuarantorReferenceEntity guarantorReference)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT delete ");
            
            GuarantorReferenceDAO dao = daoFactory(GuarantorReferenceDAO.class);
            dao.remove(guarantorReference);
            
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
     * @throws UnexpectedException
     */
    public void deleteAllGuarantorReference(GuarantorEntity guarantor)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteAllGuarantorReference ");
            
            GuarantorReferenceDAO dao = daoFactory(GuarantorReferenceDAO.class);
            dao.deleteAllGuarantorReference(guarantor);
            
            LOGGER.debug(" >> END deleteAllGuarantorReference ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
