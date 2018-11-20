<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="label.member.register"/></title>
     <link href="/resources/img/favicon.ico" rel="icon" type="image/x-icon">
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
            background: url(<c:url value="/resources/img/front/LO_Background-Image2.jpg"/>);
            font-size: 14px;
            font-family:SegoeUI, sans-serif;
            color: #001022;
            background-size: cover
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
    
    .main_box {
    position: absolute;
    top: 45%;
    left: 50%;
    margin-top: -230px;
    margin-left: -200px;
    width: 500px;
    height: 630px;
    display: block;
    padding: 40px;
    border-radius: 3px;
    background: rgba(255,255,255,0.2);
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

.main_box .login_form h5 {
    line-height: 16px;
    text-align: justify;
} 

.main_box .login_form {
    padding: 0 20px 20px 20px;
    -moz-border-radius: 0 0 4px 4px;
    -webkit-border-radius: 0 0 4px 4px;
    border-radius: 0 0 4px 4px;
    text-align: left;
}

.login_form{text-align: justify;} 

.btn-group-lg>.btn, .btn-lg {
    padding: 10px 16px;
    font-size: 18px;
    line-height: 1.3333333;
    border-radius: 0;
}
            
    </style>
</head>
<body>
<div class="main_box">
    <div class="login_logo" style="text-align: center">
         <img src='<c:url value="/resources/img/logo.png"/>' >
    </div>
    <div class="login_box">
        <div class="login_form">
            <h5><spring:message code="label.member.register.welcome"/><spring:message code="label.company.title"/>!</h5>
           <spring:message code="label.member.register.successfully"/>
            <br /><br />
           <spring:message code="label.company.title"/> signature Spa experience is available in the brand’s own Spas, and in luxurious hotel properties around the world. 
            <br />
<br />Please feel free to contact us to know more about our Spa offer, treatments or locations, or become one of our selected hotel partners.
<br/><br/>
            <div class="form-group space">
                 <label class="t"></label>
                 <a href='<c:url value="/memberLogin"/>' class="btn-primary btn-lg"><spring:message code="label.login.login"/></a>
             </div>
            
        </div>
    </div>
    <br>
</div>
<script type="text/javascript" src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>
</body>
</html>