<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.price"/></th>
        <th><spring:message code="label.duration"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.label2}</td>
            <td>${item.getFinalPrice(shopId)}</td>
            <td>${item.duration}</td>
            <td>
                <button class="btn btn-primary selectPOBtn" data-product-name="${item.label3}" data-product-option-id="${item.id}">
                    <spring:message code="label.select"/>
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/po/quickSearchForSingleBookList" scope="request"/>
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