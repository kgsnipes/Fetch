package org.kgsnipes.site.jobs;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.util.Date;
import java.util.Map;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.kgsnipes.site.Main;
import org.kgsnipes.site.util.SiteMonitorStat;
import org.kgsnipes.site.util.SiteMonitorUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SiteURLJob implements Job {

	private static final Logger log=Logger.getLogger(SiteURLJob.class);
	
    public SiteURLJob() {
        // Instances of Job must have a public no-argument constructor.
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
    	ObjectMapper mapper=new ObjectMapper();
        JobDataMap data = context.getMergedJobDataMap();
        Map<String,String> configMap=Main.config.getSiteURLConfigByURL(data.get("stat").toString());
        
       SiteMonitorStat stat=SiteMonitorUtil.getSiteMonitorStatByURI(data.get("stat").toString());
        
        
        
        try {
        	
        	String url=(String)data.get("stat");

          
            long stopTime=0l;
            long startTime=System.currentTimeMillis();
            HttpResponse response = SiteMonitorUtil.getResonseForGet(url);
            stopTime=System.currentTimeMillis();
            double timeDiffSeconds=0.0;
            if(stopTime-startTime>0)
            	timeDiffSeconds=(stopTime-startTime)/1000;
            
            stat.setLatency(timeDiffSeconds);
            int status = response.getStatusLine().getStatusCode();
            stat.setPollCount(stat.getPollCount()+1);
            if (status >= 200 && status < 300 && timeDiffSeconds<stat.getThreshold()) {
            	
            	//this is a success hit
            	stat.setSuccessCount(stat.getSuccessCount()+1);
            	
            }
            else if ((status < 200 || status >= 300) && timeDiffSeconds>stat.getThreshold()) 
            {
            	//this is a failure
            	stat.setFailureCount(stat.getFailureCount()+1);
            }
           
            if(stat.getLastFailureNotified()!=null)
            {
            	if(!stat.getLastFailureNotified())
                {
             		try {
         				SiteMonitorUtil.sendMail("PANIC!!! :"+stat.getURI()+" URL DOWN "+ new Date().toString(), SiteMonitorUtil.getStatErrorNotificationHTMLPage(stat), SiteMonitorUtil.getStatErrorNotificationHTMLPage(stat));
         				stat.setLastFailureNotified(true);
         			} catch (Exception e1) {
         				// TODO Auto-generated catch block
         				stat.setLastFailureNotified(false);
         				e1.printStackTrace();
         			}
                }
            }
           
            
           
           
        } catch(Exception e){
        	
        	stat.setPollCount(stat.getPollCount()+1);
        	stat.setFailureCount(stat.getFailureCount()+1);
        	stat.setLastFailurePoint(new Date());
        	
        	
        	if(e instanceof ConnectionClosedException)
        	{
        		stat.setLastFailureMessage(" Connection was closed : "+e.getMessage());
        	}
        	else if(e instanceof IOException)
        	{
        		stat.setLastFailureMessage(" IO Exception occured : "+e.getMessage());
        	}
        	else if(e instanceof HttpException)
        	{
        		stat.setLastFailureMessage(" HTTP Exception occured : "+e.getMessage());
        	}
        	else if(e instanceof NoHttpResponseException)
        	{
        		stat.setLastFailureMessage(" HTTP Response occured : "+e.getMessage());
        	}
        	else if(e instanceof ProtocolException)
        	{
        		stat.setLastFailureMessage(" Protocol Exception occured : "+e.getMessage());
        	}
        	else if(e instanceof ParseException)
        	{
        		stat.setLastFailureMessage(" Parse Exception occured : "+e.getMessage());
        	}
        	else if(e instanceof NoRouteToHostException)
        	{
        		stat.setLastFailureMessage(" Could Not resolve Host : "+e.getMessage());
        	}
        	else
        	{
        		stat.setLastFailureMessage(e.getMessage());
        	}
        	
        	
        	try {
        		SiteMonitorUtil.sendMail("PANIC!!! :"+stat.getURI()+" URL DOWN "+ new Date().toString(), SiteMonitorUtil.getStatErrorNotificationHTMLPage(stat), SiteMonitorUtil.getStatErrorNotificationHTMLPage(stat));
				stat.setLastFailureNotified(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				stat.setLastFailureNotified(false);
				e1.printStackTrace();
			}
        	//this needs to be reported
        	
        	
        	log.error("exception occured", e);
        	e.printStackTrace();
        	
        }finally {
        
        	
        	try {
        		SiteMonitorUtil.refreshSiteMonitorOutputHTMLPage();
				log.info(mapper.writeValueAsString(stat));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
           
        }
        
        
       
    }

}