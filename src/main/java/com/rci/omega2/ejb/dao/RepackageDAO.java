package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.rci.omega2.ejb.dto.RepackageDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.RepackagePlanEntity;

public class RepackageDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(RepackageDAO.class);

    @SuppressWarnings({ "unchecked"})
    public List<RepackageDTO> findByProduct(Long productId, Integer duration) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findByProduct ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(
                    " select r.repackage_id as idEntity, r.import_code as importCode, r.duration as duration from TB_REPACKAGE r ");
            sql.append(" where active = 1 ");
            sql.append(" and duration = :duration ");
            sql.append(
                    " and exists (select 1 from TB_REPACKAGE_PRODUCT rp where rp.repackage_id = r.repackage_id and rp.product_id = :productId) ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());

            query.setParameter("productId", productId);
            query.setParameter("duration", duration);

            List<Object[]> dossiers = query.getResultList();

            List<RepackageDTO> lista = new ArrayList<>();

            for (Object[] row : dossiers) {
                RepackageDTO dossier = new RepackageDTO();
                dossier.setId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[0].toString())));
                dossier.setImportCode(String.valueOf(row[1].toString()));
                dossier.setDuration(Integer.valueOf(row[2].toString()));

                lista.add(dossier);
            }

            LOGGER.debug(" >> END findByProduct ");
            return lista;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } 
            
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<RepackagePlanEntity> getRepackagePlanByPackageId(Long repackageId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT getRepackagePlanByPackageId ");
            
            Session session = this.getEntityManager().unwrap(Session.class);

            Criteria criteria = session.createCriteria(RepackagePlanEntity.class);
            criteria.add(Restrictions.eq("repackage.id", repackageId));
            criteria.addOrder(Order.asc("instalment"));

            List<RepackagePlanEntity> lista = criteria.list();

            LOGGER.debug(" >> END getRepackagePlanByPackageId ");
            return lista;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
}
