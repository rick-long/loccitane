<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="row new-row-width">
    <label><spring:message code="label.commission.rule.attribute"/></label>
    <table class="table table-striped">
        <thead>
        <tr>
            <%--<th><spring:message code="label.reference"/></th>--%>
            <th class="col-lg-1"><spring:message code="label.name"/></th>
            <th class="col-lg-2"><spring:message code="label.description"/></th>
            <th class="col-lg-1"><spring:message code="label.value"/> (%)</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${commissionRuleVO.commissionAttributeVOs}" var="item">
            <c:if test="${item.reference eq 'RATE' }">
            <tr>
                <%--<td>${item.reference}</td>--%>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td>
                    <input type="text" class="form-control attributeKeys" data-attribute-key-id="${item.commissionAttributeKeyId}" 
                    	data-reference="${item.reference}" data-name="${item.name}" data-description="${item.description}" value="${item.value}"/>
                </td>
            </tr>
            </c:if>
        </c:forEach>
        <c:forEach items="${commissionRuleVO.commissionAttributeVOs}" var="item">
            <c:if test="${item.reference eq 'EXTRA_RATE' }">
                <tr>
                        <%--<td>${item.reference}</td>--%>
                    <td>${item.name}</td>
                    <td>${item.description}</td>
                    <td>
                        <input type="text" class="form-control attributeKeys" data-attribute-key-id="${item.commissionAttributeKeyId}"
                               data-reference="${item.reference}" data-name="${item.name}" data-description="${item.description}" value="${item.value}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        <c:forEach items="${commissionRuleVO.commissionAttributeVOs}" var="item">
            <c:if test="${item.reference eq 'TARGET_RATE' }">
                <tr>
                        <%--<td>${item.reference}</td>--%>
                    <td>${item.name}</td>
                    <td>${item.description}</td>
                    <td>
                        <input type="text" class="form-control attributeKeys" data-attribute-key-id="${item.commissionAttributeKeyId}"
                               data-reference="${item.reference}" data-name="${item.name}" data-description="${item.description}" value="${item.value}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        <c:forEach items="${commissionRuleVO.commissionAttributeVOs}" var="item">
            <c:if test="${item.reference eq 'TARGET_EXTRA_RATE' }">
                <tr>
                        <%--<td>${item.reference}</td>--%>
                    <td>${item.name}</td>
                    <td>${item.description}</td>
                    <td>
                        <input type="text" class="form-control attributeKeys" data-attribute-key-id="${item.commissionAttributeKeyId}"
                               data-reference="${item.reference}" data-name="${item.name}" data-description="${item.description}" value="${item.value}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>
