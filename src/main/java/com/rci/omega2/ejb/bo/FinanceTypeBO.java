package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.FinanceTypeDAO;
import com.rci.omega2.ejb.dto.FinanceTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.FinanceTypeEntity;

@Stateless
public class FinanceTypeBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(PersonBO.class);
    
    public FinanceTypeEntity findById(Long idFinanceType) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findById ");
            
            FinanceTypeDAO dao = daoFactory(FinanceTypeDAO.class);
            FinanceTypeEntity temp = dao.find(FinanceTypeEntity.class, idFinanceType);
            
            LOGGER.debug(" >> END findById ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<FinanceTypeDTO> findFinanceTypeActiveByUser(Long userId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findFinanceTypeActiveByUser ");

            FinanceTypeDAO dao = daoFactory(FinanceTypeDAO.class);
            List<FinanceTypeDTO> temp = dao.findFinanceTypeActiveByUser(userId);
            
            LOGGER.debug(" >> END findFinanceTypeActiveByUser ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
 
    public FinanceTypeEntity findFinanceTypeEntity(FinanceTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findFinanceTypeEntity ");
            
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getFinanceTypeId())){
                return null;
            }
            
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getFinanceTypeId());
            
            LOGGER.debug(" >> INIT findFinanceTypeEntity ");
            return findById(id);
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
