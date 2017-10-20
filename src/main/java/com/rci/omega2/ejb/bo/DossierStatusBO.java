package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.DossierStatusDAO;
import com.rci.omega2.ejb.dto.DossierStatusDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.DossierStatusEntity;

@Stateless
public class DossierStatusBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(DossierStatusBO.class);
    
    public List<DossierStatusDTO> findDossierStatus()throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findDossierStatus ");
            
            DossierStatusDAO dao = daoFactory(DossierStatusDAO.class);
            List<DossierStatusDTO> temp = dao.findDossierStatus();
            
            LOGGER.debug(" >> END findDossierStatus ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public DossierStatusEntity findDossierStatus(Long id)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findDossierStatus ");
            
            DossierStatusDAO dao = daoFactory(DossierStatusDAO.class);
            DossierStatusEntity temp = dao.find(DossierStatusEntity.class, id);
            
            LOGGER.debug(" >> END findDossierStatus ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
