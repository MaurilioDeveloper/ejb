package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import com.rci.omega2.common.exception.CryptoException;
import com.rci.omega2.ejb.dto.CarInsuranceDTO;
import com.rci.omega2.ejb.dto.InsuranceDeniedDTO;
import com.rci.omega2.ejb.utils.AppDtoUtils;
import com.rci.omega2.entity.CarInsuranceEntity;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;
import com.rci.omega2.entity.enumeration.StatusSantanderEnum;

public class CarInsuranceDAO extends BaseDAO{

@SuppressWarnings("unchecked")
public List<CarInsuranceEntity> getCanceled(InsuranceDeniedDTO filters) throws CryptoException{
       
        StringBuilder sql = new StringBuilder();
        
        sql.append("    SELECT CI.CAR_INSURANCE_ID, CASE WHEN DO.IMPORT_CODE_OMEGA IS NULL THEN DO.DOSSIER_ID ELSE DO.IMPORT_CODE_OMEGA END AS NUM_DOSSIER, DO.ADP, CI.CPF_CNPJ, CI.DT_INSERT, CI.PREMIUM, CIS.DESCRIPTION, CT.NAME_CLIENT ");
        sql.append(" , CASE WHEN DO.PERSON_ID_DOSSIER_MANAGER = DO.PERSON_ID_SALESMAN then p_manager.NAME_PERSON  ");
        sql.append("    else p_manager.NAME_PERSON || ' / ' || p_vend.NAME_PERSON end as VENDEDOR  ");
        sql.append("    , DO.DOSSIER_ID ");
        sql.append("    , DST.DOSSIER_STATUS_ID");
        sql.append("    FROM  TB_PROPOSAL PR                 "); 
        sql.append("    INNER JOIN TB_CAR_INSURANCE CI ON PR.PROPOSAL_ID = CI.PROPOSAL_ID   "); 
        sql.append("    INNER JOIN TB_CAR_INSURANCE_STATUS CIS ON CI.CAR_INSURANCE_STATUS_ID = CIS.CAR_INSURANCE_STATUS_ID");
        sql.append("    INNER JOIN TB_DOSSIER DO ON PR.DOSSIER_ID = DO.DOSSIER_ID                                         ");
        sql.append("    INNER JOIN TB_USER US ON CI.USER_ID = US.USER_ID                                                  ");
        sql.append("    INNER JOIN TB_PERSON PR ON US.USER_ID = PR.USER_ID                                                ");
        sql.append("    INNER JOIN TB_PERSON p_manager on p_manager.PERSON_ID = DO.PERSON_ID_DOSSIER_MANAGER              ");
        sql.append("    INNER JOIN TB_PERSON p_vend on p_vend.PERSON_ID = DO.PERSON_ID_SALESMAN                           ");
        sql.append("    INNER JOIN TB_CUSTOMER CT ON CT.CUSTOMER_ID = DO.CUSTOMER_ID                                      "); 
        sql.append("    INNER JOIN TB_DOSSIER_STATUS DST ON DO.DOSSIER_STATUS_ID = DST.DOSSIER_STATUS_ID                  ");
        sql.append("    WHERE   1 = 1  ");
        
        
       
       
       sql.append(" SELECT ci FROM CarInsuranceEntity AS ci ");
       sql.append(" join fetch ci.proposal as pr ");
       sql.append(" join fetch pr.dossier as do ");
       sql.append(" join fetch do.customer as ct ");
       sql.append(" join fetch ct.structure as st ");
       sql.append(" join fetch st.dealership as ht ");
       sql.append(" where do.statusSantander = :statsCancel ");
 
       
//       if(filters.getIdDossier() > 0){
//           sql.append(" and do.id = :idDossier "); 
//       }
       if(AppDtoUtils.isNotNullAndNotEmpty(filters.getAdp())){
           sql.append(" and do.adp = :adpDossier "); 
       }
       
       if(AppDtoUtils.isNotNullAndNotEmpty(filters.getTypePerson())){
           sql.append(" and ct.personType = :personType "); 
       }

       if(filters.getDateExpirationInit() != null || filters.getDateExpirationEnd() != null){
           sql.append(" and do.expiryDate BETWEEN :dateExpirationInit and :dateExpirationEnd"); 
       }
       
       if(filters.getDateCreationInit() != null || filters.getDateCreationEnd() != null){
           sql.append(" and do.includeDate BETWEEN :dateCreationInit and :dateCreationEnd");
       }
       
       Query query = super.getEntityManager().createQuery( sql.toString());
       
       query.setParameter("statsCancel", StatusSantanderEnum.NA);
//       if(filters.getIdDossier() > 0 ){
//           query.setParameter("idDossier",filters.getIdDossier());
//           
//       }
       if(AppDtoUtils.isNotNullAndNotEmpty(filters.getAdp())){
           query.setParameter("adpDossier",filters.getAdp());
       }
       
       if(AppDtoUtils.isNotNullAndNotEmpty(filters.getTypePerson())){
           query.setParameter("personType",   PersonTypeEnum.getType(filters.getTypePerson()));
       }
       
       if(filters.getDateExpirationInit() != null || filters.getDateExpirationEnd() != null){
           query.setParameter("dateExpirationInit",  filters.getDateExpirationInit());
           query.setParameter("dateExpirationEnd",   filters.getDateCreationEnd());
       }
       
       
       if(filters.getDateCreationInit() != null || filters.getDateCreationEnd() != null){
           sql.append(" and do.includeDate BETWEEN :dateCreationInit and :dateCreationEnd");
           query.setParameter("dateCreationInit",  filters.getDateCreationInit());
           query.setParameter("dateCreationEnd",   filters.getDateCreationEnd());
       }

//       return  query.getResultList();
       return null;
   }
    
    public List<CarInsuranceEntity> findByFilter(CarInsuranceDTO filters) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ci FROM CarInsuranceEntity AS ci ");
        sql.append(" join fetch ci.proposal as pr ");
        sql.append(" join fetch pr.dossier as do ");
        sql.append(" join fetch do.customer as ct ");
        sql.append(" join fetch ct.structure as st ");
        sql.append(" join fetch st.dealership as ht ");
        sql.append(" where 1 = 1 ");
        
        if (filters.getProposalId() > 0) {
            sql.append(" and ci.proposal.id = :proposalId "); 
        }

        if (filters.getDateCreationInit() != null && filters.getDateCreationEnd() != null) {
            sql.append(" and ci.proposal.id between :dateCreationInit and :dateCreationEnd "); 
        }
        
        Query query = super.getEntityManager().createQuery(sql.toString());

        if(filters.getProposalId() > 0){
            query.setParameter("proposalId", filters.getProposalId()); 
        }

        if (filters.getDateCreationInit() != null && filters.getDateCreationEnd() != null) {
            query.setParameter("dateCreationInit", filters.getDateCreationInit());
            query.setParameter("dateCreationEnd", filters.getDateCreationEnd());
        }
        
        return query.getResultList();
    }

}
