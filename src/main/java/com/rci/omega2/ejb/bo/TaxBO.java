package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.TaxDAO;
import com.rci.omega2.ejb.dto.TaxDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

@Stateless
public class TaxBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(TaxBO.class);

    public TaxDTO findTaxTC(String personType) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findTaxTC ");
            TaxDAO dao = daoFactory(TaxDAO.class);
            TaxDTO temp = dao.findTaxTC(personType);
            LOGGER.debug(" >> END findTaxTC ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public TaxDTO findTaxTR(String province, String gender, List<String> list) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findTaxTR ");
            TaxDAO dao = daoFactory(TaxDAO.class);
            if (list != null && !list.isEmpty()) {
                for (String specialTypeId : list) {
                    return dao.findTaxTR(CriptoUtilsOmega2.decrypt(province), gender, CriptoUtilsOmega2.decryptIdToLong(specialTypeId));

                }
            } else {
                return dao.findTaxTR(CriptoUtilsOmega2.decrypt(province), gender, null);
            }
            LOGGER.debug(" >> END findTaxTR ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        return null;
    }

    public TaxDTO findTaxTAB() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findTaxTAB ");
            TaxDAO dao = daoFactory(TaxDAO.class);
            TaxDTO temp = dao.findTaxTAB();
            LOGGER.debug(" >> END findTaxTAB ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
