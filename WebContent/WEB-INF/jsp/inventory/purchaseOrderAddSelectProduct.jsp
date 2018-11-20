<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style>
    .quantity {
        text-align: right;
    }
</style>

<div class="row new-row-width">
    <label><spring:message code="label.inventory.selected.products"/> </label>
    <table class="table table-striped">
        <thead>
        <tr>
            <th class="col-lg-4"><spring:message code="label.name"/></th>
            <th class="col-lg-1"><spring:message code="label.qty"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody id="productOptionBody">
        <c:choose>
            <c:when test="${inventoryPurchaseOrderVO.state eq 'EDIT'}">
                <c:forEach items="${inventoryPurchaseOrder.inventoryPurchaseItems}" var="item" varStatus="status">
                <tr>
                    <td>
                       <%-- <div class="select-category" data-selectable="option" data-root-id="3" data-product-option-id="${item.productOption.id}"></div>--%>
                        <select name="productOptionId">
                            <c:forEach items="${ProductOptions}" var="options">
                                <option value="${options.id}" <c:if test="${item.productOption.id == options.id}">selected</c:if>>${options.label33}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input class="quantity form-control" type="text" value="${item.qty}"/>
                    </td>
                    <td>
                        <button class="addItem">+</button>
                        <c:if test="${status.index gt 0}">
                            <button type="button" class="removeItem">-</button>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td>
                        <%--<div class="select-category" data-selectable="option" data-root-id="3"></div>--%>
                        <select name="productOptionId">
                            <c:forEach items="${ProductOptions}" var="options">
                                <option value="${options.id}">${options.label33}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input class="quantity form-control" type="text"/>
                    </td>
                    <td>
                        <button class="addItem">+</button>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
<table id="template1" class="table table-striped hide">
    <tr>
        <td>
            <select name="productOptionId">
                <c:forEach items="${ProductOptions}" var="options">
                    <option value="${options.id}">${options.label33}</option>
                </c:forEach>
            </select>
        </td>

        <td class="col-lg-1">
            <input class="quantity form-control" type="text"/>
        </td>
        <td>
            <button type="button" class="addItem">+</button>
            <button type="button" class="removeItem hide">-</button>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(function () {
        $('.select-category', Dialog.getContext()).selectCategory({});

        $('.addItem', getContext()).click(function () {
            addItem($(this));
        });

        function addItem($this) {
            var newItem = $('#template1', getContext()).find('tr').clone(false);
            $this.parents('tr').after(newItem);
            $this.addClass('hide'); // 隱藏當前的按鈕

            // 註冊treatment下拉框
            newItem.find('.select-category').selectCategory({});


            newItem.find('.addItem').click(function () {
                addItem($(this));
            });

            newItem.find('.removeItem').click(function () {
                $(this).parents('tr').remove();
                var addItem = $('#productOptionBody', getContext()).find('.addItem');
                if (addItem.length == 1) {
                    addItem.removeClass('hide');
                }
            }).removeClass('hide');
        }
    });
</script>
