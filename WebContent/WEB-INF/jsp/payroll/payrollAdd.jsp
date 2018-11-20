<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

	
	<c:url var="url" value="/payroll/add"/>
	<form id="defaultForm" method="post" data-form-token="${TokenUtil.get(pageContext)}" class="form-horizontal" action='${url }'>
		<%-- <div class="form-group">
	        <label class="col-sm-4 control-label"><spring:message code="label.shop"/></label>
	        <div class="col-lg-5">
                <select class="selectpicker form-control" name="shopId" id="shopId">
	         		<option value=""><spring:message code="label.option.select.all"/></option>
					<c:forEach var="shop" items="${shopList }">
						<option value="${shop.id }">${shop.name }</option>
					</c:forEach>
				</select>
	        </div>
	    </div> --%>
	    <div class="form-group">
	        <label class="col-lg-4 control-label"><spring:message code="label.payroll.staff"/></label>
	        <div class="col-lg-5">
                <select class="selectpicker form-control" name="staffId" id="staffId">
	         		<option value="00"><spring:message code="label.option.select.all"/></option>
					<c:forEach var="staff" items="${staffList }">
						<option value="${staff.id }">${staff.displayName }</option>
					</c:forEach>
				</select>
	        </div>
	    </div>
		<div class="form-group">
	        <label class="col-lg-4 control-label"><spring:message code="label.date"/></label>
	        <div class="col-lg-5">
	            <select class="selectpicker form-control" name="payrollDate" id="payrollDate">
	         		<option value=""><spring:message code="label.option.select.all"/></option>
					<c:forEach var="yearAndMonth" items="${allYearAndMonths }">
						<option value="${yearAndMonth}" <c:if test="${yearAndMonth eq currentMonth }">selected</c:if>>${yearAndMonth}</option>
					</c:forEach>
				</select>
	        </div>
	    </div>
		<div class="modal-footer">
	        <div class="bootstrap-dialog-footer">
	            <div class="bootstrap-dialog-footer-buttons">
	                <button type="button" class="btn btn-primary dialogSubmitBtn" id="submit">
	                    <spring:message code="label.button.submit"/>
	                </button>
	                <button type="button" class="btn btn-info dialogResetBtn" id="resetBtn">
	                    <spring:message code="label.button.reset"/>
	                </button>
	            </div>
	        </div>
	    </div>	
	</form>
	
	<script type="text/javascript">
		$(document).ready(function() {
			//staff
			/* $('#shopId',Dialog.getContext()).change(function(){
				$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$(this).val(),showAll:true},function (res) {
		       	 	$('#staffId',Dialog.getContext()).html(res);
		        });
		    }).trigger("change"); */
			
			//payroll date
			/* $.post('<c:url value="/common/loadYearAndMonth"/>', function (res) {
	       	 	$('#payrollDate',Dialog.getContext()).html(res);
	        }); */
		});
	</script>