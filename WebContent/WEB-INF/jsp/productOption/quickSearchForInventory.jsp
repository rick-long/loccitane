<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <c:url var="url" value="/po/quickSearchForInventoryList"/>
    <form id="commonSearchForm" method="post" class="form-horizontal" action="${url}">
        <input type="hidden" name="supplierId" value="${productOptionListVO.supplierId}"/>
        <div class="form-group">
            <div class="col-sm-5">
                <input name="key" id="key" class="form-control" placeholder='<spring:message code="label.name"/>'/>
            </div>
            <a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                <spring:message code="label.button.search"/>
            </a>
        </div>
    </form>
    <div id="pageList"></div>
    <div>
        <div class="form-group">
            <label class="control-label"><spring:message code="label.selected.product.optionList"/></label>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.reference"/></th>
                <th><spring:message code="label.default.price"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody id="selectedProductOptions">
            </tbody>
        </table>
    </div>
</div>

<div class="modal-footer">
    <div class="bootstrap-dialog-footer">
        <div class="bootstrap-dialog-footer-buttons">
            <button class="btn btn-default" id="done"><spring:message code="label.button.add"/></button>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function () {
        
        // 选择product option 事件
        $('#pageList', Dialog.getContext()).on('click', 'button.selectPOBtn', function () {
            var $this = $(this);
            var html = $($this.parents('tr').clone());
            html.find('.selectPOBtn').remove();
            html.find('.removePOBtn').removeClass('hide');
            html.appendTo($('#selectedProductOptions', Dialog.getContext()));
        });

        $('#selectedProductOptions', Dialog.getContext()).on('click', 'button.removePOBtn', function () {
            $(this).parents('tr').remove();
        });


        $('#done', Dialog.getContext()).click(function () {
            var parentDialog = Dialog.getParentContext();
            var html = $('#selectedProductOptions', Dialog.getContext()).find('tr').clone();
            if ($.trim(html)) {
                html = $(html);
                html.find('td.hide').removeClass('hide');

                // 注册删除事件
                html.find('.removePOBtn').click(function () {
                    $(this).parents('tr').remove();
                });

                html.appendTo($("#productOptionBody", parentDialog));
                Dialog.get().close();
            }
        });
    });
</script>

