<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="label.login.forgot.password"/></title>
  	<link href='<c:url value="/resources/js/jqueryui/jquery-ui.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-contextMenu/jquery.contextMenu.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/multi-select/multi-select.css"/>?v=${version}' rel="stylesheet">

    <!-- Bootstrap -->
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.css.map"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.css.map"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/bootstrap-dialog/bootstrap-dialog.css"/>?v=${version}' rel="stylesheet">
	
    <link href='<c:url value="/resources/css/metisMenu/metisMenu.min.css"/>?v=${version}' rel="stylesheet">

    <!-- Custom Fonts -->
    <link href='<c:url value="/resources/css/font-awesome/font-awesome.min.css"/>?v=${version}' rel="stylesheet">

    <!-- bootstrapvalidator-0.4.5 -->
    <link href='<c:url value="/resources/css/bootstrapvalidator-0.4.5/bootstrapValidator.css"/>?v=${version}' rel="stylesheet">
    <!-- jquery.datetimepicker -->
    <link href='<c:url value="/resources/js/boootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/js/bootstrap-submenu/css/bootstrap-submenu.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/boostrap-select/css/bootstrap-select.css"/>?v=${version}' rel="stylesheet">
    
    <link href='<c:url value="/resources/css/base/public.css"/>?v=${version}' rel="stylesheet">
    
        <link href='<c:url value="/resources/css/front/public_front.css"/>?v=${version}' rel="stylesheet">
    <style type="text/css">
  *, *:before, *:after {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box
        }

	input:-webkit-autofill{
    background-color:transparent;
    background-image: none;
    color: rgb(0, 0, 0);
}

        body {
            background: url(<c:url value="/resources/img/front/bg_front_memeber-old.jpg"/>);
            font-size: 14px;
            font-family: "Avenir Next", Avenir, "Helvetica Neue", Helvetica, "Lantinghei SC", "Hiragino Sans GB";
            color: #fff;
            background-size: cover
        }

        input, textarea, keygen, select, button {
            font-family: "Avenir Next", Avenir, "Helvetica Neue", Helvetica, "Lantinghei SC", "Hiragino Sans GB";
            color: #747f8c;
            font-size: 14px
        }

        a:link {
            color: #fff;
        }

        input[type="text"], input[type="email"], input[type="password"] {
            display: inline-block;
            padding: 8px 3px;
            width:88% !important;
            box-sizing: border-box;
            background: transparent;
			border: 0;
            color: #fff;
        }

.login_logo {
    text-align: center;
}
	
		
	.form-control {
    display: block;
    width: 100%;
    height: 28px;
    padding: 3px 9px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #fff;
    background-color: transparent;
    background-image: none;
    border: 0;
    border-radius: 0;
    -webkit-box-shadow: none;
    box-shadow: none;
    -webkit-transition:  none;
    -o-transition: none;
    transition: none;
}

.btn.btn-primary.btn-lg {
    background: transparent !important;
    color: #fff;
    border: 1px solid #fff;
    border-radius: 25px;
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
            	<spring:message code="label.member.forgot.password.msg"/>
            <br />
			<div class="form-group space">
                 <label class="t"></label>
                 <a href='<c:url value="/memberLogin"/>' class="btn btn-primary btn-lg"><spring:message code="label.login.login"/></a>
             </div>
        </div>
    </div>
    <br>
</div>
<script type="text/javascript" src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>
</body>
</html>