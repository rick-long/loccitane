<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>
	<style>
		.radio-inline2{
			padding-top:0;
			padding-left:0;
		}
	input[type=checkbox], input[type=radio]{
		vertical-align:sub !important;
	}
	</style>
<div class="signle-form">
<div class="container">
<h3 class='text-h3-white'>
	<spring:message code="label.member.add.management1"/>
</h3>
<c:url var="url" value="/member/add" />
<div class="row">
<form:form data-forward="memberListMenu" data-form-token="${TokenUtil.get(pageContext)}"   modelAttribute="memberAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
 	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.firstname"/> *</label>
      	<div class="col-lg-9">
         	<form:input path="firstName" id="firstName" class="form-control"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12 ">
      	<label class="col-lg-3 control-label"><spring:message code="label.lastname"/> *</label>
      	<div class="col-lg-9">
         	<form:input path="lastName" id="lastName" class="form-control"/>
      	</div>
 	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.email"/> *</label>
      	<div class="col-lg-9">
         	<form:input path="email" id="email" class="form-control"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.mobile.phone"/> *</label>
      	<div class="col-lg-9">
         	<form:input path="mobilePhone" id="mobilePhone" class="form-control"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.home.phone"/></label>
      	<div class="col-lg-9">
         	<form:input path="homePhone" id="homePhone" class="form-control"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.gender"/> *</label>
      	<div class="col-lg-9">
         	 <label class="radio-inline">	<form:radiobutton path="gender" value="FEMALE" /> <spring:message code="label.gender.FEMALE"/></label>
			 <label class="radio-inline">	<form:radiobutton path="gender" value="MALE"/> <spring:message code="label.gender.MALE"/></label>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
        <label class="col-lg-3 control-label"><spring:message code="label.dateofbirth"/></label>
        <div class="col-lg-9">
	       	<div class="input-group date form_time">
	        	<input id=dateOfBirth name="dateOfBirth" class="form-control dateOfBirth" 
	        		value='<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>' size="16">
	            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	        </div>
        </div>
    </div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.notification"/></label>
      	<div class="col-lg-9">
      		  <label class="radio-inline radio-inline2"><form:checkbox path="notification" value="EMAIL"/> Email</label>
      		 <label class="radio-inline radio-inline2"><form:checkbox path="notification" value="SMS"/> SMS</label>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.home.shop"/> *</label>
      	<div class="col-lg-9">
         	<select class="selectpicker form-control" name="shopId" id="shopId">
				<c:forEach var="s" items="${shopList }">
					<option value="${s.id }" <c:if test="${currentShop.id == s.id}">selected</c:if>>${s.name }</option>
				</c:forEach>
			</select>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.preferred.contact"/></label>
      	<div class="col-lg-9">
         	<form:input path="preferredContact" id="preferredContact" class="form-control"/>
      	</div>
  	</div>

	<div class="form-group col-lg-12">
		<label class="col-lg-3 control-label"><spring:message code="label.preferred.shop"/></label>
		<div class="col-lg-9">
			<form:select class="selectpicker form-control" path="preferredShop" id="preferredShop">
				<form:option value=""><spring:message code="label.option.select.single"/></form:option>
				<c:forEach var="ps" items="${preferredShopList }">
					<form:option value="${ps.id }">${ps.name }</form:option>
				</c:forEach>
			</form:select>
		</div>
	</div>

	<%-- create by william -- 2018-8-14 --%>
	<c:forEach var="ptc" begin="1" end="${preferredTherapistCount}">
		<div class="form-group col-lg-12">
			<label class="col-lg-3 control-label"><spring:message code="label.preferred.therapist${ptc}"/></label>
			<div class="col-lg-9">
				<form:select class="selectpicker form-control suitabledTherapists" path="preferredTherapistId${ptc}" id="preferredTherapistId${ptc}">
					<form:option value=""><spring:message code="label.option.select.single"/></form:option>
					<c:forEach var="pt" items="${preferredTherapistList}">
						<form:option value="${pt.id }">${pt.username }</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
	</c:forEach>

	<div class="form-group col-lg-12">
		<label class="col-lg-3 control-label"><spring:message code="label.preferred.room"/></label>
		<div class="col-lg-9">
			<form:select class="selectpicker form-control" path="preferredRoom" id="preferredRoom">
			</form:select>
		</div>
	</div>
   	
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.country"/></label>
      	<div class="col-lg-9">
         	<form:input path="addressVO.country" id="addressVO.country" class="form-control"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.city"/></label>
      	<div class="col-lg-9">
         	<form:input path="addressVO.city" id="addressVO.city" class="form-control"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.address"/></label>
      	<div class="col-lg-9">
         	<form:input path="addressVO.addressExtention" id="addressVO.addressExtention" class="form-control"/>
      	</div>
  	</div>
    <div class="form-group col-lg-12">
        <label class="col-lg-3 control-label"><spring:message code="label.district"/> *</label>
        <div class="col-lg-9 district-select">
            <ex:tree tree="${districtData}" name="addressVO.district" selectable="1,2,3"/>
        </div>
    </div>
    <div class="form-group col-lg-12">
        <label class="col-lg-3 control-label"><spring:message code="label.member.user.group1"/></label>
        <div class="col-lg-9">
        <ul class="label_list">
        	<c:forEach var="ug" items="${userGroups}" varStatus="idx">
			<li><label class="radio-inline">
					<input type="checkbox" name="usergroupsIds" value="${ug.id}"/>&nbsp;${ug.name}
			</label></li>
		</c:forEach>
             </ul>
        </div>
    </div>
	<div class="form-group col-lg-12">
		<label class="col-lg-3 control-label"><spring:message code="label.member.user.group2"/></label>
		<div class="col-lg-9">
			<ul class="label_list">
				<c:forEach var="ug" items="${userGroups1}" varStatus="idx">
					<li><label class="radio-inline">
						<input type="checkbox" name="usergroupsIds" value="${ug.id}"/>&nbsp;${ug.name}
					</label></li>
				</c:forEach>
			</ul>
		</div>
	</div>
  	<%--<div class="form-group">
      	<label class="col-lg-3 control-label"><spring:message code="label.communication.channel"/></label>
      	<div class="col-lg-5">
         	<form:select class="selectpicker form-control" path="communicationChannelId" id="communicationChannelId">
				<c:forEach var="cc" items="${communicationChannelList }">
					<form:option value="${cc.id }">${cc.name }</form:option>
				</c:forEach>
			</form:select>
      	</div>
  	</div>--%>
  	<div class="form-group col-lg-12">
        <label class="col-lg-3 control-label"><spring:message code="label.remarks"/></label>
        <div class="col-lg-9">
            <form:textarea path="remarks" id="remarks" class="form-control"/>
        </div>
    </div>
    <%--<div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.air.miles.membership.number"/></label>
        <div class="col-lg-5">
            <form:textarea path="airmilesMembershipNumber" id="airmilesMembershipNumber" class="form-control"/>
        </div>
    </div>--%>
    <div class="form-group col-lg-12">
      	<label class="col-lg-3 control-label"><spring:message code="label.member.opted.out"/> </label>
      	<div class="col-lg-9">
         	 <input type="checkbox" name="optedOut"/>
      	</div>
  	</div>
  	<div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary formPageSubmit" id="dlgSubmit">
                    <spring:message code="label.button.submit"/>
                </button>
                <%-- <button type="button" class="btn btn-info dialogResetBtn" id="resetBtn">
                    <spring:message code="label.button.reset"/>
                </button> --%>
            </div>
        </div>
    </div>
</form:form>
	</div>
	</div>
	</div>
<script type="text/javascript">
$(document).ready(function() {
	
    $('#defaultForm').bootstrapValidator({
    	message: '<spring:message code="label.errors.is.not.valid"/>',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            firstName: {
                message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
                    }
                }
            },
            lastName: {
                message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
                    }
                }
            },
            mobilePhone: {
                message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
                    },
                    mobilePhone: {
                        message: '<spring:message code="label.errors.is.not.mobile.phone"/>'
                    }
                }
            },
            email: {
            	message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
                    },
                    emailAddress: {
                        message: '<spring:message code="label.errors.email.correct.address"/>'
                    }
                }
            }
        }
    })
    $('#dateOfBirth').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('.input-group-addon').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });

    $(".district-select").multiMenu();

    $('#preferredShop', getContext()).change(function () {
        //room
        $.each($('#preferredRoom', getContext()), function (index) {
            var obj = $(this);
            $.post('<c:url value="/room/roomSelectList"/>', {
                shopId: $('#preferredShop',getContext()).val(),
                companyId: 1,
                showAll: false,
                roomId: null,
            }, function (res) {
                obj.html(res);
            });
        });
        
       //therapist
        $.each($('.suitabledTherapists',getContext()), function (index) {
            var obj=$(this);
            var objName=obj.attr('name');
            if(index==0 || objName=='tipsTherapist'){
                $.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#preferredShop',getContext()).val()},function (res) {
                    obj.html(res);
                });
            }else{
                $.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#preferredShop',getContext()).val(),showAll:false},function (res) {
                    obj.html(res);
                });
            }
        });
    }).trigger('change');

});

</script>
 