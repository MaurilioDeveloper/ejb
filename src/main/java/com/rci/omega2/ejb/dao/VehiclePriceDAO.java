package com.rci.omega2.ejb.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.VehiclePriceDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

public class VehiclePriceDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(VehiclePriceDAO.class);

    @SuppressWarnings("rawtypes")
    public VehiclePriceDTO findByVersion(Long idVersion) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findByVersion ");
            
            Query query = super.getEntityManager().createQuery("SELECT p.id, p.amount  "
                    + "FROM VehiclePriceEntity as p " 
                    + "where p.vehicleVersion.id = :idVersion " 
                    + "and (sysdate BETWEEN p.initialPeriod and p.finalPeriod or p.finalPeriod = null) "
                    + "and sysdate >= p.initialPeriod "
                    + "order by p.initialPeriod desc");
            query.setParameter("idVersion", idVersion);
            
            List result = query.getResultList();
            
            if(!result.isEmpty()){
                Object[] price = (Object[])result.get(0);
                
                VehiclePriceDTO dto = new VehiclePriceDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(price[0].toString()));
                dto.setAmount(new BigDecimal(price[1].toString()));
                
                return dto;
            }
            
            LOGGER.debug(" >> END findByVersion ");
            return null;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
        
    }
    
}
