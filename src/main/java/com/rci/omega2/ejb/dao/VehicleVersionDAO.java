package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.VehicleVersionDTO;
import com.rci.omega2.ejb.dto.VehicleVersionGroupDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.VehicleVersionEntity;
import com.rci.omega2.entity.enumeration.VehicleTypeEnum;

public class VehicleVersionDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(VehicleVersionDAO.class);
    
    
    @SuppressWarnings("unchecked")
    public List<VehicleVersionGroupDTO> findListByModelAndVehicleType(String fipe, String vehicleType) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findListByModelAndVehicleType ");
            
            Query query = super.getEntityManager().createQuery("select  v.fipe, v.description "
                    + "from VehicleVersionEntity v "
                    + "join v.vehicleModel m "
                    + "where v.vehicleModel.fipe = :fipe "
                    + "and v.vehicleType = :vehicleType "
                    + "and active = 1 "
                    + "group by v.fipe, v.description "
                    + "order by v.description");
            query.setParameter("fipe",fipe);
            query.setParameter("vehicleType", VehicleTypeEnum.valueOf(vehicleType));
            
            List<Object[]>versions = query.getResultList();
            List<VehicleVersionGroupDTO> listResult = new ArrayList<VehicleVersionGroupDTO>();
            
            for(Object[]version : versions){
                VehicleVersionGroupDTO dto = new VehicleVersionGroupDTO();
                //dto.setId(CriptoUtilsOmega2.encrypt(version[0].toString()));
                dto.setFipe(version[0].toString());
                dto.setDescription(version[1].toString());
                
                listResult.add(dto);
            }
            
            LOGGER.debug(" >> END findListByModelAndVehicleType ");
            return listResult;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
            
    }
    
    @SuppressWarnings("unchecked")
    public List<VehicleVersionDTO> findModelYears(String description, String fipe, String vehicleType, Integer manufactureYear) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findModelYears ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" select v.id, v.modelYear           ");
            sql.append(" from VehicleVersionEntity v        ");
            sql.append(" join v.vehicleModel m              ");
            sql.append(" where v.description = :description ");
            sql.append(" and v.fipe = :fipe                 ");
            sql.append(" and v.vehicleType = :vehicleType   ");
            sql.append(" and v.active = 1                   ");
            
            if (manufactureYear != null) {
                sql.append(" and v.manufactureYear = :manufactureYear ");
            }
            sql.append(" group by v.id,v.modelYear ");
            sql.append(" order by v.modelYear desc ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("description", description);
            query.setParameter("fipe", fipe);
            query.setParameter("vehicleType", VehicleTypeEnum.valueOf(vehicleType));
            
            if (manufactureYear != null) {
                query.setParameter("manufactureYear", manufactureYear);
            }
            
            List<VehicleVersionDTO> list = new ArrayList<VehicleVersionDTO>();
            
            List<Object[]>versions = query.getResultList();
            
            for(Object[]version : versions){
                VehicleVersionDTO dto = new VehicleVersionDTO();
                dto.setVersionId(CriptoUtilsOmega2.encrypt(version[0].toString()));
                dto.setYearModel(Integer.valueOf(version[1].toString()));
                
                list.add(dto);
            }
            
            LOGGER.debug(" >> END findModelYears ");
            return list;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }
    
    @SuppressWarnings("unchecked")
    public List<Integer> findManufactureYears(String description, String fipe, String vehicleType) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findManufactureYears ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" select v.manufactureYear           ");
            sql.append(" from VehicleVersionEntity v        ");
            sql.append(" join v.vehicleModel m              ");
            sql.append(" where v.description = :description ");
            sql.append(" and v.fipe = :fipe                 ");
            sql.append(" and v.vehicleType = :vehicleType   ");
            sql.append(" and v.active = 1                   ");
            sql.append(" group by v.manufactureYear         ");
            sql.append(" order by v.manufactureYear desc    ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("fipe", fipe);
            query.setParameter("vehicleType", VehicleTypeEnum.valueOf(vehicleType));
            query.setParameter("description", description);
            
            List<Integer> list = new ArrayList<Integer>();
            
            list = query.getResultList();
            
            LOGGER.debug(" >> END findManufactureYears ");
            return list;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }
    
    @SuppressWarnings("unchecked")
    public List<VehicleVersionDTO> findListVehicleVersionByModel(Long vehicleModelId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findListVehicleVersionByModel ");
            
            Query query = super.getEntityManager().createQuery(" SELECT vve.id, vve.description "
                    +" FROM VehicleVersionEntity vve "
                    +" WHERE vve.vehicleModel.id = :vehicleModelId ");
            
            query.setParameter("vehicleModelId", vehicleModelId);
            
            List<Object[]> rows = query.getResultList();
            List<VehicleVersionDTO> listResult = new ArrayList<VehicleVersionDTO>();
            
            for(Object[] row : rows ){
                VehicleVersionDTO dto = new VehicleVersionDTO();
                dto.setVersionId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setDescription(row[1].toString());
                
                listResult.add(dto);
            }
            
            LOGGER.debug(" >> END findListVehicleVersionByModel ");
            return listResult;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
    @SuppressWarnings("unchecked")
    public VehicleVersionEntity findVehicleVersion(String fipe, Integer modelYear, Integer manufactureYear) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findVehicleVersion ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append(" SELECT vv FROM VehicleVersionEntity vv                 ");
            sql.append("    WHERE vv.fipe like :fipe                            ");
            sql.append("         AND vv.modelYear = :modelYear                  ");
            sql.append("         AND vv.manufactureYear = :manufactureYear      ");
            
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("fipe", fipe + "%");
            query.setParameter("modelYear", modelYear);
            query.setParameter("manufactureYear", manufactureYear);
            
            List<VehicleVersionEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findVehicleVersion ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }      
        
    }

    @SuppressWarnings("unchecked")
    public List<VehicleVersionGroupDTO> findListByVehicleType(Long modelId, String vehicleType) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findListByVehicleType ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" select  v.fipe, v.description,v.vehicleGender  ");
            sql.append(" from VehicleVersionEntity v                    ");
            sql.append(" join v.vehicleModel m                          ");
            sql.append(" where v.vehicleType = :vehicleType             ");
            sql.append(" and v.vehicleModel.id = :modelId               ");
            sql.append(" and active = 1                                 ");
            sql.append(" group by v.fipe, v.description,v.vehicleGender ");
            sql.append(" order by v.description                         ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());
            
            query.setParameter("vehicleType", VehicleTypeEnum.valueOf(vehicleType));
            query.setParameter("modelId", modelId);
            
            List<Object[]>versions = query.getResultList();
            List<VehicleVersionGroupDTO> listResult = new ArrayList<VehicleVersionGroupDTO>();
            
            for(Object[]version : versions){
                VehicleVersionGroupDTO dto = new VehicleVersionGroupDTO();
                dto.setFipe(version[0].toString());
                dto.setDescription(version[1].toString());
                dto.setGender(version[2].toString());
                listResult.add(dto);
            }
            
            LOGGER.debug(" >> END findListByVehicleType ");
            return listResult;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }      
        
    }
    
}
