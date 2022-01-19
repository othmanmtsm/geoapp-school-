$(document).ready(function() {
    $.ajax({
        url: "PhoneController",
        data: { op:"getnbpos" },
        method: "GET",
        success: function(data) {
			nb_pos_chart(data);
        }
    });

	$("#show_chart").click(function(){
		var month = $("#months").val();
		$.ajax({
	        url: "PhoneController",
	        data: { op:"getnbpospermonth", month:month },
	        method: "GET",
	        success: function(data) {
				nb_pos_month_chart(data, month);
	        }
	    });
	});

	function nb_pos_chart(data){
		var labels = [];
		var d = [];
		data.forEach(a => {
			console.dir(a);
			labels.push(a[0]);
			d.push(a[1]);
		});
		
		const ctx = document.getElementById('myChart').getContext('2d');
		const myChart = new Chart(ctx, {
		    type: 'bar',
		    data: {
		        labels: labels,
		        datasets: [{
		            label: '# of registered Positions',
		            data: d,
		            backgroundColor: [
		                'rgba(255, 99, 132, 0.2)',
		            ],
		            borderColor: [
		                'rgba(255, 99, 132, 1)',
		            ],
		            borderWidth: 1
		        }]
		    },
		    options: {
		        scales: {
		            y: {
		                beginAtZero: true
		            }
		        }
		    }
		});
	};
	
	var myChart2 = undefined;
	function nb_pos_month_chart(data, month){
		if(myChart2 != undefined){
			myChart2.destroy();
		}
		var labels = [];
		var d = [];
		data.forEach(a => {
			console.dir(a);
			labels.push(a[0]);
			d.push(a[1]);
		});
		const ctx = document.getElementById('myChart2').getContext('2d');
		myChart2 = new Chart(ctx, {
		    type: 'bar',
		    data: {
		        labels: labels,
		        datasets: [{
		            label: `# of registered Positions for the month ${month}`,
		            data: d,
		            backgroundColor: [
		                'rgba(255, 99, 132, 0.2)',
		            ],
		            borderColor: [
		                'rgba(255, 99, 132, 1)',
		            ],
		            borderWidth: 1
		        }]
		    },
		    options: {
		        scales: {
		            y: {
		                beginAtZero: true
		            }
		        }
		    }
		});
	}
})