<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="order-search">
    <h3>
        <spring:message code="label.clock.attendance"/>
    </h3>
    <c:url var="url" value="/staff/staffClockList"/>
    <form:form modelAttribute="staffClockListVo" id="commonSearchForm" method="post" class="form-inline"
               action="${url}">
        <div class="row">
            <div class="col-sm-12">
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.attendance"/></label>
                        <form:input path="barcode" id="barcode" class="form-control"></form:input>
                    </div>
                </div>


                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.from.date"/></label>
                        <div class="input-group date form_time">
                            <input id="fromDate" name="fromDate" class="form-control fromDate"
                                   value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
                            <span class="input-group-addon" id="fromDateSpan"><span
                                    class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.to.date"/></label>
                        <div class="input-group date form_time">
                            <input id="toDate" name="toDate" class="form-control toDate"
                                   value='<fmt:formatDate value="${toDate}" pattern="yyyy-MM-dd"/>' size="16">
                            <span class="input-group-addon" id="toDateSpan"><span
                                    class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.shop"/></label>
                        <select id="shopId" name="shopId" class="selectpicker form-control">
                            <option value=""><spring:message code="label.option.select.single"/></option>
                            <c:forEach items="${shopList}" var="item">
                                <option value="${item.id}"
                                        <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

            </div>


            <div class="col-sm-12">
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.option.clock"/></label>
                        <form:select id="type" path="type" class="selectpicker form-control">
                            <form:option value="-1"><spring:message code="label.option.select.single"/></form:option>
                            <form:option value="0"><spring:message code="label.option.clock.in"/></form:option>
                            <form:option value="1"><spring:message code="label.option.clock.out"/></form:option>
                        </form:select>
                    </div>
                </div>

            </div>

            <div class="col-sm-12">
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <a data-permission="attendance:search" href="javascript:;" onclick="commonSearchForm(this);"
                           class="btn btn-default search-btn">
                            <i class="glyphicon glyphicon-search"></i>
                            <spring:message code="label.button.search"/>
                        </a>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <a data-permission="attendance:export" href="javascript:;"
                           onclick='exportSearchForm(this,"<c:url value="/staff/export?"/>");'
                           class="btn btn-default exportBtn"><i class="glyphicon glyphicon-search"></i>Export
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
    <div id="pageList">
    </div>
</div>

<script type="text/javasc<%----%>ript">
    $('#fromDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('.input-group-addon').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });

    $('#toDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#toDateSpan').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });

    //shop
    $('#shopId', Dialog.getContext()).change(function () {
        $.post('<c:url value="/staff/staffSelectList"/>', {
            shopId: $('#shopId', Dialog.getContext()).val(),
            showAll: false
        }, function (res) {
            $('#staffId').html(res);
        });
    }).trigger('change');


    //异步加载export
    function exportSearchForm(obj, url) {
        var pDiv = $(obj).parents(".management");
        var form = pDiv.find("#commonSearchForm");
        $('.exportBtn').attr("href", url + form.serialize());
    }
</script>