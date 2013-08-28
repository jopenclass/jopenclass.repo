$(function() {
	$("#btnAddStudent").button().live('click', function() {
		addStudent();
	});	
});

function addStudent(){

	var student = new Object();

	student.firstName = $('#firstName').val();
	student.lastName = $('#lastName').val();
	student.address = $('#address').val();
	student.contactNumber = $('#contactNumber').val();
	student.email = $('#email').val();
	student.grade = $('#grade').val();
	student.password = $('#password').val();
	$.ajax({
		type : "POST",
		url : "/JOpenClass/saveStudent",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(student),
		success : function(response) {
			$('#info').html(response.message);
			$('#firstName').val('');
			$('#lastName').val('');
			$('#address').val('');
			$('#contactNumber').val('');
			$('#email').val('');
			$('#grade').val('');
			$('#password').val('');			
			$('#passwordMatch').val('');
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});	
}