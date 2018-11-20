package org.spa.vo.callback;

import org.spa.model.prepaid.Prepaid;

import java.io.Serializable;

public class PrepaidListCallBackVO implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    private String name;
    private Long id;
    private String clientName;
    private String reference;
    // the member of prepaid table
    private Long memberId;
    private String prepaidType;
    private Double prepaidValue;

    public PrepaidListCallBackVO() {
		super();
	}
    public PrepaidListCallBackVO(Prepaid prepaid){
        this.id=prepaid.getId();
        this.clientName=prepaid.getUser().getFullName();
        this.reference=prepaid.getReference();
        this.name=prepaid.getName();
        this.memberId = prepaid.getUser().getId();
        this.prepaidType = prepaid.getPrepaidType();
        this.prepaidValue = prepaid.getPrepaidValue();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPrepaidType() {
        return prepaidType;
    }

    public void setPrepaidType(String prepaidType) {
        this.prepaidType = prepaidType;
    }

    public Double getPrepaidValue() {
        return prepaidValue;
    }

    public void setPrepaidValue(Double prepaidValue) {
        this.prepaidValue = prepaidValue;
    }
}
