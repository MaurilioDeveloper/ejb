package com.rci.omega2.ejb.integrations.santander.soaputils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import com.rci.omega2.ejb.bo.ConfigFileBO;

public class HeaderHandlerResolver implements HandlerResolver {
    private TokenSecurityConfiguration pTokenSecurity;

    public HeaderHandlerResolver() {
        super();
        pTokenSecurity = new TokenSecurityConfiguration();
        ConfigFileBO conf = new ConfigFileBO();
        conf.init();
        pTokenSecurity.setUsername(conf.getProperty( "santander.webservice.security.username"));
        pTokenSecurity.setKey(conf.getProperty( "santander.webservice.security.key"));
        pTokenSecurity.setCnpj(conf.getProperty( "santander.webservice.security.cnpj"));
        pTokenSecurity.setCodigoGrupoCanal(conf.getProperty( "santander.webservice.security.codigogrupocanal"));
        pTokenSecurity.setActiveNumeroIntermediario(conf.getProperty( "santander.webservice.security.active.numerointermediario"));
        pTokenSecurity.setNumeroIntermediario(conf.getProperty( "santander.webservice.security.numerointermediario"));
 
    }
    
    public HeaderHandlerResolver(ConfigFileBO conf) {
        pTokenSecurity = new TokenSecurityConfiguration();
        pTokenSecurity.setUsername(conf.getProperty( "santander.webservice.security.username"));
        pTokenSecurity.setKey(conf.getProperty( "santander.webservice.security.key"));
        pTokenSecurity.setCnpj(conf.getProperty( "santander.webservice.security.cnpj"));
        pTokenSecurity.setCodigoGrupoCanal(conf.getProperty( "santander.webservice.security.codigogrupocanal"));
        pTokenSecurity.setActiveNumeroIntermediario(conf.getProperty( "santander.webservice.security.active.numerointermediario"));
        pTokenSecurity.setNumeroIntermediario(conf.getProperty( "santander.webservice.security.numerointermediario"));
 
    }
    
    @SuppressWarnings("rawtypes")
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        List<Handler> handlerChain = new ArrayList<Handler>();

        HeaderHandler hh = new HeaderHandler(pTokenSecurity);

        handlerChain.add(hh);

        return handlerChain;
    }
}
