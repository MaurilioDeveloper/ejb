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

import com.rci.omega2.tex.service.classtype.LocaisRgType;
import com.rci.omega2.tex.service.stub.TeleportserviceStub;
import com.rci.omega2.ejb.bo.ConfigFileBO;

import uteleport_teleport.ConsultaLocaisExpedicaoRGDocument;
import uteleport_teleport.ConsultaLocaisExpedicaoRGDocument.ConsultaLocaisExpedicaoRG;
import uteleport_teleport.ConsultaLocaisExpedicaoRGResponseDocument;

public class ConsultaLocaisExpedicaoRgService {

    @EJB
    private ConfigFileBO configFile;
    
    private static final String RETURN_TAG = "return";
    private static final String ENCOLDING_UTF8 = "utf-8";
    private static final String ENCOLDING_ISO = "ISO-8859-1";
    
    public LocaisRgType consultaLocaisExpedicaoRG() throws UnsupportedEncodingException, JAXBException, ParserConfigurationException, SAXException, IOException{
        TeleportserviceStub stub = new TeleportserviceStub(configFile.getProperty("nimbol.webservice.security.url"));
        ConsultaLocaisExpedicaoRGDocument req = ConsultaLocaisExpedicaoRGDocument.Factory.newInstance();
        ConsultaLocaisExpedicaoRG reqobj = ConsultaLocaisExpedicaoRG.Factory.newInstance();
        reqobj.setSenha(configFile.getProperty("nimbol.webservice.security.password"));
        req.setConsultaLocaisExpedicaoRG(reqobj);
        ConsultaLocaisExpedicaoRGResponseDocument retorno = stub.consultaLocaisExpedicaoRG(req);
        return  convertClassLOCAISRGType(getContentByTag(retorno.toString(), RETURN_TAG));
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
    
    private LocaisRgType convertClassLOCAISRGType(String xml) throws JAXBException, UnsupportedEncodingException{        
        JAXBContext jaxbContext = JAXBContext.newInstance(LocaisRgType.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(xml.getBytes(ENCOLDING_UTF8)), ENCOLDING_ISO));
        JAXBElement<LocaisRgType> rootElement = (JAXBElement<LocaisRgType>) jaxbUnmarshaller.unmarshal(new StreamSource(in), LocaisRgType.class);
        return rootElement.getValue();
    }
}
