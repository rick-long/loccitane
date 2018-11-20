<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.template"/></label>
    <label class="col-lg-5">
        ${bonusRuleVO.bonusTemplate.name}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
    <label class="col-lg-5">
        ${bonusRuleVO.description}
    </label>
</div>
<div class="row new-row-width">
    <label><spring:message code="lable.bonus.discount.attribute.key"/> </label>
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
        <c:forEach items="${bonusRuleVO.bonusAttributeVOs}" var="item">
            <tr>
                <td>${item.reference}</td>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td>${item.value}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>