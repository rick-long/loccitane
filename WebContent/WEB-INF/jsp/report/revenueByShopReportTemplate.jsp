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

<fmt:formatDate value="${item.purchaseOrder.purchaseDate}" pattern="yyyy-MM-dd"/>

<div class="left-table">
<table border="0" cellspacing="0" class="table table-hover commission_analysis">
	<tbody>
		<tr>
			<th><spring:message code="left.navbar.shop"/> </th>
			<th><spring:message code="label.totalrevenue"/> </th>
		</tr>
		<c:forEach items="${revenuesMap}" var="revenue">
		<tr>
			<td>${revenue.key}</td>
			<td>
				<fmt:formatNumber type="number" pattern="#,#00.0#" value="${revenue.value}"/>
			</td>
		</tr>
		</c:forEach>
	</tbody>   
</table>
</div>