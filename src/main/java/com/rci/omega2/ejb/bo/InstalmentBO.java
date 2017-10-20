package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.InstalmentDAO;
import com.rci.omega2.ejb.dao.PhoneDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.GuarantorEntity;
import com.rci.omega2.entity.InstalmentEntity;
import com.rci.omega2.entity.ProposalEntity;

@Stateless
public class InstalmentBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(InstalmentBO.class);
   
    /**
     * 
     * @param instalment
     * @return
     * @throws UnexpectedException
     */
    public void insert(InstalmentEntity instalment)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            
            InstalmentDAO dao = daoFactory(InstalmentDAO.class);
            dao.save(instalment);
            
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
     * @param instalment
     * @return
     * @throws UnexpectedException
     */
    public void delete(InstalmentEntity instalment)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT delete ");
            
            InstalmentDAO dao = daoFactory(InstalmentDAO.class);
            dao.remove(instalment);
            
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
     * @param instalment
     * @throws UnexpectedException
     */
    public void deleteAllInstalmentProposal(ProposalEntity proposal)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteAllInstalmentProposal ");
            
            InstalmentDAO dao = daoFactory(InstalmentDAO.class);
            dao.deleteAllInstalmentProposal(proposal);
            
            LOGGER.debug(" >> END deleteAllInstalmentProposal ");
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
