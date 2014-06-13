package org.kgsnipes.site;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Logger;
import org.kgsnipes.site.util.EmailNotificationConfig;
import org.kgsnipes.site.util.SiteMonitorConfig;
import org.kgsnipes.site.util.SiteMonitorStat;
import org.kgsnipes.site.util.SiteMonitorUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main {
	
	private static final Logger log=Logger.getLogger(Main.class);

	public static Scheduler scheduler=null;
	public static SiteMonitorConfig config=null;
	public static CommandLine cmdLine=null;
	public static List<SiteMonitorStat> stat=new ArrayList<SiteMonitorStat>();
	public static EmailNotificationConfig emailNotificationConfig=null;
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			cmdLine=SiteMonitorUtil.usePosixParser(args);
			readConfig(args);
			readEmailConfig(args);
			 scheduler = StdSchedulerFactory.getDefaultScheduler();
			 attachShutDownHook();
			 scheduler.start();
			 startSchedulingJobs();
		}
		catch(Exception ex)
		{
			log.error("Application Hit An Exception",ex);
			ex.printStackTrace();
		}
		finally
		{
			
		}

	}
	
	public static void readEmailConfig(String[] args) {
		
	
		if(cmdLine.hasOption("config"))
		{
			log.info("The config file "+cmdLine.getOptionValue("config"));
			File f=new File(cmdLine.getOptionValue("config"));
			if(f.exists())
			{
				try {
					emailNotificationConfig=SiteMonitorUtil.getEmailConfig(SiteMonitorUtil.getXMLDocument(f.toURI().toURL().toString()));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				log.info("No such config file found " +cmdLine.getOptionValue("config"));
			}
			
		}
		else
		{
			log.info("No config file mentioned");
		}
		
	}

	public static void readConfig(String []args)
	{
		config=new SiteMonitorConfig();
		if(cmdLine.hasOption("config"))
		{
			log.info("The config file "+cmdLine.getOptionValue("config"));
			File f=new File(cmdLine.getOptionValue("config"));
			if(f.exists())
			{
				try {
					config.setConfigDoc(SiteMonitorUtil.getXMLDocument(f.toURI().toURL().toString()));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				log.info("No such config file found " +cmdLine.getOptionValue("config"));
			}
			
		}
		else
		{
			log.info("No config file mentioned");
		}
		
	}
	
	
	 public static void attachShutDownHook(){
		  Runtime.getRuntime().addShutdownHook(new Thread() {
		   @Override
		   public void run() {
			   
			   try {
					scheduler.shutdown();
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					log.error("Application Hit An Exception",e);
					e.printStackTrace();
				}
		   
		   }
		  });
		  
		 }
	 public static void startSchedulingJobs(){
		 
		 try {
			 SiteMonitorUtil.createJobsAndSchedule(scheduler,config,stat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Application Hit An Exception",e);
			e.printStackTrace();
		}
		 
	 }

}
