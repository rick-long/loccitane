<%--
  Created by IntelliJ IDEA.
  User: hary
  Date: 2017/3/22
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style>
        #shopLocation {
            width: 300px;
            height: 200px;
        }
    </style>
</head>
<body>

<div class="location">

    <div id="shopLocation"></div>


</div>

<script type="text/javascript">
    function initMap() {
        var myLatLng = {lat: -25.363, lng: 131.044};

        var map = new google.maps.Map(document.getElementById('shopLocation'), {
            zoom: 18,
            center: myLatLng
        });

        var marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            title: 'Hello World!'
        });
    }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDZ-LWdkc4FnsNr5vtQyTLKSgnnxSasKFA&callback=initMap"></script>

</body>
</html>
