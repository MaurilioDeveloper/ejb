package com.rci.omega2.ejb.bo;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.AgenteCertificadoDTO;
import com.altec.bsbr.app.afc.webservice.impl.DadosAdpClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.DadosAdpClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.DadosPessoaisClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.DadosPessoaisClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpoint;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpointService;
import com.altec.bsbr.app.afc.webservice.impl.InsereEnderecoClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereEnderecoClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InsereGarantiaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereGarantiaClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InserePropostaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InserePropostaClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InsereReferenciaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereReferenciaClientResponse;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.DossierDAO;
import com.rci.omega2.ejb.dao.ProposalDAO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.integrations.santander.Utils;
import com.rci.omega2.ejb.integrations.santander.soaputils.HeaderHandlerResolver;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.AppOrderUtils;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.ejb.utils.SantanderConstants;
import com.rci.omega2.ejb.utils.SantanderServicesUtils;
import com.rci.omega2.ejb.utils.ValidateConstants;
import com.rci.omega2.entity.CustomerReferenceEntity;
import com.rci.omega2.entity.DossierEntity;
import com.rci.omega2.entity.DossierStatusEntity;
import com.rci.omega2.entity.GuarantorEntity;
import com.rci.omega2.entity.GuarantorReferenceEntity;
import com.rci.omega2.entity.PhoneEntity;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalServiceEntity;
import com.rci.omega2.entity.SpecialVehicleTypeEntity;
import com.rci.omega2.entity.enumeration.AccountTypeEnum;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;
import com.rci.omega2.entity.enumeration.PhoneTypeEnum;
import com.rci.omega2.entity.enumeration.StatusSantanderEnum;
import com.rci.omega2.entity.enumeration.VehicleTypeEnum;

@Stateless
public class SantanderSubmitBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(SantanderSubmitBO.class);
    
    @EJB
    private CertifiedAgentBO certifiedAgentBO;
    @EJB
    private SantanderReturnErrorBO santanderReturnErrorBO; 
    @EJB
    private DossierBO dossierBO; 
    @EJB
    private ConfigFileBO configFile;
    @EJB
    private SantanderSubmitValidateBO santanderSubmitValidateBO;
    
    private FinanciamentosOnlineEndpoint santanderWebService = null;
    private DossierDAO dossierDao;
    private ProposalDAO proposalDao;
    
    private final SimpleDateFormat SDF_MONTH = new SimpleDateFormat("MM");
    private final SimpleDateFormat SDF_YEAR = new SimpleDateFormat("YYYY");
    
    public void sendDossierToSantander(String dossierIdTokken) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT sendDossierToSantander ");
            Long dossierId = CriptoUtilsOmega2.decryptIdToLong(dossierIdTokken);

            initializeServices();
            
            DossierEntity dossier = dossierDao.findDossierRootFetch(dossierId);
            
            ProposalEntity proposal = proposalDao.findProposalMainByDossier(dossier.getId(), dossier.getAdp());

            santanderSubmitValidateBO.submitValidate(dossier, proposal);
            
            // Step 1
            InserePropostaClientResponse retorno1 = sendStep1(dossier, proposal);
            returnValidate(retorno1.getCodigoRetorno(), retorno1.getCodigoErro(), retorno1.getDescricaoErro());
            
            dossier.setAdp(retorno1.getNumeroPropostaAdp());
            proposal.setAdp(retorno1.getNumeroPropostaAdp());
            
            // Step 2 - cliente
            DadosPessoaisClientResponse retorno2 = sendStep2Client(dossier);
            returnValidate(retorno2.getCodigoRetorno(), retorno2.getCodigoErro(), retorno2.getDescricaoErro());
            String numeroIntermediarioCliente = retorno2.getDadosPessoais().getNumeroInternoCliente();

            InsereEnderecoClientResponse retorno3 = sendStep3Adress(dossier, numeroIntermediarioCliente);
            returnValidate(retorno3.getCodigoRetorno(), retorno3.getCodigoErro(), retorno3.getDescricaoErro());

            InsereReferenciaClientResponse retorno4 = sendStep4Referencias(dossier, numeroIntermediarioCliente);
            returnValidate(retorno4.getCodigoRetorno(), retorno4.getCodigoErro(), retorno4.getDescricaoErro());

            if (dossier.getCustomer().getCustomerSpouse() != null) {

                DadosPessoaisClientResponse retorno2s = sendStep2ClientSpouse(dossier);
                returnValidate(retorno2s.getCodigoRetorno(), retorno2s.getCodigoErro(), retorno2s.getDescricaoErro());
                
                String numeroIntermediarioClienteSpouse = retorno2.getDadosPessoais().getNumeroInternoCliente();

                InsereEnderecoClientResponse retorno3s = sendStep3AdressSpouse(dossier, numeroIntermediarioClienteSpouse);
                returnValidate(retorno3s.getCodigoRetorno(), retorno3s.getCodigoErro(), retorno3s.getDescricaoErro());
            }

            int idGarantor = 1;
            for (GuarantorEntity guarantorEntity : AppOrderUtils.ordinateGuarantorList(dossier.getListGuarantor())) {

                DadosPessoaisClientResponse retorno2Guarantor = sendStep2Guarantor(dossier, guarantorEntity, false, idGarantor, proposal);
                returnValidate(retorno2Guarantor.getCodigoRetorno(), retorno2Guarantor.getCodigoErro(), retorno2Guarantor.getDescricaoErro());
                
                String numeroIntermediarioGuarantor = retorno2Guarantor.getDadosPessoais().getNumeroInternoCliente();

                InsereEnderecoClientResponse retorno3Guarantor = sendStep3AdressGuarantor(dossier, guarantorEntity, numeroIntermediarioGuarantor, false, idGarantor, proposal);
                returnValidate(retorno3Guarantor.getCodigoRetorno(), retorno3Guarantor.getCodigoErro(), retorno3Guarantor.getDescricaoErro());

                InsereReferenciaClientResponse retorno4Guarantor = sendStep4ReferenciasGuarantor(dossier, guarantorEntity, numeroIntermediarioGuarantor, false, idGarantor, proposal);
                returnValidate(retorno4Guarantor.getCodigoRetorno(), retorno4Guarantor.getCodigoErro(), retorno4Guarantor.getDescricaoErro());

                if (guarantorEntity.getGuarantorSpouse() != null) {
                    DadosPessoaisClientResponse retorno2SpouseGuarantor = sendStep2SpouseGuarantor(dossier, guarantorEntity, false, idGarantor, proposal);
                    returnValidate(retorno2SpouseGuarantor.getCodigoRetorno(), retorno2SpouseGuarantor.getCodigoErro(), retorno2SpouseGuarantor.getDescricaoErro());

                    String numeroIntermediarioSpouseGuarantor = retorno2Guarantor.getDadosPessoais().getNumeroInternoCliente();
                    
                    InsereEnderecoClientResponse retorno3SpouseGuarantor = sendStep3AdressSpouseGuarantor(dossier, guarantorEntity, numeroIntermediarioSpouseGuarantor, false, idGarantor, proposal);
                    returnValidate(retorno3SpouseGuarantor.getCodigoRetorno(), retorno3SpouseGuarantor.getCodigoErro(), retorno3SpouseGuarantor.getDescricaoErro());
                }

                if (proposal.getFinanceType().getId().equals(AppConstants.TYPE_FINANCING_LEASING) && dossier.getCustomer().getPersonType().equals(PersonTypeEnum.PJ) && idGarantor == 1) {
                    DadosPessoaisClientResponse retorno2FaithfulCustodian = sendStep2Guarantor(dossier, guarantorEntity, true, idGarantor, proposal);
                    returnValidate(retorno2FaithfulCustodian.getCodigoRetorno(), retorno2FaithfulCustodian.getCodigoErro(), retorno2FaithfulCustodian.getDescricaoErro());
                    
                    String faithfulCustodian = retorno2Guarantor.getDadosPessoais().getNumeroInternoCliente();
                    InsereEnderecoClientResponse retorno3FaithfulCustodian = sendStep3AdressGuarantor(dossier, guarantorEntity, faithfulCustodian, false, idGarantor, proposal);
                    returnValidate(retorno3FaithfulCustodian.getCodigoRetorno(), retorno3FaithfulCustodian.getCodigoErro(), retorno3FaithfulCustodian.getDescricaoErro());

                    InsereReferenciaClientResponse retorno4FaithfulCustodianr = sendStep4ReferenciasGuarantor(dossier, guarantorEntity, faithfulCustodian, false, idGarantor, proposal);
                    returnValidate(retorno4FaithfulCustodianr.getCodigoRetorno(), retorno4FaithfulCustodianr.getCodigoErro(), retorno4FaithfulCustodianr.getDescricaoErro());

                    if (guarantorEntity.getGuarantorSpouse() != null) {
                        DadosPessoaisClientResponse retorno2SpouseFaithfulCustodian = sendStep2SpouseGuarantor(dossier, guarantorEntity, false, idGarantor, proposal);
                        returnValidate(retorno2SpouseFaithfulCustodian.getCodigoRetorno(), retorno2SpouseFaithfulCustodian.getCodigoErro(), retorno2SpouseFaithfulCustodian.getDescricaoErro());

                        String numeroIntermediarioSpouseFaithfulCustodian = retorno2FaithfulCustodian.getDadosPessoais().getNumeroInternoCliente();
                        InsereEnderecoClientResponse retorno3SpouseFaithfulCustodian = sendStep3AdressSpouseGuarantor(dossier, guarantorEntity, numeroIntermediarioSpouseFaithfulCustodian, false, idGarantor, proposal);
                        returnValidate(retorno3SpouseFaithfulCustodian.getCodigoRetorno(), retorno3SpouseFaithfulCustodian.getCodigoErro(), retorno3SpouseFaithfulCustodian.getDescricaoErro());
                    }
                }
                idGarantor++;
            }

            InsereGarantiaClientResponse retorno5 = sendStep5GarantiaClient(dossier);
            returnValidate(retorno5.getCodigoRetorno(), retorno5.getCodigoErro(), retorno5.getDescricaoErro());

            DadosAdpClientResponse retorno6 = sendStep6Fim(dossier);
            returnValidate(retorno6.getCodigoRetorno(), retorno6.getCodigoErro(), retorno6.getDescricaoErro());
            
            dossier.setStatusSantander(StatusSantanderEnum.valueOf(retorno6.getDadosAdp().getNumeroStatusProposta()));
            dossier.setDossierStatus(dossierDao.find(DossierStatusEntity.class, dossier.getStatusSantander().getDossierStatusId()));

            dossierDao.update(dossier);
            proposalDao.update(proposal);
            
            LOGGER.debug(" >> END sendDossierToSantander ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    private void returnValidate(String returnCode, String returnErrorCode, String returnErrorMessage) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT returnValidate ");
        if(AppUtil.isNullOrEmpty(returnCode) || !returnCode.trim().equals(ValidateConstants.SUCCESS_RETURN_CODE)){
            santanderReturnErrorBO.handlerSantanderBusinessException(returnErrorCode, returnErrorMessage);
        }
        LOGGER.debug(" >> END returnValidate ");
    }
    
    private void initializeServices() throws UnexpectedException {
        
        LOGGER.debug(" >> INIT initializeServices ");
        santanderWebService = prepareSantanderWebService();
        
        dossierDao = daoFactory(DossierDAO.class);
        proposalDao = daoFactory(ProposalDAO.class);
        
        LOGGER.debug(" >> END initializeServices ");
    }

    private FinanciamentosOnlineEndpoint prepareSantanderWebService() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT prepareSantanderWebService ");
            FinanciamentosOnlineEndpointService santanderService = new FinanciamentosOnlineEndpointService(new URL(configFile.getProperty("santander.webservice.financiamentos.endpoint.wsdl")));
            santanderService.setHandlerResolver(new HeaderHandlerResolver(configFile));
            
            FinanciamentosOnlineEndpoint temp = santanderService.getFinanciamentosOnlineEndpointPort();
            LOGGER.debug(" >> END prepareSantanderWebService ");
            return temp;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void errorCertifiedAgentFactory() throws BusinessException {
        throw new BusinessException(ValidateConstants.REQUIRED_CERTIFIED_AGENT);
    }
    
    private InserePropostaClientResponse sendStep1(DossierEntity dossier, ProposalEntity proposta) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT sendStep1 ");
            
            InserePropostaClientRequest envio = new InserePropostaClientRequest();
            if (!AppUtil.isNullOrEmpty(dossier.getAdp())) {
                envio.setNumeroPropostaAdp(dossier.getAdp());
            }
            
            envio.setCodigoModalidade(SantanderConstants.CODIGO_MODALIDADE);
            envio.setCodigoFormaPagamento(SantanderConstants.CODIGO_FORMA_PAGAMENTO);
            envio.setCodigoTipoMoeda(SantanderConstants.CODIGO_TIPO_MOEDA);
            envio.setNumeroTabelaFinanciamento(proposta.getProduct().getImportCode());
            
            Calendar calendario = Calendar.getInstance();
            calendario.add(Calendar.DATE, proposta.getDelayValue());
            envio.setDataVencimento1(SantanderServicesUtils.dateToStringSantanderFormat(calendario.getTime()));
            envio.setIsencaoTC(dossier.getTcExempt() ? SantanderConstants.S : SantanderConstants.N);
            
            // FIXO
            envio.setIsencaoTAB(SantanderConstants.N);
            envio.setNumeroIntermediario(dossier.getStructure().getDealership().getTab());
            envio.setNomeVendedor(dossier.getSalesman().getNamePerson());
            
            if (proposta.getFinanceType().getId().equals(AppConstants.TYPE_FINANCING_LEASING)) {
                envio.setNumeroIdProduto(SantanderConstants.NUMERO_ID_PRODUTO_0014);
            } else {
                envio.setNumeroIdProduto(SantanderConstants.NUMERO_ID_PRODUTO_0015);
            }
            
            envio.setNumeroQuantidadePrestacoes(String.valueOf(proposta.getInstalmentQuantity()));
            envio.setValorBem(Utils.bigToString(dossier.getVehiclePrice(), AppConstants.DECIMAL_2));
            envio.setValorEntrada(Utils.bigToString(proposta.getDeposit(), AppConstants.DECIMAL_2));
            envio.setValorPrestacao(Utils.bigToString(proposta.getInstalmentAmount(), AppConstants.DECIMAL_2));
            envio.setValorFinanciamento(Utils.bigToString(proposta.getFinancedAmount(), AppConstants.DECIMAL_2));
            
            // Buscar os serviços e verificar se o mesmo tem spf
            Set<ProposalServiceEntity> services = proposta.getListProposalService();
            boolean haveSPF = false;
            for (ProposalServiceEntity proposalServiceEntity : services) {
                if (proposalServiceEntity.getService().getServiceType().getId().equals(AppConstants.SPF_KEY_ID)) {
                    haveSPF = true;
                    break;
                }
            }
            
            envio.setValorSeguro(haveSPF ? SantanderConstants.VALOR_SEGURO_1 : SantanderConstants.VALOR_SEGURO_0);
            envio.setCodigoComissao(proposta.getCommission().getImportCode());
            
            AgenteCertificadoDTO retorno = findCertifiedAgentAndCertificadeByCode(dossier.getStructure().getDealership().getTab(), Long.valueOf(dossier.getCertifiedAgent()));
            if(AppUtil.isNullOrEmpty(retorno)){
                errorCertifiedAgentFactory();
            }
            
            envio.setCodigoAgenteCertificado(String.valueOf(retorno.getCodAgente()));
            envio.setCodigoCertificadoAgente(retorno.getNumCertificado());
            envio.setIndicadorAleteracaoAgente(SantanderConstants.N);
            
            InserePropostaClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso1Inicio(envio);
            
            LOGGER.debug(" >> END sendStep1 ");
            return response;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private AgenteCertificadoDTO findCertifiedAgentAndCertificadeByCode(String tabCode, Long codeAgent) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT findCertifiedAgentAndCertificadeByCode ");
        List<AgenteCertificadoDTO> retorno = certifiedAgentBO.findCertifiedAgentFromSantander(tabCode);
        for (AgenteCertificadoDTO agenteCertificadoDTO : retorno) {
            if (codeAgent.equals(agenteCertificadoDTO.getCodAgente())) {
                return agenteCertificadoDTO;
            }
        }
        
        LOGGER.debug(" >> END findCertifiedAgentAndCertificadeByCode ");
        return null;
    }
    
    public DadosPessoaisClientResponse sendStep2Client(DossierEntity dossier) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT sendStep2Client ");
        DadosPessoaisClientRequest envio = new DadosPessoaisClientRequest();
        
        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setCodigoTipoPessoa(SantanderServicesUtils.personTypeToForJ(dossier.getCustomer().getPersonType().name()));
        
        String economicActivity = null;
        Long economicGroup = null;
        
        if (dossier.getCustomer().getPersonType().equals(PersonTypeEnum.PF)) {
            envio.setNumeroTipoDocumento(dossier.getCustomer().getDocumentType().getImportCode());
            envio.setCodigoDocumento(dossier.getCustomer().getDocumentNumber());
            envio.setCodigoEstadoOrgaoEmissor(dossier.getCustomer().getDocumentProvince().getAcronym());
            envio.setCodigoPaisDocumento(dossier.getCustomer().getDocumentCountry().getImportCode());
            envio.setDescricaoNaturalidade(dossier.getCustomer().getNaturalness().toUpperCase());
            envio.setCodigoEstadoNaturalidade(dossier.getCustomer().getProvince().getAcronym());
            envio.setCodigoNacionalidade(dossier.getCustomer().getCountry().getImportCode());
            envio.setCodigoSexo(dossier.getCustomer().getPersonGender().getAbbreviation());
            envio.setDataAdmissao(SantanderServicesUtils.dateToStringSantanderFormat(dossier.getCustomer().getEmployer().getAdmissionDate()));
            envio.setDataEmissaoDocumento(SantanderServicesUtils.dateToStringSantanderFormat(dossier.getCustomer().getEmissionDate()));
            if (dossier.getCustomer().getDocumentValidate() != null) {
                envio.setDataValidadeDocumento(SantanderServicesUtils.dateToStringSantanderFormat(dossier.getCustomer().getDocumentValidate()));
            }
            if (dossier.getCustomer().getEmployer().getProfession() != null) {
                envio.setDescricaoProfissao(dossier.getCustomer().getEmployer().getProfession().getDescription());
            }
            envio.setDescricaoTelOutrasRendas(AppConstants.STRING_EMPTY);
            envio.setIndicativoDeficienteFisico(SantanderServicesUtils.boolToSOrN(dossier.getCustomer().getHandicapped()));
            envio.setIndicativoFuncSantander(SantanderConstants.N);
            envio.setNomeEmpresa(dossier.getCustomer().getEmployer().getEmployerName());
            envio.setNomeMae(dossier.getCustomer().getMotherName());
            envio.setNomePai(dossier.getCustomer().getFatherName());
            envio.setNomeOrgaoEmissor(dossier.getCustomer().getEmissionOrganism().getImportCode());
            envio.setNumeroClienteRelac(AppConstants.STRING_EMPTY);
            envio.setNumeroCnpjEmpresa(dossier.getCustomer().getEmployer().getCnpj());
            envio.setNumeroCpfCnpj(dossier.getCustomer().getCpfCnpj());
            
            envio.setNumeroDependentes("000");
            envio.setNumeroEstadoCivil(dossier.getCustomer().getCivilState().getId().toString());
            envio.setNumeroIndicativoGrauParentes(AppConstants.STRING_EMPTY);
            envio.setNumeroInstrucao(dossier.getCustomer().getEducationDegree().getId().toString());
            envio.setNumeroOcupacao(dossier.getCustomer().getEmployer().getOccupation().getImportCode());
            envio.setNumeroPessoaPoliticaExpo(dossier.getCustomer().getPoliticalExposition().getId().toString());
            if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getEmployerSize())){
                envio.setNumeroPorteEmpresarial(dossier.getCustomer().getEmployer().getEmployerSize().toString());
            }
            envio.setNumeroRenda(dossier.getCustomer().getEmployer().getIncomeType().getImportCode());
            envio.setNumeroComprovanteRenda(dossier.getCustomer().getEmployer().getProofIncomeType().getImportCode()); // renda
            envio.setValorOutrasRendas(Utils.bigToString(dossier.getCustomer().getOtherIncome(), 2));
            envio.setValorPatrimonio(Utils.bigToString(dossier.getCustomer().getEmployer().getValueAssets(), 2));
            envio.setValorRendaMensal(Utils.bigToString(dossier.getCustomer().getIncome(), 2));
            envio.setNumeroTpRepresentante("03");
            
            if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getEconomicActivity())){
                economicActivity = dossier.getCustomer().getEmployer().getEconomicActivity().getImportCode();
            }
            if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getIndustrialSegment())){
                economicGroup = dossier.getCustomer().getEmployer().getIndustrialSegment().getId();
            }
        } else {
            envio.setCodigoSedePropria(SantanderServicesUtils.boolToSOrN(dossier.getCustomer().getBuildingOwner()));
            envio.setNumeroClienteRelac("00000001");
            envio.setNumeroCnpjEmpresa(dossier.getCustomer().getCpfCnpj());
            envio.setNumeroCpfCnpj(dossier.getDossierManager().getCpf());
            envio.setNumeroPorteEmpresarial(dossier.getCustomer().getCustomerSize().toString());
            envio.setValorPatrimonio(AppConstants.STRING_EMPTY);
            envio.setValorRendaMensal(Utils.bigToString(dossier.getCustomer().getIncome(), 2));
            envio.setCodigoNaturezaJuridica(dossier.getCustomer().getLegalNature().getImportCode());
            
            economicActivity = dossier.getCustomer().getCustomerEconomicActivity().getImportCode();
            economicGroup = dossier.getCustomer().getCustomerIndustrialSegment().getId();
        }
        
        envio.setNumeroAtividadeEconomica((economicActivity != null ? economicActivity : "000"));
        envio.setNumeroGrupoAtivEconomica((economicGroup != null ? economicGroup.toString() : "000"));
        envio.setNumeroTipoVinculoPart("1");
        
        envio.setDataNascimento(SantanderServicesUtils.dateToStringSantanderFormat(dossier.getCustomer().getDateBirth()));
        envio.setNomeCompleto(dossier.getCustomer().getNameClient());
        
        DadosPessoaisClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso2DadosPessoais(envio);
        
        LOGGER.debug(" >> END sendStep2Client ");
        return response;
        

    }

    private InsereEnderecoClientResponse sendStep3Adress(DossierEntity dossier, String numeroIntermediarioCliente) throws MalformedURLException {

        LOGGER.debug(" >> INIT sendStep3Adress ");
        
        InsereEnderecoClientRequest envio = new InsereEnderecoClientRequest();
        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setCodigoEnderecoCorrespondencia(dossier.getCustomer().getMailingAddressType().getImportCode());

        envio.setNumeroClienteInterno(numeroIntermediarioCliente);

        PhoneEntity cel = null;
        PhoneEntity comercial = null;
        PhoneEntity fixo = null;
        
        if (!dossier.getCustomer().getListPhone().isEmpty()) {
            for (PhoneEntity phone : dossier.getCustomer().getListPhone()) {
                if(!santanderSubmitValidateBO.isValidPhone(phone)){
                    continue;
                }
                if (phone.getPhoneType().equals(PhoneTypeEnum.CELULAR)) {
                    cel = phone;
                }
                if (phone.getPhoneType().equals(PhoneTypeEnum.COMERCIAL)) {
                    comercial = phone;
                }
                if (phone.getPhoneType().equals(PhoneTypeEnum.FIXO)) {
                    fixo = phone;
                }
            }

            if (dossier.getCustomer().getPersonType().equals(PersonTypeEnum.PF)) {
                if (cel != null) {
                    envio.setCodigotelCel(SantanderServicesUtils.formatPhone(cel.getPhone()));
                    envio.setNumeroDddCel(SantanderServicesUtils.formatDDD(cel.getDdd()));
                }
                if (comercial != null) {
                    envio.setDescricaoTelComercial(SantanderServicesUtils.formatPhone(comercial.getPhone()));
                    envio.setNumeroDddTelComercial(SantanderServicesUtils.formatDDD(comercial.getDdd()));
                    envio.setNumeroTelComercialRamal(SantanderServicesUtils.formatRamal(comercial.getExtensionLine()));
                }
            } else {
                if (fixo != null) {
                    envio.setDescricaoTelComercial(SantanderServicesUtils.formatPhone(fixo.getPhone()));
                    envio.setNumeroDddTelComercial(SantanderServicesUtils.formatDDD(fixo.getDdd()));
                    envio.setNumeroTelComercialRamal(SantanderServicesUtils.formatRamal(fixo.getExtensionLine()));
                }
            }
            if (fixo != null) {
                envio.setDescricaotelResidencial(SantanderServicesUtils.formatPhone(fixo.getPhone()));
                envio.setNumeroDddResidencial(SantanderServicesUtils.formatDDD(fixo.getDdd()));
                envio.setNumeroTipoTelefResiden(String.valueOf(dossier.getCustomer().getPersonalPhoneType().getImportCode()));
            }
        }

        Calendar cal = Calendar.getInstance();
        if (dossier.getCustomer().getPersonType().equals(PersonTypeEnum.PF)) {
            envio.setCodigoPaisComercial(AppConstants.STRING_EMPTY);
            envio.setCodigoPaisResidencial(AppConstants.STRING_EMPTY);
            envio.setCodigoSiglaUfComercial(dossier.getCustomer().getEmployerAddress().getProvince().getAcronym());
            envio.setCodigoSiglaUfResidencial(dossier.getCustomer().getProvince().getAcronym());

            cal.setTime(dossier.getCustomer().getAddressSince());
            envio.setDataAnoResideDesde(String.valueOf(cal.get(Calendar.YEAR)));
            envio.setDataMesResideDesde(String.valueOf(cal.get(Calendar.MONTH) + 1));
            envio.setDescricaoComplementoEndComercial(dossier.getCustomer().getEmployerAddress().getComplement());
            envio.setDescricaoEnderecoComercial(dossier.getCustomer().getEmployerAddress().getAddress());
            envio.setDescricaoEnderecoEmail(dossier.getCustomer().getEmail());
            envio.setNomeBairroComercial(dossier.getCustomer().getEmployerAddress().getNeighborhood());
            envio.setNomeCidadeComercial(dossier.getCustomer().getEmployerAddress().getCity());
            envio.setNumeroCepComercial(SantanderServicesUtils.formatPostalCode(dossier.getCustomer().getEmployerAddress().getPostCode()));
            envio.setNumeroEnderecoComercial(SantanderServicesUtils.formatNumber(dossier.getCustomer().getEmployerAddress().getNumber(), 5));
            envio.setNumeroTipoResidencia(String.valueOf(dossier.getCustomer().getResidenceType().getId()));

        } else {
            envio.setCodigoPaisComercial(AppConstants.STRING_EMPTY);
            envio.setCodigoPaisResidencial(AppConstants.STRING_EMPTY);
            envio.setCodigoSiglaUfComercial(dossier.getCustomer().getProvince().getAcronym());
            envio.setCodigoSiglaUfResidencial(dossier.getCustomer().getProvince().getAcronym());
            cal.setTime(dossier.getCustomer().getDateBirth());
            envio.setDataMesResideDesde(String.valueOf(cal.get(Calendar.YEAR)));
            envio.setDataMesResideDesde(String.valueOf(cal.get(Calendar.MONTH) + 1));
            envio.setDescricaoComplementoEndComercial(dossier.getCustomer().getAddress().getComplement());
            envio.setDescricaoEnderecoComercial(dossier.getCustomer().getAddress().getAddress());
            envio.setDescricaoEnderecoEmail(AppConstants.STRING_EMPTY);
            envio.setNomeBairroComercial(dossier.getCustomer().getAddress().getNeighborhood());
            envio.setNomeCidadeComercial(dossier.getCustomer().getAddress().getCity());
            envio.setNumeroCepComercial(SantanderServicesUtils.formatPostalCode(dossier.getCustomer().getAddress().getPostCode()));
            envio.setNumeroEnderecoComercial(dossier.getCustomer().getAddress().getNumber());

        }

        envio.setNumeroResidencial(SantanderServicesUtils.formatNumber(dossier.getCustomer().getAddress().getNumber(), 5));
        envio.setNumeroCepResidencial(SantanderServicesUtils.formatPostalCode(dossier.getCustomer().getAddress().getPostCode()));
        envio.setNomeHomepage(AppConstants.STRING_EMPTY);
        envio.setNomeCidadeResidencial(dossier.getCustomer().getAddress().getCity());
        envio.setDescricaoComplementoResidencia(dossier.getCustomer().getAddress().getComplement());
        envio.setDescricaoEnderecoResidencia(dossier.getCustomer().getAddress().getAddress());
        envio.setNomeBairroResidencial(dossier.getCustomer().getAddress().getNeighborhood());

        InsereEnderecoClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso3Endereco(envio);
        
        LOGGER.debug(" >> END sendStep3Adress ");
        return response;
    }

    private InsereReferenciaClientResponse sendStep4Referencias(DossierEntity dossier, String numeroIntermediarioCliente) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep4Referencias ");
        
        InsereReferenciaClientRequest envio = new InsereReferenciaClientRequest();
        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setNumeroClienteInterno(numeroIntermediarioCliente);
        envio.setNumeroClienteRelacional(AppConstants.STRING_EMPTY);
        
        if (dossier.getCustomer().getPersonType().equals(PersonTypeEnum.PF) && !AppUtil.isNullOrEmpty(dossier.getCustomer().getListCustomerReference())) {
            ArrayList<CustomerReferenceEntity> lista = AppOrderUtils.ordinateCustomerReferenceList(dossier.getCustomer().getListCustomerReference());
            
            if(lista.size() > 0){
                CustomerReferenceEntity reference = lista.get(0);
                envio.setNomeRefer1(reference.getDescription());
                envio.setNumeroDddRefer1(SantanderServicesUtils.formatDDD(reference.getDdd()));
                envio.setDescricaoTelefoneRefer1(SantanderServicesUtils.formatPhone(reference.getPhone()));
            }

            if(lista.size() > 1){
                CustomerReferenceEntity reference = lista.get(1);
                envio.setNomeRefer2(reference.getDescription());
                envio.setNumeroDddRefer2(SantanderServicesUtils.formatDDD(reference.getDdd()));
                envio.setDescricaoTelefoneRefer2(SantanderServicesUtils.formatPhone(reference.getPhone()));
            }
        }

        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getBankDetail()) && !AppUtil.isNullOrEmpty(dossier.getCustomer().getBankDetail().getBank())){
            envio.setCodigoTipoContaBancaria(AccountTypeEnum.CORRENTE.getAcronym());
            envio.setNumeroBanco(dossier.getCustomer().getBankDetail().getBank().getImportCode());
            envio.setNumeroAgencia(dossier.getCustomer().getBankDetail().getBranch());
            envio.setCodigoDigitoAgencia(dossier.getCustomer().getBankDetail().getBranchDigit());
            envio.setNumeroContaCorrente(dossier.getCustomer().getBankDetail().getAccount());
            envio.setCodigoDigitoContaCorrente(dossier.getCustomer().getBankDetail().getAccountDigit());
            envio.setNumeroDddTelefoneBanco(SantanderServicesUtils.formatDDD(dossier.getCustomer().getBankDetail().getDdd()));
            envio.setDescricaoTelefoneBanco(SantanderServicesUtils.formatPhone(dossier.getCustomer().getBankDetail().getPhone()));
            
            if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getBankDetail().getAccountOpeningDate())){
                envio.setNumeroAnoClienteDesde(SDF_YEAR.format(dossier.getCustomer().getBankDetail().getAccountOpeningDate()));
                envio.setNumeroMesClienteDesde(SDF_MONTH.format(dossier.getCustomer().getBankDetail().getAccountOpeningDate()));
            }
        }

        InsereReferenciaClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso4Referencias(envio);
        
        LOGGER.debug(" >> END sendStep4Referencias ");
        return response;
    }
    
    private DadosPessoaisClientResponse sendStep2ClientSpouse(DossierEntity dossier) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep2ClientSpouse ");
        DadosPessoaisClientRequest envio = new DadosPessoaisClientRequest();

        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setCodigoTipoPessoa(SantanderServicesUtils.personTypeToForJ(dossier.getCustomer().getPersonType().name()));

        envio.setCodigoSexo(dossier.getCustomer().getCustomerSpouse().getPersonGender().getAbbreviation());
        envio.setDataAdmissao(SantanderServicesUtils.dateToStringSantanderFormat(dossier.getCustomer().getCustomerSpouse().getAdmissionDate()));
        envio.setDataNascimento(SantanderServicesUtils.dateToStringSantanderFormat(dossier.getCustomer().getCustomerSpouse().getDateBirth()));
        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getCustomerSpouse().getProfession())){
            envio.setDescricaoProfissao(dossier.getCustomer().getCustomerSpouse().getProfession().getDescription());
        }
        envio.setNomeCompleto(dossier.getCustomer().getCustomerSpouse().getNameClient());
        envio.setNomeEmpresa(dossier.getCustomer().getCustomerSpouse().getEmployerName());
        envio.setNumeroClienteRelac("00000001");
        envio.setNumeroCpfCnpj(dossier.getCustomer().getCustomerSpouse().getCpf());
        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getCustomerSpouse().getOccupation())){
            envio.setNumeroOcupacao(dossier.getCustomer().getCustomerSpouse().getOccupation().getImportCode());
        }
        envio.setNumeroTipoVinculoPart("2");
        envio.setValorRendaMensal(Utils.bigToString(dossier.getCustomer().getCustomerSpouse().getIncome(), 2));

        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getCustomerSpouse().getProvince())){
            envio.setCodigoEstadoNaturalidade(dossier.getCustomer().getCustomerSpouse().getProvince().getAcronym());
        }
        envio.setCodigoDocumento(dossier.getCustomer().getCustomerSpouse().getDocumentNumber());
        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getCustomerSpouse().getEmissionOrganism())){
            envio.setNomeOrgaoEmissor(dossier.getCustomer().getCustomerSpouse().getEmissionOrganism().getDescription());
        }
        envio.setIndicativoFuncSantander(SantanderConstants.N);
        envio.setNumeroDependentes("000");
        
        DadosPessoaisClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso2DadosPessoais(envio);
        
        LOGGER.debug(" >> END sendStep2ClientSpouse ");
        return response;
    }
    
    private InsereEnderecoClientResponse sendStep3AdressSpouse(DossierEntity dossier, String numeroIntermediarioClienteSpouse) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep3AdressSpouse ");
        InsereEnderecoClientRequest envio = new InsereEnderecoClientRequest();
        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setCodigoEnderecoCorrespondencia(dossier.getCustomer().getMailingAddressType().getImportCode());

        envio.setNumeroClienteInterno(numeroIntermediarioClienteSpouse);
        
        envio.setCodigoPaisResidencial(AppConstants.STRING_EMPTY);
        
        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getCustomerSpouse().getProvince())){
            envio.setCodigoSiglaUfComercial(dossier.getCustomer().getCustomerSpouse().getProvince().getAcronym());
        }
        
        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getAddress().getProvince())){
            envio.setCodigoSiglaUfResidencial(dossier.getCustomer().getAddress().getProvince().getAcronym());
        }

        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getAddressSince())){
            envio.setDataAnoResideDesde(SDF_YEAR.format(dossier.getCustomer().getAddressSince()));
            envio.setDataMesResideDesde(SDF_MONTH.format(dossier.getCustomer().getAddressSince()));
        }
        
        envio.setDescricaoTelComercial(SantanderServicesUtils.formatPhone(dossier.getCustomer().getCustomerSpouse().getPhone()));
        envio.setNumeroDddTelComercial(SantanderServicesUtils.formatDDD(dossier.getCustomer().getCustomerSpouse().getDdd()));

        envio.setNumeroTipoResidencia(String.valueOf(dossier.getCustomer().getResidenceType().getId()));

        envio.setNumeroResidencial(SantanderServicesUtils.formatNumber(dossier.getCustomer().getAddress().getNumber(), 5));
        envio.setNumeroCepResidencial(SantanderServicesUtils.formatPostalCode(dossier.getCustomer().getAddress().getPostCode()));
        envio.setNomeHomepage(AppConstants.STRING_EMPTY);
        envio.setNomeCidadeResidencial(dossier.getCustomer().getAddress().getCity());
        envio.setDescricaoComplementoResidencia(dossier.getCustomer().getAddress().getComplement());
        envio.setDescricaoEnderecoResidencia(dossier.getCustomer().getAddress().getAddress());
        envio.setNomeBairroResidencial(dossier.getCustomer().getAddress().getNeighborhood());

        envio.setCodigoPaisComercial(AppConstants.STRING_EMPTY);
        envio.setDescricaoComplementoEndComercial(AppConstants.STRING_EMPTY);
        envio.setNomeCidadeComercial(AppConstants.STRING_EMPTY);
        envio.setNumeroEnderecoComercial(AppConstants.STRING_EMPTY);
        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployerAddress())){
            envio.setNumeroCepComercial(dossier.getCustomer().getEmployerAddress().getPostCode());
            envio.setDescricaoEnderecoComercial(dossier.getCustomer().getEmployerAddress().getAddress());
            envio.setNomeBairroComercial(dossier.getCustomer().getEmployerAddress().getNeighborhood());
        }
        
        InsereEnderecoClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso3Endereco(envio);
        
        LOGGER.debug(" >> END sendStep3AdressSpouse ");
        return response;
    }
    
    private DadosPessoaisClientResponse sendStep2Guarantor(DossierEntity dossier,GuarantorEntity guarantorEntity, boolean isFaithfulCustodian, int idGarantor, ProposalEntity proposta) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep2Guarantor ");
        DadosPessoaisClientRequest envio = new DadosPessoaisClientRequest();

        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setCodigoTipoPessoa(SantanderServicesUtils.personTypeToForJ(guarantorEntity.getPersonType().name()));

        envio.setNumeroTipoDocumento(guarantorEntity.getDocumentType().getImportCode());
        envio.setCodigoDocumento(guarantorEntity.getDocumentNumber());
        envio.setCodigoEstadoOrgaoEmissor(guarantorEntity.getDocumentProvince().getAcronym());
        envio.setCodigoPaisDocumento(guarantorEntity.getDocumentCountry().getImportCode());
        envio.setDescricaoNaturalidade(guarantorEntity.getNaturalness().toUpperCase());
        envio.setCodigoEstadoNaturalidade(guarantorEntity.getProvince().getAcronym());
        envio.setCodigoNacionalidade(guarantorEntity.getCountry().getImportCode());
        envio.setCodigoSexo(guarantorEntity.getPersonGender().getAbbreviation());
        envio.setDataAdmissao(SantanderServicesUtils.dateToStringSantanderFormat(guarantorEntity.getEmissionDate()));
        envio.setDataEmissaoDocumento(SantanderServicesUtils.dateToStringSantanderFormat(guarantorEntity.getEmissionDate()));
        if (guarantorEntity.getDocumentValidate() != null) {
            envio.setDataValidadeDocumento(SantanderServicesUtils.dateToStringSantanderFormat(guarantorEntity.getDocumentValidate()));
        }
        envio.setDescricaoProfissao(guarantorEntity.getEmployer().getProfession().getDescription());
        envio.setDescricaoTelOutrasRendas(AppConstants.STRING_EMPTY);
        envio.setIndicativoDeficienteFisico(SantanderServicesUtils.boolToSOrN(guarantorEntity.getHandicapped()));
        envio.setIndicativoFuncSantander(SantanderConstants.N);
        envio.setNomeEmpresa(guarantorEntity.getEmployer().getEmployerName());
        envio.setNomeMae(guarantorEntity.getMotherName());
        envio.setNomePai(guarantorEntity.getFatherName());
        envio.setNomeOrgaoEmissor(guarantorEntity.getEmissionOrganism().getImportCode());

        envio.setNumeroCnpjEmpresa(guarantorEntity.getEmployer().getCnpj());
        envio.setNumeroCpfCnpj(guarantorEntity.getCpfCnpj());
        envio.setNumeroDependentes("000");
        envio.setNumeroEstadoCivil(guarantorEntity.getCivilState().getId().toString());
        envio.setNumeroIndicativoGrauParentes(guarantorEntity.getKinshipType().getImportCode());
        envio.setNumeroInstrucao(guarantorEntity.getEducationDegree().getId().toString());
        envio.setNumeroOcupacao(guarantorEntity.getEmployer().getOccupation().getImportCode());
        envio.setNumeroPessoaPoliticaExpo(guarantorEntity.getPoliticalExposition().getId().toString());
        if(!AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getEmployerSize())){
            envio.setNumeroPorteEmpresarial(guarantorEntity.getEmployer().getEmployerSize().toString());
        }
        
        if(!AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getIncomeType())){
            envio.setNumeroRenda(guarantorEntity.getEmployer().getIncomeType().getImportCode());
        }
        
        if(!AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getProofIncomeType())){
            envio.setNumeroComprovanteRenda(guarantorEntity.getEmployer().getProofIncomeType().getImportCode());
        }
        
        envio.setValorRendaMensal(Utils.bigToString(guarantorEntity.getIncome(), 2));
        envio.setValorOutrasRendas(Utils.bigToString(guarantorEntity.getOtherIncome(), 2));
        envio.setValorPatrimonio(Utils.bigToString(guarantorEntity.getEmployer().getValueAssets(), 2));

        if(!AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getEconomicActivity())){
            envio.setNumeroAtividadeEconomica(guarantorEntity.getEmployer().getEconomicActivity().getImportCode());
        } else {
            envio.setNumeroAtividadeEconomica("000");
        }

        if(!AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getIndustrialSegment())){
            envio.setNumeroGrupoAtivEconomica(String.valueOf(guarantorEntity.getEmployer().getIndustrialSegment().getId()));
        } else {
            envio.setNumeroGrupoAtivEconomica("000");
        }

        envio.setNumeroClienteRelac("00000001");

        if (isFaithfulCustodian) {
            envio.setNumeroTpRepresentante("14");
            envio.setNumeroTipoVinculoPart("7");
        } else {
            envio.setNumeroTpRepresentante("12");
            if (idGarantor == 1) {
                envio.setNumeroTipoVinculoPart("3");
            } else {
                envio.setNumeroTipoVinculoPart("3");
            }
        }
        envio.setDataNascimento(SantanderServicesUtils.dateToStringSantanderFormat(guarantorEntity.getDateBirth()));
        envio.setNomeCompleto(guarantorEntity.getNameClient());

        DadosPessoaisClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso2DadosPessoais(envio);
        
        LOGGER.debug(" >> END sendStep2Guarantor ");
        return response;
    }

    private InsereEnderecoClientResponse sendStep3AdressGuarantor(DossierEntity dossier, GuarantorEntity guarantorEntity, String numeroIntermediarioGuarantor, boolean isFaithfulCustodian, int idGaratnor, ProposalEntity proposta) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep3AdressGuarantor ");
        
        InsereEnderecoClientRequest envio = new InsereEnderecoClientRequest();
        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setCodigoEnderecoCorrespondencia(guarantorEntity.getMailingAddressType().getImportCode());

        if (!isFaithfulCustodian) {
            envio.setNumeroClienteInterno(numeroIntermediarioGuarantor);
        } else {
            envio.setNumeroClienteInterno("00000007");
        }

        if (!AppUtil.isNullOrEmpty(guarantorEntity.getListPhone())) {
            PhoneEntity cel = null;
            PhoneEntity comercial = null;
            PhoneEntity fixo = null;
            for (PhoneEntity phone : guarantorEntity.getListPhone()) {
                if (phone.getPhoneType().equals(PhoneTypeEnum.CELULAR)) {
                    cel = phone;
                }
                if (phone.getPhoneType().equals(PhoneTypeEnum.COMERCIAL)) {
                    comercial = phone;
                }
                if (phone.getPhoneType().equals(PhoneTypeEnum.FIXO)) {
                    fixo = phone;
                }
            }
            if (guarantorEntity.getPersonType().equals(PersonTypeEnum.PF)) {
                if (cel != null) {
                    envio.setCodigotelCel(SantanderServicesUtils.formatPhone(cel.getPhone()));
                    envio.setNumeroDddCel(SantanderServicesUtils.formatDDD(cel.getDdd()));
                }
                if (comercial != null) {
                    envio.setDescricaoTelComercial(SantanderServicesUtils.formatPhone(comercial.getPhone()));
                    envio.setNumeroDddTelComercial(SantanderServicesUtils.formatDDD(comercial.getDdd()));
                    envio.setNumeroTelComercialRamal(SantanderServicesUtils.formatRamal(comercial.getExtensionLine()));
                }
            } else {
                if (fixo != null) {
                    envio.setDescricaoTelComercial(SantanderServicesUtils.formatPhone(fixo.getPhone()));
                    envio.setNumeroDddTelComercial(SantanderServicesUtils.formatDDD(fixo.getDdd()));
                    envio.setNumeroTelComercialRamal(SantanderServicesUtils.formatRamal(fixo.getExtensionLine()));
                }
            }
            if (fixo != null) {
                envio.setDescricaotelResidencial(SantanderServicesUtils.formatPhone(fixo.getPhone()));
                envio.setNumeroDddResidencial(SantanderServicesUtils.formatDDD(fixo.getDdd()));
                envio.setNumeroTipoTelefResiden(String.valueOf(guarantorEntity.getPersonalPhoneType().getImportCode()));
            }
        }

        envio.setCodigoPaisComercial(AppConstants.STRING_EMPTY);
        envio.setCodigoPaisResidencial(AppConstants.STRING_EMPTY);
        envio.setCodigoSiglaUfComercial(guarantorEntity.getEmployerAddress().getProvince().getAcronym());
        envio.setCodigoSiglaUfResidencial(guarantorEntity.getAddress().getProvince().getAcronym());

        envio.setDataAnoResideDesde(SDF_YEAR.format(guarantorEntity.getAddressSince()));
        envio.setDataMesResideDesde(SDF_MONTH.format(guarantorEntity.getAddressSince()));
        
        envio.setDescricaoComplementoEndComercial(guarantorEntity.getEmployerAddress().getComplement());
        envio.setDescricaoEnderecoComercial(guarantorEntity.getEmployerAddress().getAddress());
        envio.setDescricaoEnderecoEmail(guarantorEntity.getEmail());
        envio.setNomeBairroComercial(guarantorEntity.getEmployerAddress().getNeighborhood());
        envio.setNomeCidadeComercial(guarantorEntity.getEmployerAddress().getCity());
        envio.setNumeroCepComercial(SantanderServicesUtils.formatPostalCode(guarantorEntity.getEmployerAddress().getPostCode()));
        envio.setNumeroEnderecoComercial(SantanderServicesUtils.formatNumber(guarantorEntity.getEmployerAddress().getNumber(), 5));
        envio.setNumeroTipoResidencia(String.valueOf(guarantorEntity.getResidenceType().getId()));
        envio.setNumeroResidencial(SantanderServicesUtils.formatNumber(guarantorEntity.getAddress().getNumber(), 5));
        envio.setNumeroCepResidencial(SantanderServicesUtils.formatPostalCode(guarantorEntity.getAddress().getPostCode()));
        envio.setNomeHomepage(AppConstants.STRING_EMPTY);

        envio.setNomeCidadeResidencial(guarantorEntity.getAddress().getCity());
        envio.setDescricaoComplementoResidencia(guarantorEntity.getAddress().getComplement());
        envio.setDescricaoEnderecoResidencia(guarantorEntity.getAddress().getAddress());
        envio.setNomeBairroResidencial(guarantorEntity.getAddress().getNeighborhood());

        InsereEnderecoClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso3Endereco(envio);
        
        LOGGER.debug(" >> END sendStep3AdressGuarantor ");
        return response;
    }
    
    private InsereReferenciaClientResponse sendStep4ReferenciasGuarantor(DossierEntity dossier, GuarantorEntity guarantorEntity, String numeroIntermediarioGuarantor, boolean isFaithfulCustodian, int idGaratnor, ProposalEntity proposta) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep4ReferenciasGuarantor ");
        InsereReferenciaClientRequest envio = new InsereReferenciaClientRequest();
        envio.setNumeroPropostaAdp(dossier.getAdp());
        if (guarantorEntity.getPersonType().equals(PersonTypeEnum.PF)) {
            int i = 1;
            for (GuarantorReferenceEntity reference : guarantorEntity.getListGuarantorReference()) {
                if (i < 2) {
                    envio.setNomeRefer1(reference.getDescription());
                    envio.setNumeroDddRefer1(SantanderServicesUtils.valueOrDefault(reference.getDdd(), AppConstants.STRING_EMPTY));
                    envio.setDescricaoTelefoneRefer1(SantanderServicesUtils.valueOrDefault(reference.getPhone(), AppConstants.STRING_EMPTY));
                } else {
                    envio.setNomeRefer2(reference.getDescription());
                    envio.setNumeroDddRefer2(SantanderServicesUtils.valueOrDefault(reference.getDdd(), AppConstants.STRING_EMPTY));
                    envio.setDescricaoTelefoneRefer2(SantanderServicesUtils.valueOrDefault(reference.getPhone(), AppConstants.STRING_EMPTY));
                    break;
                }
                i++;
            }
        }

        
        if(!AppUtil.isNullOrEmpty(guarantorEntity.getBankDetail()) && !AppUtil.isNullOrEmpty(guarantorEntity.getBankDetail().getBank())){
            envio.setNumeroBanco(guarantorEntity.getBankDetail().getBank().getImportCode());
            
            envio.setCodigoTipoContaBancaria(AccountTypeEnum.CORRENTE.getAcronym());
            
            envio.setNumeroAgencia(guarantorEntity.getBankDetail().getBranch());
            envio.setCodigoDigitoAgencia(guarantorEntity.getBankDetail().getBranchDigit());
            
            envio.setNumeroContaCorrente(guarantorEntity.getBankDetail().getAccount());
            envio.setCodigoDigitoContaCorrente(guarantorEntity.getBankDetail().getAccountDigit());
            
            envio.setNumeroAnoClienteDesde(SDF_YEAR.format(guarantorEntity.getBankDetail().getAccountOpeningDate()));
            envio.setNumeroMesClienteDesde(SDF_MONTH.format(guarantorEntity.getBankDetail().getAccountOpeningDate()));
            
            envio.setNumeroDddTelefoneBanco(SantanderServicesUtils.formatDDD(guarantorEntity.getBankDetail().getDdd()));
            envio.setDescricaoTelefoneBanco(guarantorEntity.getBankDetail().getPhone());
        }

        envio.setNumeroClienteInterno(numeroIntermediarioGuarantor);

        if (!isFaithfulCustodian) {
            envio.setNumeroClienteInterno(numeroIntermediarioGuarantor);
        } else {
            envio.setNumeroClienteInterno("00000007");
        }
        envio.setNumeroClienteRelacional("00000001");
        
        InsereReferenciaClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso4Referencias(envio);
        
        LOGGER.debug(" >> END sendStep4ReferenciasGuarantor ");
        return response;
    }
    
    private DadosPessoaisClientResponse sendStep2SpouseGuarantor(DossierEntity dossier, GuarantorEntity guarantorEntity, boolean b, int idGaratnor, ProposalEntity proposta) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep2SpouseGuarantor ");
        DadosPessoaisClientRequest envio = new DadosPessoaisClientRequest();

        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setCodigoTipoPessoa(SantanderServicesUtils.personTypeToForJ(guarantorEntity.getPersonType().name()));
        // pessoa fisica

        envio.setNumeroTipoDocumento(AppConstants.STRING_EMPTY);
        envio.setCodigoDocumento(guarantorEntity.getGuarantorSpouse().getDocumentNumber());
        if(!AppUtil.isNullOrEmpty(guarantorEntity.getGuarantorSpouse().getProvince())){
            envio.setCodigoEstadoNaturalidade(guarantorEntity.getGuarantorSpouse().getProvince().getAcronym());
        }

        envio.setCodigoSexo(guarantorEntity.getGuarantorSpouse().getPersonGender().getAbbreviation());
        envio.setDataAdmissao(SantanderServicesUtils.dateToStringSantanderFormat(guarantorEntity.getGuarantorSpouse().getAdmissionDate()));

        if(!AppUtil.isNullOrEmpty(guarantorEntity.getGuarantorSpouse().getProfession())){
            envio.setDescricaoProfissao(guarantorEntity.getGuarantorSpouse().getProfession().getDescription());
        }

        envio.setIndicativoFuncSantander(SantanderConstants.N);
        envio.setNomeEmpresa(guarantorEntity.getGuarantorSpouse().getEmployerName());
        if(!AppUtil.isNullOrEmpty(guarantorEntity.getGuarantorSpouse().getEmissionOrganism())){
            envio.setNomeOrgaoEmissor(guarantorEntity.getGuarantorSpouse().getEmissionOrganism().getDescription());
        }
        envio.setNumeroCpfCnpj(guarantorEntity.getGuarantorSpouse().getCpf());

        envio.setNumeroDependentes("000");
        envio.setNumeroEstadoCivil(guarantorEntity.getCivilState().getId().toString());
        envio.setNumeroIndicativoGrauParentes(AppConstants.STRING_EMPTY);
        if(!AppUtil.isNullOrEmpty(guarantorEntity.getGuarantorSpouse().getOccupation())){
            envio.setNumeroOcupacao(guarantorEntity.getGuarantorSpouse().getOccupation().getImportCode());
        }

        envio.setValorRendaMensal(Utils.bigToString(guarantorEntity.getGuarantorSpouse().getIncome(), 2));
        if (b) {
            envio.setNumeroClienteRelac("00000007");
            envio.setNumeroTipoVinculoPart("8");
        } else {
            if (idGaratnor == 1) {
                envio.setNumeroClienteRelac("00000003");
                envio.setNumeroTipoVinculoPart("4");
            } else {
                envio.setNumeroClienteRelac("00000005");
                envio.setNumeroTipoVinculoPart("6");
            }
        }

        envio.setDataNascimento(SantanderServicesUtils.dateToStringSantanderFormat(guarantorEntity.getGuarantorSpouse().getDateBirth()));
        envio.setNomeCompleto(guarantorEntity.getGuarantorSpouse().getNameClient());
        
        DadosPessoaisClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso2DadosPessoais(envio);
        
        LOGGER.debug(" >> END sendStep2SpouseGuarantor ");
        return response;
    }

    private InsereEnderecoClientResponse sendStep3AdressSpouseGuarantor(DossierEntity dossier, GuarantorEntity guarantorEntity, String numeroIntermediarioGuarantorSpouse, boolean b, int idGaratnor, ProposalEntity proposta) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep3AdressSpouseGuarantor ");
        InsereEnderecoClientRequest envio = new InsereEnderecoClientRequest();
        envio.setNumeroPropostaAdp(dossier.getAdp());
        envio.setCodigoEnderecoCorrespondencia(guarantorEntity.getMailingAddressType().getImportCode());

        envio.setNumeroClienteInterno(numeroIntermediarioGuarantorSpouse);

        envio.setCodigoPaisComercial(AppConstants.STRING_EMPTY);
        envio.setCodigoPaisResidencial(AppConstants.STRING_EMPTY);
        envio.setCodigoSiglaUfComercial(guarantorEntity.getProvince().getAcronym());
        envio.setCodigoSiglaUfResidencial(guarantorEntity.getProvince().getAcronym());

        envio.setDataAnoResideDesde(SDF_YEAR.format(guarantorEntity.getAddressSince()));
        envio.setDataMesResideDesde(SDF_MONTH.format(guarantorEntity.getAddressSince()));
        
        envio.setDescricaoComplementoEndComercial(AppConstants.STRING_EMPTY);
        envio.setDescricaoEnderecoComercial(guarantorEntity.getEmployerAddress().getAddress());
        envio.setNomeBairroComercial(guarantorEntity.getAddress().getCity());
        envio.setNomeCidadeComercial(guarantorEntity.getAddress().getCity());
        envio.setNumeroCepComercial(guarantorEntity.getAddress().getPostCode());

        envio.setDescricaoTelComercial(SantanderServicesUtils.formatPhone(guarantorEntity.getGuarantorSpouse().getPhone()));
        envio.setNumeroDddTelComercial(SantanderServicesUtils.formatDDD(guarantorEntity.getGuarantorSpouse().getDdd()));

        envio.setNumeroTipoResidencia(String.valueOf(guarantorEntity.getResidenceType().getId()));

        envio.setNumeroResidencial(SantanderServicesUtils.formatNumber(guarantorEntity.getAddress().getNumber(), 5));
        envio.setNumeroCepResidencial(SantanderServicesUtils.formatPostalCode(guarantorEntity.getAddress().getPostCode()));
        envio.setNomeHomepage(AppConstants.STRING_EMPTY);
        envio.setNomeCidadeResidencial(guarantorEntity.getAddress().getCity());
        envio.setDescricaoComplementoResidencia(guarantorEntity.getAddress().getComplement());
        envio.setDescricaoEnderecoResidencia(guarantorEntity.getAddress().getAddress());
        envio.setNomeBairroResidencial(guarantorEntity.getAddress().getNeighborhood());
        
        InsereEnderecoClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso3Endereco(envio);
        
        LOGGER.debug(" >> END sendStep3AdressSpouseGuarantor ");
        return response;
    }

    private InsereGarantiaClientResponse sendStep5GarantiaClient(DossierEntity dossier) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep5GarantiaClient ");
        InsereGarantiaClientRequest envio = new InsereGarantiaClientRequest();

        envio.setNumeroProposta(dossier.getAdp());
        envio.setCodigoEstadoLicenciamento(dossier.getDossierVehicle().getRegisterProvince().getAcronym());
        envio.setCodigoTipoCombustivel(dossier.getDossierVehicle().getVehicleVersion().getFuel().getImportCode());

        Set<SpecialVehicleTypeEntity> listveicleEspeial = dossier.getListSpecialVehicleType();
        boolean istaxi = false;
        boolean isAdapted = false;
        for (SpecialVehicleTypeEntity specialVehicleTypeEntity : listveicleEspeial) {
            if (specialVehicleTypeEntity.getId().equals("1")) {
                istaxi = true;
            } else if (specialVehicleTypeEntity.getId().equals("2")) {
                isAdapted = false;
            }
        }
        envio.setIndicativoAdaptado(SantanderServicesUtils.boolToSOrN(isAdapted));
        envio.setIndicativoTaxi(SantanderServicesUtils.boolToSOrN(istaxi));

        envio.setIndicativoProcVeiculo(SantanderServicesUtils.boolToNorI(dossier.getDossierVehicle().getNationalCar()));
        envio.setIndicativoZeroKm(SantanderServicesUtils.boolToSOrN(dossier.getDossierVehicle().getVehicleVersion().getVehicleType().equals(VehicleTypeEnum.NOVO)));
        envio.setNumeroAnoFabricacao(SantanderServicesUtils.formatNumber(String.valueOf(dossier.getDossierVehicle().getVehicleVersion().getManufactureYear()), 4));
        envio.setNumeroAnoModelo(SantanderServicesUtils.formatNumber(String.valueOf(dossier.getDossierVehicle().getVehicleVersion().getModelYear()), 4));
        envio.setNumeroTabMarca(dossier.getDossierVehicle().getVehicleVersion().getVehicleModel().getVehicleBrand().getImportCode());

        envio.setDescricaoMarca(dossier.getStructure().getDealership().getDealershipName());
        envio.setNumeroTabMarca(dossier.getDossierVehicle().getVehicleVersion().getSantanderMarca());
        envio.setNumeroTabModelo(dossier.getDossierVehicle().getVehicleVersion().getSantanderModelo());

        envio.setCodigoEstadoPlaca(dossier.getDossierVehicle().getRegisterProvince().getAcronym());
        envio.setCodigoRenavam(dossier.getDossierVehicle().getRenavam());
        envio.setDescricaoChassi(dossier.getDossierVehicle().getChassi());
        if(!AppUtil.isNullOrEmpty(dossier.getDossierVehicle().getColor())){
            envio.setDescricaoCor(dossier.getDossierVehicle().getColor().getDescription());
        }
        envio.setDescricaoMarca(dossier.getDossierVehicle().getVehicleVersion().getVehicleModel().getVehicleBrand().getDescription());
        envio.setCodigoObjFinanciado(GeneralUtils.isSaleTypeNewVehicle(dossier.getSaleType()) ? "AN" : "AU");
        
        envio.setDescricaoModelo(dossier.getDossierVehicle().getVehicleVersion().getVehicleModel().getDescription());
        
        InsereGarantiaClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso5Garantias(envio);
        
        LOGGER.debug(" >> END sendStep5GarantiaClient ");
        return response;
    }

    private DadosAdpClientResponse sendStep6Fim(DossierEntity dossier) throws MalformedURLException {
        
        LOGGER.debug(" >> INIT sendStep6Fim ");
        DadosAdpClientRequest envio = new DadosAdpClientRequest();
        envio.setNumeroIntermediario(dossier.getStructure().getDealership().getTab());
        envio.setNumeroPropostaAdp(dossier.getAdp());
        
        DadosAdpClientResponse response = santanderWebService.enviaPropostaFinanciamentoVeiculoPasso6Fim(envio);
        
        LOGGER.debug(" >> END sendStep6Fim ");
        return response;
    }

}
