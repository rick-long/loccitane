<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${status !=''}">
<c:url var="url" value="/book/saveStatusForDoubleBooking"/>
<form:form modelAttribute="doubleBookingVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
	<spring:message code="label.book.change.status"/> <b>${status}</b> <spring:message code="label.book.for.double.booking"/>
	<input type="hidden" name="status" value="${status}"/>
    </br>
    <fieldset>
        <legend></legend>
         <div class="form-group">
             <label class="col-lg-4 control-label"><spring:message code="label.client"/> : ${parentBookItem.book.user.fullName}</br>${parentBookItem.productName}</label></br>/ ${parentBookItem.status}

             <c:if test="${status eq'COMPLETE' and parentBookItem.book.hasMoreThanOneBookItemCanBePaid and parentBookItem.book.status !='PAID' and parentBookItem.status eq 'CHECKIN_SERVICE' }">
                 <a data-permission="sales:bookToCheckout"  onclick='window.location.href="/?loadingUrl=<c:url value="/sales/bookToCheckout?bookId=${parentBookItem.book.id}"/>"' title="Check Out" class="btn btn-primary  btn-edit form-page" data-draggable="true" data-title='Check Out'>
                     <i class="glyphicon glyphicon-ok"></i>
                 </a>
             </c:if>
             <c:if test="${status ne 'COMPLETE' and parentBookItem.status ne status  and parentBookItem.status ne 'COMPLETE' }">
         	 <input type="checkbox" name="bookItemIds" value="${parentBookItem.id}"/>
             </c:if>
         </div>
        <c:forEach items="${childrens}" var="bi" varStatus="idx">
            <div class="form-group">

                <label class="col-lg-4 control-label"> <spring:message code="label.client"/> :${bi.book.user.fullName}</br>  ${bi.productName}</label></br>/ ${bi.status}
                <c:if test="${status eq'COMPLETE' and bi.book.hasMoreThanOneBookItemCanBePaid and bi.book.status ne'PAID' and bi.status eq 'CHECKIN_SERVICE' }">
                    <a data-permission="sales:bookToCheckout" onclick='window.location.href="/?loadingUrl=<c:url value="/sales/bookToCheckout?bookId=${bi.book.id}"/>"'  title="Check Out" class="btn btn-primary  btn-edit form-page" data-draggable="true" data-title='Check Out'>
                        <i class="glyphicon glyphicon-ok"></i>
                    </a>
                </c:if>
                <c:if test="${status ne 'COMPLETE' and bi.status ne status  and bi.status ne 'COMPLETE' }">
                    <input type="checkbox" name="bookItemIds" value="${bi.id}"/>
                    </c:if>
            </div>
        </c:forEach>
    </fieldset>
    <c:if test="${status !='COMPLETE'}">
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary dialogSubmitBtn">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
    </c:if>

</form:form>
</c:if>






