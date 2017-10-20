package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.UpdateStatusSyncDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.enumeration.StatusSyncStateEnum;

@Stateless
public class UpdateStatusSyncBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(UpdateStatusSyncBO.class);

    /**
     * 
     * @param adp
     * @param statusSyncState
     * @throws UnexpectedException
     */
    public void updateStatusSyncState(String adp, StatusSyncStateEnum statusSyncState) throws UnexpectedException {
        try{
            LOGGER.debug(" >> INIT updateStatusSyncState ");
            UpdateStatusSyncDAO dao = daoFactory(UpdateStatusSyncDAO.class);
            dao.updateStatusSyncState(adp, statusSyncState);
            LOGGER.debug(" >> END updateStatusSyncState ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
