package org.spa.vo.common;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/03/23.
 */
public class SelectOptionVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long value;

    private String label;

    private Boolean selected;

    private String groupName;

    public SelectOptionVO() {
    }

    public SelectOptionVO(Long value, String label) {
        this.value = value;
        this.label = label;
    }

    public SelectOptionVO(String groupName) {
        this.groupName = groupName;
    }

    public SelectOptionVO(String groupName, Long value, String label) {
        this.groupName = groupName;
        this.value = value;
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
