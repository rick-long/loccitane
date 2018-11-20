<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/front" %>

<t:layout title="Loyalty Rewards">
	<input id="redeemCode" name="redeemCode" type="hidden" value="${redeemCode}">
	<h2><spring:message code="front.label.loyalty.rewards.details"/>  ${member.firstName }</h2>
    <div style="background-color: rgba(255,255,255,0.85);padding:10px;">
     <div class="row">
      <div class="col-sm-3" style=" line-height: 25px;"><spring:message code="label.redeem.client.name"/>: ${member.firstName } ${member.lastName }<br/></div>
      <div class="col-sm-3" style=" line-height: 25px;"><spring:message code="front.label.registration.date"/> : <fmt:formatDate value="${member.created}" pattern="yyyy-MM-dd"/></div>
      <div class="col-sm-3" style=" line-height: 25px;"><spring:message code="label.redeem.client.current.ll"/>: ${member.currentLoyaltyLevel.name}<br/></div>
      <div class="col-sm-3" style=" line-height: 25px;"><spring:message code="label.redeem.client.remain.lp"/>: <fmt:formatNumber type="number" pattern="#,#00.00#" value="${member.remainLoyaltyPoints}"/></div>
      <div class="col-sm-3" style=" line-height: 25px;"><spring:message code="front.label.expiry.date.for.loyalty.level"/> : <c:if test="${empty llexpiryDate}">
				 <spring:message code="label.gender.UNKNOWN"/>
			</c:if>
            <c:if test="${!empty llexpiryDate}">
				<fmt:formatDate value="${llexpiryDate}" pattern="yyyy-MM-dd"/>
			</c:if>
            <br/>
            </div>
     </div>
	<!--<table class="table table-striped table-hover" style="margin-top: 1%;margin-bottom: 1%;width: 98%">
		<tr>
			<td><spring:message code="label.redeem.client.name"/>:</td>
			<td>${member.firstName } ${member.lastName }</td>
			<td><%-- <spring:message code="label.redeem.client.Date.Registered"/> --%>Registration Date:</td>
			<td><fmt:formatDate value="${member.created}" pattern="yyyy-MM-dd"/></td>
		</tr>
		<tr>
			<td><spring:message code="label.redeem.client.current.ll"/>:</td>
			<td>${member.currentLoyaltyLevel.name}</td>
			<td><spring:message code="label.redeem.client.remain.lp"/>:</td>
			<td><fmt:formatNumber type="number" pattern="#,#00.00#" value="${member.remainLoyaltyPoints}"/></td>
		</tr>
		<tr>
			<td>Expiry Date  for Loyalty Level:</td>
			<c:if test="${empty llexpiryDate}">
				<td> N/A</td>
			</c:if>
			<c:if test="${!empty llexpiryDate}">
				<td><fmt:formatDate value="${llexpiryDate}" pattern="yyyy-MM-dd"/></td>
			</c:if>
			<td></td>
			<td></td>
		</tr>
	</table>-->
</div>
	<ul id="myTab" class="nav nav-tabs" style="background:rgba(255,255,255,0.85);">
		<li class="active">
			<a href="#home" data-toggle="tab">
				<spring:message code="front.label.redemption.awards"/>
			</a>
		</li>
		<li><a href="#yourRedemptions" data-toggle="tab"><spring:message code="front.label.your.redemptions"/> </a></li>
		<li><a href="#loyaltyPointsHistory" data-toggle="tab"><spring:message code="front.label.loyalty.points.history"/> </a></li>
		<li><a href="#salesHistory" data-toggle="tab"><spring:message code="label.member.sales.history"/> </a></li>

	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="home">
        <div style="border: 1px solid ;margin-bottom: 1%">
			<table class="table table-striped" style="margin-top: 1%;margin-bottom: 1%;width: 98%">
				<thead>
				<tr>
					<th><spring:message code="label.name"/> </th>
					<th><spring:message code="label.description"/></th>
					<th><spring:message code="label.redeem.required.lp"/></th>
					<%--<th><spring:message code="label.award.be.worth"/></th>--%>

					<th></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${awardRedemptions}" var="ar">
					<tr data-id="${ar.id}">
						<td>${ar.name}</td>
						<td>${ar.description }</td>
						<td><fmt:formatNumber type="number" pattern="#,#00.00#" value="${ar.requiredLp}"/></td>
						<td>
							<c:if test="${member.remainLoyaltyPoints >= ar.requiredLp and ar.startDate.time<=now.time and ar.endDate.time>=now.time}">
								<button class="btn btn-link" onclick="redeem(${ar.id},${member.id},${ar.requiredLp},'${ar.name}')">REDEEM NOW</button>
							</c:if>
						</td>

					</tr>
				</c:forEach>
				</tbody>
			</table>
            </div>
		</div>
		<div class="tab-pane fade" id="yourRedemptions">
        <div style="border: 1px solid ;margin-bottom: 1%">
			<table class="table table-striped" style="margin-top: 1%;margin-bottom: 1%;width: 98%">
				<thead>
				<tr>
					<th><spring:message code="label.name"/> </th>
					<th><spring:message code="label.description"/> </th>
					<th><spring:message code="label.redeem.award.date"/> </th>
					<th><spring:message code="front.label.redeem.loyalty.points"/> </th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${awardRedemptionTransactions}" var="art">
					<tr data-id="${art.id}">
						<td>${art.awardRedemption.name }</td>
						<td>${art.awardRedemption.description }</td>
						<td><fmt:formatDate value="${art.redeemDate}" pattern="yyyy-MM-dd"/></td>
						<td ><fmt:formatNumber type="number" pattern="#,#00.00#" value="${art.redeemLp}"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<br/>
            </div>
            <p>
			<strong><spring:message code="front.label.statements6"/> <a href="http://www.senseoftouch.com.hk/membership/" style="color: #2aabd2"><spring:message code="front.label.please.click.here"/> </a></strong></p>
		</div>
	<div class="tab-pane fade " id="loyaltyPointsHistory">
		<div style="border: 1px solid ;margin-bottom: 1%">
			<table class="table table-striped" style="margin-top: 1%;margin-bottom: 1%;width: 98%">
				<thead>
				<tr>
					<th><spring:message code="label.purchase.date"/></th>
					<th><spring:message code="label.expiry.date"/></th>
					<th><spring:message code="label.earn.points"/></th>
					<th><spring:message code="label.redeemed.points"/></th>
					<th><spring:message code="label.earn.channel"/></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${lptList}" var="item">
					<tr data-id="${item.id}">
						<td><fmt:formatDate value="${item.earnDate}" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.expiryDate}" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatNumber type="number" pattern="#,#00.00#" value="${item.earnPoints}"/></td>
						<td><fmt:formatNumber type="number" pattern="#,#00.00#" value="${item.redeemedPoints}"/></td>
						<td>${item.earnChannel}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="tab-pane fade " id="salesHistory">
		<div style="border: 1px solid ;margin-bottom: 1%">
			<c:forEach items="${orderList}" var="sales">
				<table class="table table-striped">
					<thead>
					<tr data-id="${sales.id}">
						<th class="col-lg-3"><fmt:formatDate value="${sales.purchaseDate}" pattern="yyyy-MM-dd"/></th>
						<th class="col-lg-2">${sales.reference}</th>
						<th class="col-lg-2">${sales.shop.name }</th>
						<th class="col-lg-3">${sales.paymentMethodsAndAmount}</th>
						<th class="col-lg-3"></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${sales.purchaseItems}" var="pi">
						<tr>
							<td>
								<c:choose>
									<c:when test="${pi.buyPrepaidTopUpTransaction !=null}">
										<c:set var="prepaidType">
											${fn:replace(pi.buyPrepaidTopUpTransaction.prepaidType, "_", "")}
										</c:set>
										<spring:message code="label.prepaid.type.${prepaidType}"/>
									</c:when>
									<c:otherwise>
										${pi.productOption.label3}
									</c:otherwise>
								</c:choose>
							</td>
							<td><fmt:formatNumber type="number" pattern="#,#00.00#" value="${pi.price}" /></br>(<spring:message code="label.price"/> )</td>
							<td>${pi.qty }</br>(<spring:message code="label.qty"/> )</td>
							<td><fmt:formatNumber type="number" pattern="#,#00.00#" value="${pi.amount}" /></br>(<spring:message code="label.amount"/> )</td>
							<td><fmt:formatNumber type="number" pattern="#,#00.00#" value="${pi.discountValue}" /></br>(<spring:message code="label.discount"/> )</td>
							<td></td>

						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:forEach>
		</div>
	</div>
<%--redeem success mode--%>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
									 aria-label="Close">
					&times;
				</button>
					<h3 class="modal-title" id="myModalLabel">
						<spring:message code="front.label.redeem"/>
					</h3>
				</div>
				<div class="modal-body">
					<p id="redeemSaveStatus"></p>
				</div>
			</div>
		</div>
	</div>
	<script>

        function redeem(id,memberId,requiredLp,name) {
           var redeemCode= $('#redeemCode').val();
                BootstrapDialog.confirm({
                    title: '<spring:message code="front.label.redeem.confirm"/> ',
                    message: '<spring:message code="front.label.long.message7"/> '+name+' <spring:message code="front.label.with"/> '+ requiredLp +' <spring:message code="front.label.loyalty.points"/> ',
                    callback: function (status) {
                        if (status) {
                            // 發起動作請求
                            $.ajax({
                                type: "POST",
                                dataType: "JSON",
                                async: false,
                                url: '<c:url value="/front/redeem"/>',
                                data: {"id":id,"memberId":memberId,"redeemCode":redeemCode},
                                success: function(data) {
                                    if (data.status =='succeed') {
                                        $('#redeemSaveStatus').text("Redeem success, please pay attention to the mailbox!")
                                        $('#myModal').modal('show');
                                        setTimeout("location.reload()",2000)

                                    } else {
                                        $('#redeemSaveStatus').text("Redeem Failed!");
                                        $('#myModal').modal('show');
                                        setTimeout("location.reload()",2000);
                                    }

                                }

                            });
                        } else {
                        }
                    }
                });
        }

	</script>
</t:layout>
