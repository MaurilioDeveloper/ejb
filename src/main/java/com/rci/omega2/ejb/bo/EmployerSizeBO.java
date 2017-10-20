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
import com.rci.omega2.ejb.dao.EmployerSizeDAO;
import com.rci.omega2.ejb.dto.EmployerSizeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.EmployerSizeEntity;

@Stateless
public class EmployerSizeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(EmployerSizeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<EmployerSizeDTO> findAllEmployerSize() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllEmployerSize ");
            
            EmployerSizeDAO dao = daoFactory(EmployerSizeDAO.class);
            
            List<EmployerSizeEntity> employer = dao.findAll(EmployerSizeEntity.class);

            List<EmployerSizeDTO> lsDto = new ArrayList<>();

            for (EmployerSizeEntity item : employer) {
                EmployerSizeDTO dto = new EmployerSizeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllEmployerSize ");
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
     * @param employerSizeId
     * @return
     * @throws UnexpectedException
     */
    public EmployerSizeEntity findEmployerSizeById(Long employerSizeId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findEmployerSizeById ");
            EmployerSizeDAO dao = daoFactory(EmployerSizeDAO.class);
            EmployerSizeEntity employerSize = dao.find(EmployerSizeEntity.class, employerSizeId);
            
           
            if(AppUtil.isNullOrEmpty(employerSize)){
                //Busca dominio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.PORTE_EMPRESA, String.valueOf(employerSizeId).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    employerSize = new EmployerSizeEntity();
                    employerSize.setActive(Boolean.TRUE);
                    employerSize.setId(Long.valueOf(domain.getCodigo()));
                    employerSize.setDescription(domain.getDescricao());
                    dao.save(employerSize);
                }
            }
            
            LOGGER.debug(" >> END findEmployerSizeById ");
            return employerSize;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }


}
