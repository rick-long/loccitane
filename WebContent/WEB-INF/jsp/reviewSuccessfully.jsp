<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>

<%
    // 所有css / js資源版本控制，每次更新的時候需要修改版本號，保證客戶端使用的css和js都是最新的代碼
    request.setAttribute("version", "20160830");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="label.review"/></title>
</head>
<link href='<c:url value="/resources/js/comment/css/comment.css"/>?v=${version}' rel="stylesheet">
<body>
<div class="form_for_review">
<div class="banner_for_review">
<img src="/resources/img/Reception-new.jpg" width="100%">
</div>
	
    <ul class="rev_pro clearfix" style="height: 200px; color:red">
    	<li>
    		<c:if test="${status eq 'not_exist' }">
    			<h3><spring:message code="label.review.success.notexit"/></h3>
    		</c:if>
    		<c:if test="${status eq 'repeat' }">
    			<h3><spring:message code="label.review.success.repeat"/></h3>
    		</c:if>
    		<c:if test="${status eq 'success' }">
    			<h3><spring:message code="label.review.success.text"/></h3>
    		</c:if>
    	</li>
    </ul>
   <a href='<c:url value="/"/>'><spring:message code="label.button.return"/></a>

</div>
</body>
</html>
