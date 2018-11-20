<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <c:url var="url" value="/po/quickSearchForSalesList"/>
    <form id="commonSearchForm" method="post" class="form-horizontal" action="${url}">
        <div class="form-group">
            <div class="col-sm-5">
                <spring:message code="label.product.filter.by.product.service"/> <input name="key" id="key" class="form-control" placeholder='<spring:message code="label.name"/>'/>
            </div>
            <input type="hidden" name="shopId" id="shopId" value="${productOptionListVO.shopId}"/>
            <a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                <spring:message code="label.button.search"/>
            </a>
        </div>
    </form>
    <div id="pageList"></div>
    <div>
    	<form id="itemForm" method="post" class="form-horizontal">
	        <table class="table table-striped">
	            <thead>
	            <tr>
	            	<th><spring:message code="label.start.time"/></th>
	                <th><spring:message code="label.name"/></th>
	                <th><spring:message code="label.default.price"/></th>
	                <th><spring:message code="label.qty"/></th>
	                <c:forEach var="numTherapist"  begin="1" end="${numberOfTherapistUsed}">
	                	<th><spring:message code="label.therapist"/>${numTherapist}</th>
	                </c:forEach>
	                <th><spring:message code="label.extra.discount"/></th>
	                <th></th>
	            </tr>
	            </thead>
	            <tbody id="selectedProductOptions">
	            	<input type="hidden" name="memberId" id="memberId" value="${productOptionListVO.memberId}"/>
	            	<input type="hidden" name="shopId" id="shopId" value="${productOptionListVO.shopId}"/>
	            </tbody>
	        </table>
        </form>
    </div>
</div>

<div class="modal-footer">
    <div class="bootstrap-dialog-footer">
        <div class="bootstrap-dialog-footer-buttons">
            <button class="btn btn-default" id="done"><spring:message code="label.add.item"/></button>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function () {
       
        // 选择product option 事件
        $('#pageList', Dialog.getContext()).on('click', 'button.selectPOBtn', function () {
            var $this = $(this);
            var html = $($this.parents('tr').clone());
            
            html.find('.selectPOBtn').remove();
            html.find('.removePOBtn').removeClass('hide');
            if ($.trim(html)) {
                html = $(html);
                html.find('td.inventory').remove();
                html.find('td.hide').removeClass('hide');
                
                // 注册删除事件
                html.find('.removePOBtn').click(function () {
                    $(this).parents('tr').remove();
                });

                html.find('input.time-picker').datetimepicker({
	                format: 'H:i',
	                datepicker: false,
	                step: 15
	            });
                
                html.appendTo($("#selectedProductOptions", Dialog.getContext()));
            }
        });

        $('#selectedProductOptions', Dialog.getContext()).on('click', 'button.removePOBtn', function () {
            $(this).parents('tr').remove();
        });


        $('#done', Dialog.getContext()).click(function (event) {
        	event.preventDefault();// 禁止a连接的href事件
        	var parentDialog = Dialog.getParentContext();
            var form = $('#itemForm', Dialog.getContext());
         	$.ajax({
                 url: '<c:url value="/sales/addItemDetails"/>',
                 type: "POST",
                 dataType: "text",
                 data: form.serialize(),
                 success: function(response) {
                	 $("#itemDetails tbody", parentDialog).append(response);
           	   		 Dialog.get().close();
                 }
          	});
        });
    });
</script>

