package org.spa.service;

import org.spa.dao.base.BaseDao;
import org.spa.model.Document;
import org.spa.model.company.Company;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Ivy on 2016-6-7
 */
public interface DocumentService extends BaseDao<Document>{

    public Document save(MultipartFile file, Company company, String type);

    Document save(MultipartFile file, Company company, String type, String path);
}
