<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<t:layout title="Home Page">
	<h2><spring:message code="left.navbar.home"/> </h2>
	<div class="table_index_content">
		<c:if test="${user !=null }">
			<%-- <div class="new_news_title">
				<h3>What's new?</h3>
				<p>Membership to the Sense of Touch Spa Diva Loyalty Programme is FREE. <br/>
						 Get your <a href="<c:url value="/switchPath.do?prefix=/user&page=/clientMembership.do?state=init"/>">Spa Diva Card</a> now.</p>
			</div> --%>
		</c:if>

		<div class="box_item_list">
			<%-- <div class="box_item">
				<p><a href="http://www.senseoftouch.com.hk/promotions/">
					<img src="<c:url value='/resources/img/front/icon_partnersPromotion.gif'/>" class="icon">
				</a></p>
				<p><a href="http://www.senseoftouch.com.hk/promotions/">Partner's Promotion</a><br>to know the latest partner's promotion here. </p>
			</div> --%>
			<div class="box_item">
				<p><a href='<c:url value="/front/book/toAdd"/>'>
					<img src="<c:url value='/resources/img/front/icon/transferBooking.svg'/>" class="icon"></a></p>
				<p><a href='<c:url value="/front/book/toAdd"/>'><spring:message code="label.book.channel.ONLINE"/> </a>
					<br>
					<spring:message code="front.label.long.message8"/> </p>
			</div>

				<div class="box_item">

                     <p><a href='<c:url value="/front/toMyLoyalty"/>'>
                        <img src="<c:url value='/resources/img/front/icon/loyalty.svg'/>" class="icon">
                                        </a></p>
                        <p><a href='<c:url value="/front/toMyLoyalty"/>'><spring:message code="front.label.my.loyalty"/> </a><br><spring:message code="front.label.long.message9"/> </p>

                </div>

			<div class="box_item">
				<p><a href="<c:url value="/front/prepaid/voucherAddMain"/>">
					<img src="<c:url value='/resources/img/front/icon/gift.svg'/>" class="icon">
				</a></p>
				<p><a href='<c:url value="/front/prepaid/voucherAddMain"/>'><spring:message code="front.label.buy.gift.voucher"/> </a><br><spring:message code="front.label.long.message10"/> </p>
			</div>
			<div class="box_item">
				<p><a href="<c:url value="/front/prepaid/myPrepaid"/>">
					<img src="<c:url value='/resources/img/front/icon/card.svg'/>" class="icon">
				</a></p>
				<p><a href='<c:url value="/front/prepaid/myPrepaid"/>'><spring:message code="front.label.my.package.voucher"/> </a><br><spring:message code="front.label.long.message11"/> </p>
			</div>
			<div class="box_item">
				<p><a href='<c:url value="/front/book/myBookings"/>'>
					<img src="<c:url value='/resources/img/front/icon/list.svg'/>" class="icon">
				</a>
				</p>

				<p>
					<a href='<c:url value="/front/book/myBookings"/>'> <spring:message code="front.label.my.bookings"/> </a>
					<br>
					<spring:message code="front.label.long.message12"/>
				<p>
			</div>

			<div class="box_item">
				<p>
					<a href='<c:url value="/front/profile"/>'>
						<img src="<c:url value='/resources/img/front/icon/profile_light.svg'/>" class="icon">
					</a>
				</p>

				<p>
					<a href='<c:url value="/front/profile"/>'><spring:message code="front.label.my.profile"/> </a>
					<br>
					<spring:message code="front.label.long.message13"/> </p>

			</div>


			<div class="box_item">
                    <a href='<c:url value="/front/toChangePwd"/>'>
                        <img src="<c:url value='/resources/img/front/icon/password.svg'/>" border="0" class="icon"/>
                    </a>
                </p>
                <p>
                    <a href='<c:url value="/front/toChangePwd"/>'><spring:message code="label.change.password"/> </a>
                    <br>
                    <spring:message code="front.label.long.message14"/>
                </p>

			</div>

		</div>
	</div>

</t:layout>