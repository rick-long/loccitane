<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<div class="management" id="order-search">
	<h3>
		<spring:message code="label.payroll.search.management"/>
		<a href='<c:url value="/payroll/toAdd"/>' class="btn btn-primary dialog" data-draggable="true" data-title='<spring:message code="label.payroll.add.management"/>'>
			<i class="glyphicon glyphicon-plus"></i>
			<spring:message code="label.button.add"/>
		</a>
	</h3>
	<c:url var="url" value="/payroll/list"/>
	<form id="commonSearchForm" method="post" class="form-inline" action="${url}">	
    <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
			    <select class="selectpicker form-control" name="shopId" id="shopId">
			         		<option value=""><spring:message code="label.option.select.all"/></option>
							<c:forEach var="shop" items="${shopList }">
								<option value="${shop.id }" <c:if test="${currentShop.id == shop.id}">selected</c:if>>${shop.name }</option>
							</c:forEach>
						</select>
			        </div>
				</div>

      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.payroll.staff"/></label>
          <select class="selectpicker form-control" name="staffId" id="staffId">
		                	
						</select>
			        </div>
			    </div>

      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.date"/></label>
			      <select class="selectpicker form-control" name="payrollDate" id="payrollDate">
			         		<option value=""><spring:message code="label.option.select.all"/></option>
							<c:forEach var="yearAndMonth" items="${allYearAndMonths }">
								<option value="${yearAndMonth}" <c:if test="${yearAndMonth eq currentMonth }">selected</c:if>>${yearAndMonth}</option>
							</c:forEach>
						</select>
			        </div></div>
		     <div class="col-lg-12 col-sm-6">
                <div class="form-group col-md-12"> <a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
		                	<i class="glyphicon glyphicon-search"></i>
		                	<spring:message code="label.button.search"/>
		                </a>
				</div>
       </div>
       </div>
       </div>
	</form>
	<div id="pageList">
		
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		//staff 
		$('#shopId').change(function(){
			$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$(this).val(),showAll:true},function (res) {
	       	 	$('#staffId').html(res);
	        });
	    }).trigger("change");
		//payroll date
		/* $.post('<c:url value="/common/loadYearAndMonth"/>', function (res) {
       	 	$('#payrollDate').html(res);
        }); */
	});
</script>