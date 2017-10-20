package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.ParamDAO;
import com.rci.omega2.ejb.dto.ParamDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ParamEntity;
import com.rci.omega2.entity.util.enumation.ParamEnum;

@Stateless
public class ParamBO extends BaseBO{

    private static final Logger LOGGER = LogManager.getLogger(ParamBO.class);
    
    public ParamDTO findParamDefaultFinancialType() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findParamDefaultFinancialType ");
            
            ParamDAO dao = daoFactory(ParamDAO.class);
            ParamDTO temp = converterEntityToDTO(dao.find(ParamEnum.TIPO_FINANCIAMENTO_DEFAULT));
            
            LOGGER.debug(" >> END findParamDefaultFinancialType ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * 
     * @param param
     * @return
     * @throws UnexpectedException
     */
    public ParamEntity findParam(ParamEnum param) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findParam ");
            
            ParamDAO dao = daoFactory(ParamDAO.class);
            ParamEntity temp = dao.find(param);
            
            LOGGER.debug(" >> END findParam ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
     
    
    
    
    public void saveParamProposalUpdate(ParamDTO dto) throws UnexpectedException{
        
        LOGGER.debug(" >> INIT saveParamProposalUpdate ");
        
        ParamDAO dao = daoFactory(ParamDAO.class);
        Long paramId = Long.valueOf(CriptoUtilsOmega2.decrypt(dto.getParamId()));
        ParamEntity entity = new ParamEntity();//dao.find(ParamEnum.LATEST_UPDATE_BUTTON_PARAM_CODE);
        if(entity.getId().equals(paramId) && entity.getParamBoolean()!=dto.getParamBoolean()){
            try {
                entity.getParamBoolean();
                dao.update(entity);
            } catch (UnexpectedException e) {
                throw e;
            } catch (Exception e) {
                LOGGER.error(AppConstants.ERROR, e);
                throw new UnexpectedException(e);
            }
        }
        
        LOGGER.debug(" >> END saveParamProposalUpdate ");
    }
    
    private ParamDTO converterEntityToDTO(ParamEntity entity) throws UnexpectedException{
        
        LOGGER.debug(" >> INIT converterEntityToDTO ");
        
        ParamDTO dto = new ParamDTO();
        dto.setParamId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        dto.setParamBoolean(entity.getParamBoolean());
        dto.setParamValue(entity.getParamValue());
        dto.setParamAmount(entity.getParamAmount() );
        
        LOGGER.debug(" >> END converterEntityToDTO ");
        return dto;
    }
    
}
