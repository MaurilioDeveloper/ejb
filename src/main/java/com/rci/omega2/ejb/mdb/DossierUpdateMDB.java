package com.rci.omega2.ejb.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.bo.DossierBO;
import com.rci.omega2.ejb.utils.AppConstants;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/dossierUpdateQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class DossierUpdateMDB implements MessageListener {

    private static final Logger LOGGER = LogManager.getLogger(DossierUpdateMDB.class);
    
    @EJB
    private DossierBO dossierBO;
    
    @Override
    public void onMessage(Message message) {
        ObjectMessage objMessage = (ObjectMessage) message;
        try {
            dossierBO.updateDossierFromSantander(String.valueOf(objMessage.getObject()), AppConstants.SYSTEM_OMEGA_USER);
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
        }

    }

}
