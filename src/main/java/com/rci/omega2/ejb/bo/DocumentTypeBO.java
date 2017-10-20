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
import com.rci.omega2.ejb.dao.DocumentTypeDAO;
import com.rci.omega2.ejb.dto.DocumentTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.DocumentTypeEntity;

@Stateless
public class DocumentTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(DocumentTypeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<DocumentTypeDTO> findAllDocumentType() throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT findAllDocumentType ");
            DocumentTypeDAO dao = daoFactory(DocumentTypeDAO.class);
            
            List<DocumentTypeEntity> document = dao.findAll(DocumentTypeEntity.class);

            List<DocumentTypeDTO> lsDto = new ArrayList<>();

            for (DocumentTypeEntity item : document) {
                DocumentTypeDTO dto = new DocumentTypeDTO();
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
    public DocumentTypeEntity findDocumentTypeByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDocumentTypeByImportCode ");
            
            DocumentTypeDAO dao = daoFactory(DocumentTypeDAO.class);
            DocumentTypeEntity documentType = dao.findDocumentTypeByImportCode(importCode);

            
            if(AppUtil.isNullOrEmpty(documentType)){
                //Busca dominio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                        SantanderDomainEnum.TIPO_DOCUMENTO, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    documentType = new DocumentTypeEntity();
                    documentType.setActive(Boolean.TRUE);
                    documentType.setImportCode(domain.getCodigo());
                    documentType.setDescription(domain.getDescricao());
                    dao.save(documentType);
                }
            }
            
            LOGGER.debug(" >> END findDocumentTypeByImportCode ");
            return documentType;
            
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
