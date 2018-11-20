<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>

<%
    // 所有css / js資源版本控制，每次更新的時候需要修改版本號，保證客戶端使用的css和js都是最新的代碼
    request.setAttribute("version", "20160830");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="label.member.register"/></title>
    <!-- Bootstrap -->
     <link href="/resources/img/favicon.ico" rel="icon" type="image/x-icon">
    <link href='<c:url value="/resources/js/jqueryui/jquery-ui.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/jquery-contextMenu/jquery.contextMenu.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/multi-select/multi-select.css"/>?v=${version}' rel="stylesheet">

    <!-- Bootstrap -->
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.css.map"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap-theme.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.css.map"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/css/bootstrap-dialog/bootstrap-dialog.css"/>?v=${version}' rel="stylesheet">
	
    <link href='<c:url value="/resources/css/metisMenu/metisMenu.min.css"/>?v=${version}' rel="stylesheet">

    <!-- Custom Fonts -->
    <link href='<c:url value="/resources/css/font-awesome/font-awesome.min.css"/>?v=${version}' rel="stylesheet">

    <!-- bootstrapvalidator-0.4.5 -->
    <link href='<c:url value="/resources/css/bootstrapvalidator-0.4.5/bootstrapValidator.css"/>?v=${version}' rel="stylesheet">
    <!-- jquery.datetimepicker -->
    <link href='<c:url value="/resources/js/boootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.css"/>?v=${version}' rel="stylesheet">

    <link href='<c:url value="/resources/js/bootstrap-submenu/css/bootstrap-submenu.min.css"/>?v=${version}' rel="stylesheet">
    <link href='<c:url value="/resources/js/boostrap-select/css/bootstrap-select.css"/>?v=${version}' rel="stylesheet">
    
    <link href='<c:url value="/resources/css/base/public.css"/>?v=${version}' rel="stylesheet">
    
        <link href='<c:url value="/resources/css/front/public_front.css"/>?v=${version}' rel="stylesheet">
    
    <style type="text/css">
  *, *:before, *:after {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box
        }

        input:focus {
            outline: none;
			background:transparent;
        }
		
		input:-webkit-autofill, textarea:-webkit-autofill, select:-webkit-autofill {
    background-color:transparent;
    background-image: none;
    color: rgb(0, 0, 0);
}

        [placeholder]:focus::-webkit-input-placeholder {
            -webkit-transition: opacity 0.5s ease;
            -moz-transition: opacity 0.5s ease;
            transition: opacity 0.5s ease;
            opacity: 0
        }

       html {
           background: url(<c:url value="/resources/img/front/LO_Background-Image2.jpg"/>);
            font-size: 14px;
            font-family:SegoeUI, sans-serif;
            color: #001022;
            background-size: cover;
			background-attachment:fixed;
			background-repeat:no-repeat;
        }
		
	body {
            font-size: 14px;
            font-family:SegoeUI, sans-serif;
    line-height: 1.42857143;
    color: #001022;
    background: none;
}	
       

        input, textarea, keygen, select, button {
            font-family: SegoeUI, sans-serif;
            color: #A4A6B5;
            font-size: 14px
        }

       input:-webkit-autofill, textarea:-webkit-autofill, select:-webkit-autofill {
    background-color: transparent;
    background-image: none;
    color: rgb(0, 0, 0);
}

        a:link {
            color: #fff;
        }

        input[type="text"], input[type="email"], input[type="password"] {
            display: inline-block;
            padding: 8px 3px;
            width: 100% !important;
            box-sizing: border-box;
            background: transparent;
			border: 0;
            color: #fff;
        }

        input[type="text"]:focus, input[type="email"]:focus, input[type="password"]:focus {
            background: transparent;
			color:#fff;
        }
		
	label {
    display: inline-block;
    max-width: 100%;
    margin-bottom: 5px;
    font-weight: 400; 
	font-size:14px;
}
.modal-footer {
    padding: 15px;
    text-align: right;
    margin-top: 1px;
    border: 0;
    background:none;
}

table.registers_table td {
    padding: 6px 0;
}
	
	.main_box {
    top: 36%;
    width: 500px;
    height: 950px;
        }
			
	.form-control {
    display: block;
    width: 100%;
    height: 28px;
    padding: 3px 9px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #fff;
    background-color: transparent;
    background-image: none;
    border: 0;
    border-radius:0;
    -webkit-box-shadow: none;
    box-shadow: none;
    -webkit-transition:none;
    -o-transition:none;
    transition:none;
}

   .input-group-addon {
    padding: 4px 12px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #A4A6B5;
    text-align: center;
    background-color: transparent;
    border: 0;
    border-radius: 3px;
    width: 100%;
}

 .btn-default {
    text-shadow: none;
    background: transparent;
    background-repeat: repeat-x;
    border-color: 0;
    color:#001022;
	border:0;
}
    .btn-default:focus, .btn-default:hover {
    border: 0;
    background: transparent;
    color: #001022;
    text-shadow: none;
}
 
    .btn-primary, .btn-info {
    background: #ffcb00;
    border-color: #ffcb00;
    color: #001022;
    text-shadow: none;
    border-radius: 0;
    padding: 6px 20px;

}

    .btn-primary:focus, .btn-primary:hover {
    border: 1px solid #f7d756;
    background: #f7d756;
    color: #001022;
    text-shadow: none;
}

 .help-block {
    display: block;
    margin-top:0;
    margin-bottom: 10px;
    color: #ff0000;
    position: absolute;
       right: -75%;
    top: 0%;
    width: 70%;
}

.login_logo h2{ text-transform:uppercase;}

#error_msg {color: #ff0000;}

.checkbox-inline, .radio-inline input[type=radio] {
    position: absolute;
    margin-top: 4px\9;
    margin-left: -20px;
    margin-top:2px;
}

.btn-group.bootstrap-select.form-control {
    margin-left: 11px;
    border: 1px solid #A4A6B5;
    border-radius: 3px;
    width: 88%;
}

.main_box input[type="text"], .main_box input[type="email"], .main_box input[type="password"] {
    display: inline-block;
    border-radius: 3px;
    padding: 5px 15px;
    color: #A4A6B5;
    width: 100%!important;
    box-sizing: border-box;
    border: 1px solid #A4A6B5;
    border-radius: 3px;
    background: transparent;
    margin-top: 5px;
}

.bootstrap-select.btn-group .btn .filter-option {
    display: inline-block;
    width: 100%;
    overflow: hidden;
    line-height: 1.625;
    text-align: left;
    vertical-align: middle;
    color: #001022;
}

input#dateOfBirth {
    color: #001022;
}

.checkbox-inline, .radio-inline {
    position: relative;
    display: inline-block;
    padding-left: 20px;
    margin-bottom: 0;
    font-weight: 400;
    vertical-align: middle;
    cursor: pointer;
	color: #001022;
}


.main_box .login_logo {
    overflow: hidden;
    padding: 10px 25px 15px 25px;
    -moz-border-radius: 4px 4px 0 0;
    -webkit-border-radius: 4px 4px 0 0;
    border-radius: 4px 4px 0 0;
    text-transform: uppercase;
}

.input-group.date.form_time {
    width: 100%;
    border-right: 1px solid #A4A6B5;
    border-radius: 3px;
	background:#fff;
}
    </style>
</head>
<body>
<div class="main_box">
    <div class="login_logo" style="text-align: center">
           <img src='<c:url value="/resources/img/logo.png"/>' >
    </div>
    <div class="login_box">
        <div class="login_logo input_boder">
        <h3>Registers</h3>
          	<h6>Required fields *</h6>
        </div>
        <div class="login_form">
        	<c:url var="url" value="/memberRegister" />
            <form id="registerForm" method="post" action='${url }' data-form-token="${TokenUtil.get(pageContext)}"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="registers_table">
  <tr>
    <td width="40%"><label class="control-label"><spring:message code="label.firstname"/> *</label>
        </td>
    <td width="60%"><div class="col-lg-12 input_boder">
                    	<input id="firstName" value="" name="firstName" type="text" class="form-control"></div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.lastname"/> *</label></td>
    <td><div class="col-lg-12 input_boder">
       <input id="lastName" value="" name="lastName" type="text" class="form-control"  >
                    </div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.gender"/> *</label></td>
    <td><div class="col-lg-12">
			         	 <label class="radio-inline"><input type="radio" name="gender" value="FEMALE" checked="checked" /> <spring:message code="label.gender.FEMALE"/></label>
						 <label class="radio-inline"><input type="radio" name="gender" value="MALE"/> <spring:message code="label.gender.MALE"/></label>
	      	</div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.dateofbirth"/> *</label></td>
    <td><div class="col-lg-12 input_boder">
				       	<div class="input-group date form_time">
				        	<input id="dateOfBirth" name="dateOfBirth" class="form-control dateOfBirth" 
				        		value='<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>' size="16">
				            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
				        </div>
	        </div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.email"/> *</label></td>
    <td><div class="col-lg-12 input_boder">
                    	<input id="email" value="" name="email" type="text" class="form-control">
            </div></td>
  </tr>

  <tr>
    <td><label class="control-label"><spring:message code="label.address"/></label></td>
    <td><div class="col-lg-12 input_boder">
			         	<input  type="text" name="addressVO.addressExtention" id="addressVO.addressExtention" class="form-control"/>
	      	</div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.district"/> *</label></td>
    <td><div class="col-lg-12 district-select input_boder">
			        	<input  type="text" name="addressVO.district" id="addressVO.district" class="form-control" value="HK"/>
			            <%-- <ex:tree tree="${districtData}" name="addressVO.district" selectable="1,2,3"/> --%>
	        </div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.country"/></label></td>
    <td><div class="col-lg-12 input_boder">
			         	<input  type="text" name="addressVO.country" id="addressVO.country" class="form-control"/>
			      	</div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.mobile.phone"/> *</label></td>
    <td><div class="col-lg-12 input_boder">
			         	<input  type="text" name="mobilePhone" id="mobilePhone" class="form-control"/>
			      	</div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.home.phone"/></label></td>
    <td><div class="col-lg-12 input_boder">
			         	<input  type="text" name="homePhone" id="homePhone" class="form-control"/>
			      	</div></td>
  </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.shop"/> *</label></td>
    <td class="input_boder"> <select name="shopId" class="selectpicker form-control ">
                        <c:forEach items="${shopList}" var="item">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select></td>
  </tr>
       <tr>
             <td><label class="control-label"><spring:message code="label.password"/> *</label></td>
              <td><div class="col-lg-12 input_boder">
                   <input id="password" value="" name="password" type="password" class="form-control">
              </div></td>
      </tr>
                <tr>
                    <td><label class="control-label"><spring:message code="label.confirm.password"/>*</label></td>
                    <td><div class="col-lg-12 input_boder">
                        <input id="confirmPassword" value="" name="confirmPassword" type="password" class="form-control">
                    </div></td>
                </tr>
  <tr>
    <td><label class="control-label"><spring:message code="label.notification"/></label></td>
    <td><div class="col-lg-12">
			      		 <label class="radio-inline" style="padding-left:0;"><input type="checkbox" name="notification" value="EMAIL" checked="checked"/> Email</label>
			      		<%-- <label class="radio-inline" style="padding-left:0;"><input type="checkbox" name="notification" value="SMS"/> SMS</label>--%>
			      	</div></td>
  </tr>
                <tr>
                    <td><label class="control-label"><spring:message code="label.member.register.optOut"/>：</label></td>
                    <td><div class="col-lg-12">
                        <label class="radio-inline" style="padding-left:0;"><input type="checkbox" name="optedOut" value="1" checked="checked"/> <samp>Sign up for newsletter to know about the latest offers, new treatments or new products</samp></label>
                        <%-- <label class="radio-inline" style="padding-left:0;"><input type="checkbox" name="notification" value="SMS"/> SMS</label>--%>
                    </div></td>
                </tr>
</table>
			  	<div id="error_msg"></div>
              	<div class="modal-footer">
        			<div class="bootstrap-dialog-footer">
	                    <button type="button" class="btn btn-primary" id="registerBtn">
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
    <br>
</div>
<script src='<c:url value="/resources/js/base/jquery-1.11.3.min.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/jqueryui/jquery-ui.min.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/bootstrap-dialog/bootstrap-dialog.min.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/jquery-contextMenu/jquery.contextMenu.min.js"/>?v=${version}'></script>

<!-- Metis Menu Plugin JavaScript -->
<script src='<c:url value="/resources/js/metisMenu/metisMenu.min.js"/>?v=${version}'></script>
<!-- multi-select -->
<script src='<c:url value="/resources/js/multi-select/jquery.multi-select.js"/>?v=${version}'></script>

<!-- bootstrapvalidator-0.4.5 -->
<script src='<c:url value="/resources/js/bootstrapvalidator-0.4.5/bootstrapValidator.js"/>?v=${version}'></script>

<!-- jquery.datetimepicker -->
<script src='<c:url value="/resources/js/jquery.datetimepicker/jquery.datetimepicker.full.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/boootstrap-datepicker/js/bootstrap-datepicker.min.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/boootstrap-datepicker/locales/bootstrap-datepicker.en-GB.min.js"/>?v=${version}'></script>

<%-- bootstrap-filestyle --%>
<script src='<c:url value="/resources/js/bootstrap-filestyle/bootstrap-filestyle.min.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/jquery-loadmask/jquery.loadmask.js"/>?v=${version}'></script>

<!--ztree -->
<script src='<c:url value="/resources/js/ztree/js/jquery.ztree.all.min.js"/>?v=${version}'></script>

<!-- bootstrap modal -->

<script src='<c:url value="/resources/js/jquery-treetable/jquery.treetable.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/boostrap-select/js/bootstrap-select.js"/>?v=${version}'></script>

<script type="text/javascript" src='<c:url value="/resources/js/bootstrap-submenu/js/bootstrap-submenu.js"/>?v=${version}'></script>
<%--<script type="text/javascript" src='<c:url value="/resources/js/bootstrap-submenu/js/docs.js"/>?v=${version}'></script>--%>

<!-- dialog -->
<script src='<c:url value="/resources/js/base/common.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/base/dialog.js"/>?v=${version}'></script>
<script src='<c:url value="/resources/js/base/form-page.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/base/jquery.select-category.js"/>?v=${version}'></script>

<script src='<c:url value="/resources/js/base/json2.js"/>?v=${version}'></script>

<script type="text/javascript">
$(document).ready(function () {

	$('#registerForm').bootstrapValidator({
		message: '<spring:message code="label.errors.is.not.valid"/>',
	    feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	    },
	    fields: {

	        email: {
	        	message: '<spring:message code="label.errors.is.not.valid"/>',
	            validators: {
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
    
    $('#registerBtn').click(function () {
    	var form=$('#registerForm');
    	var bootstrapValidator = form.data('bootstrapValidator');
        if($("#firstName").val()==''){
            var  fieldErrors2='<spring:message code="label.errors.required.fields"/>' ;
            $('#error_msg').html(fieldErrors2);
            return;
        }
        if($("#lastName").val()==''){
            var  fieldErrors2='<spring:message code="label.errors.required.fields"/>' ;
            $('#error_msg').html(fieldErrors2);
            return;
        }

        if($("#dateOfBirth").val()==''){
            var  fieldErrors2='<spring:message code="label.errors.required.fields"/>' ;
            $('#error_msg').html(fieldErrors2);
            return;
        }

        if($("#mobilePhone").val()==''){
            var  fieldErrors2='<spring:message code="label.errors.required.fields"/>' ;
            $('#error_msg').html(fieldErrors2);
            return;
        }

        if($("#password").val()==''){
            var  fieldErrors2='<spring:message code="label.errors.required.fields"/>' ;
            $('#error_msg').html(fieldErrors);
            return;
        }
        if($("#confirmPassword").val()==''){
            var  fieldErrors2='<spring:message code="label.errors.required.fields"/>' ;
            $('#error_msg').html(fieldErrors2);
            return;
        }

        if($("#password").val()!=$("#confirmPassword").val()){
            var  fieldErrors2='<spring:message code="label.errors.password.mismatch"/>';
            $('#error_msg').html(fieldErrors2);
            return;
        }

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
	        url: '<c:url value="/memberRegister"/>',
	        type: "POST",
	        dataType: "json",
	        data: form.serialize(),
	        success: function(json) {
	        	switch (json.statusCode) {
		            case 200:
		            	window.location.href='<c:url value="/memberRegisterSuccess"/>';
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
</body>
</html>