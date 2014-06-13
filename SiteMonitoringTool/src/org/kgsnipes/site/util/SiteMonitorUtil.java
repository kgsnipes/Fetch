package org.kgsnipes.site.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.kgsnipes.site.Main;
import org.kgsnipes.site.jobs.SiteURLJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class SiteMonitorUtil {
	
	private static final Logger log=Logger.getLogger(Main.class);
	
	
	 public static Options constructPosixOptions()
	   {
	      final Options posixOptions = new Options();
	      posixOptions.addOption("config", true, "config file path.");
	      return posixOptions;
	   }
	 
	 
	 public static CommandLine usePosixParser(final String[] commandLineArguments)
	   {
	      final CommandLineParser cmdLinePosixParser = new BasicParser();
	      final Options posixOptions = constructPosixOptions();
	      CommandLine commandLine=null;
	      try
	      {
	         commandLine = cmdLinePosixParser.parse(posixOptions, commandLineArguments);
	         
	      }
	      catch (ParseException parseException)  // checked exception
	      {
	    	  log.error(
	              "Encountered exception while parsing using PosixParser:\n"
	            + parseException.getMessage() );
	      }
	      
	      return commandLine;
	   }
	 
	 
	 public static Document getXMLDocument(String file)
		{
			Document document=null;
			 SAXReader reader = new SAXReader();
			 try
			 {
				 document = reader.read(file);
			 }
			 catch(Exception ex)
			 {
				log.error("Exception in parsing the xml document",ex);
				ex.printStackTrace(); 
			 }
		     
			 return document;  
		}
	 
	 public static void createJobsAndSchedule(Scheduler scheduler,SiteMonitorConfig config,List<SiteMonitorStat> stat)throws Exception
	 {
		 scheduleSiteURLJob(scheduler, config,stat);
		 //scheduleDomainJob(scheduler, config,stat);
	 }
	 
	 public static void scheduleSiteURLJob(Scheduler scheduler,SiteMonitorConfig config,List<SiteMonitorStat> stat)throws Exception
	 {
		 
		 
		 List<Map<String,String>> site=config.getSiteURLConfigs();
		 
		 for(Map<String,String> m:site)
		 {
			 SiteMonitorStat statm=new SiteMonitorStat();
			 statm.setURI(m.get("url"));
			 statm.setInterval(Integer.parseInt(m.get("interval").toString()));
			 statm.setThreshold(Integer.parseInt(m.get("latencyThreshold").toString()));
			 JobDetail job = JobBuilder.newJob(SiteURLJob.class)
					    .withIdentity("job"+m.get("url").toString(), "SiteMonitoringScheduler")
					    .usingJobData("stat", statm.getURI())
					    .build();
			 
			 Trigger trigger = TriggerBuilder.newTrigger()
			            .withIdentity("trigger"+m.get("url").toString(), "SiteMonitoringScheduler")
			            .startNow()
			            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
			                    .withIntervalInSeconds(statm.getInterval())
			                    .repeatForever())            
			            .build();
			 
			 scheduler.scheduleJob(job, trigger);
			 stat.add(statm);

		 }
	 }
	 
	 public static void scheduleDomainJob(Scheduler scheduler,SiteMonitorConfig config,List<SiteMonitorStat> stat)throws Exception
	 {
		 
		 List<Map<String,String>> site=config.getSiteURLConfigs();
		 
		 for(Map<String,String> m:site)
		 {
			 SiteMonitorStat statm=new SiteMonitorStat();
			 statm.setURI(m.get("domain"));
			 statm.setInterval(Integer.parseInt(m.get("interval").toString()));
			 statm.setThreshold(Integer.parseInt(m.get("latencyThreshold").toString()));
			 JobDetail job = JobBuilder.newJob(SiteURLJob.class)
					    .withIdentity("job"+m.get("url").toString(), "SiteMonitoringScheduler")
					    .usingJobData("stat", statm.getURI())
					    .build();
			 
			 Trigger trigger = TriggerBuilder.newTrigger()
			            .withIdentity("trigger"+m.get("url").toString(), "SiteMonitoringScheduler")
			            .startNow()
			            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
			                    .withIntervalInSeconds(statm.getInterval())
			                    .repeatForever())            
			            .build();
			 
			 scheduler.scheduleJob(job, trigger);
			 stat.add(statm);

		 }
	 }
	 
	public static SiteMonitorStat getSiteMonitorStatByURI(String uri)
	{
		SiteMonitorStat retVal=null;
		for(SiteMonitorStat m:Main.stat)
		{
			if(m.getURI().equals(uri))
			{
				retVal=m;
				break;
			}
		}
			
		return retVal;
	}
	
	
	public static EmailNotificationConfig getEmailConfig(Document doc)
	{
		EmailNotificationConfig config=null;
		 Element e =(Element) doc.selectSingleNode("/SiteMonitor/notifier[@type='email']");
		 if(e!=null)
		 {
			 config=new EmailNotificationConfig();
			 config.setRecipients(e.attributeValue("recipients").split(","));
			 config.setCc(e.attributeValue("cc").split(","));
			 config.setBcc(e.attributeValue("bcc").split(","));
			 config.setSmtpHost(e.attributeValue("smtp"));
			 config.setSmtpPort(Integer.parseInt(e.attributeValue("smtpPort")));
			 config.setTlsEnabled(Boolean.parseBoolean(e.attributeValue("tls")));
			 config.setSender(e.attributeValue("userName"));
			 config.setPass(e.attributeValue("password"));
		 }
		
		return config;
	}
	
	
	public static void sendMail(String subject,String text,String html) throws Exception
	{
		//sendMailForReal(subject, text, html);
		log.info(subject+ "  "+text);
	}
	
	public static void sendMailForReal(String subject,String text,String html) throws Exception
	{
		EmailNotificationConfig config=Main.emailNotificationConfig;
		
		if(config!=null)
		{
			SimpleEmail email = new SimpleEmail();
			email.setHostName(config.getSmtpHost());
			email.setAuthentication(config.getSender(), config.getPass());
			email.setDebug(true);
			email.setSmtpPort(config.getSmtpPort());

			for (int i = 0; i < config.getRecipients().length; i++)
			{
			    email.addTo(config.getRecipients()[i]);
			}

			email.setFrom(config.getSender(), "Email Notification");
			email.setSubject(subject);
			email.setMsg(text);
			email.setStartTLSEnabled(config.getTlsEnabled());
			email.send();
		}

		
	}
	
	
	public static HttpResponse getResonseForGet(String url) throws Exception
	{
		HttpResponse response=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	            HttpGet httpget = new HttpGet(url);

	          
	            ResponseHandler<HttpResponse> responseHandler = new ResponseHandler<HttpResponse>() {

	                public HttpResponse handleResponse(
	                        final HttpResponse response) throws ClientProtocolException, IOException {
	                   
	                    return response;
	                }

	            };
	            
	            
	             response = httpclient.execute(httpget, responseHandler);
	             int status = response.getStatusLine().getStatusCode();
	            if(status == 302)
	            {
	            	 String redirectURL = response.getFirstHeader("Location").getValue();
	            	 httpclient.close();
           		  	return getResonseForGet(redirectURL);
	            }
	        }
	        catch(Exception ex)
	        {
	        	log.error("Exception",ex);
	        	ex.printStackTrace();
	        }
	        finally{
	        	httpclient.close();
	        }
		return response;
	}
	
}
