package com.rci.omega2.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.ProductCoeficientDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.JdbcUtils;
import com.rci.omega2.ejb.utils.ProductCoefficientEnum;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;

public class ProductCoefficientDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(ProductCoefficientDAO.class);
    
    @SuppressWarnings("unchecked")
    public List<ProductCoeficientDTO> findCoefficientAndTaxCoefficient(Long commissionId, Long productId, String personType, Integer term, Integer delayValue) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCoefficientAndTaxCoefficient ");
            
            Query query = super.getEntityManager().createQuery(
                    "select  pc.id, pc.coefficient, pc.taxCoefficient,pc.depositPercent " 
                            + "from ProductCoefficientEntity pc "
                            + "join pc.commission c " 
                            + "join pc.product p " 
                            + "where c.id = :commissionId "
                            + "and p.id = :productId " 
                            + "and pc.personType = :personType " 
                            + "and pc.term = :term "
                            + "and pc.delayValue = :delayValue " 
                            + "order by pc.depositPercent asc ");
            query.setParameter("commissionId", commissionId);
            query.setParameter("productId", productId);
            query.setParameter("personType", PersonTypeEnum.valueOf(personType));
            query.setParameter("term", term);
            query.setParameter("delayValue", delayValue);
            List<Object[]> result = query.getResultList();
            
            List<ProductCoeficientDTO> list = new ArrayList<ProductCoeficientDTO>();
            for (Object[] coef : result) {
                ProductCoeficientDTO productCoeficientDTO =  new ProductCoeficientDTO();
                productCoeficientDTO.setCoeffcientId(CriptoUtilsOmega2.encrypt(coef[0].toString()));
                productCoeficientDTO.setCoefficient((BigDecimal) coef[1]);
                productCoeficientDTO.setTaxCoefficient(JdbcUtils.adjustValue(coef[2]));
                productCoeficientDTO.setDepositPercent(JdbcUtils.adjustValue(coef[3]));
                list.add(productCoeficientDTO);
            }
            
            LOGGER.debug(" >> END findCoefficientAndTaxCoefficient ");
            return list;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } 
    }

    @SuppressWarnings("unchecked")
    public List<CommissionDTO> findCommissions(Long productId, String personType) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissions ");
            
            Query query = super.getEntityManager().createQuery(
                    "select c.id, c.description " 
                            + "from ProductCoefficientEntity pc " 
                            + "inner join pc.commission c "
                            + "inner join pc.product p " 
                            + "where p.id = :productId " 
                            + "and pc.personType = :personType "
                            + "group by c.id, c.description " 
                            + "order by c.description");
            query.setParameter("productId", productId);
            query.setParameter("personType", PersonTypeEnum.valueOf(personType));
            
            List<CommissionDTO> listDto = new ArrayList<CommissionDTO>();
            List<Object[]> list = query.getResultList();
            for (Object[] object : list) {
                CommissionDTO dto = new CommissionDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(object[0].toString()));
                dto.setDescription(object[1].toString());
                listDto.add(dto);
            }
            
            LOGGER.debug(" >> END findCommissions ");
            return listDto;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }
    
    @SuppressWarnings("unchecked")
    public List<CommissionDTO> findCommissionsFromTable(Long productId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionsFromTable ");
            
            Query query = super.getEntityManager().createQuery(
                    "select c.id, c.description " 
                            + "from ProductCoefficientEntity pc " 
                            + "inner join pc.commission c "
                            + "inner join pc.product p " 
                            + "where p.id = :productId " 
                            + "group by c.id, c.description " 
                            + "order by c.description");
            query.setParameter("productId", productId);
    
            List<CommissionDTO> listDto = new ArrayList<CommissionDTO>();
            List<Object[]> list = query.getResultList();
            
            for (Object[] object : list) {
                CommissionDTO dto = new CommissionDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(object[0].toString()));
                dto.setDescription(object[1].toString());
                listDto.add(dto);
            }
            
            LOGGER.debug(" >> END findCommissionsFromTable ");
            return listDto;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }
    
    public BigDecimal findMinDepositPercent(Long commissionId, Long productId, String personType, Integer term, Integer delayValue) throws UnexpectedException {
        @SuppressWarnings("unused")
        TreeMap<BigDecimal, HashMap<ProductCoefficientEnum, BigDecimal>> map = new TreeMap<BigDecimal, HashMap<ProductCoefficientEnum, BigDecimal>>();
        
        try {
            LOGGER.debug(" >> INIT findMinDepositPercent ");
            
            Query query = super.getEntityManager().createQuery(
                    "select min(pc.depositPercent)"
                            + "from ProductCoefficientEntity pc "
                            + "join pc.commission c " 
                            + "join pc.product p " 
                            + "where c.id = :commissionId "
                            + "and p.id = :productId " 
                            + "and pc.personType = :personType " 
                            + "and pc.term = :term "
                            + "and pc.delayValue = :delayValue " 
                            + "order by pc.depositPercent asc ",BigDecimal.class);
            query.setParameter("commissionId", commissionId);
            query.setParameter("productId", productId);
            query.setParameter("personType", PersonTypeEnum.valueOf(personType));
            query.setParameter("term", term);
            query.setParameter("delayValue", delayValue);
            
            BigDecimal temp = (BigDecimal) query.getSingleResult();
            
            LOGGER.debug(" >> END findMinDepositPercent ");
            return temp;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
            
    }
    
    @SuppressWarnings("unchecked")
    public List<Integer> findTerms(Long productId, PersonTypeEnum personType) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findTerms ");
            
            StringBuilder sql = new StringBuilder();
            sql.append(" select distinct pc.term ");
            sql.append(" from ProductCoefficientEntity pc ");
            sql.append(" inner join pc.product p ");
            sql.append(" where pc.personType = :personType ");
            sql.append(" and p.id = :productId ");
            sql.append(" order by pc.term ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("personType", personType);
            query.setParameter("productId", productId);
            
            List<Integer> list = query.getResultList();
                    
            LOGGER.debug(" >> END findTerms ");
            return list;            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }
    
    @SuppressWarnings("unchecked")
    public List<Integer> findDelayValues(Long productId, PersonTypeEnum personType) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDelayValues ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" select distinct pc.delayValue      ");
            sql.append(" from ProductCoefficientEntity pc   ");
            sql.append(" inner join pc.product p            ");
            sql.append(" where pc.personType = :personType  ");
            sql.append(" and p.id = :productId              ");
            sql.append(" order by pc.delayValue             ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("personType", personType);
            query.setParameter("productId", productId);
            
            List<Integer> list = query.getResultList();
    
            LOGGER.debug(" >> END findDelayValues ");
            return list;
       
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
            
    }
    
}
