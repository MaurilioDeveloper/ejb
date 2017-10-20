package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.common.util.SantanderDomainEnum;
import com.rci.omega2.ejb.dao.CivilStateDAO;
import com.rci.omega2.ejb.dao.PhoneDAO;
import com.rci.omega2.ejb.dto.CivilStateDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.CivilStateEntity;
import com.rci.omega2.entity.PhoneEntity;

@Stateless
public class CivilStateBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(CivilStateBO.class);

    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    
    public List<CivilStateDTO> findAllCivilState() throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT findAllCivilState ");
            CivilStateDAO dao = daoFactory(CivilStateDAO.class);
            List<CivilStateEntity> civilStates = dao.findAll(CivilStateEntity.class);

            List<CivilStateDTO> lsDto = new ArrayList<>();

            for (CivilStateEntity item : civilStates) {
                CivilStateDTO dto = new CivilStateDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setValue(item.getId());
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllCivilState ");
            return lsDto;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

     /**
      * Add Dominio caso não tenha
      * @param id
      * @return
      * @throws UnexpectedException
      */
    public CivilStateEntity findCivilStateById(Long id) throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT findCivilStateById ");
            CivilStateDAO dao = daoFactory(CivilStateDAO.class);
            CivilStateEntity civilState = dao.find(CivilStateEntity.class, id);
            
            if(AppUtil.isNullOrEmpty(civilState)){
                //Busca dominio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.ESTADO_CIVIL, String.valueOf(id).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    civilState = new CivilStateEntity();
                    civilState.setActive(Boolean.TRUE);
                    civilState.setId(Long.valueOf(domain.getCodigo()));
                    civilState.setDescription(domain.getDescricao());
                    dao.save(civilState);
                }
            }
            
            LOGGER.debug(" >> END findCivilStateById ");
            return civilState;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    /**
     * 
     * @param phone
     * @return
     * @throws UnexpectedException
     */
    public void insert(PhoneEntity phone)throws UnexpectedException{

        try {
            LOGGER.debug(" >> INIT insert ");
            PhoneDAO dao = daoFactory(PhoneDAO.class);
            dao.save(phone);
            LOGGER.debug(" >> END insert ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    
}
