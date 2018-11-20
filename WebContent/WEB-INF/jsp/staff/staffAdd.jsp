<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>
<style type="text/css">
    #serviceTreeBody td {
        border: 1px solid #977777;
    }
    .tab-content{    
        background-color: rgba(255,255,255,0.9);
        padding: 20px;
    }
    #holiday{
        margin:0 auto;
    }

    </style>
<div class="signle-form">
<div class="container">
<h3 class='text-h3-white'>
    <spring:message code="label.staff.add.management1"/>
</h3>
<c:url var="url" value="/staff/add" />
<div class="row">
<form id="defaultForm" data-forward="staffListMenu" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action='${url }'>
    <ul class="nav nav-tabs" id="myTab">
        <li class="active"><a href="#profile"><spring:message code="label.staff.profile"/></a></li>
        <!-- <li><a href="#service">Service</a></li> -->
        <li><a href="#payroll"><spring:message code="label.staff.pay.mode"/></a></li>
        <li><a href="#holiday"><spring:message code="label.staff.holiday.settings"/></a></li>
    </ul>
    <div class="tab-content">
    <div id="profile" class="tab-pane fade in active newprofile">
        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.home.shop"/>*</label>
            <div class="col-lg-9">
            <ul class="label_list">
            	<c:forEach var="s" items="${shopList }" varStatus="idx">
                	<li><label class="radio-inline">
                		<input type="checkbox" name="homeShopIds" value="${s.id}" <c:if test="${idx.first }">checked</c:if>/>&nbsp;${s.name}
               		</label></li>
                 </c:forEach>
                 </ul>
            </div>
        </div>
        <div class="form-group ">
           <label class="col-lg-3 control-label"><spring:message code="label.firstname"/>*</label>
           <div class="col-lg-9">
              <input type="text" name="firstName" id="firstName" class="form-control"/>
           </div>
       </div>
       <div class="form-group ">
           <label class="col-lg-3 control-label"><spring:message code="label.lastname"/>*</label>
           <div class="col-lg-9">
               <input type="text" name="lastName" id="lastName" class="form-control"/>
           </div>
       </div>
       <%-- <div class="form-group">
           <label class="col-lg-3 control-label"><spring:message code="label.username"/></label>
           <div class="col-lg-5">
              <input type="text" name="username" id="username" class="form-control" readonly/>
           </div>
       </div> --%>
       <div class="form-group ">
           <label class="col-lg-3 control-label"><spring:message code="label.password"/>*</label>
           <div class="col-lg-9">
              <input type="password"  name="password" id="password" class="form-control"/>
           </div>
       </div>
       <div class="form-group ">
           <label class="col-lg-3 control-label"><spring:message code="label.login.confirmPassword"/>*</label>
           <div class="col-lg-9">
              <input type="password"  name="confirmPassword" id="confirmPassword" class="form-control"/>
           </div>
       </div>
       
       <div class="form-group ">
           <label class="col-lg-3 control-label"><spring:message code="label.display.name"/>*</label>
           <div class="col-lg-9">
               <input type="text" name="displayName" id="displayName" class="form-control"/>
           </div>
       </div>
       <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.staff.role.name"/>*</label>
            <div class="col-lg-9">
                <select class="selectpicker form-control" name="sysRoleId" id="sysRoleId">
                    <c:forEach items="${sysRoleList}" var="role">
                        <option value="${role.id }">${role.name }</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.email"/>*</label>
            <div class="col-lg-9">
                <input type="text" name="email" id="email" class="form-control"/>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.mobile.phone"/>*</label>
            <div class="col-lg-9">
                <input type="text" name="mobilePhone" id="mobilePhone" class="form-control"/>
            </div>
        </div>

        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.gender"/>*</label>
            <div class="col-lg-9">
            <ul class="label_list">
                <li><label class="radio-inline"><input type="radio" name="gender" value="FEMALE" checked/> <spring:message code="label.gender.FEMALE"/></label></li>
              <li>  <label class="radio-inline"> <input type="radio" name="gender" value="MALE"/> <spring:message code="label.gender.MALE"/></label></li>
              </ul>
            </div>
        </div>
        <div class="form-group ">
	      	<label class="col-lg-3 control-label"><spring:message code="label.address"/></label>
	      	<div class="col-lg-9">
	         	<input type="text" name="addressVO.addressExtention" id="addressVO.addressExtention" class="form-control"/>
	      	</div>
	  	</div>
	    <div class="form-group ">
	        <label class="col-lg-3 control-label"><spring:message code="label.district"/> *</label>
	        <div class="col-lg-9 district-select">
	            <ex:tree tree="${districtData}" name="addressVO.district" selectable="1,2,3"/>
	        </div>
	    </div>
        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.staff.commission.group"/>*</label>
            <div class="col-lg-9">
            <ul class="label_list">
            	<c:forEach var="sg" items="${staffGroupsForCommission }" varStatus="idx">
                	<li><label class="radio-inline">
                		<input type="radio" name="staffGroupForCommission" value="${sg.id}" <c:if test="${idx.first }">checked</c:if>/>&nbsp;${sg.name}
               		</label></li> 
                 </c:forEach>
                 </ul>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.dateofbirth"/></label>
            <div class="col-lg-9">
                <div class="input-group date form_time">
                     <input type="text" id=dateOfBirth name="dateOfBirth" class="form-control dateOfBirth"
                        value='<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>' size="16">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                </div>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.join.date"/>*</label>
            <div class="col-lg-9">
                <div class="input-group date form_time">
                    <input id=joinDate name="joinDate" class="form-control joinDate"
                        value='<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>' size="16">
                    <span class="input-group-addon" id="joinDateSpan"><span class="glyphicon glyphicon-time"></span></span>
                </div>
            </div>
         </div>
         <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.whether.has.mpf"/>?</label>
            <div class="col-lg-9">
            	<select class="selectpicker form-control" name="hasMPF" id="hasMPF">
					<option value="true"><spring:message code="label.option.yes"/></option>
					<option value="false"><spring:message code="label.option.no"/></option>
				</select>
            </div>
         </div>
        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.attendance"/></label>
            <div class="col-lg-9">
                <input type="text" name="barCode" id="barCode" class="form-control"/>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-lg-3 control-label"><spring:message code="label.pin"/></label>
            <div class="col-lg-9">
                <input type="text" name="pin" id="pin" class="form-control"/>
            </div>
        </div>
    </div>
    <%-- <div id="service" class="tab-pane fade newprofile">
        <div class="form-group">
            <label class="col-lg-2 control-label"><spring:message code="label.category"/></label>
            <div id="categoryTreeId" class="col-lg-5">

            </div>
        </div>

    </div> --%>
    <div id="payroll" class="tab-pane fade">
    <div class="newprofile">
        <div class="form-group ">
            <label for="payrollTemplateId" class="col-lg-3 control-label"><spring:message code="label.staff.pay.mode1"/></label>
            <div class="col-lg-9" id="payrollTemplateIdSelect">

            </div>
        </div>
        </div>
        <div id="staffPayrollAttr"></div>
    </div>
    <div id="holiday" class="tab-pane fade newprofile">
        <div class="form-group ">
            <label class="col-lg-3 control-label" style="padding-top:17px !important"><spring:message code="label.annual.leave"/></label>
            <div class="col-lg-9">
            	* <spring:message code="label.staff.holiday.annual.leave.msg"/>.
                <input type="text" name="annualLeave" id="annualLeave" class="form-control"/>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-lg-3 control-label" style="padding-top:17px !important"><spring:message code="label.sick.leave"/></label>
            <div class="col-lg-9">
            	* <spring:message code="label.staff.holiday.sick.leave.msg"/>.
                <input type="text" name="sickLeave" id="sickLeave" class="form-control"/>
            </div>
        </div>
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
</div>
</form>
    </div>
    </div>
    </div>
<script type="text/javascript">
$(document).ready(function() {
	
	$('#myTab a:first').tab('show');
    
    $('#myTab a').click(function (e) { 
      	e.preventDefault();
      	$(this).tab('show');
    });
	
	//tab 3
	// payroll template
     $.post('<c:url value="/payroll/payrollTemplateSelectList"/>', function (res) {
         $('#payrollTemplateIdSelect').html(res);
         var selectedVal=$('#payrollTemplateId').find("option:selected").val();
         loadPayrollAttributes(selectedVal);
         
         $('#payrollTemplateId').on("change",function(){
        	 loadPayrollAttributes($(this).val());
         });
     });
	 
     function loadPayrollAttributes(payrollTemplateId){
    	$.post('<c:url value="/staff/staffAddPayrollAttribute"/>',{payrollTemplateId: payrollTemplateId}, function (res) {
       	 	$('#staffPayrollAttr').html(res);
        });
     }
    
    $('#defaultForm').bootstrapValidator({
    	message: '<spring:message code="label.errors.is.not.valid"/>',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
            	message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                    	message: '<spring:message code="label.errors.is.required"/>'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '<spring:message code="label.errors.username.length"/> 6-18'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '<spring:message code="label.errors.username.regexp"/>'
                    }
                }
            },
            password: {
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
                   /*  ,different: {
                        field: 'username',
                        message: '<spring:message code="label.errors.password.username.cant.same"/>'
                    } */
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
                    identical: {
                        field: 'password',
                        message: '<spring:message code="label.errors.password.not.same"/>'
                    }
                }
            },
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
            displayName: {
            	message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
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
            mobilePhone: {
            	message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
                    },
                    regexp: {
                        regexp: /^[0-9]*$/,
                        message: 'Should be a number.'
                    }
                }
            },
            pin: {
                message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                        message: '<spring:message code="label.errors.is.required"/>'
                    },
                    regexp: {
                        regexp: /^\d{4}$/,
                        message: 'Should be 4 number.'
                    }
                }
            }
        }
    });
    
    $('#dateOfBirth').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#joinDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    
    $('.input-group-addon').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });
    $('#joinDateSpan').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });
   
    
    $(".district-select", Dialog.getContext()).multiMenu();
});
</script>
 