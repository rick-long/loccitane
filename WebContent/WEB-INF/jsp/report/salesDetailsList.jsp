<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<h3 class="text-h3"><spring:message code="label.summary.details"/></h3> 
<table class="table table-striped">
	<thead>
		<tr>
			<th><spring:message code="label.prod.type"/> </th>
			<th><spring:message code="label.category"/> </th>
			<th><spring:message code="label.unit"/> </th>
			<th><spring:message code="label.amount"/> </th>
   	    	<c:if test="${showCommission }">
   	    		<th class="col-lg-3"><spring:message code="left.navbar.commission"/> </th>
   	    	</c:if>
   	    </tr>
   	</thead>
   	<tbody>	
	<c:if test="${summaryList !=null }">
		<c:set var="totalUnit" value="0"/>
		<c:set var="sumAmount" value="0"/>
		<c:set var="totalCommission" value="0"/>
   		<c:forEach items="${summaryList}" var="summaryObject">
   
			<tr>
				<td>
					<c:set var="ptype">
               		 	${fn:replace(summaryObject.prodType, "-", "")}
               		</c:set>
					<spring:message code="label.${ptype}" />
				</td>
				<td>${summaryObject.categoryName }</td>
				<td>
					<fmt:formatNumber type="number" pattern="#,#00.0#" value="${summaryObject.unit}"/>
					<c:set var="totalUnit" value="${totalUnit + summaryObject.unit }"/>
				</td>
				<td>
					<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${summaryObject.amount}"/>
					<c:set var="sumAmount" value="${sumAmount + summaryObject.amount }"/>
				</td>
				<c:if test="${showCommission }">
					<td class="col-lg-3"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${summaryObject.commission}"/>
    	    		<c:set var="totalCommission" value="${totalCommission + summaryObject.commission }"/>
    	    	</td>
    	    	</c:if>
			</tr>
		</c:forEach>
	</c:if>
	<tr>
		<td></td>
		<td></td>
		<td><b>${totalUnit }</b></td>
		<td><b><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sumAmount}"/></b></td>
		<c:if test="${showCommission }">
			<td><b><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalCommission}"/></b></td>
		</c:if>
	</tr>
	</tbody>
</table>
<br/><br/>
<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th><spring:message code="label.orderid"/> </th>
			<th><spring:message code="label.shop"/> </th>
			<th><spring:message code="label.purchase.date"/> </th>
			<th><spring:message code="label.sales.clientname"/> </th>
			<%--<th class="col-lg-2"><spring:message code="label.sales.ishotelguest"/> </th>--%>
			<th><spring:message code="label.username"/> </th>
			<th><spring:message code="label.email"/> </th>
			<th><spring:message code="label.phone"/> </th>
			<th><spring:message code="left.navbar.product"/> </th>
			<th><spring:message code="label.sales.therapistcommi"/> </th>
			<!-- <th class="col-lg-2">Commi Rate(%)</th> -->
			<th><spring:message code="label.therapist.size"/> </th>
			<th><spring:message code="label.qty"/> </th>
			<th><spring:message code="label.item.amount"/> </th>
			<th><spring:message code="label.effective.val"/> </th>
			<th><spring:message code="label.discount.extra.discount"/> </th>
			<th><spring:message code="label.payment"/> </th>
			<th><spring:message code="label.full.net.price"/> </th>
			<th><spring:message code="label.cost.of.product"/> </th>
			<th><spring:message code="label.package"/> </th>
			<th><spring:message code="label.voucher"/> </th>
		</tr>
	</thead>
	<tbody>
	
	<c:if test="${page !=null }">
		<c:forEach items="${page.list}" var="item">
		<tr>
			<td>${item.purchaseOrder.reference} / ${item.id }</td>
			<td>${item.purchaseOrder.shop.name}</td>
			<td><fmt:formatDate value="${item.purchaseOrder.purchaseDate}" pattern="yyyy-MM-dd"/></td>
			<td>${item.purchaseOrder.user.fullName}</td>
			<%--<td>${item.purchaseOrder.hotelGuest}</td>--%>
			<td>${item.purchaseOrder.user.currentLoyaltyLevel.name}</td>
			<td>${item.purchaseOrder.user.email}</td>
			<td>
				${item.purchaseOrder.user.mobilePhone}
            </td>
			<td>
				<c:choose>
					<c:when test="${item.buyPrepaidTopUpTransaction !=null}">
						<spring:message code="label.report.purchase"/>
						<c:set var="prepaidType">
	                		 ${fn:replace(item.buyPrepaidTopUpTransaction.prepaidType, "_", "")}
	                	</c:set>
	                	<spring:message code="label.prepaid.type.${prepaidType}"/>
					</c:when>
					<c:otherwise>
						${item.productOption.label3}
					</c:otherwise>
				</c:choose>
			</td>
			<td>${item.therapistAndCommission}</td>
			<%-- <td>${item.therapistAndCommissionRate}</td> --%>
			<td>${item.staffCommissions.size()}</td>
			<td>${item.qty}</td>
			<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.amount}"/></td>
			<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.effectiveValue}"/></td>
			<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.discountValue}"/>(<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.extraDiscountValue}"/>)</td>
			<td>${item.purchaseOrder.paymentMethodsAndAmount}</td>
			<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.productOption.originalPrice}"/></td>
			<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.productOption.costOfProduct}"/></td>
			<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.packagePaid}"/></td>
			<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.voucherPaid}"/></td>
		</c:forEach>
	</c:if>
	</tbody>
</table>
<table class="table" style="border-top:1px solid #ccc;">
	<tr>
		<td align="right" colspan="6"><font color="#ff0000"><spring:message code="label.total.commission"/>: <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalCommission}" /></font></td>
		<td align="right" colspan="2"><font color="#ff0000"><strong><spring:message code="label.total.revenue"/>: <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalRevenue}" /></strong></font></td>
		<td align="right" colspan="2"><font color="#ff0000"><strong><spring:message code="label.gross.revenue"/>: <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${grossRevenue}" /></strong></font></td>
	</tr>
	<c:if test="${!empty paymentAmount}">
		<tr>
			<td align="right" colspan="7">
				<c:forEach items="${paymentAmount}" var="p" varStatus="pidx">
					<font color="#888888">${p.key}: <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${p.value}"/></font>
					<c:if test="${!pidx.last }">/</c:if>
				</c:forEach>
			</td>
		</tr>
	</c:if>
</table>
<!-- page start  -->
<c:set var="pageUrl"
	value="${pageContext.request.contextPath}/report/listSalesDetails" scope="request" />
<c:import url="/WEB-INF/jsp/common/pagination.jsp" />
<!-- page end  -->