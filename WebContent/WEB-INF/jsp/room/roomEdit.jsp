<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div id="roomAddContext">
    <c:url var="editUrl" value="/room/edit"/>
    <form:form modelAttribute="roomEditVO" data-form-token="${TokenUtil.get(pageContext)}" id="roomEditForm" action="${editUrl}" method="post" class="form-horizontal">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.shop"/>*</label>
            <div class="col-lg-5">
                    ${roomEditVO.shop.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
            <div class="col-lg-5">
                <form:input path="name" id="name" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.capacity"/>*</label>
            <div class="col-lg-5">
                <form:input path="capacity" id="capacity" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
            <div class="col-lg-5">
                <form:textarea path="remarks" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.category"/></label>
            <div id="categoryTreeId" class="col-lg-5 margin_float">
                <%--<ul id="categoryTree" class="ztree" style="width:360px; overflow:auto;"></ul>--%>
            </div>
        </div>

        <input type="hidden" name="id" value="${roomEditVO.id}"/>
        <div class="modal-footer">
            <div class="bootstrap-dialog-footer">
                <div class="bootstrap-dialog-footer-buttons">
                    <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
                        <spring:message code="label.button.submit"/>
                    </button>
                </div>
            </div>
        </div>
        <c:forEach items="${room.allCategoryIds}" var="item">
            <input name="categoryIds" value="${item}" type="hidden">
        </c:forEach>
        <c:forEach items="${room.allTreatmentIds}" var="item">
            <input name="productIds" value="${item}" type="hidden">
        </c:forEach>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function () {

        // 加载categoryProductTree
        $('#categoryTreeId', Dialog.getContext()).load('<c:url value="/category/categoryProductTree"/>', {
            dataUrl: '<c:url value="/category/getSimpleCategoryNodes?roomId=${roomEditVO.id}"/>'
        });

        $('form', Dialog.getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                name: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                capacity: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: 'Should be a positive integer.'
                        }
                    }
                }
            }
        });
    });
</script>
