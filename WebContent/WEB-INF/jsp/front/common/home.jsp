<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>


	<%-- <a href="<c:url value="/brand/toAdd"/>" data-target="#myModal" class="btn btn-primary" data-toggle="modal">edit</a> --%>


<button id="" onclick="javascript:openModal('<c:url value="/brand/toAdd"/>');" 
	class="btn btn-primary" data-toggle="modal" data-target="#myModal">
						<spring:message code="label.button.add"/>
					</button>
					
					<button id="" onclick="javascript:openModal('<c:url value="/brand/toEdit"/>');" 
	class="btn btn-primary" data-toggle="modal" data-target="#myModal">
						<spring:message code="label.button.edit"/>
					</button>
<c:import url="/WEB-INF/jsp/common/modal.jsp"/>

</body>