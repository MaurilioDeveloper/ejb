package com.rci.omega2.ejb.bo;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.StructureDAO;
import com.rci.omega2.ejb.dto.StructureDTO;
import com.rci.omega2.ejb.dto.StructureSearchDTO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.Brand;
import com.rci.omega2.entity.DealershipEntity;
import com.rci.omega2.entity.StructureEntity;
import com.rci.omega2.entity.UserEntity;

@Stateless
public class StructureBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(StructureBO.class);

    public List<StructureDTO> findStructureDealershipByUser(Long userId, Boolean regionalView)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findStructureDealershipByUser ");
            StructureDAO dao = daoFactory(StructureDAO.class);
            if (regionalView) {
                List<StructureDTO> temp = dao.findStructureDealershipByUserRegional(userId);
                LOGGER.debug(" >> END findStructureDealershipByUser ");
                return temp;
            } else {
                List<StructureDTO> temp = dao.findStructureDealershipByUser(userId);
                LOGGER.debug(" >> END findStructureDealershipByUser ");
                return temp;
            }
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public List<StructureDTO> findStructureDealershipBySallesmanUser(Long sallesmanUser)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findStructureDealershipBySallesmanUser ");
            StructureDAO dao = daoFactory(StructureDAO.class);
            List<StructureDTO> temp = dao.findStructureDealershipBySallesmanUser(sallesmanUser);
            
            LOGGER.debug(" >> END findStructureDealershipBySallesmanUser ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<Long> findIdsStructureDealershipByUser(Long userId, Boolean regionalView) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findIdsStructureDealershipByUser ");
            StructureDAO dao = daoFactory(StructureDAO.class);
            
            if (regionalView) {
                List<Long> temp = dao.findIdsStructureDealershipByUserRegional(userId);
                LOGGER.debug(" >> END findIdsStructureDealershipByUser ");
                return temp;
            } else {
                List<Long> temp = dao.findIdsStructureDealershipByUser(userId);
                LOGGER.debug(" >> END findIdsStructureDealershipByUser ");
                return temp;
            }
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public StructureEntity findById(Long id) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findById ");
            
            StructureDAO dao = daoFactory(StructureDAO.class);
            StructureEntity temp = dao.findById(id);
            
            LOGGER.debug(" >> END findById ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public DealershipEntity findDealershipByStructure(Long structureId) throws UnexpectedException{
        try{
            LOGGER.debug(" >> INIT findDealershipByStructure ");
            
            StructureDAO dao = daoFactory(StructureDAO.class);
            DealershipEntity temp = dao.findDealershipByStructure(structureId);
            
            LOGGER.debug(" >> END findDealershipByStructure ");
            return temp;
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
            StructureDAO dao = daoFactory(StructureDAO.class);
            Set<Brand> temp = dao.getBrandByUserId(user);
            LOGGER.debug(" >> END getBrandByUserId ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    public List<StructureDTO> findAllStructureDealership() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllStructureDealership ");
            StructureDAO dao = daoFactory(StructureDAO.class);
            List<StructureDTO> temp = dao.findAllStructureDealership();
            
            LOGGER.debug(" >> END findAllStructureDealership ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<StructureSearchDTO> findStructureSearch(Long userId, String dealershipName, String dealershipCode)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findStructureSearch ");
            
            StructureDAO dao = daoFactory(StructureDAO.class);
            List<StructureSearchDTO> temp = dao.findStructureSearch(userId, dealershipName, dealershipCode);
            
            LOGGER.debug(" >> END findStructureSearch ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<StructureSearchDTO> findStructuresToProfile(Long userId, String dealershipName, String dealershipCode)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findStructuresToProfile ");
            List<StructureSearchDTO> list = findStructureSearch(userId, dealershipName, dealershipCode);
            if (list.size() == 0) {
                throw new BusinessException("no-delaerships-to-profile");
            }
            LOGGER.debug(" >> END findStructuresToProfile ");
            return list;
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
            StructureDAO dao = daoFactory(StructureDAO.class);
            StructureEntity temp = dao.findByDossier(dossierId);
            
            LOGGER.debug(" >> END findByDossier ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
