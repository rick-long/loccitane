<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.name"/></label>
        <label class="col-lg-5">
            ${bonusTemplate.name}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
        <label class="col-lg-5">
            ${bonusTemplate.description}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.content"/></label>
        <label class="col-lg-5">
            ${discountTemplate.content}
        </label>
    </div>
    <div class="row new-row-width">
        <table class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="label.reference"/></th>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
            </tr>
            </thead>
            <tbody id="body">
            <c:forEach items="${bonusTemplate.bonusAttributeKeys}" var="item">
                <tr>
                    <td>${item.reference}</td>
                    <td>${item.name}</td>
                    <td>${item.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>