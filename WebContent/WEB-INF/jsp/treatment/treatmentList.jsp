<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover" id="bookItems">
	<thead>
		 <tr>
		     <th><spring:message code="label.name"/></th>
		     <th><spring:message code="label.category"/></th>
		     <th><spring:message code="label.product.options.or.treatment.code"/></th>
		     <th><spring:message code="label.supernumeraryprice.add.management"/></th>
			 <th class="text-center"><spring:message code="label.treatment.shops.availability"/></th>
			 <th class="text-center"><spring:message code="label.show.online"/></th>
		     <th></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
                <td>${item.name}</td>
                <td>
	               ${item.category.fullName }
                </td>
                <td>
                	<c:if test="${not empty item.productOptions }">
	                	<c:forEach items="${item.productOptions}" var="po">
							<p>${po.label4}</p>
                		</c:forEach>
					</c:if>
	               	<c:if test="${empty item.productOptions }">
                		<p>	N/A<p>
                	</c:if>
                </td>
               	<td align="center">
                	<c:if test="${not empty item.productOptions }">
	                	<c:forEach items="${item.productOptions}" var="po">
					    <a data-permission="po:toAddSupernumeraryPrice" href='<c:url value="/po/toAddSupernumeraryPrice"/>?productOptionId=${po.id}' class="btn btn-primary dialog lo-m-r-15" data-title='<spring:message code="label.supernumeraryprice.add.management"/>'>
				            	<i class="glyphicon glyphicon-usd"></i> SP
				            </a></br>
                		</c:forEach>
					</c:if>
	               	<c:if test="${empty item.productOptions }">
                		
                	</c:if>
                </td>
				<td align="center">
							<a data-permission="po:toProductSelectShop" href='<c:url value="/po/toProductSelectShop"/>?productId=${item.id}' class="btn btn-primary dialog btn-edit" data-title='<spring:message code="label.Treatment.Availability"/>'>
								<i class="glyphicon glyphicon-home"></i>
							</a>
				</td>
				<td align="center"><spring:message code="label.${item.showOnApps}"/></td>
                <td>
                    <a data-permission="product:toEdit" href='<c:url value="/treatment/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-reload-btn=".search-btn" data-title='<spring:message code="label.treatment.edit.management"/>'>
                        <i class="glyphicon glyphicon-edit"></i>
                    </a>
                    <c:if test="${item.isActive}">
	                    <a data-permission="product:remove" href='<c:url value="/product/remove"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
	                       <i class="glyphicon glyphicon-trash"></i>
	                    </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/treatment/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->

<script>
    $(document).ready(function () {
		$.ajax({
			url: '<c:url value="/barcode/checkProductOptionKey"/>',
			type: "POST",
			dataType: "json",
			data: {"reference": "barcode", "categoryName" : "Treatment", "isActive" : "true"},
			success: function (response) {
				var json = eval(response);
				console.log("================="+json);
				if (json == false) {
					$('#bookItems tr', Dialog.getContext()).find('th:eq(3)').hide();
					$('#bookItems tr', Dialog.getContext()).find('td:eq(3)').hide();
				}
			}
		});
    });

</script>