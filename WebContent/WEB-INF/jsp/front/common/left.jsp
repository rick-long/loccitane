<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="sidebar-nav navbar-collapse">
    <ul class="nav" id="side-menu">
        <li>
            <a href="<c:url value="/front/book/list"/>">
                <i class="glyphicon glyphicon-calendar"></i> <spring:message code="left.navbar.book"/>
                <span class="fa arrow"></span>
            </a>
        </li>
    </ul>
</div>
