package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.UserCommissionDTO;
import com.rci.omega2.ejb.dto.UserCommissionTempDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.UserCommissionTempEntity;

public class UserCommissionTempDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(UserCommissionTempDAO.class);

    
    @SuppressWarnings("unchecked")
    public List<UserCommissionTempDTO> findCommissionTempBySalesman(Long salesmanId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findCommissionTempBySalesman ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT                                                                         ");
            sql.append(" UCT.USER_COMMISSION_TEMP_ID,                                                   ");
            sql.append(" UCT.DT_INSERT,                                                                 ");
            sql.append(" P.NAME_PERSON AS NAME_USER,                                                    ");
            sql.append(" PSALE.NAME_PERSON AS NAME_SALESMAN,                                            ");
            sql.append(" CM.DESCRIPTION AS COMMISSION,                                                  ");
            sql.append(" FT.DESCRIPTION AS FINANCE,                                                     ");
            sql.append(" ST.DESCRIPTION AS SALES,                                                       ");
            sql.append(" UCT.EXPIRE_DATE,                                                               ");
            sql.append(" UCT.PROPOSAL_ID                                                                ");
            sql.append(" FROM TB_USER_COMMISSION_TEMP UCT                                               ");
            sql.append(" INNER JOIN TB_COMMISSION CM ON CM.COMMISSION_ID = UCT.COMMISSION_ID            ");
            sql.append(" INNER JOIN TB_SALE_TYPE ST ON ST.SALE_TYPE_ID = UCT.SALE_TYPE_ID               ");
            sql.append(" INNER JOIN TB_FINANCE_TYPE FT ON FT.FINANCE_TYPE_ID = UCT.FINANCE_TYPE_ID      ");
            sql.append(" INNER JOIN TB_USER USALE ON USALE.USER_ID = UCT.SALESMAN_USER_ID               ");
            sql.append(" INNER JOIN TB_PERSON PSALE ON PSALE.USER_ID = USALE.USER_ID                    ");
            sql.append(" INNER JOIN TB_USER U ON U.USER_ID = UCT.USER_ID                                ");
            sql.append(" INNER JOIN TB_PERSON P ON P.USER_ID = U.USER_ID                                ");
            sql.append(" WHERE UCT.SALESMAN_USER_ID = :salesmanId                                       ");
            sql.append(" ORDER BY UCT.DT_INSERT                                                         ");
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            
            query.setParameter("salesmanId", salesmanId);
            
            List<Object[]> rows = query.getResultList();
            List<UserCommissionTempDTO> retorno = new ArrayList<UserCommissionTempDTO>();
            
            for(Object[] row : rows){            
                UserCommissionTempDTO dto = new UserCommissionTempDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setInsertDate((Date) row[1]);
                dto.setUserName(row[2].toString());
                dto.setSalesmanName(row[3].toString());
                dto.setCommission(row[4].toString());
                dto.setFinanceType(row[5].toString());
                dto.setSaleType(row[6].toString());
                dto.setExpireDate((Date) row[7]);
                dto.setProposalId((Long) row[8]);
                retorno.add(dto);
            }
            
            LOGGER.debug(" >> END findCommissionTempBySalesman ");
            return retorno;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
    

    @SuppressWarnings("unchecked")
    public List<CommissionDTO> findCommissionTempByUser(Long userId, Long financeTypeId, Long saleTypeId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findCommissionTempByUser ");
            
            Query query = super.getEntityManager().createQuery(" SELECT uc " 
                    + " FROM UserCommissionTempEntity uc "
                    + " WHERE uc.user.id = :userId and uc.saleType.id = :saleTypeId and uc.financeType.id= :financeTypeId ");        
            query.setParameter("userId", userId);
            query.setParameter("financeTypeId", financeTypeId);
            query.setParameter("saleTypeId", saleTypeId);
            
            List<UserCommissionTempEntity> listUserCommissionEntity = query.getResultList();
            List<CommissionDTO> retorno = new ArrayList<CommissionDTO>();

            for (UserCommissionTempEntity userCommissionTempEntity : listUserCommissionEntity) {
                Hibernate.initialize(userCommissionTempEntity.getCommission());
                CommissionDTO dto = new CommissionDTO();

                dto.setId(CriptoUtilsOmega2.encrypt(userCommissionTempEntity.getCommission().getId()));
                dto.setDescription(userCommissionTempEntity.getCommission().getDescription());
                dto.setTemp(true);
                retorno.add(dto);
            }

            LOGGER.debug(" >> END findCommissionTempByUser ");
            return retorno;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
            
    }
    

    public UserCommissionDTO findCommissionTempById(Long selectedId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findCommissionTempById ");
            
            Query query = super.getEntityManager().createQuery(" SELECT uc " 
                    + " FROM UserCommissionTempEntity uc "
                    + " WHERE uc.commission.id = :selectedId ");        
            query.setParameter("selectedId", selectedId);
    
            
            UserCommissionTempEntity usserCommissionEntity = (UserCommissionTempEntity) query.getSingleResult();
            
            if(usserCommissionEntity != null){
                Hibernate.initialize(usserCommissionEntity.getCommission());
                UserCommissionDTO dto = new UserCommissionDTO();
    
                dto.setCommisonId(CriptoUtilsOmega2.encrypt(usserCommissionEntity.getCommission().getId()));
                dto.setDescription(usserCommissionEntity.getCommission().getDescription());
                dto.setTemp(true);
                
                return dto;
            }
    
            LOGGER.debug(" >> END findCommissionTempById ");
            return null;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
            
    }

    @SuppressWarnings("unchecked")
    public List<UserCommissionTempDTO> findCommissionTempBySalesmanWithFilters(Long salesmanId,Long financeTypeId, Long saleTypeId, Long selectedId, Long financetableId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findCommissionTempBySalesmanWithFilters ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT                                                                     ");
            sql.append(" UCT.USER_COMMISSION_TEMP_ID,                                               ");
            sql.append(" UCT.DT_INSERT,                                                             ");
            sql.append(" P.NAME_PERSON AS NAME_USER,                                                ");
            sql.append(" PSALE.NAME_PERSON AS NAME_SALESMAN,                                        ");
            sql.append(" CM.DESCRIPTION AS COMMISSION,                                              ");
            sql.append(" FT.DESCRIPTION AS FINANCE,                                                 ");
            sql.append(" ST.DESCRIPTION AS SALES,                                                   ");
            sql.append(" UCT.EXPIRE_DATE,                                                           ");
            sql.append(" UCT.PROPOSAL_ID                                                            ");
            sql.append(" FROM TB_USER_COMMISSION_TEMP UCT                                           ");
            sql.append(" INNER JOIN TB_COMMISSION CM ON CM.COMMISSION_ID = UCT.COMMISSION_ID        ");
            sql.append(" INNER JOIN TB_SALE_TYPE ST ON ST.SALE_TYPE_ID = UCT.SALE_TYPE_ID           ");
            sql.append(" INNER JOIN TB_FINANCE_TYPE FT ON FT.FINANCE_TYPE_ID = UCT.FINANCE_TYPE_ID  ");
            sql.append(" INNER JOIN TB_USER USALE ON USALE.USER_ID = UCT.SALESMAN_USER_ID           ");
            sql.append(" INNER JOIN TB_PERSON PSALE ON PSALE.USER_ID = USALE.USER_ID                ");
            sql.append(" INNER JOIN TB_USER U ON U.USER_ID = UCT.USER_ID                            ");
            sql.append(" INNER JOIN TB_PERSON P ON P.USER_ID = U.USER_ID                            ");
            sql.append(" WHERE UCT.SALESMAN_USER_ID = :salesmanId                                   ");
            sql.append(" AND FT.FINANCE_TYPE_ID = :financeTypeId                                    ");   
            sql.append(" AND ST.SALE_TYPE_ID = :saleTypeId                                          ");             
            sql.append(" AND UCT.EXPIRE_DATE > :getToNow                                            ");        
            sql.append(" ORDER BY UCT.DT_INSERT                                                     ");
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            
            query.setParameter("salesmanId", salesmanId);
            query.setParameter("financeTypeId", financeTypeId);
            query.setParameter("saleTypeId", saleTypeId);
            query.setParameter("getToNow", AppUtil.getToday().getTime());
            
            List<Object[]> rows = query.getResultList();
            List<UserCommissionTempDTO> retorno = new ArrayList<UserCommissionTempDTO>();

            for (Object[] row : rows) {
                UserCommissionTempDTO dto = new UserCommissionTempDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setInsertDate((Date) row[1]);
                dto.setUserName(row[2].toString());
                dto.setSalesmanName(row[3].toString());
                dto.setCommission(row[4].toString());
                dto.setFinanceType(row[5].toString());
                dto.setSaleType(row[6].toString());
                dto.setExpireDate((Date) row[7]);
                dto.setProposalId((Long) row[8]);

                if (selectedId != null && row[0].toString().equalsIgnoreCase(selectedId.toString())) {
                    dto.setSelected(true);
                }
                retorno.add(dto);
            }
            
            LOGGER.debug(" >> END findCommissionTempBySalesmanWithFilters ");
            return retorno;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
}
