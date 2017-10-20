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
import com.rci.omega2.ejb.dao.LegalNatureDAO;
import com.rci.omega2.ejb.dto.LegalNatureDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.LegalNatureEntity;

@Stateless
public class LegalNatureBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(LegalNatureBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    public List<LegalNatureDTO> findAllLegalNature() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllLegalNature ");
            
            LegalNatureDAO dao = daoFactory(LegalNatureDAO.class);
            
            List<LegalNatureEntity> legal = dao.findAll(LegalNatureEntity.class);
            
            List<LegalNatureDTO> lsDto = new ArrayList<>();
            
            for (LegalNatureEntity item : legal){
                LegalNatureDTO dto = new LegalNatureDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                dto.setImportCode(item.getImportCode());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllLegalNature ");
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
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    public LegalNatureEntity findLegalNatureByImportCode(String importCode) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findLegalNatureByImportCode ");
            
            LegalNatureDAO dao = daoFactory(LegalNatureDAO.class);
            LegalNatureEntity legalNature = dao.findLegalNatureByImportCode(importCode);
            
            if(AppUtil.isNullOrEmpty(legalNature)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.NATUREZA_JURIDICA, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    legalNature = new LegalNatureEntity();
                    legalNature.setActive(Boolean.TRUE);
                    legalNature.setImportCode(domain.getCodigo());
                    legalNature.setDescription(domain.getDescricao());
                    dao.save(legalNature);
                }
            }
            
            LOGGER.debug(" >> END findLegalNatureByImportCode ");
            return legalNature;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
