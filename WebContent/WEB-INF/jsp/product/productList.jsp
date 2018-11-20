<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover" id="bookItems">
	<thead>
		 <tr>
		     <th><spring:message code="label.name"/></th>
		     <th><spring:message code="label.brand"/></th>
		     <th><spring:message code="label.category"/></th>
		     <th><spring:message code="label.description"/></th>
			 <th><spring:message code="label.barcode"/></th>
		     <th><spring:message code="label.product.option"/></th>
		     <th><spring:message code="label.supernumeraryprice.add.management"/></th>
		     <th class="col-lg-1"></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
                <td>${item.name}</td>
                <td>${item.brand.name}</td>
                <td>
	               ${item.category.fullName }
                </td>
                <td>${item.description }</td>
				<td>
					<c:if test="${not empty item.productOptions }">
						<c:forEach items="${item.productOptions}" var="po">
							<p>${po.barcode }</p>
						</c:forEach>
					</c:if>
				</td>
                <td>
                	<c:if test="${not empty item.productOptions }">
	                	<c:forEach items="${item.productOptions}" var="po">

							<p>${po.allOptionValues}</p>
                		</c:forEach>
					</c:if>
	               	<c:if test="${empty item.productOptions }">
                		<p>	N/A<p>
                	</c:if>
                </td>
               	<td>
                	<c:if test="${not empty item.productOptions }">
	                	<c:forEach items="${item.productOptions}" var="po">
					    	<a data-permission="po:toAddSupernumeraryPrice" href='<c:url value="/po/toAddSupernumeraryPrice"/>?productOptionId=${po.id}' class="btn btn-primary dialog" data-title='<spring:message code="label.supernumeraryprice.add.management"/>'>
				            	<i class="glyphicon glyphicon-usd"></i> SP
				            </a></br>
                		</c:forEach>
					</c:if>
	               	<c:if test="${empty item.productOptions }">
                		
                	</c:if>
                </td>
                <td>
                    <a data-permission="product:toEdit" href='<c:url value="/product/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-reload-btn=".search-btn" data-title='<spring:message code="label.product.edit.management"/>'>
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
<c:set var="pageUrl" value="${pageContext.request.contextPath}/product/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->

<script>
    $(document).ready(function () {
        $.ajax({
            url: '<c:url value="/barcode/checkProductOptionKey"/>',
            type: "POST",
            dataType: "json",
            data: {"reference": "barcode", "categoryName" : "Goods", "isActive" : "true"},
            success: function (response) {
                var json = eval(response);
                console.log("================="+json);
                if (json == false) {
                    $('#bookItems tr', Dialog.getContext()).find('th:eq(4)').hide();
                    $('#bookItems tr', Dialog.getContext()).find('td:eq(4)').hide();
                }
            }
        });
    });

</script>