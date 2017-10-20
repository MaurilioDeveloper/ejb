package com.rci.omega2.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.ServiceDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.entity.ServiceEntity;

public class ServiceDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(ServiceDAO.class);
    
    public List<ServiceEntity> findAll() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAll ");
            
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("active", true);
            
            List<ServiceEntity> temp = super.findByCriteria(ServiceEntity.class, parameters);
            
            LOGGER.debug(" >> END findAll ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<ServiceDTO> findService(Long structureId, Long productId, String vehicleType, Long financeTypeId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findService ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT ");
            sql.append("     S.SERVICE_ID, "); 
            sql.append("     S.DESCRIPTION, "); 
            sql.append("     S.PERCENTAGE, "); 
            sql.append("     S.AMOUNT, "); 
            sql.append("     S.MAXIMUM_AMOUNT, "); 
            sql.append("     S.MINIMUM_AMOUNT, "); 
            sql.append("     S.SELECTED_DEFAULT, "); 
            sql.append("     ST.DESCRIPTION as servicetypeDescription, "); 
            sql.append("     COALESCE (SS.REQUIRED, 0) AS REQUIRED, "); 
            sql.append("     S.IMPORT_CODE_OMEGA, "); 
            sql.append("     ST.SERVICE_TYPE_ID as SERVICE_TYPE_ID, "); 
            sql.append("     CASE WHEN S.MAXIMUM_AMOUNT IS NULL AND S.MINIMUM_AMOUNT IS NULL THEN 0 ELSE 1 END AS EDITABLE "); 
            sql.append(" FROM TB_SERVICE S "); 
            sql.append(" INNER JOIN TB_SERVICE_TYPE ST ON ST.SERVICE_TYPE_ID = S.SERVICE_TYPE_ID "); 
            sql.append(" LEFT JOIN TB_SERVICE_STRUCTURE SS ON SS.SERVICE_ID = S.SERVICE_ID AND SS.STRUCTURE_ID = 183 AND SS.ACTIVE = 1 ");
            sql.append(" WHERE S.ACTIVE = 1 ");            
            sql.append(" AND ST.SERVICE_TYPE_ID <> 30 "); // -- Remove o cotizador            
            sql.append(" AND NOT EXISTS (SELECT 1 FROM TB_SERVICE_STRUCTURE ST WHERE ST.ACTIVE = 0 AND ST.SERVICE_ID = S.SERVICE_ID AND ST.STRUCTURE_ID = :structureId) "); // -- Vinculo na concessionaria ");
            sql.append(" AND ( ");            
            sql.append("         EXISTS (SELECT 1 FROM TB_SERVICE_PRODUCT SP WHERE SP.SERVICE_ID = S.SERVICE_ID AND SP.PRODUCT_ID = :productId) "); // -- Vinculo no produto ");
            sql.append("         OR ");
            sql.append("         ( ");            
            sql.append("                 EXISTS (SELECT 1 FROM TB_SERVICE_VEHICLE_TYPE SVT WHERE SVT.SERVICE_ID = S.SERVICE_ID AND SVT.VEHICLE_TYPE = :vehicleType ) "); // -- Vinculo no tipo de venda ");
            sql.append("                 AND ");            
            sql.append("                 EXISTS (SELECT 1 FROM TB_SERVICE_FINANCE_TYPE SFT WHERE SFT.SERVICE_ID = S.SERVICE_ID AND SFT.FINANCE_TYPE_ID = :financeType ) "); // -- Vinculo no tipo de financiamento ");
            sql.append("         ) ");
            sql.append(" ) ");
            sql.append(" ORDER BY S.SERVICE_ID ");            
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            
            // query.setParameter("active", Boolean.TRUE);
            query.setParameter("structureId", structureId);
            query.setParameter("productId", productId);
            query.setParameter("vehicleType", vehicleType);
            query.setParameter("financeType", financeTypeId);
            
            List<Object[]> services = query.getResultList(); 
            
            List<ServiceDTO> listService = new ArrayList<ServiceDTO>(); 
            
            for(Object[] row : services){
                ServiceDTO dto = new ServiceDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setDescription((String) row[1]);
                dto.setPercentage((BigDecimal) row[2]);
                dto.setAmount((BigDecimal) row[3]);
                dto.setMaxAmount((BigDecimal) row[4]);
                dto.setMinAmount((BigDecimal) row[5]);
                dto.setSelecetedDefault((Boolean) GeneralUtils.convertBoolean((BigDecimal) row[6]));
                dto.setDescriptionType((String) row[7]);
                dto.setRequired((Boolean) GeneralUtils.convertBoolean((BigDecimal) row[8]));
                dto.setImportCodeOmega(Long.parseLong( row[9].toString() ));
                dto.setServiceTypeId(Long.parseLong( row[10].toString() ));
                dto.setEditable(GeneralUtils.convertBoolean((BigDecimal) row[11]));
                
                listService.add(dto);
            }
            
            LOGGER.debug(" >> END findService ");
            return listService;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
    @SuppressWarnings("unchecked")
    public List<ServiceDTO> findServiceStructure(Long structureId) throws Exception {
        try {
            LOGGER.debug(" >> INIT findServiceStructure ");
            
            Query query = super.getEntityManager().createNativeQuery(
                    " SELECT "
                            +"   S.SERVICE_ID, "
                            +"   S.DESCRIPTION, "
                            +"   COALESCE (SS.ACTIVE, 1) AS ACTIVE, "
                            +"   COALESCE (SS.REQUIRED, 0), "
                            +"   COALESCE (SS.SERVICE_STRUCTURE_ID, 0) "
                            +" FROM TB_SERVICE S "
                            +" LEFT JOIN TB_SERVICE_STRUCTURE SS ON SS.SERVICE_ID = S.SERVICE_ID AND SS.STRUCTURE_ID = :structureId "
                            +" WHERE S.ACTIVE = :active ");
            
            query.setParameter("active", Boolean.TRUE);
            query.setParameter("structureId", structureId);
            List<Object[]> services = query.getResultList(); 
            
            List<ServiceDTO> listService = new ArrayList<ServiceDTO>(); 
            
            for(Object[] row : services){
                ServiceDTO dto = new ServiceDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setDescription((String) row[1]);
                dto.setActiveStructure((Boolean) row[2].equals(new BigDecimal(1))?true:false);
                dto.setRequired((Boolean) GeneralUtils.convertBoolean((BigDecimal) row[3]));
                dto.setIdServiceStructure(CriptoUtilsOmega2.encrypt(Long.valueOf((row[4]).toString())));
                dto.setIdStructure(CriptoUtilsOmega2.encrypt(structureId));
                listService.add(dto);
            }
            
            LOGGER.debug(" >> END findServiceStructure ");
            return listService;    
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    

    /**
     * 
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public ServiceEntity findByImportCodeOmega(String importCodeOmega)throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findByImportCodeOmega ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append(" SELECT sv FROM ServiceEntity sv                ");
            sql.append("    WHERE sv.importCodeOmega = :importCode      ");
            
            Query query =  super.getEntityManager()
                    .createQuery(sql.toString());
            query.setParameter("importCode", importCodeOmega);
            
            List<ServiceEntity> ls = query.getResultList();
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findByImportCodeOmega ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
