package com.rci.omega2.ejb.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = false)
public class SantanderBusinessException extends BusinessException {

    private static final long serialVersionUID = 9676234923L;

    public SantanderBusinessException(Exception ex) {

        super(ex);
    }

    public SantanderBusinessException(String msg) {
        super(msg);
    }

}
