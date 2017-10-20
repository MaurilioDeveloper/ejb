package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.ColorDAO;
import com.rci.omega2.ejb.dto.ColorDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ColorEntity;

@Stateless
public class ColorBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(ColorBO.class);

    public List<ColorDTO> findAllColor() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAllColor ");
            ColorDAO dao = daoFactory(ColorDAO.class);
            
            List<ColorEntity> legal = dao.findAll(ColorEntity.class);
            
            List<ColorDTO> lsDto = new ArrayList<>();
            
            for (ColorEntity item : legal){
                ColorDTO dto = new ColorDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllColor ");
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
     * @param description
     * @return
     * @throws UnexpectedException
     */
    public ColorEntity findColorByDescription(String description) throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT findColorByDescription ");
            ColorDAO dao = daoFactory(ColorDAO.class);
            ColorEntity color = dao.findColorByDescription(description.toUpperCase().toString());
            
            if(AppUtil.isNullOrEmpty(color)){
                //Salva no banco
                color = new ColorEntity();
                color.setDescription(description.toUpperCase().toString());
                dao.save(color);
            }
            
            LOGGER.debug(" >> END findColorByDescription ");
            return color;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
