package com.rci.omega2.ejb.integrations.santander;

import java.net.MalformedURLException;

import javax.ejb.EJB;

import com.altec.bsbr.app.afc.webservice.impl.SimulacaoClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.SimulacaoPropostaClientRequest;
import com.rci.omega2.ejb.bo.ConfigFileBO;
import com.rci.omega2.ejb.bo.VehicleVersionBO;
import com.rci.omega2.ejb.dto.SantanderSimulationDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.entity.VehicleVersionEntity;
import com.rci.omega2.entity.enumeration.VehicleTypeEnum;

/**
 * 
 * @author Ricardo Zandonai (rzandonai@stefanini.com)
 *
 */
public class SantanderSimulation {
    /**
     * 
     * @param simulationDTO
     * @return
     * @throws MalformedURLException
     */

    @EJB
    VehicleVersionBO vehicleVersionBO;

    @SuppressWarnings("static-access")
    public SimulacaoClientResponse send(SantanderSimulationDTO simulationDTO) throws MalformedURLException, UnexpectedException {
        ApiCalls apiCall = new ApiCalls();
        Utils util = new Utils();
        ApiCallsUtils acu = new ApiCallsUtils();

        SimulacaoPropostaClientRequest simulacaoPropostaRequest = new SimulacaoPropostaClientRequest();

        String simTable = simulationDTO.getSimulationTable();
        // Quando não informada a tabela utilizar a padrão 00000
        if (simTable == null) {
            simTable = "00000";
        }

        simulacaoPropostaRequest.setNumeroTabelaFinanciamento(simTable);

        String finTypeCode = acu.getDomCodeByDescription("35", simulationDTO.getTypeOfFinancing());
        // Envia o codigo do produto
        simulacaoPropostaRequest.setNumeroProduto(finTypeCode);

        simulacaoPropostaRequest = addVehicleInfos(simulationDTO, acu, simulacaoPropostaRequest);

        // Indica se ira simular com seguro
        simulacaoPropostaRequest.setCodigoIndicadorSeguro("N");

        // Valor total do ben
        simulacaoPropostaRequest.setValorTotal(util.bigToString(simulationDTO.getTotalValue(),2));
        // Valor da entrada
        simulacaoPropostaRequest.setValorEntrada(util.bigToString(simulationDTO.getInputValue(),2));
        // valor de parcelas
        simulacaoPropostaRequest.setNumeroParcelas(simulationDTO.getParcelsNumber().toString());
        
        String modalityOfFinancing = acu.getDomCodeByDescription("17",simulationDTO.getModalityOfFinancing());
        // Codigo da modalidade de financiamento
        simulacaoPropostaRequest.setCodigoModalidade(modalityOfFinancing);

        String tipoPessoa = acu.getDomCodeByDescription("34",simulationDTO.getPersonType().getAbbreviation());
        // tipo de pessoa     
        simulacaoPropostaRequest.setCodigoTipoPessoa(tipoPessoa);

        // Cpf do cliente
        simulacaoPropostaRequest.setNumeroCpfCnpj(simulationDTO.getCpfCnpj());
        
        String stateCode = acu.getDomCodeByDescription("39",simulationDTO.getProvinceName());
        // Seta o estado
        simulacaoPropostaRequest.setCodigoEstado(stateCode);
        
        simulacaoPropostaRequest.setCodigoTipoPagamento("CA");

        // Dia do primeiro vencimento
        simulacaoPropostaRequest.setDataPrimeiroVencimento(simulationDTO.getFirstPaymentDate());

        
        simulacaoPropostaRequest.setCodigoFrota("00");
        
        simulacaoPropostaRequest.setIsencaoTAB((simulationDTO.getTaxTab()?"N":"S"));
        simulacaoPropostaRequest.setIsencaoTC((simulationDTO.getTaxTc()?"N":"S"));
        simulacaoPropostaRequest.setCodigoFrota(simulationDTO.getFrotaCode());
        
        ConfigFileBO configFile = new ConfigFileBO();
        // For unit tests
        if (!configFile.isLoaded()) {
            configFile.init();
        }
        simulacaoPropostaRequest.setNumeroIntermediario(configFile.getProperty("santander.webservice.security.numerointermediario"));
        
        // indica se deve cobrar a tac Não obrigatorio
        //simulacaoPropostaRequest.setCodigoTarifaCadastroRenovacao("S");
        SimulacaoClientResponse envioSimulacao = apiCall.sendSimulaFinanciamentoVeiculo(simulacaoPropostaRequest);

        return envioSimulacao;

    }

    private SimulacaoPropostaClientRequest addVehicleInfos(SantanderSimulationDTO simulationDTO, ApiCallsUtils acu,
            SimulacaoPropostaClientRequest simulacaoPropostaRequest) throws UnexpectedException, MalformedURLException {
        VehicleVersionEntity vehicleVersion = vehicleVersionBO.findOne(simulationDTO.getIdVersion());
        String brand = null;
        String yearManufacture = null;
        String yearModel = null;
        String vehicleSaleType = "SIM";
        String modelName= null;
        String fuelName = null;

        String vehicleType = null;
        if (vehicleVersion != null && vehicleVersion.getVehicleModel() != null
                && vehicleVersion.getVehicleModel().getVehicleBrand() != null) {
            
            modelName = vehicleVersion.getDescription(); 
            brand = vehicleVersion.getVehicleModel().getVehicleBrand().getDescription();
            
            if (vehicleVersion.getVehicleType().equals(VehicleTypeEnum.USADO)) {
                vehicleSaleType = "NÃO";
            }
            
            fuelName = vehicleVersion.getFuel().getDescription();
            vehicleType = acu.getDomCodeByDescription("13", vehicleSaleType);
            yearModel = vehicleVersion.getModelYear().toString();
            
            if (vehicleVersion.getManufactureYear() != null) {
                yearManufacture = vehicleVersion.getManufactureYear().toString();
            } else {
                //se for vazio e o mesmo do modelo
                yearManufacture = yearModel;
            }

        } else {
            throw new UnexpectedException("Could not locate the vehicle");
        }

        String brandCode = acu.getBrandByNameAndVehicleType(brand, vehicleType);
        // marca do veiculo
        simulacaoPropostaRequest.setNumeroMarca(brandCode);

        String vehicleModel =  acu.getModelByBrandNameAndType(brandCode, vehicleType, modelName );
        // Modelo do veiculo
        simulacaoPropostaRequest.setCodigoModeloVeiculo(vehicleModel);
        
        simulacaoPropostaRequest.setNumeroAnoModeloVeiculo(yearModel);
        
        String fuelCode =  acu.getFuelCodebyBrandAndModel( vehicleType,  vehicleModel,  brandCode, fuelName, yearModel);
        // Ano modelo do veiculo
        simulacaoPropostaRequest.setCodigoTipoCombustivel(fuelCode);

        // ano de fabricação
        simulacaoPropostaRequest.setNumeroAnoFabricacao(yearManufacture);
        
        // indica se é 0 km
        simulacaoPropostaRequest.setCodigoIndicadorZeroKm(vehicleType);
        
        String nomeProcedence = acu.getProcedenceCodebyBrandAndModel(vehicleType,  vehicleModel,  brandCode,  yearModel);
        String procedenceCode = acu.getDomCodeByDescription("25", nomeProcedence);
        // indica a procedencia
        simulacaoPropostaRequest.setCodigoIndicadorProcedencia(procedenceCode);

        // indica se é um taxi
        simulacaoPropostaRequest.setCodigoIndicadorTaxi( acu.getDomCodeByBoolean("14", simulationDTO.getIsTaxi()));

        // indica se o é um veiculo adptado
        simulacaoPropostaRequest.setCodigoIndicadorVeiculoAdaptado(acu.getDomCodeByBoolean("15", simulationDTO.getIsAdapted()));
      
        return simulacaoPropostaRequest;
    }
}