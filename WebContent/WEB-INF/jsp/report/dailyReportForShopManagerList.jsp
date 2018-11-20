<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<div class="table-responsive">
<table class="table report_inner_table">
	<tr>
		<th><spring:message code="label.daily.total.sales"/> </th>
	</tr>
	<tr valign="top">
		<td>
        <div class="Daily_Total_Sales_div"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table report_inner_table table-striped">
  <tr>
    <td> <h5 class="title_for_report_new"><spring:message code="label.report.income.sub.total"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sumIncome + others}"/></h5></td>
</tr>
  <c:set var="sum_income" value="0"/>
	<c:set var="sum_prepaid" value="0"/>
	
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.report.cash"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${cash}"/>
		</td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.report.eps"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${eps}"/>
		</td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.report.VISA"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${visa}"/>
		</td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.report.AE"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${ae}"/>
		</td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.report.unionpay"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${uniompay}"/>
		</td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.CAOTHERS"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${others}"/>
		</td>
	</tr>
</table>
</div>
<div class="Daily_Total_Sales_div"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table report_inner_table table-striped" align="right">
  <tr>
		<td><h5 class="title_for_report_new"><spring:message code="label.report.sot.prepaid.sub.total"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sumPrepaid}"/></h5></td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.package"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${packages}"/>
		</td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.voucher"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${voucher}"/>
		</td>
	</tr>
</table>
</div>
<div class="Daily_Total_Sales_div"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table report_inner_table table-striped">
  <tr>
		<td><h5 class="title_for_report_new"><spring:message code="label.report.other.prepaid.sub.total"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sumOtherPrepaid}"/></h5></td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.report.hotel.guest"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${hotels}"/>
		</td>
	</tr>
	<tr>
		<td>
			-&nbsp;&nbsp;<spring:message code="label.report.wings.guest"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${wings}"/>
		</td>
	</tr>
</table>
</div>
<div class="Daily_Total_Sales_div"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table report_inner_table table-striped">
  <tr>
		<td><h5 class="title_for_report_new"><spring:message code="label.report.total.revenue"/> :&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalRevenue}"/></h5></td>
	</tr>
</table>
</div>
       </td>
	</tr>
</table>
</div>
<br/>
<div class="table-responsive">
<table class="table report_inner_table table-striped">
	<tr>
		<th><spring:message code="label.daily.breakdown"/> </th>
	</tr>
	<tr>
		<td><spring:message code="label.number.of.clients"/> :&nbsp;${numOfClients}</td>
	</tr>
	
	<c:set var="ttotalUnit" value="0"/>
	<c:set var="ptotalUnit" value="0"/>
	<c:set var="packagetotalUnit" value="0"/>
	<c:set var="vouchertotalUnit" value="0"/>
	<c:set var="tsumAmount" value="0"/>
	<c:set var="psumAmount" value="0"/>
	<c:set var="packagesumAmount" value="0"/>
	<c:set var="vouchersumAmount" value="0"/>
  	<c:forEach items="${summaryList}" var="summaryObject">
  		<c:if test="${summaryObject.prodType eq 'CA-TREATMENT' }">
			<c:set var="ttotalUnit" value="${ttotalUnit + summaryObject.unit }"/>
			<c:set var="tsumAmount" value="${tsumAmount + summaryObject.amount }"/>
		</c:if>
		<c:if test="${summaryObject.prodType eq 'CA-GOODS' }">
			<c:set var="ptotalUnit" value="${ptotalUnit + summaryObject.unit }"/>
			<c:set var="psumAmount" value="${psumAmount + summaryObject.amount }"/>
		</c:if>
		<c:if test="${summaryObject.prodType eq 'PREPAID' && (summaryObject.categoryName eq 'Cash Package' || summaryObject.categoryName eq 'Treatment Package')}">
			<c:set var="packagetotalUnit" value="${packagetotalUnit + summaryObject.unit }"/>
			<c:set var="packagesumAmount" value="${packagesumAmount + summaryObject.amount }"/>
		</c:if>
		<c:if test="${summaryObject.prodType eq 'PREPAID' &&  (summaryObject.categoryName eq 'Cash Voucher' || ummaryObject.categoryName eq 'Treatment Voucher')}">
			<c:set var="vouchertotalUnit" value="${vouchertotalUnit + summaryObject.unit }"/>
			<c:set var="vouchersumAmount" value="${vouchersumAmount + summaryObject.amount }"/>
		</c:if>
	</c:forEach>
	<tr>
		<td><spring:message code="label.number.of.treatments.sold"/> :– <spring:message code="label.unit"/> :&nbsp;<fmt:formatNumber type="number" pattern="0" value="${ttotalUnit}"/>;&nbsp;Total:&nbsp;<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${tsumAmount}"/></td>
	</tr>
	<c:forEach items="${summaryList}" var="summaryObject">
	  		<c:if test="${summaryObject.prodType eq 'CA-TREATMENT' }">
				<tr>
					<td style="word-wrap:break-word;word-break:break-all;">
						-&nbsp;&nbsp;${summaryObject.categoryName }&nbsp;
                        <br/>-&nbsp;&nbsp;unit:<fmt:formatNumber type="number" pattern="0" value="${summaryObject.unit}"/>;&nbsp;total:<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${summaryObject.amount}"/>
					</td>
				</tr>
			</c:if>
	</c:forEach>
	<tr>
		<td><spring:message code="label.number.of.product.sold"/> :– <spring:message code="label.unit"/>:&nbsp;<fmt:formatNumber type="number" pattern="0" value="${ptotalUnit}"/>;&nbsp;Total:&nbsp;<fmt:formatNumber type="number" pattern="#,#00.0#" value="${psumAmount}"/></td>
	</tr>
	<tr>
		<td><spring:message code="label.number.of.package.sold"/> :– <spring:message code="label.unit"/>:&nbsp;<fmt:formatNumber type="number" pattern="0" value="${packagetotalUnit}"/>;&nbsp;Total:&nbsp;<fmt:formatNumber type="number" pattern="#,#00.0#" value="${packagesumAmount}"/></td>
	</tr>
	<tr>
		<td><spring:message code="label.number.of.voucher.sold"/> :– <spring:message code="label.unit"/>:&nbsp;<fmt:formatNumber type="number" pattern="0" value="${vouchertotalUnit}"/>;&nbsp;Total:&nbsp;<fmt:formatNumber type="number" pattern="#,#00.0#" value="${vouchersumAmount}"/></td>
	</tr>
</table>
</div>
<br/>
<div class="table-responsive">
<table class="table report_inner_table table-striped">
	<tr>
		<th><spring:message code="label.therapist.breakdown"/> </th>
	</tr>
	<tr><td><c:forEach items="${staffSummary}" var="map">
    <div class="Daily_Total_Sales_div"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table report_inner_table table-striped">
    <tr>
			<td>
				<h5 class="title_for_report_new">-&nbsp;&nbsp;${map.key}</h5>
			</td>
		</tr>
		<c:set var="ttotalUnit_staff" value="0"/>
		<c:set var="ptotalUnit_staff" value="0"/>
		<c:set var="packagetotalUnit_staff" value="0"/>
		<c:set var="vouchertotalUnit_staff" value="0"/>
		<c:set var="tsumAmount_staff" value="0"/>
		<c:set var="psumAmount_staff" value="0"/>
		<c:set var="packagesumAmount_staff" value="0"/>
		<c:set var="vouchersumAmount_staff" value="0"/>
		<c:forEach items="${map.value}" var="vo">
	        <c:if test="${vo.prodType eq 'CA-TREATMENT' }">
				<c:set var="ttotalUnit_staff" value="${ttotalUnit_staff + vo.unit }"/>
				<c:set var="tsumAmount_staff" value="${tsumAmount_staff + vo.amount }"/>
			</c:if>
			<c:if test="${vo.prodType eq 'CA-GOODS' }">
				<c:set var="ptotalUnit_staff" value="${ptotalUnit_staff + vo.unit }"/>
				<c:set var="psumAmount_staff" value="${psumAmount_staff + vo.amount }"/>
			</c:if>
			<c:if test="${vo.prodType eq 'PREPAID' && vo.categoryName eq 'Package'}">
				<c:set var="packagetotalUnit_staff" value="${packagetotalUnit_staff + vo.unit }"/>
				<c:set var="packagesumAmount_staff" value="${packagesumAmount_staff + vo.amount }"/>
			</c:if>
			<c:if test="${vo.prodType eq 'PREPAID' && vo.categoryName eq 'Voucher'}">
				<c:set var="vouchertotalUnit_staff" value="${vouchertotalUnit_staff + vo.unit }"/>
				<c:set var="vouchersumAmount_staff" value="${vouchersumAmount_staff + vo.amount }"/>
			</c:if>
		</c:forEach>
		<tr>
			<td><spring:message code="left.navbar.treatment"/> :&nbsp;<spring:message code="label.number.of.treatment.sold"/> :<fmt:formatNumber type="number" pattern="0" value="${ttotalUnit_staff}"/>;&nbsp;total:<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${tsumAmount_staff}"/></td>
		</tr>
		<tr>
			<td><spring:message code="label.product"/> :&nbsp;<spring:message code="label.number.of.product.sold"/> :<fmt:formatNumber type="number" pattern="0" value="${ptotalUnit_staff}"/>;&nbsp;total:$<fmt:formatNumber type="number" pattern="#,#00.0#" value="${psumAmount_staff}"/></td>
		</tr>
		<tr>
			<td><spring:message code="label.package"/> :&nbsp;<spring:message code="label.number.of.package.sold"/> :<fmt:formatNumber type="number" pattern="0" value="${packagetotalUnit_staff}"/>;&nbsp;total:$<fmt:formatNumber type="number" pattern="#,#00.0#" value="${packagesumAmount_staff}"/></td>
		</tr>
		<tr>
			<td><spring:message code="label.voucher"/> :&nbsp;<spring:message code="label.number.of.voucher.sold"/> :<fmt:formatNumber type="number" pattern="0" value="${vouchertotalUnit_staff}"/>;&nbsp;:$<fmt:formatNumber type="number" pattern="#,#00.0#" value="${vouchersumAmount_staff}"/></td>
		</tr>
    </table>
    </div>
	</c:forEach>
    </td>
    </tr>
</table>
</div>
