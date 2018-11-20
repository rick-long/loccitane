<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3>
        <spring:message code="label.bonus.rule.search.management"/>
        <a data-permission="bonus:bonusRuleToAdd" href='<c:url value="/bonus/bonusRuleToAdd"/>' class="btn btn-primary dialog" data-width="680" data-reload-btn="a.search-btn" data-title='<spring:message code="label.bonus.rule.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/bonus/bonusRuleList"/>
    <form:form id="commonSearchForm" modelAttribute="bonusRuleListVO" method="post" class="form-inline" action="${url}">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
            <div class="col-lg-5">
                <form:select id="bonusTemplateId" path="bonusTemplateId" class="selectpicker form-control">
                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                    <c:forEach items="${bonusTemplateList}" var="item">
                        <form:option value="${item.id}">${item.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="label.isactive"/></label>
            <div class="col-lg-5">
                <form:select class="selectpicker form-control" path="isActive" id="isActive">
                    <form:option value=""><spring:message code="label.option.select.single"/></form:option>
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