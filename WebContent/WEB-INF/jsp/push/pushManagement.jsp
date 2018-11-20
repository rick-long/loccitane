<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="management" id="brand-search">
    <h3><spring:message code="label.notifications"/>
        <a data-permission="push:toAdd" href='<c:url value="/push/toAdd"/>' class="btn btn-primary dialog" data-title='<spring:message code="label.button.add"/>'
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/push/list"/>
    <form:form  id="commonSearchForm" method="post" class="form-inline" action="${url}">
        <div class="row">
            <div class="col-sm-12">
                <div class="col-lg-12 col-sm-6">
                    <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                        <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/></a>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
    <div id="pageList">
    </div>
</div>