<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/front/common/jspInit.jsp" %>
<c:url var="url" value="/front/book/confirm"/>
<div class="row new-row-width">
    <table id="treatmentTable" class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="label.treatment"/></th>
            <th><spring:message code="label.therapist"/></th>
            <th><spring:message code="label.therapist.on.request"/></th>
            <th><spring:message code="label.room"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${frontBookVO.frontBookItemVOs}" var="item" varStatus="status">
            <tr>
                <td>${item.productOption.label3}</td>
                <td>
                    <select class="selectTherapists selectpicker form-control" data-time='<fmt:formatDate value="${item.appointmentTime}" pattern="yyyy-MM-dd HH:mm"/>' data-product-option-id="${item.productOption.id}" data-room-id="${item.room.id}">
                        <option value=""><spring:message code="label.option.select.single"/> </option>
                        <optgroup label='<spring:message code="label.book.available.therapist"/>'>
                            <c:forEach items="${item.availableTherapists}" var="therapist">
                                <option value="${therapist.id}" data-available="true"
                                        <c:if test="${status.index eq 0 && therapist.id eq preferTherapistId}">selected</c:if>>${therapist.displayName}</option>
                            </c:forEach>
                        </optgroup>
                        <optgroup label='<spring:message code="label.book.not.available.therapist"/>'>
                            <c:forEach items="${item.notAvailableTherapists}" var="therapist">
                                <option value="${therapist.id}" data-available="false">${therapist.displayName}</option>
                            </c:forEach>
                        </optgroup>
                    </select>
                </td>
                <td>
                    <input type="checkbox" class="onRequest">
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

<script type="text/javascript">
    var selectTherapists = $('select.selectTherapists', getContext());
    selectTherapists.selectpicker({});
    selectTherapists.on('show.bs.select', function () {
        var $this = $(this);
        var html = [];
        html.push('<input type="hidden" name="shopId" value="${frontBookVO.shopId}"/>');
        var index = 0;
        selectTherapists.not($this).each(function () {
            var $self = $(this);
            if ($self.val()) {
                html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productOptionId" value="{1}"/>', index, $self.data('product-option-id')));
                html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].appointmentTime" value="{1}"/>', index, $self.data('time')));
                html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].therapistId" value="{1}"/>', index, $self.val()));
                index++;
            }
        });
        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].productOptionId" value="{1}"/>', index, $this.data('product-option-id')));
        html.push(String.format('<input type="hidden" name="bookItemVOs[{0}].appointmentTime" value="{1}"/>', index, $this.data('time')));

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
                        if (item.groupName == "NOT_BLOCKED") {
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

    var firstBookItem = $('#treatmentTable', getContext()).find("tr:eq(1)");
    if (firstBookItem.find('.selectTherapists').selectpicker('val')) {
        firstBookItem.find('.onRequest').prop("checked", true); // 设置选中
    }

    selectTherapists.on('rendered.bs.select', function () {
        var $this = $(this);
        $this.data('available', $this.find("option:selected").data('available') ? true : false);
    });

    <c:if test="${not empty error}">
    BootstrapDialog.alert({
        title: "<spring:message code="lable.error"/>",
        message: "${error}",
        callback: function () {
            $('#previous', getContext()).trigger('click');
        }
    });
    </c:if>
</script>
