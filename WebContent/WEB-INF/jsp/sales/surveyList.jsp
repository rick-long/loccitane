<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped ${nullClass}">
	<thead>
		<tr>
			<th class="col-lg-3"><spring:message code="label.sales.datetime"/> </th>
			<th class="col-lg-3"><spring:message code="label.sales.clientname"/> </th>
			<th class="col-lg-2"><spring:message code="label.sales.status"/> </th>
			<th class="col-lg-4"><spring:message code="label.sales.action"/> </th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${page !=null }">
			<c:forEach items="${page.list}" var="item">
				<tr data-id="${item.id}">
					<td><fmt:formatDate value="${item.orderCompletedDate}" pattern="yyyy-MM-dd"/></td>
					<td>${item.member.fullName}</td>
					<td><spring:message code="label.survey.status.${item.status}"/></td>
					<td>
						<c:if test="${item.status eq 'ACTIVING' }">
                        <a href='<c:url value="/reviewForm/${item.purchaseOrder.reference}"/>' title='<spring:message code="label.button.edit"/>' class="btn btn-primary btn-edit">
							<span class="red-bg"><spring:message code="label.sales.ratingnow"/> </i></span>
						</a>
						<a href='<c:url value="/survey/send?orderSurveyId=${item.id}"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn"
								data-title='<spring:message code="label.dialog.confirm"/>' data-message='Are you sure to send email ?'>
	                     <span class="yellow-bg"> <spring:message code="label.sales.sendemail"/> </span>
	                    </a>
						
						
						<a href='<c:url value="/survey/notSend?orderSurveyId=${item.id}"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn"
								data-title='<spring:message code="label.dialog.confirm"/>' data-message='Are you sure to Do Not send email ?'>
	                      <span class="orange-bg"><spring:message code="label.sales.donotsendemail"/> </span>
	                    </a>
	                    </c:if>
					</td>
				</tr>
			</c:forEach>
          </c:if>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/survey/list" scope="request" />
<c:import url="/WEB-INF/jsp/common/pagination.jsp" />
<!-- page end  -->