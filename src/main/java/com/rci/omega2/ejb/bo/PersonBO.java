package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.PersonDAO;
import com.rci.omega2.ejb.dto.PersonDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.PersonEntity;

@Stateless
public class PersonBO extends BaseBO{
    
 private static final Logger LOGGER = LogManager.getLogger(PersonBO.class);
    
    public List<PersonDTO> findPersonSalesmanByDealership(Long structureId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findPersonSalesmanByDealership ");
            PersonDAO dao = daoFactory(PersonDAO.class);
            List<PersonDTO> temp = dao.findPersonSalesmanByDealership(structureId);
            LOGGER.debug(" >> END findPersonSalesmanByDealership ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public List<PersonDTO> findPersonSalesmanByUser(Long userId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findPersonSalesmanByUser ");
            PersonDAO dao = daoFactory(PersonDAO.class);
            List<PersonDTO> temp = dao.findPersonSalesmanByUser(userId);
            LOGGER.debug(" >> END findPersonSalesmanByUser ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public PersonEntity findById(Long idPerson) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT findById ");
            PersonDAO dao = daoFactory(PersonDAO.class);
            PersonEntity temp = dao.find(PersonEntity.class, idPerson);
            LOGGER.debug(" >> END findById ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
