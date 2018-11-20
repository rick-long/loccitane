<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th class="col-lg-2"><spring:message code="label.template.name"/></th>
        <th class="col-lg-3"><spring:message code="label.description"/></th>
        <th class="col-lg-2"><spring:message code="label.attributes"/></th>
        <th><spring:message code="label.commission.no.of.categories"/></th>
        <th><spring:message code="label.commission.no.of.products"/></th>
        <th><spring:message code="label.staff.group"/></th>
        <%--<th><spring:message code="label.isactive"/></th>--%>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
            <td>${item.commissionTemplate.name}</td>
            <td>${item.description}</td>
            <td>
            	<c:if test="${! empty item.commissionAttributes }">
            		<c:forEach items="${item.commissionAttributes}" var="ca">
                        <c:if test="${ca.commissionAttributeKey.reference eq 'RATE' }">${ca.commissionAttributeKey.name }: ${ca.value } %</br></c:if>
            		</c:forEach>
                    <c:forEach items="${item.commissionAttributes}" var="ca">
                        <c:if test="${ca.commissionAttributeKey.reference eq 'EXTRA_RATE' }">${ca.commissionAttributeKey.name }: ${ca.value } %</br></c:if>
                    </c:forEach>
                    <c:forEach items="${item.commissionAttributes}" var="ca">
                        <c:if test="${ca.commissionAttributeKey.reference eq 'TARGET_RATE' }">${ca.commissionAttributeKey.name }: ${ca.value } %</br></c:if>
                    </c:forEach>
                    <c:forEach items="${item.commissionAttributes}" var="ca">
                        <c:if test="${ca.commissionAttributeKey.reference eq 'TARGET_EXTRA_RATE' }">${ca.commissionAttributeKey.name }: ${ca.value } %</br></c:if>
                    </c:forEach>
            	</c:if>
            </td>
            <td align="center">
            	<c:if test="${! empty item.categories }">
            		${item.categories.size()}
            	</c:if>
            </td>
            <td align="center">
            	<c:if test="${! empty item.products }">
            		${item.products.size()}
            	</c:if>
            </td>
            <td>
            	<c:if test="${! empty item.userGroups }">
            		<c:forEach items="${item.userGroups}" var="ug">
            			${ug.name}
            		</c:forEach>
            	</c:if>
            </td>
           	<%--<td><spring:message code="label.${item.isActive}"/></td>--%>
            <td>
                <a data-permission="commission:commissionRuleView" href='<c:url value="/commission/commissionRuleView?id=${item.id}"/>' title='<spring:message code="label.commission.rule.view"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.commission.rule.view"/>'>
                    <i class="glyphicon glyphicon-eye-open"></i>
                </a>
                <a data-permission="commission:commissionRuleToEdit" href='<c:url value="/commission/commissionRuleToEdit?id=${item.id}"/>' title='<spring:message code="label.commission.rule.edit"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.commission.rule.edit"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <c:if test="${item.isActive}">
	                <a data-permission="commission:commissionRuleRemove" href='<c:url value="/commission/commissionRuleRemove"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
	                   <i class="glyphicon glyphicon-trash"></i>
	                </a>
	            </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/commission/commissionRuleList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->