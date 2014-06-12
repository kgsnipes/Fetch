package org.kgsnipes.site.util;

import java.util.Date;

public class SiteMonitorStat {

	private String URI;
	private Long pollCount;
	private Long successCount;
	private Long failureCount;
	private Integer interval;
	private Integer threshold;
	private Date lastFailurePoint;
	private Boolean lastFailureNotified;
	private Float errorPercentage;
	private Float successPercentage;
	
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
	}
	public Long getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(Long failureCount) {
		this.failureCount = failureCount;
		this.setErrorPercentage((this.getFailureCount()/(float)this.getPollCount())*100);
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
