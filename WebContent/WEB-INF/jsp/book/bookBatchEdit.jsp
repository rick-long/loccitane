<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style type="text/css">
    #scheduleAddContext table.time-panel tr td {
        width: auto;
        height: auto;
        margin: 0 auto;
        text-align: left;
        color: #000;
    }

    #scheduleAddContext table.time-panel tr td span {
        width: 32px;
        height: 32px;
        line-height: 32px;
        border-radius: 16px;
        text-align: center;
        display: inline-block;
        cursor: pointer;
    }

    #scheduleAddContext table.time-panel tr td span.active, #scheduleAddContext table.time-panel tr td span:hover {
        color: #fff;
        background-color: #5eb0c6;
        border-color: #5eb0c6;
    }

    #scheduleAddContext #weeksField table.time-panel tr td span {
        width: 50px;
        height: 50px;
        line-height: 50px;
        border-radius: 25px;
    }

    input[type=checkbox].check-box {
        height: 22px;
        min-width: 22px;
        line-height: 22px;
        display: inline-block;
        margin: 0 2px;
        vertical-align: middle;
    }

    .check-group {
        height: 36px;
        line-height: 36px;
        display: inline-block;
        vertical-align: middle;
    }
	
	.ui-autocomplete{
		max-height:200px;
		overflow-y:scroll;
		overflow-x:hidden;
		position:absolute;
		z-index:1051;
	}
	
</style>
<div class="management">
 <h3>
	<spring:message code="label.book.batch.edit"/>
</h3>
<c:url var="url" value="/bookBatch/editBookBatch"/>
<div id="scheduleAddContext">
    <form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" id="defaultForm"  class="form-horizontal">
    		<input type="hidden" value="${bookBatch.id }" name="id" id="id">
        <div id="baseStep" class="baseStep step active">
            <div class="form-group">
                <label class="col-lg-3 control-label"><spring:message code="label.client"/></label>
                <div class="col-lg-5">
		        	<div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
			           	<input type="hidden" name="memberId" id="memberId" class="form-control" value="${bookBatch.member.id }"/>
			           	<input type="hidden" name="username" id="username" class="form-control" value="${bookBatch.member.username }"/>
			          	<input type="text" name="fullName" id="fullName" class="form-control quick-search" value="${bookBatch.member.id }_${bookBatch.member.username }" readonly/>
			          	<span class="input-group-addon">
			      			<span class="glyphicon glyphicon-search"></span></span>
	                </div>
                </div>
            </div>
            <div class="form-group">
            	<label class="col-lg-3 control-label"><spring:message code="label.shop"/></label>
            	<div class="col-lg-5">
		         	<select id="shopId" name="shopId" class="selectpicker form-control">
		          		<c:forEach items="${shopList}" var="item">
		         			<option value="${item.id}" <c:if test="${bookBatch.shop.id == item.id }">selected</c:if>>${item.name}</option>
		         		</c:forEach>
		      		</select>
	      		</div>
            </div>
            <div class="form-group">
            	<label class="col-lg-3 control-label"><spring:message code="label.therapist"/></label>
            	<div class="col-lg-5">
		            <select class="selectpicker form-control suitabledTherapists" name="therapistId" id="therapistId">
		            		
					</select>
					<input id="therapistIdInit" value="${bookBatch.therapist.id }">
				</div>
			</div>
			<div class="form-group">
            	<label class="col-lg-3 control-label"><spring:message code="label.treatment.tree"/></label>
            	<div class="col-lg-5">
					
					<input type="hidden" id="categoryId" name="categoryId">
					<input type="hidden" id="productId" name="productId">
					<input type="hidden" name="duration" value="">
					<input type="hidden" name="processTime" value="">
					<input type="hidden" id="productOptionId" name="productOptionId" value="${bookBatch.productOption.id}">
					<input type="text" class="form-control quick-search select-treatment" placeholder="${bookBatch.productOption.labelWithCodeNoBr}"/>
					
				</div>
			</div>
            <div class="form-group">
                <label class="col-lg-3 control-label">
                    <spring:message code="label.repeat"/>
                </label>
                <div class="col-lg-5">
                    <select id="repeatType" name="repeatType" class="selectpicker form-control">
                        <option value="0" <c:if test="${bookBatch.repeatType eq '0' }">selected</c:if>><spring:message code="label.book.batch.repeat.0"/></option>
                        <option value="1" <c:if test="${bookBatch.repeatType eq '1' }">selected</c:if>><spring:message code="label.book.batch.repeat.1"/></option>
                        <option value="2" <c:if test="${bookBatch.repeatType eq '2' }">selected</c:if>><spring:message code="label.book.batch.repeat.2"/></option>
                    </select>
                </div>
            </div>

            <div id="monthsField" class="form-group hide">
                <label for="type" class="col-lg-3 control-label">
                    <spring:message code="label.months"/>
                </label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <div class="check-group">
                                    <input class="select-all check-box" type="checkbox"/>
                                    <spring:message code="label.select.all"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach begin="1" end="12" var="item">
                                    <span class='month-item <c:if test="${months.contains(item)}">active</c:if>' data-value="${item}"><spring:message code="label.repeat.booking.month.${item}"/></span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="weeksField" class="form-group hide">
                <label for="type" class="col-lg-3 control-label">
                    <spring:message code="label.weeks"/></label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <div class="check-group">
                                    <input class="select-all check-box" type="checkbox"/>
                                    <spring:message code="label.select.all"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach begin="1" end="7" var="item">
                                    <span class='week-item <c:if test="${weeks.contains(item)}">active</c:if>' data-value="${item}"><spring:message code="label.repeat.booking.week.${item}"/></span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="daysField" class="form-group hide">
                <label for="type" class="col-lg-3 control-label">
                    <spring:message code="label.days"/></label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <div class="check-group">
                                    <input class="select-all check-box" type="checkbox"/>
                                    <spring:message code="label.select.all"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach begin="1" end="31" var="item">
                                    <span class='day-item <c:if test="${days.contains(item)}">active</c:if>' data-value="${item}">${item}</span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            
            <div id="startDateField" class="form-group">
                <label class="col-lg-3 control-label"><spring:message code="label.repeat.start.date"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="repeatStartDate" name="repeatStartDate" class="form-control" value='<fmt:formatDate value="${bookBatch.startDate}" pattern="yyyy-MM-dd"/>' size="16" readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div id="endDateField" class="form-group">
                <label class="col-lg-3 control-label"><spring:message code="label.repeat.end.date"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="repeatEndDate" name="repeatEndDate" class="form-control" value='<fmt:formatDate value="${bookBatch.endDate}" pattern="yyyy-MM-dd"/>' size="16" readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div id="startTimeField" class="form-group">
                <label class="col-lg-3 control-label"><spring:message code="label.start.time"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="repeatStartTime" name="repeatStartTime" class="form-control" value='${bookBatch.startTime}' size="16"  readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div id="endTimeField" class="form-group">
                <label class="col-lg-3 control-label"><spring:message code="label.end.time"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="repeatEndTime" name="repeatEndTime" class="form-control" value='${bookBatch.endTime}' size="16"  readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div class='form-group'>
                <label for="remarks" class="col-lg-3 control-label">
                    <spring:message code="label.remarks"/></label>
                <div class="col-lg-5">
                    <textarea id="remarks" name="remarks" class="form-control"></textarea>
                </div>
            </div>
        </div>
        <div id="confirmStep" class="confirmStep step"></div>

        <div class="modal-footer">
            <div class="bootstrap-dialog-footer">
                <div class="bootstrap-dialog-footer-buttons">
                    <button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
                    <button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
                    <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
                        <spring:message code="label.book.status.save"/>
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        // 註冊對話框的提交事件
        $('#previous', Dialog.getContext()).hide();
        $('#dlgSubmit', Dialog.getContext()).hide();

        $('.select-category', Dialog.getContext()).selectCategory({});
        
        $('#shopId',Dialog.getContext()).change(function () {
            //therapist
        		var shopId=$(this).val();
            var therapistIdInit=('#therapistIdInit',Dialog.getContext()).val();
            $.post('<c:url value="/staff/staffSelectList"/>',{shopId:shopId,initialValue:therapistIdInit},function (res) {
            		if(therapistIdInit =='99999999'){
            			$('.suitabledTherapists',Dialog.getContext()).html('<option value="99999999" selected><spring:message code="front.label.any.therapist"/></option>'+res);
            		}else{
            			$('.suitabledTherapists',Dialog.getContext()).html('<option value="99999999"><spring:message code="front.label.any.therapist"/></option>'+res);
            		}
            		
            });
        }).trigger('change');
        
      //update End Time;
        $('#repeatStartTime',Dialog.getContext()).change(function(){
        	updateEndTime();
        });
	
        
       $('#next', Dialog.getContext()).click(function () {
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');

            if (currentStep.hasClass('baseStep')) {
                var bootstrapValidator = $('form', Dialog.getContext()).data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 重置formData
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }

                // 生成参数
                var html = [];
                var repeatType = $("#repeatType", Dialog.getContext()).val();
                
                $('form', Dialog.getContext()).find('input[name="days"]').remove();
                $('form', Dialog.getContext()).find('input[name="weeks"]').remove();
                $('form', Dialog.getContext()).find('input[name="months"]').remove();

                $('.day-item.active', Dialog.getContext()).each(function(){
                    html.push(String.format('<input type="hidden" name="days" value="{0}">',  $(this).data('value')));
                });
                $('.week-item.active', Dialog.getContext()).each(function(){
                    html.push(String.format('<input type="hidden" name="weeks" value="{0}">',  $(this).data('value')));
                });
                $('.month-item.active', Dialog.getContext()).each(function(){
                    html.push(String.format('<input type="hidden" name="months" value="{0}">',  $(this).data('value')));
                });
                

                $(html.join('')).appendTo($('form', Dialog.getContext()));
                $.post('<c:url value="/bookBatch/confirmBookBatch"/>', $('form', Dialog.getContext()).serialize(), function (res) {
                    nextStep.html(res);
                    var errorMessage = $('#error', Dialog.getContext()).val();
                    if (errorMessage) {
                        alert({
                            title: "<spring:message code="lable.error"/> ",
                            message: errorMessage
                        });
                    } else {
                        currentStep.removeClass('active');
                        nextStep.addClass('active');
                        $('#dlgSubmit', Dialog.getContext()).show();
                        $('#next', Dialog.getContext()).hide();
                        $('#previous', Dialog.getContext()).show();
                    }
                });
            }
        });

        $('#previous', Dialog.getContext()).click(function () {
            $('#dlgSubmit', Dialog.getContext()).hide();
            var currentStep = $('.step.active', Dialog.getContext());
            var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
            if (previousStep.hasClass('baseStep')) {
                $(this).hide();
            }
            $('#next', Dialog.getContext()).show();
        });
        

        $('#repeatStartDate, #repeatEndDate', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            datepicker: true,
            step: 5,
            value: '<fmt:formatDate value="${scheduleVO.startDateTime}" pattern="yyyy-MM-dd"/>'
        });

        $('#repeatStartTime, #repeatEndTime', Dialog.getContext()).datetimepicker({
            format: 'H:i',
            timepicker: true,
            datepicker: false,
            step: 5,
            value: '<fmt:formatDate value="${scheduleVO.startDateTime}" pattern="HH:mm"/>'
        });

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
                shopId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                productOptionId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                repeatStartDate: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                repeatEndDate: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                repeatStartTime: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                repeatEndTime: {
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

        $('.day-item,.week-item,.month-item', Dialog.getContext()).click(function () {
            var $this = $(this);
            if ($this.hasClass('active')) {
                $this.removeClass('active');
            } else {
                $this.addClass('active');
            }
        });

        $('#repeatType', Dialog.getContext()).change(function () {
            /*$('.day-item,.week-item,.month-item', Dialog.getContext()).removeClass('active'); // 刪除已經選擇的*/
            var val = $(this).val();
            var daysField = $("#daysField", Dialog.getContext());
            var weeksField = $("#weeksField", Dialog.getContext());
            var monthsField = $("#monthsField", Dialog.getContext());
            var startTimeField = $("#startTimeField", Dialog.getContext());
            var endTimeField = $("#endTimeField", Dialog.getContext());
            var startDateField = $("#startDateField", Dialog.getContext());
            var endDateField = $("#endDateField", Dialog.getContext());
            var startDateTimeField = $("#startDateTimeField", Dialog.getContext());
            var endDateTimeField = $("#endDateTimeField", Dialog.getContext());
            var noneRepeatGroup = $("#noneRepeatGroup", Dialog.getContext());
            daysField.addClass("hide");
            weeksField.addClass("hide");
            monthsField.addClass("hide");
            startTimeField.removeClass('hide');
            endTimeField.removeClass('hide');
            startDateField.removeClass('hide');
            endDateField.removeClass('hide');
            noneRepeatGroup.addClass('hide');
            switch (val) {
                case '0':
                    daysField.removeClass("hide");
                    break;
                case '1':
                    weeksField.removeClass("hide");
                    break;
                case '2':
                    daysField.removeClass("hide");
                    monthsField.removeClass("hide");
                    break;
                default:
                    break;
            }
        }).trigger('change');

        $('.select-all', Dialog.getContext()).click(function () {
            var $this = $(this);
            if ($this.is(':checked')) {
                $this.parents('table').find('span').addClass('active');
            } else {
                $this.parents('table').find('span').removeClass('active');
            }
        });
    });
	
	// 更新EndTime
	function updateEndTime() {
		var displayName = $('input[name=displayName]');
		var duration = $('input[name=duration]').val();
		var processTime = $('input[name=processTime]').val();
		var startTime = $('input[name=repeatStartDate]').val() + ' ' + $('input[name=repeatStartTime]').val() + ':00';
		console.log('startTime:'+startTime);
		if ($('input[name=repeatStartTime]').val() && duration && startTime) {
			processTime = processTime ? processTime : 0;
			let addTime=parseInt(duration) + parseInt(processTime);
			//console.log('add time:'+addTime);
			let newTime=moment(startTime).add(addTime, 'm');
			$('input[name=repeatEndTime]').val(moment(newTime).format("HH:mm"));
		}
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
						value: res.treatmentCode+' | '+res.treatmentName+' | '+res.duration+' mins '
					}
				}));
			}
     	}); 
		},
		select: function( event, ui ) {
			$(this).parent().find('input[name=duration]').val(ui.item.duration);
			$(this).parent().find('input[name=processTime]').val(ui.item.processTime);
			$(this).parent().find('input[name=productOptionId]').val(ui.item.id);
			updateEndTime();
        }
	}
	
	var selector='input.select-treatment';
	$(document).on('keydown.autocomplete', selector, function() {
		$(this).autocomplete(autocomplete_options);
	});
	
</script>
