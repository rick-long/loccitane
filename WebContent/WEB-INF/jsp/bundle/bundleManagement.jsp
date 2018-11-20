<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<style>
    #shopId{
        width:100%;
        height:30px;
    }
</style>
<div class="management">
    <h3>
        <spring:message code="label.bundle.search"/>
        <a data-permission="bundle:bundleToAdd" href='<c:url value="/bundle/bundleToAdd"/>' class="btn btn-primary dialog" data-width="680" data-reload-btn="a.search-btn" data-title='<spring:message code="label.bundle.add"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/bundle/bundleList"/>
    <form:form id="commonSearchForm" modelAttribute="bundleListVO" method="post" class="form-inline" action="${url}">
        <div class="row">
            <div class="col-sm-12">
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
                        <select id="shopId" name="shopId" cssClass="selectpicker form-control">
                            <option value=""><spring:message code="label.option.select.all"/></option>
                            <c:forEach items="${shopList}" var="item">
                                <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.start.time"/> </label>
                        <div class="input-group date form_time">
                            <input id="startTime" name="startTime" class="form-control startTime"
                                   value="" size="16">
                            <span class="input-group-addon" id="startTimeSpan"><span class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.end.time"/> </label>
                        <div class="input-group date form_time">
                            <input id="endTime" name="endTime" class="form-control endTime"
                                   value="" size="16">
                            <span class="input-group-addon" id="endTimeSpan"><span class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.code"/></label>
                        <form:input path="code" id="code" class="form-control"/>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.isactive"/></label>
                        <form:select class="selectpicker form-control" path="isActive" id="isActive">
                            <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                            <form:option value=""><spring:message code="label.option.select.single"/></form:option>
                            <form:option value="false"><spring:message code="label.option.no"/></form:option>
                        </form:select>
                    </div>
                </div>
                <%--<div class="col-lg-3 col-sm-6">--%>
                    <%--<div class="form-group col-md-12">--%>
                        <%--<label class="control-label"><spring:message code="label.treatment.tree"/></label>--%>
                        <%--<div class="select-category" data-selectable="category,product" data-root-id="1"></div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="col-lg-12 col-sm-6">
                    <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                        <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/>
                    </a>
                    </div>
                </div>
            </div>
        </div>

    </form:form>
    <div id="pageList">
    </div>
</div>

<script type="text/javascript">
    $('#endTime').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('.input-group-addon').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });

    $('#startTime').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#startTimeSpan').click(function () {
        var input = $(this).siblings('input').trigger('focus');
    });

    $('.select-category').selectCategory({});
</script>