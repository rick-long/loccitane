<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<t:layout title="Online Voucher">
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_index">
			<tr>
				<td colspan="2"><font color="#9D0000"><b><spring:message code="front.label.order.submitted"/> </b></font></td>
			</tr>
</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="0"  class="form_default" >
<tr >
    <td valign="top">
			<br/>
				<spring:message code="front.label.statements10"/> <br/>
				<spring:message code="front.label.statements11"/> <br/>
				<br/><spring:message code="front.label.statements12"/> <br/>
					
			<br/>
			<br/>
			
    </td>
</tr>
</table>
</t:layout>