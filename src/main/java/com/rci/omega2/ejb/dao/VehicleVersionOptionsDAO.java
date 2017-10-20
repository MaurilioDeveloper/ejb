package com.rci.omega2.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.VehicleVersionOptionsDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

public class VehicleVersionOptionsDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(VehicleVersionOptionsDAO.class);
    
    @SuppressWarnings("unchecked")
    public List<VehicleVersionOptionsDTO> findListByVersion(Long id) throws UnexpectedException { 
        
        try {
            LOGGER.debug(" >> INIT findListByVersion ");
            
            Query query = super.getEntityManager().createQuery("SELECT o.id, o.description, vo.amount "
                    + "FROM VehicleVersionOptionsEntity as vo " 
                    + "join vo.vehicleVersion as v "
                    + "join vo.vehicleOptions o "
                    + "where v.id = :id "
                    + "and vo.active = 1 "
                    + "order by o.description");
            query.setParameter("id", id);
            
            List<Object[]> listOptions = query.getResultList();
            List<VehicleVersionOptionsDTO> list = new ArrayList<VehicleVersionOptionsDTO>();
            
            for(Object[]option:listOptions){
                VehicleVersionOptionsDTO dto = new VehicleVersionOptionsDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(option[0].toString()));
                dto.setDescription(option[1].toString());
                dto.setAmount((BigDecimal)option[2]);
                
                list.add(dto);
            }
            
            LOGGER.debug(" >> END findListByVersion ");
            return list;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
}
