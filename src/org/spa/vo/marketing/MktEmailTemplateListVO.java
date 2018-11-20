package org.spa.vo.marketing;

import org.spa.model.marketing.MktEmailTemplate;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-6-1
 */
public class MktEmailTemplateListVO extends Page<MktEmailTemplate> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;
    private String subject;
	private String isActive;

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
