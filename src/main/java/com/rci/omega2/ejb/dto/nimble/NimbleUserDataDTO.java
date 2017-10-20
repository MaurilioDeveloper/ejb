package com.rci.omega2.ejb.dto.nimble;

import java.util.HashMap;
import java.util.Map;

public class NimbleUserDataDTO {

    private String usuario;
    private String nomeCurto;
    private String nomeCompleto;
    private String nascimento;
    private String cpf;
    private String pis;
    private String corretora;
    private String estruturaVendas;
    private String produtor;
    private String endereco;
    private String bairro;
    private String estado;
    private String cidade;
    private String cep;
    private String emailPessoal;
    private String foneResidencial;
    private String foneAlternativo;
    private Integer banco;
    private String agencia;
    private String agenciaDigito;
    private String contaCorrente;
    private Integer contaCorrenteDigito;
    private Boolean ativo;
    private Boolean linkProprioNimble;
    private Integer tipoUsuario;
    private Boolean acessoAoSistema;
    private Boolean privilegioSupervisor;
    private NimbleUserConfigEnvioEmailDTO configEnvioEmail;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNomeCurto() {
        return nomeCurto;
    }

    public void setNomeCurto(String nomeCurto) {
        this.nomeCurto = nomeCurto;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public String getCorretora() {
        return corretora;
    }

    public void setCorretora(String corretora) {
        this.corretora = corretora;
    }

    public String getEstruturaVendas() {
        return estruturaVendas;
    }

    public void setEstruturaVendas(String estruturaVendas) {
        this.estruturaVendas = estruturaVendas;
    }

    public String getProdutor() {
        return produtor;
    }

    public void setProdutor(String produtor) {
        this.produtor = produtor;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEmailPessoal() {
        return emailPessoal;
    }

    public void setEmailPessoal(String emailPessoal) {
        this.emailPessoal = emailPessoal;
    }

    public String getFoneResidencial() {
        return foneResidencial;
    }

    public void setFoneResidencial(String foneResidencial) {
        this.foneResidencial = foneResidencial;
    }

    public String getFoneAlternativo() {
        return foneAlternativo;
    }

    public void setFoneAlternativo(String foneAlternativo) {
        this.foneAlternativo = foneAlternativo;
    }

    public Integer getBanco() {
        return banco;
    }

    public void setBanco(Integer banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getAgenciaDigito() {
        return agenciaDigito;
    }

    public void setAgenciaDigito(String agenciaDigito) {
        this.agenciaDigito = agenciaDigito;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public Integer getContaCorrenteDigito() {
        return contaCorrenteDigito;
    }

    public void setContaCorrenteDigito(Integer contaCorrenteDigito) {
        this.contaCorrenteDigito = contaCorrenteDigito;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getLinkProprioNimble() {
        return linkProprioNimble;
    }

    public void setLinkProprioNimble(Boolean linkProprioNimble) {
        this.linkProprioNimble = linkProprioNimble;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Boolean getAcessoAoSistema() {
        return acessoAoSistema;
    }

    public void setAcessoAoSistema(Boolean acessoAoSistema) {
        this.acessoAoSistema = acessoAoSistema;
    }

    public Boolean getPrivilegioSupervisor() {
        return privilegioSupervisor;
    }

    public void setPrivilegioSupervisor(Boolean privilegioSupervisor) {
        this.privilegioSupervisor = privilegioSupervisor;
    }

    public NimbleUserConfigEnvioEmailDTO getConfigEnvioEmail() {
        return configEnvioEmail;
    }

    public void setConfigEnvioEmail(NimbleUserConfigEnvioEmailDTO configEnvioEmail) {
        this.configEnvioEmail = configEnvioEmail;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
