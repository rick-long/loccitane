package test;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spa.model.commission.CommissionRule;
import org.spa.model.product.ProductOption;
import org.spa.service.commission.CommissionRuleService;
import org.spa.service.product.ProductOptionService;
import org.spa.utils.Results;
import org.spa.vo.product.ProductOptionListVO;
import org.spa.vo.product.ProductOptionQuickSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml",
                                "classpath:spring-hibernate.xml",
                                "classpath:spring-mvc.xml",
                                 "classpath:spring-shiro.xml"})
@WebAppConfiguration
@Transactional
public class HitCommissionRuleTest {

    @Autowired
    private CommissionRuleService commissionRuleService;

    @Autowired
    private ProductOptionService productOptionService;


    @Test
    public void test(){
        ProductOption productOption = productOptionService.get(6368l);
        CommissionRule commissionRule = commissionRuleService.hitCommissionRule(productOption,13l);
        System.out.println("commission:"+commissionRule);
    }

    @Test
    public void test1(){
       /* String code = "9";
        List<ProductOptionQuickSearchVo> objects = new ArrayList<>();
        if(StringUtils.isNotBlank(code)){
            List<ProductOption> options = productOptionService.searchProductOptionList(code);
            for(ProductOption productOption : options){
                ProductOptionQuickSearchVo quickSearchVo = new ProductOptionQuickSearchVo();
                quickSearchVo.setId(productOption.getId());
                if(StringUtils.isNotBlank(productOption.getCode())){
                    quickSearchVo.setTreatmentCode(productOption.getCode());
                }else{
                    quickSearchVo.setTreatmentCode("");
                }

                if(StringUtils.isNotBlank(productOption.getProduct().getName())){
                    quickSearchVo.setTreatmentName(productOption.getProduct().getName());
                }else{
                    quickSearchVo.setTreatmentName("");
                }
                quickSearchVo.setDuration(productOption.getDuration());
                if( StringUtils.isNotBlank(productOption.getPrice())){
                    quickSearchVo.setPrice(productOption.getPrice());
                }else{
                    quickSearchVo.setPrice("");
                }
                quickSearchVo.setProcessTime(productOption.getProcessTime());
                objects.add(quickSearchVo);
            }
        }
        Results instance = Results.getInstance();
        instance.setCode(Results.CODE_SUCCESS).addMessage("successMsg", objects);
        Object o = JSON.toJSON(instance);
        System.out.println(o);
*/
        //instance.setCode(Results.CODE_SUCCESS).addMessage("successMsg", objects);

    }

}
