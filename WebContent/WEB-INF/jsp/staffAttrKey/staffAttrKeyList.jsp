<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover">
	<thead>
		 <tr>
		     <th><spring:message code="label.name"/></th>
		     <th><spring:message code="label.reference"/></th>
		     <th><spring:message code="label.uiType"/></th>
		     <th><spring:message code="label.initOption"/></th>
		     <th><spring:message code="label.default.value"/></th>
		     <th><spring:message code="label.is.for.payroll"/></th>
		     <th><spring:message code="label.isactive"/></th>
		     <th></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}">
                <td>${item.name}</td>
                <td>${item.reference }</td>
                <td>
                	<c:set var="uitype">
                		 ${fn:replace(item.uiType, "-", "")}
                	</c:set>
                	<spring:message code="label.uiType.${uitype}"/>
                </td>
                <td>${item.initOption}</td>
                <td>${item.defaultValue}</td>
                <td><spring:message code="label.${item.isForPayroll}"/></td>
                 <td><spring:message code="label.${item.isActive}"/></td>
                <td>
					<a href='<c:url value="/staffAttrKey/toEdit?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-draggable="true" 
                		data-title='<spring:message code="label.staffAttrKey.edit.management"/>'> <i class="glyphicon glyphicon-edit"></i></a>
                </td>
            </tr>
        </c:forEach>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/staffAttrKey/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->