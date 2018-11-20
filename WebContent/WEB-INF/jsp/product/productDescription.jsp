<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<c:if test="${pdkList !=null && pdkList.size()>0 }">
	<fieldset>
		<legend></legend>
		<c:forEach items="${pdkList}" var="pdk" varStatus="idx">
			<div class="form-group">
				<label class="col-lg-4 control-label">${pdk.name}</label>
				<div class="col-lg-5">
					<c:if test="${pdk.uiType eq 'text-area' }">
						<textarea id="pdvalues[${idx.index }].value" name="pdvalues[${idx.index }].value" class="form-control">${pdk.defaultValue}</textarea>
					</c:if>
					<c:if test="${pdk.uiType eq 'text' }">
						<input type="text" id="pdvalues[${idx.index }].value" name="pdvalues[${idx.index }].value" class="form-control" value="${pdk.defaultValue}"/>
					</c:if>
				</div>
				<input type="hidden" name="pdvalues[${idx.index }].key" id="pdvalues[${idx.index }].key" value="${pdk.id}"/>
			</div>
		</c:forEach>
	</fieldset>
</c:if>