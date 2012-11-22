<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Subject List</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<style>
body {
	font-size: 62.5%;
}

label,input {
	display: block;
}

input.text {
	margin-bottom: 12px;
	width: 95%;
	padding: .4em;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

h1 {
	font-size: 1.2em;
	margin: .6em 0;
}

div#courses-contain {
	width: 350px;
	margin: 20px 0;
}

div#courses-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#courses-contain table td,div#courses-contain table th {
	border: 1px solid #eee;
	padding: .6em 10px;
	text-align: left;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

.validateTips {
	border: 1px solid transparent;
	padding: 0.3em;
}
</style>
<script>
	$(function() {
		var subjectName = $("#subjectName"), grade = $("#grade"), allFields = $(
				[]).add(subjectName).add(grade), tips = $(".validateTips");
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
				updateTips("Length of " + n + " must be between " + min
						+ " and " + max + ".");
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
								+ grade.val() + "&id="
								+ editId,
						success : function(response) {
							$('#info').html(response["message"]);
							if (editId < 0) {
								$("#subjects tbody")
										.append(
												"<tr id='"+response.subject.id+"'>"
														+ "<td>"
														+ response.subject.subjectName
														+ "</td>"
														+ "<td>"
														+ response.subject.grade
														+ "</td>"
														+ "<td><a class='edit_subject' href='#"
														+ response.subject.id
														+ "'>edit</a></td>"
														+ "<td><input type='checkbox' name='del_list' value='" + response.subject.id
									+ "'></td>"
														+ "</tr>");
							} else {

								$("tr#" + response.subject.id)
										.replaceWith(
												"<tr id='"+response.subject.id+"'>"
														+ "<td>"
														+ response.subject.subjectName
														+ "</td>"
														+ "<td>"
														+ response.subject.grade
														+ "</td>"
														+ "<td><a class='edit_subject' href='#"
												+ response.subject.id
												+ "'>edit</a></td>"
														+ "<td><input type='checkbox' name='del_list' value='" + response.subject.id
							+ "'></td>"
														+ "</tr>");
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
									&& checkLength(subjectName, "Subject Name",
											2, 25);

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
					var values = $('input[name="del_list"]:checkbox:checked')
							.map(function() {
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
					$("#dialog-form").dialog("open");
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
		});

	});
</script>
</head>
<h1>Existing Subjects:</h1>
<body>

	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div id="dialog-confirm" title="Delete the item?">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>All the Course Categories and Courses related to the category will
				be permanently deleted and cannot be recovered. Are you really sure?
			</p>
		</div>

		<div id="dialog-message" title="No items selected">
			<p>You have not selected any item to delete.</p>
		</div>

		<p id="info"></p>

		<button id="create_subject">Add</button>
		<button id="delete_subjects">Delete</button>

	</sec:authorize>

	<div id="dialog-form" title="Add a new Course Category">
		<p class="validateTips">All form fields are required.</p>

		<form>
			<fieldset>
				<label for="subjectName">Subject Name</label> <input type="text"
					name="subjectName" id="subjectName"
					class="text ui-widget-content ui-corner-all" /> <label for="grade">Grade</label>
				<input type="text" name="grade" id="grade"
					class="text ui-widget-content ui-corner-all" />
			</fieldset>
		</form>
	</div>

	<div id="courses-contain" class="ui-widget">

		<table id="subjects" class="ui-widget ui-widget-content">
			<thead>
				<tr class="ui-widget-header ">
					<th>Subject Name</th>
					<th>Grade</th>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<th>Edit</th>
						<th>Delete<input type="checkbox" name="select-all"
							id="select-all"></th>
					</sec:authorize>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${subjectList}" var="subject" varStatus="status">
					<tr id="${subject.id}">
						<td>${subject.subjectName}</td>
						<td>${subject.grade}</td>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<td><a class="edit_subject" href="#${subject.id}">edit</a></td>
							<td><input type="checkbox" name="del_list"
								value="${subject.id}"></td>
						</sec:authorize>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>