<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title><spring:message code="label.company.title"/> Spa System - Demo Version 1.0</title>
    <%
        // 所有css / js資源版本控制，每次更新的時候需要修改版本號，保證客戶端使用的css和js都是最新的代碼
        request.setAttribute("version", "20170605");
    %>
     <link href="/resources/img/favicon.ico" rel="icon" type="image/x-icon" />
    <link href='<c:url value="/resources/js/jqueryui/jquery-ui.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-contextMenu/jquery.contextMenu.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/multi-select/multi-select.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/ztree/css/zTreeStyle/zTreeStyle.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-treetable/css/screen.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-treetable/css/jquery.treetable.theme.default.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-treetable/css/jquery.treetable.css"/>?v=${version}' rel="stylesheet">

    <!-- Bootstrap -->
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css"/>?v=${version}' rel="stylesheet">
    <%-- <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.css"/>?v=${version}' rel="stylesheet"> --%>
    <%-- <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.css"/>?v=${version}' rel="stylesheet"> --%>
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.css.map"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.css.map"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/bootstrap-dialog/bootstrap-dialog.css"/>?v=${version}' rel="stylesheet">
    <%-- <link href='<c:url value="/resources/js/boostrap-select/css/bootstrap-select.css"/>?v=${version}' rel="stylesheet"> --%>
    <link href='<c:url value="/resources/js/jquery-loadmask/jquery.loadmask.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/css/metisMenu/metisMenu.min.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/css/base/page.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/homepage/home-page.css"/>?v=${version}' rel="stylesheet">

    <!-- Timeline CSS -->
    <link href='<c:url value="/resources/css/timeline/timeline.css"/>?v=${version}' rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href='<c:url value="/resources/css/morris/morris.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/js/kindeditor/themes/default/default.css"/>?v=${version}' rel="stylesheet"/>
    <link href='<c:url value="/resources/js/kindeditor/plugins/code/prettify.css"/>?v=${version}' rel="stylesheet"/>

    <!-- Custom Fonts -->
    <link href='<c:url value="/resources/css/font-awesome/font-awesome.min.css"/>?v=${version}' rel="stylesheet">

    <!-- bootstrapvalidator-0.4.5 -->
    <link href='<c:url value="/resources/css/bootstrapvalidator-0.4.5/bootstrapValidator.css"/>?v=${version}' rel="stylesheet">
    <!-- jquery.datetimepicker -->
    <%--<link href='<c:url value="/resources/css/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css"/>?v=${version}' rel="stylesheet">--%>
    <link href='<c:url value="/resources/js/boootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/js/bootstrap-submenu/css/bootstrap-submenu.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/boostrap-select/css/bootstrap-select.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/review/css/normalize.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/review/css/style.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/base/responsive.css"/>?v=${version}' rel="stylesheet">
    <script type="text/javascript">
        var globalServletContext = '<%= request.getContextPath() %>';
        console.info(globalServletContext);
    </script>
    <!-- jQuery -->
<!--     <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=Xqf3asDQCVfcSzbeiA0by9iXGy0tkwv7"></script> -->
    <script src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>?v=${version}'></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body class="">
<div class="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">

        <!-- header start -->
        <%@ include file="/WEB-INF/jsp/common/header.jsp" %>
        <!-- header end -->
    </nav>
         <!-- left start -->
        <div class="navbar-default sidebar" role="navigation">
            <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
        </div>
        <!-- /.navbar-static-side -->
        <!-- left end -->

    <!-- right start-->
    <div id="page-wrapper">
        <div id="right-content">

        </div>
    </div>
    <!-- /#page-wrapper -->
    <!-- right end -->

    <!-- footer start -->
    <nav class="navbar navbar-default navbar-static-footer" role="navigation" style="margin-bottom: 0">
        <div class="footer">
            <%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
        </div>
    </nav>
    <!-- footer end -->
</div>

<!-- jQuery UI-->
<script src='<c:url value="/resources/js/jqueryui/jquery-ui.min.js"/>?v=${version}'></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script type="text/javascript">
    $(function(){
        // Resolve name collision between jQuery UI and Twitter Bootstrap
        $.widget.bridge('uitooltip', $.ui.tooltip);
    });
</script>
<!-- Bootstrap Core JavaScript -->
<script src='<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.js"/>?v=${version}'></script>
<%--<script src='<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.min.js"/>?v=${version}'></script>--%>
<script src='<c:url value="/resources/js/bootstrap-dialog/bootstrap-dialog.min.js"/>?v=${version}'></script>
<%-- <script src='<c:url value="/resources/js/boostrap-select/js/bootstrap-select.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/boostrap-select/js/i18n/defaults-en_US.js"/>?v=${version}'></script> --%>

<script src='<c:url value="/resources/js/jquery-contextMenu/jquery.contextMenu.min.js"/>?v=${version}'></script>

<!-- Metis Menu Plugin JavaScript -->
<script src='<c:url value="/resources/js/metisMenu/metisMenu.min.js"/>?v=${version}'></script>
<!-- multi-select -->
<script src='<c:url value="/resources/js/multi-select/jquery.multi-select.js"/>?v=${version}'></script>
<!-- Morris Charts JavaScript -->
<script src='<c:url value="/resources/js/raphael/raphael-min.js"/>?v=${version}'></script>
<%-- <script src="${path}/resources/js/morrisjs/morris.min.js"/></script>
<script src="${path}/resources/js/morrisjs/morris-data.js"/></script> --%>
<!-- home page -->
<script src='<c:url value="/resources/js/homepage/home-page.js"/>?v=${version}'></script>

<link href='<c:url value="/resources/css/base/public.css"/>?v=${version}' rel="stylesheet">

<!-- bootstrapvalidator-0.4.5 -->
<script src='<c:url value="/resources/js/bootstrapvalidator-0.4.5/bootstrapValidator.js"/>?v=${version}'></script>

<!-- jquery.datetimepicker -->
<%--<script src='<c:url value="/resources/js/bootstrap-datetimepicker/bootstrap-datetimepicker.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/bootstrap-datetimepicker/locales/bootstrap-datetimepicker.fr.js"/>?v=${version}'></script>--%>
<script src='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.full.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/boootstrap-datepicker/js/bootstrap-datepicker.min.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/boootstrap-datepicker/locales/bootstrap-datepicker.en-GB.min.js"/>?v=${version}'></script>

<%-- bootstrap-filestyle --%>
<script src='<c:url value="/resources/js/bootstrap-filestyle/bootstrap-filestyle.min.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/jquery-loadmask/jquery.loadmask.js"/>?v=${version}'></script>

<!--ztree -->
<script src='<c:url value="/resources/js/ztree/js/jquery.ztree.all.min.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/kindeditor/kindeditor-all.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/kindeditor/lang/en.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/kindeditor/plugins/code/prettify.js"/>?v=${version}'></script>

<!-- bootstrap modal -->
<%--<script src='<c:url value="/resources/js/base/modal.js"/>?v=${version}'></script>--%>

<script src='<c:url value="/resources/js/jquery-treetable/jquery.treetable.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/boostrap-select/js/bootstrap-select.js"/>?v=${version}'></script>
<!-- common form -->
<script src='<c:url value="/resources/js/base/commonSearchForm.js"/>?v=${version}'></script>

<script type="text/javascript" src='<c:url value="/resources/js/bootstrap-submenu/js/bootstrap-submenu.js"/>?v=${version}'></script>
<%--<script type="text/javascript" src='<c:url value="/resources/js/bootstrap-submenu/js/docs.js"/>?v=${version}'></script>--%>

<!-- dialog -->
<script src='<c:url value="/resources/js/base/common.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/base/dialog.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/base/form-page.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/base/jquery.select-category.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/base/json2.js"/>?v=${version}'></script>
<script src="<c:url value="/resources/review/js/jquery.raty.min.js"/>?v=${version}"></script>

<script src="<c:url value="/resources/js/comment/js/moment.min.js"/>?v=${version}"></script>

<script type="text/javascript">
    // 授权
    function assignPermission($html) {

        //console.info('currentPermission:' + globalPermissions);
        $('[data-permission]', $html).each(function () {
            var $this = $(this);
            $this.hide(); // 全部操作隐藏
            var permission = $this.data('permission');
            //console.info(permission);
            for (var i = globalPermissions.length - 1; i >= 0; i--) {
                if (globalPermissions[i] == permission) {
                    $this.show(); // 有权限，显示出来
                }
            }
        });
        return $html;
    }
    // 初始化permission数组
    var globalPermissions = [];
    var tempPermissions = [];
    $(function () {
        // 解决jquery ui 的datePicker和bootstrap的datePicker的冲突
        $.fn.datePickerBS = $.fn.datepicker.noConflict();

        <c:forEach items="${user.permissionSet}" var="item">
        globalPermissions.push('${item}');
        </c:forEach>
        // 权限细分，把test:list,view细分成：test:list和test:view
        for (var i = globalPermissions.length - 1; i >= 0; i--) {
            var permission = globalPermissions[i];
            var index = permission.indexOf(',');
            var maxLength = permission.length;
            if (index > 0) {
                var colonIndex = permission.lastIndexOf(':');
                tempPermissions.push(permission.substring(0, index));
                if (colonIndex > 0) {
                    tempPermissions.push(permission.substring(0, colonIndex + 1) + permission.substring(index + 1, maxLength));
                } else {
                    tempPermissions.push(permission.substring(index + 1, maxLength));
                }
            } else {
                tempPermissions.push(permission);
            }
        }
        //console.info(tempPermissions);
        globalPermissions = tempPermissions;

        assignPermission($('#side-menu'));
        $('#side-menu').removeClass('hide');
    });

    // 替换html方法, 隐藏所有没有权限的操作
    (function ($, oldHtmlMethod) {
        // Override the core html method in the jQuery object.
        $.fn.html = function () {
            var html = arguments[0];
            if (html) {
                try {
                    // 是dom结构内容
                    var $html = $(html);
                    if ($html.length > 0) {
                        $html = assignPermission($html);
                        return oldHtmlMethod.apply(this, [$html]); // 设置新的html内容
                    }

                } catch (err) {
                    //console.info(err); // 不可执行
                }
                return oldHtmlMethod.apply(this, arguments);
            } else {
                // 获取内容
                // Execute the original HTML method using the
                // augmented arguments collection.
                return oldHtmlMethod.apply(this, arguments);
            }
        };
    })(jQuery, jQuery.fn.html);


    $(function () {
        // 窗口大小改变事件
        $(window).resize(function () {
            var leftMenu = $('#side-menu');
            var selectPage = leftMenu.find('li a.selected');
            if (selectPage.data('need-reload') == true) {
                selectPage.trigger('click');
            } else {
                var parent = getContext();
                $('.page', parent).width(parent.width()); // 计算page的宽度
                var pageList = $('#pageList', parent);
                var prevElement = pageList.prev();
                if (pageList.length == 1 && prevElement.length == 1) {
                    var height = $(window).height() - Math.ceil(prevElement.offset().top) - prevElement.outerHeight() - 80;
                    pageList.height(height);
                }
            }
        });
        
        var loadingUrl = '${loadingUrl}';
        if(!loadingUrl) {
        	loadingUrl = $('#menuContext').find('li.firstMenu a').attr('href');
        }
        
        if(loadingUrl && loadingUrl != '#') {
        	 var pageWrapper = $('#page-wrapper');
     	    pageWrapper.mask('Loading ... ');
             $.ajax({
                 url: loadingUrl,
                 type: "post",
                 dataType: "html",
                 success: function (response) {
                     try {
                         var json = $.parseJSON(response);
                         if (json.statusCode == 401) {
                             Dialog.alert({
                                 title: "Access denied",
                                 message: json.message
                             });
                         } else if (json.statusCode == 301) {
                             Dialog.alert({
                                 title: "Timeout",
                                 message: json.message,
                                 callback: function () {
                                     window.location.href = json.forward;
                                 }
                             });
                         }
                     } catch (err) {
                         $("#right-content").html(response).find(".search-btn").trigger("click");
                         var allMenus = $('#side-menu').find('a[href!=#][class!=dialog]');
     	                for (var i = 0; i < allMenus.length; i++) {
     		                var menu = $(allMenus[i]);
     		                console.info(menu);
     		                if (menu.attr('href') === loadingUrl) {
     			                // 设置选中的菜单
     			                menu.addClass('selected');
     			                var pul = menu.parents('ul');
     			                pul.addClass('in');
     			                pul.parent('li').addClass('active');
     			                break;
     		                }
     	                }
                     }
                 }
             }).always(function () {
     	        pageWrapper.unmask();
             }); 
        }
    });
</script>

</body>
</html>

