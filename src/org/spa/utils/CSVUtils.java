package org.spa.utils;

import java.io.*;
import java.util.*;
/**
 * Created by Ivy on 2017/07/26.
 */
public class CSVUtils {
	
	public static void writeCSV(List<Object> headList,List<List<Object>> dataList,String fileName,String filePath) {
		
		 File csvFile = null;
		 BufferedWriter csvWtriter = null;
		 try {
			 csvFile = new File(filePath + fileName);
			 File parent = csvFile.getParentFile();
			 if (parent != null && !parent.exists()) {
				 parent.mkdirs();
		 	}
		 	csvFile.createNewFile();
				
		 	 // GB2312使正确读取分隔符","
		 	csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
			
		 	//文件下载，使用如下代码
		 	//response.setContentType("application/csv;charset=gb18030");
		 	//response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		 	//ServletOutputStream out = response.getOutputStream();
		 	//csvWtriter = new BufferedWriter(new OutputStreamWriter(out, "GB2312"), 1024);
			 
		 	int num = headList.size() / 2;
		 	StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < num; i++) {
				buffer.append(" ,");
		 	}
		 	// 写入文件头部
		 	writeRow(headList, csvWtriter);
		 	
		 	// 写入文件内容
		 	for (List<Object> row : dataList) {
		 		writeRow(row, csvWtriter);
			}
		 	csvWtriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		 	try {
		 		csvWtriter.close();
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
		}
	}
		  
		/**
			 * 写一行数据
			* @param row 数据列表
			* @param csvWriter
			* @throws IOException
			*/
	private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
		int i=1;
		for (Object data : row) {
			StringBuffer sb = new StringBuffer();
			String rowStr ="";
			if(i == row.size()){
				 rowStr = sb.append("\"").append(data).append("\"").toString();
			}else{
				 rowStr = sb.append("\"").append(data).append("\",").toString();
			}
			i++;
			csvWriter.write(rowStr);
		}
		csvWriter.newLine();
	}
}
