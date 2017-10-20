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
import com.rci.omega2.ejb.dao.ProvinceDAO;
import com.rci.omega2.ejb.dto.ProvinceDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.AppDtoUtils;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ProvinceEntity;

@Stateless
public class ProvinceBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(ProvinceBO.class);

    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    /**
     * Add domain caso não tenha
     * @param acronym
     * @return
     * @throws UnexpectedException
     */
    public ProvinceEntity findProvinceByAcronym(String acronym) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProvinceByAcronym ");
            
            ProvinceDAO dao = daoFactory(ProvinceDAO.class);
            ProvinceEntity provincy = dao.findProvinceByAcronym(acronym);
            
            
            if(AppUtil.isNullOrEmpty(provincy)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.UNIDADE_FEDERACAO, String.valueOf(acronym).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    provincy = new ProvinceEntity();
                    provincy.setActive(Boolean.TRUE);
                    provincy.setAcronym(domain.getCodigo());
                    provincy.setDescription(domain.getDescricao());
                    dao.save(provincy);
                }
            }
            
            LOGGER.debug(" >> END findProvinceByAcronym ");
            return provincy;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<ProvinceDTO> findAll() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAll ");
            
            ProvinceDAO dao = daoFactory(ProvinceDAO.class);
            
            List<ProvinceDTO> listaDto = new ArrayList<>();
            
            List<ProvinceEntity> listaEntity = dao.findAll();
            
            for (ProvinceEntity entity : listaEntity) {
                listaDto.add(AppDtoUtils.populateProvinceDTO(entity));
            }
            
            LOGGER.debug(" >> END findAll ");
            return listaDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public ProvinceDTO findProvinceFromUser(List<ProvinceDTO> listDto, Long idUser) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProvinceFromUser ");
            
            ProvinceDTO dto = findProvinceByUser(idUser);
            
            if (dto.getId() != null) {
                ProvinceDTO temp = findSelectedProvince(listDto, dto.getId());
                return temp;
            }
            
            LOGGER.debug(" >> END findProvinceFromUser ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        return null;
    }

    public ProvinceDTO findSelectedProvince(List<ProvinceDTO> listDto, String selectedProvince) throws UnexpectedException {
        LOGGER.debug(" >> INIT findSelectedProvince ");
        
        Long idSelectedProvince = CriptoUtilsOmega2.decryptIdToLong(selectedProvince);
        ProvinceDTO dto = new ProvinceDTO();
        if(!AppUtil.isNullOrEmpty(idSelectedProvince)){
            for (ProvinceDTO provinceDTO : listDto) {
                Long idProvince = CriptoUtilsOmega2.decryptIdToLong(provinceDTO.getId());
                if (idSelectedProvince.equals(idProvince)) {
                    dto.setId(provinceDTO.getId());
                    dto.setDescription(provinceDTO.getDescription());
                }
            }
        }
        
        LOGGER.debug(" >> END findSelectedProvince ");
        return dto;
    }

    public ProvinceDTO findProvinceByUser(Long userId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProvinceByUser ");
            
            ProvinceDAO dao = daoFactory(ProvinceDAO.class);
            ProvinceDTO temp = dao.findProvinceByUser(userId);
            
            LOGGER.debug(" >> END findProvinceByUser ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
