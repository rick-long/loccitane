package com.spa.thread;
import com.spa.constant.CommonConstant;
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
 * 发送通用的 email
 *
 * @author Ivy on 2016-6-15
 */
public class CommonSendEmailThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final MktMailShotService mktMailShotService = SpringUtil.getBean(MktMailShotServiceImpl.class);

    private Map<String, Object> parameterMap;

    public static CommonSendEmailThread getInstance(Map<String, Object> parameterMap) {
        CommonSendEmailThread thread = new CommonSendEmailThread();
        thread.parameterMap = parameterMap;
        return thread;
    }

    @Override
    public void run() {
        User user = (User) parameterMap.get("user");
        Company company = (Company) parameterMap.get("company");
        String templateType = (String) parameterMap.get("templateType");
        String userName = CommonSendEmailThread.class.getSimpleName();
        DateTime dateTime = new DateTime().plusSeconds(CommonConstant.EMAIL_JOB_DELAY_START);
        // saveBlock mailshot
        MktMailShot mktMailShot = mktMailShotService.saveCommonMktMailShot(templateType, user, company);
        logger.debug("start create " + templateType + " Email Job ...");
        EmailTemplate emailTemplate = EmailTemplate.getInstance(mktMailShot);
        // email template 参数
        emailTemplate.setContentData("username", user.getUsername());
        emailTemplate.setContentData(parameterMap);
        // job 运行参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("user", user);
        jobDataMap.put("mktMailShot", mktMailShot);
        jobDataMap.put("emailTemplate", emailTemplate);
        jobDataMap.put("executor", userName);
        jobDataMap.put("emailAddress", new EmailAddress(user.getEmail(), user.getUsername()));
        EmailJob.scheduleJob(dateTime.toDate(), jobDataMap, mktMailShot.getType());
        logger.debug("finish create " + templateType + " Email Job.");
    }
}
