package com.spa.thread;

import com.spa.constant.CommonConstant;
import com.spa.email.EmailAddress;
import com.spa.freemarker.EmailTemplate;
import com.spa.job.EmailJob;
import org.joda.time.DateTime;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.user.User;
import org.spa.model.user.UserCode;

/**
 * 发送注册email
 *
 * @author Ivy on 2016-6-15
 */
public class RegistrationThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private User user;

    private MktMailShot mktMailShot;

    private UserCode userCode;

    public static RegistrationThread getInstance(MktMailShot mktMailShot, User user, UserCode userCode) {
        RegistrationThread registrationThread = new RegistrationThread();
        registrationThread.mktMailShot = mktMailShot;
        registrationThread.user = user;
        registrationThread.userCode = userCode;
        return registrationThread;
    }

    @Override
    public void run() {
        String userName = RegistrationThread.class.getSimpleName();
        DateTime dateTime = new DateTime().plusSeconds(CommonConstant.EMAIL_JOB_DELAY_START);
        logger.debug("start create Registration Email Job ...");
        EmailAddress emailAddress=new EmailAddress();
        EmailTemplate emailTemplate = EmailTemplate.getInstance(mktMailShot);
        // email template 参数
        emailTemplate.setContentData("username", user.getUsername());
        emailTemplate.setContentData("user", user);
        emailTemplate.setContentData("code", userCode.getCode());
        // job 运行参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("user", user);
        jobDataMap.put("emailAddress", new EmailAddress(user.getEmail(), user.getUsername()));
        jobDataMap.put("mktMailShot", mktMailShot);
        jobDataMap.put("emailTemplate", emailTemplate);
        jobDataMap.put("executor", userName);
        EmailJob.scheduleJob(dateTime.toDate(), jobDataMap, mktMailShot.getType());
        logger.debug("finish create Registration Email Job.");
    }
}
