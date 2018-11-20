package org.spa.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ServletUtil {

    private static final Logger logger = LoggerFactory.getLogger(ServletUtil.class);
    public static final String UPLOAD_FILE_PATH;

    static {
        String slash = File.separator;
        UPLOAD_FILE_PATH = new StringBuffer()
                .append(System.getProperty("user.home"))
                .append(slash)
                .append("asclepous")
                .append(slash)
                .append("upload")
                .append(slash)
                .toString();
        new File(UPLOAD_FILE_PATH).mkdirs();
        logger.debug("UPLOAD_FILE_PATH:{}", UPLOAD_FILE_PATH);
    }


    public static void download(File file, String fileName, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            logger.debug("download file path:{}", file.getAbsolutePath());
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        // 删除服务器的临时文件
        file.delete();
    }

    /**
     * 获取上下文路径
     *
     * @param folder
     * @return
     */
    public static String getContextPath(String folder) {
        String path = new File(ServletUtil.class.getResource("/../../").getPath()).getPath();
        if (folder.startsWith("/")) {
            path += folder;
        } else {
            path = path + "/" + folder;
        }
        System.out.println("getContextPath:" + path);
        new File(path).mkdirs();
        return path;
    }

    /**
     * 上传文件到相对path路径下, 使用上传的文件名保存
     *
     * @param multipartFile
     * @param path
     * @return
     */
    public static File upload(MultipartFile multipartFile, String path) {
        return upload(multipartFile, path, null);
    }

    /**
     * 上传文件到相对path路径下
     *
     * @param multipartFile
     * @param path
     * @param fileName      带后缀的文件名, FilenameUtils.getExtension(originalFilename) 可以获取后缀
     */
    public static File upload(MultipartFile multipartFile, String path, String fileName) {
        String originalFilename = multipartFile.getOriginalFilename();
        String saveFileName = StringUtils.isEmpty(fileName) ? originalFilename : fileName;
        String filePath = getContextPath(path);
        File saveFile = new File(filePath, saveFileName); // 保存的文件
        try {
            multipartFile.transferTo(saveFile); // 保存multiPartFile
            logger.debug("upload file path:{}{}", filePath, saveFileName);
            return saveFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFile;
    }

    public static void upload(File file, String fileName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            logger.debug("download file path:{}", file.getAbsolutePath());
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(UPLOAD_FILE_PATH+""+fileName);
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest httpRequest) {
        if (org.apache.commons.lang.StringUtils.equalsIgnoreCase("XMLHttpRequest", httpRequest.getHeader("X-Requested-With"))) {
            return true;
        }
        return false;
    }
}
