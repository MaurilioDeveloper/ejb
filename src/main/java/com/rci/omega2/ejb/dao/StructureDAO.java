package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.StructureDTO;
import com.rci.omega2.ejb.dto.StructureSearchDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.Brand;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.DealershipEntity;
import com.rci.omega2.entity.StructureEntity;
import com.rci.omega2.entity.UserEntity;
import com.rci.omega2.entity.enumeration.StructureTypeEnum;

public class StructureDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(StructureDAO.class);

    @SuppressWarnings("unchecked")
    public List<StructureDTO> findStructureDealershipByUser(Long userId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findStructureDealershipByUser ");
            
            Query query = super.getEntityManager().createNativeQuery(
                    "  SELECT DISTINCT S.STRUCTURE_ID, D.BIR, S.DESCRIPTION, FB.IMPORT_CODE_ACTOR AS ID_BRAND"
                            + "  FROM TB_STRUCTURE S "
                            + "  LEFT OUTER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S.STRUCTURE_ID AND US.USER_ID = :userId "
                            + "  LEFT OUTER JOIN TB_DEALERSHIP D ON D.STRUCTURE_ID = S.STRUCTURE_ID "
                            + "  LEFT OUTER JOIN TB_FINANCIAL_BRAND FB ON FB.FINANCIAL_BRAND_ID = S.FINANCIAL_BRAND_ID "

                            + "  WHERE S.STRUCTURE_TYPE = :structureType "
                            + "  START WITH S.STRUCTURE_ID = US.STRUCTURE_ID "
                            + "  CONNECT BY PRIOR S.STRUCTURE_ID = S.STRUCTURE_ID_PARENT "
                            + " ORDER BY S.DESCRIPTION ");

            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());
            query.setParameter("userId", userId);

            List<Object[]> structures = query.getResultList();

            List<StructureDTO> listStructure = new ArrayList<StructureDTO>();

            for (Object[] row : structures) {
                StructureDTO dto = new StructureDTO();
                dto.setStructureId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setByr((String) row[1]);
                dto.setDescription((String) row[2]);
                dto.setBrand((String) row[3]);

                listStructure.add(dto);
            }

            LOGGER.debug(" >> END findStructureDealershipByUser ");
            return listStructure;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public List<StructureDTO> findStructureDealershipBySallesmanUser(Long sallesmanUser) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findStructureDealershipBySallesmanUser ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(
                    "  SELECT S.STRUCTURE_ID, S.FINANCIAL_BRAND_ID , S.DESCRIPTION, S.VEHICLE_BRAND_ID, V.DESCRIPTION AS FB_DESCRIPTION, V.IMPORT_CODE_ACTOR ");
            sql.append("  FROM TB_STRUCTURE S ");
            sql.append("  INNER JOIN TB_FINANCIAL_BRAND V ON S.FINANCIAL_BRAND_ID = V.FINANCIAL_BRAND_ID ");
            sql.append(
                    "  INNER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S.STRUCTURE_ID AND US.USER_ID = :sallesmanUser ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());

            query.setParameter("sallesmanUser", sallesmanUser);

            List<Object[]> structures = query.getResultList();

            List<StructureDTO> listStructure = new ArrayList<StructureDTO>();

            for (Object[] row : structures) {
                StructureDTO dto = new StructureDTO();
                dto.setStructureId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setBrand(CriptoUtilsOmega2.encrypt(Long.valueOf((row[1]).toString())));
                dto.setDescription(row[2].toString());
                dto.setBrandVehicle(
                        row[3] != null ? CriptoUtilsOmega2.encrypt(Long.valueOf((row[3]).toString())) : null);
                dto.setFinanceBrandDescription(row[4].toString());
                dto.setFinanceBrandImportCode(row[5].toString());
                listStructure.add(dto);
            }

            LOGGER.debug(" >> END findStructureDealershipBySallesmanUser ");
            return listStructure;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public List<StructureDTO> findStructureDealershipByUserRegional(Long userId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findStructureDealershipByUserRegional ");
            
            Query query = super.getEntityManager().createNativeQuery(
                    " SELECT S.STRUCTURE_ID, D.BIR, S.DESCRIPTION , FB.IMPORT_CODE_ACTOR  AS ID_BRAND "
                            + " FROM TB_STRUCTURE S" + " INNER JOIN TB_DEALERSHIP D ON D.STRUCTURE_ID = S.STRUCTURE_ID "
                            + " INNER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S.STRUCTURE_ID "
                            + " INNER JOIN TB_FINANCIAL_BRAND FB ON FB.FINANCIAL_BRAND_ID = S.FINANCIAL_BRAND_ID "

                            + " WHERE S.STRUCTURE_TYPE = :structureType  " + " AND US.USER_ID = :userId "
                            + " ORDER BY S.DESCRIPTION ");

            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());
            query.setParameter("userId", userId);

            List<Object[]> structures = query.getResultList();

            List<StructureDTO> listStructure = new ArrayList<StructureDTO>();

            for (Object[] row : structures) {
                StructureDTO dto = new StructureDTO();
                dto.setStructureId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setByr((String) row[1]);
                dto.setDescription((String) row[2]);
                dto.setBrand((String) row[3]);
                listStructure.add(dto);
            }

            LOGGER.debug(" >> END findStructureDealershipByUserRegional ");
            return listStructure;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public List<Long> findIdsStructureDealershipByUser(Long userId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findIdsStructureDealershipByUser ");
            
            Query query = super.getEntityManager().createNativeQuery("  SELECT S.STRUCTURE_ID "
                    + "  FROM TB_STRUCTURE S "
                    + "  LEFT OUTER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S.STRUCTURE_ID AND US.USER_ID = :userId "
                    + "  LEFT OUTER JOIN TB_DEALERSHIP D ON D.STRUCTURE_ID = S.STRUCTURE_ID "
                    + "  WHERE S.STRUCTURE_TYPE = :structureType " + "  START WITH S.STRUCTURE_ID = US.STRUCTURE_ID "
                    + "  CONNECT BY PRIOR S.STRUCTURE_ID = S.STRUCTURE_ID_PARENT ");

            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());
            query.setParameter("userId", userId);

            List<Long> idsStructures = query.getResultList();

            LOGGER.debug(" >> END findIdsStructureDealershipByUser ");
            return idsStructures;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public List<Long> findIdsStructureDealershipByUserRegional(Long userId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findIdsStructureDealershipByUserRegional ");
            
            Query query = super.getEntityManager().createNativeQuery(" SELECT S.STRUCTURE_ID " + " FROM TB_STRUCTURE S"
                    + " INNER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S.STRUCTURE_ID "
                    + " WHERE S.STRUCTURE_TYPE = :structureType  " + " AND US.USER_ID = :userId "
                    + " ORDER BY DESCRIPTION ");

            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());
            query.setParameter("userId", userId);

            List<Long> idsStructures = query.getResultList();

            LOGGER.debug(" >> END findIdsStructureDealershipByUserRegional ");
            return idsStructures;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    @SuppressWarnings("unchecked")
    public List<Long> findIdsFinancialBrandUser(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findIdsFinancialBrandUser ");
            
            Query query = super.getEntityManager().createNativeQuery(
                    "SELECT FINANCIAL_BRAND_ID " + "FROM TB_STRUCTURE " + "WHERE STRUCTURE_TYPE = :structureType "
                            + "AND FINANCIAL_BRAND_ID IS NOT NULL " + "START WITH STRUCTURE_ID IN (SELECT S1.STRUCTURE_ID "
                            + "                            FROM TB_STRUCTURE S1, TB_USER_STRUCTURE US "
                            + "                            WHERE US.STRUCTURE_ID = S1.STRUCTURE_ID "
                            + "                            AND US.USER_ID = :userId) "
                            + "CONNECT BY PRIOR STRUCTURE_ID = STRUCTURE_ID_PARENT " + "GROUP BY FINANCIAL_BRAND_ID "
                            + " ORDER BY DESCRIPTION ");
    
            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());
            query.setParameter("userId", userId);

            List<Long> idsStructures = query.getResultList();

            LOGGER.debug(" >> END findIdsFinancialBrandUser ");
            return idsStructures;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
            
    }

    @SuppressWarnings("unchecked")
    public List<Long> findIdsFinancialBrandByUserRegional(Long userId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findIdsFinancialBrandByUserRegional ");
            
            Query query = super.getEntityManager().createNativeQuery(" SELECT S.STRUCTURE_ID " + " FROM TB_STRUCTURE S"
                    + " INNER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S.STRUCTURE_ID "
                    + " WHERE S.STRUCTURE_TYPE = :structureType  " + " AND US.USER_ID = :userId "
                    + " ORDER BY DESCRIPTION ");
    
            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());
            query.setParameter("userId", userId);
    
            List<Long> idsStructures = query.getResultList();
            
            LOGGER.debug(" >> END findIdsFinancialBrandByUserRegional ");
            return idsStructures;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }

    public StructureEntity findById(Long id) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findById ");
            
            Query query = super.getEntityManager().createQuery(" SELECT s FROM StructureEntity AS s WHERE s.id = :id ");
            query.setParameter("id", id);
    
            StructureEntity entity = (StructureEntity) query.getSingleResult();
            
            LOGGER.debug(" >> END findById ");
            return entity;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }

    public DealershipEntity findDealershipByStructure(Long structureId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDealershipByStructure ");
            
            Query query = super.getEntityManager().createQuery(
                    " SELECT d FROM StructureEntity AS s INNER JOIN s.dealership AS d WHERE s.id = :structureId ");
            query.setParameter("structureId", structureId);
    
            DealershipEntity entity = (DealershipEntity) query.getSingleResult();
            
            LOGGER.debug(" >> END findDealershipByStructure ");
            return entity;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
        
    }

    @SuppressWarnings("unchecked")
    public List<StructureDTO> findAllStructureDealership() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAllStructureDealership ");
            
            Query query = super.getEntityManager()
                    .createNativeQuery(" SELECT STR.STRUCTURE_ID, DLS.BIR, DLS.TAB, STR.DESCRIPTION FROM TB_STRUCTURE STR"
                            + " JOIN TB_DEALERSHIP DLS" + " ON STR.STRUCTURE_ID = DLS.STRUCTURE_ID");
    
            List<Object[]> structures = query.getResultList();

            List<StructureDTO> listStructure = new ArrayList<StructureDTO>();

            for (Object[] row : structures) {
                StructureDTO dto = new StructureDTO();
                dto.setStructureId(CriptoUtilsOmega2.encrypt(row[0].toString()));
                dto.setByr((String) row[1]);
                dto.setTab((String) row[2]);
                dto.setDescription((String) row[3]);
                listStructure.add(dto);
            }

            LOGGER.debug(" >> END findAllStructureDealership ");
            return listStructure;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    @SuppressWarnings("unchecked")
    public List<StructureSearchDTO> findStructureSearch(Long userId, String strucutureName, String structureCode)
            throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findStructureSearch ");
            
            String sql = "  SELECT DISTINCT S.STRUCTURE_ID, S.DESCRIPTION, S.IMPORT_CODE_ACTOR " + "  FROM TB_STRUCTURE S "
                    + "  LEFT OUTER JOIN TB_USER_STRUCTURE US ON US.STRUCTURE_ID = S.STRUCTURE_ID AND US.USER_ID = :userId "
                    + "  LEFT OUTER JOIN TB_DEALERSHIP D ON D.STRUCTURE_ID = S.STRUCTURE_ID "
                    + "  WHERE S.STRUCTURE_TYPE = :structureType ";
            
            if (!strucutureName.trim().isEmpty() && structureCode.trim().isEmpty()) {
                sql += "AND UPPER(S.DESCRIPTION) LIKE UPPER(:strucutureName) ";
            }
            
            if (!structureCode.trim().isEmpty()) {
                sql += "AND S.IMPORT_CODE_ACTOR = :structureCode ";
            }
            
            sql += "  START WITH S.STRUCTURE_ID = US.STRUCTURE_ID "
                    + "  CONNECT BY PRIOR S.STRUCTURE_ID = S.STRUCTURE_ID_PARENT " + " ORDER BY S.DESCRIPTION ";
            Query query = super.getEntityManager().createNativeQuery(sql);
    
            query.setParameter("structureType", StructureTypeEnum.CONCESSIONARIA.name());
            query.setParameter("userId", userId);
            
            if (!strucutureName.trim().isEmpty() && structureCode.trim().isEmpty()) {
                query.setParameter("strucutureName", "%" + strucutureName + "%");
            }
            
            if (!structureCode.trim().isEmpty()) {
                query.setParameter("structureCode", structureCode);
            }
    
            List<Object[]> structures = query.getResultList();
    
            List<StructureSearchDTO> listStructure = new ArrayList<StructureSearchDTO>();
    
            for (Object[] row : structures) {
                StructureSearchDTO dto = new StructureSearchDTO();
                dto.setStructureId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setStructureName(row[1].toString());
                dto.setStructureCode(row[2].toString());
                listStructure.add(dto);
            }
            
            LOGGER.debug(" >> END findStructureSearch ");
            return listStructure;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
        
    }

    public Set<Brand> getBrandByUserId(UserEntity user) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT getBrandByUserId ");
            
            Set<Brand> brands = new TreeSet<Brand>();

            List<StructureDTO> listConcessioria = null;

            if (user.getUserType().getRegionalView()) {
                listConcessioria = this.findStructureDealershipByUserRegional(user.getId());
            } else {
                listConcessioria = this.findStructureDealershipByUser(user.getId());
            }

            for (StructureDTO structureDTO : listConcessioria) {
                if (structureDTO.getBrand() != null && !structureDTO.getBrand().equals("")) {
                    Brand brand = Brand.getById(structureDTO.getBrand());
                    brands.add(brand);
                }
            }

            LOGGER.debug(" >> END getBrandByUserId ");
            return brands;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    public StructureEntity findByDossier(Long dossierId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findByDossier ");
            
            Query query = super.getEntityManager()
                    .createQuery(" SELECT ds.structure FROM DossierEntity AS ds WHERE ds.id = :dossierId ");
            query.setParameter("dossierId", dossierId);

            StructureEntity entity = (StructureEntity) query.getSingleResult();
            
            LOGGER.debug(" >> END findByDossier ");
            return entity;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }  
        
    }

}
