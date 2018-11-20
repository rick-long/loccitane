<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="ex" tagdir="/WEB-INF/tags" %>
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
	
	.ui-autocomplete{
		max-height:200px;
		overflow-y:scroll;
		overflow-x:hidden;
	}
	
</style>
<h3 class='text-h3-white'>
    <spring:message code="label.book.add.management1"/>
</h3>
<c:set var="isEdit" value="${bookVO.state eq 'EDIT'}"/>
<c:url var="url" value="/book/add"/>
<form action="${url}" data-forward="${bookVO.forward}" data-form-token="${TokenUtil.get(pageContext)}" method="post"
      class="form-horizontal">
    <input type="hidden" name="id" value="${book.id}"/>
    <input type="hidden" name="state" value="${bookVO.state}"/>
    <div id="baseStep" class="baseStep step orderDetails_management active ">
        <div class="contentdetail">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
                <div class="col-lg-5">
                    <c:choose>
                        <c:when test="${isEdit}">
                            ${book.shop.name}
                            <input type="hidden" name="shopId" value="${book.shop.id}"/>
                        </c:when>
                        <c:otherwise>
                            <select id="shopId" name="shopId" class="selectpicker form-control">
                                <c:forEach items="${shopList}" var="item">
                                    <option value="${item.id}"
                                            <c:if test="${currentShop.id eq item.id}">selected</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.client"/></label>
                <div class="col-lg-5">
                    <c:choose>
                        <c:when test="${isEdit}">
                            ${book.user.fullName}
                            <input type="hidden" name="memberId" value="${book.user.id}"/>
                        </c:when>
                        <c:otherwise>
                            <div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>'
                                 data-title='<spring:message code="label.client.quick.search"/>'>
                                <input type="hidden" name="memberId" id="memberId" class="form-control"/>
                                <input type="hidden" name="username" id="username" class="form-control"/>
                                <input type="text" name="fullName" id="fullName" class="form-control quick-search"
                                       readonly/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-search"></span>
                                </span>
                            </div>
                             <a id="clientView" data-permission="book:clientView" onclick="clientView()"  title='<spring:message code="label.button.view"/>' class="btn btn-primary dialog btn-edit"
                                data-reload-btn="a.search-btn" data-width="950" data-title='<spring:message code="label.button.view"/>'
                                style="display: inline;position:absolute;right:-7px;top:3px">
                             <i class="glyphicon glyphicon-eye-open"></i>
                            </a>
                            <span class="glyphicon glyphicon-pencil walkInGuest"><spring:message code="label.sales.guest"/> </span>
                            <span id="quickAddMember" class="glyphicon glyphicon-plus quickAddMember"><spring:message code="label.book.quick.register"/></span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div id="guestArea" class="hide">
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.firstname"/></label>
                    <div class="col-lg-5">
                        <input name="firstName" id="firstName" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.lastname"/></label>
                    <div class="col-lg-5">
                        <input name="lastName" id="lastName" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.email"/></label>
                    <div class="col-lg-5">
                        <input name="email" id="email" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.mobile.phone"/></label>
                    <div class="col-lg-5">
                        <input name="mobilePhone" id="mobilePhone" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.country"/></label>
                    <div class="col-lg-5">
                        <input name="country" id="country" class="form-control"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.appointment.date"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="startAppointmentTime" name="startAppointmentTime"
                               class="form-control startAppointmentTime"
                               value='<fmt:formatDate value="${bookVO.startAppointmentTime}" pattern="yyyy-MM-dd"/>'
                               readonly>
                        <span class="input-group-addon time-toggle"><span
                                class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
                <div class="col-lg-5">
                    <textarea name="remarks" class="form-control"></textarea>
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
                    <input type="checkbox" name="pregnancy"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.sametimetoshareroom"/></label>
                <div class="col-lg-5">
                    <input type="checkbox" name="sameTimeToShareRoom" id="sameTimeToShareRoom"/>
                </div>
            </div>
        </div>
        <div class="row new-row-width">
            <table id="bookItems" class="table table-striped">
                <thead>
                <tr>
                    <th class="col-lg-4">
                        <div class="Treatment"> <spring:message code="label.treatment"/></div>
                        <a id="addBundle" onclick="addBundle(this)" class="btn btn-primary dialog" data-width="680"
                           data-title='<spring:message code="label.book.choose.bundle"/>'>
                            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.book.choose.bundle"/>
                        </a>
                    </th>
                    <th class="col-lg-1 col-md-1"><spring:message code="label.guest.amount"/></th>
                    <th class="col-lg-1 col-md-1 text-center"><spring:message code="label.share.room"/></th>
                    <th class="col-lg-1 col-md-1"><spring:message code="label.start.time"/></th>
                    <th class="col-lg-1 col-md-1"><spring:message code="label.end.time"/></th>
                    <th class="col-lg-6 col-md-6"></th>
                </tr>
                </thead>
                <tbody>
                <tr id="firstBookItem" data-bundle-id="">
                    <td>
                       <%-- <div class="select-category" data-selectable="option" data-root-id="2" data-root-id1="4"></div>--%>
                        <input type="hidden" name="productOptionId" value="">
                        <input type="hidden" name="productId" value="">
						<input type="hidden" name="duration" value="">
						<input type="hidden" name="processTime" value="">
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
                        <div class="time-select">
                            <ex:tree tree="${timeTreeData}" clazz="startTime" selectId='${currentTime}'
                                     selectName='${bookVO.startTimeString}'/>
                        </div>
                    </td>
                    <td>
                        <div class="time-select">
                            <ex:tree tree="${timeTreeData}" clazz="endTime" selectId="${currentTime}"
                                     selectName="${currentTime}"/>
                        </div>
                    </td>
                    <td>
                        <button type="button" class="addBookItem">+</button>
                        <button type="button" class="removeBookItem hide">-</button>
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
                <button type="button" class="btn btn-default" id="previous"><spring:message
                        code="label.book.status.previous"/></button>
                <button type="button" class="btn btn-default" id="next"><spring:message
                        code="label.book.status.next"/></button>
                <button type="button" class="btn btn-default" id="waiting"><spring:message
                        code="label.book.status.waiting"/></button>
                <button type="button" class="btn btn-default formPageSubmit" data-skip-validate="true" id="submit">
                    <spring:message code="label.book.status.save"/>
                </button>
            </div>
        </div>
    </div>
</form>

<%-- 模板 --%>
<table id="bookItemTemplate" class="hide">
    <tr data-bundle-id="">
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
                <ex:tree tree="${timeTreeData}" clazz="startTime" selectId="${currentTime}"
                         selectName="${currentTime}"/>
            </div>
        </td>
        <td>
            <div class="time-select endTimeDiv">
                <ex:tree tree="${timeTreeData}" clazz="endTime" selectId="${currentTime}" selectName="${currentTime}"/>
            </div>
        </td>
        <td>
            <button type="button" class="addBookItem">+</button>
            <button type="button" class="removeBookItem">-</button>
        </td>
    </tr>
</table>
<script type="text/javascript">

    $(document).ready(function () {

        $('.input-group-addon').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
        // 初始化下拉框
        $('#bookItems .time-select', getContext()).multiMenu();

        $('#bookItems .select-category', getContext()).selectCategory({
            callback: function (context) {
                // 注册变更事件
                context.find('input[name=displayName]').change(function () {
                    updateEndTime($(this).parents('tr'));
                });
            },
            click: function () {
                /* console.info('click');
                console.info($(this).data('product-option-id'));
                var poId = $(this).data('product-option-id')
                $.ajax({
                    url: '
                <c:url value="/barcode/getBarCode"/>',
                    type: "POST",
                    dataType: "json",
                    data: {"productOptionId":poId},
                    success: function(response) {
                        var json = eval(response);
                        console.log(json);
                        if (json.flag == false) {
                            BootstrapDialog.show({
                                title: '
                <spring:message code="label.notification"/>',
                                message: '
                <spring:message code="label.book.barcode.is.empty"/> '
                            });
                        }
                        $("#barCode", Dialog.getContext()).val(json.barCode);
                    }
                }); */
            }
        });

        $('.removeBookItem', $('#firstBookItem')).click(function () {
            $(this).parents('tr').remove();
        });
		
		$(document).on('click', '.addBookItem', function(){addBookItem($(this));});
		$(document).on('click', '.removeBookItem', function(){$(this).closest('tr').remove();});

        function addBookItem(elm) {
			var id=parseInt($(elm).closest('tr').find('td input[name="productOptionId"]').val());
			console.log(id);
            if (!id) {
                Dialog.alert({
                    title: "<spring:message code="lable.error"/> ",
                    message: "<spring:message code="label.book.please.select.treatment"/> "
                });
                return;
            }

            var newBookItem = $('#bookItemTemplate', getContext()).find('tr').clone(false);
            var previousItemTR = $(elm).parents('tr');
            previousItemTR.after(newBookItem);
			$(elm).closest('tbody').find('input[name=productOptionId_temp]').attr('name', 'productOptionId');
			newBookItem.find('.time-select').multiMenu();
			console.log($(elm).closest('tr').find('input.endTime').val());
			newBookItem.find('.startTimeDiv').find('.startTime').val($(elm).closest('tr').find('input.endTime').val());
            //$this.addClass('hide'); // 隱藏當前的按鈕

            // 註冊treatment下拉框
			/*
            newBookItem.find('.select-category').selectCategory({
                callback: function () {
                    var displayName = newBookItem.find('input[name=displayName]');
                    displayName.change(function () {
                        updateEndTime($(this).parents('tr'));
                    });
                }
            });
            var isSameTimeToShareRoom = $('#sameTimeToShareRoom', getContext());
            if(isSameTimeToShareRoom.is(":checked")){
            	newBookItem.find('input.shareRoom').prop('checked',true);
            }
            newBookItem.find('select.guestAmount').selectpicker({});

            newBookItem.find('.time-select').multiMenu();

            newBookItem.find('.addBookItem').click(function () {
                addBookItem($(this));
            });

            newBookItem.find('.removeBookItem').click(function () {
                $(this).parents('tr').remove();
                var addBookItem = $('#bookItems', getContext()).find('.addBookItem');
                if (addBookItem.length == 1) {
                    addBookItem.removeClass('hide');
                }
            }).removeClass('hide');

            var previousEndTime = previousItemTR.find('.endTime').val();
            newBookItem.find('.startTimeDiv').find('.select-id').val(previousEndTime);
            newBookItem.find('.startTimeDiv').find('.startTime').val(previousEndTime).change(function () {
                updateEndTime($(this).parents('tr'));
            });
			*/
        }

        $('#startAppointmentTime', Dialog.getContext()).change(function () {
            $(this).trigger('input');
        });

		$(document).on('change', 'input.startTime', function(){
			updateEndTime($(this).parents('tr'));
		});

/*
        $('#firstBookItem').find('.startTime').change(function () {
            updateEndTime($(this).parents('tr'));
        });
		*/


        // 註冊對話框的提交事件
        $('#previous', Dialog.getContext()).hide();
        $('#waiting', Dialog.getContext()).hide();
        $('#submit', Dialog.getContext()).hide();

        $('#next', Dialog.getContext()).click(function () {
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');
            var html;
            var parent = getContext();

            if (currentStep.hasClass('baseStep')) {
                var bootstrapValidator = $('form', Dialog.getContext()).data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 重置formData
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }

                // 验证bookItem
                if ($('input[name="productOptionId"]', getContext()).filter(function () {
                    return !this.value;
                }).length > 0) {
                    Dialog.alert({
                        title: "<spring:message code="lable.error"/> ",
                        message: "<spring:message code="label.book.please.select.treatment"/> "
                    });
                    return;
                }
                var bookItemTbodySize = $("#bookItems tbody", getContext()).find('tr').size();
                if (bookItemTbodySize < 1) {
                    Dialog.alert({
                        title: "<spring:message code="lable.error"/> ",
                        message: "<spring:message code="label.book.please.select.treatment.bundle"/> "
                    });
                    return;
                }
                // 组装parameter
                html = [];
                if ($('#fullName', getContext()).val() == 'GUEST') {
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
                                message: "<spring:message code="label.book.email.format.error"/> "
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
                }

                html.push(String.format('<input type="hidden" name="shopId" value="{0}"/>', $('#shopId', getContext()).val()));
                html.push(String.format('<input type="hidden" name="memberId" value="{0}"/>', $('#memberId', getContext()).val()));
                html.push(String.format('<input type="hidden" name="startAppointmentTime" value="{0}"/>', $('#startAppointmentTime', getContext()).val()));
                html.push(String.format('<input type="hidden" name="state" value="{0}"/>', '${bookVO.state}'));


                // bookItem
                $("#bookItems tbody", getContext()).find('tr').each(function (index, item) {
                    //console.log("789");
                    var $this = $(this);
                    var bundleId = $this.data('bundle-id');
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productOptionId" value="{1}"/>', index, $this.find('input[name=productOptionId]').val()));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].guestAmount" value="{1}"/>', index, $this.find('select.guestAmount').selectpicker('val')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].shareRoom" value="{1}"/>', index, $this.find('input.shareRoom').is(":checked")));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].startTime" value="{1}"/>', index, $this.find('input.startTime').val()));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].endTime" value="{1}"/>', index, $this.find('input.endTime').val()));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].bundleId" value="{1}"/>', index, $this.data('bundle-id')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].bookItemIdex" value="{1}"/>', index, index));
                    if (bundleId == '') {
                        var dp = $this.find('input[name=displayName]');
						html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].duration" value="{1}"/>', index, $this.find('input[name=duration]').val()));
                        //html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].duration" value="{1}"/>', index, dp.data('duration') + dp.data('process-time')));
                        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productId" value="{1}"/>', index, $this.find('input[name=productId]').val()));
                    } else {
                        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].duration" value="{1}"/>', index, $this.find('input[name=duration]').val()));
                        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productId" value="{1}"/>', index, $this.find('#productId').val()));
                    }
                });

                var param = $('<form></form>').html(html.join('')).serialize();
                console.info(param);
                $.post('<c:url value="/book/assignRoomResource"/>', param, function (res) {
                    $('#previous', Dialog.getContext()).show();
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    nextStep.html(res);
                });
            } else if (currentStep.hasClass('resourceStep')) {
                $('form', Dialog.getContext()).find('input[name^="bookItemVOs"]').remove();
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
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].bookItemIdex" value="{1}"/>', index, index));

                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].duration" value="{1}"/>', index, $item.data('duration')));
                    html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productId" value="{1}"/>', index, $item.data('product-id')));
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

                $(html.join('')).appendTo($('form', Dialog.getContext()));
                $.post('<c:url value="/book/confirm"/>', $('form', Dialog.getContext()).serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    $('#submit', Dialog.getContext()).show();
                    $('#next', Dialog.getContext()).hide();
                });
            }
        });

        $('#previous', Dialog.getContext()).click(function () {
            $('#submit', Dialog.getContext()).hide();
            var currentStep = $('.step.active', Dialog.getContext());
            var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
            if (previousStep.hasClass('baseStep')) {
                $('#previous', Dialog.getContext()).hide();
                $('#next', Dialog.getContext()).show();
                $('#waiting', Dialog.getContext()).hide();
            } else if ($('input[name=status]', Dialog.getContext()).val() == 'WAITING') {
                $('#waiting', Dialog.getContext()).show();
            } else {
                $('#next', Dialog.getContext()).show();
            }
        });

        $('#startAppointmentTime', Dialog.getContext()).datetimepicker({
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
                            $('#fullName', getContext()).trigger('change');
                        });
                    }
                }
            });
        });

        $('form', getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                shopId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                fullName: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                startAppointmentTime: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        });

        $('#fullName', getContext()).change(function () {
            if ($(this).val() == 'GUEST') {
                $('#guestArea', getContext()).removeClass('hide');
            } else {
                $('#guestArea', getContext()).addClass('hide');
            }
        });

        $('#quickAddMember', getContext()).click(function () {
            $('#guestArea', getContext()).addClass('hide');
            var options = {
                url: '<c:url value="/member/toQuickAdd"/>',
                title: '<spring:message code="label.member.quick.add"/>',
                urlData: {shopId: $('#shopId', getContext()).val()}
            };
            Dialog.create(options);
            return false;
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
			console.log('add time:'+addTime);
			let new_date=moment(startTime).add(addTime, 'm');
			console.log(moment(new_date).format("HH:mm"));
			//var date = getDateFromFormat(startTime, 'yyyy-MM-dd HH:mm:ss');
			//date.setMinutes(date.getMinutes() + duration + processTime);
			row.find('.endTime').val(moment(new_date).format("HH:mm"));
		}
	}

    function clientView() {
        $("#clientView").attr("href", '<c:url value="/book/clientView?userId="/>' + $("#memberId").val());
    }

    function addBundle() {
        var startTime;
        var total = $('#bookItems tbody').find('tr').length;
        $('#bookItems tbody').find('tr').each(function (index, item) {
            if (index == total - 1) {
                startTime = $(item).find('.endTime').val();
            }
        });
        var sameTimeToShareRoom = $('#sameTimeToShareRoom', getContext());
        var isSameTimeToShareRoom = 'NO';
        if (sameTimeToShareRoom.is(":checked")) {
        	isSameTimeToShareRoom = 'YES';
        }
        $("#addBundle").attr("href", '<c:url value="/book/toChooseBundle?shopId="/>' + $("#shopId").val()
            + "&bookingDate=" + $("#startAppointmentTime").val() + "&startTime=" + startTime);
    }

    function removeBundle(btnDom) {
        var timestamp = $(btnDom).parents('tr').data('timestamp');
        $('#bookItems tbody').find('tr').each(function () {
            var trBundleId = $(this).data('timestamp');
            if (trBundleId == timestamp) {
                $(this).remove();
            }
        });
    }

    function searchProductOptionList(obj){
        $.ajax({
            url: '<c:url value="/po/searchProductOptionList?code="/>'+obj.value,
            type: "POST",
            dataType: "json",
            success: function (response) {

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
						productId:res.productId,
						processTime: res.processTime,
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
	
	/*
	$( ".select-treatment" ).autocomplete({
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
						value: res.treatmentCode+' | '+res.treatmentName+' | '+res.duration+' | '+res.price
					}
				}));
			}
     	}); 
		},
		select: function( event, ui ) {
			$(this).parent().find('input[name=productOptionId]').val(ui.item.id);
            //console.log($(this).parent().find('input[name=productOptionId]').val());
        }
	});
	*/
	
</script>
