package org.kgsnipes.site.jobs;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
        
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet((String)data.get("url"));

          
            ResponseHandler<HttpResponse> responseHandler = new ResponseHandler<HttpResponse>() {

                public HttpResponse handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                   
                    return response;
                }

            };
            long stopTime=0l;
            long startTime=System.currentTimeMillis();
            HttpResponse response = httpclient.execute(httpget, responseHandler);
            stopTime=System.currentTimeMillis();
            float timeDiffSeconds=(stopTime-startTime)/1000;
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300 && timeDiffSeconds<stat.getThreshold()) {
            	
            	//this is a success hit
            	stat.setSuccessCount(stat.getSuccessCount()+1);
            	
            }
            else if ((status < 200 || status >= 300) && timeDiffSeconds>stat.getThreshold()) 
            {
            	//this is a failure
            	stat.setFailureCount(stat.getFailureCount()+1);
            }
            
            
            stat.setPollCount(stat.getPollCount()+1);
            
           
           
        } catch(Exception e){
        	
        	stat.setPollCount(stat.getPollCount()+1);
        	stat.setFailureCount(stat.getFailureCount()+1);
        	stat.setLastFailurePoint(new Date());
        	
        	
        	try {
				SiteMonitorUtil.sendMail("PANIC!!! :"+stat.getURI()+" URL DOWN "+ new Date().toString(), mapper.writeValueAsString(stat), mapper.writeValueAsString(stat));
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
        		
				log.info(mapper.writeValueAsString(stat));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("exception occured", e);
				e.printStackTrace();
			}
        }
        
        
       
    }

}