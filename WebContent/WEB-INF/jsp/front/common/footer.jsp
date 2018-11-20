<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
	<p class="text-center">
		<spring:message code="label.company.copyright"/><fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>
   		<a href="http://www.imanagesystems.com/" target="_blank">IMS</a>
   	</p>

