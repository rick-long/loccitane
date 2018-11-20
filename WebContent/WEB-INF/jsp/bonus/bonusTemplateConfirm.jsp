<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.name"/></label>
    <label class="col-lg-5">
        ${bonusTemplateVO.name}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
    <label class="col-lg-5">
        ${bonusTemplateVO.description}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.content"/></label>
    <label class="col-lg-5">
        ${bonusTemplateVO.content}
    </label>
</div>
<c:if test="${not empty bonusTemplateVO.bonusAttributeKeyVO}">
    <div class="row new-row-width">
        <label>Discount Attribute Key</label>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="label.reference"/></th>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
            </tr>
            </thead>
            <tbody id="body">
            <c:forEach items="${bonusTemplateVO.bonusAttributeKeyVO}" var="item">
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
