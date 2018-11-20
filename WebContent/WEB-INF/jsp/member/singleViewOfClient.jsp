<%@page import="org.spa.model.user.ConsentForm" %>
<%@page import="org.spa.model.user.ConsentFormUser" %>
<%@page import="org.spa.model.user.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>
    <style>
    .spa_Data_select{
        margin-top:20px;
    }
    .spa_Data_title{
        font-size:20px;
        font-weight:bold;
    }
    .info_profile_version{
        margin-top:20px;
    }
    .member-introduction{
        margin-top:20px;
    margin-left: 27px;
    }
    .keycell{
      text-align:unset !important;
    }
    .valuecell{
      padding-left:20px;
    }
    .table-striped > tbody > tr:nth-of-type(even){
      background:#f9f9f9;
    }
    .spa-p-l-30{
      padding-left:30px;
    }
    </style>
<h3>
    <spring:message code="label.member.single.viewof.client"/>
</h3>
<div class="member_view">
<div class="row">
    <table width="100%">

        <c:if test="${client !=null }">
            <tr>
                <div class="col-sm-12 text-center">
                    <p class='spa_Data_title'>
                        <spring:message code="label.member.view.report.in"/> &nbsp;&nbsp;
                            <c:if test="${viewTime=='3' }">
                                <c:set var="threeMActiveCss" value="active"/><c:set var="sixMActiveCss" value=""/><c:set var="TwMActiveCss" value=""/><c:set var="tfMActiveCss" value=""/><c:set var="allActiveCss" value=""/>
                            </c:if>
                            <c:if test="${viewTime=='6' }">
                                <c:set var="threeMActiveCss" value=""/><c:set var="sixMActiveCss" value="active"/><c:set var="TwMActiveCss" value=""/><c:set var="tfMActiveCss" value=""/><c:set var="allActiveCss" value=""/>
                            </c:if>
                            <c:if test="${viewTime=='12' }">
                                <c:set var="threeMActiveCss" value=""/><c:set var="sixMActiveCss" value=""/><c:set var="TwMActiveCss" value="active"/><c:set var="tfMActiveCss" value=""/><c:set var="allActiveCss" value=""/>
                            </c:if>
                            <c:if test="${viewTime=='24' }">
                                <c:set var="threeMActiveCss" value=""/><c:set var="sixMActiveCss" value=""/><c:set var="TwMActiveCss" value=""/><c:set var="tfMActiveCss" value="active"/><c:set var="allActiveCss" value=""/>
                            </c:if>
                            <c:if test="${viewTime=='ALL' }">
                                <c:set var="threeMActiveCss" value=""/><c:set var="sixMActiveCss" value=""/><c:set var="TwMActiveCss" value=""/><c:set var="tfMActiveCss" value=""/><c:set var="allActiveCss" value="active"/>
                            </c:if>
                        </p>
                        <div class="spa_Data_select">

                            <a data-permission="member:toEdit" href='<c:url value="/member/singleViewOfClient?id=${client.id}&viewTime=3"/>'
                            class="btn btn-primary form-page ${threeMActiveCss}"
                            data-draggable="true" data-title='<spring:message code="label.member.singleviewofclient"/>'><spring:message code="label.member.3.months"/>
                            </a>
                            &nbsp;

                            <a data-permission="member:toEdit" href='<c:url value="/member/singleViewOfClient?id=${client.id}&viewTime=6"/>'
                            class="btn btn-primary form-page ${sixMActiveCss}"
                            data-draggable="true" data-title='<spring:message code="label.member.singleviewofclient"/>'><spring:message code="label.member.6.months"/>
                            </a>
                            &nbsp;

                            <a data-permission="member:toEdit" href='<c:url value="/member/singleViewOfClient?id=${client.id}&viewTime=12"/>'
                            class="btn btn-primary form-page ${TwMActiveCss}"
                            data-draggable="true" data-title='<spring:message code="label.member.singleviewofclient"/>'><spring:message code="label.member.12.months"/>
                            </a>
                            &nbsp;
                            <a data-permission="member:toEdit" href='<c:url value="/member/singleViewOfClient?id=${client.id}&viewTime=24"/>'
                            class="btn btn-primary form-page ${tfMActiveCss}"
                            data-draggable="true" data-title='<spring:message code="label.member.singleviewofclient"/>'><spring:message code="label.member.24.months"/>
                            </a>
                            &nbsp;

                            <a data-permission="member:toEdit" href='<c:url value="/member/singleViewOfClient?id=${client.id}&viewTime=ALL"/>'
                            class="btn btn-primary form-page ${allActiveCss}"
                            data-draggable="true" data-title='<spring:message code="label.member.singleviewofclient"/>'><spring:message code="label.member.all.months"/>
                            </a>
                        </div>




                 </div>  

            </tr>

            <tr>
                <td>
                   <div class="col-lg-4 col-md-4"></div>
                   <div class="col-lg-4 col-md-4 member-introduction">
                    <table class="info_profile_version">
                        <c:if test="${client !=null }">
                            <tr>
                                <td class="keycell"><spring:message code="label.username"/>:</td>
                                <td class="valuecell">${client.username}</td>
                            </tr>
                            <tr>
                                <td class="keycell" ><spring:message code="label.fullName"/>:</td>
                                <td class="valuecell">${client.fullName }</td>
                            </tr>
                            <tr>
                                <td class="keycell" ><spring:message code="label.email"/>:</td>
                                <td class="valuecell">${client.email }</td>
                            </tr>
                            <tr>
                                <td class="keycell"><spring:message code="label.gender"/>:</td>
                                <td class="valuecell">${client.gender}</td>

                            </tr>
                            <tr>
                                <td class="keycell"><spring:message code="label.member.dateOfBirth"/>:</td>
                                <td class="valuecell">
                                    <fmt:formatDate value="${client.dateOfBirth}" pattern="yyyy-MM-dd"/>
                                </td>

                            </tr>
                            <tr>
                                <td class="keycell"><spring:message code="front.label.registration.date"/>:</td>
                                <td class="valuecell">
                                    <fmt:formatDate value="${client.created}" pattern="yyyy-MM-dd"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="keycell"><spring:message code="label.created.by"/>:</td>
                                <td class="valuecell">${client.createdBy }</td>

                            </tr>
                            <%--<tr>--%>
                                <%--<td class="keycell"><spring:message code="label.redeem.client.current.ll"/>:</td>--%>
                                <%--<td class="valuecell">--%>
                                    <%--<c:if test="${client.currentLoyaltyLevel !=null }">--%>
                                        <%--${client.currentLoyaltyLevel.name}--%>
                                        <%--(<c:if test="${llexpiryDate !=null }"><fmt:formatDate value="${llexpiryDate}" pattern="yyyy-MM-dd"/></c:if>--%>
                                        <%--<c:if test="${llexpiryDate ==null }">N/A</c:if>--%>
                                        <%--)--%>
                                    <%--</c:if>--%>
                                <%--</td>--%>
                            <%--</tr>--%>
                            <tr>
                                <td class="keycell"><spring:message code="label.member.cash.package.remaining"/>:</td>
                                <td class="valuecell">
                                    ${client.remainValueOfCashPackage}
                                </td>
                            </tr>
                            <%--<tr>--%>
                                <%--<td class="keycell"><spring:message code="label.member.next.loyalty.level"/>:</td>--%>
                                <%--<td class="valuecell">--%>
                                    <%--<c:if test="${client.currentLoyaltyLevel !=null}">--%>
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${client.currentLoyaltyLevel.rank == 1}">--%>
                                                <%--<spring:message code="label.spending.required"/>&nbsp;--%>
                                                <%--<div style="font-size: 12px;display:inline;">--%>
                                                    <%--${client.needMoneyToGetNextLevel}--%>
                                                <%--</div>--%>
                                                <%--<spring:message code="label.to.become"/>&nbsp;--%>
                                                <%--<div style="font-size: 12px;display:inline;">--%>
                                                    <%--'${client.nextLoyaltyLevel.name}'--%>
                                                <%--</div>--%>
                                            <%--</c:when>--%>
                                            <%--<c:otherwise>--%>
                                                <%--<c:choose>--%>
                                                    <%--<c:when test="${client.currentLoyaltyLevel.rank ==7 || client.currentLoyaltyLevel.rank == 6}">--%>
                                                        <%--<spring:message code="label.highest.divalevel"/> </br>--%>
                                                        <%--<spring:message code="label.spending.required"/> &nbsp;--%>
                                                        <%--<div style="font-size: 12px;display:inline;">--%>
                                                            <%--${client.needMoneyToRenewCurrentLevel}--%>
                                                        <%--</div>--%>
                                                        <%--<spring:message code="label.to.renew"/>&nbsp;--%>
                                                        <%--<div style="font-size: 12px;display:inline;">--%>
                                                            <%--<c:choose>--%>
                                                                <%--<c:when test="${client.currentLoyaltyLevel.rank ==7}">--%>
                                                                    <%--'<spring:message code="label.book.black.diva"/> '--%>
                                                                <%--</c:when>--%>
                                                                <%--<c:otherwise>--%>
                                                                    <%--'${client.currentLoyaltyLevel.name}'--%>
                                                                <%--</c:otherwise>--%>
                                                            <%--</c:choose>--%>
                                                        <%--</div>--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:otherwise>--%>
                                                        <%--<spring:message code="label.spending.required"/> &nbsp;--%>
                                                        <%--<div style="font-size: 12px;display:inline;">--%>
                                                            <%--${client.needMoneyToGetNextLevel}--%>
                                                        <%--</div>--%>
                                                        <%--<spring:message code="label.to.become"/>&nbsp;--%>
                                                        <%--<div style="font-size: 12px;display:inline;">--%>
                                                            <%--'${client.nextLoyaltyLevel.name}'--%>
                                                        <%--</div>--%>
                                                        <%--</br>--%>
                                                        <%--<spring:message code="label.spending.required"/> &nbsp;--%>
                                                        <%--<div style="font-size: 12px;display:inline;">--%>
                                                            <%--${client.needMoneyToRenewCurrentLevel}--%>
                                                        <%--</div>--%>
                                                        <%--<spring:message code="label.to.renew"/>&nbsp;--%>
                                                        <%--<div style="font-size: 12px;display:inline;">--%>
                                                            <%--'${client.currentLoyaltyLevel.name}'--%>
                                                        <%--</div>--%>
                                                    <%--</c:otherwise>--%>
                                                <%--</c:choose>--%>
                                            <%--</c:otherwise>--%>
                                        <%--</c:choose>--%>
                                    <%--</c:if>--%>
                                <%--</td>--%>
                            <%--</tr>--%>
                            <tr>
                                <td class="keycell" ><spring:message code="left.navbar.member.adjustpoints.loyaltypoints"/>:</td>
                                <td class="valuecell">${client.remainLoyaltyPoints}</td>
                            </tr>
                        </c:if>
                    </table>
                   </div>
                    <div class="col-lg-4 col-md-4"></div>
                </td>

            </tr>
            <tr>
              <td>
    <div class="col-sm-12">
     <div class="col-lg-12">
<table class="table table-striped table-hover">
                        <caption><spring:message code="label.member.consent.form"/></caption>
                        <thead>
                        <tr>
                            <c:forEach items="${consentFormList}" var="cf">
                                <th>${cf.name }</th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <c:forEach items="${consentFormList}" var="cf1">

                                <td>
                                    <%
                                        User user = (User) request.getAttribute("client");
                                        ConsentForm cf1 = (ConsentForm) pageContext.getAttribute("cf1");
                                        ConsentFormUser cfu = user.getConsentFormDetails(cf1.getId());
                                        if (cfu != null && cfu.getShop() != null) {
                                    %>
                                    <%=cfu.getShop().getReference() %>
                                    <%
                                    } else {
                                    %>
                                    No
                                    <%
                                        }
                                    %>
                                </td>
                            </c:forEach>
                        </tr>
                        </tbody>
                    </table>
</div>
</div>

<div class="col-sm-12">

   <div class="col-lg-6 col-sm-12"><table class="table table-striped table-hover">
                        <caption><spring:message code="label.member.sales.history"/></caption>
                        <thead>
                        <tr>
                            <th><spring:message code="label.date"/> </th>
                            <th><spring:message code="label.shop"/></th>
                            <th><spring:message code="label.product"/></th>
                            <th><spring:message code="label.total.amount"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="shsum" value="0"></c:set>
                        <c:forEach items="${salesHistoryList}" var="item">
                            <tr>
                                <td><fmt:formatDate value="${item.purchaseDate}"
                                                    pattern="yyyy-MM-dd"/></td>
                                <td>${item.shop.name }</td>
                                <td>${item.purchaseItemNamesTherapist}</td>
                                <td>
                                	${item.totalAmount }
                                	<c:set var="shsum" value="${shsum+item.totalAmount}"></c:set>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                        	<td><b><spring:message code="label.total"/></b></td>
                        	<td></td>
                        	<td></td>
                        	<td><b>${shsum}</b></td>
                        </tr>
                        </tbody>
                    </table></div>
    <div class="col-lg-6 col-sm-12">
        <table class="table table-striped table-hover">
            <caption><spring:message code="label.member.spending.summary"/></caption>
            <thead>
            <tr>
                <th><spring:message code="label.member.product.type"/></th>
                <th><spring:message code="label.member.1.months"/></th>
                <th><spring:message code="label.member.3.months"/></th>
                <th><spring:message code="label.member.6.months"/></th>
                <th><spring:message code="label.member.12.months"/></th>
                <th><spring:message code="label.member.24.months"/></th>
                <th><spring:message code="label.member.all.months"/> </th>
            </tr>
            </thead>
            <tbody>
            <c:set var="oneSSsum" value="0"></c:set>
            <c:set var="threeSSsum" value="0"></c:set>
            <c:set var="sixSSsum" value="0"></c:set>
            <c:set var="teSSsum" value="0"></c:set>
            <c:set var="ttSSsum" value="0"></c:set>
            <c:set var="allSSsum" value="0"></c:set>
            <c:forEach items="${spendingSummaryList}" var="item">
                <c:if test="${item.prodType eq 'CA-TREATMENT'}">
                    <tr>

                        <td>Treatment</td>
                        <td>${item.oneMonth }<c:set var="oneSSsum" value="${oneSSsum+item.oneMonth}"></c:set></td>
                        <td>${item.threeMonth }<c:set var="threeSSsum" value="${threeSSsum+item.threeMonth}"></c:set></td>
                        <td>${item.sixMonth }<c:set var="sixSSsum" value="${sixSSsum+item.sixMonth}"></c:set></td>
                        <td>${item.twelveMonth }<c:set var="teSSsum" value="${teSSsum+item.twelveMonth}"></c:set></td>
                        <td>${item.twentyFourMonth }<c:set var="ttSSsum" value="${ttSSsum+item.twentyFourMonth}"></c:set></td>
                        <td>${item.allMonth }<c:set var="allSSsum" value="${allSSsum+item.allMonth}"></c:set></td>
                    </tr>
                </c:if>
                <c:if test="${item.prodType eq 'CA-GOODS'}">
                    <tr>

                        <td>Goods</td>
                        <td>${item.oneMonth }<c:set var="oneSSsum" value="${oneSSsum+item.oneMonth}"></c:set></td>
                        <td>${item.threeMonth }<c:set var="threeSSsum" value="${threeSSsum+item.threeMonth}"></c:set></td>
                        <td>${item.sixMonth }<c:set var="sixSSsum" value="${sixSSsum+item.sixMonth}"></c:set></td>
                        <td>${item.twelveMonth }<c:set var="teSSsum" value="${teSSsum+item.twelveMonth}"></c:set></td>
                        <td>${item.twentyFourMonth }<c:set var="ttSSsum" value="${ttSSsum+item.twentyFourMonth}"></c:set></td>
                        <td>${item.allMonth }<c:set var="allSSsum" value="${allSSsum+item.allMonth}"></c:set></td>
                    </tr>
                </c:if>
            </c:forEach>
            <tr>
                <td></td>
                <td><b>${oneSSsum }</b></td>
                <td><b>${threeSSsum }</b></td>
                <td><b>${sixSSsum }</b></td>
                <td><b>${teSSsum }</b></td>
                <td><b>${ttSSsum }</b></td>
                <td><b>${allSSsum }</b></td>
            </tr>
            </tbody>
        </table></div>
</div>
 <div class="col-sm-12">

                    <div class="col-lg-6 col-sm-12">
                    <table class="table table-striped table-hover">
                        <caption><spring:message code="label.svoc.treatment.purchased"/></caption>
                        <thead>
                        <tr>
                            <th><spring:message code="label.shop"/></th>
                            <!-- <th>Category</th> -->
                            <th><spring:message code="label.svoc.total.sales"/></th>
                            <th><spring:message code="label.svoc.total.units"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="tssum" value="0"></c:set>
                        <c:set var="ttsum" value="0"></c:set>
                        <c:forEach items="${treatmentSalesAnalysis}" var="item">
                            <tr>
                                <td>${item.shopName }</td>
                                <%-- <td>${item.prodType }</td> --%>
                                <td>${item.totalSalesValue }<c:set var="tssum" value="${tssum+ item.totalSalesValue}"></c:set></td>
                                <td>${item.totalTreatments }<c:set var="ttsum" value="${ttsum+ item.totalTreatments}"></c:set></td>
                            </tr>
                        </c:forEach>
                        	<tr>
                                <td><b><spring:message code="label.total"/></b></td>
                                <!-- <td></td> -->
                                <td>${tssum}</td>
                                <td>${ttsum}</td>
                            </tr>
                        </tbody>
                    </table></div>
     <div class="col-lg-6 col-sm-12">
         <table class="table table-striped table-hover">
             <caption><spring:message code="label.NotShowSales"/></caption>
             <thead>
             <tr>
                 <th><spring:message code="label.date"/></th>
                 <th><spring:message code="label.shop"/></th>
                 <th><spring:message code="label.client"/></th>
                 <th><spring:message code="label.treatment"/></th>
                 <th><spring:message code="label.total.amount"/></th>
             </tr>
             </thead>
             <tbody>
             <c:forEach items="${purchaseItemList}" var="pi">
                 <tr>
                     <td>${pi.purchaseOrder.purchaseDate}</td>
                     <td>${pi.purchaseOrder.shop.name }</td>
                     <td>${pi.purchaseOrder.user.fullName }</td>
                     <td>${pi.productOption.label33 }</td>
                     <td>${pi.purchaseOrder.totalAmount }</td>
                 </tr>
             </c:forEach>
             </tbody>
         </table>
     </div>
</div>
                  <div class="col-sm-12">

                  <div class="col-lg-6 col-sm-12">
                      <table class="table table-striped table-hover">
                          <caption><spring:message code="label.member.loyalty.points.transactions"/></caption>
                          <thead>
                          <tr>
                              <th><spring:message code="label.earn.date"/></th>
                              <th><spring:message code="label.expiry.date"/></th>
                              <th><spring:message code="label.earn.points"/></th>
                              <th><spring:message code="label.redeemed.points"/></th>
                              <th><spring:message code="label.earn.channel"/></th>
                              <th><spring:message code="label.remarks"/></th>
                          </tr>
                          </thead>

                          <tbody>
                          <c:forEach items="${lptList}" var="item">
                              <tr>
                                  <td><fmt:formatDate value="${item.earnDate}" pattern="yyyy-MM-dd"/></td>
                                  <td><fmt:formatDate value="${item.expiryDate}" pattern="yyyy-MM-dd"/></td>
                                  <td>${item.earnPoints }</td>
                                  <td>${item.redeemedPoints }</td>
                                  <td>${item.earnChannel }</td>
                                  <td>${item.remarks }</td>
                              </tr>
                          </c:forEach>
                          </tbody>
                      </table></div>
                    <div class="col-lg-6 col-sm-12"><table class="table table-striped table-hover">
                      <caption><spring:message code="label.member.family.details"/></caption>
                      <thead>
                      <tr>
                          <th><spring:message code="label.name"/></th>
                          <th><spring:message code="label.email"/></th>
                          <th><spring:message code="label.tel"/></th>
                      </tr>
                      </thead>
                      <tbody>
                      <c:forEach items="${familyDetailsList}" var="item">
                          <tr>
                              <td>${item.name }</td>
                              <td>${item.email }</td>
                              <td>${item.telNum }</td>
                          </tr>
                      </c:forEach>
                      </tbody>
                  </table>
                  </div>
                  </div>
                </td>
            </tr>
        </c:if>
    </table>
    </div>
</div>

