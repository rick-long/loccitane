<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.template"/></label>
    <label class="col-lg-5">
        ${commissionRuleVO.commissionTemplate.name}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
    <label class="col-lg-5">
        ${commissionRuleVO.description}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.category"/></label>
    <label class="col-lg-5">
        <select multiple="multiple" class="selectpicker form-control">
            <c:forEach items="${commissionRuleVO.categoryVOs}" var="item">
                <option>${item.label}</option>
            </c:forEach>
        </select>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.product"/></label>
    <label class="col-lg-5">
        <select multiple="multiple" class="selectpicker form-control">
            <c:forEach items="${commissionRuleVO.productVOs}" var="item">
                <option>${item.label}</option>
            </c:forEach>
        </select>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.staff.group"/></label>
    <label class="col-lg-5">
        <select multiple="multiple" class="selectpicker form-control">
            <c:forEach items="${commissionRuleVO.userGroupVOs}" var="item">
                <option>${item.label}</option>
            </c:forEach>
        </select>
    </label>
</div>
<div class="row new-row-width">
    <label><spring:message code="label.commission.attribute.key"/></label>
    <table class="table table-striped">
        <thead>
        <tr>
            <th class="col-lg-1><spring:message code="label.name"/></th>
            <th class="col-lg-2><spring:message code="label.description"/></th>
            <th class="col-lg-1><spring:message code="label.value"/></th>
        </tr>
        </thead>
        <tbody id="body">
        <c:forEach items="${commissionRuleVO.commissionAttributeVOs}" var="item">
            <c:if test="${item.reference eq 'RATE' }">
            <tr>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td>${item.value}</td>
            </tr>
            </c:if>
        </c:forEach>
        <c:forEach items="${commissionRuleVO.commissionAttributeVOs}" var="item">
        <c:if test="${item.reference eq 'EXTRA_RATE' }">
        <tr>
            <td>${item.name}</td>
            <td>${item.description}</td>
            <td>${item.value}</td>
        </tr>
        </c:if>
        </c:forEach>
        <c:forEach items="${commissionRuleVO.commissionAttributeVOs}" var="item">
        <c:if test="${item.reference eq 'TARGET_RATE' }">
        <tr>
            <td>${item.name}</td>
            <td>${item.description}</td>
            <td>${item.value}</td>
        </tr>
        </c:if>
        </c:forEach>
        <c:forEach items="${commissionRuleVO.commissionAttributeVOs}" var="item">
        <c:if test="${item.reference eq 'TARGET_EXTRA_RATE' }">
        <tr>
            <td>${item.name}</td>
            <td>${item.description}</td>
            <td>${item.value}</td>
        </tr>
        </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>