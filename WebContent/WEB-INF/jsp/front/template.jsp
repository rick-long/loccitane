<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/front/common/jspInit.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title><spring:message code="front.label.loccitane.member"/> </title>
    <!-- Bootstrap -->
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/bootstrap-dialog/bootstrap-dialog.css"/>' rel="stylesheet">
    <!-- Custom Fonts -->
    <link href='<c:url value="/resources/css/font-awesome/font-awesome.min.css"/>' rel="stylesheet">

    <!-- bootstrapvalidator-0.4.5 -->
    <link href='<c:url value="/resources/css/bootstrapvalidator-0.4.5/bootstrapValidator.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/base/public.css"/>' rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery -->
    <script src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>
</head>
<body>
<div class="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <!-- header start -->
        <%@ include file="/WEB-INF/jsp/front/common/header.jsp" %>
        <!-- header end -->

        <!-- left start -->
        <div class="navbar-default sidebar" role="navigation">
            <%@ include file="/WEB-INF/jsp/front/common/left.jsp" %>
        </div>
        <!-- /.navbar-static-side -->
        <!-- left end -->
    </nav>
    <!-- right start-->
    <div id="page-wrapper">
        <div id="right-content">
            <jsp:include page="${partial}"/>
        </div>
    </div>
    <!-- /#page-wrapper -->
    <!-- right end -->

    <!-- footer start -->
    <nav class="navbar navbar-default navbar-static-footer" role="navigation" style="margin-bottom: 0">
        <div class="footer">
            <%@ include file="/WEB-INF/jsp/front/common/footer.jsp" %>
        </div>
    </nav>
    <!-- footer end -->
</div>

<script src='<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.min.js"/>'></script>
<script src='<c:url value="/resources/js/bootstrap-dialog/bootstrap-dialog.min.js"/>'></script>
<script src='<c:url value="/resources/js/bootstrapvalidator-0.4.5/bootstrapValidator.js"/>'></script>
<script src='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.full.js"/>'></script>
</body>
</html>

