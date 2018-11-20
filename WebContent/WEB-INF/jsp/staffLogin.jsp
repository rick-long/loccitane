<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<link href='<c:url value="/resources/css/base/public.css"/>'  rel="stylesheet" type="text/css">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/resources/img/favicon.ico" rel="icon" type="image/x-icon">
    <title><spring:message code="label.company.title"/></title>
    <style type="text/css">
        *,*:before,*:after{-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box}
        input:focus {outline: none;}
        [placeholder]:focus::-webkit-input-placeholder{-webkit-transition:opacity 0.5s ease;-moz-transition:opacity 0.5s ease;transition:opacity 0.5s ease;opacity:0}
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

   </style>
</head>
<body>
<div class="inner-bg">
<div class="container">
<div class="row">
<div class="main_box col-sm-8 col-sm-offset-2">
    <div class="login_box">
        <div class="login_logo">
            <img src='<c:url value="/resources/img/logo.png"/>' >
        </div>

        <div class="login_form">
            <form action='<c:url value="/staffLogin"/>' id="loginForm" method="post">
                <input type="hidden" name="accountType" value="STAFF">
                <div class="form-group">
                    <input id="userName" value="" name="userName" type="text" class="form-control x319 in" autocomplete="off" placeholder='<spring:message code="label.login.account"/>'>
                </div>
                <div class="form-group">
                    <input id="password" value="" name="password" type="password" class="form-control x319 in" placeholder='<spring:message code="label.login.password"/>'>
                </div>
                <div class="login_msg">
                    <c:if test="${not empty error}">
                        <spring:message code="label.login.error"/>
                    </c:if>
                </div>

                <div class="form-group space">
                    <label class="t"></label>

                    <input type="submit" id="login_ok" value='&nbsp;<spring:message code="label.login.login"/>&nbsp;' class="btn btn-primary btn-lg">

                </div>
                <div class="form-group space">
                    <label class="t"></label>
                    <a href='<c:url value="/staffForgetPassword"/>'><spring:message code="label.login.forgot.password"/></a>
                </div>
            </form>
        </div>
    </div>
    <br>

    <div class="bottom">
       <spring:message code="label.company.copyright"/><fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>
    </div>
</div>
</div>
</div>
</div>
<script type="text/javascript" src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>
<script type="text/javascript">
    $(function () {
        var userName = $("#userName");
        var password = $("#password");
        userName.focus();
        $("#loginForm").submit(function () {
            if (!$.trim(userName.val())) {
                userName.css('border', '2px #ff0000 solid');
                return;
            }
            if (!$.trim(password.val())) {
                password.css('border', '2px #ff0000 solid');
                return;
            }
            $("#login_ok").attr("disabled", true).val('Login..');
        });

        // 显示超时信息
        if (window.location.href.indexOf('timeout=true') > -1) {
            $(".login_msg").html("Session Timeout, Login again!");
        }
    });
</script>
</body>
</html>