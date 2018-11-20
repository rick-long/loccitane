<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<t:layout>
    <div id="bookManagement" class="management">
        <h3><spring:message code="label.book.search.management"/>
            <a href='<c:url value="/front/book/toAdd"/>' target="_blank" class="btn btn-primary" data-width="680" data-reload-btn="a.search-btn" data-title='<spring:message code="label.book.add.management"/>'>
                <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
            </a>
        </h3>
        <c:url var="url" value="/front/book/list"/>
        <form:form id="commonSearchForm" modelAttribute="bookListVO" method="post" class="form-inline" action="${url}">
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="label.status"/></label>
                <div class="col-lg-5">
                    <form:select id="status" path="status" class="selectpicker form-control">
                        <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                        <form:option value="PENDING"><spring:message code="label.book.status.PENDING"/></form:option>
                        <form:option value="CONFIRM"><spring:message code="label.book.status.CONFIRM"/></form:option>
                        <form:option value="CHECKIN_SERVICE"><spring:message code="label.book.status.CHECKIN_SERVICE"/></form:option>
                        <form:option value="COMPLETE"><spring:message code="label.book.status.COMPLETE"/></form:option>
                        <form:option value="NOT_SHOW"><spring:message code="label.book.status.NOT_SHOW"/></form:option>
                        <form:option value="CANCEL"><spring:message code="label.book.status.CANCEL"/></form:option>
                        <form:option value="WAITING"><spring:message code="label.book.status.WAITING"/></form:option>
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

</t:layout>
