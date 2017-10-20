package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.DossierVehicleEntity;

public class DossierVehicleDAO extends BaseDAO {
   
    private static final Logger LOGGER = LogManager.getLogger(DossierVehicleDAO.class);
    
    @SuppressWarnings({ "unchecked"})
    public DossierVehicleEntity findByDossierId(Long dossierId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findByDossierId ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    select pe.dossierVehicle from DossierEntity pe   ");
            sql.append("    where pe.id = :dossierId                         ");
        
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("dossierId", dossierId);
            
            List<DossierVehicleEntity> lista = query.getResultList();
            
            if(!AppUtil.isNullOrEmpty(lista)){
                return lista.get(0);
            }
            
            LOGGER.debug(" >> END findByDossierId ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
}
