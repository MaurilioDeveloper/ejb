package com.rci.omega2.ejb.dto.nimble;

import java.util.HashMap;
import java.util.Map;

public class NimbleUserConfigEnvioEmailDTO {

    private String nome;
    private String email;
    private String assinaturaPadrao;
    private String assinaturaProposta;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssinaturaPadrao() {
        return assinaturaPadrao;
    }

    public void setAssinaturaPadrao(String assinaturaPadrao) {
        this.assinaturaPadrao = assinaturaPadrao;
    }

    public String getAssinaturaProposta() {
        return assinaturaProposta;
    }

    public void setAssinaturaProposta(String assinaturaProposta) {
        this.assinaturaProposta = assinaturaProposta;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
