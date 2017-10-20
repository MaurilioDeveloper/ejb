package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.FinanceTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.AppDtoUtils;
import com.rci.omega2.entity.FinanceTypeEntity;

public class FinanceTypeDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(FinanceTypeDAO.class);

    @SuppressWarnings("unchecked")
    public List<FinanceTypeDTO> findFinanceTypeActiveByUser(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findFinanceTypeActiveByUser ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("    SELECT distinct uc.financeType          ");
            sql.append("    FROM UserCommissionEntity AS uc         ");
            sql.append("    WHERE uc.user.id=:userId                ");
            sql.append("    AND uc.financeType.active = :active     ");
            sql.append("    ORDER BY uc.financeType.description     ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("userId", userId);
            query.setParameter("active", Boolean.TRUE);
            
            List<FinanceTypeEntity> financeType = query.getResultList();
            
            List<FinanceTypeDTO> temp = AppDtoUtils.populateFinanceTypeDTO(financeType, true);
            
            LOGGER.debug(" >> END findFinanceTypeActiveByUser ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }      
        
    }

}
