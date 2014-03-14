$(function() {

$("#editProfileBtn").button().live('click', function() {
		var response = $.ajax({
			type : "POST",
			url : "/JOpenClass/getloggedstudentinfo",
			success : function(response) {
				//console.log(response);
				$('input#firstName').val(response.lecturer.firstName);
				$('input#lastName').val(response.lecturer.lastName);
				$('input#address').val(response.lecturer.address);
				$('input#contactNumber').val(response.lecturer.contactNumber);
				$('input#lecturerInfo').val(response.lecturer.lecturerInfo);
				$('input#email').val(response.lecturer.email);
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});


$("#saveProfileBtn").button().live('click', function() {

		var data = new Object();
		data.student = new Object();
		data.student.firstName = $('input#firstName').val();
		data.student.lastName = $('input#lastName').val();
		data.student.address = $('input#address').val();
		data.student.contactNumber = $('input#contactNumber').val();
		data.student.email = $('input#email').val();

		$('#editProfileModal').modal('hide');
		var response = $.ajax({

			type : "POST",
			url : "/JOpenClass/saveloggedstudentinfo",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(data),
			success : function(response) {
				document.getElementById("info").innerHTML = "successfully updated";//response.message;
				document.getElementById("tbFirstName").innerHTML = $('input#firstName').val();
				document.getElementById("tbLastName").innerHTML = $('input#lastName').val();
				document.getElementById("tbAddress").innerHTML = $('input#address').val();
				document.getElementById("tbContactNumber").innerHTML = $('input#contactNumber').val();				
				if(response.emailChanged=="yes"){
					$('#loginMsgModal').modal('show');
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});

$('#changePassBtn')
.click(
		function(event) {

			var newPass = $('input#newPassword').val();
			var newPassConfirm = $('input#newPassConfirm').val();

			if (newPass != newPassConfirm) {
				document.getElementById("passMisMatchErr").innerHTML = "password mismatch";

			} else {
				$('#chagePassModal').modal('hide');

				var dataObj = new Object();
				dataObj.password = $('input#password').val();
				dataObj.newPassword = newPass;
				dataObj.newPassConfirm = newPassConfirm;

				var response = $
						.ajax({
							type : "POST",
							url : "/JOpenClass/changepass",
							contentType : "application/json; charset=utf-8",
							data : JSON.stringify(dataObj),
							success : function(response) {
								$('#info')
										.html(response["message"]);
							},
							error : function(e) {
								alert('Error: ' + e);
							}
						});

			}
		});

});