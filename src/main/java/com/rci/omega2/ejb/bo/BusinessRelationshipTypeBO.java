package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.BusinessRelationshipTypeDAO;
import com.rci.omega2.ejb.dto.BusinessRelationshipTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.BusinessRelationshipTypeEntity;

@Stateless
public class BusinessRelationshipTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(BusinessRelationshipTypeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 
    

    public List<BusinessRelationshipTypeDTO> findAllBusinessRelationshipType() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAllBusinessRelationshipType ");
            
            BusinessRelationshipTypeDAO dao = daoFactory(BusinessRelationshipTypeDAO.class);
            
            List<BusinessRelationshipTypeEntity> business = dao.findAllBusinessRelationshipType();
            
            List<BusinessRelationshipTypeDTO> lsDto = new ArrayList<>();
            
            for (BusinessRelationshipTypeEntity item : business){
                BusinessRelationshipTypeDTO dto = new BusinessRelationshipTypeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                dto.setImportCode(item.getImportCode());
                lsDto.add(dto);
            }

            LOGGER.debug(" >> END findAllBusinessRelationshipType ");
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
    public BusinessRelationshipTypeEntity findBusinessRelationshipTypeByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findBusinessRelationshipTypeByImportCode ");
            
            BusinessRelationshipTypeDAO dao = daoFactory(BusinessRelationshipTypeDAO.class);
            BusinessRelationshipTypeEntity businessRelationshipType = dao.findBusinessRelationshipTypeByImportCode(importCode);
            
            
            if(AppUtil.isNullOrEmpty(businessRelationshipType)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderBusinessRelationshipType(String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    businessRelationshipType = new BusinessRelationshipTypeEntity();
                    businessRelationshipType.setActive(Boolean.TRUE);
                    businessRelationshipType.setImportCode(domain.getCodigo());
                    businessRelationshipType.setDescription(domain.getDescricao());
                    dao.save(businessRelationshipType);
                }
            }
            
            LOGGER.debug(" >> END findBusinessRelationshipTypeByImportCode ");
            return businessRelationshipType;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
