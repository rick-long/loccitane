<%@page import="org.spa.model.product.ProductOption"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<table class="table table-striped">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.reference"/></th>
        <th><spring:message code="label.category"/></th>
        <th><spring:message code="label.product.option"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <%
    Long shopId=(Long)request.getAttribute("shopId");
    %>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td class="hide">
                <input class="time-picker form-control" data-product-option-id="${item.id}" readonly/>
            </td>
            <td>${item.product.name}</td>
            <td>${item.product.reference }</td>
            <td>${item.product.category.category.name}/${item.product.category.name}</td>
            <td>
	            Duration:${item.duration}</br>
	            	<%
	            		ProductOption po=(ProductOption)pageContext.getAttribute("item");
	            		Double finalPrice=po.getFinalPrice(shopId);
	            	%>
	            Price:<%=finalPrice %>
            </td>
            <td>
                <button class="btn btn-primary selectPOBtn" data-product-option-id="${item.id}"><spring:message code="label.select"/></button>
                <button class="btn btn-warning removePOBtn hide" data-product-option-id="${item.id}"><spring:message code="label.remove"/></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/po/quickSearchForBookList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->