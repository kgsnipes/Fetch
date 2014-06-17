package org.kgsnipes.site.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SiteMonitorStat {

	private String URI;
	private Long pollCount=0l;
	private Long successCount=0l;
	private Long failureCount=0l;
	private Integer interval=0;
	private Integer threshold=0;
	private Date lastFailurePoint;
	private Boolean lastFailureNotified;
	private Double errorPercentage=0.0;
	private Double successPercentage=0.0;
	private String lastFailureMessage="NA";
	private Double averageLatency=0.0;
	private Double latency=0.0;
	private List<SiteMonitorErrorLog> errorLogs=new ArrayList<SiteMonitorErrorLog>();
	
	
	public List<SiteMonitorErrorLog> getErrorLogs() {
		return errorLogs;
	}
	public void setErrorLogs(List<SiteMonitorErrorLog> errorLogs) {
		this.errorLogs = errorLogs;
	}
	public Double getLatency() {
		return latency;
	}
	public void setLatency(Double latency) {
		this.latency =+ latency;
		if(this.latency>0.0 && this.pollCount>0)
			this.setAverageLatency(this.latency/this.pollCount);
	}
	public Double getAverageLatency() {
		return averageLatency;
	}
	public void setAverageLatency(Double averageLatency) {
			this.averageLatency=averageLatency;
	}
	public String getLastFailureMessage() {
		return lastFailureMessage;
	}
	public void setLastFailureMessage(String lastFailureMessage) {
		this.lastFailureMessage = lastFailureMessage;
		if(this.lastFailurePoint!=null && this.errorLogs!=null)
			this.errorLogs.add(new SiteMonitorErrorLog(this.lastFailureMessage,new Date(lastFailurePoint.getTime())));
		else
		{
			this.errorLogs=new ArrayList<SiteMonitorErrorLog>();
			this.errorLogs.add(new SiteMonitorErrorLog(this.lastFailureMessage,new Date()));
		}
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
		this.setSuccessPercentage((this.getSuccessCount()/(double)this.getPollCount())*100);
		this.setErrorPercentage((this.getFailureCount()/(double)this.getPollCount())*100);
		
	}
	public Long getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(Long failureCount) {
		this.failureCount = failureCount;
		this.setErrorPercentage((this.getFailureCount()/(double)this.getPollCount())*100);
		this.setSuccessPercentage((this.getSuccessCount()/(double)this.getPollCount())*100);
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
	public Double getErrorPercentage() {
		return errorPercentage;
	}
	public void setErrorPercentage(Double errorPercentage) {
		this.errorPercentage = errorPercentage;
	}
	public Double getSuccessPercentage() {
		return successPercentage;
	}
	public void setSuccessPercentage(Double successPercentage) {
		this.successPercentage = successPercentage;
	}

}
