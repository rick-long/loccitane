<%@page import="org.spa.vo.sales.OrderItemVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<script src='<c:url value="/resources/js/base/jquery.form.js"/>'></script>
<style>
	span#quickAddMember {
		width: 125px !important;

	}

	.orderDetails_management {
		padding-right: 23px
	}

	.shopId > .glyphicon-ok {
		right: -6px !important;
	}

	.client > i {
		right: -36px !important;
	}

	.Treatment{
		margin-right:20px;
		display:inline;
	}
</style>
<h3 class='text-h3-white'>
	<spring:message code="label.sales.add.management1"/>
</h3>
<div class="orderDetails">
   <div class="orderDetails_management">
	<table class="clientDetails">
		<tr>
	        <td class="col-lg-5"><spring:message code="label.purchase.date"/>*</td>
	        <td>
	            <div class="input-group date form_time">
		        	<input id="purchaseDateDisplay" name="purchaseDateDisplay" class="form-control purchaseDateDisplay"
		        		value='<fmt:formatDate value="${purchaseDate}" pattern="yyyy-MM-dd"/>' size="16">
		        	<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	        </td>
	    </tr>
		<tr>
			<td class="col-lg-5"><spring:message code="label.client"/>*</td>
	    	<td>
	           	<div class="input-group dialog" data-url='<c:url value="/member/quicksearchWithEvent"/>' data-title='<spring:message code="label.client.quick.search"/>'>

					<input type="text" name="fullName" id="fullName" class="form-control quick-search" readonly/>
					<span class="input-group-addon">
	              		<span class="glyphicon glyphicon-search"></span>
	               	</span>
	           </div>

					<a id="clientView" data-permission="book:clientView" onclick="clientView()"  title='<spring:message code="label.button.view"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn"
					   data-width="950" data-title='<spring:message code="label.button.view"/>'  style="display: inline;position:absolute;right:-7px;top:3px">
                            <i class="glyphicon glyphicon-eye-open"></i>
                        </a>
	           <span class="glyphicon glyphicon-pencil walkInGuest"><spring:message code="label.sales.guest"/> </span>

	           <%-- <spring:message code="label.sales.ishotelguest"/> --%> <input type="hidden" name="isHotelGuestDisplay" id="isHotelGuestDisplay" value="false"/>
		    </td>
		</tr>
		<tr>
	       	<td class="col-lg-5"><spring:message code="label.shop"/>*</td>
	       	<td>
	       		<select name="shopIdDisplay" id="shopIdDisplay" class="selectpicker form-control">
	                <c:forEach items="${shopList}" var="item">
	                    <option value="${item.id}">${item.name}</option>
	                </c:forEach>
	            </select>
	       	</td>
	   	</tr>
	   	<tr>
	    	<td class="col-lg-5"><spring:message code="label.remarks"/></td>
			<td>
	 			<textarea name="remarksDisplay" id="remarksDisplay" class="form-control"></textarea>
	 			<spring:message code="label.sales.showremarksininvoice"/> <input type="checkbox" name="showRemarksInInvoiceDisplay" id="showRemarksInInvoiceDisplay" value="false"/>
	    	</td>
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" class="clientDetails">
  <tr>
    <td class="col-lg-5"><span style="text-decoration:underline"> <b><spring:message code="label.sales.additems"/> </b></span>
	</td>
    <td><span style="text-decoration:underline">
		<a href='<c:url value="/prepaid/toAdd"/>' class="btn btn-primary dialog" data-draggable="true" data-title='<spring:message code="label.prepaid.add.management"/>'>
			<i class="glyphicon glyphicon-plus"></i>
			<spring:message code="label.sales.addprepaid"/>
	    </a>
	</span></td>
  </tr>
</table>

    </div>
	<div class="addItem" id="addItem">
		<ul class="nav nav-tabs" id="myTab">
	        <li class="active"><a href="#goods"><spring:message code="label.sales.goods"/> </a></li>
	        <!-- <li><a href="#tips">Tips</a></li> -->
	    </ul>
	    <div class="tab-content">
	    	<div id="goods" class="tab-pane fade in active">
	    		<form id="goodsItemForm" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
	    		<table class="table table-striped box_align">
				    <tr>
				        <th><spring:message code="left.navbar.product"/></th>
				        <th class="col-lg-1"><spring:message code="label.qty"/></th>
				        <c:forEach var="numTherapist"  begin="1" end="${numberOfTherapistUsed}">
				        	<th><spring:message code="label.therapist"/> (${numTherapist})</th>
				       	</c:forEach>
				        <%-- <th><spring:message code="label.extra.discount" />(<spring:message code="label.unit.HKcurrency"/> )</th> --%>
						<th><spring:message code="label.adjust.net.value" /> (<spring:message code="label.unit.HKcurrency"/>)</th>
						<th><spring:message code="label.sales.suitablepackage"/></th>
						<th class="col-lg-1"><spring:message code="label.sales.fillvoucher"/></th>
				        <th></th>
				    </tr>
				    <tr>
				        <td>
				            <div class="select-category" data-selectable="option" data-root-id="3" data-root-id1="7"></div>
				        </td>
				        <td>
				        	<select class="selectpicker form-control goodQty" name="itemVOs[0].qty">
				                <option value="1">1</option>
				                <option value="2">2</option>
				                <option value="3">3</option>
				                <option value="4">4</option>
				                <option value="5">5</option>
				                <option value="6">6</option>
				                <option value="7">7</option>
				                <option value="8">8</option>
				                <option value="9">9</option>
				                <option value="10">10</option>
			            	</select>
			            </td>
			            <c:forEach var="numTherapist"  begin="1" end="${numberOfTherapistUsed}">
			            <td>
			            	<select class="selectpicker form-control suitabledTherapists" name="itemVOs[0].therapists[${numTherapist }].staffId" style="    display: inline!important;
    width: 85%;
    vertical-align: middle;">

					        </select>
			   				<input type="hidden" name="itemVOs[0].therapists[${numTherapist}].requested" value="false">
		  				  	<input type="hidden" name="itemVOs[0].therapists[${numTherapist}].key" value="${numTherapist}" /></td>
			            </c:forEach>
			   			<%-- <td>
				                <input type="text" class="form-control extradiscount" id="itemVOs[0].extraDiscount" value="" name="itemVOs[0].adjustNetValue" >
				            </td> --%>
				            <td>
				                <input type="text" class="form-control adjustNetValue" id="itemVOs[0].adjustNetValue" value="" name="itemVOs[0].adjustNetValue" >
				            </td>
				        <td>
							<select class="selectpicker form-control suitabledPackages" data-product-option-id=""
								name="itemVOs[0].paidByPackageId" id="itemVOs[0].paidByPackageId">

					        </select>
						</td>
						<td>
							<input type="text" class="form-control" name="itemVOs[0].paidByVoucherRef" value="" size="15"/>
						</td>
				        <td>
				            <button class="btn btn-primary" id="addGoodsItem"><spring:message code="label.sales.additem"/> </button>
				            <input type="hidden" id="idxId" name="itemVOs[0].idxId" value="0">
				        </td>
				    </tr>
				</table>
				</form>
	    	</div>
	    	<!-- <div id="tips" class="tab-pane fade content_in_details">
	    		<table id="tipsTable"class="TipsDetails" >
	    			<tr>
	    				<td class="col-lg-3"><font color="red">*</font>Amount</td>
				        <td>
				            <input id="tipsAmount" name="tipsAmount" class="form-control" value=""><font color="red">(HKD)</font>
				        </td>
				        <TD></TD>
	    			</tr>
	    			<tr>
	    				<td class="col-lg-3">Staff</td>
				        <td>
				            <select class="selectpicker form-control suitabledTherapists" id="tipsTherapist" name="tipsTherapist">

					        </select>
				        </td>
				        <TD></TD>
	    			</tr>
	    			<tr>
	    				<TD></TD>
	    				<TD><button class="btn btn-primary" id="addTipsItem" data-idx-id="0">ADD ITEM</button></TD>
	    				<td></td>
	    			</tr>
	    		</table>
	    	</div> -->
	    </div>
	</div>

	<c:url var="url" value="/sales/checkout"/>
	<form:form modelAttribute="orderVO" id="orderForm"  data-forward="salesListMenu" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action='${url }'>
        <table class="table table-striped" id="itemCart">
			<thead>
			<tr>
				<th class="col-lg-2"><span style="text-decoration:underline"><spring:message code="label.sales.items"/> </span></th>
				<th class="col-lg-2"><span style="text-decoration:underline"><spring:message code="label.sales.therapistcommi"/></span></th>
				<th class="col-lg-1"><span style="text-decoration:underline"><spring:message code="label.qty"/></span></th>
				<th class="col-lg-1"><span style="text-decoration:underline"><spring:message code="label.sales.unitcost"/></span></th>
				<th class="col-lg-1"><span style="text-decoration:underline"><spring:message code="label.sales.adjustment"/></span></th>
				<th class="col-lg-2"><span style="text-decoration:underline"><spring:message code="label.sales.prepaidpaidamount"/></span></th>
				<th class="col-lg-2"><span style="text-decoration:underline"><spring:message code="label.sales.finalamount"/></span></th>
				<th class="col-lg-1"><span style="text-decoration:underline"></span></th>
			</tr>
			</thead>
			<tbody>

			</tbody>
		</table>

		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="total_show">
		  	<tr>
		    	<td width="75%" class="total_text"><spring:message code="label.sales.totalamount"/>: </td>
		    	<td width="25%" class="totalamount_td"><spring:message code="label.unit.HKcurrency"/> </td>
		  	</tr>
		</table>

		<div class="payment content_in_details">
			<c:forEach var="numPM"  begin="1" end="${numberOfPMUsed}">
				<table border="0" cellspacing="0" cellpadding="0" class="checkout_table">
				  	<tr>
					    <td class="col-lg-1"><label class="control-label"><spring:message code="label.sales.pm"/> #${numPM}</label></td>
					    <td>
					    <div class="col-lg-5 margin-left">
						    <table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
							    	<td class="col-lg-3">
							    		<form:select class="selectpicker form-control pm_select${numPM}" path="paymentMethods[${numPM}].id">
											<c:if test="${numPM != 1 }">
									        	<form:option value=""><spring:message code="label.option.select.single"/></form:option>
									        </c:if>
											<c:forEach var="pm" items="${pmList }">
												<form:option value="${pm.id }">${pm.name }</form:option>
										    </c:forEach>
										</form:select>
									</td>
							    	<td class="col-lg-2">
							    		<form:input path="paymentMethods[${numPM}].value" value="" class="form-control paymentMethods${numPM }Val"/>
										<form:hidden path="paymentMethods[${numPM}].key" class="form-control" value="${numPM }"/>
									</td>
							  	</tr>
							</table>
						</div>
						</td>
  					</tr>
				</table>
			</c:forEach>
		</div>

		<div class="print_invoice">
			<spring:message code="lable.sales.print.invoice"/>
			<input type="checkbox" name="printInvoiceCheckbox" id="printInvoiceCheckbox" value="0"/>
		</div>

		<div class="modal-footer content_in_details">
		    <div class="bootstrap-dialog-footer">
		        <div class="bootstrap-dialog-footer-buttons">
		        	<input type="hidden" name="memberId" id="memberId" class="form-control"/>
	                <input type="hidden" name="username" id="username" class="form-control"/>
	                <input type="hidden" name="purchaseDate" id="purchaseDate" class="form-control"/>
	                <input type="hidden" name="shopId" id="shopId" class="form-control"/>
	                <input type="hidden" name="remarks" id="remarks" class="form-control"/>
	                <input type="hidden" name="subTotal" id="subTotal" class="form-control"/>
	                <input type="hidden" name="showRemarksInInvoice" id="showRemarksInInvoice" class="form-control"/>
	                <input type="hidden" name="isHotelGuest" id="isHotelGuest" class="form-control"/>
					<input type="hidden" name="finalAmount" id="finalAmount" class="form-control"/>
                    <input type="hidden" name="printInvoice" id="printInvoice" class="form-control" value="false"/>
                    <button type="button" class="btn btn-default formPageSubmit hide checkoutBtn" data-skip-validate="true" id="submit">
						<spring:message code="label.sales.checkout"/>
					</button>
		        </div>
		    </div>
		</div>
	</form:form>
</div>


<script type="text/javascript">
    $(document).ready(function () {

    	$('#myTab a:first').tab('show');//初始化显示哪个tab

        $('#myTab a').click(function (e) {
          	e.preventDefault();//阻止a链接的跳转行为
          	$(this).tab('show');//显示当前选中的链接及关联的content
        });

    	//shop
    	$('#shopIdDisplay').change(function () {
    		//therapist
    		$.each($('.suitabledTherapists'), function (index) {
    			var obj=$(this);
    			var objName=obj.attr('name');
    			if(index==0 || objName=='tipsTherapist'){
    				$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopIdDisplay').val()},function (res) {
        				obj.html(res);
        	        });
    			}else{
    				$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopIdDisplay').val(),showAll:false},function (res) {
        				obj.html(res);
        	        });
    			}
            });
    		//clear cart item
    		 $('#itemCart tbody').find('tr').each(function () {
    	         $(this).remove();
    	     });
        }).trigger('change');

		//walk guest
		 $('.walkInGuest').click(function(){
		     $.ajax({
	             url: '<c:url value="/member/walkInGuest"/>',
	             type: "POST",
	             dataType: "json",
	             success: function(data) {
	            	 if(data.errorFields.length > 0) {
		         		$.each(data.errorFields, function (index, item) {
		         			if(item.fieldName =='username'){
		         				$('#username').attr("value", item.errorMessage);
		         				$('#fullName').attr("value", item.errorMessage);

		         			}
		         			if(item.fieldName =='id'){
		         				$('#memberId').attr("value", item.errorMessage);
		         			}
		         			getSuitablePackages();
		         		});
	             	}
	          	}
	     	});
		 });
		//init product option
		$('.select-category').selectCategory({});

		$('#purchaseDateDisplay').datetimepicker({
	        format: 'Y-m-d',
	        timepicker: false
	    });
	    $('.input-group-addon').click(function () {
	        var input = $(this).siblings('input').trigger('focus');
	    });

	  	//packages init
		getSuitablePackages();

	  	//add tips to item
		$('#addTipsItem').on('click',function (event) {
			event.preventDefault();// 禁止a连接的href事件

			clearEorroMsg();

			var idxVal=$('#addTipsItem').data('idx-id');
			if($('input[name="tipsAmount"]',$('#tipsTable')).filter(function(){return !this.value;}).length > 0) {
                Dialog.alert({
                    title: "Error",
                    message: "Please input amount!"
                });
                return;
            }
			$.ajax({
                url: '<c:url value="/sales/addTipsItems?idxId="/>'+idxVal+"&amount="+$('#tipsAmount').val()+"&staffId="+$('#tipsTherapist').val(),
                type: "POST",
                dataType: "text",
                success: function(response) {
               	 $("#itemCart tbody").append(response);
               	 $(".checkoutBtn").removeClass("hide");
               	 $(".alipayBtn").removeClass("hide");
               	 //clear
               	 var idx=parseInt(idxVal);
               	 idx++;
               	 $('#tipsTable').find('input[name^="tipsItemVOs"]').each(function(){
               		 replaceName(this,idx);
               	 });
               	 $('#idxId',$('#tipsTable')).attr('value',idx);
               	 calTotalAmount();
                }
         	});
		});
		//goods to add item
		$('#addGoodsItem').on('click',function (event) {
        	event.preventDefault();// 禁止a连接的href事件

        	clearEorroMsg();

        	var form = $('#goodsItemForm');

        	//check product
        	if($('input[name="productOptionId"]',form).filter(function(){return !this.value;}).length > 0) {
                Dialog.alert({
                    title: "Error",
                    message: "Please select product!"
                });
                return;
            }
        	var qty =parseInt($('.goodQty').val());
        	// check inventory
        	$.ajax({
	           url: '<c:url value="/product/checkInventory?productOptionId="/>'+$('input[name="productOptionId"]',form).val()+"&shopId="+$('#shopIdDisplay').val(),
	           type: "POST",
	           dataType: "json",
	           success: function(data) {
	        	   if(data > 0 && (data-qty) >= 0){
	        		   addGoodsItem(form);
	        	   }else{
	        		   BootstrapDialog.alert('Sorry,there is not enough inventory,qty is '+data+'.');
	        	   }
	           }
	        });

        });

        $('#itemCart').on('click', 'button.removeItemBtn', function () {
        	var idxIdRB = $(this).data('idx-id');
			var prepaidPaidAmt= $('#prepaidPaidAmt_'+idxIdRB).val();
			var url;

        	var packageId= $('#itemVOs\\['+idxIdRB+'\\]\\.paidByPackageId').val();
        	var voucherRef= $('#itemVOs\\['+idxIdRB+'\\]\\.paidByVoucherRef').val();
        	if(packageId !=''){
        		url = '<c:url value="/sales/removeItem?packageId="/>'+packageId+'&prepaidPaidAmt='+prepaidPaidAmt;
        	}
        	if(voucherRef !=''){
        		url = '<c:url value="/sales/removeItem?voucherRef="/>'+voucherRef+'&prepaidPaidAmt='+prepaidPaidAmt;
        	}

         	$.ajax({
         		url: url,
	           type: "POST",
	           dataType: "json",
	           success: function(data) {
	        	   //
	           }
	        });

	         $(this).parents('tr').remove();
	         calTotalAmount();
	     });


        $('.alipayBtn').click(function(){
            $(".alipaydiv").removeClass("hide");

        });


        $("#printInvoiceCheckbox").change(function() {
            var flag = $("#printInvoiceCheckbox").is(":checked");
            $('#printInvoice').attr('value',flag);
        });

     });

    function clearEorroMsg(){
    	$('#itemCart').find('.error_msg').each(function(){
    		this.remove();
    	});
    }

    function addGoodsItem(form){
    	var html = [];
   		var idxVal=$('#idxId',form).val();
		
       	html.push(String.format('<input type="hidden" class="productOptionIdClass" name="itemVOs[{0}].productOptionId" value="{1}"/>',idxVal, $('#productOptionId',form).val()));
        $("#itemCart tbody", getContext()).find('tr').each(function(index, item) {
            var $this = $(this);
            if($this.find('input.productOptionId') !=null){
            	 html.push(String.format('<input type="hidden" name="itemVOs[{0}].productOptionId" value="{1}"/>', index, $this.find('.productOptionId').val()));
                 html.push(String.format('<input type="hidden" name="itemVOs[{0}].qty" value="{1}"/>', index, $this.find('.qty').val()));
            }
        });
       	$(html.join('')).appendTo(form);
       	$.ajax({
               url: '<c:url value="/sales/addCartItems?idxId="/>'+idxVal+"&shopId="+$('#shopIdDisplay').val()+ "&memberId=" + $('#memberId').val()+"&purchaseDate="+$('#purchaseDateDisplay').val(),
               type: "POST",
               dataType: "text",
               data: form.serialize(),
               success: function(response) {
              	 $("#itemCart tbody").append(response);
              	 $(".checkoutBtn").removeClass("hide");
              	 $(".alipayBtn").removeClass("hide");
              	 //clear
              	 var idx=parseInt(idxVal);
              	 idx++;
              	 form.find('input[name^="itemVOs"]',form).each(function(){
              		 replaceName(this,idx);
              	 });
              	 form.find('select[name^="itemVOs"]',form).each(function(){
              		 replaceName(this,idx);
              	 });
              	 $('#idxId',form).attr('value',idx);
              	 $('.productOptionIdClass',form).remove();
              	 //fill the payment value by auto
              	 calTotalAmount();
               }
       	});
    }
    
    function addGoodsItemv1(form){
    	//this is the first version not contain the target or bundle discount
    	var html = [];
   		var idxVal=$('#idxId',form).val();

       	html.push(String.format('<input type="hidden" class="productOptionIdClass" name="itemVOs[{0}].productOptionId" value="{1}"/>',idxVal, $('#productOptionId',form).val()));
       	$(html.join('')).appendTo(form);
       	$.ajax({
               url: '<c:url value="/sales/addCartItems?idxId="/>'+idxVal+"&shopId="+$('#shopIdDisplay').val()+ "&memberId=" + $('#memberId').val()+"&purchaseDate="+$('#purchaseDateDisplay').val(),
               type: "POST",
               dataType: "text",
               data: form.serialize(),
               success: function(response) {
              	 $("#itemCart tbody").append(response);
              	 $(".checkoutBtn").removeClass("hide");
              	 $(".alipayBtn").removeClass("hide");
              	 //clear
              	 var idx=parseInt(idxVal);
              	 idx++;
              	 form.find('input[name^="itemVOs"]',form).each(function(){
              		 replaceName(this,idx);
              	 });
              	 form.find('select[name^="itemVOs"]',form).each(function(){
              		 replaceName(this,idx);
              	 });
              	 $('#idxId',form).attr('value',idx);
              	 $('.productOptionIdClass',form).remove();
              	 //fill the payment value by auto
              	 calTotalAmount();
               }
       	});
    }
    function replaceName(obj,idx){
    	var inputName= $(obj).attr('name');
		 var inpStrs=inputName.split(".");
		 var replaceName;
		 if(inpStrs.length==3){
			 replaceName="itemVOs["+idx+"]."+inpStrs[1]+"."+inpStrs[2];
		 }else{
			 replaceName="itemVOs["+idx+"]."+inpStrs[1];
		 }
		 $(obj).attr('name',replaceName);
    }

    function calTotalAmount(){
    	var totalAmount="0";
   	 	$('#itemCart').find('.finalAmount').each(function(){
    		var val=$(this).data('val');
    		totalAmount=parseFloat(totalAmount)+parseFloat(val);
    	});
   	 	$('.paymentMethods1Val').attr('value',totalAmount);
   	 	$('.totalamount_td').html('HK$'+totalAmount);
   	 	$('#subTotal').attr('value',totalAmount);
   	 	if(totalAmount == '0'){
	 		$(".pm_select1").val(4);
	 	}else{
	 		$(".pm_select1").val(17);
	 	}
    }

    function getSuitablePackages(){
    	$.post('<c:url value="/prepaid/suitabledPackagesSelect"/>',{memberId:$('#memberId').val(),productOptionId:$('#productOptionId').val()},function (res) {
 			$('.suitabledPackages').html(res);
        });
		$('.suitabledPackages').on("change",function(){
    		var suitabledPackagesVal=$(this).val();
    		if(suitabledPackagesVal == ''){
    			$(this).parent().next().children().removeAttr('readonly');
    		}else{
    			$(this).parent().next().children().attr('readonly','true');
    			$(this).parent().next().children().val('');
    		}
        });
	}
    function formPageBeforeSubmit(){
    	$('#shopId').attr('value',$('#shopIdDisplay').val());
    	// $('#purchaseDate').attr('value',$('#purchaseDateDisplay').val());
        $("#purchaseDate").val($('#purchaseDateDisplay').val());
    	$('#remarks').attr('value',$('#remarksDisplay').val());
    	var showRemarksInInvoiceVal= document.getElementById("showRemarksInInvoiceDisplay").checked;
    	$('#showRemarksInInvoice').attr('value',showRemarksInInvoiceVal);
    	var isHotelGuestVal= document.getElementById("isHotelGuestDisplay").checked;
    	$('#isHotelGuest').attr('value',isHotelGuestVal);
    	var finalAmount = $('.finalAmount').data('val');
        $('#finalAmount').attr('value',finalAmount);

    }
    function clientView() {
        $("#clientView").attr("href",'<c:url value="/book/clientView?userId="/>'+$("#memberId").val());
    }

</script>