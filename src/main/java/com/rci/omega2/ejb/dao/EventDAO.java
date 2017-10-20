package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.rci.omega2.ejb.dto.EventDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;

public class EventDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(EventDAO.class);

    @SuppressWarnings({ "unchecked"})
    public List<EventDTO> findByProposal(Long proposalId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findByProposal ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" select e.dt_insert as eventDate, ds.description as status, ps.name_person as userName, et.description as details   ");
            sql.append(" from tb_event e                                                                                                    ");
            sql.append(" inner join tb_dossier_status ds on ds.dossier_status_id = e.dossier_status_id                                      ");
            sql.append(" inner join tb_event_type et on et.event_type_id = e.event_type_id                                                  ");
            sql.append(" inner join tb_user u on u.user_id = e.user_id                                                                      ");
            sql.append(" inner join tb_person ps on ps.user_id = u.user_id                                                                  ");
            sql.append(" where e.proposal_id = :proposalId                                                                                  ");
            sql.append(" order by e.dt_insert desc, e.event_id desc                                                                         ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            query.setParameter("proposalId", proposalId);
        
            List<EventDTO> lista = resultTransformer(query.getResultList());

            LOGGER.debug(" >> END findByProposal ");
            return lista;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    
    private List<EventDTO> resultTransformer(List<Object[]> resultList) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT resultTransformer ");
            
            AliasToBeanResultTransformer transform = new AliasToBeanResultTransformer(EventDTO.class);
            
            List<EventDTO> lista = new ArrayList<>(resultList.size());
            
            String[] alias = {"eventDate", "status", "userName", "details"};
            
            for (Object[] row : resultList) {
                lista .add((EventDTO) transform.transformTuple(row, alias));
            }
            
            LOGGER.debug(" >> END resultTransformer ");
            return lista;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
}
