package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.SantanderReturnMessageDao;
import com.rci.omega2.ejb.dto.MailDTO;
import com.rci.omega2.ejb.exception.SantanderBusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.EmailEnum;
import com.rci.omega2.entity.DossierEntity;
import com.rci.omega2.entity.SantanderReturnMessageEntity;
import com.rci.omega2.entity.util.enumation.ParamEnum;

@Stateless
public class SantanderReturnErrorBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(SantanderEmployeeTypeBO.class);
    
    @EJB
    private DossierBO dossierBO;
    
    @EJB
    private SendMailBO sendMailBO;
    
    @EJB
    private ParamBO paramBO;
    
    
    /**
     * 
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    private SantanderReturnMessageEntity findSantanderEmployeeTypeByImportCode(String code) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSantanderEmployeeTypeByImportCode ");
            
            SantanderReturnMessageDao dao = daoFactory(SantanderReturnMessageDao.class);
            SantanderReturnMessageEntity temp = dao.findSantanderReturnMessageByCode(code);
            
            LOGGER.debug(" >> END findSantanderEmployeeTypeByImportCode ");
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
     * @param erro
     * @throws Exception
     */
    private void save(SantanderReturnMessageEntity erro) throws Exception {
        LOGGER.debug(" >> INIT save ");
        SantanderReturnMessageDao dao = daoFactory(SantanderReturnMessageDao.class);
         dao.save(erro);
         
         LOGGER.debug(" >> END save ");
         return;
        
    }
    
    /**
     * 
     * @param codigoErro
     * @param descricaoErro
     * @throws UnexpectedException
     */
    public void handlerSantanderBusinessException(String codigoErro, String descricaoErro) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT handlerSantanderBusinessException ");
        SantanderReturnMessageEntity erro = findSantanderEmployeeTypeByImportCode(codigoErro);
        if (erro == null) {
            erro = new SantanderReturnMessageEntity();
            erro.setKey(codigoErro);
            erro.setMessage(descricaoErro);
            try {
                LOGGER.debug(" >> END handlerSantanderBusinessException ");
                save(erro);
            } catch (UnexpectedException e) {
                throw e;
            } catch (Exception e) {
                LOGGER.error(AppConstants.ERROR, e);
                throw new UnexpectedException(e);
            }
        }
        throw new SantanderBusinessException(codigoErro + " - " + descricaoErro);
        
        
    }
    
    /**
     * 
     * @param adp
     * @param codigoErro
     * @param descricaoErro
     * @throws UnexpectedException
     */
    public void handlerSantanderBusinessException(String adp, String codigoErro, String descricaoErro) throws UnexpectedException { 
        LOGGER.error(AppConstants.ERROR, codigoErro + " - " + descricaoErro);
        LOGGER.debug(" >> INIT handlerSantanderBusinessException ");
        /* Muda o status da dossier para mudanca de concessionaria e envia e-mail */
        if(AppConstants.SANTANDER_ERROR_CODE_CHANGED_CONCESS_TAB
                                            .equalsIgnoreCase(codigoErro)){
            DossierEntity dossier = dossierBO.findDossierByAdp(adp);
            dossier.setStructureChanged(Boolean.TRUE);
            MailDTO mailDto = new MailDTO();
            mailDto.setSubject(AppConstants.MAIL_SUBJECT_CHANGED_CONCESS_TAB);
            mailDto.setContent(AppConstants.MAIL_BODY_CHANGED_CONCESS_TAB);
            List<String> emails = new ArrayList<String>();
            emails.add(paramBO.findParam(ParamEnum.EMAIL_RCI_PARAM_CODE).getParamLabel());
            mailDto.setDestinationList(emails);
            mailDto.setEmailEnum(EmailEnum.RCI);
            sendMailBO.sendMessage(mailDto);
            
        }
        
        this.handlerSantanderBusinessException(codigoErro, descricaoErro); 
        LOGGER.debug(" >> END handlerSantanderBusinessException ");
    }
    
}
