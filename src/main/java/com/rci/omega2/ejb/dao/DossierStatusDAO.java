package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.DossierStatusDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.DossierStatusEntity;

public class DossierStatusDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(DossierStatusDAO.class);
    
    public List<DossierStatusDTO> findDossierStatus() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDossierStatus ");
            List<DossierStatusEntity> dossierStatus = super.findAll(DossierStatusEntity.class);     
            
            List<DossierStatusDTO> temp = populateDossierStatusDTO(dossierStatus);
            
            LOGGER.debug(" >> END findDossierStatus ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    private List<DossierStatusDTO> populateDossierStatusDTO(List<DossierStatusEntity> dossierStatus) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT populateDossierStatusDTO ");
            
            List<DossierStatusDTO> listDto = new ArrayList<DossierStatusDTO>();
            
            for(DossierStatusEntity ds : dossierStatus){
                DossierStatusDTO dsDto = new DossierStatusDTO();
                dsDto.setDossierStatusId(CriptoUtilsOmega2.encrypt(ds.getId()));
                dsDto.setDescription(ds.getDescription());
                listDto.add(dsDto);
            }
        
            LOGGER.debug(" >> END populateDossierStatusDTO ");
            return listDto;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
}
