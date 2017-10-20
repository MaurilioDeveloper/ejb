package com.rci.omega2.ejb.utils;

public interface ValidateConstants {

    public final String SUCCESS_RETURN_CODE = "00";
    
    public final String ERROR_MAINTENANCE = "Funcionalidade em manutenção";
    
    public final String ERROR_PROPOSAL_NOT_EDIT = "Esta proposta não pode ser alterada";
    
    public final String ERROR_PROPOSAL_NOT_SEND = "Esta proposta não pode ser enviada";
    
    public final String ERROR_PROPOSAL_NOT_FOUND = "Proposta não identificada";
    
    public final String REQUIRED_CERTIFIED_AGENT = "O agente certificado é obrigatório";
    
    public final String SAVE_PROPOSAL_ERROR_CALCULATE = "Houve um problema no calculo";
    
    public final Integer KEY_CUSTOMER = 1;
    public final Integer KEY_GUARANTOR = 2;
    
    public final String CALCULATE_REQUIRED_VEHICLE_PRICE = "O valor do veículo não pode ser nulo";
    public final String CALCULATE_REQUIRED_COMMISSION = "A comissão não pode ser nulo";
    public final String CALCULATE_REQUIRED_PRODUCT = "A tabela de financiamento não pode ser nulo";
    public final String CALCULATE_REQUIRED_SALE_TYPE = "O tipo de venda não pode ser nulo";
    public final String CALCULATE_REQUIRED_FINANCE_TYPE = "O tipo de financiamento não pode ser nulo";
    public final String CALCULATE_REQUIRED_VEHICLE_VERSION = "A versão do veículo não pode ser nulo";
    public final String CALCULATE_REQUIRED_PERSON_TYPE = "O tipo de pessoa não pode ser nulo";
    public final String CALCULATE_REQUIRED_TERM = "A quantidade de parcelas não pode ser nulo";
    public final String CALCULATE_REQUIRED_DELAY_VALUE = "A carência não pode ser nulo";
    public final String CALCULATE_REQUIRED_PROVINCE_CALCULATE = "A unidade federação do endereço não pode ser nulo";
    public final String CALCULATE_REQUIRED_TC_EXEMPT = "A isenção da TC não pode ser nulo";
    public final String CALCULATE_REQUIRED_REPACKAGE = "O plano de pagamento é obrigatório";
    public final String CALCULATE_ERROR_REPACKAGE = "O pacote não atende à quantidade de parcelas selecionado";

    public final String CUSTOMER_REQUIRED_NAME = "O nome do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_CPF_CNPJ = "O CPF/CNPJ do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_COUNTRY = "A nacionalidade do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_PROVINCE = "A UF do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_NATURALNESS = "A naturalidade do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_PERSON_GENDER = "O sexo do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_ADMISSION_DATE = "A data de admissão do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_OCCUPATION = "A oocupação do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_CIVIL_STATE = "O estado civil do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_CONTACT = "É necessário informar fone residencial ou fone celular ou o email do cliente";
    public final String CUSTOMER_REQUIRED_PERSONAL_PHONE_TYPE = "O tipo de telefone do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_POLITICAL_EXPOSITION = "O campo pessoa politicamente exposta do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_EDUCATION_DEGREE = "O grau de instrução do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_MOTHER_NAME = "O nome da mãe do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_INCOME_TYPE = "O tipo de renda do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_PROOF_INCOME_TYPE = "O Tipo de Comprovação de Renda do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_EMPLOYER_NAME = "O nome da empresa do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_INCOME = "A renda mensal do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_MAILING_ADDRESS_TYPE = "O endereço de correspondência do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_COMERCIAL_PHONE_TYPE = "O telefone comercial do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_NATIONAL_CAR = "A origem do veículo é obrigatório";
    
    public final String CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_PROVINCE = "A unidade federação do endereço da empresa do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_CITY = "A cidade do endereço da empresa do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_NEIGHBORHOOD = "O bairro do endereço da empresa do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_POST_CODE = "O CEP do endereço da empresa do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_NUMBER = "O número do endereço da empresa do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_EMPLOYER_ADDRESS = "O endereço da empresa do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_EMPLOYER_PROFESSION = "O cargo / função do cliente é obrigatório";
    
    public final String CUSTOMER_REQUIRED_ADDRESS_POST_CODE = "O cep do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_ADDRESS_NUMBER = "O número do endereço do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_ADDRESS = "O endereço do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_ADDRESS_NEIGHBORHOOD = "O bairro do endereço do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_ADDRESS_CITY = "A cidade do endereço do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_ADDRESS_PROVINCE = "A unidade federação do endereço do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_ADDRESS_SINCE = "O campo Reside no endereço desde é obrigatório";
    
    public final String CUSTOMER_REQUIRED_RESIDENCE_TYPE = "O tipo de residência do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_BANK_DETAILS = "Os dados bancários do cliente devem ser informados.";
    public final String CUSTOMER_REQUIRED_REFERENCE = "Uma referência do cliente é obrigatória";
    public final String CUSTOMER_REQUIRED_SPOUSE = "Os dados do conjuge do cliente são obrigatórios";
    public final String CUSTOMER_REQUIRED_SPOUSE_NAME = "O nome do conjuge do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_SPOUSE_CPF = "O cpf do conjuge do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_SPOUSE_PERSON_GENDER = "O sexo do conjuge do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_SPOUSE_DATE_BIRTH = "O aniversário do conjuge do cliente é obrigatório";
    
    public final String CUSTOMER_REQUIRED_PJ_INCOME = "O faturamento mensal do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_PJ_LEGAL_NATURE = "A natureza jurídica do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_PJ_BUILDING_OWNER = "O campo sede própria do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_PJ_ECONOMIC_ACTIVITY = "O campo atividade econômica do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_PJ_INDUSTRIAL_SEGMENT = "O campo grupo atividade econômica do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_PJ_BIRTH = "O campo data de abertura é obrigatório";
    public final String CUSTOMER_REQUIRED_PJ_EMPLOYER_SIZE = "O porte da empresa é obrigatório";
    
    public final String CUSTOMER_REQUIRED_DOCUMENT_TYPE = "O tipo documento do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_DOCUMENT_PROVINCE = "A UF do documento do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_DOCUMENT_EMISSION_ORGANISM = "O órgão emissor do documento do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_DOCUMENT_COUNTRY = "O país do documento do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_DOCUMENT_NUMBER = "O número do documento do cliente é obrigatório";
    public final String CUSTOMER_REQUIRED_DOCUMENT_EMISSION_DATE = "A data de emissão do documento do cliente é obrigatório";

    public final String CUSTOMER_ERROR_BIRTH = "O campo data de aniversário do cliente está incorreto";
    public final String CUSTOMER_ERROR_BIRTH_18 = "O cliente precisa ter mais do que 18 anos";
    public final String CUSTOMER_ERROR_ADDRESS_SINCE = "O campo reside no endereço desde do cliente está incorreto";
    public final String CUSTOMER_ERROR_ACCOUNT_BIRTH = "O campo data de abertura da conta do cliente está incorreto";
    public final String CUSTOMER_ERROR_SPOUSE_BIRTH = "O campo data de aniversário da esposa do cliente está incorreto";
    
    public final String GUARANTOR_01 = "[ Avalista 01 ] ";
    public final String GUARANTOR_02 = "[ Avalista 02 ] ";
    
    public final String GUARANTOR_REQUIRED_NAME = "O nome do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_CPF = "O cpf do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_MOTHER_NAME = "O nome da mãe do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_DATE_BIRTH = "A data de nascimento do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_PROVINCE = "A UF do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_COUNTRY = "A nacionalidade do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_NATURALNESS = "A naturalidade do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_PERSON_GENDER = "O sexo do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMISSION_ORGANISM = "O orgão emissor do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_CIVIL_STATE = "O estado civil do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_KINSHIP_TYPE = "O grau de parentesco do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EDUCATION_DEGREE = "O grau de instrução do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_POLITICAL_EXPOSITION = "A renda mensal do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_INCOME = "O campo pessoa politicamente exposta do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_MAILING_ADDRESS_TYPE = "O campo endereço de correspondência do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_RESIDENCE_TYPE = "O tipo residencia do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_FIXO_PHONE_TYPE = "O telefone fixo do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_COMERCIAL_PHONE_TYPE = "O telefone comercial do avalista é obrigatório";
    
    public final String GUARANTOR_REQUIRED_ADDRESS_POST_CODE = "O cep do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_ADDRESS_NUMBER = "O número do endereço do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_ADDRESS = "O endereço do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_ADDRESS_NEIGHBORHOOD = "O bairro do endereço do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_ADDRESS_CITY = "A cidade do endereço do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_ADDRESS_PROVINCE = "A unidade federação do endereço do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_ADDRESS_SINCE = "O campo reside no endereço desde do avalista é obrigatório";
    
    public final String GUARANTOR_REQUIRED_SPOUSE = "Os dados do conjuge do avalista são obrigatórios";
    public final String GUARANTOR_REQUIRED_SPOUSE_NAME = "O nome do conjuge do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_SPOUSE_CPF = "O cpf do conjuge do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_SPOUSE_PERSON_GENDER = "O sexo do conjuge do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_SPOUSE_DATE_BIRTH = "O aniversário do conjuge do avalista é obrigatório";
    
    public final String GUARANTOR_REQUIRED_DOCUMENT_TYPE = "O tipo documento do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_DOCUMENT_NUMBER = "O número do documento do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_DOCUMENT_EMISSION_DATE = "A data de emissão do documento do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_DOCUMENT_PROVINCE = "O UF do documento do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_DOCUMENT_COUNTRY = "O país do documento do avalista é obrigatório";
    
    public final String GUARANTOR_REQUIRED_EMPLOYER = "Os dados profissionais do avalista são necessários";
    public final String GUARANTOR_REQUIRED_EMPLOYER_NAME = "O nome da empresa do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_PROFESSION = "O cargo / função do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_OCCUPATION = "A ocupação do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_INCOME_TYPE = "O tipo de renda do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_PROOF_INCOME_TYPE = "O tipo comprovação de renda do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_PROVINCE = "O estato do endereço da empresa do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_POST_CODE = "O cep do endereço da empresa do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_NUMBER = "O número do endereço da empresa do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_ADDRESS = "O endereço da empresa do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_NEIGHBORHOOD = "O bairro do endereço da empresa do avalista é obrigatório";
    public final String GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_CITY = "A cidade do endereço da empresa do avalista é obrigatório";

    public final String GUARANTOR_REQUIRED_BANK_DETAILS = "Os dados bancários do avalista devem ser informados.";
    
    public final String GUARANTOR_ERROR_BIRTH = "O campo data de aniversário do avalista está incorreto";
    public final String GUARANTOR_ERROR_ADDRESS_SINCE = "O campo reside no endereço desde do avalista está incorreto";
    public final String GUARANTOR_ERROR_ACCOUNT_BIRTH = "O campo data de abertura da conta do avalista está incorreto";
    public final String GUARANTOR_ERROR_SPOUSE_BIRTH = "O campo data de aniversário da esposa do avalista está incorreto";
    
    public final String BANK_DETAILS_BRANCH = " [Agência]";
    public final String BANK_DETAILS_ACCOUNT = " [Conta]";
    public final String BANK_DETAILS_ACCOUNT_OPENING_DATE = " [Data de abertura]";
    
}
