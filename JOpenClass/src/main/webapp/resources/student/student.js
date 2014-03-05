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
	student.enabled = $("#isActive").is(":checked");
	student.userRoles = $('input[name="roles"]:checkbox:checked')
	.map(function() {
		return this.value;
	}).get();
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
			$("#isActive").attr('checked', false);
			$('#ROLE_LEC').prop('checked', false);
			$('#ROLE_ADMIN').prop('checked', false);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});	
}