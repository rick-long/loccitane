<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/front/common/jspInit.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/front" %>

<c:forEach items="${bookItemVOList}" var="item">
    <c:set var="selectedTherapistLength" value="${item.therapistVOS.size()}"/>
    <ul class="bookItem">
        <div class="remove_box">
            <img rc='<c:url value="/resources/img/front/remove.png"/>' alt=""/>
            <div class="clear removeItem" data-id="${item.id}"><span class="glyphicon glyphicon-remove"></span></div>
        </div>
        <li><spring:message code="label.start.time"/> : <fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/>
        </li>
        <li>
          <spring:message code="label.end.time"/> : <fmt:formatDate value="${item.endAppointmentTime}" pattern="HH:mm"/>
        </li>
        <li>
            <spring:message code="label.treatment"/> : ${item.productName}
        </li>
        <li>
            <c:choose>
                <c:when test="${item.requireSelectTherapist}">
                    <spring:message code="label.therapist"/> :
                    <c:forEach begin="0" end="${item.productOption.capacity - 1}" var="therapistItem"
                               varStatus="indexStatus">
                        <c:set var="currentTherapist" value="${item.therapistVOS.get(therapistItem).therapist}"/>
                        <div class="selectedTherapists">
                            <select class="selectTherapists selectpicker" data-book-item-id="${item.id}"
                                    data-therapist-index="${therapistItem}">
                                <option value=""><spring:message code="label.sales.pleaseselect"/> </option>
                                <optgroup label="Available Therapists">
                                    <c:forEach items="${item.availableTherapists}" var="therapist">
                                        <option value="${therapist.id}"
                                                data-available="true" ${currentTherapist ne null && therapist.id eq currentTherapist.id ? 'selected':''}>${therapist.displayName}</option>
                                    </c:forEach>
                                </optgroup>
                                <optgroup label="Not Available Therapists">
                                    <c:forEach items="${item.notAvailableTherapists}" var="therapist">
                                        <option value="${therapist.id}"
                                                data-available="false" ${currentTherapist ne null && therapist.id eq currentTherapist.id ? 'selected':''}>${therapist.displayName}</option>
                                    </c:forEach>
                                </optgroup>
                            </select>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <spring:message code="label.therapist"/> :
                    <c:forEach items="${item.therapistVOS}" var="requestTherapist" varStatus="status">
                        ${requestTherapist.therapist ne null ? requestTherapist.therapist.displayName : "No therapist available!"}${status.last? "" : ", "}
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </li>
        <li>
            <spring:message code="label.room"/> : ${item.room ne null ? item.room.displayName : "No rooms available!"}
        </li>
        <li>
            <spring:message code="label.sales.status"/> : <samp <c:if test="${item.status eq 'WAITING' }">style="color: red ;font-weight:bold"</c:if> > <spring:message code="label.book.status.${item.status}"/></samp>
        </li>
    </ul>
</c:forEach>
<div class="noBookItems ${empty bookItemVOList ? '' : 'hide'}">
    <spring:message code="front.label.no.booking.item"/>
</div>

<script type="text/javascript">
    $('.removeItem').click(function () {
        var $self = $(this);
        BootstrapDialog.confirm({
            title: '<spring:message code="label.notification"/> ',
            message: '<spring:message code="lable.are.you.sure.to.cancel"/> ',
            callback: function (status) {
                if(status){
                    if ($('.bookItem').length <= 1) {
                        $("#confirmArea").addClass('hide');
                        $('.noBookItems').removeClass('hide');
                        $('.booking').addClass('hide');
                    }
                    $self.parents('.bookItem').remove();
                    $.post('<c:url value="/front/book/removeItem"/>', {id: $self.data('id')}, function () {
                    });
                }

            }
    });
    });
    var selectTherapists = $('select.selectTherapists');
    selectTherapists.selectpicker({});
    selectTherapists.on('changed.bs.select', function () {
        var $this = $(this);
        // 获取所有已经选择的therapist的bookItem信息

        var param = {
            bookItemId: $this.data('book-item-id'),
            therapistId: $this.val(),
            therapistIndex: $this.data('therapist-index'),
            available: $this.find('option:selected').data('available')
        };
        $.post('<c:url value="/front/book/ajaxSelectTherapist"/>', param, function (res) {
            if (res) {
                $('.book-item-list').html(res);
            }
        });
    });


</script>