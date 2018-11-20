<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>
	<style>
	.radio-inline2{
	padding-top:0 !important;
	padding-left:0 !important;
	}
	.radio-inline3{
	padding-top:0 !important;

	}
	input[type=checkbox], input[type=radio]{
	vertical-align:sub !important;
	}
	</style>
<div class="signle-form">
 <div class="container">
   <h3 class='text-h3-white'>
	<spring:message code="label.member.edit.management1"/>
   </h3>
<c:url var="url" value="/member/edit" />
<div class="row">
<form:form data-forward="memberListMenu" modelAttribute="memberEditVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
	<input type="hidden" id="currentMemberId" value="${currentMemberId} "/>
	<div class="form-group col-lg-12">
     	<label class="col-lg-3 control-label"><spring:message code="label.username"/></label>
 		<div class="col-lg-9">
            ${memberEditVO.username}
    	</div>
	</div>
	<div class="form-group col-lg-12">
    	<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.firstname"/> *</label>
 		<div class="col-lg-9 col-xs-12">
    		<form:input path="firstName" id="firstName" class="form-control"/>
    	</div>
	</div>
	<div class="form-group col-lg-12">
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.lastname"/> *</label>
		 <div class="col-lg-9 col-xs-12">
	    	<form:input path="lastName" id="lastName" class="form-control"/>
	    </div>
	</div>
	<div class="form-group col-lg-12">
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.email"/> *</label>
	 	<div class="col-lg-9 col-xs-12">
	    	<form:input path="email" id="email" class="form-control"/>
	    </div>
	</div>
	<div class="form-group col-lg-12">
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.gender"/> *</label>
	 	<div class="col-lg-9 col-xs-12">
	    	 <label class="radio-inline radio-inline3"><form:radiobutton path="gender" value="FEMALE"/> <spring:message code="label.gender.FEMALE"/></label>
			 <label class="radio-inline radio-inline3"><form:radiobutton path="gender" value="MALE"/> <spring:message code="label.gender.MALE"/></label>
	    </div>
	</div>
	<div class="form-group col-lg-12">
        <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.dateofbirth"/></label>
        <div class="col-lg-9 col-xs-12">
	       	<div class="input-group date form_time">
	        	<input id=dateOfBirth name="dateOfBirth" class="form-control dateOfBirth" 
	        		value='<fmt:formatDate value="${dateOfBirth}" pattern="yyyy-MM-dd"/>' size="16">
	            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	        </div>
        </div>
    </div>
	<div class="form-group col-lg-12">
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.notification"/></label>
	 	<div class="col-lg-9 col-xs-12">
	 		 <label class="radio-inline radio-inline2"><form:checkbox path="notification" value="EMAIL"/> <spring:message code="label.email"/> </label>
	 		 <label class="radio-inline  radio-inline2"><form:checkbox path="notification" value="SMS"/> <spring:message code="lable.SMS"/> </label>
	    </div>
	</div>
	<div class="form-group col-lg-12">
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.mobile.phone"/> *</label>
		 <div class="col-lg-9 col-xs-12">
	    	<form:input path="mobilePhone" id="mobilePhone" class="form-control"/>
	    </div>
	</div>
	<div class="form-group col-lg-12">
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.home.phone"/></label>
		 <div class="col-lg-9 col-xs-12">
	    	<form:input path="homePhone" id="homePhone" class="form-control"/>
	    </div>
	</div>
	<div class="form-group col-lg-12">
      	<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.country"/></label>
      	<div class="col-lg-9 col-xs-12">
         	<form:input path="addressVO.country" id="addressVO.country" class="form-control"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.city"/></label>
      	<div class="col-lg-9 col-xs-12">
         	<form:input path="addressVO.city" id="addressVO.city" class="form-control"/>
         	<form:hidden path="addressVO.id" id="addressVO.id"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
      	<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.address"/></label>
      	<div class="col-lg-9 col-xs-12">
         	<form:input path="addressVO.addressExtention" id="addressVO.addressExtention" class="form-control"/>
      	</div>
  	</div>
  	<div class="form-group col-lg-12">
        <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.member.user.group1"/></label>
        <div class="col-lg-9 col-xs-12">
            <ul class="label_list">
                <c:forEach var="ug" items="${userGroupsMarket }">
                <c:choose>
                <c:when test="${userGroups !=null && userGroups.size() >0}">
                <li><label class="radio-inline radio-inline2">
                    <c:set var="checkVar">

                    </c:set>
                    <c:forEach var="userGroup" items="${userGroups }">
                        <c:if test="${ug.id == userGroup.id }">
                            <c:set var="checkVar">
                                checked
                            </c:set>
                        </c:if>
                    </c:forEach>
                    <input type="checkbox" name="usergroupsIds" value="${ug.id}" ${checkVar}/> ${ug.name}
                </label>
                    </c:when>
                    <c:otherwise>
                        <label class="radio-inline radio-inline2">
                            <input type="checkbox" name="usergroupsIds" value="${ug.id}"/> ${ug.name}
                        </label>
                    </c:otherwise>
                    </c:choose>

                    </c:forEach>
                </li>
            </ul>
        </div>
    </div>
	<div class="form-group col-lg-12">
		<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.member.user.group2"/></label>
		<div class="col-lg-9 col-xs-12">
			<ul class="label_list">
				<c:forEach var="ug" items="${userGroupsDiscount }">
				<c:choose>
				<c:when test="${userGroups !=null && userGroups.size() >0}">
				<li><label class="radio-inline radio-inline2">
					<c:set var="checkVar">

					</c:set>
					<c:forEach var="userGroup" items="${userGroups }">
						<c:if test="${ug.id == userGroup.id }">
							<c:set var="checkVar">
								checked
							</c:set>
						</c:if>
					</c:forEach>
					<input type="checkbox" name="usergroupsIds" value="${ug.id}" ${checkVar}/> ${ug.name}
				</label>
					</c:when>
					<c:otherwise>
						<label class="radio-inline radio-inline2">
							<input type="checkbox" name="usergroupsIds" value="${ug.id}"/> ${ug.name}
						</label>
					</c:otherwise>
					</c:choose>

					</c:forEach>
				</li>
			</ul>
		</div>
	</div>
    <div class="form-group col-lg-12">
        <label class="col-lg-3 col-xs-12  control-label"><spring:message code="label.district"/> *</label>
        <div class="col-lg-9 district-select">
            <ex:tree tree="${districtData}" name="addressVO.district" selectable="1,2,3" selectId="${memberEditVO.addressVO.district}" selectName="${memberEditVO.addressVO.district}"/>
        </div>
    </div>
    <div class="form-group col-lg-12">
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.home.shop"/> *</label>
	 	<div class="col-lg-9 col-xs-12">
	    	<form:select class="selectpicker form-control" path="shopId" id="shopId">
				<c:forEach var="s" items="${shopList }">
					<form:option value="${s.id }">${s.name }</form:option>
				</c:forEach>
			</form:select>
   		</div>
	</div>
	<div class="form-group col-lg-12">
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.preferred.contact"/></label>
		<div class="col-lg-9 col-xs-12">
	    	<form:input path="preferredContact" id="preferredContact" class="form-control"/>
	    </div>
	</div>

	<div class="form-group col-lg-12">
		<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.preferred.shop"/></label>
		<div class="col-lg-9 col-xs-12">
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
			<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.preferred.therapist${ptc}"/></label>
			<div class="col-lg-9 col-xs-12">
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
		<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.preferred.room"/></label>
		<div class="col-lg-9 col-xs-12">
			<form:select class="selectpicker form-control" path="preferredRoom" id="preferredRoom">
			</form:select>
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
        <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.remarks"/></label>
        <div class="col-lg-9 col-xs-12">
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
	    <label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.enabled"/></label>
	 	<div class="col-lg-9 col-xs-12">
	    	<form:select class="selectpicker form-control" path="enabled" id="enabled">
				<form:option value="true"><spring:message code="label.option.yes"/></form:option>
				<form:option value="false"><spring:message code="label.option.no"/></form:option>
			</form:select>
	    </div>
	</div>
	<div class="form-group col-lg-12">
      	<label class="col-lg-3 col-xs-12 control-label"><spring:message code="label.member.opted.out"/> </label>
      	<div class="col-lg-9 col-xs-12">
      	 	<input type="checkbox" name="optedOut" ${memberEditVO.optedOut ? "checked" : ""}/>
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
                <form:hidden path="id" id="id" />
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
        fields: {firstName: {
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

//     var roomId = ${preferredRoomId};
    var roomId = '';
    /* get room by shop id */
    $('#preferredShop', getContext()).change(function () {
        //room
        $.each($('#preferredRoom', getContext()), function (index) {
            var obj = $(this);
            $.post('<c:url value="/room/roomSelectList"/>', {
                shopId: $('#preferredShop',getContext()).val(),
                companyId: 1,
                showAll: false,
                roomId: roomId,
                currentMemberId : $('#currentMemberId', getContext()).val()
            }, function (res) {
                obj.html(res);
            });
        });
    }).trigger('change');

    $('#preferredShop', getContext()).change(function () {
        console.log(12132);
        //therapist
        $.each($('.suitabledTherapists', getContext()), function (index) {
            var obj = $(this);
        	var preferreTherapist_0 = '' ;
        	var preferreTherapist_1 = '' ;
        	var preferreTherapist_2 = '' ;
            if(index == 0){
            	preferreTherapist_0 = index ;
            }
            if(index == 1){
            	preferreTherapist_1 = index ;
            }
            if(index == 2){
            	preferreTherapist_2 = index ;
            }
            $.post('<c:url value="/staff/therapistSelectList"/>', {
            	currentMemberId : $('#currentMemberId', getContext()).val(),
                shopId: $('#preferredShop', getContext()).val(),
                showAll: false,
                preferreTherapist_0 : preferreTherapist_0,
                preferreTherapist_1 : preferreTherapist_1,
                preferreTherapist_2 : preferreTherapist_2
            }, function (res) {
                obj.html(res);
            });
        });
    }).trigger('change');

});
</script>
 