package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.EventDAO;
import com.rci.omega2.ejb.dao.ProposalDAO;
import com.rci.omega2.ejb.dto.EventDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.EventEntity;
import com.rci.omega2.entity.ProposalEntity;

@Stateless
public class EventBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(EventBO.class);

    
    /**
     * 
     * @param event
     * @return
     * @throws UnexpectedException
     */
    public void insert(EventEntity event)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            EventDAO dao = daoFactory(EventDAO.class);
            dao.save(event);
            LOGGER.debug(" >> END insert ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }


    public List<EventDTO> findByDossier(Long dossierId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findByDossier ");
            
            ProposalDAO proposalDAO = daoFactory(ProposalDAO.class);
            ProposalEntity proposal = proposalDAO.findProposalWithAdpByDossier(dossierId);
            
            if(AppUtil.isNullOrEmpty(proposal)){
                return new ArrayList<EventDTO>();
            }
            
            EventDAO dao = daoFactory(EventDAO.class);
            List<EventDTO> listaDto =  dao.findByProposal(proposal.getId());
            
            LOGGER.debug(" >> END findByDossier ");
            return listaDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
