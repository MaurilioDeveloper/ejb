package com.rci.omega2.ejb.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.NotificationListDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;

public class NotificationDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(NotificationDAO.class);

    public List<NotificationListDTO> findNotificationsListByUser(Long userId, Long idNotification)
            throws UnexpectedException, SQLException, IOException {

        try {
            LOGGER.debug(" >> INIT findNotificationsListByUser ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("    SELECT N.DT_INSERT, D.IMPORT_CODE_OMEGA, P.ADP, C.CPF_CNPJ,            ");
            sql.append("        C.NAME_CLIENT, S.DESCRIPTION,                                      ");
            sql.append("        PE.NAME_PERSON, N.NOTIFICATION_ID, N.READ, N.USER_ID,              ");
            sql.append("        D.DOSSIER_ID, P.PROPOSAL_ID                                        ");
            sql.append("    FROM TB_NOTIFICATION N                                                 ");
            sql.append("      INNER JOIN TB_USER U ON N.USER_ID = U.USER_ID                        ");
            sql.append("      INNER JOIN TB_PROPOSAL P ON N.PROPOSAL_ID = P.PROPOSAL_ID            ");
            sql.append("      INNER JOIN TB_DOSSIER D ON D.DOSSIER_ID = P.DOSSIER_ID               ");
            sql.append("      INNER JOIN TB_CUSTOMER C ON C.CUSTOMER_ID = D.CUSTOMER_ID            ");
            sql.append("      INNER JOIN TB_DOSSIER_STATUS S                                       ");
            sql.append("                ON S.DOSSIER_STATUS_ID = N.DOSSIER_STATUS_ID               ");
            sql.append("      INNER JOIN TB_PERSON PE ON D.PERSON_ID_SALESMAN = PE.PERSON_ID       ");
            sql.append("    WHERE U.USER_ID = (:userId)                                            ");

            if (idNotification != null) {
                sql.append("    AND N.NOTIFICATION_ID = (:idNotification)                          ");
            }
            sql.append("    ORDER BY N.READ ASC, N.DT_UPDATE DESC                                  ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());
            query.setParameter("userId", userId);

            if (idNotification != null) {
                query.setParameter("idNotification", idNotification);
            }

            @SuppressWarnings("unchecked")
            List<Object[]> result = query.getResultList();
            List<NotificationListDTO> notifications = new ArrayList<NotificationListDTO>();

            for (Object[] data : result) {
                NotificationListDTO dto = new NotificationListDTO();
                dto.setChangeData((Date) data[0]); 
                if(data[1] == null){
                    dto.setProposal(data[11].toString());
                } else {
                    dto.setProposal(data[1].toString());
                }
                if (data[2] != null) {
                    dto.setProposalAdpNumber(Integer.valueOf(data[2].toString()));
                }
                dto.setCpfCnpj(data[3].toString());
                dto.setClientName(data[4].toString());
                dto.setStatus(data[5].toString());
                dto.setSalesman(data[6].toString());
                if (data[7] != null) {
                    dto.setId(CriptoUtilsOmega2.encrypt(data[7].toString()));
                }
                dto.setRead(Integer.valueOf(data[8].toString()));
                dto.setUserId(data[9].toString());
                dto.setDossierId(CriptoUtilsOmega2.encrypt(data[10].toString()));
                notifications.add(dto);
            }

            LOGGER.debug(" >> END findNotificationsListByUser ");
            return notifications;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
