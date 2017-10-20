package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.BaseDAO;
import com.rci.omega2.ejb.dao.CarInsuranceDAO;
import com.rci.omega2.ejb.dao.InsuranceDAO;
import com.rci.omega2.ejb.dto.CarInsuranceDTO;
import com.rci.omega2.ejb.dto.InsuranceDossierRequestDTO;
import com.rci.omega2.ejb.dto.InsuranceDossierSoldDTO;
import com.rci.omega2.ejb.dto.InsuranceStatusDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CarInsuranceEntity;
import com.rci.omega2.entity.CarInsuranceStatusEntity;


@Stateless
public class CarInsuranceBO extends BaseBO{

    private static final Logger LOGGER = LogManager.getLogger(CarInsuranceBO.class);
    
    public List<InsuranceStatusDTO> findInsuranceStatus() throws UnexpectedException {
        
        try {
            
            InsuranceDAO dao = daoFactory(InsuranceDAO.class);
            return dao.findInsuranceStatus();
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    public List<InsuranceDossierSoldDTO> findDossierSold(InsuranceDossierRequestDTO request) throws UnexpectedException{
        
        try {
            
            InsuranceDAO dao = daoFactory(InsuranceDAO.class);
            return dao.findDossierSold(request);
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
   
    public List<CarInsuranceEntity> findByFilter(CarInsuranceDTO dto) throws UnexpectedException {
        CarInsuranceDAO dao = daoFactory(CarInsuranceDAO.class);
        return dao.findByFilter(dto);
    }
    
    public CarInsuranceEntity find(Long id) throws UnexpectedException {
        try {
            CarInsuranceDAO dao = daoFactory(CarInsuranceDAO.class);
            return dao.find(CarInsuranceEntity.class, id);
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public void update(CarInsuranceEntity carInsuranceEntity) throws UnexpectedException {
        try {
            CarInsuranceDAO dao = daoFactory(CarInsuranceDAO.class);
            dao.update(carInsuranceEntity);
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public List<CarInsuranceStatusEntity> findAllStatus() throws UnexpectedException {
        try {
            BaseDAO baseDAO = daoFactory(BaseDAO.class);
            return baseDAO.findAll(CarInsuranceStatusEntity.class);
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
