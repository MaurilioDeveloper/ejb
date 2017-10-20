package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.SaleTypeDAO;
import com.rci.omega2.ejb.dto.SaleTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.SaleTypeEntity;

@Stateless
public class SaleTypeBO extends BaseBO {

	private static final Logger LOGGER = LogManager.getLogger(SaleTypeBO.class);

	@EJB
	private ConfigFileBO configFile;
	
	public List<SaleTypeEntity> findAll() throws UnexpectedException {
		try {
		    LOGGER.debug(" >> INIT findAll ");
		    
		    SaleTypeDAO dao = daoFactory(SaleTypeDAO.class);
		    List<SaleTypeEntity> temp = dao.findAll(SaleTypeEntity.class);
		    
		    LOGGER.debug(" >> END findAll ");
		    return temp;
		} catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
	}
	
	public List<SaleTypeDTO> findSaleTypeActive() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSaleTypeActive ");
            
            SaleTypeDAO dao = daoFactory(SaleTypeDAO.class);
            List<SaleTypeDTO> temp = dao.findSaleTypeActive();
            
            LOGGER.debug(" >> END findSaleTypeActive ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<SaleTypeDTO> findSaleTypeActiveByUser(Long userId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSaleTypeActiveByUser ");
            
            SaleTypeDAO dao = daoFactory(SaleTypeDAO.class);
            List<SaleTypeDTO> temp = dao.findSaleTypeActiveByUser(userId);
            
            LOGGER.debug(" >> END findSaleTypeActiveByUser ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    public List<SaleTypeDTO> findSaleTypeActiveByUserAndFinanceType(Long userId,Long financeTypeid) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSaleTypeActiveByUserAndFinanceType ");
            
            SaleTypeDAO dao = daoFactory(SaleTypeDAO.class);
            List<SaleTypeDTO> temp = dao.findSaleTypeActiveByUserAndFinanceType(userId,financeTypeid);
            
            LOGGER.debug(" >> END findSaleTypeActiveByUserAndFinanceType ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    

}
