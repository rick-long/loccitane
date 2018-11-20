<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/front" %>

<t:layout title="My Profile">
<div class="profile-box">
   	<div class="title_for_all"><h2> <spring:message code="front.label.my.profile"/> </h2></div>
   	<c:url var="url" value="/front/saveProfile" />
    <div class="booking_detail_ctrl">
     <div class="content_left_page">
   <form id="profileForm" method="post" action='${url }' data-form-token="${TokenUtil.get(pageContext)}">
   	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="profile_table">
	  	<tr>
	    	<td width="35%"><label class="control-label">
	    		<spring:message code="label.firstname"/> *</label>
	        </td>
	    	<td width="65%">
	    		<div class="col-lg-12">
	        	<input id="firstName" value="${member.firstName }" name="firstName" type="text" class="form-control"></div>
	        </td>
	  	</tr>
	  	<tr>
	    	<td><label class="control-label"><spring:message code="label.lastname"/> *</label></td>
	    	<td>
	    		<div class="col-lg-12">
	       			<input id="lastName" value="${member.lastName }" name="lastName" type="text" class="form-control"  >
	            </div>
	       	</td>
	  	</tr>
  	<tr>
    	<td><label class="control-label"><spring:message code="label.gender"/> *</label></td>
    	<td>
    		<div class="col-lg-12">
				<label class="radio-inline"><input type="radio" name="gender" value="FEMALE" <c:if test="${member.gender eq 'FEMALE'}"> checked</c:if>/> <spring:message code="label.gender.FEMALE"/></label>
				<label class="radio-inline"><input type="radio" name="gender" value="MALE" <c:if test="${member.gender eq 'MALE'}"> checked</c:if>/> <spring:message code="label.gender.MALE"/></label>
	      	</div>
	    </td>
  	</tr>
  	<tr>
    	<td><label class="control-label"><spring:message code="label.dateofbirth"/></label></td>
    	<td><div class="col-lg-12">
	       		<div class="input-group date form_time">
	        	<input id="dateOfBirth" name="dateOfBirth" class="form-control dateOfBirth" 
	        		value='<fmt:formatDate value="${member.dateOfBirth }" pattern="yyyy-MM-dd"/>' size="16">
	            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	        	</div>
	        </div>
	    </td>
  	</tr>
 	<tr>
    	<td><label class="control-label"><spring:message code="label.email"/> *</label></td>
    	<td>
    		<div class="col-lg-12">
            	<input id="email" value="${member.email }" name="email" type="text" class="form-control">
            </div>
       	</td>
  	</tr>
  	<tr>
	    <td><label class="control-label"><spring:message code="label.address"/></label></td>
	    <td>
	    	<div class="col-lg-12">
				<input  type="text" value="${address}" name="addressVO.addressExtention" id="addressVO.addressExtention" class="form-control"/>
		    </div>
		</td>
  	</tr>
  	<tr>
    	<td><label class="control-label"><spring:message code="label.district"/> *</label></td>
    	<td>
    		<div class="col-lg-12 district-select">
				<input  type="text" name="addressVO.district" id="addressVO.district" class="form-control" value="${district}"/>
			    <%-- <ex:tree tree="${districtData}" name="addressVO.district" selectable="1,2,3"/> --%>
	        </div>
	   	</td>
  	</tr>
  	<tr>
    	<td><label class="control-label"><spring:message code="label.country"/></label></td>
    	<td>
    		<div class="col-lg-12">
         		<input  type="text" name="addressVO.country" value="${country}" id="addressVO.country" class="form-control"/>
      		</div>
      	</td>
  	</tr>
  	<tr>
    	<td><label class="control-label"><spring:message code="label.mobile.phone"/> *</label></td>
    	<td>
    		<div class="col-lg-12">
	         	<input  type="text" name="mobilePhone" id="mobilePhone" value="${mobilePhone}" class="form-control"/>
	      	</div>
	   	</td>
  	</tr>
  	<tr>
    	<td><label class="control-label"><spring:message code="label.home.phone"/></label></td>
    	<td>
    		<div class="col-lg-12">
	         	<input  type="text" name="homePhone" id="homePhone" value="${homePhone}" class="form-control"/>
	      	</div>
	    </td>
  	</tr>
  	<tr>
    	<td><label class="control-label"><spring:message code="label.shop"/> *</label></td>
    	<td> <div class="col-lg-12">
    		<select name="shopId" class="selectpicker form-control input_boder">
               <c:forEach items="${shopList}" var="item">
                   <option value="${item.id}" <c:if test="${member.shop.id == item.id}">selected</c:if>>${item.name}</option>
               </c:forEach>
            </select></div>
        </td>
  	</tr>
  	<tr>
    	<td><label class="control-label"><spring:message code="label.notification"/></label></td>
    	<td>
    		<div class="col-lg-12">
				<label class="radio-inline"><input type="checkbox" name="notification" value="EMAIL" <c:if test="${member.notification eq 'EMAIL'}"> checked</c:if>/> Email</label>
			   	<%-- <label class="radio-inline"><input type="checkbox" name="notification" value="SMS" <c:if test="${member.notification eq 'SMS'}"> checked</c:if>/> SMS</label> --%>
			</div>
		</td>  
  	</tr>
    <tr>
    	<td><label class="control-label"><spring:message code="label.password"/></label></td>
    	<td>
      <div class="col-lg-8">
		  <input class="form-control" id="disabledInput" type="text" placeholder="******" disabled></div>
        <div class="col-lg-4">
		 <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" style="border: 1px solid #5eb0cb;
background: #5eb0cb;
color: #fff;
text-shadow: none;
padding: 4px 20px;
vertical-align: middle;">Modify</a></div>
		</td> 
  	</tr>
    <tr>
    <td colspan="2">
    <div id="collapseOne" class="panel-collapse collapse" style="background: #e3e3e3;">
	<div class="panel-body">
    <div class="form-group">
	<form method="post" action="add_chk.php">
    <div class="col-lg-12" style="margin-bottom:10px;">
   <label class="col-lg-4 control-label"> <spring:message code="label.new.password"/> ：</label>
   <div class="col-lg-8"><input class="easyui-validatebox form-control" required missingMessage="密码必须填写" size="20" type="password" name="newPassword" id="pwd1"></input></div></div>
   
	<div class="col-lg-12">
    <label class="col-lg-4 control-label"><spring:message code="label.confirmPassword"/> ：</label>
    <div class="col-lg-8"><input class="easyui-validatebox form-control" required missingMessage="密码必须填写" size="20" type="password" name="confirmPassword" id="pwd2" /><span id="tishi"></span></input></div></div>
      </form>
      </div>
  </div>
</div>
</div>
        </td>
    </tr>
</table>

<div id="error_msg"></div>
<div class="modal-footer">
	<div class="bootstrap-dialog-footer">
		<input  type="hidden" name="enabled" id="enabled" value="true"/>
		<input  type="hidden" name="id" id="id" value="${member.id}"/>
		<button type="button" class="btn btn-primary" id="submitBtn">
			<spring:message code="label.button.submit"/>
		</button>
		<button type="button" class="btn btn-info dialogResetBtn" id="resetBtn">
    		<spring:message code="label.button.reset"/>
    	</button>
	</div>
</div>
</form>
</div>
</div>
</div>
</div>
</t:layout>
<script type="text/javascript">
$(document).ready(function () {

	$('#profileForm').bootstrapValidator({
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
	        },

            newPassword: {
                message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '<spring:message code="label.errors.password.length"/> 6-18'
                    }
                }
            },
            confirmPassword: {
                message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '<spring:message code="label.errors.password.length"/> 6-18'
                    },
                    identical: {//相同
                        field: 'newPassword', //需要进行比较的input name值
                        message: '<spring:message code="label.errors.password.not.same"/>'
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
    
    $('#submitBtn').click(function () {
    	var form=$('#profileForm');
    	var bootstrapValidator = form.data('bootstrapValidator');
    	
        if (bootstrapValidator) {
            // 自定义验证
            if (typeof bootstrapValidator.options.customValidate === 'function' && !bootstrapValidator.options.customValidate()) {
                return;
            }
            bootstrapValidator.validate(); // 验证
            var result = bootstrapValidator.isValid(); // 取出结果
            if (!result) {
                return;
            }
        }
	    $.ajax({
	        url: '<c:url value="/front/saveProfile"/>',
	        type: "POST",
	        dataType: "json",
	        data: form.serialize(),
	        success: function(json) {
	        	switch (json.statusCode) {
		            case 200:
		            	window.location.href='<c:url value="/front/index"/>';
		            	break;
		            case 300:
		            	 if (json.errorFields.length > 0) {
		                     var fieldErrors = '';
		                     $.each(json.errorFields, function (index, item) {
		                         var fieldName = item.fieldName;
		                         var errorMessage = item.errorMessage;
		                         fieldErrors += fieldName +" : "+errorMessage + "<br/>";
		                     });
		                     
		                     if (fieldErrors) {
		                    	 $('#error_msg').html(fieldErrors);
		                     }
		                 }
		                 if (form) {
		                     form.data('form-token', json.form_token);  // 重新设置token的值
		                 }
		            	break;
		            default:
		                console.info("status code error:" + json.statusCode);
		            break;
	        	}
	        }
	    });
    });
});
</script>
