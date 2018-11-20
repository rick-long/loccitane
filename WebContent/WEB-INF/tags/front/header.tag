<%@tag pageEncoding="UTF-8" %>
<%@ tag import="org.spa.model.user.User" %>
<%@ tag import="org.spa.utils.WebThreadLocal" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    User user = WebThreadLocal.getUser();
    jspContext.setAttribute("user", user);
%>
<div class="navbar-header">
    <div class="logo-company">&nbsp;</div>
    <a href="?language=zh"><spring:message code="header.changelang.zh"/></a> | <a href="?language=en"><spring:message code="header.changelang.en"/></a>
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#left-sidebar-navigation-context">
        <span class="sr-only"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href='<c:url value="/"/>'><spring:message code="header.welcome"/>
        <%-- <sec:authentication property="principal.user.email"/> --%>
        <sec:authentication property="principal.username"/>
    </a>
   
    	<c:if test="${user.currentLoyaltyLevel.rank == 1 }">
    		<%-- <c:if test="${user.email eq 'ivy@imanagesystems.com' }"> --%>
	    		<div class="add_to_wallet"><a href="<c:url value="/front/download/passbook?username="/>${user.email}">Add To Apple Wallet</a></div>
	    	<%-- </c:if> --%>
	    </c:if>
</div>
<!-- /.navbar-header -->
<ul class="nav navbar-top-links navbar-right">
    <%--  <li class="dropdown">
          <div class="changeLang">
                <spring:message code="header.changelang"/>
                <select id="langType" onchange="javascript:langTypeChange('<c:url value="/lang"/>',this.options[this.options.selectedIndex].value);">
                    <option value="en" <c:if test="${localeCode == 'en_US'}">selected</c:if>><spring:message code="header.changelang.en"/></option>
                    <option value="zh"<c:if test="${localeCode == 'zh_CN'}">selected</c:if>><spring:message code="header.changelang.zh"/></option>
                    <option value="ch"<c:if test="${localeCode == 'ch_CN'}">selected</c:if>><spring:message code="header.changelang.ch"/></option>
                </select>
         </div>
         <!-- /.dropdown-messages -->
     </li> --%>
    <!-- /.dropdown -->
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Hello ${user.fullName}</a>
        </li>
    <li class="dropdown">
        <a href='<c:url value="/memberLogout" />' class="logout">&nbsp;<i class="glyphicon glyphicon-off"></i><spring:message code="header.logout"/></a>
        <!-- /.dropdown-messages -->
    </li>
    <!-- /.dropdown -->
</ul>
<!-- /.navbar-top-links -->
<script type="text/javascript">

    function langTypeChange(url,val){
        window.location.href=url+'?langType='+val;
    }
</script>