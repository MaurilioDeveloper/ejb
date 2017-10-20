package com.rci.omega2.ejb.bo;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.DossierSubmitRoleDAO;
import com.rci.omega2.ejb.dto.DossierSubmitRoleDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

@Stateless
public class DossierSubmitRoleBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(DossierSubmitRoleBO.class);

    public List<DossierSubmitRoleDTO> findRoleStructure(Long structureId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findRoleStructure ");
            
            DossierSubmitRoleDAO dao = daoFactory(DossierSubmitRoleDAO.class);
            List<DossierSubmitRoleDTO> temp = dao.findRoleStructure(structureId);
            
            LOGGER.debug(" >> END findRoleStructure ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void saveSubmitRole(DossierSubmitRoleDTO submitRoleDto) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT saveSubmitRole ");
            
            DossierSubmitRoleDAO dao = daoFactory(DossierSubmitRoleDAO.class);
            Long userTypeId = Long.valueOf(CriptoUtilsOmega2.decrypt(submitRoleDto.getUserTypeId()));
            Long idStructure =  Long.valueOf(CriptoUtilsOmega2.decrypt(submitRoleDto.getIdStructure()));
            
            if(BigDecimal.ONE.equals(submitRoleDto.getAllowed())){                
                dao.saveSubmitRole(userTypeId, idStructure);
            }else if(BigDecimal.ZERO.equals(submitRoleDto.getAllowed())){
                dao.deleteSubmitRole(userTypeId, idStructure);
            }
            LOGGER.debug(" >> END saveSubmitRole ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
}
