<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3>Test Page</h3>
    <form class="form-horizontal">
        <%--  <div class="form-group">
              <label class="col-lg-4 control-label">表单重复提交测试:</label>
              <div class="col-lg-5">
                  <a href='<c:url value="/test/testDuplicateSubmit"/>' class="btn btn-primary dialog" data-width="680" data-reload-btn="a.search-btn" data-title='Test'>
                      Open
                  </a>
              </div>
          </div>--%>
        <div class="form-group">
            <label class="col-lg-4 control-label">选择Category:</label>
            <div class="col-lg-5">
                <div class="select-category" data-selectable="category" data-category-id="2"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label">选择product:</label>
            <div class="col-lg-5">
                <div class="select-category" data-selectable="product"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label">选择option:</label>
            <div class="col-lg-5">
                <div class="select-category" data-selectable="option"></div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label">选择Category 和 product:</label>
            <div class="col-lg-5">
                <div class="select-category" data-selectable="category,product"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label"> product 和 productOption</label>
            <div class="col-lg-5">
                <div class="select-category" data-selectable="product,option" data-product-id="1"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label">选择Category 和 product 和 productOption</label>
            <div class="col-lg-5">
                <div class="select-category" data-selectable="category,product,option" data-product-option-id="1"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label">从treatments开始初始化</label>
            <div class="col-lg-5">
                <div class="select-category" data-selectable="category" data-root-id="2"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label">multi Menu</label>
            <div class="col-lg-5">
                <input type="button" id="multiMenuPage" value="MultiMenu"/>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
   /* $(function () {
        var parent = getContext();
        $('.select-category', parent).selectCategory({});

        $("#multiMenuPage", parent).click(function(){
            $.post('<c:url value="/test/testMultiMenu"/>', function(res){
                getContext().html(res);
            });
        });
    });*/
</script>

