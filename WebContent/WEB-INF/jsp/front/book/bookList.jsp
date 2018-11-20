<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<t:layout>
    <div id="bookManagement" class="management">
        <h3><spring:message code="label.book.search.management"/>
            <a href='<c:url value="/front/book/toAdd"/>' class="btn btn-primary" data-width="680"
               data-reload-btn="a.search-btn" data-title='<spring:message code="label.book.add.management"/>'>
                <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
            </a>
        </h3>
        <c:url var="pageUrl" value="/front/book/list"/>
        <form:form id="commonSearchForm" modelAttribute="bookListVO" method="post" class="form-inline"
                   action="${pageUrl}">
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="label.status"/></label>
                <div class="col-lg-5">
                    <form:select id="status" path="status" class="selectpicker form-control">
                        <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                        <form:option value="PENDING"><spring:message code="label.book.status.PENDING"/></form:option>
                        <form:option value="CONFIRM"><spring:message code="label.book.status.CONFIRM"/></form:option>
                        <form:option value="CHECKIN_SERVICE"><spring:message code="label.book.status.CHECKIN_SERVICE"/></form:option>
                        <form:option value="COMPLETE"><spring:message code="label.book.status.COMPLETE"/></form:option>
                        <form:option value="NOT_SHOW"><spring:message code="label.book.status.NOT_SHOW"/></form:option>
                        <form:option value="CANCEL"><spring:message code="label.book.status.CANCEL"/></form:option>
                        <form:option value="WAITING"><spring:message code="label.book.status.WAITING"/></form:option>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default search-btn">
                        <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/>
                    </button>
                </div>
            </div>

            <div id="pageList">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><spring:message code="label.shop"/></th>
                        <th><spring:message code="label.appointment.time"/></th>
                        <th width="550px"><spring:message code="label.item.details"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="item">
                        <tr data-id="${item.id}">
                            <td>${item.shop.name}</td>
                            <td><fmt:formatDate value="${item.appointmentTime}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <c:forEach items="${item.bookItems}" var="bookItem" varStatus="status">
                                    <div>
                                        <spring:message code="label.start.time"/>:
                                        <fmt:formatDate value="${bookItem.appointmentTime}" pattern="HH:mm"/><br/>
                                        <spring:message code="label.treatment"/>: ${bookItem.productName}<br/>
                                        <c:if test="${not empty bookItem.therapistList}">
                                            <spring:message code="label.therapist"/>:
                                            <c:forEach items="${bookItem.therapistList}" var="therapist"
                                                       varStatus="therapistStatus">
                                                ${therapist.fullName}${therapistStatus.last?"" : ","}
                                            </c:forEach>
                                            <br/>
                                        </c:if>
                                        <c:if test="${bookItem.room ne null}">
                                            <spring:message code="label.room"/>: ${bookItem.room.name}
                                            <br/>
                                        </c:if>
                                        <spring:message code="label.status"/>:
                                        <spring:message code="label.book.status.${bookItem.status}"/>
                                    </div>
                                </c:forEach>
                            </td>
                            <td>
                                <c:if test="${item.status eq 'PENDING' or item.status eq 'WAITING'}">
                                    <a href='<c:url value="/front/book/cancel?bookId=${item.id}"/>'
                                       class="btn btn-primary cancel">
                                        <i class="glyphicon glyphicon-remove"></i>
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:import url="/WEB-INF/jsp/front/common/pagination.jsp"/>
            </div>
        </form:form>
    </div>
    <script type="text/javascript">
        $(function () {
            // 注册cancel事件
            $('.cancel', getContext()).click(function (e) {
                e.preventDefault();
                var $this = $(this);
                BootstrapDialog.confirm({
                    title: '<spring:message code="front.label.cancel.confirm"/> ',
                    message: '<spring:message code="front.label.long.message6"/> ',
                    callback: function (res) {
                        if (res) {
                            // 發起動作請求
                            $.post($this.attr('href'), function () {
                                window.location.href = '<c:url value="/front/book/list"/>';
                            });
                        }
                    }
                });
            });
        });
    </script>
</t:layout>

