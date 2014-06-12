package org.kgsnipes.site.util;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.kgsnipes.site.Main;

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
	 
	 public static void createJobsAndSchedule(SiteMonitorConfig config)throws Exception
	 {
		 
	 }
	 

}
