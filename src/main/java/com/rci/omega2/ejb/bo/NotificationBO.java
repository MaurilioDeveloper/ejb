package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.NotificationDAO;
import com.rci.omega2.ejb.dto.NotificationListDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.NotificationEntity;

@Stateless
public class NotificationBO extends BaseBO{

    private static final Logger LOGGER = LogManager.getLogger(NotificationBO.class);

    public List<NotificationListDTO> findNotifications(Long userId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findNotifications ");
            
            NotificationDAO dao = daoFactory(NotificationDAO.class);
            List<NotificationListDTO> resultNotices = dao.findNotificationsListByUser(userId, null);
            
            LOGGER.debug(" >> END findNotifications ");
            return resultNotices;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public  List<NotificationListDTO> updateNotification(Long userId,NotificationEntity notificationEntity) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT updateNotification ");
            
            NotificationDAO dao = daoFactory(NotificationDAO.class);
            NotificationEntity entity = dao.find(NotificationEntity.class, notificationEntity.getId());
            entity.setRead(notificationEntity.getRead());
            entity.setId(notificationEntity.getId());
            dao.update(entity);    
            List<NotificationListDTO> resultNotifications = dao.findNotificationsListByUser(userId, null);
            
            LOGGER.debug(" >> END updateNotification ");
            return resultNotifications;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    
}
