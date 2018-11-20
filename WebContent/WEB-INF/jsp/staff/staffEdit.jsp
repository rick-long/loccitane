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
    .form-horizontal .control-label{
        text-align:left !important;
        display:block;
        margin-bottom:7px !important;
    }

    #payrollTemplateIdSelect{
        float:unset;
    }
    .table>tbody>tr>td{
        width:33%;
        border-right:1px dashed;

    }
    .table>tbody>tr>td:last-child{
        border-right:unset;

    }
    .table > thead:first-child > tr:first-child > th{
        border-right:1px dashed;
    }
    .table > thead:first-child > tr:first-child > th:last-child{
        border-right:unset;
    }

</style>
<h3 class='text-h3-white'>
    <spring:message code="label.staff.edit.management1"/>
</h3>
<c:url var="url" value="/staff/edit" />
<form id="defaultForm" data-forward="staffListMenu" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action='${url}'>
    <ul class="nav nav-tabs" id="myTab">
        <li class="active"><a href="#profile"><spring:message code="label.staff.profile"/></a></li>
       <!--  <li><a href="#service">Service</a></li> -->
        <li><a href="#payroll"><spring:message code="label.staff.pay.mode"/></a></li>
        <li><a href="#holiday"><spring:message code="label.staff.holiday.settings"/></a></li>
    </ul>
    <div class="tab-content">
        <input type="hidden" id="id" name="id" value="${staff.id }">
        <input type="hidden" id="payrollTemplateIdHidden" value="${payrollTemplateIdHidden}">
        <div id="profile" class="tab-pane fade in active">
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.home.shop"/>*</label>
                <div class="col-lg-5">
                    <ul class="label_list">
                        <c:forEach var="s" items="${shopList }">
                        <c:choose>
                        <c:when test="${staffHomeShops !=null && staffHomeShops.size() >0}">
                        <li><label class="radio-inline">
                            <c:set var="checkVar">

                            </c:set>
                            <c:forEach var="homeShop" items="${staffHomeShops }">
                                <c:if test="${s.id == homeShop.id }">
                                    <c:set var="checkVar">
                                        checked
                                    </c:set>
                                </c:if>
                            </c:forEach>
                            <input type="checkbox" name="homeShopIds" value="${s.id}" ${checkVar}/> ${s.name}
                        </label>
                            </c:when>
                            <c:otherwise>
                                <label class="radio-inline">
                                    <input type="checkbox" name="homeShopIds" value="${s.id}" <c:if test="${idx.first }">checked</c:if>/> ${s.name}
                                </label>
                            </c:otherwise>
                            </c:choose>

                            </c:forEach>
                        </li>
                    </ul>
                </div>
            </div>

            <%-- <div class="form-group">
               <label class="col-lg-3 control-label"><spring:message code="label.username"/></label>
               <div class="col-lg-5">
                  <input type="text" name="username" id="username" value="${staff.username }" class="form-control"/>
               </div>
           </div> --%>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.firstname"/>*</label>
                <div class="col-lg-5">
                    <input type="text" name="firstName" id="firstName" value="${staff.firstName }" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.lastname"/>*</label>
                <div class="col-lg-5">
                    <input type="text" name="lastName" id="lastName" value="${staff.lastName }" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.display.name"/>*</label>
                <div class="col-lg-5">
                    <input type="text" name="displayName" id="displayName" value="${staff.displayName }" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.staff.role.name"/>*</label>
                <div class="col-lg-5">
                    <select class="selectpicker form-control" name="sysRoleId" id="sysRoleId">
                        <c:forEach items="${sysRoleList}" var="role">
                            <option value="${role.id }" <c:if test="${role.id == staff.firstRoleForStaff.id}">selected</c:if>>${role.name }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.email"/>*</label>
                <div class="col-lg-5">
                    <input type="text" name="email" id="email" value="${staff.email }" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.mobile.phone"/>*</label>
                <div class="col-lg-5">
                    <input type="text" name="mobilePhone" id="mobilePhone" value="${staff.mobilePhone }" class="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.gender"/>*</label>
                <div class="col-lg-5">
                    <ul class="label_list">
                        <li>
                            <label class="radio-inline"><input type="radio" name="gender" value="FEMALE" <c:if test="${'FEMALE' == staff.gender }"> checked</c:if>/> <spring:message code="label.gender.FEMALE"/></label></li>
                        <li><label class="radio-inline"> <input type="radio" name="gender" value="MALE" <c:if test="${'MALE' == staff.gender }"> checked</c:if>/> <spring:message code="label.gender.MALE"/></label>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.address"/></label>
                <div class="col-lg-5">
                    <input type="text" name="addressVO.addressExtention" id="addressVO.addressExtention" class="form-control" value="${staffVO.addressVO.addressExtention}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.district"/></label>
                <div class="col-lg-5 district-select">
                    <ex:tree tree="${districtData}" name="addressVO.district" selectable="1,2,3" selectId="${staffVO.addressVO.district}" selectName="${staffVO.addressVO.district}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.staff.commission.group"/>*</label>
                <div class="col-lg-5">
                    <ul class="label_list">
                        <c:forEach var="sg" items="${staffGroupsForCommission }">
                            <li><label class="radio-inline">
                                <input type="radio" name="staffGroupForCommission" value="${sg.id}" <c:if test="${currentStaffGroup !=null && (sg.id == currentStaffGroup.id)}">checked</c:if>/> ${sg.name}
                            </label></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.dateofbirth"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input type="text" id=dateOfBirth name="dateOfBirth" class="form-control dateOfBirth"
                               value='<fmt:formatDate value="${staff.dateOfBirth}" pattern="yyyy-MM-dd"/>' size="16">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.join.date"/>*</label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id=joinDate name="joinDate" class="form-control joinDate"
                               value='<fmt:formatDate value="${staff.joinDate}" pattern="yyyy-MM-dd"/>' size="16">
                        <span class="input-group-addon" id="joinDateSpan"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
	            <label class="col-lg-2 control-label text-right"> <spring:message code="label.whether.has.mpf"/>?</label>
	            <div class="col-lg-5">
	                <select class="selectpicker form-control" name="hasMPF" id="hasMPF">
						<option value="true" <c:if test="${staff.hasMPF}">selected</c:if>><spring:message code="label.option.yes"/></option>
						<option value="false" <c:if test="${!staff.hasMPF}">selected</c:if>><spring:message code="label.option.no"/></option>
					</select>
	            </div>
	         </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.attendance"/></label>
                <div class="col-lg-5">
                    <input type="text" name="barCode" id="barCode" value="${staff.barcode}" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.staff.pin"/></label>
                <div class="col-lg-5">
                    <input type="text" name="pin" id="pin" value="${staff.pin}" class="form-control"/>
                </div>
            </div>
            <%--<div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.isactive"/></label>
                <select name="active">
                    <div class="col-lg-5">
                        <option value="true" class="form-control" <c:if test="${staff.activing == true}">selected</c:if>>Active</option>
                        <option value="false" class="form-control" <c:if test="${staff.activing == false}">selected</c:if>>No Active</option>
                    </div>
                </select>
            </div>--%>
        </div>
        <%-- <div id="service" class="tab-pane fade">
            <div class="form-group">
                <label class="col-lg-2 control-label"><spring:message code="label.category"/></label>
                <div id="categoryTreeId" class="col-lg-5">

                </div>
            </div>
        </div> --%>
        <div id="payroll" class="tab-pane fade">
            <div class="newprofile">
                <div class="form-group">
                    <label for="payrollTemplateId" class="col-lg-2 control-label text-right"><spring:message code="label.staff.pay.mode1"/></label>
                    <div class="col-lg-5" id="payrollTemplateIdSelect">

                    </div>
                </div>
            </div>
            <div id="staffPayrollAttr"></div>
        </div>

        <div id="holiday" class="tab-pane fade">
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.annual.leave"/></label>
                <div class="col-lg-5">
                    * <spring:message code="label.staff.holiday.annual.leave.msg"/>.
                    <input type="text" name="annualLeave" id="annualLeave" value='${staff.staffHoliday.annualLeave}' class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label text-right"><spring:message code="label.sick.leave"/></label>
                <div class="col-lg-5">
                    * <spring:message code="label.staff.holiday.sick.leave.msg"/>.
                    <input type="text" name="sickLeave" id="sickLeave" value="${staff.staffHoliday.sickLeave}" class="form-control"/>
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
    <%-- <c:forEach items="${staff.staffTreatmentses}" var="item">
        <c:if test="${not empty item.category}">
            <input name="categoryIds" value="${item.category.id}" type="hidden">
        </c:if>
        <c:if test="${not empty item.product}">
            <input name="productIds" value="${item.product.id}" type="hidden">
        </c:if>
    </c:forEach> --%>
</form>
<script type="text/javascript">
    $(document).ready(function() {

        $('#myTab a:first').tab('show');

        $('#myTab a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        })
		
        //tab 3
        // payroll template
        $.post('<c:url value="/payroll/payrollTemplateSelectList"/>',{initialValue:$('#payrollTemplateIdHidden').val()},function (res) {
            $('#payrollTemplateIdSelect').html(res);
            loadPayrollAttributes($('#payrollTemplateIdHidden').val());

            $('#payrollTemplateId').on("change",function(){
                loadPayrollAttributes($(this).val());
            });
        });

        function loadPayrollAttributes(payrollTemplateId){
            $.post('<c:url value="/staff/staffAddPayrollAttribute"/>',{payrollTemplateId: payrollTemplateId,id:$('#id').val()}, function (res) {
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
                firstName: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
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
 