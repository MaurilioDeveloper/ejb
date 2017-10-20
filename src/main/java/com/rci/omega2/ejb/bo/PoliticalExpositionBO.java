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
import com.rci.omega2.ejb.dao.PoliticalExpositionDAO;
import com.rci.omega2.ejb.dto.PoliticalExpositionDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.PoliticalExpositionEntity;

@Stateless
public class PoliticalExpositionBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(PoliticalExpositionBO.class);

    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    public List<PoliticalExpositionDTO> findAllPoliticalExposition() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllPoliticalExposition ");
            PoliticalExpositionDAO dao = daoFactory(PoliticalExpositionDAO.class);
            
            List<PoliticalExpositionEntity> political = dao.findAll(PoliticalExpositionEntity.class);
            
            List<PoliticalExpositionDTO> lsDto = new ArrayList<>();
            
            for(PoliticalExpositionEntity item : political){
                PoliticalExpositionDTO dto = new PoliticalExpositionDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            LOGGER.debug(" >> END findAllPoliticalExposition ");
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
     * @param politicalExpositionId
     * @return
     * @throws UnexpectedException
     */
    public PoliticalExpositionEntity findPoliticalExpositionById(Long politicalExpositionId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findPoliticalExpositionById ");
            PoliticalExpositionDAO dao = daoFactory(PoliticalExpositionDAO.class);
            PoliticalExpositionEntity politicalExposition = dao.find(PoliticalExpositionEntity.class, politicalExpositionId);
           
           
           if(AppUtil.isNullOrEmpty(politicalExposition)){
               //Busca dominio no Santander
               DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                   SantanderDomainEnum.PESSOA_EXPOSTA_POLITICAMENTE, String.valueOf(politicalExpositionId).trim());
               
               if(!AppUtil.isNullOrEmpty(domain)){
                   //Salva no banco
                   politicalExposition = new PoliticalExpositionEntity();
                   politicalExposition.setActive(Boolean.TRUE);
                   politicalExposition.setId(Long.valueOf(domain.getCodigo()));
                   politicalExposition.setDescription(domain.getDescricao());
                   dao.save(politicalExposition);
               }
           }
           LOGGER.debug(" >> END findPoliticalExpositionById ");
           return politicalExposition;
           
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }


}
