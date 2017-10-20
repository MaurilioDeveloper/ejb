package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.ProductCoefficientDAO;
import com.rci.omega2.ejb.dao.ProductPromotionalDAO;
import com.rci.omega2.ejb.dao.UserCommissionDAO;
import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.UserCommissionDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.UserCommissionEntity;

@Stateless
public class UserCommissionBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(UserCommissionBO.class);
    
    public List<UserCommissionDTO> findCommissionByUser(Long userId, Long financeTypeId, Long saleTypeId, Long selectedId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCommissionByUser ");
            UserCommissionDAO dao = daoFactory(UserCommissionDAO.class);
            List<UserCommissionDTO> temp = dao.findCommissionByUser(userId, financeTypeId, saleTypeId,selectedId);
            LOGGER.debug(" >> END findCommissionByUser ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    public List<UserCommissionDTO> findCommissionSalemenAndBySaleTypeAndTable(Long userId, Long financeTypeId, Long saleTypeId, Long selectedId,Long productId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCommissionSalemenAndBySaleTypeAndTable ");
            UserCommissionDAO dao = daoFactory(UserCommissionDAO.class);
            List<UserCommissionDTO> temp = dao.findCommissionSalemenAndBySaleTypeAndTable(userId, saleTypeId, productId, selectedId);
            LOGGER.debug(" >> END findCommissionSalemenAndBySaleTypeAndTable ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
   
    

    
    public List<CommissionDTO> findCommissionByTable(Long productId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCommissionByTable ");
            ProductCoefficientDAO dao = daoFactory(ProductCoefficientDAO.class);
            List<CommissionDTO> list = dao.findCommissionsFromTable(productId);
            List<CommissionDTO> ret = new ArrayList<CommissionDTO>();
            //tratamento temporario para trazer as commissões de tabelas em promoção
            for (CommissionDTO commissionDTO : list) {
                CommissionDTO comision = new CommissionDTO();
                comision.setId(commissionDTO.getId());
                comision.setDescription(commissionDTO.getDescription());
                ret.add(comision);
            }
            LOGGER.debug(" >> END findCommissionByTable ");
            return ret;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    public List<CommissionDTO> findPromotionalCommissionByTable(Long selectedId,Long productId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findPromotionalCommissionByTable ");
            ProductPromotionalDAO productPromotionalDAO = daoFactory(ProductPromotionalDAO.class);
            
            List<CommissionDTO> list = productPromotionalDAO.findPromotionalCommissions(productId);
            List<CommissionDTO> ret = new ArrayList<CommissionDTO>();
            //tratamento temporario para trazer as commissões de tabelas em promoção
            for (CommissionDTO commissionDTO : list) {
                CommissionDTO comision = new CommissionDTO();
                comision.setId(commissionDTO.getId());
                comision.setDescription(commissionDTO.getDescription());
                if(commissionDTO.getId().equals(selectedId)){
                    comision.setSelected(true); 
                }
                ret.add(comision);
            }
            LOGGER.debug(" >> END findPromotionalCommissionByTable ");
            return ret;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    public List<UserCommissionDTO> findMaxCommission(Long saleTypeId, Long productId, Long selectedId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findMaxCommission ");
            UserCommissionDAO dao = daoFactory(UserCommissionDAO.class);
            List<UserCommissionDTO> list = dao.findCommissionBySaleTypeAndTable(saleTypeId,productId,selectedId);
            UserCommissionDTO max = Collections.max(list, Comparator.comparing(c -> Integer.parseInt(c.getDescription())));
            List<UserCommissionDTO> ret =  new  ArrayList<UserCommissionDTO>();
            ret.add(max);
            LOGGER.debug(" >> END findMaxCommission ");
            return ret;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    public UserCommissionEntity findCommissionById(Long selectedId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCommissionById ");
            UserCommissionDAO dao = daoFactory(UserCommissionDAO.class);
            UserCommissionEntity temp = dao.find(UserCommissionEntity.class, selectedId);
            LOGGER.debug(" >> END findCommissionById ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }


    public List<CommissionDTO> findCommissionByUser(Long salesmanId, Long financeTypeId, Long saleTypeId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCommissionByUser ");
            UserCommissionDAO dao = daoFactory(UserCommissionDAO.class);
            List<CommissionDTO> temp = dao.findCommissionByUser(salesmanId,financeTypeId,saleTypeId);
            LOGGER.debug(" >> END findCommissionByUser ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
