package com.rci.omega2.ejb.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ForbiddenException extends UnexpectedException {

	private static final long serialVersionUID = 9676234923L;

	public ForbiddenException() {
	    super();
	}
	
	public ForbiddenException(Exception ex) {
		super(ex);
	}
	
	public ForbiddenException(String message) {
		super(message);
	}
}
