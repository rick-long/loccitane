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
    <title>review</title>
</head>
<link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css"/>?v=${version}' rel="stylesheet">
<script src='<c:url value="/resources/js/comment/js/jquery-1.8.3.min.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/comment/js/bootstrap.min.js"/>?v=${version}'></script>


<body>
<div>
<h2>&nbsp avg:${avg}</h2>
<p>&nbsp &nbsp top10</p>
<table class="table" style="width: 900px">

    <c:forEach items="${top10List}" var="top10">
        <tr>
            <td><a href="/front/test2?shopId=${top10.shopId}" >${top10.shopName}</a></td>
            <td>${top10.customerServiceStar}</td>

        </tr>
    </c:forEach>


</table>

<p>&nbsp &nbsp Low10</p>
<table class="table" style="width: 900px;">

    <c:forEach items="${low10List}" var="low10">
        <tr>
            <td><a href="/front/test2?shopId=${low10.shopId}" >${low10.shopName}</a></td>
           <td>${low10.customerServiceStar}</td>

        </tr>
    </c:forEach>
</table>
</div>
</body>
</html>
