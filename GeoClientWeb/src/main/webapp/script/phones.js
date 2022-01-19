$(document).ready(function() {

    $.ajax({
        url: "UserController",
        data: { op:"load" },
        method: "POST",
        success: function(data) {
			console.dir(data);
            fillSelect(data);
        }
    });

	$.ajax({
        url: "PhoneController",
        data: { op:"load" },
        method: "POST",
        success: function(data) {
			console.dir(data);
            fillTable(data);
        }
    });

	$("#add").click(function() {
		var imei = $("#imei").val();
		var user = $("#users").val();
		console.log(user);
		$.ajax({
			url: "PhoneController",
			data: {imei: imei, user: user},
			method: "POST",
			success: function(data) {
				fillTable(data);
			}
		});
	});

	function fillTable(data) {
		var str = "";
		data.forEach(e => {
			str += "<tr><td>" + e + "</td><td><a href='/GeoClientWeb/map.html?imei=" + e + "'>Show Map</a></td></tr>";
		});
		$("#content").html(str);
	}
	
	function fillSelect(data) {
		var str = "";
		data.forEach(e => {
			str += "<option value= "+ e.id + ">" + e.name + "</option>";
		});
		$('#users').html(str);
	}
	
	$(document).on("click", "input.del" , function(e) {
		var code = e.target.attributes['code'].value;
		console.log(code);
		$.ajax({
			url: "UserController",
			data: {code: code, op: 'delete'},
			method: 'POST',
			success: function(data) {
				remplir(data);
			}
		});
    });
});

