package org.spa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class PDFUtil {

	// wkhtmltopdf 在系统中的路径
	//private static final String EXECUTER = "D:\\commonTools\\wkhtmltopdf\\bin\\wkhtmltopdf";//mac 环境下
	private static final String EXECUTER = "wkhtmltopdf";//linux环境下
	private static final String JSESSIONID_KEY = "JSESSIONID";

	private static String PDF_PATH = "";

	static {
		try {
			PDF_PATH = new File(PDFUtil.class.getClassLoader().getResource("").toURI()).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath() + "/temp/pdf/";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		new File(PDF_PATH).mkdirs();
	}

	public static String getUrlRoot(HttpServletRequest request) {
		String urlRoot;
		if (request.getServerPort() == 80) {
			urlRoot = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		} else {
			urlRoot = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
		return urlRoot;
	}

	/**
	 * 
	 * @param url
	 *            相對路徑
	 * @param request
	 * @param parameter
	 *            請求參數
	 * @return
	 * @throws IOException
	 */
	public static File convert(String url, HttpServletRequest request, Map<String, Object> parameter) throws IOException {
		String destPath = PDF_PATH + RandomStringUtils.randomAlphanumeric(40) + ".pdf";
		System.out.println(destPath);
		StringBuilder cmd = new StringBuilder();
		cmd.append(EXECUTER);
        // content type
//        cmd.append(" --custom-header Content-Type 'application/x-www-form-urlencoded; charset=UTF-8' ");
		// JSESSIONID 处理
        //cmd.append(" --no-images ");
        cmd.append(" --load-error-handling ignore ");
		cmd.append(" --cookie ");
		cmd.append(JSESSIONID_KEY).append(" \"").append(request.getSession().getId()).append("\" ");
		// 其他cookie处理
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (JSESSIONID_KEY.equals(cookie.getName())) {
				continue;
			}
			cmd.append(" --cookie ");
			cmd.append(cookie.getName()).append(" \"").append(cookie.getValue()).append("\" ");
		}

		// 处理参数
        // !!! 参数至少区分是不是字符串，如果是字符串加引号，如果不是字符串，必须删除引号
        if (parameter != null) {
            for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            	if(entry.getValue() !=null){
            		 if (entry.getValue() instanceof CharSequence) {
            			 if(StringUtils.isNotBlank((CharSequence)entry.getValue())){
            				 cmd.append(" --post ").append(entry.getKey()).append(" '").append(entry.getValue()).append("' ");
            			 }
                     } else {
                         cmd.append(" --post ").append(entry.getKey()).append(" ").append(entry.getValue()).append(" ");
                     }
            	}
            }
        }

        cmd.append(" ");
        cmd.append(getUrlRoot(request));
        cmd.append(url); // 相对url路径
        cmd.append(" ");

		cmd.append(destPath); // 輸出的pdf文件路徑

		System.out.println("convert command:" + cmd.toString());
        InputStream inputStream = null;
		try {
			Process p = Runtime.getRuntime().exec(cmd.toString());
            inputStream = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				System.out.println(tmp);
			}

            // 输出错误
            InputStreamReader isr = new InputStreamReader(p.getErrorStream(), "utf-8");
            BufferedReader errors = new BufferedReader(isr);
            String line;
            System.out.println("ERROR:");
            while ((line = errors.readLine()) != null) {
                System.out.println(line); //输出内容
            }
            p.getOutputStream().close();
			int exitValue = p.waitFor();
			System.out.println("exitValue:" + exitValue);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return new File(destPath);
	}
	/**
	 *
	 * @param url
	 *            相對路徑
	 * @param parameter
	 *            請求參數
	 * @return
	 * @throws IOException
	 */
	public static File convert(String url, Map<String, Object> parameter) throws IOException {
		String destPath = PDF_PATH + RandomStringUtils.randomAlphanumeric(40) + ".pdf";
		System.out.println(destPath);
		StringBuilder cmd = new StringBuilder();
		cmd.append(EXECUTER);
		// content type
//        cmd.append(" --custom-header Content-Type 'application/x-www-form-urlencoded; charset=UTF-8' ");
		// JSESSIONID 处理
		//cmd.append(" --no-images ");
		cmd.append(" --load-error-handling ignore ");

		// 处理参数
		// !!! 参数至少区分是不是字符串，如果是字符串加引号，如果不是字符串，必须删除引号
		if (parameter != null) {
			for (Map.Entry<String, Object> entry : parameter.entrySet()) {
				if(entry.getValue() !=null){
					if (entry.getValue() instanceof CharSequence) {
						if(StringUtils.isNotBlank((CharSequence)entry.getValue())){
							cmd.append(" --post ").append(entry.getKey()).append(" ").append(entry.getValue()).append(" ");
						}
					} else {
						cmd.append(" --post ").append(entry.getKey()).append(" ").append(entry.getValue()).append(" ");
					}
				}
			}
		}
		cmd.append(" ");
		cmd.append(url); // 相对url路径
		cmd.append(" ");
		cmd.append(destPath); // 輸出的pdf文件路徑
		System.out.println("convert command:" + cmd.toString());
		InputStream inputStream = null;
		try {
			Process p = Runtime.getRuntime().exec(cmd.toString());
			inputStream = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				System.out.println(tmp);
			}

			// 输出错误
			InputStreamReader isr = new InputStreamReader(p.getErrorStream(), "utf-8");
			BufferedReader errors = new BufferedReader(isr);
			String line;
			System.out.println("ERROR:");
			while ((line = errors.readLine()) != null) {
				System.out.println(line); //输出内容
			}
			p.getOutputStream().close();
			int exitValue = p.waitFor();
			System.out.println("exitValue:" + exitValue);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
		}
		return new File(destPath);
	}
	public static File convert(String url, HttpServletRequest request) throws IOException {
		return convert(url, request, null);
	}
}
