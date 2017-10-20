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

public class VehicleFileDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(VehicleFileDAO.class);
    /**
     * Search the image by versionId if empty returns search by modelId if empty
     * returns search by brandId
     * @throws UnexpectedException 
     */

    @SuppressWarnings("unchecked")
    public List<String> getUrlsImageVehicleByVersion(Long versionId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT getUrlsImageVehicleByVersion ");
            
            Query queryVersion = super.getEntityManager().createQuery(
                    "select f.link " + "from VehicleVersionEntity v " + "join v.listFile f " + "where v.id = :versionId ");
            queryVersion.setParameter("versionId", versionId);
            
            List<String> temp = queryVersion.getResultList();
            
            LOGGER.debug(" >> END getUrlsImageVehicleByVersion ");
            return temp;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    @SuppressWarnings("unchecked")
    public List<String> getUrlsImageVehicleByFipe(String fipe) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT getUrlsImageVehicleByFipe ");
            
            Query queryVersion = super.getEntityManager().createQuery(
                    "select f.link " + "from VehicleVersionEntity v " + "join v.listFile f " + "where v.fipe = :fipe ");
            queryVersion.setParameter("fipe", fipe);
            
            List<String> temp = queryVersion.getResultList();
            
            LOGGER.debug(" >> END getUrlsImageVehicleByFipe ");
            return temp;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
            
    }

    @SuppressWarnings("unchecked")
    public List<String> getUrlsImageVehicleByModel(Long modelId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT getUrlsImageVehicleByModel ");
            
            Query queryModel = super.getEntityManager().createQuery(
                    "select f.link " + "from VehicleModelEntity m " + "join m.listFile f " + "where m.id = :modelId ");
            queryModel.setParameter("modelId", modelId);
            
            List<String> temp = queryModel.getResultList();
            
            LOGGER.debug(" >> END getUrlsImageVehicleByModel ");
            return temp;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }

    @SuppressWarnings("unchecked")
    public List<String> getUrlsImageVehicleByBrand(Long brandId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT getUrlsImageVehicleByBrand ");
            
            Query queryBrand = super.getEntityManager().createQuery(
                    "select f.link " + "from VehicleBrandEntity b " + "join b.listFile f " + "where b.id = :brandId ");
            queryBrand.setParameter("brandId", brandId);
            
            List<String> temp = queryBrand.getResultList();
            
            LOGGER.debug(" >> END getUrlsImageVehicleByBrand ");
            return temp;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }
    
    @SuppressWarnings("unchecked")
    public FileDTO findFileVehicleByVersion(Long versionId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findFileVehicleByVersion ");
            
            Query queryVersion = super.getEntityManager().createNativeQuery(
                    " SELECT F.FILE_ID, F.LINK, F.FILE_NAME, FVV.VEHICLE_VERSION_ID "
                    + " FROM TB_FILE F "
                    + " INNER JOIN TB_FILE_VEHICLE_VERSION FVV ON FVV.FILE_ID = F.FILE_ID "
                    + " WHERE FVV.VEHICLE_VERSION_ID = :versionId "
                    + " ORDER BY F.FILE_ID ").setMaxResults(1);
            queryVersion.setParameter("versionId", versionId);
            
            FileDTO temp = populateListFileDTO(queryVersion.getResultList());
            
            LOGGER.debug(" >> END findFileVehicleByVersion ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
            
    }

    @SuppressWarnings("unchecked")
    public FileDTO  findFileVehicleByModel(Long modelId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findFileVehicleByModel ");
            
            Query queryModel = super.getEntityManager().createNativeQuery(
                    " SELECT F.FILE_ID, F.LINK, F.FILE_NAME, FVM.VEHICLE_MODEL_ID "
                    + " FROM TB_FILE F "
                    + " INNER JOIN TB_FILE_VEHICLE_MODEL FVM ON FVM.FILE_ID = F.FILE_ID "
                    + " WHERE FVM.VEHICLE_MODEL_ID = :modelId "
                    + " ORDER BY F.FILE_ID ").setMaxResults(1);
            queryModel.setParameter("modelId", modelId);
            
            FileDTO temp = populateListFileDTO(queryModel.getResultList());
            
            LOGGER.debug(" >> END findFileVehicleByModel ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
        
    }

    @SuppressWarnings("unchecked")
    public FileDTO  findFileVehicleByBrand(Long brandId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findFileVehicleByBrand ");
            
            Query queryBrand = super.getEntityManager().createNativeQuery(
                    " SELECT F.FILE_ID, F.LINK, F.FILE_NAME, FVB.VEHICLE_BRAND_ID "
                    + " FROM TB_FILE F "
                    + " INNER JOIN TB_FILE_VEHICLE_BRAND FVB ON FVB.FILE_ID = F.FILE_ID "
                    + " WHERE FVB.VEHICLE_BRAND_ID = :brandId "
                    + " ORDER BY F.FILE_ID ").setMaxResults(1);
            queryBrand.setParameter("brandId", brandId);
            
            FileDTO temp = populateListFileDTO(queryBrand.getResultList());
            
            LOGGER.debug(" >> END findFileVehicleByBrand ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
    private FileDTO populateListFileDTO(List<Object[]> listEntity) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT populateListFileDTO ");
            
            FileDTO dto = new FileDTO();
            
            for(Object[] row : listEntity){
                dto.setFileId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[0].toString())));
                //adiciona no link da imagem data e hora para não ficar cache.
                dto.setFileLink((String) row[1]+"?no_cache_"+AppUtil.getNowString());
                dto.setFileName((String) row[2]);
                dto.setFileObjectId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[3].toString())));
            }
            
            LOGGER.debug(" >> END populateListFileDTO ");
            return dto;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
    }
    
}
