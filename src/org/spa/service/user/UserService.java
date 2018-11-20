package org.spa.service.user;

import com.spa.controller.api.MemberAPIVO;
import com.spa.jxlsBean.MemberImportJxlsBean;
import com.spa.jxlsBean.importDemo.StaffImportJxlsBean;
import org.hibernate.criterion.DetachedCriteria;
import org.spa.dao.base.BaseDao;
import org.spa.model.product.ProductOption;
import org.spa.model.staff.StaffInOrOut;
import org.spa.model.user.User;
import org.spa.utils.Results;
import org.spa.vo.app.member.*;
import org.spa.vo.importDemo.ImportDemoVO;
import org.spa.vo.page.Page;
import org.spa.vo.staff.StaffInOrOutVo;
import org.spa.vo.staff.StaffServiceSettingsVO;
import org.spa.vo.staff.StaffVO;
import org.spa.vo.user.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface UserService extends BaseDao<User>{
	
	public User getLoginUserByAccountWithType(String account, String accountType);
	
	public User getUserByUsername(String username,String accountType);
	
	public User getUserByEmail(String email,String accountType);
	
	public List<User> getAvalibleUsersByAccountTypeAndRoleRef(String accountType,String roleName);
    
    public void saveOrUpdateStaff(StaffVO staffVO);

    public User saveMember(MemberAddVO memberAddVO);
    public User saveAppsMember(AppsMemberAddVO AppsmemberAddVO);
    public User saveMember(MemberAddVO memberAddVO,Boolean register);
    
    public void updateMember(MemberEditVO memberEditVO);
    
    public List<User> getMemberListByShop(Long shopId, DetachedCriteria criteria);

    public List<User> getTherapistByShop(Long shopId, DetachedCriteria criteria);

    public List<User> getTherapistsBySkill(Long shopId, ProductOption productOption,Boolean isOnline);

    public List<User> getAvailableTherapistList(Long shopId, ProductOption productOption, Date startTime, Date endTime,Boolean isOnline);

    public boolean checkTherapistSkill(User therapist, ProductOption productOption);

    public Set<String> getSysRoles(String userName, String accountType);

    public Set<String> getPermissions(String userName, String accountType);

    String generateNextUsername(String accountType);

    void saveResetPassword(ForgetPasswordVO forgetPasswordVO);
    void saveAppsResetPassword(AppsForgetPasswordVO appsForgetPasswordVO, String url);

    void newMembersSendEmail(ForgetPasswordVO forgetPasswordVO);

    String updatePassword(ChangePasswordVO changePasswordVO);

    void resetPassword(ResetPasswordVO resetPasswordVO);

    public void saveRegister(RegisterVO registerVO);
    
    public List<MemberImportJxlsBean> importMember(ImportDemoVO memberImportVO);
    
    public List<StaffImportJxlsBean> importStaff(ImportDemoVO memberImportVO);

    void enable(Long userId);

    void disable(Long userId);

    boolean isMobileUsed(Long companyId, String mobileNumber);

    boolean isMobileUsed(Long companyId, String mobileNumber, Long excludeUserId);
    
    public Double getNeedMoneyToGetNextLevel(Long userId);
    public Double getNeedMoneyToRenewCurrentLevel(Long userId);
    public Double getRemainValueOfCashPackage(Long userId);
    
    Page<User> listMember(MemberAdvanceVO memberAdvanceVO);

    List<User> listAllMember(MemberAdvanceVO memberAdvanceVO);

    List<User> getStaffListByRole(String sysRoleReference);
    
    public void saveServiceSettingsForStaff(StaffServiceSettingsVO staffServiceSettingsVO);
    
    public Map<String,String> getMemberDetailsImortToSFByCSV(Integer limitNum,Boolean isDemo);
    public Map<String,String> getMemberDetailsImortToSFByCSV(Integer limitNum);
    public Map<String,String> getMemberDetailsImortToSFByCSV(Integer limitNum,Boolean isDemo,List<Object[]> searchResults);
    
    public Results updateMemberDetailsByAPI(MemberAPIVO memberAPIVO);
    
    public List<Object[]> getMemberDetailsExportToCSVForSF(Integer limitNum);
    public List<Object[]> getMemberDetailsExportToCSVForSF(Integer limitNum,Boolean isDemo);
    
    public void updateUserSetLastModifier(String date,Boolean  demoRecords);
    
    public List<User> getUsersByBirthdayMonth(Integer month);
    public List<Long> getMemberByNotInIds(List<Long> ids);
    
    public void updateMobilePhone(AppsMobilePhoneEditVO mobilePhoneEditVO);
    
    public String updateAppsMemberPassword(ChangeAppsPasswordVO changePasswordVO);
    
    public void updateAppsMember(MemberAppsEditVO memberEditVO);

    Object[] clock(StaffInOrOutVo staff) throws ParseException;

    int editClock(Long id,String datetime,Long type) throws ParseException;
}
