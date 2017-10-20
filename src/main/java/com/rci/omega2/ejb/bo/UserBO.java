package com.rci.omega2.ejb.bo;

import static com.rci.omega2.ejb.utils.CriptoUtilsOmega2.decryptIdToLong;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import com.rci.omega2.common.crypt.decrypt.CriptoUtils;
import com.rci.omega2.ejb.dao.UserDAO;
import com.rci.omega2.ejb.dao.UserTokenDAO;
import com.rci.omega2.ejb.dto.MailDTO;
import com.rci.omega2.ejb.dto.RoleFunctionDTO;
import com.rci.omega2.ejb.dto.UserDTO;
import com.rci.omega2.ejb.dto.ws.FilterUserDTO;
import com.rci.omega2.ejb.dto.ws.UserWsDTO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.EmailEnum;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.entity.DealershipEntity;
import com.rci.omega2.entity.UserEntity;
import com.rci.omega2.entity.UserTokenEntity;

@Stateless
public class UserBO extends BaseBO {
    private static final Logger LOGGER = LogManager.getLogger(UserBO.class);

    @EJB
    private SendMailBO sendMailBO;

    public UserEntity findOne(String username) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findOne ");
            UserDAO dao = daoFactory(UserDAO.class);
            UserEntity user = dao.findOne(username);
            if (user != null) {
                return user;
            }
            LOGGER.debug(" >> END findOne ");
            return null;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public UserEntity findOne(Long id) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findOne ");
            UserDAO dao = daoFactory(UserDAO.class);
            UserEntity temp = dao.find(UserEntity.class, id);
            LOGGER.debug(" >> END findOne ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public boolean functionValidation(Long userId, Long functionId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT functionValidation ");
            UserDAO dao = daoFactory(UserDAO.class);
            boolean temp = dao.functionValidation(userId, functionId);
            LOGGER.debug(" >> END functionValidation ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public void updateUser(UserEntity user) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT updateUser ");
            UserDAO dao = daoFactory(UserDAO.class);
            dao.update(user);
            LOGGER.debug(" >> END updateUser ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public void requestToken(String userEmail, String path, String theme) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT requestToken ");
            UserDAO dao = daoFactory(UserDAO.class);
            UserEntity userEntity;
            if (GeneralUtils.isAnEmail(userEmail)) {
                userEntity = dao.findByEmail(userEmail);
            } else {
                userEntity = dao.findOne(userEmail);
            }

            if (userEntity == null) {
                throw new BusinessException("msg-invalid-user-email");
            }

            // Envia o Token por e-mail
            MailDTO mailDto = new MailDTO();
            mailDto.setContent(this.getHtmlForgetPassword(userEntity, theme, path));

            mailDto.setSubject("Link para reset de senha");
            String email = userEntity.getPerson().getEmail();
            List<String> emails = new ArrayList<String>();
            emails.add(email);
            mailDto.setDestinationList(emails);
            mailDto.setEmailEnum(EmailEnum.RCI);
            sendMailBO.sendMessage(mailDto);
            LOGGER.debug(" >> END requestToken ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public String verifyToken(String idUser, String token) throws UnexpectedException {
        Long idUserDecript = Long.valueOf(CriptoUtilsOmega2.decrypt(idUser));

        LOGGER.debug(" >> INIT verifyToken ");
        try {
            UserTokenDAO daoToken = daoFactory(UserTokenDAO.class);
            UserTokenEntity entityToken = daoToken.findTokenByUser(idUserDecript);
            if (entityToken.getId() == null) {
                throw new BusinessException("msg-invalid-token");
            }
            if (!entityToken.getToken().equals(token) || entityToken.getChangeDate() != null) {
                throw new BusinessException("msg-invalid-token");
            }

            Date dateOneDayAfter = GeneralUtils.addDayToDate(1, entityToken.getIncludeDate());
            if (dateOneDayAfter.before(new Date())) {
                throw new BusinessException("msg-invalid-token");
            }
            String idTokenCript = CriptoUtilsOmega2.encrypt(entityToken.getId());
            LOGGER.debug(" >> END verifyToken ");
            return idTokenCript;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public void resetPassword(Long idUser, Long idToken, String newPassword) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT resetPassword ");
            UserDAO dao = daoFactory(UserDAO.class);
            UserTokenDAO daoToken = daoFactory(UserTokenDAO.class);

            UserEntity userEntity = dao.find(UserEntity.class, idUser);
            userEntity.setPassword(newPassword);
            updateUser(userEntity);

            UserTokenEntity tokenEntity = daoToken.findTokenByUser(idUser);
            tokenEntity.setChangeDate(new Date());
            daoToken.save(tokenEntity);
            LOGGER.debug(" >> END resetPassword ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<UserDTO> findUserStructure(Long userId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findUserStructure ");
            UserDAO dao = daoFactory(UserDAO.class);
            List<UserDTO> temp = dao.findUserStructure(userId);
            LOGGER.debug(" >> END findUserStructure ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public void updateSubmitUser(UserDTO userDto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT updateSubmitUser ");
            UserDAO dao = daoFactory(UserDAO.class);
            Long userId = decryptIdToLong(userDto.getUserid());

            if (BigDecimal.ONE.equals(userDto.getDossierSubmitAllowed())) {
                dao.saveSubmitRoleUser(userId);
            } else {
                dao.deleteSubmitRoleUser(userId);
            }
            LOGGER.debug(" >> END updateSubmitUser ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<RoleFunctionDTO> findRoleEntity(Long userId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findRoleEntity ");
            UserDAO dao = daoFactory(UserDAO.class);
            List<RoleFunctionDTO> temp = dao.findRoleEntity(userId);
            LOGGER.debug(" >> END findRoleEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private String getHtmlForgetPassword(UserEntity userEntity, String theme, String url) throws UnexpectedException {
        LOGGER.debug(" >> INIT getHtmlForgetPassword ");
        StringBuilder html = new StringBuilder();

        InputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("forgetPassword/email-" + theme + ".html");

            reader = new BufferedReader(new InputStreamReader(fis));

            // Cria o token
            String token = UUID.randomUUID().toString();
            UserTokenEntity tokenEntity = new UserTokenEntity();
            UserTokenDAO daoToken = daoFactory(UserTokenDAO.class);
            tokenEntity.setToken(token);
            tokenEntity.setUser(userEntity);
            tokenEntity.setIncludeDate(new Date());
            daoToken.save(tokenEntity);

            String userId = CriptoUtils.encrypt(userEntity.getId());

            String line = reader.readLine();
            while (line != null) {
                line = line.replace(AppConstants.KEY_NAME, userEntity.getPerson().getNamePerson());
                line = line.replace(AppConstants.KEY_CLICK_HERE, url + "/#/reset_password/" + token + "/" + userId);
                line = line.replace(AppConstants.KEY_IMG_LOGO,
                        url + AppConstants.IMG_FOLDER + "logo-mail-" + theme + ".jpg");
                line = line.replace(AppConstants.KEY_URL, url + "/#/reset_password/" + userId + "/" + token);

                html.append(line);
                line = reader.readLine();
            }
            LOGGER.debug(" >> END getHtmlForgetPassword ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

        return html.toString();
    }

    public UserEntity findByPerson(Long personId) throws UnexpectedException {
        LOGGER.debug(" >> INIT findByPerson ");
        UserDAO dao = daoFactory(UserDAO.class);
        UserEntity user = dao.findByPerson(personId);
        LOGGER.debug(" >> END findByPerson ");
        return user;
    }

    public List<UserWsDTO> getUsersByFilter(FilterUserDTO filters) throws UnexpectedException {
        UserDAO dao = daoFactory(UserDAO.class);
        List<UserEntity> users = dao.findAllUsingFilters(filters);
        List<UserWsDTO> list = new ArrayList<UserWsDTO>();
        for (UserEntity userEntity : users) {
            UserWsDTO userReturn = new UserWsDTO();
            Hibernate.initialize(userEntity.getPerson());
            String password = CriptoUtilsOmega2.encrypt(userEntity.getLogin() + "||" + userEntity.getId());
            if (userEntity.getPerson() != null) {
                userReturn.setCpf(userEntity.getPerson().getCpf());
                if(!userEntity.getListStructure().isEmpty()){
                    DealershipEntity dealership = userEntity.getListStructure().iterator().next().getDealership();
                    if(dealership != null){
                        userReturn.setCnpj(dealership.getCnpj());
                    }
                }
                userReturn.setName(userEntity.getPerson().getNamePerson());
            }
            userReturn.setLogin(userEntity.getLogin());
            userReturn.setPassword(password);
            userReturn.setActive(userEntity.getActive());
            list.add(userReturn);
        }

        return list;
    }

}
