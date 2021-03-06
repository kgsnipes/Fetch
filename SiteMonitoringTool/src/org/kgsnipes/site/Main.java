package org.kgsnipes.site;

import java.io.Console;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Logger;
import org.kgsnipes.site.util.EmailNotificationConfig;
import org.kgsnipes.site.util.SiteMonitorConfig;
import org.kgsnipes.site.util.SiteMonitorStat;
import org.kgsnipes.site.util.SiteMonitorUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.google.common.base.Strings;

public class Main {
	
	private static final Logger log=Logger.getLogger(Main.class);

	public static Scheduler scheduler=null;
	public static SiteMonitorConfig config=null;
	public static CommandLine cmdLine=null;
	public static List<SiteMonitorStat> stat=new ArrayList<SiteMonitorStat>();
	public static EmailNotificationConfig emailNotificationConfig=null;
	public static String outputFileName="siteMonitor.html";
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			cmdLine=SiteMonitorUtil.usePosixParser(args);
			readConfig(args);
			readConfigOutput(args);
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
					/*if(emailNotificationConfig.getEnabled() && Strings.isNullOrEmpty(emailNotificationConfig.getPass()))
					{
						System.out.println("Enter your email alert password : ");
						Console cons=null;
						 char[] passwd=null;
						 if ((cons = System.console()) != null &&
						     (passwd = cons.readPassword("[%s]", "Password:")) != null) {
							 emailNotificationConfig.setPass(new String(passwd));
						 }
					}*/
						
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
	
	public static void readConfigOutput(String []args)
	{
		config=new SiteMonitorConfig();
		if(cmdLine.hasOption("output"))
		{
			log.info("The output file "+cmdLine.getOptionValue("output"));
			
			if(cmdLine.getOptionValue("output")!=null)
			{
				outputFileName=cmdLine.getOptionValue("output");
			}
		}
		else
		{
			log.info("No output file mentioned");
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
