<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
	

	<table class="table table-striped table-hover">
		<thead>
			<tr data-id="${item.id}">
				<th class="col-lg-1"><spring:message code="label.date"/></th>
				<th class="col-lg-2"><spring:message code="label.orderid"/> </th>
				<th class="col-lg-2"><spring:message code="label.shop"/> </th>
				<th class="col-lg-1"><spring:message code="label.sales.clientname"/> </th>
				<th class="col-lg-1"><spring:message code="label.total.revenue"/> </th>
				<th class="col-lg-1"><spring:message code="label.discount"/> </th>
				<th class="col-lg-2"><spring:message code="label.payment"/> </th>
				<th class="col-lg-1"><spring:message code="label.revenue.received"/></th>
				<th class="col-lg-2"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item">
				<tr>
				<td><fmt:formatDate value="${item.purchaseDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.reference}</td>
				<td>${item.shop.name }</td>
				<td>${item.user.fullName}</td>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.totalEffectivevalue}" /></td>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.totalDiscount}" /></td>
				<td>
					<c:if test="${payments !=null}">
						<c:forEach items="${payments}" var="payment" varStatus="px">
							${payment.paymentMethod.name}: <spring:message code="label.currency.default"/>
								<fmt:formatNumber type="number" pattern="#,#00.0#" value="${payment.amount}"/>
							<c:if test="${!px.last }"> / </c:if>
						</c:forEach>
					</c:if>
				</td>
				<td><spring:message code="label.currency.default"/><font color="#ea6e6e"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.totalAmount}" /></font></td>
				<td>
					<a href="javascript:;" onclick="printInvoice(${item.id});">
						<i class="glyphicon glyphicon-print"></i>
					</a>
					<a href='<c:url value="/sales/showItemDetails?purchaseOrderId=${item.id}"/>' title='<spring:message code="label.show.order.details"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
					   data-title='<spring:message code="label.show.order.details"/>'>
						<i class="glyphicon glyphicon-th-list"></i>
					</a>
					<c:if test="${item.canBeRemoved }">
						<a data-permission="sales:delete" class="dialog dialog-confirm" href='<c:url value="/sales/delete?id=${item.id}"/>'
						   data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>'
						   data-message='<spring:message code="label.sure.to.deldete"/>'>
							<i class="glyphicon glyphicon-trash"></i>
						</a>
					</c:if>
				</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
<table class="table" style="border-top:1px solid #ccc;">
	<tr>
		<td align="right" colspan="6"><font color="#ff0000"><spring:message code="label.total.commission"/>: <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalCommission}" /></font></td>
		<td align="right" colspan="2"><font color="#ff0000"><strong><spring:message code="label.gross.revenue"/>: <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${grossRevenue}" /></strong></font></td>
	</tr>
	<tr>
		<td align="right" colspan="7">
			<c:forEach items="${paymentAmount}" var="p" varStatus="pidx">
				<font color="#888888">${p.key}: <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${p.value}"/></font>
				<c:if test="${!pidx.last }">/</c:if>
			</c:forEach>
		</td>
	</tr>
</table>
<!-- page start  -->
<c:set var="pageUrl"
	value="${pageContext.request.contextPath}/sales/listOrder" scope="request" />
<c:import url="/WEB-INF/jsp/common/pagination.jsp" />
<!-- page end  -->

<script type="text/javascript">

   function printInvoice(id){
        BootstrapDialog.confirm({
            title: 'Confirmation',
            message: 'Do you want to print invoice? ',
            callback: function (status) {
                if (status) {
                    // 發起動作請求
                  window.location.href ='<c:url value="/sales/print?id="/>'+id;
                }
            }
        });
   };
</script>