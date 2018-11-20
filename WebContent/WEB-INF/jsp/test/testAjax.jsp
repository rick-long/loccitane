<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title><spring:message code="label.company.title"/></title>

    <link href='<c:url value="/resources/js/jqueryui/jquery-ui.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-contextMenu/jquery.contextMenu.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/multi-select/multi-select.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/js/ztree/css/zTreeStyle/zTreeStyle.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-treetable/css/screen.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-treetable/css/jquery.treetable.theme.default.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-treetable/css/jquery.treetable.css"/>' rel="stylesheet">

    <!-- Bootstrap -->
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css"/>' rel="stylesheet">
    <%-- <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.css"/>' rel="stylesheet"> --%>
    <%-- <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.css"/>' rel="stylesheet"> --%>
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.css.map"/>' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.css.map"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/bootstrap-dialog/bootstrap-dialog.css"/>' rel="stylesheet">
    <%-- <link href='<c:url value="/resources/js/boostrap-select/css/bootstrap-select.css"/>' rel="stylesheet"> --%>
    <link href='<c:url value="/resources/js/jquery-loadmask/jquery.loadmask.css"/>' rel="stylesheet">

    <link href='<c:url value="/resources/css/metisMenu/metisMenu.min.css"/>' rel="stylesheet">

    <link href='<c:url value="/resources/css/homepage/home-page.css"/>' rel="stylesheet">

    <!-- Timeline CSS -->
    <link href='<c:url value="/resources/css/timeline/timeline.css"/>' rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href='<c:url value="/resources/css/morris/morris.css"/>' rel="stylesheet">

    <link href='<c:url value="/resources/js/kindeditor/themes/default/default.css"/>' rel="stylesheet"/>
    <link href='<c:url value="/resources/js/kindeditor/plugins/code/prettify.css"/>' rel="stylesheet"/>

    <!-- Custom Fonts -->
    <link href='<c:url value="/resources/css/font-awesome/font-awesome.min.css"/>' rel="stylesheet">

    <!-- bootstrapvalidator-0.4.5 -->
    <link href='<c:url value="/resources/css/bootstrapvalidator-0.4.5/bootstrapValidator.css"/>' rel="stylesheet">
    <!-- jquery.datetimepicker -->
    <%--<link href='<c:url value="/resources/css/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css"/>' rel="stylesheet">--%>
    <link href='<c:url value="/resources/js/boootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>' rel="stylesheet">

    <link href='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.css"/>' rel="stylesheet">

    <link href='<c:url value="/resources/js/bootstrap-submenu/css/bootstrap-submenu.min.css"/>' rel="stylesheet">

    <!-- jQuery -->
    <script src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>'></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body class="">
test Ajaxvvvv

<div id="content"></div>

register btn: <button id="registerAjaxLoad">register ajax load</button>
btn:<button id="ajaxLoad">ajaxLoad</button>
<!-- jQuery UI-->
<script src='<c:url value="/resources/js/jqueryui/jquery-ui.min.js"/>'></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script type="text/javascript">
    $(function(){
        // Resolve name collision between jQuery UI and Twitter Bootstrap
        $.widget.bridge('uitooltip', $.ui.tooltip);
    });
</script>
<!-- Bootstrap Core JavaScript -->
<script src='<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.js"/>'></script>
<%--<script src='<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.min.js"/>'></script>--%>
<script src='<c:url value="/resources/js/bootstrap-dialog/bootstrap-dialog.min.js"/>'></script>
<%-- <script src='<c:url value="/resources/js/boostrap-select/js/bootstrap-select.js"/>'></script>
<script src='<c:url value="/resources/js/boostrap-select/js/i18n/defaults-en_US.js"/>'></script> --%>

<script src='<c:url value="/resources/js/jquery-contextMenu/jquery.contextMenu.min.js"/>'></script>

<!-- Metis Menu Plugin JavaScript -->
<script src='<c:url value="/resources/js/metisMenu/metisMenu.min.js"/>'></script>
<!-- multi-select -->
<script src='<c:url value="/resources/js/multi-select/jquery.multi-select.js"/>'></script>
<!-- Morris Charts JavaScript -->
<script src='<c:url value="/resources/js/raphael/raphael-min.js"/>'></script>
<%-- <script src="${path}/resources/js/morrisjs/morris.min.js"/></script>
<script src="${path}/resources/js/morrisjs/morris-data.js"/></script> --%>
<!-- home page -->
<script src='<c:url value="/resources/js/homepage/home-page.js"/>'></script>

<link href='<c:url value="/resources/css/base/public.css"/>' rel="stylesheet">

<!-- bootstrapvalidator-0.4.5 -->
<script src='<c:url value="/resources/js/bootstrapvalidator-0.4.5/bootstrapValidator.js"/>'></script>

<!-- jquery.datetimepicker -->
<%--<script src='<c:url value="/resources/js/bootstrap-datetimepicker/bootstrap-datetimepicker.js"/>'></script>
<script src='<c:url value="/resources/js/bootstrap-datetimepicker/locales/bootstrap-datetimepicker.fr.js"/>'></script>--%>
<script src='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.full.js"/>'></script>

<script src='<c:url value="/resources/js/boootstrap-datepicker/js/bootstrap-datepicker.min.js"/>'></script>
<script src='<c:url value="/resources/js/boootstrap-datepicker/locales/bootstrap-datepicker.en-GB.min.js"/>'></script>

<%-- bootstrap-filestyle --%>
<script src='<c:url value="/resources/js/bootstrap-filestyle/bootstrap-filestyle.min.js"/>'></script>

<script src='<c:url value="/resources/js/jquery-loadmask/jquery.loadmask.js"/>'></script>

<!--ztree -->
<script src='<c:url value="/resources/js/ztree/js/jquery.ztree.all.min.js"/>'></script>


<script src='<c:url value="/resources/js/kindeditor/kindeditor-all.js"/>'></script>
<script src='<c:url value="/resources/js/kindeditor/lang/en.js"/>'></script>
<script src='<c:url value="/resources/js/kindeditor/plugins/code/prettify.js"/>'></script>

<!-- bootstrap modal -->
<%--<script src='<c:url value="/resources/js/base/modal.js"/>'></script>--%>

<script src='<c:url value="/resources/js/jquery-treetable/jquery.treetable.js"/>'></script>

<!-- common form -->
<script src='<c:url value="/resources/js/base/commonSearchForm.js"/>'></script>

<script type="text/javascript" src='<c:url value="/resources/js/bootstrap-submenu/js/bootstrap-submenu.js"/>'></script>
<%--<script type="text/javascript" src='<c:url value="/resources/js/bootstrap-submenu/js/docs.js"/>'></script>--%>

<!-- dialog -->
<script src='<c:url value="/resources/js/base/dialog.js"/>'></script>

<script src='<c:url value="/resources/js/base/jquery.select-category.js"/>'></script>

<script src='<c:url value="/resources/js/base/json2.js"/>'></script>

<script src='<c:url value="/resources/js/base/page.js"/>'></script>

<script type="text/javascript">
    $(function(){

       $("#registerAjaxLoad").click(function(){
           $('body').on('click',"#ajaxLoad", function(){
               $("#content").load('<c:url value="/test/testContent"/>');
           });
       });

        console.info("test");
    });

    /*$(function(){
        console.info('ccc');
        $("#content").load('<c:url value="/test/testContent"/>');
    });*/

</script>

</body>
</html>

