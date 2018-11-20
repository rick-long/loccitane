package org.spa.vo.sales;

import java.io.Serializable;

/**
 * Created by Ivy on 2018/08/17.
 */
public class OrderBundleVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long bundleId;
	private Double bundleAmount;
	private int groups;
	
	private double totalBundleEffectiveVal;

	public Long getBundleId() {
		return bundleId;
	}

	public void setBundleId(Long bundleId) {
		this.bundleId = bundleId;
	}

	public Double getBundleAmount() {
		return bundleAmount;
	}

	public void setBundleAmount(Double bundleAmount) {
		this.bundleAmount = bundleAmount;
	}

	public int getGroups() {
		return groups;
	}

	public void setGroups(int groups) {
		this.groups = groups;
	}

	public double getTotalBundleEffectiveVal() {
		return totalBundleEffectiveVal;
	}

	public void setTotalBundleEffectiveVal(double totalBundleEffectiveVal) {
		this.totalBundleEffectiveVal = totalBundleEffectiveVal;
	}
	
}
