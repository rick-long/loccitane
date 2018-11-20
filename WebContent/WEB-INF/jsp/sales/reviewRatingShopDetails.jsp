<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="rat-content rat-detail">
	<table>
		<tr class="rat-title">
			<td class="date"><spring:message code="label.sales.date"/> </td>
			<td class="custom-name"><spring:message code="label.sales.membername"/> </td>
			<td class="serve-rat"><spring:message code="label.sales.customerservicerating"/> </td>
			<td class="cle-rat"><spring:message code="label.sales.cleanlinessrating"/> </td>
			<td class="review-text"><spring:message code="label.sales.reviewtext"/> </td>
		</tr>
<c:if test="${page !=null }">
		<c:forEach items="${page.list}" var="reviewRatingShop">
			<tr class="detail-content">
				<td class="date"><fmt:formatDate value="${reviewRatingShop.created}" pattern="yyyy-MM-dd"/></td>
				<td class="custom-name">${reviewRatingShop.createdBy}</td>
				<td class="serve-rat"><span>${reviewRatingShop.customerServiceStar}</span> <span class="star" data-bg="yellow" data-score="${reviewRatingShop.customerServiceStar}"></span></td>
				<td class="cle-rat"><span>${reviewRatingShop.cleanlinessStar}</span> <span class="star" data-bg="blue" data-score="${reviewRatingShop.cleanlinessStar}"></span></td>
				<td class="review-text">
						${reviewRatingShop.review.reviewText}
				</td>
			</tr>
		</c:forEach>
</c:if>
	</table>
	<!-- page start -->
<%--	<c:url var="pageUrl" value="/review/shopDetails?shopId=13" scope="request"/>
	<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>--%>
	<!-- page end -->
</div>
<script>
    $(function () {


        $('.detail-content').each(function (i) {
            if (i%2 === 1 && i !== 0) {
                $(this).addClass('gray');
            }
        });

        $('.star').each(function () {
            var color = $(this).attr('data-bg');
            $(this).raty({
                width: 90,

                space : false,

                readOnly: true,

                half     : true,

                starHalf : '/resources/review/star-half-'+ color +'.svg',

                starOff  : '/resources/review/star-off.svg',

                starOn   : '/resources/review/star-on-'+ color +'.svg',

                score: function() {
                    return $(this).attr('data-score');
                }
            });
        });

        $('.review-text').hover(function () {
            $(this).find('span').show();
        },function () {
            $(this).find('span').hide();
        })
    });
</script>
