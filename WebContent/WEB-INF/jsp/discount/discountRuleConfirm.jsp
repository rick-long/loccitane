<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
    <label class="col-lg-5">
        <c:forEach items="${discountRuleVO.shopList}" var="item">
            <div>${item.name}</div>
        </c:forEach>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.template"/></label>
    <label class="col-lg-5">
        ${discountRuleVO.discountTemplate.name}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.start.time"/></label>
    <label class="col-lg-5">
        <fmt:formatDate value="${discountRuleVO.startTime}" pattern="yyyy-MM-dd"/>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.end.time"/></label>
    <label class="col-lg-5">
        <fmt:formatDate value="${discountRuleVO.endTime}" pattern="yyyy-MM-dd"/>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
    <label class="col-lg-5">
        ${discountRuleVO.description}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.category"/></label>
    <label class="col-lg-5">
        <select multiple="multiple" class="selectpicker form-control">
            <c:forEach items="${discountRuleVO.categoryVOs}" var="item">
                <option>${item.label}</option>
            </c:forEach>
        </select>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.product"/></label>
    <label class="col-lg-5">
        <select multiple="multiple" class="selectpicker form-control">
            <c:forEach items="${discountRuleVO.productVOs}" var="item">
                <option>${item.label}</option>
            </c:forEach>
        </select>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.user.group"/></label>
    <label class="col-lg-5">
        <select multiple="multiple" class="selectpicker form-control">
            <c:forEach items="${discountRuleVO.userGroupIds}" var="item">
                <option>${item.label}</option>
            </c:forEach>
        </select>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.loyaltygroup"/> </label>
    <label class="col-lg-5">
        <select multiple="multiple" class="selectpicker form-control">
            <c:forEach items="${discountRuleVO.loyaltyGroupIds}" var="item">
                <option>${item.label}</option>
            </c:forEach>
        </select>
    </label>
</div>
<div class="row new-row-width">
    <label><spring:message code="label.discountattributekey"/> </label>
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
        <c:forEach items="${discountRuleVO.discountAttributeVOs}" var="item">
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