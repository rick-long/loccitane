<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<c:if test="${pokList !=null && pokList.size()>0}">
<div class="po" id="po-add">
   <!-- form: -->
	<section>
	     <div class="col-lg-6 col-lg-offset-0">
	         <form:form modelAttribute="poAddVO" id="poAddVOForm" method="post" class="form-horizontal" action=''>
	             <table id="po_item_list" style="border-bottom: 1px solid #efefef;">
		             <c:forEach items="poItemList" var="poItem" varStatus="idx">
						<tr>
							<td>
								<c:set var="index" value="${idx.index }" scope="request"/>
								<%@ include file="/WEB-INF/jsp/product/productOptionItem.jsp"%>
							</td>
						</tr>
					</c:forEach>
				 </table>
				 
				 <form:hidden id="productId" path="productId" value="${productId}"/>
				 
                 <div class="form-group">
                     <div class="col-lg-9 col-lg-offset-3">
                         <button type="button" class="btn btn-primary" id="submitBtn"><spring:message code="label.button.save"/></button>
                	 </div>
	            </div>
	         </form:form>
	     </div>
	</section>
</div>
</c:if>
<script type="text/javascript">
//button submit
var poDiv=$("div#po-add");
$($('#submitBtn',poDiv)).click(function() {

	 $.ajax({
           url: '<c:url value="/product/addPo" />',
           type: "POST",
           data: $('#poAddVOForm',poDiv).serialize(),
           success: function(responseText) {
	          	if(responseText.statusCode == '200') {
	          	     alert(responseText.message);
	          	     //$('#submitBtn',poDiv).attr("disabled","disabled");
	          	   	gotoRight('<c:url value="/product" />');
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