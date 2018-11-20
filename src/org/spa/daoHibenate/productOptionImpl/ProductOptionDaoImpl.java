package org.spa.daoHibenate.productOptionImpl;

import org.hibernate.Query;
import org.spa.dao.productOption.ProductOptionDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.ProductOption;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class ProductOptionDaoImpl extends BaseDaoHibernate<ProductOption> implements ProductOptionDao {

    @Override
    public List<ProductOption> searchProductOptionList(String code/*, Integer pageNumber, Integer pageSize*/) {
        String sql = "select po.* from PRODUCT_OPTION_ATTRIBUTE poa " +
                " LEFT JOIN PRODUCT_OPTION_KEY pok on poa.product_option_key_id=pok.id " +
                " left join product_option po on po.id = poa.product_option_id " +
                " where pok.is_active=1 and poa.is_active=1 and po.is_active=1 and pok.reference = 'code' " +
                " and pok.company_id = 1" +
                " and poa.`value` like '%"+code+"%'"+
                " order by po.id desc ";
        List<ProductOption> options = getSession().createSQLQuery(sql).addEntity(ProductOption.class).list();
        /*String number = "select count(1) from PRODUCT_OPTION_ATTRIBUTE poa " +
                " LEFT JOIN PRODUCT_OPTION_KEY pok on poa.product_option_key_id=pok.id " +
                " left join product_option po on po.id = poa.product_option_id " +
                " where pok.reference = '%"+code+"%'" +
                " and pok.company_id = 1" +
                " and poa.`value` like '%" +code+"%'" +
                " limit ? , ? ";
        BigInteger total = (BigInteger) getSession().createSQLQuery(number).setParameter(0, (pageNumber - 1) * pageSize).setParameter(1, pageSize).uniqueResult();*/
        return options;
    }

    @Override
    public Long findProductOptionByCode(String code) {
        String sql = "select DISTINCT(poa.id) from product_option_attribute poa " +
                "left join product_option_key pok on poa.product_option_key_id =pok.id " +
                "left join product_option_key_category pokca on pokca.product_option_key_id=pok.id " +
                "where pok.is_active=1 and poa.is_active=1 and pokca.category_id=2 " +
                "and pok.reference='code' " +
                "and poa.`value` = ?";
        BigInteger bigInteger = (BigInteger) getSession().createSQLQuery(sql).setParameter(0, code).uniqueResult();
        if(bigInteger ==null){
            return null;
        }
        return bigInteger.longValue();
    }

    @Override
    public List<ProductOption> findProductOptionLikeByCode(String code) {
        String sql = "select po.* from product_option_attribute poa " +
                " left join product_option_key pok on poa.product_option_key_id =pok.id " +
                " left join product_option_key_category pokca on pokca.product_option_key_id=pok.id " +
                " left join product_option po on po.id = poa.product_option_id"+
                " where pok.is_active=1 and poa.is_active=1 and pokca.category_id=2 " +
                " and pok.reference='code' " +
                " and poa.`value` like '%"+code+ "%'";
        List<ProductOption> options = getSession().createSQLQuery(sql).addEntity(ProductOption.class).list();
        return options;
    }
}
