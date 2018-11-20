<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<t:layout title="Online Voucher">
<h2>Online Voucher</h2>
<div class="voucher-content">
<p><font color="#9D0000"><b><spring:message code="front.label.statements14"/> </b></font></p>
<p><spring:message code="front.label.statements15"/> :</p>
                
<p><spring:message code="front.label.statements16"/> <br/>
<spring:message code="front.label.statements17"/> <br/>
<spring:message code="front.label.statements18"/> <br/>
<spring:message code="front.label.statements19"/> <br/><br/></p>

<p><font color="#9D0000"><spring:message code="front.label.statements20"/> </font><br/></p>

<p><spring:message code="front.label.statements21"/> :<br/></p>

<p><a href='<c:url value="/front/prepaid/voucherToAdd?prepaidType=CASH_VOUCHER"/>'><img src="<c:url value='/resources/img/front/icon_voucher_cash.png'/>" border="0"/>	</a>&nbsp;
<a href='<c:url value="/front/prepaid/voucherToAdd?prepaidType=TREATMENT_VOUCHER"/>'>
<img src="<c:url value='/resources/img/front/icon_voucher_trmt.png'/>" border="0"/></a>
</p> 

<p><br/><u><spring:message code="label.remarks"/> </u><br/>
                    <ul class="remarks_list">
					<li><spring:message code="front.label.statements22"/> </li>
					<li><spring:message code="front.label.statements23"/> </li>
                    </ul></p>           
</div>
<!--

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_index">
			<tr>
				<td colspan="2"><font color="#9D0000"><b>Thank you for choosing to buy an online voucher!</b></font></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2"><p>Input the voucher details for your purchase. The voucher can either be sent by e-mail or gift wrapped and collected at one of our shops. The quick and easy way to purchase 
				a special gift for yourself or your family and friends! Please follow these steps to complete the process:</p>			
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">
					<p>1. Select "Buy Treatment Voucher".<br/>
					2. Input the voucher details for your purchase. The voucher can either be sent by e-mail or gift wrapped and collected at one of our shops.<br/>
					3. Submit the purchase request and pay by credit card using the secure payment facility of our payment provider.<br/>
					4. On successful payment, you (and your friend if appropriate) will receive an email with the voucher or the pick up details.<br/>
				 </p></td>
			</tr>				
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2"><font color="#9D0000">
					Submit the purchase request and pay by Paypal.
				</font></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">
					<p>To commence, please select one of the following:</p><br/><br/>
				 </td>
			</tr>								
			<tr>
			<td colspan="2">
				<a href='<c:url value="/front/prepaid/voucherToAdd?prepaidType=CASH_VOUCHER"/>'>
					<img src="<c:url value='/resources/img/front/icon_voucher_cash.png'/>" border="0"/>
               	</a>
               	
               	&nbsp;
               	
				<a href='<c:url value="/front/prepaid/voucherToAdd?prepaidType=TREATMENT_VOUCHER"/>'>
					<img src="<c:url value='/resources/img/front/icon_voucher_trmt.png'/>" border="0"/>
               	</a>

			</td>
			</tr>
			<tr>
				<td colspan="2">
					<br/><p><u>Remarks</u></p><br/>
                    <ul class="remarks_list">
					<li>You must specify an email address for yourself in order to complete the process.</li>
					<li>Please keep the e-voucher in a safe place and present it at the shop on arrival. The validity and entitlement of the voucher will be based on the record kept in our Shop Management System.</li>
                    </ul>									
				 </td>
			</tr>			
		</table>
-->
</t:layout>