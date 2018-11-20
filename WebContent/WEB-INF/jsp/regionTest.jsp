<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>regionTest</title>
    <style>
        select{
            padding:5px 0;
        }
        .outer{
            width:500px;
            margin:20px auto;
        }
    </style>

</head>
<body>
<form  method="post" action='/front/region/saveTest'>
<div class="outer">
    <select name="province" id="province">
        <option value="请选择">请选择</option>
    </select>
    <select name="city" id="city">
        <option value="请选择">请选择</option>
    </select>
    <select name="town" id="town">
        <option value="请选择">请选择</option>
    </select>
    <input type="submit" value="Submit" />
</div>
</form>
</body>
<script type="text/javascript" src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/jQueryProvinces/area.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/jQueryProvinces/select.js"/>'></script>
</html>