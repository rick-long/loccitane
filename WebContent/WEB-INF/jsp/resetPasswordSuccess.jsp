<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/resources/img/favicon.ico" rel="icon" type="image/x-icon">
    <title><spring:message code="label.login.reset.password"/></title>
    <style type="text/css">
 html {
           background: url(<c:url value="/resources/img/LO_Background-Image3.jpg"/>);
            font-size: 14px;
            font-family: SegoeUI, sans-serif;
            color: #747f8c;
            background-size: cover;
			background-attachment:fixed;
			background-repeat:no-repeat;
        }
		
	body {
    font-family:SegoeUI, sans-serif;
    font-size: 12px;
    line-height: 1.42857143;
    color: #666;
    background: none;
}	

.main_box {
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -230px;
    margin-left: -200px;
    width: 400px;
    height: 380px;
    display: block;
    padding: 40px;
    border-radius: 3px;
    background: rgba(255,255,255,0.6);
}

.bottom {
    text-align: center;
    font-size: 12px;
    color: #f001022;
}

input::-webkit-input-placeholder { /* WebKit browsers */
color:#A4A6B5;
}

input:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
color:#A4A6B5;
}

input::-moz-placeholder { /* Mozilla Firefox 19+ */
　color:#A4A6B5;
}

input:-ms-input-placeholder { /* Internet Explorer 10+ */
color:#A4A6B5;
}

.login_btn{    background: #ffcb00!important;
    border: 1px solid #ffcb00;
    border-radius: 0;
    line-height: 30px;
    width: 100%;
    color: #001022;
	cursor: pointer;}

    </style>
</head>
<body>
<div class="main_box">
    <div class="login_box">
        <div class="login_logo">
        </div>
        <div class="login_form">
            <spring:message code="label.reset.password.successfully"/>
            <br/><br/>
            <a href='<c:url value="/memberLogin"/>' class="login_btn"><spring:message code="label.login.login"/></a>
        </div>
    </div>
    <br>
    <div class="bottom">
        <spring:message code="label.company.copyright"/><fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>
    </div>
</div>
<script type="text/javascript" src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>
</body>
</html>