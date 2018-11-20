<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3>
        <spring:message code="label.commission.rule.search.management"/>
        <a data-permission="commission:commissionRuleToAdd" href='<c:url value="/commission/commissionRuleToAdd"/>' class="btn btn-primary dialog" data-width="680" data-reload-btn="a.search-btn" data-title='<spring:message code="label.commission.rule.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/commission/commissionRuleList"/>
    <form:form id="commonSearchForm" modelAttribute="commissionRuleListVO" method="post" class="form-inline" action="${url}">
	     <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
			    <form:select id="commissionTemplateId" path="commissionTemplateId" class="selectpicker form-control">
			                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
			                    <c:forEach items="${commissionTemplateList}" var="item">
			                        <form:option value="${item.id}">${item.name}</form:option>
			                    </c:forEach>
			                </form:select>
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
			        
			 <div class="col-lg-3 col-sm-6">
       <div class="form-group col-md-12">
          	<label class="control-label"><spring:message code="label.treatment.tree"/></label>
			<div class="select-category" data-selectable="category,product" data-root-id="1"></div>
	   </div>
	   </div>
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
    $(function () {
        $('.select-category').selectCategory({});
    });
</script>