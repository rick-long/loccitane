<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

	<option value=""><spring:message code="label.option.select.single"/></option>
	<c:if test="${suitablePackages !=null && suitablePackages.size() >0 }">
		<c:forEach var="prepaid" items="${suitablePackages }">
	    	<option value="${prepaid.id }">${prepaid.name }[${prepaid.remainingValueByAvailableTransaction}]</option>
	    </c:forEach>
    </c:if>