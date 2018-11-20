<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.name"/></label>
    <label class="col-lg-5">
        ${discountTemplateVO.name}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
    <label class="col-lg-5">
        ${discountTemplateVO.description}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.content"/></label>
    <label class="col-lg-5">
        ${discountTemplateVO.content}
    </label>
</div>
<c:if test="${not empty discountTemplateVO.discountAttributeKeyVO}">
    <div class="row new-row-width">
        <label><spring:message code="label.discountattributekey"/> </label>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="label.reference"/></th>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
            </tr>
            </thead>
            <tbody id="body">
            <c:forEach items="${discountTemplateVO.discountAttributeKeyVO}" var="item">
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
