<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="dropdown">
    <input type="hidden" id="categoryId" name="categoryId" value="${categorySelectVO.categoryId}"/>
    <input type="hidden" id="productId" name="productId" value="${categorySelectVO.productId}"/>
    <input type="hidden" id="productOptionId" name="productOptionId" value="${categorySelectVO.productOptionId}"/>
  	
    <input id="categorySelect2_displayName" name="displayName" placeholder='Please select' class="form-control dropdown-toggle" data-duration='${categorySelectVO.duration}' data-process-time='${categorySelectVO.processTime}' data-toggle="dropdown" value='${categorySelectVO.displayName}' data-submenu readonly/>
    <%-- 递归显示菜单 --%>
    <jsp:include page="categoryMenu.jsp"/>
</div>