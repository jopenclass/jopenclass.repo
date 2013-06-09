$(function() {

	$('#featureBtn').button().live(
			'click',
			function() {
				var featureIds = $(
						'input[name="feature_list"]:checkbox:checked').map(
						function() {
							return this.value;
						}).get();

				var response = $.ajax({
					type : "POST",
					url : "/JOpenClass/featureBatches",
					contentType : "application/json; charset=utf-8",
					data : JSON.stringify(featureIds),
					success : function(response) {
						$('#info').html(response["message"]);
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
			});

});