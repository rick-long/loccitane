<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="userlevel-search">
    <h3><spring:message code="label.mkt.email.template.search.management"/>
        <a data-permission="marketing:toMktEmailTemplateAdd" href='<c:url value="/marketing/toMktEmailTemplateAdd"/>' class="btn btn-primary dialog" data-draggable="true"
           data-title='<spring:message code="label.mkt.email.template.add.management"/>' data-width="750">
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/marketing/mktEmailTemplateList"/>
    <form:form modelAttribute="mktEmailTemplateListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="label.type"/></label>
            <div class="col-lg-5">
                <form:select path="type" id="type" class="selectpicker form-control">
                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                    <form:option value="NEWSLETTER"><spring:message code="label.marketing.email.template.type.NEWSLETTER"/></form:option>
                    <form:option value="BIRTHDAY"><spring:message code="label.marketing.email.template.type.BIRTHDAY"/></form:option>
                    <form:option value="REGISTRATION"><spring:message code="label.marketing.email.template.type.REGISTRATION"/></form:option>
                    <form:option value="THANK_YOU_EMAIL"><spring:message code="label.marketing.email.template.type.THANK_YOU_EMAIL"/></form:option>
                    <form:option value="THANK_YOU_REDEEM"><spring:message code="label.marketing.email.template.type.THANK_YOU_REDEEM"/></form:option>
                    <form:option value="RESET_PASSWORD"><spring:message code="label.marketing.email.template.type.RESET_PASSWORD"/></form:option>
                    <form:option value="LOYALTY_LEVEL_UPGRADE"><spring:message code="label.marketing.email.template.type.LOYALTY_LEVEL_UPGRADE"/></form:option>
                    <form:option value="LOYALTY_LEVEL_DOWNGRADE"><spring:message code="label.marketing.email.template.type.LOYALTY_LEVEL_DOWNGRADE"/></form:option>
                    <form:option value="LOYALTY_LEVEL_EXPIRY"><spring:message code="label.marketing.email.template.type.LOYALTY_LEVEL_EXPIRY"/></form:option>
                    <form:option value="INVENTORY"><spring:message code="label.marketing.email.template.type.INVENTORY"/></form:option>
                    <form:option value="EMAIL_HEADER"><spring:message code="label.marketing.email.template.type.EMAIL_HEADER"/></form:option>
                    <form:option value="EMAIL_END"><spring:message code="label.marketing.email.template.type.EMAIL_END"/></form:option>
                    <form:option value="EMAIL_FOOTER"><spring:message code="label.marketing.email.template.type.EMAIL_FOOTER"/></form:option>
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="label.subject"/></label>
            <div class="col-lg-5">
                <form:input path="subject" id="subject" class="form-control"/>
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