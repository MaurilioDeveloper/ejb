package com.rci.omega2.ejb.dto;

import java.util.ArrayList;
import java.util.List;

import com.rci.omega2.ejb.utils.EmailEnum;

public class MailDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> destinationList;
	public String subject;
	private String content;
	public EmailEnum emailEnum;
	private List<MailAttachDTO> attachments;
	
	public List<String> getDestinationList() {
		if(destinationList == null){
			destinationList = new ArrayList<String>();
		}
		return destinationList;
	}
	public void setDestinationList(List<String> destinationList) {
		this.destinationList = destinationList;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public List<MailAttachDTO> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<MailAttachDTO> attachments) {
		this.attachments = attachments;
	}
	public EmailEnum getEmailEnum() {
		return emailEnum;
	}
	public void setEmailEnum(EmailEnum emailEnum) {
		this.emailEnum = emailEnum;
	}

}
