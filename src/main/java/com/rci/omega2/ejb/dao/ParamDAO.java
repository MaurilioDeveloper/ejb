package com.rci.omega2.ejb.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ParamEntity;
import com.rci.omega2.entity.util.enumation.ParamEnum;

public class ParamDAO extends BaseDAO {

    private static final Logger LOGGER = LogManager.getLogger(ParamDAO.class);

    public ParamEntity find(ParamEnum param) throws UnexpectedException {

        LOGGER.debug(" >> INIT find ");
        ParamEntity entity = null;

        try {
            entity = super.find(ParamEntity.class, param.getId());
            LOGGER.debug(" >> END find ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

        return entity;
    }

}
