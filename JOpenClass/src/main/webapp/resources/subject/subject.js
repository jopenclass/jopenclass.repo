
	function insertSubject() {

		var subjectName = $('#subjectName').val();
		var grade = $('#grade').val();
		var subjectDetails = $('#subjectDetails').val();

		$.ajax({
			type : "POST",
			url : "/JOpenClass/savesubject",
			data : "subjectName=" + subjectName + "&grade=" + grade+ "&subjectDetails=" + subjectDetails
					+ "&id=-1",
			success : function(response) {
				$('#info').html(response.message);
				$('#subjectName').val('');
				$('#grade').val('');
				$('#subjectDetails').val('');
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}