package org.kgsnipes.site.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kgsnipes.site.Main;
import org.kgsnipes.site.util.SiteMonitorErrorLog;
import org.kgsnipes.site.util.SiteMonitorStat;
import org.kgsnipes.site.util.SiteMonitorUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ReportingJob implements Job {

	private static final Logger log=Logger.getLogger(ReportingJob.class);
	
    public ReportingJob() {
        // Instances of Job must have a public no-argument constructor.
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
    	
        JobDataMap data = context.getMergedJobDataMap();
         
        try {
        	
        	
        	SiteMonitorUtil.sendMailForReport("Fetch - Site Monito Report  - Generated On"+ new Date().toString(), SiteMonitorUtil.getStatEmailPage(Main.stat), SiteMonitorUtil.getStatEmailPage(Main.stat));
            
           for(SiteMonitorStat s:Main.stat)
           {
        	   s.setErrorLogs(new ArrayList<SiteMonitorErrorLog>());
           }
           
        } catch(Exception e){
        	
        	
        	
        }finally {
        
        	
           
        }
        
        
       
    }

}