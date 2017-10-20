package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.BankDetailDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.BankDetailEntity;

@Stateless
public class BankDetailBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(BankDetailBO.class);

    
    /**
     * 
     * @param bankDetailEntity
     * @return
     * @throws UnexpectedException
     */
    public BankDetailEntity update(BankDetailEntity bankDetailEntity)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT update ");
            
            BankDetailDAO dao = daoFactory(BankDetailDAO.class);
            BankDetailEntity temp = dao.update(bankDetailEntity);
            
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
     * @param bankDetailEntity
     * @return
     * @throws UnexpectedException
     */
    public void insert(BankDetailEntity bankDetailEntity)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT insert ");
            BankDetailDAO dao = daoFactory(BankDetailDAO.class);
            dao.save(bankDetailEntity);    
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
     * @param bankDetail
     * @return
     * @throws UnexpectedException
     */
    public void delete(BankDetailEntity bankDetail)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT delete ");
            BankDetailDAO dao = daoFactory(BankDetailDAO.class);
            dao.remove(bankDetail);
            LOGGER.debug(" >> END delete ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
}
