package com.spa.controller.common;

import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.product.ProductOption;
import org.spa.model.product.ProductOptionKey;
import org.spa.vo.common.CheckProductOptionKeyReq;
import org.spa.vo.common.SearchBarCodeReq;
import org.spa.vo.common.SearchBarCodeVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by wz832 on 2018/6/14.
 */
@Controller
@RequestMapping("barcode")
public class BarCodeController extends BaseController {

    @RequestMapping("searchByBarCode")
    @ResponseBody
    public SearchBarCodeVO searchBarCode(@Valid SearchBarCodeReq searchBarCodeReq) {
        SearchBarCodeVO searchBarCodeVO = new SearchBarCodeVO();
        if (searchBarCodeReq.getBarCode() == null) {
            searchBarCodeVO.setFlag(false);
            return searchBarCodeVO;
        }

        //barCode不为空的时候根据barCode进行查询
        ProductOption po = productOptionService.get("barcode", searchBarCodeReq.getBarCode());
        if (po == null || StringUtils.isEmpty(po.getBarcode())) {
            searchBarCodeVO.setFlag(false);
            return searchBarCodeVO;
        }

        searchBarCodeVO.setProductOptionId(po.getId());
        searchBarCodeVO.setDisplayName(po.getLabel33());
        return searchBarCodeVO;
    }

    @RequestMapping("getBarCode")
    @ResponseBody
    public SearchBarCodeVO getBarCode(@Valid SearchBarCodeReq searchBarCodeReq) {
        SearchBarCodeVO searchBarCodeVO = new SearchBarCodeVO();
        if (searchBarCodeReq.getProductOptionId() == null) {
            searchBarCodeVO.setFlag(false);
            return searchBarCodeVO;
        }

        //ProductOptionId不为空的时候根据ProductOptionId进行查询
        ProductOption po = productOptionService.get(searchBarCodeReq.getProductOptionId());
        if (po == null || StringUtils.isEmpty(po.getBarcode())) {
            searchBarCodeVO.setFlag(false);
            return searchBarCodeVO;
        }
        searchBarCodeVO.setBarCode(po.getBarcode());
        return searchBarCodeVO;
    }



//    @RequestMapping("generateBarCode")
//    @ResponseBody
//    public GenerateBarCodeVO generateBarCode() {
//        int num = (int)((Math.random() * 9 + 1) * 100000000);
//        GenerateBarCodeVO gb = new GenerateBarCodeVO();
//        gb.setBarcode(String.valueOf(num));
//
//
//        String fieldName = "barcode";
//        String tableName = "Product_Option";
//        Integer number = 0001;
//
//        String nums = commonService.getIDBySql(fieldName,tableName, number);
//        int a = Integer.valueOf(nums) + 1;
//        System.out.println(a);
//
//        //得到一个NumberFormat的实例
//        NumberFormat nf = NumberFormat.getInstance();
//        //设置是否使用分组
//        nf.setGroupingUsed(false);
//        //设置最大整数位数
//        nf.setMaximumIntegerDigits(4);
//        //设置最小整数位数
//        nf.setMinimumIntegerDigits(4);
//        //输出测试语句
//        System.out.println(nf.format(a));
//
//        return gb;
//    }

    @RequestMapping("checkProductOptionKey")
    @ResponseBody
    public boolean checkProductOptionKey(@Valid CheckProductOptionKeyReq checkProductOptionKeyReq) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductOptionKey.class);

        if (StringUtils.isNotBlank(checkProductOptionKeyReq.getReference())) {
            detachedCriteria.add(Restrictions.eq("reference", checkProductOptionKeyReq.getReference().toUpperCase()));
        }

        if (StringUtils.isNotBlank(checkProductOptionKeyReq.getIsActive())) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(checkProductOptionKeyReq.getIsActive())));
        }

        if(StringUtils.isNotBlank(checkProductOptionKeyReq.getCategoryName())){
            detachedCriteria.createAlias("categories", "pokeycategory");
            detachedCriteria.add(Restrictions.eq("pokeycategory.name", checkProductOptionKeyReq.getCategoryName()));
        }

        List<ProductOptionKey> poAttrKeyPage = productOptionKeyService.list(detachedCriteria);
        if (poAttrKeyPage.size() == 0) {
            return false;
        }
        return true;
    }


}
