package com.spa.job;

import com.spa.email.Attachment;
import com.spa.email.EmailAddress;
import com.spa.email.EmailSender;
import com.spa.email.SMTPConfig;
import com.spa.freemarker.EmailTemplate;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.user.User;
import org.spa.utils.RandomUtil;
import org.spa.utils.SpringUtil;
import org.stringtemplate.v4.ST;

import java.util.Date;
import java.util.List;

/**
 * @author Ivy on 2018-1-31
 */
public class EmailSendToMultiAddressJob extends OpenSessionQuartzJobBean {
	
    private SMTPConfig smtpConfig = SMTPConfig.SMTP_CONFIG;

    public static final StdScheduler SCHEDULER = (StdScheduler) SpringUtil.getBean("baseQuartzScheduler");

    public static void scheduleJob(Date startTime, JobDataMap jobDataMap, String group) {
        String identity = RandomUtil.generateRandomNumberWithTime("EMAIL-");
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(identity, group).startAt(startTime);
        JobDetail jobDetail = JobBuilder.newJob(EmailSendToMultiAddressJob.class).withIdentity(identity, group).setJobData(jobDataMap).build();
        try {
            SCHEDULER.scheduleJob(jobDetail, triggerBuilder.build());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeJob(JobExecutionContext context) throws JobExecutionException {
    	System.out.println("--EmailSendToMultiAddressJob----executeJob-----");
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        MktMailShot mktMailShot = (MktMailShot) jobDataMap.get("mktMailShot");
        EmailTemplate emailTemplate = (EmailTemplate) jobDataMap.get("emailTemplate");
        User user = (User) jobDataMap.get("user");
        String subject2=(String)jobDataMap.get("subject2");
        List<EmailAddress> toAddresses = ( List<EmailAddress>) jobDataMap.get("emailAddresses");
        String executor = (String) jobDataMap.get("executor");
        String attachmentPath=(String) jobDataMap.get("attachmentPath");
        String attachmentName=(String) jobDataMap.get("attachmentName");
        List<Attachment> attachments=(List<Attachment>) jobDataMap.get("attachments");
        emailTemplate.convert();
        String subject= emailTemplate.getSubject();
       if(subject2!=null){
          subject =subject2;
       }
        String content = emailTemplate.getContent();

        EmailSender emailSender;
        emailSender = EmailSender.getInstance(user, mktMailShot, subject, content);
        
        emailSender.setToAddresses(toAddresses);
        
        emailSender.setExecutor(executor);
        if(attachments!=null && attachments.size()>0){
            emailSender.setAttachments(attachments);
        }
        if (StringUtils.isNotBlank(attachmentPath)){
            emailSender.addAttachment(attachmentPath,"",attachmentName);
        }
        emailSender.addCc(smtpConfig.getFromAddress());
        boolean res = emailSender.send();
        logger.debug("Send email res {}, email content:{}", res, content);
    }
}
