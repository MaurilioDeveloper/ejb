package com.rci.omega2.ejb.bo;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.ConsultaPropostaDetalhadaAFC;
import com.altec.bsbr.app.afc.webservice.impl.ConsultaPropostaDetalhadaResponseAFC;
import com.altec.bsbr.app.afc.webservice.impl.ConsultaPropostaRequestDTO;
import com.altec.bsbr.app.afc.webservice.impl.DadosAgenteCertificado;
import com.altec.bsbr.app.afc.webservice.impl.DadosAvalista;
import com.altec.bsbr.app.afc.webservice.impl.DadosBasicosProposta;
import com.altec.bsbr.app.afc.webservice.impl.DadosClienteDadosProfissionais;
import com.altec.bsbr.app.afc.webservice.impl.DadosConjuge;
import com.altec.bsbr.app.afc.webservice.impl.DadosEndereco;
import com.altec.bsbr.app.afc.webservice.impl.DadosFlex;
import com.altec.bsbr.app.afc.webservice.impl.DadosGarantia;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpoint;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpointService;
import com.altec.bsbr.app.afc.webservice.impl.Parcelas;
import com.altec.bsbr.app.afc.webservice.impl.ReferenciaPessoalBancaria;
import com.rci.omega2.async.service.ws.client.SalesmanCommunicationRequest;
import com.rci.omega2.async.service.ws.client.SalesmanCommunicationService;
import com.rci.omega2.async.service.ws.client.SalesmanCommunicationWs;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.DossierDAO;
import com.rci.omega2.ejb.dto.DossierDTO;
import com.rci.omega2.ejb.dto.DossierFilterDTO;
import com.rci.omega2.ejb.dto.DossierTransfFilterDTO;
import com.rci.omega2.ejb.dto.MailDTO;
import com.rci.omega2.ejb.exception.SantanderBusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.integrations.santander.soaputils.HeaderHandlerResolver;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.EmailEnum;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.ejb.utils.SantanderServicesUtils;
import com.rci.omega2.entity.AddressEntity;
import com.rci.omega2.entity.AddressTypeEntity;
import com.rci.omega2.entity.BankDetailEntity;
import com.rci.omega2.entity.BankEntity;
import com.rci.omega2.entity.BusinessRelationshipTypeEntity;
import com.rci.omega2.entity.CivilStateEntity;
import com.rci.omega2.entity.ColorEntity;
import com.rci.omega2.entity.CommissionEntity;
import com.rci.omega2.entity.CountryEntity;
import com.rci.omega2.entity.CustomerEntity;
import com.rci.omega2.entity.CustomerReferenceEntity;
import com.rci.omega2.entity.CustomerSpouseEntity;
import com.rci.omega2.entity.DocumentTypeEntity;
import com.rci.omega2.entity.DossierEntity;
import com.rci.omega2.entity.DossierTransferStructureEntity;
import com.rci.omega2.entity.DossierVehicleEntity;
import com.rci.omega2.entity.EconomicActivityEntity;
import com.rci.omega2.entity.EducationDegreeEntity;
import com.rci.omega2.entity.EmissionOrganismEntity;
import com.rci.omega2.entity.EmployerEntity;
import com.rci.omega2.entity.EmployerSizeEntity;
import com.rci.omega2.entity.EventEntity;
import com.rci.omega2.entity.GuarantorEntity;
import com.rci.omega2.entity.GuarantorReferenceEntity;
import com.rci.omega2.entity.GuarantorSpouseEntity;
import com.rci.omega2.entity.IncomeTypeEntity;
import com.rci.omega2.entity.IndustrialSegmentEntity;
import com.rci.omega2.entity.InstalmentEntity;
import com.rci.omega2.entity.KinshipTypeEntity;
import com.rci.omega2.entity.LegalNatureEntity;
import com.rci.omega2.entity.OccupationEntity;
import com.rci.omega2.entity.PersonEntity;
import com.rci.omega2.entity.PhoneEntity;
import com.rci.omega2.entity.PoliticalExpositionEntity;
import com.rci.omega2.entity.ProductEntity;
import com.rci.omega2.entity.ProfessionEntity;
import com.rci.omega2.entity.ProofIncomeTypeEntity;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalServiceEntity;
import com.rci.omega2.entity.ProposalTaxEntity;
import com.rci.omega2.entity.ProvinceEntity;
import com.rci.omega2.entity.ResidenceTypeEntity;
import com.rci.omega2.entity.SantanderEmployeeTypeEntity;
import com.rci.omega2.entity.ServiceEntity;
import com.rci.omega2.entity.SpecialVehicleTypeEntity;
import com.rci.omega2.entity.StructureEntity;
import com.rci.omega2.entity.VehicleVersionEntity;
import com.rci.omega2.entity.enumeration.AccountTypeEnum;
import com.rci.omega2.entity.enumeration.PersonGenderEnum;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;
import com.rci.omega2.entity.enumeration.PersonalPhoneTypeEnum;
import com.rci.omega2.entity.enumeration.PhoneTypeEnum;
import com.rci.omega2.entity.enumeration.SpecialVehicleTypeEnum;
import com.rci.omega2.entity.enumeration.StatusSantanderEnum;
import com.rci.omega2.entity.enumeration.StatusSyncStateEnum;
import com.rci.omega2.entity.enumeration.TaxTypeEnum;
import com.rci.omega2.entity.util.enumation.ParamEnum;

@Stateless
public class DossierBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(DossierBO.class);

    @EJB
    private ConfigFileBO configFile;

    @EJB
    private StructureBO structureBO;

    @EJB
    private PersonBO personBO;

    @EJB
    private ProposalBO proposalBO;

    @EJB
    private DossierStatusBO dossierStatusBO;

    @EJB
    private ProposalServiceBO proposalServiceBO;

    @EJB
    private CommissionBO commissionBO;

    @EJB
    private ProductBO productBO;

    @EJB
    private FinanceTypeBO financeTypeBO;

    @EJB
    private ProposalTaxBO proposalTaxBO;

    @EJB
    private ProvinceBO provinceBO;

    @EJB
    private CustomerBO customerBO;

    @EJB
    private CountryBO countryBO;

    @EJB
    private DocumentTypeBO documentTypeBO;

    @EJB
    private EmissionOrganismBO emissionOrganismBO;

    @EJB
    private CivilStateBO civilStateBO;

    @EJB
    private EducationDegreeBO educationDegreeBO;

    @EJB
    private OccupationBO occupationBO;

    @EJB
    private ProfessionBO professionBO;

    @EJB
    private CustomerSpouseBO customerSpouseBO;

    @EJB
    private IncomeTypeBO incomeTypeBO;

    @EJB
    private ProofIncomeTypeBO proofIncomeTypeBO;

    @EJB
    private IndustrialSegmentBO industrialSegmentBO;
    
    @EJB
    private SantanderEmployeeTypeBO santanderEmployeeTypeBO;
    
    @EJB
    private EmployerSizeBO employerSizeBO;
    
    @EJB
    private EconomicActivityBO economicActivityBO;

    @EJB
    private LegalNatureBO legalNatureBO;
    
    @EJB
    private AddressBO addressBO; 
    
    @EJB
    private PoliticalExpositionBO politicalExpositionBO;
    
    @EJB
    private PhoneBO phoneBO;
    
    @EJB
    private ResidenceTypeBO residenceTypeBO; 
    
    @EJB
    private AddressTypeBO addressTypeBO;
    
    @EJB
    private CustomerReferenceBO customerReferenceBO;
    
    @EJB
    private BankBO bankBO; 
    
    @EJB
    private VehicleVersionBO vehicleVersionBO;
    
    @EJB
    private DossierVehicleBO dossieVehicleBO;
    
    @EJB
    private ColorBO colorBO;
    
    @EJB
    private SpecialTypeBO specialTypeBO;
    
    @EJB
    private GuarantorBO guarantorBO;
    
    @EJB
    private KinshipTypeBO kinshipTypeBO;
    
    @EJB
    private BusinessRelationshipTypeBO businessRelationshipTypeBO;
    
    @EJB
    private GuarantorReferenceBO guarantorReferenceBO;
    
    @EJB
    private GuarantorSpouseBO guarantorSpouseBO;
    
    @EJB
    private SantanderReturnErrorBO santanderReturnErrorBO;
    
    @EJB
    private EmployerBO employerBO;
    
    @EJB
    private ParamBO paramBO;
    
    @EJB
    private SendMailBO sendMailBO;
    
    @EJB
    private EventBO eventBO;
    
    @EJB
    private EventTypeBO eventTypeBO;
    
    @EJB
    private UserBO userBO;
    
    @EJB
    private BankDetailBO bankDetailBO;
    
    @EJB
    private GuarantorTypeBO guarantorTypeBO;
    
    @EJB
    private InstalmentBO instalmentBO;
    
    @EJB
    private ServiceBO serviceBO;
    
    @EJB
    private UpdateStatusSyncBO updateStatusSyncBO;
    
    public List<DossierDTO> findDossierFromMyProposal(DossierFilterDTO dossierFilter) throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT findDossierFromMyProposal ");
            
            DossierDAO dao = daoFactory(DossierDAO.class);
            List<DossierDTO> temp = dao.findDossierFromMyProposal(dossierFilter);
            
            LOGGER.debug(" >> END findDossierFromMyProposal ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<DossierDTO> findDossierChanged() throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findDossierChanged ");
            
            DossierDAO dao = daoFactory(DossierDAO.class);
            List<DossierDTO> temp = dao.findDossierChanged();
            
            LOGGER.debug(" >> END findDossierChanged ");
            return temp;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public DossierDTO findDossierChangedById(Long idDossier) throws UnexpectedException {
        
        
        try {
            LOGGER.debug(" >> INIT findDossierChangedById ");
            
            DossierDAO dao = daoFactory(DossierDAO.class);
            DossierDTO temp = dao.findDossierChangedById(idDossier);
            
            LOGGER.debug(" >> END findDossierChangedById ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    
    /**
     * 
     * @param adp
     * @throws UnexpectedException
     */
    public void updateDossierFromTest(String adp, ConsultaPropostaDetalhadaResponseAFC consultaPropostaDetalhada) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT updateDossierFromTest ");
            //Atualiza Proposta
            updateDossier(adp, AppConstants.SYSTEM_OMEGA_USER, consultaPropostaDetalhada);
            LOGGER.debug(" >> END updateDossierFromTest ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    
    /**
     * 
     * @param adp
     * @throws UnexpectedException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateDossierFromSantander(String adp, String username) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT updateDossierFromSantander ");
            FinanciamentosOnlineEndpointService santanderService = new FinanciamentosOnlineEndpointService(
                    new URL(configFile.getProperty("santander.webservice.financiamentos.endpoint.wsdl")));
            santanderService.setHandlerResolver(new HeaderHandlerResolver(configFile));
            FinanciamentosOnlineEndpoint proxy = santanderService.getFinanciamentosOnlineEndpointPort();

            ConsultaPropostaRequestDTO request = new ConsultaPropostaRequestDTO();

            request.setCodigoProposta(adp); // ADP
            request.setNumeroIntermediario(proposalBO.getTabCode(adp)); // TAB

            ConsultaPropostaDetalhadaResponseAFC consultaPropostaDetalhada = proxy.consultaPropostaDetalhada(request);
            
            //Atualiza Proposta
            updateDossier(adp, username, consultaPropostaDetalhada);
            LOGGER.debug(" >> END updateDossierFromSantander ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    /**
     * 
     * @param adp
     * @return
     * @throws UnexpectedException
     */
    public DossierEntity findDossierByAdp(String adp) throws UnexpectedException {
        
        try{
            LOGGER.debug(" >> INIT findDossierByAdp ");
            
            DossierDAO dao = daoFactory(DossierDAO.class);
            DossierEntity temp = dao.findDossierByAdp(adp);
            
            LOGGER.debug(" >> END findDossierByAdp ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
   

    /**
     * 
     * @param consultaPropostaDetalhada
     * @throws UnexpectedException
     */
    private void updateDossier(String adp, String username ,ConsultaPropostaDetalhadaResponseAFC consultaPropostaDetalhada) throws UnexpectedException{
        
         try {
             LOGGER.debug(" >> INIT updateDossier ");
                    
            /*
             *  Caso ocorreu um erro no Santander será entrará 
             *  nesse metodo e a será emitida uma exception 
             */
            if(!AppUtil.isNullOrEmpty(consultaPropostaDetalhada.getCodigoErro())){
                //throw a Exception
                santanderReturnErrorBO.handlerSantanderBusinessException(adp, consultaPropostaDetalhada.getCodigoErro(), consultaPropostaDetalhada.getDescricaoErro());
            }
            
            ConsultaPropostaDetalhadaAFC dadosSantander = consultaPropostaDetalhada.getConsultaPropostaDetalhada();
            DadosBasicosProposta dadosBasicosSantander = dadosSantander.getDadosBasicosProposta();
            DadosAgenteCertificado dadosAgenteCertificadoSantander = dadosSantander.getDadosAgenteCertificado();

            
            DossierDAO dao = daoFactory(DossierDAO.class);

            DossierEntity dossier = dao.findDossierByAdp(adp);
            ProposalEntity proposal = proposalBO.findProposalByAdp(adp);

            /** Atualiza comissao **/
            if(!AppUtil.isNullOrEmpty(dadosBasicosSantander.getCodigoComissao())){
                CommissionEntity commission= commissionBO.findCommissionByImportCode(dadosBasicosSantander.getCodigoComissao().trim());
                if(AppUtil.isNullOrEmpty(commission)){
                    //send email
                    this.sendMailDomainNotRecognized(dadosBasicosSantander.getCodigoComissao(), AppConstants.DOMAIN_COMMISSION);
                }else{
                    proposal.setCommission(commission);
                }
            }else{
                proposal.setCommission(commissionBO.findCommissionByImportCode(AppConstants.COMMISSION_DEFAULT));
            }
            
            
            //Busca SPF service da proposta
            ProposalServiceEntity sfpProposalService = proposalServiceBO.findSPFProposal(proposal);
            
            //caso tenha sido removido pelo santader ele será removido na base
            if (AppUtil.isNullOrEmptyOrZero(dadosBasicosSantander.getCdSeguro()) && !AppUtil.isNullOrEmpty(sfpProposalService)) {
                proposal.getListProposalService().remove(sfpProposalService);
                proposalServiceBO.delete(sfpProposalService);
                
            }else if(!AppUtil.isNullOrEmptyOrZero(dadosBasicosSantander.getCdSeguro()) && AppUtil.isNullOrEmpty(sfpProposalService)){
                //caso tenha sido adicionado pelo santader e nao tenha na base será incluído
                
                //busca o SPF service
                ServiceEntity spfService = serviceBO.findByImportCodeOmega(AppConstants.SPF_IMPORT_CODE_OMEGA);
                
                ProposalServiceEntity newProposalService = new ProposalServiceEntity();
                newProposalService.setAmount(spfService.getPercentage());
                newProposalService.setService(spfService);
                newProposalService.setProposal(proposal);
                proposalServiceBO.insert(newProposalService);
                
                proposal.getListProposalService().add(newProposalService);
            }
            
            /* Calcula total de servico */
            /** Atualiza valor do veiculo **/
            BigDecimal totalServices = BigDecimal.ZERO;

            for (ProposalServiceEntity proposalService : proposal.getListProposalService()) {
                /** Seguro proteção financeira. */
                
                if (!AppConstants.SPF_IMPORT_CODE_OMEGA.equals(proposalService.getService().getImportCodeOmega())) {
                    /**
                     * Soma os demais seguros para serem subtraídos do valor do
                     * bem
                     */
                    totalServices = totalServices.add(proposalService.getAmount());
                }

            }
            dossier.setVehiclePrice(SantanderServicesUtils.stringToBigDecimal(dadosBasicosSantander.getValorBem())
                                                                                                .subtract(totalServices));
            /** FIM **/

            /** Codigo do agente certificado **/
            if (dadosAgenteCertificadoSantander.getCodigoAgenteCertificado() != null) {
                dossier.setCertifiedAgent(
                        Long.valueOf(dadosAgenteCertificadoSantander.getCodigoAgenteCertificado()).toString());
            }else{
                dossier.setCertifiedAgent("");
            }

            /** Numero da loja filial **/
            dossier.setBranchStoreNumber(dadosBasicosSantander.getNumeroLojaFilial());
            /** Data aprovação **/
            dossier.setApprovalCode(dadosBasicosSantander.getCdAprovacao());

            /** Atualiza numero da tabela de financiamento **/
            Date createdData = SantanderServicesUtils.santanderFormatToDate(dadosBasicosSantander.getDataInclusao(), "yyyyMMdd");
            
            if(!AppUtil.isNullOrEmpty(dadosBasicosSantander.getNumeroTabelaFinanciamento())){
                ProductEntity product = productBO.findProductByImportCodeAndPeriod(
                                            Long.valueOf(dadosBasicosSantander.getNumeroTabelaFinanciamento()).toString(), createdData); 
                
                if(AppUtil.isNullOrEmpty(product)){
                    //send email
                    this.sendMailDomainNotRecognized(dadosBasicosSantander.getNumeroTabelaFinanciamento()
                                            + " na data vigente " + new SimpleDateFormat("dd/MM/yyyy").format(createdData), AppConstants.DOMAIN_PRODUCT);
                }else{
                    proposal.setProduct(product);
                }
            }else{
                proposal.setProduct(null);
            }
            /** FIM **/

            /* Extrai Parcelas */
            this.extractInstalment(dadosSantander, proposal);

            /** Qt dias de carencia **/
            if(!AppUtil.isNullOrEmpty(dadosBasicosSantander.getNumeroQuantidadeDiasCarencia())){
                proposal.setDelayValue(Integer.valueOf(dadosBasicosSantander.getNumeroQuantidadeDiasCarencia()));
            }else{
                proposal.setDelayValue(0);
            }

            /** Data da simulacao **/
            Calendar calInserted = Calendar.getInstance();
            calInserted.add(Calendar.DAY_OF_YEAR, -proposal.getDelayValue());
            calInserted.getTime();
            proposal.setIncludeDate(calInserted.getTime());
            dossier.setIncludeDate(calInserted.getTime());

            /** Valor taxa mensal **/
            if(!AppUtil.isNullOrEmpty(dadosBasicosSantander.getValorTaxaMensal())){
                proposal.setMontlyRate(SantanderServicesUtils
                        .stringToBigDecimal(String.valueOf(new BigDecimal(dadosBasicosSantander.getValorTaxaMensal()).divide(new BigDecimal(1000000)))));
            }else{
                proposal.setMontlyRate(BigDecimal.ZERO);
            }
            
            /** Valor de entrada **/
            proposal.setDeposit(SantanderServicesUtils.stringToBigDecimal(dadosBasicosSantander.getValorEntrada()));

            /** Valor financiado **/
            proposal.setFinancedAmount(SantanderServicesUtils
                    .stringToBigDecimal(dadosBasicosSantander.getValorFinanciamento())
                    .subtract(SantanderServicesUtils
                            .stringToBigDecimal(dadosBasicosSantander.getValorTotalImpostoFinanc()))
                    .subtract(SantanderServicesUtils.stringToBigDecimal(dadosBasicosSantander.getValorSeguro())));
            
            /** Parecer Crédito **/
            proposal.setCreditReport(dadosBasicosSantander.getTextoObsLojista());

            /** Parecer Logista **/
            proposal.setStoreControl(dadosBasicosSantander.getTextoControleLoja());

            /* Extrai as taxas */
            this.extractTaxes(dadosBasicosSantander, dossier, proposal);
            
            /** seta o valor da prestação **/
            proposal.setInstalmentAmount(
                    SantanderServicesUtils.stringToBigDecimal(dadosBasicosSantander.getValorPrestacao()));

            // Aplica a regra de data de validade da Proposta.
            // Data de vencimento da 1º parcela
            Date expireDate = SantanderServicesUtils.santanderFormatToDate(
                                        dadosBasicosSantander.getDataVencimento1(), "yyyyMMdd");
            // Caso seja diferente
            if (AppUtil.isNullOrEmpty(dossier.getExpiryDate()) 
                    || !expireDate.equals(AppUtil.truncateDate(dossier.getExpiryDate()))) {
                
                /* Se o status retornado pelo Santander for diferente de EF a data será atualizar;
                 * Se o status retornado pelo Santander for igual a EF e o status interno do Omega(BD) 
                 * for diferente de 10 a data será atualizada;
                 */
                if (!StatusSantanderEnum.EF.name().equalsIgnoreCase(dadosBasicosSantander.getCodigoStatus())
                       || ((StatusSantanderEnum.EF.name().equalsIgnoreCase(dadosBasicosSantander.getCodigoStatus()) 
                                                       && !dossier.getDossierStatus().equals(StatusSantanderEnum.EF)))) {
                    /** Data de vencimento **/
                    dossier.setExpiryDate(SantanderServicesUtils.add20DaysOnExpireDate(dossier.getIncludeDate()));
                }
            }

            /* Extrai Customer */
            this.extractCustomer(dadosSantander, dossier);
            
            /* Extrai Vehicle */
            this.extractVehicle(dadosSantander, dossier);
            
            /* Extrai Avalistas */
            this.extractGuarantor(dadosSantander, dossier);
            
            // atualiza proposta
            proposalBO.update(proposal);

            /** seta status sync state **/
            dossier.setStatusSyncState(StatusSyncStateEnum.NONE);
            
            // Update of status proposta, caso o status tenha sido alterado
            if (!dadosSantander.getDadosBasicosProposta().getCodigoStatus()
                            .equals(String.valueOf(dossier.getStatusSantander()))) {
                /** Data que alterou o Status **/
                dossier.setStatusChangeDate(AppUtil.getToday().getTime());
                /** Seta o status interno do Omega **/
                dossier.setStatusSantander(StatusSantanderEnum.valueOf(dadosBasicosSantander.getCodigoStatus()));
                /** Seta o status do Santander **/
                dossier.setDossierStatus(
                        dossierStatusBO.findDossierStatus(dossier.getStatusSantander().getDossierStatusId()));
                
                /* Add Evento 'Change Status' */
                EventEntity event = new EventEntity();
                event.setProposal(proposal);
                event.setDossierStatus(dossier.getDossierStatus());
                event.setUser(userBO.findOne(username));
                event.setEventType(eventTypeBO.findEventTypeById(AppConstants.EVENT_TYPE_CHANGE_STATUS));
                eventBO.insert(event);
           
                //Atualiza dossier
                dao.update(dossier);
                
                //Envia notificacao vendedor 
                this.pushNotificationSalesman(adp, dossier, proposal);
                
            }else{
                //Atualiza dossier
                dao.update(dossier);
            }
            LOGGER.debug(" >> END updateDossier ");
         } catch (SantanderBusinessException e) {
             updateStatusSyncBO.updateStatusSyncState(adp, StatusSyncStateEnum.SANTANDER_ERROR);
             throw e;
         } catch (UnexpectedException e) {
             updateStatusSyncBO.updateStatusSyncState(adp, StatusSyncStateEnum.DOSSIER_UPDATE_ERROR);
             throw e;
         } catch (Exception e) {
             updateStatusSyncBO.updateStatusSyncState(adp, StatusSyncStateEnum.DOSSIER_UPDATE_ERROR);
             LOGGER.error(AppConstants.ERROR, e);
             throw new UnexpectedException(e);
         }
    }

    /**
     * @param dadosBasicosSantander
     * @param dossier
     * @param proposal
     * @throws UnexpectedException
     */
    private void extractTaxes(DadosBasicosProposta dadosBasicosSantander, 
                        DossierEntity dossier, ProposalEntity proposal) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT extractTaxes ");
        
        /** Indicador de isenção de TC (Taxa cadastral) **/
        dossier.setTcExempt(
                SantanderServicesUtils.sOrNtoBoolToBoolean(dadosBasicosSantander.getIndicadorIsencaoTC()));
        
        //Remove todas as taxas
        proposal.setListProposalTax(new HashSet<ProposalTaxEntity>());
        proposalTaxBO.deleteAllProposalTax(proposal);

        /** Atualiza o valor da TR **/
        ProposalTaxEntity taxTR = new ProposalTaxEntity();
        taxTR.setAmount(
                SantanderServicesUtils.stringToBigDecimal(dadosBasicosSantander.getDespServicosTerceiros()));
        taxTR.setTaxType(TaxTypeEnum.TR);
        taxTR.setProposal(proposal);
        proposal.getListProposalTax().add(taxTR);
        //Adiciona taxa
        proposalTaxBO.insert(taxTR);

        //Se não for isento de TC
        if(!dossier.getTcExempt()){
            /** Add a taxa TC **/
            ProposalTaxEntity taxTC = new ProposalTaxEntity();
            taxTC.setTaxType(TaxTypeEnum.TC);
            taxTC.setProposal(proposal);
            taxTC.setAmount(SantanderServicesUtils.stringToBigDecimal(dadosBasicosSantander.getValorTAC()));
            proposal.getListProposalTax().add(taxTC);
            //adiciona taxa
            proposalTaxBO.insert(taxTC);
        }
        LOGGER.debug(" >> END extractTaxes ");
    }

    /**
     * @param adp
     * @param dossier
     * @throws UnexpectedException
     */
    private void pushNotificationSalesman(String adp, DossierEntity dossier, ProposalEntity proposal)
                                                                                            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT pushNotificationSalesman ");
            
            //Send push to salesman - get WSDL
            SalesmanCommunicationWs salesmanCommunicationService = new SalesmanCommunicationWs(
                                        new URL(configFile.getProperty("endpoint.omega2.salesman_communication") + "?WSDL"));
            SalesmanCommunicationService proxy = salesmanCommunicationService.getSalesmanCommunicationWsPort();
            //set the endpoint on proxy
            BindingProvider bindingProvider = (BindingProvider) proxy;
            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, configFile.getProperty("endpoint.omega2.salesman_communication"));
            
            
            String notificationDetail = String.format(AppConstants.NOTIFICATION_DETAIL,dossier.getAdp(), 
                                                      dossierStatusBO.findDossierStatus(dossier.getDossierStatus().getId()).getDescription());
            
            SalesmanCommunicationRequest salesmanCommunicationRequest = new SalesmanCommunicationRequest();
            salesmanCommunicationRequest.setUser(proposal.getUser().getLogin());
            salesmanCommunicationRequest.setTitle(AppConstants.NOTIFICATION_TITLE);
            salesmanCommunicationRequest.setDescription(notificationDetail);
            salesmanCommunicationRequest.setProposalId(proposal.getId());
            salesmanCommunicationRequest.setAdp(adp);
            proxy.sendMessageSalesman(salesmanCommunicationRequest);
            
            LOGGER.debug(" >> END pushNotificationSalesman ");
        } catch (UnexpectedException e) {
            LOGGER.error(AppConstants.ERROR, e);
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
        }
    }

    /**
     * 
     * @param guarantor
     * @throws UnexpectedException 
     */
    private void deleteGuarantor(GuarantorEntity guarantor) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT deleteGuarantor ");
        
            EmployerEntity employerToRemove = guarantor.getEmployer();
            BankDetailEntity bankDetailToRemove = guarantor.getBankDetail();
            AddressEntity addressToRemove = guarantor.getAddress();
            AddressEntity addressEmployerToRemove = guarantor.getEmployerAddress();
            GuarantorSpouseEntity guarantorSpouseToRemove = guarantor.getGuarantorSpouse();
            guarantor.setEmployer(null);
            guarantor.setBankDetail(null);
            guarantor.setAddress(null);
            guarantor.setEmployerAddress(null);
            guarantor.setGuarantorSpouse(null);
            guarantor.setListGuarantorReference(null);
            guarantor.setListPhone(null);
            
            if(!AppUtil.isNullOrEmpty(employerToRemove)){
                employerBO.delete(employerToRemove);
            }
            if(!AppUtil.isNullOrEmpty(bankDetailToRemove)){
                bankDetailBO.delete(bankDetailToRemove);
            }
            if(!AppUtil.isNullOrEmpty(addressToRemove)){
                addressBO.delete(addressToRemove);
            }
            if(!AppUtil.isNullOrEmpty(addressEmployerToRemove)){
                addressBO.delete(addressEmployerToRemove);
            }
            if(!AppUtil.isNullOrEmpty(guarantorSpouseToRemove)){
                guarantorSpouseBO.delete(guarantorSpouseToRemove);
            }
            if(!AppUtil.isNullOrEmpty(guarantor)){
                guarantorReferenceBO.deleteAllGuarantorReference(guarantor);
            }
            if(!AppUtil.isNullOrEmpty(guarantor)){
                phoneBO.deleteAllPhoneGuarantor(guarantor);
            }
            if(!AppUtil.isNullOrEmpty(guarantor)){
                guarantorBO.delete(guarantor); 
            }
            
            LOGGER.debug(" >> END deleteGuarantor ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * @param listDadosAvalistasSantander
     * @param dossier
     * @throws ParseException
     * @throws UnexpectedException
     */
    private void extractGuarantor(ConsultaPropostaDetalhadaAFC dadosSantander, DossierEntity dossier)
            throws UnexpectedException {
        
        try{
            LOGGER.debug(" >> INIT extractGuarantor ");
            
            List<DadosAvalista> listDadosAvalistasSantander = dadosSantander.getListaAvalistas();
            Map<String, GuarantorEntity> mapDadosAvalistasSantanderUpdate = new HashMap<String, GuarantorEntity>();
            Map<String, GuarantorEntity> mapDadosAvalistasSantanderRemove = new HashMap<String, GuarantorEntity>();
            
            //zera a lista            
            Set<GuarantorEntity> guarantorSet = dossier.getListGuarantor();
            dossier.setListGuarantor(new HashSet<GuarantorEntity>());

            
            /* Apaga todos os avalistas caso não venha do Santander */
            if(AppUtil.isNullOrEmpty(listDadosAvalistasSantander)){
                
                for(GuarantorEntity guarantor :  guarantorSet){
                    this.deleteGuarantor(guarantor);
                }
                return; 
            }
            
            /* Popula mapa com os avalistas para remoção e atualização */
            for(GuarantorEntity guarantor :  guarantorSet){
                mapDadosAvalistasSantanderUpdate.put(AppUtil.onlyNumberNullableZero(guarantor.getCpfCnpj()), guarantor);
                mapDadosAvalistasSantanderRemove.put(AppUtil.onlyNumberNullableZero(guarantor.getCpfCnpj()), guarantor);
            }
            
                         
            /* Percorre a lista de Avalistas do Santander */
            for(DadosAvalista guarantorSantander : listDadosAvalistasSantander){
                
                //Avalista
                String cpfGuarantor = SantanderServicesUtils.extractCPF(
                        AppUtil.onlyNumberNullableZero(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroCpfCnpj()));
                
                //Se o tipo de avalista é 14 (fiel depositario) vai para o proximo registro, pois ele vem duplicado
                if(AppConstants.GUARANTOR_MAIN.endsWith(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroTpRepresentante())){
                   continue;
                }
                
                GuarantorEntity guarantor = mapDadosAvalistasSantanderUpdate.get(cpfGuarantor);
                
                /* Caso possua o avalista do santander estiver no banco será atualizado e removido do mapa de remoção */
                if(!AppUtil.isNullOrEmpty(guarantor)){
                    mapDadosAvalistasSantanderRemove.remove(cpfGuarantor);
                }else{
                    /* Será incluido o avalista caso não esteja no banco */
                    guarantor = new GuarantorEntity();
                    /** Portador de necessidades especiais **/
                    guarantor.setHandicapped(Boolean.FALSE);
                    /** Tipo pessoal **/
                    guarantor.setPersonType(PersonTypeEnum.PF);
                    /** Tipo avalista default **/
                    guarantor.setGuarantorType(guarantorTypeBO.findGuarantorTypeById(AppConstants.GUARANTOR_TYPE_DEFAULT));
                    
                }
                
                /** Numero CPF Avalista **/
                guarantor.setCpfCnpj(cpfGuarantor);
                
                /** Nome Avalista **/
                guarantor.setNameClient(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeCompleto());
                
                /** Genero Avalista **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista())){
                    PersonGenderEnum personGender = PersonGenderEnum.getGenderByAbbreviation(
                                                        guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoSexo());
                    if(AppUtil.isNullOrEmpty(personGender)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoSexo(), AppConstants.DOMAIN_GENDER);
                    }else{
                        guarantor.setPersonGender(personGender);
                    }
                
                }else{
                    guarantor.setPersonGender(null);
                }
                
                /** Data Nascimento **/
                guarantor.setDateBirth(SantanderServicesUtils
                        .santanderFormatToDate(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDataNascimento(), "yyyyMMdd"));
                
                /** Grau Parentesco **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroIndicativoGrauParentes())){
                    KinshipTypeEntity kinshipType = kinshipTypeBO.findKinshipTypeByImportCode(SantanderServicesUtils.extractFillZeroToLeft(
                                                    guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroIndicativoGrauParentes(), 2));
                    if(AppUtil.isNullOrEmpty(kinshipType)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroIndicativoGrauParentes(), AppConstants.DOMAIN_KINSHIP_TYPE);
                    }else{
                        guarantor.setKinshipType(kinshipType);
                    }
                    
                }else{
                    guarantor.setKinshipType(null);
                }
                
                /** Tipo Relacionamento Empresa **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoTipoRelacionamento())){
                    
                    BusinessRelationshipTypeEntity businessRelationshipType = businessRelationshipTypeBO
                            .findBusinessRelationshipTypeByImportCode(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoTipoRelacionamento());
                    if(AppUtil.isNullOrEmpty(businessRelationshipType)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoTipoRelacionamento(), AppConstants.DOMAIN_BUSINESS_RELATIONSHIP_TYPE);
                    }else{
                        guarantor.setBusinessRelationshipType(businessRelationshipType);
                    }
                    
                }else{
                    guarantor.setBusinessRelationshipType(null);
                }
                
                /** Estado Civil do Avalista **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroEstadoCivil())){
                    CivilStateEntity civilState = civilStateBO
                            .findCivilStateById(Long.valueOf(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroEstadoCivil()));  
                    if(AppUtil.isNullOrEmpty(civilState)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroEstadoCivil(), AppConstants.DOMAIN_CIVIL_STATE);
                    }else{
                        guarantor.setCivilState(civilState);
                    }
                }else{
                    guarantor.setCivilState(null);
                }

                /** Estado Naturalidade **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoEstadoNaturalidade())){
                    ProvinceEntity province = 
                            provinceBO.findProvinceByAcronym(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoEstadoNaturalidade());
                  
                    if(AppUtil.isNullOrEmpty(province)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoEstadoNaturalidade(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                    }else{
                        guarantor.setProvince(province);
                    }
                }else{
                    guarantor.setProvince(null);
                }
                
                
                /** Cidade Nascimento **/
                guarantor.setNaturalness(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoNaturalidade());
                
                /** Nacionalidade **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoNacionalidade())){
                    CountryEntity country = countryBO.findCountryByAcronym(
                            guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoNacionalidade());
                    if(AppUtil.isNullOrEmpty(country)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoNacionalidade(), AppConstants.DOMAIN_COUNTRY);
                    }else{
                        guarantor.setCountry(country);
                    }
                }else{
                    guarantor.setCountry(null);
                }

                /** Grau de Instrucao **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroInstrucao())){
                    EducationDegreeEntity educationDegree = educationDegreeBO
                                .findEducationDegreeById(Long.valueOf(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroInstrucao()));
                    if(AppUtil.isNullOrEmpty(educationDegree)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroInstrucao(), AppConstants.DOMAIN_EDUCATION_DEGREE);
                    }else{
                       guarantor.setEducationDegree(educationDegree);
                    }
                }else{
                    guarantor.setEducationDegree(null);
                }
                
                /** Avalista Politicamente Exposta **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroPessoaPoliticaExpo())){
                    PoliticalExpositionEntity politicalExposition = politicalExpositionBO.findPoliticalExpositionById(
                            Long.valueOf(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroPessoaPoliticaExpo()));
                    if(AppUtil.isNullOrEmpty(politicalExposition)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista()
                                                .getNumeroPessoaPoliticaExpo(), AppConstants.DOMAIN_POLITICAL_EXPOSITION);
                    }else{
                        guarantor.setPoliticalExposition(politicalExposition);
                    }
                }else{
                    guarantor.setPoliticalExposition(null);
                }

                /** Nome do Pai Avalista **/
                guarantor.setFatherName(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomePai());
                
                /** Nome do Mãe Avalista **/
                guarantor.setMotherName(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeMae());
                
                /** Orgão Emissor do Avalista **/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeOrgaoEmissor())){
                      EmissionOrganismEntity emissionOrganism = emissionOrganismBO
                            .findEmissionOrganismByAcronym(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeOrgaoEmissor());

                      if(AppUtil.isNullOrEmpty(emissionOrganism)){
                          //send email
                          this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeOrgaoEmissor(), AppConstants.DOMAIN_EMISSION_ORGANISM);
                      }else{
                          guarantor.setEmissionOrganism(emissionOrganism);
                      }
                }else{
                    guarantor.setEmissionOrganism(null);
                }
                
                /** Estado Orgao Emissor do Avalista**/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoEstadoOrgaoEmissor())){
                    ProvinceEntity province = provinceBO.findProvinceByAcronym(
                            guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoEstadoOrgaoEmissor());
                    if(AppUtil.isNullOrEmpty(province)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoEstadoOrgaoEmissor(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                    }else{
                        guarantor.setDocumentProvince(province);    
                    }
                }else{
                    guarantor.setDocumentProvince(null);
                }
                
                /** País emissor do documento do Avalista**/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoPaisDocumento())){
                    CountryEntity country = countryBO.findCountryByAcronym(
                            guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoPaisDocumento());
                    if(AppUtil.isNullOrEmpty(country)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoPaisDocumento(), AppConstants.DOMAIN_COUNTRY);
                    }else{
                        guarantor.setDocumentCountry(country);    
                    }
                }else{
                    guarantor.setDocumentCountry(null);
                }

                /** Data da emissao do documento de Avalista**/
                guarantor.setEmissionDate(SantanderServicesUtils
                        .santanderFormatToDate(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDataEmissaoDocumento(), "yyyyMMdd"));

                /** Tipo de documento do Avalista**/
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroTipoDocumento())){
                
                    DocumentTypeEntity documentType = documentTypeBO.findDocumentTypeByImportCode(
                                guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroTipoDocumento());
                    if(AppUtil.isNullOrEmpty(documentType)){
                        //send email
                        this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroTipoDocumento(), AppConstants.DOMAIN_DOCUMENT_TYPE);
                    }else{
                        guarantor.setDocumentType(documentType);    
                    }
                }else{
                    guarantor.setDocumentType(null);
                }
                /** Numero Documento **/
                guarantor.setDocumentNumber(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoDocumento());
                
                /** Data Validade **/
                String strExpireDocumentDate = guarantorSantander.getDadosPessoaisEbancariosAvalista().getDataValidadeDocumento();
                if (!AppUtil.isNullOrEmptyOrZero(strExpireDocumentDate)) {
                    guarantor.setDocumentValidate(
                            SantanderServicesUtils.santanderFormatToDate(strExpireDocumentDate, "yyyyMMdd"));
                }else{
                    guarantor.setDocumentValidate(null);
                }
                   
                guarantor.setDossier(dossier);
                dossier.getListGuarantor().add(guarantor);
                
                /* Insert Guarantor */
                if(AppUtil.isNullOrEmpty(guarantor.getId())){
                    guarantorBO.insert(guarantor);    
                }
                
                /* Dados profissionais */
                /* Remove so dados profissionais da empresa caso o numero do CNPJ for igual de zero ou Null*/
                if(!AppUtil.isNullOrEmptyOrZero(SantanderServicesUtils.extractCNPJ(
                        guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroCnpjEmpresa()))){
                    EmployerEntity guarantorEmployer = guarantor.getEmployer(); 
                    if(AppUtil.isNullOrEmpty(guarantorEmployer)){
                        guarantorEmployer = new EmployerEntity();
                    }
                        
                    /** Data de admissão do Avalista**/
                    guarantorEmployer.setAdmissionDate(
                            SantanderServicesUtils.santanderFormatToDate(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDataAdmissao(), "yyyyMM"));
    
                    /** Tipo de Renda do Avalista**/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroRenda())){
                            IncomeTypeEntity incomeType = incomeTypeBO.findIncomeTypeByImportCode(
                                            guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroRenda());
                        if(AppUtil.isNullOrEmpty(incomeType)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroRenda(), AppConstants.DOMAIN_INCOME_TYPE);
                        }else{
                            guarantorEmployer.setIncomeType(incomeType);    
                        }
                    }else{
                        guarantorEmployer.setIncomeType(null);
                    }
                    
                    /** Tipo Comprovante Renda do Avalista**/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroComprovanteRenda())){
                        ProofIncomeTypeEntity proofIncomeType = proofIncomeTypeBO
                                    .findProofIncomeTypeByImportCode(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroComprovanteRenda());
                        
                        if(AppUtil.isNullOrEmpty(proofIncomeType)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroComprovanteRenda(), AppConstants.DOMAIN_PROOF_INCOME_TYPE );
                        }else{
                            guarantorEmployer.setProofIncomeType(proofIncomeType);
                        }
                    }else{
                        guarantorEmployer.setProofIncomeType(null);
                    }
    
                    /** Atividade Economica do Avalista **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroAtividadeEconomica())){
                            EconomicActivityEntity economicActivity = economicActivityBO.findEconomicActivityByImportCode(
                                    SantanderServicesUtils.extractFillZeroToLeft(
                                            guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroAtividadeEconomica(),3));
                            if(AppUtil.isNullOrEmpty(economicActivity)){
                                //send email
                                this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroAtividadeEconomica(), AppConstants.DOMAIN_ECONOMIC_ACTIVITY);
                            }else{
                                guarantorEmployer.setEconomicActivity(economicActivity);
                            }
                    }else{
                        guarantorEmployer.setEconomicActivity(null);
                    }
                    
                    /** Grupo Atividade Economica do Avalista **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroGrupoAtivEconomica())){
                        IndustrialSegmentEntity industrialSegment = industrialSegmentBO.findIndustrialSegmentById(Long.valueOf(
                                guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroGrupoAtivEconomica()));
                        if(AppUtil.isNullOrEmpty(industrialSegment)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroGrupoAtivEconomica(), AppConstants.DOMAIN_INDUSTRIAL_SEGMENT);
                        }else{
                            guarantorEmployer.setIndustrialSegment(industrialSegment);
                        }
                    }else{
                        guarantorEmployer.setIndustrialSegment(null);
                    }
                    
                    
                    /** Cargo / Função do Avalista **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoProfissao())){
                        ProfessionEntity profession = professionBO.findProfessionByDescription(
                                guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoProfissao());
                        if(AppUtil.isNullOrEmpty(profession)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoProfissao(), AppConstants.DOMAIN_PROFESSION);
                        }else{
                            guarantorEmployer.setProfession(profession);
                        }
                    }else{
                        guarantorEmployer.setProfession(null);
                    }
                    
                    
                    /** Indicativo se é funcionario Santander do Avalista**/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getIndicativoFuncSantander())){
                         SantanderEmployeeTypeEntity santanderEmployeeType = 
                                        santanderEmployeeTypeBO.findSantanderEmployeeTypeByImportCode(
                                                guarantorSantander.getDadosPessoaisEbancariosAvalista().getIndicativoFuncSantander());
                         if(AppUtil.isNullOrEmpty(santanderEmployeeType)){
                             //send email
                             this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getIndicativoFuncSantander(), AppConstants.DOMAIN_SANTANDER_EMPLOYEE_TYPE);
                         }else{
                             guarantorEmployer.setSantanderEmployeeType(santanderEmployeeType);
                         }
                    }else{
                        guarantorEmployer.setSantanderEmployeeType(null);
                    }
                    
                    /** Ocupação de Avalista **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroOcupacao())){
                        OccupationEntity occupation = occupationBO.findOccupationByImportCode(
                                        SantanderServicesUtils.extractFillZeroToLeft(
                                                guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroOcupacao(), 3));
                        if(AppUtil.isNullOrEmpty(occupation)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroOcupacao(), AppConstants.DOMAIN_OCCUPATION);
                        }else{
                            guarantorEmployer.setOccupation(occupation); 
                        }
                    }else{
                        guarantorEmployer.setOccupation(null); 
                    }
                    
                    /** CNPJ do Avalista **/
                    guarantorEmployer.setCnpj(SantanderServicesUtils.extractCNPJ(
                            guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroCnpjEmpresa()));
                
                    /** Nome da Empresa do Avalista **/
                    guarantorEmployer.setEmployerName(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeEmpresa());
                    
                    /** Tamanho da Empresa do Avalista **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroPorteEmpresarial())){
                         EmployerSizeEntity employerSize = employerSizeBO.findEmployerSizeById(
                                       Long.valueOf(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroPorteEmpresarial()));
                        if(AppUtil.isNullOrEmpty(employerSize)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroPorteEmpresarial(), AppConstants.DOMAIN_EMPLOYER_SIZE);
                        }else{
                            guarantorEmployer.setEmployerSize(employerSize);   
                        }
                    }else{
                        guarantorEmployer.setEmployerSize(null);
                    }
                    
                    /** Valor Patrimonio da Empresa do Avalista**/
                    guarantorEmployer.setValueAssets(SantanderServicesUtils.stringToBigDecimal(
                            guarantorSantander.getDadosPessoaisEbancariosAvalista().getValorPatrimonio()));
                    
                    //atualiza empresa
                    guarantor.setEmployer(guarantorEmployer);
                    
                    //Salva ou Atualiza
                    if(AppUtil.isNullOrEmpty(guarantorEmployer.getId())){
                        employerBO.insert(guarantorEmployer);
                    }else{
                        employerBO.update(guarantorEmployer);
                    }
                    
                    /** Endereco Profissional do Avalista **/ 
                    AddressEntity addressEmployerGuarantor = guarantor.getEmployerAddress();
                    if(AppUtil.isNullOrEmpty(addressEmployerGuarantor)){
                        addressEmployerGuarantor = new AddressEntity();
                    }
                        
                    /** Cep **/
                    addressEmployerGuarantor.setPostCode(SantanderServicesUtils.extractFillZeroToLeft(
                                            guarantorSantander.getDadosEnderecoAvalista().getNumeroCepComercial(), 8));
                    
                    /** Número Endereço **/
                    addressEmployerGuarantor.setNumber(guarantorSantander.getDadosEnderecoAvalista().getNumeroEnderecoComercial());
                    
                    /** Endereço **/
                    addressEmployerGuarantor.setAddress(guarantorSantander.getDadosEnderecoAvalista().getDescricaoEnderecoComercial());
                    
                    /** Complemento **/
                    addressEmployerGuarantor.setComplement(guarantorSantander.getDadosEnderecoAvalista().getDescricaoComplementoEndComercial());
                    
                    /** Bairro **/
                    addressEmployerGuarantor.setNeighborhood(guarantorSantander.getDadosEnderecoAvalista().getNomeBairroComercial());
                    
                    /** Cidade **/
                    addressEmployerGuarantor.setCity(guarantorSantander.getDadosEnderecoAvalista().getNomeCidadeComercial());
                    
                    /** Codigo Sigla UF **/      
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosEnderecoAvalista().getCodigoSiglaUfComercial())){
                        ProvinceEntity province = provinceBO.findProvinceByAcronym(
                                                guarantorSantander.getDadosEnderecoAvalista().getCodigoSiglaUfComercial());
                        if(AppUtil.isNullOrEmpty(province)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosEnderecoAvalista().getCodigoSiglaUfComercial(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                        }else{
                            addressEmployerGuarantor.setProvince(province);
                        }
                    }else{
                        addressEmployerGuarantor.setProvince(null);
                    }
                    
                    //Atualiza endereço profissional guarantor
                    guarantor.setEmployerAddress(addressEmployerGuarantor);
                                                        
                    //Atualiza/ insere endereço profissional
                    if(AppUtil.isNullOrEmpty(addressEmployerGuarantor.getId())){
                        addressBO.insert(addressEmployerGuarantor);
                    }else{
                        addressBO.update(addressEmployerGuarantor);
                    }
                    
                }else{
                    /* Caso não tenha dados profissionais da empresa e endereco será apagado */
                    if(!AppUtil.isNullOrEmpty(guarantor.getEmployer())){
                        EmployerEntity employer = guarantor.getEmployer();
                        guarantor.setEmployer(null);
                        employerBO.delete(employer);
                    }
                    if(!AppUtil.isNullOrEmpty(guarantor.getEmployerAddress())){
                        AddressEntity addressEmployerGuarantorToRemove = guarantor.getEmployerAddress(); 
                        guarantor.setEmployerAddress(null);
                        addressBO.delete(addressEmployerGuarantorToRemove);
                    }
                }
                /** Valor Outras Rendas Mensal do Cliente**/
                guarantor.setOtherIncome(SantanderServicesUtils.stringToBigDecimal(
                                        guarantorSantander.getDadosPessoaisEbancariosAvalista().getValorOutrasRendas()));
                
                /** Valor Renda Mensal do Cliente **/
                guarantor.setIncome(SantanderServicesUtils.stringToBigDecimal(
                                        guarantorSantander.getDadosPessoaisEbancariosAvalista().getValorRendaMensal()));
                
                //Limpa lista de referencia
                guarantor.setListGuarantorReference(new HashSet<GuarantorReferenceEntity>());
                guarantorReferenceBO.deleteAllGuarantorReference(guarantor);
                
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeRefer1()) 
                        || !AppUtil.isNullOrEmptyOrZero(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroDddRefer1())
                        || !AppUtil.isNullOrEmptyOrZero(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoTelefoneRefer1())){
                    /** Referencia Pessoal 1 **/
                    GuarantorReferenceEntity guarantorReference1 = new GuarantorReferenceEntity();
                    guarantorReference1.setDescription(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeRefer1());
                    guarantorReference1.setDdd(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroDddRefer1()));
                    guarantorReference1.setPhone(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoTelefoneRefer1()));
                    guarantorReference1.setGuarantor(guarantor);
                    guarantor.getListGuarantorReference().add(guarantorReference1);
                    guarantorReferenceBO.insert(guarantorReference1);
                }  
                
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeRefer2()) 
                        || !AppUtil.isNullOrEmptyOrZero(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroDddRefer2())
                        || !AppUtil.isNullOrEmptyOrZero(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoTelefoneRefer2())){
                        /** Referencia Pessoal 2 **/
                        GuarantorReferenceEntity guarantorReference2 = new GuarantorReferenceEntity();
                        guarantorReference2.setDescription(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNomeRefer2());
                        guarantorReference2.setDdd(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroDddRefer2()));
                        guarantorReference2.setPhone(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoTelefoneRefer2()));
                        guarantorReference2.setGuarantor(guarantor);
                        guarantor.getListGuarantorReference().add(guarantorReference2);
                        guarantorReferenceBO.insert(guarantorReference2);
                }
                
                /* Remove o banco caso o numero da agencia e conta for diferente de zero ou Null*/
                if(!AppUtil.isNullOrEmptyOrZero(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroAgencia())
                        || !AppUtil.isNullOrEmptyOrZero(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroContaCorrente())){

                    //Dados Bancarios
                    BankDetailEntity bankGuarantor = guarantor.getBankDetail();
                    if(AppUtil.isNullOrEmpty(bankGuarantor)){
                        bankGuarantor = new BankDetailEntity();
                    }
                    
                    /** Codigo Banco **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroBanco())){
                        BankEntity bank = bankBO.findBankByImportCode(SantanderServicesUtils.extractImportCodeBank(
                                                        guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroBanco()));
                        if(AppUtil.isNullOrEmpty(bank)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroBanco(), AppConstants.DOMAIN_BANK);
                        }else{
                            bankGuarantor.setBank(bank);
                        }
                    }else{
                        bankGuarantor.setBank(null);
                    }
                    
                    /** Numero Agencia **/
                    bankGuarantor.setBranch(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroAgencia());
                    /** Digito Agencia **/
                    bankGuarantor.setBranchDigit(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoDigitoAgencia());
                    /** Numero Conta **/
                    bankGuarantor.setAccount(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroContaCorrente());
                    /** Digito Conta **/
                    bankGuarantor.setAccountDigit(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoDigitoContaCorrente());
                    /** Data Abertura **/
                    bankGuarantor.setAccountOpeningDate(SantanderServicesUtils.convertDateSinceOf(
                            guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroMesClienteDesde(), guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroAnoClienteDesde()));
                    /** DDD **/
                    bankGuarantor.setDdd(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosPessoaisEbancariosAvalista().getNumeroDddTelefoneBanco()));
                    /** Telefone **/
                    bankGuarantor.setPhone(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosPessoaisEbancariosAvalista().getDescricaoTelefoneBanco()));
                    /** Tipo da Conta **/
                    bankGuarantor.setAccountType(AccountTypeEnum.bySantanderCode(guarantorSantander.getDadosPessoaisEbancariosAvalista().getCodigoTipoContaBancaria()));
                    bankGuarantor.setGuarantor(guarantor);
                    guarantor.setBankDetail(bankGuarantor);
                    
                    //Atualiza banco
                    if(AppUtil.isNullOrEmpty(bankGuarantor.getId())){
                        bankDetailBO.insert(bankGuarantor);
                    }else{
                        bankDetailBO.update(bankGuarantor);    
                    }
                }else{
                    if(!AppUtil.isNullOrEmpty(guarantor.getBankDetail())){
                        BankDetailEntity bankDetailToRemove = guarantor.getBankDetail();
                        guarantor.setBankDetail(null);
                        bankDetailBO.delete(bankDetailToRemove);
                    }
                }
                
                //Remove todos os telefones
                guarantor.setListPhone(new HashSet<PhoneEntity>());
                phoneBO.deleteAllPhoneGuarantor(guarantor);
           
                if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosEnderecoAvalista())){   
                    
                    /** Email **/
                    guarantor.setEmail(guarantorSantander.getDadosEnderecoAvalista().getDescricaoEnderecoEmail());
                    guarantor.setAddressSince(SantanderServicesUtils.convertDateSinceOf(
                                        guarantorSantander.getDadosEnderecoAvalista().getDataMesResideDesde(),
                                        guarantorSantander.getDadosEnderecoAvalista().getDataAnoResideDesde()));
                    
                    
                    /** Tipo Residencia **/
                    if (!AppUtil.isNullOrEmpty(guarantorSantander.getDadosEnderecoAvalista().getNumeroTipoResidencia())) {
                        ResidenceTypeEntity residenceType = residenceTypeBO
                                    .findResidenceTypeById(Long.valueOf(guarantorSantander.getDadosEnderecoAvalista().getNumeroTipoResidencia()));
                        if (AppUtil.isNullOrEmpty(residenceType)) {
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosEnderecoAvalista().getNumeroTipoResidencia(), AppConstants.DOMAIN_RESIDENCE_TYPE);
                        }else{
                            guarantor.setResidenceType(residenceType);    
                        }
                    }else{
                        guarantor.setResidenceType(null);
                    }
                    
                    
                    /** Tipo Telefone **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosEnderecoAvalista().getNumeroTipoTelefResiden())){
                        if (AppConstants.CHANGE_PHONE_TYPE_TO_MESSAGE.equals(guarantorSantander.getDadosEnderecoAvalista().getNumeroTipoTelefResiden())) {
                            guarantor.setPersonalPhoneType(PersonalPhoneTypeEnum.RECADO);
                        } else {
                            guarantor.setPersonalPhoneType(PersonalPhoneTypeEnum
                                    .getByImportCode(Integer.valueOf(guarantorSantander.getDadosEnderecoAvalista().getNumeroTipoTelefResiden())));
                        }
                    }else{
                        guarantor.setPersonalPhoneType(null);
                    }
                    
                    /** Endereco Correspondência Avalista **/
                    if (!AppUtil.isNullOrEmpty(guarantorSantander.getDadosEnderecoAvalista().getCodigoEnderecoCorrespondencia())) {
                        AddressTypeEntity addressType = addressTypeBO
                                .findAddressTypeByImportCode(guarantorSantander.getDadosEnderecoAvalista().getCodigoEnderecoCorrespondencia());
                        if (AppUtil.isNullOrEmpty(addressType)) {
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosEnderecoAvalista().getCodigoEnderecoCorrespondencia(), AppConstants.DOMAIN_MAILING_ADDRESS_TYPE);
                        }else{
                            guarantor.setMailingAddressType(addressType);  
                        }
                    }else{
                        guarantor.setMailingAddressType(null);
                    }
                    
                    
                    
                    /** Add telefone fixo **/
                    PhoneEntity homePhoneGuarantor = new PhoneEntity();
                    
                    homePhoneGuarantor.setDdd(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosEnderecoAvalista().getNumeroDddResidencial()));
                    homePhoneGuarantor.setPhone(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosEnderecoAvalista().getDescricaoTelResidencial()));
                    homePhoneGuarantor.setPhoneType(PhoneTypeEnum.FIXO);
                    homePhoneGuarantor.setGuarantor(guarantor);
                    guarantor.getListPhone().add(homePhoneGuarantor);
                    
                    phoneBO.insert(homePhoneGuarantor);
                    
                    /** Add telefone comercial **/
                    PhoneEntity comercialPhoneGuarantor = new PhoneEntity();
                    
                    comercialPhoneGuarantor.setDdd(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosEnderecoAvalista().getNumeroDddTelComercial()));
                    comercialPhoneGuarantor.setPhone(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosEnderecoAvalista().getDescricaoTelComercial()));
                    comercialPhoneGuarantor.setPhoneType(PhoneTypeEnum.COMERCIAL);
                    comercialPhoneGuarantor.setGuarantor(guarantor);
                    guarantor.getListPhone().add(comercialPhoneGuarantor);
                    
                    phoneBO.insert(comercialPhoneGuarantor);
                    
    
                    /** Add telefone celular **/
                    PhoneEntity cellPhoneGuarantor = new PhoneEntity();
                    
                    cellPhoneGuarantor.setDdd(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosEnderecoAvalista().getNumeroDddCel()));
                    cellPhoneGuarantor.setPhone(SantanderServicesUtils.extractPhone(guarantorSantander.getDadosEnderecoAvalista().getCodigotelCel()));
                    cellPhoneGuarantor.setPhoneType(PhoneTypeEnum.CELULAR);
                    cellPhoneGuarantor.setGuarantor(guarantor);
                    guarantor.getListPhone().add(cellPhoneGuarantor);
                    
                    phoneBO.insert(cellPhoneGuarantor);

                    //Endereco Avalista
                    AddressEntity addressGuarantor = guarantor.getAddress();
                    if(AppUtil.isNullOrEmpty(addressGuarantor)){
                        addressGuarantor = new AddressEntity();
                    }
                        
                    /** Cep **/
                    addressGuarantor.setPostCode(SantanderServicesUtils.extractFillZeroToLeft(
                                            guarantorSantander.getDadosEnderecoAvalista().getNumeroCepResidencial(), 8));
                    
                    /** Número Endereço **/
                    addressGuarantor.setNumber(guarantorSantander.getDadosEnderecoAvalista().getNumeroResidencial());
                    
                    /** Endereço **/
                    addressGuarantor.setAddress(guarantorSantander.getDadosEnderecoAvalista().getDescricaoEnderecoResidencia());
                    
                    /** Complemento **/
                    addressGuarantor.setComplement(guarantorSantander.getDadosEnderecoAvalista().getDescricaoComplementoResidencia());
                    
                    /** Bairro **/
                    addressGuarantor.setNeighborhood(guarantorSantander.getDadosEnderecoAvalista().getNomeBairroResidencia());
                    
                    /** Cidade **/
                    addressGuarantor.setCity(guarantorSantander.getDadosEnderecoAvalista().getNomeCidadeResidencial());
                    
                    /** Codigo Sigla UF **/      
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosEnderecoAvalista().getCodigoSiglaUfResidencial())){
                        ProvinceEntity province = provinceBO.findProvinceByAcronym(
                                                guarantorSantander.getDadosEnderecoAvalista().getCodigoSiglaUfResidencial());
                        if(AppUtil.isNullOrEmpty(province)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosEnderecoAvalista().getCodigoSiglaUfResidencial(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                        }else{
                            addressGuarantor.setProvince(province);
                        }
                    }else{
                        addressGuarantor.setProvince(null);
                    }
                    
                    //Atualiza endereço guarantor
                    guarantor.setAddress(addressGuarantor);
                                                        
                    //Atualiza endereço
                    if(AppUtil.isNullOrEmpty(addressGuarantor.getId())){
                        addressBO.insert(addressGuarantor);
                    }else{
                        addressBO.update(addressGuarantor);
                    }
                
                
                }else{
                    if(!AppUtil.isNullOrEmpty(guarantor.getAddress())){
                        //remove endereco do guarantor
                        AddressEntity adressToRemove = guarantor.getAddress();
                        guarantor.setAddress(null);
                        addressBO.delete(adressToRemove);
                        //remove endereco profissional do guarantor
                        AddressEntity adressEmployerToRemove = guarantor.getEmployerAddress();
                        guarantor.setEmployerAddress(null);
                        addressBO.delete(adressEmployerToRemove);
                    }
                    /* Email Avalista */
                    guarantor.setEmail(null);
                    /* Reside desde */
                    guarantor.setAddressSince(null);
                    /* Tipo telefone */
                    guarantor.setPersonalPhoneType(null);
                }
                
                
                // Conjuge
                if (guarantorSantander.getDadosConjugeAvalista() != null 
                        && !AppUtil.isNullOrEmpty(guarantorSantander.getDadosConjugeAvalista().getNumeroCpfCnpjConju())) {

                    GuarantorSpouseEntity guarantorSpouse = guarantor.getGuarantorSpouse();

                    if (AppUtil.isNullOrEmpty(guarantorSpouse)) {
                        guarantorSpouse = new GuarantorSpouseEntity();
                    }

                    /** Nome do conjuge **/
                    guarantorSpouse.setNameClient(guarantorSantander.getDadosConjugeAvalista().getNomeCompletoConju());

                    /** CPF do conjuge **/
                    guarantorSpouse
                            .setCpf(SantanderServicesUtils.extractCPF(guarantorSantander.getDadosConjugeAvalista().getNumeroCpfCnpjConju()));

                    /** Genero do conjuge **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosConjugeAvalista().getCodigoSexoConju())){
                        PersonGenderEnum personGender = PersonGenderEnum.getGenderByAbbreviation(
                                            guarantorSantander.getDadosConjugeAvalista().getCodigoSexoConju());
                        if(AppUtil.isNullOrEmpty(personGender)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosConjugeAvalista().getCodigoSexoConju(), AppConstants.DOMAIN_GENDER);
                        }
                        guarantorSpouse.setPersonGender(personGender);
                    }else{
                        guarantorSpouse.setPersonGender(null);
                    }
                    
                    /** Data nascimento do conjuge **/
                    guarantorSpouse.setDateBirth(SantanderServicesUtils
                            .santanderFormatToDate(guarantorSantander.getDadosConjugeAvalista().getDataNascimentoConju(), "yyyyMMdd"));

                    /** Número documento **/
                    guarantorSpouse.setDocumentNumber(guarantorSantander.getDadosConjugeAvalista().getCodigoDocumentoConju());

                    /** Orgão Emissor **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosConjugeAvalista().getNomeOrgaoEmissorConju())){
                       EmissionOrganismEntity emissionOrganism = emissionOrganismBO
                                    .findEmissionOrganismByAcronym(guarantorSantander.getDadosConjugeAvalista().getNomeOrgaoEmissorConju());
                        if(AppUtil.isNullOrEmpty(emissionOrganism)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosConjugeAvalista().getNomeOrgaoEmissorConju(), AppConstants.DOMAIN_EMISSION_ORGANISM);
                        }else{
                            guarantorSpouse.setEmissionOrganism(emissionOrganism);
                        }
                    }else{
                        guarantorSpouse.setEmissionOrganism(null);
                    }

                    /** UF Orgão Emissor **/
                    if (!AppUtil.isNullOrEmpty(guarantorSantander.getDadosConjugeAvalista().getCodigoEstadoOrgaoEmissorConju())) {
                        ProvinceEntity province = provinceBO.findProvinceByAcronym(guarantorSantander.getDadosConjugeAvalista().getCodigoEstadoOrgaoEmissorConju());
                        if (AppUtil.isNullOrEmpty(province)) {
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosConjugeAvalista().getCodigoEstadoOrgaoEmissorConju(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                        }else{
                            guarantorSpouse.setProvince(province);
                        }
                    }else{
                        guarantorSpouse.setProvince(null);
                    }
                    
                    /** Ocupação conjuge **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosConjugeAvalista().getNumeroOcupacaoConju())){
                        OccupationEntity occupation = occupationBO.findOccupationByImportCode(SantanderServicesUtils
                                .extractFillZeroToLeft(guarantorSantander.getDadosConjugeAvalista().getNumeroOcupacaoConju(), 3));
                        if(AppUtil.isNullOrEmpty(occupation)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosConjugeAvalista().getNumeroOcupacaoConju(), AppConstants.DOMAIN_OCCUPATION);
                        }else{
                            guarantorSpouse.setOccupation(occupation);    
                        }
                    }else{
                        guarantorSpouse.setOccupation(null);
                    }
                    
                    /** Profissão **/
                    if(!AppUtil.isNullOrEmpty(guarantorSantander.getDadosConjugeAvalista().getDescFuncaoConju())){
                        ProfessionEntity profession = professionBO
                                .findProfessionByDescription(guarantorSantander.getDadosConjugeAvalista().getDescFuncaoConju());

                        if(AppUtil.isNullOrEmpty(profession)){
                            //send email
                            this.sendMailDomainNotRecognized(guarantorSantander.getDadosConjugeAvalista().getDescFuncaoConju(), AppConstants.DOMAIN_PROFESSION);
                        }else{
                            guarantorSpouse.setProfession(profession);
                        }
                    }else{
                        guarantorSpouse.setProfession(null);
                    }
                    
                    /** Nome empresa **/
                    guarantorSpouse.setEmployerName(guarantorSantander.getDadosConjugeAvalista().getNomeEmpresaConju());

                    /** Data admissão **/
                    guarantorSpouse.setAdmissionDate(
                            SantanderServicesUtils.santanderFormatToDate(guarantorSantander.getDadosConjugeAvalista().getDataAdmissaoConju(), "yyyyMM"));

                    /** Renda mensal **/
                    guarantorSpouse.setIncome(
                            SantanderServicesUtils.stringToBigDecimal(guarantorSantander.getDadosConjugeAvalista().getValorRendaMensalConju()));

                    /** DDD **/
                    guarantorSpouse.setDdd(
                            SantanderServicesUtils.extractPhone(guarantorSantander.getDadosConjugeAvalista().getNumeroDddTelComercialConju()));

                    /** Telefone **/
                    guarantorSpouse.setPhone(
                            SantanderServicesUtils.extractPhone(guarantorSantander.getDadosConjugeAvalista().getDescricaoTelComercialConju()));

                    /** Ramal **/
                    guarantorSpouse.setExtensionLine(SantanderServicesUtils
                            .extractPhone(guarantorSantander.getDadosConjugeAvalista().getNumeroTelComercialRamalConju()));

                    //Esposa Guarantidor
                    guarantor.setGuarantorSpouse(guarantorSpouse);
                    guarantorSpouse.setGuarantor(guarantor);
                    
                    // Atualiza Conjuge
                    if(AppUtil.isNullOrEmpty(guarantorSpouse.getId())){
                        guarantorSpouseBO.insert(guarantorSpouse);
                    }else{
                        guarantorSpouseBO.update(guarantorSpouse);
                    }

                }else{
                    /* Caso o conjuge tenha sido removido no santander tb será apagado no banco */
                    if(!AppUtil.isNullOrEmpty(guarantor.getGuarantorSpouse())){
                        GuarantorSpouseEntity guarantorSpouseToRemove = guarantor.getGuarantorSpouse();
                        guarantor.setGuarantorSpouse(null);
                        guarantorSpouseBO.delete(guarantorSpouseToRemove);
                    }
                }
                
                //Atualiza guarantor
                guarantorBO.update(guarantor);
            }
            
            /* O avalisa que está no banco e não está no Santander será removido */
            for(GuarantorEntity guarantorToRemove : mapDadosAvalistasSantanderRemove.values()){
                this.deleteGuarantor(guarantorToRemove);
            }
            
            LOGGER.debug(" >> END extractGuarantor ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    /**
     * @param dadosGarantiaSantander
     * @param dossier
     * @throws UnexpectedException
     */
    private void extractVehicle(ConsultaPropostaDetalhadaAFC dadosSantander, DossierEntity dossier)
            throws UnexpectedException {
        
        LOGGER.debug(" >> INIT extractVehicle ");
        
        DadosGarantia dadosGarantiaSantander = dadosSantander.getDadosGarantia();

        if(!AppUtil.isNullOrEmpty(dadosGarantiaSantander)){
            
            DossierVehicleEntity dossierVehicle  = dossier.getDossierVehicle();
            if(AppUtil.isNullOrEmpty(dossierVehicle)){
                dossierVehicle = new DossierVehicleEntity();
            }    
        
            String fipeCode = "";
            
            if(!AppUtil.isNullOrEmpty(dadosGarantiaSantander.getNumeroTabMarca()) && !AppUtil.isNullOrEmpty(dadosGarantiaSantander.getNumeroTabModelo())){
                // este codigo nao vem o -1 por isso do % no final
                fipeCode = dadosGarantiaSantander.getNumeroTabMarca().substring(2) + dadosGarantiaSantander.getNumeroTabModelo().substring(2);
            }
            
            //Busca a versao veiculo
            VehicleVersionEntity vehicleVersion = vehicleVersionBO.findVehicleVersion(fipeCode, 
                                                Integer.valueOf(dadosGarantiaSantander.getNumeroAnoModelo()), 
                                                Integer.valueOf(dadosGarantiaSantander.getNumeroAnoFabricacao()));  
                                        
            if(AppUtil.isNullOrEmpty(vehicleVersion)){
                //send email
                this.sendMailDomainNotRecognized("fipe: " + fipeCode 
                                                + " - ano modelo: " + dadosGarantiaSantander.getNumeroAnoModelo()
                                                + " - ano fabrição: " + dadosGarantiaSantander.getNumeroAnoFabricacao(), AppConstants.DOMAIN_VEHICLE_VERSION);
            }else{
                dossierVehicle.setVehicleVersion(vehicleVersion);    
            }
            
            
            /** Placa **/
            dossierVehicle.setRegisterNumber(dadosGarantiaSantander.getDescricaoPlaca());
            
            /** Chassi **/
            dossierVehicle.setChassi(StringUtils.trimToNull(dadosGarantiaSantander.getDescricaoChassi()));
            
            /** Cor **/
            if(!AppUtil.isNullOrEmpty(dadosGarantiaSantander.getDescricaoCor())){
                ColorEntity color = colorBO.findColorByDescription(
                                StringUtils.trimToNull(dadosGarantiaSantander.getDescricaoCor()).toUpperCase());

                if(AppUtil.isNullOrEmpty(color)){
                    //send email
                    this.sendMailDomainNotRecognized(dadosGarantiaSantander.getDescricaoCor(), AppConstants.DOMAIN_VEHICLE_COLOR);
                }else{
                    dossierVehicle.setColor(color);
                }
            }else{
                dossierVehicle.setColor(null);
            }
            
            /** Renavam **/
            dossierVehicle.setRenavam(dadosGarantiaSantander.getCodigoRenavam());
            
            /** Indicativo se é Nacional **/
            dossierVehicle.setNationalCar(AppConstants.NATIONAL_VEHICLE.equalsIgnoreCase(dadosGarantiaSantander.getIndicativoProcVeiculo()));
            
            /** Estado Licenciamento **/
            if(!AppUtil.isNullOrEmpty(dossierVehicle.getLicenseProvince())){
                ProvinceEntity province = provinceBO.findProvinceByAcronym(dadosGarantiaSantander.getCodigoEstadoLicenciamento());
                if(AppUtil.isNullOrEmpty(province)){
                    //send email
                    this.sendMailDomainNotRecognized(dadosGarantiaSantander.getCodigoEstadoLicenciamento(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                }else{
                    dossierVehicle.setLicenseProvince(province);
                }
            }else{
                dossierVehicle.setLicenseProvince(null);
            }
            
            /** Estado do Registro **/
            if(!AppUtil.isNullOrEmpty(dadosGarantiaSantander.getCodigoEstadoPlaca())){
                ProvinceEntity province = provinceBO.findProvinceByAcronym(dadosGarantiaSantander.getCodigoEstadoPlaca());
                
                if(AppUtil.isNullOrEmpty(province)){
                    //send email
                    this.sendMailDomainNotRecognized(dadosGarantiaSantander.getCodigoEstadoPlaca(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                }else{
                    dossierVehicle.setRegisterProvince(province);
                }
            }else{
                dossierVehicle.setRegisterProvince(null);
            }
            
            /** Adiciona Veiculo **/
            dossierVehicle.setDossier(dossier);
            dossier.setDossierVehicle(dossierVehicle);
            
            if(AppUtil.isNullOrEmpty(dossierVehicle.getId())){
                dossieVehicleBO.insert(dossierVehicle);
            }else{
                dossieVehicleBO.update(dossierVehicle);
            }

        
            //Limpa lista special vehicle
            dossier.setListSpecialVehicleType(new HashSet<SpecialVehicleTypeEntity>());
            /** Veiculo Adaptado **/
            if(SantanderServicesUtils.sOrNtoBoolToBoolean(dadosGarantiaSantander.getIndicativoAdaptado())){
                
                dossier.getListSpecialVehicleType().add(specialTypeBO.findSpecialVehicleTypeByDescription(
                                    StringUtils.trimToNull(SpecialVehicleTypeEnum.ADAPTADO.getDescription()).toUpperCase()));
            }
            /** Veiculo Taxi **/
            if(SantanderServicesUtils.sOrNtoBoolToBoolean(dadosGarantiaSantander.getIndicativoTaxi())){
                dossier.getListSpecialVehicleType().add(specialTypeBO.findSpecialVehicleTypeByDescription(
                                        StringUtils.trimToNull(SpecialVehicleTypeEnum.TAXI.getDescription()).toUpperCase()));
            }
        }else{
            if(!AppUtil.isNullOrEmpty(dossier.getDossierVehicle())){
                //Limpa lista special vehicle
                dossier.setListSpecialVehicleType(new HashSet<SpecialVehicleTypeEntity>());
                DossierVehicleEntity dossierVehicleToRemove = dossier.getDossierVehicle();
                dossier.setDossierVehicle(null);
                dossieVehicleBO.delete(dossierVehicleToRemove);
            }
        }
        
        LOGGER.debug(" >> END extractVehicle ");
  
    }

    /**
     * 
     * @param dadosSantander
     * @param dossier
     * @throws UnexpectedException
     */
    private void extractCustomer(ConsultaPropostaDetalhadaAFC dadosSantander, DossierEntity dossier)
            throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT extractCustomer ");
            
            DadosClienteDadosProfissionais dadosProfissionaisSantander = dadosSantander
                    .getDadosClienteDadosProfissionais();
            DadosConjuge dadosConjugeSantander = dadosSantander.getDadosConjuge();
            DadosEndereco dadosEnderecoSantander = dadosSantander.getDadosEndereco();
            ReferenciaPessoalBancaria dadosReferenciaPessoalBancariaSantander = 
                                                        dadosSantander.getReferenciaPessoalBancaria();
            // Atualiza customer
            CustomerEntity customer = dossier.getCustomer();

            // Limpa lista de telefone
            customer.setListPhone(new HashSet<PhoneEntity>());
            phoneBO.deleteAllPhoneCustomer(customer);

            if (!SantanderServicesUtils.isPessoaJuridica(dadosProfissionaisSantander.getCodigoTipoPessoa())) {
                /* Cliente PF */

                /** Naturalidade **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getCodigoEstadoNaturalidade())) {
                    ProvinceEntity province = 
                            provinceBO.findProvinceByAcronym(dadosProfissionaisSantander.getCodigoEstadoNaturalidade());
                    if (AppUtil.isNullOrEmpty(province)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getCodigoEstadoNaturalidade(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                    }else{
                        customer.setProvince(province);
                    }
                }else{
                    customer.setProvince(null);
                }

                /** PF **/
                customer.setPersonType(PersonTypeEnum.PF);
                
                /** CPF **/
                customer.setCpfCnpj(SantanderServicesUtils.extractCPF(AppUtil.onlyNumberNullableZero(dadosProfissionaisSantander.getNumeroCpfCnpj())));

                /** Numero documento **/
                customer.setDocumentNumber(dadosProfissionaisSantander.getCodigoDocumento());

                /** Estado Orgao Emissor **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getCodigoEstadoOrgaoEmissor())) {
                     ProvinceEntity province = provinceBO.findProvinceByAcronym(dadosProfissionaisSantander.getCodigoEstadoOrgaoEmissor());
                    if (AppUtil.isNullOrEmpty(province)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getCodigoEstadoOrgaoEmissor(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                    }else{
                        customer.setDocumentProvince(province);
                    }
                }else{
                    customer.setDocumentProvince(null);
                }

                /** País emissor do documento **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getCodigoPaisDocumento())) {
                    CountryEntity country = countryBO.findCountryByAcronym(dadosProfissionaisSantander.getCodigoPaisDocumento());
                    if (AppUtil.isNullOrEmpty(country)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getCodigoPaisDocumento(), AppConstants.DOMAIN_COUNTRY);
                    }else{
                        customer.setDocumentCountry(country);
                    }
                }else{
                    customer.setDocumentCountry(null);
                }

                /** Data da emissao do documento **/
                customer.setEmissionDate(SantanderServicesUtils
                        .santanderFormatToDate(dadosProfissionaisSantander.getDataEmissaoDocumento(), "yyyyMMdd"));

                /** Tipo de documento **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroTipoDocumento())) {
                    DocumentTypeEntity documentType = documentTypeBO
                            .findDocumentTypeByImportCode(dadosProfissionaisSantander.getNumeroTipoDocumento());
                    if (AppUtil.isNullOrEmpty(documentType)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroTipoDocumento(), AppConstants.DOMAIN_DOCUMENT_TYPE);
                    }else{
                        customer.setDocumentType(documentType);
                    }
                }else{
                    customer.setDocumentType(null);
                }

                /** Expiração documento **/
                // Caso venha a data de expiração será salva
                String strExpireDocumentDate = dadosProfissionaisSantander.getDataValidadeDocumento();
                if (!AppUtil.isNullOrEmptyOrZero(strExpireDocumentDate)) {
                    customer.setDocumentValidate(
                            SantanderServicesUtils.santanderFormatToDate(strExpireDocumentDate, "yyyyMMdd"));
                }else{
                    customer.setDocumentValidate(null);
                }

                /** Orgão Emissor **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNomeOrgaoEmissor())) {
                    EmissionOrganismEntity emissionOrganism = emissionOrganismBO
                                    .findEmissionOrganismByAcronym(dadosProfissionaisSantander.getNomeOrgaoEmissor());

                    if (AppUtil.isNullOrEmpty(emissionOrganism)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNomeOrgaoEmissor(), AppConstants.DOMAIN_EMISSION_ORGANISM);
                    }else{
                        customer.setEmissionOrganism(emissionOrganism);
                    }
                }else{
                    customer.setEmissionOrganism(null);
                }

                /** Genero **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getCodigoSexo())) {
                    PersonGenderEnum personGender = PersonGenderEnum.getGenderByAbbreviation(dadosProfissionaisSantander.getCodigoSexo());
                    if (AppUtil.isNullOrEmpty(personGender)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getCodigoSexo(), AppConstants.DOMAIN_GENDER);
                    }else{
                        customer.setPersonGender(personGender);
                    }
                }else{
                    customer.setPersonGender(null);
                }

                /** Estado civil  **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroEstadoCivil())) {
                    CivilStateEntity civilState = civilStateBO
                                .findCivilStateById(Long.valueOf(dadosProfissionaisSantander.getNumeroEstadoCivil()));
                    if (AppUtil.isNullOrEmpty(civilState)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroEstadoCivil(), AppConstants.DOMAIN_CIVIL_STATE);
                    }else{
                        customer.setCivilState(civilState);
                    }
                }else{
                    customer.setCivilState(null);
                }

                /** Grau de Instrução  **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroInstrucao())) {
                    EducationDegreeEntity educationDegree = educationDegreeBO
                                            .findEducationDegreeById(Long.valueOf(dadosProfissionaisSantander.getNumeroInstrucao()));
                    if (AppUtil.isNullOrEmpty(educationDegree)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroInstrucao(), AppConstants.DOMAIN_EDUCATION_DEGREE);
                    }else{
                        customer.setEducationDegree(educationDegree);
                    }
                }else{
                    customer.setEducationDegree(null);
                }

                /** Indicativo cliente possue necessidades especiais **/
                customer.setHandicapped(SantanderServicesUtils
                        .sOrNtoBoolToBoolean(dadosProfissionaisSantander.getIndicativoDeficienteFisico()));
                /* END Cliente PF */

                /* Dados profissionais */
                /* Remove so dados profissionais da empresa caso o numero do CNPJ for igual de zero ou Null*/
                if(!AppUtil.isNullOrEmptyOrZero(SantanderServicesUtils.extractCNPJ(dadosProfissionaisSantander.getNumeroCnpjEmpresa()))){
                    EmployerEntity employer = customer.getEmployer();
                    if (AppUtil.isNullOrEmpty(employer)) {
                        employer = new EmployerEntity();
                    }
    
                    /** Data de admissão **/
                    employer.setAdmissionDate(SantanderServicesUtils
                            .santanderFormatToDate(dadosProfissionaisSantander.getDataAdmissao(), "yyyyMM"));
    
                    /** Tipo de Renda  **/
                    if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroRenda())) {
                        IncomeTypeEntity IncomeType = incomeTypeBO.findIncomeTypeByImportCode(dadosProfissionaisSantander.getNumeroRenda());
                        if (AppUtil.isNullOrEmpty(IncomeType)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroRenda(), AppConstants.DOMAIN_INCOME_TYPE);
                         }else{
                             employer.setIncomeType(IncomeType);
                         }
                    }else{
                        employer.setIncomeType(null);
                    }
    
                    /** Tipo Comprovante Renda  **/
                    if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroComprovanteRenda())) {
                        ProofIncomeTypeEntity proofIncomeType = proofIncomeTypeBO
                                .findProofIncomeTypeByImportCode(dadosProfissionaisSantander.getNumeroComprovanteRenda());
                        if (AppUtil.isNullOrEmpty(proofIncomeType)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroComprovanteRenda(), AppConstants.DOMAIN_PROOF_INCOME_TYPE);
                        }else{
                            employer.setProofIncomeType(proofIncomeType);
                        }
                    }else{
                        employer.setProofIncomeType(null);
                    }
    
                    /** Atividade Economica **/
                    if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroAtividadeEconomica())) {
                        EconomicActivityEntity economicActivity = economicActivityBO
                                .findEconomicActivityByImportCode(SantanderServicesUtils.extractFillZeroToLeft(
                                                        dadosProfissionaisSantander.getNumeroAtividadeEconomica(), 3));
                        if (AppUtil.isNullOrEmpty(economicActivity)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroAtividadeEconomica(), AppConstants.DOMAIN_ECONOMIC_ACTIVITY);
                        }else{
                            employer.setEconomicActivity(economicActivity);
                        }
                    }else{
                        employer.setEconomicActivity(null);
                    }
    
                    /** Grupo Atividade Economica **/
                    if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroGrupoAtivEconomica())) {
                        IndustrialSegmentEntity industrialSegment = industrialSegmentBO.findIndustrialSegmentById(
                                                    Long.valueOf(dadosProfissionaisSantander.getNumeroGrupoAtivEconomica()));
                        if (AppUtil.isNullOrEmpty(industrialSegment)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroGrupoAtivEconomica(), AppConstants.DOMAIN_INDUSTRIAL_SEGMENT);
                        }else{
                            employer.setIndustrialSegment(industrialSegment);
                        }
                    }else{
                        employer.setIndustrialSegment(null);
                    }
    
                    /** Cargo / Função / Profissão **/
                    if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getDescricaoProfissao())) {
                        ProfessionEntity professionEntity = professionBO
                                .findProfessionByDescription(dadosProfissionaisSantander.getDescricaoProfissao());
                        if (AppUtil.isNullOrEmpty(professionEntity)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getDescricaoProfissao(), AppConstants.DOMAIN_PROFESSION);
                        }else{
                            employer.setProfession(professionEntity);
                        }
                    }else{
                        employer.setProfession(null);
                    }
    
                    /** Indicativo se é funcionario Santander **/
                    if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getIndicativoFuncSantander())) {
                        SantanderEmployeeTypeEntity santanderEmployeeType = 
                                                santanderEmployeeTypeBO.findSantanderEmployeeTypeByImportCode(dadosProfissionaisSantander.getIndicativoFuncSantander());
                        if (AppUtil.isNullOrEmpty(santanderEmployeeType)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getIndicativoFuncSantander(), AppConstants.DOMAIN_SANTANDER_EMPLOYEE_TYPE);
                        }else{
                            employer.setSantanderEmployeeType(santanderEmployeeType);
                        }
                    }else{
                        employer.setSantanderEmployeeType(null);
                    }
    
                    /** Ocupação **/
                    if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroOcupacao())) {
                        OccupationEntity occupation = occupationBO.findOccupationByImportCode(SantanderServicesUtils
                                                    .extractFillZeroToLeft(dadosProfissionaisSantander.getNumeroOcupacao(), 3));
                        if (AppUtil.isNullOrEmpty(occupation)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroOcupacao(), AppConstants.DOMAIN_OCCUPATION);
                        }else{
                            employer.setOccupation(occupation);
                        }
                    }else{
                        employer.setOccupation(null);
                    }
    
                    /** CNPJ **/
                    employer.setCnpj(SantanderServicesUtils.extractCNPJ(dadosProfissionaisSantander.getNumeroCnpjEmpresa()));
    
                    /** Nome da Empresa **/
                    employer.setEmployerName(dadosProfissionaisSantander.getNomeEmpresa());
    
                    /** Porte da Empresa **/
                    if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroPorteEmpresarial())) {
                        EmployerSizeEntity employerSize = employerSizeBO.findEmployerSizeById(
                                            Long.valueOf(dadosProfissionaisSantander.getNumeroPorteEmpresarial()));
                        if (AppUtil.isNullOrEmpty(employerSize)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroPorteEmpresarial(), AppConstants.DOMAIN_EMPLOYER_SIZE);
                        }else{
                            employer.setEmployerSize(employerSize);
                        }
                    }else{
                        employer.setEmployerSize(null);
                    }
    
                    /** Valor Patrimonio da Empresa **/
                    employer.setValueAssets(
                            SantanderServicesUtils.stringToBigDecimal(dadosProfissionaisSantander.getValorPatrimonio()));
    
                    // Add employer
                    customer.setEmployer(employer);
                    
                    if(AppUtil.isNullOrEmpty(employer.getId())){
                        employerBO.insert(employer);
                    }else{
                        employerBO.update(employer);
                    }
               
                    
                    /* Endereco profissionais */
                    AddressEntity employerAddress = customer.getEmployerAddress();
                    if (AppUtil.isNullOrEmpty(employerAddress)) {
                        employerAddress = new AddressEntity();
                    }
    
                    /** Cep Comercial **/
                    employerAddress.setPostCode(SantanderServicesUtils
                            .extractFillZeroToLeft(dadosEnderecoSantander.getNumeroCepComercial(), 8));
    
                    /** Número Endereço Comercial **/
                    employerAddress.setNumber(dadosEnderecoSantander.getNumeroEnderecoComercial());
    
                    /** Endereço Comercial **/
                    employerAddress.setAddress(dadosEnderecoSantander.getDescricaoEnderecoComercial());
    
                    /** Complementar Endereco **/
                    employerAddress.setComplement(dadosEnderecoSantander.getDescricaoComplementoEndComercial());
    
                    /** Bairro Comercial **/
                    employerAddress.setNeighborhood(dadosEnderecoSantander.getNomeBairroComercial());
    
                    /** Cidade Comercial **/
                    employerAddress.setCity(dadosEnderecoSantander.getNomeCidadeComercial());
    
                    /** Codigo Sigla UF **/
                    if (!AppUtil.isNullOrEmpty(dadosEnderecoSantander.getCodigoSiglaUfComercial())) {
                        ProvinceEntity province = provinceBO.findProvinceByAcronym(dadosEnderecoSantander.getCodigoSiglaUfComercial());
                        if (AppUtil.isNullOrEmpty(province)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosEnderecoSantander.getCodigoSiglaUfComercial(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                        }else{
                            employerAddress.setProvince(province);
                        }
                    }else{
                        employerAddress.setProvince(null);
                    }

                    // Atualiza endereço
                    customer.setEmployerAddress(employerAddress);
                    
                    if(AppUtil.isNullOrEmpty(employerAddress.getId())){
                        addressBO.insert(employerAddress);
                    }else{
                        addressBO.update(employerAddress);
                    }
                    
                }else{
                    /* Caso não tenha dados profissionais da empresa e endereco será apagado */
                    if(!AppUtil.isNullOrEmpty(customer.getEmployer())){
                        EmployerEntity employer = customer.getEmployer();
                        customer.setEmployer(null);
                        employerBO.delete(employer);
                    }
                    if(!AppUtil.isNullOrEmpty(customer.getEmployerAddress())){
                        AddressEntity adressToRemove = customer.getEmployerAddress(); 
                        customer.setEmployerAddress(null);
                        addressBO.delete(adressToRemove);
                    }
                }
                
                /** Valor Outras Rendas Mensal do Cliente **/
                customer.setOtherIncome(
                        SantanderServicesUtils.stringToBigDecimal(dadosProfissionaisSantander.getValorOutrasRendas()));

                /* Conjuge */
                if (dadosConjugeSantander != null 
                        && !AppUtil.isNullOrEmptyOrZero(AppUtil.onlyNumberNullableZero(dadosConjugeSantander.getNumeroCpfCnpj()))){

                    CustomerSpouseEntity customerSpouse = customer.getCustomerSpouse();
                    if (customerSpouse == null) {
                        customerSpouse = new CustomerSpouseEntity();
                    }

                    /** Nome do conjuge **/
                    customerSpouse.setNameClient(dadosConjugeSantander.getNomeCompleto());

                    /** CPF do conjuge **/
                    customerSpouse.setCpf(SantanderServicesUtils.extractCPF(dadosConjugeSantander.getNumeroCpfCnpj()));

                    /** Genero do conjuge **/
                    if (!AppUtil.isNullOrEmpty(dadosConjugeSantander.getCodigoSexo())) {
                        
                        PersonGenderEnum personGender = PersonGenderEnum.getGenderByAbbreviation(dadosConjugeSantander.getCodigoSexo());
                        if (AppUtil.isNullOrEmpty(personGender)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosConjugeSantander.getCodigoSexo(), AppConstants.DOMAIN_GENDER);
                        }else{
                            customerSpouse.setPersonGender(personGender);
                        }
                    }else{
                        customerSpouse.setPersonGender(null);
                    }

                    /** Data nascimento do conjuge **/
                    customerSpouse.setDateBirth(SantanderServicesUtils
                            .santanderFormatToDate(dadosConjugeSantander.getDataNascimento(), "yyyyMMdd"));

                    /** Número documento **/
                    customerSpouse.setDocumentNumber(dadosConjugeSantander.getCodigoDocumento());

                    
                    
                    /** Orgão Emissor **/
                    if (!AppUtil.isNullOrEmpty(dadosConjugeSantander.getNomeOrgaoEmissor())) {
                        EmissionOrganismEntity emissionOrganism = emissionOrganismBO
                                                .findEmissionOrganismByAcronym(dadosConjugeSantander.getNomeOrgaoEmissor());
                        if (AppUtil.isNullOrEmpty(emissionOrganism)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosConjugeSantander.getNomeOrgaoEmissor(), AppConstants.DOMAIN_EMISSION_ORGANISM);
                        }else{
                            customerSpouse.setEmissionOrganism(emissionOrganism);
                        }
                    }else{
                        customerSpouse.setEmissionOrganism(null);
                    }
                    
                    /** UF Orgão Emissor **/
                    if (!AppUtil.isNullOrEmpty(dadosConjugeSantander.getCodigoEstadoOrgaoEmissor())) {
                        ProvinceEntity province = provinceBO.findProvinceByAcronym(dadosConjugeSantander.getCodigoEstadoOrgaoEmissor());
                        if (AppUtil.isNullOrEmpty(province)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosConjugeSantander.getCodigoEstadoOrgaoEmissor(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                        }else{
                            customerSpouse.setProvince(province);
                        }
                    }else{
                        customerSpouse.setProvince(null);
                    }

                    /** Ocupação conjuge **/
                    if (!AppUtil.isNullOrEmpty(dadosConjugeSantander.getNumeroOcupacao())) {
                        OccupationEntity occupation = occupationBO.findOccupationByImportCode(SantanderServicesUtils
                                                        .extractFillZeroToLeft(dadosConjugeSantander.getNumeroOcupacao(), 3));
                        if (AppUtil.isNullOrEmpty(occupation)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosConjugeSantander.getNumeroOcupacao(), AppConstants.DOMAIN_OCCUPATION);
                        }else{
                            customerSpouse.setOccupation(occupation);
                        }
                    }else{
                        customerSpouse.setOccupation(null);
                    }

                    /** Profissão **/
                    if (!AppUtil.isNullOrEmpty(dadosConjugeSantander.getDescFuncao())) {
                        ProfessionEntity profession = professionBO.findProfessionByDescription(dadosConjugeSantander.getDescFuncao());
                        if (AppUtil.isNullOrEmpty(profession)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosConjugeSantander.getDescFuncao(), AppConstants.DOMAIN_PROFESSION);
                        }else{
                            customerSpouse.setProfession(profession);
                        }
                    }else{
                        customerSpouse.setProfession(null);
                    }

                    /** Nome empresa **/
                    customerSpouse.setEmployerName(dadosConjugeSantander.getNomeEmpresa());

                    /** Data admissão **/
                    customerSpouse.setAdmissionDate(SantanderServicesUtils
                            .santanderFormatToDate(dadosConjugeSantander.getDataAdmissao(), "yyyyMM"));

                    /** Renda mensal **/
                    customerSpouse.setIncome(
                            SantanderServicesUtils.stringToBigDecimal(dadosConjugeSantander.getValorRendaMensal()));

                    /** DDD **/
                    customerSpouse.setDdd(
                            SantanderServicesUtils.extractPhone(dadosConjugeSantander.getNumeroDddTelComercial()));

                    /** Telefone **/
                    customerSpouse.setPhone(
                            SantanderServicesUtils.extractPhone(dadosConjugeSantander.getDescricaoTelComercial()));

                    /** Ramal **/
                    customerSpouse.setExtensionLine(
                            SantanderServicesUtils.extractPhone(dadosConjugeSantander.getNumeroTelComercialRamal()));

                    // Atualiza CustomerSpouse
                    customerSpouse.setCustomer(customer);
                    customer.setCustomerSpouse(customerSpouse);
                    
                    //Inserir ou Atualizar
                    if(AppUtil.isNullOrEmpty(customerSpouse.getId())){
                        customerSpouseBO.insert(customerSpouse);
                    }else{
                        customerSpouseBO.update(customerSpouse);
                    }
                }else{
                    /* Caso o conjuge tenha sido removido no santander tb será apagado no banco */
                    if(!AppUtil.isNullOrEmpty(customer.getCustomerSpouse())){
                        CustomerSpouseEntity customerSpouseToRemove = customer.getCustomerSpouse();
                        customer.setCustomerSpouse(null);
                        customerSpouseBO.delete(customerSpouseToRemove);
                    }
                }

                /** Nome da mãe **/
                customer.setMotherName(dadosProfissionaisSantander.getNomeMae());

                /** Nome do pai **/
                customer.setFatherName(dadosProfissionaisSantander.getNomePai());

                /** Pessoa Politicamente Exposta **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroPessoaPoliticaExpo())) {
                    PoliticalExpositionEntity politicalExposition = politicalExpositionBO.findPoliticalExpositionById(
                                                            Long.valueOf(dadosProfissionaisSantander.getNumeroPessoaPoliticaExpo()));
                    if (AppUtil.isNullOrEmpty(politicalExposition)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroPessoaPoliticaExpo(), AppConstants.DOMAIN_POLITICAL_EXPOSITION);
                    }else{
                        customer.setPoliticalExposition(politicalExposition);
                    }
                }else{
                    customer.setPoliticalExposition(null);
                }

  
                if (!AppUtil.isNullOrEmpty(dadosEnderecoSantander)) {
                    /** Add telefone fixo **/
                    PhoneEntity homePhone = new PhoneEntity();
    
                    homePhone.setDdd(SantanderServicesUtils.extractPhone(dadosEnderecoSantander.getNumeroDddResidencial()));
                    homePhone.setPhone(
                            SantanderServicesUtils.extractPhone(dadosEnderecoSantander.getDescricaoTelResidencial()));
                    homePhone.setPhoneType(PhoneTypeEnum.FIXO);
                    homePhone.setCustomer(customer);
                    customer.getListPhone().add(homePhone);
    
                    phoneBO.insert(homePhone);
                    
                    
                    /* Endereco cliente */
                    AddressEntity address = customer.getAddress();
                    if (AppUtil.isNullOrEmpty(address)) {
                        address = new AddressEntity();
                    }

                    /** Cep **/
                    address.setPostCode(SantanderServicesUtils
                            .extractFillZeroToLeft(dadosEnderecoSantander.getNumeroCepResidencial(), 8));

                    /** Número Endereço **/
                    address.setNumber(dadosEnderecoSantander.getNumeroResidencial());

                    /** Endereço **/
                    address.setAddress(dadosEnderecoSantander.getDescricaoEnderecoResidencia());

                    /** Complemento **/
                    address.setComplement(dadosEnderecoSantander.getDescricaoComplementoResidencia());

                    /** Bairro **/
                    address.setNeighborhood(dadosEnderecoSantander.getNomeBairroResidencia());

                    /** Cidade **/
                    address.setCity(dadosEnderecoSantander.getNomeCidadeResidencial());

                    /** Codigo Sigla UF **/
                    if(!AppUtil.isNullOrEmpty(dadosEnderecoSantander.getCodigoSiglaUfResidencial())){
                        ProvinceEntity province = provinceBO.findProvinceByAcronym(
                                                dadosEnderecoSantander.getCodigoSiglaUfResidencial()); 
                        if (AppUtil.isNullOrEmpty(province)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosEnderecoSantander.getCodigoSiglaUfComercial(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                        }else{
                            address.setProvince(province);
                        }
                    }else{
                        address.setProvince(null);
                    }
                    // Atualiza endereco
                    customer.setAddress(address);
                    
                    if(AppUtil.isNullOrEmpty(address.getId())){
                        addressBO.insert(address);
                    }else{
                        addressBO.update(address);
                    }

                }else{
                    if(!AppUtil.isNullOrEmpty(customer.getAddress())){
                        AddressEntity adressToRemove = customer.getAddress();
                        customer.setAddress(null);
                        addressBO.delete(adressToRemove);
                    }
                }

            } else {
                // Cliente PJ
                /** PJ **/
                customer.setPersonType(PersonTypeEnum.PJ);

                /** CNPJ **/
                customer.setCpfCnpj(SantanderServicesUtils.extractCNPJ(dadosProfissionaisSantander.getNumeroCpfCnpj()));
                
                /* Caso um cliente PJ tenha dados da empresa profissionais ele será apagado */
                EmployerEntity employer = customer.getEmployer();
                if(!AppUtil.isNullOrEmpty(employer)){
                    customer.setEmployer(null);
                    employerBO.delete(employer);
                }
                if(!AppUtil.isNullOrEmpty(customer.getEmployerAddress())){
                    AddressEntity adressToRemove = customer.getEmployerAddress(); 
                    customer.setEmployerAddress(null);
                    addressBO.delete(adressToRemove);
                }
                
                /** Natureza Juridica **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getCodigoNaturezaJuridica())) {
                    LegalNatureEntity legalNature = legalNatureBO.findLegalNatureByImportCode(SantanderServicesUtils
                                                .extractFillZeroToLeft(dadosProfissionaisSantander.getCodigoNaturezaJuridica(), 3));
                    if (AppUtil.isNullOrEmpty(legalNature)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getCodigoNaturezaJuridica(), AppConstants.DOMAIN_LEGAL_NATURE);
                    }else{
                        customer.setLegalNature(legalNature);
                    }
                }else{
                    customer.setLegalNature(null);
                }

                /** Porte Empresa **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroPorteEmpresarial())) {
                    EmployerSizeEntity employerSize = employerSizeBO.findEmployerSizeById(
                                    Long.valueOf(dadosProfissionaisSantander.getNumeroPorteEmpresarial()));
                    if (AppUtil.isNullOrEmpty(employerSize)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroPorteEmpresarial(), AppConstants.DOMAIN_EMPLOYER_SIZE);
                    }else{
                        customer.setCustomerSize(employerSize);
                    }
                }else{
                    customer.setCustomerSize(null);
                }

                /** Atividade Economica **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroAtividadeEconomica())) {
                    EconomicActivityEntity economicActivity = economicActivityBO
                                    .findEconomicActivityByImportCode(SantanderServicesUtils.extractFillZeroToLeft(
                                                            dadosProfissionaisSantander.getNumeroAtividadeEconomica(), 3));
                    if (AppUtil.isNullOrEmpty(economicActivity)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroAtividadeEconomica(), AppConstants.DOMAIN_ECONOMIC_ACTIVITY );
                    }else{
                        customer.setCustomerEconomicActivity(economicActivity);
                    }
                }else{
                    customer.setCustomerEconomicActivity(null);
                }

                /** Grupo Atividade Economica **/
                if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getNumeroGrupoAtivEconomica())) {
                    IndustrialSegmentEntity industrialSegment = industrialSegmentBO.findIndustrialSegmentById(
                                                Long.valueOf(dadosProfissionaisSantander.getNumeroGrupoAtivEconomica()));
                    if (AppUtil.isNullOrEmpty(industrialSegment)) {
                        //send email
                        this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getNumeroGrupoAtivEconomica(), AppConstants.DOMAIN_INDUSTRIAL_SEGMENT);
                    }else{
                        customer.setCustomerIndustrialSegment(industrialSegment);
                    }
                }else{
                    customer.setCustomerIndustrialSegment(null);
                }
                
                /** Sede Propria **/
                customer.setBuildingOwner(
                        SantanderServicesUtils.sOrNtoBoolToBoolean(dadosProfissionaisSantander.getCodigoSedePropria()));
                
                if (!AppUtil.isNullOrEmpty(dadosEnderecoSantander)) {
                    /* Endereco cliente */
                    AddressEntity address = customer.getAddress();
                    if (AppUtil.isNullOrEmpty(address)) {
                        address = new AddressEntity();
                    }
    
                    /** Cep **/
                    address.setPostCode(SantanderServicesUtils
                            .extractFillZeroToLeft(dadosEnderecoSantander.getNumeroCepComercial(), 8));
    
                    /** Número Endereço **/
                    address.setNumber(dadosEnderecoSantander.getNumeroEnderecoComercial());
    
                    /** Endereço **/
                    address.setAddress(dadosEnderecoSantander.getDescricaoEnderecoComercial());
    
                    /** Complemento **/
                    address.setComplement(dadosEnderecoSantander.getDescricaoComplementoEndComercial());
    
                    /** Bairro **/
                    address.setNeighborhood(dadosEnderecoSantander.getNomeBairroComercial());
    
                    /** Cidade **/
                    address.setCity(dadosEnderecoSantander.getNomeCidadeComercial());
    
                    /** Codigo Sigla UF **/
                    if(!AppUtil.isNullOrEmpty(dadosEnderecoSantander.getCodigoSiglaUfComercial())){
                        ProvinceEntity province = provinceBO.findProvinceByAcronym(
                                                dadosEnderecoSantander.getCodigoSiglaUfComercial()); 
                        if (AppUtil.isNullOrEmpty(province)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosEnderecoSantander.getCodigoSiglaUfComercial(), AppConstants.DOMAIN_ACRONYM_PROVINCE);
                        }else{
                            address.setProvince(province);
                        }
                    }else{
                        address.setProvince(null);
                    }
                    // Atualiza endereco
                    customer.setAddress(address);
                    
                    if(AppUtil.isNullOrEmpty(address.getId())){
                        addressBO.insert(address);
                    }else{
                        addressBO.update(address);
                    }
    
                }else{
                    if(!AppUtil.isNullOrEmpty(customer.getAddress())){
                        AddressEntity adressToRemove = customer.getAddress();
                        customer.setAddress(null);
                        addressBO.delete(adressToRemove);
                    }
                }
            }
           
            //Dados Comum Customer
            if (!AppUtil.isNullOrEmpty(dadosEnderecoSantander)) {
            
                /** Add telefone comercial **/
                PhoneEntity comercialPhone = new PhoneEntity();
                comercialPhone
                        .setDdd(SantanderServicesUtils.extractPhone(dadosEnderecoSantander.getNumeroDddTelComercial()));
                comercialPhone.setPhone(
                        SantanderServicesUtils.extractPhone(dadosEnderecoSantander.getDescricaoTelComercial()));
                comercialPhone.setPhoneType(PhoneTypeEnum.COMERCIAL);
                comercialPhone.setExtensionLine(
                        SantanderServicesUtils.extractPhone(dadosEnderecoSantander.getNumeroTelComercialRamal()));
                comercialPhone.setCustomer(customer);
                customer.getListPhone().add(comercialPhone);
                phoneBO.insert(comercialPhone);
                
                /** Add telefone celular **/
                PhoneEntity cellPhone = new PhoneEntity();
    
                cellPhone.setDdd(SantanderServicesUtils.extractPhone(dadosEnderecoSantander.getNumeroDddCel()));
                cellPhone.setPhone(SantanderServicesUtils.extractPhone(dadosEnderecoSantander.getCodigotelCel()));
                cellPhone.setPhoneType(PhoneTypeEnum.CELULAR);
                cellPhone.setCustomer(customer);
                customer.getListPhone().add(cellPhone);
    
                phoneBO.insert(cellPhone);
    
            }

            /** Cliente desde **/
            customer.setAddressSince(SantanderServicesUtils.convertDateSinceOf(
                    dadosEnderecoSantander.getDataMesResideDesde(), dadosEnderecoSantander.getDataAnoResideDesde()));

            /** Tipo Residencia **/
            if (!AppUtil.isNullOrEmpty(dadosEnderecoSantander.getNumeroTipoResidencia())) {
                ResidenceTypeEntity residenceType = residenceTypeBO
                            .findResidenceTypeById(Long.valueOf(dadosEnderecoSantander.getNumeroTipoResidencia()));
                if (AppUtil.isNullOrEmpty(residenceType)) {
                    //send email
                    this.sendMailDomainNotRecognized(dadosEnderecoSantander.getNumeroTipoResidencia(), AppConstants.DOMAIN_RESIDENCE_TYPE);
                }else{
                    customer.setResidenceType(residenceType);    
                }
            }else{
                customer.setResidenceType(null);
            }

            /** Tipo Telefone **/
            if(!AppUtil.isNullOrEmpty(dadosEnderecoSantander.getNumeroTipoTelefResiden())){
                if (AppConstants.CHANGE_PHONE_TYPE_TO_MESSAGE.equals(dadosEnderecoSantander.getNumeroTipoTelefResiden())) {
                    customer.setPersonalPhoneType(PersonalPhoneTypeEnum.RECADO);
                } else {
                    customer.setPersonalPhoneType(PersonalPhoneTypeEnum
                            .getByImportCode(Integer.valueOf(dadosEnderecoSantander.getNumeroTipoTelefResiden())));
                }
            }else{
                customer.setPersonalPhoneType(null);
            }

            /** Endereco Correspondência **/
            if (!AppUtil.isNullOrEmpty(dadosEnderecoSantander.getCodigoEnderecoCorrespondencia())) {
                AddressTypeEntity addressType = addressTypeBO
                        .findAddressTypeByImportCode(dadosEnderecoSantander.getCodigoEnderecoCorrespondencia());
                if (AppUtil.isNullOrEmpty(addressType)) {
                    //send email
                    this.sendMailDomainNotRecognized(dadosEnderecoSantander.getCodigoEnderecoCorrespondencia(), AppConstants.DOMAIN_MAILING_ADDRESS_TYPE);
                }else{
                    customer.setMailingAddressType(addressType);  
                }
            }else{
                customer.setMailingAddressType(null);
            }
            
            
            /** Email **/
            customer.setEmail(dadosEnderecoSantander.getDescricaoEnderecoEmail());

            /** Nome cliente **/
            customer.setNameClient(dadosProfissionaisSantander.getNomeCompleto());

            /** Nacionalidade **/
            if (!AppUtil.isNullOrEmpty(dadosProfissionaisSantander.getCodigoNacionalidade())) {
                CountryEntity country = 
                        countryBO.findCountryByAcronym(dadosProfissionaisSantander.getCodigoNacionalidade());
                if (AppUtil.isNullOrEmpty(country)) {
                    //send email
                    this.sendMailDomainNotRecognized(dadosProfissionaisSantander.getCodigoNacionalidade(), AppConstants.DOMAIN_COUNTRY);
                }else{
                    customer.setCountry(country);
                }
            }else{
                customer.setCountry(null);
            }

            /** Cidade de nascimento **/
            customer.setNaturalness(dadosProfissionaisSantander.getDescricaoNaturalidade());

            /** Valor Renda Mensal do Cliente **/
            customer.setIncome(
                    SantanderServicesUtils.stringToBigDecimal(dadosProfissionaisSantander.getValorRendaMensal()));

            /** Data de Nascimento ou Abertura **/
            customer.setDateBirth(SantanderServicesUtils
                    .santanderFormatToDate(dadosProfissionaisSantander.getDataNascimento(), "yyyyMMdd"));

            // Limpa lista de referencia
            customer.setListCustomerReference(new HashSet<CustomerReferenceEntity>());
            customerReferenceBO.deleteAllCustomerReference(customer);

            if(!AppUtil.isNullOrEmpty(dadosReferenciaPessoalBancariaSantander)){
                
                
                /** Referencia Pessoal 1 **/
                if(!AppUtil.isNullOrEmpty(dadosReferenciaPessoalBancariaSantander.getNomeRefer1()) 
                        || !AppUtil.isNullOrEmptyOrZero(dadosReferenciaPessoalBancariaSantander.getNumeroDddRefer1())
                        || !AppUtil.isNullOrEmptyOrZero(dadosReferenciaPessoalBancariaSantander.getDescricaoTelefoneRefer1())){
                    CustomerReferenceEntity customerReference1 = new CustomerReferenceEntity();
                    customerReference1.setDescription(dadosReferenciaPessoalBancariaSantander.getNomeRefer1());
                    customerReference1.setDdd(
                            SantanderServicesUtils.extractPhone(dadosReferenciaPessoalBancariaSantander.getNumeroDddRefer1()));
                    customerReference1.setPhone(SantanderServicesUtils
                            .extractPhone(dadosReferenciaPessoalBancariaSantander.getDescricaoTelefoneRefer1()));
                    customerReference1.setCustomer(customer);
                    customer.getListCustomerReference().add(customerReference1);
                    customerReferenceBO.insert(customerReference1);
                }
                
                /** Referencia Pessoal 2 **/
                if(!AppUtil.isNullOrEmpty(dadosReferenciaPessoalBancariaSantander.getNomeRefer2()) 
                        || !AppUtil.isNullOrEmptyOrZero(dadosReferenciaPessoalBancariaSantander.getNumeroDddRefer2())
                        || !AppUtil.isNullOrEmptyOrZero(dadosReferenciaPessoalBancariaSantander.getDescricaoTelefoneRefer2())){

                    CustomerReferenceEntity customerReference2 = new CustomerReferenceEntity();
                    customerReference2.setDescription(dadosReferenciaPessoalBancariaSantander.getNomeRefer2());
                    customerReference2.setDdd(
                            SantanderServicesUtils.extractPhone(dadosReferenciaPessoalBancariaSantander.getNumeroDddRefer2()));
                    customerReference2.setPhone(SantanderServicesUtils
                            .extractPhone(dadosReferenciaPessoalBancariaSantander.getDescricaoTelefoneRefer2()));
                    customerReference2.setCustomer(customer);
                    customer.getListCustomerReference().add(customerReference2);
                    customerReferenceBO.insert(customerReference2);
                }
                
                /* Remove o banco caso o numero da agencia e conta for diferente de zero ou Null*/
                if(!AppUtil.isNullOrEmptyOrZero(dadosReferenciaPessoalBancariaSantander.getNumeroAgencia())
                        || !AppUtil.isNullOrEmptyOrZero(dadosReferenciaPessoalBancariaSantander.getNumeroContaCorrente())){

                    //Dados Bancarios
                    BankDetailEntity bankDetail = customer.getBankDetail();
                    if (AppUtil.isNullOrEmpty(bankDetail)) {
                        bankDetail = new BankDetailEntity();
                    }
    
                    /** Codigo Banco **/
                    if (!AppUtil.isNullOrEmpty(dadosReferenciaPessoalBancariaSantander.getNumeroBanco())) {
                       
                        BankEntity bank = bankBO.findBankByImportCode(SantanderServicesUtils
                                .extractImportCodeBank(dadosReferenciaPessoalBancariaSantander.getNumeroBanco()));
                        if (AppUtil.isNullOrEmpty(bank)) {
                            //send email
                            this.sendMailDomainNotRecognized(dadosReferenciaPessoalBancariaSantander.getNumeroBanco(), AppConstants.DOMAIN_BANK);
                        }else{
                            bankDetail.setBank(bank);
                        }
                    }else{
                        bankDetail.setBank(null);
                    }
    
                    /** Numero Agencia **/
                    bankDetail.setBranch(dadosReferenciaPessoalBancariaSantander.getNumeroAgencia());
                    /** Digito Agencia **/
                    bankDetail.setBranchDigit(dadosReferenciaPessoalBancariaSantander.getCodigoDigitoAgencia());
                    /** Numero Conta **/
                    bankDetail.setAccount(dadosReferenciaPessoalBancariaSantander.getNumeroContaCorrente());
                    /** Digito Conta **/
                    bankDetail.setAccountDigit(dadosReferenciaPessoalBancariaSantander.getCodigoDigitoContaCorrente());
                    /** Data Abertura **/
                    bankDetail.setAccountOpeningDate(SantanderServicesUtils.convertDateSinceOf(
                                                dadosReferenciaPessoalBancariaSantander.getNumeroMesClienteDesde(),
                                                dadosReferenciaPessoalBancariaSantander.getNumeroAnoClienteDesde()));
                    /** DDD **/
                    bankDetail.setDdd(SantanderServicesUtils
                            .extractPhone(dadosReferenciaPessoalBancariaSantander.getNumeroDddTelefoneBanco()));
                    /** Telefone **/
                    bankDetail.setPhone(SantanderServicesUtils
                            .extractPhone(dadosReferenciaPessoalBancariaSantander.getDescricaoTelefoneBanco()));
                    /** Tipo da Conta **/
                    bankDetail.setAccountType(AccountTypeEnum
                            .bySantanderAcronym(dadosReferenciaPessoalBancariaSantander.getCodigoTipoContaBancaria()));
                    bankDetail.setCustomer(customer);
    
                    // Atualiza banco
                    customer.setBankDetail(bankDetail);
                    
                    //Inclui/Atualiza banco
                    if(AppUtil.isNullOrEmpty(bankDetail.getId())){
                        bankDetailBO.insert(bankDetail);
                    }else{
                        bankDetailBO.update(bankDetail);
                    }
                }else{
                    if(!AppUtil.isNullOrEmpty(customer.getBankDetail())){
                        BankDetailEntity bankDetailToRemove = customer.getBankDetail();
                        customer.setBankDetail(null);
                        bankDetailBO.delete(bankDetailToRemove);
                    }
                }
                
            }else{
                if(!AppUtil.isNullOrEmpty(customer.getBankDetail())){
                    BankDetailEntity bankDetailToRemove = customer.getBankDetail();
                    customer.setBankDetail(null);
                    bankDetailBO.delete(bankDetailToRemove);
                }
            }

            // Atualiza o customer
            customerBO.update(customer);

            LOGGER.debug(" >> END extractCustomer ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
    /**
     * 
     * @param dadosSantander
     * @param proposal
     * @throws UnexpectedException
     */
    private void extractInstalment(ConsultaPropostaDetalhadaAFC dadosSantander, ProposalEntity proposal)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT extractInstalment ");
            DadosFlex dadosFlexSantander = dadosSantander.getDadosFlex();
            DadosBasicosProposta dadosBasicosSantander = dadosSantander.getDadosBasicosProposta();

            // Data de vencimento da 1º parcela
            Date expireDate = SantanderServicesUtils.santanderFormatToDate(
                    dadosBasicosSantander.getDataVencimento1(), "yyyyMMdd");
            // Qtde prestações
            Integer instalmentsQt = Integer.valueOf(dadosBasicosSantander.getNumeroQuantidadePrestacoes());

            //Limpa as parcelas
            proposal.setListInstalment(new HashSet<InstalmentEntity>());
            instalmentBO.deleteAllInstalmentProposal(proposal);

            List<InstalmentEntity> instalments = new ArrayList<>();
            
            /** Atualiza as parcelas **/
            Calendar calInstalment = Calendar.getInstance();
            calInstalment.setTime(expireDate);
            
            List<Date> dates = GeneralUtils.calculateInstalmentDate(calInstalment, instalmentsQt);
            
            if (AppConstants.SANTANDER_TYPE_FINANCING_CDC_OR_CDCFLEX.equalsIgnoreCase(dadosBasicosSantander.getNumeroIdProduto())) {
                if (dadosFlexSantander.getCdPctFlex() != null && 0 != Integer.valueOf(dadosFlexSantander.getCdPctFlex())) {
                    /** Tipo financiamento CDC-FLEX **/
                    proposal.setFinanceType(financeTypeBO.findById(AppConstants.TYPE_FINANCING_CDC_FLEX));

                    /** Atualiza as parcelas **/
                    for (int i = 0; i < dadosFlexSantander.getParcelasFlex().size(); i++) {
                        Parcelas parcela = dadosFlexSantander.getParcelasFlex().get(i);
                        InstalmentEntity instalment = new InstalmentEntity();
                        instalment.setAmount(SantanderServicesUtils.stringToBigDecimal(parcela.getValor()));
                        instalment.setInstalment(Integer.valueOf(parcela.getNumero()));
                        instalment.setInstalmentDate(SantanderServicesUtils.santanderFormatToDate(parcela.getDataVenc(), "yyyyMMdd"));
                        instalment.setProposal(proposal);
                        proposal.getListInstalment().add(instalment);
                        instalments.add(instalment);
                        
                        if(AppUtil.isNullOrEmpty(instalment.getIncludeDate())){
                            instalment.setInstalmentDate(dates.get(i));
                        }
                        instalmentBO.insert(instalment);

                    }

                } else {
                    /** Tipo financiamento CDC **/
                    proposal.setFinanceType(financeTypeBO.findById(AppConstants.TYPE_FINANCING_CDC));

                    for (int i = 1; i <= instalmentsQt; i++) {
                        InstalmentEntity instalment = new InstalmentEntity();
                        instalment.setAmount(SantanderServicesUtils.stringToBigDecimal(dadosBasicosSantander.getValorPrestacao()));
                        instalment.setInstalment(i);
                        instalment.setProposal(proposal);
                        proposal.getListInstalment().add(instalment);
                        instalments.add(instalment);
                        
                        if(AppUtil.isNullOrEmpty(instalment.getIncludeDate())){
                            instalment.setInstalmentDate(dates.get(i-1));
                        }
                        instalmentBO.insert(instalment);

                    }
                }
            } else {
                /** Tipo financiamento LEASING **/
                proposal.setFinanceType(financeTypeBO.findById(AppConstants.TYPE_FINANCING_LEASING));

                for (int i = 1; i <= instalmentsQt; i++) {
                    InstalmentEntity instalment = new InstalmentEntity();
                    instalment.setAmount(SantanderServicesUtils.stringToBigDecimal(dadosBasicosSantander.getValorPrestacao()));
                    instalment.setInstalment(i);
                    instalment.setProposal(proposal);
                    proposal.getListInstalment().add(instalment);
                    instalments.add(instalment);
                    
                    if(AppUtil.isNullOrEmpty(instalment.getIncludeDate())){
                        instalment.setInstalmentDate(dates.get(i-1));
                    }
                    instalmentBO.insert(instalment);

                }
            }

            //TODO validar com o Maycu - PASTRE
            /** Atualiza as parcelas **/
            /*
            Calendar calInstalment = Calendar.getInstance();
            calInstalment.setTime(expireDate);
            
            List<Date> dates = GeneralUtils.calculateInstalmentDate(calInstalment, instalments.size());
            for (int i = 0; i < instalments.size(); i++) {
                InstalmentEntity instalment = instalments.get(i);
                
                if(AppUtil.isNullOrEmpty(instalment.getIncludeDate())){
                    instalment.setInstalmentDate(dates.get(i));
                }
                
                instalmentBO.insert(instalment);
            }*/
            
            /** Qt de parcelas (Duração) **/
            proposal.setInstalmentQuantity(instalmentsQt);
            LOGGER.debug(" >> END extractInstalment ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    /**
     * 
     * @param domain
     * @param value
     * @throws UnexpectedException
     */
    private void sendMailDomainNotRecognized(String value, String domain) throws UnexpectedException{
       try{ 
            LOGGER.debug(" >> INIT sendMailDomainNotRecognized ");
            String body = String.format(AppConstants.DOMAIN_NOT_FOUND_MSG_ERROR, value, domain);
            
            MailDTO mailDto = new MailDTO();
            mailDto.setSubject(AppConstants.DOMAIN_NOT_FOUND_SUBJ_ERROR);
            mailDto.setContent(body);
            List<String> emails = new ArrayList<String>();
            emails.add(paramBO.findParam(ParamEnum.EMAIL_RCI_PARAM_CODE).getParamLabel());
            mailDto.setDestinationList(emails);
            mailDto.setEmailEnum(EmailEnum.RCI);
            
            
            sendMailBO.sendMessage(mailDto);
            LOGGER.debug(" >> END sendMailDomainNotRecognized ");
       } catch (UnexpectedException e) {
           throw e;
       } catch (Exception e) {
           LOGGER.error(AppConstants.ERROR, e);
           throw new UnexpectedException(e);
       }
    }
    
    
    
    public void insertDossierTransfStructure(DossierTransfFilterDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT insertDossierTransfStructure ");
            DossierDAO dao = daoFactory(DossierDAO.class);
            DossierEntity dossier = dao.find(DossierEntity.class, dto.getDossierId());
            StructureEntity structureFrom = structureBO.findById(dto.getStructureIdFrom());
            StructureEntity structureTo = structureBO.findById(dto.getStructureIdTo());
            PersonEntity personFrom = personBO.findById(dto.getPersonIdFrom());
            PersonEntity personTo = personBO.findById(dto.getPersonIdTo());

            DossierTransferStructureEntity entity = new DossierTransferStructureEntity();
            entity.setDossier(dossier);
            entity.setSalesmanFrom(personFrom);
            entity.setSalesmanTo(personTo);
            entity.setStructureFrom(structureFrom);
            entity.setStructureTo(structureTo);
            entity.setIncludeDate(dto.getIncludeDate());
            entity.setUser(dto.getUserEntity());

            dao.save(entity);

            dossier.setStructureChanged(false);
            dossier.setSalesman(personTo);
            dossier.setStructure(structureTo);
            dao.update(dossier);

            LOGGER.debug(" >> END insertDossierTransfStructure ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
