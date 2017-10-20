package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.ServiceDAO;
import com.rci.omega2.ejb.dto.ServiceDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ServiceEntity;

@Stateless
public class ServiceBO extends BaseBO{
    private static final Logger LOGGER = LogManager.getLogger(ServiceBO.class);
    
    public List<ServiceDTO> findService(Long structureId, Long productId, String vehicleType, Long financeTypeId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findService ");
            ServiceDAO dao = daoFactory(ServiceDAO.class);
            List<ServiceDTO> temp = dao.findService(structureId, productId, vehicleType, financeTypeId);
            
            LOGGER.debug(" >> END findService ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public List<ServiceDTO> findServiceStructure(Long structureId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findServiceStructure ");
            ServiceDAO dao = daoFactory(ServiceDAO.class);
            List<ServiceDTO> temp = dao.findServiceStructure(structureId);
            
            LOGGER.debug(" >> END findServiceStructure ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public ServiceEntity findById(Long id)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findById ");
            ServiceDAO dao = daoFactory(ServiceDAO.class);
            ServiceEntity temp = dao.find(ServiceEntity.class, id);
            
            LOGGER.debug(" >> END findById ");
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
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    public ServiceEntity findByImportCodeOmega(String importCodeOmega)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findByImportCodeOmega ");
            ServiceDAO dao = daoFactory(ServiceDAO.class);
            ServiceEntity temp = dao.findByImportCodeOmega(importCodeOmega);    
            LOGGER.debug(" >> END findByImportCodeOmega ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
