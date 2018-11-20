<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<t:layout title="My booking">
	<h2>Bookings</h2>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="home">
				<c:forEach items="${bookList}" var="item">

					<P>
						<span style="font-weight:bold;font-size:14px"><spring:message code="left.navbar.shop"/> : <span style="color: #761c19">${item.shop.name}&nbsp;&nbsp;  </span></span>
						<span style="font-weight:bold;font-size:14px"><spring:message code="label.guest.amount"/> : <span style="color: #761c19">${item.guestAmount}&nbsp; &nbsp; </span> </span>
						<span style="font-weight:bold;font-size:14px"><spring:message code="label.appointment.time"/> : <span style="color: #761c19"><fmt:formatDate value="${item.appointmentTime}" pattern="yyyy-MM-dd"/>&nbsp; &nbsp; </span> </span>
					</P>
			<div style="border: 1px solid ;margin-bottom: 1%">
				<table class="table" style="margin-top: 1%;margin-bottom: 1%;width: 98%">
						<thead>
						<tr>
							<th><spring:message code="label.start.time"/> </th>
							<th><spring:message code="label.end.time"/> </th>
							<th><spring:message code="left.navbar.treatment"/> </th>
							<th><spring:message code="label.therapist"/> </th>
							<th><spring:message code="label.room"/> </th>
							<th><spring:message code="label.status"/> </th>
						</tr>
						</thead>
						<c:forEach items="${item.bookItems}" var="bookItem">
							<tr>
								<td><fmt:formatDate value="${bookItem.appointmentTime}" pattern="HH:mm"/></td>

								<td><fmt:formatDate value="${bookItem.appointmentEndTime}" pattern="HH:mm"/></td>
								<td>${bookItem.productOption.label3}</td>
								<td><c:if test="${not empty bookItem.therapists}">${bookItem.therapists}</c:if>
								</td>
								<td><c:if test="${not empty bookItem.room}"> ${bookItem.room.name}</c:if></td>
								<td>${bookItem.status}</td>
							</tr>
						</c:forEach>
					</table>
			</div>
				</c:forEach>



			<%--<table class="table table-striped table-hover">
				<thead>
				<tr>
					<th><spring:message code="label.shop"/></th>
					<th><spring:message code="label.fullName"/></th>
					<th><spring:message code="label.guest.amount"/></th>
					<th><spring:message code="label.appointment.time"/></th>
					<th class="col-lg-5">Items Details</th>
					<th>status</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${bookList}" var="item">
					<tr data-id="${item.id}">
						<td>${item.shop.name}</td>
						<td>${item.user.fullName}</td>
						<td>${item.guestAmount}</td>
						<td><fmt:formatDate value="${item.appointmentTime}" pattern="yyyy-MM-dd"/></td>
						<td>
							<table class="table">
								<tr>
									<th>Start</th>
									<th>End</th>
									<th>Treatment</th>
									<th>Therapist</th>
									<th>Room</th>
									<th>Status</th>
								</tr>
								<c:forEach items="${item.bookItems}" var="bookItem">
									<tr>
										<td><fmt:formatDate value="${bookItem.appointmentTime}" pattern="HH:mm"/></td>

									<td><fmt:formatDate value="${bookItem.appointmentEndTime}" pattern="HH:mm"/></td>
									<td>${bookItem.productOption.label3}</td>
									<td><c:if test="${not empty bookItem.therapists}">${bookItem.therapists}</c:if>
										</td>
										<td><c:if test="${not empty bookItem.room}"> ${bookItem.room.name}</c:if></td>
										<td>${bookItem.status}</td>
								</tr>
								</c:forEach>
							</table>

						</td>
						<td>${item.status}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>--%>
</t:layout>