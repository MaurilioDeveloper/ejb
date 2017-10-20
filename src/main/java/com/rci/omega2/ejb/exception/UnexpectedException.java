package com.rci.omega2.ejb.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class UnexpectedException extends Exception{

	private static final long serialVersionUID = 9676234923L;

	public UnexpectedException(){
	    super();
	}
	
	public UnexpectedException(Exception ex) {
		super(ex);
	}
	
	public UnexpectedException(String message) {
		super(message);
	}
	
}
