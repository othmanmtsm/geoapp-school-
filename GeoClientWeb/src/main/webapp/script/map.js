$(document).ready(function() {	
	var mapOptions = {
	   center: [31.625522195254273, -7.989292144775391],
	   zoom: 15
	}
	var map = L.map('map', mapOptions);
	var layer = new L.TileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png');
	map.addLayer(layer);
	
	var params = (new URL(document.location)).searchParams;
	
	$("#title").html("Loactions for : " + params.get("imei"));
	
	$("#search").click(function() {
		var imei = params.get("imei");
		var date1 = $("#date1").val();
		var date2 = $("#date2").val();

		$.ajax({
			url: "PhoneController",
			data: {op: "getpos", imei: imei, date1: date1, date2: date2},
			method: "POST",
			success: function(data) {
				drawMarker(data);
			}
		});
	});
	
	var markers = [];
	
	function drawMarker(data) {
		markers.forEach(m => {
			m.remove();
		});
		data.forEach(e => {
			var marker = new L.Marker([e.latitude, e.longitude]);
			marker.addTo(map);
			marker.bindPopup('Date : ' + e.date);
			markers.push(marker);
		});
	}
});
