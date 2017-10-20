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
import com.rci.omega2.ejb.dao.AddressTypeDAO;
import com.rci.omega2.ejb.dto.AddressTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.AddressTypeEntity;

@Stateless
public class AddressTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(AddressTypeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<AddressTypeDTO> findAllMailingAddressType() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAllMailingAddressType ");
            AddressTypeDAO dao = daoFactory(AddressTypeDAO.class);
            
            List<AddressTypeEntity> address = dao.findAllMailingAddressType();

            List<AddressTypeDTO> lsDto = new ArrayList<>();

            for (AddressTypeEntity item : address) {
                AddressTypeDTO dto = new AddressTypeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllMailingAddressType ");
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
    public AddressTypeEntity findAddressTypeByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAddressTypeByImportCode ");
            
            AddressTypeDAO dao = daoFactory(AddressTypeDAO.class);
            AddressTypeEntity addressType = dao.findAddressTypeByImportCode(importCode);
            
            if(AppUtil.isNullOrEmpty(addressType)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.TIPO_ENDERECO, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    addressType = new AddressTypeEntity();
                    addressType.setActive(Boolean.TRUE);
                    addressType.setImportCode(domain.getCodigo());
                    addressType.setDescription(domain.getDescricao());
                    dao.save(addressType);
                }
            }
            
            LOGGER.debug(" >> END findAddressTypeByImportCode ");
            return addressType;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
