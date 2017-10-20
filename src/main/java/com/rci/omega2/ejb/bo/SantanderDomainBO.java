package com.rci.omega2.ejb.bo;

import java.net.URL;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.AtividadeEconomicaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.altec.bsbr.app.afc.webservice.impl.DominiosClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.DominiosGeraisClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpoint;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpointService;
import com.altec.bsbr.app.afc.webservice.impl.TipoRelacionamentoClientRequest;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.common.util.SantanderDomainEnum;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.integrations.santander.soaputils.HeaderHandlerResolver;
import com.rci.omega2.ejb.utils.AppConstants;

@Stateless
public class SantanderDomainBO extends BaseBO{
    
    
    private static final Logger LOGGER = LogManager.getLogger(SantanderDomainBO.class);
    
    @EJB
    private ConfigFileBO configFile;
    

    /**
     * 
     * @param santanderDomain
     * @param codeItem
     * @return
     * @throws UnexpectedException
     */
    public DominioDTO findSantanderDomain(SantanderDomainEnum santanderDomain, String codeItem)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT findSantanderDomain ");
            
            FinanciamentosOnlineEndpointService santanderService = new FinanciamentosOnlineEndpointService(
                                    new URL(configFile.getProperty("santander.webservice.financiamentos.endpoint.wsdl")));
            santanderService.setHandlerResolver(new HeaderHandlerResolver(configFile));
            FinanciamentosOnlineEndpoint proxy = santanderService.getFinanciamentosOnlineEndpointPort();

            
            DominiosGeraisClientRequest request = new DominiosGeraisClientRequest();
            request.setCodigoDominio(santanderDomain.getCode());
            
            DominiosClientResponse response = proxy.listaDominiosGerais(request);
            
            
            if(!AppUtil.isNullOrEmpty(response.getDominios())){
                for(DominioDTO dominio : response.getDominios().getOpcoes()){
                    //encontra item do dominio
                    if(String.valueOf(codeItem).trim().equalsIgnoreCase(dominio.getCodigo())){
                        return dominio;
                    }
                }
            }

            LOGGER.debug(" >> END findSantanderDomain ");
            return null;
            
        }catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * 
     * @param santanderDomain
     * @param codeItem
     * @return
     * @throws UnexpectedException
     */
    public DominioDTO findSantanderEconomicActivity(String codeItem)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT findSantanderEconomicActivity ");
            
            FinanciamentosOnlineEndpointService santanderService = new FinanciamentosOnlineEndpointService(
                                    new URL(configFile.getProperty("santander.webservice.financiamentos.endpoint.wsdl")));
            santanderService.setHandlerResolver(new HeaderHandlerResolver(configFile));
            FinanciamentosOnlineEndpoint proxy = santanderService.getFinanciamentosOnlineEndpointPort();
            
            DominiosClientResponse response = proxy.listaAtividadesEconomicas(new AtividadeEconomicaClientRequest());
            
            
            if(!AppUtil.isNullOrEmpty(response.getDominios())){
                for(DominioDTO dominio : response.getDominios().getOpcoes()){
                    //encontra item do dominio
                    if(String.valueOf(codeItem).trim().equalsIgnoreCase(dominio.getCodigo())){
                        return dominio;
                    }
                }
            }

            LOGGER.debug(" >> END findSantanderEconomicActivity ");
            return null;
            
        }catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * 
     * @param codeItem
     * @return
     * @throws UnexpectedException
     */
    public DominioDTO findSantanderBusinessRelationshipType(String codeItem)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT findSantanderBusinessRelationshipType ");
            
            FinanciamentosOnlineEndpointService santanderService = new FinanciamentosOnlineEndpointService(
                                    new URL(configFile.getProperty("santander.webservice.financiamentos.endpoint.wsdl")));
            santanderService.setHandlerResolver(new HeaderHandlerResolver(configFile));
            FinanciamentosOnlineEndpoint proxy = santanderService.getFinanciamentosOnlineEndpointPort();
            
            DominiosClientResponse response = proxy.listarTipoRelacionamento(new TipoRelacionamentoClientRequest());
            
            if(!AppUtil.isNullOrEmpty(response.getDominios())){
                for(DominioDTO dominio : response.getDominios().getOpcoes()){
                    //encontra item do dominio
                    if(String.valueOf(codeItem).trim().equalsIgnoreCase(dominio.getCodigo())){
                        return dominio;
                    }
                }
            }

            LOGGER.debug(" >> END findSantanderBusinessRelationshipType ");
            return null;
            
        }catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
