package com.spa.controller.marketing;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.marketing.MktChannel;
import org.spa.model.marketing.MktEmailTemplate;
import org.spa.model.marketing.MktMailShot;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.marketing.MktChannelListVO;
import org.spa.vo.marketing.MktChannelVO;
import org.spa.vo.marketing.MktEmailTemplateListVO;
import org.spa.vo.marketing.MktEmailTemplateVO;
import org.spa.vo.marketing.MktMailShotListVO;
import org.spa.vo.marketing.MktMailShotVO;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 *
 * @author Ivy 2016-6-1
 */
@Controller
@RequestMapping("marketing")
public class MarketingController extends BaseController {

    // channel ---------------------------------------------------------------------------------------------------

    @RequestMapping("toMktChannelView")
    public String toMktChannelView(Model model, MktChannelListVO mktChannelListVO) {
        return "marketing/mktChannelManagement";
    }

    @RequestMapping("mktChannelList")
    public String mktChannelList(Model model, MktChannelListVO mktChannelListVO) {
        String name = mktChannelListVO.getName();
        String isActive = mktChannelListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MktChannel.class);
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
        Page<MktChannel> page = mktChannelService.list(detachedCriteria, mktChannelListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(mktChannelListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "marketing/mktChannelList";
    }

    @RequestMapping("toMktChannelAdd")
    public String toMktChannelAdd(MktChannelVO mktChannelVO) {
        return "marketing/mktChannelAdd";
    }

    @RequestMapping("mktChannelAdd")
    @ResponseBody
    public AjaxForm mktChannelAdd(@Valid MktChannelVO mktChannelVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            mktChannelVO.setCompany(WebThreadLocal.getCompany());
            mktChannelService.saveOrUpdate(mktChannelVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("toMktChannelEdit")
    public String toMktChannelEdit(MktChannelVO mktChannelVO) {
        MktChannel mktChannel = mktChannelService.get(mktChannelVO.getId());
        mktChannelVO.setReference(mktChannel.getReference());
        mktChannelVO.setName(mktChannel.getName());
        mktChannelVO.setIsActive(mktChannel.isIsActive());
        return "marketing/mktChannelEdit";
    }

    @RequestMapping("mktChannelEdit")
    @ResponseBody
    public AjaxForm mktChannelEdit(@Valid MktChannelVO mktChannelVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            mktChannelService.saveOrUpdate(mktChannelVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }
    }

    // mail shot -------------------------------------------------------------------------------------

    @RequestMapping("toMktMailShotView")
    public String toMktMailShotView(Model model, MktMailShotListVO mktMailShotListVO) {
        return "marketing/mktMailShotManagement";
    }

    @RequestMapping("mktMailShotList")
    public String mktMailShotList(Model model, MktMailShotListVO mktMailShotListVO) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MktMailShot.class);
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        //page start
        Page<MktMailShot> page = mktMailShotService.list(detachedCriteria, mktMailShotListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(mktMailShotListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "marketing/mktMailShotList";
    }

    @RequestMapping("toMktMailShotAdd")
    public String toMktMailShotAdd(MktMailShotVO mktMailShotVO, Model model) {
        List<MktChannel> channelList = mktChannelService.getActiveListByCompany(WebThreadLocal.getCompany().getId());
        model.addAttribute("channelList", channelList);
        return "marketing/mktMailShotAdd";
    }

    @RequestMapping("mktMailShotAddContent")
    public String mktMailShotAddContent(MktMailShotVO mktMailShotVO) {
        return "marketing/mktMailShotAddContent";
    }

    @RequestMapping("mktMailShotAddConfirm")
    public String mktMailShotAddConfirm(MktMailShotVO mktMailShotVO, Model model) {
        mktMailShotVO.setChannel(mktChannelService.get(mktMailShotVO.getChannelId()));
        model.addAttribute("mktMailShotVO", mktMailShotVO);
        return "marketing/mktMailShotAddConfirm";
    }

    @RequestMapping("mktMailShotAdd")
    @ResponseBody
    public AjaxForm mktMailShotAdd(@Valid MktMailShotVO mktMailShotVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            mktMailShotVO.setChannel(mktChannelService.get(mktMailShotVO.getChannelId()));
            mktMailShotVO.setCompany(WebThreadLocal.getCompany());
            mktMailShotService.save(mktMailShotVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    // Email template ------------------------------------------------------------------------------

    @RequestMapping("toMktEmailTemplateView")
    public String toMktEmailTemplateView(Model model, MktEmailTemplateListVO mktEmailTemplateListVO) {
        return "marketing/mktEmailTemplateManagement";
    }

    @RequestMapping("mktEmailTemplateList")
    public String mktEmailTemplateList(Model model, MktEmailTemplateListVO mktEmailTemplateListVO) {
        String type = mktEmailTemplateListVO.getType();
        String subject = mktEmailTemplateListVO.getSubject();
        String isActive = mktEmailTemplateListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MktEmailTemplate.class);
        if (StringUtils.isNotBlank(type)) {
            detachedCriteria.add(Restrictions.like("type", type, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(subject)) {
            detachedCriteria.add(Restrictions.like("subject", subject, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        //page start
        Page<MktEmailTemplate> page = mktEmailTemplateService.list(detachedCriteria, mktEmailTemplateListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(mktEmailTemplateListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "marketing/mktEmailTemplateList";
    }

    @RequestMapping("toMktEmailTemplateAdd")
    public String toMktEmailTemplateAdd(MktEmailTemplateVO mktEmailTemplateVO) {
        return "marketing/mktEmailTemplateAdd";
    }

    @RequestMapping("mktEmailTemplateAdd")
    @ResponseBody
    public AjaxForm mktEmailTemplateAdd(@Valid MktEmailTemplateVO mktEmailTemplateVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            mktEmailTemplateVO.setCompany(WebThreadLocal.getCompany());
            mktEmailTemplateService.saveOrUpdate(mktEmailTemplateVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("toMktEmailTemplateEdit")
    public String toMktEmailTemplateEdit(MktEmailTemplateVO mktEmailTemplateVO) {
        MktEmailTemplate mktEmailTemplate = mktEmailTemplateService.get(mktEmailTemplateVO.getId());
        mktEmailTemplateVO.setType(mktEmailTemplate.getType());
        mktEmailTemplateVO.setSubject(mktEmailTemplate.getSubject());
        mktEmailTemplateVO.setContent(mktEmailTemplate.getContent());
        mktEmailTemplateVO.setIsActive(mktEmailTemplate.isIsActive());
        return "marketing/mktEmailTemplateEdit";
    }

    @RequestMapping("mktEmailTemplateEdit")
    @ResponseBody
    public AjaxForm mktEmailTemplateEdit(@Valid MktEmailTemplateVO mktEmailTemplateVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            mktEmailTemplateService.saveOrUpdate(mktEmailTemplateVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }
    }
}
