<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="row new-row-width">
    <table class="table table-striped">
        <thead>
        <tr>
           <%--  <th><spring:message code="label.reference"/></th> --%>
            <th><spring:message code="label.name"/></th>
           <%--  <th><spring:message code="label.description"/></th> --%>
            <th><spring:message code="label.value"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderVO.outSourceAttributeVOs}" var="item" varStatus="idx">
            <tr>
                <%-- <td>${item.reference}</td> --%>
                <td>${item.name}</td>
               <%--  <td>${item.description}</td> --%>
                <td>
                	<input type="text" id="outSourceAttributeVOs[${idx.index}].value" name="outSourceAttributeVOs[${idx.index}].value" value="${item.value }"/>
                </td>
            </tr>
<input type="hidden" id="outSourceAttributeVOs[${idx.index}].outSourceAttributeKeyId" name="outSourceAttributeVOs[${idx.index}].outSourceAttributeKeyId" value="${item.outSourceAttributeKeyId }"/>
<input type="hidden" id="outSourceAttributeVOs[${idx.index}].reference" name="outSourceAttributeVOs[${idx.index}].reference" value="${item.reference }"/>
<input type="hidden" id="outSourceAttributeVOs[${idx.index}].name" name="outSourceAttributeVOs[${idx.index}].name" value="${item.name }"/>
<input type="hidden" id="outSourceAttributeVOs[${idx.index}].description" name="outSourceAttributeVOs[${idx.index}].description" value="${item.description }"/>

        </c:forEach>
        </tbody>
    </table>
</div>
