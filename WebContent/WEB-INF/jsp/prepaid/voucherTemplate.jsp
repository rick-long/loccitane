<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<style>

.bg_div {width:88%;margin:0 auto;color:#0010228;}

.title_voucher h1{color:#0010228;text-align:center;font-size:48px;}
.title_voucher{margin:0 auto; width:100%;padding:20px 0;}

h3{color:#0010228;}

h6{color:#0010228;margin:2px 0;font-size: 14px;}

.voucher_bg {width: 100%;height: auto;margin: 0 auto;}

.voucher_cont {width:60%;
    margin: 0 auto;
    position: absolute;
    top:10%;
    text-align: center;
    z-index: 2;
    left:20%;
 }
	
.voucher_bg img {position: relative;right:0;}
.voucher_footer {width:80%;margin: 0 auto;position:fixed;bottom:0;left:11%;}
	
.terms{width:100%;
    color: #0010228;
    font-size: 14px;
    margin: 8px auto;
    line-height: 12px;

}

.terms ul{padding:0;margin:0 auto;width:96%;}

.terms ul li{width:98%;
        color: #0010228;
        font-size: 13px;
        line-height: 15px;
		list-style-type:disc;
		padding:2px 0;
		display:list-item;
		list-style-position:outside;
		text-indent: 2px;
		test-align:justify;}
	
.location_div {float: left;width: 96%;}

.location_div ul {padding: 0;margin: 0;}

.location_div ul li {
        width: 100%;
        color: #0010228;
        font-size: 13px;
        float: left;
        list-style: none;
        line-height: 18px;
		padding:2px 0;
    }	
	
</style>
<div class="bg_div">
    <div class="voucher_bg">
        <img src="/resources/img/backlogov3-new.jpg" width="765" height="auto" /> 
        <div class="voucher_cont"> 
        <div class="title_voucher"><h1>Gift Voucher</h1></div>    
<table width="80%" style="color:#0010228;">
                <tr>
                    <td style="font-size:16px;">You are entitled to<br/><h2>  <c:if test="${prepaid.prepaidType == 'TREATMENT_PACKAGE' || prepaid.prepaidType == 'TREATMENT_VOUCHER' }">
                        ${prepaid.firstPrepaidTopUpTransaction.productOption.product.name}
                    </c:if>
                        <c:if test="${prepaid.prepaidType == 'CASH_PACKAGE' || prepaid.prepaidType == 'CASH_VOUCHER' }">
                            ${prepaid.name}
                        </c:if></h2>

                    </td>
                </tr>
                <tr>
                    <td>
                        <hr/>
                    </td>
                </tr>
                </table>
            <table width="80%" style="color:#0010228;">
                <tr>
                    <td width="40%" style="font-size:14px;">Voucher No.:</td>
                    <td width="60%" style="font-size:16px;"><strong>${prepaid.reference}</strong></td>
                </tr>
                <tr>
                    <td style="font-size:16px;">Date of
                        Issue:</td>
                    <td style="font-size:18px;"><strong><fmt:formatDate value="${prepaid.firstPrepaidTopUpTransaction.topUpDate}" pattern="yyyy-MM-dd"/></strong></td>
                </tr>
                <tr>
                    <td style="font-size:16px;">Expired
                        Date:</td>
                    <td style="font-size:18px;"><strong><fmt:formatDate value="${prepaid.firstPrepaidTopUpTransaction.expiryDate}" pattern="yyyy-MM-dd"/></strong></td>
                </tr>
            </table>
        </div>
    </div>
   
    <div class="voucher_footer">
        <div class="terms">
        <h6>Terms & Conditions:</h6>
    <c:if test="${prepaid.isOnline}">
    <ul>
        <li>Valid for 6 months from date of issue;</li>
        <li>Not valid for use with other promotions;</li>
        <li>Not redeemable for cash and no change for any unused value;</li>
        <li>Expired vouchers will not be accepted;</li>
        <li>Please note that treatment prices can vary between locations and additional charge may be incurred upon redemption if the price at location of redemption is higher than at location of purchase. Please check with reception in advance when 
         making your appointment to avoid any misunderstanding when redeeming your voucher;</li>
        <li>Management reserves all rights.</li>
    </ul>
    </c:if>
           <c:if test="${prepaid.isRedeem}">
              <ul>
                  <c:forEach items="${termsList}" var="termsLists" varStatus="idx">
                  	<c:choose>
                  		<c:when test="${idx.last}">
                  			<li>${termsLists}.</li>
                  		</c:when>
                  		<c:otherwise><li>${termsLists};</li></c:otherwise>
                  	</c:choose>
                  </c:forEach>
              </ul>
           </c:if>
    </div>
     <div class="location_div">
 <img src="/resources/img/flower.png" width="100%" height="auto" />    
 <h6>L'OCCITANE SPA</h6>
        <ul>
            <li>2 Star Crest, 9 Star St, Wan Chai
              <span style="float:right;">TEL: 2143 6288</span>
            </li>
            <li>Shop 1094, Elements, Kowloon Station, TST, Kowloon
              <span style="float:right;">TEL: 2561 6832</span>
            </li>
            <li>Shop M-5a, MTR Level, V City, Tuen Mun, New Territories
              <span style="float:right;">TEL: 2791 2278</span>
            </li>
            <li>Shop 1013, 10/F, World Commerce Centre, Harbour City, Tsim Sha Tsu
              <span style="float:right;">TEL: 2201 4543</span>
            </li>
            <li>38/F, Tower Two, Times Square, Causeway Bay, Hong Kong
              <span style="float:right;">TEL: 2554 1221</span>
          </li>
        </ul>
    </div>
    </div>
</div>
