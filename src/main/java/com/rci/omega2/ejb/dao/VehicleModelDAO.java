package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.VehicleModelDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.enumeration.VehicleGenderEnum;
import com.rci.omega2.entity.enumeration.VehicleTypeEnum;

public class VehicleModelDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(VehicleModelDAO.class);
        
    @SuppressWarnings("unchecked")
    public List<VehicleModelDTO> listByBrandAndVehicleType(Long idBrand, String vehicleType) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT listByBrandAndVehicleType ");
            
            Query query = super.getEntityManager().createQuery("SELECT m.id, m.description "
                    + "FROM VehicleVersionEntity as v " 
                    + "left join v.vehicleModel as m "
                    + "where v.vehicleType = :vehicleType " 
                    + "and m.vehicleBrand.id = :idBrand " 
                    + "group by m.id, m.description "
                    + "order by m.description");
            query.setParameter("vehicleType", VehicleTypeEnum.valueOf(vehicleType));
            query.setParameter("idBrand", idBrand);
            
            List<Object[]> models = query.getResultList();        
            List<VehicleModelDTO> listReturn = new ArrayList<VehicleModelDTO>();
            
            for(Object[] row : models){
                VehicleModelDTO dto = new VehicleModelDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setDescription((String) row[1]);
                dto.setVehicleType(vehicleType);
                listReturn.add(dto);
            }
            
            LOGGER.debug(" >> END listByBrandAndVehicleType ");
            return listReturn;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public List<VehicleModelDTO> listByBrandAndVehicleTypeAndVehicleGender(Long idBrand, String vehicleType, String vehicleGender) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT listByBrandAndVehicleTypeAndVehicleGender ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT m.id, m.description             ");
            sql.append(" FROM VehicleVersionEntity as v         ");
            sql.append(" left join v.vehicleModel as m          ");
            sql.append(" where v.vehicleType = :vehicleType     ");
            sql.append(" and v.active = 1                       ");
            sql.append(" and v.vehicleGender = :gender          ");
            sql.append(" and m.vehicleBrand.id = :idBrand       ");
            sql.append(" group by m.id, m.description           ");
            sql.append(" order by m.description                 ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            
            query.setParameter("vehicleType", VehicleTypeEnum.valueOf(vehicleType));
            query.setParameter("gender", VehicleGenderEnum.valueOf(vehicleGender));
            query.setParameter("idBrand", idBrand);
            
            List<Object[]> models = query.getResultList();        
            
            List<VehicleModelDTO> listReturn = new ArrayList<VehicleModelDTO>();
            
            for(Object[] row : models){
                VehicleModelDTO dto = new VehicleModelDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setDescription((String) row[1]);
                dto.setVehicleType(vehicleType);
                listReturn.add(dto);
            }
            
            LOGGER.debug(" >> END listByBrandAndVehicleTypeAndVehicleGender ");
            return listReturn;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
            
    }
    
    @SuppressWarnings("unchecked")
    public List<VehicleModelDTO> listVehicleModelByBrand(Long idVehicleBrand) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT listVehicleModelByBrand ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT vme.id, vme.description                 ");
            sql.append(" FROM VehicleModelEntity vme                    ");
            sql.append(" WHERE vme.vehicleBrand.id = :idVehicleBrand    ");
            sql.append(" ORDER BY upper(vme.description)                ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("idVehicleBrand", idVehicleBrand);
            
            List<Object[]> models = query.getResultList();
            List<VehicleModelDTO> listReturn = new ArrayList<VehicleModelDTO>();
            
            for(Object[] row : models){
                VehicleModelDTO dto = new VehicleModelDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setDescription((String) row[1]);
                
                listReturn.add(dto);
            }
            
            LOGGER.debug(" >> END listVehicleModelByBrand ");
            return listReturn;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }      
        
    }
    
}
