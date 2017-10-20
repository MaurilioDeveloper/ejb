package com.rci.omega2.ejb.mdb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.MailAttachDTO;
import com.rci.omega2.ejb.dto.MailDTO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/mailQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class MailMDB implements MessageListener {

    private static final Logger LOGGER = LogManager.getLogger(MailMDB.class);

    @Resource(mappedName = "java:jboss/amazon/RCIMail")
    private Session mailSession;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objMessage = (ObjectMessage) message;
        try {
            sendMail((MailDTO) objMessage.getObject());
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
        }

    }

    /**
     * 
     * @param mailDTO
     * @throws BusinessException
     * @throws UnexpectedException
     */
    private void sendMail(MailDTO mailDTO) throws UnexpectedException {
        LOGGER.debug("===== Iniciando metodo sendMail =====");
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(mailDTO.getEmailEnum().getMailSender());

            Address[] to = new InternetAddress[mailDTO.getDestinationList().size()];
            for (int i = 0; i < mailDTO.getDestinationList().size(); i++) {
                to[i] = new InternetAddress(mailDTO.getDestinationList().get(i));
            }

            m.setFrom(from);
            m.setRecipients(javax.mail.Message.RecipientType.TO, to);
            m.setSubject(mailDTO.subject);
            m.setSentDate(Calendar.getInstance(new Locale("pt", "BR")).getTime());

            if (mailDTO.getAttachments() != null) {
                attach(m, mailDTO);
            } else {
                m.setContent(mailDTO.getContent(), AppConstants.MAIL_HTML_TYPE);
            }

            LOGGER.debug("===== Enviando mensagem... =====");
            Transport.send(m);
            LOGGER.debug("===== Mensagem enviada =====");

        } catch (MessagingException e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void attach(MimeMessage msg, MailDTO mailDTO) throws UnexpectedException {

        try {
            Multipart mp = new MimeMultipart();

            if (mailDTO.getContent() != null) {
                MimeBodyPart bp = new MimeBodyPart();
                bp.setContent(mailDTO.getContent(), AppConstants.MAIL_HTML_TYPE);
                mp.addBodyPart(bp);
            }

            for (MailAttachDTO file : mailDTO.getAttachments()) {
                InputStreamDataSourceDTO attach = new InputStreamDataSourceDTO();

                attach.setName(file.getAttachName());
                attach.setContentType(file.getAttachType());
                attach.setBytes(file.getAttach());

                MimeBodyPart bp = new MimeBodyPart();
                bp.setDataHandler(new DataHandler(attach));
                bp.setFileName(attach.getName());

                mp.addBodyPart(bp);
            }

            msg.setContent(mp, AppConstants.MAIL_HTML_TYPE);

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
   private class InputStreamDataSourceDTO implements DataSource {

        private String name;
        private String contentType;
        private byte[] bytes;

        public String getContentType() {
            return contentType;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(bytes);
        }

        public String getName() {
            return name;
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Cannot write to this read-only resource");
        }

        @SuppressWarnings("unused")
        public byte[] getBytes() {
            return bytes == null ? null : bytes.clone();
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes == null ? null : bytes.clone();
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

    }

}
