<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<t:layout title="Online Voucher">
		<div class="profile-box">
			<form id="giftVoucherForm" action='<c:url value="/front/prepaid/voucherAdd"/>' method="post" class="form-horizontal">
        <div class="title_for_all">
            <h2><c:if test="${prepaidType eq 'CASH_VOUCHER' }">
			<b><spring:message code="label.prepaid.type.CASHVOUCHER"/> </b>
			</c:if>
           <c:if test="${prepaidType eq 'TREATMENT_VOUCHER' }">
			<b><spring:message code="label.prepaid.type.TREATMENTVOUCHER"/> </b>
		   </c:if>
            </h2>
</div>
			<!--<table class="table_index" border="0">
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<c:if test="${prepaidType eq 'CASH_VOUCHER' }">
				<tr>
					<td colspan="2"><font color="#9D0000"><b>Cash Voucher</b></font></td>
				</tr>		
				</c:if>
				<c:if test="${prepaidType eq 'TREATMENT_VOUCHER' }">
				<tr>
					<td colspan="2"><font color="#9D0000"><b>Treatment Voucher</b></font></td>
				</tr>		
				</c:if>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>-->
            <div class="booking_detail_ctrl">
               <div class="content_left_page">
			<table class="form_default" border="0" width="100%">
					<c:if test="${prepaidType eq 'TREATMENT_VOUCHER' }">
						<tr>
							<td>
								<spring:message code="label.treatment"/>
							</td>
							<td>
								<div class="col-lg-6">
									<input type="hidden" id="prepaidType" name="prepaidType" value="TREATMENT_VOUCHER" />
									<input type="hidden" id="productOptionId" name="productOptionId" value="" />
									<div class="select-category" data-selectable="option" data-root-id="2" style="width:400px;"></div>
								</div>
								
								<script type="text/javascript">
                                		$(function () {
                                			
                                			$('.select-category').selectCategory({
                                	        	callback: function (context) {
                                	                // 注册变更事件
                                	            },
                                	            click: function() {
                               		                var poId=$(this).data('product-option-id');
                               		                $('#productOptionId').val(poId);
                               		        		$.ajax({
                               		                    url: '<c:url value="/po/rerurnPOPrice?shopId=19"/>',
                               		                    type: "POST",
                               		                    dataType: "text",
                               		                    data: {poId: poId},
                               		                    success: function(response) {
                               		                    	$('#prepaidValue').val(response);
                               		                    	$('#showAmount').html("HK$"+response);
                               		                    }
                               		                });
                                	            }
                                        });
                                        });
                                </script>
							</td>
						</tr>	
					</c:if>
				
					<c:if test="${prepaidType eq 'CASH_VOUCHER' }">
						<tr>
							<td><spring:message code="front.label.voucher.value"/> </td>
							<td>
								<div class="col-lg-6" style="width:300px;">
								<input type="hidden" id="prepaidType" name="prepaidType" value="CASH_VOUCHER" />
								<select id="voucherVal" class="selectpicker form-control" >
								 	
                                    <option value="250">HK$250</option>
                                    <option value="500">HK$500</option>
                                    <option value="750">HK$750</option>
                                    <option value="1000">HK$1000</option>
                                    <option value="1250">HK$1250</option>
                                    <option value="1500">HK$1500</option>
                                    <option value="2000">HK$2000</option>
                                    <option value="2500">HK$2500</option>
                                    <option value="3000">HK$3000</option>
                                    <option value="3500">HK$3500</option>
                                    <option value="4000">HK$4000</option>
                                    <option value="5000">HK$5000</option>
                                    
                                </select>
                                </div>
                                <script type="text/javascript">
                                		$(function () {
                                            $('#voucherVal').bind('change',function(){
                                                $('#showAmount').html("HK$"+$('#voucherVal').val());
                                                $('#prepaidValue').val($('#voucherVal').val());
                                            }).change();
                                        });
                                </script>
							</td>
						</tr>
					</c:if>				
					<%-- <tr>
						<td><label class="control-label">Promotion Code</label></td>
						<td>
							<div class="col-lg-6">				
								<input type="text" id="promotionCode" size="50" class="form-control"/>
							</div>
						</td>
					</tr>	 --%>		
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2"><p><spring:message code="front.label.additional.notice"/> </p>
                            <input type="hidden" id="pickUpType" name="pickUpType" value="location"/>
                            <ul class="nav nav-tabs" id="myTab">
                            	<li class="active"><a href="#location"><spring:message code="front.label.pick.up.from.spa"/> </a></li>
						        <li><a href="#email"><spring:message code="front.label.send.me.by.email"/> </a></li>
						        <li><a href="#friend"><spring:message code="front.label.deliver.this.gift.to.my.friend"/> </a></li>
                            </ul>
                            <div class="tab-content" style="background:none;height:100%;">
                            	<div id="location" class="tab-pane fade in active">
                                   <div class="col-lg-4" style="padding:5px 0;"><spring:message code="front.label.select.one.of.the.locations"/> </div>
                        			<div class="col-lg-8">
                            			<select id="pickUpLocation" name="pickUpLocation" class="selectpicker form-control">
		                                <c:forEach items="${shopList}" var="shop">
		                                	<c:if test="${shop.id != 26}">
		                                    	<option value="${shop.id}">${shop.name}</option>
		                                    </c:if>
		                                </c:forEach>
	                            	</select>
                        			</div>
                                </div>
                                <div id="email"  class="tab-pane fade">
                                    <div class="col-lg-12"><spring:message code="front.label.statements13"/> :</div>
                                     <div class="col-lg-12"><p>${loginMemberEmail }</div>
                                </div>
                                <div id="friend" class="tab-pane fade">
                                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                       <tr>
                                           <td colspan="2" class="label_default" style="padding:5px 0;"><spring:message code="front.label.additional.email"/> </td>
                                      
                                           <td colspan="2" class="value_default" style="padding:5px 0;">
                                               <input type="text" id="additionalEmail" size="50" class="form-control" name="additionalEmail" value=""/>
                                           </td>
                                       </tr>
                                       <tr>
                                           <td colspan="2" class="label_default" style="padding:5px 0;"><spring:message code="front.label.additional.name"/> </td>
                                       
                                           <td colspan="2" class="value_default" style="padding:5px 0;">
                                           	<input type="text" id="additionalName" size="50" class="form-control" name="additionalName" value=""/>
                                           </td>
                                       </tr>
                                       <tr>
                                           <td colspan="2" class="label_default" style="padding:5px 0;"><spring:message code="front.label.additional.message"/> </td>
                                      
                                           <td colspan="2" class="value_default" style="padding:5px 0;">
                                               <textarea id="additionalMessage" name="additionalMessage" class="form-control"></textarea>
                                           </td>
                                       </tr>
                                   </table>
                                </div>
                            </div>
                            <br/><hr/>
                        </td>
					</tr>
					<tr>
						<td class="label_default"><spring:message code="front.label.voucher.amount"/> :</td>
						<td>
							<div class="col-lg-6">
							<div id="showAmount"></div>
							<input type="hidden"  id="prepaidValue" name="prepaidValue" value=""/>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>			
					<tr>
						<td class="label_default"><spring:message code="front.label.voucher.expired.date"/> :</td>
						<td>
							<div class="col-lg-6">
							<fmt:formatDate value="${expiryDate6M}" pattern="yyyy-MM-dd"/>
							<input type="hidden" id="expiryDate" name="expiryDate" value='<fmt:formatDate value="${expiryDate6M}" pattern="yyyy-MM-dd"/>'>
							</div>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr>
						<td colspan="2">
							<div class='form_default_buttons'>
								<input type="hidden"  id="paymentName" name="paymentName" value="PayPal"/>
								<a href="#" onclick="submitPayment();">
									<img src="<c:url value='/resources/img/front/PAYPAL_en.png'/>" border="0" alt="Paid By Palpal"/>
								</a>
								&nbsp;&nbsp;
							</div>
						</td>
					</tr>
				</table>
			</form>
	</div>
    </div>
    </div>
</t:layout>

			<script type="text/javascript">
	            
				function submitPayment(){
					
					var form = $('#giftVoucherForm');
					var pickUPType= $('#pickUpType').val();
					if(pickUPType == 'friend'){
						var email = $('#additionalEmail').val()
						if(email == ''){
							BootstrapDialog.alert({
		                        title: 'Error',
		                        message: 'Email is required!'
		                    });
		                    return false;
						}
						var emailRegExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				        var emailReg = emailRegExp.test(email);
				        if(!emailReg){
				        	BootstrapDialog.alert({
		                        title: 'Error',
		                        message: 'Email is incorrect!'
		                    });
		                    return false;
				        }
					}
	               	window.location.href = '<c:url value="/front/prepaid/voucherAdd?"/>' + form.serialize();
	               /* 
	           		$.ajax({
		                    url: '<c:url value="/front/prepaid/voucherSave"/>',
		                    type: "POST",
		                    dataType: "text",
		                    data: form.serialize(),
		                   	success: function(response) {
		                    	//
		                   	} 
		               	});
	                */
	            }
				
                $(document).ready(function(){
                    $('#myTab a:first').tab('show');
                    
                    $('#myTab a').click(function (e) { 
                      	e.preventDefault();
                      	$(this).tab('show');
                     /* 	$('#pickUpType').val($(this).val());*/
                        $('#pickUpType').val($(this).attr("href").replace('#',''));
                        if($(this).attr("href").replace('#','')!='location'){
                            $("#pickUpLocation").val("");
						}else{
                            $("#pickUpLocation").val($("#pickUpLocation option:first-child").val());
						}

                    });
                    
                });

	        </script>