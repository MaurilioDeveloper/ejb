package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalServiceEntity;

public class ProposalServiceDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(ProposalServiceDAO.class);
    
    /**
     * 
     * @param proposal
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public ProposalServiceEntity findSPFProposal(ProposalEntity proposal) throws UnexpectedException  {
        
        try {
            LOGGER.debug(" >> INIT findSPFProposal ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT ps  FROM ProposalServiceEntity ps                        ");
            sql.append("    WHERE ps.service.importCodeOmega = :spfImportcodeOmega       ");
            sql.append("    AND ps.proposal.id = :proposalId                             ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("spfImportcodeOmega", AppConstants.SPF_IMPORT_CODE_OMEGA);
            query.setParameter("proposalId", proposal.getId());

            List<ProposalServiceEntity> ls = query.getResultList();

            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findSPFProposal ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
            
    }

    public void deleteAllProposalService(ProposalEntity proposal) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT deleteAllProposalService ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" DELETE FROM ProposalServiceEntity pt        ");
            sql.append("    WHERE pt.proposal.id = :proposalId       ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("proposalId", proposal.getId());

            query.executeUpdate();

            super.getEntityManager().flush();
            
            LOGGER.debug(" >> END deleteAllProposalService ");

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    

    }
    
}
