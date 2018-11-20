<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.spa.model.user.ConsentForm" %>
<%@page import="org.spa.model.user.ConsentFormUser" %>
<%@page import="org.spa.model.user.User" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<c:if test="${accountType eq 'GUEST' }">
	<spring:message code="label.book.visitors"/>
</c:if>
<div class="container-fluid">
<c:if test="${accountType ne 'GUEST' }">
     <div class="row lo-m-t-15">
      <div class="col-md-5  col-xs-12 text-right"><strong><spring:message code="label.username"/> : </strong></div>
      <div class="col-md-7 col-xs-12">${client.username}</div>
    </div>
    <div class="row lo-m-t-15">
      <div class="col-md-5  col-xs-12 text-right"><strong><spring:message code="label.fullName"/> : </strong></div>
      <div class="col-md-7 col-xs-12">${client.fullName }</div>
    </div>
      <div class="row lo-m-t-15">
      <div class="col-md-5  col-xs-12 text-right"><strong><spring:message code="label.gender"/> : </strong></div>
      <div class="col-md-7 col-xs-12">${client.gender}</div>
    </div>
      <div class="row lo-m-t-15">
      <div class="col-md-5  col-xs-12 text-right"><strong><spring:message code="label.member.dateOfBirth"/> : </strong></div>
      <div class="col-md-7 col-xs-12"><fmt:formatDate value="${client.dateOfBirth}" pattern="yyyy-MM-dd"/></div>
    </div>
     <div class="row lo-m-t-15">
      <div class="col-md-5  col-xs-12 text-right"><strong><spring:message code="label.member.cash.package.remaining"/> : </strong></div>
      <div class="col-md-7 col-xs-12">${client.remainValueOfCashPackage}</div>
    </div>
    <hr/>
     <div class="row lo-m-t-15">
       <div class="col-md-12">
       <h3><spring:message code="label.prepaid.top.up.transaction.management"/> </h3>
			<c:forEach items="${prepaidList}" var="item" varStatus="idx">
			<table class="table table-striped big_list">
				<thead>
				<th><fmt:formatDate value="${item.firstPrepaidTopUpTransaction.topUpDate}" pattern="yyyy-MM-dd"/></th>
				<th>${item.reference}</th>
				<th><c:set var="prepaidType">
					${fn:replace(item.prepaidType, "_", "")}
				</c:set> <spring:message code="label.prepaid.type.${prepaidType}" />
				</th>
				<th>${item.firstPrepaidTopUpTransaction.productOption.label3}</th>
				<th></th>
				</thead>

				<c:forEach items="${item.prepaidTopUpTransactions}" var="prepaidTopUpTransactions" varStatus="idx">
					<tbody>
					<tr>
						<td>${prepaidTopUpTransactions.topUpReference}</td>
						<td>${prepaidTopUpTransactions.shop.name }</td>
						<td><fmt:formatNumber type="number" pattern="#,##0.0#" value="${prepaidTopUpTransactions.topUpInitValue}" /></br>(<font color="#ff8888"><fmt:formatNumber type="number" pattern="#,##0.0#" value="${prepaidTopUpTransactions.remainValue}"/></font>)</td>
						<td><fmt:formatDate value="${prepaidTopUpTransactions.expiryDate}" pattern="yyyy-MM-dd"/></td>
						<td>${prepaidTopUpTransactions.productOption.label3 }</td>
					</tr>
					</tbody>
				</c:forEach>
			</table>
  </div>
 </div> 
  <div class="row lo-m-t-15">
       <div class="col-md-12">
       <h3><spring:message code="label.member.consent.form"/> </h3>
		<table class="table table-striped big_list">
			<thead>
			<tr>
				<c:forEach items="${consentFormList}" var="cf">
					<th>${cf.name }</th>
				</c:forEach>
			</tr>
			</thead>
			<tbody>
			<tr>
				<c:forEach items="${consentFormList}" var="cf1">

					<td>
						<%
							User user = (User) request.getAttribute("client");
							ConsentForm cf1 = (ConsentForm) pageContext.getAttribute("cf1");
							ConsentFormUser cfu = user.getConsentFormDetails(cf1.getId());
							if (cfu != null && cfu.getShop() != null) {
						%>
						<%=cfu.getShop().getReference() %>
						<%
						} else {
						%>
						No
						<%
							}
						%>
					</td>
				</c:forEach>
			</tr>
			</tbody>
		</table>
  </div>
</div>
 <div class="row lo-m-t-15">
   <div class="col-md-12">
      <h3><spring:message code="label.member.family.details"/></h3> 
		<table class="table table-striped big_list">
			<thead>
			<tr>
				<th><spring:message code="label.name"/> </th>
				<th><spring:message code="label.email"/> </th>
				<th><spring:message code="label.tel"/> </th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${familyDetailsList}" var="item">
				<tr>
					<td>${item.name }</td>
					<td>${item.email }</td>
					<td>${item.telNum }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
 </div>
</div>
</c:if>
</div>
