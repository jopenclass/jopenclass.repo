	$(function() {

		function insertLecturer() {
			var data = new Object();
			var lecturer = new Object();

			lecturer.firstName = $('#firstName').val();
			lecturer.lastName = $('#lastName').val();
			lecturer.address = $('#address').val();
			lecturer.contactNumber = $('#contactNumber').val();
			lecturer.user = new Object();
			lecturer.user.enabled = true;
			lecturer.user.email = $('#email').val();
			lecturer.user.password = $('#password').val();
			lecturer.user.enabled = $("input:radio:checked").val();
			//lecturer.subjectList = $(".chzn-select").val();
			lecturer.user.userRoles = $('input[name="roles"]:checkbox:checked')
					.map(function() {
						return this.value;
					}).get();
			lecturer.id = -1;
			data.lecturer = lecturer;
			data.selectedSubjects = $(".chzn-select").val();
			$.ajax({
				type : "POST",
				url : "/JOpenClass/savelecturer",
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify(data),
				success : function(response) {
					$('#info').html(response.message);
					$('#firstName').val('');
					$('#lastName').val('');
					$('#address').val('');
					$('#contactNumber').val('');
					$('#email').val('');
					$('#password').val('');
					$('#passwordMatch').val('');
					$('#ROLE_LEC').prop('checked', false);
					$('#ROLE_ADMIN').prop('checked', false);
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		$("#dialog-message").dialog({
			modal : true,
			autoOpen : false,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#create-lecturer").button().live('click', function() {
			var pass = $('#password').val();
			var passMatch = $('#passwordMatch').val();
			if (pass != passMatch || (pass.length <= 0)) {
				$("#dialog-message").dialog("open");
			} else {
				insertLecturer();
			}
		});
	});