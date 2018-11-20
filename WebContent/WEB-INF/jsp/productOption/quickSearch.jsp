<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <c:url var="url" value="/po/quickSearchList"/>
    <form id="commonSearchForm" method="post" class="form-horizontal" action="${url}">
        <input type="hidden" name="supplierId" value="${productOptionListVO.supplierId}"/>
        <input type="hidden" name="category" value="${productOptionListVO.category}"/>
        <div class="form-group">
            <div class="col-sm-5">
                 e="key" id="key" class="form-control" placeholder='<spring:message code="label.name"/>'/>
            </div>
            <a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                <spring:message code="label.button.search"/>
            </a>
        </div>
    </form>
    <div id="pageList"></div>
</div>

