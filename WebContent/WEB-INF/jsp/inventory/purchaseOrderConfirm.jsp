<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="form-group">
    <label class="col-lg-2 control-label"><spring:message code="label.supplier"/></label>
    <div class="col-lg-5">
        ${supplier.name}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-2 control-label"><spring:message code="label.purchase.date"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${inventoryPurchaseOrderVO.date}" pattern="yyyy-MM-dd"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-2 control-label"><spring:message code="label.delivery.date"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${inventoryPurchaseOrderVO.expectedDeliveryDate}" pattern="yyyy-MM-dd"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-2 control-label"><spring:message code="label.shop"/></label>
    <div class="col-lg-5">
        <c:forEach items="${shopList}" var="item" varStatus="status">
            <c:if test="${status.index gt 0}">, </c:if>${item.name}
        </c:forEach>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-2 control-label"><spring:message code="label.remarks"/></label>
    <div class="col-lg-5">
        ${inventoryPurchaseOrderVO.remarks}
    </div>
</div>

<div class="row new-row-width">
    <h4><spring:message code="label.inventory.selected.product.option"/> </h4>
    <table class="table table-striped">
        <thead>
        <tr>
            <th class="col-lg-5"><spring:message code="label.name"/></th>
            <th class="col-lg-2"><spring:message code="label.reference"/></th>
            <th class="col-lg-2"><spring:message code="label.default.price"/></th>
            <th class="col-lg-1"><spring:message code="label.qty"/></th>
            <th class="col-lg-2"><spring:message code="label.total"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${inventoryPurchaseOrderVO.inventoryPurchaseOrderItemVOs}" var="item">
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
