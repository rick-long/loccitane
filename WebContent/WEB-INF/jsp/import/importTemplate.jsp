<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="big-title-area container">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="text-h1-white"><spring:message code="label.import.${importDemoVO.module}"/></h1>
        </div>
    </div>
</div>
<div class="management container import-form">
    <form data-form-token="${TokenUtil.get(pageContext)}" id="defaultForm"
          enctype="multipart/form-data" method="post" class="form-horizontal" action="/importDemo/doImport">
        <div class="row">
            <div class="col-lg-12">
                <div class="form-group col-lg-3 col-sm-12">
                    <label class="control-label text-right"><spring:message code="lable.prepaid.excel.file"/> </label>
                </div>
                <div class="form-group col-lg-6 col-sm-12">
                    <input type="file" id="importFile" name="importFile" accept=".xls"/>
                    <input type="hidden" id="module" name="module" value="${importDemoVO.module }"/>
                </div>
                <div class="form-group col-lg-3 col-sm-12">
                    <%--<a href="javascript:;" onclick="importDemo();" class="btn btn-default">
                     <spring:message code="label.button.import"/>
                 </a>--%>
                    <input type="submit" class="btn btn-default"  onclick="return func()"  value="<spring:message code="label.button.import"/>">
                </div>
            </div>
            <div class="col-lg-6 col-lg-offset-3 col-xs-offset"><div id="verify"></div></div>
            
        </div>

    </form>
</div>
<script type="text/javascript">


    $(document).ready(function () {

        $(":file", getContext()).filestyle({
            buttonText: '',
            placeholder: 'Choose File'
        });


    });

    var form = $('form', getContext());

    function func(){
        var str = $("#importFile").val();
        console.log(str)
        if(!str){
            $("#verify").html("<font>Please upload excel file</font>");
            return false;
        }
        var fileExt = str.substring(str.lastIndexOf('.') + 1);
        if(fileExt == 'xls'){
            return true;
        }else{
            $("#verify").val("Please upload excel file");
            return false;
        }
    }


    function importDemo() {
        var formData = new FormData(form[0]);
        $.ajax({
            url: '<c:url value="/doImport"/>',
            type: 'POST',
            data: formData,
            async: false,
            success: function (res) {
                if (res.message != undefined) {
                    BootstrapDialog.alert(res.message);
                }
            },
            cache: false,
            contentType: false,
            processData: false
        });
    }
</script>
 