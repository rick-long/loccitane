<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div>
    <form:form method="post" modelAttribute="commissionTemplateVO" class="form-horizontal" enctype="multipart/form-data">
        <form:hidden path="id"/>
        <div class="form-group">
            <label for="name" class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
            <div class="col-lg-5">
                <input type="text" id="name" name="name" value="${commissionTemplate.name}" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-lg-4 control-label">
                <spring:message code="label.description"/>*</label>
            <div class="col-lg-5">
                <textarea id="description" name="description" class="form-control">${commissionTemplate.description}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="content" class="col-lg-4 control-label"><spring:message code="label.content"/>*</label>
            <div class="col-lg-5">
                <textarea id="content" name="content" class="form-control">${commissionTemplate.content}</textarea>
            </div>
        </div>
        <div class="row new-row-width">
             <label><spring:message code="label.commission.attribute.key"/>*</label>
             <table class="table table-striped">
                 <thead>
                 <tr>
                     <th><spring:message code="label.reference"/></th>
                     <th><spring:message code="label.name"/></th>
                     <th><spring:message code="label.description"/></th>
                     <th>
                         <button type="button" id="addRow" class="btn btn-default"><spring:message code="label.addRow"/></button>
                     </th>
                 </tr>
                 </thead>
                 <tbody id="body">
                 <c:forEach items="${commissionTemplate.commissionAttributeKeys}" var="item" varStatus="status">
                     <tr>
                         <td>
                             <input type="text" class="form-control reference" value="${item.reference}"/>
                         </td>
                         <td>
                             <input type="text" class="form-control name" value="${item.name}"/>
                         </td>
                         <td>
                             <input type="text" class="form-control description" value="${item.description}"/>
                         </td>
                         <td>
                             <button type="button" class='btn btn-warning removePOBtn<c:if test="${status.first}"> hide</c:if>'><spring:message code="label.button.remove"/></button>
                         </td>
                     </tr>
                 </c:forEach>
                 </tbody>
             </table>
         </div>
    </form:form>
</div>
<div class="modal-footer">
    <div class="bootstrap-dialog-footer">
        <div class="bootstrap-dialog-footer-buttons">
            <button class="btn btn-default" id="submit"><spring:message code="label.book.status.save"/></button>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
       
        var form = $('form', Dialog.getContext());

        // 註冊對話框的提交事件
        var submitBtn = $('#submit', Dialog.getContext());
        
	    $('.removePOBtn', Dialog.getContext()).click(function () {
	        $(this).parents('tr').remove();
	    });
	
	    // 添加一行
	    $('#addRow', Dialog.getContext()).click(function () {
	        var html = $($('#body', Dialog.getContext()).find('tr:first').clone()); // 克隆第一行的数据
	        html.find('button.removePOBtn').removeClass('hide').click(function () {
	            $(this).parents('tr').remove();
	        });
	        html.find('.form-control').val('');
	        html.appendTo($('#body', Dialog.getContext()));
	    });

        submitBtn.click(function () {
        	
        	 var bootstrapValidator = form.data('bootstrapValidator');
             bootstrapValidator.validate(); // 验证
             var result = bootstrapValidator.isValid(); // 取出结果
             if (!result) {
                 return;
             }
          	// 验证 discount attribute key
           	var inputFlag = $('input.reference', Dialog.getContext()).filter(function () {
                 return !this.value;
             }).length;
             if (inputFlag > 0) {
                 Dialog.alert({
                     title: '<spring:message code="lable.error"/>',
                     message: '<spring:message code="lable.bonus.please.input.reference"/> '
                 });
                 return;
             }

             inputFlag = $('input.name', Dialog.getContext()).filter(function () {
                 return !this.value;
             }).length;

             if (inputFlag > 0) {
                 Dialog.alert({
                     title: '<spring:message code="lable.error"/>',
                     message: '<spring:message code="lable.bonus.please.input.name"/> '
                 });
                 return;
             }

             // reference 重复判断
             var inputValues = [];
             var referenceInput = $('input.reference', Dialog.getContext());
             inputFlag = false;
             referenceInput.each(function (index, item) {
                 var val = $(item).val();
                 if ($.inArray(val, inputValues) > -1) {
                     inputFlag = true;
                     return false;
                 } else {
                     inputValues.push(val);
                 }
             });
             if (inputFlag) {
                 Dialog.alert({
                     title: '<spring:message code="lable.error"/>',
                     message: '<spring:message code="lable.bonus.duplicate.input.reference"/> '
                 });
                 return;
             }

             form.find('input[name^="commissionAttributeKeyVO"]').remove();
             // 添加参数
             var html = [];
             var trs = $('#body', Dialog.getContext()).find("tr");
             trs.each(function (index, item) {
                 var $item = $(item);
                 html.push(String.format('<input type="hidden" name="commissionAttributeKeyVO[{0}].reference" value="{1}"/>', index, $item.find('.reference').val()));
                 html.push(String.format('<input type="hidden" name="commissionAttributeKeyVO[{0}].name" value="{1}"/>', index, $item.find('.name').val()));
                 html.push(String.format('<input type="hidden" name="commissionAttributeKeyVO[{0}].description" value="{1}"/>', index, $item.find('.description').val()));
                 html.push(String.format('<input type="hidden" name="commissionAttributeKeyVO[{0}].displayOrder" value="{1}"/>', index, index + 1));
             });

             $(html.join('')).appendTo(form);
             var formData = new FormData(form[0]);
            var formData = new FormData(form[0]);
            $.ajax({
                url: '<c:url value="/commission/commissionTemplateSave"/>',
                type: 'POST',
                data: formData,
                async: false,
                success: function (res) {
                    if (res['statusCode'] === 200) {
                    	/* $('a.search-btn', Dialog.getParentContext()).trigger('click'); // 触发重新查询事件 */
                        Dialog.get().close();
                    }
                },
                cache: false,
                contentType: false,
                processData: false
            });
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
                }
            }
        });
    });
</script>
