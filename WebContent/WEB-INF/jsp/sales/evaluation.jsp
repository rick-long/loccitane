<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%--<link href='<c:url value="/resources/review/css/normalize.css"/>' rel="stylesheet">--%>
<link href='<c:url value="/resources/review/css/style.css"/>' rel="stylesheet">
<style>
    .row{
        margin-left:unset !important;
        margin-right:unset !important;
    }
    .p-b-20{
        padding-bottom:20px;
    }
    .panel-body h5{
        margin-top:33px;
    }

    @media(min-width: 768px){
        .negative_pos2{
            padding-right:27px;
        }
        .review_pos2{
        padding-left:27px;
        }
        .number_pos2{
            padding-left: 26px !important;
            padding-right:16px;
        }
        .conversion_pos2{
            padding-right:27px;
        }
        .sentiment_pos2{
            padding-left:26px;
            padding-right:17px;
        }
        .breakdown_pos2{
            padding-right:27px;
        }
    }
    @media(min-width: 1550px){
        .co-lg-2{
        width:16.66666667%;
        float:left;
        }
        .review_pos{
        padding-left:30px;
        }
        .negative_pos{
        padding-right:11px;
        }
        .number_pos{
        padding-left:19px !important;
        }
        .conversion_pos{
            padding-right:19px !important;
        }
        .sentiment_pos{
            padding-left:11px;
            padding-right:18px;
        }
        .breakdown_pos{
            padding-right:30px;
        }
    }
</style>
<%--<link href='<c:url value="/resources/css/style.css"/>' rel="stylesheet">--%>
<%--<script src="/resources/Chart.js-master/src/chart.js"></script>--%>

<%--<ul id="myTab" class="nav nav-tabs">
	<li class="active"><a href="#home" data-toggle="tab">Dashboard</a></li>
	<li><a onclick=javascript:window.location.href="/?loadingUrl=/review/listingToView" data-toggle="tab" >Listing</a></li>

</ul>--%>
<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade in active" id="home">
<%--  <div class="management">
      <form method="post" class="form-horizontal" action="/?loadingUrl=/review/evaluation">
      <div class="home_search">
      <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-4 col-sm-12">
          <div class="form-group col-md-12">
          <label class="control-label">Reporting Period：</label><br/>
           <button type="button" class="btn btn-primary" 
    data-toggle="button" onclick=javascript:window.location.href="/?loadingUrl=/review/evaluation?date=year">Past year
</button> <button type="button" class="btn btn-primary" 
    data-toggle="button" onclick=javascript:window.location.href="/?loadingUrl=/review/evaluation?date=6months"> Past 6months
</button> <button type="button" class="btn btn-primary" 
    data-toggle="button" onclick=javascript:window.location.href="/?loadingUrl=/review/evaluation?date=30Days">Past 30 Days
</button> <button type="button" class="btn btn-primary" 
    data-toggle="button" onclick=javascript:window.location.href="/?loadingUrl=/review/evaluation?date=7Days"> Past 7 Days
</button>
                </div>
                </div>
      &lt;%&ndash;
          <div class="col-lg-2 col-sm-12">
          <div class="form-group col-md-12"><label class="control-label">Custom：</label>
	         <div class="input-group date form_time">
		        	<input id="fromDate" name="fromDate" class="form-control fromDate"
		        		value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		        	<span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        	 </div>
	    </div>
         </div>&ndash;%&gt;


        &lt;%&ndash;  <div class="col-lg-2 col-sm-12">
          <div class="form-group col-md-12"><label class="control-label">to：</label>
	         <div class="input-group date form_time">
		        	<input id="toDate" name="toDate" class="form-control fromDate"
		        		value='<fmt:formatDate value="${toDate}" pattern="yyyy-MM-dd"/>' size="16">
		        	<span class="input-group-addon" id="toDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        	 </div>
	    </div>
         </div>
          <input type="submit"/>
          <div class="col-lg-2 col-sm-6">
              <div class="form-group col-md-12"><br/><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
                  <i class="glyphicon glyphicon-search"></i>
                  <spring:message code="label.button.search"/>
              </a></div>
          </div>
         </div> &ndash;%&gt;
        </div>
</div>
  </form>
  </div>--%>
  
 <!------- rating view --------->
<div class="management-box">
<div class="row">
 <div class="col-lg-4 col-sm-12"><div class="panel panel-default">
    <div class="panel-body">
       <h5><spring:message code="label.sales.customerservices"/> </h5>
       <h1>${reviewStatisticalVO.customerServicesAvg}</h1>
       <div align="center"><p class="star" data-bg="yellow" data-score="${reviewStatisticalVO.customerServicesAvg}"></p></div>
    </div>
</div></div>
 <div class="col-lg-4 col-sm-12"><div class="panel panel-default">
    <div class="panel-body">
         <h5><spring:message code="label.sales.cleanliness"/> </h5>
         <h1>${reviewStatisticalVO.shopCleanlinessAvg}</h1>
         <div align="center"><p class="star" data-bg="yellow" data-score="${reviewStatisticalVO.shopCleanlinessAvg}"></p></div>
    </div>
</div></div>
 <div class="col-lg-4 col-sm-12"><div class="panel panel-default p-b-20">
    <div class="panel-body">
         <h5><spring:message code="label.sales.nps"/> </h5>
         <h1>${reviewStatisticalVO.nps}%</h1>
    </div>
</div></div>
</div>
</div>

<!------- Dashboard view ---------> 

<div class="dashboard_line">
 <div class="co-lg-2 col-sm-6 review_pos review_pos2"><div class="panel panel-default">
    <div class="panel-body">
     <div class="Round">
      <h3>${reviewStatisticalVO.reviewRatingTreatmentCount}</h3>
     </div>
       <h5><spring:message code="label.sales.reviewreceired"/> </h5>
    </div>
</div></div>
 <div class="co-lg-2 col-sm-6 negative_pos  negative_pos2" ><div class="panel panel-default">
    <div class="panel-body">
      <div class="Round2">
      <h3><fmt:formatNumber type="number" pattern="#,##0" value="${reviewStatisticalVO.badReviewCount}"/></h3>
     </div>
      <h5><spring:message code="label.sales.negativereview"/> </h5>
    </div>
</div></div>
 
<div class="co-lg-2 col-sm-6 number_pos number_pos2" ><div class="panel panel-default">
    <div class="panel-body">
   <div class="Round3">
      <h3><fmt:formatNumber type="number" pattern="#,##0" value="${reviewStatisticalVO.orderCount}"/></h3>
     </div>
       <h5><spring:message code="label.sales.numberoftreatments"/> </h5>
    </div>
</div></div>
<div class="co-lg-2 col-sm-6 conversion_pos conversion_pos2" ><div class="panel panel-default">
    <div class="panel-body">
    <div class="circle" style="left:0">
		<div class="pie_left"><div class="pieleft"></div></div>
		<div class="pie_right"><div class="pieright"></div></div>
		<div class="piemask"><span>${reviewStatisticalVO.highPraisePercentage}</span>%</div>
	</div> 
       <h5><spring:message code="label.sales.reviewconversion"/> </h5>
    </div>
</div></div>
<div class="co-lg-2 col-sm-6 sentiment_pos sentiment_pos2" ><div class="panel panel-default">
    <div class="panel-body">
     <div class="canvas_center"><canvas id="chart" width="165" height="165"></canvas></div>
  <table id="chartData">
    <tr>
      <th><spring:message code="label.sales.widget"/> </th><th><spring:message code="label.sales.sales"/> </th>
     </tr>

    <tr style="color:#5eb0cb">
      <td><spring:message code="label.sales.superwidget"/> </td><td><%--${reviewStatisticalVO.highPraisePercentage}--%>${reviewStatisticalVO.reviewStar5+reviewStatisticalVO.reviewStar4}</td>
    </tr>

    <tr style="color: #f6ae13">
      <td><spring:message code="label.sales.megawidget"/> </td><td>${reviewStatisticalVO.reviewStar3}</td>
    </tr>

    <tr style="color: #FF7474">
      <td><spring:message code="label.sales.hyperwidget"/> </td><td>${reviewStatisticalVO.reviewStar1+reviewStatisticalVO.reviewStar2}</td>
    </tr>
  </table>
       <h5><spring:message code="label.sales.sentiment"/> </h5>
    </div>
</div></div>
<div class="co-lg-2 col-sm-6 breakdown_pos breakdown_pos2" ><div class="panel panel-default">
    <div class="panel-body">
      <div class="star-progress">
      <ul>
      <li>
       <div style="margin:0;padding:0 0 6px 0;text-align: left;"><img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"><span style="float:right">${reviewStatisticalVO.reviewStar5}%</span></div>
      <div class="progress">
	<div class="progress-bar progress-bar-success" role="progressbar"
		 aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
		 style="width:${reviewStatisticalVO.reviewStar5}%;">
		<span class="sr-only">${reviewStatisticalVO.reviewStar5}%</span>
	</div>
</div></li>
        <li>
        <div style="margin:0;padding:0 0 6px 0;text-align: left;"><img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <span style="float:right">${reviewStatisticalVO.reviewStar4}%</span></div>
        <div class="progress">
	<div class="progress-bar progress-bar-success" role="progressbar"
		 aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
		 style="width:${reviewStatisticalVO.reviewStar4}%;">
		<span class="sr-only">${reviewStatisticalVO.reviewStar4}%</span>
	</div>
</div></li>
         <li>
         <div style="margin:0;padding:0 0 6px 0;text-align: left;"><img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <span style="float:right">${reviewStatisticalVO.reviewStar3}%</span></div>
         <div class="progress">
    <div class="progress-bar progress-bar-warning" role="progressbar"
         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
         style="width: ${reviewStatisticalVO.reviewStar3}%;">
        <span class="sr-only">${reviewStatisticalVO.reviewStar3}%</span>
    </div></li>
         <li>
         <div style="margin:0;padding:0 0 6px 0;text-align: left;"><img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <span style="float:right">${reviewStatisticalVO.reviewStar2}%</span></div>
         <div class="progress">
    <div class="progress-bar progress-bar-danger" role="progressbar"
         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
         style="width:${reviewStatisticalVO.reviewStar2}%;">
        <span class="sr-only">${reviewStatisticalVO.reviewStar2}%</span>
    </div></div></li>
         <li>
         <div style="margin:0;padding:0 0 6px 0;text-align: left;"><img src="/resources/review/star-on-yellow.svg" alt="1" title="gorgeous"> <span style="float:right">${reviewStatisticalVO.reviewStar1}%</span></div>
         <div class="progress">
    <div class="progress-bar progress-bar-danger" role="progressbar"
         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
         style="width: ${reviewStatisticalVO.reviewStar1}%;">
        <span class="sr-only">${reviewStatisticalVO.reviewStar1}%</span>
    </div>
</div></li>
      </ul>
      </div>
       <h5 style="margin-top: 8px;"><spring:message code="label.sales.breakdown"/> </h5>
    </div>
</div></div>
</div>

<!------- rating list  view --------->

<div class="shop_line" style="padding:0 13px;">
    <div class="col-lg-4 col-sm-12">
    <div class="panel panel-default">
    <div class="reviews therapist">
<div class="grade">
		<div class="top">
			<div class="left">
				<img src="resources/review/img/icon_shop.png" alt="">
				<span><spring:message code="label.sales.shopreviews"/> </span>
			</div>
			<div class="right">
			<%--	<a href="/?loadingUrl=/review/reviewRatingShopList">View</a>--%>
			</div>
		</div>
		 <h1>${reviewStatisticalVO.customerServicesAvg}</h1>
         <div align="center"><p class="star" data-bg="yellow" data-score="${reviewStatisticalVO.customerServicesAvg}"></p></div>
		
	</div>
	<div class="rat-content">
		<ul class="TOP10">
			<li class="rat-title">
				<h1><spring:message code="label.sales.shopname"/> </h1>
				<a class="active" href="javascript: void(0)"><spring:message code="label.sales.top"/> </a>
				<a href="javascript: void(0)"><spring:message code="label.sales.bottom"/> </a>
			</li>
			<c:forEach items="${shopTop10List}" var="shop">
				<li>
					<span class="reviews-name">${shop.shopName}</span>
					<span>${shop.customerServiceStar}</span>
					<p class="star" data-bg="yellow" data-score="${shop.customerServiceStar}"></p>
				</li>


			</c:forEach>


		</ul>

		<ul class="LOW10">
			<li class="rat-title">
				<h1><spring:message code="label.sales.shopname"/> </h1>
				<a href="javascript: void(0)"><spring:message code="label.sales.top"/> </a>
				<a class="active" href="javascript: void(0)"><spring:message code="label.sales.bottom"/></a>
			</li>
			<c:forEach items="${shopLow10List}" var="shop">
				<li>
					<span class="reviews-name">${shop.shopName}</span>
					<span>${shop.customerServiceStar}</span>
					<p class="star" data-bg="yellow" data-score="${shop.customerServiceStar}"></p>
				</li>
			</c:forEach>

		</ul>
	</div>
    </div>
</div>
    </div>

    
    <div class="col-lg-4 col-sm-12">
    <div class="panel panel-default">
    <div class="reviews treatment">
	<div class="grade">
		<div class="top">
			<div class="left">
				<img src="/resources/review/img/icon_treatment.png" alt="">
				<span><spring:message code="label.sales.treatmentreviews"/> </span>
			</div>
			<div class="right">
				<%--<a href="/?loadingUrl=/review/ratingTreatmentList">View</a>--%>
			</div>
		</div>
		
		 <h1>${reviewStatisticalVO.satisfactionLevelStarAvg}</h1>
         <div align="center"><p class="star" data-bg="yellow" data-score="${reviewStatisticalVO.satisfactionLevelStarAvg}"></p></div>
	</div>
	<div class="rat-content">
		<ul class="TOP10">
			<li class="rat-title">
				<h1><spring:message code="label.treatment.name"/> </h1>
				<a class="active" href="javascript: void(0)"><spring:message code="label.sales.top"/></a>
				<a href="javascript: void(0)"><spring:message code="label.sales.bottom"/></a>
			</li>

			<c:forEach items="${treatmentTop10List}" var="treatment">

				<li>
					<span class="reviews-name">${treatment.productName}</span>
					<span>${treatment.satisfactionLevelAvg}</span>
					<p class="star" data-bg="yellow" data-score="${treatment.satisfactionLevelAvg}"></p>
				</li>
				</c:forEach>


		</ul>

		<ul class="LOW10">
			<li class="rat-title">
				<h1><spring:message code="label.treatment.name"/></h1>
				<a href="javascript: void(0)"><spring:message code="label.sales.top"/></a>
				<a class="active" href="javascript: void(0)"><spring:message code="label.sales.bottom"/></a>
			</li>
			<c:forEach items="${treatmentLow10List}" var="treatment">

				<li>
					<span class="reviews-name">${treatment.productName}</span>
					<span>${treatment.satisfactionLevelAvg}</span>
					<p class="star" data-bg="yellow" data-score="${treatment.satisfactionLevelAvg}"></p>
				</li>
			</c:forEach>

		</ul>
	</div>
</div>
    </div>
    </div>
    
    <div class="col-lg-4 col-sm-12">
    <div class="panel panel-default">
    <div class="reviews therapist">
	<div class="grade">
		<div class="top">
			<div class="left">
				<img src="/resources/review/img/icon_therapist.png" alt="">
				<span><spring:message code="label.sales.therapistreviews"/> </span>
			</div>
			<div class="right">
			<%--	<a href="/?loadingUrl=/review/ratingTreatmentTherapistList">View</a>--%>
			</div>
		</div>
		
		 <h1>${reviewStatisticalVO.staffStarAvg}</h1>
         <div align="center"><p class="star" data-bg="yellow" data-score="${reviewStatisticalVO.staffStarAvg}"></p></div>
	</div>
	<div class="rat-content">
		<ul class="TOP10">
			<li class="rat-title">
				<h1><spring:message code="label.sales.therapistname"/> </h1>
				<a class="active" href="javascript: void(0)"><spring:message code="label.sales.top"/> </a>
				<a href="javascript: void(0)"><spring:message code="label.sales.bottom"/> </a>
			</li>
			<c:forEach items="${treatmentTherapistTop10List}" var="treatmentTherapist">

				<li>
				<span class="reviews-name">${treatmentTherapist.staffName}</span>
					<span>${treatmentTherapist.staffStarAvg}</span>
					<p class="star" data-bg="yellow" data-score="${treatmentTherapist.staffStarAvg}"></p>
				</li>

			</c:forEach>


		</ul>

		<ul class="LOW10">
			<li class="rat-title">
				<h1><spring:message code="label.sales.therapistname"/> </h1>
				<a href="javascript: void(0)"><spring:message code="label.sales.top"/></a>
				<a class="active" href="javascript: void(0)"><spring:message code="label.sales.bottom"/></a>
			</li>
			<c:forEach items="${treatmentTherapistLow10List}" var="treatmentTherapist">

				<li>
					<span class="reviews-name">${treatmentTherapist.staffName}</span>
					<span>${treatmentTherapist.staffStarAvg}</span>
					<p class="star" data-bg="yellow" data-score="${treatmentTherapist.staffStarAvg}"></p>
				</li>

			</c:forEach>
		</ul>
	</div>
</div>
</div>
    </div></div>  
 </div>


<%--<div class="tab-pane fade" id="ios">
    <div class="management">
      <form method="post" class="form-horizontal">
      <div class="home_search">
      <div class="row">
      <div class="col-sm-12">
     <div class="col-lg-2 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label">Shop</label>
              <select id="shopId" name="shopId" class="selectpicker form-control">
                  <c:forEach items="${shopList}" var="item">
                      <option value="${item.id}"
                              <c:if test="${bookVO.shopId eq item.id}">selected</c:if>>${item.name}</option>
                  </c:forEach>
              </select>
          </div>
        </div>
         <div class="col-lg-2 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label">Therapist</label>
	        	<select id="staffId" name="staffId" class="selectpicker form-control">
	      		</select>
           </div>
        </div>

          <div class="col-lg-3 col-sm-6">
              <div class="form-group col-md-12">
                  <label class="control-label"><spring:message code="label.treatment.tree"/></label>
                  <div class="select-category" data-selectable="option" data-root-id="2" ></div>
              </div>
          </div>
        
          <div class="col-lg-2 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label">Date</label>
	         <div class="input-group date form_time">
		        	<input id="fromDate" name="fromDate" class="form-control fromDate"
		        		value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		        	<span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
       	    </div>
	    </div>
         </div>
       
       <div class="col-lg-2 col-sm-6">
           <div class="form-group col-md-12"><br/><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
	        <i class="glyphicon glyphicon-search"></i>
	       <spring:message code="label.button.search"/>
               	</a></div>
       </div>
         
        </div>
         
              <div class="col-sm-12">
      <div class="col-lg-8 col-sm-12">
          <div class="form-group col-md-12">
          <label class="control-label">Reporting Period：</label>
           <button type="button" class="btn btn-primary" 
    data-toggle="button">Past year
</button> <button type="button" class="btn btn-primary" 
    data-toggle="button"> Past 6months
</button> <button type="button" class="btn btn-primary" 
    data-toggle="button">Past 30 Days
</button> <button type="button" class="btn btn-primary" 
    data-toggle="button"> Past 7 Days
</button>
          </div>
                </div>
                
 
</div>
  </form>
  </div>


<div class="home-shop rat-content">

<table class="table table-striped table-hover">
        <tr class="rat-title">
            <td width="10%" class="date">Date</td>
            <td width="10%" class="custom-name">Custom Name</td>
            <td width="15%" class="serve-rat">Customer Service Rating</td>
            <td width="15%" class="cle-rat">Cleanliness Rating</td>
            <td width="15%" class="serve-rat">Therapist</td>
            <td width="5%" class="review-text">NPS</td>
            <td width="40%" class="review-text">Review Text</td>
        </tr>
    <c:forEach items="${listings}" var="listing">
        <tr class="detail-content">
            <td class="date"><fmt:formatDate value="${listing.date}" pattern="yyyy-MM-dd"/></td>
            <td class="custom-name">${listing.name}</td>
            <td class="serve-rat"><span>${listing.customerServiceRating}</span>
                <p class="star" data-bg="yellow" data-score="${listing.customerServiceRating}"></p></td>
            <td class="cle-rat"><span>${listing.cleanlinessRating}</span>
                <p class="star" data-bg="blue" data-score="${listing.cleanlinessRating}"></p></td>
            <td class="serve-rat"><span>${listing.therapistStar}</span>
                <p class="star" data-bg="yellow" data-score="${listing.therapistStar}"></p></td>
            <td class="review-text">8</td>
            <td class="review-text">
               ${listing.reviewText}
            </td>
        </tr>


    </c:forEach>

       &lt;%&ndash; <tr class="detail-content">
            <td class="date">Jun/01/2018</td>
            <td class="custom-name">Lily Cheung</td>
            <td class="serve-rat"><span>4.0</span>
            <p class="star" data-bg="yellow" data-score="4.0"></p></td>
            <td class="cle-rat"><span>3.9</span>
            <p class="star" data-bg="blue" data-score="3.9"></p></td>
             <td class="serve-rat"><span>4.6</span>
            <p class="star" data-bg="yellow" data-score="4.6"></p></td>
            <td class="review-text">8</td>
            <td class="review-text">
                The technician is very friendly and skillful.
                <span>Technician is very friendly, skilled,
                store is very clean, as well
                as 30% discount.</span>
            </td>
        </tr>&ndash;%&gt;
    </table>
<!--<ul>
     
        <li class="rat-title">
            <h1>Name</h1>
            <p class="rat-cus">Customer Serivces Rating</p>
            <p>Cleanliness Rating</p>
        </li>
        
      <li>
            <a href="shop_details.html">
            <span class="type-name">Central Spa (Flagship Spa)</span>
            <span>4.6</span>
            <p class="star" data-bg="yellow" data-score="4.6"></p>
            <span>3.9</span>
            <p class="star" data-bg="blue" data-score="3.9"></p></a>
        </li>
        <li>
            <a href="shop_details.html">
            <span class="type-name">Central Spa (Flagship Spa)</span>
            <span>2.3</span>
            <p class="star" data-bg="yellow" data-score="2.3"></p>
            <span>3.3</span>
            <p class="star" data-bg="blue" data-score="3.3"></p></a>
        </li>
    </ul>-->
</div>
</div>--%>

<%--<script src="<c:url value="/resources/review/js/jquery-1.11.3.min.js"/>"></script>
<script src="<c:url value="/resources/review/js/jquery.raty.min.js"/>"></script>--%>


<script>
    //shop
/*    $('#shopId',Dialog.getContext()).change(function () {
        $.post('<c:url value="/staff/therapistSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),showAll:false},function (res) {
            $('#staffId').html(res);
        });
    }).trigger('change');*/
    function addClassFn (el,a,b) {
        $(el + ' .' + a).find('li').each(function (i) {
            if (i%2 === 0 && i !== 0) {
                $(this).addClass('gray');
            }
        });

        $(el + ' .' + b).find('li').each(function (i) {
            if (i%2 === 0 && i !== 0) {
                $(this).addClass('gray');
            }
        });
    }

    $(function () {
        addClassFn ('.shop','TOP10','LOW10');

        addClassFn ('.therapist','TOP10','LOW10');

        addClassFn ('.treatment','TOP10','LOW10');

      
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

        $('.rat-title').find('a').click(function () {
            $(this).parent().parent().hide().siblings().show();
        });
    })
	

</script>

<!--[if IE]>
<script src="http://explorercanvas.googlecode.com/svn/trunk/excanvas.js"></script>
<![endif]-->
<script>

// Run the code when the DOM is ready
$( pieChart );

function pieChart() {

  // Config settings
  var chartSizePercent = 75;                        // The chart radius relative to the canvas width/height (in percent)
  var sliceBorderWidth = 0;                         // Width (in pixels) of the border around each slice
  var sliceBorderStyle = "#fff";                    // Colour of the border around each slice
  var sliceGradientColour = "#fff";                 // Colour to use for one end of the chart gradient
  var maxPullOutDistance = 15;                      // How far, in pixels, to pull slices out when clicked
  var pullOutFrameStep = 4;                         // How many pixels to move a slice with each animation frame
  var pullOutFrameInterval = 10;                    // How long (in ms) between each animation frame
  var pullOutLabelPadding = 10;                     // Padding between pulled-out slice and its label  
  var pullOutLabelFont = "bold 16px 'Trebuchet MS', Verdana, sans-serif";  // Pull-out slice label font
  var pullOutValueFont = "bold 12px 'Trebuchet MS', Verdana, sans-serif";  // Pull-out slice value font
  var pullOutValuePrefix = "$";                     // Pull-out slice value prefix
  var pullOutShadowColour = "rgba( 0, 0, 0, .5 )";  // Colour to use for the pull-out slice shadow
  var pullOutShadowOffsetX = 5;                     // X-offset (in pixels) of the pull-out slice shadow
  var pullOutShadowOffsetY = 5;                     // Y-offset (in pixels) of the pull-out slice shadow
  var pullOutShadowBlur = 5;                        // How much to blur the pull-out slice shadow
  var pullOutBorderWidth = 2;                       // Width (in pixels) of the pull-out slice border
  var pullOutBorderStyle = "#333";                  // Colour of the pull-out slice border
  var chartStartAngle = -.5 * Math.PI;              // Start the chart at 12 o'clock instead of 3 o'clock

  // Declare some variables for the chart
  var canvas;                       // The canvas element in the page
  var currentPullOutSlice = -1;     // The slice currently pulled out (-1 = no slice)
  var currentPullOutDistance = 0;   // How many pixels the pulled-out slice is currently pulled out in the animation
  var animationId = 0;              // Tracks the interval ID for the animation created by setInterval()
  var chartData = [];               // Chart data (labels, values, and angles)
  var chartColours = [];            // Chart colours (pulled from the HTML table)
  var totalValue = 0;               // Total of all the values in the chart
  var canvasWidth;                  // Width of the canvas, in pixels
  var canvasHeight;                 // Height of the canvas, in pixels
  var centreX;                      // X-coordinate of centre of the canvas/chart
  var centreY;                      // Y-coordinate of centre of the canvas/chart
  var chartRadius;                  // Radius of the pie chart, in pixels

  // Set things up and draw the chart
  init();


  /**
   * Set up the chart data and colours, as well as the chart and table click handlers,
   * and draw the initial pie chart
   */

  function init() {

    // Get the canvas element in the page
    canvas = document.getElementById('chart');

    // Exit if the browser isn't canvas-capable
    if ( typeof canvas.getContext === 'undefined' ) return;

    // Initialise some properties of the canvas and chart
    canvasWidth = canvas.width;
    canvasHeight = canvas.height;
    centreX = canvasWidth / 2;
    centreY = canvasHeight / 2;
    chartRadius = Math.min( canvasWidth, canvasHeight ) / 2 * ( chartSizePercent / 100 );

    // Grab the data from the table,
    // and assign click handlers to the table data cells
    
    var currentRow = -1;
    var currentCell = 0;

    $('#chartData td').each( function() {
      currentCell++;
      if ( currentCell % 2 != 0 ) {
        currentRow++;
        chartData[currentRow] = [];
        chartData[currentRow]['label'] = $(this).text();
      } else {
       var value = parseFloat($(this).text());
       totalValue += value;
       value = value.toFixed(2);
       chartData[currentRow]['value'] = value;
      }

      // Store the slice index in this cell, and attach a click handler to it
      $(this).data( 'slice', currentRow );
      $(this).click( handleTableClick );

      // Extract and store the cell colour
      if ( rgb = $(this).css('color').match( /rgb\((\d+), (\d+), (\d+)/) ) {
        chartColours[currentRow] = [ rgb[1], rgb[2], rgb[3] ];
      } else if ( hex = $(this).css('color').match(/#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/) ) {
        chartColours[currentRow] = [ parseInt(hex[1],16) ,parseInt(hex[2],16), parseInt(hex[3], 16) ];
      } else {
        alert( "Error: Colour could not be determined! Please specify table colours using the format '#xxxxxx'" );
        return;
      }

    } );

    // Now compute and store the start and end angles of each slice in the chart data

    var currentPos = 0; // The current position of the slice in the pie (from 0 to 1)

    for ( var slice in chartData ) {
      chartData[slice]['startAngle'] = 2 * Math.PI * currentPos;
      chartData[slice]['endAngle'] = 2 * Math.PI * ( currentPos + ( chartData[slice]['value'] / totalValue ) );
      currentPos += chartData[slice]['value'] / totalValue;
    }

    // All ready! Now draw the pie chart, and add the click handler to it
    drawChart();
    $('#chart').click ( handleChartClick );
  }


  /**
   * Process mouse clicks in the chart area.
   *
   * If a slice was clicked, toggle it in or out.
   * If the user clicked outside the pie, push any slices back in.
   *
   * @param Event The click event
   */

  function handleChartClick ( clickEvent ) {

    // Get the mouse cursor position at the time of the click, relative to the canvas
    var mouseX = clickEvent.pageX - this.offsetLeft;
    var mouseY = clickEvent.pageY - this.offsetTop;

    // Was the click inside the pie chart?
    var xFromCentre = mouseX - centreX;
    var yFromCentre = mouseY - centreY;
    var distanceFromCentre = Math.sqrt( Math.pow( Math.abs( xFromCentre ), 2 ) + Math.pow( Math.abs( yFromCentre ), 2 ) );

    if ( distanceFromCentre <= chartRadius ) {

      // Yes, the click was inside the chart.
      // Find the slice that was clicked by comparing angles relative to the chart centre.

      var clickAngle = Math.atan2( yFromCentre, xFromCentre ) - chartStartAngle;
      if ( clickAngle < 0 ) clickAngle = 2 * Math.PI + clickAngle;
                  
      for ( var slice in chartData ) {
        if ( clickAngle >= chartData[slice]['startAngle'] && clickAngle <= chartData[slice]['endAngle'] ) {

          // Slice found. Pull it out or push it in, as required.
          toggleSlice ( slice );
          return;
        }
      }
    }

    // User must have clicked outside the pie. Push any pulled-out slice back in.
    pushIn();
  }


  /**
   * Process mouse clicks in the table area.
   *
   * Retrieve the slice number from the jQuery data stored in the
   * clicked table cell, then toggle the slice
   *
   * @param Event The click event
   */

  function handleTableClick ( clickEvent ) {
    var slice = $(this).data('slice');
    toggleSlice ( slice );
  }


  /**
   * Push a slice in or out.
   *
   * If it's already pulled out, push it in. Otherwise, pull it out.
   *
   * @param Number The slice index (between 0 and the number of slices - 1)
   */

  function toggleSlice ( slice ) {
    if ( slice == currentPullOutSlice ) {
      pushIn();
    } else {
      startPullOut ( slice );
    }
  }

 
  /**
   * Start pulling a slice out from the pie.
   *
   * @param Number The slice index (between 0 and the number of slices - 1)
   */

  function startPullOut ( slice ) {

    // Exit if we're already pulling out this slice
    if ( currentPullOutSlice == slice ) return;

    // Record the slice that we're pulling out, clear any previous animation, then start the animation
    currentPullOutSlice = slice;
    currentPullOutDistance = 0;
    clearInterval( animationId );
    animationId = setInterval( function() { animatePullOut( slice ); }, pullOutFrameInterval );

    // Highlight the corresponding row in the key table
    $('#chartData td').removeClass('highlight');
    var labelCell = $('#chartData td:eq(' + (slice*2) + ')');
    var valueCell = $('#chartData td:eq(' + (slice*2+1) + ')');
    labelCell.addClass('highlight');
    valueCell.addClass('highlight');
  }

 
  /**
   * Draw a frame of the pull-out animation.
   *
   * @param Number The index of the slice being pulled out
   */

  function animatePullOut ( slice ) {

    // Pull the slice out some more
    currentPullOutDistance += pullOutFrameStep;

    // If we've pulled it right out, stop animating
    if ( currentPullOutDistance >= maxPullOutDistance ) {
      clearInterval( animationId );
      return;
    }

    // Draw the frame
    drawChart();
  }

 
  /**
   * Push any pulled-out slice back in.
   *
   * Resets the animation variables and redraws the chart.
   * Also un-highlights all rows in the table.
   */

  function pushIn() {
    currentPullOutSlice = -1;
    currentPullOutDistance = 0;
    clearInterval( animationId );
    drawChart();
    $('#chartData td').removeClass('highlight');
  }
 
 
  /**
   * Draw the chart.
   *
   * Loop through each slice of the pie, and draw it.
   */

  function drawChart() {

    // Get a drawing context
    var context = canvas.getContext('2d');
        
    // Clear the canvas, ready for the new frame
    context.clearRect ( 0, 0, canvasWidth, canvasHeight );

    // Draw each slice of the chart, skipping the pull-out slice (if any)
    for ( var slice in chartData ) {
      if ( slice != currentPullOutSlice ) drawSlice( context, slice );
    }

    // If there's a pull-out slice in effect, draw it.
    // (We draw the pull-out slice last so its drop shadow doesn't get painted over.)
    if ( currentPullOutSlice != -1 ) drawSlice( context, currentPullOutSlice );
  }


  /**
   * Draw an individual slice in the chart.
   *
   * @param Context A canvas context to draw on  
   * @param Number The index of the slice to draw
   */

  function drawSlice ( context, slice ) {

    // Compute the adjusted start and end angles for the slice
    var startAngle = chartData[slice]['startAngle']  + chartStartAngle;
    var endAngle = chartData[slice]['endAngle']  + chartStartAngle;
      
    if ( slice == currentPullOutSlice ) {

      // We're pulling (or have pulled) this slice out.
      // Offset it from the pie centre, draw the text label,
      // and add a drop shadow.

      var midAngle = (startAngle + endAngle) / 2;
      var actualPullOutDistance = currentPullOutDistance * easeOut( currentPullOutDistance/maxPullOutDistance, .8 );
      startX = centreX + Math.cos(midAngle) * actualPullOutDistance;
      startY = centreY + Math.sin(midAngle) * actualPullOutDistance;
      context.fillStyle = 'rgb(' + chartColours[slice].join(',') + ')';
      context.textAlign = "center";
      context.font = pullOutLabelFont;
      context.fillText( chartData[slice]['label'], centreX + Math.cos(midAngle) * ( chartRadius + maxPullOutDistance + pullOutLabelPadding ), centreY + Math.sin(midAngle) * ( chartRadius + maxPullOutDistance + pullOutLabelPadding ) );
      context.font = pullOutValueFont;
      context.fillText( pullOutValuePrefix + chartData[slice]['value'] + " (" + ( parseInt( chartData[slice]['value'] / totalValue * 100 + .5 ) ) +  "%)", centreX + Math.cos(midAngle) * ( chartRadius + maxPullOutDistance + pullOutLabelPadding ), centreY + Math.sin(midAngle) * ( chartRadius + maxPullOutDistance + pullOutLabelPadding ) + 20 );
      context.shadowOffsetX = pullOutShadowOffsetX;
      context.shadowOffsetY = pullOutShadowOffsetY;
      context.shadowBlur = pullOutShadowBlur;

    } else {

      // This slice isn't pulled out, so draw it from the pie centre
      startX = centreX;
      startY = centreY;
    }

    // Set up the gradient fill for the slice
    var sliceGradient = context.createLinearGradient( 400, 0, canvasWidth*.85, canvasHeight*.85 );
    sliceGradient.addColorStop( 0, sliceGradientColour );
    sliceGradient.addColorStop( 1, 'rgb(' + chartColours[slice].join(',') + ')' );

    // Draw the slice
    context.beginPath();
    context.moveTo( startX, startY );
    context.arc( startX, startY, chartRadius, startAngle, endAngle, false );
    context.lineTo( startX, startY );
    context.closePath();
    context.fillStyle = sliceGradient;
    context.shadowColor = ( slice == currentPullOutSlice ) ? pullOutShadowColour : "rgba( 0, 0, 0, 1 )";
    context.fill();
    context.shadowColor = "rgba( 0, 0, 0, 0 )";

    // Style the slice border appropriately
    if ( slice == currentPullOutSlice ) {
      context.lineWidth = pullOutBorderWidth;
      context.strokeStyle = pullOutBorderStyle;
    } else {
      context.lineWidth = sliceBorderWidth;
      context.strokeStyle = sliceBorderStyle;
    }

    // Draw the slice border
    context.stroke();
  }


  /**
   * Easing function.
   *
   * A bit hacky but it seems to work! (Note to self: Re-read my school maths books sometime)
   *
   * @param Number The ratio of the current distance travelled to the maximum distance
   * @param Number The power (higher numbers = more gradual easing)
   * @return Number The new ratio
   */

  function easeOut( ratio, power ) {
    return ( Math.pow ( 1 - ratio, power ) + 1 );
  }

};

</script>

<script src="/resources/review/js/jquery.js"></script>
<script>
$(function() {
			$('.circle').each(function(index, el) {
				var num = $(this).find('span').text() * 5;
				if (num<=180) {
					$(this).find('.pieright').css('transform', "rotate(" + num + "deg)");
				} else {
					$(this).find('.pieright').css('transform', "rotate(180deg)");
					$(this).find('.pieleft').css('transform', "rotate(" + (num - 180) + "deg)");
				};
			});

		});
</script>

