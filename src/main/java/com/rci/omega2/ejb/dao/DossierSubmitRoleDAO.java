package com.rci.omega2.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.DossierSubmitRoleDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

public class DossierSubmitRoleDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(DossierSubmitRoleDAO.class);
    
    @SuppressWarnings("unchecked")
    public List<DossierSubmitRoleDTO> findRoleStructure(Long structureId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findRoleStructure ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT ");
            sql.append("   UT.USER_TYPE_ID, ");
            sql.append("   UT.DESCRIPTION, ");
            // U1 traz a quantidade de usuarios da concecionaria por tipo de usuario.
            sql.append("   COUNT(U1.USER_ID) AS USER_STRUCTURE, ");
            // U2 traz a quantidade de usuarios da concecionaria por tipo de usuario que tem permissão de enviar proposta.
            sql.append("   COUNT(U2.USER_ID) AS USER_ROLE_ENVIAR, ");
            sql.append("   CASE  ");
            // Se a concecionaria nao tem usuario para aquele tipo de usuario devolve "null".
            sql.append("     WHEN COUNT(U1.USER_ID) = 0 THEN NULL ");
            // Se a concecionaria nao tem usuario com permissão de envio para aquele tipo de usuario devolve 0 (false).
            sql.append("     WHEN COUNT(U2.USER_ID) = 0 THEN 0  ");
            // Se todos os usuario da concecionario com aquele tipo de usuario tem permissão devolve 1 (true).
            sql.append("     WHEN COUNT(U1.USER_ID) = COUNT(U2.USER_ID) THEN 1  ");
            // Se nem todos os usuario da concecionario com aquele tipo de usuario tem permissão devolve "null".
            sql.append("     ELSE NULL  ");
            sql.append("   END AS SUBMISSAO ");
            sql.append(" FROM TB_USER_TYPE UT ");
            sql.append(" LEFT JOIN (SELECT U.* FROM TB_USER U  ");
            sql.append("            INNER JOIN TB_USER_STRUCTURE US ON US.USER_ID = U.USER_ID AND US.STRUCTURE_ID = :structureId) ");
            sql.append("            U1 ON U1.USER_TYPE_ID = UT.USER_TYPE_ID ");
            sql.append(" LEFT JOIN (SELECT U.* FROM TB_USER U ");
            sql.append("            INNER JOIN TB_USER_STRUCTURE US ON US.USER_ID = U.USER_ID AND US.STRUCTURE_ID = :structureId ");
            sql.append("            INNER JOIN TB_USER_PROFILE UP ON UP.USER_ID = U.USER_ID ");
            sql.append("            INNER JOIN TB_PROFILE P ON P.PROFILE_ID = UP.PROFILE_ID ");
            sql.append("            INNER JOIN TB_PROFILE_ROLE PR ON PR.PROFILE_ID = P.PROFILE_ID ");
            sql.append("            INNER JOIN TB_ROLE R ON R.ROLE_ID = PR.ROLE_ID AND R.ROLE = :enviarProposta) ");
            sql.append("            U2 ON U2.USER_TYPE_ID = UT.USER_TYPE_ID ");
            sql.append(" GROUP BY UT.USER_TYPE_ID, UT.DESCRIPTION ");
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            
            query.setParameter("structureId", structureId);
            query.setParameter("enviarProposta", "ENVIA_PROPOSTA");
            
            List<Object[]> users = query.getResultList();
            List<DossierSubmitRoleDTO> listDossierSubmitRole = new ArrayList<DossierSubmitRoleDTO>(); 
            
            for(Object[] row : users){
                DossierSubmitRoleDTO dto = new DossierSubmitRoleDTO();
                dto.setUserTypeId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setDescription((String) row[1]);
                dto.setAllowed((BigDecimal) row[4]);
                dto.setIdStructure(CriptoUtilsOmega2.encrypt(Long.valueOf(structureId)));
                listDossierSubmitRole.add(dto);
            }
            
            LOGGER.debug(" >> END findRoleStructure ");
            return listDossierSubmitRole;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }   
    
    public void saveSubmitRole(Long userTypeId, Long idStructure) throws UnexpectedException{

        try {
            LOGGER.debug(" >> INIT saveSubmitRole ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" INSERT INTO TB_USER_PROFILE(USER_ID, PROFILE_ID)         ");
            sql.append(" SELECT US.USER_ID, PR.PROFILE_ID                         ");
            sql.append(" FROM TB_ROLE R                                           ");
            sql.append(" INNER JOIN TB_PROFILE_ROLE PR ON PR.ROLE_ID = R.ROLE_ID, ");
            sql.append(" TB_USER_STRUCTURE US                                     ");
            sql.append(" INNER JOIN TB_USER U ON U.USER_ID = US.USER_ID           ");
            sql.append(" WHERE R.ROLE = 'ENVIA_PROPOSTA'                          ");
            sql.append(" AND US.STRUCTURE_ID = :idStructure                       ");
            sql.append(" AND U.USER_TYPE_ID = :userTypeId                         ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());

            query.setParameter("userTypeId", userTypeId);
            query.setParameter("idStructure", idStructure);
            query.executeUpdate();

            LOGGER.debug(" >> END saveSubmitRole ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } 
            
    }
    
    public void deleteSubmitRole(Long userTypeId, Long idStructure) throws UnexpectedException{

        try {
            LOGGER.debug(" >> INIT deleteSubmitRole ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" DELETE FROM TB_USER_PROFILE UP                                                                         ");
            sql.append(" WHERE UP.PROFILE_ID IN (                                                                               ");
            sql.append("                            SELECT PR.PROFILE_ID                                                        ");
            sql.append("                            FROM TB_ROLE R INNER JOIN TB_PROFILE_ROLE PR ON PR.ROLE_ID = R.ROLE_ID      ");
            sql.append("                            WHERE R.ROLE = :enviarProposta )                                            ");
            sql.append(" AND UP.USER_ID IN (                                                                                    ");
            sql.append("                            SELECT U.USER_ID                                                            ");
            sql.append("                            FROM TB_USER_STRUCTURE US INNER JOIN TB_USER U ON U.USER_ID = US.USER_ID    ");
            sql.append("                            WHERE US.STRUCTURE_ID = :idStructure AND U.USER_TYPE_ID = :userTypeId )     ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());

            query.setParameter("userTypeId", userTypeId);
            query.setParameter("idStructure", idStructure);
            query.setParameter("enviarProposta", "ENVIA_PROPOSTA");

            query.executeUpdate();  

            LOGGER.debug(" >> END deleteSubmitRole ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
}
