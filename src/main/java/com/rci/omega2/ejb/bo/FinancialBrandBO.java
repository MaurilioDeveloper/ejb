package com.rci.omega2.ejb.bo;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.FileDAO;
import com.rci.omega2.ejb.dao.FinancialBrandDAO;
import com.rci.omega2.ejb.dao.StructureDAO;
import com.rci.omega2.ejb.dto.FileDTO;
import com.rci.omega2.ejb.dto.FileRequestDTO;
import com.rci.omega2.ejb.dto.FinancialBrandDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.Brand;
import com.rci.omega2.entity.FileEntity;
import com.rci.omega2.entity.FinancialBrandEntity;
import com.rci.omega2.entity.UserEntity;

@Stateless
public class FinancialBrandBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(FinancialBrandBO.class);
    
    @EJB
    private S3AccessBO s3AccessBO;

    public List<FinancialBrandDTO> findAllFinancialBrands() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAllFinancialBrands ");
            
            FinancialBrandDAO dao = daoFactory(FinancialBrandDAO.class);
            List<FinancialBrandDTO> result = dao.findAllFinancialBrands();
            
            LOGGER.debug(" >> END findAllFinancialBrands ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public Set<FinancialBrandEntity> findFinancialBrandsById(Set<Long> idsFinancialBrands) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findFinancialBrandsById ");
            
            FinancialBrandDAO dao = daoFactory(FinancialBrandDAO.class);
            Set<FinancialBrandEntity> result = dao.findFinancialBrandsById(idsFinancialBrands);
            
            LOGGER.debug(" >> END findFinancialBrandsById ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public Set<FinancialBrandEntity> findFinancialBrandById(String id) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findFinancialBrandById ");
            
            FinancialBrandDAO dao = daoFactory(FinancialBrandDAO.class);
            Set<FinancialBrandEntity> result = dao.findFinancialBrandById(id);
            
            LOGGER.debug(" >> END findFinancialBrandById ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    public List<FinancialBrandDTO> findFinancialBrandsBynoticeId(String idNotice) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findFinancialBrandsBynoticeId ");
            
            FinancialBrandDAO dao = daoFactory(FinancialBrandDAO.class);
            List<FinancialBrandDTO> result = dao.findFinancialBrandsBynoticeId(idNotice);
            
            LOGGER.debug(" >> END findFinancialBrandsBynoticeId ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public Brand getBrand(UserEntity user) throws UnexpectedException {
        Set<Brand> brands = null;
        try {
            LOGGER.debug(" >> INIT getBrand ");
            
            StructureDAO dao = daoFactory(StructureDAO.class);
            brands = dao.getBrandByUserId(user);
            
            LOGGER.debug(" >> END getBrand ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        if (brands.contains(Brand.NISSAN) && brands.contains(Brand.RENAULT)) {
            return Brand.RCI;
        } else if (brands.contains(Brand.NISSAN)) {
            return Brand.NISSAN;
        } else if (brands.contains(Brand.RENAULT)) {
            return Brand.RENAULT;
        } else {
            return Brand.RCI;
        }
        
    }
    
    public FileDTO findImageByFinancialBrandEnum(Integer id) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT findImageByFinancialBrandEnum ");
            
            FileDAO dao = daoFactory(FileDAO.class);
            FileDTO result = dao.findImageByFinancialBrandEnum(id);
            
            LOGGER.debug(" >> END findImageByFinancialBrandEnum ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public FileDTO findImageByfinancialBrand(Long id) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT findImageByfinancialBrand ");
            
            FileDAO dao = daoFactory(FileDAO.class);
            FileDTO result = dao.findImageByfinancialBrand(id);
            
            LOGGER.debug(" >> END findImageByfinancialBrand ");
            return result;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void saveFileFinancialBrand(FileRequestDTO file) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveFileFinancialBrand ");
            
            FileDAO daoFile = daoFactory(FileDAO.class);
            FinancialBrandDAO daoFinancialBrand = daoFactory(FinancialBrandDAO.class);
            FileEntity fileEntity = new FileEntity();
            if(file.getFileId() == null){
                //salva o arquivo(FileEntity)
                fileEntity.setDescription(file.getDescription());
                fileEntity.setFileName(file.getFileName());
                fileEntity.setLink(file.getFileLink());
                fileEntity.setIncludeDate(AppUtil.getToday().getTime());
                daoFile.save(fileEntity);
                
                FinancialBrandEntity financialBrandEntity = new FinancialBrandEntity();
                financialBrandEntity.setId(file.getFileObjectId());
                financialBrandEntity = daoFinancialBrand.find(FinancialBrandEntity.class, financialBrandEntity.getId());
                financialBrandEntity.setPromotionalImage(fileEntity);
                daoFinancialBrand.save(financialBrandEntity);
            }else{
                //Edita o arquivo(FileEntity)
                fileEntity.setId(file.getFileId());
                fileEntity = daoFile.find(FileEntity.class, fileEntity.getId());
                fileEntity.setFileName(file.getFileName());
                fileEntity.setChangeDate(AppUtil.getToday().getTime());
                daoFile.update(fileEntity);
            }
            s3AccessBO.putObjectFromS3(fileEntity.getLink(),file.getFile());
            
            LOGGER.debug(" >> END saveFileFinancialBrand ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void deleteFileFinancialBrand(Long fileId, Long financialBrandId) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT deleteFileFinancialBrand ");
            
            FileDAO daoFile = daoFactory(FileDAO.class);
            FinancialBrandDAO daoFinancialBrand = daoFactory(FinancialBrandDAO.class);
            
            FinancialBrandEntity financialBrandEntity = new FinancialBrandEntity();
            financialBrandEntity.setId(financialBrandId);
            financialBrandEntity = daoFinancialBrand.find(FinancialBrandEntity.class, financialBrandEntity.getId());
            financialBrandEntity.setPromotionalImage(null);
            daoFinancialBrand.save(financialBrandEntity);
        
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(fileId);
            fileEntity = daoFile.find(FileEntity.class, fileEntity.getId());
            daoFile.remove(fileEntity);
            s3AccessBO.deleteObjectFromS3(fileEntity.getLink());
            
            LOGGER.debug(" >> END deleteFileFinancialBrand ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
