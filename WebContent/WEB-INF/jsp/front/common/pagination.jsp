<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@page import="java.net.URLDecoder" %>

<link href='<c:url value="/resources/css/base/page.css"/>' rel="stylesheet">

<div class="page">
    <input type="hidden" id="curQueryString" value='<%=URLDecoder.decode(request.getAttribute("curQueryString").toString(), "utf-8") %>'/>
    <%-- 上一页 --%>
    <c:if test="${page.pageNumber==1 && page.totalPages!=0}">
        <a class="current prev prevNext" href="javascript:;"><i class="glyphicon glyphicon-chevron-left"></i></a>
    </c:if>
    <c:if test="${page.pageNumber!=1 && page.totalPages!=0}">
        <a class="prevactive" data-page-number="${page.pageNumber-1}" href="javascript:queryPage('${pageUrl}',${page.pageNumber-1});">
            <i class="glyphicon glyphicon-chevron-left"></i></a>
    </c:if>
    <c:set var="flag_front" value="true"/>
    <c:set var="flag_later" value="true"/>
    <div>
        <c:forEach var="pageNo" begin="1" end="${page.totalPages}">
            <c:choose>
                <c:when test="${page.pageNumber == pageNo }">
                    <a class="current active">${pageNo}</a>
                </c:when>

                <c:when test="${page.totalPages <= 7 }">
                    <a class="page-link" data-page-number="${pageNo}" href="javascript:queryPage('${pageUrl}',${pageNo});">${pageNo}</a>
                </c:when>
                <c:when test="${pageNo == 1 }">
                    <a class="page-link" data-page-number="${pageNo}" href="javascript:queryPage('${pageUrl}',${pageNo});">${pageNo}</a>
                </c:when>
                <c:when test="${(page.pageNumber ==5) && (pageNo == 2)}">
                    <a class="page-link" data-page-number="${pageNo}" href="javascript:queryPage('${pageUrl}',${pageNo});">${pageNo}</a>
                </c:when>
                <c:when test="${page.pageNumber >=6 && page.totalPages >7 && pageNo == 2 && flag_front}">
                    <span class="ellipse">…</span>
                </c:when>
                <c:when test="${(page.pageNumber == 1  || page.pageNumber == 2 || page.pageNumber == 3) &&(pageSize == 4 || pageSize == 5)}">
                    <a class="page-link" data-page-number="${pageNo}" href="javascript:queryPage('${pageUrl}',${pageNo});">${pageNo}</a>
                </c:when>
                <c:when test="${pageNum - page.pageNumber < 3 && page.pageNumber - pageNo < 3}">
                    <a class="page-link" data-page-number="${pageNo}" href="javascript:queryPage('${pageUrl}',${pageNo});">${pageNo}</a>
                </c:when>
                <c:when test="${(page.pageNumber == page.totalPages || page.pageNumber ==  page.totalPages - 1) &&(pageNo ==page.totalPages - 3 || pageNo == page.totalPages - 4)}">
                    <a class="page-link" data-page-number="${pageSize}" href="javascript:queryPage('${pageUrl}',${pageSize});">${pageSize}</a>
                </c:when>
                <c:when test="${page.totalPages - page.pageNumber ==4 && page.totalPages - pageNo == 1}">
                    <a class="page-link" data-page-number="${pageNo}" href="javascript:queryPage('${pageUrl}',${pageNo});">${pageNo}</a>
                </c:when>
                <c:when test="${page.totalPages - pageNo == 1 && page.totalPages >7 && pageNo != page.totalPages && flag_later}">
                    <span class="ellipse">…</span>
                </c:when>
                <c:when test="${pageNo == page.totalPages}">
                    <a class="page-link" data-page-number="${pageNo}" href="javascript:queryPage('${pageUrl}',${pageNo});">${pageNo}</a>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>

    <%-- 下一页 --%>
    <c:if test="${page.pageNumber==page.totalPages && page.totalPages!=0}">
        <a class="current next prevNext" href="javascript:;"><i class="glyphicon glyphicon-chevron-right"></i></a>
    </c:if>
    <c:if test="${page.pageNumber!=page.totalPages && page.totalPages!=0}">
        <a class="current nextactive" href="javascript:queryPage('${pageUrl}',${page.pageNumber+1});">
            <i class="glyphicon glyphicon-chevron-right"></i></a>
    </c:if>
</div>
<!-- page -->
