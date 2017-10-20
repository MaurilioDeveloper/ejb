package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.EmissionOrganismDAO;
import com.rci.omega2.ejb.dto.EmissionOrganismDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.EmissionOrganismEntity;

@Stateless
public class EmissionOrganismBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(EmissionOrganismBO.class);

    public List<EmissionOrganismDTO> findAllEmissionOrganism() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllEmissionOrganism ");
            EmissionOrganismDAO dao = daoFactory(EmissionOrganismDAO.class);
            
            List<EmissionOrganismEntity> emission = dao.findAll(EmissionOrganismEntity.class);
            
            List<EmissionOrganismDTO> lsDto = new ArrayList<>();
            
            for(EmissionOrganismEntity item : emission){
                EmissionOrganismDTO dto = new EmissionOrganismDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllEmissionOrganism ");
            return lsDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    
    /**
     * 
     * @param acronym
     * @return
     * @throws UnexpectedException
     */
    public EmissionOrganismEntity findEmissionOrganismByAcronym(String acronym) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findEmissionOrganismByAcronym ");
            
            EmissionOrganismDAO dao = daoFactory(EmissionOrganismDAO.class);
            EmissionOrganismEntity emissionOrganism = dao.findEmissionOrganismByAcronym(acronym.toUpperCase().trim());
            
            
            if(AppUtil.isNullOrEmpty(emissionOrganism)){
                //Salva no banco
                emissionOrganism = new EmissionOrganismEntity();
                emissionOrganism.setActive(Boolean.TRUE);
                emissionOrganism.setImportCode(acronym.toUpperCase().toString() );
                emissionOrganism.setDescription(acronym.toUpperCase().toString());
                dao.save(emissionOrganism);
            }
            
            LOGGER.debug(" >> END findEmissionOrganismByAcronym ");
            return emissionOrganism;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
}
