<%@page import="org.spa.vo.sales.OrderItemVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<tr>
	<td>
		<spring:message code="label.CATIPS"/>
	</td>
	<td>
		${staffName}
		<input type="hidden" name="tipsItemVOs[${idxId}].key"value="${staffId}"/>
	</td>
	<td>
		1
	</td>
	<td>
		<spring:message code="label.money.currency"/>${amount}
		<input type="hidden" name="tipsItemVOs[${idxId}].value"value="${amount}"/>
	</td>
	<td>
		
	</td>
	<td>
		
	</td>
	<td class="finalAmount" data-val="${amount}">
		<spring:message code="label.money.currency"/>${amount}
	</td>
	<td>
		<button class="btn btn-warning removeItemBtn">Remove</button>
	</td>
</tr>