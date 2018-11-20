<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style type="text/css">

    .management {
        min-height: 500px;
    }
</style>
<div class="management">
    <h3>Test Page</h3>
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-lg-4 control-label">Time Multi Select Menu:</label>
            <div class="col-lg-5 time-select multi-select">
                <ex:tree name="timeId" tree="${timeTreeList}" clazz="startTime"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label">District List:</label>
            <div class="col-lg-5 multi-select">
                <ex:tree name="districtId" tree="${districtList}" selectable="1,2,3"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label">Category:</label>
            <div class="col-lg-5 multi-select">
                <input type="text" class="show form-control " value="" readonly="">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">

    $(function () {

        // 初始化menu
        $('.multi-select', getContext()).multiMenu();
        //$('#districtId', getContext()).multiMenu();

    });
</script>

