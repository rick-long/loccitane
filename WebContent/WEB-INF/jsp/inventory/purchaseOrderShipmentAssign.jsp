<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="row new-row-width">
    <label><spring:message code="label.products"/> </label>
    <table class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="label.name"/></th>
            <c:forEach items="${shopList}" var="item">
                <th>${item.name}</th>
            </c:forEach>
            <th><spring:message code="label.total.qty"/></th>
        </tr>
        </thead>
        <tbody id="productOptionBody">
        <c:forEach items="${inventoryPurchaseOrderShipmentVO.inventoryPurchaseOrderShipmentItemVOs}" var="item">
            <tr class="shipment-item" data-product-option-id="${item.productOption.id}" data-qty="${item.qty}">
                <td>${item.productOption.label6}</td>
                <c:forEach items="${shopList}" var="shop">
                    <td>
                        <input type="text" class="form-control shop-quantity" data-shop-id="${shop.id}" data-product-option-id="${item.productOption.id}"/>
                    </td>
                </c:forEach>
                <td class="shop-total-quantity" data-total-qty="${item.qty}" data-current-qty="0">0 / ${item.qty}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    $(function () {
        
        $('input.shop-quantity', Dialog.getContext()).change(function () {
            var $this = $(this);

            var currentQty = 0;
            $this.parents('tr').find('input.shop-quantity').each(function (index, item) {
                var val = +$(item).val();
                currentQty += (val ? val : 0);
            });
            console.info(currentQty);
            var totalQty = $this.parent().siblings('td.shop-total-quantity');
            totalQty.data('current-qty', currentQty);
            totalQty.html(currentQty + " / " + totalQty.data('total-qty'));
        });
    });
</script>