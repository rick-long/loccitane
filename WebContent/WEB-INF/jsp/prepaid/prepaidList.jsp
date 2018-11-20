<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th><spring:message code="label.prepaid.type" /></th>
			<th><spring:message code="label.reference" /></th>
			<th><spring:message code="label.name" /></th>
			<th><spring:message code="label.treatment"/></th>
			<th><spring:message code="label.prepaid.bought.shop"/></th>
			<th><spring:message code="label.client" /></th>
			<th><spring:message code="label.prepaidValue"/></th>
			<th><spring:message code="label.face.value"/></th>
			<th><spring:message code="label.prepaid.init.value" /></th>
			<th><spring:message code="label.prepaid.remain.value" /></th>
			<th><spring:message code="label.purchase.date" /></th>
			<th><spring:message code="label.remarks" /></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	<c:if test="${page !=null }">
		<c:forEach items="${page.list}" var="item">
			<tr data-id="${item.id}" class='<c:if test="${item.isActive == 'false'}">deleted_table</c:if>'>
				<td><c:set var="prepaidType">
                		 ${fn:replace(item.prepaidType, "_", "")}
                	</c:set> <spring:message code="label.prepaid.type.${prepaidType}" />
				</td>
				<td>${item.reference}</td>
				<td>${item.name}</td>
				<td>
					<c:if test="${item.prepaidType == 'TREATMENT_PACKAGE' || item.prepaidType == 'TREATMENT_VOUCHER' }">
						${item.firstPrepaidTopUpTransaction.productOption.label3}
					</c:if>
				</td>
				<td>${item.shop.name }</td>
				<td>${item.user.firstName} ${item.user.lastName}</td>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.prepaidValue}"/></td>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.faceValue}"/></td>
				<c:choose>
					<c:when test="${item.prepaidType == 'TREATMENT_PACKAGE' || item.prepaidType == 'TREATMENT_VOUCHER'}">
						<td><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.initValue}"/></td>
						<td><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.remainingValueByAvailableTransaction}"/></td>
					</c:when>
					<c:otherwise>
						<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.initValue}"/></td>
						<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.remainingValueByAvailableTransaction}"/></td>
					</c:otherwise>
				</c:choose>

				<td><fmt:formatDate value="${item.firstPrepaidTopUpTransaction.topUpDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.remarks}</td>
				<td>
					<c:if test='${item.isTopUpPrepaid}'>
						<a href='<c:url value="/prepaid/toTopUp?id=${item.id}"/>' title='<spring:message code="label.button.top.up"/>' data-permission="prepaid:toTopUp" class="btn btn-primary dialog btn-edit" data-draggable="true"
							data-title='<spring:message code="label.prepaid.topup.management"/>'>
							 <i class="glyphicon glyphicon-circle-arrow-up"></i>
						</a>
					</c:if>
					<a href='<c:url value="/prepaid/showTopUpTransaction?id=${item.id}"/>' title='<spring:message code="label.button.show.transactions"/>' data-permission="prepaid:showTopUpTransaction" class="btn btn-primary dialog btn-edit" data-draggable="true"
						data-title='<spring:message code="label.prepaid.top.up.transaction.management"/>' data-width="1100">
						<i class="glyphicon glyphicon-list-alt"></i>
					</a>
					<c:if test='${item.isEditPepaid && !item.isRedeem}'>
						<a href='<c:url value="/prepaid/toEdit?id=${item.id}"/>' title='<spring:message code="label.button.edit"/>' data-permission="prepaid:toEdit" class="btn btn-primary dialog btn-edit" data-draggable="true"
							data-title='<spring:message code="label.prepaid.edit.management"/>'>
							<i class="glyphicon glyphicon-edit"></i>
						</a>
					</c:if>
					<c:if test='${(item.prepaidType == "CASH_VOUCHER"
									||  item.prepaidType == "TREATMENT_VOUCHER" )}'>
						<a href="javascript:;" onclick="printVoucher(${item.id});" title='<spring:message code="label.button.print"/>'>
	                        <i class="glyphicon glyphicon-print"></i>
	                    </a>
						<%-- <a href='<c:url value="/prepaid/printvoucher?prepaidId=${item.id}"/>' title="Print" class="btn btn-primary btn-edit"><i class="glyphicon glyphicon-print"></i></a> --%>
					</c:if>

					<c:if test='${!item.isUsedPrepaid && !item.isRedeem}'>
	                    <a href='<c:url value="/prepaid/delete?prepaidId=${item.id}"/>' title='<spring:message code="label.button.remove"/>' data-permission="prepaid:toEdit" class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
	                       <i class="glyphicon glyphicon-trash"></i>
	                    </a>
					</c:if>
					<a href='<c:url value="/prepaid/usedHistory?prepaidId=${item.id}"/>' title='<spring:message code="label.button.used.history"/>'title="Used History" class="btn btn-primary dialog btn-edit" data-draggable="true"
							data-title='<spring:message code="label.prepaid.used.history"/>' data-width="880">
						H
					</a>
				</td>
			</tr>
		</c:forEach>
	</c:if>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl"
	value="${pageContext.request.contextPath}/prepaid/list" scope="request" />
<c:import url="/WEB-INF/jsp/common/pagination.jsp" />
<!-- page end  -->

<script type="text/javascript">

   function printVoucher(id){
        BootstrapDialog.confirm({
            title: '<spring:message code="label.prepaid.confirmation"/> ',
            message: '<spring:message code="label.prepaid.print.voucher"/> ',
            callback: function (status) {
                if (status) {
                    // 發起動作請求
                  window.location.href ='<c:url value="/prepaid/printvoucher?prepaidId="/>'+id;
                }
            }
        });
   };
</script>