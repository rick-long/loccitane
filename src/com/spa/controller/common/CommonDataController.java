package com.spa.controller.common;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.spa.model.Document;
import org.spa.utils.DateUtil;
import org.spa.utils.ServletUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ivy on 2016/08/23.
 */
@Controller
@RequestMapping("common")
public class CommonDataController extends BaseController {

    @RequestMapping("/loadYearAndMonth")
    public String loadYearAndMonth(HttpServletRequest request, Model model) {

        List<String> allYearAndMonths = new ArrayList<>();
        Date fromDate = new Date();
        Date toDate = new Date();
        try {
            fromDate = new SimpleDateFormat("yyyy-MM").parse("2016-08");
            toDate = new SimpleDateFormat("yyyy-MM").parse("2020-01");//定义结束日期

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar date = Calendar.getInstance();//定义日期实例
        date.setTime(fromDate);//设置日期起始时间
        while (date.getTime().before(toDate)) {//判断是否到结束日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String yearAndMonthString = sdf.format(date.getTime());
            allYearAndMonths.add(yearAndMonthString);
            date.add(Calendar.MONTH, 1);//进行当前日期月份加1
        }
        model.addAttribute("allYearAndMonths", allYearAndMonths);
        
        Date now = new Date();
        try {
			String currentMonth=DateUtil.dateToString(now, "yyyy-MM");
			model.addAttribute("currentMonth", currentMonth);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return "common/date/yearAndDate";
    }

    @RequestMapping("downloadDocument/{documentId}")
    public void downloadDocument(@PathVariable Long documentId, HttpServletResponse response) {
        Document document = documentService.get(documentId);
        if (document != null && StringUtils.isNotBlank(document.getPath())) {
            String path = document.getAbsolutePath();
            System.out.println("document absolutePath:" + path);
            ServletUtil.download(new File(path), document.getName(), response);
        } else {
            System.out.println("cannot found document with id:" + documentId);
        }
    }
}
