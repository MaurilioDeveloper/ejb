package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.bo.AddressBO;
import com.rci.omega2.ejb.dto.SaleTypeDTO;
import com.rci.omega2.ejb.dto.SalesTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.SaleTypeEntity;

public class SaleTypeDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(AddressBO.class);

    @SuppressWarnings("unchecked")
    public List<SaleTypeDTO> findSaleTypeActive() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findSaleTypeActive ");
            
            Query query = super.getEntityManager()
                    .createQuery(" SELECT st " + " FROM SaleTypeEntity AS st " + " WHERE active = :active ");
            query.setParameter("active", Boolean.TRUE);

            List<SaleTypeEntity> saleType = query.getResultList();

            List<SaleTypeDTO> temp = populateSaleTypeDTO(saleType);
            
            LOGGER.debug(" >> END findSaleTypeActive ");
            return temp;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    private List<SaleTypeDTO> populateSaleTypeDTO(List<SaleTypeEntity> saleType) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT populateSaleTypeDTO ");
            
            List<SaleTypeDTO> listDto = new ArrayList<SaleTypeDTO>();

            for (SaleTypeEntity entity : saleType) {
                SaleTypeDTO dto = new SaleTypeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
                dto.setDescription(entity.getDescription());
                listDto.add(dto);
            }

            LOGGER.debug(" >> END populateSaleTypeDTO ");
            return listDto;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } 
        
    }

    @SuppressWarnings("unchecked")
    public List<SaleTypeDTO> findSaleTypeActiveByUser(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findSaleTypeActiveByUser ");
            
            Query query = super.getEntityManager().createQuery("SELECT distinct uc.saleType "
                    + " FROM UserCommissionEntity AS uc WHERE uc.user.id=:userId and uc.saleType.active = :active ");

            query.setParameter("userId", userId);
            query.setParameter("active", Boolean.TRUE);

            List<SaleTypeEntity> saleType = query.getResultList();

            List<SaleTypeDTO> temp = populateSaleTypeDTO(saleType);
            
            LOGGER.debug(" >> END findSaleTypeActiveByUser ");
            return temp;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }  
        
    }

    @SuppressWarnings("unchecked")
    public List<SaleTypeDTO> findSaleTypeActiveByUserAndFinanceType(Long userId, Long financeTypeid) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findSaleTypeActiveByUserAndFinanceType ");
            
            Query query = super.getEntityManager().createQuery("SELECT distinct uc.saleType "
                    + " FROM UserCommissionEntity AS uc WHERE uc.user.id=:userId and uc.financeType.id=:financeTypeid and uc.saleType.active = :active ");
            query.setParameter("userId", userId);
            query.setParameter("financeTypeid", financeTypeid);
            query.setParameter("active", Boolean.TRUE);

            List<SaleTypeEntity> saleType = query.getResultList();
            
            List<SaleTypeDTO> temp = populateSaleTypeDTO(saleType);
            
            LOGGER.debug(" >> END findSaleTypeActiveByUserAndFinanceType ");
            return temp;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
            
    }
    
    @SuppressWarnings("unchecked")
    public List<SalesTypeDTO> findAllActive()throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findAllActive ");
            
            List<SalesTypeDTO> listResult = new ArrayList<SalesTypeDTO>();
            Query query = super.getEntityManager().createNativeQuery("select sale_type_id, description "
                    + "from tb_sale_type " + "where active = 1 " + "order by description ");
            List<Object[]> list = query.getResultList();

            for (Object[] type : list) {
                SalesTypeDTO dto = new SalesTypeDTO();
                String id = CriptoUtilsOmega2.encrypt(type[0].toString());
                dto.setId(id);
                dto.setValue(Long.valueOf(type[0].toString()));
                dto.setDescription(type[1].toString());
                listResult.add(dto);
            }

            LOGGER.debug(" >> END findAllActive ");
            return listResult;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
            
    }
    
}
