<%@page import="org.spa.vo.sales.OrderItemVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

	<c:if test="${orderVO !=null && orderVO.itemVOs !=null}">
		<c:forEach items="${orderVO.itemVOs}" var="item" varStatus="idx">
			<c:if test="${item.productOptionId !=null}">
				<tr data-id="${item.productOptionId}" data-bundle-id="${item.bundleId }">
					<td>
						${item.productOption.label3}
						<c:if test="${item.bookItemId !=null }">
							<font color="red">#${orderVO.bookRef}</font>
						</c:if>
						<input type="hidden" name="itemVOs[${item.idxId}].bookItemId" id="itemVOs[${item.idxId}].bookItemId" value="${item.bookItemId}"/>
						<input class="productOptionId" type="hidden" name="itemVOs[${item.idxId}].productOptionId" id="itemVOs[${item.idxId}].productOptionId" value="${item.productOptionId}"/>
						<%-- <input type="hidden" name="itemVOs[${idx.index}].startTime" id="itemVOs[${idx.index}].startTime" value='<fmt:formatDate value="${bookItem.appointmentTime}" pattern="HH:mm"/>'/> --%>
						<input type="hidden" name="itemVOs[${item.idxId}].bundleId" id="itemVOs[${item.idxId}].bundleId" value="${item.bundleId}" />
					</td>
					<td>
						<c:forEach items="${item.therapists}" var="therapist" varStatus="theIdx">
							<c:if test="${therapist.key !=null }">
								<c:if test="${therapist.staffName !=null }">${therapist.staffName }(<fmt:formatNumber type="number" pattern="0.00" value="${therapist.commission}" />)</br></c:if>
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
					<td><spring:message code="label.money.currency"/>0</td>
					<td><spring:message code="label.money.currency"/>0</td>
					<td class="finalAmount" data-val="${item.amount}">
						<spring:message code="label.money.currency"/>
						<fmt:formatNumber type="number" pattern="0.00" value="${item.amount}" />
					</td>
					<c:if test="${idx.last }">
						<td>
							<button class="btn btn-warning removeBundleItemBtn" >Remove</button>
						</td>
					</c:if>
					<c:if test="${!idx.last }">
						<td></td>
					</c:if>
				</tr>
			</c:if>
		</c:forEach>
	</c:if>