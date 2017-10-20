package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.StructureDTO;
import com.rci.omega2.ejb.exception.ForbiddenException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.StructureEntity;
import com.rci.omega2.entity.UserEntity;

@Stateless
public class SecurityBO extends BaseBO {
    private static final Logger LOGGER = LogManager.getLogger(SecurityBO.class);
    
    @EJB
    private StructureBO structureBO;
    
    public void verifyPerimissionDataByDossier(Long dossierId, UserEntity user) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT verifyPerimissionDataByDossier ");
            StructureEntity structure = structureBO.findByDossier(dossierId);
            verifyPerimissionData(structure, user);
            LOGGER.debug(" >> END verifyPerimissionDataByDossier ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void verifyPerimissionData(StructureEntity structure, UserEntity user) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT verifyPerimissionData ");
            List<StructureDTO> listStructure = structureBO.findStructureDealershipByUser(user.getId(), user.getUserType().getRegionalView());
            for (StructureDTO structureDTO : listStructure) {
                Long structureId = Long.valueOf(CriptoUtilsOmega2.decrypt(structureDTO.getStructureId()));
                if(structure.getId().equals(structureId)){
                    return;
                }
            }
            LOGGER.debug(" >> END verifyPerimissionData ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
        throw new ForbiddenException();
    }
    
}
