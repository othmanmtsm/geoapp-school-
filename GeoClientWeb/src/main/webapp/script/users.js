$(document).ready(function() {

    $.ajax({
        url: "UserController",
        data: { op:"load" },
        method: "POST",
        success: function(data) {
			console.dir(data);
            remplir(data);
        }
    });

	$("#add").click(function() {
		var birthdate = $("#birthdate").val();
		var email = $("#email").val();
		var name = $("#name").val();
		var password = $("#password").val();
		$.ajax({
			url: "UserController",
			data: {birthdate: birthdate, email: email, name: name, password: password},
			method: "POST",
			success: function(data) {
				remplir(data);
			}
		});
	});

	function remplir(data) {
		var ligne = "";
		data.forEach(e => {
			ligne += "<tr><td>" + e.birthDate + "</td><td>" + e.name + "</td><td>" + e.email + "</td><td><input class='del' type='submit' value='Supprimer' code='" + e.id + "'/></td><td>Modifier</td></tr>";
		});
		$("#content").html(ligne);
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

