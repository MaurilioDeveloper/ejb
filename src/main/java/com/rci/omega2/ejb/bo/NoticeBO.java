package com.rci.omega2.ejb.bo;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.NoticeDAO;
import com.rci.omega2.ejb.dto.NoticeDTO;
import com.rci.omega2.ejb.dto.NoticeListDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.FinancialBrandEntity;
import com.rci.omega2.entity.NoticeEntity;

@Stateless
public class NoticeBO extends BaseBO {

    @EJB
    private StructureBO structureBO;

    @EJB
    private UserBO userBO;

    @EJB
    private FinancialBrandBO financialBrandBO;

    private static final Logger LOGGER = LogManager.getLogger(PersonBO.class);

    public List<NoticeListDTO> findNotices(Long idFinancialBrand) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findNotices ");
            
            NoticeDAO dao = daoFactory(NoticeDAO.class);
            List<NoticeListDTO> resultNotices = dao.findNoticesListByUser(idFinancialBrand);
            
            LOGGER.debug(" >> END findNotices ");
            return resultNotices;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public NoticeDTO findNoticeById(Long noticeId) throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findNoticeById ");
            
            NoticeDAO dao = daoFactory(NoticeDAO.class);
            NoticeDTO notice = dao.findNoticeById(noticeId);
            
            LOGGER.debug(" >> END findNoticeById ");
            return notice;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public void insertNotice(NoticeEntity noticeEntity, Long idUser, Set<Long> idsFinancialBrands)
            throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findNoticeById ");
            
            Set<FinancialBrandEntity> result = financialBrandBO.findFinancialBrandsById(idsFinancialBrands);

            noticeEntity.setListFinancialBrand(result);

            noticeEntity.setUser(userBO.findOne(idUser));

            NoticeDAO dao = daoFactory(NoticeDAO.class);
            dao.save(noticeEntity);

            LOGGER.debug(" >> END findNoticeById ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    public void updateNotice(NoticeEntity noticeEntity, Set<Long> idsFinancialBrands) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT updateNotice ");
            
            NoticeDAO dao = daoFactory(NoticeDAO.class);
            NoticeEntity entity = dao.find(NoticeEntity.class, noticeEntity.getId());

            Set<FinancialBrandEntity> result = financialBrandBO.findFinancialBrandsById(idsFinancialBrands);

            entity.setTitle(noticeEntity.getTitle());
            entity.setNotice(noticeEntity.getNotice());
            entity.setChangeDate(noticeEntity.getChangeDate());
            entity.setPublished(noticeEntity.getPublished());
            entity.setListFinancialBrand(result);

            dao.update(entity);
            
            LOGGER.debug(" >> END updateNotice ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<NoticeDTO> findAllNotices() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllNotices ");
            
            NoticeDAO dao = daoFactory(NoticeDAO.class);
            List<NoticeDTO> resultNotices = dao.findAllNotices();
            
            LOGGER.debug(" >> END findAllNotices ");
            return resultNotices;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public void deleteNotice(String idNotice) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT deleteNotice ");
            
            NoticeDAO dao = daoFactory(NoticeDAO.class);
            NoticeEntity entity = dao.find(NoticeEntity.class, Long.parseLong(idNotice));
            dao.inativeNotice(entity);
            
            LOGGER.debug(" >> END deleteNotice ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

}
