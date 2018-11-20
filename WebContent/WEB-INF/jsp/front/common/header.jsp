<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<div class="navbar-header">
    <div class="logo-company">&nbsp;</div>
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href='<c:url value="/"/>'><spring:message code="header.welcome"/>
    	<%-- <sec:authentication property="principal.user.email"/> --%>
    	<sec:authentication property="principal.username"/>
    </a>     
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