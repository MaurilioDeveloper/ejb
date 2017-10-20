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
import com.rci.omega2.ejb.dao.CountryDAO;
import com.rci.omega2.ejb.dto.CountryDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.AppOrderUtils;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.CountryEntity;

@Stateless
public class CountryBO extends BaseBO{
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    private static final Logger LOGGER = LogManager.getLogger(CountryBO.class);
       
    public List<CountryDTO> findAllCountry() throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findAllCountry ");
            CountryDAO dao = daoFactory(CountryDAO.class);
            List<CountryEntity> countrys = dao.findAll(CountryEntity.class);
            countrys = AppOrderUtils.ordinateCountryEntity(countrys);
            
            List<CountryDTO> lsDto = new ArrayList<>();
            
            for(CountryEntity item : countrys){
                CountryDTO dto = new CountryDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }
            
            LOGGER.debug(" >> END findAllCountry ");
            return lsDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * Add domain caso não tenha
     * @param acronym
     * @return
     * @throws UnexpectedException
     */
    public CountryEntity findCountryByAcronym(String acronym) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCountryByAcronym ");
            CountryDAO dao = daoFactory(CountryDAO.class);
            CountryEntity country = dao.findCountryByAcronym(acronym);
            
            if(AppUtil.isNullOrEmpty(country)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.PAISES, String.valueOf(acronym).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    country = new CountryEntity();
                    country.setActive(Boolean.TRUE);
                    country.setImportCode(domain.getCodigo());
                    country.setDescription(domain.getDescricao());
                    dao.save(country);
                }
            }
            
            LOGGER.debug(" >> END findCountryByAcronym ");
            return country;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
}
