package org.spa.model.salesforce;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "IMPORT_TRIGGER_HISTORY", catalog = "loccitane")
public class ImportTriggerHistory implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String reference;
	private String module;
	private String operationEnum;
	private String channel;
	private String description;
	private String triggerTime;
	
	private String cronExpression;
	private String status;
	private String createdBy;
	private Date created;
	
	public ImportTriggerHistory(){
	}
	
	public ImportTriggerHistory(String reference, String module, String operationEnum, String channel, String description,
			String triggerTime, String cronExpression, String status, String createdBy, Date created) {
		this.module = module;
		this.operationEnum = operationEnum;
		this.channel = channel;
		this.description = description;
		this.triggerTime = triggerTime;
		this.cronExpression = cronExpression;
		this.status = status;
		this.createdBy = createdBy;
		this.created = created;
		this.reference=reference;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "reference", nullable = false,length = 100)
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@Column(name = "module", nullable = false, length = 100)
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	@Column(name = "operation_enum", length = 100)
	public String getOperationEnum() {
		return operationEnum;
	}
	public void setOperationEnum(String operationEnum) {
		this.operationEnum = operationEnum;
	}
	@Column(name = "description", length = 255)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "channel", length = 100)
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Column(name = "trigger_time", length = 100)
	public String getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}
	@Column(name = "cron_expression", length = 100)
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	@Column(name = "status", nullable = false, length = 45)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	@Column(name = "created_by", length = 100)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
