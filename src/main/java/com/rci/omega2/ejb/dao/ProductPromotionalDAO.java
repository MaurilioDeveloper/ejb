package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ProductPromotionalEntity;

public class ProductPromotionalDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(ProductPromotionalDAO.class);

    @SuppressWarnings("unchecked")
    public List<ProductPromotionalEntity> searchProductPromotional(Long productId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT searchProductPromotional ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" select pe from ProductPromotionalEntity pe ");
            sql.append(" left join fetch pe.commission cm           ");
            sql.append(" left join fetch pe.repackage rp            ");
            sql.append(" where pe.product.id = :productId           ");

            Query query = super.getEntityManager().createQuery(sql.toString());

            query.setParameter("productId", productId);

            List<ProductPromotionalEntity> ls = query.getResultList();
            
            LOGGER.debug(" >> END searchProductPromotional ");
            return ls;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } 
    }
    
    @SuppressWarnings("unchecked")
    public List<CommissionDTO> findPromotionalCommissions(Long productId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findPromotionalCommissions ");
            
            Query query = super.getEntityManager().createQuery(
                            "select c.id, c.description " 
                            + "from ProductPromotionalEntity pc " 
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
            
            LOGGER.debug(" >> END findPromotionalCommissions ");
            return listDto;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
    }

}
