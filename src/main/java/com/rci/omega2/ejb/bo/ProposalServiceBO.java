package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.ProposalServiceDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalServiceEntity;

@Stateless
public class ProposalServiceBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(ProposalServiceBO.class);
    
    public void delete(ProposalServiceEntity proposalService)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT delete ");
            ProposalServiceDAO dao = daoFactory(ProposalServiceDAO.class);
            dao.remove(proposalService);
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
     * @param proposalService
     * @return
     * @throws UnexpectedException
     */
    public void insert(ProposalServiceEntity proposalService)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            ProposalServiceDAO dao = daoFactory(ProposalServiceDAO.class);
            dao.save(proposalService);
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
     * @param proposal
     * @return
     * @throws UnexpectedException
     */
    public ProposalServiceEntity findSPFProposal(ProposalEntity proposal) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSPFProposal ");
            
            ProposalServiceDAO dao = daoFactory(ProposalServiceDAO.class);
            ProposalServiceEntity temp = dao.findSPFProposal(proposal);
            
            LOGGER.debug(" >> END findSPFProposal ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
