<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="form-group">
    <label class="col-lg-3 control-label"><spring:message code="label.product.option"/></label>
    <div class="col-lg-5">
        ${inventoryTransferVO.inventory.productOption.label6}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-3 control-label"><spring:message code="label.from.shop"/></label>
    <div class="col-lg-5">
        ${inventoryTransferVO.fromShop.name}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-3 control-label"><spring:message code="label.to.shop"/></label>
    <div class="col-lg-5">
        ${inventoryTransferVO.toShop.name}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-3 control-label"><spring:message code="label.entry.date"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${inventoryTransferVO.entryDate}" pattern="yyyy-MM-dd"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-3 control-label"><spring:message code="label.qty"/></label>
    <div class="col-lg-5">
        ${inventoryTransferVO.qty}
    </div>
</div>
<script type="text/javascript">
    $(function () {
       
        <c:if test="${error}">
        console.info($('#previous', Dialog.getContext()));
        $('#previous', Dialog.getContext()).trigger('click');
        Dialog.alert({
            title: "<spring:message code="lable.error"/>",
            message: "<spring:message code="label.inventory.not.enough.inventory.qty.for.transfer"/> "
        });
        </c:if>
    });
</script>