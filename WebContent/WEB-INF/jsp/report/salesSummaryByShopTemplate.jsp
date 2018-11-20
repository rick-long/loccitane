<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<style type="text/css">
table{border:1px solid #e6e6e6;font-size:11px;}

table th{line-height: 1.42857143;
    vertical-align: middle;}

.total-bar {
    border: 1px solid #666;
    border-left: 0;
    border-right: 0;
    background: #e6e6e6;
    font-weight: 700;
    margin-bottom: 20px;
	font-size:14px;
}

.left-table {width:80%;float: left;}
.sales_by_date-right {width: 20%;float: left;}	

table.table.table-hover.commission_analysis{
    min-width: 43%;
    float: left;
    margin: 8px 0 0 5px;
    min-height: 100%;
    width: 300px;
}

.table.table-hover.commission_analysis b, .table.table-hover.sales_analysis b{
    font-size: 14px;
    float: left;
}

.table.table-hover.commission_analysis th{background:#666;color:#fff;}

table.table.table-hover.commission_analysis tr{width:100%;}

table.table.table-hover.sales_analysis{
    min-width: 43%;
    float: left;
    margin: 8px 0 0 5px;
    min-height: 100%;
    width: 300px;
}

table.table.table-hover.sales_analysis tr{
    width:100%;
}

.table.table-hover.sales_analysis th, .table.table-hover.commission_analysis th, .table.sales_by_date th{background: #f0f0f0;color:#666;border-left: 1px solid #e6e6e6;border-right: 1px solid #e6e6e6;}


.table.table-hover.sales_analysis td, .table.table-hover.commission_analysis td, .table.sales_by_date td{color:#666;border-left: 1px solid #e6e6e6;border-right: 1px solid #e6e6e6;border-bottom: 1px solid #e6e6e6;}

.table.table-hover.sales_analysis > thead:first-child > tr:first-child > td{ background: #666;color: #fff;padding: 5px 2px;}


.table.sales_by_date {width:99%;float: left;}

.table.sales_by_date > thead:first-child > tr:first-child > td{background: #666;color: #fff;padding: 5px 2px;}
	
.table.table-hover.commission_analysis > thead:first-child > tr:first-child > td{background: #666;color: #fff;padding: 5px 2px;}
	
.table.table-hover.Value-Summary, .table.table-striped.table-hover.Breakdown{
	width:99%;
    float: left;
    margin: 10px auto;
    border:1px solid #e6e6e6;
	}
	
.table.table-hover.Value-Summary > thead:first-child > tr:first-child > td{background: #666;color: #fff;padding: 5px 2px;}	
	
.table.table-striped.table-hover.Breakdown > thead:first-child > tr:first-child > td{background: #666;color: #fff;}		

.table.table-striped.table-hover.Breakdown th, .table.table-hover.Value-Summary th{background: #e6e6e6;
    color: #666;
    border-left: 1px solid #e6e6e6;
    border-right: 1px solid #e6e6e6;}

tr.tbale_head {background: #666;color: #fff;padding:5px 2px;}
tr.tbale_head a:hover{background: #666;}
tr.tbale_head b{color: #fff;padding:5px 0;}
</style>

<div class="show-infomation">
	<p><spring:message code="label.sales.summary.by.shop.of.sense.touch"/> </p>
	<p><spring:message code="label.reportsummaryforperiod"/> ${fromDate} to ${toDate}</p>
</div>

<div class="left-table col-lg-9">
<div class="col-lg-6 col-sm-6">

<table border="0" cellspacing="0" class="table sales_by_date">
	<thead>
		<tr>
			<td align="center"><spring:message code="label.shop"/> </td>
			<td align="center"><spring:message code="label.no.clients"/> </td>
			<td align="center"><spring:message code="label.treatment"/> </td>
			<td align="center"><spring:message code="label.products"/> </td>
			<td align="center"><spring:message code="label.CAHAIRSALON"/> </td>
			<td align="center"><spring:message code="label.packages"/> </td>
			<td align="center"><spring:message code="label.vouchers"/> </td>
			<td align="center"><spring:message code="label.total"/> </td>
			
		</tr>
	</thead>
	<tbody>
	
	<c:set var="totalAmtT" value="0"/>
	<c:set var="totalAmtPro" value="0"/>
	<c:set var="totalAmtPg" value="0"/>
	<c:set var="totalAmtV" value="0"/>
	<c:set var="totalAmtHs" value="0"/>
	<c:set var="totalAmt" value="0"/>
	<c:set var="totalClients" value="0"/>
	<c:if test="${results !=null }">
		<c:forEach items="${results}" var="map">
		<c:set var="vo" value="${map.value}"/>
		<tr>
			<td align="center">${map.key}</td>
			<td align="center">
				<fmt:formatNumber type="number" pattern="0" value="${vo.numOfClients}"/>
					<c:set var="totalClients" value="${totalClients + vo.numOfClients }"/>
			</td>
			<td align="center">
				<fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.treatmentRevenue}"/>
				<c:set var="totalAmtT" value="${totalAmtT + vo.treatmentRevenue}"/>
			</td>
			<td align="center">
					<fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.productRevenue}"/>
					<c:set var="totalAmtPro" value="${totalAmtPro + vo.productRevenue}"/>
			</td>
			<td align="center">
					<fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.hairsalonRevenue}"/>
					<c:set var="totalAmtHs" value="${totalAmtHs + vo.hairsalonRevenue}"/>
			</td>
			<td align="center">
					<fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.packagesRevenue}"/>
					<c:set var="totalAmtPg" value="${totalAmtPg + vo.packagesRevenue }"/>
			</td>
			<td align="center">
					<fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.vouchersRevenue}"/>
					<c:set var="totalAmtV" value="${totalAmtV + vo.vouchersRevenue}"/>
			</td>
			<td align="center">
				<fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.treatmentRevenue +vo.productRevenue+vo.packagesRevenue +vo.vouchersRevenue +vo.hairsalonRevenue}"/>
			</td>
			
		</tr>
		</c:forEach>
	</c:if>
	
	<tr>
		<td align="center"><b><spring:message code="label.total"/></b></td>
		<td align="center"><b><fmt:formatNumber type="number" pattern="0" value="${totalClients}"/></b></td>
		<td align="center"><b><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalAmtT}"/></b></td>
		<td align="center"><b><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalAmtPro}"/></b></td>
		<td align="center"><b><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalAmtHs}"/></b></td>
		<td align="center"><b><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalAmtPg}"/></b></td>
		<td align="center"><b><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalAmtV}"/></b></td>
		<td align="center"><b><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalAmtT +totalAmtPro+totalAmtPg +totalAmtHs +totalAmtV}"/></b></td>
	</tr>
	<tr> <td colspan="8" align="center"><font color="red"> <spring:message code="label.report.message"/> </font></td></tr>
	</tbody>
	
</table>
</div>
</div>