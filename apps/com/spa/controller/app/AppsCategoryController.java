package com.spa.controller.app;
import com.spa.controller.BaseController;
import org.joda.time.DateTime;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.utils.DateUtil;
import org.spa.utils.Results;
import org.spa.vo.app.callback.CategoryCallBackVO;
import org.spa.vo.app.callback.ProductOptionCallBackVO;
import org.spa.vo.category.CategoryVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("apps/category")
public class AppsCategoryController extends BaseController {

  /* @RequestMapping("list")
   @ResponseBody
   public Results list(@RequestBody CategoryVO categoryVO) {
       Results results = Results.getInstance();
       if(categoryVO.getId()==null){
           return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id  required");
       }
       List<Category> categoryList=categoryService.getCategoriesByParentCategoryId(categoryVO.getId(),1l);
       List<CategoryCallBackVO>categoryCallBackVOS=new ArrayList<>();
       if(categoryList!=null&&categoryList.size()>0){
           for(Category category:categoryList){
               CategoryCallBackVO categoryCallBackVO=new CategoryCallBackVO(category);
               List <CategoryCallBackVO>categoryChildList=new ArrayList<>();
               for(Category category1:category.getCategories()){
                   if(category1.isIsActive()){
                       List<Product>productList=productService.listByCategory(category1,true);
                       if(productList!=null && productList.size()>0){
                           CategoryCallBackVO categoryChildCallBackVO=new CategoryCallBackVO(category1);
                           categoryChildList.add(categoryChildCallBackVO);
                       }
                   }
               }
               categoryCallBackVO.setCategoryChildList(categoryChildList);
               categoryCallBackVOS.add(categoryCallBackVO);
           }
       }
       return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", categoryCallBackVOS);
   }*/
  @RequestMapping("list")
  @ResponseBody
  public Results list(@RequestBody CategoryVO categoryVO) {
      Results results = Results.getInstance();
      if(categoryVO.getId()==null){
          return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id  required");
      }
      List<Category> categoryList=categoryService.getCategoriesByParentCategoryId(categoryVO.getId(),1l);
      List<CategoryCallBackVO>categoryCallBackVOS=new ArrayList<>();
      if(categoryList!=null&&categoryList.size()>0){
          for(Category category:categoryList){
              CategoryCallBackVO categoryCallBackVO=new CategoryCallBackVO(category);
              categoryCallBackVOS.add(categoryCallBackVO);
          }
      }
      return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", categoryCallBackVOS);
  }
    @RequestMapping("childCategory")
    @ResponseBody
    public Results childCategory(@RequestBody CategoryVO categoryVO) {
        Results results = Results.getInstance();
        if(categoryVO.getId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id  required");
        }
        if(categoryVO.getShopId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "shop id  required");
        }
        List<Category> categoryList=categoryService.getCategoriesByParentCategoryId(categoryVO.getId(),1l);
        List <CategoryCallBackVO>categoryChildList=new ArrayList<>();
        if(categoryList!=null&&categoryList.size()>0){
            for(Category category:categoryList){
            	if(category.getCategories() !=null && category.getCategories().size()>0){
            		Iterator<Category> it = category.getCategories().iterator();
            		while(it.hasNext()){
            			Category subCategory = it.next();
            			List<Product>productList=productService.listByCategory(subCategory,true,categoryVO.getShopId());
                        if(productList!=null && productList.size()>0){
                            CategoryCallBackVO categoryChildCallBackVO=new CategoryCallBackVO(subCategory,true);
                            categoryChildList.add(categoryChildCallBackVO);
                        }
            		}
            	}
                List<Product>productList=productService.listByCategory(category,true,categoryVO.getShopId());
                if(productList!=null && productList.size()>0){
                    CategoryCallBackVO categoryChildCallBackVO=new CategoryCallBackVO(category);
                    categoryChildList.add(categoryChildCallBackVO);
                }
            }
        }
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", categoryChildList);
    }
    
    public void checkChildrenByCategory(Category category){
    	
    }
    @RequestMapping("product")
    @ResponseBody
    public Results product(@RequestBody CategoryVO categoryVO) {
        Results results = Results.getInstance();
        if(categoryVO.getId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "id  required");
        }
        if(categoryVO.getShopId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "shop id  required");
        }
        Category category=  categoryService.get(categoryVO.getId());
        List<ProductOptionCallBackVO>productOptionCallBackVOs=new ArrayList<>();
     if(category!=null){
         List<Product>productList=productService.listByCategory(category,true,categoryVO.getShopId());
         for(Product product:productList){
             for(ProductOption productOption:product.getProductOptions()){
                 ProductOptionCallBackVO productOptionCallBackVO=new ProductOptionCallBackVO(productOption,categoryVO.getShopId());
                 if(categoryVO.getStartTime()!=null){
                     Date endAppointment = new DateTime(categoryVO.getStartTime()).plusMinutes(productOption.getDuration() + productOption.getProcessTime()).toDate();
                     try {
                         productOptionCallBackVO.setEndTime(DateUtil.dateToString(endAppointment,"HH:mm"));
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
                 }
                 productOptionCallBackVOs.add(productOptionCallBackVO);
             }
         }
     }
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", productOptionCallBackVOs);
    }

}
