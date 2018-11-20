<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="management" id="family-search">
    <h3><spring:message code="label.family.management"/>
        <a href='<c:url value="/member/toFamilyAdd?memberId=${familyVO.memberId}"/>' class="btn btn-primary dialog" data-draggable="true"
           data-title='<spring:message code="label.family.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/></a>
    </h3>
    <c:url var="url" value="/member/familyList"/>
    <form:form modelAttribute="familyVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
        <form:hidden path="memberId"/>
                    <div class='row'>
                        <div class="col-sm-12">
                            <div class="col-lg-3 col-sm-6">
                            <div class="form-group col-md-12">
                            <label class="control-label"><spring:message code="label.name"/></label>

                            <form:input path="name" id="name" class="form-control"/>
                            </div>
                            </div>
                            <div class="col-lg-12 col-sm-6 ">
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