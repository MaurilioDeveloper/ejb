package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.rci.omega2.ejb.dto.PersonDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.PersonEntity;
import com.rci.omega2.entity.enumeration.StructureTypeEnum;

public class PersonDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(PersonDAO.class);

    @SuppressWarnings("unchecked")
    public List<PersonDTO> findPersonSalesmanByDealership(Long structureId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findPersonSalesmanByDealership ");
            
            Query query = super.getEntityManager().createNativeQuery(" SELECT P.PERSON_ID, P.USER_ID, P.NAME_PERSON "
                    + " FROM TB_PERSON P " + " INNER JOIN TB_USER U ON U.USER_ID = P.USER_ID AND U.ACTIVE = :active "
                    + " INNER JOIN TB_USER_TYPE UT ON UT.USER_TYPE_ID = U.USER_TYPE_ID AND UT.USER_TYPE_ID = :typeSalesman "
                    + " INNER JOIN TB_USER_STRUCTURE US ON US.USER_ID = U.USER_ID "
                    + " WHERE US.STRUCTURE_ID = :structureId " + "ORDER BY P.NAME_PERSON ");

            query.setParameter("active", Boolean.TRUE);
            query.setParameter("structureId", structureId);
            query.setParameter("typeSalesman", 20L);

            List<Object[]> persons = query.getResultList();
            List<PersonDTO> listPerson = new ArrayList<PersonDTO>();

            for (Object[] row : persons) {
                PersonDTO dto = new PersonDTO();
                dto.setPersonId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setUserId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[1]).toString())));
                dto.setName((String) row[2]);
                listPerson.add(dto);
            }
            
            LOGGER.debug(" >> END findPersonSalesmanByDealership ");
            return listPerson;
            

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public List<PersonDTO> findPersonSalesmanByUser(Long userId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findPersonSalesmanByUser ");
            
            Query query = super.getEntityManager().createNativeQuery(" SELECT P.PERSON_ID, P.USER_ID, P.NAME_PERSON "
                    + " FROM TB_PERSON P " + " INNER JOIN TB_USER U ON U.USER_ID = P.USER_ID AND U.ACTIVE = :active "
                    + " INNER JOIN TB_USER_TYPE UT ON UT.USER_TYPE_ID = U.USER_TYPE_ID AND UT.USER_TYPE_ID = :typeSalesman "
                    + " INNER JOIN TB_USER_STRUCTURE US ON US.USER_ID = U.USER_ID "
                    + " WHERE US.STRUCTURE_ID IN (SELECT S.STRUCTURE_ID "
                    + "                           FROM TB_STRUCTURE S "
                    + "                           LEFT OUTER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S.STRUCTURE_ID AND US.USER_ID = :userId "
                    + "                           LEFT OUTER JOIN TB_DEALERSHIP D ON D.STRUCTURE_ID = S.STRUCTURE_ID "
                    + "                           WHERE S.STRUCTURE_TYPE = :structureType "
                    + "                           START WITH S.STRUCTURE_ID = US.STRUCTURE_ID "
                    + "                           CONNECT BY PRIOR S.STRUCTURE_ID = S.STRUCTURE_ID_PARENT) "
                    + " ORDER BY P.NAME_PERSON ");

            query.setParameter("active", Boolean.TRUE);
            query.setParameter("userId", userId);
            query.setParameter("typeSalesman", 20L);
            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());

            List<Object[]> persons = query.getResultList();

            List<PersonDTO> listPerson = new ArrayList<PersonDTO>();

            for (Object[] row : persons) {
                PersonDTO dto = new PersonDTO();
                dto.setPersonId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setUserId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[1]).toString())));
                dto.setName((String) row[2]);
                listPerson.add(dto);
            }

            LOGGER.debug(" >> END findPersonSalesmanByUser ");
            return listPerson;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings({ "deprecation" })
    public PersonEntity findPersonByUserId(Long userId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findPersonByUserId ");
            
            Session session = this.getEntityManager().unwrap(Session.class);
            Criteria criteria = session.createCriteria(PersonEntity.class);
            criteria.add(Restrictions.eq("user.id", userId));

            PersonEntity entity = (PersonEntity) criteria.uniqueResult();

            LOGGER.debug(" >> END findPersonByUserId ");
            return entity;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
}
