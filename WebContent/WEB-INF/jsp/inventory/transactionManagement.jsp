<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3><spring:message code="label.inventory.transaction.search.management"/>
        <a href='<c:url value="/inventory/transactionToAdd"/>' class="btn btn-primary dialog" data-width="680" data-reload-btn="a.search-btn" data-title='<spring:message code="label.inventory.transaction.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/inventory/transactionList"/>
    <form:form id="commonSearchForm" modelAttribute="inventoryTransactionListVO" method="post" class="form-inline" action="${url}">
    <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
            <select id="shopId" name="shopId" cssClass="selectpicker form-control">
                    <option value=""><spring:message code="label.option.select.all"/></option>
                    <c:forEach items="${shopList}" var="item">
                        <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                    </c:forEach>
                </select>
            </div>
       </div>
          <div class="col-lg-3 col-sm-6">
              <div class="form-group col-md-12">
                  <div class="categoryTreeMenu">
                      <label class="control-label"><spring:message code="label.category"/> </label>
                      <div class="select-category" data-selectable="category,product,option" data-root-id="3"></div>
                  </div>
              </div>
          </div>
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
     <%-- <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.product"/></label>
           <form:input id="productName" path="productName" cssClass="form-control"/>
            </div>
        </div>--%>

      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.transaction.type"/></label>
           <form:select id="transactionType" path="transactionType" cssClass="selectpicker form-control">
                    <form:option value="">
                        <spring:message code="label.option.select.all"/>
                    </form:option>
                    <form:option value="NEW_STOCK">
                        <spring:message code="label.inventory.transaction.type.NEW_STOCK"/>
                    </form:option>
                    <form:option value="ADJUSTMENT_ADD">
                        <spring:message code="label.inventory.transaction.type.ADJUSTMENT_ADD"/>
                    </form:option>
                    <form:option value="ADJUSTMENT_MINUS">
                        <spring:message code="label.inventory.transaction.type.ADJUSTMENT_MINUS"/>
                    </form:option>
                    <form:option value="SALE"><spring:message code="label.inventory.transaction.type.SALE"/>
                    </form:option>
                    <form:option value="RETURNED">
                        <spring:message code="label.inventory.transaction.type.RETURNED"/>
                    </form:option>
           </form:select>
            </div>
        </div>
          <div class="col-lg-3 col-sm-6">
              <div class="form-group col-md-12">
                  <label class="control-label"><spring:message code="label.from.date"/></label>
                  <div class="input-group date form_time">
                      <input id="fromDate" name="fromDate" class="form-control fromDate" value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
                      <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
                  </div>
              </div>
          </div>

          <div class="col-lg-3 col-sm-6">
              <div class="form-group col-md-12">
                  <label class="control-label"><spring:message code="label.to.date"/></label>
                  <div class="input-group date form_time">
                      <input id="toDate" name="toDate" class="form-control toDate" value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
                      <span class="input-group-addon" id="toDateSpan"><span class="glyphicon glyphicon-time"></span></span>
                  </div>
              </div>
          </div>
      </div>
          <div class="col-sm-12">
              <div class="col-lg-3 col-sm-6">
                  <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                      <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/>
                  </a>
                  </div>
              </div>
              <div class="col-lg-3 col-sm-6">
                  <div class="form-group col-md-12">
                      <a data-permission="inventory:inventoryDetailsExport" href="javascript:;" onclick='exportSearchForm(this,"<c:url value="/inventory/inventoryDetailsExport?"/>");' class="btn btn-default exportBtn" ><i class="glyphicon glyphicon-search"></i>Export
                      </a>
                  </div>
              </div>
              </div>
          </div>
        <%--<div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.product.option"/></label>
            <div class="col-lg-5">
                <div class="input-group dialog" data-url='<c:url value="/po/quickSearch"/>' data-title='<spring:message code="label.button.search"/>'>
                    <form:hidden id="productOptionId" path="productOptionId" cssClass="form-control"/>
                    <form:input id="productName" path="productName" cssClass="form-control quick-search" readonly="true"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
            </div>
        </div>--%>

    </form:form>
    <div id="pageList">
    </div>
</div>
<script>
    $(function () {
    $('#fromDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#toDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    //rootCategoryId
    $('#rootCategoryId').change(function () {
        var $val=$(this).val();
        $('.select-category').data('product-option-id',$val);
        $('.select-category').selectCategory({});
    }).trigger('change');

    $('.select-category').selectCategory({});
    });
    //shop
    $('#shopId',Dialog.getContext()).change(function () {
        $.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),showAll:false},function (res) {
            $('#staffId').html(res);
        });
    }).trigger('change');

    //异步加载export
    function exportSearchForm(obj,url) {
        var pDiv = $(obj).parents(".management");
        var form = pDiv.find("#commonSearchForm");
        $('.exportBtn').attr("href",url+ form.serialize());
    }
</script>
