<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="ex" tagdir="/WEB-INF/tags" %>
<style>
	.ui-autocomplete{
		max-height:200px;
		overflow-y:scroll;
		overflow-x:hidden;
	}
</style>
<h3 class='text-h3-white'>
    <spring:message code="label.book.edit.management1"/>
</h3>
<c:url var="url" value="/book/edit"/>
<form action="${url}" data-forward="${bookVO.forward}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
    <input id="bookId" type="hidden" name="id" value="${book.id}"/>
    <div id="baseStep" class="baseStep step orderDetails_management active ">
        <div class="contentdetail">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
                <div class="col-lg-5">
                    ${book.shop.name}
                    <input id="shopId" type="hidden" name="shopId" value="${book.shop.id}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.client"/></label>
                <div class="col-lg-5">
                    ${book.user.fullName}
                    <input id="memberId" type="hidden" name="memberId" value="${book.user.id}"/>
                </div>
            </div>
            <c:set var="guest" value="${book.guest}"/>
            <c:if test="${guest ne null}">
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.firstname"/></label>
                    <div class="col-lg-5">
                        <input name="firstName" id="firstName" value="${guest.firstName}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.lastname"/></label>
                    <div class="col-lg-5">
                        <input name="lastName" id="lastName"  value="${guest.lastName}" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.email"/></label>
                    <div class="col-lg-5">
                        <input name="email" id="email"  value="${guest.email}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.mobile.phone"/></label>
                    <div class="col-lg-5">
                        <input name="mobilePhone" id="mobilePhone"  value="${guest.mobilePhone}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.country"/></label>
                    <div class="col-lg-5">
                        <input name="country" id="country"  value="${guest.country}" class="form-control"/>
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.appointment.date"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="startAppointmentTime" name="startAppointmentTime" class="form-control startAppointmentTime" value='<fmt:formatDate value="${book.appointmentTime}" pattern="yyyy-MM-dd"/>' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
                <div class="col-lg-5">
                    <textarea name="remarks" class="form-control">${book.remarks}</textarea>
                </div>
            </div>
            <%--<div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.walkin"/></label>
                <div class="col-lg-5">
                    <input type="checkbox" name="walkIn" ${book.walkIn ? "checked" : ""}/>
                </div>
            </div>--%>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.pregnancy"/></label>
                <div class="col-lg-5">
                    <input type="checkbox" name="pregnancy" ${book.pregnancy ? "checked" : ""}/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.sametimetoshareroom"/></label>
                <div class="col-lg-5">
                    <input type="checkbox" name="sameTimeToShareRoom" ${book.allShareSingleRoom ? 'checked': ''} id="sameTimeToShareRoom"/>
                </div>
            </div>
        </div>
        <div class="row new-row-width">
            <table id="bookItems" class="table table-striped">
                <thead>
                <tr>
                    <th><spring:message code="label.treatment"/>
                    	<a id="addBundle" onclick="addBundle()"  class="btn btn-primary dialog" data-width="680" data-title='<spring:message code="label.book.choose.bundle"/>'>
	            			<i class="glyphicon glyphicon-plus"></i><spring:message code="label.book.choose.bundle"/>
	        			</a>
                    </th>
                    <th><spring:message code="label.guest.amount"/></th>
                    <th><spring:message code="label.share.room"/></th>
                    <th><spring:message code="label.start.time"/></th>
                    <th><spring:message code="label.end.time"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${book.bookFirstStepRecords}" var="item" varStatus="status">
                	<c:set var ="bundle_css">
                		<c:choose>
                        	<c:when test="${item.bundleId !=null }">
                        		bundle_css
                        	</c:when>
                        	<c:otherwise>
                        		 
                        	</c:otherwise>
                       	</c:choose>
                	</c:set>
                	<tr class="row-treatment ${bundle_css}" data-bundle-id="${item.bundleId}">
                        <td>
						
                        <input type="hidden" name="productOptionId" value="${item.productOption.id}">
                        <input type="hidden" name="productId" value="${item.productOption.product.id}">
						<input type="hidden" name="duration" value="${item.productOption.duration}">
						<input type="hidden" name="processTime" value="${item.productOption.processTime}">
                        <input type="text" class="form-control quick-search select-treatment" value="${item.productOption.label33}"/>

                        </td>
                        <td>
                            <select class="selectpicker form-control guestAmount">
	                            <c:choose>
		                        	<c:when test="${item.bundleId !=null }">
		                        		 <option value="1">1</option>
		                        	</c:when>
		                        	<c:otherwise>
		                        		<c:forEach begin="1" end="5" var="amountIndex">
		                                    <option value="${amountIndex}" ${item.guestAmount eq amountIndex? 'selected' : ''}>${amountIndex}</option>
		                                </c:forEach>
		                        	</c:otherwise>
	                        	</c:choose>
                                
                            </select>
                        </td>
                        <td align="center">
                            <input type="checkbox" class="shareRoom" ${(item.shareRoom || book.allShareSingleRoom) ? 'checked': ''}>
                        </td>
                        <td>
                            <div class="time-select">
                                <ex:tree tree="${timeTreeData}" clazz="startTime" selectId='${item.startTime}' selectName='${item.startTime}'/>
                            </div>
                        </td>
                        <td>
	                        <div class="time-select">
				                <ex:tree tree="${timeTreeData}" clazz="endTime" selectId="${item.endTime}" selectName="${item.endTime}"/>
				            </div>
	                    </td>
                        <td>
                       	 	<c:choose>
	                        	<c:when test="${item.bundleId !=null }">
	                        		 <button type="button" onClick="removeBundle(this)">-</button>
	                        	</c:when>
	                        	<c:otherwise>
	                        		 <c:if test="${not status.first}">
		                                <button type="button" class="removeBookItem">-</button>
		                            </c:if>
	                        	</c:otherwise>
                        	</c:choose>
                           
                        </td>
                    </tr>
                </c:forEach>
                <tr class="addLine">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        <button type="button" class="addBookItem">+</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div id="resourceStep" class='resourceStep step'></div>
    <div id="confirmStep" class="confirmStep step"></div>

    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
                <button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
                <button type="button" class="btn btn-default" id="waiting"><spring:message code="label.book.status.waiting"/></button>
                <button type="button" class="btn btn-default formPageSubmit" data-skip-validate="true" id="submit">
                    <spring:message code="label.book.status.save"/>
                </button>
            </div>
        </div>
    </div>
</form>

<%-- 模板 --%>
<table id="bookItemTemplate" class="hide">
     <tr class="row-treatment" data-bundle-id="">
        <td>
            <input type="hidden" name="productId" value="">
			<input type="hidden" name="duration" value="">
			<input type="hidden" name="processTime" value="">
			<input type="hidden" name="productOptionId_temp" value="">
			<input type="text" class="form-control quick-search select-treatment"/>
        </td>
        <td>
            <select class="selectpicker form-control guestAmount">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </td>
        <td align="center">
            <input type="checkbox" class="shareRoom">
        </td>
        <td>
            <div class="time-select startTimeDiv">
                <ex:tree tree="${timeTreeData}" clazz="startTime" selectId="${currentTime}" selectName="${currentTime}"/>
            </div>
        </td>
        <td>
        	<div class="time-select">
      			<ex:tree tree="${timeTreeData}" clazz="endTime" selectId="${item.endTime}" selectName="${item.endTime}"/>
  			</div>
       	</td>
        <td>
            <button type="button" class="removeBookItem">-</button>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(document).ready(function () {
        // 初始化下拉框
        $('#bookItems .time-select', getContext()).multiMenu();

        $('#bookItems .select-category', getContext()).selectCategory({
            callback: function (context) {
                // 注册变更事件
                context.find('input[name=displayName]').change(function () {
                    updateEndTime($(this).parents('tr'));
                });
            }
        });

        $('.addBookItem', getContext()).click(function () {
			
			let rows=$('#bookItems').find('td input[name="productOptionId"]');
			console.log(rows);
			let invalid=rows.filter(key=>{console.log(rows[key]); return ! $(rows[key]).val();}).length;
			console.log(invalid);
			if(invalid){
				Dialog.alert({
                    title: "<spring:message code="lable.error"/> ",
                    message: "<spring:message code="label.book.please.select.treatment"/> "
                });
                return;
			};

            let newBookItem = $('#bookItemTemplate', getContext()).find('tr').clone(false);
			let last_row=$('#bookItems tr.row-treatment').last();
            let previousItemTR = last_row;
			let startTime=last_row.last().find('input.endTime').val();
			console.log('startTime:'+startTime);
            previousItemTR.after(newBookItem);
			$('#bookItems tr input[name=productOptionId_temp]').attr('name', 'productOptionId');
			newBookItem.find('.time-select').multiMenu();
			newBookItem.find('.startTimeDiv').find('.startTime').val(startTime);
            newBookItem.find('.removeBookItem').click(function () {
                $(this).parents('tr').remove();
                });
        });

        $('.removeBookItem', getContext()).click(function () {
            $(this).parents('tr').remove();
        });

        $('#startAppointmentTime', getContext()).change(function () {
            $(this).trigger('input');
        });

        $('#bookItems').find('.startTime').change(function () {
            updateEndTime($(this).parents('tr'));
        });


        // 註冊對話框的提交事件
        $('#previous', getContext()).hide();
        $('#waiting', getContext()).hide();
        $('#submit', getContext()).hide();

        $('#next', getContext()).click(function () {
            var currentStep = $('.step.active', getContext());
            var nextStep = currentStep.next('.step');
            var html;
            var parent = getContext();

            if (currentStep.hasClass('baseStep')) {
                // 验证bookItem
                if ($('input[name="productOptionId"]', getContext()).filter(function () {
                            return !this.value;
                        }).length > 0) {
                    Dialog.alert({
                        title: "<spring:message code="lable.error"/>",
                        message: "<spring:message code="label.book.please.select.treatment"/>"
                    });
                    return;
                }

                // 组装parameter
                html = [];

                <c:if test="${guest ne null}">
                // 验证
                var firstName = $('#firstName', parent).val();
                if (firstName) {
                    html.push(String.format('<input type="hidden" name="firstName" value="{0}"/>', firstName));
                }
                var lastName = $('#lastName', parent).val();
                if (lastName) {
                    html.push(String.format('<input type="hidden" name="lastName" value="{0}"/>', lastName));
                }
                var email = $('#email', parent).val();
                if (email) {
                    if (!isEmailValid(email)) {
                        Dialog.alert({
                            title: "<spring:message code="lable.error"/>",
                            message: "<spring:message code="label.book.email.format.error"/>"
                        });
                        return;
                    }
                    html.push(String.format('<input type="hidden" name="email" value="{0}"/>', email));
                }

                var mobilePhone = $('#mobilePhone', parent).val();
                if (mobilePhone) {
                    if (!isPhoneValid(mobilePhone)) {
                        Dialog.alert({
                            title: "<spring:message code="lable.error"/>",
                            message: "<spring:message code="label.book.phone.format.error"/>"
                        });
                        return;
                    }
                    html.push(String.format('<input type="hidden" name="mobilePhone" value="{0}"/>', mobilePhone));
                }

                var country = $('#country', parent).val();
                if (country) {
                    html.push(String.format('<input type="hidden" name="country" value="{0}"/>', country));
                }
                var sameTimeToShareRoom = $('#sameTimeToShareRoom', parent);
                if (sameTimeToShareRoom.is(":checked")) {
                    html.push(String.format('<input type="hidden" name="sameTimeToShareRoom" value="{0}"/>', sameTimeToShareRoom.val()));
                }
                </c:if>

                html.push(String.format('<input type="hidden" name="id" value="{0}"/>', $('#bookId', getContext()).val()));
                html.push(String.format('<input type="hidden" name="shopId" value="{0}"/>', $('#shopId', getContext()).val()));
                html.push(String.format('<input type="hidden" name="memberId" value="{0}"/>', $('#memberId', getContext()).val()));
                html.push(String.format('<input type="hidden" name="startAppointmentTime" value="{0}"/>', $('#startAppointmentTime', getContext()).val()));
                html.push(String.format('<input type="hidden" name="state" value="{0}"/>', '${bookVO.state}'));

                // bookItem
                $("#bookItems tbody", getContext()).find('tr').not('.addLine').each(function (index, item) {
                    var $this = $(this);
                    var bundleId = $this.data('bundle-id');
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productOptionId" value="{1}"/>', index, $this.find('input[name=productOptionId]').val()));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].guestAmount" value="{1}"/>', index, $this.find('select.guestAmount').selectpicker('val')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].shareRoom" value="{1}"/>', index, $this.find('input.shareRoom').is(":checked")));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].startTime" value="{1}"/>', index, $this.find('input.startTime').val()));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].endTime" value="{1}"/>', index, $this.find('input.endTime').val()));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].bundleId" value="{1}"/>', index, $this.data('bundle-id')));
                    //html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productId" value="{1}"/>', index, $this.find('input[name=productId]').val()));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].bookItemIdex" value="{1}"/>', index, index));
                    
                    if (bundleId == '') {
                        var dp = $this.find('input[name=displayName]');
						html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].duration" value="{1}"/>', index, $this.find('input[name=duration]').val()));
                        //html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].duration" value="{1}"/>', index, dp.data('duration') + dp.data('process-time')));
                        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productId" value="{1}"/>', index, $this.find('input[name=productId]').val()));
                    } else {
                        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].duration" value="{1}"/>', index, $this.find('input[name=duration]').val()));
                        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productId" value="{1}"/>', index, $this.find('input[name=productId]').val()));
                    }
                });

                var param = $('<form></form>').html(html.join('')).serialize();
                $.post('<c:url value="/book/reallocateRoomResource"/>', param, function (res) {
                    $('#previous', getContext()).show();
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    nextStep.html(res);
                });
            } else if (currentStep.hasClass('resourceStep')) {
                $('form', getContext()).find('input[name^="bookItemVOs"]').remove();
                var bookItems = $('.bookItemTr', $('#resourceStep'));
                html = [];
                bookItems.each(function (index) {
                    var $item = $(this);
                    var roomId = $item.data('room-id');
                    roomId = roomId ? roomId : '';
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].tempId" value="{1}">', index, $item.data('temp-id')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].tempParentId" value="{1}">', index, $item.data('temp-parent-id')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productOptionId" value="{1}">', index, $item.data('product-option-id')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].appointmentTime" value="{1}">', index, $item.data('time')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].endAppointmentTime" value="{1}">', index, $item.data('end-time')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].roomId" value="{1}">', index, roomId));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].bundleId" value="{1}">', index, $item.data('bundle-id')));
                    var selectedTherapistsDivs = $item.find('div.selectedTherapists');

                    var available = false;
                    selectedTherapistsDivs.each(function () {
                        var therapistDiv = $(this);
                        var therapistIndex = therapistDiv.data('therapist-index');
                        var selectTherapists = therapistDiv.find('select.selectTherapists');
                        var therapistId = selectTherapists.selectpicker('val');
                        var onRequest = therapistDiv.find('input.onRequest').is(":checked");
                        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].requestTherapistVOs[{1}].therapistId" value="{2}">', index, therapistIndex, therapistId));
                        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].requestTherapistVOs[{1}].onRequest" value="{2}">', index, therapistIndex, onRequest));

                        if (therapistId) {
                            available |= selectTherapists.find('option:selected').data('available'); // 有一个available
                        }
                    });
                    var status = (available == true && roomId) ? "CONFIRM" : "WAITING";
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].status" value="{1}">', index, status));
                });

                $(html.join('')).appendTo($('form', getContext()));
                $.post('<c:url value="/book/editConfirm"/>', $('form', getContext()).serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    $('#submit', getContext()).show();
                    $('#next', getContext()).hide();
                });
            }
        });

        $('#previous', getContext()).click(function () {
            $('#submit', getContext()).hide();
            var currentStep = $('.step.active', getContext());
            var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
            if (previousStep.hasClass('baseStep')) {
                $('#previous', getContext()).hide();
                $('#next', getContext()).show();
                $('#waiting', getContext()).hide();
            } else if ($('input[name=status]', getContext()).val() == 'WAITING') {
                $('#waiting', getContext()).show();
            } else {
                $('#next', getContext()).show();
            }
        });

        $('#startAppointmentTime', getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            datepicker: true,
            value: '<fmt:formatDate value="${bookVO.startAppointmentTime}" pattern="yyyy-MM-dd"/>'
        });

        $('.walkInGuest', getContext()).click(function () {
            var parent = getContext();
            $.ajax({
                url: '<c:url value="/member/walkInGuest"/>',
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.errorFields.length > 0) {
                        $.each(data.errorFields, function (index, item) {
                            if (item.fieldName == 'username') {
                                $('#username', parent).attr("value", item.errorMessage);
                                $('#fullName', parent).attr("value", item.errorMessage);
                            }
                            if (item.fieldName == 'id') {
                                $('#memberId', parent).attr("value", item.errorMessage);
                            }
                        });
                    }
                }
            });
        });
        
        $('#sameTimeToShareRoom', getContext()).change(function() { 
            var $root = $(this);
            $('#bookItems tbody').find('tr').each(function () {
                var shareRoomCheckBox = $(this).find('input.shareRoom');
                if ($root.is(":checked")) {
                	shareRoomCheckBox.prop('checked',true);
                }else {
                	shareRoomCheckBox.prop('checked',false);
                }          
            });
        });
    });
	
	// 更新EndTime
	function updateEndTime(row) {
		//console.log(row);
		var displayName = row.find('input[name=displayName]');
		var duration = row.find('input[name=duration]').val();
		var processTime = row.find('input[name=processTime]').val();
		//var duration = displayName.data('duration');
		//var processTime = displayName.data('process-time');
		var startTime = $('#startAppointmentTime').val() + ' ' + $(row).find('.startTime').val() + ':00';
		console.log('startTime:'+startTime);
		if (duration && startTime) {
			processTime = processTime ? processTime : 0;
			let addTime=parseInt(duration) + parseInt(processTime);
			let new_date=moment(startTime).add(addTime, 'm');

			row.find('.endTime').val(moment(new_date).format("HH:mm"));
		}
	}
    
    function addBundle(){
		var startTime;
		var total = $('#bookItems tbody').find('tr').length;
		console.log(total);
	 	$('#bookItems tbody').find('tr').each(function(index, item){
	 		if(index == total -2){
	 			startTime = $(item).find('.endTime').val();
	 		}
    	});
   	 	$("#addBundle").attr("href",'<c:url value="/book/toChooseBundle?shopId="/>'+$("#shopId").val()
   	 		+"&bookingDate="+$("#startAppointmentTime").val()+"&startTime="+startTime);
   	}
    
    function removeBundle(btnDom){
    	var bundleId = $(btnDom).parents('tr').data('bundle-id');

    	$('#bookItems tbody').find('tr').each(function(){
    		var trBundleId = $(this).data('bundle-id');
    		if(trBundleId == bundleId){
    			$(this).remove();
    		}
       	});
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
			$(this).parent().find('input[name=duration]').val(ui.item.duration);
			$(this).parent().find('input[name=processTime]').val(ui.item.processTime);
            $(this).parent().find('input[name=productId]').val(ui.item.productId);
			updateEndTime($(this).closest('tr'));
            //console.log($(this).parent().find('input[name=productOptionId]').val());
        }
	}
	
	var selector='input.select-treatment';
	
	$(document).on('keydown.autocomplete', selector, function() {
		$(this).autocomplete(autocomplete_options);
	});
	
</script>
