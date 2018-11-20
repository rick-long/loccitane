package com.spa.controller.product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.product.*;
import org.spa.model.user.User;
import org.spa.utils.*;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.page.Page;
import org.spa.vo.product.ProductAddVO;
import org.spa.vo.product.ProductEditVO;
import org.spa.vo.product.ProductListVO;
import org.spa.vo.user.MemberListVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/01/16.
 */
@Controller
@RequestMapping("treatment")
public class TreatmentController extends BaseController {


    public static final String PRINT_BARCODE_URL="/treatment/treatmentTemplate";
	
    @RequestMapping("toView")
    public String treatmentManagement(Model model, ProductListVO treatmentListVO) {
        return "treatment/treatmentManagement";
    }

    @RequestMapping("list")
    public String treatmentList(Model model, ProductListVO treatmentListVO) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);

        String name = treatmentListVO.getName();
        String isActive = treatmentListVO.getIsActive();
        String showOnline =treatmentListVO.getShowOnline();
        
        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(showOnline)) {
            detachedCriteria.add(Restrictions.eq("showOnApps", Boolean.valueOf(showOnline)));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
        }

        Long categoryId = treatmentListVO.getCategoryId();
        if (categoryId == null) {
            Category category=categoryService.get("reference", CommonConstant.CATEGORY_REF_TREATMENT);
            categoryId = category.getId();
        }


        List<Long> allChildren = new ArrayList<>();
        allChildren.add(categoryId);
        categoryService.getAllChildrenByCategory(allChildren, categoryId);
        if(allChildren.size() > 0) {
            detachedCriteria.add(Restrictions.in("category.id", allChildren));
        }

        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }

        if (StringUtils.isNotBlank(treatmentListVO.getBarcode())) {
            detachedCriteria.createAlias("productOptions", "p").add(Restrictions.eq("p.barcode", treatmentListVO.getBarcode()));
        }

        //page start
        Page<Product> treatmentPage = productService.list(detachedCriteria, treatmentListVO.getPageNumber(), treatmentListVO.getPageSize());
        model.addAttribute("page", treatmentPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(treatmentListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "treatment/treatmentList";
    }

    @RequestMapping("toAdd")
    public String toAddProduct(Model model, ProductAddVO treatmentAddVO) {

        //set default brand to be selected
        Brand defaultBrand = brandService.get("reference", CommonConstant.DEFAULT_BRAND_REF);
        treatmentAddVO.setBrandId(defaultBrand.getId());
        Category category= null;
        if (treatmentAddVO.getCategoryId() == null) {
            category=categoryService.get("reference", CommonConstant.CATEGORY_REF_TREATMENT);
        } else {
            category=categoryService.get(Long.valueOf(treatmentAddVO.getCategoryId()));
        }
        model.addAttribute("category", category);
        return "treatment/treatmentAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm addProduct(@Valid ProductAddVO treatmentAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Product treatment = null;
            try {
                treatment = productService.saveProduct(treatmentAddVO);
            } catch (Exception e) {
                AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.product.option"));
            }
            AjaxForm successAjaxForm = AjaxFormHelper.success();
            successAjaxForm.addErrorFields(new ErrorField("treatmentId", treatment.getId().toString()));
            successAjaxForm.addErrorFields(new ErrorField("success", I18nUtil.getMessageKey("label.add.successfully")));
            return successAjaxForm;
        }
    }

    @RequestMapping("toEdit")
    public String toEditProduct(Model model, ProductEditVO treatmentEditVO) {

        //set default brand to be selected
        Long id = treatmentEditVO.getId();
        if (id != null && id > 0) {
            Product treatment = productService.get(id);
            treatmentEditVO.setBrandId(treatment.getBrand().getId());

            treatmentEditVO.setName(treatment.getName());

            treatmentEditVO.setCategoryId(treatment.getCategory().getId());
            treatmentEditVO.setShowOnline(treatment.getShowOnApps());
            treatmentEditVO.setId(treatment.getId());
            treatmentEditVO.setActive(treatment.isIsActive());

        }
        return "treatment/treatmentEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm editProduct(@Valid ProductEditVO treatmentEditVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Product treatment = null;
            try {
                treatment = productService.updateProduct(treatmentEditVO);
            } catch (Exception e) {
              return AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.product.option"));
            }
            AjaxForm successAjaxForm = AjaxFormHelper.success();
            successAjaxForm.addErrorFields(new ErrorField("treatmentId", treatment.getId().toString()));
            successAjaxForm.addErrorFields(new ErrorField("success", I18nUtil.getMessageKey("label.add.successfully")));
            return successAjaxForm;
        }
    }

    @RequestMapping("addPdkeyAndPokey")
    public String addPdkeyAndPokey(ProductAddVO treatmentAddVO, Model model) {
        Long companyId = WebThreadLocal.getCompany().getId();
        Long categoryId = treatmentAddVO.getCategoryId();
       
        List<ProductDescriptionKey> pdkList = productDescriptionKeyService.getPdKeysByCategoryIdAndCompanyId(categoryId, companyId);
        List<ProductOptionKey> pokList= productOptionKeyService.getPoKeysByCategoryIdAndCompanyId(categoryId, companyId);
        if(pokList != null || pokList.size() > 0){
            Collections.sort(pokList, new Comparator<ProductOptionKey>() {
                @Override
                public int compare(ProductOptionKey o1, ProductOptionKey o2) {
                    if(o1.getId() > o2.getId()){
                        return -1;
                    }
                    if(o1.getId() < o2.getId()){
                        return 1;
                    }
                    return 0;
                }
            });
        }

        model.addAttribute("pdkList", pdkList);
        model.addAttribute("pokList", pokList);
        
        return "treatment/addPdkeyAndPokey";
    }

    @RequestMapping("editPdkeyAndPokey")
    public String editPdkeyAndPokey(ProductEditVO treatmentEditVO, Model model) {
        Long companyId = WebThreadLocal.getCompany().getId();
        Long categoryId = treatmentEditVO.getCategoryId();
        
        List<ProductDescriptionKey> pdkList = productDescriptionKeyService.getPdKeysByCategoryIdAndCompanyId(categoryId, companyId);
        List<ProductOptionKey> pokList= productOptionKeyService.getPoKeysByCategoryIdAndCompanyId(categoryId, companyId);
        if(pokList != null || pokList.size() > 0){
            Collections.sort(pokList, new Comparator<ProductOptionKey>() {
                @Override
                public int compare(ProductOptionKey o1, ProductOptionKey o2) {
                    if(o1.getId() > o2.getId()){
                        return -1;
                    }
                    if(o1.getId() < o2.getId()){
                        return 1;
                    }
                    return 0;
                }
            });
        }
        model.addAttribute("pdkList", pdkList);
        model.addAttribute("pokList", pokList);
        Product treatment = productService.get(treatmentEditVO.getId());
        model.addAttribute("product", treatment);
        return "treatment/editPdkeyAndPokey";
    }

    @RequestMapping("treatmentConfirm")
    public String treatmentConfirm(ProductAddVO treatmentAddVO, Model model) {
        Category category = categoryService.get(treatmentAddVO.getCategoryId());
        model.addAttribute("category", category);
        
        addPdkeyAndPokey(treatmentAddVO, model);
        model.addAttribute("productVO", treatmentAddVO);
        return "treatment/treatmentConfirm";
    }

    @RequestMapping("editConfirm")
    public String editConfirm(ProductEditVO treatmentEditVO, Model model) {
        Category category = categoryService.get(treatmentEditVO.getCategoryId());

        model.addAttribute("category", category);

        editPdkeyAndPokey(treatmentEditVO, model);
        model.addAttribute("productVO", treatmentEditVO);
        return "treatment/treatmentConfirm";
    }


    @RequestMapping("printBarCode")
    public void printBarCode(ProductListVO treatmentListVO, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap();
        map.put("categoryId", treatmentListVO.getCategoryId());

        String downloadFileName = RandomUtil.generateRandomNumberWithDate("Customer-Report-")+".pdf";
        try {

            File downloadFile = PDFUtil.convert(PRINT_BARCODE_URL, request, map);
            ServletUtil.download(downloadFile, downloadFileName, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("treatmentTemplate")
    public String treatmentTemplate( Long categoryId , Model model) {

        Category category= null;

        if (categoryId == null) {
            category=categoryService.get("reference", CommonConstant.CATEGORY_REF_TREATMENT);
        } else {
            category=categoryService.get(Long.valueOf(categoryId));
        }

        Map<Long, Object> map = new HashMap<>();
        for (Category category1 : category.getSortedChilden()) {
            for (Product p : category1.getSortedProducts()) {
                for (ProductOption po : p.getProductOptions()) {
                    if (StringUtils.isBlank(po.getBarcode())) {
                        String num = generateBarCode();
                        map.put(po.getId(), num);
                        po.setId(po.getId());
                        po.setBarcode(num);
                        productOptionService.update(po);
                    }
                }
            }
        }

        for (Long key : map.keySet()) {
            String paths = new File(TreatmentController.class.getResource("/").getPath()).getParentFile().getParentFile().getParentFile().getParentFile().toURI().getPath() + "temp/" + map.get(key) + ".png";
            BarCodeUtil.generateFile(String.valueOf(map.get(key)), paths);
        }

        model.addAttribute("category", category);
        String path = new File(TreatmentController.class.getResource("/").getPath()).getParentFile().getParentFile().getParentFile().getParentFile().toURI().getPath() + "temp/";
        String paths = null;
        if (path.substring(0, 1).equals("/")) {
            paths = path.substring(1, path.length());
        }
        model.addAttribute("paths", paths);
        return "treatment/treatmentTemplate";
    }


    public String generateBarCode() {
        int numTopThree = 489;
        String fieldName = "barcode";
        String tableName = "Product_Option";
        Integer number = 0001;
        String nums = commonService.getIDBySql(fieldName,tableName, number);
        int numMiddleFour = Integer.valueOf(nums) + 1;
        System.out.println(numMiddleFour);

        //得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(4);
        //设置最小整数位数
        nf.setMinimumIntegerDigits(4);
        //输出测试语句
        System.out.println(nf.format(numMiddleFour));

        int numLastSix = (int)((Math.random() * 9 + 1) * 100000);

        String num = numTopThree + nf.format(numMiddleFour) + numLastSix;
        return String.valueOf(num);
    }

    @RequestMapping("quicksearch")
    public String quicksearch(Model model, MemberListVO memberListVO) {

        return "treatment/quickSearchTreatment";
    }

    @RequestMapping("quicksearchlist")
    public String quicksearchlist(Model model, MemberListVO memberListVO) {

        /*String username=memberListVO.getUsername();
        if (StringUtils.isNotBlank(username)) {

            DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
            criteria.add(Restrictions.eq("enabled", true));
            criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
            criteria.createAlias("phones", "phone");

//			3个or以上也可以这么实现
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("email", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("firstName", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("lastName", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("fullName", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("phone.number", username, MatchMode.ANYWHERE));
            criteria.add(disjunction);

            //page start
            Page<User> memberPage = userService.list(criteria, memberListVO.getPageNumber(),memberListVO.getPageSize());
            if(memberPage ==null ||  (memberPage !=null && memberPage.getList().size() ==0)){
                DetachedCriteria criteria1 = DetachedCriteria.forClass(User.class);
                criteria1.add(Restrictions.eq("enabled", true));
                criteria1.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER));

//				3个or以上也可以这么实现
                Disjunction disjunction2 = Restrictions.disjunction();
                disjunction2.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
                disjunction2.add(Restrictions.like("email", username, MatchMode.ANYWHERE));
                disjunction2.add(Restrictions.like("firstName", username, MatchMode.ANYWHERE));
                disjunction2.add(Restrictions.like("lastName", username, MatchMode.ANYWHERE));
                disjunction2.add(Restrictions.like("fullName", username, MatchMode.ANYWHERE));
                criteria1.add(disjunction2);

                memberPage = userService.list(criteria1, memberListVO.getPageNumber(),memberListVO.getPageSize());
            }

            model.addAttribute("page", memberPage);

            try {
                model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(memberListVO),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }*/

        //page end
        return "member/quickSearchMemberList";
    }
}
