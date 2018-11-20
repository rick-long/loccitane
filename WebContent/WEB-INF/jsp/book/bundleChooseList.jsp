<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="ex" tagdir="/WEB-INF/tags" %>


<%-- 模板2 --%>
<table id="bookItemTemplate2" class="hide">
        <tr class="bundle_css" data-bundle-id="${bundleId}" data-timestamp="${timestamp}">
            <td>
            <input type="hidden" id="categoryId" name="categoryId" value="">
            <input type="hidden" id="productId" name="productId" value="${po1Id}">
            <input type="hidden" id="productOptionId" name="productOptionId" value="${po1.id}">
	    <input name ="duration" id="duration" type="hidden" class="form-control" value="${po1Duration}"/>
            <input name="displayName" value="${po1.label33}" class="form-control dropdown-toggle"
                   data-duration="${po1.duration}" data-process-time="${po1.processTime}" data-toggle="dropdown" value="" data-submenu="" readonly="">
        </td>
        <td>
            <select class="selectpicker form-control guestAmount">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </td>
        <td align="center">
            <input type="checkbox" class="shareRoom" <c:if test="${isSameTimeToShareRoom != null && isSameTimeToShareRoom eq 'YES'}">checked="checked"</c:if> >
        </td>
        <td>
            <div class="time-select startTimeDiv">
                <ex:tree tree="${timeTreeData}" clazz="startTime" selectId="${po1StartTime}" selectName="${po1StartTime}"/>
            </div>
        </td>
        <td>
            <div class="time-select endTimeDiv">
                <ex:tree tree="${timeTreeData}" clazz="endTime" selectId="${po1EndTime}" selectName="${po1EndTime}"/>
            </div>
        </td>
    </tr>
        <tr class="bundle_css" id="bundleLastItem" data-bundle-id="${bundleId}" data-timestamp="${timestamp}">
            <td>
            <input type="hidden" id="categoryId" name="categoryId" value="">
            <input type="hidden" id="productId" name="productId" value="${po2Id}">
	       <input name ="duration" id="duration" type="hidden" class="form-control" value="${po2Duration}"/>
            <input type="hidden" id="productOptionId" name="productOptionId" value="${po2.id}">
            <input name="displayName" value="${po2.label33}" class="form-control dropdown-toggle"
                   data-duration="${po1.duration}" data-process-time="${po2.processTime}" data-toggle="dropdown" value="" data-submenu="" readonly="">
        </td>
        <td>
            <select class="selectpicker form-control guestAmount">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </td>
        <td align="center">
            <input type="checkbox" class="shareRoom" <c:if test="${isSameTimeToShareRoom != null && isSameTimeToShareRoom eq 'YES'}">checked="checked"</c:if>>
        </td>
        <td>
            <div class="time-select startTimeDiv">
                <ex:tree tree="${timeTreeData}" clazz="startTime" selectId="${po2StartTime}" selectName="${po2StartTime}"/>
            </div>
        </td>
        <td>
            <div class="time-select endTimeDiv">
                <ex:tree tree="${timeTreeData}" clazz="endTime" selectId="${po2EndTime}" selectName="${po2EndTime}"/>
            </div>
        </td>
        <td>
            <button type="button" class="addBookItem">+</button>
            <button type="button" onclick="removeBundle(this)">-</button>
        </td>
    </tr>
</table>