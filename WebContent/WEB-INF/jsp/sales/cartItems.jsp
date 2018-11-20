<%@page import="org.spa.vo.sales.OrderItemVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<c:choose>
<c:when test="${orderVO !=null && orderVO.validPrepaidMessage !=null}">
	<tr class="error_msg">
		<td colspan="8">
			<c:if test="${orderVO.validPrepaidMessage eq 'NULL'}"><font color="red"><b>The prepaid is not exist in system,please check!</b></font></c:if>
			<c:if test="${orderVO.validPrepaidMessage eq 'EXPIRY'}"><font color="red"><b>The prepaid is expired in system,please check!</b></font></c:if>
			<input type="hidden"id="validSuccess" value="false"/>
		</td>
	</tr>
</c:when>
<c:otherwise>
	<c:if test="${orderVO !=null && orderVO.itemVOs !=null}">
		<c:forEach items="${orderVO.itemVOs}" var="item">
			<c:if test="${item.productOptionId !=null}">
				<tr data-id="${item.productOptionId}">
					<td>
						${item.productOption.label3}

						<input type="hidden" name="itemVOs[${item.idxId}].bookItemId" id="itemVOs[${item.idxId}].bookItemId" value="${item.bookItemId}"/>
						<input class="productOptionId" type="hidden" name="itemVOs[${item.idxId}].productOptionId" id="itemVOs[${item.idxId}].productOptionId" value="${item.productOptionId}"/>
						<%-- <input type="hidden" name="itemVOs[${idx.index}].startTime" id="itemVOs[${idx.index}].startTime" value='<fmt:formatDate value="${bookItem.appointmentTime}" pattern="HH:mm"/>'/> --%>
					</td>
					<td>
						<c:forEach items="${item.therapists}" var="therapist" varStatus="theIdx">
							<c:if test="${therapist.key !=null }">
								<c:if test="${therapist.staffName !=null }">${therapist.staffName }: ${therapist.commission}(${therapist.extraCommission})</br></c:if>
						       <input type="hidden" name="itemVOs[${item.idxId}].therapists[${theIdx.index}].key" value="${therapist.key}"/>
						       <input type="hidden" name="itemVOs[${item.idxId}].therapists[${theIdx.index}].staffId" value="${therapist.staffId}"/>
						       <input type="hidden" name="itemVOs[${item.idxId}].therapists[${theIdx.index}].requested" value="${therapist.requested}"/>
							</c:if>
						</c:forEach>
					</td>
					<td>
						${item.qty}
						<input class="qty" type="hidden" name="itemVOs[${item.idxId}].qty" id="itemVOs[${item.idxId}].qty" value="${item.qty}"/>
					</td>
					<td>
						<spring:message code="label.money.currency"/><fmt:formatNumber type="number" pattern="0.00" value="${item.price}" />
					</td>
					<td>
						<spring:message code="label.money.currency"/><fmt:formatNumber type="number" pattern="0.00" value="${item.finalDiscount}" />
						<%-- <input type="hidden" name="itemVOs[${item.idxId}].extraDiscount" id="itemVOs[${item.idxId}].extraDiscount" value="${item.extraDiscount}"/> --%>
						<input type="hidden" name="itemVOs[${item.idxId}].adjustNetValue" id="itemVOs[${item.idxId}].adjustNetValue" value="${item.adjustNetValue}"/>
					</td>
					<td>
						<spring:message code="label.money.currency"/><fmt:formatNumber type="number" pattern="0.00" value="${item.prepaidPaidAmount}" />
						<input type="hidden" name="itemVOs[${item.idxId}].paidByPackageId" id="itemVOs[${item.idxId}].paidByPackageId" value="${item.paidByPackageId}"/>
						<input type="hidden" name="itemVOs[${item.idxId}].paidByVoucherRef" id="itemVOs[${item.idxId}].paidByVoucherRef" value="${item.paidByVoucherRef}"/>
						<input type="hidden" id="prepaidPaidAmt_${item.idxId}" value="${item.prepaidPaidAmount}"/>
					</td>
					<td class="finalAmount" data-val="${item.finalAmount}">
						<spring:message code="label.money.currency"/><fmt:formatNumber type="number" pattern="0.00" value="${item.finalAmount}" />
					</td>
					<td>
						<button class="btn btn-warning removeItemBtn" data-book-item-id="${item.bookItemId}" data-idx-id="${item.idxId}">Remove</button>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</c:if>
</c:otherwise>
</c:choose>