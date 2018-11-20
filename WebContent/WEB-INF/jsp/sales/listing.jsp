<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%--<link href='<c:url value="/resources/review/css/normalize.css"/>' rel="stylesheet">--%>
<link href='<c:url value="/resources/review/css/style.css"/>' rel="stylesheet">
<%--<link href='<c:url value="/resources/css/style.css"/>' rel="stylesheet">--%>
<%--<script src="/resources/Chart.js-master/src/chart.js"></script>--%>


 <%--   <div class="management">
        <c:url var="url" value="/?loadingUrl=/review/listing" />
        <form method="post" class="form-horizontal" id="commonSearchForm" method="post" class="form-inline" action="${url}">
            <div class="home_search">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="col-lg-2 col-sm-6">
                            <div class="form-group col-md-12"><label class="control-label">Shop</label>
                                <select id="shopId" name="shopId" class="selectpicker form-control">
                                    <c:forEach items="${shopList}" var="item">
                                        <option value="${item.id}"
                                                <c:if test="${bookVO.shopId eq item.id}">selected</c:if>>${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-2 col-sm-6">
                            <div class="form-group col-md-12"><label class="control-label">Therapist</label>
                                <select id="staffId" name="staffId" class="selectpicker form-control">
                                </select>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6">
                            <div class="form-group col-md-12">
                                <label class="control-label"><spring:message code="label.treatment.tree"/></label>
                                <div class="select-category" data-selectable="option" data-root-id="2" ></div>
                            </div>
                        </div>

                        <div class="col-lg-2 col-sm-6">
                            <div class="form-group col-md-12"><label class="control-label">Date</label>
                                <div class="input-group date form_time">
                                    <input id="fromDate" name="fromDate" class="form-control fromDate"
                                           value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
                                    <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-2 col-sm-6">
                            <div class="form-group col-md-12"><br/><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
                                <i class="glyphicon glyphicon-search"></i>
                                <spring:message code="label.button.search"/>
                            </a></div>
                        </div>
                    </div>
                </div>
            </div>
            <input type="submit" value="Submit" />
        </form>

                    <div class="col-sm-12">
                        <div class="col-lg-8 col-sm-12">
                            <div class="form-group col-md-12">
                                <label class="control-label">Reporting Period：</label>
                                <button type="button" class="btn btn-primary"
                                        data-toggle="button">Past year
                                </button> <button type="button" class="btn btn-primary"
                                                  data-toggle="button"> Past 6months
                            </button> <button type="button" class="btn btn-primary"
                                              data-toggle="button">Past 30 Days
                            </button> <button type="button" class="btn btn-primary"
                                              data-toggle="button"> Past 7 Days
                            </button>
                            </div>
                        </div>


                    </div>
    </div>--%>
        <table class="table table-striped table-hover">
            <tr class="rat-title">
                <td width="10%" class="date"><spring:message code="label.sales.date"/></td>
                <td width="10%" class="custom-name"><spring:message code="label.sales.membername"/> </td>
                <td width="15%" class="serve-rat"><spring:message code="label.sales.customerservicerating"/> </td>
                <td width="15%" class="cle-rat"><spring:message code="label.sales.cleanlinessrating"/> </td>
                <td width="15%" class="serve-rat"><spring:message code="label.therapist"/> </td>
                <td width="5%" class="review-text"><spring:message code="label.sales.nps"/> </td>
                <td width="40%" class="review-text"><spring:message code="label.sales.reviewtext"/> </td>
            </tr>
            <c:forEach items="${listings}" var="listing">
                <tr class="detail-content">
                    <td class="date"><fmt:formatDate value="${listing.date}" pattern="yyyy-MM-dd"/></td>
                    <td class="custom-name">${listing.name}</td>
                    <td class="serve-rat"><span>${listing.customerServiceRating}</span>
                        <p class="star" data-bg="yellow" data-score="${listing.customerServiceRating}"></p></td>
                    <td class="cle-rat"><span>${listing.cleanlinessRating}</span>
                        <p class="star" data-bg="blue" data-score="${listing.cleanlinessRating}"></p></td>
                    <td class="serve-rat"><span>${listing.therapistStar}</span>
                        <p class="star" data-bg="yellow" data-score="${listing.therapistStar}"></p></td>
                    <td class="review-text">${listing.nps}</td>
                    <td class="review-text">
                            ${listing.reviewText}
                    </td>
                </tr>


            </c:forEach>


        </table>
<script>
    //shop
/*    $('#shopId',Dialog.getContext()).change(function () {
        $.post('<c:url value="/staff/therapistSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),showAll:false},function (res) {
            $('#staffId').html(res);
        });
    }).trigger('change');*/

    $(function () {
/*        $('#fromDate').datetimepicker({
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
        $('#toDateSpan').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
        $('.select-category').selectCategory({});*/
        $('.star').each(function () {
            var color = $(this).attr('data-bg');
            $(this).raty({
                width: 90,

                space : false,

                readOnly: true,

                half     : true,

                starHalf : '/resources/review/star-half-'+ color +'.svg',

                starOff  : '/resources/review/star-off.svg',

                starOn   : '/resources/review/star-on-'+ color +'.svg',

                score: function() {
                    return $(this).attr('data-score');
                }
            });
        });

        $('.rat-title').find('a').click(function () {
            $(this).parent().parent().hide().siblings().show();
        });
    })
</script>