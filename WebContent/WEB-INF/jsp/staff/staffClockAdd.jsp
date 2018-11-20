<%@page import="com.spa.constant.CommonConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="management container">
 <h3>
	<spring:message code="left.navbar.attendance.clock"/>
</h3>
<c:url var="url" value="/staff/clockInOrOut"/>
<div class="row">
<%--    <div id="allmap"></div>--%>
<form:form data-forward="clockView" modelAttribute="staffInOrOutVo" id="defaultForm" method="post" class="form-horizontal" action='${url}'>
    <input type="hidden" name="lat" id="lat"/>
    <input type="hidden" name="lng" id="lng"/>
    <div class="form-group col-lg-12">
        <label class="col-lg-4 control-label text-right"><spring:message code="label.pin"/>*</label>
        <div class="col-lg-5">
            <form:input path="pin" id="pin" class="form-control"/>
        </div>
    </div>

    <div class="form-group col-lg-12">
        <label class="col-lg-4 control-label text-right"><spring:message code="label.attendance"/>*</label>
        <div class="col-lg-5">
            <%--<input name="staffNumber" id="staffNumber" class="form-control"/>--%>
            <form:input path="staffNumber" id="barcode" class="form-control"/>
        </div>

    </div>
    <div class="form-group col-lg-12">
        <label class="col-lg-4 control-label text-right"><spring:message code="label.shop"/>*</label>
        <div class="col-lg-5">
            <select name="shop" id="shop" class="form-control text-right">
                <option value="">please select</option>
                <c:forEach items="${user.staffHomeShops}" var="item">
                    <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                </c:forEach>
            </select>
        </div>

    </div>
    
    <div class="form-group col-lg-12">
        <label class="col-lg-4 control-label text-right"><spring:message code="label.google.map.current.address"/>*</label>
        <div class="col-lg-5">
            <form:input path="currentAddress" id="currentAddress" class="form-control" readonly="true"/>
        </div>
        <label style="line-height: 32px;" class="text-right">
         <a href='<c:url value="/showGoogleMap"/>' title='position' class="btn btn-primary dialog btn-edit" data-draggable="true">
             <i class="glyphicon glyphicon-map-marker"></i>
         </a>
         </label>
    </div>
    
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary formPageSubmit" id="dlgSubmit">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form:form>
</div>

<script type="text/javascript">
    $(document).ready(function () {
    	
        var mapProp = {
                center:new google.maps.LatLng(51.508742,-0.120850),
                zoom:5,
                mapTypeId:google.maps.MapTypeId.ROADMAP
            };
            var map=new google.maps.Map(document.getElementById("googleMap"), mapProp);

        $('#defaultForm',getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                captcha: {
                    validators: {}
                }
            }
        });

        function formPageBeforeSubmit(){
            console.log("111");
            var staffNumber = $("#staffNumber").val();
            $("#barcode").val(staffNumber);
        }
    });
    
    function formPageSubmitCallBack(){
    		alert('here---');
    	 	window.location.href= '<c:url value="/bookBatch/toAddBookBatch"/>';
    }

</script>