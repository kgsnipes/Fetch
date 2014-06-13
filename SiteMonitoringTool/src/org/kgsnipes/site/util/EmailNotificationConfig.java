package org.kgsnipes.site.util;

public class EmailNotificationConfig {
	
	private String[] recipients;
	private String[] cc;
	private String[] bcc;
	private String smtpHost;
	private Integer smtpPort;
	private Boolean tlsEnabled;
	private String sender;
	private String pass;
	private Boolean enabled;
	
	
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String[] getRecipients() {
		return recipients;
	}
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String[] getBcc() {
		return bcc;
	}
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public Integer getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}
	public Boolean getTlsEnabled() {
		return tlsEnabled;
	}
	public void setTlsEnabled(Boolean tlsEnabled) {
		this.tlsEnabled = tlsEnabled;
	}
	
	

}
