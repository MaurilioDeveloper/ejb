package com.rci.omega2.ejb.integrations.santander.soaputils;


/**
 * Legacy refacto by mdebonnaire
 *
 * Manage the token security
 * @author Mlandreau
 *
 */

public class TokenSecurityConfiguration {

    /** End point URL */
    private String endPointUrlFinanciamentos;
    /** End point URL dominios */
    private String endPointUrlDominios;
    /** token:security - username */
    private String username;
    /** token:security - key */
    private String key;
    /** token:security - cnpj */
    private String cnpj;
    /** token:security - codigoGrupoCanal */
    private String codigoGrupoCanal;
    /** token:security - numeroIntermediario */
    private String numeroIntermediario;
    

    /** If is in production */
    private String executedInProduction;
    
    /** If numero intermediario in step 1 is active */
    private String activeNumeroIntermediario;
    
    /**
     * Return the the username field
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Defines the  the username field
     * @param pUsername the username to set
     */
    public void setUsername(String pUsername) {
        username = pUsername;
    }
    /**
     * Return the the key field
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * Defines the  the key field
     * @param pKey the key to set
     */
    public void setKey(String pKey) {
        key = pKey;
    }
    /**
     * Return the the cnpj field
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }
    /**
     * Defines the  the cnpj field
     * @param pCnpj the cnpj to set
     */
    public void setCnpj(String pCnpj) {
        cnpj = pCnpj;
    }
    /**
     * Return the the codigoGrupoCanal field
     * @return the codigoGrupoCanal
     */
    public String getCodigoGrupoCanal() {
        return codigoGrupoCanal;
    }
    /**
     * Defines the  the codigoGrupoCanal field
     * @param pCodigoGrupoCanal the codigoGrupoCanal to set
     */
    public void setCodigoGrupoCanal(String pCodigoGrupoCanal) {
        codigoGrupoCanal = pCodigoGrupoCanal;
    }
    /**
     * Return the the numeroIntermediario field
     * @return the numeroIntermediario
     */
    public String getNumeroIntermediario() {
        return numeroIntermediario;
    }
    /**
     * Defines the  the numeroIntermediario field
     * @param pNumeroIntermediario the numeroIntermediario to set
     */
    public void setNumeroIntermediario(String pNumeroIntermediario) {
        numeroIntermediario = pNumeroIntermediario;
    }
    /**
     * Return the the endPointUrl field
     * @return the endPointUrl
     */
    public String getEndPointUrlFinanciamentos() {
        return endPointUrlFinanciamentos;
    }
    /**
     * Defines the  the endPointUrl field
     * @param pEndPointUrl the endPointUrl to set
     */
    public void setEndPointUrlFinanciamentos(String pEndPointUrl) {
        endPointUrlFinanciamentos = pEndPointUrl;
    }

    /**
     * Return the the executedInProduction field
     * @return the executedInProduction
     */
    public String getExecutedInProduction() {
        return executedInProduction;
    }
    /**
     * Defines the  the executedInProduction field
     * @param pExecutedInProduction the executedInProduction to set
     */
    public void setExecutedInProduction(String pExecutedInProduction) {
        executedInProduction = pExecutedInProduction;
    }
    
    /**
     * Return the the executedInProduction field
     * @return the executedInProduction
     */
    public Boolean isExecutedInProduction() {
        return "1".equals(executedInProduction);
    }
    
    /**
     * Return the ActiveNumeroIntermediario field
     * @return the ActiveNumeroIntermediario
     */
    public String getActiveNumeroIntermediario() {
        return activeNumeroIntermediario;
    }
    
    /**
     * Defines the  the ActiveNumeroIntermediario field
     * @param pActiveNumeroIntermediario the ActiveNumeroIntermediario to set
     */
    public void setActiveNumeroIntermediario(String pActiveNumeroIntermediario) {
        activeNumeroIntermediario = pActiveNumeroIntermediario;
    }
    
    /**
     * Return the the ActiveNumeroIntermediario field
     * @return the ActiveNumeroIntermediario
     */
    public Boolean isActiveNumeroIntermediario() {
        return "1".equals(activeNumeroIntermediario);
    }
    /**
     * Return the the endPointUrlDominios field
     * @return the endPointUrlDominios
     */
    public String getEndPointUrlDominios() {
        return endPointUrlDominios;
    }
    /**
     * Defines the  the endPointUrlDominios field
     * @param pEndPointUrlDominios the endPointUrlDominios to set
     */
    public void setEndPointUrlDominios(String pEndPointUrlDominios) {
        endPointUrlDominios = pEndPointUrlDominios;
    }
}
