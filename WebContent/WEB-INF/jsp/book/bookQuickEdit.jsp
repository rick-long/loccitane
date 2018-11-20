<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="ex" tagdir="/WEB-INF/tags" %>

<style>
	.ui-autocomplete{
		max-height:200px;
		overflow-y:scroll;
		overflow-x:hidden;
		position:absolute;
		z-index:1051;
	}


</style>

<c:url var="url" value="/book/bookQuickEdit"/>
<form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
    <input id="shopId" type="hidden" name="shopId" value="${bookQuickVO.shopId}"/>
    <input type="hidden" name="therapistId" value="${bookQuickVO.therapistId}"/>
    <input type="hidden" id ="doubleBooking" name="doubleBooking" value="${bookQuickVO.doubleBooking}"/>
    <input type="hidden" id ="bookId" name="bookId" value="${bookQuickVO.bookId}"/>

    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.therapist"/></label>
            <div class="col-lg-5 topmargin">
                ${bookQuickVO.therapist.displayName}
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.appointment.time"/></label>
            <div class="col-lg-5 topmargin">
            	<c:choose>
	            <c:when test="${bookQuickVO.doubleBooking !=null && bookQuickVO.doubleBooking}">
	           		<fmt:formatDate value="${bookQuickVO.startAppointmentTime}" pattern="yyyy-MM-dd"/>
	                <div class="time-select startTimeDiv">

		                <ex:tree tree="${timeTreeData}" clazz="startTime" selectId="${currentTime}" selectName="${currentTime}"/>
		            </div>
		            <input type="hidden" name="originalBookItemId" value="${bookQuickVO.originalBookItemId}"/>
		            <input type="hidden" id ="bookingDate" name="bookingDate" value='<fmt:formatDate value="${bookQuickVO.startAppointmentTime}" pattern="yyyy-MM-dd"/>'/>
		            <input type="hidden" name="startTime" id="startTime"
		            	value=""/>
	            </c:when>
	            <c:otherwise>
	            	<fmt:formatDate value="${bookQuickVO.startAppointmentTime}" pattern="yyyy-MM-dd HH:mm"/>
	            	<input type="hidden" name="startTimeStamp"   value="${bookQuickVO.startTimeStamp}"/>
	            </c:otherwise>
            	</c:choose>
             </div>
        </div>
        <%--<div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.client"/> *</label>
            <div class="col-lg-5">
                &lt;%&ndash;<div class="input-group dialog">
                    <input id="memberId" type="hidden" name="memberId" value="${bookQuickVO.memberId}"/>
                    <input type="hidden" name="username" id="username" class="form-control"
                           value="${bookQuickVO.member.fullName}"/>
                    <input type="text" name="fullName" id="fullName" class="form-control quick-search" value="${bookQuickVO.member.fullName}" readonly/>
                </div>&ndash;%&gt;
                <div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
                    <input type="hidden" name="memberId" id="memberId" class="form-control" value="${bookQuickVO.memberId}" />
                    <input type="hidden" name="username" id="username" class="form-control" value="${bookQuickVO.member.username}"/>
                    <input type="text" name="fullName" id="fullName" class="form-control quick-search" value="${bookQuickVO.member.fullName}" readonly/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
                <a id="clientView" data-permission="book:clientView" onclick="clientView()"  title='<spring:message code="label.button.view"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="950" data-title='<spring:message code="label.button.view"/>' style="display: inline;">
                    <i class="glyphicon glyphicon-eye-open"></i>
                </a>
                <br/>
                <span class="glyphicon glyphicon-pencil walkInGuest"><spring:message code="label.sales.guest"/> </span>
                <span id="quickAddMember" class="glyphicon glyphicon-plus"><spring:message code="label.book.quick.register"/> </span>
            </div>
        </div>--%>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.client"/></label>
            <div class="col-lg-5">
                ${book.user.fullName}
                <input id="memberId" type="hidden" name="memberId" value="${book.user.id}"/>
            </div>
        </div>
        <c:set var="guest" value="${guest}"/>
        <c:if test="${guest ne null}">
        <%--<div id="guestArea" class="hide">--%>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.firstname"/></label>
                <div class="col-lg-5">
                    <input name="firstName" id="firstName" value="${guest.firstName}" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.lastname"/></label>
                <div class="col-lg-5">
                    <input name="lastName" id="lastName" value="${guest.lastName}" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.email"/></label>
                <div class="col-lg-5">
                    <input name="email" id="email" value="${guest.email}" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.mobile.phone"/></label>
                <div class="col-lg-5">
                    <input name="mobilePhone" id="mobilePhone" value="${guest.mobilePhone}" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.country"/></label>
                <div class="col-lg-5">
                    <input name="country" id="country" value="${guest.country}" class="form-control"/>
                </div>
            </div>
        <%--</div>--%>
        </c:if>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.treatment"/></label>
            <div class="col-lg-5">
				<input type="hidden" id="categoryId" name="categoryId">
				<input type="hidden" id="productId" name="productId">
				<input type="hidden" id="productOptionId" name="productOptionId" value="${bookQuickVO.productOptionId}">
				<input type="text" class="form-control quick-search select-treatment" placeholder="${productOption.labelWithCodeNoBr}" value=""/>
            </div>
        </div>
        <%--<div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.walkin"/></label>
            <div class="col-lg-5">
                <input type="checkbox" name="walkIn"/>
            </div>
        </div>--%>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.pregnancy"/></label>
            <div class="col-lg-5">
                <input type="checkbox" name="pregnancy" ${bookQuickVO.book.pregnancy ? "checked" : ""}/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
            <div class="col-lg-5">
                <textarea name="remarks" class="form-control" >${bookQuickVO.book.remarks}</textarea>
            </div>
        </div>
    </div>
    <div id="confirmStep" class="confirmStep step"></div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/> </button>
                <button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/> </button>
                <button type="button" class="btn btn-default dialogSubmitBtn" data-skip-validate="true" id="submit">
                    <spring:message code="label.book.status.save"/>
                </button>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {

    	$('#baseStep .time-select', Dialog.getContext()).multiMenu();

        $('.select-category', Dialog.getContext()).selectCategory({});

        // 註冊對話框的提交事件
        $('#previous', Dialog.getContext()).hide();
        $('#submit', Dialog.getContext()).hide();
        $('#next', Dialog.getContext()).click(function () {
            var parentEndTimeStr= "${date} "+'${currentEndTime}';
            var parentTimeStr= "${date} "+'${currentTime}';
            var childTimeStr=  "${date} "+$('#baseStep', Dialog.getContext()).find('.startTimeDiv').find('.select-id').val();
            var parentEndDate=new Date(parentEndTimeStr.replace("-", "/").replace("-", "/"));
            var parentDate=new Date(parentTimeStr.replace("-", "/").replace("-", "/"));
            var childDate= new Date(childTimeStr.replace("-", "/").replace("-", "/"));
            if(parentDate>childDate){
                Dialog.alert({
                    title: "<spring:message code="lable.error"/>",
                    message: "<spring:message code="label.book.reselect.scheduled.time"/>"
                });
                return;
            }
            if( childDate>parentEndDate){
                Dialog.alert({
                    title: "<spring:message code="lable.error"/>",
                    message: "<spring:message code="label.book.reselect.scheduled.time"/>"
                });
                return;
            }
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');
            if (currentStep.hasClass('baseStep')) {
                var form = $('form', Dialog.getContext());
                var bootstrapValidator = form.data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 重置formData
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }
                if (!$('#productOptionId', form).val()) {
                    Dialog.alert({
                        title: "<spring:message code="lable.error"/>",
                        message: "<spring:message code="label.book.please.select.treatment"/>"
                    });
                    return;
                }

                // guest 处理
                if ($('#fullName', Dialog.getContext()).val() == 'GUEST') {
                    // 验证
                    var email = $('#email', Dialog.getContext()).val();
                    if (email && !isEmailValid(email)) {
                        Dialog.alert({
                            title: "<spring:message code="lable.error"/>",
                            message: "<spring:message code="label.book.email.format.error"/>"
                        });
                        return;
                    }

                    var mobilePhone = $('#mobilePhone', Dialog.getContext()).val();
                    if (mobilePhone && !isPhoneValid(mobilePhone)) {
                        Dialog.alert({
                            title: "<spring:message code="lable.error"/>",
                            message: "<spring:message code="label.book.phone.format.error"/>"
                        });
                        return;
                    }
                }
                
                //double booking
                var doubleBooking =$('#doubleBooking').val();
                if(doubleBooking !=null && doubleBooking){
                	 var startTime = $('#baseStep', Dialog.getContext()).find('.startTimeDiv').find('.select-id').val();
                	 $('#startTime').attr('value',startTime);
                }
               
                $.post('<c:url value="/book/bookQuickEditConfirm"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    $('#submit', Dialog.getContext()).show();
                    $('#next', Dialog.getContext()).hide();
                    $('#previous', Dialog.getContext()).show();
                });
            }
        });

        $('#previous', Dialog.getContext()).click(function () {
            $('#submit', Dialog.getContext()).hide();
            var currentStep = $('.step.active', Dialog.getContext());
            currentStep.removeClass('active').prev('.step').addClass('active');
            $('#previous', Dialog.getContext()).hide();
            $('#next', Dialog.getContext()).show();
        });

        $('form', Dialog.getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                fullName: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        });

        $('.walkInGuest', Dialog.getContext()).click(function () {

            $.ajax({
                url: '<c:url value="/member/walkInGuest"/>',
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.errorFields.length > 0) {
                        $.each(data.errorFields, function (index, item) {
                            if (item.fieldName == 'username') {
                                $('#username', Dialog.getContext()).attr("value", item.errorMessage);
                                $('#fullName', Dialog.getContext()).attr("value", item.errorMessage);
                            }
                            if (item.fieldName == 'id') {
                                $('#memberId', Dialog.getContext()).attr("value", item.errorMessage);
                            }
                            $('#fullName', Dialog.getContext()).trigger('change');
                        });
                    }
                }
            });
        });


        $('#fullName').change(function () {
            console.info('   guest  : ',$(this).val());
            if ($(this).val() == 'GUEST') {
                $('#guestArea', Dialog.getContext()).removeClass('hide');
            } else {
                $('#guestArea', Dialog.getParentContext()).addClass('hide');
            }
        });

        $('#quickAddMember', Dialog.getContext()).click(function () {
            var options = {
                url: '<c:url value="/member/toQuickAdd"/>',
                title: '<spring:message code="label.member.quick.add"/>',
                urlData: {shopId: $('#shopId', Dialog.getContext()).val()}
            };
            Dialog.create(options);
            return false;
        });
    });

    function clientView() {
        $("#clientView").attr("href",'<c:url value="/book/clientView?userId="/>'+$("#memberId").val());
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
            $(this).parent().find('input[name=productId]').val(ui.item.productId);
        }
	}
	
	var selector='input.select-treatment';
	$(document).on('keydown.autocomplete', selector, function() {
		$(this).autocomplete(autocomplete_options);
	});

</script>
