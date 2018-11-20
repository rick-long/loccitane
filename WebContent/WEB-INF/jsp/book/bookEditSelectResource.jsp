<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:if test="${bookVO.guestAmount > 1 && not isAssignShareRoom}">
    <div class="warning">Warning:No available room for sharing!</div>
</c:if>

<div id="pageList_new">
    <div><h3><spring:message code="label.book.current.book.item.status"/> :</h3></div>
    <table class="table table-striped">
        <thead>
        <tr style="border-top: 1px solid #b9b9b9;">
            <th width="28%"><spring:message code="label.start.time"/></th>
            <th width="28%"><spring:message code="label.treatment"/></th>
            <th width="24%"><spring:message code="label.therapist"/> / <spring:message code="label.therapist.on.request"/></th>
            <th width="20%"><spring:message code="label.room"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${book.bookItems}" var="item" varStatus="status">
            <tr>
                <td><fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/></td>
                <td>${item.productOption.label3}</td>
                <td>
                    <c:forEach items="${item.bookItemTherapists}" var="itemTherapist">
                        <div>
                            <span>${itemTherapist.user.displayName}</span>
                            <input style="margin-top:-1px;" type="checkbox" ${itemTherapist.onRequest ? 'checked': ''} disabled>
                        </div>
                    </c:forEach>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty item.room}">
                            ${item.room.name}(${item.room.capacity})
                        </c:when>
                        <c:otherwise>
                            <div class="error"><spring:message code="front.label.No.room.available"/> </div>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="pageList_new">
    <div class="row new-row-width">
        <table id="treatmentTable" class="table table-striped">
            <thead>
            <tr>
                <th width="14%"><spring:message code="label.start.time"/></th>
                <th width="14%"><spring:message code="label.end.time"/></th>
                <th width="28%"><spring:message code="label.treatment"/></th>
                <th width="24%"><spring:message code="label.therapist"/> / <spring:message code="label.therapist.on.request"/></th>
                <th width="20%"><spring:message code="label.room"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bookVO.bookItemVOs}" var="item" varStatus="status">
                <tr class="bookItemTr" data-temp-id="${item.tempId}" data-temp-parent-id="${item.tempParentId}" data-end-time='<fmt:formatDate value="${item.endAppointmentTime}" pattern="yyyy-MM-dd HH:mm"/>'
                    data-time='<fmt:formatDate value="${item.appointmentTime}" pattern="yyyy-MM-dd HH:mm"/>' data-product-option-id="${item.productOption.id}" data-room-id="${item.room.id}"
                    data-bundle-id ="${item.bundleId}">
                    <td><fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/></td>
                    <td><fmt:formatDate value="${item.endAppointmentTime}" pattern="HH:mm"/></td>
                    <td>${item.productOption.label3}</td>
                    <td>
                        <c:forEach begin="0" end="${item.productOption.capacity - 1}" var="therapistItem" varStatus="indexStatus">
                            <div class="selectedTherapists" data-therapist-index="${therapistItem}">
                                <select class="selectTherapists selectpicker"  data-time='<fmt:formatDate value="${item.appointmentTime}" pattern="yyyy-MM-dd HH:mm"/>' data-product-option-id="${item.productOption.id}" data-room-id="${item.room.id}">
                                    <option value="">Please Select</option>
                                    <optgroup label="Available Therapists">
                                        <c:forEach items="${item.availableTherapists}" var="therapist">
                                            <option value="${therapist.id}" data-available="true" <c:if test="${status.index eq 0 && therapist.id eq preferTherapistId}">selected</c:if>>${therapist.displayName}</option>
                                        </c:forEach>
                                    </optgroup>
                                    <optgroup label="Not Available Therapists">
                                        <c:forEach items="${item.notAvailableTherapists}" var="therapist">
                                            <option value="${therapist.id}" data-available="false">${therapist.displayName}</option>
                                        </c:forEach>
                                    </optgroup>
                                </select>
                                <input type="checkbox" data-therapist-index="${therapistItem}" class="onRequest">
                            </div>
                        </c:forEach>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty item.room}">
                                ${item.room.name}(${item.room.capacity})
                            </c:when>
                            <c:otherwise>
                                <div class="error"><spring:message code="front.label.No.room.available"/> </div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>	</div>

<script type="text/javascript">
    var selectTherapists = $('select.selectTherapists', Dialog.getContext());
    selectTherapists.selectpicker({});
    selectTherapists.on('show.bs.select', function () {
        var $this = $(this);
        // 获取所有已经选择的therapist的bookItem信息
        var bookItems = $('.bookItemTr', $('#resourceStep'));
        var html = [];
        html.push(String.format('<input type="hidden" name="id" value="{0}"/>', $('#bookId', getContext()).val()));
        html.push(String.format('<input type="hidden" name="shopId" value="{0}"/>', $('#shopId', getContext()).val()));
        bookItems.each(function (index) {
            var $item = $(this);
            html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productOptionId" value="{1}">', index, $item.data('product-option-id')));
            html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].appointmentTime" value="{1}">', index, $item.data('time')));
            html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].endAppointmentTime" value="{1}">', index, $item.data('end-time')));
            html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].bundleId" value="{1}">', index, $item.data('bundle-id')));

            var selectedTherapistsDivs = $item.find('div.selectedTherapists'); // 一个或者多个therapist select list

            selectedTherapistsDivs.each(function () {
                var therapistDiv = $(this);
                var therapistIndex = therapistDiv.data('therapist-index');
                var selectTherapists = therapistDiv.find('select.selectTherapists'); // therapist select框
                var therapistId = selectTherapists.selectpicker('val');
                // 记录已经被block的therapist
                html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].requestTherapistVOs[{1}].therapistId" value="{2}">', index, therapistIndex, therapistId));
                html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].requestTherapistVOs[{1}].currentRequest" value="{2}">', index, therapistIndex, selectTherapists.is($this)));
            });
        });

        var param = $(html.join('')).serialize();
        $.post('<c:url value="/book/getAvailableTherapistList"/>', param, function (res) {
            if (res) {
                html = [];
                html.push('<option value="">Please Select</option>');
                $.each(res, function (index, item) {
                    console.info(item.groupName);
                    if (!item.value) {
                        if (index > 0) {
                            html.push('</optgroup>');
                        }
                        html.push('<optgroup label="' + item.groupName + '">');
                    } else {
                        if(item.groupName == "NOT_BLOCKED") {
                            html.push(String.format('<option value="{0}" data-available="{1}">{2}</option>', item.value, true, item.label));
                        } else {
                            html.push(String.format('<option value="{0}">{1}</option>', item.value, item.label));
                        }
                    }
                });
                html.push('</optgroup>');
                var currentSelect = $this.val();
                $this.html(html.join(''));
                $this.val(currentSelect);
                $this.selectpicker('refresh');
            }
        });
    });

    var firstBookItem = $('#treatmentTable', Dialog.getContext()).find("tr:eq(1)");
    if(firstBookItem.find('.selectTherapists').selectpicker('val')) {
        firstBookItem.find('.onRequest').prop("checked", true); // 设置选中
    }

    selectTherapists.on('rendered.bs.select', function () {
        var $this = $(this);
        $this.data('available', $this.find("option:selected").data('available') ? true : false);
    });

    <c:if test="${not empty error}">
    Dialog.alert({
        title: "Error",
        message: "${error}",
        callback: function () {
            $('#previous', getContext()).trigger('click');
        }
    });
    </c:if>
</script>
