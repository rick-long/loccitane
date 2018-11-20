package org.spa.daoHibenate.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.spa.dao.order.PurchaseItemDao;
import org.spa.dao.prepaid.PrepaidDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.PurchaseItem;
import org.spa.utils.DateUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.report.CustomerReportVO;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.SpendingSummaryVO;
import org.springframework.stereotype.Repository;

import com.spa.constant.CommonConstant;
import org.springframework.test.context.jdbc.Sql;

@Repository
public class PurchaseItemDaoHibernate extends BaseDaoHibernate<PurchaseItem> implements PurchaseItemDao {

    private PrepaidDao prepaidDao;

    public PrepaidDao getPrepaidDao() {
        return prepaidDao;
    }

    public void setPrepaidDao(PrepaidDao prepaidDao) {
        this.prepaidDao = prepaidDao;
    }

    @Override
    public List<SpendingSummaryVO> getSpengingSummary(final Map parameters) {
        Date startDate = (Date) parameters.get("startDate");
        Date endDate = (Date) parameters.get("endDate");
        Long companyId = (Long) parameters.get("companyId");
        Long userId = (Long) parameters.get("userId");


        StringBuffer hql = new StringBuffer();
        hql.append("select ");
        hql.append("prod.prod_type");
        hql.append(",sum(pi.amount)");
        hql.append(" from ");
        hql.append(" PURCHASE_ORDER po ");
        hql.append(", PURCHASE_ITEM pi ");
        hql.append(", PRODUCT_OPTION prod_opt ");
        hql.append(", PRODUCT prod ");
        hql.append("where po.is_active = true");
        hql.append(" and po.status = 'COMPLETED'");
        hql.append(" and po.id = pi.purchase_order_id");
        hql.append(" and pi.product_option_id = prod_opt.id");
        hql.append(" and prod_opt.product_id = prod.id");

        if (companyId != null) {
            hql.append(" and po.company_id = " + companyId);
        }
        if (startDate != null) {
            hql.append(" and po.purchase_date >= " + "'" + DateUtil.toString(DateUtil.getFirstMinuts(startDate), "yyyy-MM-dd HH:mm:ss") + "'");
        }
        if (endDate != null) {
            hql.append(" and po.purchase_date <= " + "'" + DateUtil.toString(DateUtil.getLastMinuts(endDate), "yyyy-MM-dd HH:mm:ss") + "'");
        }

        if (userId != null) {
            hql.append(" and po.user_id = " + userId);
        }
        hql.append(" group by prod.prod_type ");
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(hql.toString());

        List<SpendingSummaryVO> results = new ArrayList<SpendingSummaryVO>();

        List<Object[]> list = query.list();
        for (Object[] object : list) {
            SpendingSummaryVO vo = new SpendingSummaryVO();
            vo.setProdType((String) object[0]);
            vo.setValue((Double) object[1]);
            results.add(vo);
        }
        return results;
    }

    @Override
    public Long getPurchaseItemIdOfSalesDetailsTotalSize(final SalesSearchVO salesSearchVO) {
        Long totalSize = 0l;
        List<Long> list = getPurchaseItemIdOfSalesDetails(salesSearchVO, true, false);
        if (list != null && list.size() > 0) {
            totalSize = new Long(list.size());
        }
        return totalSize;
    }

    @Override
    public List<Long> getAllPurchaseItemIdOfSalesDetails(final SalesSearchVO salesSearchVO) {
        return getPurchaseItemIdOfSalesDetails(salesSearchVO, true, false);
    }

    @Override
    public List<Long> getPurchaseItemIdOfSalesDetails(final SalesSearchVO salesSearchVO) {
        return getPurchaseItemIdOfSalesDetails(salesSearchVO, false, false);
    }

    @Override
    public List<Long> getPurchaseOrderIdOfSalesDetails(final SalesSearchVO salesSearchVO) {
        return getPurchaseItemIdOfSalesDetails(salesSearchVO, true, true);
    }

    private List<Long> getPurchaseItemIdOfSalesDetails(final SalesSearchVO salesSearchVO, final boolean calTotalSize, final boolean getPurchaseOrderId) {
        Long companyId = salesSearchVO.getCompanyId();
        String status = salesSearchVO.getStatus();
        String username = salesSearchVO.getUsername();
        Long shopId = salesSearchVO.getShopId();
        Long paymentMethodId = salesSearchVO.getPaymentMethodId();
        Long staffId = salesSearchVO.getStaffId();
        String prodType = salesSearchVO.getProdType();
        Long categoryId = salesSearchVO.getCategoryId();
        Long[] lowestCategoriesByCategoryIds = salesSearchVO.getLowestCategoriesByCategoryIds();
        Long productId = salesSearchVO.getProductId();
        Long productOptionId = salesSearchVO.getProductOptionId();

        String startDateSql = "";
        String endDateSql = "";
        String companySql = "";
        String userObj = "";
        String usernameSql = "";

//    	String shopObj="";
        String shopSql = "";

        String paymentObj = "";
        String paymentSql = "";

        String staffObj = "";
        String staffSql = "";

        String prodTypeObj = "";
        String prodTypeSql = "";

        String cateforyObj = "";
        String categorySql = "";

        String productObj = "";
        String productSql = "";

        String productOptionObj = "";
        String productOptionSql = "";

        String prepaidSql = "";

        String toFromDate = salesSearchVO.getFromDate().replaceAll("'", "");
        String toDate = salesSearchVO.getToDate().replaceAll("'", "");
        toDate = toDate + " 23:59:59";

        if (companyId != null) {
            companySql = " and po.company_id = " + companyId;
        }
        if (StringUtils.isNoneBlank(salesSearchVO.getFromDate())) {
            startDateSql = " and po.purchase_date >= '" + toFromDate + "'";
        }
        if (StringUtils.isNoneBlank(salesSearchVO.getToDate())) {
            endDateSql = " and po.purchase_date <= '" + toDate + "'";
        }
        if (StringUtils.isNoneBlank(username)) {
            username = username.replaceAll("'", "");
            userObj = " LEFT JOIN USER u ON po.user_id=u.id";
            usernameSql = " and u.username = '" + username + "' ";
        }
        if (shopId != null) {
//    		shopObj=",SHOP s";
            shopSql = " and po.shop_id = " + shopId;
        } else {
            if (salesSearchVO.getIsSearchByJob() != null && salesSearchVO.getIsSearchByJob()) {
            } else {
                shopSql = " and po.shop_id in (:shopIds)";
            }
        }

        if (staffId != null) {
            staffObj = " LEFT JOIN STAFF_COMMISSION sc ON sc.purchase_item_id=pi.id ";
            staffSql = " and sc.staff_id = " + staffId;
        }
        if (StringUtils.isNoneBlank(prodType)) {
            if (prodType.equals(CommonConstant.CATEGORY_REF_PREPAID)) {
                prepaidSql = " and pi.prepaid_top_up_transaction_id is not null ";
            } else {
                prodTypeObj = " LEFT JOIN  PRODUCT_OPTION pro ON pro.id=pi.product_option_id "
                        + " LEFT JOIN PRODUCT p ON p.id=pro.product_id ";
                prodTypeSql = " and p.prod_type = '" + prodType + "' ";
            }
        }
        if (categoryId != null && lowestCategoriesByCategoryIds != null && lowestCategoriesByCategoryIds.length > 0) {
            cateforyObj = " LEFT JOIN CATEGORY c ON c.id=p.category_id ";
            categorySql = " and p.category_id in (:lowestCategoriesByCategoryIds)";
        }
        if (productId != null) {
            productSql = " and pro.product_id = " + productId;
        }
        if (productOptionId != null) {
            productOptionSql = " and pi.product_option_id = " + productOptionId;
        }

        if (paymentMethodId != null) {
            paymentObj = " LEFT JOIN PAYMENT pay ON  po.id=pay.purchase_order_id ";
            paymentSql = " and pay.payment_method_id = " + paymentMethodId;
        }
        String limitSql = "";
        if (!calTotalSize) {
            limitSql = " limit " + salesSearchVO.getPageSize() + " offset " + ((salesSearchVO.getPageNumber() - 1) * salesSearchVO.getPageSize());
        }

        String returnSql = "";
        if (getPurchaseOrderId) {
            returnSql = "SELECT straight_join distinct po.id FROM PURCHASE_ORDER po "
                    + " LEFT JOIN PURCHASE_ITEM pi ON pi.purchase_order_id=po.id ";
        } else {
            returnSql = "SELECT straight_join distinct pi.id FROM PURCHASE_ITEM pi "
                    + " LEFT JOIN PURCHASE_ORDER po ON pi.purchase_order_id=po.id ";
        }
        String sql = returnSql
                + userObj
                + staffObj
//    			+shopObj
                + prodTypeObj
                + cateforyObj
                + productObj
                + productOptionObj
                + paymentObj

                + " where 1=1"
                + " and po.status = '" + status + "'"
                + " and po.is_active = 1"
                //+ " and po.is_active = true " comment this, as it will uncount those finished prepaid order
                + companySql
                + startDateSql
                + endDateSql
                + usernameSql
                + staffSql
                + shopSql
                + prodTypeSql
                + categorySql
                + prepaidSql
                + productSql
                + productOptionSql
                + paymentSql
                + " order by po.id desc"
                + limitSql;
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery = sqlQuery.addScalar("id", StandardBasicTypes.LONG);
        if (categoryId != null && lowestCategoriesByCategoryIds != null && lowestCategoriesByCategoryIds.length > 0) {
            sqlQuery.setParameterList("lowestCategoriesByCategoryIds", lowestCategoriesByCategoryIds);
        }
        if (shopId == null) {
            if (salesSearchVO.getIsSearchByJob() != null && salesSearchVO.getIsSearchByJob()) {
            } else {
                sqlQuery.setParameterList("shopIds", WebThreadLocal.getHomeShopIdsByLoginStaff());
            }
        }
        System.out.println(sql);
        List<Long> list = (List<Long>) sqlQuery.list();
        return list;
    }

    @Override
    public List<PurchaseItem> getPurchaseItemsByIds(final Long[] piIds) {

        String idsp = "";
        int idx = 1;
        for (Long id : piIds) {
            idsp += id + ",";
            if (idx == piIds.length) {
                idsp += id;
            } else {
                idsp += id + ",";
            }
            idx++;
        }
        String sql = "select * from  PURCHASE_ITEM  where id in (" + idsp + ") order by id desc ";
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(PurchaseItem.class);
        List<PurchaseItem> results = (List<PurchaseItem>) query.list();
        return results;
    }

    @Override
    public List<CustomerReportVO> getTotalSalesByFormatDate(final SalesSearchVO salesSearchVO) {
        return returnBySearchConditions(salesSearchVO, true, false, false, false);
    }

//	@Override
//	public List<CustomerReportVO> getTotalSalesAndCommission(final SalesSearchVO salesSearchVO){
//		return returnBySearchConditions(salesSearchVO, false, true, false, false);
//	}

    @Override
    public List<CustomerReportVO> getCommissionAnalysisByStaff(SalesSearchVO salesSearchVO) {
        return returnBySearchConditions(salesSearchVO, false, false, true, false);
    }

    @Override
    public List<CustomerReportVO> getSalesAnalysisByCategory(SalesSearchVO salesSearchVO) {
        return returnBySearchConditions(salesSearchVO, false, false, false, true);
    }

    private List<CustomerReportVO> returnBySearchConditions(final SalesSearchVO salesSearchVO, final boolean groupByFormatDate, final boolean searchTotalSalesAndComm,
                                                            final boolean searchCommissionAnalysisByStaff, final boolean searchSalesAnalysisByCategory) {
        Long companyId = salesSearchVO.getCompanyId();
        String status = salesSearchVO.getStatus();

        String username = salesSearchVO.getUsername();
        Long shopId = salesSearchVO.getShopId();
        Long paymentMethodId = salesSearchVO.getPaymentMethodId();
        Long staffId = salesSearchVO.getStaffId();
        String prodType = salesSearchVO.getProdType();
        Long categoryId = salesSearchVO.getCategoryId();
        Long[] lowestCategoriesByCategoryIds = salesSearchVO.getLowestCategoriesByCategoryIds();

        Long productId = salesSearchVO.getProductId();
        Long productOptionId = salesSearchVO.getProductOptionId();


        String startDateSql = "";
        String endDateSql = "";
        String companySql = "";
        String userObj = "";
        String usernameSql = "";

//    	String shopObj="";
        //use po.shop_id=?
        String shopSql = "";

        String paymentObj = "";
        String paymentSql = "";

        String staffObj = "";
        String staffSql = "";

        String prodTypeObj = "";
        String prodTypeSql = "";

        String cateforyObj = "";
        String categorySql = "";

        String productObj = "";
        String productSql = "";

        String productOptionObj = "";
        String productOptionSql = "";
        String toFromDate = salesSearchVO.getFromDate().replaceAll("'", "");
        String toDate = salesSearchVO.getToDate().replaceAll("'", "");
        toDate = toDate + " 23:59:59";

        String prepaidSql = "";

        if (companyId != null) {
            companySql = " and po.company_id = " + companyId;
        }
        if (StringUtils.isNoneBlank(salesSearchVO.getFromDate())) {
            startDateSql = " and po.purchase_date >= '" + toFromDate + "'";
        }
        if (StringUtils.isNoneBlank(salesSearchVO.getToDate())) {
            endDateSql = " and po.purchase_date <= '" + toDate + "'";
        }
        if (StringUtils.isNoneBlank(username)) {
            username = username.replaceAll("'","");
            userObj = " LEFT JOIN USER u ON u.id=po.user_id";
            usernameSql = " and u.username = '" + username + "' ";
        }

        if (shopId != null) {
//    		shopObj=",SHOP s";
            shopSql = " and po.shop_id = " + shopId;
        } else {
            if (salesSearchVO.getIsSearchByJob() != null && salesSearchVO.getIsSearchByJob()) {
            } else {
                shopSql = " and po.shop_id in (:shopIds)";
            }
        }

        if (staffId != null) {
            staffObj = " LEFT JOIN STAFF_COMMISSION sc ON sc.purchase_item_id=pi.id ";
            if (StringUtils.isBlank(username)) {
                staffObj += " LEFT JOIN USER u ON u.id = sc.staff_id ";
            }
            staffSql = " and sc.staff_id = " + staffId;
        } else {
            if (searchCommissionAnalysisByStaff) {
                staffObj = " LEFT JOIN STAFF_COMMISSION sc ON sc.purchase_item_id=pi.id ";
                if (StringUtils.isNoneBlank(username)) {
                    staffObj += " and sc.staff_id= u.id ";
                } else {
                    staffObj += " LEFT JOIN USER u ON u.id = sc.staff_id ";
                }

            }
        }
        if (StringUtils.isNotBlank(prodType)) {
            if (prodType.equals(CommonConstant.CATEGORY_REF_PREPAID)) {
                if (searchSalesAnalysisByCategory) {
                    prodTypeObj = " LEFT JOIN  PREPAID_TOP_UP_TRANSACTION ptt ON ptt.id=pi.prepaid_top_up_transaction_id ";
                }
                prepaidSql = " and pi.prepaid_top_up_transaction_id is not null ";
            } else {
                prodTypeObj = " LEFT JOIN  PRODUCT_OPTION pro ON pro.id=pi.product_option_id "
                        + " LEFT JOIN PRODUCT p ON p.id=pro.product_id ";
                if (searchSalesAnalysisByCategory) {
                    cateforyObj = " LEFT JOIN CATEGORY c ON c.id=p.category_id ";
                }
                prodTypeSql = " and p.prod_type = '" + prodType + "' ";
                prepaidSql = " and pi.prepaid_top_up_transaction_id is null ";
            }
        }
        if (categoryId != null && lowestCategoriesByCategoryIds != null && lowestCategoriesByCategoryIds.length > 0) {
            if (!searchSalesAnalysisByCategory) {
                cateforyObj = " LEFT JOIN CATEGORY c ON c.id=p.category_id ";
            }
            categorySql = " and p.category_id in (:lowestCategoriesByCategoryIds)";
        }

        if (productId != null) {
            productSql = " and pro.product_id = " + productId;
        }
        if (productOptionId != null) {
            productOptionSql = " and pi.product_option_id = " + productOptionId;
        }

        if (paymentMethodId != null) {
            paymentObj = " LEFT JOIN PAYMENT pay ON  po.id=pay.purchase_order_id ";
            paymentSql = " and pay.payment_method_id = " + paymentMethodId;
        }

        String limitSql = "";

        //search conditions
        String searchSql = "";
        if (groupByFormatDate) {
            searchSql = "SELECT po.purchase_date as date,sum(pi.amount) as sumAmt FROM PURCHASE_ORDER po "
                    + " LEFT JOIN PURCHASE_ITEM pi ON po.id=pi.purchase_order_id ";
        } else if (searchCommissionAnalysisByStaff) {

            searchSql = "SELECT u.display_name,sum(sc.amount) as sumAmt,sum(sc.commission_value) as sumCommVal,sum(sc.amount * sc.target_commission_rate/100) as sumBonusCommVal,sum(sc.amount * sc.extra_commission_rate/100) as sumExtraCommission,sum(sc.amount * sc.target_extra_commission_rate/100) as sumTargetExtraCommission FROM PURCHASE_ITEM pi "
                    + " LEFT JOIN PURCHASE_ORDER po ON po.id=pi.purchase_order_id ";

        } else if (searchSalesAnalysisByCategory) {
            if (StringUtils.isNoneBlank(prodType) && prodType.equals(CommonConstant.CATEGORY_REF_PREPAID)) {
                searchSql = "SELECT ptt.prepaid_type,count(pi.id) as sumId,sum(pi.amount) as sumAmt FROM PURCHASE_ITEM pi "
                        + " LEFT JOIN PURCHASE_ORDER po ON po.id = pi.purchase_order_id ";
            } else {

                searchSql = "SELECT c.name,count(pi.id) as sumId,sum(pi.amount) as sumAmt FROM PURCHASE_ITEM pi "
                        + " LEFT JOIN PURCHASE_ORDER po ON po.id = pi.purchase_order_id  ";
            }
        }

        // order by
        String orderBySql = "";
        // group by
        String groupBySql = "";
        if (groupByFormatDate) {
            groupBySql = " group by DATE_FORMAT(po.purchase_date, '%Y-%m-%d') ";
            orderBySql = "order by po.purchase_date";
        } else if (searchCommissionAnalysisByStaff) {
            groupBySql = " group by u.id ";
            orderBySql = " order by u.username";
        } else if (searchSalesAnalysisByCategory) {
            if (StringUtils.isNoneBlank(prodType) && prodType.equals(CommonConstant.CATEGORY_REF_PREPAID)) {
                groupBySql = " group by ptt.prepaid_type ";
                orderBySql = " order by ptt.prepaid_type";
            } else {
                groupBySql = " group by c.id ";
                orderBySql = " order by c.name";
            }
        }

        String sql = searchSql
                + userObj
                + staffObj
//    			+shopObj
                + prodTypeObj
                + cateforyObj
                + productObj
                + productOptionObj
                + paymentObj

                + " where 1=1"
                + " and po.status = '" + status + "'"
                + " and po.is_active = 1"
                //+ " and po.is_active = true " comment this, as it will uncount those finished prepaid order
                + companySql
                + startDateSql
                + endDateSql
                + usernameSql
                + staffSql
                + shopSql
                + prodTypeSql
                + categorySql
                + prepaidSql
                + productSql
                + productOptionSql
                + paymentSql
                + groupBySql
                + orderBySql
                + limitSql;

        List<CustomerReportVO> list = new ArrayList<CustomerReportVO>();
//    	System.out.println("----sql------"+sql);
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);

        if (categoryId != null && lowestCategoriesByCategoryIds != null && lowestCategoriesByCategoryIds.length > 0) {
            sqlQuery.setParameterList("lowestCategoriesByCategoryIds", lowestCategoriesByCategoryIds);
        }

        if (shopId == null) {
            if (salesSearchVO.getIsSearchByJob() != null && salesSearchVO.getIsSearchByJob()) {
            } else {
                sqlQuery.setParameterList("shopIds", WebThreadLocal.getHomeShopIdsByLoginStaff());
            }
        }
        if (groupByFormatDate) {
            sqlQuery.addScalar("date", StandardBasicTypes.DATE);
            sqlQuery.addScalar("sumAmt", StandardBasicTypes.DOUBLE);
        } else if (searchCommissionAnalysisByStaff) {
            sqlQuery.addScalar("display_name", StandardBasicTypes.STRING);
            sqlQuery.addScalar("sumAmt", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("sumCommVal", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("sumBonusCommVal", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("sumExtraCommission", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("sumTargetExtraCommission", StandardBasicTypes.DOUBLE);
        } else if (searchSalesAnalysisByCategory) {
            if (StringUtils.isNoneBlank(prodType) && prodType.equals(CommonConstant.CATEGORY_REF_PREPAID)) {
                sqlQuery.addScalar("prepaid_type", StandardBasicTypes.STRING);
                sqlQuery.addScalar("sumId", StandardBasicTypes.LONG);
                sqlQuery.addScalar("sumAmt", StandardBasicTypes.DOUBLE);
            } else {
                sqlQuery.addScalar("name", StandardBasicTypes.STRING);
                sqlQuery.addScalar("sumId", StandardBasicTypes.LONG);
                sqlQuery.addScalar("sumAmt", StandardBasicTypes.DOUBLE);
            }
        }
        List<Object[]> results = (List<Object[]>) sqlQuery.list();
        if (results == null) {
            return null;
        }
        for (Object[] object : results) {
            CustomerReportVO vo = new CustomerReportVO();
            if (groupByFormatDate) {
                vo.setDate(object[0] != null ? (Date) object[0] : null);
                vo.setSumAmt(object[1] != null ? (Double) object[1] : 0d);
            } else if (searchCommissionAnalysisByStaff) {
                vo.setStaff(object[0] != null ? (String) object[0] : null);
                vo.setSumAmt(object[1] != null ? (Double) object[1] : 0d);
                vo.setSumCommiVal(object[2] != null ? (Double) object[2] : 0d);
                vo.setSumBonusCommi(object[3] != null ? (Double) object[3] : 0d);
                vo.setSumExtraCommission(object[4] != null ? (Double) object[4] : 0d);
                vo.setSumTargetExtraCommission(object[5] != null ? (Double) object[5] : 0d);
            } else if (searchSalesAnalysisByCategory) {
                vo.setCategoryName(object[0] != null ? (String) object[0] : null);
                vo.setSumQty(object[1] != null ? new Double((Long) object[1]) : 0d);
                vo.setSumAmt(object[2] != null ? (Double) object[2] : 0d);
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public PurchaseItem getPIByPrepaidId(Long prepaidId) {

        String sql = "select DISTINCT(pi.id) from PURCHASE_ITEM pi "
                + " left join PREPAID_TOP_UP_TRANSACTION ptt on pi.prepaid_top_up_transaction_id =ptt.id"
                + " left join PREPAID p on ptt.prepaid_id =p .id "
                + " where p.id = " + prepaidId;
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addScalar("id", StandardBasicTypes.LONG);
        query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Long piId = 0l;
        PurchaseItem pi = null;
        List<Long> results = (List<Long>) query.list();
        if (results != null && results.size() > 0) {
            piId = results.get(0);
            pi = get(piId);
        }
        return pi;
    }

    public Long getPurchaseItemIdOfSalesDetailsCount(final SalesSearchVO salesSearchVO, final boolean calTotalSize, final boolean getPurchaseOrderId) {
        Long companyId = salesSearchVO.getCompanyId();
        String status = salesSearchVO.getStatus();


        String username = salesSearchVO.getUsername();
        Long shopId = salesSearchVO.getShopId();
        Long paymentMethodId = salesSearchVO.getPaymentMethodId();
        Long staffId = salesSearchVO.getStaffId();
        String prodType = salesSearchVO.getProdType();
        Long categoryId = salesSearchVO.getCategoryId();
        Long[] lowestCategoriesByCategoryIds = salesSearchVO.getLowestCategoriesByCategoryIds();

        Long productId = salesSearchVO.getProductId();
        Long productOptionId = salesSearchVO.getProductOptionId();

        String startDateSql = "";
        String endDateSql = "";
        String companySql = "";
        String userObj = "";
        String usernameSql = "";

//    	String shopObj="";
        String shopSql = "";

        String paymentObj = "";
        String paymentSql = "";

        String staffObj = "";
        String staffSql = "";

        String prodTypeObj = "";
        String prodTypeSql = "";

        String cateforyObj = "";
        String categorySql = "";

        String productObj = "";
        String productSql = "";

        String productOptionObj = "";
        String productOptionSql = "";

        String prepaidSql = "";
        String toFromDate = salesSearchVO.getFromDate().replaceAll("'", "");
        String toDate = salesSearchVO.getToDate().replaceAll("'", "");
        toDate = toDate + " 23:59:59";

        if (companyId != null) {
            companySql = " and po.company_id = " + companyId;
        }
        if (StringUtils.isNoneBlank(salesSearchVO.getFromDate())) {
            startDateSql = " and po.purchase_date >= '" + toFromDate + "'";
        }
        if (StringUtils.isNoneBlank(salesSearchVO.getToDate())) {
            endDateSql = " and po.purchase_date <= '" + toDate + "'";
        }
        if (StringUtils.isNoneBlank(username)) {
            userObj = " LEFT JOIN USER u ON po.user_id=u.id";
            usernameSql = " and u.username = '" + username + "' ";
        }
        if (shopId != null) {
//    		shopObj=",SHOP s";
            shopSql = " and po.shop_id = " + shopId;
        } else {
            if (salesSearchVO.getIsSearchByJob() != null && salesSearchVO.getIsSearchByJob()) {
            } else {
                shopSql = " and po.shop_id in (:shopIds)";
            }
        }

        if (staffId != null) {
            staffObj = " LEFT JOIN STAFF_COMMISSION sc ON sc.purchase_item_id=pi.id ";
            staffSql = " and sc.staff_id = " + staffId;
        }
        if (StringUtils.isNoneBlank(prodType)) {
            if (prodType.equals(CommonConstant.CATEGORY_REF_PREPAID)) {
                prepaidSql = " and pi.prepaid_top_up_transaction_id is not null ";
            } else {
                prodTypeObj = " LEFT JOIN  PRODUCT_OPTION pro ON pro.id=pi.product_option_id "
                        + " LEFT JOIN PRODUCT p ON p.id=pro.product_id ";
                prodTypeSql = " and p.prod_type = '" + prodType + "' ";
            }
        }
        if (categoryId != null && lowestCategoriesByCategoryIds != null && lowestCategoriesByCategoryIds.length > 0) {
            cateforyObj = " LEFT JOIN CATEGORY c ON c.id=p.category_id ";
            categorySql = " and p.category_id in (:lowestCategoriesByCategoryIds)";
        }
        if (productId != null) {
            productSql = " and pro.product_id = " + productId;
        }
        if (productOptionId != null) {
            productOptionSql = " and pi.product_option_id = " + productOptionId;
        }

        if (paymentMethodId != null) {
            paymentObj = " LEFT JOIN PAYMENT pay ON  po.id=pay.purchase_order_id ";
            paymentSql = " and pay.payment_method_id = " + paymentMethodId;
        }
        String limitSql = "";
        if (!calTotalSize) {
            limitSql = " limit " + salesSearchVO.getPageSize() + " offset " + ((salesSearchVO.getPageNumber() - 1) * salesSearchVO.getPageSize());
        }

        String returnSql = "";
        if (getPurchaseOrderId) {
            returnSql = "SELECT straight_join distinct  COUNT(*) FROM PURCHASE_ORDER po "
                    + " LEFT JOIN PURCHASE_ITEM pi ON pi.purchase_order_id=po.id ";
        } else {
            returnSql = "SELECT straight_join distinct  COUNT(*) FROM PURCHASE_ITEM pi "
                    + " LEFT JOIN PURCHASE_ORDER po ON pi.purchase_order_id=po.id ";
        }
        String sql = returnSql
                + userObj
                + staffObj
//    			+shopObj
                + prodTypeObj
                + cateforyObj
                + productObj
                + productOptionObj
                + paymentObj

                + " where 1=1"
                + " and po.status = '" + status + "'"
                + " and po.is_active = 1"
                //+ " and po.is_active = true " comment this, as it will uncount those finished prepaid order
                + companySql
                + startDateSql
                + endDateSql
                + usernameSql
                + staffSql
                + shopSql
                + prodTypeSql
                + categorySql
                + prepaidSql
                + productSql
                + productOptionSql
                + paymentSql
                + " order by po.id desc"
                + limitSql;
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        System.out.println(sql);
/*
		sqlQuery = sqlQuery.addScalar("COUNT(*)", StandardBasicTypes.LONG);
*/

        if (categoryId != null && lowestCategoriesByCategoryIds != null && lowestCategoriesByCategoryIds.length > 0) {
            sqlQuery.setParameterList("lowestCategoriesByCategoryIds", lowestCategoriesByCategoryIds);
        }
        if (shopId == null) {
            if (salesSearchVO.getIsSearchByJob() != null && salesSearchVO.getIsSearchByJob()) {
            } else {
                sqlQuery.setParameterList("shopIds", WebThreadLocal.getHomeShopIdsByLoginStaff());
            }
        }
        String conunt = "0";
        if (sqlQuery.uniqueResult() != null) {
            conunt = sqlQuery.uniqueResult().toString();
        }
        return Long.valueOf(conunt);
    }
}
