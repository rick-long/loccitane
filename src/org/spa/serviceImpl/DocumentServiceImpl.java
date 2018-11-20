package org.spa.serviceImpl;

import java.util.Date;

import com.spa.constant.CommonConstant;
import org.apache.commons.io.FilenameUtils;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.Document;
import org.spa.model.company.Company;
import org.spa.service.DocumentService;
import org.spa.utils.RandomUtil;
import org.spa.utils.ServletUtil;
import org.spa.utils.WebThreadLocal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ivy 2016-6-7
 */
@Service
public class DocumentServiceImpl extends BaseDaoHibernate<Document> implements DocumentService {

    @Override
    public Document save(MultipartFile file, Company company, String type) {
        return save(file, company, type, CommonConstant.UPLOAD_DOCUMENT_PATH);
    }

    @Override
    public Document save(MultipartFile file, Company company, String type, String path) {
        if(file == null || file.isEmpty()) {
            return null;
        }
        if(!path.endsWith("/"))  {
            path = path + "/";
        }
        // 保存
        String fileName = RandomUtil.generateRandomNumberWithTime(company.getCode() + "-") + "." +  FilenameUtils.getExtension(file.getOriginalFilename());
        ServletUtil.upload(file, path, fileName);
        Document document = new Document();
        document.setCompany(company);
        document.setName(fileName);
        document.setType(type);
        document.setPath(path + fileName);
        Date date = new Date();
        String username = WebThreadLocal.getUser().getUsername();
        document.setIsActive(true);
        document.setCreated(date);
        document.setCreatedBy(username);
        document.setLastUpdated(date);
        document.setLastUpdatedBy(username);
        save(document);
        return document;
    }
}
