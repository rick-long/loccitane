<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="container lo-p-v-none">
    <div class="row">
        <div class="figures_box col-lg-3 col-md-6 col-sm-6">
            <div class="total_revenue">
                <span class="blue-bg"><spring:message code="label.totalrevenue"/></span>
                <span class="ct-7-days">Today</span>
            </div>
            <div class="figures">
                <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalFigureVO.totalRevenue}"/>
            </div>
        </div>
        <div class="figures_box col-lg-3 col-md-6 col-sm-6 ">
            <div class="total_revenue">
                <span class="red-bg"><spring:message code="label.shop.manager.total.services"/></span>
                <span class="ct-7-days">Today</span>
            </div>
            <div class="figures">
                <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalFigureVO.totalServices}"/>
            </div>
        </div>
        <div class="figures_box col-lg-3 col-md-6 col-sm-6">
            <div class="total_revenue">
                <span class="yellow-bg"><spring:message code="label.shop.manager.total.packages"/></span>
                <span class="ct-7-days">Today</span></div>
            <div class="figures">
                <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalFigureVO.totalPackages}"/>
            </div>
        </div>
        <%--<div class="figures_box col-lg-3 col-md-6 col-sm-6">--%>
            <%--<div class="total_revenue">--%>
                <%--<span class="orange-bg"><spring:message code="label.shop.manager.total.retail"/></span>--%>
                <%--<span class="ct-7-days">Today</span></div>--%>
            <%--<div class="figures">--%>
                <%--<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${totalFigureVO.totalRetail}"/>--%>
            <%--</div>--%>
        <%--</div>--%>

        <div class="figures_box col-lg-3 col-md-6 col-sm-6">
            <div class="total_revenue">
                <span class="green-bg"> <spring:message code="front.label.bookings"/></span>
                <span class="ct-7-days">Today</span></div>
            <div class="figures">
                ${totalFigureVO.bookings}
            </div>
        </div>

        <div class="figures_box col-lg-3 col-md-6 col-sm-6" >
            <div class="total_revenue">
                <span class="deepbule-bg"><spring:message code="label.book.waiting.list"/></span>
                <span class="ct-7-days">Today</span>
            </div>
            <div class="figures">
                <a id="submit" type="submit" data-permission="book:toView" href='<c:url value="/book/toView?flag=1"/>'>${totalFigureVO.waitingList}</a>
            </div>
        </div>


        <%--<div class="figures_box col-lg-3 col-md-6 col-sm-6">--%>
            <%--<div class="total_revenue">--%>
                <%--<span class="lgihtdrak-bg"><spring:message code="label.shop.manager.walk.ins"/></span>--%>
                <%--<span class="ct-7-days">Today</span>--%>
            <%--</div>--%>
            <%--<div class="figures">--%>
                <%--${totalFigureVO.walkIns}--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="figures_box col-lg-3 col-md-6 col-sm-6">
            <div class="total_revenue">
                <span class="grey-bg"><spring:message code="label.book.status.NOT_SHOW"/></span>
                <span class="ct-7-days">Today</span></div>
            <div class="figures">
                ${totalFigureVO.noShows}
            </div>
        </div>

    </div>
</div>
<script>
    $('#submit', Dialog.getContext()).click(function (e) {
        e.preventDefault();
        window.location.href= '<c:url value="/"/>?loadingUrl=' + $(this).attr('href');
    });

</script>
