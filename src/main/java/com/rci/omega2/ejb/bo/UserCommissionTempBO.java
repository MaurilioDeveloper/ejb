package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.CommissionDAO;
import com.rci.omega2.ejb.dao.FinanceTypeDAO;
import com.rci.omega2.ejb.dao.SaleTypeDAO;
import com.rci.omega2.ejb.dao.UserCommissionTempDAO;
import com.rci.omega2.ejb.dao.UserDAO;
import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.UserCommissionDTO;
import com.rci.omega2.ejb.dto.UserCommissionTempDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CommissionEntity;
import com.rci.omega2.entity.FinanceTypeEntity;
import com.rci.omega2.entity.SaleTypeEntity;
import com.rci.omega2.entity.UserCommissionTempEntity;
import com.rci.omega2.entity.UserEntity;

@Stateless
public class UserCommissionTempBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(UserCommissionTempBO.class);

    public List<UserCommissionTempDTO> findCommissionTempBySalesman(Long salesmanId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCommissionTempBySalesman ");
            UserCommissionTempDAO dao = daoFactory(UserCommissionTempDAO.class);
            List<UserCommissionTempDTO> temp = dao.findCommissionTempBySalesman(salesmanId);
            
            LOGGER.debug(" >> END findCommissionTempBySalesman ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<UserCommissionDTO> findActiveCommissionTempBySalesman(Long salesmanId, Long financeTypeId,
            Long saleTypeId, Long selectedId, Long financetableId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findActiveCommissionTempBySalesman ");
            UserCommissionTempDAO dao = daoFactory(UserCommissionTempDAO.class);
            List<UserCommissionTempDTO> list = dao.findCommissionTempBySalesmanWithFilters(salesmanId, financeTypeId,
                    saleTypeId, selectedId, financetableId);
            List<UserCommissionDTO> active = new ArrayList<UserCommissionDTO>();
            for (UserCommissionTempDTO userCommissionTempDTO : list) {
                UserCommissionDTO comision = new UserCommissionDTO();
                comision.setCommisonId(userCommissionTempDTO.getId());
                comision.setDescription(userCommissionTempDTO.getCommission());
                comision.setTemp(true);
                comision.setSelected(userCommissionTempDTO.getSelected());
                active.add(comision);
            }
            LOGGER.debug(" >> END findActiveCommissionTempBySalesman ");
            return active;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public void saveCommissionTemp(UserCommissionTempEntity entity) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveCommissionTemp ");
            UserCommissionTempDAO daoUserCommissionTemp = daoFactory(UserCommissionTempDAO.class);
            CommissionDAO daoCommission = daoFactory(CommissionDAO.class);
            UserDAO daoUser = daoFactory(UserDAO.class);
            SaleTypeDAO daoSaleType = daoFactory(SaleTypeDAO.class);
            FinanceTypeDAO daoFinanceType = daoFactory(FinanceTypeDAO.class);

            entity.setCommission(daoCommission.find(CommissionEntity.class, entity.getCommission().getId()));
            entity.setSalesman(daoUser.find(UserEntity.class, entity.getSalesman().getId()));
            entity.setSaleType(daoSaleType.find(SaleTypeEntity.class, entity.getSaleType().getId()));
            entity.setFinanceType(daoFinanceType.find(FinanceTypeEntity.class, entity.getFinanceType().getId()));
            entity.setUser(daoUser.find(UserEntity.class, entity.getUser().getId()));
            entity.setIncludeDate(AppUtil.getToday().getTime());
            entity.setExpireDate(AppUtil.getNowPlusHours(1).getTime());

            daoUserCommissionTemp.save(entity);
            LOGGER.debug(" >> END saveCommissionTemp ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public UserCommissionDTO findCommissionTempById(Long selectedId) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT findCommissionTempById ");
        UserCommissionTempDAO daoUserCommissionTemp = daoFactory(UserCommissionTempDAO.class);
        UserCommissionDTO temp = daoUserCommissionTemp.findCommissionTempById(selectedId);
        LOGGER.debug(" >> END findCommissionTempById ");
        return temp;

    }

    public List<CommissionDTO> findActiveCommissionTempByUser(Long salesmanId, Long financeTypeId, Long saleTypeId) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT findActiveCommissionTempByUser ");
        UserCommissionTempDAO dao = daoFactory(UserCommissionTempDAO.class);
        List<CommissionDTO> temp = dao.findCommissionTempByUser(salesmanId,financeTypeId,saleTypeId);
        LOGGER.debug(" >> END findActiveCommissionTempByUser ");
        return temp; 
    }

}
