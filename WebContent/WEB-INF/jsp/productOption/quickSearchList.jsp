<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped">
    <thead>
    <tr>
        <th class="col-lg-5"><spring:message code="label.name"/></th>
        <th class="col-lg-5"><spring:message code="label.reference"/></th>
        <th class="col-lg-2"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.label2}</td>
            <td>${item.product.reference }</td>
            <td>
                <button class="btn btn-primary selectPOBtn" data-product-name="${item.product.name}" data-product-option-id="${item.id}">
                    <spring:message code="label.select"/>
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/po/quickSearchList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->

<script type="text/javascript">
    $(function () {
        
        $('.selectPOBtn', Dialog.getContext()).click(function () {
            var $this = $(this);
            var productOptionId = $this.data('product-option-id');
            var productName = $this.data('product-name');
            var parentContext = Dialog.getParentContext();
            var $productName = $('#productName', parentContext);
            $productName.val(productName);
            $('#productOptionId', parentContext).val(productOptionId);
            Dialog.get().close(); // 关闭对话框

            $productName.trigger('input'); // 更新状态

        });
    });
</script>