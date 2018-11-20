<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
	
    <table id="bookItems" class="table table-striped table-hover">
    	<thead>
		    <tr>
		    	<!-- <th></th> -->
		        <th><spring:message code="label.start.time" /></th>
		        <th><spring:message code="label.product" /></th>
		        <th><spring:message code="label.therapist" /></th>
		        <th><spring:message code="label.room" /></th>
		        <th><spring:message code="label.status" /></th>
		        <th></th>
		    </tr>
	    </thead>
	    <tbody>
		    <c:forEach items="${book.bookItems}" var="item" varStatus="idx">
		   		<tr data-id="${item.id}">
		   			<%-- <td>
		   				 <input type="checkbox" name="" value="true" data-book-item-id="${item.id}"/>
		   			</td> --%>
           			<td>
           				<fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/>
           				<input type="hidden" name="itemVOs[${idx.index+1}].bookItemId" id="itemVOs[${idx.index+1}].bookItemId" value="${item.id}" data-book-item-id="${item.id}"/>
           				<input type="hidden" name="itemVOs[${idx.index+1}].startTime" id="itemVOs[${idx.index+1}].startTime" value='<fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/>' data-book-item-id="${item.id}"/>
           				<input type="hidden" name="itemVOs[${idx.index+1}].duration" id="itemVOs[${idx.index+1}].duration" value="${item.duration}" data-book-item-id="${item.id}"/>
           			</td>
           			<td>
           				${item.productOption.product.name}#${item.duration}
           				<input type="hidden" name="itemVOs[${idx.index+1}].productOptionId" id="itemVOs[${idx.index+1}].productOptionId" value="${item.productOption.id}" data-book-item-id="${item.id}"/>
           			</td>
           			<td>
           				<c:forEach items="${item.users}" var="user" varStatus="ridx">
           					${user.username} </br>
            				<input type="hidden" name="itemVOs[${idx.index+1}].therapists[${ridx.index +1}].staffId" value="${user.id }" data-book-item-id="${item.id}"/>
			        		<input type="hidden" name="itemVOs[${idx.index+1}].therapists[${ridx.index +1}].key" value="${ridx.index +1}" data-book-item-id="${item.id}"/>
			        		<input type="hidden" name="itemVOs[${idx.index+1}].therapists[${ridx.index +1}].requested" value="false" data-book-item-id="${item.id}"/>
           				</c:forEach>
           			</td>
           			<td class="hide">
		            	${item.price}
		            	<input type="hidden" name="itemVOs[${idx.index+1}].price" id="itemVOs[${idx.index+1}].price" value="${item.price}" data-book-item-id="${item.id}"/>
		            </td>
		            <td class="hide">
		            	1
		           	 	<input type="hidden" class="form-control quantity" id="itemVOs[${idx.index+1}].qty" name="itemVOs[${idx.index+1}].qty" value="1" data-book-item-id="${item.id}">
		            </td>
           			<td class="noshow">
           				${item.room.name}
           			</td>
           			<td class="hide">
		                <input type="text" class="form-control extradiscount" id="itemVOs[${idx.index+1}].extraDiscount" value="" name="itemVOs[${idx.index+1}].extraDiscount" data-book-item-id="${item.id}">
		            </td>
		            <td class="noshow">
           				<spring:message code="label.book.status.${item.status}"/>
           			</td>
           			<td>
           				<c:if test="${item.bookItemWhetherCanBeCheckout}">
           					<button class="btn btn-primary selectPOBtn" id="selectPOBtn" data-book-item-id="${item.id}"><spring:message code="label.select"/> </button>
           				</c:if>
           				<button class="btn btn-warning removePOBtn hide" data-book-item-id="${item.id}"><spring:message code="label.button.remove"/> </button>
           			</td>
           		</tr>
		    </c:forEach>
		    <!-- <tr><td><button class="btn btn-warning payBtn">Pay</button></td></tr> -->
		</tbody>
	</table>
	<fieldset>
		<legend>
			<span class="glyphicon glyphicon-shopping-cart glyphicon-plus">
                 <spring:message code="label.sales.selecteditem"/>
              </span>
        </legend>
		<div>
	    	<form id="itemForm" method="post" class="form-horizontal">
		        <table class="table table-striped">
		            <thead>
		            <tr>
		            	<th><spring:message code="label.start.time"/></th>
		                <th><spring:message code="label.product"/></th>
		                <th><spring:message code="label.therapist"/></th>
		                <th><spring:message code="label.default.price"/></th>
		                <th><spring:message code="label.qty"/></th>
		                <th><spring:message code="label.extra.discount"/></th>
		                <th></th>
		            </tr>
		            </thead>
		            <tbody id="selectedBookItems">
		            	<input type="hidden" name="memberId" id="memberId" value="${book.user.id}"/>
		            	<input type="hidden" name="shopId" id="shopId" value="${book.shop.id}"/>
		            </tbody>
		        </table>
	        </form>
	    </div>
	</fieldset>
	<c:if test="${book.hasMoreThanOneBookItemCanBePaid }">
		<div class="modal-footer">
		    <div class="bootstrap-dialog-footer">
		        <div class="bootstrap-dialog-footer-buttons">
		            <button class="btn btn-default" id="done">Finish</button>
		        </div>
		    </div>
		</div>
	</c:if>
	<script type="text/javascript">
	
	$(document).ready(function () {
		 
	 	$('#selectPOBtn', Dialog.getContext()).on('click',function () {
		 	var $this = $(this);
            var html = $($this.parents('tr').clone());
            
            html.find('.selectPOBtn').remove();
            html.find('.removePOBtn').removeClass('hide');
            if ($.trim(html)) {
                html = $(html);
                html.find('td.noshow').remove();
                html.find('td.hide').removeClass('hide');
                
                // 注册删除事件
                html.find('.removePOBtn').click(function () {
                    $(this).parents('tr').remove();
                });
				
                html.appendTo($("#selectedBookItems", Dialog.getContext()));
                
            }
         });
		 
		 $('#selectedBookItems', Dialog.getContext()).on('click', 'button.removePOBtn', function () {
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