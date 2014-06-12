package org.kgsnipes.site.jobs;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DomainMonitorJob implements Job {

	private static final Logger log=Logger.getLogger(DomainMonitorJob.class);
	
    public DomainMonitorJob() {
        // Instances of Job must have a public no-argument constructor.
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        JobDataMap data = context.getMergedJobDataMap();
       
    }

}