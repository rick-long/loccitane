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
		z-index:1051;
	}
	
</style>

    	<c:url var="url" value="/prepaid/topUp"/>
		<form:form modelAttribute="prepaidAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
		 	<form:hidden path="id"/>
		 	<form:hidden path="isTopUp"/>
	   		<div id="baseStep" class="baseStep step active">
	        	<div class="form-group">
			    	<label class="col-lg-4 control-label"><spring:message code="label.reference"/>*</label>
					<div class="col-lg-5">
			 			<form:input path="reference" id="reference" class="form-control" readonly="true"/>
			    	</div>
				</div>
	        	<div class="form-group">
			    	<label class="col-lg-4 control-label"><spring:message code="label.client"/>*</label>
					<div class="col-lg-5">
			 			<form:input path="username" id="username" class="form-control" readonly="true"/>
			 			<form:hidden path="memberId" id="memberId" class="form-control" readonly="true"/>
			    	</div>
				</div>
				<div class="form-group">
		            <label class="col-lg-4 control-label"><spring:message code="label.shop"/>*</label>
		            <div class="col-lg-5">
		                <form:select path="shopId" class="selectpicker form-control" disabled="disabled">
		                    <c:forEach items="${shopList}" var="item">
		                        <form:option value="${item.id}">${item.name}</form:option>
		                    </c:forEach>
		                </form:select>
		                <form:hidden path="shopId"/>
		            </div>
		        </div>
				<div class="form-group">
			 		<label class="col-lg-4 control-label"><spring:message code="label.prepaid.type"/>*</label>
					<div class="col-lg-5">
			    		<form:select id="prepaidType" path="prepaidType" class="selectpicker form-control" disabled="true">
						   	<option value="TREATMENT_PACKAGE" <c:if test="${'TREATMENT_PACKAGE' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.TREATMENTPACKAGE"/></option>
						   	<option value="CASH_PACKAGE" <c:if test="${'CASH_PACKAGE' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.CASHPACKAGE"/></option>
						</form:select>
						<form:hidden path="prepaidType"/>
						<input type="hidden" id="prepaidTypeInit" value="${prepaidType}"/>
			    	</div>
				</div>
				<c:if test="${'TREATMENT_PACKAGE' == prepaidAddVO.prepaidType}">
					<div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.treatment.tree"/></label>
					    <div class="col-lg-5">
								
				<input type="hidden" id="categoryId" name="categoryId">
				<input type="hidden" id="productId" name="productId">
				<input type="hidden" id="productOptionId" name="productOptionId" value="${prepaidAddVO.productOptionId }">
				<input type="text" class="form-control quick-search select-treatment" placeholder="${prepaidAddVO.po.labelWithCodeNoBr}"/>
								
						</div>
				    </div>
				</c:if>
				<c:if test="${'CASH_PACKAGE' == prepaidAddVO.prepaidType}">
					<div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.prepaid.package.type"/></label>
	                    <div class="col-lg-5">
	                        <select class="selectpicker form-control suitabledPackageTypes" name="packageType" id="packageType">
					    		
							</select>
							<input type="hidden" id="packageTypeInit" value="${packageType}"/>
						</div>
					</div>
				</c:if>
			    <div class="form-group">
			       	<label class="col-lg-4 control-label"><spring:message code="label.prepaid.top.up.value"/>*</label>
			        <div class="col-lg-5">
			            <form:input path="prepaidValue" id="prepaidValue" class="form-control"/>
			        </div>
			    </div>
				<div class="form-group ">
					<label class="col-lg-4 control-label"><spring:message code="label.prepaid.init.units"/>*</label>
					<div class="col-lg-5">
						<form:input path="initValue" id="initValue" class="form-control"/>
					</div>
				</div>

			    <%-- <div class="form-group">
			        <label class="col-lg-4 control-label"><spring:message code="label.extra.discount"/></label>
			        <div class="col-lg-5">
			            <form:input path="extraDiscount" id="extraDiscount" class="form-control"/>
			        </div>
			    </div> --%>
			    <div class="form-group">
			        <label class="col-lg-4 control-label"><spring:message code="label.purchase.date"/>*</label>
			        <div class="col-lg-5">
			            <div class="input-group date form_time">
				        	<input id=purchaseDate name="purchaseDate" class="form-control purchaseDate" 
				        		value='<fmt:formatDate value="${purchaseDate}" pattern="yyyy-MM-dd"/>' size="16">
				            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
				        </div>
			        </div>
			    </div>
			    <div class="form-group">
			        <label class="col-lg-4 control-label"><spring:message code="label.prepaid.expiry.date"/>*</label>
			        <div class="col-lg-5">
			            <div class="input-group date form_time">
				        	<input id=expiryDate name="expiryDate" class="form-control expiryDate" 
				        		value='<fmt:formatDate value="${expiryDate12M}" pattern="yyyy-MM-dd"/>' size="16">
				        		<%-- <input type="hidden" id="expiryDate6M" value='<fmt:formatDate value="${expiryDate6M}" pattern="yyyy-MM-dd"/>'>
								<input type="hidden" id="expiryDate12M" value='<fmt:formatDate value="${expiryDate12M}" pattern="yyyy-MM-dd"/>'> --%>
				            <span class="input-group-addon" id="expiryDateSpan"><span class="glyphicon glyphicon-time"></span></span>
				        </div>
			        </div>
			    </div>
			    <c:forEach var="numTherapist"  begin="1" end="${numberOfTherapistUsed}">
			    	<div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.therapist"/> ${numTherapist}</label>
					    <div class="col-lg-5">
					    	<select class="selectpicker form-control suitabledTherapists" name="therapists[${numTherapist }].id" id="therapists[${numTherapist }].id">
					    		<c:if test="${numTherapist != 1 }">
				            		<option value=""><spring:message code="label.option.select.single"/></option>
				            	</c:if>
					            <c:forEach var="therapistObj" items="${therapistList }">
					            	<option value="${therapistObj.id }">${therapistObj.username }</option>
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
								    <td width="49%" >
								    	<form:select class="selectpicker form-control" path="paymentMethods[${numPM}].id">
											<c:if test="${numPM != 1 }">
												<form:option value=""><spring:message code="label.option.select.single"/></form:option>
											</c:if>
											<c:forEach var="pm" items="${pmList }">
												<form:option value="${pm.id }">${pm.name }</form:option>
											</c:forEach>
										</form:select>
									</td>
								    <td width="49%">
								    	<form:input path="paymentMethods[${numPM}].value" value="" class="form-control paymentMethods${numPM}Val"/>
										<form:hidden path="paymentMethods[${numPM}].key" class="form-control" value="${numPM }"/>
									</td>
							  	</tr>
							</table>
						</div>
					</div>
				</c:forEach>
				
				<div class="form-group">
			    	<label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
					<div class="col-lg-5">
			 			<form:textarea path="ptRemarks" id="ptRemarks" class="form-control"/>
			    	</div>
				</div>
	    	</div>
	    	<div id="calCommStep" class="calCommStep step"></div>
	    	<div class="modal-footer">
			    <div class="bootstrap-dialog-footer">
			        <div class="bootstrap-dialog-footer-buttons">
		          		<button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
                		<button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
               			<button type="button" class="btn btn-default dialogSubmitBtn" id="submit"><spring:message code="label.button.save"/></button>
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
    			if(index==0 || objName=='tipsTherapist'){
    				$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val()},function (res) {
        				obj.html(res);
        	        });
    			}else{
    				$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),showAll:false},function (res) {
        				obj.html(res);
        	        });
    			}
            });
        }).trigger('change');
      
    	$('.suitabledPackageTypes',Dialog.getContext()).change(function () {
		 	var obj=$(this);
		 	var prepaidTypeInit=$("#prepaidTypeInit").val();
		 	var packageType=$("#packageType").val();
		 	var packageTypeInit=$("#packageTypeInit").val();
		 	if(packageType !=null){
		 		packageTypeInit = packageType;
		 	}
			$.post('<c:url value="/brand/suitabledPackageTypes"/>',{prepaidType:prepaidTypeInit,packageTypeInit:packageTypeInit},function (res) {
				obj.html(res);
       		});
	 	}).trigger('change');
    	
    	
    	 var pType = $('#prepaidType',Dialog.getContext()).val();
    	 /* var expiryDate6M=$('#expiryDate6M', Dialog.getContext()).val();
         var expiryDate12M=$('#expiryDate12M', Dialog.getContext()).val(); */
		/* if('TREATMENT_PACKAGE' == pType || 'CASH_PACKAGE' == pType){
			$('#expiryDate', Dialog.getContext()).val(expiryDate12M);
		}else{
			$('#expiryDate', Dialog.getContext()).val(expiryDate6M);
		} */
			
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
	    
	  	//prepaid value bind event
   	 	$('#prepaidValue').bind('change', function(){
        	$('.paymentMethods1Val').val($(this).val());
     	});
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