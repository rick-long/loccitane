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


    	<c:url var="url" value="/prepaid/edit"/>
		<form:form modelAttribute="prepaidAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
	   		<div id="baseStep" class="baseStep step active">
	   			<div class="form-group">
			    	<label class="col-lg-4 control-label text-right"><spring:message code="label.reference"/>*</label>
					<div class="col-lg-5">
						<c:if test="${prepaidAddVO.prepaidType =='CASH_VOUCHER' || prepaidAddVO.prepaidType =='TREATMENT_VOUCHER' }">
			 				<form:input path="reference" id="reference" class="form-control"/>
			 			</c:if>
			 			<c:if test="${prepaidAddVO.prepaidType !='CASH_VOUCHER' && prepaidAddVO.prepaidType !='TREATMENT_VOUCHER'}">
			 				<form:input path="reference" id="reference" class="form-control" readonly="true"/>
			 			</c:if>
			    	</div>
				</div>
	        	<div class="form-group">
			    	<label class="col-lg-4 control-label text-right"><spring:message code="label.client"/>*</label>
					<div class="col-lg-5">
			 			<div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
	                     	<input type="hidden" name="memberId" id="memberId" class="form-control" value="${prepaidAddVO.memberId }"/>
	                     	<input type="hidden" name="username" id="username" class="form-control" value="${prepaidAddVO.username }"/>
	                     	<input type="text" name="fullName" id="fullName" class="form-control quick-search" value="${prepaidAddVO.member.fullName }" readonly/>
		                     <span class="input-group-addon">
		                         <span class="glyphicon glyphicon-search"></span>
		                     </span>
	                 	</div>
						<a id="clientView" data-permission="book:clientView" onclick="clientView()"  title='<spring:message code="label.button.view"/>'
						   class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="950"
						   data-title='<spring:message code="label.button.view"/>' style="display: inline;position:absolute;right:-7px;top:3px">
							<i class="glyphicon glyphicon-eye-open"></i>
						</a>
			    	</div>
				</div>

				<div class="form-group">
		            <label class="col-lg-4 control-label text-right"><spring:message code="label.shop"/>*</label>
		            <div class="col-lg-5">
			 			<form:select path="shopId" class="selectpicker form-control">
		                    <c:forEach items="${shopList}" var="item">
		                        <form:option value="${item.id}">${item.name}</form:option>
		                    </c:forEach>
		                </form:select>
		            </div>
		        </div>
				<div class="form-group">
			 		<label class="col-lg-4 control-label text-right"><spring:message code="label.prepaid.type"/>*</label>
					<div class="col-lg-5">
			 			<select id="prepaidType" name="prepaidType" class="selectpicker form-control">
			 				<c:choose>
			 					<c:when test="${'CASH_PACKAGE' eq prepaidType}">
			 						<option value="CASH_PACKAGE" <c:if test="${'CASH_PACKAGE' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.CASHPACKAGE"/></option>
			 					</c:when>
								<c:when test="${'TREATMENT_PACKAGE' eq prepaidType}">
								<option value="TREATMENT_PACKAGE" <c:if test="${'TREATMENT_PACKAGE' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.TREATMENTPACKAGE"/></option>
							</c:when>
								<c:when test="${'CASH_VOUCHER' eq prepaidType}">
								<option value="CASH_VOUCHER" <c:if test="${'CASH_VOUCHER' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.CASHVOUCHER"/></option>
							</c:when>
								<c:when test="${'TREATMENT_VOUCHER' eq prepaidType}">
									<option value="TREATMENT_VOUCHER" <c:if test="${'TREATMENT_VOUCHER' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.TREATMENTVOUCHER"/></option>
								</c:when>
								<%--<c:otherwise>
                                    <option value="CASH_PACKAGE" <c:if test="${'CASH_PACKAGE' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.CASHPACKAGE"/></option>
                                      <option value="TREATMENT_PACKAGE" <c:if test="${'TREATMENT_PACKAGE' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.TREATMENTPACKAGE"/></option>
                                   <option value="CASH_VOUCHER" <c:if test="${'CASH_VOUCHER' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.CASHVOUCHER"/></option>
                                      <option value="TREATMENT_VOUCHER" <c:if test="${'TREATMENT_VOUCHER' eq prepaidType}">selected</c:if>><spring:message code="label.prepaid.type.TREATMENTVOUCHER"/></option>
                                </c:otherwise>--%>
			 				</c:choose>
						</select>
			    	</div>
				</div>
				<div class="form-group categoryTreeMenu">
					<label class="col-lg-4 control-label text-right"><spring:message code="label.treatment.tree"/></label>

					<div class="col-lg-5">
							
				<input type="hidden" id="categoryId" name="categoryId">
				<input type="hidden" id="productId" name="productId">
				<input type="hidden" id="productOptionId" name="productOptionId" value="${prepaidAddVO.productOptionId }">
				<input type="text" class="form-control quick-search select-treatment" placeholder="${prepaidAddVO.po.labelWithCodeNoBr}"/>
							
							<span style="font-size: 85%;color: #a94442;"id="treeIsNONull"></span>
					</div>
					<input type="hidden" name="hidproductOptionId" id="hidproductOptionId" class="form-control" value="${prepaidAddVO.productOptionId }"/>
				</div>

				<%-- <div class="form-group">
					<label class="col-lg-4 control-label"><spring:message code="label.barcode"/> </label>
					<div class="col-lg-5">
						<form:input path="barCode" id="barCode" name="barCode" size="13" maxlength="13" class="form-control"/>
					</div>
					<input type="hidden" name="hidprepaidType" id="hidprepaidType" class="form-control" value="${prepaidAddVO.prepaidType }"/>
				</div>
 --%>
			    <div class="form-group isTransfer">
					<label class="col-lg-4 control-label text-right"><spring:message code="label.prepaid.is.transfer"/></label>
                    <div class="col-lg-5">
                        <form:select class="selectpicker form-control" path="isTransfer" id="isTransfer">
			            	<form:option value="true"><spring:message code="label.option.yes"/></form:option>
			                <form:option value="false"><spring:message code="label.option.no"/></form:option>
			            </form:select>
					</div>
                </div>
                <div class="form-group isFree">
					<label class="col-lg-4 control-label text-right"><spring:message code="label.prepaid.is.free"/></label>
                    <div class="col-lg-5">
                        <form:select class="selectpicker form-control" path="isFree" id="isFree">
			            	<form:option value="true"><spring:message code="label.option.yes"/></form:option>
			                <form:option value="false"><spring:message code="label.option.no"/></form:option>
			            </form:select>
					</div>
                </div>
                <div class="form-group packageType form-group-inactive">
					<label class="col-lg-4 control-label text-right"><spring:message code="label.prepaid.package.type"/></label>
	                <div class="col-lg-5">
	                	<select class="selectpicker form-control suitabledPackageTypes" name="packageType" id="packageType">
					    		
						</select>
						<input type="hidden" id="packageTypeInit" value="${packageType}"/>
					</div>
	            </div>
			    <div class="form-group ">
			        <label class="col-lg-4 control-label text-right"><spring:message code="label.prepaidValue"/>*</label>
			        <div class="col-lg-5">
			            <form:input path="prepaidValue" id="prepaidValue" class="form-control"/>
			        </div>
			    </div>
			    
			    <div class="form-group initValue">
			        <label class="col-lg-4 control-label text-right"><spring:message code="label.prepaid.init.units"/>*</label>
			        <div class="col-lg-5">
			            <form:input path="initValue" id="initValue" class="form-control"/>
			        </div>
			    </div>
			    
			    <div class="form-group remainValue form-group-inactive">
			        <label class="col-lg-4 control-label text-right"><spring:message code="label.prepaid.remain.value"/>*</label>
			        <div class="col-lg-5">
			            <form:input path="remainValue" id="remainValue" class="form-control"/>
			        </div>
			    </div>
			    <%-- <div class="form-group">
			        <label class="col-lg-4 control-label"><spring:message code="label.extra.discount"/></label>
			        <div class="col-lg-5">
			            <form:input path="extraDiscount" id="extraDiscount" class="form-control"/>
			        </div>
			    </div> --%>
			    <div class="form-group">
			        <label class="col-lg-4 control-label text-right"><spring:message code="label.purchase.date"/>*</label>
			        <div class="col-lg-5">
			            <div class="input-group date form_time">
				        	<input id=purchaseDate name="purchaseDate" class="form-control purchaseDate" 
				        		value='<fmt:formatDate value="${purchaseDate}" pattern="yyyy-MM-dd"/>' size="16">
				            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
				        </div>
			        </div>
			    </div>
			    <div class="form-group">
			        <label class="col-lg-4 control-label text-right"><spring:message code="label.prepaid.expiry.date"/>*</label>
			        <div class="col-lg-5">
			            <div class="input-group date form_time">
				        	<input id=expiryDate name="expiryDate" class="form-control expiryDate" 
				        		value='<fmt:formatDate value="${expiryDate}" pattern="yyyy-MM-dd"/>' size="16">
				            <span class="input-group-addon" id="expiryDateSpan"><span class="glyphicon glyphicon-time"></span></span>
				        </div>
			        </div>
			    </div>
			    <c:forEach var="numTherapist"  begin="0" end="${numberOfTherapistUsed-1}">
			    	<div class="form-group">
						<label class="col-lg-4 control-label text-right"><spring:message code="label.therapist"/> ${numTherapist +1}</label>
					    <div class="col-lg-5">
					    	<c:if test="${not empty therapistMap[numTherapist]}">
					    		<input type="hidden" value="${therapistMap[numTherapist].id}" id="${numTherapist}selected">
					    	</c:if>
					    	
						    <select class="selectpicker form-control suitabledTherapists" name="therapists[${numTherapist }].id" id="therapists[${numTherapist }].id">
					    		<c:if test="${numTherapist != 0 }">
				            		<option value=""><spring:message code="label.option.select.single"/></option>
				            	</c:if>
				            	
					    		<c:forEach var="therapistObj" items="${therapistList }">
					            	<option value="${therapistObj.id }" 
					            		<c:if test="${not empty therapistMap[numTherapist] && therapistMap[numTherapist].id == therapistObj.id}">selected</c:if>>
					            		${therapistObj.username }
					            	</option>
					            </c:forEach>
					        </select>
					        <form:hidden path="therapists[${numTherapist}].key" value="${numTherapist}"/>
						</div>
					</div>
				</c:forEach>
				<c:forEach var="numPM"  begin="1" end="${numberOfPMUsed}">
			    	<div class="form-group">
						<label class="col-lg-4 control-label text-right"><spring:message code="label.payment.method"/> ${numPM}</label>
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
					<label class="col-lg-4 control-label text-right"><spring:message code="label.isactive"/></label>
                    <div class="col-lg-5">
                        <form:select class="selectpicker form-control" path="isActive" id="isActive">
			            	<form:option value="true"><spring:message code="label.option.yes"/></form:option>
			                <form:option value="false"><spring:message code="label.option.no"/></form:option>
			            </form:select>
					</div>
                </div>
				<div class="form-group">
			    	<label class="col-lg-4 control-label text-right"><spring:message code="label.remarks"/></label>
					<div class="col-lg-5">
			 			<form:textarea path="remarks" id="remarks" class="form-control"/>
			    	</div>
				</div>


	    	</div>
	    	<div id="calCommStep" class="calCommStep step"></div>
	    	
	    	<div class="modal-footer">
			    <div class="bootstrap-dialog-footer">
			        <div class="bootstrap-dialog-footer-buttons">
			        	<form:hidden path="id"/>
		          		<button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
                		<button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
               			<button type="button" class="btn btn-default dialogSubmitBtn" id="submit"><spring:message code="label.button.save"/></button>
			       </div>
			   </div>
			</div>
    	</form:form>
    	
<script type="text/javascript">


    $(document).ready(function () {

    	$('.select-category', Dialog.getContext()).selectCategory({
        	callback: function (context) {
                // 注册变更事件
            },
            click: function() {
            	var form = $('#defaultForm', Dialog.getContext());
      			var prepaidType=$('#prepaidType',Dialog.getContext()).val();
                var poId=$(this).data('product-option-id');
        		if('TREATMENT_VOUCHER' == prepaidType){
	                var shopId=$('#shopId',form).val();
	        		$.ajax({
	                    url: '<c:url value="/po/rerurnPOPrice?poId="/>'+poId+'&shopId='+shopId,
	                    type: "POST",
	                    dataType: "text",
	                    data: form.serialize(),
	                    success: function(response) {
	                    	$('#prepaidValue').val(response);
	                    	$('.paymentMethods1Val').val(response);
	                    }
	                });
        		}
            }
    	});

    	
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
    			var idVal=index+"selected";
    			var initialVal=$('#'+idVal,Dialog.getContext()).val();
    			if(index==0 || objName=='tipsTherapist'){
    				$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),initialValue:initialVal,showAll:false},function (res) {
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
	          		if ($('.remainValue', Dialog.getContext()).hasClass('form-group-inactive')){}else{$('.remainValue', Dialog.getContext()).addClass('form-group-inactive');}
	          		
	            }else{
	            	//hide is transfer
	            	if (isTransferFormGroup.hasClass('form-group-inactive')){}else{isTransferFormGroup.addClass('form-group-inactive');}
	            	//hide is free
	            	if (isFreeFormGroup.hasClass('form-group-inactive')){}else{isFreeFormGroup.addClass('form-group-inactive');}
	            	
	            	if ($('.initValue', Dialog.getContext()).hasClass('form-group-inactive')){$('.initValue', Dialog.getContext()).removeClass('form-group-inactive');}
	            	if ($('.remainValue', Dialog.getContext()).hasClass('form-group-inactive')){$('.remainValue', Dialog.getContext()).removeClass('form-group-inactive');}
	            	
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
        			if ($('.remainValue', Dialog.getContext()).hasClass('form-group-inactive')){$('.remainValue', Dialog.getContext()).removeClass('form-group-inactive');}
        		}else{
        			if ($('.packageType', Dialog.getContext()).hasClass('form-group-inactive')){}else{$('.packageType', Dialog.getContext()).addClass('form-group-inactive');}
        			if ($('.remainValue', Dialog.getContext()).hasClass('form-group-inactive')){$('.remainValue', Dialog.getContext()).removeClass('form-group-inactive');}
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
		        
		        var remainValue = $('#remainValue').val();
		        var prepaidValue = $('#prepaidValue').val();
		        /* if(parseFloat(prepaidValue) < parseFloat(remainValue)){
		        	alert('Prepaid vlaue should be greater than remain value!');
		        	return;
		        } */
                var pType2 = $('#prepaidType',Dialog.getContext()).val();
                if((pType2 =='TREATMENT_PACKAGE') || pType2 ==('TREATMENT_VOUCHER')){
                    if ($('#categoryId', Dialog.getContext()).val() == '' && $('#productId', Dialog.getContext()).val() == '' && $('#productOptionId', Dialog.getContext()).val() == '') {
                        $("#treeIsNONull").html('<spring:message code="label.errors.is.not.valid"/> ');
                        return;
                    }

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


	  	
   	 	/* $('#remainValue').bind('change', function(){
     	$('.paymentMethods1Val').val($(this).val()); 
  		});*/


        var hidprepaidType=$("#hidprepaidType", Dialog.getContext()).val();
        if('TREATMENT_PACKAGE' == hidprepaidType || 'TREATMENT_VOUCHER' == hidprepaidType) {

            var poId = $("#hidproductOptionId", Dialog.getContext()).val();
            $.ajax({
                url: '<c:url value="/barcode/getBarCode"/>',
                type: "POST",
                dataType: "json",
                data: {"productOptionId": poId},
                success: function (response) {
                    var json = eval(response);
                    if (json.flag == false) {
                        BootstrapDialog.show({
                            title: '<spring:message code="label.notification"/>',
                            message: '<spring:message code="label.book.barcode.is.empty"/> '
                        });
                    }
                    $("#barCode", Dialog.getContext()).val(json.barCode);
                }
            });



            /* $("#barCode", Dialog.getContext()).keyup(function(){
                var barCode = $("#barCode", Dialog.getContext()).val();
                var length = barCode.length;
                if(length >= 13){
                    searchByBarCode(barCode);
                }
            }); */


            function searchByBarCode(barCode)
            {
                $.ajax({
                    url: '<c:url value="/barcode/searchByBarCode"/>',
                    type: "POST",
                    dataType: "json",
                    data: {"barCode":barCode},
                    success: function(response) {
                        var json = eval(response);
                        if (json.flag == false) {
                            BootstrapDialog.show({
                                title: '<spring:message code="label.notification"/>',
                                message: '<spring:message code="label.book.please.check.the.barcode"/>'
                            });
                        }
                        $("#productOptionId", Dialog.getContext()).val(json.productOptionId);
                        $("#categorySelect2_displayName", Dialog.getContext()).attr("value", json.displayName);
                    }
                });
            };
        }
});

    
    function formDialogBeforeSubmit(){
		 var form = $('#defaultForm', Dialog.getContext());
		 var commissionDisplay=form[0].commissionDispaly.value;
		 form[0].commissionRate.value=commissionDisplay;
	}
    function clientView() {
        $("#clientView").attr("href",'<c:url value="/book/clientView?userId="/>'+$('#memberId', Dialog.getContext()).val());
    }

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