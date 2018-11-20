package com.spa.controller.api;

import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.spa.model.user.User;
import org.spa.service.user.UserService;
import org.spa.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/apps")
public class MemberApiForAppsController extends BaseController {
    @Autowired
    UserService userService;
    @RequestMapping("login")
    @ResponseBody
    public Results login(@RequestBody MemberAPIVO memberAPIVO) {
        Results results = Results.getInstance();
        String username = memberAPIVO.getUsername();
        String password = memberAPIVO.getPassword();
        User loginer = userService.getUserByEmail(username, CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
//        if (loginer == null || !loginer.getPassword().equals(EncryptUtil.SHA1(password))) {
//            return results.setCode(Results.CODE_ARGUMENT_ERROR).addMessage("errorMsg", "Account or password error!");
//        }
        MemberAppsApiVo memberAppsApiVo= new MemberAppsApiVo(loginer);
        System.out.println("---cal  api/apps ---login--diva level--"+memberAppsApiVo.getDivaLevel());
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", memberAppsApiVo);
    }

}
