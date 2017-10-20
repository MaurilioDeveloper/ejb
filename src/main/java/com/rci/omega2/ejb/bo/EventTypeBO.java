package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.EventTypeDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.EventTypeEntity;

@Stateless
public class EventTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(EventTypeBO.class);

    
    /**
     * 
     * @param eventTypeId
     * @return
     * @throws UnexpectedException
     */
    public EventTypeEntity findEventTypeById(Long eventTypeId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findEventTypeById ");
            
            EventTypeDAO dao = daoFactory(EventTypeDAO.class);
            EventTypeEntity temp = dao.find(EventTypeEntity.class, eventTypeId);
            
            LOGGER.debug(" >> END findEventTypeById ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
