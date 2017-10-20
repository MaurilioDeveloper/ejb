package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.BusinessRelationshipTypeEntity;

public class BusinessRelationshipTypeDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(BusinessRelationshipTypeDAO.class);

    @SuppressWarnings("unchecked")
    public List<BusinessRelationshipTypeEntity> findAllBusinessRelationshipType() throws UnexpectedException {
        
        try {
            
            LOGGER.debug(" >> INIT findAllBusinessRelationshipType ");
            
            Query query = super.getEntityManager().createQuery(" FROM BusinessRelationshipTypeEntity ");

            List<BusinessRelationshipTypeEntity> business = query.getResultList();
            
            LOGGER.debug(" >> END findAllBusinessRelationshipType ");
            return business;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    public BusinessRelationshipTypeEntity findOne(Long id) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findOne ");
            
            BusinessRelationshipTypeEntity temp = super.find(BusinessRelationshipTypeEntity.class, id);
            
            LOGGER.debug(" >> END findOne ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    /**
     * 
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public BusinessRelationshipTypeEntity findBusinessRelationshipTypeByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findBusinessRelationshipTypeByImportCode ");
            
            StringBuilder sql = new  StringBuilder();
        
            sql.append("    SELECT br FROM BusinessRelationshipTypeEntity br       ");
            sql.append("    WHERE br.importCode = :importCode                      ");
        
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
            
            List<BusinessRelationshipTypeEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
        
            LOGGER.debug(" >> END findBusinessRelationshipTypeByImportCode ");
            return null;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
}
