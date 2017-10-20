package com.rci.omega2.ejb.dto;

import java.io.Serializable;

public class MailAttachDTO implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String attachType;
	private String attachName;
	private byte[] attach;

	public MailAttachDTO(byte[] attach, String attachName, String attachType) {
		this.attachName = attachName;
		this.attach = attach;
		this.attachType = attachType;
	}
	
	public byte[] getAttach() {
		return attach;
	}
	public void setAttach(byte[] attach) {
		this.attach = attach;
	}
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
}
