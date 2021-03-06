package org.kgsnipes.site.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class SiteMonitorConfig {
	
	private static Document configDoc;

	public static Document getConfigDoc() {
		return configDoc;
	}

	public static void setConfigDoc(Document configDoc) {
		SiteMonitorConfig.configDoc = configDoc;
	}
	
	public Map<String,String> getSiteURLConfigByURL(String url)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		 Element e =(Element) configDoc.selectSingleNode("/SiteMonitor/URLMonitor[@url='"+url+"']");
		 if(e!=null)
		 {
			map.put("url", e.attributeValue("url"));
			map.put("interval", e.attributeValue("interval"));
			map.put("latencyThreshold", e.attributeValue("latencyThreshold"));
		 }
		return map;
		
	}
	
	public Map<String,String> getDomainConfigByDomain(String domain)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		 Element e =(Element) configDoc.selectSingleNode("/SiteMonitor/DomainMonitor[@domain='"+domain+"']");
		 if(e!=null)
		 {
			map.put("domain", e.attributeValue("domain"));
			map.put("interval", e.attributeValue("interval"));
			map.put("latencyThreshold", e.attributeValue("latencyThreshold"));
		 }
		return map;
		
	}
	
	public List<Map<String,String>> getSiteURLConfigs()
	{
		List<Map<String,String>> configs=new ArrayList<Map<String,String>>();
		
		 List<Node> nodes=configDoc.selectNodes("/SiteMonitor/URLMonitor");
		 if(nodes!=null)
		 {
			for(Node n:nodes)
			{
				Element e=(Element)n;
				Map<String,String> map=new HashMap<String,String>();
				map.put("url", e.attributeValue("url"));
				map.put("interval", e.attributeValue("interval"));
				map.put("latencyThreshold", e.attributeValue("latencyThreshold"));
				configs.add(map);
			}
		 }
		return configs;
		
	}
	
	public List<Map<String,String>> getDomainConfigs()
	{
		List<Map<String,String>> configs=new ArrayList<Map<String,String>>();
		
		 List<Node> nodes=configDoc.selectNodes("/SiteMonitor/DomainMonitor");
		 if(nodes!=null)
		 {
			for(Node n:nodes)
			{
				Element e=(Element)n;
				Map<String,String> map=new HashMap<String,String>();
				map.put("domain", e.attributeValue("domain"));
				map.put("interval", e.attributeValue("interval"));
				map.put("latencyThreshold", e.attributeValue("latencyThreshold"));
				configs.add(map);
			}
		 }
		return configs;
		
	}
	
	public Map<String,String> getReportingConfigByForEmail()
	{
		Map<String,String> map=new HashMap<String,String>();
		
		 Element e =(Element) configDoc.selectSingleNode("/SiteMonitor/reporting[@type='email']");
		 if(e!=null)
		 {
			 
			map.put("type", e.attributeValue("type"));
			map.put("enabled", e.attributeValue("enabled"));
			map.put("interval", e.attributeValue("interval"));
			map.put("smtp", e.attributeValue("smtp"));
			
			map.put("smtpPort", e.attributeValue("smtpPort"));
			map.put("tls", e.attributeValue("tls"));
			map.put("recipients", e.attributeValue("recipients"));
			map.put("cc", e.attributeValue("cc"));
			map.put("bcc", e.attributeValue("bcc"));
			
			map.put("userName", e.attributeValue("userName"));
			map.put("password", e.attributeValue("password"));
			
		 }
		return map;
		
	}

	
	
}
