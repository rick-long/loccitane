package org.spa.model.staticPage;

// Generated 2015-11-19 11:54:45 by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by Ivy on 2016/01/16.
 */
@Entity
@Table(name = "STATIC_PAGES", catalog = "parcelive")
public class StaticPages implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String url;
	private String parameter;
	private String content;
	private Date date;
	private Date stopAutoUpdateDate;

	public StaticPages() {
	}

	public StaticPages(String url, String content, Date date) {
		this.url = url;
		this.content = content;
		this.date = date;
	}

	public StaticPages(String url, String parameter, String content, Date date, Date stopAutoUpdateDate) {
		this.url = url;
		this.parameter = parameter;
		this.content = content;
		this.date = date;
		this.stopAutoUpdateDate = stopAutoUpdateDate;
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

	@Column(name = "url", nullable = false, length = 65535)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "parameter", length = 65535)
	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", nullable = false, length = 19)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stop_auto_update_date", length = 19)
	public Date getStopAutoUpdateDate() {
		return this.stopAutoUpdateDate;
	}

	public void setStopAutoUpdateDate(Date stopAutoUpdateDate) {
		this.stopAutoUpdateDate = stopAutoUpdateDate;
	}

}
