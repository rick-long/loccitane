<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:if test="${showAll !=null && showAll }">
    <option value=""><spring:message code="label.option.select.all"/></option>
</c:if>
<c:if test="${showAll !=null && !showAll }">
    <option value=""><spring:message code="label.option.select.single"/></option>
</c:if>
<c:forEach var="pr" items="${roomList}">
    <option value="${pr.id}" <c:if test="${pr.id == roomId}">selected</c:if>>${pr.name }</option>
</c:forEach>



