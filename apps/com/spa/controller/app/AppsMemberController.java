package com.spa.controller.app;
import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.spa.utils.NumberUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.spa.model.user.User;
import org.spa.utils.EncryptUtil;
import org.spa.utils.Results;
import org.spa.vo.app.callback.MemberCallBackVO;
import org.spa.vo.app.member.ChangeAppsPasswordVO;
import org.spa.vo.app.member.MemberAppsEditVO;
import org.spa.vo.app.member.AppsMobilePhoneEditVO;

@RestController
@RequestMapping("apps/member")
public class AppsMemberController extends BaseController {
    @RequestMapping({"/updateMember"})
    @ResponseBody
    public Results updateMember(@RequestBody MemberAppsEditVO memberEditVO) {
        Results results = Results.getInstance();
        User member=userService.get(Long.valueOf(memberEditVO.getId()));

        if(member==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The member does not exist.");
        }
        if(memberEditVO.getId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id  required");
        }
        if(StringUtils.isBlank(memberEditVO.getFirstName())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "First Name required");
        }
        if(StringUtils.isBlank(memberEditVO.getLastName())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Last Name required");
        }
        if(StringUtils.isBlank(memberEditVO.getGender())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Gender required");
        }
        if(StringUtils.isBlank(memberEditVO.getAddressExtention())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Address Extention required");
        }
        if(StringUtils.isBlank(memberEditVO.getDistrict())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "District required");
        }
        if(memberEditVO.getDateOfBirth()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Date Of Birth required");
        }
        if(StringUtils.isBlank(memberEditVO.getShopId())){
            memberEditVO.setShopId(String.valueOf(member.getShop().getId()));
        }
        userService.updateAppsMember(memberEditVO);
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "success");
    }

    @RequestMapping({"/updateMobilePhone"})
    @ResponseBody
    public Results updateMobilePhone(@RequestBody AppsMobilePhoneEditVO mobilePhoneEditVO) {
        Results results = Results.getInstance();
        User member=userService.get(Long.valueOf(mobilePhoneEditVO.getId()));
        if(member==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The member does not exist.");
        }
        if(StringUtils.isBlank(mobilePhoneEditVO.getNotification())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Notification required");
        }
        //check tel#
        if (StringUtils.isBlank(mobilePhoneEditVO.getMobilePhone())) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Mobile phone  required");
        } else if (!NumberUtil.isNumeric(mobilePhoneEditVO.getMobilePhone())) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Mobile phone should be number.");
        } else if(userService.isMobileUsed(1l, mobilePhoneEditVO.getMobilePhone())) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Mobile phone has been used.");
        }
        userService.updateMobilePhone(mobilePhoneEditVO);
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "success");
    }
//更新密码
    @RequestMapping({"/changePwd"})
    @ResponseBody
    public Results changePwd(@RequestBody ChangeAppsPasswordVO changePasswordVO) {
        Results results = Results.getInstance();
        User member=userService.get(Long.valueOf(changePasswordVO.getUserId()));
        if(member==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The member does not exist.");
        }
      if(!member.getPassword().equals(EncryptUtil.SHA1(changePasswordVO.getOldPassword()))){
          return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Original password error");
       }
        if(StringUtils.isBlank(changePasswordVO.getPassword())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Password required");
        }
        //check tel#
        if (changePasswordVO.getPassword().length() < 6 || changePasswordVO.getPassword().length() > 18) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The length of password should be 6-18");
        }
        // password check confirmPassword
        if(!changePasswordVO.getPassword().equals(changePasswordVO.getConfirmPassword())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Passwords are not consistent!");
        }
        userService.updateAppsMemberPassword(changePasswordVO);
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "success");
    }
    @RequestMapping("getMember")
    @ResponseBody
    public Results getMember(@RequestBody MemberAppsEditVO memberEditVO){
        Results results = Results.getInstance();
        if(memberEditVO.getId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id required");
        }
        User member = userService.get(memberEditVO.getId());
        if(member==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The member does not exist.");
        }
        MemberCallBackVO callback= new MemberCallBackVO(member);
        System.out.println("---cal  api/apps ---login--diva level--"+callback.getDivaLevel());
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", callback);
    }

}
