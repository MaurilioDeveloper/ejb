package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.DocumentTypeEntity;

public class DocumentTypeDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(DocumentTypeDAO.class);

    @SuppressWarnings("unchecked")
    public DocumentTypeEntity findDocumentTypeByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDocumentTypeByImportCode ");
            StringBuilder sql = new  StringBuilder();
            
            sql.append(" SELECT dt FROM DocumentTypeEntity dt           ");
            sql.append("    WHERE dt.importCode = :importCode           ");
        
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
        
            List<DocumentTypeEntity > ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
        
            LOGGER.debug(" >> END findDocumentTypeByImportCode ");
            return null;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}
