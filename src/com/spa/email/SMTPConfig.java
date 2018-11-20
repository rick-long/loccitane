package com.spa.email;

import org.spa.utils.PropertiesUtil;

public class SMTPConfig implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String host;
	private int port;
	private boolean ssl;
	private String username;
	private String password;
    private String fromAddress;
    private String fromName;


	public static final SMTPConfig SMTP_CONFIG = new SMTPConfig();
	
	static {
        String username =  PropertiesUtil.getValueByName("SMTP_USERNAME");//"supportmkt@senseoftouch.com.hk";
        String password = PropertiesUtil.getValueByName("SMTP_PASSWORD");//"6g47sJH";
        String host = PropertiesUtil.getValueByName("SMTP_HOST");//"mail.senseoftouch.com.hk";
        int port = PropertiesUtil.getIntegerValueByName("SMTP_PORT");//7366;
        String fromAddress = PropertiesUtil.getValueByName("SMTP_FROM_ADDRESS");//"supportmkt@senseoftouch.com.hk";
        String fromName = PropertiesUtil.getValueByName("SMTP_FROM_NAME");//"Sense of Touch";
	    SMTP_CONFIG.setHost(host);
	    SMTP_CONFIG.setPort(port);
	    SMTP_CONFIG.setUsername(username);
	    SMTP_CONFIG.setPassword(password);
        SMTP_CONFIG.setFromAddress(fromAddress);
        SMTP_CONFIG.setFromName(fromName);
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
}
