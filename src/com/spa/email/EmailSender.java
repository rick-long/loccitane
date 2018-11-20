package com.spa.email;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.spa.model.Document;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.user.User;
import org.spa.service.marketing.MktOutboxService;
import org.spa.serviceImpl.marketing.MktOutboxServiceImpl;
import org.spa.utils.SpringUtil;

public class EmailSender implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String executor; // 执行者
    private List<EmailAddress> toAddresses = new ArrayList<EmailAddress>();
    private List<EmailAddress> ccAddresses = new ArrayList<EmailAddress>();
    private List<EmailAddress> bccAddresses = new ArrayList<EmailAddress>();
    private List<Attachment> attachments = new ArrayList<Attachment>();
    private String subject;
    private String content;
    private User user; // 接收email的user对象
    private Prepaid prepaid;



    private MktMailShot mktMailShot;
    private SMTPConfig smtpConfig = SMTPConfig.SMTP_CONFIG;

    protected static final MktOutboxService mktOutboxService = SpringUtil.getBean(MktOutboxServiceImpl.class);

    protected EmailSender() {
    }

    public static EmailSender getInstance(User user, MktMailShot mktMailShot, String subject, String content) {
        EmailSender emailSender = new EmailSender();
/*        emailSender.addTo(user.getEmail(), user.getUsername());*/
        emailSender.setUser(user);
        emailSender.setMktMailShot(mktMailShot);
        Document document = mktMailShot.getDocument();
        if (document != null) {
            String attachmentPath = document.getAbsolutePath();
            System.out.println("attachmentPath:" + attachmentPath);
            emailSender.addAttachment(Attachment.getInstance(attachmentPath));
        }
        emailSender.setSubject(subject);
        emailSender.setContent(content);
        return emailSender;
    }

    public EmailSender addAttachment(Attachment attachment) {
        attachments.add(attachment);
        return this;
    }

    public Prepaid getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(Prepaid prepaid) {
        this.prepaid = prepaid;
    }

    public User getUser() {
        return user;
    }

    public EmailSender setUser(User user) {
        this.user = user;
        return this;
    }
    public MktMailShot getMktMailShot() {
        return mktMailShot;
    }

    public EmailSender setMktMailShot(MktMailShot mktMailShot) {
        this.mktMailShot = mktMailShot;
        return this;
    }

    public SMTPConfig getSmtpConfig() {
        return smtpConfig;
    }

    public EmailSender setSmtpConfig(SMTPConfig smtpConfig) {
        this.smtpConfig = smtpConfig;
        return this;
    }

    public EmailSender addAttachment(String path) {
        attachments.add(Attachment.getInstance(path));
        return this;
    }

    public EmailSender addAttachment(String path, String description, String name) {
        Attachment attachment = Attachment.getInstance(path);
        attachment.setName(name).setDescription(description);
        attachments.add(attachment);
        return this;
    }

    public EmailSender addAttachmentWithUrl(String url) {
        attachments.add(Attachment.getInstance().setUrl(url));
        return this;
    }

    public EmailSender addAttachmentWithUrl(String url, String description, String name) {
        Attachment attachment = Attachment.getInstance();
        attachment.setUrl(url).setName(name).setDescription(description);
        attachments.add(attachment);
        return this;
    }

    public EmailSender addTo(String email) {
        toAddresses.add(new EmailAddress(email));
        return this;
    }

    public EmailSender addTo(String email, String name) {
        toAddresses.add(new EmailAddress(email, name));
        return this;
    }

    public EmailSender addCc(String email) {
        ccAddresses.add(new EmailAddress(email));
        return this;
    }

    public EmailSender addCc(String email, String name) {
        ccAddresses.add(new EmailAddress(email, name));
        return this;
    }

    public EmailSender addBcc(String email) {
        bccAddresses.add(new EmailAddress(email));
        return this;
    }

    public EmailSender addBcc(String email, String name) {
        bccAddresses.add(new EmailAddress(email, name));
        return this;
    }

    public String getToAddressesStr() {
        if (toAddresses.size() == 0) {
            return null;
        }
        return StringUtils.join(toAddresses.stream().map(e->e.getAddress()).toArray(), ",");
        /*StringBuilder sb = new StringBuilder();
        for (EmailAddress ea : toAddresses) {
            sb.append(ea.getAddress());
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1);*/
    }

    public String getCcAddressesStr() {
        if (ccAddresses.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (EmailAddress ea : ccAddresses) {
            sb.append(ea.getAddress());
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public String getBccAddressesStr() {
        if (bccAddresses.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (EmailAddress ea : bccAddresses) {
            sb.append(ea.getAddress());
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public List<EmailAddress> getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(List<EmailAddress> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public List<EmailAddress> getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(List<EmailAddress> ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public List<EmailAddress> getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(List<EmailAddress> bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getSubject() {
        return subject;
    }

    public EmailSender setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public EmailSender setContent(String content) {
        this.content = content;
        return this;
    }

    public SMTPConfig getSMTPConfig() {
        return smtpConfig;
    }

    public EmailSender setSMTPConfig(SMTPConfig smtpConfig) {
        this.smtpConfig = smtpConfig;
        return this;
    }

    public EmailSender setSMTPConfig(String host, int port, boolean ssl, String username, String password) {
        SMTPConfig smtpConfig = new SMTPConfig();
        smtpConfig.setHost(host);
        smtpConfig.setPort(port);
        smtpConfig.setSsl(ssl);
        smtpConfig.setUsername(username);
        smtpConfig.setPassword(password);
        this.smtpConfig = smtpConfig;
        return this;
    }

    /**
     * 发送Email
     *
     * @return
     */
    public boolean send() {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(smtpConfig.getHost());
        email.setSmtpPort(smtpConfig.getPort());
        email.setAuthenticator(new DefaultAuthenticator(smtpConfig.getUsername(), smtpConfig.getPassword()));
        email.setSSLOnConnect(smtpConfig.isSsl());
        email.setSubject(subject);
        try {
            email.setFrom(smtpConfig.getFromAddress(), smtpConfig.getFromName());
            email.setHtmlMsg(content);
            for (EmailAddress emailAddress : getToAddresses()) {
                email.addTo(emailAddress.getAddress(), emailAddress.getName());
            }
            for (EmailAddress emailAddress : getCcAddresses()) {
                email.addCc(emailAddress.getAddress(), emailAddress.getName());
            }
            for (EmailAddress emailAddress : getBccAddresses()) {
                email.addBcc(emailAddress.getAddress(), emailAddress.getName());
            }
            // 处理附件
            for (Attachment attachment : getAttachments()) {
                EmailAttachment emailAttachment = new EmailAttachment();
                if (StringUtils.isNotBlank(attachment.getPath())) {
                    emailAttachment.setPath(attachment.getPath());
                } else if (StringUtils.isNotBlank(attachment.getUrl())) {
                    emailAttachment.setURL(new URL(attachment.getUrl()));
                } else {
                    continue;
                }
                if (StringUtils.isNotBlank(attachment.getName())) {
                    emailAttachment.setName(attachment.getName());
                }
                if (StringUtils.isNotBlank(attachment.getDescription())) {
                    emailAttachment.setDescription(attachment.getDescription());
                } else {
                    emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
                }
                email.attach(emailAttachment);
            }
            email.send(); // 发送
            mktOutboxService.save(this, "success");
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            mktOutboxService.save(this, "error");
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            mktOutboxService.save(this, "error");
            return false;
        }
    }

    public String getExecutor() {
        return executor;
    }

    public EmailSender setExecutor(String executor) {
        this.executor = executor;
        return this;
    }

    public static void main(String[] args) {
        //SimpleEmail simpleEmail = SimpleEmail.getInstance("Ivy@interbiztech.cn", "Ivy");
        //simpleEmail.setSubject("Test").setContent("test");
        //simpleEmail.addAttachmentWithUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        //simpleEmail.send();

    }

}
