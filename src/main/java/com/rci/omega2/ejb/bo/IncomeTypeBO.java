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
import com.rci.omega2.ejb.dao.IncomeTypeDAO;
import com.rci.omega2.ejb.dto.IncomeTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.IncomeTypeEntity;

@Stateless
public class IncomeTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(IncomeTypeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<IncomeTypeDTO> findAllIncomeType() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllIncomeType ");
            
            IncomeTypeDAO dao = daoFactory(IncomeTypeDAO.class);

            List<IncomeTypeEntity> income = dao.findAll(IncomeTypeEntity.class);

            List<IncomeTypeDTO> lsDto = new ArrayList<>();

            for (IncomeTypeEntity item : income) {
                IncomeTypeDTO dto = new IncomeTypeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }

            LOGGER.debug(" >> END findAllIncomeType ");
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
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    public IncomeTypeEntity findIncomeTypeByImportCode(String importCode) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findIncomeTypeByImportCode ");
            
            IncomeTypeDAO dao = daoFactory(IncomeTypeDAO.class);
            IncomeTypeEntity incomeType = dao.findIncomeTypeByImportCode(importCode);
            
            if(AppUtil.isNullOrEmpty(incomeType)){
                //Busca dominio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.TIPO_RENDA, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    incomeType = new IncomeTypeEntity();
                    incomeType.setActive(Boolean.TRUE);
                    incomeType.setImportCode(domain.getCodigo());
                    incomeType.setDescription(domain.getDescricao());
                    dao.save(incomeType);
                }
            }
            
            LOGGER.debug(" >> END findIncomeTypeByImportCode ");
            return incomeType;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
