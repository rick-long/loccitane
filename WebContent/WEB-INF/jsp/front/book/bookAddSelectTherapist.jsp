<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/front/common/jspInit.jsp" %>

<div id="timeContext">
    <h2><spring:message code="front.label.choose.therapist"/> </h2>
    <ul>
        <li data-therapist-id=""><spring:message code="front.label.any.therapist"/> </li>
        <c:forEach items="${therapists}" var="item">
            <li data-therapist-id="${item.id}">${item.fullName}</li>
        </c:forEach>
    </ul>
</div>
<script type="text/javascript">
    $(function () {
        $('#timeContext', getContext()).find('li').click(function () {
            var $this = $(this);
            $this.siblings('li').removeClass('active');
            if ($this.hasClass('active')) {
                $this.removeClass('active');
            } else {
                $this.addClass('active');
            }
        });
    });
</script>
