<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:if test="${productBundle !=null}">
	<td>
		<c:forEach items="${productBundle.productBundleProductOptions}" var="pp" varStatus="pidx">
	        <c:if test="${pp.groups == 0}">
	            <label class="radio-inline">
	                   <input type="radio" name="group1" data-index="${pidx.index}"
	                    value="${pp.productOption.id}"/>
	                    ${pp.productOption.label33}
	                    <input type="hidden" name="productId_${pidx.index}" value="${pp.productOption.product.id}"/>
	            </label>
	        </c:if>
	    </c:forEach>
	</td>
	<td>
	    <c:forEach items="${productBundle.productBundleProductOptions}" var="pp1" varStatus="pidx1">
	        <c:if test="${pp1.groups == 1}">
	            <label class="radio-inline">
	                   <input type="radio" name="group2" data-index="${pidx1.index}"
	                      value="${pp1.productOption.id}"/>
	                      ${pp1.productOption.label33}
	                   <input type="hidden" name="productId_${pidx1.index}" data-product-id="${pp1.productOption.product.id}"/>
	            </label>
	        </c:if>
	    </c:forEach>
	</td>
</c:if>