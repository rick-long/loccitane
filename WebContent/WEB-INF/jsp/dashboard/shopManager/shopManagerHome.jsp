<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<jsp:useBean id="now" class="java.util.Date"/>
<div class="big-title-area container"> 
  <div class="row">
     <div class="col-lg-12">
     <h1 class="text-h1-white"><spring:message code="label.home.view"/></h1>
     </div>
  </div>
</div>
<div class="management container">
    <form method="post" class="form-horizontal">
      <div class="home_search">
      <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-12">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.date"/></label>
            <br/><fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>
                </div></div>
        <div class="col-lg-3 col-sm-12">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.shop"/></label>
          <select id="shopId" name="shopId" class="form-control">
                	<c:if test="${showAll}">
                    	<option value=""><spring:message code="label.option.select.all"/></option>
                    </c:if>
                    <c:forEach items="${shopList}" var="item">
                        <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                    </c:forEach>
                </select></div>
            </div>
         </div>
       </div> 
        </div>
        <div class="detail_home">
            <ul class="nav nav-tabs" id="myTab">
                <li class="active"><a href="#daily"><spring:message code="label.shop.manager.daily"/> </a></li>
            </ul>
            <div class="tab-content">
                <div id="daily" class="tab-pane fade in active">
                    <div id="upComingBookings">

                    </div>
                    <div id="todayFigures">

                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(document).ready(function () {


        $("#shopId", getContext()).change(function () {
            var selectedShop = $(this).val();
            var param = {};
            if (selectedShop) {
                param.shopId = selectedShop;
            }
            $.post('<c:url value="/dashboard/toShopManagerHome"/>', param, function (res) {
                getContext().html(res);
            });
        });

        // 异步加载upComingBookings的数据
        var param = {
            shopId: $("#shopId", getContext()).val(),
            dateMillis: ${now.time}
        };
        $.post('<c:url value="/dashboard/upComingBookings"/>', param, function (res) {
            $('#upComingBookings', getContext()).html(res);
        });

        // 异步加载today Figures的数据
        $.post('<c:url value="/dashboard/toTotalFigures"/>', param, function (res) {
            $('#todayFigures', getContext()).html(res);
        })
    });
</script>