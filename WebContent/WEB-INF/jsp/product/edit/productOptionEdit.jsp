<%@page import="org.spa.vo.product.PoItemEditVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<div class="po" id="po-edit">
   <!-- form: -->
	<section>
	     <div class="col-lg-6 col-lg-offset-0">
	         <form:form modelAttribute="poEditVO" id="poEditVOForm" method="post" class="form-horizontal" action=''>
	             <table id="po_item_list" style="border-bottom: 1px solid #efefef;">
		             <c:forEach items="${poEditVO.poItemList}" var="poItem" varStatus="idx">
						<tr>
							<td>
								<c:set var="index" value="${idx.index }" scope="request"/>
								<%@ include file="/WEB-INF/jsp/product/edit/productOptionItemEdit.jsp"%>
							</td>
						</tr>
					</c:forEach>
				 </table>
				 
				 <form:hidden id="productId" path="productId" value="${poEditVO.productId}"/>
				 
                 <div class="form-group">
                     <div class="col-lg-9 col-lg-offset-3">
                         <button type="button" class="btn btn-primary" id="submitBtn"><spring:message code="label.button.save"/></button>
                	 </div>
	            </div>
	         </form:form>
	     </div>
	</section>
</div>
<script type="text/javascript">
//button submit
var poDiv=$("div#po-edit");
$($('#submitBtn',poDiv)).click(function() {

	 $.ajax({
           url: '<c:url value="/product/editPo" />',
           type: "POST",
           data: $('#poEditVOForm',poDiv).serialize(),
           success: function(responseText) {
	          	if(responseText.statusCode == '200') {
	          	     alert(responseText.message);
	          	}
           }
	  });
});

//
function addPoOnkeyDown(event,index){
	var productId=$('#productId',poDiv).val();
	$.ajax({
   		url:'<c:url value="/product/toAddPoItem" />',
   		data:{index:index,productId:productId},
   		type:'post',
   		success:function(data){
      		$('#po_item_list',poDiv).append('<tr><td>' + data + '</td></tr>');
  		}
	});
    populateIndex(index,true);
}

function populateIndex(index,show){
    if(show){
   	 	 var documentEleRemain=$("#add_item_"+index);
		 documentEleRemain[0].style.display='none';
    }else{
		index='<c:out value='${index}'/>';
		var classDom=$("td input[class='add_item_class']");
		for(var i=0 ; i < classDom.length ; i++){
			if(i != index){
				var documentEleRemain=$("#add_item_"+(i+1));
    			 documentEleRemain[0].style.display='none';
			}
		}
    }	
}

populateIndex('',false);

</script>