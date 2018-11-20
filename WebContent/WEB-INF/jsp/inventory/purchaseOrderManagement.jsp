<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3><spring:message code="label.inventory.purchase.order.search.management"/>
        <a href='<c:url value="/inventory/purchaseOrderToAdd"/>' class="btn btn-primary form-page" data-width="680" data-reload-btn="a.search-btn" data-title='<spring:message code="label.inventory.purchase.order.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/inventory/purchaseOrderList"/>
    <form:form id="commonSearchForm" modelAttribute="inventoryPurchaseOrderListVO" method="post" class="form-inline" action="${url}">
   <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.supplier"/></label><form:select path="supplierId" id="supplierId" cssClass="selectpicker form-control">
                    <form:option value="">
                        <spring:message code="label.option.select.all"/>
                    </form:option>
                    <c:forEach items="${supplierList}" var="item">
                        <form:option value="${item.id}">${item.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.status"/></label>
            <form:select id="status" path="status" cssClass="selectpicker form-control">
                    <form:option value="">
                        <spring:message code="label.option.select.all"/>
                    </form:option>
                    <form:option value="PENDING">
                        <spring:message code="label.inventory.purchase.order.status.PENDING"/>
                    </form:option>
                    <form:option value="RECEIVING">
                        <spring:message code="label.inventory.purchase.order.status.RECEIVING"/>
                    </form:option>
                    <form:option value="COMPLETE">
                        <spring:message code="label.inventory.purchase.order.status.COMPLETE"/>
                    </form:option>
                </form:select>
            </div>
       </div>
       <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.purchase.date"/> </label>
          <div class="input-group date form_time">
		    <input id="date" name="date" class="form-control date" value='<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>' size="16">
		 <span class="input-group-addon" id="dateSpan"><span class="glyphicon glyphicon-time"></span></span>
		    </div>
	       </div>
          </div>
            
	  <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
	    	<label class="control-label"><spring:message code="label.delivery.date"/> </label>
	        <div class="input-group date form_time">
		      <input id="expectedDeliveryDate" name="expectedDeliveryDate" class="form-control expectedDeliveryDate" value='<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>' size="16">
		   <span class="input-group-addon" id="expectedDeliveryDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	        </div>
	   	</div>
        <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.reference"/></label>
            <form:input id="reference" path="reference" cssClass="form-control"/>
            </div>
        </div>
        <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.deliverynotenNumber"/> </label>
            <form:input id="deliveryNoteNumber" path="deliveryNoteNumber" cssClass="form-control"/>
            </div>
        </div>
       <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.isactive"/></label><form:select class="selectpicker form-control" path="isActive" id="isActive">
                    <form:option value=""><spring:message code="label.option.select.single"/></form:option>
                    <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                    <form:option value="false"><spring:message code="label.option.no"/></form:option>
                </form:select>
            </div>
     </div>
      <div class="col-lg-12 col-sm-6">
         <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                    <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/>
                </a>
         </div>
       </div>
       </div>
       </div>
    </form:form>
    <div id="pageList">
    </div>
</div>
<script type="text/javascript">
    $('#date').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('.input-group-addon').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });
    
    $('#expectedDeliveryDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#expectedDeliveryDateSpan').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });
    
</script>