<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<c:forEach items="${memberList}" var="item">
    <option value="${item.id}">${item.firstName} ${item.lastName}(${item.username})</option>
</c:forEach>
