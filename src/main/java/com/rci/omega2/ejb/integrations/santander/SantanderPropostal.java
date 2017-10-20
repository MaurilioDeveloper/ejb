package com.rci.omega2.ejb.integrations.santander;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.altec.bsbr.app.afc.webservice.impl.DadosAdpClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.DadosAdpClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.DadosPessoaisClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.DadosPessoaisClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.altec.bsbr.app.afc.webservice.impl.InsereEnderecoClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereEnderecoClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InsereGarantiaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereGarantiaClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InserePropostaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InserePropostaClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InsereReferenciaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereReferenciaClientResponse;
import com.rci.omega2.ejb.bo.ConfigFileBO;
import com.rci.omega2.ejb.dto.BaseSantanderProposalDTO;
import com.rci.omega2.ejb.dto.SantanderProposalStep1DTO;
import com.rci.omega2.ejb.dto.SantanderProposalStep2DTO;
import com.rci.omega2.ejb.dto.SantanderProposalStep3DTO;
import com.rci.omega2.ejb.dto.SantanderProposalStep4DTO;
import com.rci.omega2.ejb.dto.SantanderProposalStep5DTO;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;

/**
 * 
 * @author Ricardo Zandonai (rzandonai@stefanini.com)
 *
 */
public class SantanderPropostal {

    public InserePropostaClientResponse sendStep1(SantanderProposalStep1DTO simulationDTO)
            throws MalformedURLException {
        ApiCalls apiCall = new ApiCalls();
        ApiCallsUtils acu = new ApiCallsUtils();

        InserePropostaClientRequest envio = new InserePropostaClientRequest();

        String simTable = simulationDTO.getSimulationTable();
        // Quando não informada a tabela utilizar a padrão 00000
        if (simTable == null) {
            simTable = "00000";
        }

        envio.setNumeroTabelaFinanciamento(simTable);
            
        String finTypeCode = acu.getDomCodeByDescription("35", simulationDTO.getTypeOfFinancing());
        // Envia o codigo do produto
        envio.setNumeroIdProduto(finTypeCode);

        String currencyType = acu.getDomCodeByDescription("32", simulationDTO.getCurrencyType());
        envio.setCodigoTipoMoeda(currencyType);

        // Valor total do ben
        envio.setValorBem(Utils.bigToString(simulationDTO.getTotalValue(), 2));
        // Valor da entrada
        envio.setValorEntrada(Utils.bigToString(simulationDTO.getInputValue(), 2));
        // valor de parcelas
        envio.setValorPrestacao(Utils.bigToString(simulationDTO.getParcelsValue(), 2));
        envio.setValorFinanciamento(
                Utils.bigToString(simulationDTO.getTotalValue().subtract(simulationDTO.getInputValue()), 2));
        envio.setNumeroQuantidadePrestacoes(simulationDTO.getParcelsNumber().toString());

        String modalityOfFinancing = acu.getDomCodeByDescription("17", simulationDTO.getModalityOfFinancing());
        // Codigo da modalidade de financiamento
        envio.setCodigoModalidade(modalityOfFinancing);

        envio.setCodigoFormaPagamento("CA");

        // Dia do primeiro vencimento
        envio.setDataVencimento1(simulationDTO.getFirstPaymentDate());

        envio.setIsencaoTC((simulationDTO.getTaxTac() ? "N" : "S"));
        envio.setIsencaoTAB((simulationDTO.getTaxTab() ? "N" : "S"));

        envio.setNumeroIntermediario(getIntermediateNumber());

        InserePropostaClientResponse retorno = apiCall.sendProposalStep1(envio);

        return retorno;

    }

    public DadosPessoaisClientResponse sendStep2(SantanderProposalStep2DTO simulationDTO) throws MalformedURLException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        ApiCalls apiCall = new ApiCalls();
        ApiCallsUtils acu = new ApiCallsUtils();

        DadosPessoaisClientRequest envio = new DadosPessoaisClientRequest();
        envio.setNumeroPropostaAdp(simulationDTO.getNumberPropostalAdp());

        envio.setNumeroTipoVinculoPart(simulationDTO.getLinkTypeNumber());
        envio.setCodigoNaturezaJuridica(simulationDTO.getLegalNature());
        envio.setNumeroIntermediario(getIntermediateNumber());

        // tipo de pessoa
        String tipoPessoa = acu.getDomCodeByDescription("34", simulationDTO.getPersonType().getAbbreviation());
        envio.setCodigoTipoPessoa(tipoPessoa);
        // Achar o código do cliente
        envio.setNumeroClienteRelac(simulationDTO.getClientCodeRelashionship());
        envio.setNomeCompleto(simulationDTO.getClientName());
        envio.setNumeroCpfCnpj(simulationDTO.getCpfCnpj());
        // DD/MM/AAAA
        if (simulationDTO.getBirthDate() != null) {
            envio.setDataNascimento(df.format(simulationDTO.getBirthDate()));
        }
        // Sexo, Documento, Estado Civil, Nome da mãe, Nome do Pai
        if (simulationDTO.getGender() != null) {
            envio.setCodigoSexo(simulationDTO.getGender().getAbbreviation());
        }
        String documentTypeCode = acu.getDomCodeByDescription("31", simulationDTO.getDocumentType());
        envio.setNumeroTipoDocumento(documentTypeCode);
        envio.setCodigoDocumento(simulationDTO.getPersonalDocumentNumber());
        envio.setNomeOrgaoEmissor(simulationDTO.getDocumentIssuer());
        if (simulationDTO.getDocumentEmitDate() != null) {
            envio.setDataEmissaoDocumento(df.format(simulationDTO.getDocumentEmitDate()));
        }
        if (simulationDTO.getDocumentValidityDate() != null) {
            envio.setDataValidadeDocumento(df.format(simulationDTO.getDocumentValidityDate()));
        }
        String issuranceState = acu.getDomCodeByDescription("39", simulationDTO.getIssuerState());
        envio.setCodigoEstadoOrgaoEmissor(issuranceState);
        String documentCountry = acu.getDomCodeByDescription("22", simulationDTO.getDocumentCountry());
        envio.setCodigoPaisDocumento(documentCountry);
        String maritalStatus = acu.getDomCodeByDescription("07", simulationDTO.getMaritalStatus());
        envio.setNumeroEstadoCivil(maritalStatus);
        String nacionalidade = acu.getDomCodeByDescription("19", simulationDTO.getNationality());
        envio.setCodigoNacionalidade(nacionalidade);
        envio.setNomeMae(simulationDTO.getMothersName());
        envio.setNomePai(simulationDTO.getFathersName());

        // Ocupação
        String ocupationCode = acu.getDomCodeByDescription("21", simulationDTO.getOcupation());
        envio.setNumeroOcupacao(ocupationCode);

        // Proponente
        String degreeOfEducation = acu.getDomCodeByDescription("10", simulationDTO.getDegreeEducation());
        envio.setNumeroInstrucao(degreeOfEducation);

        String santanderFunctionary = acu.getDomCodeByDescription("40", simulationDTO.getSantanderFunctionaryType());
        envio.setIndicativoFuncSantander(santanderFunctionary);

        String handcappedCode = acu.getDomCodeByBoolean("04", simulationDTO.isHandcapped());
        envio.setIndicativoDeficienteFisico(handcappedCode);

        envio.setNumeroCnpjEmpresa(simulationDTO.getCnpjNumber());

        envio.setValorPatrimonio(Utils.bigToString(simulationDTO.getPatrimonyValue(), 2));

        String politicalyExposePersonCode = acu.getDomCodeByDescription("23",
                simulationDTO.getPoliticallyExposedPerson());
        envio.setNumeroPessoaPoliticaExpo(politicalyExposePersonCode);

        if (simulationDTO.getAdmissionDate() != null) {
            envio.setDataAdmissao(df.format(simulationDTO.getAdmissionDate()));
        }

        String incomeTypeCode = acu.getDomCodeByDescription("36", simulationDTO.getIncomeType());
        envio.setNumeroRenda(incomeTypeCode);

        String incomeReceiptIncomeCode = acu.getDomCodeByDescription("29", simulationDTO.getIncomeReceiptType());
        envio.setNumeroComprovanteRenda(incomeReceiptIncomeCode);

        // Se não for Estrangeiro
        if (simulationDTO.getDocumentValidityDate() != null) {
            envio.setDataValidadeDocumento(df.format(simulationDTO.getDocumentValidityDate()));
        }

        String stateOfBirth = acu.getDomCodeByDescription("39", simulationDTO.getStateOfNature());
        envio.setCodigoEstadoNaturalidade(stateOfBirth);
        envio.setDescricaoNaturalidade(simulationDTO.getPlaceOfBirth());

        envio.setDescricaoProfissao(simulationDTO.getProfessionDescription());
        envio.setNumeroProfissao(simulationDTO.getProfession());

        DominioDTO domEconimic = acu.getListEconomicActivity(simulationDTO.getEconomicActivityName());
        if (domEconimic != null) {
            envio.setNumeroAtividadeEconomica(domEconimic.getCodigo());
        }

        String economicGroup = acu.getDomCodeByDescription("12", simulationDTO.getEconomicActivityGroup());
        envio.setNumeroGrupoAtivEconomica(economicGroup);

        String size = acu.getDomCodeByDescription("24", simulationDTO.getCompanySize());
        envio.setNumeroPorteEmpresarial(size);

        String ownHead = acu.getDomCodeByBoolean("26", simulationDTO.isOwnHeadquarters());
        envio.setCodigoSedePropria(ownHead);

        String relType = acu.getRelashioshipType(simulationDTO.getRelationshipDescription());
        envio.setCodigoTipoRelacionamento(relType);

        envio.setValorRendaMensal(Utils.bigToString(simulationDTO.getMonthlyIncomeValue(), 2));

        envio.setValorOutrasRendas(Utils.bigToString(simulationDTO.getAnotherIncomeValue(), 2));

        envio.setNumeroDddOutrasRendas(simulationDTO.getDddAnotherIncome());
        envio.setDescricaoTelOutrasRendas(simulationDTO.getTelAnotherIncome());

        String degreeOfKinship = acu.getDomCodeByDescription("11", simulationDTO.getDegreeKinship());
        envio.setNumeroIndicativoGrauParentes(degreeOfKinship);

        envio.setNomeEmpresa(simulationDTO.getCompanyName());

        DadosPessoaisClientResponse result = apiCall.sendProposalStep2(envio);

        return result;
    }

    public InsereEnderecoClientResponse sendTep3(SantanderProposalStep3DTO simulationDTO) throws MalformedURLException {
        InsereEnderecoClientRequest envio = new InsereEnderecoClientRequest();
        ApiCalls apiCall = new ApiCalls();
        ApiCallsUtils acu = new ApiCallsUtils();

        envio.setNumeroClienteInterno(simulationDTO.getClientCode());
        envio.setNumeroPropostaAdp(simulationDTO.getNumberPropostalAdp());
        envio.setDescricaoEnderecoResidencia(simulationDTO.getHomeAddress());
        envio.setNumeroResidencial(simulationDTO.getHomeNumberAddress());
        envio.setNomeBairroResidencial(simulationDTO.getHomeNeighborhood());
        envio.setNumeroCepResidencial(simulationDTO.getHomeZipCode());

        // PF
        if (simulationDTO.getPersonType().equals(PersonTypeEnum.PF)) {
            envio.setNomeCidadeResidencial(simulationDTO.getHomeCityName());
            envio.setNumeroDddResidencial(simulationDTO.getHomePhoneDdd());
            envio.setNumeroResidencial(simulationDTO.getHomePhone());

            String homeStateOfNature = acu.getDomCodeByDescription("39", simulationDTO.getHomeStateOfNature());
            envio.setCodigoSiglaUfResidencial(homeStateOfNature);

            String homeCountry = acu.getDomCodeByDescription("22", simulationDTO.getHomeCountry());
            envio.setCodigoPaisResidencial(homeCountry);
        }
        envio.setNumeroDddCel(simulationDTO.getCelphoneDdd());
        envio.setCodigotelCel(simulationDTO.getCelphoneNumber());
        envio.setDescricaoEnderecoEmail(simulationDTO.getEmail());

        envio.setNumeroEnderecoComercial(simulationDTO.getComercialNumberAddress());
        envio.setDescricaoEnderecoComercial(simulationDTO.getComercialAddress());
        envio.setNomeCidadeComercial(simulationDTO.getComercialCityName());
        envio.setNomeBairroComercial(simulationDTO.getComercialNeighborhood());
        envio.setNumeroCepComercial(simulationDTO.getComercialZipCode());

        String comercialStateOfNature = acu.getDomCodeByDescription("39", simulationDTO.getComercialStateOfNature());
        envio.setCodigoSiglaUfComercial(comercialStateOfNature);

        InsereEnderecoClientResponse result = apiCall.sendProposalSte3(envio);

        return result;
    }

    public InsereReferenciaClientResponse sendStep4(SantanderProposalStep4DTO simulationDTO)
            throws MalformedURLException {
        ApiCalls apiCall = new ApiCalls();
        ApiCallsUtils acu = new ApiCallsUtils();
        InsereReferenciaClientRequest envio = new InsereReferenciaClientRequest();

        envio.setNumeroPropostaAdp(simulationDTO.getNumberPropostalAdp());
        envio.setNumeroClienteInterno(simulationDTO.getClientCodeRelashionship());
        String bankCode = acu.getDomCodeByDescription("20", simulationDTO.getBank());
        envio.setNumeroBanco(bankCode);
        envio.setNumeroAgencia(simulationDTO.getAgency());
        envio.setCodigoDigitoAgencia(simulationDTO.getAgencyDigit().toString());
        envio.setNumeroContaCorrente(simulationDTO.getAccount());
        envio.setCodigoDigitoContaCorrente(simulationDTO.getAccountDigit().toString());
        envio.setNomeRefer1(simulationDTO.getFirstRefName());
        envio.setNumeroDddRefer1(simulationDTO.getFirstRefDdd());
        envio.setDescricaoTelefoneRefer1(simulationDTO.getFirstRefPhone());
        envio.setDescricaoEndRefer1(simulationDTO.getFirstRefAddress());
        envio.setNomeRefer2(simulationDTO.getSecondRefName());
        envio.setNumeroDddRefer2(simulationDTO.getSecondRefDdd());
        envio.setDescricaoTelefoneRefer2(simulationDTO.getSecondRefPhone());
        envio.setDescricaoEndRefer2(simulationDTO.getFirstRefAddress());
        envio.setNumeroAnoClienteDesde(simulationDTO.getAccountYear().toString());
        envio.setNumeroMesClienteDesde(simulationDTO.getAccountMonth());
        String accountType = acu.getDomCodeByDescription("30", simulationDTO.getAccountType());
        envio.setCodigoTipoContaBancaria(accountType);
        envio.setNumeroDddTelefoneBanco(simulationDTO.getBankDdd());
        envio.setDescricaoTelefoneBanco(simulationDTO.getBankPhone());

        InsereReferenciaClientResponse result = apiCall.sendProposalStep4(envio);
        return result;
    }

    public InsereGarantiaClientResponse sendStep5(SantanderProposalStep5DTO simulationDTO)
            throws MalformedURLException {
        ApiCalls apiCall = new ApiCalls();
        ApiCallsUtils acu = new ApiCallsUtils();

        InsereGarantiaClientRequest envio = new InsereGarantiaClientRequest();
        envio.setNumeroIntermediario(getIntermediateNumber());
        envio.setNumeroProposta(simulationDTO.getNumberPropostalAdp());
        envio.setCodigoGarantia("0001");
        envio.setCodigoEstadoLicenciamento(simulationDTO.getStateCodeLicensing());
        envio.setCodigoTipoCombustivel(simulationDTO.getFuelType());
        String isAdapted = acu.getDomCodeByBoolean("04", simulationDTO.isAdapted());
        envio.setIndicativoAdaptado(isAdapted);
        envio.setIndicativoProcVeiculo(simulationDTO.getOriginIndicative());
        String isTaxi = acu.getDomCodeByBoolean("04", simulationDTO.isTaxi());
        envio.setIndicativoTaxi(isTaxi);
        String isZeroKm = acu.getDomCodeByBoolean("04", simulationDTO.isZeroKm());
        envio.setIndicativoZeroKm(isZeroKm);
        envio.setNumeroAnoFabricacao(simulationDTO.getManufactureYear().toString());
        envio.setNumeroAnoModelo(simulationDTO.getModelYear().toString());
        String brandCode = acu.getBrandByNameAndVehicleType(simulationDTO.getBrandDescription(), simulationDTO.getSaleType());
        String modelCode = acu.getModelByBrandNameAndType(brandCode, simulationDTO.getSaleType(), simulationDTO.getModelDescription());
        
        envio.setNumeroTabMarca(brandCode);
        envio.setNumeroTabModelo(modelCode);
        
        envio.setCodigoEstadoPlaca(simulationDTO.getStatePlaque());
        envio.setCodigoRenavam(simulationDTO.getRenavam());
        envio.setDescricaoChassi(simulationDTO.getChassis());
        envio.setDescricaoCor(simulationDTO.getColor());
        envio.setDescricaoMarca(simulationDTO.getBrandDescription());
        envio.setDescricaoModelo(simulationDTO.getModelDescription());
        envio.setDescricaoPlaca(simulationDTO.getPlaque());

        envio.setCodigoObjFinanciado(simulationDTO.getObjectCode());

        InsereGarantiaClientResponse result = apiCall.sendProposalStep5(envio);
        return result;
    }

    public DadosAdpClientResponse sendStep6(BaseSantanderProposalDTO dto) throws MalformedURLException {
        ApiCalls apiCall = new ApiCalls();
        DadosAdpClientRequest envio = new DadosAdpClientRequest();
        envio.setNumeroPropostaAdp(dto.getNumberPropostalAdp());
        envio.setNumeroIntermediario(getIntermediateNumber());
        DadosAdpClientResponse result = apiCall.sendProposalStep6(envio);
        return result;
    }

    public static String getIntermediateNumber() {
        ConfigFileBO configFile = new ConfigFileBO();
        // For unit tests
        if (!configFile.isLoaded()) {
            configFile.init();
        }
        return configFile.getProperty("santander.webservice.security.numerointermediario");
    }
}
