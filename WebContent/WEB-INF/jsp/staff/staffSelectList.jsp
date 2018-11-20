<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

	<c:if test="${showAll !=null && showAll }">
	    <option value=""><spring:message code="label.option.select.all"/></option>
	</c:if>
	<c:if test="${showAll !=null && !showAll }">
	    <option value=""><spring:message code="label.option.select.single"/></option>
	</c:if>
	<c:forEach items="${staffList}" var="item">
	    <option value="${item.id}" <c:if test="${initialValue !=null && initialValue == item.id}">selected</c:if>>${item.displayName}</option>
	</c:forEach>
