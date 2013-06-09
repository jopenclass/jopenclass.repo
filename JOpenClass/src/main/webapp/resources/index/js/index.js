$(function() {
	$('a.showCourseBtn').live(
			'click',
			function() {

				viewBatchId = $(this).attr('id').substring(4);
				var response = $.ajax({
					type : "POST",
					url : "/JOpenClass/getbatchbyid",
					data : "id=" + viewBatchId,
					success : function(response) {
						console.log(response);
						$('#courseContentSection').html("This course is conducted by" +
								"" +response.batch.lecturer.firstName+
								" " +response.batch.lecturer.lastName+
								"<br/>subject: " +response.batch.subject.subjectName+
								"<br/>grade: " +response.batch.subject.grade+
								"<br/>Fee: " +response.batch.fee+
								"<br/>Intake: " +response.batch.intake+
								"<br/>commence date: " +response.batch.commenceDate+
								"<br/>subject: " +response.batch.subject.subjectName+
								"<br/>Further details: " +response.batch.scheduleDiscription+
								
								"");
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
			});

});