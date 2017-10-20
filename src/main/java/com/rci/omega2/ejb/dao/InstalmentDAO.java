package com.rci.omega2.ejb.dao;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ProposalEntity;

public class InstalmentDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(InstalmentDAO.class);

    /**
     * 
     * @param proposal
     * @throws UnexpectedException
     */
    public void deleteAllInstalmentProposal(ProposalEntity proposal) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT deleteAllInstalmentProposal ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    DELETE FROM InstalmentEntity il              ");
            sql.append("    WHERE il.proposal.id = :proposalId           ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("proposalId", proposal.getId());
            
            query.executeUpdate();
         
            super.getEntityManager().flush();
            
            LOGGER.debug(" >> END deleteAllInstalmentProposal ");
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
}
