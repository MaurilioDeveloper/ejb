package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.OccupationEntity;

public class OccupationDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(OccupationDAO.class);

    @SuppressWarnings("unchecked")
    public OccupationEntity findOccupationByImportCode(String importCode) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findOccupationByImportCode ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("    SELECT oc FROM OccupationEntity oc             ");
            sql.append("    WHERE oc.importCode = :importCode              ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);

            List<OccupationEntity> ls = query.getResultList();

            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findOccupationByImportCode ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
