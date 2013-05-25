$(function() {
	var firstName = $("#firstName"), lastName = $("#lastName"), email = $("#email"), address = $("#address"), contactNumber = $("#contactNumber"), allFields = $(
			[]).add(firstName).add(lastName).add(email).add(address).add(
			contactNumber), tips = $(".validateTips");
	var editId = -1;
	var editUserId = -1;
	var editObject = null;
	function updateTips(t) {
		tips.text(t).addClass("ui-state-highlight");
		setTimeout(function() {
			tips.removeClass("ui-state-highlight", 1500);
		}, 500);
	}

	function checkLength(o, n, min, max) {
		if (o.val().length > max || o.val().length < min) {
			o.addClass("ui-state-error");
			updateTips("Length of " + n + " must be between " + min + " and "
					+ max + ".");
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp(o, regexp, n) {
		if (!(regexp.test(o.val()))) {
			o.addClass("ui-state-error");
			updateTips(n);
			return false;
		} else {
			return true;
		}
	}
	function deleteLecturers() {
		var del_ids = $('input[name="del_list"]:checkbox:checked').map(
				function() {
					return this.value;
				}).get();
		var response = $.ajax({
			type : "POST",
			url : "/JOpenClass/deletelecturers",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(del_ids),
			success : function(response) {
				$('#info').html(response["message"]);
				for ( var i = 0; i < del_ids.length; i++) {
					//alert(del_ids[i]);
					$("tr#" + del_ids[i]).remove();
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});

	}
	function insertLecturer() {
		var data = new Object();
		var lecturer = new Object();
		lecturer.firstName = $('#firstName').val();
		lecturer.lastName = $('#lastName').val();
		lecturer.address = $('#address').val();
		lecturer.contactNumber = $('#contactNumber').val();
		lecturer.user = new Object();
		lecturer.user.email = $('#email').val();
		lecturer.id = editId;
		lecturer.user.id = editUserId;
		data.lecturer = lecturer;
		if (editId > 0) {
			data.lecturer = editObject.lecturer;
			data.lecturer.firstName = $('#firstName').val();
			data.lecturer.lastName = $('#lastName').val();
			data.lecturer.address = $('#address').val();
			data.lecturer.contactNumber = $('#contactNumber').val();
			data.lecturer.user.userRoles = $(
					'input[name="roles"]:checkbox:checked').map(function() {
				return this.value;
			}).get();
			data.lecturer.user.email = $('#email').val();
			data.selectedSubjects = $(".chzn-select").val();
			editObject = null;
		}
		var response = $
				.ajax({
					type : "POST",
					url : "/JOpenClass/savelecturer",
					contentType : "application/json; charset=utf-8",
					data : JSON.stringify(data),
					success : function(response) {
						$('#info').html(response["message"]);
						if (editId < 0) {
							$("#lecturers tbody")
									.append(
											"<tr id='"
													+ response.lecturer.id
													+ "'>"
													+ "<td>"
													+ response.lecturer.firstName
													+ " "
													+ response.lecturer.lastName
													+ "</td>"
													+ "<td>"
													+ response.lecturer.contactNumber
													+ "</td>"
													+ "<td>"
													+ response.lecturer.user.email
													+ "</td>"
													+ "<td>"
													+ response.lecturer.address
													+ "</td>"
													+ "<td><a class='edit_lecturer' href='#"
													+ response.lecturer.id
													+ "'>edit</a></td>"
													+ "<td><input type='checkbox' name='del_list' value='"
													+ response.lecturer.id
													+ "'></td>" + "</tr>");
						} else {

							$("tr#" + response.lecturer.id)
									.replaceWith(
											"<tr id='"
													+ response.lecturer.id
													+ "'>"
													+ "<td>"
													+ response.lecturer.firstName
													+ " "
													+ response.lecturer.lastName
													+ "</td>"
													+ "<td>"
													+ response.lecturer.contactNumber
													+ "</td>"
													+ "<td>"
													+ response.lecturer.user.email
													+ "</td>"
													+ "<td>"
													+ response.lecturer.address
													+ "</td>"
													+ "<td><a class='edit_lecturer' href='#"
													+ response.lecturer.id
													+ "'>edit</a></td>"
													+ "<td><input type='checkbox' name='del_list' value='"
													+ response.lecturer.id
													+ "'></td>" + "</tr>");
						}
						editId = -1;
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
		return response;
	}

	$("#dialog-form")
			.dialog(
					{
						autoOpen : false,
						height : 500,
						width : 400,
						modal : true,
						buttons : {
							"Submit" : function() {
								var bValid = true;
								allFields.removeClass("ui-state-error");

								bValid = bValid
										&& checkLength(firstName, "First Name",
												2, 25);
								bValid = bValid
										&& checkLength(lastName, "Last Name",
												2, 25);

								bValid = bValid
										&& checkLength(email, "email", 4, 80);

								bValid = bValid
										&& checkRegexp(
												email,
												/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
												"eg. ui@jquery.com");
								if (bValid) {

									insertLecturer();
									$(this).dialog("close");
								}
							},
							Cancel : function() {
								$(this).dialog("close");
								editUserId = -1;
							}
						},
						close : function() {
							allFields.val("").removeClass("ui-state-error");
						}
					});

	$("#create-lecturer").button().live('click', function() {
		editId = -1;
		$("#dialog-form").dialog("open");
	});

	$("#dialog-confirm").dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		modal : true,
		buttons : {
			"Delete" : function() {
				$(this).dialog("close");
				deleteLecturers();
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#dialog-message").dialog({
		autoOpen : false,
		modal : true,
		buttons : {
			Ok : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#delete_lecturers").button().live(
			'click',
			function() {
				var values = $('input[name="del_list"]:checkbox:checked').map(
						function() {
							return this.value;
						}).get();
				if (0 < values.length) {
					$("#dialog-confirm").dialog("open");
				} else {
					$("#dialog-message").dialog("open");
				}

			});

	$('#select-all').click(function(event) {
		if (this.checked) {
			$(':checkbox').each(function() {
				this.checked = true;
			});
		} else {
			$(':checkbox').each(function() {
				this.checked = false;
			});
		}
	});

	$('a.edit_lecturer').live(
			'click',
			function() {

				editId = $(this).attr('href').substring(1);
				var response = $.ajax({
					type : "POST",
					url : "/JOpenClass/getlecturerbyid",
					data : "id=" + editId,
					success : function(response) {
						editObject = response;
						$('#info').html(response["message"]);
						$('input#firstName').val(response.lecturer.firstName);
						$('input#lastName').val(response.lecturer.lastName);
						$('input#address').val(response.lecturer.address);
						$('input#email').val(response.lecturer.user.email);
						$('input#contactNumber').val(
								response.lecturer.contactNumber);
						$('input#lec_id').val(response.lecturer.id);
						editUserId = response.lecturer.user.id;
						if (response.lecturer.subjectList != null) {
							var subjects = response.lecturer.subjectList;
							var subjectIds = new Array();
							for ( var i = 0; i < subjects.length; i++) {

								$('option#option' + subjects[i].id).attr(
										'selected', 'selected');
							}
							$('#subjectSelect').trigger("liszt:updated");
						}
						if (response.lecturer.user.userRoles != null) {
							var userRoles = response.lecturer.user.userRoles;
							for ( var i = 0; i < userRoles.length; i++) {
								if (userRoles[i] == 'ROLE_ADMIN') {
									$('#check_admin_role')
											.prop('checked', true);
								}
								if (userRoles[i] == 'ROLE_LEC') {
									$('#check_lec_role').prop('checked', true);
								}

							}
						}
						$("#dialog-form").dialog("open");
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
			});

});