package com.rci.omega2.ejb.bo;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.VehicleOptionsDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.VehicleOptionsEntity;

public class VehicleOptionsBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(VehicleVersionOptionsBO.class);
    
    public List<VehicleOptionsEntity> findAll() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAll ");
            VehicleOptionsDAO dao = daoFactory(VehicleOptionsDAO.class);
            List<VehicleOptionsEntity> temp = dao.findAll(VehicleOptionsEntity.class);

            LOGGER.debug(" >> END findAll ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public VehicleOptionsEntity findOne(Long id) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findOne ");
            VehicleOptionsDAO dao = daoFactory(VehicleOptionsDAO.class);
            VehicleOptionsEntity temp = dao.find(VehicleOptionsEntity.class, id);
            LOGGER.debug(" >> END findOne ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }


}
