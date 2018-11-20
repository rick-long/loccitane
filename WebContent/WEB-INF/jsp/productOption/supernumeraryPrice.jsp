<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/po/addSupernumeraryPrice"/>
<form:form modelAttribute="supernumeraryPriceAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
 	<spring:message code="label.prepaid.product.name"/> : ${pname}</br>
    <spring:message code="label.po"/> : ${poRef}</br>
    <spring:message code="label.product.ordinary.price"/>: ${supernumeraryPriceAddVO.originalPrice}
    <fieldset>
        <legend></legend>
        <c:forEach items="${shopList}" var="shop" varStatus="idx">
            <div class="form-group">
                <label class="col-lg-5 control-label">${shop.name}</label>
                <div class="col-lg-5">
                    <c:if test="${not empty kvMap[shop.id]}">
                        <form:input path="shopPriceList[${idx.index }].value" value="${kvMap[shop.id].value }" class="form-control"/>
                        <form:hidden path="shopPriceList[${idx.index }].key" value="${kvMap[shop.id].key }"/>
                        <form:hidden path="shopPriceList[${idx.index }].id" value="${kvMap[shop.id].id }"/>
                    </c:if>
                    <c:if test="${empty kvMap[shop.id]}">
                        <form:input path="shopPriceList[${idx.index }].value" class="form-control"/>
                        <form:hidden path="shopPriceList[${idx.index }].key" value="${shop.id}"/>
                        <form:hidden path="shopPriceList[${idx.index }].id"/>
                    </c:if>
                </div>
                <form:hidden path="productOptionId"/>
                <form:hidden path="originalPrice"/>
            </div>
        </c:forEach>
    </fieldset>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
               <%-- <button type="button" class="btn btn-info dialogResetBtn">
                    <spring:message code="label.button.reset"/>
                </button>--%>
                <button type="button" class="btn btn-primary dialogSubmitBtn">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form:form>