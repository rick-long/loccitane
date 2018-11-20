<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="label.company.title"/><spring:message code="label.member.login"/></title>
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


 html {
           background: url(<c:url value="/resources/img/front/LO_Background-Image2.jpg"/>);
            font-size: 14px;
            font-family:SegoeUI, sans-serif;
            color: #FFF;
            background-size: cover;
			background-attachment:fixed;
			background-repeat:no-repeat;
        }
		
	body {
    font-family:SegoeUI, sans-serif;
    font-size: 12px;
    line-height: 1.42857143;
    color: #fff;
    background: none;
}	
       

        input, textarea, keygen, select, button {
            font-family:SegoeUI, sans-serif;
            color: #747f8c;
            font-size: 14px
        }

        a:link {
            color: #fff;
        }

        input[type="text"], input[type="email"], input[type="password"] {
            display: inline-block;
            padding: 4px 3px;
            width:100% !important;
            box-sizing: border-box;
            background: transparent;
			border: 0;
            color: #fff;
        }

.login_logo {
    text-align: center;
}

.main_box .login_form {
    padding: 0 25px 0 25px;
    -moz-border-radius: 0 0 4px 4px;
    -webkit-border-radius: 0 0 4px 4px;
    border-radius: 0 0 4px 4px;
    text-align: left;
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
    color: #001022;
    border: 1px solid #001022;
    border-radius: 0;
    width: 80% !important;
    cursor: pointer;
    line-height: 30px;
}	
	.bottom {
    text-align: center;
    font-size: 12px;
	color:#001022;
}	

    .login_box .form-group {
    margin-top: 15px;
    padding: 0 0 7px 0;
}	

.main_box input[type="text"], .main_box input[type="email"], .main_box input[type="password"] {
            display: inline-block;
            border-radius: 3px;
            padding:5px 15px;
            color: #747f8c;
            width: 100%!important;
            box-sizing: border-box;
            border: 1px solid #A4A6B5;
            border-radius:0;
            background: transparent;
			margintop:5px;
        }
		

    </style>
</head>
<body>
<div class="main_box">
    <div class="login_box">
        <div class="login_logo">
           <img src='<c:url value="/resources/img/logo.png"/>' >
        </div>
        <c:if test="${isAfterRegister}">
			<div class="after_register">
				<spring:message code="label.member.login.welcome"/>
			</div>
		</c:if>
        <div class="login_form">
            <form action='<c:url value="/memberLogin"/>' id="loginForm" method="post">  
                <input type="hidden" name="accountType" value="MEMBER">
                <div class="form-group input_boder"><input id="userName" value="" name="userName" type="text" class="form-control in" autocomplete="off" placeholder="<spring:message code="label.login.account"/>">
                </div>
                <div class="form-group input_boder">                 
                   <input id="password" value="" name="password" type="password" class="form-control x319 in" placeholder="<spring:message code="label.login.password"/>">
                </div>
                <div class="login_msg">
                    <c:if test="${not empty error}">
                 <spring:message code="label.login.error"/>
                    </c:if>
                </div>

                <div class="form-group space">
                    <label class="t"></label>
                    <input type="submit" id="login_ok" value='&nbsp;<spring:message code="label.member.sign.in"/>&nbsp;' class="btn btn-primary btn-lg">
                </div>
            </form> 
        </div>
         <div class="forget"><a href='<c:url value="/memberForgetPassword"/>'>  <spring:message code="label.login.forgot.password"/>?</a></div>
     	 <div class="form-group space" style="margin-top:20px;text-align: center;">
             <label class="t"></label>
             <a href='<c:url value="/memberRegister"/>' class="btn btn-primary btn-lg">&nbsp;'&nbsp;&nbsp;<spring:message code="label.member.login.login"/>'&nbsp;</a>
         </div>
         <div class="bottom">
        		<spring:message code="label.company.copyright"/><fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>
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
    });
</script>
</body>
</html>