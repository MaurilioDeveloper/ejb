package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.ServiceDAO;
import com.rci.omega2.ejb.dao.ServiceStructureDAO;
import com.rci.omega2.ejb.dao.StructureDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ServiceEntity;
import com.rci.omega2.entity.ServiceStructureEntity;

@Stateless
public class ServiceStructureBO extends BaseBO{
    
private static final Logger LOGGER = LogManager.getLogger(ServiceStructureBO.class);
    
    public void updateService(ServiceStructureEntity entity)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT updateService ");
            ServiceStructureDAO dao = daoFactory(ServiceStructureDAO.class);
            dao.updateService(entity);
            LOGGER.debug(" >> END updateService ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void saveService(ServiceStructureEntity entity)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveService ");
            ServiceStructureDAO dao = daoFactory(ServiceStructureDAO.class);
            ServiceDAO daoService = daoFactory(ServiceDAO.class);
            StructureDAO daoStructure = daoFactory(StructureDAO.class);
            
            entity.setService(daoService.find(ServiceEntity.class, entity.getService().getId()));
            entity.setStructure(daoStructure.findById(entity.getStructure().getId()));
            dao.saveService(entity);
            LOGGER.debug(" >> END saveService ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public ServiceStructureEntity findById(Long id)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findById ");
            ServiceStructureDAO dao = daoFactory(ServiceStructureDAO.class);
            ServiceStructureEntity temp = dao.find(ServiceStructureEntity.class, id);
            LOGGER.debug(" >> END findById ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
