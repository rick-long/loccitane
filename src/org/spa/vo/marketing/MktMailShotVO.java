package org.spa.vo.marketing;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;
import org.spa.model.marketing.MktChannel;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Ivy on 2016-6-1
 */
public class MktMailShotVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long[] userGroupIds;

    @NotNull
    private Long channelId;

    private MktChannel channel;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    private MultipartFile attachment;

    private Company company;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long[] getUserGroupIds() {
        return userGroupIds;
    }

    public void setUserGroupIds(Long[] userGroupIds) {
        this.userGroupIds = userGroupIds;
    }

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public MktChannel getChannel() {
        return channel;
    }

    public void setChannel(MktChannel channel) {
        this.channel = channel;
    }
}
