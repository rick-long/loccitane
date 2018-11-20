<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="voucheradd">
<form id="defaultForm" action='<c:url value="/front/prepaid/voucherSave"/>' method="post" class="form-horizontal">
	Voucher Confirmation
	<input type="hidden" id="prepaidType" name="prepaidType" value="${frontVoucherVO.prepaidType}" />
	<input type="hidden" id="memberId" name="memberId" value="${frontVoucherVO.memberId}" />
	<input type="hidden" id="pickUpType" name="pickUpType" value="${frontVoucherVO.pickUpType}" />
	<input type="hidden" id="paymentName" name="paymentName" value="${frontVoucherVO.paymentName}" />
	
    <c:if test="${frontVoucherVO.po !=null}">
    	<spring:message code="label.prepaid.type.TREATMENTVOUCHER"/> -
    	<spring:message code="label.treatment"/> : ${frontVoucherVO.po.label6 }
    	<input type="hidden" id="productOptionId" name="productOptionId" value="${frontVoucherVO.productOptionId}" />
    </c:if>
    <c:if test="${frontVoucherVO.prepaidType eq 'CASH_VOUCHER'}">
    	<spring:message code="label.prepaid.type.CASHVOUCHER"/> -
    </c:if>
    <spring:message code="front.label.face.value"/> : ${frontVoucherVO.prepaidValue }
    <input type="hidden" id="prepaidValue" name="prepaidValue" value="${frontVoucherVO.prepaidValue}" />
    <input type="hidden" id="expiryDate" name="expiryDate" value="${frontVoucherVO.expiryDate}"/>
    <p>Additional Notice</p>
    <c:if test="${frontVoucherVO.pickUpType eq 'location'}">
    	<spring:message code="front.label.pick.up.from"/> ${frontVoucherVO.shop.name};
    	<input type="hidden" id="pickUpLocation" name="pickUpLocation" value="${frontVoucherVO.pickUpLocation}" />
    </c:if>
    <c:if test="${frontVoucherVO.pickUpType eq 'email'}">
    	<spring:message code="front.label.statements13"/> ${loginMemberEmail};
    </c:if>
    <c:if test="${frontVoucherVO.pickUpType eq 'friend'}">
    	<spring:message code="front.label.statements24"/> :
     	<c:if test="${frontVoucherVO.additionalEmail !=''}">
    		<spring:message code="front.label.additional.email"/> :${frontVoucherVO.additionalEmail}
    		<input type="hidden" id="additionalEmail" name="additionalEmail" value="${frontVoucherVO.additionalEmail}" />
    	</c:if>
    	<c:if test="${frontVoucherVO.additionalName !=''}">
    		<spring:message code="front.label.additional.name"/> :${frontVoucherVO.additionalName}
    		<input type="hidden" id="additionalName" name="additionalName" value="${frontVoucherVO.additionalName}" />
    	</c:if>
    	<c:if test="${frontVoucherVO.additionalMessage !=''}">
    		<spring:message code="front.label.additional.message"/> :${frontVoucherVO.additionalMessage}
    		<input type="hidden" id="additionalMessage" name="additionalMessage" value="${frontVoucherVO.additionalMessage}" />
    	</c:if>
    </c:if>
    <spring:message code="front.label.payment.method.is"/> ${frontVoucherVO.paymentName};
    <button type="button" class="btn btn-default" id="save"><spring:message code="label.button.submit"/> </button>
</form>
</div>
<script type="text/javascript">
	            
	$("#save").click(function () {
		var form = $('#defaultForm');
    	window.location.href = '<c:url value="/front/prepaid/voucherSave?"/>' + form.serialize();
	}
</script>