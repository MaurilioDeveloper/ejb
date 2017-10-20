package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.UserProfileFunctionDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

public class FunctionDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(FunctionDAO.class);

    @SuppressWarnings("unchecked")
    public List<UserProfileFunctionDTO> findFunctionsByRole(Long roleId)throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findFunctionsByRole ");
            
            List<UserProfileFunctionDTO> result = new ArrayList<UserProfileFunctionDTO>();
            
            Query query = super.getEntityManager().createQuery("select f.id, f.description, f.url "
                    + "from RoleEntity r "
                    + "join r.listFunction f "
                    + "where r.id = :roleId ");
            query.setParameter("roleId", roleId);
            
            List<Object[]> functions = query.getResultList();
            
            for (Object[] function : functions) {
                UserProfileFunctionDTO dto = new UserProfileFunctionDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(function[0].toString()));
                dto.setDescription(function[1].toString());
                dto.setUrl(function[2].toString());
                result.add(dto);
            }
            
            LOGGER.debug(" >> END findFunctionsByRole ");
            return result;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
            
    }

}
