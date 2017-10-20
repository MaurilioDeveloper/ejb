package com.rci.omega2.ejb.bo;

import java.net.URL;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.AgenteCertConsultaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.AgenteCertConsultaClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.AgenteCertificadoDTO;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpoint;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpointService;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.integrations.santander.soaputils.HeaderHandlerResolver;
import com.rci.omega2.ejb.utils.AppConstants;

@Stateless
public class CertifiedAgentBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(CertifiedAgentBO.class);

    @EJB
    private ConfigFileBO configFile;
    
    
    public List<AgenteCertificadoDTO> findCertifiedAgentFromSantander(String tab) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCertifiedAgentFromSantander ");
            
            FinanciamentosOnlineEndpointService santanderService = new FinanciamentosOnlineEndpointService(new URL(configFile.getProperty("santander.webservice.financiamentos.endpoint.wsdl")));
            santanderService.setHandlerResolver(new HeaderHandlerResolver(configFile));
            FinanciamentosOnlineEndpoint proxy = santanderService.getFinanciamentosOnlineEndpointPort();

            AgenteCertConsultaClientRequest request = new AgenteCertConsultaClientRequest();

            request.setNumeroIntermediario(tab);

            AgenteCertConsultaClientResponse response = proxy.listaAgenteCertificado(request);

            LOGGER.info("WSDL listaAgenteCertificado");
            LOGGER.info("RESULT CODE: " + response.getCodigoRetorno());
            LOGGER.info("ERROR CODE: " + response.getCodigoErro());
            LOGGER.info("ERROR MESSAGE: " + response.getDescricaoErro());
            
            List<AgenteCertificadoDTO> temp = response.getAgentesCertificados();
            
            LOGGER.debug(" >> END findCertifiedAgentFromSantander ");
            return temp;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
