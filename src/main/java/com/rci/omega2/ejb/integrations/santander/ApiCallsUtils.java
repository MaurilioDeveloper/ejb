package com.rci.omega2.ejb.integrations.santander;

import java.net.MalformedURLException;
import java.util.List;

import com.altec.bsbr.app.afc.webservice.impl.AnoCombustivelClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.AnoCombustivelClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.AnoCombustivelDTO;
import com.altec.bsbr.app.afc.webservice.impl.AtividadeEconomicaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.CombustivelDTO;
import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.altec.bsbr.app.afc.webservice.impl.DominiosClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.DominiosGeraisClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.MarcaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.ModeloClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.TipoRelacionamentoClientRequest;

public class ApiCallsUtils {
    public List<DominioDTO> getDom(String domCode) throws MalformedURLException {
        ApiCalls gdb = new ApiCalls();
        DominiosGeraisClientRequest dgcr = new DominiosGeraisClientRequest();
        dgcr.setCodigoDominio(domCode);
        // numeroProduto codigo
        DominiosClientResponse retornodom = gdb.getDominiosCliente(dgcr);
        if (retornodom.getDominios() != null) {
            return retornodom.getDominios().getOpcoes();
        } else {
            return null;
        }
    }

    public String getDomCodeByDescription(String domCode, String desc) throws MalformedURLException {

        for (DominioDTO dondto : getDom(domCode)) {
            if (dondto.getDescricao().equalsIgnoreCase(desc)) {
                return dondto.getCodigo();
            }
        }
        return null;
    }

    public String getDomCodeByBoolean(String domCode, Boolean bol) throws MalformedURLException {
        for (DominioDTO dondto : getDom(domCode)) {
            if ((bol && dondto.getDescricao().equalsIgnoreCase("SIM"))
                    || (!bol && dondto.getDescricao().equalsIgnoreCase("NÃO"))) {
                return dondto.getCodigo();
            }
        }
        return null;
    }

    public String getRelashioshipType(String natureCode)throws MalformedURLException{
        ApiCalls gdb = new ApiCalls();
        TipoRelacionamentoClientRequest tipoRelClientRequest = new TipoRelacionamentoClientRequest();
        tipoRelClientRequest.setCodigoNaturezaJuridica(natureCode);
        DominiosClientResponse relType = gdb.getTiposRelacionamento(tipoRelClientRequest);
        
        for(DominioDTO domDto : relType.getDominios().getOpcoes()){
            if(domDto.getDescricao().equalsIgnoreCase(natureCode)){
                return domDto.getCodigo();
            }
        }
        return null;
    }
    
    public String getBrandByNameAndVehicleType(String nomeMarca, String tipoVeiculo) throws MalformedURLException {
        ApiCalls gdb = new ApiCalls();
        MarcaClientRequest marcaClientRequest = new MarcaClientRequest();
        marcaClientRequest.setCodigoTipoVeiculo(tipoVeiculo);
        DominiosClientResponse marca = gdb.getListaVeiculoMarca(marcaClientRequest);

        for (DominioDTO domdto : marca.getDominios().getOpcoes()) {
            if (domdto.getDescricao().equalsIgnoreCase(nomeMarca)) {
                return domdto.getCodigo();
            }
        }
        return null;
    }

    public String getModelByBrandNameAndType(String brand, String vehicleType, String modelName)
            throws MalformedURLException {
        ApiCalls gdb = new ApiCalls();
        ModeloClientRequest modeloClientRequest = new ModeloClientRequest();
        modeloClientRequest.setNumeroMarca(brand);
        modeloClientRequest.setCodigoTipoVeiculo(vehicleType);

        DominiosClientResponse models = gdb.getListaVeiculoModelo(modeloClientRequest);

        for (DominioDTO domdto : models.getDominios().getOpcoes()) {
            if (domdto.getDescricao().toUpperCase().startsWith(modelName.toUpperCase())) {
                return domdto.getCodigo();
            }
        }
        // Dynamique 1.6 16V Hi-Flex SUV
        return null;
    }

    public String getFuelCodebyBrandAndModel(String vehicleType, String vehicleModel, String brandCode, String fuelName,
            String modelYear) throws MalformedURLException {
        ApiCalls gdb = new ApiCalls();
        AnoCombustivelClientRequest anoCombustivelClientRequest = new AnoCombustivelClientRequest();
        anoCombustivelClientRequest.setNumeroMarca(brandCode);
        anoCombustivelClientRequest.setCodigoTipoVeiculo(vehicleType);
        anoCombustivelClientRequest.setNumeroModelo(vehicleModel);
        AnoCombustivelClientResponse anoModeloresponse = gdb.getListaAnoCombustivel(anoCombustivelClientRequest);

        for (AnoCombustivelDTO anoCom : anoModeloresponse.getAnoCombustivel().getOpcoes()) {
            if (anoCom.getNumeroAno().equalsIgnoreCase(modelYear)) {
                for (CombustivelDTO domdto : anoCom.getCombustiveis()) {
                    if (domdto.getDescricao().startsWith(fuelName)) {
                        return domdto.getCodigo();
                    }
                }
            }
        }
        return null;
    }

    public String getProcedenceCodebyBrandAndModel(String vehicleType, String vehicleModel, String brandCode,
            String modelYear) throws MalformedURLException {
        ApiCalls gdb = new ApiCalls();
        AnoCombustivelClientRequest anoCombustivelClientRequest = new AnoCombustivelClientRequest();
        anoCombustivelClientRequest.setNumeroMarca(brandCode);
        anoCombustivelClientRequest.setCodigoTipoVeiculo(vehicleType);
        anoCombustivelClientRequest.setNumeroModelo(vehicleModel);
        AnoCombustivelClientResponse anoModeloresponse = gdb.getListaAnoCombustivel(anoCombustivelClientRequest);

        for (AnoCombustivelDTO anoCom : anoModeloresponse.getAnoCombustivel().getOpcoes()) {
            if (anoCom.getNumeroAno().equalsIgnoreCase(modelYear)) {
                return anoCom.getProcedenciaVeiculo();
            }
        }
        return null;
    }

    public DominioDTO getListEconomicActivity(String economicActivityName) throws MalformedURLException {
        ApiCalls gdb = new ApiCalls();

        AtividadeEconomicaClientRequest atividadeEconomicaClientRequest = new AtividadeEconomicaClientRequest();
        DominiosClientResponse retorno = gdb.getListaAtivadeEconomica(atividadeEconomicaClientRequest);

        for (DominioDTO anoCom : retorno.getDominios().getOpcoes()) {

            if (anoCom.getDescricao().startsWith(economicActivityName)) {
                return anoCom;
            }
        }
        return null;
    }

}
