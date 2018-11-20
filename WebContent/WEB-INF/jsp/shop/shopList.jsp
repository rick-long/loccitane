<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th class="col-lg-3"><spring:message code="label.name"/></th>
        <th class="col-lg-2"><spring:message code="label.email"/></th>
        <th class="col-lg-2"><spring:message code="label.business.phone"/></th>
        <th class="col-lg-3"><spring:message code="label.address"/></th>
        <th class="col-lg-1"><spring:message code="label.isonline"/></th>
        <th class="col-lg-1"><spring:message code="label.isonline.booking"/></th>
        <%-- <th><spring:message code="label.type"/></th> --%>
        <th class="col-lg-3"><spring:message code="label.remarks"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
            <td>${item.name}</td>
            <td>${item.email}</td>
           	<td align="center">
           		<c:forEach items="${item.phones}" var="phone">
           			${phone.number}
           		</c:forEach>
           	</td>
           	<td>
				<c:forEach items="${item.addresses}" var="addr">
           			${addr.addressExtention}
           		</c:forEach>
			</td>

			<td align="center"><spring:message code="label.${item.isOnline}"/></td>
            <td align="center"><spring:message code="label.${item.showOnlineBooking}"/></td>
			<%-- <td><c:choose>
				<c:when test="${item.outSourceTemplate !=null }">${item.outSourceTemplate.name}</c:when>
				<c:otherwise><spring:message code="label.shop.type.normal"/></c:otherwise>
			</c:choose></td> --%>
            <td>${item.remarks }</td>
            <td>
                <a data-permission="shop:toEdit" href='<c:url value="/shop/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-width="800" data-reload-btn="a.search-btn" data-title='<spring:message code="label.shop.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <a data-permission="shop:toSort" href='<c:url value="/shop/toSort"/>?id=${item.id}' title='<spring:message code="label.button.user.sort"/>' class="btn btn-primary dialog btn-edit" data-width="450" data-reload-btn="a.search-btn" data-title='<spring:message code="label.staff.sort.management"/>'>
                    <i class="glyphicon glyphicon-user"></i>
                </a>
                <a data-permission="shop:toSort" href='<c:url value="/shop/toSortRoom"/>?id=${item.id}' title='<spring:message code="label.button.room.sort"/>' class="btn btn-primary dialog btn-edit" data-width="450" data-reload-btn="a.search-btn" data-title='<spring:message code="label.staff.room.sort.management"/>'>
                    <i class="glyphicon glyphicon-home"></i>
                </a>
                <c:if test="${item.isActive}">
	                <a data-permission="shop:remove" href='<c:url value="/shop/remove"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
	                   <i class="glyphicon glyphicon-trash"></i>
	                </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value='/shop/list' scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->