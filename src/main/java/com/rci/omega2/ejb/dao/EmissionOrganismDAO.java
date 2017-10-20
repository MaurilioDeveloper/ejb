package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.EmissionOrganismEntity;

public class EmissionOrganismDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(EmissionOrganismDAO.class);

    @SuppressWarnings("unchecked")
    public EmissionOrganismEntity findEmissionOrganismByAcronym(String acronym) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findEmissionOrganismByAcronym ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    SELECT eo FROM EmissionOrganismEntity eo        ");
            sql.append("    WHERE eo.importCode = :importCode               ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", acronym);
            
            List<EmissionOrganismEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findEmissionOrganismByAcronym ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
}
