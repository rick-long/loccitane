<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
    <style>
        td{
            width:33% !important;
        }
    </style>
<div class="row new-row-width">
    <label><spring:message code="label.staff.pay.mode.attribute"/></label>
    <table class="table table-striped">
        <thead>
        <tr>
            <%-- <th><spring:message code="label.reference"/></th> --%>
            <th><spring:message code="label.name"/></th>
            <th><spring:message code="label.description"/></th>
            <th><spring:message code="label.value"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${staffVO.staffPayrollAttributeVOs}" var="item" varStatus="idx">
            <tr>
                <%-- <td>${item.reference}</td> --%>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td>
                	<span class="totalamount_td"><spring:message code="label.money.currency"/></span> 
                	<input  class="form-control" type="text" id="staffPayrollAttributeVOs[${idx.index}].value" name="staffPayrollAttributeVOs[${idx.index}].value" value="${item.value }"/>
                </td>
            </tr>
<input type="hidden" id="staffPayrollAttributeVOs[${idx.index}].payrollAttributeKeyId" name="staffPayrollAttributeVOs[${idx.index}].payrollAttributeKeyId" value="${item.payrollAttributeKeyId }"/>
<input type="hidden" id="staffPayrollAttributeVOs[${idx.index}].reference" name="staffPayrollAttributeVOs[${idx.index}].reference" value="${item.reference }"/>
<input type="hidden" id="staffPayrollAttributeVOs[${idx.index}].name" name="staffPayrollAttributeVOs[${idx.index}].name" value="${item.name }"/>
<input type="hidden" id="staffPayrollAttributeVOs[${idx.index}].description" name="staffPayrollAttributeVOs[${idx.index}].description" value="${item.description }"/>

        </c:forEach>
        </tbody>
    </table>
</div>
