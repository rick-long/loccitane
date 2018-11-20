<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>


<fieldset>
<legend><h4><spring:message code="label.po"/></h4></legend>
<div class="po-item" id="poitem-add">		
	<input type="hidden" name="poItemList[${index}].poId"/>	
	<c:forEach items="${pokList}" var="pok" varStatus="idx">
		<div class="form-group">
			<label class="col-lg-3 control-label">${pok.name}</label>
			<div class="col-lg-5">
				<c:if test="${pok.uiType eq 'text-area' }">
					<%-- <input type="text" name="poItemList[${index}].poValues[${idx.index }].value" class="form-control"/> --%>
				</c:if>
				<c:if test="${pok.uiType eq 'text' }">
					<input type="text" name="poItemList[${index}].poValues[${idx.index }].value" class="form-control"/>
				</c:if>
			</div>
			<input type="hidden" name="poItemList[${index}].poValues[${idx.index }].key" value="${pok.id}"/>
		</div>
	</c:forEach>
</div>
</fieldset>
<input type="button" id="add_item_<c:out value='${index+1}'/>" class="" value="-"
			onclick="addPoOnkeyDown(event,<c:out value='${index+1}'/>)">