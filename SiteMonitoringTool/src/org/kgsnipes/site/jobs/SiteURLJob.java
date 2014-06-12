package org.kgsnipes.site.jobs;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
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

        JobDataMap data = context.getMergedJobDataMap();
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet((String)data.get("url"));

          
            ResponseHandler<HttpResponse> responseHandler = new ResponseHandler<HttpResponse>() {

                public HttpResponse handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                   
                    return response;
                }

            };
            
            HttpResponse response = httpclient.execute(httpget, responseHandler);
            
            
           
        } catch(Exception e){
        	
        	log.error("exception occured", e);
        	e.printStackTrace();
        	
        }finally {
        
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