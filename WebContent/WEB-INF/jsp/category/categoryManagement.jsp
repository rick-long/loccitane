<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="management" id="category-search">
    <h3><spring:message code="label.category.search.management"/>
        <a data-permission="category:toAdd" href='<c:url value="/category/toAdd"/>' class="btn btn-primary dialog" data-title='<spring:message code="label.category.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/category/list"/>
    <form:form modelAttribute="categoryListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
    <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.name"/></label>
         <form:input path="name" id="name" class="form-control"/>
            </div>
       </div>
        <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.product.type"/></label>
           <form:select class="selectpicker form-control" path="rootCategoryId" id="rootCategoryId">
                    <c:forEach var="subCategory" items="${subCategoryList }">
                        <form:option value="${subCategory.id }">${subCategory.name }</form:option>
                    </c:forEach>
                </form:select>
            </div>
      </div>
		 <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <div class="categoryTreeMenu">
			<label class="control-label"><spring:message code="label.category"/></label>
		  <div class="select-category" data-selectable="category" data-root-id="2"></div>
			</div>
	    </div></div>
        
        <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"> <label class="control-label"><spring:message code="label.isactive"/></label>
           <form:select class="selectpicker form-control" path="isActive" id="isActive">
                	<form:option value="true"><spring:message code="label.option.yes"/></form:option>
                	<form:option value="false"><spring:message code="label.option.no"/></form:option>
                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                </form:select>
            </div>
      	</div>
        <div class="col-lg-12 col-sm-6">
           <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                    <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/>
                </a> </div>
       </div>
       </div>
       </div>

    </form:form>
    <div id="pageList">
    </div>
</div>
<script type="text/javascript">
    $(function () {
      //rootCategoryId
    	$('#rootCategoryId').change(function () {
    		var $val=$(this).val();
    		$('.select-category').data('root-id',$val);
    		$('.select-category').selectCategory({});
        }).trigger('change');
      
    	$('.select-category').selectCategory({});
    });
</script>