<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <link href='<c:url value="/resources/css/base/print.css"/>' rel="stylesheet">
    <title><spring:message code="label.inventory.print.inventory.purchase.order"/> </title>
</head>
<body>
<div class="invoice">
<form class="form-horizontal">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="inv_info" align="center">
  <tr>
    <td style="text-align:left;"><label class="col-lg-3 control-label"><spring:message code="label.company"/>:</label>
        <label class="col-lg-5">
            ${inventoryPurchaseOrder.company.name}
        </label></td>
    <td> <label class="col-lg-3 control-label"><spring:message code="label.supplier"/>:</label>
        <label class="col-lg-5">
            ${inventoryPurchaseOrder.supplier.name}
        </label></td>
    <td style="text-align:right;"> <label class="col-lg-3 control-label"><spring:message code="label.date"/>:</label>
        <label class="col-lg-5">
            <fmt:formatDate value="${inventoryPurchaseOrder.date}" pattern="yyyy-MM-dd"/>
        </label></td>
  </tr>
  <tr>
    <td style="text-align:left;"> <label class="col-lg-3 control-label"><spring:message code="label.delivery.date"/>:</label>
        <label class="col-lg-5">
            <fmt:formatDate value="${inventoryPurchaseOrder.expectedDeliveryDate}" pattern="yyyy-MM-dd"/>
        </label></td>
    <td> <label class="col-lg-3 control-label"><spring:message code="label.remarks"/>:</label>
        <label class="col-lg-5">
            ${inventoryPurchaseOrder.remarks}
        </label></td>
    <td style="text-align:right;">&nbsp;</td>
  </tr>
</table>
   <br/>   <br/>   <br/>   <br/>
    <h3><spring:message code="label.purchaseorderitemdetails"/> :</h3>
 <div style="margin-top:20px;border-top:1px solid #ccc;">
           <table class="table table-striped price_info" align="center">
            <thead>
            <tr>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.reference"/></th>
                <th><spring:message code="label.default.price"/></th>
                <th><spring:message code="label.qty"/></th>
                <th><spring:message code="label.total"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${inventoryPurchaseOrder.inventoryPurchaseItems}" var="item">
                <tr>
                    <td>${item.productOption.label6}</td>
                    <td>${item.productOption.reference}</td>
                    <td>${item.productOption.originalPrice}</td>
                    <td>${item.qty}</td>
                    <td>${item.total}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>
</div>
</body>
</html>
