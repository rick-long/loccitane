<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<form method="post" class="form-horizontal">
    <!--
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.supplier"/></label>
        <div class="col-lg-5">
            ${inventoryPurchaseOrder.supplier.name}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.date"/></label>
        <div class="col-lg-5">
            <fmt:formatDate value="${inventoryPurchaseOrder.date}" pattern="yyyy-MM-dd"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.delivery.date"/></label>
        <div class="col-lg-5">
            <fmt:formatDate value="${inventoryPurchaseOrder.expectedDeliveryDate}" pattern="yyyy-MM-dd"/>
        </div>
    </div>
    <%--<div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.delivery.no"/></label>
        <div class="col-lg-5">
            ${inventoryPurchaseOrder.deliveryNoteNumber}
        </div>
    </div>--%>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.remarks"/></label>
        <div class="col-lg-5">
            ${inventoryPurchaseOrder.remarks}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.status"/></label>
        <div class="col-lg-5">
            ${inventoryPurchaseOrder.status}
        </div>
    </div>
    -->
    <label><spring:message code="label.purchaseorderitemdetails"/> :</label>
    <div style="font-size: 12px;margin-bottom: 20px">
        <table class="table table-striped">
            <thead>
            <tr>
                <th class="col-lg-5"><spring:message code="label.name"/></th>
                <th class="col-lg-3"><spring:message code="label.reference"/></th>
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
    <label><spring:message code="label.shipmentdetails"/> :</label>
    <c:if test="${not empty inventoryPurchaseOrder.inventoryPurchaseOrderShipments}">
        <c:forEach items="${inventoryPurchaseOrder.inventoryPurchaseOrderShipments}" var="shipment">
        <div style="font-size: 12px;margin-bottom: 20px">
                <label>${shipment.deliveryNumber} -
                    <fmt:formatDate value="${shipment.deliveryDate}" pattern="yyyy-MM-dd"/></label>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th class="col-lg-5"><spring:message code="label.name"/></th>
                        <c:forEach items="${shopList}" var="shop">
                            <th class="col-lg-5">${shop.name}</th>
                        </c:forEach>
                        <th><spring:message code="label.total.qty"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${shipment.inventoryPurchaseOrderShipmentItems}" var="shipmentItem">
                        <tr>
                            <td>${shipmentItem.productOption.label2}</td>
                            <c:forEach items="${shopList}" var="shop">
                                <td>
                                    <c:forEach items="${shipmentItem.inventoryTransactions}" var="transaction">
                                        <c:if test="${shop eq transaction.shop && transaction.inventory.productOption eq shipmentItem.productOption}">
                                            ${transaction.qty}
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </c:forEach>
                            <td>${shipmentItem.qty}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:forEach>
    </c:if>
</form>

