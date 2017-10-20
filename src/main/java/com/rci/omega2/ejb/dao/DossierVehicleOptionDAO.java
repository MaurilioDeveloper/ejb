package com.rci.omega2.ejb.dao;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;

public class DossierVehicleOptionDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(DossierVehicleOptionDAO.class);

    public void deleteByDossier(Long dossierId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT deleteByDossier ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    delete from DossierVehicleOptionEntity pe ");
            sql.append("    where pe.dossier.id = :dossierId          ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("dossierId", dossierId);
            query.executeUpdate();
            
            LOGGER.debug(" >> END deleteByDossier ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}
