<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
	<style>
		.btn-primary{
			background:#ffcb00;
		}
	</style>
<div class="management" id="prepaid-search">
	<h3>
		<spring:message code="label.prepaid.search.management"/>
		<a href='<c:url value="/prepaid/toAdd"/>' class="btn btn-primary dialog" data-draggable="true" data-title='<spring:message code="label.prepaid.add.management"/>'>
			<i class="glyphicon glyphicon-plus"></i>
			<spring:message code="label.button.add"/>
		</a>
	</h3>
	<c:url var="url" value="/prepaid/list" />
	<form:form modelAttribute="prepaidListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
		<div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.reference"/></label>
		<form:input path="reference" id="reference" class="form-control"/>
        </div>
	</div>
    
    <div class="col-lg-3 col-sm-6">
       <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.client"/></label>
			<div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
			<form:hidden path="memberId" id="memberId" class="form-control"/>
			<input type="hidden" name="username" id="username" class="form-control"/>
			<input type="text" name="fullName" id="fullName" class="form-control quick-search" readonly/>
		<span class="input-group-addon">
		    <span class="glyphicon glyphicon-search"></span>
		      </span>
		</div>
    </div> 
							<%--<a href='<c:url value="/member/quicksearch"/>' class="btn btn-primary dialog" data-draggable="true"
                                data-title='<spring:message code="label.client.quick.search"/>'><spring:message code="label.button.search"/>
                            </a>--%>
					</div>
		  <div class="col-lg-3 col-sm-6">
			  <div class="form-group col-md-12">
				  <label class="control-label"><spring:message code="label.prepaid.from.date"/></label>
				  <div class="input-group date form_time">
					  <input id="fromDate" name="fromDate" class="form-control" value="" size="16">
					  <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
				  </div>
			  </div>
		  </div>

		  <div class="col-lg-3 col-sm-6">
			  <div class="form-group col-md-12">
				  <label class="control-label"><spring:message code="label.prepaid.to.date"/></label>
				  <div class="input-group date form_time">
					  <input id="toDate" name="toDate" class="form-control"
							 value="" size="16">
					  <span class="input-group-addon" id="toDateSpan"><span class="glyphicon glyphicon-time"></span></span>
				  </div>
			  </div>
		  </div>

		  <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.prepaid.type"/></label>
          <form:select id="prepaidType" path="prepaidType" class="selectpicker form-control">
		<form:option value=""><spring:message code="label.option.select.single"/></form:option>
		<form:option value="CASH_PACKAGE"><spring:message code="label.prepaid.type.CASHPACKAGE"/></form:option>
		<form:option value="TREATMENT_PACKAGE"><spring:message code="label.prepaid.type.TREATMENTPACKAGE"/></form:option>
		<form:option value="CASH_VOUCHER"><spring:message code="label.prepaid.type.CASHVOUCHER"/></form:option>
		<form:option value="TREATMENT_VOUCHER"><spring:message code="label.prepaid.type.TREATMENTVOUCHER"/></form:option>
							 <form:option value="MEMBERSHIP_FEE"><spring:message code="label.prepaid.type.MEMBERSHIPFEE"/></form:option>
						</form:select>
					</div>
				</div>
                
	<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.shop"/></label>
		<select id="shopId" name="shopId" class="selectpicker form-control">
		<option value=""><spring:message code="label.option.select.single"/></option>
		<c:forEach items="${shopList}" var="item">
		<option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
                
<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.treatment.tree"/></label>
		<div class="select-category" data-selectable="category,product,option" data-root-id="2" ></div>
					</div>
				</div>
               
               
	<div class="col-lg-3 col-sm-6">
         <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.isactive"/></label>
          <form:select class="selectpicker form-control" path="isActive" id="isActive">
		<form:option value="">All</form:option>
		<form:option value="true"><spring:message code="label.option.yes"/></form:option>
		<form:option value="false"><spring:message code="label.option.no"/></form:option>
		</form:select>
		</div>
	</div>

       </div>
			<div class="col-sm-12">
				<div class="col-lg-3 col-sm-6">
					<div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" ><i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/></a></div>
				</div>

				<div class="col-lg-3 col-sm-6">
					<div class="form-group col-md-12"><a data-permission="prepaid:export" href="javascript:;" onclick='exportSearchForm(this,"<c:url value="/prepaid/export?"/>");' class="btn btn-default exportBtn" ><i class="glyphicon glyphicon-search"></i><spring:message code="label.button.export"/>
					</a>
                    </div>
				</div>
			</div>

		<%-- <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="label.product.tree"/></label>
            <div class="col-lg-5">
                   <div class="select-category" data-selectable="category,product,option"></div>
            </div>
        </div> --%>
</div>
	</form:form>
	<div id="pageList">

	</div>
</div>

<script type="text/javascript">


    $(function () {
        $('#fromDate').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('#fromDate').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });

        $('#toDate').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('#toDateSpan').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
        $('.select-category').selectCategory({});
    });

    //异步加载export
    function exportSearchForm(obj,url) {
        var pDiv = $(obj).parents(".management");
        var form = pDiv.find("#commonSearchForm");
        $('.exportBtn').attr("href",url+ form.serialize());
    }
</script>