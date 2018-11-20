<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<p color="#888888"><spring:message code="label.prepaid.name" />: ${prepaid.name}</p>
<p color="#888888"><spring:message code="label.prepaid.reference" />: ${prepaid.reference}</p>
<p color="#888888"><spring:message code="label.client" /> : ${prepaid.user.fullName}</p>

<table class="table table-striped table-hover sx-td">
	<thead>
		<tr>
			<th><spring:message code="label.topup.reference" /></th>
			<th class="col-lg-1"><spring:message code="label.topup.shop" /></th>
			<c:if test="${prepaid.prepaidType == 'TREATMENT_PACKAGE' || prepaid.prepaidType == 'TREATMENT_VOUCHER'}">
				<th><spring:message code="label.treatment"/></th>
			</c:if>
			
			<th><spring:message code="label.prepaid.top.up.value" /></th>
			<th><spring:message code="label.face.value" /></th>
			<th class="col-lg-1">
				<spring:message code="label.prepaid.init.units" />
				</br>(<font color="#ff8888">
				<spring:message code="label.prepaid.remain" /></font>)
			</th>
			<%-- <th class=""><spring:message code="label.extra.discount" /></th> --%>
			<th class="col-lg-1"><spring:message code="label.topup.date" /></th>
			<th class="col-lg-1"><spring:message code="label.topup.expirydate" /></th>
			<th class="col-lg-1"><spring:message code="label.topup.commission.rate" /></th>
			<th class="col-lg-2"><spring:message code="label.topup.therapists" /></th>
			<th class="col-lg-2"><spring:message code="label.topup.payment.methods" /></th>
			<%--<th><spring:message code="label.isactive" /></th>--%>
			<th><spring:message code="label.remarks" /></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pptList}" var="item" varStatus="idx">
			
			<c:set var="trCss">
				<c:if test="${!item.isActive }">
					no-change
				</c:if>
			</c:set>
			<tr data-id="${item.id}" class='${trCss} <c:if test="${item.isActive == 'false'}">deleted_table</c:if>'>
				<td>${item.topUpReference}</td>
				<td>${item.shop.name }</td>
				<c:if test="${prepaid.prepaidType == 'TREATMENT_PACKAGE' || prepaid.prepaidType == 'TREATMENT_VOUCHER' }">
					<td>${item.productOption.label3 }</td>
				</c:if>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.topUpValue}"/></td>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.faceValue}"/></td>
				<c:choose>
					<c:when test="${item.prepaidType == 'TREATMENT_PACKAGE' || item.prepaidType == 'TREATMENT_VOUCHER'}">
						<td><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.topUpInitValue}"/></br>(<font color="#ff8888">${item.remainValue}</font>)</td>
					</c:when>
					<c:otherwise>
						<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.topUpInitValue}"/></br>(<font color="#ff8888"><spring:message code="label.currency.default"/>${item.remainValue}</font>)</td>
					</c:otherwise>
				</c:choose>
				<%-- <td><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.extraDiscount}"/></td> --%>
				<td><fmt:formatDate value="${item.topUpDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.expiryDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.therapistAndCommissionRate}</td>
				<td>${item.therapistsAndCommissions}</td>
				<td>${item.paymentMethodsAndAmountWhenBuyPrepaid}</td>
				<%--<td><spring:message code="label.${item.isActive}" /></td>--%>
				<td>${item.remarks}</td>
				<td>
						<c:if test="${! item.isUsedTopUpTransaction && !item.isRoot}">
							<a href="javascript:;" onclick="deleteTransaction(${item.id})" data-permission="prepaid:toEdit" class="btn btn-primary btn-edit">
								<i class="glyphicon glyphicon-trash"></i>
							</a>
						</c:if>
						<c:if test="${! item.isUsedTopUpTransaction && !item.isRoot}">
							<a href='<c:url value="/prepaid/toEditTopUpTransaction?ptId=${item.id}&id=${item.prepaid.id }"/>' data-permission="prepaid:toEdit" class="btn btn-primary dialog btn-edit" data-draggable="true"
								data-title='Edit prepaid top up transaction' data-width="880">
								<i class="glyphicon glyphicon-edit"></i>
							</a>
						</c:if>
						<c:if test="${item.isActive && item.remainValue >0 && (item.prepaidType == 'TREATMENT_PACKAGE' || item.prepaidType == 'CASH_PACKAGE') }">
							<a href='<c:url value="/prepaid/toEditExpiryDate?ptId=${item.id}"/>' data-permission="prepaid:toEditExpiryDate" class="btn btn-primary dialog btn-edit" data-draggable="true"
								data-title='Edit expiry date' data-width="880">
								<i class="glyphicon glyphicon-cog"></i>
							</a>
						</c:if>
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
               Dialog.get().close();
               Dialog.success(response.message, function () {
                   $('a.search-btn', getContext()).trigger('click'); // 触发重新查询事件
               });
           }
        });
 	}
</script>
