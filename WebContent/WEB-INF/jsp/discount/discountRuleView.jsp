<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
        <label class="col-lg-5">
            <c:forEach items="${discountRule.shops}" var="shop">
                <div>${shop.name}</div>
            </c:forEach>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.template"/></label>
        <label class="col-lg-5">
            ${discountRule.discountTemplate.name}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.start.time"/></label>
        <label class="col-lg-5">
            <fmt:formatDate value="${discountRule.startTime}" pattern="yyyy-MM-dd"/>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.end.time"/></label>
        <label class="col-lg-5">
            <fmt:formatDate value="${discountRule.endTime}" pattern="yyyy-MM-dd"/>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
        <label class="col-lg-5">
            ${discountRule.description}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.category"/></label>
        <label class="col-lg-5">
            <select multiple="multiple" class="selectpicker form-control">
                <c:forEach items="${discountRule.categories}" var="item">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.product"/></label>
        <label class="col-lg-5">
            <select multiple="multiple" class="selectpicker form-control">
                <c:forEach items="${discountRule.products}" var="item">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.user.group"/></label>
        <label class="col-lg-5">
            <select multiple="multiple" class="selectpicker form-control">
                <c:forEach items="${discountRule.userGroups}" var="item">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.loyaltygroup"/> </label>
        <label class="col-lg-5">
            <select multiple="multiple" class="selectpicker form-control">
                <c:forEach items="${discountRule.loyaltyGroups}" var="item">
                    <option>${item.name}</option>
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
            <c:forEach items="${discountRule.discountAttributes}" var="item">
                <tr>
                    <td>${item.discountAttributeKey.reference}</td>
                    <td>${item.discountAttributeKey.name}</td>
                    <td>${item.discountAttributeKey.description}</td>
                    <td>${item.value}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>