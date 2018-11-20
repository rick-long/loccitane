<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th><spring:message code="label.product" /></th>
			<th><spring:message code="label.qty" /></th>
			<th><spring:message code="label.amount" /></th>
			<th><spring:message code="label.discount" /></th>
			<th><spring:message code="label.topup.therapists" /></th>
			<th><spring:message code="label.used.prepaid" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${itemList}" var="item" varStatus="idx">
			<tr data-id="${item.id}">
				<td>
					<c:choose>
						<c:when test="${item.buyPrepaidTopUpTransaction !=null}">
							Buy 
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
				<td>${item.qty }</td>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.amount}"/></td>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.discountValue}" /></td>
				<td>${item.therapistAndCommission}</td>
				<td>
					<c:choose>
						<c:when test="${item.firstPaymentWhenRedeemPrepaid !=null}">
							${item.firstPaymentWhenRedeemPrepaid.redeemPrepaidTopUpTransaction.prepaid.reference} 
							<%-- </br>
							${item.firstPaymentWhenRedeemPrepaid.redeemPrepaidTopUpTransaction.topUpReference} --%>
						</c:when>
						<c:otherwise>

						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript">

 function deleteTransaction(id){
	 $.ajax({
           url: '<c:url value="/prepaid/deleteTopUpTransaction?prepaidTopUpTransactionId="/>'+id,
           type: "POST",
           dataType: "json",
           success: function(response) {
        	   BootstrapDialog.alert(response.message);
        	   Dialog.get().close();
           }
        });
 }
</script>
