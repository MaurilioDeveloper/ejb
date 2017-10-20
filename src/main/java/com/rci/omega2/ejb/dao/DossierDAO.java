package com.rci.omega2.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dto.DossierDTO;
import com.rci.omega2.ejb.dto.DossierFilterDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.DossierEntity;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;
import com.rci.omega2.entity.enumeration.StatusSyncStateEnum;
import com.rci.omega2.entity.enumeration.StructureTypeEnum;

public class DossierDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(DossierDAO.class);
    
    @SuppressWarnings("unchecked")
    public List<DossierDTO> findDossierFromMyProposal(DossierFilterDTO dossierFilter) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDossierFromMyProposal ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT DISTINCT ");
            sql.append(" PS.NAME_PERSON, ");
            sql.append(" CASE WHEN D.IMPORT_CODE_OMEGA IS NULL THEN D.DOSSIER_ID ELSE D.IMPORT_CODE_OMEGA END AS NUM_DOSSIER, ");
            sql.append(" D.ADP, ");
            sql.append(" C.CPF_CNPJ, ");
            sql.append(" C.NAME_CLIENT, ");
            sql.append(" DS.DESCRIPTION AS DESCRIPTION_STATUS , ");
            sql.append(" D.EXPIRY_DATE, ");
            sql.append(" VV.DESCRIPTION AS DESCRIPTION_VEHICLE , ");
            sql.append(" D.DOSSIER_ID, ");
            sql.append(" ST.DESCRIPTION, ");
            sql.append(" D.DT_INSERT, ");
            sql.append(" VM.DESCRIPTION AS DESCRIPTION_MODEL ");
            sql.append(" FROM TB_PROPOSAL P ");
            sql.append(" INNER JOIN TB_DOSSIER D ON D.DOSSIER_ID = P.DOSSIER_ID ");
            sql.append(" INNER JOIN TB_STRUCTURE S ON S.STRUCTURE_ID = D.STRUCTURE_ID ");
            sql.append(" INNER JOIN TB_DOSSIER_STATUS DS ON DS.DOSSIER_STATUS_ID = D.DOSSIER_STATUS_ID ");
            sql.append(" INNER JOIN TB_PERSON PS ON PS.PERSON_ID = D.PERSON_ID_SALESMAN ");
            sql.append(" INNER JOIN TB_CUSTOMER C ON C.CUSTOMER_ID = D.CUSTOMER_ID ");
            sql.append(" INNER JOIN TB_DOSSIER_VEHICLE DV ON DV.DOSSIER_VEHICLE_ID = D.DOSSIER_VEHICLE_ID "); 
            sql.append(" INNER JOIN TB_VEHICLE_VERSION VV ON VV.VEHICLE_VERSION_ID = DV.VEHICLE_VERSION_ID ");
            sql.append(" INNER JOIN TB_VEHICLE_MODEL VM ON VM.VEHICLE_MODEL_ID = VV.VEHICLE_MODEL_ID ");
            sql.append(" INNER JOIN TB_SALE_TYPE ST ON ST.SALE_TYPE_ID = D.SALE_TYPE_ID ");
            sql.append(" WHERE 1 = 1 ");
        
            if(dossierFilter.getRegionalView()){
                sql.append(" AND (EXISTS (SELECT 1 FROM TB_REGIONAL_USER RU ");
                sql.append("              WHERE RU.REGIONAL_ID = S.REGIONAL_ID ");
                sql.append("              AND RU.USER_ID = :userId ");
                sql.append("              AND STRUCTURE_TYPE = :structureType )) ");
            }else{            
                sql.append(" AND (EXISTS (SELECT 1  FROM TB_STRUCTURE SP ");
                sql.append("                    WHERE STRUCTURE_TYPE = :structureType ");
                sql.append("                    AND SP.STRUCTURE_ID = S.STRUCTURE_ID ");
                sql.append("                    START WITH STRUCTURE_ID IN (SELECT S1.STRUCTURE_ID ");
                sql.append("                                                FROM TB_STRUCTURE S1 ");
                sql.append("                                                INNER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S1.STRUCTURE_ID ");
                sql.append("                                                WHERE US.USER_ID = :userId) ");
                sql.append("                    CONNECT BY PRIOR SP.STRUCTURE_ID = SP.STRUCTURE_ID_PARENT)) ");
            }

            if(!AppUtil.isNullOrEmpty(dossierFilter.getIdDossier())){            
                sql.append(" AND (D.DOSSIER_ID = :idDossier OR D.IMPORT_CODE_OMEGA = :idDossier ) ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getAdp())){              
                sql.append(" AND P.ADP = :adp ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDateCreationInit())){            
                sql.append(" AND D.DT_INSERT >= :dateCreationInit ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDateCreationEnd())){            
                sql.append(" AND D.DT_INSERT <= :dateCreationEnd ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getNameClient())){            
                sql.append(" AND C.NAME_CLIENT = :nameClient ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getCpfCnpj())){            
                sql.append(" AND C.CPF_CNPJ = :cpfCnpj ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getProposedStatus())){
                sql.append(" AND D.DOSSIER_STATUS_ID = :proposedStatus ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDealership())){            
                sql.append(" AND D.STRUCTURE_ID = :dealership ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getSalesman())){            
                sql.append(" AND D.PERSON_ID_SALESMAN = :salesman ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDateExpirationInit())){            
                sql.append(" AND D.EXPIRY_DATE >= :dateExpirationInit ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDateExpirationEnd())){            
                sql.append(" AND D.EXPIRY_DATE <= :dateExpirationEnd ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getTypePerson()) && !dossierFilter.getTypePerson().equals(PersonTypeEnum.ALL.name())){            
                sql.append(" AND C.PERSON_TYPE = :typePerson ");
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getSaleTypeId())){            
                sql.append(" AND D.SALE_TYPE_ID = :saleTypeId ");
            }
            if(dossierFilter.getTaxTc()){
                sql.append(" AND D.TC_EXEMPT = 1 ");
            }
            sql.append(" ORDER BY  D.DT_INSERT DESC ");
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString()).setMaxResults(200);
            
            query.setParameter("userId", dossierFilter.getUserId());
            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());
          
            if(!AppUtil.isNullOrEmpty(dossierFilter.getIdDossier())){            
                query.setParameter("idDossier", dossierFilter.getIdDossier());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getAdp())){              
                query.setParameter("adp", dossierFilter.getAdp());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDateCreationInit())){            
                query.setParameter("dateCreationInit", dossierFilter.getDateCreationInit());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDateCreationEnd())){            
                query.setParameter("dateCreationEnd", dossierFilter.getDateCreationEnd());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getNameClient())){            
                query.setParameter("nameClient", dossierFilter.getNameClient());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getCpfCnpj())){            
                query.setParameter("cpfCnpj", dossierFilter.getCpfCnpj());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getProposedStatus())){
                query.setParameter("proposedStatus", dossierFilter.getProposedStatus());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDealership())){            
                query.setParameter("dealership", dossierFilter.getDealership());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getSalesman())){            
                query.setParameter("salesman", dossierFilter.getSalesman());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDateExpirationInit())){            
                query.setParameter("dateExpirationInit", dossierFilter.getDateExpirationInit());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getDateExpirationEnd())){            
                query.setParameter("dateExpirationEnd", dossierFilter.getDateExpirationEnd());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getTypePerson()) && !dossierFilter.getTypePerson().equals(PersonTypeEnum.ALL.name())){            
                query.setParameter("typePerson", dossierFilter.getTypePerson());
            }
            if(!AppUtil.isNullOrEmpty(dossierFilter.getSaleTypeId())){
                query.setParameter("saleTypeId", dossierFilter.getSaleTypeId());
            }
        
            List<Object[]> dossiers = query.getResultList();   
            
            List<DossierDTO> listDossier = new ArrayList<DossierDTO>(); 
            
            for(Object[] row : dossiers){
                DossierDTO dossier = new DossierDTO();
                dossier.setNameSalesman((String) row[0]);
                dossier.setNumDossier(((BigDecimal) row[1]).toString());
                dossier.setAdp((String) row[2]);
                dossier.setCpfCnpj((String) row[3]);
                dossier.setNameClient((String) row[4]);
                dossier.setStatus((String) row[5]);
                dossier.setExpirationDate((Date) row[6]);
                
                dossier.setDossierId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[8].toString())));
                dossier.setSaleType((String) row[9]);
                
                StringBuilder model = new StringBuilder();
                model.append((String) row[11]);
                model.append(" - ");
                model.append((String) row[7]);
                
                dossier.setVehicleModel(model.toString());
                
                listDossier.add(dossier);
            }
        
            LOGGER.debug(" >> END findDossierFromMyProposal ");
            return listDossier;
        
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    
    @SuppressWarnings("unchecked")
    public List<DossierDTO> findDossierChanged() throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findDossierChanged ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT DISTINCT ");
            sql.append(" PS.NAME_PERSON, ");
            sql.append(" CASE WHEN D.IMPORT_CODE_OMEGA IS NULL THEN D.DOSSIER_ID ELSE D.IMPORT_CODE_OMEGA END AS NUM_DOSSIER, ");
            sql.append(" D.ADP, ");
            sql.append(" C.CPF_CNPJ, ");
            sql.append(" C.NAME_CLIENT, ");
            sql.append(" DS.DESCRIPTION AS DESCRIPTION_STATUS , ");
            sql.append(" D.EXPIRY_DATE, ");
            sql.append(" VV.DESCRIPTION AS DESCRIPTION_VEHICLE , ");
            sql.append(" D.DOSSIER_ID, ");
            sql.append(" ST.DESCRIPTION, ");
            sql.append(" D.DT_INSERT, ");
            sql.append(" P.FINANCED_AMOUNT, ");
            sql.append(" P.PRODUCT_ID, ");
            sql.append(" PS.PERSON_ID, ");
            sql.append(" D.STRUCTURE_ID ");
            sql.append(" FROM TB_PROPOSAL P ");
            sql.append(" INNER JOIN TB_DOSSIER D ON D.DOSSIER_ID = P.DOSSIER_ID ");
            sql.append(" INNER JOIN TB_STRUCTURE S ON S.STRUCTURE_ID = D.STRUCTURE_ID ");
            sql.append(" INNER JOIN TB_DOSSIER_STATUS DS ON DS.DOSSIER_STATUS_ID = D.DOSSIER_STATUS_ID ");
            sql.append(" INNER JOIN TB_PERSON PS ON PS.PERSON_ID = D.PERSON_ID_SALESMAN ");
            sql.append(" INNER JOIN TB_CUSTOMER C ON C.CUSTOMER_ID = D.CUSTOMER_ID ");
            sql.append(" INNER JOIN TB_DOSSIER_VEHICLE DV ON DV.DOSSIER_VEHICLE_ID = D.DOSSIER_VEHICLE_ID "); 
            sql.append(" INNER JOIN TB_VEHICLE_VERSION VV ON VV.VEHICLE_VERSION_ID = DV.VEHICLE_VERSION_ID ");
            sql.append(" INNER JOIN TB_SALE_TYPE ST ON ST.SALE_TYPE_ID = D.SALE_TYPE_ID ");
            sql.append(" WHERE P.ADP IS NOT NULL ");
            sql.append(" AND D.STRUCTURE_CHANGED = :changed ");
            sql.append(" ORDER BY  D.DT_INSERT DESC ");
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString()).setMaxResults(200);
        
            query.setParameter("changed", Boolean.TRUE);
        
            List<Object[]> dossiers = query.getResultList();   
            List<DossierDTO> listDossier = new ArrayList<DossierDTO>(); 
        
            for(Object[] row : dossiers){
                DossierDTO dossier = new DossierDTO();
                dossier.setNameSalesman((String) row[0]);
                dossier.setNumDossier(((BigDecimal) row[1]).toString());
                dossier.setAdp((String) row[2]);
                dossier.setCpfCnpj((String) row[3]);
                dossier.setNameClient((String) row[4]);
                dossier.setStatus((String) row[5]);
                dossier.setExpirationDate((Date) row[6]);
                dossier.setVehicleModel((String) row[7]);
                dossier.setDossierId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[8].toString())));
                dossier.setSaleType((String) row[9]);
                dossier.setFinancedAmount((BigDecimal) row[11]);
                dossier.setProductId(getValueString(row[12]));
                dossier.setSalesmanId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[13].toString())));
                dossier.setStructureId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[14].toString())));
                listDossier.add(dossier);
            }
        
            LOGGER.debug(" >> END findDossierChanged ");
            return listDossier;
        
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    private String getValueString(Object obj){
        LOGGER.debug(" >> INIT getValueString ");
        if(obj == null){
            return null;
        }
        String temp = String.valueOf(obj);
        
        LOGGER.debug(" >> END getValueString ");
        return temp;
    }
    
    @SuppressWarnings("unchecked")
    public DossierDTO findDossierChangedById(Long idDossier) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDossierChangedById ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT DISTINCT ");
            sql.append(" PS.NAME_PERSON, ");
            sql.append(" CASE WHEN D.IMPORT_CODE_OMEGA IS NULL THEN D.DOSSIER_ID ELSE D.IMPORT_CODE_OMEGA END AS NUM_DOSSIER, ");
            sql.append(" D.ADP, ");
            sql.append(" C.CPF_CNPJ, ");
            sql.append(" C.NAME_CLIENT, ");
            sql.append(" DS.DESCRIPTION AS DESCRIPTION_STATUS , ");
            sql.append(" D.EXPIRY_DATE, ");
            sql.append(" VV.DESCRIPTION AS DESCRIPTION_VEHICLE , ");
            sql.append(" D.DOSSIER_ID, ");
            sql.append(" ST.DESCRIPTION, ");
            sql.append(" D.DT_INSERT, ");
            sql.append(" P.FINANCED_AMOUNT, ");
            sql.append(" P.PRODUCT_ID, ");
            sql.append(" PS.PERSON_ID, ");
            sql.append(" D.STRUCTURE_ID, ");
            sql.append(" DS.BIR ");
            sql.append(" FROM TB_PROPOSAL P ");
            sql.append(" INNER JOIN TB_DOSSIER D ON D.DOSSIER_ID = P.DOSSIER_ID ");
            sql.append(" INNER JOIN TB_STRUCTURE S ON S.STRUCTURE_ID = D.STRUCTURE_ID ");
            sql.append(" INNER JOIN TB_DEALERSHIP DS ON S.STRUCTURE_ID = DS.STRUCTURE_ID ");
            sql.append(" INNER JOIN TB_DOSSIER_STATUS DS ON DS.DOSSIER_STATUS_ID = D.DOSSIER_STATUS_ID ");
            sql.append(" INNER JOIN TB_PERSON PS ON PS.PERSON_ID = D.PERSON_ID_SALESMAN ");
            sql.append(" INNER JOIN TB_CUSTOMER C ON C.CUSTOMER_ID = D.CUSTOMER_ID ");
            sql.append(" INNER JOIN TB_DOSSIER_VEHICLE DV ON DV.DOSSIER_VEHICLE_ID = D.DOSSIER_VEHICLE_ID "); 
            sql.append(" INNER JOIN TB_VEHICLE_VERSION VV ON VV.VEHICLE_VERSION_ID = DV.VEHICLE_VERSION_ID ");
            sql.append(" INNER JOIN TB_SALE_TYPE ST ON ST.SALE_TYPE_ID = D.SALE_TYPE_ID ");
            sql.append(" WHERE P.ADP IS NOT NULL ");
            sql.append(" AND D.STRUCTURE_CHANGED = :changed ");
            sql.append(" AND (D.DOSSIER_ID = :idDossier OR D.IMPORT_CODE_OMEGA = :idDossier )  ");
            sql.append(" ORDER BY  D.DT_INSERT DESC ");
        
            Query query = super.getEntityManager().createNativeQuery(sql.toString()).setMaxResults(200);
        
            query.setParameter("idDossier", idDossier);
            query.setParameter("changed", Boolean.TRUE);
            
            List<Object[]> dossiers = query.getResultList();   
        
            DossierDTO dossier = new DossierDTO();
            
            for(Object[] row : dossiers){
                dossier.setNameSalesman((String) row[0]);
                dossier.setNumDossier(((BigDecimal) row[1]).toString());
                dossier.setAdp((String) row[2]);
                dossier.setCpfCnpj((String) row[3]);
                dossier.setNameClient((String) row[4]);
                dossier.setStatus((String) row[5]);
                dossier.setExpirationDate((Date) row[6]);
                dossier.setVehicleModel((String) row[7]);
                dossier.setDossierId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[8].toString())));
                dossier.setSaleType((String) row[9]);
                dossier.setFinancedAmount((BigDecimal) row[11]);
                dossier.setProductId((String) row[12].toString());
                dossier.setSalesmanId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[13].toString())));
                dossier.setStructureId(CriptoUtilsOmega2.encrypt(Long.valueOf(row[14].toString())));
                dossier.setBir((String) row[15].toString());
            }
        
            LOGGER.debug(" >> END findDossierChangedById ");
            return dossier;
        
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * 
     * @param adp
     * @return
     * @throws UnexpectedException 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public DossierEntity findDossierByAdp(String adp) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDossierByAdp ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("    SELECT ds FROM DossierEntity ds          ");
            sql.append("    LEFT JOIN FETCH ds.customer ct           ");
            sql.append("    LEFT JOIN FETCH ct.employer em           ");
            sql.append("    LEFT JOIN FETCH ct.employerAddress ea    ");
            sql.append("    LEFT JOIN FETCH ct.customerSpouse cs     ");
            sql.append("    LEFT JOIN FETCH ct.bankDetail bd         ");
            sql.append("    LEFT JOIN FETCH ds.dossierVehicle dv     ");
            sql.append("    LEFT JOIN FETCH dv.vehicleVersion vv     ");
            sql.append("    LEFT JOIN FETCH ds.listGuarantor lg      ");
            sql.append("    LEFT JOIN FETCH lg.employer em           ");
            sql.append("    LEFT JOIN FETCH lg.address ad            ");
            sql.append("    LEFT JOIN FETCH lg.employerAddress eg    ");
            sql.append("    LEFT JOIN FETCH lg.listPhone lp          ");
            sql.append("    WHERE ds.adp = :adp                      ");
        
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("adp", adp);
            
            List<DossierEntity> result = query.getResultList();
            
            if(!result.isEmpty()){
                return result.get(0);
            }
        
            LOGGER.debug(" >> END findDossierByAdp ");
            return null;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    public DossierEntity findOne(Long id) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findOne ");
            
            DossierEntity temp = super.find(DossierEntity.class, id);
            
            LOGGER.debug(" >> END findOne ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
           
    }
    
    public String findAdp(Long dossierId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAdp ");
            
            StringBuilder sql = new StringBuilder();
        
            sql.append("SELECT ds.adp FROM DossierEntity ds WHERE ds.id = :dossierId");
        
            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("dossierId", dossierId);
        
            Object adp = query.getSingleResult();   
            
            if(adp == null){
                return null;
            }
            
            String temp = String.valueOf(adp);
            LOGGER.debug(" >> END findAdp ");
            
            return temp;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    @SuppressWarnings("deprecation")
    public DossierEntity findDossierRootFetch(Long dossierId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDossierRootFetch ");
            
            Session session = this.getEntityManager().unwrap(Session.class);
            Criteria criteria = session.createCriteria(DossierEntity.class);
            criteria.add(Restrictions.eq("id", dossierId));
            
            criteria.setFetchMode("financialBrand", FetchMode.JOIN);
            criteria.setFetchMode("structure", FetchMode.JOIN);
            criteria.setFetchMode("dossierStatus", FetchMode.JOIN);
            criteria.setFetchMode("saleType", FetchMode.JOIN);
            criteria.setFetchMode("salesman", FetchMode.JOIN);
            criteria.setFetchMode("dossierManager", FetchMode.JOIN);
            criteria.setFetchMode("customer", FetchMode.JOIN);
            criteria.setFetchMode("dossierVehicle", FetchMode.JOIN);
            criteria.setFetchMode("dossierVehicle.vehicleVersion", FetchMode.JOIN);
            criteria.setFetchMode("dossierVehicle.vehicleVersion.vehicleModel", FetchMode.JOIN);
            
            criteria.setFetchMode("listDossierVehicleAccessory", FetchMode.JOIN);
            criteria.setFetchMode("listDossierVehicleOption", FetchMode.JOIN);
            criteria.setFetchMode("listGuarantor", FetchMode.JOIN);
            criteria.setFetchMode("listSpecialVehicleType", FetchMode.JOIN);
            
            DossierEntity entity = (DossierEntity) criteria.uniqueResult();
            
            LOGGER.debug(" >> END findDossierRootFetch ");
            return entity;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
