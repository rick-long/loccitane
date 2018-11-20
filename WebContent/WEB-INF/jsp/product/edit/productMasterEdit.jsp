<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>


<div class="row" id="master-edit">
	<!-- form: -->
    <section>
    	<div class="col-lg-6 col-lg-offset-0">
            <form:form modelAttribute="productEditVO" id="masterForm" method="post" class="form-horizontal" action=''>
                 <fieldset>
                 <legend><spring:message code="label.product.details"/></legend>
                	<div class="form-group">
                        <label class="col-lg-4 control-label"><spring:message code="label.name"/></label>
                        <div class="col-lg-5">
                           <form:input path="name" id="name" class="form-control"/>
                        </div>
                    </div>
                   	<div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.brand"/></label>
					    <div class="col-lg-5">
					       	<form:select class="selectpicker form-control" path="brandId" id="brandId">
					            <c:forEach var="brand" items="${brandList }">
					            	<form:option value="${brand.id }">${brand.name }</form:option>
					            </c:forEach>
					         </form:select>
						</div>
					</div>
                    <div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.supplier"/></label>
					    <div class="col-lg-5">
					       	<form:select class="selectpicker form-control" path="supplierId" id="supplierId">
					            <c:forEach var="supplier" items="${supplierList }">
					            	<form:option value="${supplier.id }">${supplier.name }</form:option>
					            </c:forEach>
					         </form:select>
						</div>
					</div>
                    <div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.parent.category"/></label>
					    <div class="col-lg-5">
					       	<form:select class="selectpicker form-control" path="parentCategoryId" id="parentCategoryId">
					            <c:forEach var="parCategory" items="${parentCategoryList }">
					            	<form:option value="${parCategory.id }">${parCategory.name }</form:option>
					            </c:forEach>
					         </form:select>
						</div>
					</div>
				   <div class="form-group">
						<label class="col-lg-4 control-label"><spring:message code="label.sub.category"/></label>
					    <div class="col-lg-5">
					    	<form:select class="selectpicker form-control" path="secondCategoryId" id="secondCategoryId">
					        </form:select>
					        <input type="hidden" id="secondCategoryIdTemp" value="${productEditVO.secondCategoryId }"/>
						</div>
					</div>
					</fieldset>
					<div id="productdescriptions">
					
					</div>
                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <button type="button" class="btn btn-primary" id="submitBtn"><spring:message code="label.button.save"/></button>
                            <form:hidden path="id" id="id" />
                        </div>
                    </div>
                </form:form>
            </div>
        </section>
</div>

<script type="text/javascript">

var masterDiv=$("div#master-edit");

$(document).ready(function() {
	
   $('#masterForm',masterDiv).bootstrapValidator({
    	message: '<spring:message code="label.errors.is.not.valid"/>',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
            	message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                    	message: '<spring:message code="label.errors.is.required"/>'
                    }
                }
            },
            secondCategoryId: {
            	message: '<spring:message code="label.errors.is.not.valid"/>',
                validators: {
                    notEmpty: {
                    	message: '<spring:message code="label.errors.is.required"/>'
                    }
                }
            }
        }
    })
    
	//category casecade select
	var pCategoryChangeUrl = "<%=request.getContextPath()+"/category/getSubCat"%>";
	var secondCategoryIdTemp=$('#secondCategoryIdTemp',masterDiv).val();
	categorysCasecade($('#parentCategoryId',masterDiv),$('#secondCategoryId',masterDiv),pCategoryChangeUrl,secondCategoryIdTemp);

});



	//级联查询
	function categorysCasecade(currDomId,casecadeDomId,casecadeUrl,initVal){
		currDomId.on("change",function(){
			var casecadeParameter=$(this).val();
			subCategoryCasecade(casecadeParameter, casecadeDomId, casecadeUrl,initVal);
		})
		casecadeDomId.on("change",function(){
			var casecadeParameter=$(this).val();
			pdCasecade(casecadeParameter, casecadeDomId,initVal);
		})
		var casecadeParameter=currDomId.val();
		subCategoryCasecade(casecadeParameter, casecadeDomId, casecadeUrl,initVal);
		
		var secondCategoryIdTemp=$('#secondCategoryIdTemp',masterDiv).val();
		pdCasecade(secondCategoryIdTemp, casecadeDomId,initVal);
	}

	function subCategoryCasecade(casecadeParameter,casecadeDomId,casecadeUrl,initVal){
		$.ajax({
			 url:casecadeUrl,
			 type: "post",
			 dataType: "json",
			 data: {casecadeParameter:casecadeParameter},
			 success: function(data) {
				 casecadeDomId[0].innerHTML = "";
				 casecadeDomId.append('<option value="">All</option>');
				 for (var label in data) {
					 casecadeDomId.append("<option value=" + label + ">" +data[label] + "</option>");
	            }
				if(initVal != undefined){
					casecadeDomId.val(initVal);
				}
				
			 },
			 error:function(){}
		});
	}
	function pdCasecade(casecadeParameter,casecadeDomId,initVal){
		$.ajax({
			 url:'<c:url value="/product/listPdForEdit"/>',
			 type: "post",
			 dataType: "text",
			 data: {secondCategoryId:casecadeParameter,id:$('#id',masterDiv).val()},
			 success: function(data) {
				 $('#productdescriptions',masterDiv).html(data);
			 },
			 error:function(){}
		});
	}
	
	
	$($('#submitBtn',masterDiv)).click(function() {
		var valid= $('#masterForm',masterDiv).data("bootstrapValidator");
		$.ajax({
             url: '<c:url value="/product/edit" />',
             type: "POST",
             data: $('#masterForm',masterDiv).serialize(),
             success: function(responseText) {
            	 serviceValidateCallBack(responseText);
             }
        });
	});

	function serviceValidateCallBack(json){
		if (json.statusCode == '300' && json.errorFields.length > 0) {
	        // 验证错误处理
	        $.each(json.errorFields, function (index, item) {
	        	
	            alert('error:'+item.fieldName+'-----'+item.errorMessage);
	        });
	    } else if (json.statusCode == '200') {
	    	 $.each(json.errorFields, function (index, item) {
	    		 var fname=item.fieldName;
	    		 if(fname =='success'){
	    			 alert(item.errorMessage);
	    			 
	    		 }else if(fname =='productId'){
	    			 $('#myTabs a').each(function(e){
	    				 var tabname =$(this).attr("data-tabname");
	    		         if(tabname =='po'){
	    				 	var url='<c:url value="/product/toEditPo?productId="/>'+item.errorMessage;
	    				 	
   			                $.ajax({
   			                   url: url,
   			                   type: "POST",
   			                   dataType: "text",
   			                   success: function(response) {
   			                	 	var tabPanDiv=$("div#productadd");
 			                	   $('#po',tabPanDiv).html(response);
   			                   }
   			                });
    			            $(this).tab('show');//显示当前选中的链接及关联的content
    			            $('#submitBtn',masterDiv).attr("disabled","disabled");
	    		         }
	    			 });
	    		 }
	    	 });
	        
	    }
	}
	
</script>