package com.spa.controller.privilege;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.company.Company;
import org.spa.model.privilege.SysResource;
import org.spa.model.privilege.SysRole;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.page.Page;
import org.spa.vo.user.SysRoleListVO;
import org.spa.vo.user.SysRoleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * 角色控制器
 *
 * @author Ivy 2016-5-24
 */
@Controller
@RequestMapping("sysRole")
public class SysRoleController extends BaseController {

    @RequestMapping("sysRoleManagement")
    public String sysRoleManagement(Model model, SysRoleListVO sysRoleListVO) {
        return "sysRole/sysRoleManagement";
    }

    @RequestMapping("list")
    public String list(Model model, SysRoleListVO sysRoleListVO) {
        String name = sysRoleListVO.getName();
        String isActive = sysRoleListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysRole.class);
        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        //page start
        Page<SysRole> rolePage = sysRoleService.list(detachedCriteria, sysRoleListVO);
        model.addAttribute("page", rolePage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(sysRoleListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "sysRole/sysRoleList";
    }

    @RequestMapping("sysRoleAdd")
    public String sysRoleAdd(SysRoleVO sysRoleVO) {
        return "sysRole/sysRoleAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm add(@Valid SysRoleVO sysRoleVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Company company = WebThreadLocal.getCompany();
            String reference = sysRoleVO.getReference();
            AjaxForm errorAjaxForm = AjaxFormHelper.error();
            DetachedCriteria dc = DetachedCriteria.forClass(SysRole.class);
            List<SysRole> list = sysRoleService.getActiveListByRefAndCompany(dc, reference, company.getId());
            if (list != null && list.size() > 0) {
                errorAjaxForm.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
            }
            if (!errorAjaxForm.getErrorFields().isEmpty()) {
                return errorAjaxForm;
            } else {
                sysRoleVO.setCompany(company);
                sysRoleService.saveOrUpdate(sysRoleVO);
                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
            }
        }
    }

    @RequestMapping("sysRoleEdit")
    public String sysRoleEdit(SysRoleVO sysRoleVO) {
        SysRole sysRole = sysRoleService.get(sysRoleVO.getId());
        sysRoleVO.setReference(sysRole.getReference());
        sysRoleVO.setName(sysRole.getName());
        sysRoleVO.setIsActive(sysRole.isIsActive());
        return "sysRole/sysRoleEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm edit(@Valid SysRoleVO sysRoleVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            sysRoleService.saveOrUpdate(sysRoleVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }
    }

    @RequestMapping("sysRolePermissionAssign")
    public String sysRolePermissionAssign(SysRoleVO sysRoleVO, Model model) {
        SysRole sysRole = sysRoleService.get(sysRoleVO.getId());
        sysRoleVO.setReference(sysRole.getReference());
        sysRoleVO.setName(sysRole.getName());
        sysRoleVO.setIsActive(sysRole.isIsActive());
        List<SysResource> sysResourceList = sysResourceService.getOrderedList();
        model.addAttribute("sysResourceList", sysResourceList);
        Long[] sysResourceIds = sysRole.getSysResources().stream().map(e -> e.getId()).toArray(Long[]::new);
        sysRoleVO.setSysResourceIds(sysResourceIds);
        model.addAttribute("sysRoleVO", sysRoleVO);
        return "sysRole/sysRolePermissionAssign";
    }

    @RequestMapping("assign")
    @ResponseBody
    public AjaxForm assign(SysRoleVO sysRoleVO) {
        Long id = sysRoleVO.getId();
        if (id == null) {
            return AjaxFormHelper.error("id is null!");
        }
        sysRoleService.assignPermissions(sysRoleVO);
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
    }
}
