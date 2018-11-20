<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@ tag import="java.util.ArrayList" %>
<%@ tag import="java.util.List" %>
<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="tree" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
   多级下拉菜单选择框
   需要提供 TreeVO结构的List集合作为数据源
   调用multiMenu()方法初始化
   参数：
    1. name 隐藏域input的name值
    2. clazz是显示的input的样式设置
    3. selectId初始化的id值
    4. selectName是初始化显示的值
    5. selectable 可以点击的层级: 1,2,3 ... 默认只可以选择叶子节点
    6. tree 数据源

    其他：
    自定义每级宽度(level-1, level-2, level-3 ...)：
    .time-select .menu-area ul.level-1 {
        width:80px;
    }
    .time-select .menu-area ul.level-2 {
        width:60px;
        text-align: center;
    }
    .time-select .menu-area ul.level-3 {
        width:100px;
        text-align: center;
    }
--%>
<%@ attribute name="name" %>
<%@ attribute name="clazz" %>
<%@ attribute name="selectId" rtexprvalue="true" %>
<%@ attribute name="selectName" rtexprvalue="true" %>
<%@ attribute name="selectable" required="false" %>
<%@ attribute name="tree" required="true" type="java.util.List<org.spa.vo.common.TreeVO>" %>

<div class="multi-menu">
    <input type="hidden" name="${name}" value="${selectId}" class="select-id"/>
    <input type="text" class="show form-control ${clazz}" value="${selectName}" readonly>
    <div class="menu-area hide">
        <ul class='level-1 <c:if test="${levelList.contains(level)}">selectable</c:if>'>
            <c:forEach items="${tree}" var="item">
                <li>
                    <a class="multi-link" data-id="${item.id}" data-display-name="${item.displayName}">${item.displayName}</a>
                    <tree:treeOption tree="${item.children}" level='${2}'/>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
