<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<div>
<!-- 	<div style="height: 42px;"> -->

<!-- 		<div style="width: 280px; float: left;"> -->
<%-- 			<label class="control-label"><spring:message --%>
<%-- 					code="label.google.map.position.type" />&nbsp;</label> <label --%>
<!-- 				class="control-label"> <select -->
<!-- 				class="selectpicker form-control" id="positionTypeSelectId" -->
<!-- 				onchange="chosePositionType();"> -->
<%-- 					<option value="true"><spring:message --%>
<%-- 							code="label.google.map.position.type.address" /></option> --%>
<%-- 					<option value=""><spring:message --%>
<%-- 							code="label.google.map.position.type.lonlat" /></option> --%>
<!-- 			</select></label> -->
<!-- 		</div> -->

<!-- 	</div> -->
<!-- 	<div style="float: left;" id="addressPositionId"> -->
<%-- 		<label class="control-label"><spring:message --%>
<%-- 				code="label.google.map.address" /> &nbsp;</label><label --%>
<!-- 			class="control-label"> <input id="address" -->
<!-- 			class="form-control" placeholder="Enter your address" /></label> -->

<!-- 	</div> -->
	<div style="float: left; display: none; width: 183px;"
		id="longitudePositionId">
		<label class="control-label"><spring:message
				code="label.google.map.longitude" /> &nbsp;</label><label
			class="control-label" style="width: 108px;"> <input
			path="longitude" id="longitude" name="longitude" class="form-control" /></label>

	</div>
	<div style="float: left; display: none;" id="latitudePositionId">
		<label class="control-label"><spring:message
				code="label.google.map.latitude" /> &nbsp;</label><label
			class="control-label" style="width: 108px;"> <input
			path="latitude" id="latitude" name="latitude" class="form-control" /></label>

	</div>
<!-- 	<div class="form-group col-md-12" -->
<!-- 		style="width: 64px; line-height: 23px;"> -->
<!-- 		<a href="javascript:;" id="positionSearch" -->
<!-- 			class="btn btn-default search-btn"> <i -->
<!-- 			class="glyphicon glyphicon-search"></i> -->
<!-- 		</a> -->
<!-- 	</div> -->
	<div id="googleMap" style="width: 100%; height: 380px; margin: 0 auto;"></div>
	<div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
               <button class="btn btn-primary" id="okBtn">OK</button>
            </div>
        </div>
    </div>
</div>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBNPN-SfdmIFtc8bUaYgUnISu93S1S6oWk&libraries=places&callback=initMap"
	async defer>
</script>

<script type="text/javascript">

	var selectId = document.getElementById("positionTypeSelectId");
	var addressPositionId = document.getElementById("addressPositionId");
	var latitudePositionId = document.getElementById("latitudePositionId");
	var longitudePositionId = document.getElementById("longitudePositionId");

	var addressInput = document.getElementById("address");
	var longitudeInput = document.getElementById("longitude");
	var latitudeInput = document.getElementById("latitude");
	$(document).ready(function() {
	});

	function chosePositionType() {
		if (0 == selectId.selectedIndex) {
			addressPositionId.style.display = "block";
			latitudePositionId.style.display = "none";
			longitudePositionId.style.display = "none";
			longitudeInput.value = '';
			latitudeInput.value = '';
		} else {
			addressInput.value = '';
			addressPositionId.style.display = "none";
			latitudePositionId.style.display = "block";
			longitudePositionId.style.display = "block";
		}
	};
	// google map初始化部件
	var map,service;
	// 标记数列
	var markersArray = [];
	// 浏览器定位的当前位置
	var currentPosition = null;
	function initMap() {
		var mapProp = null;
    	if (navigator.geolocation){
    		navigator.geolocation.getCurrentPosition(function(data){
    			console.log(data);
    			currentPosition = new google.maps.LatLng(data.coords.latitude, data.coords.longitude);
                mapProp = {
            	        center : currentPosition,
            			zoom : 5,
            			mapTypeId : google.maps.MapTypeId.ROADMAP
            		};
        	    document.getElementById("lat").value = data.coords.latitude;
        	    document.getElementById("lng").value = data.coords.longitude;
        	    
        	    // 根据 当前的经纬度去查找附近的地址
        	    var geocoder = new google.maps.Geocoder;
        	    
        	    var latlng = {lat: data.coords.latitude, lng: data.coords.longitude};
                
                geocoder.geocode({'location': latlng}, function(results, status) {
                    if (status === 'OK') {
                    	console.log(results);
                      if (results[0]) {
                    	  var result = results[0];
                    	  document.getElementById("currentAddress").value = result.formatted_address;
                      } 
                    }
                  });
        	    
        		map = new google.maps.Map(document.getElementById("googleMap"),
        				mapProp);
                
        		var marker = new google.maps.Marker({
        			position : currentPosition,
        		});
        		marker.setMap(map);
        		markersArray.push(marker);
    		},function(err){
    			alert("Browser does not support geolocation");
    		});
    	}else {
    		alert("Browser does not support geolocation");
    	}
	};

	$("#positionSearch").bind("click", function() {
	//    每次查询时把上次查询的地点标记清除,以免混淆 
		if (markersArray) {
		      for (i in markersArray) {
		        markersArray[i].setMap(null);
		      }
		    }
		if (0 == selectId.selectedIndex) {
			//去除空格
			var str = addressInput.value.replace(/(^\s*)|(\s*$)/g, '');
			if(str == '' || str == undefined || str == null){
				alert("Please input correct content!!!");
				return;
			}
		 var request = {
				 location: currentPosition,
				 radius: '500',
				 query: addressInput.value
			};
		 service = new google.maps.places.PlacesService(map);
		 service.textSearch(request, callback);
			
		}

	});
	function callback(results, status) {
		  if (status == google.maps.places.PlacesServiceStatus.OK) {
		    for (var i = 0; i < results.length; i++) {
		    	var place = results[i];
		    	currentPosition = new google.maps.LatLng(place.geometry.location.lat(), place.geometry.location.lng());
		    	if(i==0){
	                mapProp = {
	            	        center : currentPosition,
	            			zoom : 15,
	            			mapTypeId : google.maps.MapTypeId.ROADMAP
	            		};
	        		map = new google.maps.Map(document.getElementById("googleMap"),
	        				mapProp);
		    	}
        		var marker = new google.maps.Marker({
        			position : currentPosition ,
        		});
        		marker.setMap(map);
        		
        		var infowindow = new google.maps.InfoWindow({
        			  content:"Hello World!"
        			  });

        		google.maps.event.addListener(marker, 'click', function() {
        			  infowindow.open(map,marker);
        		});
        		
        		markersArray.push(marker);
		    }
		  }
	}
	$("#okBtn").bind("click", function() {
		 Dialog.get().close();
		});
</script>
