<%@ page import="com.spa.constant.CommonConstant" %>
<%@ page import="org.spa.utils.WebThreadLocal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.book.batch.number"/></th>
        <th><spring:message code="label.client"/></th>
        <th><spring:message code="label.shop"/></th>
        <th><spring:message code="label.therapist"/></th>
        <th><spring:message code="label.treatment"/></th>
        <th><spring:message code="label.repeat.start.date"/></th>
        <th><spring:message code="label.repeat.end.date"/></th>
        <th><spring:message code="label.start.time"/></th>
        <th><spring:message code="label.end.time"/></th>
        <th><spring:message code="label.repeat"/></th>
        <th><spring:message code="label.months"/></th>
        <th><spring:message code="label.weeks"/></th>
        <th><spring:message code="label.days"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.batchNumber}</td>
            <td>${item.member.fullName}</td>
            <td>${item.shop.name}</td>
            <td>
            		<c:if test="${item.therapist !=null }">${item.therapist.displayName}</c:if>
            		<c:if test="${item.therapist ==null }"><spring:message code="label.any"/></c:if>
            	</td>
            <td>${item.productOption.label33}</td>
            <td><fmt:formatDate value="${item.startDate}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${item.endDate}" pattern="yyyy-MM-dd"/></td>
            <td>${item.startTime}</td>
            <td>${item.endTime}</td>
            <td><spring:message code="label.book.batch.repeat.${item.repeatType}"/></td>
            <td>${item.months}</td>
            <td>${item.weeks}</td>
            <td>${item.days}</td>
            <td>
                <a data-permission="bookBatch:toEditBookBatch" href='<c:url value="/bookBatch/toEditBookBatch?id=${item.id}"/>' title='<spring:message code="label.book.batch.edit"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
					data-title='<spring:message code="label.book.batch.edit"/>' data-width="880">
					<i class="glyphicon glyphicon-edit"></i>
				</a>
				<a data-permission="bookBatch:removeBookBatch" href='<c:url value="/bookBatch/removeBookBatch?id=${item.id}"/>' title='<spring:message code="label.button.remove"/>' 
					class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
                   <i class="glyphicon glyphicon-trash"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/bookBatch/listBookBatch" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->
