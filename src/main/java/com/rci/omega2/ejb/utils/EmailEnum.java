package com.rci.omega2.ejb.utils;

public enum EmailEnum {
	RENAULT("noreply@portal.bancorenault.com.br"), NISSAN("noreply@portal.credinissan.com.br"), RCI("noreply@rcidirect.com.br");
	
	private String mailSender;
	
	private EmailEnum(String mailSender){
		this.mailSender = mailSender;
	}
	
	public String getMailSender(){
		return this.mailSender;
	}
	
}
