<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>


<fieldset>
<legend><h4><spring:message code="label.po"/>:${poItem.poRef }</h4></legend>
<div class="po-item" id="poitem-edit">	
	<input type="hidden" name="poItemList[${index}].productOptionId" value="${poItem.productOptionId }"/>	
	<c:forEach items="${poItem.pokeyList}" var="pok" varStatus="idx">
		<div class="form-group">
			<label class="col-lg-4 control-label">${pok.name}</label>
			<div class="col-lg-5">
				<c:if test="${not empty poItem.kvMap[pok.id]}">
					<c:if test="${pok.uiType eq 'text-area' }">
						<textarea name="poItemList[${index}].kvMap[${pok.id }].value" id="poItemList[${index}].kvMap[${pok.id }].value" >${poItem.kvMap[pok.id].value }</textarea>
					</c:if>
					<c:if test="${pok.uiType eq 'text' }">
						<form:input path="poItemList[${index}].kvMap[${pok.id }].value" value="${poItem.kvMap[pok.id].value }" class="form-control"/>
					</c:if>
					<input type="hidden" name="poItemList[${index}].kvMap[${pok.id }].id" value="${poItem.kvMap[pok.id].id }"/>
				</c:if>
				<c:if test="${empty poItem.kvMap[pok.id]}">
					<c:if test="${pok.uiType eq 'text-area' }">
						<textarea name="poItemList[${index}].kvMap[${pok.id }].value" id="poItemList[${index}].kvMap[${pok.id }].value" ></textarea>
					</c:if>
					<c:if test="${pok.uiType eq 'text' }">
						<form:input path="poItemList[${index}].kvMap[${pok.id }].value" class="form-control"/>
					</c:if>
					<input type="hidden" name="poItemList[${index}].kvMap[${pok.id }].id"/>
				</c:if>
			</div>
			<input type="hidden" name="poItemList[${index}].kvMap[${pok.id }].key" value="${pok.id}"/>
		</div>
	</c:forEach>
</div>
</fieldset>

<c:if test="${index == poEditVO.poItemList.size()-1}">
	<input type="button" id="add_item_<c:out value='${index+1}'/>" class="" value="+"
			onclick="addPoOnkeyDown(event,<c:out value='${index+1}'/>)">
</c:if>