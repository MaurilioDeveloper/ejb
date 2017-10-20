package com.rci.omega2.ejb.dto.nimble;

public class NimbleStructureDataDTO {
    private Integer codigo;
    private String sigla;
    private String nome;
    private String endereco;
    private String bairro;
    private String uf;
    private String cidade;
    private String cNPJ;
    private Boolean ativo;
    private Integer corretora;
   

    public Integer getCodigo() {
    return codigo;
    }

    public void setCodigo(Integer codigo) {
    this.codigo = codigo;
    }

    public String getSigla() {
    return sigla;
    }

    public void setSigla(String sigla) {
    this.sigla = sigla;
    }

    public String getNome() {
    return nome;
    }

    public void setNome(String nome) {
    this.nome = nome;
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

    public String getUf() {
    return uf;
    }

    public void setUf(String uf) {
    this.uf = uf;
    }

    public String getCidade() {
    return cidade;
    }

    public void setCidade(String cidade) {
    this.cidade = cidade;
    }

    public String getCNPJ() {
    return cNPJ;
    }

    public void setCNPJ(String cNPJ) {
    this.cNPJ = cNPJ;
    }

    public Boolean getAtivo() {
    return ativo;
    }

    public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
    }

    public Integer getCorretora() {
    return corretora;
    }

    public void setCorretora(Integer corretora) {
    this.corretora = corretora;
    }


}
