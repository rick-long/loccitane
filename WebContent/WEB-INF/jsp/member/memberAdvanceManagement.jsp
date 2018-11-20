<%@ page import="org.joda.time.DateTime" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="management" id="member-search">
    <h3><spring:message code="label.member.search.management"/></h3>
    <c:url var="url" value="/member/advanceList"/>
    <form:form modelAttribute="memberListVO" id="commonSearchForm" method="post" class="form-inline"
               action="${url}">
        <input id="searchType" type="hidden" name="searchType" value="VISIT">
        <table border="0" cellspacing="0" cellpadding="0" class="management_table">
            <tbody>
            <tr>
                <td colspan="4">
                    <ul class="nav nav-tabs" id="searchTypeTab">
                    	<li class="active"><a href="#spent" data-search-type="SPENT"><spring:message code="label.member.visited"/></a></li>
                        <li><a href="#visited" data-search-type="VISIT"><spring:message code="label.member.visited"/></a></li>
                        <li><a href="#notVisited" data-search-type="NOT_VISIT"><spring:message code="label.member.not.visited"/></a></li>
                    </ul>
                    <div id="allTabContent" class="tab-content">
                    	<div id="spent" class="tab-pane fade in active newprofile">
                            <table class="clientDetails">
                                <tbody>
                                <tr>
                                    <td>

                                        <label class="control-label">
                                            <spring:message code="label.member.date.rang.start"/>
                                        </label>
                                        <div class="input-group col-lg-5">
	                                        <div class="input-group date form_time">
									        	<%--<input id="startDateSpent" name="startDateSpent" class="form-control startDateSpent"--%>
									        		<%--value='<fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd"/>' size="16">--%>
									            <span class="input-group-addon" id="startDateSpentSpan"><span class="glyphicon glyphicon-time"></span></span>
									        </div>
										</div>
                                    </td>
                                    <td>
                                        <label class="control-label">
                                            <spring:message code="label.member.date.rang.end"/>

                                        </label>
                                        <div class="input-group col-lg-5">
	                                        <div class="input-group date form_time">
									        	<%--<input id="endDateSpent" name="endDateSpent" class="form-control endDateSpent"--%>
                                                       <%--value='<fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd"/>' size="16">--%>
									            <span class="input-group-addon" id="endDateSpentSpan"><span class="glyphicon glyphicon-time"></span></span>
									        </div>
										</div>
                                    </td>
                                    <td>
                                        <label class="control-label">
                                           By Shop 
                                        </label>
                                        <div class="input-group col-lg-5">
                                        <select class="selectpicker form-control" name="shopId" id="shopId">
                                        	<option value=""><spring:message code="label.option.select.single"/></option>
                                            <c:forEach items="${shopList}" var="item">
												<option value="${item.id}">${item.name}</option>
											</c:forEach>
                                        </select>
										</div>
                                    </td>
                                    <td>
                                        <label class="control-label">
                                            <spring:message code="label.member.by.category"/>
                                        </label>
                                        <div class="input-group col-lg-5">
                                        	<div class="categoryTreeMenu">
										 		<div class="select-category" data-selectable="category,product,option" data-root-id="1"></div>
											</div>
										</div>
                                    </td>
                                 	<td>
                                        <label class="control-label">
                                            <spring:message code="label.member.mini.spent.amount"/>
                                        </label>
                                        <div class="input-group col-lg-5">
                                        	<input name="miniSpentAmount" id="miniSpentAmount" class="form-control"/>
										</div>
                                    </td>
                                 
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="visited" class="tab-pane fade newprofile">
                            <table class="clientDetails">
                                <tbody>
                                <tr>
                                    <td>

                                        <label class="control-label">
                                            <spring:message code="label.visit.for"/>*
                                        </label>
                                        <div class="input-group col-lg-5">
                                        <select class="selectpicker form-control" name="pastMonth" id="pastMonth">
                                            <option value="1"><spring:message code="label.member.Last.1.Month"/></option>
                                            <option value="2"><spring:message code="label.member.Last.2.Month"/></option>
                                            <option value="3"><spring:message code="label.member.Last.3.Month"/></option>
                                            <option value="4"><spring:message code="label.member.Last.4.Month"/></option>
                                            <option value="5"><spring:message code="label.member.Last.5.Month"/></option>
                                            <option value="6"><spring:message code="label.member.Last.6.Month"/></option>
                                        </select>
										</div>
                                    </td>
                                    <td>

                                        <label class="control-label">
                                            <spring:message code="label.shop"/>*
                                        </label>
                                        <div class="input-group col-lg-5">
                                        <select class="selectpicker form-control" name="shopId" id="shopId">
                                        	<option value=""><spring:message code="label.option.select.single"/></option>
                                            <c:forEach items="${shopList}" var="item">
												<option value="${item.id}">${item.name}</option>
											</c:forEach>
                                        </select>
										</div>
                                    </td>
                                 <td></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                      	<div id="notVisited" class="tab-pane fade newprofile">

                            <table class="clientDetails">
                                <tbody>
                                <tr>
                                    <td>
                                        <%
                                            DateTime dateTime = new DateTime().minusMonths(1);
                                            pageContext.setAttribute("date", dateTime.toDate());
                                        %>

                                        <label class="control-label"><spring:message
                                                code="label.start.date"/></label><div class="input-group date col-lg-5" >
                                        <input id="notVisitStartDate" name="notVisitStartDate"
                                               class="form-control"
                                               value='<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>'
                                               readonly>
                                        <span class="input-group-addon time-toggle">
                                                        <span class="glyphicon glyphicon-time"></span>
                                                    </span>
                                    </div>

                                    </td>
                              
                                    <td> <label class="control-label"> <spring:message code="label.visit.for"/>*</label>
     <div class="input-group col-lg-5">
        <select class="selectpicker form-control" name="pastMonth"
                                                id="pastMonth">
            <option value="1"><spring:message code="label.member.Last.1.Month"/></option>
            <option value="2"><spring:message code="label.member.Last.2.Month"/></option>
            <option value="3"><spring:message code="label.member.Last.3.Month"/></option>
            <option value="4"><spring:message code="label.member.Last.4.Month"/></option>
            <option value="5"><spring:message code="label.member.Last.5.Month"/></option>
            <option value="6"><spring:message code="label.member.Last.6.Month"/></option>
                                        </select>
</div>
                                    </td>
                                    <td>

                                        <label class="control-label">
                                            <spring:message code="label.shop"/>*
                                        </label>
                                        <div class="input-group col-lg-5">
                                        <select class="selectpicker form-control" name="shopId" id="shopId">
                                        	<option value=""><spring:message code="label.option.select.single"/></option>
                                            <c:forEach items="${shopList}" var="item">
												<option value="${item.id}">${item.name}</option>
											</c:forEach>
                                        </select>
										</div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <table border="0" cellspacing="0" cellpadding="0" class="management_table">
                            <tbody>
                            <tr>
                                <td>
                                    <a href="javascript:;" onclick="commonSearchForm(this);"
                                       class="btn btn-default search-btn">
                                        <i class="glyphicon glyphicon-search"></i>
                                        <spring:message code="label.button.search"/>
                                    </a>

                                    <a id="addToGroupBtn" class="btn btn-default" href='javascript:void(0)'>
                                        <spring:message code="label.button.add.to.group"/>
                                    </a>
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form:form>

    <div id="pageList"></div>

</div>
<script type="text/javascript">
    $(function () {
        $('#searchTypeTab').find('a').click(function (e) {
            e.preventDefault();
            var $this = $(this);
            $this.tab('show');
            $('#searchType').val($this.data('search-type'));
            var allTabContent = $('#allTabContent', getContext());
            allTabContent.find('select').prop('disabled', true);
            allTabContent.find('input').prop('disabled', true);
            var currentTab = $($this.attr('href'), getContext());
            console.info(currentTab);
            currentTab.find('select').prop('disabled', false);
            currentTab.find('input').prop('disabled', false);
        });

        console.info($('#searchTypeTab').find('li:eq(0) a'));
        //$('#searchTypeTab').find('li:eq(0) a').trigger('click');

        $('#addToGroupBtn', getContext()).click(function (event) {
            event.preventDefault();
            var options = {
                url: '<c:url value="/member/toGroupAddUser"/>',
                title: '<spring:message code="label.member.group.add.user"/>',
                urlData: $('#commonSearchForm', getContext()).serialize()
            };
            Dialog.create(options);
            return false;
        });

        $('#notVisitStartDate', getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            datepicker: true,
            value: '<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>'
        });
        
        $('#startDateSpent').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('.input-group-addon').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
        
        $('#endDateSpent').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('#endDateSpentSpan').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
    	$('.select-category').selectCategory({});
    });
</script>