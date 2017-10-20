package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.VehicleVersionDAO;
import com.rci.omega2.ejb.dto.VehicleVersionDTO;
import com.rci.omega2.ejb.dto.VehicleVersionGroupDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.VehicleVersionEntity;

@Stateless
public class VehicleVersionBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(VehicleVersionBO.class);

    @EJB
    VehicleFileBO vehicleFileBO;

    public List<VehicleVersionEntity> findAll() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAll ");
            VehicleVersionDAO dao = daoFactory(VehicleVersionDAO.class);
            List<VehicleVersionEntity> temp = dao.findAll(VehicleVersionEntity.class);
            LOGGER.debug(" >> END findAll ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public VehicleVersionEntity findOne(Long id) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findOne ");
            VehicleVersionDAO dao = daoFactory(VehicleVersionDAO.class);
            VehicleVersionEntity temp = dao.find(VehicleVersionEntity.class, id);
            LOGGER.debug(" >> END findOne ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<VehicleVersionGroupDTO> findListBVehicleType(Long modelId, String vehicleType) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findListBVehicleType ");
            VehicleVersionDAO dao = daoFactory(VehicleVersionDAO.class);
            List<VehicleVersionGroupDTO> list = dao.findListByVehicleType(modelId, vehicleType);

            for (VehicleVersionGroupDTO vehicleVersionGroupDTO : list) {
                List<String> listFiles = vehicleFileBO.getUrlsImageVehicleByVersionAndModel(CriptoUtilsOmega2.decryptIdToLong(vehicleVersionGroupDTO.getId()), modelId);
                String url = "404.png";
                if (!listFiles.isEmpty()) {
                    url = listFiles.get(0);
                }
                vehicleVersionGroupDTO.setUrl(url);
            }
            LOGGER.debug(" >> END findListBVehicleType ");
            return list;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<Integer> findManufactureYears(String description, String fipe, String vehicleType)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findManufactureYears ");
            VehicleVersionDAO dao = daoFactory(VehicleVersionDAO.class);
            List<Integer> temp = dao.findManufactureYears(description, fipe, vehicleType);
            LOGGER.debug(" >> END findManufactureYears ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<VehicleVersionDTO> findModelYears(String description, String fipe, String saleType,
            Integer manufactureYear) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findModelYears ");
            VehicleVersionDAO dao = daoFactory(VehicleVersionDAO.class);
            List<VehicleVersionDTO> temp = dao.findModelYears(description, fipe, saleType, manufactureYear);
            LOGGER.debug(" >> END findModelYears ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<VehicleVersionDTO> findListVehicleVersionByModel(Long vehicleModelId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findListVehicleVersionByModel ");
            VehicleVersionDAO dao = daoFactory(VehicleVersionDAO.class);
            List<VehicleVersionDTO> temp = dao.findListVehicleVersionByModel(vehicleModelId);
            LOGGER.debug(" >> END findListVehicleVersionByModel ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    /**
     * 
     * @param fipe
     * @param modelYear
     * @param manufactureYear
     * @return
     * @throws UnexpectedException
     */
    public VehicleVersionEntity findVehicleVersion(String fipe, Integer modelYear, Integer manufactureYear)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findVehicleVersion ");
            VehicleVersionDAO dao = daoFactory(VehicleVersionDAO.class);
            VehicleVersionEntity temp = dao.findVehicleVersion(fipe, modelYear, manufactureYear);
            LOGGER.debug(" >> END findVehicleVersion ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
