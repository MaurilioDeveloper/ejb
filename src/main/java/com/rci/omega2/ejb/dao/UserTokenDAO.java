package com.rci.omega2.ejb.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.entity.UserTokenEntity;

public class UserTokenDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(UserTokenDAO.class);

    public String findIdTokenByUser(Long idUser) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findIdTokenByUser ");
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00");
            
            Date oneDayBefore = GeneralUtils.removeDayToDate(1, new Date());
            
            String dateBeforeString = df.format(oneDayBefore);
            oneDayBefore = df.parse(dateBeforeString);
            
            Query queryExists = super.getEntityManager().createQuery("select t.id from UserTokenEntity t " 
                    + "where t.user.id = :idUser "
                    + "and t.includeDate between :dateBefore and sysdate " 
                    + "order by t.includeDate desc ");
            queryExists.setParameter("idUser", idUser);
            queryExists.setParameter("dateBefore", oneDayBefore);
            
            String idToken = queryExists.getResultList().get(0).toString();
            
            LOGGER.debug(" >> END findIdTokenByUser ");
            return idToken;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
    public UserTokenEntity findTokenByUser(Long idUser) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findTokenByUser ");
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00");
            
            Date oneDayBefore = GeneralUtils.removeDayToDate(1, new Date());
            
            String dateBeforeString = df.format(oneDayBefore);
            oneDayBefore = df.parse(dateBeforeString);
            
            Query queryExists = super.getEntityManager()
                    .createQuery("select t from UserTokenEntity t " + "where t.user.id = :idUser "
                            + "and t.includeDate between :dateBefore and sysdate " + "order by t.includeDate desc ");
            queryExists.setParameter("idUser", idUser);
            queryExists.setParameter("dateBefore", oneDayBefore);
            UserTokenEntity entity = new UserTokenEntity();
    
            if (queryExists.getResultList().size() > 0) {
                entity = (UserTokenEntity) queryExists.getResultList().get(queryExists.getFirstResult());
            }
            
            LOGGER.debug(" >> END findTokenByUser ");
            return entity;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
}
