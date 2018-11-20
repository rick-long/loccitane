<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/front/common/jspInit.jsp" %>
<c:url var="url" value="/front/book/confirm"/>
<form:form modelAttribute="frontBookVO" action='${url}' data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
    <c:if test="${waiting}">
        <input type="hidden" name="statue" value="WAITING"/>
    </c:if>
    <div class="row new-row-width">
        <label><spring:message code="front.label.select.therapists"/> </label>
        <table id="treatmentTable" class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="left.navbar.product"/> </th>
                <th><spring:message code="label.product.option"/> </th>
                <th><spring:message code="label.therapist"/> </th>
                <th><spring:message code="front.label.assign.room"/> </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${frontBookVO.frontBookItemVOs}" var="item" varStatus="status">
                <tr>
                    <td>${item.frontBookVO.productOption.product.name}</td>
                    <td>${item.frontBookVO.productOption.label}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not waiting && not empty item.availableTherapistList}">
                                <input type="hidden" name="frontBookItemVOs[${status.index}].id" value="${item.id}"/>
                                <form:select path="frontBookItemVOs[${status.index}].therapistId" class="selectTherapists selectpicker form-control">
                                    <form:option value="" label="Please Select"/>
                                    <c:forEach items="${item.availableTherapistList}" var="therapist">
                                        <form:option value="${therapist.id}" label="${therapist.displayName}"/>
                                    </c:forEach>
                                </form:select>
                            </c:when>
                            <c:otherwise>
                                <div class="error"><spring:message code="front.label.no.therapist.available"/> </div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not waiting && not empty item.room}">
                                ${item.room.name}
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
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <a href='<c:url value="/front/book/add"/>' class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/> </a>
                <button type="submit" class="btn btn-default" id="next">${waiting ? "Waiting" : "Next"}</button>
            </div>
        </div>
    </div>
</form:form>

<script type="text/javascript">
    $(function () {
        var parent = $('form');
        $('.selectTherapists', parent).click(function () {
            var $this = $(this);
            $this.find('option').prop('disabled', false);
            $('.selectTherapists', parent).not($this).each(function () {
                var value = $(this).val();
                if (value) {
                    $this.find('option[value=' + value + ']').prop('disabled', true);
                }
            });
        });

        parent.submit(function (event) {
            <c:if test="${not waiting}">
            var selectTherapists = $('select.selectTherapists', parent).filter(function () {
                return !this.value; // 表示过滤那些还没有选择中的输入框
            });
            if (selectTherapists.length > 0) {
                BootstrapDialog.alert({
                    title: '<spring:message code="lable.error"/>',
                    message: '<spring:message code="front.label.please.select.therapists"/> '
                });
                event.preventDefault();
                return false;
            }
            </c:if>
        });
    });
</script>
