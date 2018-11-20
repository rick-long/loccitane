<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
    background: rgba(255,255,255,0.6);
}

        input[type="text"], input[type="email"], input[type="password"] {
            display: inline-block;
            padding: 8px 3px;
            width:100% !important;
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
    cursor: pointer;
    background: #ffcb00!important;
    border: 1px solid #ffcb00;
    border-radius: 0;
    line-height: 30px;
    width: 100%;
    color: #001022;
	
        }
        .error {
               height: auto;
    background: none;
    padding: 3px;
    width: 100%;
    text-align: center;
    margin: 10px 0;
    float: left;
    color: #ff0000;
        }

        .login_logo h2{ color:#fff;}
		
         .login_box .form-group {
    margin-top: 15px;
    padding: 0 0 7px 0;
}

.main_box input[type="text"], .main_box input[type="email"], .main_box input[type="password"] {
    display: inline-block;
    border-radius: 3px;
    padding: 5px 15px;
    color: #747f8c;
    width:100%!important;
    box-sizing: border-box;
    border: 1px solid #A4A6B5;
    border-radius:0;
    background: transparent;
    margin-top: 5px;
}

.main_box .login_box .form-group img {
    margin-top: 6px;
    height: 24px;
    vertical-align: top;
}

    </style>
</head>
<body>
<div class="main_box">
    <div class="login_box">
        <div class="login_logo input_boder">
            <h2> <spring:message code="label.login.forgot.password"/></h2>
        </div>
        <div class="login_form">
            <form id="memberForgetPasswordForm" action='<c:url value="/memberForgetPassword"/>' method="post">
                <input type="hidden" name="form_token" value="${TokenUtil.get(pageContext)}"/>

                <div class="form-group input_boder">
                   <input id="email" value="" name="email" type="text" class="form-control x319 in" autocomplete="off" placeholder="<spring:message code="label.email"/>">
                </div>
                <div class="form-group space">
                    <label class="t"></label>
                    <input type="submit" id="submitBtn" value="&nbsp;<spring:message code="label.button.send"/>&nbsp;" class="btn btn-primary btn-lg">
                </div>
            </form>
        </div>
    </div>
    <br>
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