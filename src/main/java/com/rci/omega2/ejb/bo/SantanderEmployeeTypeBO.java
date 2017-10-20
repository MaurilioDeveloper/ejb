package com.rci.omega2.ejb.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.common.util.SantanderDomainEnum;
import com.rci.omega2.ejb.dao.SantanderEmployeeTypeDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.SantanderEmployeeTypeEntity;

@Stateless
public class SantanderEmployeeTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(SantanderEmployeeTypeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    /**
     * 
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    public SantanderEmployeeTypeEntity findSantanderEmployeeTypeByImportCode(String importCode) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSantanderEmployeeTypeByImportCode ");
            
            SantanderEmployeeTypeDAO dao = daoFactory(SantanderEmployeeTypeDAO.class);
            SantanderEmployeeTypeEntity santanderEmployeeType = dao.findSantanderEmployeeTypeByImportCode(importCode);
            
            if(AppUtil.isNullOrEmpty(santanderEmployeeType)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.TIPO_FUNCIONARIO_SANTANDER, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    santanderEmployeeType = new SantanderEmployeeTypeEntity();
                    santanderEmployeeType.setActive(Boolean.TRUE);
                    santanderEmployeeType.setImportCode(domain.getCodigo());
                    santanderEmployeeType.setDescription(domain.getDescricao());
                    dao.save(santanderEmployeeType);
                }
            }
            
            LOGGER.debug(" >> END findSantanderEmployeeTypeByImportCode ");
            return santanderEmployeeType;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
