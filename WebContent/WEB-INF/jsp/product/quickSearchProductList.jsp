<%@page import="java.util.List"%>
<%@page import="org.spa.model.user.User"%>
<%@page import="org.spa.utils.WebThreadLocal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<%
List<Long> shopids =WebThreadLocal.getHomeShopIdsByLoginStaff();
 pageContext.setAttribute("shopids", shopids);
%>
<table class="table table-striped table-hover">
	<thead>
		 <tr>
		     <th class="col-lg-2"><spring:message code="label.name"/></th>
		     <th class="col-lg-2"><spring:message code="label.brand"/></th>
		     <th class="col-lg-2"><spring:message code="label.category"/></th>
		     <th class="col-lg-2"><spring:message code="label.description"/></th>
			 <th class="col-lg-2"><spring:message code="label.supernumeraryprice.add.management"/></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
                <td>${item.name}</td>
                <td>${item.brand.name} ttt ${item.category.id}</td>
                <td>
	               ${item.category.fullName }
                </td>
                <td>${item.description }</td>

               	<td>
					<c:if test="${not empty item.productOptions }">
						<c:forEach items="${item.productOptions}" var="po">
							<p>${po.label4}</p>
							<c:if test="${not empty item.productOptions }">
							<c:forEach items="${po.staffProductOptionSupernumeraryPrices}" var="spa">
								<c:if test="${fn:contains(shopids,spa.shop.id)}">
									<samp>${spa.shop.name}:<fmt:formatNumber type="number" pattern="#,#00.0#" value="${spa.additionalPrice + po.originalPrice}"/></samp><br/>
								</c:if>
							</c:forEach>
							</c:if>
							<br/>
						</c:forEach>
					</c:if>

                </td>

            </tr>
        </c:forEach>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/product/quickSearchProductList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->