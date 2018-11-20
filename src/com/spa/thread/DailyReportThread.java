package com.spa.thread;

import com.spa.constant.CommonConstant;
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
import org.spa.utils.NumberUtil;
import org.spa.utils.SpringUtil;
import org.spa.vo.report.SalesDetailsSummaryVO;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 发送 DayEndReportThread email
 *
 * @author Ivy on 2018-2-1
 */
public class DailyReportThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final MktMailShotService mktMailShotService = SpringUtil.getBean(MktMailShotServiceImpl.class);

    private Map<String, Object> parameterMap;

    public static DailyReportThread getInstance(Map<String, Object> parameterMap) {
        DailyReportThread thread = new DailyReportThread();
        thread.parameterMap = parameterMap;
        return thread;
    }
    @Override
    public void run() {
    	DecimalFormat df = new DecimalFormat("#,##0.00");
    	System.out.println("DayEndReportThread----run-----");
    	Company company = (Company) parameterMap.get("company");
    	Double sumIncome =(Double)parameterMap.get("sumIncome");
    	Double sumPrepaid =(Double)parameterMap.get("sumPrepaid");
    	Double totalSales =(Double)parameterMap.get("totalSales");
    	
    	Double cash =(Double)parameterMap.get("cash");
    	Double visa =(Double)parameterMap.get("visa");
    	Double eps =(Double)parameterMap.get("eps");
    	Double ae =(Double)parameterMap.get("ae");
    	Double uniompay =(Double)parameterMap.get("uniompay");
    	Double others =(Double)parameterMap.get("others");
    	
    	Double packages =(Double)parameterMap.get("packages");
    	Double voucher =(Double)parameterMap.get("voucher");
    	Double wings =(Double)parameterMap.get("wings");
    	Double hotels =(Double)parameterMap.get("hotels");
    	
    	List<SalesDetailsSummaryVO> summaryList =(List<SalesDetailsSummaryVO>)parameterMap.get("summaryList");
    	
    	Integer numOfClients =(Integer)parameterMap.get("numOfClients");
    	
    	Map<String,List<SalesDetailsSummaryVO>> staffSummary =(Map<String,List<SalesDetailsSummaryVO>>)parameterMap.get("staffSummary");
    	
        User user = (User) parameterMap.get("user");
        String shopName =(String)parameterMap.get("shopName");
        
        String templateType = (String) parameterMap.get("templateType");
        String userName = CommonSendEmailThread.class.getSimpleName();
        List<EmailAddress> emailAddresses = (List<EmailAddress>) parameterMap.get("emailAddresses");
        DateTime dateTime = new DateTime().plusSeconds(CommonConstant.EMAIL_JOB_DELAY_START);
        // saveBlock mailshot
        MktMailShot mktMailShot = mktMailShotService.saveCommonMktMailShot(templateType, user, company);
        logger.debug("start create " + templateType + " Email Job ...");
        System.out.println("start create " + templateType + " Email Job ...");
        EmailTemplate emailTemplate = EmailTemplate.getInstance(mktMailShot);
/*        // email template 参数
        emailTemplate.setContentData("username", user.getUsername());*/
        emailTemplate.setContentData(parameterMap);
        // job 运行参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("user", user);
        System.out.println("DailyReportThread totalSales=" + totalSales + " ...cash="+cash);
        jobDataMap.put("sumIncome", "$"+df.format(sumIncome));
        jobDataMap.put("sumPrepaid","$"+ df.format(sumPrepaid));
        jobDataMap.put("totalSales", "$"+df.format(totalSales));
        jobDataMap.put("cash", "$"+df.format(cash));
        jobDataMap.put("visa", "$"+df.format(visa));
        jobDataMap.put("eps", "$"+df.format(eps));
        jobDataMap.put("ae", "$"+df.format(ae));
        jobDataMap.put("uniompay", "$"+df.format(uniompay));
        jobDataMap.put("packages", "$"+df.format(packages));
        jobDataMap.put("voucher", "$"+df.format(voucher));
        jobDataMap.put("wings", "$"+df.format(wings));
        jobDataMap.put("hotels", "$"+df.format(hotels));
        jobDataMap.put("others","$"+ df.format(others));
        
        jobDataMap.put("numOfClients", numOfClients);
        
        jobDataMap.put("staffSummary",staffSummary);
        jobDataMap.put("summaryList", summaryList);
        
        jobDataMap.put("shopName", shopName);
        jobDataMap.put("mktMailShot", mktMailShot);
        jobDataMap.put("emailTemplate", emailTemplate);
        jobDataMap.put("executor", userName);
        jobDataMap.put("emailAddresses", emailAddresses);
        EmailSendToMultiAddressJob.scheduleJob(dateTime.toDate(), jobDataMap, mktMailShot.getType());
        logger.debug("finish create " + templateType + " Email Job.");
    }
}
