package job;

import org.quartz.*;

public class MyJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();

       /* JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");*/

        System.err.println("Instance " + key + " of DumbJob says: " + ", and val is: ");
    }
}
