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
import com.rci.omega2.ejb.dao.KinshipTypeDAO;
import com.rci.omega2.ejb.dto.KinshipTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.SantanderServicesUtils;
import com.rci.omega2.entity.KinshipTypeEntity;

@Stateless
public class KinshipTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(KinshipTypeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<KinshipTypeDTO> findAllKinshipType() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllKinshipType ");
            
            KinshipTypeDAO dao = daoFactory(KinshipTypeDAO.class);
            
            List<KinshipTypeEntity> business = dao.findAll(KinshipTypeEntity.class);
            
            List<KinshipTypeDTO> lsDto = new ArrayList<>();
            
            for (KinshipTypeEntity item : business){
                KinshipTypeDTO dto = new KinshipTypeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                dto.setImportCode(item.getImportCode());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllKinshipType ");
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
     * @param kinshipTypeById
     * @return
     * @throws UnexpectedException
     */
    public KinshipTypeEntity findKinshipTypeById(Long kinshipTypeById) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findKinshipTypeById ");
            
            KinshipTypeDAO dao = daoFactory(KinshipTypeDAO.class);
            KinshipTypeEntity temp = dao.find(KinshipTypeEntity.class, kinshipTypeById);
            
            LOGGER.debug(" >> END findKinshipTypeById ");
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
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    public KinshipTypeEntity findKinshipTypeByImportCode(String importCode) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findKinshipTypeByImportCode ");
            KinshipTypeDAO dao = daoFactory(KinshipTypeDAO.class);
            KinshipTypeEntity kinshipType = dao.findKinshipTypeByImportCode(importCode);

            if(AppUtil.isNullOrEmpty(kinshipType)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.GRAU_PARENTESCO, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    kinshipType = new KinshipTypeEntity();
                    kinshipType.setActive(Boolean.TRUE);
                    kinshipType.setImportCode(SantanderServicesUtils.extractFillZeroToLeft(domain.getCodigo(),2));
                    kinshipType.setDescription(domain.getDescricao());
                    dao.save(kinshipType);
                }
            }
            
            LOGGER.debug(" >> END findKinshipTypeByImportCode ");
            return kinshipType;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
}
