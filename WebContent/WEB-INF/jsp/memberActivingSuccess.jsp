<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="label.company.title"/></title>
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
            background: url(<c:url value="/resources/img/background.jpg"/>);
            font-size: 14px;
            font-family: "Avenir Next", Avenir, "Helvetica Neue", Helvetica, "Lantinghei SC", "Hiragino Sans GB";
            color: #747f8c;
            background-size: cover
        }

        input, textarea, keygen, select, button {
            font-family: "Avenir Next", Avenir, "Helvetica Neue", Helvetica, "Lantinghei SC", "Hiragino Sans GB";
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
            height: 400px;
            display: block;
            padding: 40px;
            border-radius: 3px;
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
            color: #747f8c;
            width: 100% !important;
            box-sizing: border-box;
            border: 1px solid #A4A6B5;
            border-radius: 3px;
            background: transparent;
        }

        input[type="text"]:focus, input[type="email"]:focus, input[type="password"]:focus {
            background: #fff
        }

        .form-group.space input#login_ok {
            background-color: #2376BC;
            color: #fff;
            border: none;
            border-radius: 3px;
            width: 100% !important;
            cursor: pointer;
            line-height: 35px;
        }
    </style>
</head>
<body>
<div class="main_box">
    <div class="login_box">
        <div class="login_logo">
        </div>
        <div class="login_form">
            <spring:message code="label.member.activing.msg"/><a href='<c:url value="/memberLogin"/>'><spring:message code="label.login.login"/></a>.
        </div>
    </div>
    <br>
    <div class="bottom">
        <spring:message code="label.company.copyright"/> <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>
    </div>
</div>
<script type="text/javascript" src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>
</body>
</html>