<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.description"/></th>
        <th><spring:message code="label.redeem.type"/></th>
        <th><spring:message code="label.redeem.channel"/></th>
        <th><spring:message code="label.redeem.required.lp"/></th>
<%--        <th><spring:message code="label.redeem.required.amount"/></th>--%>
        <th><spring:message code="label.award.be.worth"/></th>
        <th><spring:message code="label.start.date"/></th>
        <th><spring:message code="label.end.date"/></th>
        <th><spring:message code="left.navbar.product"/></th>
 <%--       <th><spring:message code="label.redeem.valid.at"/></th>--%>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${item.isActive == 'false'}">deleted_table</c:if>'>
            <td>${item.name}</td>
            <td>${item.description }</td>
            <td><spring:message code="label.redeem.type.${item.redeemType}"/></td>
            <td><spring:message code="label.redeem.channel.${item.redeemChannel}"/></td>
            <td>${item.requiredLp}</td>
   <%--         <td>${item.requiredAmount}</td>--%>
            <td>${item.beWorth}</td>
            <td><fmt:formatDate value="${item.startDate}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${item.endDate}" pattern="yyyy-MM-dd"/></td>
            <td>
            	<c:if test="${item.productOption !=null}">
            		${item.productOption.label3}
            	</c:if>
            </td>
          <%--  <td>
            	${item.validAt}
            </td>--%>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="awardRedemption:toEdit" href='<c:url value="/awardRedemption/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-title='<spring:message code="label.award.redemption.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <a data-permission="awardRedemption:remove" href='<c:url value="/awardRedemption/remove"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
                    <i class="glyphicon glyphicon-trash"></i>
                 </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/awardRedemption/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->