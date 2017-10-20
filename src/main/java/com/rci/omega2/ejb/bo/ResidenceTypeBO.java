package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.common.util.SantanderDomainEnum;
import com.rci.omega2.ejb.dao.ResidenceTypeDAO;
import com.rci.omega2.ejb.dto.ResidenceTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ResidenceTypeEntity;

@Stateless
public class ResidenceTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(ResidenceTypeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<ResidenceTypeDTO> findAllTypeResidence() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllTypeResidence ");
            
            ResidenceTypeDAO dao = daoFactory(ResidenceTypeDAO.class);
            
            List<ResidenceTypeEntity> residence = dao.findAll(ResidenceTypeEntity.class);

            List<ResidenceTypeDTO> lsDto = new ArrayList<>();

            for (ResidenceTypeEntity item : residence) {
                ResidenceTypeDTO dto = new ResidenceTypeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }

            LOGGER.debug(" >> END findAllTypeResidence ");
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
     * @param residenceTypeId
     * @return
     * @throws UnexpectedException
     */
    public ResidenceTypeEntity findResidenceTypeById(Long residenceTypeId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findResidenceTypeById ");
            
            ResidenceTypeDAO dao = daoFactory(ResidenceTypeDAO.class);
            ResidenceTypeEntity residenceType = dao.find(ResidenceTypeEntity.class, residenceTypeId);
            
            if(AppUtil.isNullOrEmpty(residenceType)){
                //Busca dominio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.TIPO_RESIDENCIA, String.valueOf(residenceTypeId).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    residenceType = new ResidenceTypeEntity();
                    residenceType.setActive(Boolean.TRUE);
                    residenceType.setId(Long.valueOf(domain.getCodigo()));
                    residenceType.setDescription(domain.getDescricao());
                    dao.save(residenceType);
                }
            }
            
            LOGGER.debug(" >> END findResidenceTypeById ");
            return residenceType;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
