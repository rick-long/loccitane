<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="row new-row-width">
    <c:if test="${not empty pdkList}">
        <fieldset>
           <legend><spring:message code="label.hair.salon.descriptions"/></legend>
            <c:forEach items="${pdkList}" var="pdk" varStatus="idx">
                <div class="form-group">
                    <label class="col-lg-4 control-label">${pdk.name}</label>
                    <div class="col-lg-5">
                        <c:set var="pdValue" value=""/>
                        <c:set var="pdId" value=""/>
                        <c:forEach items="${product.productDescriptions}" var="pd">
                            <c:if test="${pdk.id eq pd.productDescriptionKey.id}">
                                <c:set var="pdValue" value="${pd.value}"/>
                                <c:set var="pdId" value="${pd.id}"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${pdk.uiType eq 'text-area' }">
                            <textarea name="pdvalues[${idx.index }].value" class="form-control">${pdValue}</textarea>
                        </c:if>
                        <c:if test="${pdk.uiType eq 'text' }">
                            <input type="text" name="pdvalues[${idx.index }].value" class="form-control" value="${pdValue}"/>
                        </c:if>
                    </div>
                    <input type="hidden" name="pdvalues[${idx.index }].key" value="${pdk.id}"/>
                    <c:if test="${not empty pdId}">
                        <input type="hidden" name="pdvalues[${idx.index }].id" value="${pdId}"/>
                    </c:if>
                </div>
            </c:forEach>
        </fieldset>
    </c:if>

    <c:if test="${not empty pokList}">
        <fieldset>
            <legend><spring:message code="label.hair.salon.options"/></legend>
            <table class="table table-striped">
                <thead>
                <tr>
                    <c:forEach items="${pokList}" var="pok" varStatus="idx">
                        <th width="20%">${pok.name}</th>
                    </c:forEach>
                    <th width="7%"></th>
                </tr>
                </thead>
                <tbody>
                <tr id="productOptionTemplate">
                    <c:forEach items="${pokList}" var="pok" varStatus="idx">
                        <td>
                            <c:if test="${pok.uiType eq 'text' }">
                                <input type="text" class="form-control pokInput" data-pok-id="${pok.id}"/>
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
            <legend><spring:message code="label.hair.salon.options.added"/></legend>
            <table class="table table-striped">
                <thead>
                <tr>
                    <c:forEach items="${pokList}" var="pok" varStatus="idx">
                        <th width="20%">${pok.name}</th>
                    </c:forEach>
                    <th width="7%"></th>
                </tr>
                </thead>
                <tbody id="productOptionAddTable">
                <c:forEach items="${product.productOptions}" var="poItem">
                    <tr data-po-id="${poItem.id}">
                        <c:forEach items="${pokList}" var="pok" varStatus="idx">
                            <td>
                                <c:set var="poaValue" value=""/>
                                <c:set var="poaId" value=""/>
                                <c:forEach items="${poItem.productOptionAttributes}" var="poa">
                                    <c:if test="${pok.id eq poa.productOptionKey.id}">
                                        <c:set var="poaValue" value="${poa.value}"/>
                                        <c:set var="poaId" value="${poa.id}"/>
                                    </c:if>
                                </c:forEach>
                                <input type="text" class="form-control pokInput" data-poa-id="${poaId}" data-pok-id="${pok.id}" value="${poaValue}"/>
                            </td>
                        </c:forEach>
                        <td>
                           <%-- <button class="btn btn-primary removePOK">
                                <i class="glyphicon glyphicon-minus"></i>
                            </button>--%>
                        </td>
                    </tr>
                </c:forEach>
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
                //$this.siblings('.input-value').html($this.val());
                hasInput = $this.val() || hasInput;
            });

            if (hasInput) {
                // 清除之前的输入
                $('#productOptionTemplate', Dialog.getContext()).find('.pokInput').val('');
                $html.find('.addPOK').addClass('hide');
                $html.find('.removePOK').removeClass('hide');
                $html.appendTo($('#productOptionAddTable', Dialog.getContext()));
            } else {
                Dialog.alert('<spring:message code="label.hair.long.message"/> ');
            }
        });

        $('#productOptionAddTable', Dialog.getContext()).on('click', 'button.removePOK', function () {
            $(this).parents('tr').remove();
        });
    });
</script>
