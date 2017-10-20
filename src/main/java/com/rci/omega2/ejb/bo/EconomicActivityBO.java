package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.EconomicActivityDAO;
import com.rci.omega2.ejb.dto.EconomicAtivityDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.SantanderServicesUtils;
import com.rci.omega2.entity.EconomicActivityEntity;

@Stateless
public class EconomicActivityBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(EconomicActivityBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    public List<EconomicAtivityDTO> findAllDocumentType() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllDocumentType ");
            
            EconomicActivityDAO dao = daoFactory(EconomicActivityDAO.class);
            
            List<EconomicActivityEntity> economic = dao.findAll(EconomicActivityEntity.class);

            List<EconomicAtivityDTO> lsDto = new ArrayList<>();

            for (EconomicActivityEntity item : economic) {
                EconomicAtivityDTO dto = new EconomicAtivityDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllDocumentType ");
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
    public EconomicActivityEntity findEconomicActivityByImportCode(String importCode) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findEconomicActivityByImportCode ");
            
            EconomicActivityDAO dao = daoFactory(EconomicActivityDAO.class);
            EconomicActivityEntity economicActivity = dao.findEconomicActivityByImportCode(importCode);
            
            if(AppUtil.isNullOrEmpty(economicActivity)){
                //Busca dominio no Santander
                DominioDTO domain = santanderDomainBO
                        .findSantanderEconomicActivity(SantanderServicesUtils.extractFillZeroToLeft(importCode, 3));
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    economicActivity = new EconomicActivityEntity();
                    economicActivity.setActive(Boolean.TRUE);
                    economicActivity.setImportCode(SantanderServicesUtils.extractFillZeroToLeft(domain.getCodigo(),3));
                    economicActivity.setDescription(domain.getDescricao());
                    dao.save(economicActivity);
                }
            }
            
            LOGGER.debug(" >> END findEconomicActivityByImportCode ");
            return economicActivity;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    

}
