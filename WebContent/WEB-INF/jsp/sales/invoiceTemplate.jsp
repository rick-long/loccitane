<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<html>
<head>
    <link href='<c:url value="/resources/css/base/print.css"/>' rel="stylesheet">
    <title>Invoice</title>
</head>
<body>
<div class="invoice">
<div class="invoice_logo"><img src="/resources/img/logo.png" width="300"></div>
<div class="common-tr">
	  <ul>
	  <li>Special Offers</li>
      <li>|</li>
      <li>Collection</li>
      <li>|</li>
      <li>Face Care</li>
      <li>|</li>
      <li>Fragrances</li>
      <li>|</li>
      <li>Bath & Body</li>
      <li>|</li>
      <li>Hair Care</li>
      <li>|</li>
	  <li>Men</li>
      <li>|</li>
      <li>Gifting Idea</li>
      <li>|</li>
      <li>Most Loved</li>
      <li>|</li>
      <li>The Brand</li>
      <li>|</li>
      <li>SPA <spring:message code="label.company.title"/></li>
     </ul>
</div>
	
<div class="rec_info">		
<h2 align="center">RECEIPT</h2>
<form class="form-horizontal">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="inv_info" align="center">
  <tr>
    <td style="text-align:left;" width="43%">
        <label class="col-lg-3 control-label">Client Name:</label>
        <label class="col-lg-5">
           &nbsp;&nbsp; ${po.user.fullName}
        </label>
    </td>
    <td width="24%">
        <label class="col-lg-3 control-label">Staff:</label>
        <label class="col-lg-5">
           &nbsp;&nbsp;${staff}
        </label>
    </td>
    <td style="text-align:right;">
        <label class="col-lg-3 control-label"></label>
        <label class="col-lg-5">
            CUSTOMER COPY
        </label>
    </td>
  </tr>
  <tr>
    <td style="text-align:left;" width="32%">
        <label class="col-lg-3 control-label">Diva Number:</label>
        <label class="col-lg-5">
            &nbsp;&nbsp;${po.user.username}
        </label>
   </td>
    <td>
        <label class="col-lg-3 control-label">&nbsp;</label>
        <label class="col-lg-5">&nbsp;</label>
</td>
    <td style="text-align:right;" width="25%">
        <label class="col-lg-3 control-label">Date:</label>
        <label class="col-lg-5">
           &nbsp;&nbsp;<fmt:formatDate value="${po.purchaseDate}" pattern="yyyy-MM-dd"/>
        </label>
    </td>
  </tr>
  <tr>
    	<td> 
        	<label class="col-lg-3 control-label">Location:</label>
        	<label class="col-lg-5">
            	&nbsp;&nbsp;${po.shop.name}
        	</label>
 		</td>
    	<td>&nbsp;</td>
    	<td>&nbsp;</td>
  </tr>
  <c:if test="${po.showRemarksInInvoice}">
  <tr>
    	<td> 
        	<label class="col-lg-3 control-label">Remarks:</label>
        	<label class="col-lg-5">
            	&nbsp;&nbsp;${po.remarks}
        	</label>
 		</td>
    	<td>&nbsp;</td>
    	<td>&nbsp;</td>
  </tr>
  </c:if>
</table>

<div style="margin-top:20px;border-top:1px solid #ccc;">
  <table class="table table-striped price_info" align="center">
            <thead>
            <tr>
                <th>Item</th>
                <th>Description</th>
                <th>Unit Price</br>(HKD)</th>
                <th>Qty</th>
                <th>Discount</br>(HKD)</th>
                <th>Amount</br>(HKD)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${po.purchaseItems}" var="item" varStatus="idx">
                <tr>
                    <td>${idx.index +1}</td>
                    <td>
                    <c:choose>
						<c:when test="${item.buyPrepaidTopUpTransaction !=null}">
							<c:set var="prepaidType">
		                		 ${fn:replace(item.buyPrepaidTopUpTransaction.prepaidType, "_", "")}
		                	</c:set>
		                	<spring:message code="label.prepaid.type.${prepaidType}"/>
						</c:when>
						<c:otherwise>
							${item.productOption.label3}
						</c:otherwise>
					</c:choose>
                    	by 
                    	 <c:forEach items="${item.staffCommissions}" var="sc">
                    	 	${sc.staff.displayName };
                    	 </c:forEach>
                    </td>
                    <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.price}"/></td>
                    <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.qty}"/></td>
                    <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.discountValue}"/></td>
                    <td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.amount}"/></td>
                </tr>
            </c:forEach>
       		<tr>
            	<td></td>
                <td><strong>Total:</strong></td>
                <td></td>
                <td><strong><fmt:formatNumber type="number" pattern="#,#00.0#" value="${po.totalQty}"/></strong></td>
                <td><spring:message code="label.currency.default"/><strong><fmt:formatNumber type="number" pattern="#,#00.0#" value="${po.totalDiscount}"/></strong></td>
                <td><spring:message code="label.currency.default"/><strong><fmt:formatNumber type="number" pattern="#,#00.0#" value="${po.totalAmount}"/></strong></td>
            </tr>
            </tbody>
        </table>
    </div>

<div class="total_price">
    <div class="form-group">
        <label class="col-lg-12 control-label">Tender:</label><br/>
        <label class="col-lg-12 control-label">
            <c:forEach items="${po.payments}" var="pay">
            	<c:if test="${pay.redeemPrepaidTopUpTransaction !=null }">
            		<c:if test="${pay.redeemPrepaidTopUpTransaction.prepaid.prepaidType =='TREATMENT_PACKAGE' || pay.redeemPrepaidTopUpTransaction.prepaid.prepaidType =='CASH_PACKAGE' }">
            			Package: &nbsp;&nbsp;
            		</c:if>
            		<c:if test="${pay.redeemPrepaidTopUpTransaction.prepaid.prepaidType =='TREATMENT_VOUCHER' || pay.redeemPrepaidTopUpTransaction.prepaid.prepaidType =='CASH_VOUCHER' }">
            			Voucher:&nbsp;&nbsp;
            		</c:if>
            		  ${pay.redeemPrepaidTopUpTransaction.prepaid.reference}/${pay.redeemPrepaidTopUpTransaction.topUpReference}(Remaining Credit: ${pay.redeemPrepaidTopUpTransaction.prepaid.remainValue})
            	</c:if>
            	<c:if test="${pay.redeemPrepaidTopUpTransaction ==null }">
            		${pay.paymentMethod.name }: &nbsp;&nbsp; <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${pay.amount}"/>
            	</c:if>
		        </br>	
		    </c:forEach>
        </label>
    </div>
    <div class="voucher_bg">
    Thank you for visiting <spring:message code="label.company.title"/> !</div>
    </div>
</form>
</div>
</div>

<div class="footer">
   <div class="media">
     <ul>
       <li><img src="/resources/img/logo.png" width="100"></li>
       <li>THIS WEBSITE IS OWNED BY <spring:message code="label.company.title"/> (FAR EAST) LIMITED</li> 
       <li><spring:message code="label.company.copyright"/> 2018 </li>
     </ul>
   </div>
   <!--<div class="location">
    <ul>
       <li>Repulse Bay 2592 9668</li> 
       <li>|</li>
       <li>Central 2526 6918</li>
       <li>|</li>
       <li>Cyberport 2980 7698</li>
       <li>|</li>
<li>Tung Chung 2561 6832</li>
<li>|</li>
<li>Tseung Kwan O 3983 0406</li>
<li>|</li>
<li>Sai Kung 2791 2278</li>
     </ul>
   </div>-->
</div>
</div>

</body>
</html>