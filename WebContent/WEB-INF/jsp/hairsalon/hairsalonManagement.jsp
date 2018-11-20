<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="product-search">
    <h3><spring:message code="label.hair.salon.search"/>
        <a data-permission="product:toAdd" href='<c:url value="/hairsalon/toAdd"/>' class="btn btn-primary dialog" data-reload-btn=".search-btn" data-title='Hair Salon Add'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/hairsalon/list"/>
    <form:form modelAttribute="productListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
   <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.name"/></label>
            <form:input path="name" id="name" class="form-control"/>
            </div>
        </div>
       <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <div class="categoryTreeMenu">
			<label class="control-label"><spring:message code="label.category.tree"/></label>
		   <div class="select-category" data-selectable="category" data-root-id="4"></div>
			</div>
	    </div></div>
        
       <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.isactive"/></label>
                <form:select path="isActive" id="isActive" class="selectpicker form-control">
                	<form:option value="true"><spring:message code="label.option.yes"/></form:option>
                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                    <form:option value="false"><spring:message code="label.option.no"/></form:option>
                </form:select>
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
    });

</script>
