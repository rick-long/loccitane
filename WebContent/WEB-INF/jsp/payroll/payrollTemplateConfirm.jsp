<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.name"/></label>
    <label class="col-lg-5">
        ${payrollTemplateVO.name}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
    <label class="col-lg-5">
        ${payrollTemplateVO.description}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.content"/></label>
    <label class="col-lg-5">
        ${payrollTemplateVO.content}
    </label>
</div>
<c:if test="${not empty payrollTemplateVO.payrollAttributeKeyVO}">
    <div class="row new-row-width">
        <label><spring:message code="label.discount.attribute.key"/></label>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="label.reference"/></th>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
            </tr>
            </thead>
            <tbody id="body">
            <c:forEach items="${payrollTemplateVO.payrollAttributeKeyVO}" var="item">
                <tr>
                    <td>${item.reference}</td>
                    <td>${item.name}</td>
                    <td>${item.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
