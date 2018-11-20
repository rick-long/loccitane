<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="product-search">
    <h3><spring:message code="label.product.search.management"/>
        <a data-permission="product:toAdd" href='<c:url value="/product/toAdd"/>' class="btn btn-primary dialog" data-reload-btn=".search-btn" data-title='<spring:message code="label.product.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/product/list"/>
    <form:form modelAttribute="productListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
	     <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.brand"/></label>
		<form:select class="selectpicker form-control" path="brandId" id="brandId">
		<form:option value=""><spring:message code="label.option.select.all"/></form:option>
		<c:forEach var="brand" items="${brandList }">
		<form:option value="${brand.id }">${brand.name }</form:option>
			                    </c:forEach>
			                </form:select>
			            </div>
			   		</div>
			<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.supplier"/></label><form:select class="selectpicker form-control" path="supplierId" id="supplierId">
			                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
			                    <c:forEach var="supplier" items="${supplierList }">
			                        <form:option value="${supplier.id }">${supplier.name }</form:option>
			                    </c:forEach>
			                </form:select>
			            </div>
			    	</div>
			       	<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.category.tree"/></label><div class="select-category" data-selectable="category" data-root-id="3"></div>
						</div>
				 	</div>
			       	<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.name"/></label>
			    <form:input path="name" id="name" class="form-control"/>
			            </div>
			        </div>
				<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.isactive"/></label>
			    <form:select path="isActive" id="isActive" class="selectpicker form-control">
			                    <form:option value="true"><spring:message code="label.option.yes"/></form:option>
			                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
			                    <form:option value="false"><spring:message code="label.option.no"/></form:option>
			                </form:select>
			            </div>
			   		</div>


		  <div class="col-lg-3 col-sm-6">
			  <div class="form-group col-md-12" id="barcodediv">
				  <label class="control-label"><spring:message code="label.barcode"/></label>
				  <form:input path="barcode" class="form-control"/>
			  </div>
		  </div>

			   <div class="col-lg-12 col-sm-6">
                <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
			                    <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/></a>
			       </div>
       </div>
       </div>
       </div>
    </form:form>
    <div id="pageList">
    </div>
</div>
<script type="text/javascript">
    var parent = getContext();
    $(document).ready(function () {

    	$('.select-category', Dialog.getContext()).selectCategory({});

		$.ajax({
			url: '<c:url value="/barcode/checkProductOptionKey"/>',
			type: "POST",
			dataType: "json",
			data: {"reference": "barcode", "categoryName" : "Goods", "isActive" : "true"},
			success: function (response) {
				var json = eval(response);
				console.log("================="+json);
				if (json == false) {
					$("#barcodediv", Dialog.getContext()).hide();
				}
			}
		});

    });

</script>
