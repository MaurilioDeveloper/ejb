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
import com.rci.omega2.ejb.dao.OccupationDAO;
import com.rci.omega2.ejb.dto.OccupationDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.SantanderServicesUtils;
import com.rci.omega2.entity.OccupationEntity;

@Stateless
public class OccupationBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(OccupationBO.class);

    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    public List<OccupationDTO> findAllOccupation() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllOccupation ");
            
            OccupationDAO dao = daoFactory(OccupationDAO.class);
            
            List<OccupationEntity> occupation = dao.findAll(OccupationEntity.class);
            
            List<OccupationDTO> lsDto = new ArrayList<>();
            
            for(OccupationEntity item : occupation){
                OccupationDTO dto = new OccupationDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                dto.setImportCode(item.getImportCode());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllOccupation ");
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
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    public OccupationEntity findOccupationByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findOccupationByImportCode ");
            OccupationDAO dao = daoFactory(OccupationDAO.class);
            OccupationEntity occupation = dao.findOccupationByImportCode(importCode);
            
            if(AppUtil.isNullOrEmpty(occupation)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.OCUPACAO, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    occupation = new OccupationEntity();
                    occupation.setActive(Boolean.TRUE);
                    occupation.setImportCode(SantanderServicesUtils.extractFillZeroToLeft(domain.getCodigo(),3));
                    occupation.setDescription(domain.getDescricao());
                    dao.save(occupation);
                }
            }
            
            LOGGER.debug(" >> END findOccupationByImportCode ");
            return occupation;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
