<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="prepaid-search">
    <ul id="myTab" class="nav nav-tabs">
        <li><a
                onclick=javascript:window.location.href="/?loadingUrl=/review/toView" data-toggle="tab">Dashboard</a>
        </li>
        <li class="active"><a data-toggle="tab">Listing</a></li>
    </ul>
    <c:url var="url" value="/review/listing"/>
    <form:form modelAttribute="salesSearchVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
        <input type="hidden" name="reviewDate" id="reviewDate" value="">
        <div class="row">
            <div class="col-sm-12">
                <div class="col-lg-2 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message
                            code="left.navbar.shop"/> </label>
                        <select id="shopId" name="shopId" class="selectpicker form-control">
                            <option value=""><spring:message code="label.sales.pleaseselect"/></option>
                            <c:forEach items="${shopList}" var="item">
                                <option value="${item.id}"
                                        <c:if test="${bookVO2.shopId eq item.id}">selected</c:if>>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-lg-2 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message
                            code="label.therapist"/> </label>
                        <select id="staffId" name="staffId" class="selectpicker form-control">
                        </select>
                    </div>
                </div>

                <div class="col-lg-2 col-sm-6">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.treatment.tree"/></label>
                        <div class="select-category" data-selectable="option" data-root-id="2"></div>
                    </div>
                </div>

                <div class="col-lg-2 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message
                            code="label.uiType.date"/> </label>
                        <div class="input-group date form_time">
                            <input id="fromDate" name="fromDate" class="form-control fromDate"
                                   value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
                            <span class="input-group-addon" id="fromDateSpan"><span
                                    class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-lg-2 col-sm-6">
                    <div class="form-group col-md-12"><br/><a href="javascript:;" onclick="commonSearchForm(this);"
                                                              class="btn btn-default search-btn">
                        <i class="glyphicon glyphicon-search"></i>
                        <spring:message code="label.button.search"/>
                    </a></div>
                </div>
            </div>
            <div class="col-sm-12">
                <div class="col-lg-12">
                    <div class="form-group col-md-12">
                        <label class="control-label"><spring:message code="label.sales.reportingperiod"/>:</label><br/>
                        <button type="button" class="btn btn-primary"
                                data-toggle="button"
                                onclick="butSelected(this); setReviewDate('year');commonSearchForm(this);">Past year
                        </button>
                        <button type="button" class="btn btn-primary"
                                data-toggle="button"
                                onclick="butSelected(this); setReviewDate('6months');commonSearchForm(this);"> Past
                            6months
                        </button>
                        <button type="button" class="btn btn-primary"
                                data-toggle="button"
                                onclick="butSelected(this); setReviewDate('30Days');commonSearchForm(this);">Past 30
                            Days
                        </button>
                        <button type="button" class="btn btn-primary active" data-toggle="button"
                                onclick="butSelected(this); setReviewDate('7Days');commonSearchForm(this);"> Past 7 Days
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form:form>

    <div id="pageList">

    </div>
</div>

<script type="text/javascript">
    //shop
    $('#shopId', Dialog.getContext()).change(function () {
        $.post('<c:url value="/staff/therapistSelectList"/>', {
            shopId: $('#shopId', Dialog.getContext()).val(),
            showAll: false
        }, function (res) {
            $('#staffId').html(res);
        });
    }).trigger('change');

    $(function () {
        $('.select-category').selectCategory({});
        $('#fromDate').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('#fromDate').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });

        $('#toDate').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });

        /*   $('#toDateSpan').click(function () {
                var input = $(this).siblings('input').trigger('focus');
            });*!/*/


        /*   $('.rat-title').find('a').click(function () {
               $(this).parent().parent().hide().siblings().show();
           });*/
    })

    function setReviewDate(reviewDate) {
        $("#reviewDate").val(reviewDate);
    }

    function butSelected(current) {
        $("#fromDate").val('');
        $(current).toggleClass("active");
        $(current).siblings().removeClass("active");
    }

    $("button.btn.btn-primary").click(function () {
        $(this).toggleClass("active");
        $(this).siblings().removeClass("active");
    })
</script>

