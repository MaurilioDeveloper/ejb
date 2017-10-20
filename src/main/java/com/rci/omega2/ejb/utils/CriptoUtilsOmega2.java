package com.rci.omega2.ejb.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.crypt.decrypt.CriptoUtils;
import com.rci.omega2.ejb.exception.UnexpectedException;

public class CriptoUtilsOmega2 {

    private static final Logger LOGGER = LogManager.getLogger(CriptoUtilsOmega2.class);
    
    public static String encrypt(String str) throws UnexpectedException {
        try {
            return CriptoUtils.encrypt(str);
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public static String encrypt(Long str) throws UnexpectedException {
        return encrypt(String.valueOf(str));
    }

    public static String encrypt(Integer str) throws UnexpectedException {
        return encrypt(String.valueOf(str));
    }

    public static String decrypt(String str) throws UnexpectedException {
        try {
            return CriptoUtils.decrypt(str);
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public static Long decryptIdToLong(String id) throws UnexpectedException {
        try {
            if (id == null) {
                return null;
            }
            return Long.parseLong(decrypt(id));
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}
