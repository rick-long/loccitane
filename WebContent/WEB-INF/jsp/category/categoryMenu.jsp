<%@ page import="org.spa.model.product.Category" %>
<%@ page import="java.util.Collection" %>
<%@ page import="org.spa.vo.category.CategorySelectVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%
    Collection<Category> categories = (Collection<Category>) request.getAttribute("categories");
    pageContext.setAttribute("categories", categories);

    CategorySelectVO categorySelectVO = (CategorySelectVO) request.getAttribute("categorySelectVO");
    pageContext.setAttribute("categorySelectVO", categorySelectVO);
%>

<ul class="dropdown-menu deep-${categorySelectVO.deep}" data-deep="${categorySelectVO.deep}">
    <c:choose>
        <c:when test="${not empty categories}">
            <c:forEach items="${categories}" var="category">
                <c:if test="${category.isActive}">
                    <li class='${category.categories.size() gt 0 or (category.products.size() gt 0 && (categorySelectVO.selectable.contains('product') || categorySelectVO.selectable.contains('option'))) ? "dropdown-submenu" : ""}'>
                        <a onclick="dropdownMenuClick(this, event);" onmouseover="dropdownMenuLoadSubMenu(this);" tabindex="0" class="${categorySelectVO.level}" data-children="${category.categories.size()}" 
                        data-category-id="${category.id}" data-is-online="${categorySelectVO.isOnline}" data-shop-id="${categorySelectVO.shopId}" 
                        data-display-name="${category.fullNameExceptProdType}" title="${category.name}">${category.name}</a>
                    </li>
                </c:if>
            </c:forEach>
        </c:when>
        <c:when test="${not empty products && (categorySelectVO.selectable.contains('product') || categorySelectVO.selectable.contains('option'))}">
            <c:forEach items="${products}" var="product">
            <c:choose>
            	<c:when test="${categorySelectVO.isOnline !=null && categorySelectVO.isOnline && product.showOnApps}">
            		<c:if test="${product.showOnApps}">
		                <li class='${product.productOptions.size() gt 0 && categorySelectVO.selectable.contains("option") ? "dropdown-submenu" : ""}'>
		                    <a onclick="dropdownMenuClick(this, event);" onmouseover="dropdownMenuLoadSubMenu(this);" tabindex="0" class="${categorySelectVO.level}" data-is-online="${categorySelectVO.isOnline}" data-shop-id="${categorySelectVO.shopId}"  
		                    data-product-id="${product.id}" data-display-name="${product.fullName2}" title="${product.name}">${product.name}</a>
		                </li>
	                </c:if>
                </c:when>
                <c:otherwise>
                	<li class='${product.productOptions.size() gt 0 && categorySelectVO.selectable.contains("option") ? "dropdown-submenu" : ""}'>
	                    <a onclick="dropdownMenuClick(this, event);" onmouseover="dropdownMenuLoadSubMenu(this);" tabindex="0" class="${categorySelectVO.level}" data-product-id="${product.id}" data-display-name="${product.fullName2}" title="${product.name}">${product.name}</a>
	                </li>
                </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:when>
        <c:when test="${not empty productOptions && categorySelectVO.selectable.contains('option')}">
            <c:forEach items="${productOptions}" var="option">
                <li>
                    <a onclick="dropdownMenuClick(this, event);" class="${categorySelectVO.level}" tabindex="0" data-product-id="${option.product.id}" data-product-option-id="${option.id}" data-duration="${option.duration}" data-capacity="${option.capacity}" data-process-time="${option.processTime}" data-display-name="${option.labelWithCodeNoBr}" title="${option.labelWithCode}">${option.labelWithCode}</a>
                </li>
            </c:forEach>
        </c:when>
    </c:choose>
</ul>
