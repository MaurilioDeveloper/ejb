package com.rci.omega2.ejb.dao;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.UserProfileRoleDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.RoleEntity;
import com.rci.omega2.entity.enumeration.RoleEnum;

public class RoleDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(UserProfileRoleDTO.class);

    public UserProfileRoleDTO findRoleByUser(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findRoleByUser ");
            
            UserProfileRoleDTO dto = new UserProfileRoleDTO();
            Query query = super.getEntityManager().createQuery(
                    "select r.id, r.description " + "from UserEntity u " + "join u.role r " + "where u.id = :userId ");
            query.setParameter("userId", userId);
            Object[] row = (Object[]) query.getSingleResult();
            dto.setId(CriptoUtilsOmega2.encrypt(row[0].toString()));
            dto.setDescription(row[1].toString());

            LOGGER.debug(" >> END findRoleByUser ");
            return dto;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }

    public String getRoleNameByUserId(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT getRoleNameByUserId ");
            
            Query query = super.getEntityManager()
                    .createQuery("select r.acronym from UserEntity u " + "join u.role r " + "where u.id = :userId");
            query.setParameter("userId", userId);

            String result = query.getSingleResult().toString();

            LOGGER.debug(" >> END getRoleNameByUserId ");
            return result;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }  
            
    }

    public RoleEntity findOne(Long id) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findOne ");
            
            RoleEntity temp = super.find(RoleEntity.class, id);
            
            LOGGER.debug(" >> END findOne ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    public boolean isAdminProfileByUser(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT isAdminProfileByUser ");
            
            String sql = "select count(*) from tb_user u " + "inner join tb_user_profile up on up.user_id = u.user_id "
                    + "inner join tb_profile_role pr on pr.profile_id = up.profile_id "
                    + "inner join tb_role r on r.role_id = pr.role_id " + "where u.user_id = :userId "
                    + "and r.role = :profileType";
            Query query = super.getEntityManager().createNativeQuery(sql);
            query.setParameter("userId", userId);
            query.setParameter("profileType", String.valueOf(RoleEnum.COMBO_CONCESSIONARIA.toString()));
            Integer count = Integer.valueOf(query.getResultList().get(0).toString());

            boolean temp = count > 0;
            
            LOGGER.debug(" >> END isAdminProfileByUser ");
            return temp;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}
