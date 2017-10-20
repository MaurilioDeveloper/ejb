package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.VehicleVersionOptionsDAO;
import com.rci.omega2.ejb.dto.VehicleVersionOptionsDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.VehicleVersionOptionsEntity;

@Stateless
public class VehicleVersionOptionsBO extends BaseBO{
    private static final Logger LOGGER = LogManager.getLogger(VehicleVersionOptionsBO.class);
    
    public List<VehicleVersionOptionsEntity> findAll() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAll ");
            VehicleVersionOptionsDAO dao = daoFactory(VehicleVersionOptionsDAO.class);
            List<VehicleVersionOptionsEntity> temp = dao.findAll(VehicleVersionOptionsEntity.class);
            LOGGER.debug(" >> END findAll ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public VehicleVersionOptionsEntity findOne(String id) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findOne ");
            VehicleVersionOptionsDAO dao = daoFactory(VehicleVersionOptionsDAO.class);
            VehicleVersionOptionsEntity temp = dao.find(VehicleVersionOptionsEntity.class, id);
            LOGGER.debug(" >> END findOne ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<VehicleVersionOptionsDTO> findListByVersion(Long id) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findListByVersion ");
            VehicleVersionOptionsDAO dao = daoFactory(VehicleVersionOptionsDAO.class);
            List<VehicleVersionOptionsDTO> listDto = dao.findListByVersion(id);
            LOGGER.debug(" >> END findListByVersion ");
            return listDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
