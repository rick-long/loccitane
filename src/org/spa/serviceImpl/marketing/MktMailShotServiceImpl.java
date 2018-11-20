package org.spa.serviceImpl.marketing;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.company.Company;
import org.spa.model.marketing.MktChannel;
import org.spa.model.marketing.MktEmailTemplate;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.user.User;
import org.spa.model.user.UserGroup;
import org.spa.service.DocumentService;
import org.spa.service.marketing.MktChannelService;
import org.spa.service.marketing.MktEmailTemplateService;
import org.spa.service.marketing.MktMailShotService;
import org.spa.service.user.UserGroupService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.marketing.MktMailShotVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spa.constant.CommonConstant;
import com.spa.thread.NewsLetterThread;

/**
 * Created by Ivy on 2016-6-1
 */
@Service
public class MktMailShotServiceImpl extends BaseDaoHibernate<MktMailShot> implements MktMailShotService {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private MktEmailTemplateService mktEmailTemplateService;

    @Autowired
    private MktChannelService mktChannelService;

    public void save(MktMailShotVO mailShotVO) {
        Date now = new Date();
        Company company = mailShotVO.getCompany();
        String userName = WebThreadLocal.getUser().getUsername();
        MktMailShot mktMailShot = new MktMailShot();
        mktMailShot.setMktChannel(mailShotVO.getChannel());
        mktMailShot.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.NEWS_LETTER_REF_PREFIX));
        mktMailShot.setCompany(company);
        mktMailShot.setType(CommonConstant.MAIL_SHOT_TYPE_NEWS_LETTER);
        mktMailShot.setStatus(CommonConstant.MAIL_SHOT_STATUS_SENDING);
        mktMailShot.setSendTime(new Date());
        mktMailShot.setSubject(mailShotVO.getSubject());
        mktMailShot.setContent(mailShotVO.getContent());
        // userIds
        Set<Long> userSet = new HashSet<>();
        for(Long userGroupId : mailShotVO.getUserGroupIds()) {
            UserGroup userGroup = userGroupService.get(userGroupId);
            userSet.addAll(userGroup.getUsers().stream().map(User::getId).collect(Collectors.toSet()));
        }
        mktMailShot.setUserIds(StringUtils.join(userSet, ","));
        mktMailShot.setSendAmount(userSet.size());
        // attachment
        MultipartFile attachment = mailShotVO.getAttachment();
        mktMailShot.setDocument(documentService.save(attachment, company, CommonConstant.DOCUMENT_TYPE_EMAIL_ATTACHMENT));

        mktMailShot.setIsActive(true);
        mktMailShot.setCreated(now);
        mktMailShot.setCreatedBy(userName);
        mktMailShot.setLastUpdated(now);
        mktMailShot.setLastUpdatedBy(userName);
        save(mktMailShot);
        new NewsLetterThread(mktMailShot).start();
    }

    public MktMailShot saveRegistrationShot(User user) {
        Date now = new Date();
        Company company = user.getCompany();
        MktEmailTemplate mktEmailTemplate = mktEmailTemplateService.getByType(CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_REGISTRATION, company.getId());
        MktChannel mktChannel = mktChannelService.getByRef(CommonConstant.MAIL_CHANNEL_SYSTEM, company.getId());
        MktMailShot mktMailShot = new MktMailShot();
        mktMailShot.setCompany(company);
        mktMailShot.setMktChannel(mktChannel);
        mktMailShot.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.NEWS_LETTER_REF_PREFIX));
        mktMailShot.setType(CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_REGISTRATION);
        mktMailShot.setUserIds(user.getId().toString());
        mktMailShot.setSubject(mktEmailTemplate.getSubject());
        mktMailShot.setContent(mktEmailTemplate.getContent());
        mktMailShot.setSendTime(now);
        mktMailShot.setSendAmount(1);
        mktMailShot.setStatus(CommonConstant.MAIL_SHOT_STATUS_SENDING);
        mktMailShot.setIsActive(true);
        mktMailShot.setCreated(now);
        mktMailShot.setCreatedBy(user.getUsername());
        mktMailShot.setLastUpdated(now);
        mktMailShot.setLastUpdatedBy(user.getUsername());
        save(mktMailShot);
        return mktMailShot;
    }

    @Override
    public MktMailShot saveCommonMktMailShot(String emailTemplateType,User user, Company company) {
    	 Date now = new Date();
         Long companyId = company.getId();
         MktEmailTemplate mktEmailTemplate = mktEmailTemplateService.getByType(emailTemplateType, companyId);
         MktChannel mktChannel = mktChannelService.getByRef(CommonConstant.MAIL_CHANNEL_SYSTEM, companyId);
         System.out.println("mktChannel:" + mktChannel);
         MktMailShot mktMailShot = new MktMailShot();
         mktMailShot.setCompany(company);
         mktMailShot.setMktChannel(mktChannel);
         mktMailShot.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.NEWS_LETTER_REF_PREFIX));
         mktMailShot.setType(emailTemplateType);
         mktMailShot.setUserIds(user.getId().toString());
         mktMailShot.setSubject(mktEmailTemplate.getSubject());
         mktMailShot.setContent(mktEmailTemplate.getContent());
         mktMailShot.setSendTime(now);
         mktMailShot.setSendAmount(1);
         mktMailShot.setStatus(CommonConstant.MAIL_SHOT_STATUS_SENDING);
         mktMailShot.setIsActive(true);
         mktMailShot.setCreated(now);
         mktMailShot.setCreatedBy(user.getUsername());
         mktMailShot.setLastUpdated(now);
         mktMailShot.setLastUpdatedBy(user.getUsername());
         save(mktMailShot);
         return mktMailShot;
    }

    @Override
    public MktMailShot saveCommonMktMailShot(String emailTemplateType,User user) {
        Date now = new Date();
        MktEmailTemplate mktEmailTemplate = mktEmailTemplateService.getByType(emailTemplateType, null);
        MktChannel mktChannel = mktChannelService.getByRef(CommonConstant.MAIL_CHANNEL_SYSTEM);
        MktMailShot mktMailShot = new MktMailShot();
        mktMailShot.setMktChannel(mktChannel);
        mktMailShot.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.NEWS_LETTER_REF_PREFIX));
        mktMailShot.setType(emailTemplateType);
        mktMailShot.setUserIds(user.getId().toString());
        mktMailShot.setSubject(mktEmailTemplate.getSubject());
        mktMailShot.setContent(mktEmailTemplate.getContent());
        mktMailShot.setSendTime(now);
        mktMailShot.setSendAmount(1);
        mktMailShot.setStatus(CommonConstant.MAIL_SHOT_STATUS_SENDING);
        mktMailShot.setIsActive(true);
        mktMailShot.setCreated(now);
        mktMailShot.setCreatedBy(user.getUsername());
        mktMailShot.setLastUpdated(now);
        mktMailShot.setLastUpdatedBy(user.getUsername());
        save(mktMailShot);
        return mktMailShot;
    }
}
