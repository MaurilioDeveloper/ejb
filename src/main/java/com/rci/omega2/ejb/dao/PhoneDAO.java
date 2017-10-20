package com.rci.omega2.ejb.dao;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CustomerEntity;
import com.rci.omega2.entity.GuarantorEntity;

public class PhoneDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(PhoneDAO.class);

    /**
     * 
     * @param customer
     * @throws UnexpectedException
     */
    public void deleteAllPhoneCustomer(CustomerEntity customer) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT deleteAllPhoneCustomer ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("    DELETE FROM PhoneEntity ph              ");
            sql.append("    WHERE ph.customer.id = :customerId      ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("customerId", customer.getId());

            query.executeUpdate();

            super.getEntityManager().flush();

            LOGGER.debug(" >> END deleteAllPhoneCustomer ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    /**
     * 
     * @param guarantor
     * @throws UnexpectedException
     */
    public void deleteAllPhoneGuarantor(GuarantorEntity guarantor) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT deleteAllPhoneGuarantor ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("    DELETE FROM PhoneEntity ph               ");
            sql.append("    WHERE ph.guarantor.id = :guarantorId     ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("guarantorId", guarantor.getId());

            query.executeUpdate();

            super.getEntityManager().flush();

            LOGGER.debug(" >> END deleteAllPhoneGuarantor ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
