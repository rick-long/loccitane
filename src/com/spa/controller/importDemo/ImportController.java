package com.spa.controller.importDemo;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.spa.jxlsBean.ExcelUtil;
import com.spa.jxlsBean.MemberImportJxlsBean;
import com.spa.jxlsBean.importDemo.PrepaidImportGiftBean;
import com.spa.jxlsBean.importDemo.PrepaidImportJxlsBean;
import com.spa.jxlsBean.importDemo.ProductImportJxlsBean;
import com.spa.jxlsBean.importDemo.StaffImportJxlsBean;
import org.jxls.common.Context;
import org.spa.utils.RandomUtil;
import org.spa.utils.ServletUtil;
import org.spa.vo.importDemo.ImportDemoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spa.controller.BaseController;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ivy on 2016/11/16.
 */
@Controller
@RequestMapping("importDemo")
public class ImportController extends BaseController {



    public final String IMPORT_STAFF = "?loadingUrl=/staff/toView";
    public final String IMPORT_MEMBER = "?loadingUrl=/member/toView";
    public final String IMPORT_PREPAID = "?loadingUrl=/prepaid/toView";
    public final String IMPORT_PRODUCT = "?loadingUrl=/treatment/toView";
    public final String IMPORT_GIFT = "?loadingUrl=/prepaid/toView";
    public final String IMPORT_ERROR_GIFT = "?loadingUrl=/importDemo/toView?module=GIFT";

    @RequestMapping("toView")
    public String toImport(Model model, String module) {
        ImportDemoVO importDemoVO = new ImportDemoVO();
        importDemoVO.setModule(module);
        model.addAttribute("importDemoVO", importDemoVO);
        return "import/importTemplate";
    }

    @RequestMapping("doImport")
    public String doStaffImport(ImportDemoVO importDemoVO,HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        String requestUrl = request.getRequestURI();
        String[] subStrs = requestUrl.split("importDemo");
        String returnPrefix = "redirect:"+subStrs[0];

        if (!importDemoVO.getImportFile().getOriginalFilename().endsWith(".xls")) {
            return returnPrefix+IMPORT_ERROR_GIFT;
        }
        if (ImageIO.read(importDemoVO.getImportFile().getInputStream()) != null) {
            return returnPrefix+IMPORT_ERROR_GIFT;
        }

        if (importDemoVO.getModule().equals("STAFF")) {
            List<StaffImportJxlsBean> staffImportJxlsBeans = userService.importStaff(importDemoVO);
            if (staffImportJxlsBeans != null && staffImportJxlsBeans.size() > 0) {
                Context context = new Context();
                context.putVar("errorStaffList", staffImportJxlsBeans);
                File downloadFile = ExcelUtil.write("staffErrorExportTemplate.xls", context);
                ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Staff-Error-Export-") + ".xls", response);
            }
            return returnPrefix+IMPORT_STAFF;

        } else if (importDemoVO.getModule().equals("PRODUCT")) {
            List<ProductImportJxlsBean> productImportJxlsBeans = productService.importProduct(importDemoVO);
            if (productImportJxlsBeans != null && productImportJxlsBeans.size() > 0) {
                Context context = new Context();
                context.putVar("errorProductList", productImportJxlsBeans);
                File downloadFile = ExcelUtil.write("productErrorExportTemplate.xls", context);
                ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Treatment-Error-Export-") + ".xls", response);
            }
            return returnPrefix+IMPORT_PRODUCT;

        } else if (importDemoVO.getModule().equals("PREPAID")) {
            List<PrepaidImportJxlsBean> prepaidImportJxlsBeans = prepaidService.saveImportPrepaid(importDemoVO);
            if (prepaidImportJxlsBeans != null && prepaidImportJxlsBeans.size() > 0) {
                Context context = new Context();
                context.putVar("errorList", prepaidImportJxlsBeans);
                File downloadFile = ExcelUtil.write("prepaidErrorExportTemplate.xls", context);
                ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Packages-Error-Export-") + ".xls", response);
            }
            return returnPrefix+IMPORT_PREPAID;

        } else if (importDemoVO.getModule().equals("MEMBER")) {
            List<MemberImportJxlsBean> memberImportJxlsBeans = userService.importMember(importDemoVO);
            if (memberImportJxlsBeans != null && memberImportJxlsBeans.size() > 0) {
                Context context = new Context();
                context.putVar("memberErrorList", memberImportJxlsBeans);
                File downloadFile = ExcelUtil.write("memberErrorExportTemplate.xls", context);
                ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Member-Error-Export-") + ".xls", response);
            }
            return returnPrefix+IMPORT_MEMBER;

        } else if (importDemoVO.getModule().equals("GIFT")) {
            List<PrepaidImportGiftBean> prepaidImportGiftBeans = prepaidService.importPrepaidGift(importDemoVO);
            if (prepaidImportGiftBeans != null && prepaidImportGiftBeans.size() > 0) {
                Context context = new Context();
                context.putVar("prepaidGiftErrorList", prepaidImportGiftBeans);
                File downloadFile = ExcelUtil.write("prepaidGiftErrorExportTemplate.xls", context);
                ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Gift-Vouchers-Error-Export-") + ".xls", response);
            }
            return returnPrefix+IMPORT_GIFT;
        }
        return null;
    }
}
