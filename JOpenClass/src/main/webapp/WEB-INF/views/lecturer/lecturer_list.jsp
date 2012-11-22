<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!doctype html>
<html lang="en">
<head>

<meta charset="utf-8" />

<title>Lecturer List</title>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/chosen/chosen.css">
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/chosen/chosen.jquery.min.js"
	type="text/javascript"></script>
<style>
body {
	font-size: 62.5%;
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

div#lecturers-contain {
	width: 350px;
	margin: 20px 0;
}

div#lecturers-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#lecturers-contain table td,div#lecturers-contain table th {
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
			if(editId>0){
				data.lecturer = editObject.lecturer;
				data.lecturer.firstName = $('#firstName').val();
				data.lecturer.lastName = $('#lastName').val();
				data.lecturer.address = $('#address').val();
				data.lecturer.contactNumber = $('#contactNumber').val();
				data.lecturer.user.userRoles =  $('input[name="roles"]:checkbox:checked')
				.map(function() {
					return this.value;
				}).get();
				data.lecturer.user.email = $('#email').val();
				data.selectedSubjects = $(".chzn-select").val();
				editObject=null;
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
												"<tr id='"+response.lecturer.id+"'>"
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
														+ "<td><input type='checkbox' name='del_list' value='" + response.lecturer.id
									+ "'></td>"
														+ "</tr>");
							} else {

								$("tr#" + response.lecturer.id)
										.replaceWith(
												"<tr id='"+response.lecturer.id+"'>"
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
														+ "<td><input type='checkbox' name='del_list' value='" + response.lecturer.id
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
											&& checkLength(firstName,
													"First Name", 2, 25);
									bValid = bValid
											&& checkLength(lastName,
													"Last Name", 2, 25);

									bValid = bValid
											&& checkLength(email, "email", 4,
													80);

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

		$('a.edit_lecturer')
				.live(
						'click',
						function() {

							editId = $(this).attr('href').substring(1);
							var response = $
									.ajax({
										type : "POST",
										url : "/JOpenClass/getlecturerbyid",
										data : "id=" + editId,
										success : function(response) {
											editObject = response;
											$('#info')
													.html(response["message"]);
											$('input#firstName')
													.val(
															response.lecturer.firstName);
											$('input#lastName').val(
													response.lecturer.lastName);
											$('input#address').val(
													response.lecturer.address);
											$('input#email')
													.val(
															response.lecturer.user.email);
											$('input#contactNumber')
													.val(
															response.lecturer.contactNumber);
											$('input#lec_id').val(
													response.lecturer.id);
											editUserId = response.lecturer.user.id;
											if (response.lecturer.subjectList != null) {
												var subjects = response.lecturer.subjectList;
												var subjectIds = new Array();
												for(var i=0;i<subjects.length;i++){
													
													$('option#option'+subjects[i].id).attr('selected', 'selected');	
												}
												$('#subjectSelect').trigger("liszt:updated");
											}
											if(response.lecturer.user.userRoles!=null){
												var userRoles = response.lecturer.user.userRoles;
												for(var i=0;i<userRoles.length;i++){
													if(userRoles[i]=='ROLE_ADMIN'){
														$('#check_admin_role').prop('checked', true);
													}
													if(userRoles[i]=='ROLE_LEC'){
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
</script>

</head>

<h1>Existing Lecturers:</h1>

<body>

	<sec:authorize access="hasRole('ROLE_ADMIN')">

		<div id="dialog-confirm" title="Delete the item?">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>Items/item will
				be permanently deleted and cannot be recovered. Are you sure?
			</p>
		</div>

		<div id="dialog-message" title="No items selected">
			<p>You have not selected any item to delete.</p>
		</div>
		<p id="info"></p>
		<!-- 		<button id="create-lecturer">Add</button> -->
		<button id="delete_lecturers">Delete</button>

	</sec:authorize>


	<div id="dialog-form" title="Lecturer">
		<p class="validateTips">All form fields are required.</p>

		<form>
			<fieldset>
				<label for="firstName">First Name</label> <input type="text"
					name="firstName" id="firstName"
					class="text ui-widget-content ui-corner-all" /> <label
					for="lastName">Last Name</label> <input type="text" name="lastName"
					id="lastName" class="text ui-widget-content ui-corner-all" /> <label
					for="contactNumber">Contact Number</label> <input type="text"
					name="contactNumber" id="contactNumber"
					class="text ui-widget-content ui-corner-all" /> <label
					for="address">Residential Address</label> <input type="text"
					name="address" id="address"
					class="text ui-widget-content ui-corner-all" /> <label for="email">Email</label>
				<input type="text" name="email" id="email" value=""
					class="text ui-widget-content ui-corner-all" /> <input
					type="hidden" name="lec_id" id="lec_id" value="" /> <label
					for="userRoles">User Roles</label> <input type="checkbox"
					name="roles" id="check_lec_role" value="ROLE_LEC">Lecturer
				<input type="checkbox" name="roles" id="check_admin_role"
					value="ROLE_ADMIN">Admin <select id='subjectSelect' name="selected"
					data-placeholder="Choose subjects" class="chzn-select" multiple
					style="width: 350px;" tabindex="2">
					<option value=""></option>
					<c:forEach items="${allSubjects}" var="subject" varStatus="status">
						<option id="option${subject.id}" value="${subject.id}">${subject.subjectName}</option>
					</c:forEach>
				</select>
				<script type="text/javascript">
					$(".chzn-select").chosen();
					$(".chzn-select-deselect").chosen({
						allow_single_deselect : true
					});
				</script>
			</fieldset>
		</form>
	</div>


	<div id="lecturers-contain" class="ui-widget">

		<table id="lecturers" class="ui-widget ui-widget-content">

			<thead>

				<tr class="ui-widget-header ">

					<th>Lecturer Name</th>
					<th>Contact Number</th>
					<th>Email Address</th>
					<th>Residential Address</th>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<th>Edit</th>
						<th>Delete<input type="checkbox" name="select-all"
							id="select-all"></th>
					</sec:authorize>
				</tr>

			</thead>

			<tbody>

				<c:forEach items="${lecturerList}" var="lecturer" varStatus="status">

					<tr id="${lecturer.id}">

						<td>${lecturer.firstName}&nbsp;${lecturer.lastName}</td>
						<td>${lecturer.contactNumber}</td>
						<td>${lecturer.user.email}</td>
						<td>${lecturer.address }</td>

						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<td><a class="edit_lecturer" href="#${lecturer.id}">edit</a></td>
							<td><input type="checkbox" name="del_list"
								value="${lecturer.id}"></td>
						</sec:authorize>

					</tr>

				</c:forEach>

			</tbody>

		</table>

	</div>

</body>

</html>