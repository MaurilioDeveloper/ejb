package com.rci.omega2.async.tex.car.insurance.ws.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.ejb.EJB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rci.omega2.tex.service.classtype.CotacaoType;
import com.rci.omega2.tex.service.stub.TeleportserviceStub;
import com.rci.omega2.ejb.bo.ConfigFileBO;

import uteleport_teleport.RecuperarCalculoDocument;
import uteleport_teleport.RecuperarCalculoDocument.RecuperarCalculo;
import uteleport_teleport.RecuperarCalculoResponseDocument;

public class RecuperarCalculoService {
    
    @EJB
    private ConfigFileBO configFile;
    
    private static final String RETURN_TAG = "return";
    private static final String ENCOLDING_UTF8 = "utf-8";
    private static final String ENCOLDING_ISO = "ISO-8859-1";
    
    public CotacaoType recuperarCalculo(String novoToken) throws UnsupportedEncodingException, JAXBException, ParserConfigurationException, SAXException, IOException{
        TeleportserviceStub stub = new TeleportserviceStub(configFile.getProperty("nimbol.webservice.security.url"));
        RecuperarCalculoDocument req = RecuperarCalculoDocument.Factory.newInstance();
        RecuperarCalculo reqobj = RecuperarCalculo.Factory.newInstance();
        reqobj.setSenha(configFile.getProperty("nimbol.webservice.security.password"));
        reqobj.setNovoToken(novoToken);
        req.setRecuperarCalculo(reqobj);
        RecuperarCalculoResponseDocument retorno = stub.recuperarCalculo(req);
        return  convertClassCOTACAOType(getContentByTag(retorno.toString(), RETURN_TAG));
    }

    private String getContentByTag(String xml, String tag) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        Element rootElement = document.getDocumentElement();
        return getString(tag, rootElement);
    }
    
    private String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }
    
    private CotacaoType convertClassCOTACAOType(String xml) throws JAXBException, UnsupportedEncodingException{        
        JAXBContext jaxbContext = JAXBContext.newInstance(CotacaoType.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(xml.getBytes(ENCOLDING_UTF8)), ENCOLDING_ISO));
        JAXBElement<CotacaoType> rootElement = (JAXBElement<CotacaoType>) jaxbUnmarshaller.unmarshal(new StreamSource(in), CotacaoType.class);
        return rootElement.getValue();
    }

}
