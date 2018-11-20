package com.spa.freemarker;

import com.spa.constant.CommonConstant;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.spa.model.marketing.MktEmailTemplate;
import org.spa.model.marketing.MktMailShot;
import org.spa.service.marketing.MktEmailTemplateService;
import org.spa.serviceImpl.marketing.MktEmailTemplateServiceImpl;
import org.spa.utils.SpringUtil;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class EmailTemplate implements Serializable {

    private static final MktEmailTemplateService mktEmailTemplateService = SpringUtil.getBean(MktEmailTemplateServiceImpl.class);

    private Map<String, Object> subjectDataMap = new HashMap<String, Object>();
    private Map<String, Object> contentDataMap = new HashMap<String, Object>();
    private String subjectTemplate;
    private String contentTemplate;
    private String subject;
    private String content;
    private String header; // html email head
    private String footer; // html email footer
    private String end; // html email end
    private Long companyId;

    private EmailTemplate() {
    }

    public static EmailTemplate getInstance(MktMailShot mktMailShot) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setSubjectTemplate(mktMailShot.getSubject());
        emailTemplate.setContentTemplate(mktMailShot.getContent());
        if(mktMailShot.getCompany() != null) {
            emailTemplate.setCompanyId(mktMailShot.getCompany().getId());
        }
        return emailTemplate;
    }

    public void initHtmlWrapper() {
        MktEmailTemplate header = mktEmailTemplateService.getByType(CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_EMAIL_HEADER, companyId);
        MktEmailTemplate end = mktEmailTemplateService.getByType(CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_EMAIL_END, companyId);
        MktEmailTemplate footer = mktEmailTemplateService.getByType(CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_EMAIL_FOOTER, companyId);
        setHeader(header != null ? header.getContent() : "");
        setEnd(end != null ? end.getContent() : "");
        setFooter(footer != null ? footer.getContent() : "");
    }

    public EmailTemplate setSubjectData(String key, Object value) {
        this.subjectDataMap.put(key, value);
        return this;
    }

    public EmailTemplate setContentData(String key, Object value) {
        this.contentDataMap.put(key, value);
        return this;
    }

    public EmailTemplate setContentData(Map<String, Object> dataMap) {
        this.contentDataMap.putAll(dataMap);
        return this;
    }

    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public EmailTemplate setSubjectTemplate(String subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
        return this;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public EmailTemplate setContentTemplate(String contentTemplate) {
        this.contentTemplate = contentTemplate;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getHeader() {
        return header;
    }

    public EmailTemplate setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getFooter() {
        return footer;
    }

    public EmailTemplate setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public String getEnd() {
        return end;
    }

    public EmailTemplate setEnd(String end) {
        this.end = end;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public EmailTemplate setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * 模板内容转换
     */
    public void convert() {
        initHtmlWrapper();
        // 处理template
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("subjectTemplate", subjectTemplate);
        stringLoader.putTemplate("contentTemplate", contentTemplate);
        cfg.setTemplateLoader(stringLoader);
        Template ct;
        Template st;
        StringWriter contentWriter = new StringWriter();
        StringWriter subjectWriter = new StringWriter();
        try {
            ct = cfg.getTemplate("contentTemplate", "utf-8");
            st = cfg.getTemplate("subjectTemplate", "utf-8");
            st.process(subjectDataMap, subjectWriter);
            ct.process(contentDataMap, contentWriter);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        subject = subjectWriter.toString();
        content = header + contentWriter.toString() + footer + end; // 合成html结构
    }

}
