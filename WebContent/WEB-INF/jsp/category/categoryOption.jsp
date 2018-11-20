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
<ul class="dropdown-menu">
    <c:forEach items="${categories}" var="category">
        <c:if test="${category.isActive}">
            <c:choose>
                <c:when test="${category.categories.size() gt 0}">
                    <li class="dropdown-submenu">
                        <a tabindex="0" class="category" data-category-id="${category.id}" data-display-name="${category.fullName}">${category.name}</a>
                        <c:set var="categories" value="${category.sortedChilden}" scope="request"/>
                        <jsp:include page="categoryOption.jsp"/>
                    </li>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${categorySelectVO.selectable.contains('product') || categorySelectVO.selectable.contains('option')}">
                            <%-- 输出category的product和production数据 --%>
                            <c:choose>
                                <c:when test="${category.products.size() gt 0}">
                                    <li class="dropdown-submenu">
                                        <a tabindex="0" class="category" data-category-id="${category.id}" data-display-name="${category.fullName}">${category.name}</a>
                                        <ul class="dropdown-menu">
                                            <c:forEach items="${category.products}" var="product">
                                                <c:if test="${product.isActive}">
                                                    <c:choose>
                                                        <c:when test="${categorySelectVO.selectable.contains('option')}">
                                                            <c:choose>
                                                                <c:when test="${product.productOptions.size() gt 0}">
                                                                    <li class="dropdown-submenu">
                                                                        <a class="product" tabindex="0" data-product-id="${product.id}" data-display-name="${product.fullName}">${product.name}</a>
                                                                        <ul class="dropdown-menu">
                                                                            <c:forEach items="${product.productOptions}" var="option">
                                                                                <c:if test="${option.isActive}">
                                                                                    <li>
                                                                                        <a class="productOption" tabindex="0" data-product-option-id="${option.id}" data-duration="${option.duration}" data-capacity="${option.capacity}" data-process-time="${option.processTime}" data-display-name="${option.label2}">${option.label3}</a>
                                                                                    </li>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                        </ul>
                                                                    </li>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <li>
                                                                        <a tabindex="0" class="product" data-product-id="${product.id}" data-display-name="${product.fullName}">${product.name}</a>
                                                                    </li>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li>
                                                                <a tabindex="0" class="product" data-product-id="${product.id}" data-display-name="${product.fullName}">${product.name}</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a tabindex="0" class="category" data-category-id="${category.id}" data-display-name="${category.fullName}">${category.name}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a tabindex="0" class="category" data-category-id="${category.id}" data-display-name="${category.fullName}">${category.name}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:forEach>
</ul>

 