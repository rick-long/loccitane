package org.spa.vo.user;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/8/28.
 */
public class ConsentFormUserVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull
    private Long shopId;
	@NotNull
	private Long consentFormId;

    private MultipartFile document;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsentFormId() {
		return consentFormId;
	}
    public void setConsentFormId(Long consentFormId) {
		this.consentFormId = consentFormId;
	}
    public Long getShopId() {
		return shopId;
	}
    public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

    public MultipartFile getDocument() {
        return document;
    }

    public void setDocument(MultipartFile document) {
        this.document = document;
    }
}
