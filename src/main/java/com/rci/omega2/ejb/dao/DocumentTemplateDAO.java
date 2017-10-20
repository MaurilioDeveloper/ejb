package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.DocumentTemplateDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.DocumentTemplateEntity;
import com.rci.omega2.entity.FinanceTypeEntity;
import com.rci.omega2.entity.FinancialBrandEntity;
import com.rci.omega2.entity.enumeration.VehicleTypeEnum;

public class DocumentTemplateDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(DocumentTemplateDAO.class);
    
    @SuppressWarnings("unchecked")
    public List<DocumentTemplateDTO> findDocumentsTemplates(List<String> lsDocsToFind) throws UnexpectedException {
        
        try {   
            LOGGER.debug(" >> INIT findDocumentsTemplates ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append(" select dte from DocumentTemplateEntity dte ");
            sql.append(" join fetch dte.template                    ");
            sql.append(" where dte.importCode IN (:ids)             ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            
            query.setParameter("ids", lsDocsToFind);
            
            List<DocumentTemplateDTO> lsDto = new ArrayList<>();
            List<DocumentTemplateEntity> functions = query.getResultList();
            
            for (DocumentTemplateEntity function : functions) {
                DocumentTemplateDTO dto = new DocumentTemplateDTO();
                
                dto.setImportCode(function.getImportCode());
                dto.setDescription(function.getDescription());
                dto.setName(function.getName());
                dto.setDocumentTemplateId(CriptoUtilsOmega2.encrypt(function.getId()));
                
                dto.setFileId(CriptoUtilsOmega2.encrypt(function.getTemplate().getId()));
                dto.setFileName(function.getTemplate().getFileName());
                dto.setLink(function.getTemplate().getLink());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findDocumentsTemplates ");
            return lsDto;
        
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public List<DocumentTemplateDTO> findDocumentTemplateByDossierId(VehicleTypeEnum vehicleType, FinancialBrandEntity financialBrand, FinanceTypeEntity financialType) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDocumentTemplateByDossierId ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT ");
            sql.append(" doc.IMPORT_CODE, ");
            sql.append(" doc.DESCRIPTION, ");
            sql.append(" doc.NAME, ");
            sql.append(" doc.DOCUMENT_TEMPLATE_ID, ");
            sql.append(" fl.FILE_ID, ");
            sql.append(" fl.FILE_NAME, ");
            sql.append(" fl.LINK ");
            sql.append(" FROM TB_DOCUMENT_TEMPLATE doc ");
            sql.append(" INNER JOIN TB_FILE fl on fl.file_id = doc.file_id ");
            sql.append(" WHERE doc.FINANCIAL_BRAND_ID = :financialBrand ");
            sql.append(" AND exists (select 1 from TB_DOC_TEMPLATE_FINANCE_TYPE ft where ft.finance_type_id = :financialType and ft.document_template_id = doc.document_template_id) ");
            sql.append(" AND exists (select 1 from TB_DOC_TEMPLATE_VEHICLE_TYPE vt where vt.vehicle_type = :vehicleType and vt.document_template_id = doc.document_template_id) ");
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString());
        
            query.setParameter("financialBrand", financialBrand.getId()); // 1 Renault 2 Nissan
            query.setParameter("financialType", financialType.getId()); // 401
            query.setParameter("vehicleType", vehicleType.name()); // "NOVO"
        
            List<Object[]> dossiers = query.getResultList();
            
            List<DocumentTemplateDTO> lista = new ArrayList<>();
        
            for(Object[] row : dossiers){
                DocumentTemplateDTO dto = new DocumentTemplateDTO();
                
                dto.setImportCode(getValueString(row[0]));
                dto.setDescription(getValueString(row[1]));
                dto.setName(getValueString(row[2]));
                dto.setDocumentTemplateId(CriptoUtilsOmega2.encrypt(getValueLong(row[3])));
                
                dto.setFileId(CriptoUtilsOmega2.encrypt(getValueLong(row[4])));
                dto.setFileName(getValueString(row[5]));
                dto.setLink(getValueString(row[6]));
                
                lista.add(dto);
            }
        
            LOGGER.debug(" >> END findDocumentTemplateByDossierId ");
            return lista;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
            
    }
    
    private Long getValueLong(Object obj){
        if(obj == null){
            return null;
        }
        return Long.valueOf(getValueString(obj));
    }
    
    private String getValueString(Object obj){
        if(obj == null){
            return null;
        }
        return String.valueOf(obj);
    }
    
}
