<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style type="text/css">

    .management {
        min-height: 700px;
    }
</style>
Select Category Version 2:

<div class="management">
    <h3>Select Category 2</h3>
    <form class="form-horizontal">
       <%-- <div class="form-group">
            <label class="col-lg-1 control-label">Select Category2:</label>
            <div class="col-lg-2">
                <div class="select-category2" data-selectable="option" data-root-id="2"></div>
            </div>
        </div>--%>


        <div class="form-group">
            <label class="col-lg-1 control-label">选择Category:</label>
            <div class="col-lg-5">
                <div class="select-category2" data-selectable="category" data-category-id="2"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-1 control-label">选择product:</label>
            <div class="col-lg-5">
                <div class="select-category2" data-selectable="product"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-1 control-label">选择option:</label>
            <div class="col-lg-5">
                <div class="select-category2" data-selectable="option"></div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-1 control-label">选择Category 和 product:</label>
            <div class="col-lg-5">
                <div class="select-category2" data-selectable="category,product"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-1 control-label">从treatments开始初始化</label>
            <div class="col-lg-5">
                <div class="select-category2" data-selectable="category" data-root-id="2"></div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">

    $(function () {

        $('.select-category2').selectCategory({
            callback: function (context) {
                context.find('input[name=displayName]').change(function () {
                    console.info($(this).val());
                });
            }
        });

    });
</script>

