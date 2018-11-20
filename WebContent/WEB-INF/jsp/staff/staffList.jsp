<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover">
	<thead>
		 <tr>
		 	<th><spring:message code="label.home.shop"/></th>
		 	 <th><spring:message code="label.staff.role.name"/></th>
		     <th><spring:message code="label.staff.login.account"/></th>
		     <%-- <th><spring:message code="label.fullName"/></th> --%>
		     <th><spring:message code="label.display.name"/></th>
		     <th><spring:message code="label.mobile.phone"/></th>
		     <th class="col-lg-2"><spring:message code="label.email"/></th>
		     <%--<th><spring:message code="label.gender"/></th>--%>
		     <%--<th><spring:message code="label.dateofbirth"/></th>--%>
		     <%--<th><spring:message code="label.join.date"/></th>--%>
		     <!-- <th>Payroll Attributes</th> -->
		     <th><spring:message code="label.staff.support.online.booking"/></th>
		     <th></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}" class='<c:if test="${!item.enabled}">deleted_table</c:if>'>
            	<td>
            		<c:choose>
	            		<c:when test="${item.staffHomeShops.size() >1 }">
	            		<ul class="nav">
		            		<li class="dropdown">
						        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="label.staff.multi.shops"/>
						            <span class="caret"></span>
						        </a>
						        <ul class="dropdown-menu" role="menu">
						        	<c:forEach var="homeShop" items="${item.staffHomeShops}">
							            <li>
							                ${homeShop.name}
							            </li>
						            </c:forEach>
						        </ul>
						    </li>
	            		</ul>
	            		</c:when>
	            		<c:otherwise>
		            		<c:forEach var="homeShop" items="${item.staffHomeShops}">
		            			${homeShop.name}
		            		</c:forEach>
	            		</c:otherwise>
            		</c:choose>
            	</td>
            	<td>
            		${item.firstRoleForStaff.name}
                </td>
                <td>${item.username}</td>
                <%-- <td>${item.fullName}</td> --%>
                <td>${item.displayName }</td>
                <td>${item.mobilePhone }</td>
                <td>${item.email }</td>
                <%--<td>--%>
                	<%--<c:if test="${item.gender ==null}">--%>
                		<%--N/A--%>
                	<%--</c:if>--%>
                	<%--<c:if test="${item.gender !=null}">--%>
                		<%--<spring:message code="label.gender.${item.gender}"/>--%>
                	<%--</c:if>--%>
                	<%----%>
                <%--</td>--%>
                <%--<td><fmt:formatDate value="${item.dateOfBirth}" pattern="yyyy-MM-dd"/></td>--%>
                <%--<td><fmt:formatDate value="${item.joinDate}" pattern="yyyy-MM-dd"/></td>--%>
                <td align="center"><spring:message code="label.${item.showOnApps}"/></td>
                <td>
                	<a data-permission="staff:toEdit" href='<c:url value="/staff/toEdit?id=${item.id}"/>' class="btn btn-primary form-page btn-edit" title='<spring:message code="label.button.edit"/>' data-draggable="true"
                		data-title='<spring:message code="label.staff.edit.management"/>'> <i class="glyphicon glyphicon-edit"></i>
                	</a>
                    <a data-permission="staff:toSchedule" href='<c:url value="/staff/toSchedule?id=${item.id}"/>' class="btn btn-primary form-page btn-edit" title='<spring:message code="label.staff.schedule.settings"/>'>
                        <i class="glyphicon glyphicon-calendar"></i>
                    </a>
                    <a data-permission="staff:toServiceSettings" href='<c:url value="/staff/toServiceSettings?staffId=${item.id}"/>' class="btn btn-primary form-page btn-edit" title='<spring:message code="label.staff.service.settings"/>'>
                        <i class="glyphicon glyphicon-cog"></i>
                    </a>
                    <a data-permission="staff:toChangePassword" href='<c:url value="toChangePassword?userId=${item.id}&checkPassword=false"/>' title='<spring:message code="label.change.password"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                       data-title='<spring:message code="label.change.password"/>'>
                        <i class="glyphicon glyphicon-lock"></i>
                    </a>
                    <c:if test="${item.enabled}">
                            <a data-permission="staff:remove" href='<c:url value="/staff/remove?id=${item.id}"/>' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.remove"/>'>
                                <i class="glyphicon glyphicon-trash"></i>
                            </a>
                   </c:if>
                   <c:if test="${!item.enabled}">
                            <a data-permission="staff:enable" href='<c:url value="/staff/enable?id=${item.id}"/>' title='<spring:message code="label.button.enabled"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.enable"/>'>
                                <i class="glyphicon glyphicon-ok"></i>
                            </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/staff/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->