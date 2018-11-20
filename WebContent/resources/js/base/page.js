function queryPage(pageUrl,pageNum){
	var curQueryString=$("#curQueryString").val();
	var obj = JSON.parse(curQueryString);
	obj.pageNumber = pageNum;
	$.ajax({
		 url:pageUrl,
		 type: "post",
		 dataType: "text",
		 data:obj,
		 success: function(response) {
			$("#pageList", Dialog.getContext()).html(response);
		 },
		 error:function(){}
	});
}

function page(pageNumber){
	commonSearchForm('<c:url value="/supplier/list"/>',$("div#supplier-search"),pageNumber);
}


$(function(){
    // 计算page的宽度
    var parent = getContext();
    $('.page', parent).width(parent.width());
});