<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.template"/></label>
        <label class="col-lg-5">
            ${commissionRule.commissionTemplate.name}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
        <label class="col-lg-5">
            ${commissionRule.description}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.category"/></label>
        <label class="col-lg-5">
            <select multiple="multiple" class="selectpicker form-control">
                <c:forEach items="${commissionRule.categories}" var="item">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.product"/></label>
        <label class="col-lg-5">
            <select multiple="multiple" class="selectpicker form-control">
                <c:forEach items="${commissionRule.products}" var="item">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.user.group"/></label>
        <label class="col-lg-5">
            <select multiple="multiple" class="selectpicker form-control">
                <c:forEach items="${commissionRule.userGroups}" var="item">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </label>
    </div>
    <div class="row new-row-width">
        <label>Commission Attribute Key</label>
        <table class="table table-striped">
            <thead>
            <tr>
                <th class="col-lg-1"><spring:message code="label.name"/></th>
                <th class="col-lg-2"><spring:message code="label.description"/></th>
                <th class="col-lg-1"><spring:message code="label.value"/> (%)</th>
            </tr>
            </thead>
            <tbody id="body">
            <c:forEach items="${commissionRule.commissionAttributes}" var="item">
                <c:if test="${item.commissionAttributeKey.reference eq 'RATE' }">
                    <tr>
                        <td>${item.commissionAttributeKey.name}</td>
                        <td>${item.commissionAttributeKey.description}</td>
                        <td>${item.value}</td>
                    </tr>
                </c:if>
            </c:forEach>
            <c:forEach items="${commissionRule.commissionAttributes}" var="item">
                <c:if test="${item.commissionAttributeKey.reference eq 'EXTRA_RATE' }">
                    <tr>
                        <td>${item.commissionAttributeKey.name}</td>
                        <td>${item.commissionAttributeKey.description}</td>
                        <td>${item.value}</td>
                    </tr>
                </c:if>
            </c:forEach>
            <c:forEach items="${commissionRule.commissionAttributes}" var="item">
                <c:if test="${item.commissionAttributeKey.reference eq 'TARGET_RATE' }">
                    <tr>
                        <td>${item.commissionAttributeKey.name}</td>
                        <td>${item.commissionAttributeKey.description}</td>
                        <td>${item.value}</td>
                    </tr>
                </c:if>
            </c:forEach>
            <c:forEach items="${commissionRule.commissionAttributes}" var="item">
                <c:if test="${item.commissionAttributeKey.reference eq 'TARGET_EXTRA_RATE' }">
                    <tr>
                        <td>${item.commissionAttributeKey.name}</td>
                        <td>${item.commissionAttributeKey.description}</td>
                        <td>${item.value}</td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>