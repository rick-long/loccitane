<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:url var="url" value="/po/productSelectShopSave"/>

<form:form modelAttribute="assignShopVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
<spring:message code="label.treatment.name"/> : ${product.name}</br>
    </br>
    <input type="hidden" name="productId"  value="${product.id}" >
    <fieldset>
        <legend></legend>
        <c:forEach items="${shopList}" var="shop" varStatus="idx">
            <div class="form-group">
                <label class="col-lg-6 control-label">${shop.name}</label>
            <input type="checkbox" name="shopIds" value="${shop.id}" <c:if test="${fn:contains(shopIds,shop.id)}">checked</c:if>/>
            </div>
        </c:forEach>
    </fieldset>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <%--<button type="button" class="btn btn-info dialogResetBtn">
                    <spring:message code="label.button.reset"/>
                </button>--%>
                <button type="button" class="btn btn-primary dialogSubmitBtn">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form:form>
