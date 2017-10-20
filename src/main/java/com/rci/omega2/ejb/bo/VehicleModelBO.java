package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.VehicleModelDAO;
import com.rci.omega2.ejb.dto.VehicleModelDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.VehicleModelEntity;

@Stateless
public class VehicleModelBO extends BaseBO {
    private static final Logger LOGGER = LogManager.getLogger(VehicleModelBO.class);

    public List<VehicleModelEntity> findAll() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAll ");
            VehicleModelDAO dao = daoFactory(VehicleModelDAO.class);
            List<VehicleModelEntity> temp = dao.findAll(VehicleModelEntity.class);
            LOGGER.debug(" >> END findAll ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public VehicleModelEntity findOne(Long id) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findOne ");
            VehicleModelDAO dao = daoFactory(VehicleModelDAO.class);
            VehicleModelEntity temp = dao.find(VehicleModelEntity.class, id);
            LOGGER.debug(" >> END findOne ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<VehicleModelDTO> listVehicleModelByBrandAndType(Long id, String vehicleType)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT listVehicleModelByBrandAndType ");
            VehicleModelDAO dao = daoFactory(VehicleModelDAO.class);
            List<VehicleModelDTO> listDto = dao.listByBrandAndVehicleType(id, vehicleType);
            
            LOGGER.debug(" >> END listVehicleModelByBrandAndType ");
            return listDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<VehicleModelDTO> listVehicleModelByBrandAndTypeAndGender(Long idBrand, String vehicleType,
            String vehicleGender) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT listVehicleModelByBrandAndTypeAndGender ");
            VehicleModelDAO dao = daoFactory(VehicleModelDAO.class);
            List<VehicleModelDTO> listDto = dao.listByBrandAndVehicleTypeAndVehicleGender(idBrand, vehicleType, vehicleGender);
            LOGGER.debug(" >> END listVehicleModelByBrandAndTypeAndGender ");
            return listDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public List<VehicleModelDTO> listVehicleModelByBrand(Long idVehicleBrand) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT listVehicleModelByBrand ");
            VehicleModelDAO dao = daoFactory(VehicleModelDAO.class);
            List<VehicleModelDTO> temp = dao.listVehicleModelByBrand(idVehicleBrand);
            LOGGER.debug(" >> END listVehicleModelByBrand ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
