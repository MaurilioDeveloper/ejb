package com.rci.omega2.ejb.dao;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.NoticeDTO;
import com.rci.omega2.ejb.dto.NoticeListDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.entity.NoticeEntity;

public class NoticeDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(NoticeDAO.class);

    @SuppressWarnings("unchecked")
    public List<NoticeListDTO> findNoticesListByUser(Long idFinancialBrand) throws UnexpectedException, SQLException, IOException {
        
        try {
            LOGGER.debug(" >> INIT findNoticesListByUser ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("     SELECT DISTINCT T1.NOTICE_ID, T1.TITLE, T1.DT_INSERT        ");
            sql.append("     FROM TB_NOTICE T1                                           ");
            sql.append("     INNER JOIN TB_NOTICE_FINANCIAL_BRAND T2                     ");
            sql.append("     ON T1.NOTICE_ID = T2.NOTICE_ID                              ");
            sql.append("     WHERE T2.FINANCIAL_BRAND_ID = (:idFinancialBrand)           ");
            sql.append("     AND ROWNUM <= 5                                             ");
            sql.append("     AND T1.ACTIVE = 1                                           ");
            sql.append("     ORDER BY T1.DT_INSERT DESC                                  ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());

            query.setParameter("idFinancialBrand", idFinancialBrand);

            List<Object[]> structures = query.getResultList();
            List<NoticeListDTO> notices = new ArrayList<NoticeListDTO>();

            for (Object[] data : structures) {
                NoticeListDTO dto = new NoticeListDTO();
                String idCript = CriptoUtilsOmega2.encrypt(data[0].toString());
                dto.setId(idCript);
                dto.setTitle(data[1].toString());
                dto.setDate((Date) data[2]);
                notices.add(dto);
            }

            LOGGER.debug(" >> END findNoticesListByUser ");
            return notices;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
            
    }
    
    public NoticeDTO findNoticeById(Long noticeId) throws UnexpectedException, SQLException, IOException {

        try {
            LOGGER.debug(" >> INIT findNoticeById ");
            
            Query query = super.getEntityManager().createQuery("    select n from NoticeEntity n where n.id= :noticeId  ");
            query.setParameter("noticeId", noticeId);

            NoticeEntity notice = (NoticeEntity) query.getResultList().get(0);
            NoticeDTO dto = new NoticeDTO();

            String idEncript = CriptoUtilsOmega2.encrypt(notice.getId());
            dto.setId(idEncript);
            dto.setTitle(notice.getTitle());
            dto.setDtInsert(notice.getIncludeDate());
            dto.setNotice(notice.getNotice());

            LOGGER.debug(" >> END findNoticeById ");
            return dto;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
    public void inativeNotice(NoticeEntity notice) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT inativeNotice ");
            
            Query query = super.getEntityManager().createNativeQuery(
                    " UPDATE TB_NOTICE A " + " SET A.ACTIVE = 0 " + " WHERE A.NOTICE_ID = " + notice.getId());

            query.executeUpdate();

            LOGGER.debug(" >> END inativeNotice ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
    @SuppressWarnings("unchecked")
    public List<NoticeDTO> findAllNotices() throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findAllNotices ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT nt.NOTICE_ID, nt.TITLE, nt.NOTICE, nt.PUBLISHED, nt.DT_INSERT, prs.NAME_PERSON      ");
            sql.append(" FROM TB_NOTICE nt                                                                          ");
            sql.append(" LEFT OUTER JOIN TB_USER usr ON nt.USER_ID = usr.USER_ID                                    ");
            sql.append(" LEFT OUTER JOIN TB_PERSON prs ON usr.USER_ID = prs.USER_ID                                 ");
            sql.append(" WHERE nt.ACTIVE = 1                                                                        ");
            sql.append(" ORDER BY nt.DT_INSERT ASC                                                                  ");

            Query query = super.getEntityManager().createNativeQuery(sql.toString());

            List<Object[]> structures = query.getResultList();
            List<NoticeDTO> notices = new ArrayList<NoticeDTO>();

            for (Object[] data : structures) {
                NoticeDTO dto = new NoticeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(data[0].toString()));
                dto.setTitle(data[1].toString());
                Clob clob = (Clob) data[2];
                dto.setNotice(GeneralUtils.convertClobToString(clob));
                dto.setPublished((boolean) data[3].toString().equals("0") ? false : true);
                dto.setDtInsert((Timestamp) data[4]);
                dto.setName(data[5].toString());
                notices.add(dto);
            }

            LOGGER.debug(" >> END findAllNotices ");
            return notices;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
}
