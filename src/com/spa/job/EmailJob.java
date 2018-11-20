package com.spa.job;

import com.spa.email.Attachment;
import com.spa.email.EmailAddress;
import com.spa.email.EmailSender;
import com.spa.email.SMTPConfig;
import com.spa.freemarker.EmailTemplate;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.user.User;
import org.spa.utils.RandomUtil;
import org.spa.utils.SpringUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Ivy on 2016-6-2
 */
public class EmailJob extends OpenSessionQuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SMTPConfig smtpConfig = SMTPConfig.SMTP_CONFIG;

    public static final StdScheduler SCHEDULER = (StdScheduler) SpringUtil.getBean("baseQuartzScheduler");

    public static void scheduleJob(Date startTime, JobDataMap jobDataMap, String group) {
        String identity = RandomUtil.generateRandomNumberWithTime("EMAIL-");
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(identity, group).startAt(startTime);
        JobDetail jobDetail = JobBuilder.newJob(EmailJob.class).withIdentity(identity, group).setJobData(jobDataMap).build();
        try {
            SCHEDULER.scheduleJob(jobDetail, triggerBuilder.build());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeJob(JobExecutionContext context) throws JobExecutionException {
    	System.out.println("EmailJob----executeJob-----");
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        MktMailShot mktMailShot = (MktMailShot) jobDataMap.get("mktMailShot");
        EmailTemplate emailTemplate = (EmailTemplate) jobDataMap.get("emailTemplate");
        User user = (User) jobDataMap.get("user");
        EmailAddress emailAddress = (EmailAddress) jobDataMap.get("emailAddress");
        String executor = (String) jobDataMap.get("executor");
        String attachmentPath=(String) jobDataMap.get("attachmentPath");
        String attachmentName=(String) jobDataMap.get("attachmentName");
        List<Attachment> attachments=(List<Attachment>) jobDataMap.get("attachments");
        emailTemplate.convert();
        String subject = emailTemplate.getSubject();
        String content = emailTemplate.getContent();
        EmailSender emailSender;
        emailSender = EmailSender.getInstance(user, mktMailShot, subject, content);
        emailSender.addTo(emailAddress.getAddress(), emailAddress.getName());
        emailSender.setExecutor(executor);
        if(attachments!=null&&attachments.size()>0){
            emailSender.setAttachments(attachments);
        }
        if (StringUtils.isNotBlank(attachmentPath)){
            emailSender.addAttachment(attachmentPath,"",attachmentName);
        }
        emailSender.addCc(smtpConfig.getFromAddress());
        boolean res = emailSender.send();
        logger.debug("Send email to {}, res {}, email content:{}", user.getEmail(), res, content);
    }
}
