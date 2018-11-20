
$('.submitBtn').click(function() {
	var pDiv=$(this).parents(".container");
	var form=pDiv.find("#defaultForm");
	var valid= form.data("bootstrapValidator");
//	if(valid){
		 var url=form.attr('action');
		 $.ajax({
             url: url,
             type: "POST",
             data: form.serialize(),
             success: function(responseText) {
            	 serviceValidateCallBack(responseText);
             }
         });
//	}else{
//		alert('faild');
//	}
});

function serviceValidateCallBack(json,myModal){
	if (json.statusCode == '300' && json.errorFields.length > 0) {
        // 验证错误处理
        $.each(json.errorFields, function (index, item) {
        	
            //var element = form.find("[name=" + item.fieldName + "]");
            //获取当前对象的上上级的class 变为form-group has-feedback has-error
            //element.parentNode.parentNode.attr("class","form-group has-feedback has-error");
            //获取当前对象的同级<i 的class变为form-control-feedback glyphicon glyphicon-remove
            //
            //替换当前对象的同级的的small为：<small class="help-block" style="display: block;">item.errorMessage</small>
            //
            alert('error:'+item.fieldName+'-----'+item.errorMessage);
        });
    } else if (json.statusCode == '200') {
        alert(json.message);
        $("#myModal").modal('hide');
        $("#right-content").find(".search-btn").trigger("click");
    }
}
$('.resetBtn').click(function() {
	var form=$(this).find("#defaultForm");
	form.data('bootstrapValidator').resetForm(true);
});