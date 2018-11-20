<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="userlevel-search">
    <h3><spring:message code="label.mkt.mail.shot.search.management"/>
        <a data-permission="marketing:toMktMailShotAdd" href='<c:url value="/marketing/toMktMailShotAdd"/>' class="btn btn-primary dialog" data-draggable="true"
           data-title='<spring:message code="label.mkt.mail.shot.add.management"/>' data-width="750">
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/marketing/mktMailShotList"/>
    <form:form modelAttribute="mktMailShotListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="label.name"/></label>
            <div class="col-lg-5">
                <form:input path="name" id="name" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="label.isactive"/></label>
            <div class="col-lg-5">
                <form:select class="selectpicker form-control" path="isActive" id="isActive">
                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                    <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                    <form:option value="false"><spring:message code="label.option.no"/></form:option>
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                    <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/>
                </a>
            </div>
        </div>
    </form:form>
    <div id="pageList">
    </div>
</div>