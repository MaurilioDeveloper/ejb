package com.rci.omega2.ejb.utils;

public interface DocumentationConstants {
    
    public final String CTRLR = "CTRLR";
    public final String CTRLN = "CTRLN";
    public final String CCR = "CCR";
    public final String CCN = "CCN";
    public final String CDCR = "CDCR";
    public final String CDCN = "CDCN";
    public final String VISTORIAN = "VISTORIAN";
    public final String VISTORIAR = "VISTORIAR";
    
    public final Integer ID_DOC_CET = 1;
    public final Integer ID_DOC_CCB = 2;
    public final Integer ID_DOC_APROVACAO = 3;
    public final Integer ID_DOC_CCBCLAUSULAS = 4;
    public final Integer ID_DOC_SPF = 5;
    public final Integer ID_DOC_SEGURO_TRANQUILIDADE = 6;
    public final Integer ID_DOC_CTRL = 7;
    public final Integer ID_DOC_CC = 8;
    public final Integer ID_DOC_VISTORIA = 9;
    public final Integer ID_DOC_COTIZADOR = 10;
    
    public final String CET_NAME = "CET";
    public final String CET_DESCRIPTION = "Custo Efetivo Total";
    
    public final String CCB_NAME = "CCB";
    public final String CCB_DESCRIPTION = "Cédula de Crédito Bancário";
    
    public final String APROVACAO_NAME = "Aprovação";
    public final String APROVACAO_DESCRIPTION = "Documento de Aprovação RCI";
    
    public final String SPF_NAME = "SPF";
    public final String SPF_DESCRIPTION = "Documento de adesão do SPF";
    
    public final String SEGURO_TRANQUILIDADE_NAME = "Seguro Tranquilidade";
    public final String SEGURO_TRANQUILIDADE_DESCRIPTION = "Documento de adesão do Seguro Tranquilidade";
    
    public final String COTIZADOR_NAME = "Cotizador";
    public final String COTIZADOR_DESCRIPTION = "Cotizador";
    
    public final String DATA_NASCIMENTO_FORMAT = "yyyyMMdd";

    /** STR SPF **/
    public final String STR_PREMIO_UNICO_TOTAL = "3,99% aplicado sobre o Valor Financiado.";
    public final String STR_IOF_TOTAL = "0,38% aplicado sobre o Prêmio.";
}
