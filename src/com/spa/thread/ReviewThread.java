package com.spa.thread;

import com.spa.email.EmailAddress;
import com.spa.freemarker.EmailTemplate;
import com.spa.job.EmailJob;
import org.joda.time.DateTime;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.company.Company;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.user.User;
import org.spa.service.marketing.MktMailShotService;
import org.spa.serviceImpl.marketing.MktMailShotServiceImpl;
import org.spa.utils.SpringUtil;

import java.util.Map;

/**
 * 发送 Review email
 *
 * @author jason on 2018-1-5
 */
public class ReviewThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final MktMailShotService mktMailShotService = SpringUtil.getBean(MktMailShotServiceImpl.class);
   
    private Map<String, Object> parameterMap;

    public static ReviewThread getInstance(Map<String, Object> parameterMap) {
        ReviewThread thread = new ReviewThread();
        thread.parameterMap = parameterMap;
        return thread;
    }
    @Override
    public void run() {
    	
    	System.out.println("ReviewThread----run-----");
        User user = (User) parameterMap.get("user");
        Integer emailJobDelayStart =  (Integer) parameterMap.get("emailJobDelayStart");
        Company company = (Company) parameterMap.get("company");
        String templateType = (String) parameterMap.get("templateType");
        String userName = CommonSendEmailThread.class.getSimpleName();
        EmailAddress emailAddress = (EmailAddress) parameterMap.get("emailAddress");
        DateTime dateTime = new DateTime().plusSeconds(emailJobDelayStart);
        // saveBlock mailshot
        MktMailShot mktMailShot = mktMailShotService.saveCommonMktMailShot(templateType, user, company);
        logger.debug("start create " + templateType + " Email Job ...");
        System.out.println("start create " + templateType + " Email Job ...");
        EmailTemplate emailTemplate = EmailTemplate.getInstance(mktMailShot);
        // email template 参数

        emailTemplate.setContentData(parameterMap);
        // job 运行参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("user", user);
        jobDataMap.put("mktMailShot", mktMailShot);
        jobDataMap.put("emailTemplate", emailTemplate);
        jobDataMap.put("executor", userName);
        jobDataMap.put("emailAddress", emailAddress);
        EmailJob.scheduleJob(dateTime.toDate(), jobDataMap, mktMailShot.getType());
        logger.debug("finish create " + templateType + " Email Job.");
    }
}
