<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="tree" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="tree" required="true" type="java.util.List<org.spa.vo.common.TreeVO>" %>
<%@ attribute name="level" required="true" type="java.lang.Integer" %>

<%-- 递归初始化tree --%>
<ul class="hide level-${level} <c:if test="${levelList.contains(level)}">selectable</c:if>">
    <c:forEach items="${tree}" var="item">
        <li>
            <a class='multi-link <c:if test="${empty item.children}">leaf</c:if>' data-id="${item.id}" data-display-name="${item.displayName}">${item.displayName}</a>
            <c:if test="${not empty item.children}">
                <tree:treeOption tree="${item.children}" level="${level + 1}"/>
            </c:if>
        </li>
    </c:forEach>
</ul>