package org.kgsnipes.site.util;

import java.util.Date;

public class SiteMonitorErrorLog {
	
	private String message;
	private Date time;
	
	
	
	public SiteMonitorErrorLog(String message, Date time) {
		super();
		this.message = message;
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	

}
