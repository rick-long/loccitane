<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>

<%
    // 所有css / js資源版本控制，每次更新的時候需要修改版本號，保證客戶端使用的css和js都是最新的代碼
    request.setAttribute("version", "20160830");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="label.review"/></title>
    <style>
.NPS_btn {
    width: 100%;
    float: left;
    text-align: center;
}
	
.btn_default {
    border: 1px solid #e3e3e3;
    padding: 19px 17px;
    border-radius: 56px;
    margin: 5px 17px;
    width: 60px;
    background: #e3e3e3;
	color:#999;
	font-size: 16px;
}

.checked{
    color: #a46117;
    border: 1px solid #fed08e;
    padding: 19px 17px;
    border-radius: 56px;
    margin: 5px 17px;
    width: 60px;
    background: #fed08e;
    font-size: 16px;     
}
</style>
</head>
<link href='<c:url value="/resources/js/comment/css/comment.css"/>?v=${version}' rel="stylesheet">
<link href='<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css"/>?v=${version}' rel="stylesheet">
  <script src="/resources/jquery-ui-1.12.1.custom/jquery.min.js"></script>
<script src="/resources/js/comment/js/jquery-1.8.3.min.js"></script>
<script src="/resources/js/comment/js/bootstrap.min.js"></script>
  
<script src='<c:url value="/resources/js/comment/js/bootstrap.min.js"/>?v=${version}'></script>

<script type="text/javascript">
    //提交点评
    function addComment3(){
        var purchaseItemsSize=${purchaseItems.size()};
       for( var i=0;i<purchaseItemsSize;i++){
           console.log(i);
           if($("input[name='reviewRatingTreatments["+i+"].valueForMoneyStar']").val()==0||$("input[name='reviewRatingTreatments["+i+"].satisfactionLevelStar']").val()==0){

               $('#reviewSaveStatus').text("Please complete the review.");
               $('#myModal').modal('show');
               return;
           }else{
               var staffCommissionsSize=${purchaseItems.get(i).staffCommissions.size()};
               for (var j=0;j<staffCommissionsSize;j++){
                   if($("input[name='reviewRatingTreatments["+i+"].reviewRatingTreatmentTherapists["+j+"].staffStar").val()==0){
                       $('#reviewSaveStatus').text("Please complete the review.");
                       $('#myModal').modal('show');
                       return;
                   }
               }
           }

       }

        if($("input[name='reviewRatingShops[0].customerServiceStar']").val()==0||$("input[name='reviewRatingShops[0].cleanlinessStar']").val()==0){
            $('#reviewSaveStatus').text("Please complete the review.");
            $('#myModal').modal('show');
            return;
        }
        if($("input[name='nps']").val()==''){
            $('#reviewSaveStatus').text("Please complete the review.");
            $('#myModal').modal('show');
            return;
        }
	window.location.href = '<c:url value="/reviewForm/save?"/>' + $('#reviewForm').serialize();
    }

    $(function(){
        //点星星
        $(document).on('click','i[cjmark]',function(){
            var num = $(this).index();
            var pmark = $(this).parents('.revinp');
            var mark = pmark.prevAll('input');

            if(mark.prop('checked')) return false;
            mark.val(num+1);
            var list = $(this).parent().find('i');
            for(var i=0;i<=num;i++){
                list.eq(i).attr('class','level_solid');
            }
            for(var i=num+1,len=list.length-1;i<=len;i++){
                list.eq(i).attr('class','level_hollow');
            }
            $(this).parent().next().html(degree[num+1]);
        })
        //点击星星
    /*    $(document).on('click','i[cjmark]',function(){
            var num = $(this).index()+1;
            var pmark = $(this).parents('.revinp');
            var mark = pmark.prevAll('input');

            if(mark.prop('checked')){
                mark.val(num);
                mark.prop('checked',false);mark.prop('disabled',false);
                $("#reviewButton").attr("disabled", true);
            }else{
                mark.val(num);
                mark.prop('checked',true);mark.prop('disabled',false);
                $("#reviewButton").attr("disabled", false);
            }
        })*/
    })
</script>
<body>
<div class="form_for_review">
<div class="banner_for_review">
<img src="/resources/img/Reception-new.jpg" width="100%">
<h4><spring:message code="label.review.what.thinkof"/> <spring:message code="label.company.title"/>?</h4>
</div>
<div class="form_list_rating">
<form id="reviewForm">
    <input type="hidden" name="orderId" value="${purchaseOrder.id}" >
    <input type="hidden" name="nps" id="nps" >
<div class="gradecon" id="Addnewskill_119">
    <ul class="rev_pro clearfix">
        <%
            int purchaseSum = 0;
            request.setAttribute("purchaseSum", purchaseSum);
        %>
        <c:forEach items="${purchaseItems}" var="purchase">
            <input type="hidden" name="reviewRatingTreatments[${purchaseSum}].productId" value="${purchase.productOption.product.id}" >
            <input type="hidden" name="reviewRatingTreatments[${purchaseSum}].productOptionId" value="${purchase.productOption.id}" >

        <h6>${purchase.productOption.label33}</h6>
        <li>
            <input class="fl" type="hidden" style="margin-top:2px;" name="reviewRatingTreatments[${purchaseSum}].valueForMoneyStar" value="0" />
            <div class="revtit"><spring:message code="label.review.valueofmoney"/></div>
            <div class="revinp">
				<span class="level">
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
				</span>
              <%--  <span class="revgrade">良</span>--%>
            </div>
        </li>
            <li>
                <input class="fl" type="hidden" style="margin-top:2px;" name="reviewRatingTreatments[${purchaseSum}].satisfactionLevelStar" value="0" />
                <div class="revtit"><spring:message code="label.review.satisfaction.level"/></div>
                <div class="revinp">
				<span class="level">
                   <%-- level_hollow--%>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
				</span>
                  <%--  <span class="revgrade">良</span>--%>
                </div>
                
            </li>
           
       <%-- <li><div class="revtit"><spring:message code="label.therapist"></spring:message></div></li>--%>
            <%
                int staffSum = 0;
                request.setAttribute("staffSum", staffSum);
            %>
            <c:forEach items="${purchase.staffCommissions}" var="staffCommission" >
                <input type="hidden" name="reviewRatingTreatments[${purchaseSum}].reviewRatingTreatmentTherapists[${staffSum}].staffId" value="${staffCommission.staff.id}" >
        <li>
            <input class="fl" type="hidden" style="margin-top:2px;" name="reviewRatingTreatments[${purchaseSum}].reviewRatingTreatmentTherapists[${staffSum}].staffStar" value="0" />
            <div class="revtit"><spring:message code="label.therapist"></spring:message>: ${staffCommission.staff.fullName}</div>
            <div class="revinp">
				<span class="level">
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
				</span>
                <%--<span class="revgrade">良</span>--%>
            </div>
        </li>
        </ul>
        
          <ul class="rev_pro clearfix"> 
                <c:set var="staffSum" value="${staffSum+1}"></c:set>
            </c:forEach>
            <c:set var="purchaseSum" value="${purchaseSum+1}"></c:set>
        </c:forEach>
        <h6><spring:message code="label.shop"/>: ${purchaseOrder.shop.name}</h6>
        <input class="fl" type="hidden" style="margin-top:2px;" name="reviewRatingShops[0].shopId" value="${purchaseOrder.shop.id}" />
        <li>
            <input class="fl" type="hidden" style="margin-top:2px;" name="reviewRatingShops[0].customerServiceStar" value="0" />
            <div class="revtit"><spring:message code="label.review.customer.service"/> </div>
            <div class="revinp">
				<span class="level">
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
				</span>
              <%--  <span class="revgrade">良</span>--%>
            </div>
        </li>
        <li>
            <input class="fl" type="hidden" style="margin-top:2px;" name="reviewRatingShops[0].cleanlinessStar" value="0" />
            <div class="revtit"><spring:message code="label.review.cleanliness"/></div>
            <div class="revinp">
				<span class="level">
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
					<i class="level_hollow" cjmark=""></i>
				</span>
             <%--   <span class="revgrade">良</span>--%>
            </div>
        </li>
       </ul>

          <ul class="rev_pro clearfix" style="border:0;"> 
        <h6><spring:message code="label.review.feel"/></h6>
        <li style="border:0;">
        <div class="NPS_btn">
          <button value="0" class="btn_default" type="button" data-toggle="button">0</button>
          <button value="1" class="btn_default" type="button" data-toggle="button">1</button>
          <button value="2" class="btn_default" type="button" data-toggle="button">2</button>
          <button value="3" class="btn_default" type="button" data-toggle="button">3</button>
          <button value="4" class="btn_default" type="button" data-toggle="button">4</button>
          <button value="5" class="btn_default" type="button" data-toggle="button">5</button>
          <button value="6" class="btn_default" type="button" data-toggle="button">6</button>
          <button value="7" class="btn_default" type="button" data-toggle="button">7</button>
          <button value="8" class="btn_default" type="button" data-toggle="button">8</button>
          <button value="9" class="btn_default" type="button" data-toggle="button">9</button>
          <button value="10" class="btn_default" type="button" data-toggle="button">10</button>
        </div>
  </li>
        <li style="border-top:1px solid #DDDDDD;border-right:0;border-left:0;border-bottom:0;"><textarea class="form-control" rows="5" name="reviewText"n placeholder="Evaluation..."></textarea><%-- <input class="btn btn-default" type="submit" value="Submit">--%></li>
       <li style="border:0;">
   <p style="text-align:center;"> <button type="button" class="btn btn-primary" ONCLICK="addComment3()" id="reviewButton">
        <spring:message code="label.button.submit"/>
    </button></p></li>
    </ul>
</div>
</form>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    &times;
                </button>
                <h3 class="modal-title" id="myModalLabel">
                    <spring:message code="label.review"/>
                </h3>
            </div>
            <div class="modal-body">
                <p id="reviewSaveStatus"></p>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
<script>
$(function(){
    $("button").each(function(){
        $(this).click(function(){
            $("button").addClass("btn_default").removeClass("checked");
            $(this).addClass("checked").removeClass("btn_default");
            $("#nps").val($(this).attr("value"));
        })
     
    });
 
})
</script>