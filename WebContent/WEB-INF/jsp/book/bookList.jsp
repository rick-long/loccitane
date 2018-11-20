<%@ page import="com.spa.constant.CommonConstant" %>
<%@ page import="org.spa.utils.WebThreadLocal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.book.number"/></th>
        <th><spring:message code="label.appointment.time"/></th>
        <th><spring:message code="label.shop"/></th>
        <th><spring:message code="label.fullName"/></th>
        <%--<th><spring:message code="label.guest.amount"/></th>--%>

        <!-- <th>Booking Status></th> -->
        <%--<th><spring:message code="label.book.channel"/></th>--%>
        <%--<th><spring:message code="label.book.paid.advance"/></th>--%>
        <th class="col-lg-3"><spring:message code="label.book.items.details"/> </th>
        <th><spring:message code="label.book.change.status"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.reference}</td>
            <td><fmt:formatDate value="${item.appointmentTime}" pattern="yyyy-MM-dd"/></td>
            <td>${item.shop.name}</td>
            <td>${item.user.fullName}</td>
            <%--<td>${item.guestAmount}</td>--%>

           <%--  <td>${item.status}</td> --%>
            <%--<td><spring:message code="label.book.channel.${item.bookingChannel}"/></td>--%>
            <%--<td><spring:message code="label.${item.mobilePrepaid}"/></td>--%>
            <td>
                <c:forEach items="${item.bookItems}" var="bookItem">
                    <div>
                       <fmt:formatDate value="${bookItem.appointmentTime}" pattern="HH:mm"/>
                       / <spring:message code="label.book.status.${bookItem.status}"/>
                       / ${bookItem.productOption.labelWithCodeNoBr}
                       <c:if test="${not empty bookItem.therapists}"> / ${bookItem.therapists}</c:if>
                       <c:if test="${not empty bookItem.room}"> / ${bookItem.room.name}</c:if>
                    </div>
                </c:forEach>
           </td>
           
            <td>

                <select id="changeStatus" class="selectpicker form-control changeBookingStatus table-select" data-book-id="${item.id}" >
                    <option value=""><spring:message code="label.option.select.single"/></option>
                    <c:if test="${item.canChangeStatus()}">
                    <option value="CONFIRM"><spring:message code="label.book.status.CONFIRM"/></option>
                    <option value="CHECKIN_SERVICE"><spring:message code="label.book.status.CHECKIN_SERVICE"/></option>
                    <option value="NOT_SHOW"><spring:message code="label.book.status.NOT_SHOW"/></option>
                    <c:if test="${item.mobilePrepaid}">
                        <option value="COMPLETE"><spring:message code="label.book.status.COMPLETE"/></option>
                    </c:if>
                    </c:if>
                </select>

            </td>
            <td>
                <c:choose>
                <c:when test="${item.hasMoreThanOneBookItemCanBePaid && item.status !='PAID'}">
                    <a data-permission="sales:bookToCheckout" href='<c:url value="/sales/bookToCheckout?bookId=${item.id}"/>'
                       title='<spring:message code="label.button.check.out"/>'  class="btn btn-primary  btn-edit form-page" role="button" aria-pressed="true"
                       data-draggable="true" data-title='Check Out'>
                        <i class="glyphicon glyphicon-list-alt"></i>
                    </a>
                </c:when>
                <c:otherwise>
                    <a data-permission="sales:bookToCheckout" href='<c:url value="/sales/bookToCheckout?bookId=${item.id}"/>'
                       title='<spring:message code="label.button.check.out"/>'  class="btn btn-primary  btn-edit form-page disabled" role="button" aria-disabled="true"
                       data-draggable="true" data-title='Check Out'>
                        <i class="glyphicon glyphicon-list-alt"></i>
                    </a>
                </c:otherwise>
                </c:choose>
                <a href='<c:url value="/book/toEdit?id=${item.id}&forward=bookListMenu"/>' title='<spring:message code="label.button.edit"/>' class="btn btn-primary form-page btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.button.edit"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>

                <c:choose>
                <c:when test="${item.status != 'CHECKIN_SERVICE' && item.status != 'COMPLETE' && item.status != 'CANCEL'}">
                <a data-permission="book:cancel" href='<c:url value="/book/cancel?bookId=${item.id}"/>' title='<spring:message code="label.button.cancel"/>'
                   class="btn btn-primary dialog dialog-confirm btn-edit" role="button" aria-pressed="true" data-reload-btn="a.search-btn"
                   data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.cancel"/>'>

                        <i class="glyphicon glyphicon-remove"></i>
                </a>
                </c:when>
                <c:otherwise>
                    <a data-permission="book:cancel" href='<c:url value="/book/cancel?bookId=${item.id}"/>' title='<spring:message code="label.button.cancel"/>'
                       class="btn btn-primary dialog dialog-confirm btn-edit disabled" role="button" aria-disabled="true" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.cancel"/>'>

                        <i class="glyphicon glyphicon-remove"></i>
                    </a>
                </c:otherwise>
                </c:choose>
                <a data-permission="book:bookView" href='<c:url value="/book/bookView?id=${item.id}"/>' title='<spring:message code="label.booking.view"/>'
                   class="btn btn-primary dialog btn-edit"  data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.booking.view"/>'>
                    <i class="glyphicon glyphicon-eye-open"></i>
                </a>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/book/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->

<script type="text/javascript">
    $(function(){
        $(".changeBookingStatus", getContext()).change(function () {
            var $this = $(this);
            if ($this.val()) {
                BootstrapDialog.confirm({
                    title: '<spring:message code="label.book.status.change.confirm"/> ',
                    message: '<spring:message code="label.book.update.all.item.status"/> ' + $this.find('option:selected').text() + '?',
                    callback: function (status) {
                        if (status) {
                            // 發起動作請求
                            $.post('<c:url value="/book/updateAllBookITemStatus"/>', {
                                bookId: $this.data('book-id'),
                                status: $this.val()
                            }, function () {
                                getContext().find('a.search-btn').trigger('click'); // 触发重新查询事件
                            });
                        } else {
                            $this.val('');
                        }
                    }
                });
            }
        });
    });
</script>