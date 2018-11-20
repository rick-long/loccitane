package org.spa.jxlsBean;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.common.Context;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class ExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	public static final String TEMPLATE_PATH;
	public static final String TEMP_PATH;
	public static final String XML_CONFIG_PATH;

	static {
		TEMPLATE_PATH = new File(ExcelUtil.class.getResource("/").getPath()).toURI().getPath() + "excelTemplate/";
		TEMP_PATH = new File(ExcelUtil.class.getResource("/").getPath()).getParentFile().getParentFile().getParentFile().getParentFile().toURI().getPath() + "temp/";
		XML_CONFIG_PATH = new File(ExcelUtil.class.getResource("/").getPath()).toURI().getPath() + "excelReaderConfig/";
		new File(TEMPLATE_PATH).mkdirs();
		new File(TEMP_PATH).mkdirs();
		new File(XML_CONFIG_PATH).mkdirs();
	}

	public static File write(String templateName, Context context) {
		String path = TEMPLATE_PATH + templateName;
		String out = TEMP_PATH + RandomStringUtils.randomAlphanumeric(40) + ".xls";
		logger.debug("output dir:" + out);
		try (InputStream is = new FileInputStream(path)) {
			try (OutputStream os = new FileOutputStream(out)) {
				JxlsHelper.getInstance().processTemplate(is, os, context);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return new File(out);
	}

	public static Map<String, Object> read(InputStream inputXLS, String xmlConfig, Map<String, Object> beans) {
		String path = XML_CONFIG_PATH + xmlConfig;
		try {
			InputStream inputXML = new FileInputStream(path);
			XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
			mainReader.read(inputXLS, beans);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return beans;
	}
}
