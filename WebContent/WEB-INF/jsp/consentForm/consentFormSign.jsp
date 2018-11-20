<%@page import="org.spa.model.user.ConsentFormUser"%>
<%@page import="org.spa.model.user.ConsentForm"%>
<%@page import="org.spa.model.shop.Shop"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style type="text/css">
    #productOptionBody td {
        border: 1px solid #ccc;
    }
</style>
<c:url var="url" value="/consentForm/sign"/>
<form  id="defaultForm" enctype="multipart/form-data" method="post" class="form-horizontal" data-form-token="${TokenUtil.get(pageContext)}" action='${url}' style="overflow-y: scroll;overflow-x: hidden; background-color: rgba(255,255,255,0.9);max-height:500px;" >
    <input type="hidden" name="userId" value="${userId}">
    <div class="form-group">
        <!--<label class="col-lg-4 control-label"><spring:message code="label.fullName"/></label>-->
        <div class="col-lg-12">
            <div>${userFullName}</div>
        </div>
    </div>
    <div class="row new-row-width">
        <table class="table table-striped">
            <thead>
            <tr>
                <th width="30%"><spring:message code="label.consent.form.name"/> </th>
                <th width="70%"><spring:message code="label.shops"/> </th>
            </tr>
            </thead>
            <tbody id="productOptionBody">
            <c:forEach items="${consentFormList}" var="consentForm" varStatus="cfidx">
                <tr data-cf-id="${consentForm.id}">
                    <td> ${consentForm.name}
                        <input name="consentFormUserVOs[${cfidx.index}].consentFormId" type="hidden" value="${consentForm.id}"/>

                        <input name="consentFormUserVOs[${cfidx.index}].document" type="file"  accept=".pdf" class="folder_style btn">
                    </td>
                    <td>
                        <c:forEach items="${shopList}" var="shop" varStatus="sidx">
                            <label class="radio-inline">
                                <%
                                    Long userId=(Long)request.getAttribute("userId");
                                    ConsentForm consentForm=(ConsentForm)pageContext.getAttribute("consentForm");
                                    Shop shop=(Shop)pageContext.getAttribute("shop");
                                    ConsentFormUser cfu=shop.getConsentFormUserDetails(userId, consentForm.getId());
                                    pageContext.setAttribute("cfu",cfu);
                                %>
                                <input type="radio" name="consentFormUserVOs[${cfidx.index}].shopId" value="${shop.id}" <c:if test="${cfu !=null}"> checked</c:if>/>
                                    ${shop.name}
                                <c:if test="${cfu.documentId ne null}">
                                    [<a href='<c:url value="/common/downloadDocument/${cfu.documentId}"/>' target="_blank"><font class="blue_down">${cfu.docuemnt.name}</font></a>]
                                </c:if>
                            </label>
                        </c:forEach>
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {
        $(":file", Dialog.getContext()).filestyle({
            buttonText: '',
            placeholder: 'Choose File'
        });
    });
</script>