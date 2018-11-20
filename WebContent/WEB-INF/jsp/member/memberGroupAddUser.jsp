<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/member/groupAddUser"/>
<form modelAttribute="userGroupVO" method="post" class="form-horizontal" action='${url}'>
  	<div class="form-group">
      	<label class="col-lg-4 control-label"><spring:message code="label.user.group"/></label>
      	<div class="col-lg-5">
            <select class="selectpicker form-control" name="userGroupId" id="userGroupId">
                <c:forEach var="item" items="${memberUserGroupList}">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
      	</div>
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

    });
</script>
 