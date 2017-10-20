package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.ProfessionDAO;
import com.rci.omega2.ejb.dto.ProfessionDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ProfessionEntity;

@Stateless
public class ProfessionBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(ProfessionBO.class);

    public List<ProfessionDTO> findAllProfession() throws UnexpectedException {
        try {
            
            LOGGER.debug(" >> INIT findAllProfession ");
            ProfessionDAO dao = daoFactory(ProfessionDAO.class);
            
            List<ProfessionEntity> profession = dao.findAll(ProfessionEntity.class);

            List<ProfessionDTO> lsDto = new ArrayList<>();

            for (ProfessionEntity item : profession) {
                ProfessionDTO dto = new ProfessionDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllProfession ");
            return lsDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    
    /**
     * Add Dominio caso não tenha
     * @param description
     * @return
     * @throws UnexpectedException
     */
    public ProfessionEntity findProfessionByDescription(String description) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProfessionByDescription ");
            
            ProfessionDAO dao = daoFactory(ProfessionDAO.class);
            ProfessionEntity profession = dao.findProfessionByDescription(description.toUpperCase().toString());
            
            if(AppUtil.isNullOrEmpty(profession)){
                //Salva no banco
                profession = new ProfessionEntity();
                profession.setActive(Boolean.TRUE);
                profession.setDescription(description.toUpperCase().toString());
                dao.save(profession);
            }
            
            LOGGER.debug(" >> END findProfessionByDescription ");
            return profession;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    
}
