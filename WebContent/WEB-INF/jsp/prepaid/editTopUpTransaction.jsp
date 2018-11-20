<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<style>
    .form-group {
        display: block;
    }

    .form-group.form-group-inactive {
        display: none;
    }
	
	.ui-autocomplete{
		max-height:200px;
		overflow-y:scroll;
		overflow-x:hidden;
		position:absolute;
		z-index:1071;
	}
	
</style>


    	<c:url var="url" value="/prepaid/editTopUpTransaction"/>
		<form:form modelAttribute="prepaidAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
	   		<div id="baseStep" class="baseStep step active">
	   			<div class="form-group">
			    	<label class="col-lg-4 control-label"><spring:message code="label.reference"/>*</label>
					<div class="col-lg-5">
			 			<input type="text" name="ptRef" id="ptRef" class="form-control" value="${ptut.topUpReference }" readonly/>
			 			<input type="hidden" name="id" id="id" class="form-control" value="${prepaid.id }"/>
			 			<input type="hidden" name="reference" id="reference" class="form-control" value="${prepaid.reference }"/>
			 			<input type="hidden" name="ptId" id="ptId" class="form-control" value="${ptut.id }"/>
			    	</div>
				</div>
	        	<div class="form-group">
			    	<label class="col-lg-4 control-label"><spring:message code="label.client"/>*</label>
					<div class="col-lg-5">
			 			<div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
	                     	<input type="hidden" name="memberId" id="memberId" class="form-control" value="${prepaid.user.id }"/>
	                     	<input type="hidden" name="username" id="username" class="form-control" value="${prepaid.user.username }"/>
	                     	<input type="text" name="fullName" id="fullName" class="form-control quick-search" value="${prepaid.user.fullName }" readonly/>
		                     <span class="input-group-addon">
		                         <span class="glyphicon glyphicon-search"></span>
		                     </span>
	                 	</div>
			    	</div>
				</div>
				<div class="form-group">
		            <label class="col-lg-4 control-label"><spring:message code="label.shop"/>*</label>
		            <div class="col-lg-5">
			 			<select name="shopId" id="shopId" class="selectpicker form-control">
		                    <c:forEach items="${shopList}" var="item">
		                        <option value="${item.id}" <c:if test="${item.reference eq ptut.shop.reference}">selected</c:if>>${item.name}</option>
		                    </c:forEach>
		               	</select>
		            </div>
		        </div>
				<div class="form-group">
			 		<label class="col-lg-4 control-label"><spring:message code="label.prepaid.type"/>*</label>
					<div class="col-lg-5">
			 			<select id="prepaidType" name="prepaidType" class="selectpicker form-control" disabled="true">
							<option value="CASH_PACKAGE" <c:if test="${'CASH_PACKAGE' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.CASHPACKAGE"/></option>
						   	<option value="TREATMENT_PACKAGE" <c:if test="${'TREATMENT_PACKAGE' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.TREATMENTPACKAGE"/></option>
						</select>
						<input type="hidden" name="prepaidType" id="prepaidType" value="${ptut.prepaidType }"/>
			    	</div>
				</div>
				<div class="form-group categoryTreeMenu">
					<label class="col-lg-4 control-label"><spring:message code="label.treatment.tree"/></label>
				    <div class="col-lg-5">
							
				<input type="hidden" id="categoryId" name="categoryId">
				<input type="hidden" id="productId" name="productId">
				<input type="hidden" id="productOptionId" name="productOptionId" value="${prepaidAddVO.productOptionId }">
				<input type="text" class="form-control quick-search select-treatment" placeholder="${prepaidAddVO.po.labelWithCodeNoBr}"/>
							
					</div>
			    </div>
                <div class="form-group packageType form-group-inactive">
					<label class="col-lg-4 control-label"><spring:message code="label.prepaid.package.type"/></label>
	                <div class="col-lg-5">
	                	<select class="selectpicker form-control suitabledPackageTypes" name="packageType" id="packageType">
					    		
						</select>
						<input type="hidden" id="packageTypeInit" value="${ptut.packageType}"/>
					</div>
	            </div>
			    <div class="form-group ">
			        <label class="col-lg-4 control-label"><spring:message code="label.prepaid.top.up.value"/>*</label>
			        <div class="col-lg-5">
			           <input type="text" name="prepaidValue" id="prepaidValue" class="form-control" value="${ptut.topUpValue }"/>
			        </div>
			    </div>
			    
			    <div class="form-group initValue">
			        <label class="col-lg-4 control-label"><spring:message code="label.prepaid.init.units"/>*</label>
			        <div class="col-lg-5">
			            <input type="text" name="initValue" id="initValue" class="form-control" value="${ptut.topUpInitValue}"/>
			        </div>
			    </div>
			    <div class="form-group">
			        <label class="col-lg-4 control-label"><spring:message code="label.prepaid.remain.value"/>*</label>
			        <div class="col-lg-5">
			         	<input type="text" name="remainValue" id="remainValue" class="form-control" value="${ptut.remainValue}"/>
			        </div>
			    </div>
			    <%-- <div class="form-group">
			        <label class="col-lg-4 control-label"><spring:message code="label.extra.discount"/></label>
			        <div class="col-lg-5">
			        	<input type="text" name="extraDiscount" id="extraDiscount" class="form-control" value="${ptut.extraDiscount}"/>
			        </div>
			    </div> --%>
			    <div class="form-group">
			        <label class="col-lg-4 control-label"><spring:message code="label.purchase.date"/>*</label>
			        <div class="col-lg-5">
			            <div class="input-group date form_time">
				        	<input id=purchaseDate name="purchaseDate" class="form-control purchaseDate" 
				        		value='<fmt:formatDate value="${ptut.topUpDate}" pattern="yyyy-MM-dd"/>' size="16">
				            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
				        </div>
			        </div>
			    </div>
			    <div class="form-group">
			        <label class="col-lg-4 control-label"><spring:message code="label.prepaid.expiry.date"/>*</label>
			        <div class="col-lg-5">
			            <div class="input-group date form_time">
				        	<input id=expiryDate name="expiryDate" class="form-control expiryDate" 
				        		value='<fmt:formatDate value="${ptut.expiryDate}" pattern="yyyy-MM-dd"/>' size="16">
				            <span class="input-group-addon" id="expiryDateSpan"><span class="glyphicon glyphicon-time"></span></span>
				        </div>
			        </div>
			    </div>
			    <c:forEach var="numTherapist"  begin="1" end="${numberOfTherapistUsed}">
			    	<div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.therapist"/> ${numTherapist}</label>
					    <div class="col-lg-5">
					   		<c:if test="${not empty therapistMap[numTherapist]}">
					    		<input type="hidden" value="${therapistMap[numTherapist].id}" id="${numTherapist}selected">
					    	</c:if>
						    <select class="selectpicker form-control suitabledTherapists" name="therapists[${numTherapist }].id" id="therapists[${numTherapist }].id">
					    		<c:if test="${numTherapist != 1 }">
				            		<option value=""><spring:message code="label.option.select.single"/></option>
				            	</c:if>
				            	
					    		<c:forEach var="therapistObj" items="${therapistList }">
					            	<option value="${therapistObj.id }" 
					            		<c:if test="${not empty therapistMap[numTherapist] && therapistMap[numTherapist].id ==therapistObj.id}">selected</c:if>>
					            		${therapistObj.username }--${therapistMap[numTherapist].id }
					            	</option>
					            </c:forEach>
					        </select>
					        <form:hidden path="therapists[${numTherapist}].key" value="${numTherapist}"/>
						</div>
					</div>
				</c:forEach>
				<c:forEach var="numPM"  begin="1" end="${numberOfPMUsed}">
			    	<div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.payment.method"/>${numPM}</label>
					    <div class="col-lg-5">
					    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="popup_payment">
						  	<tr>
						    	<td width="49%">
							    	<select class="selectpicker form-control" name="paymentMethods[${numPM }].id" id="paymentMethods[${numPM }].id">
							    		<c:if test="${numPM != 1 }">
						            		<option value=""><spring:message code="label.option.select.single"/></option>
						            	</c:if>
							    		<c:forEach var="pm" items="${pmList }">
							            	<option value="${pm.id }" 
							            		<c:if test="${not empty paymentMethodMap[numPM] && paymentMethodMap[numPM].id ==pm.id}">selected</c:if>>
							            		${pm.name }
							            	</option>
							            </c:forEach>
							        </select>
							    </td>
						    	<td width="49%">
							        <form:input path="paymentMethods[${numPM}].value" value="${paymentMethodMap[numPM].value }" class="form-control paymentMethods${numPM}Val"/>
							        <form:hidden path="paymentMethods[${numPM}].key" value="${numPM}"/>
							    </td>
						  	</tr>
						</table>
						</div>
					</div>
				</c:forEach>
				<div class="form-group">
					<label class="col-lg-4 control-label"><spring:message code="label.isactive"/></label>
                    <div class="col-lg-5">
                        <select class="selectpicker form-control" name="isActive" id="isActive">
                        	<option value="true" <c:if test="${ptut.isActive}">selected</c:if>><spring:message code="label.option.yes"/></option>
			                <option value="false" <c:if test="${!ptut.isActive}">selected</c:if>><spring:message code="label.option.no"/>
                        </select>
					</div>
                </div>
				<div class="form-group">
			    	<label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
					<div class="col-lg-5">
			 			<textarea name="ptRemarks" id="ptRemarks" class="form-control">${ptut.remarks}</textarea>
			    	</div>
				</div>
	    	</div>
	    	<div id="calCommStep" class="calCommStep step"></div>
	    	
	    	<div class="modal-footer">
			    <div class="bootstrap-dialog-footer">
			        <div class="bootstrap-dialog-footer-buttons">
		          		<button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
                		<button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
               			<button type="button" class="btn btn-default" id="submit"><spring:message code="label.button.save"/></button>
			       </div>
			   </div>
			</div>
    	</form:form>
    	
<script type="text/javascript">
	
    $(document).ready(function () {
    	
    	$('.select-category', Dialog.getContext()).selectCategory({});
    	
    	// 註冊對話框的提交事件
        var previousBtn = $('#previous', Dialog.getContext());
        var nextBtn = $('#next', Dialog.getContext());
        var submitBtn = $('#submit', Dialog.getContext());
        previousBtn.hide();
        submitBtn.hide();
      //shop
    	$('#shopId',Dialog.getContext()).change(function () {
    		//therapist
    		$.each($('.suitabledTherapists',Dialog.getContext()), function (index) {
    			var obj=$(this);
    			var objName=obj.attr('name');
    			index = index + 1;
    			var idVal=index+"selected";
    			var initialVal=$('#'+idVal,Dialog.getContext()).val();
    			if(index==1 || objName=='tipsTherapist'){
    				$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),initialValue:initialVal},function (res) {
        				obj.html(res);
        	        });
    			}else{
    				$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),initialValue:initialVal,showAll:false},function (res) {
        				obj.html(res);
        	        });
    			}
            });
    		
        }).trigger('change');
      	
    	//prepaid type onchange 事件
       	$('#prepaidType',Dialog.getContext()).change(function () {	
			var categoryTreeMenu=$('.categoryTreeMenu', Dialog.getContext());
			
			var isTransferFormGroup=$('.isTransfer', Dialog.getContext());
			var isFreeFormGroup=$('.isFree', Dialog.getContext());
    		
			var prepaidType=$(this).val();
        	if('TREATMENT_PACKAGE' == prepaidType || 'TREATMENT_VOUCHER' == prepaidType){
        		//show categoty relations
        		if (categoryTreeMenu.hasClass('form-group-inactive')) {categoryTreeMenu.removeClass('form-group-inactive');}
        		
        		if('TREATMENT_VOUCHER' == prepaidType){
	                //show is transfer
	          		if (isTransferFormGroup.hasClass('form-group-inactive')) {isTransferFormGroup.removeClass('form-group-inactive');}
	                // show is free
	          		if (isFreeFormGroup.hasClass('form-group-inactive')) {isFreeFormGroup.removeClass('form-group-inactive');}
	                
	          		if ($('.initValue', Dialog.getContext()).hasClass('form-group-inactive')){}else{$('.initValue', Dialog.getContext()).addClass('form-group-inactive');}
	            }else{
	            	//hide is transfer
	            	if (isTransferFormGroup.hasClass('form-group-inactive')){}else{isTransferFormGroup.addClass('form-group-inactive');}
	            	//hide is free
	            	if (isFreeFormGroup.hasClass('form-group-inactive')){}else{isFreeFormGroup.addClass('form-group-inactive');}
	            	
	            	if ($('.initValue', Dialog.getContext()).hasClass('form-group-inactive')){$('.initValue', Dialog.getContext()).removeClass('form-group-inactive');}
	            }
        		//hide package type
        		if ($('.packageType', Dialog.getContext()).hasClass('form-group-inactive')){}else{$('.packageType', Dialog.getContext()).addClass('form-group-inactive');}
        	}else{
        		//hide all category relations
        		if (isTransferFormGroup.hasClass('form-group-inactive')){}else{isTransferFormGroup.addClass('form-group-inactive');}
        		
        		if (isFreeFormGroup.hasClass('form-group-inactive')){}else{isFreeFormGroup.addClass('form-group-inactive');}
        		
        		if (categoryTreeMenu.hasClass('form-group-inactive')){}else{categoryTreeMenu.addClass('form-group-inactive');}
        		
        		/* if ($('.initValue', Dialog.getContext()).hasClass('form-group-inactive')){}else{$('.initValue', Dialog.getContext()).addClass('form-group-inactive');} */
        		
        		if('CASH_PACKAGE' == prepaidType){
        			//show package type
        			if ($('.packageType', Dialog.getContext()).hasClass('form-group-inactive')){$('.packageType', Dialog.getContext()).removeClass('form-group-inactive');}
        		}else{
        			if ($('.packageType', Dialog.getContext()).hasClass('form-group-inactive')){}else{$('.packageType', Dialog.getContext()).addClass('form-group-inactive');}
        		}
        	}
        	
        	$('.suitabledPackageTypes',Dialog.getContext()).change(function () {
    		 	var obj=$(this);
    		 	var packageType=$("#packageType").val();
    		 	var packageTypeInit=$("#packageTypeInit").val();
    		 	if(packageType !=null){
    		 		packageTypeInit = packageType;
    		 	}
    			$.post('<c:url value="/brand/suitabledPackageTypes"/>',{prepaidType:prepaidType,packageTypeInit:packageTypeInit},function (res) {
    				obj.html(res);
           		});
    	 	}).trigger('change');
        	
    	}).trigger('change');
    	
        var pType = $('#prepaidType',Dialog.getContext()).val();
        if((pType =='TREATMENT_PACKAGE') || pType ==('TREATMENT_VOUCHER')){
	        //validator
	        $('form', Dialog.getContext()).bootstrapValidator({
	            message: '<spring:message code="label.errors.is.not.valid"/>',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                username: {
	                    message: '<spring:message code="label.errors.is.not.valid"/>',
	                    validators: {
	                        notEmpty: {
	                            message: '<spring:message code="label.errors.is.required"/>'
	                        }
	                    }
	                },
	                prepaidValue: {
	                    message: '<spring:message code="label.errors.is.not.valid"/>',
	                    validators: {
	                        notEmpty: {
	                            message: '<spring:message code="label.errors.is.required"/>'
	                        },
	                        regexp: {
	                            regexp: /^[1-9]+(.[0-9]{2})?/,
	                            message: '<spring:message code="label.double.regexp"/>'
	                        }
	                    }
	                },
	                purchaseDate: {
	                    message: '<spring:message code="label.errors.is.not.valid"/>',
	                    validators: {
	                        notEmpty: {
	                            message: '<spring:message code="label.errors.is.required"/>'
	                        }
	                    }
	                },
	                expiryDate: {
	                    message: '<spring:message code="label.errors.is.not.valid"/>',
	                    validators: {
	                        notEmpty: {
	                            message: '<spring:message code="label.errors.is.required"/>'
	                        }
	                    }
	                },
            	   	initValue: {
                       message: '<spring:message code="label.errors.is.not.valid"/>',
                       validators: {
                    	   notEmpty: {
                               message: '<spring:message code="label.errors.is.required"/>'
                           },
                           regexp: {
                        	   regexp: /^[1-9]+(.[0-9]{2})?/,
                               message: '<spring:message code="label.double.regexp"/>'
                           }
                       }
                   	},
	                captcha: {
	                    validators: {
	                        callback: {
	                        }
	                    }
	                }
	            }
	        });
    	}else{
    		 //validator
	        $('form', Dialog.getContext()).bootstrapValidator({
	            message: '<spring:message code="label.errors.is.not.valid"/>',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                username: {
	                    message: '<spring:message code="label.errors.is.not.valid"/>',
	                    validators: {
	                        notEmpty: {
	                            message: '<spring:message code="label.errors.is.required"/>'
	                        }
	                    }
	                },
	                prepaidValue: {
	                    message: '<spring:message code="label.errors.is.not.valid"/>',
	                    validators: {
	                        notEmpty: {
	                            message: '<spring:message code="label.errors.is.required"/>'
	                        },
	                        regexp: {
	                        	regexp: /^[1-9]+(.[0-9]{2})?/,
	                            message: '<spring:message code="label.double.regexp"/>'
	                        }
	                    }
	                },
	                purchaseDate: {
	                    message: '<spring:message code="label.errors.is.not.valid"/>',
	                    validators: {
	                        notEmpty: {
	                            message: '<spring:message code="label.errors.is.required"/>'
	                        }
	                    }
	                },
	                expiryDate: {
	                    message: '<spring:message code="label.errors.is.not.valid"/>',
	                    validators: {
	                        notEmpty: {
	                            message: '<spring:message code="label.errors.is.required"/>'
	                        }
	                    }
	                },
	                captcha: {
	                    validators: {
	                        callback: {
	                        }
	                    }
	                }
	            }
	        });
    	}
       	
	    $('#purchaseDate', Dialog.getContext()).datetimepicker({
	        format: 'Y-m-d',
	        timepicker: false
	    });
	    $('.input-group-addon', Dialog.getContext()).click(function () {
	        var input = $(this).siblings('input').trigger('focus');
	    });
	    
	    $('#expiryDate', Dialog.getContext()).datetimepicker({
	        format: 'Y-m-d',
	        timepicker: false
	    });
	    $('#expiryDateSpan', Dialog.getContext()).click(function () {
	        var input = $(this).siblings('input').trigger('focus');
	    });
    
	    nextBtn.click(function () {
	    	var currentStep = $('.step.active', Dialog.getContext());
	        var nextStep = currentStep.next('.step');
	        var form = $('#defaultForm', Dialog.getContext());
	        
	        if (currentStep.hasClass('baseStep')) {
		        var bootstrapValidator = form.data('bootstrapValidator');
		        bootstrapValidator.validate(); // 验证
		        var result = bootstrapValidator.isValid(); // 取出结果
		        if (!result) {
		        	return;
		        }
                $.ajax({
                   url: '<c:url value="/prepaid/calCommissionRate"/>',
                   type: "POST",
                   dataType: "text",
                   data: form.serialize(),
                   success: function(response) {
	              	   	nextStep.html(response);
		                currentStep.removeClass('active');
		                nextStep.addClass('active');
		                submitBtn.show();
		                nextBtn.hide();
		                previousBtn.show();
                   }
                });
        	}
		});
	    
	    previousBtn.click(function () {
	         submitBtn.hide();
	         var currentStep = $('.step.active', Dialog.getContext());
	         var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
	         if (previousStep.hasClass('baseStep')) {
	             previousBtn.hide();
	             nextBtn.show();
	         } else {
	             nextBtn.show();
	         }
	     });
	    
	    submitBtn.click(function () {
	    	var form = $('#defaultForm', Dialog.getContext());
	    	$.ajax({
                url: '<c:url value="/prepaid/editTopUpTransaction"/>',
                type: "POST",
                dataType: "text",
                data: form.serialize(),
                success: function(response) {
                   	/* BootstrapDialog.alert(response.message); */
                   	
                 	Dialog.get().close(); // 关闭对话框
                 	setTimeout(function(){
                 		
                 		Dialog.get().close();
                 		$('a.search-btn').trigger('click'); // 触发重新查询事件 */
                 	},500);
                }
             });
    	});
	  	//prepaid value bind event
   	 	$('#prepaidValue').change(function () {	
        	$('.paymentMethods1Val').val($(this).val());
     	}).trigger('change');
});

	var autocomplete_options={
		delay: 800,
	  source: function(request, response){
		 $.ajax({
			url: '<c:url value="/po/searchProductOptionList"/>',
			dataType: "json",
			data:{
				code: request.term
			},
			success: function( data ) {
				if(data.code!=200){
					response([]);
					return;
				}
				if(! data.messages || ! data.messages.successMsg || data.messages.successMsg.length<1){
					response([]);
					return;
				}
				response( $.map( data.messages.successMsg, function( res ) {
					return {
						id:res.id,
						treatmentCode:res.treatmentCode,
						treatmentName:res.treatmentName,
						duration:res.duration,
						price:res.price,
						processTime: res.processTime,
						productId: res.productId,
						value: res.treatmentCode+' | '+res.treatmentName+' | '+res.duration+' mins '
					}
				}));
			}
     	}); 
		},
		select: function( event, ui ) {
			$(this).parent().find('input[name=productOptionId]').val(ui.item.id);
        }
	}
	
	var selector='input.select-treatment';
	$(document).on('keydown.autocomplete', selector, function() {
		$(this).autocomplete(autocomplete_options);
	});

</script>