package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ProofIncomeTypeEntity;

public class ProofIncomeTypeDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(ProofIncomeTypeDAO.class);

    /**
     * 
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public ProofIncomeTypeEntity findProofIncomeTypeByImportCode(String importCode) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findProofIncomeTypeByImportCode ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT pi  FROM ProofIncomeTypeEntity pi       ");
            sql.append("    WHERE pi.importCode = :importCode           ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);

            List<ProofIncomeTypeEntity> ls = query.getResultList();
            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findProofIncomeTypeByImportCode ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}
