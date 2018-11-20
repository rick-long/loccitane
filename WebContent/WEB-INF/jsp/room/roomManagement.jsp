<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div id="room-add" class="management">
    <h3><spring:message code="label.room.search.management"/>
        <a data-permission="room:toAdd" href='<c:url value="/room/toAdd"/>' class="btn btn-primary dialog" data-title='<spring:message code="label.room.add.management"/>' data-width="650">
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/room/list"/>
    <form:form modelAttribute="roomListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
   <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
           	<select name="shopId" class="selectpicker form-control">
               		<option value=""><spring:message code="label.option.select.all"/></option>
                    <c:forEach items="${shopList}" var="item">
                        <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>> ${item.name}</option>
                    </c:forEach>
                </select>
            </div>
            </div>
            
           <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.name"/></label>
           <form:input path="name" id="name" class="form-control"/>
            </div></div>
           <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.isactive"/></label>
          <form:select class="selectpicker form-control" path="isActive" id="isActive">
                	<form:option value="true"><spring:message code="label.option.yes"/></form:option>
                	<form:option value=""><spring:message code="label.option.select.all"/></form:option>
                    <form:option value="false"><spring:message code="label.option.no"/></form:option>
                </form:select>
            </div> </div>
           <div class="col-lg-12 col-sm-6">
                <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn"><i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/></a> </div>
                </div>
                </div>
                </div>
    	
    </form:form>
    <div id="pageList">

    </div>
</div>

<!-- common search form start -->
<%--<script type="text/javascript">
	
    $(document).ready(function () {
        var parent = $('#room-add');

        $('#add', parent).click(function () {
            BootstrapDialog.show({
                title: '<spring:message code="label.room.add.management"/>',
                message: $('<div/>').load('<c:url value="/room/toAdd"/>'),
                buttons: [
                    {
                        id: 'submit',
                        label: '<spring:message code="label.button.submit"/>'
                    }
                ]
            });
        });
    });
</script>--%>
