package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ProfessionEntity;

public class ProfessionDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(ProfessionDAO.class);

    @SuppressWarnings("unchecked")
    public ProfessionEntity findProfessionByDescription(String description) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findProfessionByDescription ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT ps FROM ProfessionEntity ps             ");
            sql.append("    WHERE ps.description = :description         ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("description", description);

            List<ProfessionEntity> ls = query.getResultList();

            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findProfessionByDescription ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
