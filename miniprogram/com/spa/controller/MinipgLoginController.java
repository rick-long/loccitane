package com.spa.controller;

import com.spa.constant.CommonConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.payment.Payment;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.model.user.User;
import org.spa.shiro.authc.LoginToken;
import org.spa.utils.Results;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.callback.*;
import org.spa.vo.member.MemberMinipgEditVO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("miniprogram")

public class MinipgLoginController extends BaseController {

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Results login(@RequestBody MemberVO memberAPIVO, HttpServletRequest request) {
        Results results = Results.getInstance();
        String username = memberAPIVO.getUsername();
        String password = memberAPIVO.getPassword();
        LoginToken token=new LoginToken(username, password,false, null, CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        } catch (UnknownAccountException e) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        }  catch (AccountException e) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        }
        if (!subject.isAuthenticated()) {

            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        }

        User loginer = userService.getUserByEmail(username, CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        //7天有效期
        subject.getSession().setTimeout(7 * 24 * 60 * 60 * 1000);
        MemberCallBackVO callback= new MemberCallBackVO(loginer);
        callback.setToken("JSESSIONID="+subject.getSession().getId());
        subject.getSession().setAttribute(CommonConstant.CURRENT_LOGIN_USER, loginer);
        System.out.println("---cal  api/apps ---login--diva level--"+callback.getDivaLevel());
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", callback);
    }

    @RequestMapping("getMember")
    @ResponseBody
    public Results getMember(@RequestBody MemberMinipgEditVO memberEditVO){
        Results results = Results.getInstance();
        if(memberEditVO.getId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id required");
        }
        User member = userService.get(memberEditVO.getId());
        if(member==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The member does not exist.");
        }
        org.spa.vo.app.callback.MemberCallBackVO callback= new org.spa.vo.app.callback.MemberCallBackVO(member);
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", callback);
    }

    @RequestMapping("getPrepaidList")
    @ResponseBody
    public Results getPrepaidList(@RequestBody MemberMinipgEditVO memberVO){
        Results results = Results.getInstance();
        if(memberVO.getId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id required");
        }
        User member = userService.get(memberVO.getId());
        if(member==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The member does not exist.");
        }

        List<Prepaid> prepaidList = prepaidService.getPrepaidById(memberVO.getId(), WebThreadLocal.getCompany().getId());
        if (prepaidList.size() == 0) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "You didn't have prepaid.");
        }

        List<PrepaidListCallBackVO> plcbList = new ArrayList<>();
        for (Prepaid p : prepaidList) {
            PrepaidListCallBackVO plcb = new PrepaidListCallBackVO(p);
            plcbList.add(plcb);
        }

        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", plcbList);
    }

    @RequestMapping("getListByPrepaid")
    @ResponseBody
    public Results getListByPrepaid(@RequestBody MemberMinipgEditVO memberVO){
        Results results = Results.getInstance();
        if(memberVO.getPrepaidId() == null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "You didn't have prepaid.");
        }

        Prepaid prepaid = prepaidService.get(memberVO.getPrepaidId());
        if (prepaid == null) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "You didn't have prepaid.");
        }
        List<PrepaidTopUpTransaction> ptutList = prepaidTopUpTransactionService.getActivePrepaidTopUpTransactionsByPrepaid(prepaid);

        List<PaymentCallBackVO> pcbVOList = new ArrayList<>();
        for (PrepaidTopUpTransaction ptut : ptutList) {
            List<Payment> paymentList = paymentService.getUsedPrepaidTopUpTransaction(ptut.getId());
            for (Payment payment : paymentList) {
                PaymentCallBackVO pcbVO = new PaymentCallBackVO(payment);
                pcbVOList.add(pcbVO);
            }
        }

        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", pcbVOList);
    }

    @RequestMapping("getPrepaidAllList")
    @ResponseBody
    public Results getPrepaidAllList(@RequestBody MemberMinipgEditVO memberVO){
        Results results = Results.getInstance();
        if(memberVO.getId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id required");
        }
        User member = userService.get(memberVO.getId());
        if(member==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The member does not exist.");
        }
        List<Prepaid> prepaidList = prepaidService.getPrepaidById(memberVO.getId(), WebThreadLocal.getCompany().getId());
        if (prepaidList.size() == 0) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "You didn't have prepaid.");
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startDate",firstDayOfYear);
        parameters.put("endDate", lastDayOfYear);
        parameters.put("userId", memberVO.getId());
        parameters.put("companyId", WebThreadLocal.getCompany().getId());

        List<PurchaseOrder> poList = purchaseOrderService.getSalesHistoryList(parameters);
        if (poList == null && poList.size() == 0) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "You didn't have purchase order.");
        }
        List<PurchaseOrderListCallBackVO> polcVOList = new ArrayList<>();
        for (PurchaseOrder po : poList) {
            PurchaseOrderListCallBackVO polcVO = new PurchaseOrderListCallBackVO(po);

            if (po.getPayments() == null) {
                return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "You didn't have payment detail.");
            }

            if (po.getPurchaseItems() == null) {
                return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "You didn't have purchase items detail.");
            }
            for (PurchaseItem pi : po.getPurchaseItems()) {
                PurchaseItemCallBackVO picbVO = new PurchaseItemCallBackVO(pi);
                polcVO.getPicbList().add(picbVO);
            }

            for (Payment p : po.getPayments()) {
                PaymentCallBackVO pcbVO = new PaymentCallBackVO(p);
                polcVO.getPcbList().add(pcbVO);
            }

            polcVOList.add(polcVO);
        }

        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", polcVOList);
    }

}
