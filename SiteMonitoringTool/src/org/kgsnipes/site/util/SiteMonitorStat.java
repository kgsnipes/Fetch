package org.kgsnipes.site.util;

import java.util.Date;

public class SiteMonitorStat {

	private String URI;
	private Long pollCount=0l;
	private Long successCount=0l;
	private Long failureCount=0l;
	private Integer interval=0;
	private Integer threshold=0;
	private Date lastFailurePoint;
	private Boolean lastFailureNotified;
	private Float errorPercentage;
	private Float successPercentage;
	private String lastFailureMessage;
	private Float averageLatency=0.0f;
	
	public Float getAverageLatency() {
		return averageLatency;
	}
	public void setAverageLatency(Float averageLatency) {
		if(this.averageLatency!=0.0f)
			this.averageLatency = (float) ((this.averageLatency+averageLatency)/2.0);
		else
			this.averageLatency=averageLatency;
	}
	public String getLastFailureMessage() {
		return lastFailureMessage;
	}
	public void setLastFailureMessage(String lastFailureMessage) {
		this.lastFailureMessage = lastFailureMessage;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public Long getPollCount() {
		return pollCount;
	}
	public void setPollCount(Long pollCount) {
		this.pollCount = pollCount;
	}
	public Long getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
		this.setSuccessPercentage((this.getSuccessCount()/(float)this.getPollCount())*100);
		this.setErrorPercentage((this.getFailureCount()/(float)this.getPollCount())*100);
		
	}
	public Long getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(Long failureCount) {
		this.failureCount = failureCount;
		this.setErrorPercentage((this.getFailureCount()/(float)this.getPollCount())*100);
		this.setSuccessPercentage((this.getSuccessCount()/(float)this.getPollCount())*100);
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public Date getLastFailurePoint() {
		return lastFailurePoint;
	}
	public void setLastFailurePoint(Date lastFailurePoint) {
		this.lastFailurePoint = lastFailurePoint;
	}
	public Boolean getLastFailureNotified() {
		return lastFailureNotified;
	}
	public void setLastFailureNotified(Boolean lastFailureNotified) {
		this.lastFailureNotified = lastFailureNotified;
	}
	public Float getErrorPercentage() {
		return errorPercentage;
	}
	public void setErrorPercentage(Float errorPercentage) {
		this.errorPercentage = errorPercentage;
	}
	public Float getSuccessPercentage() {
		return successPercentage;
	}
	public void setSuccessPercentage(Float successPercentage) {
		this.successPercentage = successPercentage;
	}

}
