<%@ page import="com.spa.plugin.discount.DiscountAdapter" %>
<%@ page import="org.spa.model.shop.Shop" %>
<%@ page import="org.spa.service.shop.ShopService" %>
<%@ page import="org.spa.utils.SpringUtil" %>
<%@ page import="org.spa.serviceImpl.shop.ShopServiceImpl" %>
<%@ page import="org.spa.service.user.UserService" %>
<%@ page import="org.spa.serviceImpl.user.UserServiceImpl" %>
<%@ page import="org.spa.model.user.User" %>
<%@ page import="com.spa.constant.CommonConstant" %>
<%@ page import="org.spa.model.product.ProductOption" %>
<%@ page import="org.spa.service.product.ProductOptionService" %>
<%@ page import="org.spa.serviceImpl.product.ProductOptionServiceImpl" %>
<%@ page import="com.spa.plugin.discount.DiscountPlugin" %>
<%@ page import="com.spa.plugin.discount.DiscountItemAdapter" %><%--
  Created by IntelliJ IDEA.
  User: hary
  Date: 2016/6/17
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test Discount Rule</title>
</head>
<body>

<%
    ShopService shopService = SpringUtil.getBean(ShopServiceImpl.class);
    UserService userService = SpringUtil.getBean(UserServiceImpl.class);
    ProductOptionService productOptionService = SpringUtil.getBean(ProductOptionServiceImpl.class);
    Shop shop = shopService.get(1L);
    User member = userService.getUserByEmail("498482873@qq.com", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
    DiscountAdapter discountAdapter = DiscountAdapter.newInstance(shop.getId(), member.getId());
    ProductOption productOption = productOptionService.get(1L);
    //System.out.println("productOption:" + productOption.getProduct().getName());
    discountAdapter.addItem(productOption, 1, 500D);
    DiscountPlugin.process(discountAdapter);
    for(DiscountItemAdapter itemAdapter : discountAdapter.getItemAdapters()) {
        System.out.println("amount: " + itemAdapter.getAmount() + ", discountValue:" + itemAdapter.getDiscountValue());
    }
    out.print("finish");
%>
</body>
</html>
