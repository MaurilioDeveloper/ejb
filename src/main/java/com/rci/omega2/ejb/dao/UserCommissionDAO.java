package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.UserCommissionDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.UserCommissionEntity;

public class UserCommissionDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserCommissionDAO.class);

    @SuppressWarnings("unchecked")
    public List<UserCommissionDTO> findCommissionByUser(Long userId, Long financeTypeId, Long saleTypeId,
            Long selectedId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionByUser ");
            
            Query query = super.getEntityManager().createQuery(" SELECT c.id, c.description "
                    + " FROM UserCommissionEntity uc " + " INNER JOIN uc.commission c " + " WHERE uc.user.id = :userId "
                    + " AND uc.financeType.id = :financeTypeId " + " AND uc.saleType.id = :saleTypeId ");
    
            query.setParameter("userId", userId);
            query.setParameter("financeTypeId", financeTypeId);
            query.setParameter("saleTypeId", saleTypeId);
    
            List<Object[]> rows = query.getResultList();
            List<UserCommissionDTO> retorno = new ArrayList<UserCommissionDTO>();
    
            for (Object[] row : rows) {
                UserCommissionDTO dto = new UserCommissionDTO();
    
                dto.setCommisonId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setDescription(row[1].toString());
                dto.setTemp(false);
                
                if (selectedId != null && row[0].toString().equalsIgnoreCase(selectedId.toString())) {
                    dto.setSelected(true);
                }
                
                retorno.add(dto);
            }
            
            LOGGER.debug(" >> END findCommissionByUser ");
            return retorno;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }

    public UserCommissionDTO findCommissionById(Long selectedId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionById ");
            
            Query query = super.getEntityManager().createQuery(
                    " SELECT uc " + " FROM UserCommissionEntity uc " + " WHERE uc.commission.id = :selectedId ");
            query.setParameter("selectedId", selectedId);
    
            UserCommissionEntity usserCommissionEntity = (UserCommissionEntity) query.getSingleResult();
            
            if (usserCommissionEntity != null) {
                Hibernate.initialize(usserCommissionEntity.getCommission());
                UserCommissionDTO dto = new UserCommissionDTO();
    
                dto.setCommisonId(CriptoUtilsOmega2.encrypt(usserCommissionEntity.getCommission().getId()));
                dto.setDescription(usserCommissionEntity.getCommission().getDescription());
                dto.setTemp(false);
                return dto;
            }

            LOGGER.debug(" >> END findCommissionById ");
            return null;
        
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    
    
    @SuppressWarnings("unchecked")
    public List<UserCommissionDTO> findCommissionSalemenAndBySaleTypeAndTable(Long salemenid, Long saleTypeId, Long productId, Long selectedId)
            throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionSalemenAndBySaleTypeAndTable ");
            
            Query query = super.getEntityManager()
                    .createNativeQuery("select distinct com.COMMISSION_ID,com.DESCRIPTION  from TB_PRODUCT_COEFFICIENT coef"
                            + " inner join OMEGA2.TB_USER_COMMISSION ucom  on coef.commission_id = ucom.COMMISSION_ID "
                            + " inner join OMEGA2.TB_COMMISSION com on ucom.COMMISSION_ID=com.COMMISSION_ID "
                            + " WHERE ucom.USER_ID=:salemen AND ucom.SALE_TYPE_ID = :saleTypeId  AND coef.PRODUCT_ID = :financeTableId");
            query.setParameter("salemen", salemenid);
            query.setParameter("saleTypeId", saleTypeId);
            query.setParameter("financeTableId", productId);
            
            List<Object[]> rows = query.getResultList();
            List<UserCommissionDTO> retorno = new ArrayList<UserCommissionDTO>();
    
            for (Object[] row : rows) {
                UserCommissionDTO dto = new UserCommissionDTO();
    
                dto.setCommisonId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setDescription(row[1].toString());
                dto.setTemp(false);
                
                if (selectedId != null && row[0].toString().equalsIgnoreCase(selectedId.toString())) {
                    dto.setSelected(true);
                }
                retorno.add(dto);
            }
            
            LOGGER.debug(" >> END findCommissionSalemenAndBySaleTypeAndTable ");
            return retorno;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
        
    }
    
    

    @SuppressWarnings("unchecked")
    public List<UserCommissionDTO> findCommissionBySaleTypeAndTable(Long saleTypeId, Long productId, Long selectedId)
            throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionBySaleTypeAndTable ");
            
            Query query = super.getEntityManager()
                    .createNativeQuery("select  distinct com.COMMISSION_ID, com.DESCRIPTION from TB_PRODUCT_COEFFICIENT coef"
                            + " inner join OMEGA2.TB_USER_COMMISSION ucom  on coef.commission_id = ucom.COMMISSION_ID "
                            + " inner join OMEGA2.TB_COMMISSION com on ucom.COMMISSION_ID=com.COMMISSION_ID "
                            + " WHERE ucom.SALE_TYPE_ID = :saleTypeId AND coef.PRODUCT_ID = :financeTableId");
    
            query.setParameter("saleTypeId", saleTypeId);
            query.setParameter("financeTableId", productId);
    
            List<Object[]> rows = query.getResultList();
            List<UserCommissionDTO> retorno = new ArrayList<UserCommissionDTO>();
    
            for (Object[] row : rows) {
                UserCommissionDTO dto = new UserCommissionDTO();
    
                dto.setCommisonId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setDescription(row[1].toString());
                dto.setTemp(false);
                
                if (selectedId != null && row[0].toString().equalsIgnoreCase(selectedId.toString())) {
                    dto.setSelected(true);
                }
                
                retorno.add(dto);
            }
            
            LOGGER.debug(" >> END findCommissionBySaleTypeAndTable ");
            return retorno;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
                
    }

    @SuppressWarnings("unchecked")
    public List<CommissionDTO> findCommissionByUser(Long userId, Long financeTypeId, Long saleTypeId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionByUser ");
            
            Query query = super.getEntityManager().createQuery(
                    " SELECT uc " +
            " FROM UserCommissionEntity uc " +
            " WHERE uc.user.id = :userId and uc.saleType.id = :saleTypeId and uc.financeType.id= :financeTypeId ");        
            query.setParameter("userId", userId);
            query.setParameter("financeTypeId", financeTypeId);
            query.setParameter("saleTypeId", saleTypeId);
    
    
            List<UserCommissionEntity> ListUserCommissionEntity =  query.getResultList();
            List<CommissionDTO> retorno = new ArrayList<CommissionDTO>();
            
            for (UserCommissionEntity userCommissionEntity : ListUserCommissionEntity) {
                CommissionDTO dto = new CommissionDTO();
                Hibernate.initialize(userCommissionEntity.getCommission());
                dto.setId(CriptoUtilsOmega2.encrypt(userCommissionEntity.getCommission().getId()));
                dto.setDescription(userCommissionEntity.getCommission().getDescription());
                dto.setTemp(false);
                retorno.add(dto);
            }
            
            LOGGER.debug(" >> END findCommissionByUser ");
            return retorno;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }

}
