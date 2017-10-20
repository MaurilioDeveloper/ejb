package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.ProvinceDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ProvinceEntity;

public class ProvinceDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(ProvinceDAO.class);

    @SuppressWarnings("unchecked")
    public List<ProvinceEntity> findAll() throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findAll ");
            
            Query query = super.getEntityManager()
                    .createQuery("select p from ProvinceEntity p order by p.description ");
            
            List<ProvinceEntity> temp = query.getResultList();
            
            LOGGER.debug(" >> END findAll ");
            return temp;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public ProvinceEntity findProvinceByAcronym(String acronym) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findProvinceByAcronym ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT pe FROM ProvinceEntity pe ");
            sql.append(" WHERE pe.acronym = :acronym      ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("acronym", acronym);

            List<ProvinceEntity> ls = query.getResultList();

            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findProvinceByAcronym ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public ProvinceDTO findProvinceByUser(Long userId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findProvinceByUser ");
            
            StringBuilder sbd = new StringBuilder();

            sbd.append("select p.province_id, p.description from tb_user u              ");
            sbd.append("inner join tb_user_structure us on us.user_id = u.user_id       ");
            sbd.append("inner join tb_dealership d on d.structure_id = us.structure_id  ");
            sbd.append("inner join tb_address a on a.address_id = d.address_id          ");
            sbd.append("inner join tb_province p on p.province_id = a.province_id       ");
            sbd.append("where u.user_id = :userId                                       ");
            sbd.append(" group by (p.province_id, p.description)                        ");

            String sql = sbd.toString();
            ProvinceDTO dto = new ProvinceDTO();

            Query query = super.getEntityManager().createNativeQuery(sql);
            query.setParameter("userId", userId);

            List<Object[]> listResult = query.getResultList();

            if (listResult.size() > 0) {
                Object[] province = listResult.get(0);
                if (province != null) {
                    String idEncript = CriptoUtilsOmega2.encrypt(province[0].toString());
                    dto.setId(idEncript);
                    dto.setDescription(province[1].toString());
                }
            }

            LOGGER.debug(" >> END findProvinceByUser ");
            return dto;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
