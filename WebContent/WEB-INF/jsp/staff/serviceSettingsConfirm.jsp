<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<style>
#confirmStep { width:96%; margin: 0 auto;}

#confirmStep ul#side-menu {
    height: 513px;
    max-height: 1000px;
    overflow-y: scroll;
	padding: 5px 20px 5px 5px;
}

#confirmStep ul#side-menu li {
    padding: 10px 0;
}

#confirmStep ul#side-menu li h5 {
    background: #404040;
    padding: 10px;
    color: #fff;
}

</style>
<ul class="nav" id="side-menu">
   	<li>
    <h5> <spring:message code="left.navbar.product.categorylist"/></h5>
		<ul class="nav nav-second-level">
			<c:forEach items="${cList}" var="c">
	      		<li>--${c.name }</li>
			</c:forEach>
		</ul>
    </li>
  	<li>
		<h5><spring:message code="label.productList"/></h5>
		<ul class="nav nav-second-level">
			<c:forEach items="${pList}" var="p">
	      		<li>--${p.name } <em style="color:#ff7676;">( ${p.category.fullName })</em></li>
			</c:forEach>
		</ul>
     </li>
</ul>

