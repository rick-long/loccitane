<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%--<%
    User user = (User) request.getAttribute("user");
    pageContext.setAttribute("user", user);
%>--%>

<div id="menuContext" class="sidebar-nav navbar-collapse">
    <div class="collapse-button"><i class="	glyphicon glyphicon-list"></i></div>
    <ul class="nav hide" id="side-menu">
       <li class="login_mobile">
       <span><a href="?language=zh"><img src="<c:url value='/resources/img/zh_CN.png'/>"/></a> | <a href="?language=en"><img src="<c:url value='/resources/img/en_US.png'/>"/></a></span></li>

        <li class="active firstMenu">
            <c:choose>
                <c:when test="${user.hasShopManageRole()}">
                    <a href='<c:url value="/dashboard/toShopManagerHome?shopId=${user.staffHomeShops.iterator().next().id}"/>'>
                        <i class="glyphicon glyphicon-dashboard"></i> <spring:message code="left.navbar.home"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href='#'>
                        <i class="glyphicon glyphicon-home"></i> <spring:message code="left.navbar.home"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </li>
        <li data-permission="bookModule">
            <a href="#">
                <i class="glyphicon glyphicon-calendar"></i> <spring:message code="left.navbar.book"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a id="bookListMenu" data-permission="book:toView" href='<c:url value="/book/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.book.booklist"/>
                    </a>
                </li>
                <li>
                    <a id="bookTimeTherapistViewMenu" data-need-reload="true" data-permission="book:bookTimeTherapistView" href='<c:url value="/book/bookTimeTherapistView"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.calendar.therapist.view"/>
                    </a>
                </li>
                <li>
                    <a id="bookTimeRoomViewMenu" data-need-reload="true" data-permission="book:bookTimeRoomView" href='<c:url value="/book/bookTimeRoomView"/>'>
                        <i class="glyphicon glyphicon-record"></i>   <spring:message code="left.navbar.calendar.room.view"/>
                    </a>
                </li>
                <li>
                    <a data-permission="bookBatch:toAddBookBatch" id="addBatchBookingMenu" href='<c:url value="/bookBatch/toAddBookBatch"/>'>
                        <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.book.batch.add"/>
                    </a>
                </li>
                <li>
                    <a data-permission="bookBatch:toListBookBatch" id="listBatchBookingMenu" href='<c:url value="/bookBatch/toListBookBatch"/>'>
                        <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.book.batch.list"/>
                    </a>
                </li>
            </ul>
        </li>

        <li data-permission="memberModule">
            <a href="#">
                <i class="glyphicon glyphicon-user"></i> <spring:message code="left.navbar.member"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <%-- <li>
                     <a data-permission="communicationChannel:toView" href='<c:url value="/communicationChannel/toView"/>'>
                         <i class="glyphicon glyphicon-record"></i>   <spring:message code="left.navbar.communicationChannel.communicationChannellist"/>
                     </a>
                 </li>--%>
                <li>
                    <a id="memberListMenu" data-permission="member:toView" href='<c:url value="/member/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.member.memberlist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="member:toGroupView" href='<c:url value="/member/toGroupView"/>'>
                        <i class="glyphicon glyphicon-record"></i>   <spring:message code="left.navbar.member.group.list"/>
                    </a>
                </li>

                <%-- <li>
                    <a data-permission="member:toAdvanceView" href='<c:url value="/member/toAdvanceView"/>'>
                       <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.member.advance.list"/>
                    </a>
                </li> --%>
            </ul>
        </li>
        <li data-permission="salesModule">
            <a href="#">
                <i class="glyphicon glyphicon-list-alt"></i> <spring:message code="left.navbar.sales"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a id="salesListMenu" data-permission="sales:toView" href='<c:url value="/sales/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.sales.saleslist"/>
                    </a>
                </li>
                <li>
                    <a id="reviewMenu" data-permission="review:evaluation" href='<c:url value="/review/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.review.and.rating"/>
                    </a>
                </li>
                <li>
                    <a id="surveyListMenu" data-permission="survey:toView" href='<c:url value="/survey/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i><spring:message code="left.navbar.survey.list"/>
                    </a>
                </li>
            </ul>
        </li>
        <li data-permission="attendanceModule">
            <a href="#"><i class="glyphicon glyphicon-briefcase"></i> <spring:message code="left.navbar.attendance"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a id="staffClockInOutMenu" data-permission="attendance:toAddClock" href='<c:url value="/staff/toAddClock"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.attendance.clock"/>
                    </a>
                </li>
                <li><%--attendanceRecord代替toView--%>
                    <a id="clockView" data-permission="attendance:attendanceRecord" href='<c:url value="/staff/toClockView"/>'>
                        <i class="glyphicon glyphicon-record"></i>   <spring:message code="left.navbar.attendance.records"/>
                    </a>
                </li>
            </ul>
            <!-- /.nav-second-level -->
        </li>

        <li data-permission="staffModule">
            <a href="#"><i class="glyphicon glyphicon-user"></i> <spring:message code="left.navbar.staff"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a id="staffListMenu" data-permission="staff:toView" href='<c:url value="/staff/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.staff.stafflist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="staff:toGroupView" href='<c:url value="/staff/toGroupView"/>'>
                        <i class="glyphicon glyphicon-record"></i>   <spring:message code="left.navbar.staff.group.list"/>
                    </a>
                </li>
            </ul>
            <!-- /.nav-second-level -->
        </li>
        <li data-permission="prepaidModule">
            <a href="#"><i class="glyphicon glyphicon-credit-card"></i>
                <spring:message code="left.navbar.prepaid"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="prepaid:toView" href='<c:url value="/prepaid/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.prepaid.prepaidlist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="prepaid:toImportPrepaidGif" href='<c:url value="/prepaid/toImportPrepaidGif"/>?module=GIFT'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.prepaid.gift"/>
                    </a>
                </li>
            </ul>
        </li>

        <li data-permission="productModule">
            <a href="#"><i class="glyphicon glyphicon-barcode"></i>
                <spring:message code="left.navbar.product"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="brand:toView" href='<c:url value="/brand/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.product.brandlist"/>
                    </a>
                </li>
                <%--<li>--%>
                <%--<a data-permission="supplier:toView" href='<c:url value="/supplier/toView"/>'>--%>
                    <%--<i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.product.supplierlist"/>--%>
                <%--</a>--%>
                <li>
                    <a data-permission="category:toView" href='<c:url value="/category/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.product.categorylist"/>
                    </a>
                </li>
                <%--<li>--%>
                    <%--<a data-permission="product:toView" href='<c:url value="/product/toView"/>'>--%>
                        <%--<i class="glyphicon glyphicon-record"></i>    <spring:message code="left.navbar.product.productlist"/>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <li>
                    <a data-permission="product:toView" href='<c:url value="/treatment/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.treatment.treatmentlist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="bundle:toBundleView" href='<c:url value="/bundle/toBundleView"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.bundle.bundleList"/>
                    </a>
                </li>
                <%-- <li>
                    <a data-permission="product:toView" href='<c:url value="/hairsalon/toView"/>'>
                       <i class="glyphicon glyphicon-record"></i> Hair Salon List
                    </a>
                </li> --%>
                <%-- <li>
                    <a data-permission="product:toQuickSearchProduct" href='<c:url value="/product/toQuickSearchProduct"/>'>
                        <i class="glyphicon glyphicon-record"></i> Quick Search Product
                    </a>
                </li> --%>
            </ul>
        </li>
        <li data-permission="shopModule">
            <a href="#"><i class="glyphicon glyphicon-map-marker"></i>
                <spring:message code="left.navbar.shop"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="shop:toView" href='<c:url value="/shop/toView"/>'>
                       <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.shop.shoplist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="room:toView" href='<c:url value="/room/toView"/>'>
                        <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.shop.roomlist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="consentForm:toView" href='<c:url value="/consentForm/toView"/>'>
                     <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.consent.form.list"/>
                    </a>
                </li>
            </ul>
        </li>


        <%--<li data-permission="discountModule">
            <a href="#">
                <i class="glyphicon glyphicon-usd"></i> <spring:message code="left.navbar.discount"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="discount:toRuleView" href='<c:url value="/discount/toRuleView"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.discount.rule.list"/>
                    </a>
                </li>
            </ul>
        </li>--%>



        <%--<li data-permission="payrollModule">
            <a href="#">
                <i class="glyphicon glyphicon-list"></i> <spring:message code="left.navbar.payroll"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="payroll:toView" href='<c:url value="/payroll/toView"/>'>
                      <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.payroll.list"/>
                    </a>
                </li>
            </ul>
        </li>--%>

        <%--<li data-permission="inventoryModule">
            <a href="#">
                <i class="glyphicon glyphicon-hdd"></i> <spring:message code="left.navbar.inventory"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a id="inventoryPurchaseOrderListMenu" data-permission="inventory:purchaseOrderManagement" href='<c:url value="/inventory/purchaseOrderManagement"/>'>
                      <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.inventory.purchase.order.list"/>
                    </a>
                </li>
                <li>
                    <a data-permission="inventory:transactionManagement" href='<c:url value="/inventory/transactionManagement"/>'>
                      <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.inventory.transaction.list"/>
                    </a>
                </li>
                <li>
                    <a data-permission="inventory:toView" href='<c:url value="/inventory/toView"/>'>
                      <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.inventory.list"/>
                    </a>
                </li>
            </ul>
        </li>--%>
        <%--<li data-permission="loyaltyModule">
            <a href="#">
                <i class="glyphicon glyphicon-certificate"></i> <spring:message code="left.navbar.loyalty"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="loyalty:toView" href='<c:url value="/loyalty/toView"/>'>
                       <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.loyalty.level.list"/>
                    </a>
                </li>
                <li>
                    <a data-permission="lp:toView" href='<c:url value="/lp/toView"/>'>
                      <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.loyalty.points.list"/>
                    </a>
                </li>
               &lt;%&ndash;  <li>
                    <a data-permission="sp:toView" href='<c:url value="/sp/toView"/>'>
                        <spring:message code="left.navbar.spa.points.list"/>
                    </a>
                </li> &ndash;%&gt;
                <li>
                    <a data-permission="awardRedemption:toView" href='<c:url value="/awardRedemption/toView"/>'>
                       <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.award.redemption.list"/>
                    </a>
                </li>
            </ul>
        </li>--%>
         <%--<li data-permission="bonusModule">
            <a href="#">
                <i class="glyphicon glyphicon-usd"></i> <spring:message code="left.navbar.bonus"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="bonus:toRuleView" href='<c:url value="/bonus/toRuleView"/>'>
                        <spring:message code="left.navbar.bonus.rule.list"/>
                    </a>
                </li>
            </ul>
        </li>
        <li data-permission="marketingModule">
            <a href="#">
                <i class="glyphicon glyphicon-stats"></i> <spring:message code="left.navbar.marketing"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="marketing:toMktChannelView" href='<c:url value="/marketing/toMktChannelView"/>'>
                        <spring:message code="left.navbar.channel.list"/>
                    </a>
                </li>
                <li>
                    <a data-permission="marketing:toMktMailShotView" href='<c:url value="/marketing/toMktMailShotView"/>'>
                        <spring:message code="left.navbar.mail.shot.list"/>
                    </a>
                </li>
                <li>
                    <a data-permission="marketing:toMktEmailTemplateView" href='<c:url value="/marketing/toMktEmailTemplateView"/>'>
                        <spring:message code="left.navbar.marketing.email.template.list"/>
                    </a>
                </li>
            </ul>
        </li>--%>
		<li data-permission="reportModule">
            <a href="#">
                <i class="glyphicon glyphicon-stats"></i> <spring:message code="left.navbar.report"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
		            <a data-permission="report:toSalesDetails" href='<c:url value="/report/toSalesDetails"/>'>
		             	<i class="glyphicon glyphicon-record"></i><spring:message code="left.navbar.report.salesdetails"/>
		           	</a>
       			</li>
       			<li>
		            <a data-permission="report:toSalesDetailsForStaff" href='<c:url value="/report/toSalesDetailsForStaff"/>'>
		             	<i class="glyphicon glyphicon-record"></i><spring:message code="left.navbar.report.salesdetailsforstaff"/>
		           	</a>
       			</li>
	       		<li>
	           		<a data-permission="report:toCustomer" href='<c:url value="/report/toCustomer"/>'>
	         			<i class="glyphicon glyphicon-record"></i><spring:message code="left.navbar.report.customer"/>
	                </a>
	            </li>
	            <li>
	           		<a data-permission="report:toPrepaidOutstanding" href='<c:url value="/report/toPrepaidOutstanding"/>'>
	            		<i class="glyphicon glyphicon-record"></i><spring:message code="left.navbar.report.prepaid.outstanding"/>
	                </a>
	           	</li>
	           	<li>
	           		<a data-permission="report:toDailyReportForShopManager" href='<c:url value="/report/toDailyReportForShopManager"/>'>
	            		<i class="glyphicon glyphicon-record"></i><spring:message code="left.navbar.report.shopmanagerdaily"/>
	                </a>
	           	</li>
	           	<li>
	           		<a data-permission="report:toDailyReportForSOTManager" href='<c:url value="/report/toDailyReportForSOTManager"/>'>
	            		<i class="glyphicon glyphicon-record"></i><spring:message code="left.navbar.report.sotmanagerdaily"/>
	                </a>
	           	</li>
               <%--  <li>
                    <a data-permission="report:toComplete" href='<c:url value="/report/toComplete"/>'>
                        <spring:message code="left.navbar.report.complete"/>
                    </a>
                </li>
               
                <li>
                    <a data-permission="report:toCapelliRevenue" href='<c:url value="/report/toCapelliRevenue"/>'>
                        <spring:message code="left.navbar.report.prepaid.outstanding"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toHotelCustomer" href='<c:url value="/report/toHotelCustomer"/>'>
                        <spring:message code="left.navbar.report.hotel.customer"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toSalesRevenue" href='<c:url value="/report/toSalesRevenue"/>'>
                        <spring:message code="left.navbar.report.sales.renenue"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toInventory" href='<c:url value="/report/toInventory"/>'>
                        <spring:message code="left.navbar.report.inventory"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toPrepaid" href='<c:url value="/report/toPrepaid"/>'>
                        <spring:message code="left.navbar.report.prepaid"/>
                    </a>
                </li>
                
                <li>
                    <a data-permission="report:toPayroll" href='<c:url value="/report/toPayroll"/>'>
                        <spring:message code="left.navbar.report.payroll"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toTherapistMonthlyTarget" href='<c:url value="/report/toTherapistMonthlyTarget"/>'>
                        <spring:message code="left.navbar.report.therapist.monthly.target"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toHotelOrNon" href='<c:url value="/report/toHotelOrNon"/>'>
                        <spring:message code="left.navbar.report.hotel.or.non"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toRequestClientByShop" href='<c:url value="/report/toRequestClientByShop"/>'>
                        <spring:message code="left.navbar.report.request.client.byshop"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toRequestByTerapistByTerapist" href='<c:url value="/report/toRequestByTerapistByTerapist"/>'>
                        <spring:message code="left.navbar.report.request.client.bytherapist.bytherapist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toRequestByTherapistByShop" href='<c:url value="/report/toRequestByTherapistByShop"/>'>
                        <spring:message code="left.navbar.report.request.client.bytherapist.byshop"/>
                    </a>
                </li>
                <li>
                    <a data-permission="report:toRequestByTherapistLeagueTable" href='<c:url value="/report/toRequestByTherapistLeagueTable"/>'>
                        <spring:message code="left.navbar.report.request.client.bytherapist.leaguetable"/>
                    </a>
                </li> --%>
            </ul>
        </li>
        <li data-permission="commissionModule">
            <a href="#">
                <i class="glyphicon glyphicon-tag"></i> <spring:message code="left.navbar.commission"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="commission:toRuleView" href='<c:url value="/commission/toRuleView"/>'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.commission.rule.list"/>
                    </a>
                </li>
            </ul>
        </li>
       <%--  <li data-permission="salesforceModule">
            <a href="#">
                <i class="glyphicon glyphicon-hdd"></i>Salesforce
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="salesforceData:memberDetailsImport" href='<c:url value="/salesforceData/memberDetailsImport"/>'>
                      	<i class="glyphicon glyphicon-record"></i>(LIVE) Member Details Import
                    </a>
                </li>
                <li>
                    <a data-permission="sfDataDEMO:memberDetailsImport" href='<c:url value="/sfDataDEMO/memberDetailsImport"/>'>
                      	<i class="glyphicon glyphicon-record"></i>(DEMO) Member Details Import
                    </a>
                </li>
            </ul>
        </li> --%>
        <li data-permission="importModule">
            <a href="#"><i class="glyphicon glyphicon-credit-card"></i>
                <spring:message code="left.navbar.import"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="import:toView" href='<c:url value="/importDemo/toView"/>?module=STAFF'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.staff.import"/>
                    </a>
                </li>
                <li>
                    <a data-permission="import:toView" href='<c:url value="/importDemo/toView"/>?module=PRODUCT'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.product.import"/>
                    </a>
                </li>
                <li>
                    <a data-permission="import:toView" href='<c:url value="/importDemo/toView"/>?module=PREPAID'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.prepaid.import"/>
                    </a>
                </li>
                <li>
                    <a data-permission="import:toView" href='<c:url value="/importDemo/toView"/>?module=MEMBER'>
                        <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.member.import"/>
                    </a>
                </li>
            </ul>
        </li>
        <li data-permission="sysCfgModule">
            <a href="#">
                <i class="glyphicon glyphicon-wrench"></i> <spring:message code="left.navbar.system.role.config"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <li>
                    <a data-permission="sysRole:sysRoleManagement" href='<c:url value="/sysRole/sysRoleManagement"/>'>
                       <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.system.role.management"/>
                    </a>
                </li>
                <%--<li>--%>
                    <%--<a href='<c:url value="/cache/toClearView"/>'>--%>
                      <%--<i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.clear.cache"/>--%>
                    <%--</a>--%>
                <%--</li>--%>
            </ul>
        </li>
        <li data-permission="sysSettingsModule">
            <a href="#">
                <i class="glyphicon glyphicon-cog"></i> <spring:message code="left.navbar.system.settings"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">
                <%--<li>
                    <a data-permission="outSource:toTemplateView" href='<c:url value="/outSource/toTemplateView"/>'>
                      <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.outSource.template.list"/>
                    </a>
                </li>--%>
                <li>
                    <a data-permission="pokey:toView" href='<c:url value="/pokey/toView"/>'>
                       <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.product.poKeylist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="pdkey:toView" href='<c:url value="/pdkey/toView"/>'>
                      <i class="glyphicon glyphicon-record"></i>   <spring:message code="left.navbar.product.pdKeylist"/>
                    </a>
                </li>
                <li>
                    <a data-permission="commission:toView" href='<c:url value="/commission/toView"/>'>
                      <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.commission.template.list"/>
                    </a>
                </li>
                <%--<li>
                    <a data-permission="payroll:toTemplateView" href='<c:url value="/payroll/toTemplateView"/>'>
                      <i class="glyphicon glyphicon-record"></i>  <spring:message code="left.navbar.payroll.template.list"/>
                    </a>
                </li>--%>
                <%--<li>
                    <a data-permission="discount:toView" href='<c:url value="/discount/toView"/>'>
                       <i class="glyphicon glyphicon-record"></i> <spring:message code="left.navbar.discount.template.list"/>
                    </a>
                </li>--%>
                <%-- <li>
                    <a data-permission="bonus:toView" href='<c:url value="/bonus/toView"/>'>
                        <spring:message code="left.navbar.bonus.template.list"/>
                    </a>
                </li> --%>
            </ul>
        </li>
        <li>
            <a href="#"><i class="glyphicon glyphicon-paperclip"></i>
                <spring:message code="header.my.account"/>
                <span class="fa arrow"></span>
            </a>
            <ul class="nav nav-second-level">

                <li>
                    <a href='<c:url value="toChangePassword"/>' class="dialog" data-draggable="true" data-title='<spring:message code="label.change.password"/>'>&nbsp;
                        <span class="glyphicon glyphicon-lock"></span>
                        <spring:message code="label.change.password"/>
                    </a>
                </li>
                <li>
                    <a href='<c:url value="/staffLogout"/>'>
                        <i class="glyphicon glyphicon-off"></i>&nbsp;<spring:message code="header.logout"/>
                    </a>
                </li>
            </ul>
        </li>
        <%-- 测试代码 --%>
        <%-- <c:if test="${user.username eq 'admin' or user.username eq 'AD_MIN'}">
            <li>
                <a href="#">
                    <i class="glyphicon glyphicon-align-justify"></i> Test Management
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href='<c:url value="/test/testManagement"/>'>
                            Test
                        </a>
                    </li>
                    <li>
                        <a href='<c:url value="/test/testSelectCategory2"/>'>
                            Test Select Category 2
                        </a>
                    </li>
                    <li>
                        <a href='/patch/memberDivaBetween2sys?fromDate=2017-01-05&endDate=2017-01-07'>
                            Patch Member Diva
                        </a>
                    </li>
                    <li>
	                    <a href='<c:url value="/test/testMemberDetailsExportCSV"/>'>
	                       Test export csv
	                    </a>
	                </li>
	                <li>
	                    <a href='<c:url value="/test/patchMemberLastUpdatedDate"/>'>
	                       patch
	                    </a>
	                </li>
                </ul>
            </li>
        </c:if> --%>
    </ul>
</div>
<script type="text/javascript">
    $(function () {
	    $('#side-menu').find('a[href!=#][class!=dialog]').click(function (e) {
            e.preventDefault();
	        window.location.href= '<c:url value="/"/>?loadingUrl=' + $(this).attr('href');
        });

        $('.collapse-button').click(function () {
            $("body").toggleClass("collapsed");
            $('.collapse-button i').toggleClass("glyphicon-circle-arrow-right");
            // 是否需要重新加载
            var selectedMenu = $('#side-menu').find('a.selected');
            if (selectedMenu.length == 1 && selectedMenu.data('need-reload') == true) {
                setTimeout(function () {
                    selectedMenu.trigger('click');
                }, 300);
            }
        });
    });
</script>
