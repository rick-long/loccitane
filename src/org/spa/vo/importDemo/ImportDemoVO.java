package org.spa.vo.importDemo;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/06/16.
 */
public class ImportDemoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MultipartFile importFile;
	
	private String module;

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public MultipartFile getImportFile() {
		return importFile;
	}
	public void setImportFile(MultipartFile importFile) {
		this.importFile = importFile;
	}
}
