<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.delivery.no"/></label>
    <div class="col-lg-5">
        ${inventoryPurchaseOrderShipmentVO.deliveryNumber}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.delivery.date"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${inventoryPurchaseOrderShipmentVO.deliveryDate}" pattern="yyyy-MM-dd"/>
    </div>
</div>

<div class="row new-row-width">
    <table class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="label.name"/></th>
            <c:forEach items="${shopList}" var="item">
                <th>${item.name}</th>
            </c:forEach>
            <th><spring:message code="label.total.qty"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${inventoryPurchaseOrderShipmentVO.inventoryPurchaseOrderShipmentItemVOs}" var="item">
            <tr>
                <td>${item.productOption.label6}</td>
                <c:forEach items="${shopList}" var="shop">
                    <td>
                        <c:forEach items="${item.inventoryTransactionVOs}" var="transaction">
                            <c:if test="${shop eq transaction.shop}">
                                ${transaction.qty}
                            </c:if>
                        </c:forEach>
                    </td>
                </c:forEach>
                <td>${item.qty}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
