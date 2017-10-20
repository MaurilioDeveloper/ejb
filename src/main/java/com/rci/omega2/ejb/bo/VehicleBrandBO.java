package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.VehicleBrandDAO;
import com.rci.omega2.ejb.dto.VehicleBrandDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

@Stateless
public class VehicleBrandBO extends BaseBO{
    private static final Logger LOGGER = LogManager.getLogger(VehicleBrandBO.class);

    public List<VehicleBrandDTO> findListByVehicleType(String vehicleType) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findListByVehicleType ");
            VehicleBrandDAO dao = daoFactory(VehicleBrandDAO.class);
            List<VehicleBrandDTO> listResult = dao.findListByVehicleType(vehicleType);
            LOGGER.debug(" >> END findListByVehicleType ");
            return listResult;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public String findIdBrandSelected(List<VehicleBrandDTO> list,String selected) throws UnexpectedException {
        LOGGER.debug(" >> INIT findIdBrandSelected ");
        Long idSelected = CriptoUtilsOmega2.decryptIdToLong(selected);
        for (VehicleBrandDTO dto : list) {
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            if(id.equals(idSelected)){
                return dto.getId();
            }
        }
        LOGGER.debug(" >> END findIdBrandSelected ");
        return null;
    }
    
    public List<VehicleBrandDTO> findListByVehicleBrand() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findListByVehicleBrand ");
            VehicleBrandDAO dao = daoFactory(VehicleBrandDAO.class);
            List<VehicleBrandDTO> temp = dao.findListByVehicleBrand();
            LOGGER.debug(" >> END findListByVehicleBrand ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
