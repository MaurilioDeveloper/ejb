package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.DossierVehicleDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.DossierVehicleEntity;

@Stateless
public class DossierVehicleBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(DossierVehicleBO.class);

    /**
     * 
     * @param dossierVehicle
     * @return
     * @throws UnexpectedException
     */
    public DossierVehicleEntity update(DossierVehicleEntity dossierVehicle)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT update ");
            
            DossierVehicleDAO dao = daoFactory(DossierVehicleDAO.class);
            DossierVehicleEntity temp = dao.update(dossierVehicle);
            
            LOGGER.debug(" >> END update ");
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
     * @param dossierVehicle
     * @return
     * @throws UnexpectedException
     */
    public void insert(DossierVehicleEntity dossierVehicle)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT insert ");
            
            DossierVehicleDAO dao = daoFactory(DossierVehicleDAO.class);
            dao.save(dossierVehicle);
            
            LOGGER.debug(" >> END insert ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * 
     * @param dossierVehicle
     * @return
     * @throws UnexpectedException
     */
    public void delete(DossierVehicleEntity dossierVehicle)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT delete ");
            
            DossierVehicleDAO dao = daoFactory(DossierVehicleDAO.class);
            dao.remove(dossierVehicle);
            
            LOGGER.debug(" >> END delete ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
