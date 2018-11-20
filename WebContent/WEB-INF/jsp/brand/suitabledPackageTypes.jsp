<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

	
	<option value="NORMAL" <c:if test="${packageTypeInit ==null || 'NORMAL' eq packageTypeInit }">selected</c:if>><spring:message code="label.prepaid.package.type.NORMAL"/></option>
	<c:if test="${suitabledPackageTypes !=null && suitabledPackageTypes.size() >0 }">
		<c:forEach var="brand" items="${suitabledPackageTypes }">
	    	<option value="${brand.name}" <c:if test="${brand.name eq packageTypeInit }">selected</c:if>>${brand.name}</option>
	    </c:forEach>
    </c:if>