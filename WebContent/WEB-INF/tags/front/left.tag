<%@tag pageEncoding="UTF-8" %>
<div id="left-navigate-collapse-menu" class="sidebar-nav navbar-collapse">
    <ul class="nav" id="side-menu">
        <li class="welcome">
            <a href='<c:url value="/front/index"/>'>
                <i class="glyphicon glyphicon-calendar"></i> Hello ${user.fullName}
                <span class="fa arrow"></span>
            </a>
        </li>
    	<li>
            <a href='<c:url value="/front/index"/>'>
                <i class="glyphicon glyphicon-calendar"></i><spring:message code="left.navbar.home"/>
                <span class="fa arrow"></span>
            </a>
        </li>
        <li>
            <a href="<c:url value="/front/book/toAdd"/>">
                <i class="glyphicon glyphicon-calendar"></i><spring:message code="label.book.channel.ONLINE"/>
                <span class="fa arrow"></span>
            </a>
        </li>

       <li>
            <a href="<c:url value="/front/prepaid/voucherAddMain"/>">
                <i class="glyphicon glyphicon-calendar"></i><spring:message code="front.label.buy.gift.voucher"/>
                <span class="fa arrow"></span>
            </a>
        </li>
       	<li>
            <a href='<c:url value="/front/prepaid/myPrepaid"/>'>
                <i class="glyphicon glyphicon-calendar"></i><spring:message code="front.label.my.package.voucher"/>
                <span class="fa arrow"></span>
            </a>
        </li>
        <li>
            <a href='<c:url value="/front/book/myBookings"/>'>
                <i class="glyphicon glyphicon-calendar"></i><spring:message code="front.label.my.bookings"/>
                <span class="fa arrow"></span>
            </a>
        </li>
       <li>
            <a href='<c:url value="/front/profile"/>'>
                <i class="glyphicon glyphicon-calendar"></i><spring:message code="front.label.my.profile"/>
                <span class="fa arrow"></span>
            </a>
        </li>
        <li>
            <a href='<c:url value="/front/toMyLoyalty"/>'>
                <i class="glyphicon glyphicon-calendar"></i><spring:message code="front.label.my.loyalty"/>
                <span class="fa arrow"></span>
            </a>
        </li>
        <li>
            <a href='<c:url value="/front/toChangePwd"/>'>
                <i class="glyphicon glyphicon-calendar"></i><spring:message code="label.change.password"/>
                <span class="fa arrow"></span>
            </a>
        </li>
        <li class="logout">
            <a href='<c:url value="/memberLogout"/>'>
          <i class="glyphicon glyphicon-off"></i><spring:message code="header.logout"/>
            </a>
        </li>
    </ul>
</div>
