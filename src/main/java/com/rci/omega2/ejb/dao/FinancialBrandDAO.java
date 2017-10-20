package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.FinancialBrandDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.FinancialBrandEntity;

public class FinancialBrandDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(FinancialBrandDAO.class);

    
    @SuppressWarnings("unchecked")
    public List<FinancialBrandDTO> findAllFinancialBrands() throws UnexpectedException{

        try {
            LOGGER.debug(" >> INIT findAllFinancialBrands ");
            
            List<FinancialBrandDTO> result = new ArrayList<FinancialBrandDTO>();

            Query query = super.getEntityManager().createQuery(" select r.id, r.importCodeActor, r.description, promotionalImage.id "
                    + " from FinancialBrandEntity r ");

            List<Object[]> functions = query.getResultList();

            for (Object[] function : functions) {
                FinancialBrandDTO dto = new FinancialBrandDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(function[0].toString()));
                dto.setImportCodeActor(function[1].toString());
                dto.setDescription(function[2].toString());
                dto.setFileId(function[3]!=null? CriptoUtilsOmega2.encrypt(function[3].toString()): null); 

                result.add(dto);
            }

            LOGGER.debug(" >> END findAllFinancialBrands ");
            return result;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public Set<FinancialBrandEntity> findFinancialBrandsById(Set<Long> idsFinancialBrands) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findFinancialBrandsById ");
            
            Query query = super.getEntityManager().createQuery("FROM FinancialBrandEntity item WHERE item.id IN (:ids)");
            query.setParameter("ids", idsFinancialBrands);
            Set<FinancialBrandEntity> result = new HashSet<FinancialBrandEntity>(query.getResultList());
            
            if(result == null || result.isEmpty()){
                return null;
            }
                  
            LOGGER.debug(" >> END findFinancialBrandsById ");
            return result;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }

    @SuppressWarnings("unchecked")
    public Set<FinancialBrandEntity> findFinancialBrandById(String id) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findFinancialBrandById ");
            
            Query query = super.getEntityManager().createNativeQuery(" select ta.* from TB_FINANCIAL_BRAND ta "
                    + " join TB_NOTICE_FINANCIAL_BRAND tb "
                    + " on ta.FINANCIAL_BRAND_ID = tb.FINANCIAL_BRAND_ID "
                    + " where tb.NOTICE_ID = ( :id ) ");
            query.setParameter("id", id);
            
            List<FinancialBrandEntity> ls = (List<FinancialBrandEntity>) query.getResultList();
    
            if(ls == null || ls.isEmpty()){
                return null;
            }
            
            Set<FinancialBrandEntity> foo = new HashSet<FinancialBrandEntity>(ls); 
            
            LOGGER.debug(" >> END findFinancialBrandById ");
            return foo;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
        
    }

    @SuppressWarnings("unchecked")
    public List<FinancialBrandDTO> findFinancialBrandsBynoticeId(String idNotice) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findFinancialBrandsBynoticeId ");
            
            Query query = super.getEntityManager().createNativeQuery(" SELECT FNB.FINANCIAL_BRAND_ID, FNB.DESCRIPTION "
                    + " FROM TB_FINANCIAL_BRAND FNB "
                    + " JOIN TB_NOTICE_FINANCIAL_BRAND NFNB "
                    + " ON FNB.FINANCIAL_BRAND_ID = NFNB.FINANCIAL_BRAND_ID "
                    + " JOIN TB_NOTICE NTC "
                    + " ON NFNB.NOTICE_ID = NTC.NOTICE_ID "
                    + " WHERE NTC.NOTICE_ID = :id ");
            query.setParameter("id", idNotice);

            List<FinancialBrandDTO> result = new ArrayList<>();

            List<Object[]> functions = query.getResultList();

            for (Object[] function : functions) {
                FinancialBrandDTO dto = new FinancialBrandDTO();

                dto.setId(CriptoUtilsOmega2.encrypt(function[0].toString()));
                dto.setDescription(function[1].toString());            

                result.add(dto);
            }

            LOGGER.debug(" >> END findFinancialBrandsBynoticeId ");
            return result;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     

    }
    
    public FinancialBrandEntity findByStructureId(Long structureId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findByStructureId ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("    select s.financialBrand from StructureEntity s  ");
            sql.append("    where s.id = :structureId                       ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("structureId", structureId);
            
            FinancialBrandEntity financialBrand = (FinancialBrandEntity)query.getResultList().get(0);
            
            LOGGER.debug(" >> END findByStructureId ");
            return financialBrand;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
            
    }
    
}
