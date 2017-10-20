package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.SalesmanDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.PersonEntity;

public class SimulationDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(SimulationDAO.class);

    @SuppressWarnings("unchecked")
    public List<SalesmanDTO> findListSalesmanByDealership(Long delearshipId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findListSalesmanByDealership ");
            
            StringBuilder sql = new StringBuilder("");
            
            sql.append(" SELECT P.USER_ID, P.NAME_PERSON ");
            sql.append(" FROM TB_PERSON P INNER JOIN TB_USER U ON P.USER_ID = U.USER_ID ");
            sql.append(" INNER JOIN TB_USER_STRUCTURE US ON US.USER_ID = U.USER_ID ");
            sql.append(" WHERE US.STRUCTURE_ID = :delearshipId ");
            sql.append(" AND U.ACTIVE = 1 "); 
            sql.append(" ORDER BY P.NAME_PERSON ");
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            query.setParameter("delearshipId", delearshipId);
            
            List<Object[]> salesmanResul = query.getResultList();

            List<SalesmanDTO> listResul = new ArrayList<SalesmanDTO>();
            for (Object[] object : salesmanResul) {
                SalesmanDTO dto = new SalesmanDTO();
                String idEncrypt = CriptoUtilsOmega2.encrypt(Long.valueOf(object[0].toString()));
                dto.setId(idEncrypt);
                dto.setName(object[1].toString());
                listResul.add(dto);
            }

            LOGGER.debug(" >> END findListSalesmanByDealership ");
            return listResul;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
        
    }

    public SalesmanDTO findSalesmanBySimulation(Long simulationId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findSalesmanBySimulation ");
            
            Query query = super.getEntityManager().createQuery("SELECT ds.salesman "
                    + "FROM DossierEntity  ds "
                    + "WHERE ds.id = :simulationId ");
            query.setParameter("simulationId", simulationId);
            
            PersonEntity salesmanResul = (PersonEntity) query.getSingleResult();
            SalesmanDTO dto = new SalesmanDTO();

            String idEncrypt = CriptoUtilsOmega2.encrypt(salesmanResul.getId());
            dto.setId(idEncrypt);
            dto.setName(salesmanResul.getNamePerson());

            LOGGER.debug(" >> END findSalesmanBySimulation ");
            return dto;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
