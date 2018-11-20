<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<t:layout title="Online Voucher">
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_index">
			<tr>
				<td colspan="2"><font color="#9D0000"><b><spring:message code="front.label.order.cancelled"/> </b></font></td>
			</tr>
	</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="0"  class="form_default">
<tr> 
    <td valign="top">
    		<br/>
				<spring:message code="front.label.statements7"/> <br/>
				<spring:message code="front.label.statements8"/> <spring:message code="front.label.thank.you"/>
				<br/><br/>
				<br/>
    </td>
</tr>
</table>
</t:layout>
