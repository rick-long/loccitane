package org.spa.serviceImpl.payroll;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.spa.dao.order.StaffCommissionDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.payroll.PayrollAttributeKey;
import org.spa.model.payroll.StaffPayroll;
import org.spa.model.payroll.StaffPayrollAdditional;
import org.spa.model.payroll.StaffPayrollAttributeStatistics;
import org.spa.model.payroll.StaffPayrollCategoryStatistics;
import org.spa.model.product.Category;
import org.spa.model.staff.StaffPayrollAttribute;
import org.spa.model.user.User;
import org.spa.service.book.BlockService;
import org.spa.service.order.PurchaseOrderService;
import org.spa.service.payroll.PayrollAttributeKeyService;
import org.spa.service.payroll.PayrollService;
import org.spa.service.payroll.PayrollTemplateService;
import org.spa.service.payroll.StaffPayrollAttributeService;
import org.spa.service.payroll.StaffPayrollAttributeStatisticsService;
import org.spa.service.payroll.StaffPayrollCategoryStatisticsService;
import org.spa.service.product.CategoryService;
import org.spa.service.user.UserService;
import org.spa.utils.CollectionUtils;
import org.spa.utils.DateUtil;
import org.spa.utils.PropertiesUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.payroll.PayrollAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/06/16.
 */
@Service
public class PayrollServiceImpl extends BaseDaoHibernate<StaffPayroll> implements PayrollService{

	@Autowired
	public UserService userService;
	
	@Autowired
	public PayrollAttributeKeyService payrollAttributeKeyService;
	
	@Autowired
	public StaffPayrollAttributeStatisticsService staffPayrollAttributeStatisticsService;
	
	@Autowired
	public StaffPayrollCategoryStatisticsService staffPayrollCategoryStatisticsService;
	
	@Autowired
	public StaffCommissionDao staffCommissionDao;
	
	@Autowired
	public CategoryService categoryService;
	
	@Autowired
	public PayrollTemplateService payrollTemplateService;
	
	@Autowired
	public StaffPayrollAttributeService staffPayrollAttributeService;
	
	@Autowired
	public BlockService blockService;
	
	@Autowired
	public PurchaseOrderService purchaseOrderService;
	
	@Override
	public void savePayroll(PayrollAddVO payrollAddVO) {
		
		Long staffId=payrollAddVO.getStaffId();
		
		if(staffId!=null && staffId == 00){
			//代表是all staffs
			DetachedCriteria dc = DetachedCriteria.forClass(User.class);
			dc.add(Restrictions.eq("enabled", true));
			dc.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
			dc.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
			
		    List<User> staffList=userService.list(dc);
		    for(User staff : staffList){
		    	saveAndUpdatePayroll(payrollAddVO,staff);
		    }
		}else{
			User staff=userService.get(staffId);
			saveAndUpdatePayroll(payrollAddVO,staff);
		}
	}
	
	@Override
	public void saveAndUpdatePayroll(PayrollAddVO payrollAddVO,User staff){
		Date now=new Date();
		String loginUser=WebThreadLocal.getUser().getUsername();
		
		String remarks=payrollAddVO.getRemarks();
		StaffPayroll payroll=null;
		Long payrollId=payrollAddVO.getId();
		
		if(payrollId !=null && payrollId.longValue()>0){
			payroll=get(payrollId);
			payroll.setLastUpdated(now);
			payroll.setLastUpdatedBy(loginUser);
			// service hours
			payroll.setServiceHours(payrollAddVO.getServiceHours());
			// additions
			payroll.getStaffPayrollAdditionals().clear();
			if(payrollAddVO.getAdditionalsAfMpf() !=null && payrollAddVO.getAdditionalsAfMpf().size()>0){
				List<KeyAndValueVO> additionalsAf=payrollAddVO.getAdditionalsAfMpf();
				for(KeyAndValueVO kv : additionalsAf){
					if(StringUtils.isNotBlank(kv.getKey()) && StringUtils.isNotBlank(kv.getValue())){
						StaffPayrollAdditional spa=new StaffPayrollAdditional();
						spa.setLabel(kv.getKey());
						spa.setValue(kv.getValue());
						spa.setType(CommonConstant.STAFF_PAYROLL_ADDITIONAL_TYPE_AFTER_MPF);
						spa.setStaffPayroll(payroll);
						payroll.getStaffPayrollAdditionals().add(spa);
					}
				}
			}
			
			if(payrollAddVO.getAdditionalsBfMpf() !=null && payrollAddVO.getAdditionalsBfMpf().size()>0){
				List<KeyAndValueVO> additionalsBf=payrollAddVO.getAdditionalsBfMpf();
				for(KeyAndValueVO kv : additionalsBf){
					if(StringUtils.isNotBlank(kv.getKey()) && StringUtils.isNotBlank(kv.getValue())){
						StaffPayrollAdditional spa=new StaffPayrollAdditional();
						spa.setLabel(kv.getKey());
						spa.setValue(kv.getValue());
						spa.setType(CommonConstant.STAFF_PAYROLL_ADDITIONAL_TYPE_BEFORE_MPF);
						spa.setStaffPayroll(payroll);
						payroll.getStaffPayrollAdditionals().add(spa);
					}
				}
			}
		}else{
			payroll=new StaffPayroll();
			payroll.setStaff(staff);
			payroll.setCreated(now);
			payroll.setCreatedBy(loginUser);
			payroll.setIsActive(true);
			payroll.setCompany(WebThreadLocal.getCompany());
			
			payroll.setPayrollTemplate(staff.getPayrollTemplate() ==null ? payrollTemplateService.get(2l):staff.getPayrollTemplate());
			//payroll date
			payroll.setPayrollDate(payrollAddVO.getPayrollDate());
		}
		
		payroll.setRemarks(remarks);
		saveOrUpdate(payroll);
		
		regeneratePayrolls(payroll);
	}

	@Override
	public void regeneratePayrolls(StaffPayroll payroll) {
		
		//company id
		Long companyId=null;
		if(WebThreadLocal.getCompany() !=null){
			companyId=WebThreadLocal.getCompany().getId();
		}
		// payroll basic details statistics
		User staff=payroll.getStaff();
		
		//staff payroll attribute
		Double totalBasicSalary=0d;
		
		Double guaranteedMinimum=0d;
		
		Map<String,Double> targetAmountsByTypeMap=new HashMap<String,Double>();
		
		if(staff.getStaffPayrollAttributes() !=null && staff.getStaffPayrollAttributes().size()>0){
			payroll.getStaffPayrollAttributeStatisticses().clear();
			for(StaffPayrollAttribute staffPayrollAttribute : staff.getStaffPayrollAttributes()){
				String attributeVal=staffPayrollAttribute.getValue();
				saveStaffPayrollAttributesStatistics(payroll, staffPayrollAttribute.getPayrollAttributeKey(), companyId, attributeVal);
				
				String payrollAttributeKeyRef=staffPayrollAttribute.getPayrollAttributeKey().getReference();
				
				// guaranteed minimum
				if(payrollAttributeKeyRef.equals(CommonConstant.PAYROLL_KEY_REF_GM)){
					guaranteedMinimum=Double.valueOf(attributeVal);
					continue;
				}
				// target amount
				if(payrollAttributeKeyRef.startsWith(CommonConstant.PAYROLL_KEY_REF_TARGET_AMOUNT)){
					int keyRefLengh=payrollAttributeKeyRef.length();
					int prefixLength=CommonConstant.PAYROLL_KEY_REF_TARGET_AMOUNT.length();
					Double targetAmout=Double.valueOf(attributeVal);
					String key=payrollAttributeKeyRef.substring(keyRefLengh-prefixLength, keyRefLengh);
					targetAmountsByTypeMap.put(key, targetAmout);
					continue;
				}
				//cal total basic salary
				totalBasicSalary=calTotalBasicSalary(payrollAttributeKeyRef, attributeVal, totalBasicSalary,payroll);
			}
		}
		//cal sales and commission and bonus
		Double[] totalSalesAndCommissionAndBonus=calculateTotalSalesAndCommissionAndBonusByCategory(payroll,targetAmountsByTypeMap);
		double totalSales=totalSalesAndCommissionAndBonus[0].doubleValue();
		double totalSalesCommission=totalSalesAndCommissionAndBonus[1].doubleValue();
		double totalSalesBonus=totalSalesAndCommissionAndBonus[2].doubleValue();
		
		//cal package commission and bounus
		Double[] totalPackageSalesAndCommissionAndBonus=calculateTotalPackagesSaleAndCommissionAndBonus(payroll,targetAmountsByTypeMap);
		double totalPackageSales=totalPackageSalesAndCommissionAndBonus[0].doubleValue();
		double totalPackageCommission=totalPackageSalesAndCommissionAndBonus[1].doubleValue();
		double totalpackageBonus=totalPackageSalesAndCommissionAndBonus[2].doubleValue();
		
		//cal total commission and bonus
		double totalCommission=totalSalesCommission + totalPackageCommission;
		double totalBonus=totalSalesBonus + totalpackageBonus;
		double totalSale=totalSales + totalPackageSales;
		
		payroll.setTotalBonus(totalBonus);
		payroll.setTotalCommission(totalCommission);
		payroll.setTotalSale(totalSale);	
		
		//totalAdditional Before Mpf
		Double totalAdditionalBfMpf=payroll.getTotalAdditionalBfMpf();
		
		//leave days
		Double leavePays=calculateLeavePays(payroll);
		payroll.setLeavePays(leavePays);
				
		// final salary before contribute=total basic salary +total commission + total bonus + totalAdditionalBfMpf
		Double finalSalaryBeforeContribution=totalBasicSalary + totalCommission + totalBonus + totalAdditionalBfMpf - leavePays;
		
		logger.debug("payroll totalBasicSalary::"+totalBasicSalary+",{}totalAdditional ::"+totalAdditionalBfMpf +",{}finalSalaryBeforeContribution::"+finalSalaryBeforeContribution);
		
		// gm
		if(guaranteedMinimum > 0 && guaranteedMinimum > finalSalaryBeforeContribution){
			payroll.setIsUsedGm(true);
			finalSalaryBeforeContribution=guaranteedMinimum;
		}else{
			payroll.setIsUsedGm(false);
		}
		logger.debug("payroll isGM ::"+payroll.getIsUsedGm()+",{}finalSalaryBeforeContribution ::"+finalSalaryBeforeContribution);
		payroll.setFinalSalaryBeforeContribution(finalSalaryBeforeContribution);
		
		//cal contribution
		Boolean isUserMPF=PropertiesUtil.getBooleanValueByName(CommonConstant.PAYROLL_IS_USE_CONTRIBUTE);
		if(isUserMPF && staff.getHasMPF()){
			calculateContribution(finalSalaryBeforeContribution,payroll);
		}else{
			payroll.setContribution(0d);
		}
		//total additional after mpf
		Double totalAdditionalAfMpf=payroll.getTotalAdditionalAfMpf();
		
		Double finalSalary=payroll.getFinalSalaryBeforeContribution()+payroll.getContribution()+totalAdditionalAfMpf;
		payroll.setFinalSalary(finalSalary);
	}
	
	@Override
	public Map<String,Double> getTargetAmountsByTypeMap(User staff){
		Map<String,Double> targetAmountsByTypeMap=new HashMap<String,Double>();
		
		if(staff.getStaffPayrollAttributes() !=null && staff.getStaffPayrollAttributes().size()>0){
			for(StaffPayrollAttribute staffPayrollAttribute : staff.getStaffPayrollAttributes()){
				String attributeVal=staffPayrollAttribute.getValue();
				String payrollAttributeKeyRef=staffPayrollAttribute.getPayrollAttributeKey().getReference();
				// target amount
				if(!payrollAttributeKeyRef.startsWith(CommonConstant.PAYROLL_KEY_REF_TARGET_AMOUNT)){
					continue;
				}
				Double targetAmout=Double.valueOf(attributeVal);
				
				int prefixLength=CommonConstant.PAYROLL_KEY_REF_TARGET_AMOUNT.length();
				String key=payrollAttributeKeyRef.substring(prefixLength);
				
				logger.debug("getTargetAmountsByTypeMap key {},targetAmout{}:"
						 +key+","+targetAmout);
				
				targetAmountsByTypeMap.put(key, targetAmout);
			}
		}
		return targetAmountsByTypeMap;
	}
	private Double calTotalBasicSalary(String staffPayrollAttributeKeyRef,String val,Double totalBasicSalary,StaffPayroll payroll){ 
		if(StringUtils.isNotBlank(val) 
				&& !staffPayrollAttributeKeyRef.startsWith(CommonConstant.PAYROLL_KEY_REF_TARGET_AMOUNT) 
				&& !CommonConstant.PAYROLL_KEY_REF_NOT_FOR_ADDITIONAL_LIST.contains(staffPayrollAttributeKeyRef)
				&& Double.valueOf(val).doubleValue()>0){
			//standard salary、tips、other amount
			if(staffPayrollAttributeKeyRef.equals(CommonConstant.PAYROLL_KEY_REF_HOUR_SALARY) 
					&& StringUtils.isNotBlank(payroll.getServiceHours())){
				// 处理时薪
				double baseSalary=Double.valueOf(val) * Double.valueOf(payroll.getServiceHours());
				totalBasicSalary=+baseSalary;
			}else{
				totalBasicSalary +=Double.valueOf(val);
			}
		}
		return totalBasicSalary;
	}

	private Double[] calculateTotalSalesAndCommissionAndBonusByCategory(StaffPayroll payroll,Map<String,Double> targetAmountsByTypeMap){
		
		Double[] returnVal=new Double[3];
		
		double totalSales=0;
		double totalSalesCommission=0;
		double totalSalesBonus=0;
		
		String payrollDateStr=payroll.getPayrollDate();
		Date payrollDate=DateUtil.stringToDate(payrollDateStr, "yyyy-MM");
		
		List<Category> parentCategories=categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
		if(parentCategories !=null && parentCategories.size()>0){
			for(Category c : parentCategories){
				
				Double [] totalSalesAndCommission=purchaseOrderService.calculateTotalSalesAndCommissionByCategory(payroll.getStaff(), payrollDate, c);
				Double salesStatistics=totalSalesAndCommission[0];
				Double commissionStatistics=totalSalesAndCommission[1];
				logger.debug("commissionStatistics {}::"+commissionStatistics+"--- salesStatistics {}::"+salesStatistics);
				
				//target amount
				Double targetAmount=0d;
				if(targetAmountsByTypeMap !=null && targetAmountsByTypeMap.size()>0){
					targetAmount=targetAmountsByTypeMap.get(c.getReference());
				}
				
				//cal bonus
				Double bonusStatistics=0d;
				
				//saveBlock StaffPayrollCategoryStatistics
				saveStaffPayrollCategoryStatistics(payroll, c, c.getName(), commissionStatistics, salesStatistics, bonusStatistics, targetAmount);
				
				totalSales +=salesStatistics;
				totalSalesCommission +=commissionStatistics;
				totalSalesBonus +=bonusStatistics;
			}
		}
		//return total values
		returnVal[0]=totalSales;
		returnVal[1]=totalSalesCommission;
		returnVal[2]=totalSalesBonus;
		
		return returnVal;
	}
	
	private Double[] calculateTotalPackagesSaleAndCommissionAndBonus(StaffPayroll payroll,Map<String,Double> targetAmountsByTypeMap){
		
		Double[] returnVal=new Double[3];
		
		String payrollDateStr=payroll.getPayrollDate();
		Date payrollDate=DateUtil.stringToDate(payrollDateStr, "yyyy-MM");
       
		DateTime fromDate = new DateTime(payrollDate).dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
		DateTime toDate= new DateTime(payrollDate).dayOfMonth().withMaximumValue().withSecondOfMinute(0);
		
		logger.debug("calculateTotalPackagesSaleAndCommissionAndBonus fromDate {}::"+fromDate+"--- toDate {}::"+toDate);
		
		Long saffId=payroll.getStaff().getId();
		Long companyId=null;
		if(WebThreadLocal.getCompany() !=null){
			companyId=WebThreadLocal.getCompany().getId();
		}
		
		double commissionStatistics=0;
		double salesStatistics=0;
		
		Map filterMap =CollectionUtils.getLightWeightMap();
		filterMap.put("fromDate", fromDate.toDate());
		filterMap.put("toDate", toDate.toDate());
		filterMap.put("staffId", saffId);
		filterMap.put("companyId", companyId);
		filterMap.put("isPackageSales", true);
		Double[] sum=staffCommissionDao.sumStaffCommissionByFilter(filterMap,null);
		if(sum !=null && sum.length>0){
			if(sum[0] !=null){
				commissionStatistics=sum[0];
			}
			if(sum[1] !=null){
				salesStatistics=sum[1];
			}
		}
		
		// cal target amount
		Double targetAmount=0d;
		if(targetAmountsByTypeMap !=null && targetAmountsByTypeMap.size()>0){
			targetAmount=targetAmountsByTypeMap.get(CommonConstant.PAYROLL_BONUS_TYPE_PREPAID);
		}
		// cal bonus
		Double bonusStatistics=0d;
				//calculateBonus(salesStatistics,CommonConstant.PAYROLL_BONUS_TYPE_PREPAID,targetAmount);
		
		//saveBlock StaffPayrollCategoryStatistics
		payroll.getStaffPayrollCategoryStatisticses().clear();
		saveStaffPayrollCategoryStatistics(payroll, null, CommonConstant.PAYROLL_BONUS_TYPE_PREPAID_DISPLAY, commissionStatistics, salesStatistics, bonusStatistics, targetAmount);
		
		returnVal[0]=salesStatistics;
		returnVal[1]=commissionStatistics;
		returnVal[2]=bonusStatistics;
		
		return returnVal;
	}
	
	/*private Double calculateBonus(Double totalSales,String bonusType,Double targetAmount) {
		
		Double bonus=0d;
		
		PayrollBonusVO payrollBonusVO=new PayrollBonusVO();
		payrollBonusVO.setTotalSales(totalSales);
		payrollBonusVO.setBonusType(bonusType);
		payrollBonusVO.setTargetAmount(targetAmount);
		payrollBonusVO.setBonus(0d);
		
        Map inputParameters = CollectionUtils.getLightWeightMap();
        inputParameters.put("payrollBonusVO", payrollBonusVO);
        
        Map outputParameters = CollectionUtils.getLightWeightMap();
        try {
            DetectorUtil.processing(CommonConstant.CAL_BONUS_RUN_CALSSES, inputParameters, outputParameters);
            
            logger.info("bonus ::" + ((payrollBonusVO.getBonus() != null) ? payrollBonusVO.getBonus().doubleValue() + "" : " is null"));
        } catch (ChainedException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
		return bonus;
	}*/
	
	private Double calculateLeavePays(StaffPayroll payroll){
		Double leavePays=0d;
		Double noPaidDays=blockService.countNoPaidLevaveDays(payroll.getStaff(), payroll.getPayrollDate());
		if(noPaidDays !=null && noPaidDays.doubleValue()>0){
			int totalWorkDaysInAMonth=PropertiesUtil.getIntegerValueByName(CommonConstant.TOTAL_WORK_DAYS_IN_A_MONTH);
			Double averageSalary=payroll.getStandardSalary().doubleValue() / totalWorkDaysInAMonth;
			leavePays=averageSalary.doubleValue() * noPaidDays.doubleValue();
		}
		return leavePays;
	}
	
	private void calculateContribution(Double finalSalaryBeforeContribution ,StaffPayroll payroll){
		
		User staff=payroll.getStaff();
		double contribution=0;
		
		int days=CommonConstant.PAYROLL_HAS_CONTRIBUTION_ATFER_DAYS;
		Date joinDate=staff.getJoinDate();
		Calendar cal = new GregorianCalendar();
		cal.setTime(joinDate);
		// it will contain the day of joinDate, so it just need to add 59 days
        cal.add(Calendar.DATE,days);
    		
		Boolean isProvideMPF=Boolean.FALSE;
		Date startofMPFDate=DateUtil.getFirstDay(cal.getTime());
		
		String payrollDateStr=payroll.getPayrollDate();
		Date payrollDate=DateUtil.stringToDate(payrollDateStr, "yyyy-MM");
        Date endDate=DateUtil.getLastMinuts(payrollDate);
		logger.debug("startofMPFDate ::"+startofMPFDate);
		//供款的最低数目
		Double minimumContributionNeededAmount=PropertiesUtil.getDoubleValueByName(CommonConstant.PAYROLL_MININUM_CONTRIBUTE_NEEDED_AMOUNT);
		if((endDate.after(startofMPFDate) || endDate.equals(startofMPFDate))){
			if(finalSalaryBeforeContribution !=null && finalSalaryBeforeContribution.doubleValue() >= minimumContributionNeededAmount.doubleValue()){
				isProvideMPF=Boolean.TRUE;
			}
		}
		logger.debug("payroll isProvideMPF ::"+isProvideMPF);
    	
		if(isProvideMPF){
			//每月最多的供款
    		Double maxNumContributionAmount=PropertiesUtil.getDoubleValueByName(CommonConstant.PAYROLL_MAX_CONTRIBUTION_AMOUNT);
    		//供款比率
    		Double contributionRate=PropertiesUtil.getDoubleValueByName(CommonConstant.PAYROLL_CONTRIBUTE_RATE);
			if (contributionRate!=null && contributionRate.doubleValue()>0) {
				contribution = finalSalaryBeforeContribution * contributionRate / 100;
				if(contribution > maxNumContributionAmount){
					contribution = maxNumContributionAmount;
				}
			}
			if(contribution>0){
				contribution=-contribution;
			}
			logger.debug("payroll contribution ::"+contribution);
		}
		payroll.setContribution(contribution);
	}
	
	private void saveStaffPayrollAttributesStatistics(StaffPayroll payroll,PayrollAttributeKey pak,Long companyId,String attributeVal){
		
		StaffPayrollAttributeStatistics spas=new StaffPayrollAttributeStatistics();
		spas.setPayrollAttributeKey(pak);
		spas.setStaffPayroll(payroll);
		spas.setValue(attributeVal);
		
		payroll.getStaffPayrollAttributeStatisticses().add(spas);
		
	}
	
	private void saveStaffPayrollCategoryStatistics(StaffPayroll payroll,Category category,String bonusType,
			Double commissionStatistics,Double salesStatistics,Double bonusStatistics,Double targetAmount){
		StaffPayrollCategoryStatistics spcs=new StaffPayrollCategoryStatistics();
		spcs.setStaffPayroll(payroll);
		spcs.setCategory(category);
		spcs.setCommissionStatistics(commissionStatistics);
		spcs.setSalesStatistics(salesStatistics);
		spcs.setBonusStatistics(bonusStatistics);
		spcs.setBonusType(bonusType);
		spcs.setTargetAmount(targetAmount);
		payroll.getStaffPayrollCategoryStatisticses().add(spcs);
	}
}
