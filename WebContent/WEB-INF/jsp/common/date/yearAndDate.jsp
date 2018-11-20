<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<option value=""><spring:message code="label.option.select.all"/></option>
<c:forEach var="yearAndMonth" items="${allYearAndMonths }">
	<option value="${yearAndMonth}" <c:if test="${yearAndMonth eq currentMonth }">selected</c:if>>${yearAndMonth}</option>
</c:forEach>