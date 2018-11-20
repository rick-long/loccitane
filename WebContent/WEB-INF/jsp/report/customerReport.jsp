<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style>
    .categoryTreeMenu {
        display: block;
    }

    .categoryTreeMenu.inactive {
        display: none;
    }
</style>
<div class="management container"> 
  <div class="row">
     <h1 class="text-h1-white"><spring:message code="left.navbar.report.customer"/></h1>
  </div>
</div>

<div class="management container" id="sales-details">
    <c:url var="url" value="/report/customerReportTemplate?search=true"/>
    <form:form modelAttribute="salesSearchVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
        <div class="row">
            <!--line 1-->
            <div class="col-sm-12">
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message
                            code="label.client"/></label>
                        <div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>'
                             data-title='<spring:message code="label.client.quick.search"/>'>
                            <form:hidden path="memberId" id="memberId" class="form-control"/>
                            <form:hidden path="username" id="username" class="form-control"/>
                            <form:input path="fullName" id="fullName" class="form-control quick-search"/>
                            <span class="input-group-addon">
                         <span class="glyphicon glyphicon-search"></span>
                     </span>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message
                            code="label.from.date"/></label>
                        <div class="input-group date form_time">
                            <input id="fromDate" name="fromDate" class="form-control fromDate"
                                   value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
                            <span class="input-group-addon" id="fromDateSpan"><span
                                    class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message
                            code="label.to.date"/></label>
                        <div class="input-group date form_time">
                            <input id="toDate" name="toDate" class="form-control toDate"
                                   value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
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
            <!--line 2-->
            <div class="col-sm-12">

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="left.navbar.staff"/> </label>
                        <form:select id="staffId" path="staffId" class="selectpicker form-control">
                            <form:option value=""><spring:message code="label.option.select.single"/></form:option>
                        </form:select>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.payment.method"/> </label>
                        <form:select id="paymentMethodId" path="paymentMethodId" class="selectpicker form-control">
                            <form:option value=""><spring:message code="label.option.select.single"/></form:option>
                            <c:forEach items="${paymentMethodList}" var="item">
                                <form:option value="${item.id}">${item.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.member.product.type"/> </label>
                        <form:select class="selectpicker form-control" path="rootCategoryId" id="rootCategoryId">
                            <form:option value="1">All</form:option>
                            <c:forEach var="subCategory" items="${subCategoryList }">
                                <form:option value="${subCategory.id }">${subCategory.name }</form:option>
                            </c:forEach>
                            <form:option value="1000000"><spring:message code="label.PREPAID"/></form:option>
                        </form:select>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <div class="categoryTreeMenu inactive">
                            <label class="control-label"><spring:message code="label.category"/> </label>
                            <div class="select-category" data-selectable="category,option" data-root-id="2"></div>
                        </div>
                    </div>
                </div>

            </div>
            <!--line 3-->
                <%-- <div class="col-sm-12">
              <div class="col-lg-3 col-sm-6">
                       <div class="form-group col-md-12">
                           <label class="control-label"><spring:message code="label.islanlordreport"/> </label>
                          <form:select id="isLanlordReport" path="isLanlordReport" class="selectpicker form-control">
                              <form:option value="false"><spring:message code="label.false"/></form:option>
                              <form:option value="true"><spring:message code="label.true"/></form:option>
                          </form:select>
                             </div>
                            </div>

                 </div>--%>

            <!--line 4-->
            <div class="col-sm-12">

                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default">
                            <i class="glyphicon glyphicon-search"></i>
                            <spring:message code="label.button.search"/>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12">
                        <a href="javascript:;"
                           onclick='generatePDFSearchForm(this,"<c:url value="/report/printCustomer?"/>");'
                           class="btn btn-default exportBtn">
                            <i class="glyphicon glyphicon-download-alt"></i> <spring:message code="label.button.generate.pdf"/>
                        </a>
                    </div>
                </div>

            </div>
            <!-- line 5 -->
        </div>
    </form:form>
    <div id="pageList">

    </div>
</div>
<script type="text/javascript">
    $(function () {
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
        //rootCategoryId
        $('#rootCategoryId').change(function () {
            var rcVal = $(this).val();
            if (rcVal == '1') {
                if ($('.categoryTreeMenu').hasClass('inactive')) {
                } else {
                    $('.categoryTreeMenu').addClass('inactive');
                }
                ;
                $('#productId').val('');
                $('#productOptionId').val('');
                $('#categoryId').val('');
            } else {
                if ($('.categoryTreeMenu').hasClass('inactive')) {
                    $('.categoryTreeMenu').removeClass('inactive');
                }
                ;
            }
            $('.select-category').data('root-id', rcVal);
            $('.select-category').selectCategory({});
        }).trigger('change');

        $('.select-category').selectCategory({});
    });

    $('#shopId').change(function () {
        $.post('<c:url value="/staff/staffSelectList"/>', {
            shopId: $('#shopId', Dialog.getContext()).val(),
            showAll: false
        }, function (res) {
            $('#staffId').html(res);
        });
    }).trigger('change');

    //异步加载download
    function generatePDFSearchForm(obj, url) {
        var pDiv = $(obj).parents(".management");
        var form = pDiv.find("#commonSearchForm");
        $('.exportBtn').attr("href", url + form.serialize());
    }
</script>