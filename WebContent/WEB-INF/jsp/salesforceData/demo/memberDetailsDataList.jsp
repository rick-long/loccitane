<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

		<table class="table table-striped table-hover">
				<thead>
					<tr data-id="${item.id}">
						<th class="col-lg-1"><spring:message code="label.home.shop"/></th>
					 	<th class="col-lg-1"><spring:message code="label.loyalty.level"/></th>
					    <th class="col-lg-1"><spring:message code="label.username"/></th>
					    <th class="col-lg-1"><spring:message code="label.fullName"/></th>
					    <th class="col-lg-2"><spring:message code="label.email"/></th>
				     	<th class="col-lg-1"><spring:message code="label.phone"/></th>
					    <th class="col-lg-1"><spring:message code="label.salesforce.last.modifier.date"/></th>
					    <th class="col-lg-1"><spring:message code="label.salesforce.last.update.date"/></th>
					    <th><spring:message code="label.salesforce.last.update.date,by.sf"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${demoUsers}" var="item">
					<tr>
						<td>${item.shop.name }</td>
		            	<td>${item.currentLoyaltyLevel.name}</td>
		                <td>${item.username}</td>
		                <td>${item.fullName }</td>
		                <td>${item.email }</td>
		                <td>
		                	<c:forEach items="${item.phones}" var="phone">
			           			<b><spring:message code="label.phone.${phone.type}"/></b> : ${phone.number}</br>
			           		</c:forEach>
		                </td>
		                <td><fmt:formatDate value="${item.lastModifier}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                <td><fmt:formatDate value="${item.lastUpdated}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                <td><fmt:formatDate value="${item.lastSFUpdated}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>