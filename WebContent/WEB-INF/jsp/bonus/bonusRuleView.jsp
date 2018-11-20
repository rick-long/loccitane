<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.template"/></label>
        <label class="col-lg-5">
            ${bonusRule.bonusTemplate.name}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
        <label class="col-lg-5">
            ${bonusRule.description}
        </label>
    </div>
    
    <div class="row new-row-width">
        <label><spring:message code="lable.bonus.attribute.key"/> </label>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="label.reference"/></th>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
                <th><spring:message code="label.value"/></th>
            </tr>
            </thead>
            <tbody id="body">
            <c:forEach items="${bonusRule.bonusAttributes}" var="item">
                <tr>
                    <td>${item.bonusAttributeKey.reference}</td>
                    <td>${item.bonusAttributeKey.name}</td>
                    <td>${item.bonusAttributeKey.description}</td>
                    <td>${item.value}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>