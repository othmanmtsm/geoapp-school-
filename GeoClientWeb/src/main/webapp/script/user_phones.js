$(document).ready(function() {

    $.ajax({
        url: "UserController",
        data: { op:"load" },
        method: "POST",
        success: function(data) {
            fillSelect(data);
        }
    });

	$("#find").click(function() {
		var user = $("#users").val();
		$.ajax({
			url: "UserController",
			data: {op: "getphones", user: user},
			method: "POST",
			success: function(data) {
				fillPhones(data);
			}
		});
	});

	function fillSelect(data) {
		var str = "";
		data.forEach(e => {
			str += "<option value= "+ e.id + ">" + e.name + "</option>";
		});
		$('#users').html(str);
	}
	
	function fillPhones(data){
		var str = "";
		data.forEach(e => {
			str += "<li class='list-group-item'><div> Imei : <a href='/GeoClientWeb/map.html?imei="+e+"'>" + e +"</a></div></li>";
		});
		$('#phones').html(str);
	}
});
