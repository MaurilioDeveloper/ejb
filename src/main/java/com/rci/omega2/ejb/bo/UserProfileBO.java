package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.FunctionDAO;
import com.rci.omega2.ejb.dao.RoleDAO;
import com.rci.omega2.ejb.dao.UserDAO;
import com.rci.omega2.ejb.dto.UserProfileFunctionDTO;
import com.rci.omega2.ejb.dto.UserProfileRoleDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.UserEntity;

@Stateless
public class UserProfileBO extends BaseBO{
    private static final Logger LOGGER = LogManager.getLogger(UserProfileBO.class);
    
    public UserProfileRoleDTO findRoleByUser(Long userId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findRoleByUser ");
            RoleDAO dao = daoFactory(RoleDAO.class);
            UserProfileRoleDTO dto = dao.findRoleByUser(userId);
            LOGGER.debug(" >> END findRoleByUser ");
            return dto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public List<UserProfileFunctionDTO> findFunctionsByRole(Long roleId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findFunctionsByRole ");
            FunctionDAO dao = daoFactory(FunctionDAO.class);
            List<UserProfileFunctionDTO> listReturn = dao.findFunctionsByRole(roleId);
            LOGGER.debug(" >> END findFunctionsByRole ");
            return listReturn;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public boolean isAdminProfileByUser(Long userId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT isAdminProfileByUser ");
            RoleDAO dao = daoFactory(RoleDAO.class);
            boolean temp = dao.isAdminProfileByUser(userId);
            LOGGER.debug(" >> END isAdminProfileByUser ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public Integer findProposalQuantity(Long userId)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProposalQuantity ");
            UserDAO dao = daoFactory(UserDAO.class);
            Integer temp = dao.findProposalQuantity(userId);
            
            LOGGER.debug(" >> END findProposalQuantity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void updateProposalQuantity(Long userId, Integer proposalQuantity)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT updateProposalQuantity ");
            UserDAO dao = daoFactory(UserDAO.class);
            UserEntity entity = dao.find(UserEntity.class, userId);
            entity.setProposalQuantity(proposalQuantity);
            dao.update(entity);
            LOGGER.debug(" >> END updateProposalQuantity ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
