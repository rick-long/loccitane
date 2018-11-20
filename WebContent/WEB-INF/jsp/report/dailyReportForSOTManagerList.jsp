<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>


<table class="table report_inner_table table-striped">
	<tr>
		<th align="center" colspan="3"></td>
		<th align="center" colspan="2"><spring:message code="label.treatment"/> </th>
		<th align="center" colspan="2"><spring:message code="label.packages"/> </th>
		<th align="center" colspan="2"><spring:message code="label.vouchers"/> </th>
	</tr>
	<tr>
		<td align="center"><spring:message code="left.navbar.shop"/> </td>
		<td align="center"><spring:message code="left.navbar.sales"/> </td>
		<td align="center"><spring:message code="label.revenue"/> </td>
		<td align="center"><spring:message code="label.units"/> </td>
		<td align="center"><spring:message code="label.sold"/> </td>
		<td align="center"><spring:message code="label.units"/> </td>
		<td align="center"><spring:message code="label.sold"/></td>
		<td align="center"><spring:message code="label.units"/></td>
		<td align="center"><spring:message code="label.sold"/></td>
	</tr>
	<c:set var="sum_sales" value="0"/>
	<c:set var="sum_rev" value="0"/>
	<c:set var="sum_t_units" value="0"/>
	<c:set var="sum_t_sold" value="0"/>
	<c:set var="sum_p_units" value="0"/>
	<c:set var="sum_p_sold" value="0"/>
	<c:set var="sum_v_units" value="0"/>
	<c:set var="sum_v_sold" value="0"/>
  	<c:forEach items="${list}" var="vo">
  		<tr>
  			<td align="center">${vo.shopName}</td>
  			<td align="center"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.sales}"/><c:set var="sum_sales" value="${sum_sales + vo.sales }"/></td>
  			<td align="center"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.revenue}"/><c:set var="sum_rev" value="${sum_rev + vo.revenue }"/></td>
  			<td align="center">
  				<c:if test="${vo.treatmentSummaryVo !=null }"><fmt:formatNumber type="number" pattern="0" value="${vo.treatmentSummaryVo.unit}"/><c:set var="sum_t_units" value="${sum_t_units + vo.treatmentSummaryVo.unit }"/></c:if>
  				<c:if test="${vo.treatmentSummaryVo ==null }">0</c:if>
			</td>
			<td align="center">
  				<c:if test="${vo.treatmentSummaryVo !=null }"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.treatmentSummaryVo.amount}"/><c:set var="sum_t_sold" value="${sum_t_sold + vo.treatmentSummaryVo.amount }"/></c:if>
  				<c:if test="${vo.treatmentSummaryVo ==null }"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="0"/></c:if>
			</td>
			<td align="center">
  				<c:if test="${vo.packageSummaryVo !=null }"><fmt:formatNumber type="number" pattern="0" value="${vo.packageSummaryVo.unit}"/><c:set var="sum_p_units" value="${sum_p_units + vo.packageSummaryVo.unit }"/></c:if>
  				<c:if test="${vo.packageSummaryVo ==null }">0</c:if>
			</td>
			<td align="center">
  				<c:if test="${vo.packageSummaryVo !=null }"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.packageSummaryVo.amount }"/><c:set var="sum_p_sold" value="${sum_p_sold + vo.packageSummaryVo.amount }"/></c:if>
  				<c:if test="${vo.packageSummaryVo ==null }"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="0"/></c:if>
			</td>
			<td align="center">
  				<c:if test="${vo.voucherSummaryVo !=null }"><fmt:formatNumber type="number" pattern="0" value="${vo.voucherSummaryVo.unit}"/> <c:set var="sum_v_units" value="${sum_v_units + vo.voucherSummaryVo.unit }"/></c:if>
  				<c:if test="${vo.voucherSummaryVo ==null }">0</c:if>
			</td>
			<td align="center">
  				<c:if test="${vo.voucherSummaryVo !=null }"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${vo.voucherSummaryVo.amount}"/><c:set var="sum_v_sold" value="${sum_v_sold + vo.voucherSummaryVo.amount }"/></c:if>
  				<c:if test="${vo.voucherSummaryVo ==null }"><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="0"/></c:if>
			</td>
			
		</tr>
	</c:forEach>
	<tr>
		<td align="center" class="value_default total"><b><spring:message code="label.total"/> </b></td>
		<td align="center" class="value_default total"><b><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sum_sales}"/></b></td>
		<td align="center" class="value_default total"><b<spring:message code="label.currency.default"/>><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sum_rev}"/></b></td>
		<td align="center" class="value_default total"><b><fmt:formatNumber type="number" pattern="0" value="${sum_t_units}"/></b></td>
		<td align="center" class="value_default total"><b><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sum_t_sold}"/></b></td>
		<td align="center" class="value_default total"><b><fmt:formatNumber type="number" pattern="0" value="${sum_p_units}"/></b></td>
		<td align="center" class="value_default total"><b><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sum_p_sold}"/></b></td>
		<td align="center" class="value_default total"><b><fmt:formatNumber type="number" pattern="0" value="${sum_v_units}"/></b></td>
		<td align="center" class="value_default total"><b><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${sum_v_sold}"/></b></td>
	</tr>
</table>
