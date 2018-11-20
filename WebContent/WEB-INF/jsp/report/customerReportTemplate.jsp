<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<style type="text/css">
    table {
        border: 1px solid #e6e6e6;
        font-size: 11px;
    }

    table th {
        line-height: 1.42857143;
        vertical-align: middle;
    }

    .total-bar {
        border: 1px solid #666;
        border-left: 0;
        border-right: 0;
        background: #e6e6e6;
        font-weight: 700;
        margin-bottom: 20px;
        font-size: 14px;
    }

    .left-table {
        width: 80%;
        float: left;
    }

    .sales_by_date-right {
        width: 20%;
        float: left;
    }

    table.table.table-hover.commission_analysis {
        min-width: 43%;
        float: left;
        margin: 8px 0 0 5px;
        min-height: 100%;
        width: 300px;
    }

    .table.table-hover.commission_analysis b, .table.table-hover.sales_analysis b {
        font-size: 14px;
        float: left;
    }

    .table.table-hover.commission_analysis th {
        background: #666;
        color: #fff;
    }

    table.table.table-hover.commission_analysis tr {
        width: 100%;
    }

    table.table.table-hover.sales_analysis {
        min-width: 43%;
        float: left;
        margin: 8px 0 0 5px;
        min-height: 100%;
        width: 300px;
    }

    table.table.table-hover.sales_analysis tr {
        width: 100%;
    }

    .table.table-hover.sales_analysis th, .table.table-hover.commission_analysis th, .table.sales_by_date th {
        background: #f0f0f0;
        color: #666;
        border-left: 1px solid #e6e6e6;
        border-right: 1px solid #e6e6e6;
    }

    .table.table-hover.sales_analysis td, .table.table-hover.commission_analysis td, .table.sales_by_date td {
        color: #666;
        border-left: 1px solid #e6e6e6;
        border-right: 1px solid #e6e6e6;
        border-bottom: 1px solid #e6e6e6;
    }

    .table.table-hover.sales_analysis > thead:first-child > tr:first-child > td {
        background: #666;
        color: #fff;
        padding: 5px 2px;
    }

    .table.sales_by_date {
        width: 99%;
        float: left;
    }

    .table.sales_by_date > thead:first-child > tr:first-child > td {
        background: #666;
        color: #fff;
        padding: 5px 2px;
    }

    .table.table-hover.commission_analysis > thead:first-child > tr:first-child > td {
        background: #666;
        color: #fff;
        padding: 5px 2px;
    }

    .table.table-hover.Value-Summary, .table.table-striped.table-hover.Breakdown {
        width: 99%;
        float: left;
        margin: 10px auto;
        border: 1px solid #e6e6e6;
    }

    .table.table-hover.Value-Summary > thead:first-child > tr:first-child > td {
        background: #666;
        color: #fff;
        padding: 5px 2px;
    }

    .table.table-striped.table-hover.Breakdown > thead:first-child > tr:first-child > td {
        background: #666;
        color: #fff;
    }

    .table.table-striped.table-hover.Breakdown th, .table.table-hover.Value-Summary th {
        background: #e6e6e6;
        color: #666;
        border-left: 1px solid #e6e6e6;
        border-right: 1px solid #e6e6e6;
    }

    tr.tbale_head {
        background: #666;
        color: #fff;
        padding: 5px 2px;
    }

    tr.tbale_head a:hover {
        background: #666;
    }

    tr.tbale_head b {
        color: #fff;
        padding: 5px 0;
    }
</style>

<fmt:formatDate value="${item.purchaseOrder.purchaseDate}" pattern="yyyy-MM-dd"/>
<div class="show-infomation">
    <p><h3 class="text-h3"><spring:message code="label.customsalesreportofsenseoftouch"/></h3></p>
    <p><spring:message code="label.reportsummaryforperiod"/> ${fromDate} to ${toDate}</p>
    <p><spring:message code="label.sales.location"/> :
        <c:if test="${!empty locations }">
            <c:forEach items="${locations}" var="shop">
                ${shop.name};
            </c:forEach>
        </c:if>
    </p>
</div>
<div class="total-bar">
    <table class="table">
        <c:if test="${!isLanlordReport}">
            <tr>
                <td><spring:message code="label.totalrevenue"/></td>
                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalRevenue}"/></td>
            </tr>
            <tr>
                <td><spring:message code="label.nopayment"/></td>
                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${noPayment}"/></td>
            </tr>
        </c:if>
        <tr>
            <c:if test="${!isLanlordReport}">
                <td><spring:message code="label.grossrevenue"/> (= Total Revenue - No Payment)</td>
            </c:if>
            <c:if test="${isLanlordReport}">
                <td><spring:message code="label.grossrevenue"/></td>
            </c:if>
            <td class="col-lg-8"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${grossRevenue}"/></td>
        </tr>
        <c:if test="${!isLanlordReport}">
            <tr>
                <td><spring:message code="label.total.commission"/></td>
                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#"
                                                       value="${totalCommission}"/></td>
            </tr>

            <tr>
                <td><spring:message code="label.total.extra.commission"/></td>
                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#"
                                                       value="${totalExtraCommission}"/></td>
            </tr>
        </c:if>
    </table>
</div>

<div class="container lo-p-v-none">
<div class="row">
<div class="left-table col-lg-9 lo-p-v-none">
    <div class="col-lg-6 col-sm-12 lo-p-v-none">
       <p> <c:if test="${!isLanlordReport}">
            <c:if test="${!empty commissionAnalysis}">
                <c:forEach items="${commissionAnalysis}" var="commMap">
                    <c:if test="${commMap.key !='CA-HAIRSALON' }">
                    <h3 class="text-h3 lo-m-t-15"><c:set var="categoryType">
                                    ${fn:replace(commMap.key, "-", "")}
                                </c:set>
                                    <spring:message code="label.${categoryType}"/> <spring:message
                                            code="label.commissionanalysis"/></h3>
                      <table border="0" cellspacing="0" class="table table-hover table-striped commission_analysis">
                            <tbody>
                            <tr>
                                <th><spring:message code="label.therapist"/></th>
                                <th><spring:message code="label.member.total.sales"/></th>
                                <th><spring:message code="label.ofturnover"/></th>
                                <th><spring:message code="label.commissionpayable"/></th>
                                <th><spring:message code="label.bonuscommission"/></th>
                            </tr>
                            <c:set var="totalAmtOfCommAnalysis" value="0"/>
                            <c:set var="totalCommOfCommAnalysis" value="0"/>
                            <c:set var="totalExtraCommOfCommAnalysis" value="0"/>
                            <c:if test="${!empty commMap.value}">
                                <c:forEach items="${commMap.value}" var="vo">
                                    <tr>
                                        <td>${vo.staff}</td>
                                        <td>
                                            <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.sumAmt}"/>
                                            <c:set var="totalAmtOfCommAnalysis"
                                                   value="${totalAmtOfCommAnalysis + vo.sumAmt}"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber type="number" pattern="#,#00.0#"
                                                              value="${vo.percentageOfSales * 100}"/>%
                                        </td>
                                        <c:choose>
                                            <c:when test="${vo.sumBonusCommi != 0}">
                                                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.sumBonusCommi }"/></td>
                                                <c:set var="totalCommOfCommAnalysis"
                                                       value="${totalCommOfCommAnalysis + vo.sumBonusCommi }"/>
                                            </c:when>
                                            <c:otherwise>
                                                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.sumCommiVal }"/></td>
                                                <c:set var="totalCommOfCommAnalysis"
                                                       value="${totalCommOfCommAnalysis + vo.sumCommiVal }"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${vo.sumTargetExtraCommission != 0}">
                                                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.sumTargetExtraCommission }"/></td>
                                                <c:set var="totalExtraCommOfCommAnalysis"
                                                       value="${totalExtraCommOfCommAnalysis + vo.sumTargetExtraCommission }"/>
                                            </c:when>
                                            <c:otherwise>
                                                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.sumExtraCommission }"/></td>
                                                <c:set var="totalExtraCommOfCommAnalysis"
                                                       value="${totalExtraCommOfCommAnalysis + vo.sumExtraCommission }"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <tr>
                                <td><b><spring:message code="label.total"/> </b></td>
                                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalAmtOfCommAnalysis}"/></td>
                                <td>100%</td>
                                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalCommOfCommAnalysis }"/></td>
                                <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalExtraCommOfCommAnalysis }"/></td>
                            </tr>
                            </tbody>
                        </table>
                        
                    </c:if>
                </c:forEach>
            </c:if>
        </c:if>
        </p>
    </div>
    <div class="col-lg-6 col-sm-12 lo-p-v-none">
       <p> <c:if test="${!empty salesAnalysis}">
            <c:forEach items="${salesAnalysis}" var="salesMap">
                <c:if test="${salesMap.key !='CA-HAIRSALON' }">
                <h3 class="text-h3 lo-m-t-15"><c:set var="categoryType">
                                    ${fn:replace(salesMap.key, "-", "")}
                                </c:set>
                                <spring:message code="label.${categoryType}"/> <spring:message
                                        code="label.salesanalysis"/> </h3>
                 <table border="0" cellspacing="0" class="table table-hover table-striped sales_analysis">
                        <tbody>
                        <tr>
                            <th><spring:message code="label.category"/></th>
                            <th><spring:message code="label.totalunits"/></th>
                            <th><spring:message code="label.oftotalbyunitssold"/></th>
                            <th><spring:message code="label.member.total.sales"/></th>
                            <th><spring:message code="label.oftotalbysales"/></th>
                        </tr>
                        <c:set var="totalAmtOfSalesAnalysis" value="0"/>
                        <c:set var="totalQtyOfSalesAnalysis" value="0"/>
                        <c:if test="${!empty salesMap.value}">
                            <c:forEach items="${salesMap.value}" var="vo">
                                <tr>
                                    <td>${vo.categoryName}</td>
                                    <td><fmt:formatNumber type="number" value="${vo.sumQty}"/>
                                        <c:set var="totalQtyOfSalesAnalysis"
                                               value="${totalQtyOfSalesAnalysis + vo.sumQty }"/>
                                    </td>
                                    <td><fmt:formatNumber type="number" pattern="#,#00.0#"
                                                          value="${vo.percentageOfUnits * 100}"/>%
                                    </td>
                                    <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.sumAmt}"/>
                                        <c:set var="totalAmtOfSalesAnalysis"
                                               value="${totalAmtOfSalesAnalysis + vo.sumAmt }"/>
                                    </td>
                                    <td><fmt:formatNumber type="number" pattern="#,#00.0#"
                                                          value="${vo.percentageOfSales * 100}"/>%
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <tr>
                            <td><b><spring:message code="label.total"/> </b></td>
                            <td><fmt:formatNumber type="number" value="${totalQtyOfSalesAnalysis}"/></td>
                            <td>100%</td>
                            <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#"
                                                  value="${totalAmtOfSalesAnalysis }"/></td>
                            <td>100%</td>
                        </tr>
                        </tbody>
                    </table>
                  
                </c:if>
            </c:forEach>
        </c:if>
        </p>
    </div>
</div>

<div class="sales_by_date-right col-lg-3 col-xs-12 lo-p-v-none">
   <h3 class="text-h3 lo-m-t-15"><spring:message code="label.sales.by.date"/></h3>
    <table border="0" cellspacing="0" class="table table-hover table-striped sales_by_date">
        <thead>
        <tr>
            <th><spring:message code="label.sales.date"/></th>
            <th><spring:message code="label.sales.total"/></th>
        </tr>
        </thead>
        <tbody>
        <c:set var="totalAmt" value="0"/>
        <c:if test="${totalSalesByDate !=null }">
        <c:forEach items="${totalSalesByDate}" var="item">
        <tr>
            <td><fmt:formatDate value="${item.date}" pattern="dd/MM/yyyy"/></td>
            <td>
                <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.sumAmt}"/>
                <c:set var="totalAmt" value="${totalAmt + item.sumAmt }"/>
            </td>
           </tr> 
            </c:forEach>
            </c:if> 
        <tr>
            <td><b><spring:message code="label.total"/> </b></td>
            <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalAmt}"/></td>
        </tr>
        </tbody>

    </table>
</div>
</div>
</div>