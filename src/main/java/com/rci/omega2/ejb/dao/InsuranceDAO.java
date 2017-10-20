package com.rci.omega2.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dto.InsuranceDossierRequestDTO;
import com.rci.omega2.ejb.dto.InsuranceDossierSoldDTO;
import com.rci.omega2.ejb.dto.InsuranceStatusDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

public class InsuranceDAO extends BaseDAO{

    private final String TEMP_01 = "Venda à Vista";
    private final String TEMP_02 = "Financiamento";
    
    @SuppressWarnings("unchecked")
    public List<InsuranceStatusDTO> findInsuranceStatus() throws UnexpectedException{
            
        Query query = super.getEntityManager().createNativeQuery(" SELECT U.CAR_INSURANCE_STATUS_ID, U.DESCRIPTION FROM TB_CAR_INSURANCE_STATUS U");
          
        List<Object[]> status = query.getResultList(); 
          
        List<InsuranceStatusDTO> listStatus = new ArrayList<InsuranceStatusDTO>(); 
          
          for(Object[] row : status){
              
              InsuranceStatusDTO dto = new InsuranceStatusDTO();
              dto.setCar_insurance_status_id(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
              dto.setDescription(row[1].toString());
              listStatus.add(dto);
              
          }
          
          return listStatus;
          
   }
    
    @SuppressWarnings("unchecked")
    public List<InsuranceDossierSoldDTO> findDossierSold(InsuranceDossierRequestDTO request) throws UnexpectedException{
            
        StringBuilder sql = new  StringBuilder();
        
        sql.append("    SELECT CI.CAR_INSURANCE_ID, CASE WHEN DO.IMPORT_CODE_OMEGA IS NULL THEN DO.DOSSIER_ID ELSE DO.IMPORT_CODE_OMEGA END AS NUM_DOSSIER, DO.ADP, CI.CPF_CNPJ, CI.DT_INSERT, CI.PREMIUM, CIS.DESCRIPTION, CT.NAME_CLIENT ");
        sql.append(" , CASE WHEN DO.PERSON_ID_DOSSIER_MANAGER = DO.PERSON_ID_SALESMAN then p_manager.NAME_PERSON  ");
        sql.append("    else p_manager.NAME_PERSON || ' / ' || p_vend.NAME_PERSON end as VENDEDOR  ");
        sql.append("    , DO.DOSSIER_ID ");
        sql.append("    FROM  TB_PROPOSAL PR                 "); 
        sql.append("    INNER JOIN TB_CAR_INSURANCE CI ON PR.PROPOSAL_ID = CI.PROPOSAL_ID   "); 
        sql.append("    INNER JOIN TB_CAR_INSURANCE_STATUS CIS ON CI.CAR_INSURANCE_STATUS_ID = CIS.CAR_INSURANCE_STATUS_ID");
        sql.append("    INNER JOIN TB_DOSSIER DO ON PR.DOSSIER_ID = DO.DOSSIER_ID                                         ");
        sql.append("    INNER JOIN TB_USER US ON CI.USER_ID = US.USER_ID                                                  ");
        sql.append("    INNER JOIN TB_PERSON PR ON US.USER_ID = PR.USER_ID                                                ");
        sql.append("    inner join TB_PERSON p_manager on p_manager.PERSON_ID = DO.PERSON_ID_DOSSIER_MANAGER              ");
        sql.append("    inner join TB_PERSON p_vend on p_vend.PERSON_ID = DO.PERSON_ID_SALESMAN                           ");
        sql.append("    INNER JOIN TB_CUSTOMER CT ON CT.CUSTOMER_ID = DO.CUSTOMER_ID"); 
        sql.append("    WHERE   1 = 1  ");
        
        
        if(!AppUtil.isNullOrEmpty(request.getNumberProposal())){
            sql.append(" AND CI.CAR_INSURANCE_ID = :numberProposal  ");
        } 
        if(!AppUtil.isNullOrEmpty(request.getProposal())){
            sql.append("  AND PR.PROPOSAL_ID = :proposal  ");
        }
        if(!AppUtil.isNullOrEmpty(request.getAdp())){
            sql.append(" AND  DO.ADP = :adp               ");
        }
        if(!AppUtil.isNullOrEmpty(request.getSalesMan())){
            sql.append("  AND PR.NAME_PERSON = :salesMan   ");
        }
        if(!AppUtil.isNullOrEmpty(request.getStatusSeguro())){
            sql.append("  AND  CIS.CAR_INSURANCE_STATUS_ID = :statusSeguro  ");
        }
        if(!AppUtil.isNullOrEmpty(request.getDateCreationInit()) && !AppUtil.isNullOrEmpty(request.getDateCreationEnd())){
            sql.append("  AND CI.DT_INSERT BETWEEN :dateCreationInit AND :dateCreationEnd ");
        }
        

        Query query = super.getEntityManager().createNativeQuery(sql.toString()).setMaxResults(200);
        
        if(!AppUtil.isNullOrEmpty(request.getNumberProposal())){
            query.setParameter("numberProposal", request.getNumberProposal());
        }
        if(!AppUtil.isNullOrEmpty(request.getProposal())){
            query.setParameter("proposal", request.getProposal());
        }
        if(!AppUtil.isNullOrEmpty(request.getAdp())){
            query.setParameter("adp", request.getAdp());
        }
        if(!AppUtil.isNullOrEmpty(request.getDateCreationInit())){
            query.setParameter("dateCreationInit", request.getDateCreationInit());
        }
        if(!AppUtil.isNullOrEmpty(request.getDateCreationEnd())){
            query.setParameter("dateCreationEnd", request.getDateCreationEnd());
        }
        if(!AppUtil.isNullOrEmpty(request.getSalesMan())){
            query.setParameter("salesMan", request.getSalesMan());
        }
        if(!AppUtil.isNullOrEmpty(request.getStatusSeguro())){
            query.setParameter("statusSeguro", request.getStatusSeguro());
        }
        
        List<InsuranceDossierSoldDTO> list = new ArrayList<InsuranceDossierSoldDTO>();
        
        List<Object[]> results = query.getResultList();   
        
        for(Object[] row : results){
            InsuranceDossierSoldDTO dto = new InsuranceDossierSoldDTO();
            dto.setNumberProposal(((BigDecimal) row[0]).toString());
            dto.setProposal(((BigDecimal) row[1]).toString());
            dto.setSaleTypeId(AppUtil.isNullOrEmpty(dto.getProposal()) ? TEMP_01 : TEMP_02);
            dto.setAdp((String) row[2]);
            dto.setCpf_cnpj((String) row[3]);
            dto.setDate((Date) row[4]);
            dto.setPremium(((BigDecimal) row[5]).toString());
            dto.setStatusSeguro((String) row[6]);
            dto.setClient((String) row[7]);
            dto.setSalesMan((String) row[8]);
            dto.setDossierId(((BigDecimal) row[9]).toString());
         
            list.add(dto);
        }
         
        return list;
    }
     
    
}
