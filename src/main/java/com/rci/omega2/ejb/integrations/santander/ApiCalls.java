package com.rci.omega2.ejb.integrations.santander;

import java.net.MalformedURLException;
import java.net.URL;

import com.altec.bsbr.app.afc.webservice.impl.AnoCombustivelClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.AnoCombustivelClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.AtividadeEconomicaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.DadosAdpClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.DadosAdpClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.DadosPessoaisClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.DadosPessoaisClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.DominiosClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.DominiosGeraisClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpoint;
import com.altec.bsbr.app.afc.webservice.impl.FinanciamentosOnlineEndpointService;
import com.altec.bsbr.app.afc.webservice.impl.FormaPagamentoClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereEnderecoClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereEnderecoClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InsereGarantiaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereGarantiaClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InserePropostaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InserePropostaClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.InsereReferenciaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.InsereReferenciaClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.MarcaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.ModeloClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.SimulacaoClientResponse;
import com.altec.bsbr.app.afc.webservice.impl.SimulacaoPropostaClientRequest;
import com.altec.bsbr.app.afc.webservice.impl.TipoRelacionamentoClientRequest;
import com.rci.omega2.ejb.bo.ConfigFileBO;
import com.rci.omega2.ejb.integrations.santander.soaputils.HeaderHandlerResolver;

public class ApiCalls {

    private FinanciamentosOnlineEndpoint portafinanceiros;

    public ApiCalls() throws MalformedURLException {
        init();
    }

    public void init() throws MalformedURLException {
        ConfigFileBO configFile = new ConfigFileBO();
        // For unit tests
        if (!configFile.isLoaded()) {
            configFile.init();
        }
        String prop = configFile.getProperty("santander.webservice.financiamentos.endpoint.wsdl");
        FinanciamentosOnlineEndpointService fin = new FinanciamentosOnlineEndpointService(new URL(prop));
        fin.setHandlerResolver(new HeaderHandlerResolver());
        portafinanceiros = fin.getFinanciamentosOnlineEndpointPort();
    }

    public DominiosClientResponse getDominiosCliente(DominiosGeraisClientRequest dominiosGeraisRequest)
            throws MalformedURLException {
        return portafinanceiros.listaDominiosGerais(dominiosGeraisRequest);
    }

    public DominiosClientResponse getListaVeiculoMarca(MarcaClientRequest dominioMarcaRequest)
            throws MalformedURLException {
        return portafinanceiros.listaVeiculoMarcas(dominioMarcaRequest);
    }
    
    public DominiosClientResponse getListaVeiculoModelo(ModeloClientRequest dominioModeloRequest)
            throws MalformedURLException {
        return portafinanceiros.listaVeiculoModelos(dominioModeloRequest);
    }
    
    
    public AnoCombustivelClientResponse getListaAnoCombustivel(AnoCombustivelClientRequest dominioModeloRequest)
            throws MalformedURLException {
        return portafinanceiros.listaVeiculoAnoCombustivel(dominioModeloRequest);
    }
    
    public DominiosClientResponse getListaAtivadeEconomica(AtividadeEconomicaClientRequest atividadeEconomicaClientRequest)
            throws MalformedURLException {
        return portafinanceiros.listaAtividadesEconomicas(atividadeEconomicaClientRequest);
    }

    public DominiosClientResponse getFormaPagamento(FormaPagamentoClientRequest dominioFormaPagamentoRequest) throws MalformedURLException {
        return portafinanceiros.listaFormasPagamento(dominioFormaPagamentoRequest);
    }
    
    public DominiosClientResponse getTiposRelacionamento(TipoRelacionamentoClientRequest relacionamentoClientRequest)
            throws MalformedURLException {
        return portafinanceiros.listarTipoRelacionamento(relacionamentoClientRequest);
    }

    public SimulacaoClientResponse sendSimulaFinanciamentoVeiculo(
            SimulacaoPropostaClientRequest simulacaoPropostaRequest) throws MalformedURLException {
        return portafinanceiros.simulaFinanciamentoVeiculo(simulacaoPropostaRequest);
    }    
    
    public InserePropostaClientResponse sendProposalStep1(
            InserePropostaClientRequest propostaRequest) throws MalformedURLException {
        return portafinanceiros.enviaPropostaFinanciamentoVeiculoPasso1Inicio(propostaRequest);
    }

    public DadosPessoaisClientResponse sendProposalStep2(
            DadosPessoaisClientRequest dadosPessoaRequest) throws MalformedURLException {
        return portafinanceiros.enviaPropostaFinanciamentoVeiculoPasso2DadosPessoais(dadosPessoaRequest);
    }

    public InsereEnderecoClientResponse sendProposalSte3(InsereEnderecoClientRequest endereco)throws MalformedURLException{
        return portafinanceiros.enviaPropostaFinanciamentoVeiculoPasso3Endereco(endereco);
    }
    
    public InsereReferenciaClientResponse sendProposalStep4(InsereReferenciaClientRequest referencia) throws MalformedURLException{
        return portafinanceiros.enviaPropostaFinanciamentoVeiculoPasso4Referencias(referencia);
    }
    
    public InsereGarantiaClientResponse sendProposalStep5(InsereGarantiaClientRequest garantia)throws MalformedURLException{
        return portafinanceiros.enviaPropostaFinanciamentoVeiculoPasso5Garantias(garantia);
    }
    
    public DadosAdpClientResponse sendProposalStep6(DadosAdpClientRequest fim)throws MalformedURLException{
        return portafinanceiros.enviaPropostaFinanciamentoVeiculoPasso6Fim(fim);
    }
}
