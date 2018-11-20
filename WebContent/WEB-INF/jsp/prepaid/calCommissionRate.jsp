<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:if test="${prepaidAddVO.prepaidType =='CASH_VOUCHER' || prepaidAddVO.prepaidType =='TREATMENT_VOUCHER' }">
	<div class="form-group">
	    <label class="col-lg-3 control-label"><spring:message code="label.reference"/>*</label>
	    <div class="col-lg-5">
	       	<input type="text" name="referenceBackUp" id="referenceBackUp" value="${reference}" class="form-control"/>
	    </div>
	</div>
</c:if>
<div class="form-group">
    <label class="col-lg-3 control-label"><spring:message code="label.description"/></label>
    <div class="col-lg-5">
       	<input type="text" name="prepaidName" id="prepaidName" value="${prepaidName}" class="form-control"/>
       	<%-- <input type="hidden" name="prepaidName" id="prepaidName" value="${prepaidName }"/> --%>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-3 control-label"><spring:message code="label.commission"/>*(/100)</label>
    <div class="col-lg-5">
    	<c:choose>
			<c:when test="${(prepaidAddVO.prepaidType =='CASH_VOUCHER' || prepaidAddVO.prepaidType =='TREATMENT_VOUCHER')}">
				<input type="text" id="commissionRateString" value="${commissionRateString}" class="form-control"/>
				<input type="hidden" id="commissionDispaly" name="commissionDispaly" value="${commissionRate}" class="form-control"/>
			</c:when>
			<c:otherwise>
				<input type="text" id="commissionRateString" value="${commissionRateString}" disabled="disabled" class="form-control"/>
				<input type="hidden" id="commissionDispaly" name="commissionDispaly" value="${commissionRate}" disabled="disabled" class="form-control"/>
			</c:otherwise>
    	</c:choose>
    	
       	<input type="hidden" name="commissionRate" id="commissionRate" value="${commissionRate }"/>
    </div>
</div>

