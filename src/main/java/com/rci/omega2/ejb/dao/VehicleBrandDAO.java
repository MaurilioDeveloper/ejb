package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.VehicleBrandDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.enumeration.VehicleTypeEnum;

public class VehicleBrandDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(VehicleBrandDAO.class);
    
    @SuppressWarnings("unchecked")
    public List<VehicleBrandDTO> findListByVehicleType(String vehicleType) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findListByVehicleType ");
            
            Query query = super.getEntityManager().createQuery("select b.id, b.description, b.importCode from VehicleVersionEntity v "
                    + "join v.vehicleModel m "
                    + "join m.vehicleBrand b "
                    + "where v.vehicleType = :vehicleType "
                    + "group by b.id, b.description, b.importCode "
                    + "order by b.description ");
            
            query.setParameter("vehicleType", VehicleTypeEnum.valueOf(vehicleType));
            
            List<Object[]> listResult = query.getResultList();
            List<VehicleBrandDTO> listBrands = new ArrayList<VehicleBrandDTO>();
            
            for(Object[]brand : listResult){
                VehicleBrandDTO dto = new VehicleBrandDTO();
                
                dto.setId(CriptoUtilsOmega2.encrypt(Long.valueOf(brand[0].toString())));
                dto.setDescription(brand[1].toString());
                
                String importCode = brand[2].toString();
                
                boolean showDirectSale = (importCode.equals("R1") ||importCode.equals("NI"));
                dto.setShowDirectSale(showDirectSale);
                
                listBrands.add(dto);
            }
            
            LOGGER.debug(" >> END findListByVehicleType ");
            return listBrands;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
    @SuppressWarnings("unchecked")
    public List<VehicleBrandDTO> findListByVehicleBrand() throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findListByVehicleBrand ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT vb.id, vb.description,                                                                              ");
            sql.append(" CASE WHEN vb.description = :nissan OR vb.description = :renault THEN 1 ELSE 2 END AS orderByNissanRenault  ");
            sql.append(" FROM VehicleBrandEntity vb                                                                                 ");
            sql.append(" ORDER BY orderByNissanRenault, upper(vb.description)                                                       ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            
            query.setParameter("nissan", "NISSAN");
            query.setParameter("renault", "RENAULT");
            
            List<Object[]> rows = query.getResultList();
            List<VehicleBrandDTO> listBrands = new ArrayList<VehicleBrandDTO>();
            
            for(Object[]row : rows){
                VehicleBrandDTO dto = new VehicleBrandDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[0].toString())));
                dto.setDescription(row[1].toString());
                
                listBrands.add(dto);
            }
            
            LOGGER.debug(" >> END findListByVehicleBrand ");
            return listBrands;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }

}
