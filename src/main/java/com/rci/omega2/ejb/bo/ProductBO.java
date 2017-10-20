package com.rci.omega2.ejb.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.ProductDAO;
import com.rci.omega2.ejb.dao.ProductPromotionalDAO;
import com.rci.omega2.ejb.dto.ProductDTO;
import com.rci.omega2.ejb.dto.ProductFilterDTO;
import com.rci.omega2.ejb.dto.ProductPromotionalDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ProductEntity;
import com.rci.omega2.entity.ProductPromotionalEntity;

@Stateless
public class ProductBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(ProductBO.class);
    
    public List<ProductDTO> findProduct(ProductFilterDTO productFilter) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProduct ");
            ProductDAO dao = daoFactory(ProductDAO.class);
            ProductPromotionalDAO productPromotionalDAO = daoFactory(ProductPromotionalDAO.class);
            
            List<ProductDTO> lista = dao.findProduct(productFilter);
            for (ProductDTO dto : lista) {
                if(dto.getPromotional()){
                    List<ProductPromotionalEntity> promotions = productPromotionalDAO.searchProductPromotional(CriptoUtilsOmega2.decryptIdToLong(dto.getProductId()));
                    if(!AppUtil.isNullOrEmpty(promotions)){
                        dto.setListProductPromotional(new ArrayList<>());
                        
                        for (ProductPromotionalEntity entity : promotions) {
                            ProductPromotionalDTO item = new ProductPromotionalDTO();
                            
                            item.setCommissionId(CriptoUtilsOmega2.encrypt(entity.getCommission().getId()));
                            item.setDelayValue(entity.getDelayValue());
                            item.setTerm(entity.getTerm());
                            item.setDepositPercent(entity.getDepositPercent());
                            item.setMainSource(entity.getMainSource());
                            
                            if(!AppUtil.isNullOrEmpty(entity.getRepackage())){
                                item.setRepackageId(CriptoUtilsOmega2.encrypt(entity.getRepackage().getId()));
                            }
                            
                            dto.getListProductPromotional().add(item);
                        }
                        
                    }
                }
            }
            
            return lista;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public Map<String,BigDecimal> findMinValueMaxPercentAndValueEntraceFromProductById(Long productid) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findMinValueMaxPercentAndValueEntraceFromProductById ");
            
            ProductDAO dao = daoFactory(ProductDAO.class);
            ProductEntity product = dao.find(ProductEntity.class, productid);
            Map<String,BigDecimal> mapReturn  = new HashMap<String, BigDecimal>();
            mapReturn.put("MinFinancedAmount", product.getMinFinancedAmount());
            mapReturn.put("MaxFinancedAmountPercent", product.getMaxFinancedAmountPercent());
            mapReturn.put("MinDepositPercent", product.getMinDepositPercent());
            mapReturn.put("MaxDepositPercent", product.getMaxDepositPercent());
            
            LOGGER.debug(" >> END findMinValueMaxPercentAndValueEntraceFromProductById ");
            return mapReturn;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    /**
     * 
     * @param id
     * @param createdDate
     * @return
     * @throws UnexpectedException
     */
    public ProductEntity findProductByImportCodeAndPeriod(String importCode, Date createdDate) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProductByImportCodeAndPeriod ");
            
            ProductDAO dao = daoFactory(ProductDAO.class);
            ProductEntity temp = dao.findProductByImportCodeAndPeriod(importCode, createdDate);
            
            LOGGER.debug(" >> END findProductByImportCodeAndPeriod ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    

    public ProductEntity findProductEntity(ProductDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProductEntity ");
            
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getProductId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getProductId());
            
            ProductDAO dao = daoFactory(ProductDAO.class);
            ProductEntity temp = dao.find(ProductEntity.class, id);
            
            LOGGER.debug(" >> END findProductEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
