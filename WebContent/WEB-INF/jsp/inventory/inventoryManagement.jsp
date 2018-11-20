<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3>
        <spring:message code="label.inventory.search.management"/>
    </h3>
    <c:url var="url" value="/inventory/list"/>
    <form:form id="commonSearchForm" modelAttribute="inventoryListVO" method="post" class="form-inline" action="${url}">
    <div class="row">
        <div class="col-sm-12">
            <div class="col-lg-3 col-sm-6">
                <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
                    <select id="shopId" name="shopId" cssClass="selectpicker form-control">
                        <option value=""><spring:message code="label.option.select.all"/></option>
                        <c:forEach items="${shopList}" var="item">
                            <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="form-group col-md-12">
                    <div class="categoryTreeMenu">
                        <label class="control-label"><spring:message code="label.category"/> </label>
                        <div class="select-category" data-selectable="category" data-root-id="3"></div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.brand"/></label>
                    <form:select class="selectpicker form-control" path="brandId" id="brandId">
                        <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                        <c:forEach var="brand" items="${brandList }">
                            <form:option value="${brand.id }">${brand.name }</form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.product"/></label>
                    <form:input id="productName" path="productName" cssClass="form-control"/>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.qty"/></label>
                    <form:select id="qty" path="qty" cssClass="selectpicker form-control">
                        <form:option value="-1">
                            <spring:message code="label.option.select.all"/>
                        </form:option>
                        <form:option value="0">
                            <spring:message code="label.inventory.qty.gt0"/>
                        </form:option>
                    </form:select>
                </div>
            </div>
                <%-- <td>
                     <label class="control-label"><spring:message code="label.isactive"/></label>
                     <div class="col-lg-5">
                         <form:select class="selectpicker form-control" path="isActive" id="isActive">
                             <form:option value=""><spring:message code="label.option.select.single"/></form:option>
                             <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                             <form:option value="false"><spring:message code="label.option.no"/></form:option>
                         </form:select>
                     </div>
                 </td> --%>

            <div class="col-lg-3 col-sm-6">
                <div class="form-group col-md-12"><form:hidden id="isActive" path="isActive" value="true"/>
                </div>
            </div>


        </div>
        <div class="col-sm-12">
            <div class="col-lg-3 col-sm-6">
                <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                    <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/>
                </a>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="form-group col-md-12">
                    <a data-permission="inventory:listDataExport" href="javascript:;" onclick='exportSearchForm(this,"<c:url value="/inventory/listDataExport?"/>");' class="btn btn-default exportBtn" ><i class="glyphicon glyphicon-search"></i>Export
                    </a>
                </div>
            </div>
        </div>

        </form:form>


        <%-- <div class="form-group">
             <label class="col-lg-4 control-label"><spring:message code="label.product.option"/></label>
             <div class="col-lg-5">
                 <div class="input-group" data-url='<c:url value="/po/quickSearch"/>' data-title='<spring:message code="label.button.search"/>'>
                     <form:hidden id="productOptionId" path="productOptionId" cssClass="form-control"/>
                     <form:input id="productName" path="productName" cssClass="form-control quick-search"/>
                     <span class="input-group-addon cleanBtn">
                         <span class="glyphicon glyphicon-remove"></span>
                     </span>
                 </div>
             </div>
         </div>--%>
        <%--<div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.product.option"/></label>
            <div class="col-lg-5">
                <div class="input-group dialog" data-url='<c:url value="/po/quickSearch"/>' data-title='<spring:message code="label.button.search"/>'>
                    <form:hidden id="productOptionId" path="productOptionId" cssClass="form-control"/>
                    <form:input id="productName" path="productName" cssClass="form-control quick-search" readonly="true"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                    <span class="input-group-addon cleanBtn">
                        <span class="glyphicon glyphicon-remove"></span>
                    </span>
                </div>
            </div>
        </div>--%>
        <%--   </form:form>--%>
        <div id="pageList">
        </div>
    </div>
</div>
<%--
<script type="text/javascript">
    $(function () {
        var parent = getContext();
        var $productName = $('#productName', parent);
        var cache = {}; // 缂撳瓨璁板綍
        $productName.autocomplete({
            source: function (request, response) {
                var term = request.term;
                if (term in cache) {
                    response(cache[term]);
                    return;
                }
                $.getJSON('<c:url value="/po/selectOptionJson"/>', {productName: term}, function (data, status, xhr) {
                    cache[term] = data;
                    response(data);
                });
            },
            select: function (event, ui) {
                var $this = $(this);
                $this.val(ui.item.label);
                $this.siblings('#productOptionId').val(ui.item.value);
                return false; // Prevent the widget from inserting the value.
            },
            focus: function (event, ui) {
                return false; // Prevent the widget from inserting the value.
            }
        });
    });
</script>--%>
<script>
    $(function () {
        //rootCategoryId
        $('#rootCategoryId').change(function () {
            var $val=$(this).val();
            $('.select-category').data('product-option-id',$val);
            $('.select-category').selectCategory({});
        }).trigger('change');

        $('.select-category').selectCategory({});
    });

    //异步加载export
    function exportSearchForm(obj,url) {
        var pDiv = $(obj).parents(".management");
        var form = pDiv.find("#commonSearchForm");
        $('.exportBtn').attr("href",url+ form.serialize());
    }

</script>
