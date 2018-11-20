package com.spa.controller;

import com.alibaba.fastjson.JSON;
import com.spa.constant.CommonConstant;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.model.inventory.InventoryPurchaseOrder;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.user.User;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.common.TreeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("test")
public class TestController extends BaseController {

    @RequestMapping("testManagement")
    public String testManagement() {
        return "/test/testManagement";
    }

    @RequestMapping("testDuplicateSubmit")
    public String testFrontEndFormSubmit() {
        return "/test/testDuplicateSubmit";
    }

    @RequestMapping("submitTest")
    @ResponseBody
    public AjaxForm submitTest(Long millis) {
        try {
            Thread.sleep(millis != null && millis > 0 ? millis : 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return AjaxFormHelper.error();
    }

    @RequestMapping("submitSuccess")
    @ResponseBody
    public AjaxForm submitSuccess(Long millis) {
        try {
            Thread.sleep(millis != null && millis > 0 ? millis : 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return AjaxFormHelper.success();
    }



    @RequestMapping("longReturnTest")
    @ResponseBody
    public String longReturnTest(Long millis) throws InterruptedException {
        Thread.sleep(millis != null && millis > 0 ? millis : 1000);
        //int i = 1 / 0; // 抛出错误测试
        return "OK";
    }

    @RequestMapping("testMultiMenu")
    public String testMultiMenu(Model model) {
        List<Category> categoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("categoryList", categoryList);

       List<TreeVO> treeVOList = new ArrayList<>();
        for(Category category : categoryList) {
            TreeVO treeVO = new TreeVO();
            treeVO.setId(category.getId().toString());
            treeVO.setDisplayName(category.getName());
            treeVO.setParent(null);
            addChild(treeVO, category);

            treeVOList.add(treeVO);
        }
        model.addAttribute("treeVOList", treeVOList);
        System.out.println("treeVOList:" + JSON.toJSONString(treeVOList));


        List<TreeVO> timeTreeList = new ArrayList<>();

        // 上午
        //TreeVO morningTree = new TreeVO();
        /*timeTreeList.add(addHourTree("Morning", 6, 11));
        timeTreeList.add(addHourTree("Afternoon", 12, 17));
        timeTreeList.add(addHourTree("Evening", 18, 23));*/
        model.addAttribute("timeTreeList", CommonConstant.TIME_DATA);
        model.addAttribute("districtList", CommonConstant.DISTRICT_DATA);
        System.out.println("DISTRICT_DATA:" + CommonConstant.DISTRICT_DATA);


        return "/test/testMultiMenu";
    }

    @RequestMapping("testMultiMenuV2")
    public String testMultiMenuV2(Model model) {


        // 上午
        //TreeVO morningTree = new TreeVO();
        /*timeTreeList.add(addHourTree("Morning", 6, 11));
        timeTreeList.add(addHourTree("Afternoon", 12, 17));
        timeTreeList.add(addHourTree("Evening", 18, 23));*/
        model.addAttribute("timeTreeList", CommonConstant.TIME_DATA);
        model.addAttribute("districtList", CommonConstant.DISTRICT_DATA);
        System.out.println("DISTRICT_DATA:" + CommonConstant.DISTRICT_DATA);


        return "/test/testMultiMenuV2";
    }

   /* private TreeVO addHourTree(String displayName, int startHour, int entHour) {
        TreeVO treeVO = new TreeVO();
        treeVO.setParent(null);
        treeVO.setDisplayName(displayName);
        for (int i = startHour; i <= entHour; i++) {
            TreeVO hourVO = new TreeVO();
            hourVO.setParent(treeVO);
            if (i < 10) {
                hourVO.setDisplayName("0" + i);
            } else {
                hourVO.setDisplayName(Integer.toString(i));
            }
            addMinuteTree(hourVO);
            treeVO.getChildren().add(hourVO);
        }
        return treeVO;
    }

    private void addMinuteTree(TreeVO hourTree) {
        for(int i = 0; i< 60; i+= CommonConstant.TIME_UNIT) {
            TreeVO minuteVO = new TreeVO();
            if(i < 10) {
                minuteVO.setDisplayName(hourTree.getDisplayName() + ":0" + i);
            } else {
                minuteVO.setDisplayName(hourTree.getDisplayName() + ":" + i);
            }
            minuteVO.setId(minuteVO.getDisplayName());
            hourTree.getChildren().add(minuteVO);
        }
    }
*/
    private void addChild(TreeVO parentVO, Category category) {
        if (category.getCategories().size() == 0) {

            for(Product product : category.getProducts()) {
                TreeVO treeVO = new TreeVO();
                treeVO.setParent(parentVO);
                parentVO.getChildren().add(treeVO);
                treeVO.setId(product.getId().toString());
                treeVO.setDisplayName(product.getName());
                for(ProductOption productOption : product.getProductOptions()) {
                    System.out.println("productOption:" + productOption.getLabel3());
                    TreeVO productOptionVO = new TreeVO();
                    productOptionVO.setParent(treeVO);
                    treeVO.getChildren().add(productOptionVO);
                    productOptionVO.setId(productOption.getId().toString());
                    productOptionVO.setDisplayName(productOption.getLabel3());
                }
            }
            return;
        }

        for (Category child : category.getCategories()) {
            System.out.println("category:" + child.getName());
            TreeVO treeVO = new TreeVO();
            treeVO.setParent(parentVO);
            parentVO.getChildren().add(treeVO);
            treeVO.setId(child.getId().toString());
            treeVO.setDisplayName(child.getName());
            addChild(treeVO, child);
        }
    }
    
    @RequestMapping("testPdf")
    public String testPdf() {
        return "/test/testPdf";
    }
    
    @RequestMapping("helloWorld1")
    public String helloWorld(Long id, Model model) {
        System.out.println("orderID2:" + id);
        InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(id);
        model.addAttribute("inventoryPurchaseOrder", inventoryPurchaseOrder);
        return "/test/testHelloWorld1";
    }
    
    @RequestMapping("helloWorld2")
    public String helloWorld2(String id, Model model) {
        System.out.println("orderID:" + id);
        InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(Long.parseLong(id));
        model.addAttribute("inventoryPurchaseOrder", inventoryPurchaseOrder);
        return "test/testHelloWorld2";
    }

    @RequestMapping("patchMemberLastUpdatedDate")
    public String patchMemberLastUpdatedDate() {
    	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("enabled", Boolean.TRUE));
		
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.isNull("lastUpdated"));

		detachedCriteria.add(disjunction);
		detachedCriteria.addOrder(Order.desc("username"));
		
		List<User> users = userService.list(detachedCriteria);
		
		if(users !=null && users.size()>0){
			System.out.println("users size----"+users.size());
			
			for(User user : users){
				user.setLastUpdated(user.getCreated());
				user.setLastUpdatedBy(user.getCreatedBy());
				userService.saveOrUpdate(user);
			}
		}
        return "/test/testManagement";
    }
}
