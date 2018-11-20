<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="row new-row-width">
    <label><spring:message code="label.discount.rule.attribute"/> </label>
    <table class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="label.reference"/></th>
            <th><spring:message code="label.name"/></th>
            <th><spring:message code="label.description"/></th>
            <th><spring:message code="label.value"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${discountRuleVO.discountAttributeVOs}" var="item">
            <tr>
                <td>${item.reference}</td>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td>
                    <input type="text" class="form-control attributeKeys" data-attribute-key-id="${item.discountAttributeKeyId}" data-reference="${item.reference}" data-name="${item.name}" data-description="${item.description}" value="${item.value}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
