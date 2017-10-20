package com.rci.omega2.ejb.bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.HttpHeaders;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import com.amazonaws.util.Base64;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rci.omega2.common.exception.CryptoException;
import com.rci.omega2.ejb.dao.CarInsuranceDAO;
import com.rci.omega2.ejb.dto.InsuranceDeniedDTO;
import com.rci.omega2.ejb.dto.nimble.NimbleBaseResponse;
import com.rci.omega2.ejb.dto.nimble.NimbleStructureDataDTO;
import com.rci.omega2.ejb.dto.nimble.NimbleStructureResponse;
import com.rci.omega2.ejb.dto.nimble.NimbleUserConfigEnvioEmailDTO;
import com.rci.omega2.ejb.dto.nimble.NimbleUserDataDTO;
import com.rci.omega2.ejb.dto.nimble.NimbleUserResponseDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.CarInsuranceEntity;
import com.rci.omega2.entity.DealershipEntity;
import com.rci.omega2.entity.PersonEntity;
import com.rci.omega2.entity.PhoneEntity;
import com.rci.omega2.entity.UserEntity;
import com.rci.omega2.entity.enumeration.PhoneTypeEnum;
@Stateless
public class InsuranceQuoteBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(CertifiedAgentBO.class);

    @EJB
    private ConfigFileBO configFile;
    @EJB
    StructureBO structureBO;
    @EJB
    UserBO userBo;

    public String getProductorFromUser(Long idUser) throws Exception {
        
        UserEntity user  = userBo.findOne(idUser);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NimbleUserResponseDTO response = null;

        response = mapper.readValue(nimbleGet("/BuscarUsuarioPorNome/" + user.getLogin()), NimbleUserResponseDTO.class);
        if (response.getCodeMessage() != 1) {
            // cadastrar
            if (response.getCodeMessage() == 2) {
                NimbleBaseResponse responseAddUser = mapper.readValue(
                        nimblePost("/IncluirUsuario", mapper.writeValueAsString(userToNimble(user))),
                        NimbleBaseResponse.class);
                if (responseAddUser.getCodeMessage() == 1) {
                    response = mapper.readValue(nimbleGet("/BuscarUsuarioPorNome/" + user.getLogin()),
                            NimbleUserResponseDTO.class);
                    return response.getData().getProdutor();
                } else {
                     throw new UnexpectedException(responseAddUser.getMessageEx());
                }
            }
        } else {
            if (!response.getData().getAtivo()) {
                // converter
                NimbleBaseResponse responseActive = mapper.readValue(
                        nimbleGet("/AtivarUsuario/" + response.getData().getCpf()), NimbleBaseResponse.class);
                if (responseActive.getCodeMessage() != 23) {
                    throw new UnexpectedException(responseActive.getMessageEx());
                }
                return response.getData().getProdutor();
            }
        }

        return null;
    }

    public ObjectMapper getMpapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
    public NimbleStructureDataDTO getStruct(String cnpj) throws UnexpectedException, JsonParseException, JsonMappingException, ClientProtocolException, IOException{
     
        NimbleStructureResponse returnStruct = getMpapper().readValue(nimbleGet("/BuscarEstruturaVendaPorCnpj/"+cnpj), NimbleStructureResponse.class);
        if(returnStruct.getCodeMessage() == 1){
           return  returnStruct.getData();
        }else{
            throw new UnexpectedException(returnStruct.getMessageEx());
        }
        
            
        
    }
    
    public String nimblePost(String service, String json) throws ClientProtocolException, IOException {

        String url = configFile.getProperty("nimble.url");
        String login = configFile.getProperty("nimble.user");
        String pass = configFile.getProperty("nimble.key");

        /*
         * String login = "15457";
         * 
         * String pass = "0FJ7kgiDNEx18RXfpJLOGPHWYRpxYM"; String url =
         * "http://rest.teleport.com.br:8900/integracao/v1.0/TTELEPORT";
         */

        byte[] encodedBytes = Base64.encode((login + ":" + pass).getBytes());

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url + service);
        request.setEntity(new StringEntity(json, "UTF-8"));
        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(encodedBytes));
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();

    }

    public String nimbleGet(String service) throws ClientProtocolException, IOException {

        String url = configFile.getProperty("nimble.url");
        String login = configFile.getProperty("nimble.user");
        String pass = configFile.getProperty("nimble.key");

        byte[] encodedBytes = Base64.encode((login + ":" + pass).getBytes());

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url + service);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(encodedBytes));
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();

    }

    private NimbleUserDataDTO userToNimble(UserEntity user) throws UnexpectedException, JsonParseException, JsonMappingException, ClientProtocolException, IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        NimbleUserDataDTO userNimble = new NimbleUserDataDTO();
        Hibernate.initialize(user.getPerson());
        PersonEntity person = user.getPerson();

        userNimble.setUsuario(user.getLogin());
        //TODO:nimble esta sem a senha
        String password = CriptoUtilsOmega2.encrypt(user.getLogin() + "||" + user.getId());
        userNimble.setNomeCurto(person.getNamePerson());
        userNimble.setNomeCompleto(person.getNamePerson());
        userNimble.setNascimento(dateFormat.format(person.getDateBirth()));
        userNimble.setCpf(person.getCpf());

        List struture = structureBO.findIdsStructureDealershipByUser(user.getId(), false);
        if (struture != null && !struture.isEmpty()) {
            DealershipEntity dealer = structureBO.findDealershipByStructure(((BigDecimal)struture.get(0)).longValue());
            NimbleStructureDataDTO struct = getStruct(dealer.getCnpj());
            userNimble.setCorretora(String.valueOf(struct.getCorretora()));
            userNimble.setEstruturaVendas(String.valueOf(struct.getCodigo()));
         }
        Hibernate.initialize(person.getAddress());
        userNimble.setEndereco(person.getAddress().getAddress() + ", nº:" + person.getAddress().getNumber());
        userNimble.setBairro(person.getAddress().getNeighborhood());
        userNimble.setCidade(person.getAddress().getCity());
        userNimble.setCep(person.getAddress().getPostCode());
        userNimble.setEmailPessoal(person.getEmail());

        for (PhoneEntity phone : person.getListPhone()) {
            if (phone.getPhoneType().equals(PhoneTypeEnum.CELULAR)) {
                userNimble.setFoneResidencial("(" + phone.getDdd() + ")" + phone.getPhone());
            } else {
                userNimble.setFoneAlternativo("(" + phone.getDdd() + ")" + phone.getPhone());
            }
        }

        userNimble.setAtivo(true);
        userNimble.setLinkProprioNimble(false);

        String profile = user.getUserType().getAcronym();
        if ("PROF_SALES_EXECUTIVE".equalsIgnoreCase(profile)) {
            userNimble.setTipoUsuario(1);

        } else if ("PROF_BUSINESS_MANAGER".equalsIgnoreCase(profile)) {
            userNimble.setTipoUsuario(2);

        } else if ("PROF_DEALER_ADMINISTRATOR".equalsIgnoreCase(profile)) {
            userNimble.setTipoUsuario(11);

        } else if ("PROF_CONSULTOR_FI".equalsIgnoreCase(profile)) {
            userNimble.setTipoUsuario(8);
        }

         userNimble.setAcessoAoSistema(false);
        userNimble.setPrivilegioSupervisor(false);

        NimbleUserConfigEnvioEmailDTO email = new NimbleUserConfigEnvioEmailDTO();
        email.setNome(user.getLogin());
        email.setEmail(person.getEmail());
        String ass = "Atenciosamente, " + person.getNamePerson();
        email.setAssinaturaPadrao(ass);
        email.setAssinaturaProposta(ass);
        userNimble.setConfigEnvioEmail(email);

      
        return userNimble;
    }

    public void saveSightSale(String token) {
        //TODO falta o xsd da venda então pode dar alguns problemas      
        // TODO Auto-generated method stub
        
    }

    public List<CarInsuranceEntity> getCanceledQuotes(InsuranceDeniedDTO filters) throws UnexpectedException, CryptoException {

        try {
            CarInsuranceDAO dao = daoFactory(CarInsuranceDAO.class);
            return dao.getCanceled(filters);
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);        
        }
        

//        List<CarInsuranceEntity> list = dao.getCanceled(filters);
//        List<CarInsuranceDTO> retorno = new  ArrayList<CarInsuranceDTO>();
//        
//        
//        for (CarInsuranceEntity carInsuranceEntity : list) {
//            CarInsuranceDTO dto = new CarInsuranceDTO();
//                
//                dto.setDossierId(carInsuranceEntity.getProposal().getId());
//                dto.setAdp(carInsuranceEntity.getProposal().getAdp());
//                dto.setNameSalesman(carInsuranceEntity.getProposal().getDossier().getSalesman().getNamePerson());
//                dto.setNameClient(carInsuranceEntity.getProposal().getDossier().getCustomer().getNameClient());                
//                dto.setCpfCnpj(carInsuranceEntity.getProposal().getDossier().getCustomer().getCpfCnpj());
                //dto.setStatusRevaluation(carInsuranceEntity.getProposal().get);
            
                /*dto.setDossierId(carInsuranceEntity.getProposal().getId());
                dto.setAdp(carInsuranceEntity.getProposal().getAdp());
                dto.setPersonType(carInsuranceEntity.getProposal().getDossier().getCustomer().getPersonType());
                dto.setNameClient(carInsuranceEntity.getProposal().getDossier().getCustomer().getNameClient());
                dto.setdat
                dto.setDateCreationInit(carInsuranceEntity.getProposal().getInitialPeriod());
                dto.setDateCreationEnd(carInsuranceEntity.getProposal().getFinalPeriod());
                dto.setSaleTypeId(carInsuranceEntity.getProposal().getDossier().getSaleType().getDescription());
                dto.setDealership(carInsuranceEntity.getProposal().getDossier().getStructure().getDealership().getDealershipName());
                dto.setDateExpirationInit(carInsuranceEntity.getProposal().getDossier().getExpiryDate());*/
                
            /*dto.setDossierId(carInsuranceEntity.getProposal().getDossier().getId());
            dto.setPersonType(carInsuranceEntity.getProposal().getDossier().getCustomer().getPersonType());
            dto.setNameClient(carInsuranceEntity.getProposal().getDossier().getCustomer().getNameClient());
            dto.setDealership(carInsuranceEntity.getProposal().getDossier().getStructure().getDealership().getDealershipName());
            dto.setDateCreationEnd(carInsuranceEntity.getProposal().getDossier().getStructure().getChangeDate());
            dto.setNameSalesman(carInsuranceEntity.getProposal().getDossier().getSalesman().getNamePerson());*/
            //            dto.setDateCreationInit(dateCreationInit);
            //            dto.setDateExpirationInit(dateExpirationInit);
            //            dto.setDateExpirationEnd(dateExpirationEnd);
            /*dto.setVehicleModel(carInsuranceEntity.getVehicle());
            dto.setAdp(carInsuranceEntity.getProposal().getDossier().getAdp());*/
     
    }

}
