package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.ProposalDAO;
import com.rci.omega2.ejb.dao.jdbc.ProposalJdbcDAO;
import com.rci.omega2.ejb.dto.MailAttachDTO;
import com.rci.omega2.ejb.dto.MailDTO;
import com.rci.omega2.ejb.dto.ProposalEmailDTO;
import com.rci.omega2.ejb.dto.SendProposalReportDTO;
import com.rci.omega2.ejb.dto.SendProposalSimulationReportDTO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.EmailEnum;
import com.rci.omega2.entity.ProposalEntity;

@Stateless
public class ProposalBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(ProposalBO.class);

    @EJB
    private SendMailBO sendMailBO;

    public void sendProposalByEmail(Long dossierId, byte[] attachment) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT sendProposalByEmail ");

            ProposalJdbcDAO dao = daoJdbcFactory(ProposalJdbcDAO.class);
            ProposalEmailDTO dto = dao.findEmailInformationByDossier(dossierId);

            MailDTO mailDto = new MailDTO();
            mailDto.setSubject(dto.getSimulationStatus() + " " + dto.getSimulationNumber());
            mailDto.setContent(getMailContetnt(dto));
            List<String> emails = new ArrayList<String>();
            emails.add(dto.getClientEmail());
            mailDto.setDestinationList(emails);
            mailDto.setEmailEnum(EmailEnum.RCI);

            MailAttachDTO attachmentDto = new MailAttachDTO(attachment, "proposal.pdf", "application/pdf");
            List<MailAttachDTO> listAttachment = new ArrayList<MailAttachDTO>();
            listAttachment.add(attachmentDto);            
            mailDto.setAttachments(listAttachment);

            sendMailBO.sendMessage(mailDto);

            LOGGER.debug(" >> END sendProposalByEmail ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private String getMailContetnt(ProposalEmailDTO dto) {
        LOGGER.debug(" >> INIT getMailContetnt ");

        StringBuilder msg = new StringBuilder();
        msg.append("Caro Senhor/Senhora <br><br>" + "Segue em anexo o documento solicitado. <br><br>");
        msg.append(dto.getSimulationStatus() + " " + dto.getSimulationNumber() + "<br><br>");
        msg.append("Caso necessite alguma informação adicional, por favor, entre em contato conosco.<br><br>");
        msg.append("Cordialmente<br><br>" + dto.getSalesmanName() + "<br>" + dto.getSalesmanEmail() + "<br>");
        msg.append(dto.getDealershipName());
        
        String temp = msg.toString();
        
        LOGGER.debug(" >> END getMailContetnt ");
        return temp;
    }

    public List<Map<String, Object>> generateProposalReportData(Long dossierId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT generateProposalReportData ");
            Map<String, Object> values = new HashMap<String, Object>();
            ProposalJdbcDAO dao = daoJdbcFactory(ProposalJdbcDAO.class);
            SendProposalReportDTO dto = dao.findDataReportFromDossier(dossierId);
            List<SendProposalSimulationReportDTO> listSimulationDto = dao.findSimulationsFromDossier(dossierId);

            if(dto==null || listSimulationDto==null){
                throw new BusinessException("Erro ao gerar documento da proposta");
            }

            dto.setSimulations(listSimulationDto);

            dto.setFooterMessage(getReportFooterMessage());

            values.put("sendProposal", dto);
            List<Map<String, Object>> listReturn = new ArrayList<Map<String, Object>>();
            listReturn.add(values);

            LOGGER.debug(" >> END generateProposalReportData ");
            return listReturn;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private String getReportFooterMessage(){

        LOGGER.debug(" >> INIT getReportFooterMessage ");

        StringBuilder msg = new StringBuilder();
        msg.append("Os dados contidos neste documento são meramente informativos e não constituem compromisso e/ou obrigação entre e/ou \n");
        msg.append("para as partes.\n");
        msg.append("Os valores que compõem a parcela serão detalhados na CET, no ato da efetiva contratação pelo cliente, conforme a opção \n");
        msg.append("que melhor lhe convier. \n");
        msg.append("Esta simulação é meramente ilustrativa e poderá ser alterada a qualquer momento, sem aviso e/ou notificação ao cliente. \n");
        msg.append("Esta simulação é válida por 01 (um) dia útil.");

        String temp = msg.toString();
        LOGGER.debug(" >> END getReportFooterMessage ");
        
        return temp;
    }
    /**
     * 
     * @param adp
     * @return
     * @throws UnexpectedException
     */
    public String getTabCode(String adp) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT getTabCode ");

            ProposalDAO dao = daoFactory(ProposalDAO.class);
            String temp = dao.getTabCode(adp);

            LOGGER.debug(" >> END getTabCode ");
            return temp;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public ProposalEntity findProposalCompleted(Long dossierId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProposalCompleted ");

            ProposalDAO dao = daoFactory(ProposalDAO.class);
            ProposalEntity temp = dao.findProposalCompleted(dossierId);

            LOGGER.debug(" >> INIT findProposalCompleted ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public ProposalEntity findProposalCompletedToPrint(Long dossierId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProposalCompletedToPrint ");

            ProposalDAO dao = daoFactory(ProposalDAO.class);
            ProposalEntity proposal = dao.findProposalCompletedToPrint(dossierId);

            LOGGER.debug(" >> END findProposalCompletedToPrint ");
            return proposal;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    /**
     * 
     * @param adp
     * @return
     * @throws UnexpectedException
     */
    public ProposalEntity findProposalByAdp(String adp) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProposalByAdp ");

            ProposalDAO dao = daoFactory(ProposalDAO.class);
            ProposalEntity temp = dao.findProposalByAdp(adp);

            LOGGER.debug(" >> END findProposalByAdp ");

            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    /**
     * 
     * @param proposal
     * @return
     * @throws UnexpectedException
     */
    public ProposalEntity update(ProposalEntity proposal)throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT update ");

            ProposalDAO dao = daoFactory(ProposalDAO.class);
            ProposalEntity temp = dao.update(proposal);

            LOGGER.debug(" >> END update ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }


}
