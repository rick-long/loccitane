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
import org.spa.service.user.UserService;
import org.spa.serviceImpl.user.UserServiceImpl;
import org.spa.utils.SpringUtil;

/**
 * 使用线程生成发送newsLetter的job
 * 
 * @author Ivy
 */
public class NewsLetterThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final UserService userService = SpringUtil.getBean(UserServiceImpl.class);

    private MktMailShot mktMailShot;

    public NewsLetterThread(MktMailShot mktMailShot) {
        this.mktMailShot = mktMailShot;
    }

    @Override
    public void run() {
        String[] userIds = mktMailShot.getUserIds().split(",");
        DateTime dateTime = new DateTime(mktMailShot.getSendTime());
        dateTime = dateTime.plusSeconds(CommonConstant.EMAIL_JOB_DELAY_START);
        logger.debug("start create News Letter Email Job ...");
        for (String userId : userIds) {
            User user = userService.get(Long.parseLong(userId));
            EmailTemplate emailTemplate = EmailTemplate.getInstance(mktMailShot);
            // email template 参数
            emailTemplate.setContentData("username", user.getUsername());
            // job 运行参数
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("user", user);
            jobDataMap.put("emailAddress", new EmailAddress(user.getEmail(), user.getUsername()));
            jobDataMap.put("mktMailShot", mktMailShot);
            jobDataMap.put("emailTemplate", emailTemplate);
            jobDataMap.put("executor", "NewsLetterThread");
            EmailJob.scheduleJob(dateTime.toDate(), jobDataMap, mktMailShot.getType());
            dateTime = dateTime.plusSeconds(CommonConstant.EMAIL_JOB_SEND_INTERVAL);
        }
        logger.debug("finish create News Letter Email Job.");
    }
}
