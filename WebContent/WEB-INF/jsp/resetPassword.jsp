<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/resources/img/favicon.ico" rel="icon" type="image/x-icon">
    <title><spring:message code="label.login.reset.password"/></title>
    <style type="text/css">
        *, *:before, *:after {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box
        }

        input:focus {
            outline: none;
        }

        [placeholder]:focus::-webkit-input-placeholder {
            -webkit-transition: opacity 0.5s ease;
            -moz-transition: opacity 0.5s ease;
            transition: opacity 0.5s ease;
            opacity: 0
        }

        body {
            background: url(<c:url value="/resources/img/LO_Background-Image3.jpg"/>);
            font-size: 14px;
            font-family:SegoeUI, sans-serif;;
            color: #fff;
            background-size: cover
        }

        input, textarea, keygen, select, button {
            font-family:SegoeUI, sans-serif;
            color: #747f8c;
            font-size: 14px
        }

        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px white inset;
        }

        a:link {
            color: #A4A6B5;
        }

        .main_box {
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -230px;
    margin-left: -200px;
    width: 400px;
    height: 500px;
    display: block;
    padding: 40px;
    border-radius: 3px;
    background: rgba(255,255,255,0.6);
        }

        .main_box .setting {
            position: absolute;
            top: 5px;
            right: 10px;
            width: 10px;
            height: 10px;
        }

        .main_box .setting a {
            color: #FF6600;
        }

        .main_box .setting a:hover {
            color: #555;
        }

        .login_logo {
            text-align: center;
        }

        .login_msg, .error {
            text-align: center;
            color: red;
            margin-top: 5px;
        }

        .login_form {
            padding-top: 20px;
        }

        .login_box .form-control {
            display: inline-block;
            *display: inline;
            zoom: 1;
            width: auto;
        }

        .login_box .form-control.x319 {
            width: 319px;
        }

        .login_box .form-control.x164 {
            width: 164px;
        }

        .login_box .form-group {
            margin-top: 15px;
        }

        .login_box .form-group img {
            margin-top: 1px;
            height: 32px;
            vertical-align: top;
        }

        .login_box .m {
            cursor: pointer;
        }

        .bottom {
            text-align: center;
            font-size: 12px;
        }

        input[type="text"], input[type="email"], input[type="password"] {
            display: inline-block;
            border-radius: 3px;
            padding: 10px 15px;
            color: #A4A6B5;
            width: 100% !important;
            box-sizing: border-box;
            border: 1px solid #fff;
            border-radius:0;
            background: transparent;
        }

        input[type="text"]:focus, input[type="email"]:focus, input[type="password"]:focus {
            background: #fff
        }

        .form-group.space input#registerBtn{
   background: #ffcb00!important;
    border: 1px solid #ffcb00;
    border-radius: 0;
    line-height: 30px;
    width: 100%;
    color: #001022;
	cursor: pointer;
        }
		
	.bottom {
    text-align: center;
    font-size: 12px;
	color:#001022;
}	

    </style>
</head>
<body>
<div class="main_box">
    <div class="login_box">
    <div class="login_logo">
          <img src='<c:url value="/resources/img/logo.png"/>' >
           <br/>
           Reset Password
        </div>

        <div class="login_form">
            <form id="registerForm" action='<c:url value="/resetPassword"/>' method="post">
                <input type="hidden" name="form_token" value="${TokenUtil.get(pageContext)}"/>
                <input type="hidden" name="code" value="${code}"/>
                <div class="form-group">
                    <input id="password" value="" name="password" type="password" class="form-control x319 in" placeholder="<spring:message code="label.login.new.password"/>">
                </div>
                <div class="form-group">
                    <input id="confirmPassword" value="" name="confirmPassword" type="password" class="form-control x319 in" placeholder="<spring:message code="label.login.confirmPassword"/>">
                </div>
                <div class="login_msg">
                </div>
                <div class="form-group space">
                    <label class="t"></label>
                    <input type="submit" id="registerBtn" value="&nbsp;<spring:message code="label.login.reset.password"/>&nbsp;" class="btn btn-primary btn-lg">
                </div>
            </form>
        </div>
    </div>
    <br>
    <div class="bottom">
        <spring:message code="label.company.copyright"/><fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>
    </div>
</div>
<script type="text/javascript" src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>
<script type="text/javascript">
    $(function () {
        var errorFields = [];
        <c:if test="${not empty ajaxForm && not empty ajaxForm.errorFields}">
        <c:forEach items="${ajaxForm.errorFields}" var="field">
        errorFields.push({
            name: '${field.fieldName}',
            message: '${field.errorMessage}'

        });
        </c:forEach>
        </c:if>

        $.each(errorFields, function (index, item) {
            $('input[name=' + item.name + ']').parent().append('<span class="error">' + item.message + '</span>');
        });
    });
</script>
</body>
</html>