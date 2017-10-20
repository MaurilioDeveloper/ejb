package com.rci.omega2.async.tex.car.insurance.ws.services;

import java.io.IOException;
import java.io.StringReader;

import javax.ejb.EJB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rci.omega2.tex.service.stub.TeleportserviceStub;
import com.rci.omega2.ejb.bo.ConfigFileBO;
import com.rci.omega2.entity.CarInsuranceEntity;

import uteleport_teleport.ObterCotacaoDocument;
import uteleport_teleport.ObterCotacaoDocument.ObterCotacao;
import uteleport_teleport.ObterCotacaoResponseDocument;

public class ObterCotacaoService {

    @EJB
    private ConfigFileBO configFile;
    
    private static final String RETURN_TAG = "return";

    public byte[] ObterCotacao(CarInsuranceEntity carInsuranceEntity) throws ParserConfigurationException, SAXException, IOException {
        TeleportserviceStub stub = new TeleportserviceStub(configFile.getProperty("nimbol.webservice.security.url"));
        ObterCotacaoDocument req = ObterCotacaoDocument.Factory.newInstance();
        ObterCotacao reqobj = ObterCotacao.Factory.newInstance();
        reqobj.setSenha(configFile.getProperty("nimbol.webservice.security.password"));
        reqobj.setCodCorr(Integer.parseInt(configFile.getProperty("nimbol.webservice.security.user")));
        reqobj.setSenhaCorr(configFile.getProperty("nimbol.webservice.security.key"));
        reqobj.setCalculo(carInsuranceEntity.getId().intValue());
        reqobj.setItem(Integer.parseInt(carInsuranceEntity.getFranchiseId()));
        reqobj.setCias(Float.toString(carInsuranceEntity.getCarInsurer().getId()));
        req.setObterCotacao(reqobj);
        ObterCotacaoResponseDocument retorno = stub.obter_Cotacao(req);
        return org.apache.commons.codec.binary.Base64.decodeBase64(getContentByTag(retorno.toString(), RETURN_TAG));
    }
    
    public byte[] ObterCotacao(int p1, int p2, String p3) throws ParserConfigurationException, SAXException, IOException {
        TeleportserviceStub stub = new TeleportserviceStub("http://sandbox.teleport.com.br:8008/soap/Teleport");
        ObterCotacaoDocument req = ObterCotacaoDocument.Factory.newInstance();
        ObterCotacao reqobj = ObterCotacao.Factory.newInstance();
        reqobj.setSenha("G580r$fW$$$@@fhOt%5029#fZZZs%8jQp.nX*tf86.T%gAgp");
        reqobj.setCodCorr(15457);
        reqobj.setSenhaCorr("L5p9k%x5ca2");
        reqobj.setCalculo(p1);
        reqobj.setItem(p2);
        reqobj.setCias(p3);
        req.setObterCotacao(reqobj);
        ObterCotacaoResponseDocument retorno = stub.obter_Cotacao(req);
        return org.apache.commons.codec.binary.Base64.decodeBase64(getContentByTag(retorno.toString(), RETURN_TAG));
    }
    
    private String getContentByTag(String xml, String tag) throws ParserConfigurationException, SAXException, IOException {
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

}
