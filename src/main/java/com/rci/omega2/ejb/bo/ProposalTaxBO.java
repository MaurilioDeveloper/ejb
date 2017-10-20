package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.ProposalTaxDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalTaxEntity;
import com.rci.omega2.entity.enumeration.TaxTypeEnum;

@Stateless
public class ProposalTaxBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(ProposalTaxBO.class);

    
    /**
     * 
     * @param proposalId
     * @param taxType
     * @return
     * @throws UnexpectedException
     */
    public ProposalTaxEntity findProposalTax(Long proposalId, TaxTypeEnum taxType) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProposalTax ");
            
            ProposalTaxDAO dao = daoFactory(ProposalTaxDAO.class);
            ProposalTaxEntity temp = dao.findProposalTax(proposalId, taxType);
            
            LOGGER.debug(" >> END findProposalTax ");
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
     * @param proposal
     * @throws UnexpectedException
     */
    public void deleteAllProposalTax(ProposalEntity proposal)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteAllProposalTax ");
            
            ProposalTaxDAO dao = daoFactory(ProposalTaxDAO.class);
            dao.deleteAllProposalTax(proposal);
            
            LOGGER.debug(" >> END deleteAllProposalTax ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    
    
    /**
     * 
     * @param proposalTax
     * @return
     * @throws UnexpectedException
     */
    public void insert(ProposalTaxEntity proposalTax)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            
            ProposalTaxDAO dao = daoFactory(ProposalTaxDAO.class);
            dao.save(proposalTax);
            
            LOGGER.debug(" >> END insert ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
