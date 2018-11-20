<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.code"/></label>
    <label class="col-lg-5">
        ${bundleVO.code}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.name"/></label>
    <label class="col-lg-5">
        ${bundleVO.name}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
    <label class="col-lg-5">
        ${bundleVO.description}
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.start.time"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${bundleVO.startTime}" pattern="yyyy-MM-dd"/>
     </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.end.time"/></label>
    <div class="col-lg-5">
    <fmt:formatDate value="${bundleVO.endTime}" pattern="yyyy-MM-dd"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
    <div class="col-lg-5">
    <c:forEach items="${bundleVO.shopIds}" var="sid">
        ${sid} / 
    </c:forEach>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.bundle.amount"/></label>
    <label class="col-lg-5">
        ${bundleVO.bundleAmount}
    </label>
</div>
<div class="row new-row-width">
    <label><spring:message code="label.bundle.item"/></label><br/>
    <c:forEach items="${confirmProductOptions}" var="item">
    	<spring:message code="label.bundle.group"/> ${item.key+1} : <c:forEach items="${item.value}" var="productOption">${productOption.label33}/</c:forEach></br>
    </c:forEach>
    
</div>