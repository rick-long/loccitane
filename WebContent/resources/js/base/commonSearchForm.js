
//异步加载搜索内容
function commonSearchForm(obj) {
    var pDiv = $(obj).parents(".management");
    var form = pDiv.find("#commonSearchForm");
    pDiv.mask("Loading ... ");
    $.ajax({
        url: form.attr('action'),
        type: "post",
        dataType: "text",
        data: form.serialize(),
        success: function (response) {
            pDiv.find("#pageList").html(response);
        }
    }).always(function () {
        pDiv.unmask();
    });
}
//级联查询
function categoryCasecade(currDomId,casecadeDomId,casecadeUrl,initVal){
	currDomId.on("change",function(){
		var casecadeParameter=$(this).val();
		categoryCasecadeAjax(casecadeParameter, casecadeDomId, casecadeUrl,initVal);
	})
	var casecadeParameter=currDomId.val();
	categoryCasecadeAjax(casecadeParameter, casecadeDomId, casecadeUrl,initVal);
}

function categoryCasecadeAjax(casecadeParameter,casecadeDomId,casecadeUrl,initVal){
	$.ajax({
		 url:casecadeUrl,
		 type: "post",
		 dataType: "json",
		 data: {casecadeParameter:casecadeParameter},
		 success: function(data) {
			 casecadeDomId[0].innerHTML = "";
			 casecadeDomId.append('<option value="">Please select</option>');
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