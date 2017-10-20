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
import com.rci.omega2.ejb.dao.IndustrialSegmentDAO;
import com.rci.omega2.ejb.dto.IndustrialSegmentDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.IndustrialSegmentEntity;

@Stateless
public class IndustrialSegmentBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(IndustrialSegmentBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<IndustrialSegmentDTO> findAllIndustrialSegment() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllIndustrialSegment ");
            
            IndustrialSegmentDAO dao = daoFactory(IndustrialSegmentDAO.class);

            List<IndustrialSegmentEntity> industrial = dao.findAll(IndustrialSegmentEntity.class);

            List<IndustrialSegmentDTO> lsDto = new ArrayList<>();

            for (IndustrialSegmentEntity item : industrial) {
                IndustrialSegmentDTO dto = new IndustrialSegmentDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }

            LOGGER.debug(" >> END findAllIndustrialSegment ");
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
     * @param industrialSegmentId
     * @return
     * @throws UnexpectedException
     */
    public IndustrialSegmentEntity findIndustrialSegmentById(Long industrialSegmentId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findIndustrialSegmentById ");
            
            IndustrialSegmentDAO dao = daoFactory(IndustrialSegmentDAO.class);
            IndustrialSegmentEntity industrialSegment = dao.find(IndustrialSegmentEntity.class, industrialSegmentId);
            
            if(AppUtil.isNullOrEmpty(industrialSegment)){
                //Busca dominio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.GRUPO_ATIVIDADE_ECONOMICA, String.valueOf(industrialSegmentId).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    industrialSegment = new IndustrialSegmentEntity();
                    industrialSegment.setActive(Boolean.TRUE);
                    industrialSegment.setId(Long.valueOf(domain.getCodigo()));
                    industrialSegment.setDescription(domain.getDescricao());
                    dao.save(industrialSegment);
                }
            }
            
            LOGGER.debug(" >> END findIndustrialSegmentById ");
            return industrialSegment;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
