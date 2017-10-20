package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dto.FileDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

public class FileDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(FileDAO.class);
    
    @SuppressWarnings("unchecked")
    public FileDTO findImageByfinancialBrand(Long id) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findImageByfinancialBrand ");
          
            Query query = super.getEntityManager().createNativeQuery(" SELECT F.FILE_ID, F.LINK FROM TB_FILE F "
                    + " INNER JOIN TB_FINANCIAL_BRAND FB ON FB.PROMOTIONAL_IMAGE = F.FILE_ID "
                    + " WHERE FB.FINANCIAL_BRAND_ID = :id ");
            query.setParameter("id", id);
            
            List<Object[]> file = query.getResultList();   
            
            FileDTO dto = new FileDTO();
            
            for(Object[] row : file){
                dto.setFileId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[0].toString())));
                //adiciona no link da imagem data e hora para não ficar cache.
                dto.setFileLink((String) row[1]+"?no_cache_"+AppUtil.getNowString());
            }
            
            LOGGER.debug(" >> END findImageByfinancialBrand ");
            return dto;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
        
    }
    
    @SuppressWarnings("unchecked")
    public FileDTO findImageByFinancialBrandEnum(Integer importCodeActor) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findImageByFinancialBrandEnum ");
            
            Query query = super.getEntityManager().createNativeQuery(" SELECT F.FILE_ID, F.LINK FROM TB_FILE F "
                    + " INNER JOIN TB_FINANCIAL_BRAND FB ON FB.PROMOTIONAL_IMAGE = F.FILE_ID "
                    + " WHERE FB.IMPORT_CODE_ACTOR LIKE :importCodeActor ");
            query.setParameter("importCodeActor", "%"+importCodeActor.toString()+"%");
            
            List<Object[]> file = query.getResultList();   
            
            FileDTO dto = new FileDTO();
            for(Object[] row : file){
                dto.setFileId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[0].toString())));
                //adiciona no link da imagem data e hora para não ficar cache.
                dto.setFileLink((String) row[1]+"?no_cache_"+AppUtil.getNowString());
            }
            
            LOGGER.debug(" >> END findImageByFinancialBrandEnum ");
            return dto;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
}
