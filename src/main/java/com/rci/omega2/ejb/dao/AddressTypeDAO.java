package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.AddressTypeEntity;

public class AddressTypeDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(AddressTypeDAO.class);
    
    @SuppressWarnings("unchecked")
    public List<AddressTypeEntity> findAllMailingAddressType() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAllMailingAddressType ");

            Query query = super.getEntityManager().createQuery(" FROM AddressTypeEntity ");

            List<AddressTypeEntity> address = query.getResultList();
            
            LOGGER.debug(" >> END findAllMailingAddressType ");
            return address;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    
    @SuppressWarnings("unchecked")
    public AddressTypeEntity findAddressTypeByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAddressTypeByImportCode ");
            
            StringBuilder sql = new  StringBuilder();

            sql.append("    SELECT at FROM AddressTypeEntity at         ");
            sql.append("    WHERE at.importCode = :importCode           ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
        
            List<AddressTypeEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
        
            LOGGER.debug(" >> END findAddressTypeByImportCode ");
            return null;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
}
