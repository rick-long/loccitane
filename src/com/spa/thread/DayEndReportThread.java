package com.spa.thread;

import com.spa.constant.CommonConstant;
import com.spa.email.Attachment;
import com.spa.email.EmailAddress;
import com.spa.freemarker.EmailTemplate;
import com.spa.job.EmailSendToMultiAddressJob;

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

import java.util.List;
import java.util.Map;

/**
 * 发送 DayEndReportThread email
 *
 * @author jason on 2018-1-5
 */
public class DayEndReportThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final MktMailShotService mktMailShotService = SpringUtil.getBean(MktMailShotServiceImpl.class);

    private Map<String, Object> parameterMap;

    public static DayEndReportThread getInstance(Map<String, Object> parameterMap) {
        DayEndReportThread thread = new DayEndReportThread();
        thread.parameterMap = parameterMap;
        return thread;
    }
    @Override
    public void run() {
    	
    	System.out.println("DayEndReportThread----run-----");
        User user = (User) parameterMap.get("user");
        String shopName =(String)parameterMap.get("shopName");
        Company company = (Company) parameterMap.get("company");
        String templateType = (String) parameterMap.get("templateType");
        List<Attachment> attachments = (List<Attachment>) parameterMap.get("attachments");
        String userName = CommonSendEmailThread.class.getSimpleName();
        List<EmailAddress> emailAddresses = (List<EmailAddress>) parameterMap.get("emailAddresses");
        DateTime dateTime = new DateTime().plusSeconds(CommonConstant.EMAIL_JOB_DELAY_START);
        // saveBlock mailshot
        MktMailShot mktMailShot = mktMailShotService.saveCommonMktMailShot(templateType, user, company);
        logger.debug("start create " + templateType + " Email Job ...");
        System.out.println("start create " + templateType + " Email Job ...");
        EmailTemplate emailTemplate = EmailTemplate.getInstance(mktMailShot);

        String subject=emailTemplate.getSubjectTemplate()+" For "+shopName;
/*        // email template 参数
        emailTemplate.setContentData("username", user.getUsername());*/
        emailTemplate.setContentData(parameterMap);
        // job 运行参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("subject2", subject);
        jobDataMap.put("user", user);
        jobDataMap.put("shopName", shopName);
        jobDataMap.put("mktMailShot", mktMailShot);
        jobDataMap.put("emailTemplate", emailTemplate);
        jobDataMap.put("executor", userName);
        jobDataMap.put("attachments", attachments);
        jobDataMap.put("emailAddresses", emailAddresses);
        EmailSendToMultiAddressJob.scheduleJob(dateTime.toDate(), jobDataMap, mktMailShot.getType());
        logger.debug("finish create " + templateType + " Email Job.");
    }
}
