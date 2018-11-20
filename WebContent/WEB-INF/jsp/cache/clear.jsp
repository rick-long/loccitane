<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>


<form method="post" class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-2 control-label">Cache Name:</label>
        <div class="col-lg-5">
            <select name="cacheNames" size="30" multiple>
                <c:forEach items="${cacheNames}" var="item">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label"></label>
        <div class="col-lg-5">
            ${success}
        </div>
    </div>

    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <%--<button type="button" class="btn btn-info dialogResetBtn">                     <spring:message code="label.button.reset"/>                 </button>--%>
                <button id="submit" type="button" class="btn btn-primary" data-skip-validate="true">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript">
    $('#submit', getContext()).click(function () {
        $.post('<c:url value="/cache/clear"/>', $('form', getContext()).serialize(), function (res) {
            getContext().html($(res));
        });
    });
</script>