<%@ page import="org.joda.time.DateTime" %>
<%@ page import="org.spa.model.shop.Address" %>
<%@ page import="org.spa.model.shop.OpeningHours" %>
<%@ page import="org.spa.model.shop.Shop" %>
<%@ page import="org.spa.vo.front.book.FrontBookItemVO" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/front/common/jspInit.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/front" %>

<%
    Shop shop = (Shop) request.getAttribute("currentShop");
    FrontBookItemVO frontBookItemVO = (FrontBookItemVO) request.getAttribute("frontBookItemVO");
    OpeningHours openingHours = shop.getOpeningHour(frontBookItemVO.getAppointmentDate());
    Address address = new Address();
    if(shop.getAddresses().size()!=0){
        address = shop.getAddresses().iterator().next();
    }
    request.setAttribute("address", address);
    request.setAttribute("openingHours", openingHours);

    DateTime openTime = openingHours.getOpenTimeObj();
    DateTime closeTime = openingHours.getCloseTimeObj();

    Map<Long, String> morningMap = new LinkedHashMap<>();
    Map<Long, String> afternoonMap = new LinkedHashMap<>();
    Map<Long, String> eveningMap = new LinkedHashMap<>();
    if (openingHours.isOnlineBooking()){
    int timeUnit = 15;

    DateTime current = openTime;
    DateTime morningEnd = openTime.withTimeAtStartOfDay().plusHours(13);

    while (current.isBefore(morningEnd)) {
        Date now=new Date();
        if(current.getMillis()>=now.getTime()){
            if (current.isBefore(closeTime)) {
                morningMap.put(current.getMillis(), current.toString("HH:mm"));
            }
        }
        current = current.plusMinutes(timeUnit);
    }
    if (openTime.isBefore(morningEnd)){
        current = morningEnd;
    }
    DateTime afternoonEnd = morningEnd.plusHours(5);
    while (current.isBefore(afternoonEnd)) {
        Date now=new Date();
        if(current.getMillis()>=now.getTime()) {
            if (current.isBefore(closeTime)){
                afternoonMap.put(current.getMillis(), current.toString("HH:mm"));
            }
        }
        current = current.plusMinutes(timeUnit);
    }
    if (openTime.isBefore(afternoonEnd)){
        current = afternoonEnd;
    }
    while (current.isBefore(closeTime)) {
        Date now=new Date();
        if(current.getMillis()>=now.getTime()) {
            eveningMap.put(current.getMillis(), current.toString("HH:mm"));
        }
        current = current.plusMinutes(timeUnit);
    }
    }
    request.setAttribute("morningMap", morningMap);
    request.setAttribute("afternoonMap", afternoonMap);
    request.setAttribute("eveningMap", eveningMap);
%>

<t:layout>

    <style>
        #shopLocation {
            width: 360px;
            height: 200px;
        }
    </style>
    <div class="booking_detail_ctrl">
        <fmt:formatDate var="appointmentDate" value="${frontBookItemVO.appointmentDate}" pattern="yyyy-MM-dd"/>
        <div class="shop_tab">
            <ul id="shopTab" class="nav nav-tabs">
                <c:forEach items="${shopList}" var="item" varStatus="status">
                    <li class='<c:if test="${frontBookItemVO.shopId eq item.id}">active</c:if>'
                        data-shop-id="${item.id}">
                        <a class="shopLink" href='javascript:' data-shop-id="${item.id}">${item.name}</a>
                    </li>
                </c:forEach>
            </ul>
            <div class="location">
                 <input type="hidden" name="lat" id="lat" value="${address.latitude}"/> 
                 <input type="hidden" name="lng" id="lng" value="${address.longitude}"/> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="location_table">
                    <tr>
                        <td align="left" width="20%"><img
                                src='<c:url value="/resources/img/shop/${currentShop.reference}.jpg"/>'
                                class="img_location"/></td>
                        <td width="50%"><p>
                            <c:if test="${not empty currentShop.address}"><spring:message code="label.sales.location"/> : ${currentShop.address}</c:if>
                            <c:if test="${empty currentShop.address}"><spring:message code="label.sales.location"/>: <spring:message code="front.label.data.is.null"/> </c:if>
                        </p>
                            <p>
                                <c:if test="${not empty currentShop.phoneNumber}"><spring:message code="front.label.tel"/> :${currentShop.phoneNumber}</c:if>
                                <c:if test="${empty currentShop.phoneNumber}"><spring:message code="front.label.tel"/>:<spring:message code="front.label.data.is.null"/></c:if>

                            </p>
                            <p><spring:message code="label.shop.opening.hours"/> : ${openingHours.openTimeObj.toString("HH:mm")}
                                <spring:message code="label.to.date"/> ${openingHours.closeTimeObj.toString("HH:mm")} </p></td>
                        <td align="right" width="30%">
                            <div id="shopLocation"></div>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="booking_form">
                <div class="error">
                    <ul>
                        <c:forEach items="${errors}" var="item">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                </div>
                <form id="frontBookAddForm" action='<c:url value="/front/book/addItem"/>' method="post"
                      class="form-horizontal">
                    <input type="hidden" name="shopId" value="${frontBookItemVO.shopId}"/>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="Treatment_booking">
                        <tr>
                            <td width="18%"><label class="control-label"><spring:message
                                    code="label.guest.amount"/></label></td>
                            <td width="82%">
                                <div class="col-lg-6">
                                    <select id="guestAmount" name="guestAmount"
                                            class="selectpicker form-control guestAmount">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                      <%--  <option value="3">Three Guests</option>
                                        <option value="4">Four Guests</option>
                                        <option value="5">Five Guests</option>--%>
                                    </select>
                                </div>

                                <div id="shareRoomArea" class="col-lg-2 hide">
                                    <spring:message code="front.label.share.room"/> : <input name="shareRoom" type="checkbox" checked/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><label class="control-label"><spring:message code="label.date"/></label></td>
                            <td>
                                <div class="col-lg-6">
                                    <input id="appointmentDate"
                                           name="appointmentDate"
                                           class="form-control"
                                           value='<fmt:formatDate value="${frontBookItemVO.appointmentDate}" pattern="yyyy-MM-dd"/>'
                                           readonly="readonly"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><label class="control-label"><spring:message code="label.time"/></label></td>

                            <td>

                                <div class="col-lg-12">

                                    <ul class="nav nav-tabs">
                                        <c:if test="${morningMap.size()ne 0 or afternoonMap.size()ne 0 or eveningMap.size()ne 0  }">
                                        <c:if test="${morningMap.size() ne 0}">
                                        <li class='active'>
                                            <a href="#morning" data-toggle="tab"><spring:message code="front.label.morning"/> </a>
                                        </li>
                                        </c:if>
                                        <c:if test="${afternoonMap.size() ne 0}">
                                        <li <c:if test="${morningMap.size() eq 0}">class='active'</c:if>>
                                            <a href="#afternoon" data-toggle="tab" ><spring:message code="front.label.afternoon"/> </a>
                                        </li>
                                        </c:if>
                                        <c:if test="${eveningMap.size() ne 0}">
                                        <li <c:if test="${afternoonMap.size() eq 0}">class='active'</c:if>>
                                            <a href="#evening" data-toggle="tab"><spring:message code="front.label.evening"/> </a>
                                        </li>
                                        </c:if>
                                        </c:if>
                                    </ul>
                                    <div id="timestampArea" class="tab-content time_tab">
                                        <c:if test="${morningMap.size()eq 0 and afternoonMap.size()eq 0 and eveningMap.size()eq 0  }">
                                            <p style="color: red"><spring:message code="front.label.statements5"/> </p>
                                        </c:if>

                                        <c:if test="${morningMap.size()ne 0 or afternoonMap.size()ne 0 or eveningMap.size()ne 0  }">

                                            <div id="morning" class="tab-pane fade <c:if test="${morningMap.size() ne 0}">in active </c:if> ">
                                                <ul>
                                                    <c:forEach items="${morningMap}" var="item">
                                                        <li>
                                                            <input type="radio" name="timestamp"
                                                                   value="${item.key}">${item.value}
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                            <div id="afternoon" class="tab-pane fade <c:if test="${morningMap.size() eq 0}">in active </c:if>">
                                                <ul>
                                                    <c:forEach items="${afternoonMap}" var="item">
                                                        <li>
                                                            <input type="radio" name="timestamp"
                                                                   value="${item.key}">${item.value}
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                            <div id="evening" class="tab-pane fade <c:if test="${afternoonMap.size() eq 0}">in active </c:if>">

                                                <ul>
                                                    <c:forEach items="${eveningMap}" var="item">
                                                        <li>
                                                            <input type="radio" name="timestamp"
                                                                   value="${item.key}">${item.value}
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>

                                        </c:if>

                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><label class="control-label"><spring:message code="label.treatment"/></label></td>
                            <td>
                                <div class="col-lg-6">
                                    <div class="select-category" data-selectable="option" data-root-id="2" data-is-online="1" data-shop-id="${frontBookItemVO.shopId}"></div>

                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="therapistArea" class="form-group hide">
                                    <label class="control-label"><spring:message code="label.therapist"/></label>

                                </div>
                            </td>
                            <td>
                                <div class="col-lg-6">
                                    <ul id="therapistList">
                                        <spring:message code="front.label.no.therapist.available"/>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="modal-footer">
                                    <div class="bootstrap-dialog-footer">
                                        <div class="bootstrap-dialog-footer-buttons">
                                            <button type="button"  onclick="postForm()" class="btn btn-primary"><spring:message code="label.button.add"/>
                                            </button>                          </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>

                </form>
            </div>
        </div>
        <div class="booking_comfirn_content">
            <form id="frontBookConfirmForm" action='<c:url value="/front/book/add"/>' method="post"
                  class="form-horizontal">
                <c:set var="firstBookItem" value='${bookItemVOList[0]}'/>
                <input type="hidden" id="currentShopId" value="${firstBookItem.shopId}"/>
                <input type="hidden" id="currentDate"
                       value='<fmt:formatDate value="${firstBookItem.appointmentTime}" pattern="yyyy-MM-dd"/>'/>
                <H3><spring:message code="front.label.booking.item"/> </H3>

                <div class="booking">
                    <c:if test="${firstBookItem ne null}">
                        <div>
                            <spring:message code="label.shop"/> : ${firstBookItem.shop.name}
                        </div>
                        <div>
                            <spring:message code="label.date"/> : <fmt:formatDate value="${firstBookItem.appointmentTime}" pattern="yyyy-MM-dd"/>
                        </div>
                    </c:if>
                </div>
                <div id="bookItemList" class="book-item-list">

                </div>
                <div id="confirmArea" class='${empty bookItemVOList ? "hide" : ""}'>
                    <c:if test="${submitStatus}">
                        <input id="confirm" type="submit" value="Add My Booking To Waiting List" class="btn btn-primary"/>
                    </c:if>
                    <c:if test="${!submitStatus}">
                        <input id="confirm" type="submit" value="Confirm" class="btn btn-primary"/>
                    </c:if>

                </div>
            </form>
        </div>
    </div>

    <script type="text/javascript">
      /*function blockedHint () {
            $("input:checkbox[name='therapistInfo']:checked").each(function() {
                if($(this).val()!=""){
                    var str = $(this).val();
                    var arr = str.split("_");
                    if(arr[1]=="block"){
                        alert("booking will be in waiting list");
            /*            BootstrapDialog.alert(
                            'booking will be in waiting list'
                        )
                    }

                }
            });
        }
		
		
		function blockedHint () {
			
			//console.log('hint start');
            $("input:checkbox[name='therapistInfo']:checked").each(function() {
                if($(this).val()!=""){
					console.log('val != ');
                    var str = $(this).val();
                    var arr = str.split("_");
                    if(arr[1]=="block"){
						//alert('hint');
						console.log('block');
						wait=1;
						setTimeout(function(){
							console.log('wait');
							BootstrapDialog.alert({
                        title: 'NOTIFICATION',
                        message: 'Booking will be in waiting list!'
                    });
					wait=1;
							}, 1000);
                       
                    }
                }
            });
        }*/
		
		function postForm(){
				var pass=1;
                var needCheck = $('.bookItem').length > 0;
                var currentShopId = $('#currentShopId').val();
				
                if (needCheck && currentShopId != $('input[name=shopId]').val()) {
					console.log(currentShopId);
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/> ',
                        message: '<spring:message code="front.label.long.message"/> '
                    });
                    return false;
                }

                var currentDate = $('#currentDate').val();
                var day = new Date().toLocaleDateString();
                var isBeforeDate = new Date($('#frontBookAddForm').val()) <= new Date(day);
                if (isBeforeDate) {
					console.log(currentDate);
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/> ',
                        message: '<spring:message code="front.label.date.invalid"/> '
                    });
									console.log('<spring:message code="label.button.submit"/> ');

                }

                if (needCheck && currentDate != $('#appointmentDate').val()) {
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="front.label.long.message2"/> '
                    });
                    return false;
                }

                if($('#timestampArea').find('input[name=timestamp]:checked').length == 0) {
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="front.label.time.required"/> '
                    });
                    return false;
                }

                if(!$('#productOptionId').val()) {
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="front.label.treatment.required"/> '
                    });
                    return false;
                }
			
			pass=1;
            $("input:checkbox[name='therapistInfo']:checked").each(function() {
                if($(this).val()!=""){
                    var str = $(this).val();
                    var arr = str.split("_");
                    if(arr[1]=="block"){
						//alert('hint');
						console.log('block');
						pass=0;
						BootstrapDialog.show({
							title: '<spring:message code="label.notification"/> ',
							message: '<spring:message code="front.label.long.message3"/> ',
							buttons: [{
								label: '<spring:message code="lable.ok"/> ',
								action: function(dialog) {
									console.log('btn 1');
									$('#frontBookAddForm').submit();
								}
							}]
						});
						pass=0;
                    }
                }
            });
			
			if(pass){
				$('#frontBookAddForm').submit();
			}
				
				
		}
	
     $(function () {
           $('#frontBookAddForm').submit(function () {
				console.log('submit');
                var needCheck = $('.bookItem').length > 0;
                var currentShopId = $('#currentShopId').val();
                if (needCheck && currentShopId != $('input[name=shopId]').val()) {
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="front.label.long.message4"/> '
                    });
                    return false;
                }

                var currentDate = $('#currentDate').val();

                var day = new Date().toLocaleDateString();
                var isBeforeDate = new Date($(this).val()) <= new Date(day);
                if (isBeforeDate) {
                    BootstrapDialog.alert({
                        title: 'Error',
                        message: 'DATE INVALID!'
                    });
                }

                if (needCheck && currentDate != $('#appointmentDate').val()) {
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="front.label.long.message5"/> '
                    });
                    return false;
                }

                if($('#timestampArea').find('input[name=timestamp]:checked').length == 0) {
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="front.label.time.required"/> '
                    });
                    return false;
                }

                if(!$('#productOptionId').val()) {
                    BootstrapDialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="front.label.treatment.required"/> '
                    });
                    return false;
                }
				
				
            });

            var startAjaxLoadTherapist = false;
            $(".shopLink").click(function () {
                var shopId = $(this).data('shop-id');
                $('input[name=shopId]').val(shopId);
                $(".select-category").data('shop-id',shopId);
                frontBookAddFormSubmit();
            });

            $('.select-category').selectCategory({
                callback: function (context) {
                    // 注册变更事件
                    context.find('input[name=displayName]').change(function () {
                        ajaxLoadTherapist();
                    });
                }
            });

            $('#guestAmount').change(function () {
                var therapistArea = $("#therapistArea");
                var shareRoomArea = $("#shareRoomArea");
                var therapistList = $("#therapistList");
                if ($(this).val() > 1) {
                    shareRoomArea.removeClass('hide');
                    therapistArea.addClass('hide');
                    therapistList.addClass('hide').find('input').prop('disabled', true);
                } else {
                    shareRoomArea.addClass('hide');
                    therapistArea.removeClass('hide');
                    therapistList.removeClass('hide').find('input').prop('disabled', false);
                }
            }).trigger('change');

            var dateToday = new Date();
            var currentDate = $('#appointmentDate').val();
            $('#appointmentDate').datetimepicker({
                format: 'Y-m-d',
                timepicker: false,
                datepicker: true,
                minDate: dateToday,
                value: '<fmt:formatDate value="${frontBookItemVO.appointmentDate}" pattern="yyyy-MM-dd"/>'
            }).change(function () {
                if (currentDate != $(this).val()) {
                    frontBookAddFormSubmit();
                }
            });

            $('#timestampArea').find('li').click(function () {
                var timestampRadio = $(this).find('input[name=timestamp]');
                timestampRadio.prop('checked', true);
                ajaxLoadTherapist();
            });

            function frontBookAddFormSubmit() {
                var form = $('#frontBookAddForm');
                window.location.href = '<c:url value="/front/book/toAdd?"/>' + form.serialize();
            }

            function ajaxLoadTherapist() {
                var timestamp = $('input[name=timestamp]:checked').val();
                if (!timestamp) {
                    return;
                }
                var productOptionId = $('input[name=productOptionId]').val();
                if (!productOptionId) {
                    return;
                }
                var param = {
                    timestamp: timestamp,
                    productOptionId: productOptionId,
                    shopId: $('input[name=shopId]').val()
                };
                if (!startAjaxLoadTherapist) {
                    startAjaxLoadTherapist = true;
                    $.post('<c:url value="/front/book/ajaxTherapistList"/>', param, function (res) {
                        $('#therapistList').html(res);
                    }).always(function () {
                        startAjaxLoadTherapist = false;
                    });
                }
            }


            function ajaxLoadBookItemList() {
                $.post('<c:url value="/front/book/ajaxBookItemList"/>', function (res) {
                    $('#bookItemList').html(res);
                });
            }

            ajaxLoadBookItemList();
        });

    </script>
</t:layout>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBNPN-SfdmIFtc8bUaYgUnISu93S1S6oWk&callback=initMap"
	async defer>
</script>
<script type="text/javascript">
	function initMap() {
		var mapProp = null;
		var currentPosition = null;
		var marker = null;
		var addressLon = document.getElementById("lng");
		var addressLat = document.getElementById("lat");	
        
        var lngStr = addressLon.value.replace(/(^\s*)|(\s*$)/g, '');
        var latStr = addressLat.value.replace(/(^\s*)|(\s*$)/g, '');
		if(lngStr != '' && lngStr != undefined && lngStr != null
				&& latStr != '' && latStr != undefined && latStr != null){
			
			currentPosition = new google.maps.LatLng(parseFloat(addressLat.value),parseFloat(addressLon.value));
			
			mapProp = {
					  center: currentPosition,
					  zoom:8
					  };
	        map = new google.maps.Map(document.getElementById("shopLocation"),{
	            center: currentPosition,
	            zoom: 8
	          });
	        marker = new google.maps.Marker({
		    			position : currentPosition
		    		});
		    marker.setMap(map);
		}
        
	};
</script>