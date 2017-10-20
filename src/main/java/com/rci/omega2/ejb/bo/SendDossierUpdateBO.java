package com.rci.omega2.ejb.bo;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jws.WebMethod;
import javax.ws.rs.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;


@Stateless
public class SendDossierUpdateBO extends BaseBO{

    private static final Logger LOGGER = LogManager.getLogger(SendDossierUpdateBO.class);

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connFactory;

    @Resource(mappedName = "java:/jms/queue/dossierUpdateQueue")
    private Queue queue;

    @WebMethod
    public void sendDossierUpdate(@PathParam("adp")String adp) throws UnexpectedException {
        Connection connect = null;
        try {
            LOGGER.debug(" >> INIT sendDossierUpdate ");
            connect = connFactory.createConnection();
            Session session = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);

            ObjectMessage objMsg = session.createObjectMessage();
            objMsg.setObject(adp);

            producer.send(objMsg);
            LOGGER.debug(" >> END sendDossierUpdate ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (JMSException e) {
                    LOGGER.error(AppConstants.ERROR, e);
                }
            }
        }
    }

}
