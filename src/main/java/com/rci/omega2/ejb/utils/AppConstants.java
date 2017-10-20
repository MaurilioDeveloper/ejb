package com.rci.omega2.ejb.utils;

public class AppConstants {
    
    public static final String STRING_EMPTY = "";
    public static final String ERROR = "UNESPECTED ERROR";
    public static final String ERROR_GENERIC_SANTANDER = "Ocorreu um erro no retorno do Santander. Os dados não puderam ser atualizados. Entre em contato com o Help Desk do sistema RCI Direct para maiores detalhes.";
    
    public static final String MAIL_HTML_TYPE = "text/html; charset=UTF-8";
    public static final String KEY_NAME = "#{NAME}";
    public static final String KEY_CLICK_HERE = "#{CLICK_HERE}";
    public static final String KEY_IMG_LOGO = "#{IMG_LOGO}";
    public static final String IMG_FOLDER = "/assets/images/";
    public static final String KEY_URL = "#{URL}";
    public static final String SPF_IMPORT_CODE_OMEGA = "0";
    public static final Long SPF_KEY_ID = 5L;
    public static final Long GAP_KEY_ID = 16L;
    public static final Long COTIZADOR_KEY_ID = 30L;

    public static final String USER_TYPE_PROF_SALES_EXECUTIVE = "PROF_SALES_EXECUTIVE";
    
    /** SANTANDER_PROPOSTA_CODE_CREDIT */
    public static final String SANTANDER_TYPE_FINANCING_CDC_OR_CDCFLEX = "0015"; 
    
    /** SANTANDER_PROPOSTA_PRODUCT_LEASING */
    public static final Long TYPE_FINANCING_LEASING = 401L;
    /** SANTANDER_PROPOSTA_PRODUCT_CREDIT */
    public static final Long TYPE_FINANCING_CDC = 404L;
    /** CDC FLEX CONSTANTS **/
    public static final Long TYPE_FINANCING_CDC_FLEX = 405L;
    
    /** Avalista Depositario Fiel **/
    public static final String GUARANTOR_MAIN = "14";
    
    public static final Long SALE_TYPE_SHOW_ROOM = 6L;
    public static final Long SALE_TYPE_DIRECT = 1L;
    /** Valor default da Comissão **/
    public static final String COMMISSION_DEFAULT = "00";
    
    public static final Long DOSSIER_STATUS_ANDAMENTO = 1L;
    public static final Long DOSSIER_STATUS_DADOS_A_COMPLEMENTAR = 3L;
    public static final Long DOSSIER_STATUS_EM_ANALISE = 5L;
    public static final Long DOSSIER_STATUS_NEGADA = 7L;
    public static final Long DOSSIER_STATUS_CANCELADA = 8L;
    public static final Long DOSSIER_STATUS_APROVADA = 9L;
    public static final Long DOSSIER_STATUS_EFETIVADA = 10L;
    public static final Long DOSSIER_STATUS_DESISTENCIA = 30L;
    public static final Long DOSSIER_STATUS_VENCIDA = 31L;
    public static final Long DOSSIER_STATUS_CANCELADA_SIN = 32L;
    
    /** Alterar o tipo telefone para RECADO **/
    public static final String CHANGE_PHONE_TYPE_TO_MESSAGE = "3";
    
    /** Indicativo se o veículo é nacional **/
    public static final String NATIONAL_VEHICLE = "N";
    
    /** Dominio sigla UF **/
    public static final String DOMAIN_ACRONYM_PROVINCE = "SIGLA UF";
    /** Dominio País **/
    public static final String DOMAIN_COUNTRY = "PAÍS";
    /** Dominio Banco **/
    public static final String DOMAIN_BANK = "BANCO";
    /** Dominio Porte Empresa **/
    public static final String DOMAIN_EMPLOYER_SIZE = "PORTE EMPRESA";
    /** Dominio Atividade Economica **/
    public static final String DOMAIN_ECONOMIC_ACTIVITY = "ATIVIDADE ECONÔMICA";
    /** Dominio Atividade Economica **/
    public static final String DOMAIN_INDUSTRIAL_SEGMENT = "GRUPO ATIVIDADE ECONÔMICA";
    /** Dominio Profissão **/
    public static final String DOMAIN_PROFESSION = "PROFISSÃO";
    /** Dominio Versão Veículo **/
    public static final String DOMAIN_VEHICLE_VERSION = "VERSÃO VEÍCULO";
    /** Dominio Cor Veículo **/
    public static final String DOMAIN_VEHICLE_COLOR = "COR VEÍCULO";
    /** Dominio Tipo Documento **/
    public static final String DOMAIN_DOCUMENT_TYPE = "TIPO DOCUMENTO";
    /** Dominio Tipo Renda **/
    public static final String DOMAIN_INCOME_TYPE = "TIPO RENDA";
    /** Dominio Tipo Comprovante Renda **/
    public static final String DOMAIN_PROOF_INCOME_TYPE = "TIPO COMPROVANTE RENDA";
    /** Dominio Gênero **/
    public static final String DOMAIN_GENDER = "GÊNERO";
    /** Dominio Orgão Emissor **/
    public static final String DOMAIN_EMISSION_ORGANISM = "ORGÃO EMISSOR";
    /** Dominio Ocupação **/
    public static final String DOMAIN_OCCUPATION= "OCUPAÇÃO";
    /** Dominio Pessoa Politicamente Exposta **/
    public static final String DOMAIN_POLITICAL_EXPOSITION = "PESSOAL POLITICAMENTE EXPOSTA";
    /** Dominio Natureza Jurídica **/
    public static final String DOMAIN_LEGAL_NATURE = "NATUREZA JURÍDICA";
    /** Dominio Estado Civil **/
    public static final String DOMAIN_CIVIL_STATE = "ESTADO CIVIL";
    /** Dominio Grau de Instrução **/
    public static final String DOMAIN_EDUCATION_DEGREE = "GRAU INSTRUÇÃO";
    /** Dominio Tabela Financiamento **/
    public static final String DOMAIN_PRODUCT = "TABELA FINANCIAMENTO";
    /** Dominio Grau Parentesco **/
    public static final String DOMAIN_KINSHIP_TYPE = "GRAU PARENTESCO";
    /** Dominio Tipo Relacionamento Empresa **/
    public static final String DOMAIN_BUSINESS_RELATIONSHIP_TYPE = "TIPO RELACIONAMENTO EMPRESA";
    /** Dominio Funcionario Santander **/
    public static final String DOMAIN_SANTANDER_EMPLOYEE_TYPE = "INDICATIVO FUNCIONÁRIO SANTANDER";
    /** Dominio Tipo Residencia **/
    public static final String DOMAIN_RESIDENCE_TYPE = "TIPO RESIDENCIA";
    /** Dominio Tipo Endereço Correspondencia **/
    public static final String DOMAIN_MAILING_ADDRESS_TYPE = "TIPO ENDEREÇO CORRESPONDÊNCIA";
    /** Dominio Comissão **/
    public static final String DOMAIN_COMMISSION = "COMISSÃO";
    
    
    
    /** Omega User Default **/
    public static final String SYSTEM_OMEGA_USER = "RCIDIRECT";
    
    /** Event Type Alteração Status  **/
    public static final Long EVENT_TYPE_CHANGE_STATUS = 1L;

    /** Msg Erro ed Domínio Não Encontrado **/
    public static final String DOMAIN_NOT_FOUND_MSG_ERROR = "Não foi possível encontrar o valor %1$s do domínio %2$s";
    /** Assunto Erro ed Domínio Não Encontrado **/
    public static final String DOMAIN_NOT_FOUND_SUBJ_ERROR = "[OMEGA 2] - ERRO DOMINIO";
    
    /** Detalhe Notificação **/
    public static final String NOTIFICATION_TITLE = "[RCI Direct] Alteração de Status de Proposta.";
    /** Detalhe Notificação **/
    public static final String NOTIFICATION_DETAIL = "A proposta %1$s teve seu status alterado para '%2$s'.";
    
    /** Tipo Avalista **/
    public static final Long GUARANTOR_TYPE_DEFAULT = 12L;
    
    
    /** Codigo Retorno de Erro Qdo Altera o Dossier(Proposta) de Concessionária **/
    public static final String SANTANDER_ERROR_CODE_CHANGED_CONCESS_TAB = "@ERVJE0073";
    
    public static final String MAIL_SUBJECT_CHANGED_CONCESS_TAB = "[RCI Direct] Atualização Santander - Alteração de TAB no Modulo Administrativo";
    public static final String MAIL_BODY_CHANGED_CONCESS_TAB = "<b>Houve um novo registro de Alteração de TAB no Módulo Administrativo.</b><br/><br/>";
    
    public static final Integer DECIMAL_2 = 2;
    
    public static final Long CIVIL_STATE_CASADO = 2L;
    public static final Long CIVIL_STATE_COMPANHEIRO = 6L;
    
    public static final Long FINANCIAL_BRAND_RENAULT = 1L;
    public static final Long FINANCIAL_BRAND_NISSAN = 2L;
    public static final Long FINANCIAL_BRAND_RCI = 5L;
    
}
