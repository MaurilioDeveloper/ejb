package com.rci.omega2.ejb.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BusinessException extends UnexpectedException {

	private static final long serialVersionUID = 9676234923L;

	public BusinessException(Exception ex) {
		super(ex);
	}
	
	public BusinessException(String message) {
		super(message);
	}
}
