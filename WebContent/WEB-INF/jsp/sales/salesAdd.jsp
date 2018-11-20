<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<div id="order-search" class="management">
    <h3 class='text-h3-white'>Add Booking</h3>
    <div >	
	<c:url var="url" value="/sales/checkout"/>
	<form:form modelAttribute="orderVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>

			<div class="form-group">
		        <label class="col-lg-4 control-label"><spring:message code="label.purchase.date"/></label>
		        <div class="col-lg-5">
		            <div class="input-group date form_time">
			        	<input id=purchaseDate name="purchaseDate" class="form-control purchaseDate" 
			        		value='<fmt:formatDate value="${purchaseDate}" pattern="yyyy-MM-dd"/>' size="16">
			            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
			        </div>
		        </div>
		    </div>
			<div class="form-group">
				<label class="col-lg-4 control-label"><spring:message code="label.client"/></label>
		    	<div class="col-lg-5">
		           	<div class="input-group">
		           		<div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
                             <input name="memberId" id="memberId" class="form-control" type="hidden"/>
                             <input name="username" id="username" class="form-control quick-search" readonly/>
                             <span class="input-group-addon">
                                 <span class="glyphicon glyphicon-search"></span>
                             </span>
                         </div>
		               	<span class="glyphicon glyphicon-pencil walkInGuest">Walk-In</span>
		               	<%-- <span class="input-group-addon dialog" data-url='<c:url value="/member/toAdd"/>' data-title='<spring:message code="label.client.quick.add"/>'>
		                   <span class="glyphicon glyphicon-user"></span>
		               	</span> --%>
		           	</div>
			    </div>
			</div>
			<div class="form-group">
	           	<label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
	           	<div class="col-lg-5">
	               	<select path="shopId" class="selectpicker form-control">
	                   	<c:forEach items="${shopList}" var="item">
	                       	<option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
	                   	</c:forEach>
	               	</select>
	           	</div>
	       	</div>
			<fieldset>
			<legend>
				<span class="glyphicon glyphicon-shopping-cart glyphicon-plus">
                    <%-- <a id="selectPTBtn" href='<c:url value="/po/quickSearchForSales"/>' class="btn btn-primary dialog" data-title='<spring:message code="label.button.search"/>'>
                        Add Item
                    </a> --%>
                    <a id="selectPTBtn"  class="btn btn-primary dialog" data-title='<spring:message code="label.button.search"/>'>
                       	Add Item
                    </a>
                </span>
            </legend>
            <div id="itemDetails">
            	<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th><spring:message code="label.start.time" /></th>
							<th><spring:message code="label.product" /></th>
							<th><spring:message code="label.therapist" /></th>
							<th><spring:message code="label.price" /></th>
							<th><spring:message code="label.qty" /></th>
							<th><spring:message code="label.discount" /></th>
							<th><spring:message code="label.amount" /></th>
							<th>Suitable Package(s)</th>
							<th>Fill Voucher</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
            </div>
			</fieldset>
			<%-- <div class="form-group">
				<label class="col-lg-4 control-label">Promotion Code</label>
			    <div class="col-lg-5">
			    	<form:input path="promotionCode" value="" class="form-control"/>
			    </div>
			</div> --%>
			<c:forEach var="numPM"  begin="1" end="${numberOfPMUsed}">
		    	<div class="form-group">
					<label class="col-lg-4 control-label">PM #${numPM}</label>
				    <div class="col-lg-5">
				    	<form:select class="selectpicker form-control" path="paymentMethods[${numPM}].id">
				    		<%-- <c:if test="${numPM != 1 }">
				            	<form:option value=""><spring:message code="label.option.select.single"/></form:option>
				            </c:if> --%>
				            <form:option value=""><spring:message code="label.option.select.single"/></form:option>
				            <c:forEach var="pm" items="${pmList }">
				            	<form:option value="${pm.id }">${pm.name }</form:option>
				            </c:forEach>
				        </form:select>
				        <form:input path="paymentMethods[${numPM}].value" value="" class="form-control"/>
				        <form:hidden path="paymentMethods[${numPM}].key" class="form-control" value="${numPM }"/>
					</div>
				</div>
			</c:forEach>
			<%-- <div class="form-group">
				<label class="col-lg-4 control-label">Send Thank You Email</label>
			    <div class="col-lg-5">
			    	<form:input path="promotionCode" value="" class="form-control"/>
			    </div>
			</div> --%>
		<div class="modal-footer">
		    <div class="bootstrap-dialog-footer">
		        <div class="bootstrap-dialog-footer-buttons">
		        	<button type="button" class="btn btn-default dialogSubmitBtn" id="submit">Check Out</button>
		        </div>
		    </div>
		</div>
	</form:form>
    </div>
</div>	

	<script type="text/javascript">
	
	$(document).ready(function () {
		 
		 $('#purchaseDate', Dialog.getContext()).datetimepicker({
	        format: 'Y-m-d',
	        timepicker: false
	     });
	     $('.input-group-addon', Dialog.getContext()).click(function () {
	        var input = $(this).siblings('input').trigger('focus');
	     });
	     
	     $('#itemDetails', Dialog.getContext()).on('click', 'button.removeItemBtn', function () {
	         $(this).parents('tr').remove();
	     });
	     
	     $('#selectPTBtn', Dialog.getContext()).click(function(){
            var baseUrl = '<c:url value="/po/quickSearchForSales"/>';
            $(this).attr('href', baseUrl + '?memberId=' + $('#memberId', Dialog.getContext()).val()+'&shopId=' + $('#shopId', Dialog.getContext()).val());
        });
	     
	    $('.walkInGuest', Dialog.getContext()).click(function(){ 
		     $.ajax({
	             url: '<c:url value="/member/walkInGuest"/>',
	             type: "POST",
	             dataType: "json",
	             success: function(data) {
	            	 if(data.errorFields.length > 0) {
		         		$.each(data.errorFields, function (index, item) {
		         			if(item.fieldName =='username'){
		         				$('#username', Dialog.getContext()).attr("value", item.errorMessage);
		         			}
		         			if(item.fieldName =='id'){
		         				$('#memberId', Dialog.getContext()).attr("value", item.errorMessage);
		         			}
		         		});
	             	}
	          	}
	     	});
	    });
	});
	</script>