package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.VehiclePriceDAO;
import com.rci.omega2.ejb.dto.VehiclePriceDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.VehiclePriceEntity;

@Stateless
public class VehiclePriceBO extends BaseBO{
    private static final Logger LOGGER = LogManager.getLogger(VehiclePriceBO.class);

    public List<VehiclePriceEntity> findAll() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAll ");
            VehiclePriceDAO dao = daoFactory(VehiclePriceDAO.class);
            List<VehiclePriceEntity> temp = dao.findAll(VehiclePriceEntity.class);
            LOGGER.debug(" >> END findAll ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public VehiclePriceEntity findOne(String id) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findOne ");
            VehiclePriceDAO dao = daoFactory(VehiclePriceDAO.class);
            VehiclePriceEntity temp = dao.find(VehiclePriceEntity.class, id);
            LOGGER.debug(" >> END findOne ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public VehiclePriceDTO findByVersion(Long idVersion) throws UnexpectedException {
        try{
            LOGGER.debug(" >> INIT findByVersion ");
            VehiclePriceDAO dao = daoFactory(VehiclePriceDAO.class);
            VehiclePriceDTO dto = dao.findByVersion(idVersion); 
            LOGGER.debug(" >> END findByVersion ");
            return dto; 
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
