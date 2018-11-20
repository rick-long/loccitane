<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="row new-row-width">
	<spring:message code="label.prepaid.product.name"/> : ${pname}</br>
    <c:if test="${pdkList !=null && pdkList.size() > 0 }">
        <fieldset>
            <legend><spring:message code="label.product.descriptions"/></legend>
            <c:forEach items="${pdkList}" var="pdk" varStatus="idx">
                <div class="form-group">
                    <label class="col-lg-4 control-label">${pdk.name}</label>
                    <div class="col-lg-5">
                        <c:if test="${pdk.uiType eq 'text-area' }">
                            <textarea id="pdvalues[${idx.index }].value" name="pdvalues[${idx.index }].value" class="form-control">${pdk.defaultValue}</textarea>
                        </c:if>
                        <c:if test="${pdk.uiType eq 'text' }">
                            <input type="text" id="pdvalues[${idx.index }].value" name="pdvalues[${idx.index }].value" class="form-control" value="${pdk.defaultValue}"/>
                        </c:if>
                    </div>
                    <input type="hidden" name="pdvalues[${idx.index }].key" id="pdvalues[${idx.index }].key" value="${pdk.id}"/>
                </div>
            </c:forEach>
        </fieldset>
    </c:if>

    <c:if test="${pokList != null && pokList.size()>0}">
        <fieldset>
            <legend><spring:message code="label.product.options"/></legend>
            <table class="table table-striped">
                <thead>
                <tr>
                    <c:forEach items="${pokList}" var="pok" varStatus="idx">
                        <th width="30%">${pok.name}</th>
                    </c:forEach>
                    <th width="8%"></th>
                </tr>
                </thead>
                <tbody>
                <tr id="productOptionTemplate">
                    <c:forEach items="${pokList}" var="pok" varStatus="idx">
                        <td>
                            <c:if test="${pok.uiType eq 'text' }">
                                <input type="text" class="form-control pokInput" data-pok-id="${pok.id}" value="${pok.defaultValue}"/>
                            </c:if>
                            <div class="input-value"></div>
                        </td>
                    </c:forEach>
                    <td>
                        <button class="btn btn-primary addPOK">
                            <i class="glyphicon glyphicon-plus"></i>
                        </button>
                        <button class="btn btn-primary removePOK hide">
                            <i class="glyphicon glyphicon-minus"></i>
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset>
            <legend><spring:message code="label.product.options.added"/></legend>
            <table class="table table-striped">
                <thead>
                <tr>
                    <c:forEach items="${pokList}" var="pok" varStatus="idx">
                        <th width="30%">${pok.name}</th>
                    </c:forEach>
                    <th width="8%"></th>
                </tr>
                </thead>
                <tbody id="productOptionAddTable">

                </tbody>
            </table>
        </fieldset>
    </c:if>
</div>
<script type="text/javascript">
    $(function () {
        
        $('.addPOK', Dialog.getContext()).click(function () {
            var $html = $($(this).parents('tr').clone());
            var hasInput = false;
            $html.find('input.pokInput').each(function () {
                var $this = $(this);
                //$this.hide().siblings('.input-value').html($this.val());
                hasInput = $this.val() || hasInput;
            });

            if (hasInput) {
                // 清除之前的输入
                $('#productOptionTemplate',Dialog.getContext()).find('.pokInput').val('');
                $html.find('.addPOK').addClass('hide');
                $html.find('.removePOK').removeClass('hide');
                $html.appendTo($('#productOptionAddTable', Dialog.getContext()));
            } else {
                Dialog.alert('<spring:message code="label.product.please.input.product.option.value"/> ');
            }
        });

        $('#productOptionAddTable', Dialog.getContext()).on('click', 'button.removePOK', function () {
            $(this).parents('tr').remove();
        });
    });
</script>

<%--<div class="col-lg-6 col-lg-offset-0">
                   <form:form modelAttribute="poAddVO" id="poAddVOForm" method="post" class="form-horizontal" action=''>
                       <table id="po_item_list" style="border-bottom: 1px solid #efefef;">
                           <c:forEach items="poItemList" var="poItem" varStatus="idx">
                               <tr>
                                   <td>
                                       <c:set var="index" value="${idx.index }" scope="request"/>
                                       <%@ include file="/WEB-INF/jsp/product/productOptionItem.jsp"%>
                                   </td>
                               </tr>
                           </c:forEach>
                       </table>

                       <div class="form-group">
                           <div class="col-lg-9 col-lg-offset-3">
                               <button type="button" class="btn btn-primary" id="submitBtn"><spring:message code="label.button.saveOrUpdate"/></button>
                           </div>
                       </div>
                   </form:form>
               </div>--%>
