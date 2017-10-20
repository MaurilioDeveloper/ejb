package com.rci.omega2.ejb.dao;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.TaxDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.TaxEntity;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;
import com.rci.omega2.entity.enumeration.TaxTypeEnum;
import com.rci.omega2.entity.enumeration.VehicleGenderEnum;

public class TaxDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(TaxDAO.class);
    
    public TaxDTO findTaxTC(String personType) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findTaxTC ");
            
            Query query = super.getEntityManager().createQuery(
                      " SELECT t "
                    + " FROM TaxEntity AS t "
                    + " WHERE t.active = :active "
                    + " AND t.taxType = :taxType "
                    + " AND t.personType = :personType ");
            
            query.setParameter("active", Boolean.TRUE);
            query.setParameter("taxType", TaxTypeEnum.valueOf("TC"));
            query.setParameter("personType", PersonTypeEnum.valueOf(personType));
            
            TaxEntity tax = (TaxEntity) query.getSingleResult();     
            
            TaxDTO temp = populateTaxDTO(tax);
            
            LOGGER.debug(" >> END findTaxTC ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
            
    }
    
    public TaxDTO findTaxTR(String province, String gender, Long vehiclesSpecial) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findTaxTR ");
            
            StringBuilder sql = new StringBuilder();
                    sql.append(" SELECT t "
                    + " FROM TaxEntity AS t "
                    + " WHERE t.active = :active "
                    + " AND t.taxType = :taxType "
                    + " AND t.province.id = :province "
                    + " AND t.vehicleGender = :gender "
                    );
                    
                    if(vehiclesSpecial == null){
                        sql.append(" AND t.specialVehicleType.id IS NULL");
                    }else{
                        sql.append(" AND t.specialVehicleType.id = :vehiclesSpecial");
                    }
                    
            Query query = super.getEntityManager().createQuery(sql.toString());
            
            query.setParameter("active", Boolean.TRUE);
            query.setParameter("taxType", TaxTypeEnum.valueOf("TR"));
            query.setParameter("province", Long.valueOf(province));
            query.setParameter("gender", VehicleGenderEnum.valueOf(gender));
            
            if(vehiclesSpecial != null){            
                query.setParameter("vehiclesSpecial", vehiclesSpecial);
            }
            
            TaxEntity tax = (TaxEntity) query.getSingleResult();     
            
            TaxDTO temp = populateTaxDTO(tax);
            
            LOGGER.debug(" >> END findTaxTR ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }      
        
    }
    
    public TaxDTO findTaxTAB() throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findTaxTAB ");
            
            Query query = super.getEntityManager().createQuery(
                      " SELECT t "
                    + " FROM TaxEntity AS t "
                    + " WHERE t.active = :active "
                    + " AND t.taxType = :taxType ");
            
            query.setParameter("active", Boolean.TRUE);
            query.setParameter("taxType", TaxTypeEnum.valueOf("TAB"));
            TaxEntity tax = (TaxEntity) query.getSingleResult();

            TaxDTO temp = populateTaxDTO(tax);
            
            LOGGER.debug(" >> END findTaxTAB ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
            
    }
    

    private TaxDTO populateTaxDTO(TaxEntity entity) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT populateTaxDTO ");
            
            TaxDTO dto = new TaxDTO();
            dto.setIdTax(CriptoUtilsOmega2.encrypt(entity.getId()));
            dto.setDescription(entity.getDescription());
            dto.setTaxType(entity.getTaxType().toString());
            dto.setValue(entity.getAmount());
            
            LOGGER.debug(" >> END populateTaxDTO ");
            return dto;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } 
        
    }
    
}
