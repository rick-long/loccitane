<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<div class="container" id="product-edit">
	<!-- Nav tabs -->
 	<ul class="nav nav-tabs" id="myTabs">
        <li class="active"><a href="#master" role="tab" data-url='' data-tabname="master" data-toggle="tab"><spring:message code="label.product.master"/> </a></li>
        <li class=""><a href="#po" role="tab" data-url='<c:url value="/product/toEditPo?productId=${productEditVO.id }"/>'  data-tabname="po" data-toggle="tab"><spring:message code="label.product.option"/> </a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content" id="myTabContent">
   		<div role="tabpanel" class="tab-pane active" id="master">
        	<%@ include file="/WEB-INF/jsp/product/edit/productMasterEdit.jsp"%>
    	</div>
    	<div role="tabpanel" class="tab-pane" id="po">
    	
    	</div>
	</div>
</div>

<script type="text/javascript">

$(function() {
		$('#myTabs a:first').tab('show');//初始化显示哪个tab

       $('#myTabs a').click(function(e) {
           e.preventDefault();//阻止a链接的跳转行为
           var tabname =$(this).attr("data-tabname");
           if(tabname !='master'){
        	   var url = $(this).attr("data-url");
               $.ajax({
                   url: url,
                   type: "POST",
                   dataType: "text",
                   success: function(response) {
                	   $('#'+tabname).html(response);
                   }
              });
           }
           $(this).tab('show');//显示当前选中的链接及关联的content
           
       })
   })
</script>