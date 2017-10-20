package com.rci.omega2.ejb.integrations.santander.soaputils;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    private TokenSecurityConfiguration pTokenSecurity;

    public HeaderHandler(TokenSecurityConfiguration pTokenSecurity) {
        super();

        this.pTokenSecurity = pTokenSecurity;

        if (this.pTokenSecurity == null) {
            new RuntimeException("The property tokensecurityconfiguration was not found");
        }

    }

    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get("javax.xml.ws.handler.message.outbound");


        try {
            if (outboundProperty.booleanValue()) {
                SOAPMessage message = smc.getMessage();

                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();

                SOAPHeader header = envelope.getHeader();

                SOAPElement security = header.addChildElement("security", "token", "http://santander-fo");

                SOAPElement username = security.addChildElement("username", "", "http://santander-fo");
                username.addTextNode(pTokenSecurity.getUsername());

                SOAPElement key = security.addChildElement("key", "", "http://santander-fo");
                key.addTextNode(pTokenSecurity.getKey());

                SOAPElement cnpj = security.addChildElement("cnpj", "", "http://santander-fo");
                cnpj.addTextNode(pTokenSecurity.getCnpj());

                SOAPElement codigoGrupoCanal = security.addChildElement("codigoGrupoCanal", "", "http://santander-fo");
                codigoGrupoCanal
                        .addTextNode(pTokenSecurity.getCodigoGrupoCanal());

                SOAPElement numeroIntermediario = security.addChildElement("numeroIntermediario", "",
                        "http://santander-fo");
                numeroIntermediario.addTextNode(pTokenSecurity.getNumeroIntermediario());
                smc.getMessage().saveChanges();
                
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                message.writeTo(out);
                String strMsg = new String(out.toByteArray());
            } else {
                SOAPMessage message = smc.getMessage();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                message.writeTo(out);

                String strMsg = new String(out.toByteArray());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outboundProperty.booleanValue();
    }

    public Set<QName> getHeaders() {
        QName securityHeader = new QName("http://santander-fo", "security", "token");
        HashSet<QName> headers = new HashSet<QName>();
        headers.add(securityHeader);
        return headers;
    }

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    public void close(MessageContext context) {
    }
}
