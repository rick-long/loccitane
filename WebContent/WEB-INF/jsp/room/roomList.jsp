<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover">
	<thead>
		 <tr>
             <th class="col-lg-3"><spring:message code="label.shop"/></th>
		     <th class="col-lg-2"><spring:message code="label.name"/></th>
             <th class="col-lg-1"><spring:message code="label.capacity"/></th>
             <th class="col-lg-1"><spring:message code="label.displayorder"/></th>
            <%-- <th class="col-lg-3"><spring:message code="label.room.treatment.under.room"/></th>--%>
		     <th class="col-lg-4"><spring:message code="label.remarks"/></th>
		     <th></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}" class='<c:if test="${item.isActive == 'false'}">deleted_table</c:if>'>
                <td>${item.shop.name}</td>
                <td>${item.name}</td>
                <td align="center">${item.capacity}</td>
                <td align="center">${item.sort}</td>
                <%--<td>
                	<c:choose>
	            		<c:when test="${item.roomTreatmentses !=null && item.roomTreatmentses.size() >1 }">
	            		<ul class="nav">
		            		<li class="dropdown">
						        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Multi Treatments
						            <span class="caret"></span>
						        </a>
						        <ul class="dropdown-menu" role="menu">
						        	<c:forEach var="rt" items="${item.roomTreatmentses}">
							            <li>
							               	<c:if test="${rt.category !=null}">
			                					${rt.category.fullName}</br>
			                				</c:if>
			                				<c:if test="${rt.product !=null}">
			                					${rt.product.name}</br>
			                				</c:if>
							            </li>
						            </c:forEach>
						        </ul>
						    </li>
	            		</ul>
	            		</c:when>
            		</c:choose>
                	
                </td>--%>
                <td>${item.remarks }</td>
                <td>
                    
                    <c:if test="${item.isActive == 'true'}">
                    	<a data-permission="room:toEdit" href='<c:url value="/room/toEdit"/>?id=${item.id}' title='<spring:message code="label.room.edit.management"/>' class="btn btn-primary dialog btn-edit" data-title='<spring:message code="label.room.edit.management"/>'>
	                        <i class="glyphicon glyphicon-edit"></i>
	                    </a>
	                    <a data-permission="room:remove" href='<c:url value="/room/remove?id=${item.id}"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" title='<spring:message code="label.button.remove"/>' data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.remove"/>'>
	                         <i class="glyphicon glyphicon-trash"></i>
	                    </a>
	                    
                    </c:if>
                    <c:if test="${item.isActive == 'false'}">
	                    <a data-permission="room:enabled" href='<c:url value="/room/enabled?id=${item.id}"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" title='<spring:message code="label.button.enable"/>' data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.enable"/>'>
	                         <i class="glyphicon glyphicon-ok"></i>
	                    </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/room/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->