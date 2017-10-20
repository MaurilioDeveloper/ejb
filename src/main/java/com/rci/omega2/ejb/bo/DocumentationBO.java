package com.rci.omega2.ejb.bo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import com.altec.bsbr.app.afc.webservice.impl.DocumentResponse;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpoint;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpointService;
import com.altec.bsbr.app.afc.webservice.impl.ImprimeDocumentosGeraisClientRequest;
import com.amazonaws.util.IOUtils;
import com.rci.omega2.async.tex.car.insurance.ws.services.ObterCotacaoService;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.DocumentTemplateDAO;
import com.rci.omega2.ejb.dao.ParamDAO;
import com.rci.omega2.ejb.dao.ProposalDAO;
import com.rci.omega2.ejb.dao.jdbc.ProposalJdbcDAO;
import com.rci.omega2.ejb.dto.DocumentTemplateDTO;
import com.rci.omega2.ejb.dto.DocumentationDTO;
import com.rci.omega2.ejb.dto.MailAttachDTO;
import com.rci.omega2.ejb.dto.MailDTO;
import com.rci.omega2.ejb.dto.ProposalEmailDTO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.integrations.santander.soaputils.HeaderHandlerResolver;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.DocumentationConstants;
import com.rci.omega2.ejb.utils.EmailEnum;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.entity.CarInsuranceEntity;
import com.rci.omega2.entity.DossierStatusEntity;
import com.rci.omega2.entity.FinanceTypeEntity;
import com.rci.omega2.entity.FinancialBrandEntity;
import com.rci.omega2.entity.ParamEntity;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalServiceEntity;
import com.rci.omega2.entity.enumeration.StatusSantanderEnum;
import com.rci.omega2.entity.enumeration.VehicleTypeEnum;
import com.rci.omega2.entity.util.enumation.ParamEnum;

@Stateless
public class DocumentationBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(DocumentationBO.class);

    @EJB
    private ConfigFileBO configFile;

    @EJB
    private S3AccessBO s3AccessBO;

    @EJB
    private SendMailBO sendMailBO;

    /**
     * 
     * @param adp
     * @throws MalformedURLException 
     * @throws UnexpectedException
     */
    public byte[] findDocumentToPrintFromSantander(String adp, String tab, Integer tipoRelatorio) throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT findDocumentToPrintFromSantander ");
            
            FinanciamentosOnlineEndpointService santanderService = new FinanciamentosOnlineEndpointService(new URL(configFile.getProperty("santander.webservice.financiamentos.endpoint.wsdl")));
            santanderService.setHandlerResolver(new HeaderHandlerResolver(configFile));
            FinanciamentosOnlineEndpoint proxy = santanderService.getFinanciamentosOnlineEndpointPort();

            ImprimeDocumentosGeraisClientRequest request = new ImprimeDocumentosGeraisClientRequest();

            request.setNumeroIntermediario(tab);
            request.setNumeroDocumento(new Long(adp));
            request.setTipoRelatorio(tipoRelatorio);

            DocumentResponse response = proxy.imprimeDocumentosGerais(request);
            
            if(AppUtil.isNullOrEmpty(response.getContent()) || AppUtil.isNullOrEmpty(response.getContent().getBytes())){
                LOGGER.debug("SANTANDER NAO RETORNOU ARQUIVO PARA O ADP " + adp + " - SANTANDER RETURN CODE " + response.getResultCode());
                throw new BusinessException("msg-bad-request-service-santander");
            }

            byte[] bytes = response.getContent().getBytes();

            byte[] temp = org.apache.commons.codec.binary.Base64.decodeBase64(bytes);
            
            LOGGER.debug(" >> END findDocumentToPrintFromSantander ");
            
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    public byte[] findDocumentToPrintFromNimble(CarInsuranceEntity carInsuranceEntity) throws UnexpectedException {
        try {
            ObterCotacaoService obterCotacaoService = new ObterCotacaoService();
            return obterCotacaoService.ObterCotacao(carInsuranceEntity);
        } catch (ParserConfigurationException e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } catch (SAXException e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } catch (IOException e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public List<DocumentationDTO> findDocumentTemplateByDossierId(Long dossierId) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT findDocumentTemplateByDossierId ");
        ProposalDAO proposalDAO = daoFactory(ProposalDAO.class);
        ProposalEntity proposal = proposalDAO.findProposalWithAdpByDossier(dossierId);
        validateDossierStatus(proposal.getDossier().getDossierStatus());
        
        VehicleTypeEnum vehicleType = proposal.getDossier().getDossierVehicle().getVehicleVersion().getVehicleType();
        FinancialBrandEntity financialBrand = proposal.getDossier().getFinancialBrand();
        FinanceTypeEntity financialType = proposal.getFinanceType();
        
        DocumentTemplateDAO dao = daoFactory(DocumentTemplateDAO.class);
        List<DocumentTemplateDTO> listDocumentTemplate = dao.findDocumentTemplateByDossierId(vehicleType, financialBrand, financialType);
        
        List<DocumentationDTO> documents = new ArrayList<>();
        
        // DOCUMENTO CET
        documents.add(populateDocumentationDTO(DocumentationConstants.CET_NAME, DocumentationConstants.CET_DESCRIPTION, DocumentationConstants.ID_DOC_CET));
        
        // DOCUMENTO CCB
        if(financialType.getId().equals(AppConstants.TYPE_FINANCING_CDC) || financialType.getId().equals(AppConstants.TYPE_FINANCING_CDC_FLEX)){
            if(validateStatusSantander(proposal)){
                documents.add(populateDocumentationDTO(DocumentationConstants.CCB_NAME, DocumentationConstants.CCB_DESCRIPTION, DocumentationConstants.ID_DOC_CCB));
            }
        }
        
        // DOCUMENTO APROVACAO
        documents.add(populateDocumentationDTO(DocumentationConstants.APROVACAO_NAME, DocumentationConstants.APROVACAO_DESCRIPTION, DocumentationConstants.ID_DOC_APROVACAO));
        
        for (ProposalServiceEntity entity : proposal.getListProposalService()) {
            if(entity.getService().getServiceType().getId().equals(AppConstants.SPF_KEY_ID)){
                // DOCUMENTO SPF - Seguro proteção financeira
                if(validateStatusSantander(proposal)){
                    documents.add(populateDocumentationDTO(DocumentationConstants.SPF_NAME, DocumentationConstants.SPF_DESCRIPTION, DocumentationConstants.ID_DOC_SPF));
                }
                continue;
            }
            
            if(entity.getService().getServiceType().getId().equals(AppConstants.GAP_KEY_ID)){
                // DOCUMENTO GAP - Seguro tranquilidade
                if(validateStatusSantander(proposal)){
                    documents.add(populateDocumentationDTO(DocumentationConstants.SEGURO_TRANQUILIDADE_NAME, DocumentationConstants.SEGURO_TRANQUILIDADE_DESCRIPTION, DocumentationConstants.ID_DOC_SEGURO_TRANQUILIDADE));
                }
                continue;
            }
            
            
            if(entity.getService().getServiceType().getId().equals(AppConstants.COTIZADOR_KEY_ID)){
                // DOCUMENTO COTIZADOR
                documents.add(populateDocumentationDTO(DocumentationConstants.COTIZADOR_NAME, DocumentationConstants.COTIZADOR_DESCRIPTION, DocumentationConstants.ID_DOC_COTIZADOR));
                continue;
            }
        }
        
        for (DocumentTemplateDTO item : listDocumentTemplate) {
            // DOCUMENTO CCBCLAUSULAS
            if (item.getImportCode().equals(DocumentationConstants.CDCR) || item.getImportCode().equals(DocumentationConstants.CDCN)) {
                documents.add(populateDocumentationDTO(item.getName(), item.getDescription(), DocumentationConstants.ID_DOC_CCBCLAUSULAS, item.getLink()));
            }
            
            // DOCUMENTO CTRL
            if (item.getImportCode().equals(DocumentationConstants.CTRLR) || item.getImportCode().equals(DocumentationConstants.CTRLN)) {
                documents.add(populateDocumentationDTO(item.getName(), item.getDescription(), DocumentationConstants.ID_DOC_CTRL, item.getLink()));
            }
            
            // DOCUMENTO CC
            if (item.getImportCode().equals(DocumentationConstants.CCR) || item.getImportCode().equals(DocumentationConstants.CCN)) {
                documents.add(populateDocumentationDTO(item.getName(), item.getDescription(), DocumentationConstants.ID_DOC_CC, item.getLink()));
            }
            
            // DOCUMENTO VISTORIA
            if (item.getImportCode().equals(DocumentationConstants.VISTORIAR) || item.getImportCode().equals(DocumentationConstants.VISTORIAN)) {
                documents.add(populateDocumentationDTO(item.getName(), item.getDescription(), DocumentationConstants.ID_DOC_VISTORIA, item.getLink()));
            }
        }
        
        LOGGER.debug(" >> END findDocumentTemplateByDossierId ");
        return documents;
    }
    
    private Boolean validateStatusSantander(ProposalEntity proposal) {
        
        LOGGER.debug(" >> INIT validateStatusSantander ");
        if(StatusSantanderEnum.AP.equals(proposal.getDossier().getStatusSantander())){
            return Boolean.TRUE;
        }
        if(StatusSantanderEnum.EF.equals(proposal.getDossier().getStatusSantander())){
            return Boolean.TRUE;
        }
        
        Boolean temp = Boolean.FALSE;
        
        LOGGER.debug(" >> END validateStatusSantander ");
        return temp;
    }
    
    private DocumentationDTO populateDocumentationDTO(String nameDoc, String description, Integer id){
        return populateDocumentationDTO(nameDoc, description, id, null);
    }
    
    private DocumentationDTO populateDocumentationDTO(String nameDoc, String description, Integer id, String link){
        
        LOGGER.debug(" >> INIT populateDocumentationDTO ");
        
        DocumentationDTO dto = new DocumentationDTO();
        dto.setNameDoc(nameDoc);
        dto.setDescription(description);
        dto.setIdDoc(id);
        dto.setLink(link);
        
        LOGGER.debug(" >> END populateDocumentationDTO ");
        return dto;
    }
    
    private void validateDossierStatus(DossierStatusEntity status) throws BusinessException {
        LOGGER.debug(" >> INIT validateDossierStatus ");
        if(!GeneralUtils.showDocumentList(status)){
            throw new BusinessException("Documentos indisponíveis para o status atual desta proposta");
        }
        LOGGER.debug(" >> END validateDossierStatus ");
    }
    
    public List<DocumentTemplateDTO> findDocumentsTemplates(List<String> lsDocsToFind) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findDocumentsTemplates ");
            
            DocumentTemplateDAO dao = daoFactory(DocumentTemplateDAO.class);
            List<DocumentTemplateDTO> temp = dao.findDocumentsTemplates(lsDocsToFind);
            
            LOGGER.debug(" >> END findDocumentsTemplates ");
            return temp;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    public ParamEntity findParam(ParamEnum param) throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT findParam ");
            
            ParamDAO dao = daoFactory(ParamDAO.class);
            ParamEntity temp = dao.find(param);
            
            LOGGER.debug(" >> END findParam ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public byte[] getFileS3(String link) throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT getFileS3 ");
            InputStream is = s3AccessBO.getObjectFromS3(link);

            byte[] bytes = IOUtils.toByteArray(is);

            LOGGER.debug(" >> END getFileS3 ");
            return bytes;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    public void sendDocByEmail(Long dossierId, byte[] attachment, String newSubject) throws UnexpectedException {
        
       
        try {
            LOGGER.debug(" >> INIT sendDocByEmail ");
            
            ProposalJdbcDAO dao = daoJdbcFactory(ProposalJdbcDAO.class);
            ProposalEmailDTO dto = dao.findEmailInformationByDossier(dossierId);

            MailDTO mailDto = new MailDTO();

            if (newSubject != null) {
                mailDto.setSubject(newSubject + " " + dto.getSimulationNumber());
            } else {
                mailDto.setSubject(dto.getSimulationStatus() + " " + dto.getSimulationNumber());
            }

            mailDto.setContent("Caro Senhor/Senhora <br><br>" + "Segue em anexo o documento solicitado. <br><br>"
                    + dto.getSimulationStatus() + " " + dto.getSimulationNumber() + "<br><br>"
                    + "Caso necessite de alguma informa&ccedil;&atilde;o adicional, por favor, entre em contato conosco.<br><br>"
                    + "Cordialmente<br><br>" + dto.getSalesmanName() + "<br>" + dto.getSalesmanEmail() + "<br>"
                    + dto.getDealershipName());
            List<String> emails = new ArrayList<String>();
            emails.add(dto.getClientEmail());
            mailDto.setDestinationList(emails);
            mailDto.setEmailEnum(EmailEnum.RCI);

            MailAttachDTO attachmentDto = new MailAttachDTO(attachment, "documentacao.pdf", "application/pdf");
            List<MailAttachDTO> listAttachment = new ArrayList<MailAttachDTO>();
            listAttachment.add(attachmentDto);
            mailDto.setAttachments(listAttachment);

            sendMailBO.sendMessage(mailDto);
            
            LOGGER.debug(" >> END sendDocByEmail ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
