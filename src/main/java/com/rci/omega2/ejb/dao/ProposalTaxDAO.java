package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalTaxEntity;
import com.rci.omega2.entity.enumeration.TaxTypeEnum;

public class ProposalTaxDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(ProposalTaxDAO.class);
    
   
    /**
     * 
     * @param proposalId
     * @param taxType
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public ProposalTaxEntity findProposalTax(Long proposalId, TaxTypeEnum taxType) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findProposalTax ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT pt FROM ProposalTaxEntity pt        ");
            sql.append("    WHERE pt.proposal.id = :proposalId      ");
            sql.append("    AND pt.taxType = :taxType               ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("proposalId", proposalId);
            query.setParameter("taxType", taxType);

            List<ProposalTaxEntity> ls = query.getResultList();

            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> INIT findProposalTax ");
            return null;

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
    public void deleteAllProposalTax(ProposalEntity proposal) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT deleteAllProposalTax ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" DELETE FROM ProposalTaxEntity pt        ");
            sql.append("    WHERE pt.proposal.id = :proposalId   ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("proposalId", proposal.getId());

            query.executeUpdate();

            super.getEntityManager().flush();

            LOGGER.debug(" >> END deleteAllProposalTax ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
