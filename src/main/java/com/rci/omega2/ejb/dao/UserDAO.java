package com.rci.omega2.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dto.RoleFunctionDTO;
import com.rci.omega2.ejb.dto.UserDTO;
import com.rci.omega2.ejb.dto.ws.FilterUserDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.UserEntity;
import com.rci.omega2.entity.enumeration.RoleEnum;

public class UserDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);

    @SuppressWarnings("unchecked")
    public UserEntity findOne(String username) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findOne ");
            
            Query query = super.getEntityManager().createQuery(
               "from UserEntity u " 
             + "join fetch u.person " 
             + "join fetch u.userType " 
             + "where u.login = :username ");
    
            query.setParameter("username", username);
    
            List<UserEntity> listUser = query.getResultList();
            
            if (listUser != null && listUser.size() > 0) {
                return listUser.get(0);
            }
            
            LOGGER.debug(" >> END findOne ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }

    public boolean functionValidation(Long userId, Long functionId) {
     /*   Query queryRole = super.getEntityManager()
                .createQuery("select r.id from UserEntity u " 
                        + "join u.role r where u.id = :userId ");
       
        queryRole.setParameter("userId", userId);
        Long idRole = Long.valueOf(queryRole.getSingleResult().toString());
        if (idRole == null) {
            return false;
        } else {
            Query queryFunctions = super.getEntityManager().createQuery("select count(f.id) from RoleEntity r "
                    + "join r.listFunction f " + "where r.id = :idRole " + "and f.id = :functionId ");
            queryFunctions.setParameter("idRole", idRole);
            queryFunctions.setParameter("functionId", functionId);
            Integer count = Integer.valueOf(queryFunctions.getSingleResult().toString());
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        }*/
        return false;
    }

    @SuppressWarnings("unchecked")
    public UserEntity findByEmail(String email) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findByEmail ");
            
            Query query = super.getEntityManager()
                    .createQuery("select u from UserEntity u where u.person.email = :email");
            query.setParameter("email", email);

            List<UserEntity> list = query.getResultList();

            if (!list.isEmpty()) {
                return list.get(query.getFirstResult());
            }

            LOGGER.debug(" >> END findByEmail ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }  
        
    }
    
    @SuppressWarnings("unchecked")
    public List<UserDTO> findUserStructure(Long userId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findUserStructure ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT ");
            sql.append("   U.USER_ID, ");
            sql.append("   P.NAME_PERSON, ");
            sql.append("   CASE WHEN UP1.USER_ID = U.USER_ID THEN 1 ELSE 0 END AS ROLE_ENVIAR ");
            sql.append(" FROM ");
            sql.append("   TB_USER U ");
            sql.append(" INNER JOIN TB_PERSON P ON P.USER_ID = U.USER_ID ");
            sql.append(" INNER JOIN TB_USER_STRUCTURE US ON US.USER_ID = U.USER_ID ");
            sql.append(" LEFT JOIN (SELECT UP.* FROM TB_USER_PROFILE UP ");
            sql.append("             INNER JOIN TB_PROFILE P ON P.PROFILE_ID = UP.PROFILE_ID ");
            sql.append("             INNER JOIN TB_PROFILE_ROLE PR ON PR.PROFILE_ID = P.PROFILE_ID ");
            sql.append("             INNER JOIN TB_ROLE R ON R.ROLE_ID = PR.ROLE_ID AND R.ROLE = :role ) ");
            sql.append("             UP1 ON UP1.USER_ID = U.USER_ID ");
            sql.append(" WHERE US.STRUCTURE_ID IN ( SELECT STRUCTURE_ID FROM TB_STRUCTURE ");
            sql.append("                             START WITH STRUCTURE_ID in (SELECT US1.STRUCTURE_ID FROM TB_USER_STRUCTURE US1 WHERE US1.USER_ID = :userId) ");
            sql.append("                             CONNECT BY PRIOR STRUCTURE_ID = STRUCTURE_ID_PARENT) ");
            sql.append(" ORDER BY U.USER_ID ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString()).setMaxResults(200);
            query.setParameter("userId", userId);
            query.setParameter("role", RoleEnum.ENVIA_PROPOSTA.name());

            List<Object[]> users = query.getResultList();
            List<UserDTO> listUser = new ArrayList<UserDTO>();

            for (Object[] row : users) {
                UserDTO dto = new UserDTO();
                dto.setUserid(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setNamePerson((String) row[1]);
                dto.setDossierSubmitAllowed((BigDecimal) row[2]);
                listUser.add(dto);
            }

            LOGGER.debug(" >> END findUserStructure ");
            return listUser;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
        
    }

    @SuppressWarnings("unchecked")
    public List<RoleFunctionDTO> findRoleEntity(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findRoleEntity ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT                                                                                         ");
            sql.append(" role.URL, role.ROLE, role.DESCRIPTION, p.DESCRIPTION as profile                                ");
            sql.append(" FROM TB_ROLE role                                                                              ");
            sql.append(" INNER JOIN TB_PROFILE_ROLE PR ON PR.ROLE_ID = ROLE.ROLE_ID                                     ");
            sql.append(" INNER JOIN TB_PROFILE P ON P.PROFILE_ID = PR.PROFILE_ID                                        ");
            sql.append(" INNER JOIN TB_USER_PROFILE UP ON UP.PROFILE_ID = p.PROFILE_ID  WHERE UP.USER_ID = :userId      ");
            sql.append(" ORDER BY role.ROLE DESC                                                                        ");
           
            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            
            query.setParameter("userId", userId.intValue());
            
            List<Object[]> roles = query.getResultList();
            List<RoleFunctionDTO> listRoles = new ArrayList<RoleFunctionDTO>(); 
            
            for(Object[] row : roles){
                RoleFunctionDTO dto = new RoleFunctionDTO();
                dto.setUrl(row[0]!= null? row[0].toString():"");
                dto.setRole(row[1].toString());
                dto.setDescription(row[2].toString());
                dto.setProfile(row[3].toString());
                listRoles.add(dto);
            }
            
            LOGGER.debug(" >> END findRoleEntity ");
            return listRoles;      
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
    @SuppressWarnings("unchecked")
    public List<UserEntity> findAllUsingFilters(FilterUserDTO filters) throws UnexpectedException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" from UserEntity AS u " +
                    " join fetch u.person As p " + 
                    " join fetch u.userType AS t " +
                    " where 1=1 ");
            if (filters != null) {
                if (!AppUtil.isNullOrEmpty(filters.getLoginLike())) {
                    sb.append(" and lower(u.login) like lower(:login) ");
                }
                
                if (!AppUtil.isNullOrEmpty(filters.getCpf())) {
                    sb.append(" and p.cpf = :cpf ");
                }
                if (filters.getDtUpdateStart() != null) {
                    sb.append(" and u.changeDate >= :dtini ");
                }
                
                if (filters.getDtUpdateStart() != null) {
                    sb.append(" and u.changeDate <= :dtend ");
                }
                
                if (filters.getIsActive() != null) {
                    sb.append(" and u.active = :active ");
                }
            }
            Query query = super.getEntityManager().createQuery(sb.toString());
            if (filters != null) {
                if (!AppUtil.isNullOrEmpty(filters.getLoginLike())) {
                    query.setParameter("login",  "%" + filters.getLoginLike() + "%" );
                }
                
                if (!AppUtil.isNullOrEmpty(filters.getCpf())) {
                    query.setParameter("cpf", filters.getCpf());
                }
                if (filters.getDtUpdateStart() != null) {
                    query.setParameter("dtini", filters.getDtUpdateStart());
                }
                
                if (filters.getDtUpdateStart() != null) {
                    query.setParameter("dtend", filters.getDtUpdateEnd());
                }
                
                if (filters.getIsActive() != null) {
                    query.setParameter("active", filters.getIsActive());
                }
                
                Integer registers = filters.getNumberOfRegisters();
                Integer page = filters.getPage();
                if (registers != null && registers > 0) {
                    query.setMaxResults(registers);
                    if (page != null && page >= 0) {
                        query.setFirstResult(registers * page);
                    }
                }
            }
            return query.getResultList();
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    public Integer findProposalQuantity(Long userId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findProposalQuantity ");
            
            String sql = "select u.proposalQuantity from UserEntity u where u.id = :userId ";

            Query query = super.getEntityManager().createQuery(sql);
            query.setParameter("userId", userId);

            Integer result = Integer.valueOf(query.getResultList().get(0).toString());

            LOGGER.debug(" >> END findProposalQuantity ");
            return result;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
        
    }
    
    public void saveSubmitRoleUser(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT saveSubmitRoleUser ");

            StringBuilder sql = new StringBuilder();

            sql.append(" INSERT INTO TB_USER_PROFILE(USER_ID, PROFILE_ID)           ");
            sql.append(" SELECT U.USER_ID, PR.PROFILE_ID                            ");
            sql.append(" FROM TB_USER U, TB_ROLE R                                  ");
            sql.append(" INNER JOIN TB_PROFILE_ROLE PR ON PR.ROLE_ID = R.ROLE_ID    ");
            sql.append(" WHERE R.ROLE = :role                                       ");
            sql.append(" AND U.USER_ID = :userId                                    ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            query.setParameter("userId", userId);
            query.setParameter("role", "ENVIA_PROPOSTA");

            query.executeUpdate();

            LOGGER.debug(" >> END saveSubmitRoleUser ");
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    public void deleteSubmitRoleUser(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT deleteSubmitRoleUser ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" DELETE FROM TB_USER_PROFILE UP ");
            sql.append(" WHERE UP.USER_ID = :userId ");
            sql.append(" AND UP.PROFILE_ID IN ( ");
            sql.append("                            SELECT PR.PROFILE_ID ");
            sql.append("                            FROM TB_ROLE R INNER JOIN TB_PROFILE_ROLE PR ON PR.ROLE_ID = R.ROLE_ID ");
            sql.append("                            WHERE R.ROLE = :role )  ");
            
            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            query.setParameter("userId", userId);
            query.setParameter("role", "ENVIA_PROPOSTA");

            query.executeUpdate();
            
            LOGGER.debug(" >> END deleteSubmitRoleUser ");
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
        
    }
    
    @SuppressWarnings("unchecked")
    public UserEntity findByPerson(Long personId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findByPerson ");
            
            Query query = super.getEntityManager()
                    .createQuery("select u from UserEntity u where u.person.id = :personId");
            query.setParameter("personId", personId);

            List<UserEntity> lista = query.getResultList();

            UserEntity temp = lista.get(0);
            
            LOGGER.debug(" >> END findByPerson ");
            return temp;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }  
            
    }
    
}
