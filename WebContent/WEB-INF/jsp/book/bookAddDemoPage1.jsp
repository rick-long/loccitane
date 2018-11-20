<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:set var="isEdit" value="${bookVO.state eq 'EDIT'}"/>
<c:url var="url" value="/book/add"/>
<form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${book.id}"/>
    <input type="hidden" name="state" value="${bookVO.state}"/>
    <div id="baseStep" class="baseStep step active">
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
                                        <c:if test="${bookVO.shopId eq item.id}">selected</c:if>>${item.name}</option>
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
                        <div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
                           	<input type="hidden" name="memberId" id="memberId" class="form-control"/>
                     		<input type="hidden" name="username" id="username" class="form-control"/>
                     		<input type="text" name="fullName" id="fullName" class="form-control quick-search" readonly/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-search"></span>
                                </span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.appointment.time"/></label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    <input id="startAppointmentTime" name="startAppointmentTime" class="form-control startAppointmentTime" value='<fmt:formatDate value="${bookVO.startAppointmentTime}" pattern="yyyy-MM-dd"/>' readonly>
                    <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                </div>
            </div>
        </div>
        <%--<div class="form-group">
            <label for="guestAmount" class="col-lg-4 control-label">
                <spring:message code="label.guest.amount"/></label>
            <div class="col-lg-5">
                <select id="guestAmount" name="guestAmount" class="selectpicker form-control">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
        </div>
        <div id="shareRoomGroup" class='form-group <c:if test="${not (isEdit && bookVO.guestAmount gt 1)}">hide</c:if>'>
            <label for="guestAmount" class="col-lg-4 control-label">
                <spring:message code="label.share.room"/></label>
            <div class="col-lg-5">
                <input type="checkbox" id="shareRoom" name="shareRoom" checked/>
            </div>
        </div>
        --%>

        <div class="row new-row-width">
            <table id="treatmentTable" class="table table-striped">
                <thead>
                <tr>
                    <th><spring:message code="label.treatment"/></th>
                    <th><spring:message code="label.guest.amount"/></th>
                    <th><spring:message code="label.share.room"/></th>
                    <th><spring:message code="label.start.time"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <div class="select-category" data-selectable="option"></div>
                    </td>
                    <td>
                        <select id="guestAmount" name="guestAmount" class="selectpicker form-control">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </td>
                    <td>
                        <input type="checkbox" class="onRequest">
                    </td>
                    <td>
                        <div class="input-group date form_time">
                            <input name="startAppointmentTime" class="form-control startAppointmentTime" value='<fmt:formatDate value="${bookVO.startAppointmentTime}" pattern="yyyy-MM-dd HH:mm"/>' readonly>
                            <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                        </div>
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
                <button type="button" class="btn btn-default dialogSubmitBtn" data-skip-validate="true" id="submit">
                    <spring:message code="label.book.status.save"/>
                </button>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {
        $('.select-category', Dialog.getContext()).selectCategory({});
        $('.selectpicker', Dialog.getContext()).selectpicker({});
        // init guest amount
        <c:if test="${isEdit}">
        $('#guestAmount', Dialog.getContext()).val(${book.guestAmount});
        </c:if>

        $('#shopId', Dialog.getContext()).change(function () {
            var productOptionSearchUrl = '<c:url value="/po/quickSearchForSingleBook"/>?shopId=';
            var productOptionGroup = $('#productOptionGroup', Dialog.getContext());
            productOptionGroup.data('url', productOptionSearchUrl + $(this).val());
            productOptionGroup.find('input').val('');
        }).trigger('change');

        $('#startAppointmentTime', Dialog.getContext()).change(function () {
            $(this).trigger('input');
        });

        // 註冊對話框的提交事件
        $('#previous', Dialog.getContext()).hide();
        $('#waiting', Dialog.getContext()).hide();
        $('#submit', Dialog.getContext()).hide();

        $('#startAppointmentTime', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d H:i',
            timepicker: true,
            datepicker: true,
            step: 15,
            value: '${currentDateTime}',
            allowTimes: [${allowTimes}]
        });

    });
</script>
