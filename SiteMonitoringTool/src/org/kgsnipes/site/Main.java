package org.kgsnipes.site;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Logger;
import org.kgsnipes.site.util.SiteMonitorConfig;
import org.kgsnipes.site.util.SiteMonitorUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main {
	
	private static final Logger log=Logger.getLogger(Main.class);

	static Scheduler scheduler=null;
	static SiteMonitorConfig config=null;
	static CommandLine cmdLine=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			cmdLine=SiteMonitorUtil.usePosixParser(args);
			readConfig(args);
			 scheduler = StdSchedulerFactory.getDefaultScheduler();
			 attachShutDownHook();
			 scheduler.start();
			
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
	
	public static void readConfig(String []args)
	{
		config=new SiteMonitorConfig();
		if(cmdLine.hasOption("config"))
		{
			log.info("The config file "+cmdLine.getOptionValue("config"));
			File f=new File(cmdLine.getOptionValue("config"));
			try {
				config.setConfigDoc(SiteMonitorUtil.getXMLDocument(f.toURI().toURL().toString()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			log.info("No config file found");
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
	

}
