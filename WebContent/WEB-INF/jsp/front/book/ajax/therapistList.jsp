<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/front/common/jspInit.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<c:choose>
    <c:when test="${not empty frontBookItemVO.availableTherapists || not empty frontBookItemVO.notAvailableTherapists}">
        <c:forEach items="${frontBookItemVO.availableTherapists}" var="item">
            <li class='available'>
                <input name="therapistInfo" type="checkbox" value="${item.id}_available"/> ${item.displayName}
            </li>
        </c:forEach>
        <c:forEach items="${frontBookItemVO.notAvailableTherapists}" var="item">
            <li class='unavailable'>
                <input name="therapistInfo" type="checkbox" value="${item.id}_block"/> ${item.displayName} (blocked)
            </li>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <li class="warning">
            <spring:message code="front.label.no.therapist.available"/>
        </li>
    </c:otherwise>
</c:choose>

<script type="text/javascript">

    var capacity = Number(${frontBookItemVO.productOption.capacity});
    $('input[name=therapistInfo]').click(function () {
        if (capacity === 1) {
            $('input[name=therapistInfo]').prop('checked', false);
            $(this).prop('checked', true);
            return;
        }

        if ($('input[name=therapistInfo]:checked').length > capacity) {
            alert('Select up to ' + capacity + ' therapist(s)!');
            $(this).prop('checked', false);
        }
    });
</script>