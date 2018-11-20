package job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.DateBuilder.dateOf;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzDemo {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .build();

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(5)
                        .repeatForever())
                .endAt(dateOf(22, 0, 0))
                .build();

        // Tell quartz to schedule the job using our trigger
        defaultScheduler.scheduleJob(job, trigger);

        defaultScheduler.start();

        Thread.sleep(1000l*10);

        defaultScheduler.shutdown();



    }
}
