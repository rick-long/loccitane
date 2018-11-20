<%@ tag language="java" pageEncoding="UTF-8" import="org.spa.utils.TokenUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="title" %>
<%
    // 所有css / js資源版本控制，每次更新的時候需要修改版本號，保證客戶端使用的css和js都是最新的代碼
    request.setAttribute("version", "2017331");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>
        <c:choose>
            <c:when test="${title ne null}">
                ${title}
            </c:when>
            <c:otherwise>
                <spring:message code="label.company.title"/> Spa System - Demo Version 1.0 Member Home Page
            </c:otherwise>
        </c:choose>
    </title>
    <!-- Bootstrap -->
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/bootstrap-dialog/bootstrap-dialog.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/boootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>?v=${version}' rel="stylesheet">

    <!-- Custom Fonts -->
    <link href='<c:url value="/resources/css/font-awesome/font-awesome.min.css"/>?v=${version}' rel="stylesheet">

    <!-- bootstrapvalidator-0.4.5 -->
    <link href='<c:url value="/resources/css/bootstrapvalidator-0.4.5/bootstrapValidator.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/boostrap-select/css/bootstrap-select.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/homepage/home-page.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/base/public.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/front/public_front.css"/>?v=${version}' rel="stylesheet">

    <script type="text/javascript">
        var globalServletContext = '<%= request.getContextPath() %>';
        console.info(globalServletContext);
    </script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery -->
    <script src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>?v=${version}'></script>
    <script src='<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.min.js"/>?v=${version}'></script>
    <script src='<c:url value="/resources/js/bootstrap-dialog/bootstrap-dialog.min.js"/>?v=${version}'></script>
    <script src='<c:url value="/resources/js/bootstrapvalidator-0.4.5/bootstrapValidator.js"/>?v=${version}'></script>
    <script src='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.full.js"/>?v=${version}'></script>
    <script src='<c:url value="/resources/js/boostrap-select/js/bootstrap-select.js"/>?v=${version}'></script>
    <script src='<c:url value="/resources/js/boootstrap-datepicker/js/bootstrap-datepicker.min.js"/>?v=${version}'></script>
    <script src='<c:url value="/resources/js/boootstrap-datepicker/locales/bootstrap-datepicker.en-GB.min.js"/>?v=${version}'></script>

    <script src='<c:url value="/resources/js/base/common.js"/>?v=${version}'></script>
    <script src='<c:url value="/resources/js/base/jquery.select-category.js"/>?v=${version}'></script>
    <script type="text/javascript">
        $(function () {
            // 计算right content的高度
            /*var navbarTop = $('.navbar-static-top');
            var navbarFooter = $('.navbar-static-footer');
            var contentHeight = $(window).height() - navbarTop.offset().top - navbarTop.outerHeight() - navbarFooter.outerHeight();
            $('#right-content').height(contentHeight);*/

            // 解决jquery ui 的datePicker和bootstrap的datePicker的冲突
            $.fn.datePickerBS = $.fn.datepicker.noConflict();
        });
    </script>
</head>
<body>
<div class="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <!-- header start -->
        <%@ include file="header.tag" %>
        <!-- header end -->

        <!-- left start -->
        <div id="left-sidebar-navigation-context" class="navbar-default sidebar" role="navigation">
            <%@ include file="left.tag" %>
        </div>
        <!-- /.navbar-static-side -->
        <!-- left end -->
    </nav>
    <!-- right start-->
    <div id="page-wrapper">
        <div id="right-content">
            <jsp:doBody/>
        </div>
    </div>
    <!-- /#page-wrapper -->
    <!-- right end -->

    <!-- footer start -->
    <nav class="navbar navbar-default navbar-static-footer" role="navigation" style="margin-bottom: 0">
        <div class="footer">
            <p class="text-center">
                Copyright &copy; 2015 &nbsp;
                <a href="http://www.interbiztech.com" target="_blank">INTERBIZTECH</a>
            </p>
        </div>
    </nav>
    <!-- footer end -->

</div>
</body>
</html>

