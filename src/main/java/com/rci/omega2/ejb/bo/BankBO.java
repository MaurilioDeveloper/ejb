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
import com.rci.omega2.ejb.dao.BankDAO;
import com.rci.omega2.ejb.dto.BankDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.AppOrderUtils;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.BankEntity;

@Stateless
public class BankBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(BankBO.class);

    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<BankDTO> findAllBank() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAllBank ");
            BankDAO dao = daoFactory(BankDAO.class);
            
            List<BankEntity> bank = dao.findAll(BankEntity.class);
            bank = AppOrderUtils.ordinateBankEntity(bank);
            
            List<BankDTO> lsDto = new ArrayList<>();
            
            for (BankEntity item : bank){
                BankDTO dto = new BankDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                dto.setImportCode(item.getImportCode());
                lsDto.add(dto);
            }
            LOGGER.debug(" >> END findAllBank ");
            return lsDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * 
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    public BankEntity findBankByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findBankByImportCode ");
            
            BankDAO dao = daoFactory(BankDAO.class);
            BankEntity bank = dao.findBankByImportCode(importCode);
            
            if(AppUtil.isNullOrEmpty(bank)){
                //Busca dominio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                        SantanderDomainEnum.BANCOS, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    bank = new BankEntity();
                    bank.setActive(Boolean.TRUE);
                    bank.setImportCode(domain.getCodigo());
                    bank.setDescription(domain.getDescricao());
                    dao.save(bank);
                }
            }
            
            LOGGER.debug(" >> END findBankByImportCode ");
            return bank;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
