<%@ page import="org.spa.model.user.User" %>
<%@ page import="org.spa.utils.WebThreadLocal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<%
    User user = (User) request.getAttribute("user");
    pageContext.setAttribute("user", user);
%>
<!-- chanson 原版 -->
<div class="navbar-header">
    <div class="logo-company"><img src="<c:url value='/resources/img/logo.png'/>"/></div>
    <button type="button" class="navbar-toggle">
        <span class="sr-only"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    
</div>
<script type="text/javascript">
jQuery(document).ready(function(){
    jQuery("button.navbar-toggle").click(function(){
        jQuery(".navbar-collapse, .navbar-default.sidebar").toggleClass("collapsed")
    }); 
});
</script>
<!-- /.navbar-header -->
<div class="language-area"><a href="?language=zh"><img src="<c:url value='/resources/img/zh_CN.png'/>"/></a> | <a href="?language=en"><img src="<c:url value='/resources/img/en_US.png'/>"/></a></div>
<ul class="nav navbar-top-links navbar-right">
    <li class="dropdown">
      <spring:message code="header.welcome"/>, ${user.displayName}. <%--<spring:message code="header.home.shop"/>--%>
    <%--<ul class="dropdown-menu" role="menu">
        <c:forEach items="${user.staffHomeShops}" var="item" varStatus="status">
        <li><c:if test="${not status.first}">  </c:if> <a href="javascript:" class="link_stop">${item.name}</a></li>
        </c:forEach>
    </ul>--%>
    </li>

    <li class="dropdown">
       <%--<spring:message code="header.currentShop"/>--%><spring:message code="header.home.shop"/>
            <select name="currentShopId" id="currentShopId">
                <c:forEach items="${user.staffHomeShops}" var="item">
                    <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                </c:forEach>
           </select>
    </li>

    <!-- /.dropdown -->
    <li class="dropdown" style="color:#000">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="header.logout"/>
            <span class="caret"></span>
        </a>
        <ul class="dropdown-menu" role="menu">
            <li>
				<a href='<c:url value="toChangePassword"/>' class="dialog" data-draggable="true" data-title='<spring:message code="label.change.password"/>'>&nbsp;
                    <span class="glyphicon glyphicon-lock"></span>
                    <spring:message code="label.change.password"/>
                </a>
            </li>
            <%--<li>
                <a href="#">&nbsp;
                    <span class="glyphicon glyphicon-user"></span>
                    My Profile
                </a>
            </li> --%>
            <li class="divider"></li>
            <li>
              <a href='<c:url value="/staffLogout" />' class="logout">&nbsp;<span class="glyphicon glyphicon-off"></span>&nbsp;<spring:message code="header.logout"/></a>
            </li>
        </ul>
    </li>
   <%-- <li class="dropdown">
		 <a href='<c:url value="/staffLogout" />' class="logout">&nbsp;<i class="glyphicon glyphicon-off"></i><spring:message code="header.logout"/></a>

    </li>--%>
    <!-- /.dropdown -->
</ul>
<!-- /.navbar-top-links -->
<script type="text/javascript">
$(document).ready(function () {
    $("#currentShopId").change(function (item) {
    	var currentShopId =$(this).val();
    	$.post('<c:url value="/staff/staffCurrentShopSelect"/>',{shopId:currentShopId});
    });
});
</script>