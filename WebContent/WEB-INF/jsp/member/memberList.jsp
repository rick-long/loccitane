<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover">
	<thead>
		 <tr>
		 	 <th class="col-lg-3"><spring:message code="label.home.shop"/></th>
		     <th class="col-lg-1"><spring:message code="label.username"/></th>
		     <th class="col-lg-2"><spring:message code="label.fullName"/></th>
		     <th class="col-lg-2"><spring:message code="label.email"/></th>
		     <th class="col-lg-2"><spring:message code="label.phone"/></th>
		     <th class="col-lg-1"><spring:message code="label.gender"/></th>
		     <th></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}" class='<c:if test="${!item.enabled}">deleted_table</c:if>'>
            	<td>${item.shop.name }</td>
                <td>${item.username}</td>
                <td>${item.fullName }</td>
                <td>${item.email }</td>
                <td>
                	<c:forEach items="${item.phones}" var="phone">

                        <c:if test="${phone.type eq 'MOBILE'}"><b><spring:message code="label.phone.${phone.type}"/></b> : ${phone.number}</br></c:if>

	           		</c:forEach>
                    <c:forEach items="${item.phones}" var="phone">

                      <c:if test="${phone.type eq 'HOME'}"><b><spring:message code="label.phone.${phone.type}"/></b> : ${phone.number}</br></c:if>

                    </c:forEach>
                </td>
                <td align="center">
                	<c:if test="${not empty item.gender}"><spring:message code="label.gender.${item.gender}"/></c:if>
                	<c:if test="${empty item.gender}"><spring:message code="label.book.status."/> </c:if>
                </td>
                <td>
                	<a data-permission="member:toEdit" href='<c:url value="/member/toEdit?id=${item.id}"/>' title='<spring:message code="label.button.member.edit"/>' class="btn btn-primary form-page btn-edit" data-draggable="true"
                	data-title='<spring:message code="label.member.edit.management"/>'> <i class="glyphicon glyphicon-edit"></i> </a>
                	
                	<a data-permission="consentForm:toSign" href='<c:url value="/consentForm/toSign?userId=${item.id}"/>' title='<spring:message code="label.button.sign.consent.form"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                		data-title='<spring:message code="label.button.sign.consent.form"/>'> <i class="glyphicon glyphicon-pencil"></i>
                	</a>
                    <a href='<c:url value="toChangePassword?userId=${item.id}&checkPassword=false"/>' title='<spring:message code="label.button.change.password"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                       data-title='<spring:message code="label.change.password"/>'>
                        <i class="glyphicon glyphicon-lock"></i>
                    </a>
                    <a data-permission="member:singleViewOfClient" href='<c:url value="/member/singleViewOfClient?id=${item.id}&viewTime=3"/>' title='<spring:message code="label.button.view"/>' class="btn btn-primary form-page btn-edit" data-draggable="true"
                		data-title='<spring:message code="label.member.singleviewofclient"/>'> <i class="glyphicon glyphicon-eye-open"></i>
                	</a>
                    <a data-permission="member:toFamilyView" href='<c:url value="/member/toFamilyView?memberId=${item.id}"/>' title='<spring:message code="label.button.family.details"/>' class="loadPage btn btn-primary btn-edit">
                        <i class="glyphicon glyphicon-user"></i>
                    </a>
                    <%--<a data-permission="loyalty:toAdjustLoyaltyLevel" href='<c:url value="/loyalty/toAdjustLoyaltyLevel?memberId=${item.id}"/>' title='<spring:message code="label.button.adjust.ll"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"--%>
                	<%--data-title='ADJUST LOYALTY LEVEL'> <i class="glyphicon glyphicon-plus"></i> </a>--%>
                	
                    	<c:if test="${item.enabled}">
                            <a data-permission="member:remove" href='<c:url value="/member/remove?id=${item.id}"/>' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.remove"/>'>
                                <i class="glyphicon glyphicon-trash"></i>
                            </a>
                        </c:if>
                        <c:if test="${!item.enabled}">
                            <a data-permission="member:enable" href='<c:url value="/member/enable?id=${item.id}"/>' title='<spring:message code="label.button.enable"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.enable"/>'>
                                <i class="glyphicon glyphicon-ok"></i>
                            </a>
                        </c:if>
                </td>
                
            </tr>
        </c:forEach>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/member/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->