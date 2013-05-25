$(function() {
	var subjectName = $("#subjectName"), grade = $("#grade"), subjectDetails = $("#subjectDetails"), allFields = $(
			[]).add(subjectName).add(grade).add(subjectDetails), tips = $(".validateTips");
	var editId = -1;
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
	function deleteSubjects() {
		var del_ids = $('input[name="del_list"]:checkbox:checked').map(
				function() {
					return this.value;
				}).get();

		var response = $.ajax({
			type : "POST",
			url : "/JOpenClass/deletesubjects",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(del_ids),
			success : function(response) {
				$('#info').html(response["message"]);
				del_ids = response.delList;
				for ( var i = 0; i < del_ids.length; i++) {
					$("tr#" + del_ids[i]).remove();
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});

	}
	function insertSubject() {
		var response = $
				.ajax({
					type : "POST",
					url : "/JOpenClass/savesubject",
					data : "subjectName=" + subjectName.val() + "&grade="
							+ grade.val() + "&id=" + editId
							+ "&subjectDetails="
							+ $('input#subjectDetails').val(),
					success : function(response) {
						$('#info').html(response["message"]);
						if (editId < 0) {
							$("#subjects tbody")
									.append(
											"<tr id='"
													+ response.subject.id
													+ "'>"
													+ "<td>"
													+ response.subject.subjectName
													+ "</td>"
													+ "<td>"
													+ response.subject.grade
													+ "</td>"
													+ "<td><a class='edit_subject' href='#"
													+ response.subject.id
													+ "'>edit</a></td>"
													+ "<td><input type='checkbox' name='del_list' value='"
													+ response.subject.id
													+ "'></td>" + "</tr>");
						} else {

							$("tr#" + response.subject.id)
									.replaceWith(
											"<tr id='"
													+ response.subject.id
													+ "'>"
													+ "<td>"
													+ response.subject.subjectName
													+ "</td>"
													+ "<td>"
													+ response.subject.grade
													+ "</td>"
													+ "<td><a class='edit_subject' href='#"
													+ response.subject.id
													+ "'>edit</a></td>"
													+ "<td><input type='checkbox' name='del_list' value='"
													+ response.subject.id
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

	$("#dialog-form").dialog(
			{
				autoOpen : false,
				height : 350,
				width : 400,
				modal : true,
				buttons : {
					"Submit" : function() {
						var bValid = true;
						allFields.removeClass("ui-state-error");

						bValid = bValid
								&& checkLength(subjectName, "Subject Name", 2,
										25);

						if (bValid) {

							insertSubject();
							$(this).dialog("close");
						}
					},
					Cancel : function() {
						$(this).dialog("close");
					}
				},
				close : function() {
					allFields.val("").removeClass("ui-state-error");
				}
			});

	$("#create_subject").button().live('click', function() {
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
				deleteSubjects();
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

	$("#delete_subjects").button().live(
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

	$('a.edit_subject').live('click', function() {
		editId = $(this).attr('href').substring(1);
		alert(editId);
		var response = $.ajax({
			type : "POST",
			url : "/JOpenClass/getsubjectbyid",
			data : "id=" + editId,
			success : function(response) {
				$('#info').html(response["message"]);
				$('input#subjectName').val(response.subject.subjectName);
				$('input#grade').val(response.subject.grade);
				$('input#subjectDetails').val(response.subject.subjectDetails)
				$("#dialog-form").dialog("open");
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});

});