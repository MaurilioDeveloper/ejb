package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.VehicleBrandDAO;
import com.rci.omega2.ejb.dao.VehicleFileDAO;
import com.rci.omega2.ejb.dao.VehicleModelDAO;
import com.rci.omega2.ejb.dao.VehicleVersionDAO;
import com.rci.omega2.ejb.dto.FileDTO;
import com.rci.omega2.ejb.dto.FileRequestDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.FileEntity;
import com.rci.omega2.entity.VehicleBrandEntity;
import com.rci.omega2.entity.VehicleModelEntity;
import com.rci.omega2.entity.VehicleVersionEntity;

@Stateless
public class VehicleFileBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(VehicleFileBO.class);
    @EJB
    private S3AccessBO s3AccessBO;
    
    
    public List<String> getUrlsImageVehicleByFipeAndModel(String fipe, Long idModel) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT getUrlsImageVehicleByFipeAndModel ");
            VehicleFileDAO dao = daoFactory(VehicleFileDAO.class);
            List<String> result = dao.getUrlsImageVehicleByFipe(fipe);
            // Caso a lista esteja vazia busco pelo modelo
            if (result.isEmpty()) {
                result = dao.getUrlsImageVehicleByModel(idModel);
                if (result.isEmpty()) {
                    VehicleModelDAO modelDao = daoFactory(VehicleModelDAO.class);
                    VehicleModelEntity model = modelDao.find(VehicleModelEntity.class, idModel);
                    if(model != null){
                        result = dao.getUrlsImageVehicleByBrand(model.getVehicleBrand().getId());
                    }
                }

            }
            LOGGER.debug(" >> END getUrlsImageVehicleByFipeAndModel ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    public List<String> getUrlsImageVehicleByVersionAndModel(Long idVersion, Long idModel) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT getUrlsImageVehicleByVersionAndModel ");
            VehicleFileDAO dao = daoFactory(VehicleFileDAO.class);
            List<String> result = dao.getUrlsImageVehicleByVersion(idVersion);
            // Caso a lista esteja vazia busco pelo modelo
            if (result.isEmpty()) {
                result = dao.getUrlsImageVehicleByModel(idModel);
                if (result.isEmpty()) {
                    VehicleModelDAO modelDao = daoFactory(VehicleModelDAO.class);
                    VehicleModelEntity model = modelDao.find(VehicleModelEntity.class, idModel);
                    if(model != null){
                        result = dao.getUrlsImageVehicleByBrand(model.getVehicleBrand().getId());
                    }
                }

            }
            LOGGER.debug(" >> END getUrlsImageVehicleByVersionAndModel ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public FileDTO findFileVehicleByVersion(Long versionId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findFileVehicleByVersion ");
            VehicleFileDAO dao = daoFactory(VehicleFileDAO.class);
            FileDTO temp = dao.findFileVehicleByVersion(versionId);
            
            LOGGER.debug(" >> END findFileVehicleByVersion ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    
    public List<String> findListFileVehicleByModel(Long modelId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findListFileVehicleByModel ");
            VehicleFileDAO dao = daoFactory(VehicleFileDAO.class);
          
            List<String> result = dao.getUrlsImageVehicleByModel(modelId);
            if (result.isEmpty()) {
                VehicleModelDAO modelDao = daoFactory(VehicleModelDAO.class);
                VehicleModelEntity model = modelDao.find(VehicleModelEntity.class, modelId);
                if(model != null){
                    result = dao.getUrlsImageVehicleByBrand(model.getVehicleBrand().getId());
                }
            }
            LOGGER.debug(" >> END findListFileVehicleByModel ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    public FileDTO findFileVehicleByModel(Long modelId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findFileVehicleByModel ");
            VehicleFileDAO dao = daoFactory(VehicleFileDAO.class);
            FileDTO temp = dao.findFileVehicleByModel(modelId);
            LOGGER.debug(" >> END findFileVehicleByModel ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public FileDTO findFileVehicleByBrand(Long brandId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findFileVehicleByBrand ");
            VehicleFileDAO dao = daoFactory(VehicleFileDAO.class);
            FileDTO temp = dao.findFileVehicleByBrand(brandId);
            LOGGER.debug(" >> END findFileVehicleByBrand ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void saveFileVehicleBrand(FileRequestDTO file) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveFileVehicleBrand ");
            VehicleFileDAO daoFile = daoFactory(VehicleFileDAO.class);
            VehicleBrandDAO daoBrand = daoFactory(VehicleBrandDAO.class);
            FileEntity fileEntity = new FileEntity();
            if(file.getFileId() == null){
                //salva o arquivo(FileEntity)
                fileEntity.setDescription(file.getDescription());
                fileEntity.setFileName(file.getFileName());
                fileEntity.setLink(file.getFileLink());
                fileEntity.setIncludeDate(AppUtil.getToday().getTime());
                daoFile.save(fileEntity);
                //salva o arquivo na lista de imagens do modelo
                VehicleBrandEntity brandEntity = new VehicleBrandEntity();
                brandEntity.setId(file.getFileObjectId());
                brandEntity = daoBrand.find(VehicleBrandEntity.class, brandEntity.getId());
                brandEntity.getListFile().add(fileEntity);
                daoBrand.save(brandEntity);
            }else{
                //Edita o arquivo(FileEntity)
                fileEntity.setId(file.getFileId());
                fileEntity = daoFile.find(FileEntity.class, fileEntity.getId());
                fileEntity.setFileName(file.getFileName());
                fileEntity.setChangeDate(AppUtil.getToday().getTime());
                daoFile.update(fileEntity);
            }
            s3AccessBO.putObjectFromS3(fileEntity.getLink(),file.getFile());
            LOGGER.debug(" >> END saveFileVehicleBrand ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void saveFileVehicleModel(FileRequestDTO file) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveFileVehicleModel ");
            VehicleFileDAO daoFile = daoFactory(VehicleFileDAO.class);
            VehicleModelDAO daoModel = daoFactory(VehicleModelDAO.class);
            FileEntity fileEntity = new FileEntity();
            if(file.getFileId() == null){
                //salva o arquivo(FileEntity)
                fileEntity.setDescription(file.getDescription());
                fileEntity.setFileName(file.getFileName());
                fileEntity.setLink(file.getFileLink());
                fileEntity.setIncludeDate(AppUtil.getToday().getTime());
                daoFile.save(fileEntity);
                //salva o arquivo na lista de imagens do modelo
                VehicleModelEntity modelEntity = new VehicleModelEntity();
                modelEntity.setId(file.getFileObjectId());
                modelEntity = daoModel.find(VehicleModelEntity.class, modelEntity.getId());
                modelEntity.getListFile().add(fileEntity);
                daoModel.save(modelEntity);
            }else{
                //Edita o arquivo(FileEntity)
                fileEntity.setId(file.getFileId());
                fileEntity = daoFile.find(FileEntity.class, fileEntity.getId());
                fileEntity.setFileName(file.getFileName());
                fileEntity.setChangeDate(AppUtil.getToday().getTime());
                daoFile.update(fileEntity);
            }
            s3AccessBO.putObjectFromS3(fileEntity.getLink(),file.getFile());
            LOGGER.debug(" >> END saveFileVehicleModel ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void saveFileVehicleVersion(FileRequestDTO file) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveFileVehicleVersion ");
            VehicleFileDAO daoFile = daoFactory(VehicleFileDAO.class);
            VehicleVersionDAO daoVersion = daoFactory(VehicleVersionDAO.class);
            FileEntity fileEntity = new FileEntity();
            if(file.getFileId() == null){
                //salva o arquivo(FileEntity)
                fileEntity.setDescription(file.getDescription());
                fileEntity.setFileName(file.getFileName());
                fileEntity.setLink(file.getFileLink());
                fileEntity.setIncludeDate(AppUtil.getToday().getTime());
                daoFile.save(fileEntity);
                //salva o arquivo na lista de imagens da versão
                VehicleVersionEntity versionEntity = new VehicleVersionEntity();
                versionEntity.setId(file.getFileObjectId());
                versionEntity = daoVersion.find(VehicleVersionEntity.class, versionEntity.getId());
                versionEntity.getListFile().add(fileEntity);
                daoVersion.save(versionEntity);
            }else{
                //Edita o arquivo(FileEntity)
                fileEntity.setId(file.getFileId());
                fileEntity = daoFile.find(FileEntity.class, fileEntity.getId());
                fileEntity.setFileName(file.getFileName());
                fileEntity.setChangeDate(AppUtil.getToday().getTime());
                daoFile.update(fileEntity);
            }
            
            s3AccessBO.putObjectFromS3(fileEntity.getLink(),file.getFile());
            LOGGER.debug(" >> END saveFileVehicleVersion ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void deleteFileVehicleBrand(Long fileId, Long brandId) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteFileVehicleBrand ");
            VehicleFileDAO daoFile = daoFactory(VehicleFileDAO.class);
            VehicleBrandDAO daoBrand = daoFactory(VehicleBrandDAO.class);
            
            VehicleBrandEntity brandEntity = new VehicleBrandEntity();
            brandEntity.setId(brandId);
            brandEntity = daoBrand.find(VehicleBrandEntity.class, brandEntity.getId());
        
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(fileId);
            fileEntity = daoFile.find(FileEntity.class, fileEntity.getId());
            
            brandEntity.getListFile().remove(fileEntity);
            daoBrand.save(brandEntity);
            daoFile.remove(fileEntity);
            s3AccessBO.deleteObjectFromS3(fileEntity.getLink());
            LOGGER.debug(" >> END deleteFileVehicleBrand ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void deleteFileVehicleModel(Long fileId, Long modelId) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteFileVehicleModel ");
            VehicleFileDAO daoFile = daoFactory(VehicleFileDAO.class);
            VehicleModelDAO daoModel = daoFactory(VehicleModelDAO.class);
            
            VehicleModelEntity modelEntity = new VehicleModelEntity();
            modelEntity.setId(modelId);
            modelEntity = daoModel.find(VehicleModelEntity.class, modelEntity.getId());
        
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(fileId);
            fileEntity = daoFile.find(FileEntity.class, fileEntity.getId());
            
            modelEntity.getListFile().remove(fileEntity);
            daoModel.save(modelEntity);
            daoFile.remove(fileEntity);
            s3AccessBO.deleteObjectFromS3(fileEntity.getLink());
            LOGGER.debug(" >> END deleteFileVehicleModel ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void deleteFileVehicleVersion(Long fileId, Long versionId) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteFileVehicleVersion ");
            VehicleFileDAO daoFile = daoFactory(VehicleFileDAO.class);
            VehicleVersionDAO daoVersion = daoFactory(VehicleVersionDAO.class);
            
            VehicleVersionEntity versionEntity = new VehicleVersionEntity();
            versionEntity.setId(versionId);
            versionEntity = daoVersion.find(VehicleVersionEntity.class, versionEntity.getId());
        
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(fileId);
            fileEntity = daoFile.find(FileEntity.class, fileEntity.getId());
            
            versionEntity.getListFile().remove(fileEntity);
            daoVersion.save(versionEntity);
            daoFile.remove(fileEntity);
            s3AccessBO.deleteObjectFromS3(fileEntity.getLink());
            LOGGER.debug(" >> END deleteFileVehicleVersion ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
}
