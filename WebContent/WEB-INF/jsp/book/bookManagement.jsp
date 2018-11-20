<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div id="bookManagement" class="management">
    <h3><spring:message code="label.book.search.management"/>
        <a data-permission="book:toAdd" href='<c:url value="/book/toAdd"/>?forward=bookListMenu' class="btn btn-primary form-page" data-width="680" data-reload-btn="a.search-btn" data-title='<spring:message code="label.book.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/book/list"/>
    <form:form id="commonSearchForm" modelAttribute="bookListVO" method="post" class="form-inline" action="${url}">
    <div class="row">
      <div class="col-sm-12">
       <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.treatment"/></label>
          <form:input path="treatmentName" class="form-control"/>  
           </div>
          </div>

       <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
           <label class="control-label"><spring:message code="label.therapist"/></label>
        <form:input path="therapistName" class="form-control"/>
           </div>
          </div>
    
      <div class="col-lg-3 col-sm-6">
         <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.client"/></label>
          <div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
          <form:hidden path="memberId" id="memberId" class="form-control"/>
           <input type="hidden" name="username" id="username" class="form-control"/>
            <input type="text" name="fullName" id="fullName" class="form-control quick-search" readonly/>
           <span class="input-group-addon">
            <span class="glyphicon glyphicon-search"></span>
              </span>
         
         </div>
        </div>
       </div>
		<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
           <label class="control-label"><spring:message code="label.book.channel"/></label>
          <form:select id="bookingChannel" path="bookingChannel" class="form-control selectpicker">
           <form:option value=""><spring:message code="label.option.select.all"/></form:option>
           <form:option value="STAFF"><spring:message code="label.book.channel.STAFF"/></form:option>
           <form:option value="ONLINE"><spring:message code="label.book.channel.ONLINE"/></form:option>
           <form:option value="MOBILE"><spring:message code="label.book.channel.MOBILE"/></form:option>
            </form:select>
            </div>
           </div>
       <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
           <label class="control-label"><spring:message code="label.book.item.status"/></label>
          <form:select id="status" path="status" class="form-control selectpicker">
           <form:option value=""><spring:message code="label.option.select.all"/></form:option>
           <form:option value="PENDING"><spring:message code="label.book.status.PENDING"/></form:option>
           <form:option value="CONFIRM"><spring:message code="label.book.status.CONFIRM"/></form:option>
           <form:option value="CHECKIN_SERVICE"><spring:message code="label.book.status.CHECKIN_SERVICE"/></form:option>
            <form:option value="COMPLETE"><spring:message code="label.book.status.COMPLETE"/></form:option>
            <form:option value="NOT_SHOW"><spring:message code="label.book.status.NOT_SHOW"/></form:option>
            <form:option value="CANCEL"><spring:message code="label.book.status.CANCEL"/></form:option>
            <form:option value="WAITING"><spring:message code="label.book.status.WAITING"/></form:option>
            </form:select>
            </div>
           <input type="hidden" name="flag" id="flag" value="${flag}"/>

       </div>
           
      <div class="col-lg-3 col-sm-6">
         <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.prepaid.from.date"/> </label>
          <div class="input-group date form_time">
              <input id="fromDate" name="fromDate" class="form-control" value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
             <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
            </div>
          </div> 
        </div>
                     
       <div class="col-lg-3 col-sm-6">
           <div class="form-group col-md-12">
             <label class="control-label"><spring:message code="label.prepaid.to.date"/> </label>
             <div class="input-group date form_time">
                            <input id="toDate" name="toDate" class="form-control"
                                   value='<fmt:formatDate value="${toDate}" pattern="yyyy-MM-dd"/>' size="16">
                            <span class="input-group-addon" id="toDateSpan"><span class="glyphicon glyphicon-time"></span></span>
               </div>
              </div>
            </div> 
            
        <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
             <label class="control-label"><spring:message code="label.shop"/></label>
		     <select id="shopId" name="shopId" class="form-control selectpicker">
		      <option value=""><spring:message code="label.option.select.single"/></option>
		      <c:forEach items="${shopList}" var="item">
		       <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
		        </c:forEach>
		      </select>
		    </div>
          </div>
          
		     <div class="col-lg-6 col-sm-6">
                <div class="form-group col-md-12">
                   <a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
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
    $(function() {
        $('#fromDate').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('#fromDate').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
        
        $('#toDate').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('#toDateSpan').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
    });

    $(document).ready(function(){
        var flag = $("#flag").val();
        if (flag != '' && flag == 1) {
            $("#status").val("WAITING");
        }
    });
</script>