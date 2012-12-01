<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jopen Class</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<style type="text/css">
body {
	font-size: 70%;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

.validateTips {
	border: 1px solid transparent;
	padding: 0.3em;
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
</style>
<script>
	$(function() {
		var editBatchId = -1;
		$("#commenceDate").datepicker();
		$("#commenceDate").datepicker("option", "dateFormat", "yy-mm-dd");
		$("#tabs").tabs();

		var batchName = $("#batchName").val(), intake = $("#intake").val(), fee = $(
				"#fee").val(), commenceDate = $("#commenceDate").val(), scheduleDiscription = $(
				"#scheduleDiscription").val(), subject = $("#subject").val(), allFields = $(
				[]).add(batchName).add(intake).add(fee).add(commenceDate).add(
				scheduleDiscription).add(subject), tips = $(".validateTips");

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
				Object
				o.addClass("ui-state-error");
				updateTips(n);
				return false;
			} else {
				return true;
			}
		}

		function createBatch() {

			var data = new Object();
			data.subject = $("#subject").val();
			data.batch = new Object();
			data.batch.id = editBatchId;
			data.batch.batchName = $('#batchName').val();
			data.batch.intake = $('#intake').val();
			data.batch.fee = $('#fee').val();
			data.batch.commenceDate = $('#commenceDate').val();
			data.batch.scheduleDiscription = $('#scheduleDiscription').val();

			$
					.ajax({
						type : "POST",
						url : "/JOpenClass/createbatch",
						contentType : "application/json; charset=utf-8",
						data : JSON.stringify(data),
						success : function(response) {
							$('#info').html(response.message);
							$('#batchName').val('');
							$('#intake').val('');
							$('#fee').val('');
							$('#commenceDate').val('');
							$('#scheduleDiscription').val('');

							if (editBatchId < 0) {
								$("#batches tbody")
										.append(
												"<tr id='batch"+response.batch.id+"'>"
														+ "<td><a href='#'>"
														+ response.batch.batchName
														+ "</a></td>"
														+ "<td>"
														+ response.batch.subject.subjectName
														+ "</td>"
														+ "<td>"
														+ response.batch.subject.grade
														+ "</td>"
														+ "<td>"
														+ response.batch.intake
														+ "</td>"
														+ "<td>"
														+ response.batch.fee
														+ "</td>"
														+ "<td><a class='edit_batch' href='#"
												+ response.batch.id
												+ "'>edit</a></td>"
														+ "<td><input type='checkbox' name='batch_del_list' value='" + response.batch.id
							+ "'></td>"
														+ "</tr>");
							} else {
								
								$("tr#batch" + response.batch.id)
										.replaceWith(
												"<tr id='batch"+response.batch.id+"'>"
														+ "<td><a href='#'>"
														+ response.batch.batchName
														+ "</a></td>"
														+ "<td>"
														+ response.batch.subject.subjectName
														+ "</td>"
														+ "<td>"
														+ response.batch.subject.grade
														+ "</td>"
														+ "<td>"
														+ response.batch.intake
														+ "</td>"
														+ "<td>"
														+ response.batch.fee
														+ "</td>"
														+ "<td><a class='edit_batch' href='#"
								+ response.batch.id
								+ "'>edit</a></td>"
														+ "<td><input type='checkbox' name='batch_del_list' value='" + response.batch.id
			+ "'></td>"
														+ "</tr>");
								editBatchId = -1;
							}
						},
						error : function(e) {
							alert('Error: ' + e);
						}
					});
		}

		$("#dialog-form-batch").dialog({
			autoOpen : false,
			height : 450,
			width : 400,
			modal : true,
			buttons : {
				"Submit" : function() {

					var bValid = true;
					allFields.removeClass("ui-state-error");

					// 							bValid = bValid
					// 									&& checkLength(batchName,
					// 											"Class Room Name", 1, 30);
					// 							bValid = bValid && checkLength(fee, "Fee", 1, 30);
					// 							bValid = bValid
					// 									&& checkLength(Intake, "Intake", 1,
					// 											30);
					if (bValid) {

						createBatch();
						$(this).dialog("close");
					}
				},
				Cancel : function() {
					$(this).dialog("close");
					editBatchId = -1;
				}
			},
			close : function() {
				allFields.val("").removeClass("ui-state-error");
			}
		});

		$("#create-batch").button().live('click', function() {
			editBatchId = -1;
			$("#dialog-form-batch").dialog("open");
		});
		$('a.edit_batch').live(
				'click',
				function() {

					editBatchId = $(this).attr('href').substring(1);
					var response = $.ajax({
						type : "POST",
						url : "/JOpenClass/getbatchbyid",
						data : "id=" + editBatchId,
						success : function(response) {
							$('#info').html(response["message"]);
							$('input#batchName').val(response.batch.batchName);
							$('input#intake').val(response.batch.intake);
							$('input#fee').val(response.batch.fee);
							$('input#commenceDate').val(
									response.batch.commenceDate);
							$('input#scheduleDiscription').val(
									response.batch.scheduleDiscription);
							$('select#subject').val(response.batch.subject.id);
							editBatchId = response.batch.id;

							$("#dialog-form-batch").dialog("open");
						},
						error : function(e) {
							alert('Error: ' + e);
						}
					});
				});

		$("#delete-batch").button().live(
				'click',
				function() {

					var values = $(
							'input[name="batch_del_list"]:checkbox:checked')
							.map(function() {
								return this.value;
							}).get();
					if (0 < values.length) {
						$("#dialog-confirm-batchdelete").dialog("open");
					} else {
						$("#dialog-message-noitem").dialog("open");
					}

				});
		$("#dialog-message-noitem").dialog({
			autoOpen : false,
			modal : true,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#dialog-confirm-batchdelete").dialog({

			autoOpen : false,
			resizable : false,
			height : 180,
			width : 350,
			modal : true,
			buttons : {
				"Delete" : function() {
					$(this).dialog("close");
					deleteBatches();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});

		function deleteBatches() {
			var delIds = $('input[name="batch_del_list"]:checkbox:checked')
					.map(function() {
						return this.value;
					}).get();
			var response = $.ajax({
				type : "POST",
				url : "/JOpenClass/deletebatches",
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify(delIds),
				success : function(response) {
					$('#info').html(response["message"]);
					delIds = response.delList;
					for ( var i = 0; i < delIds.length; i++) {
						$("tr#batch" + delIds[i]).remove();
					}
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}

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

	});
</script>
</head>
<body>
	<div id="tabs">
		<ul>
			<li><a href="#profilediv">Profile</a></li>
			<li><a href="#batchsdiv">Class Rooms/Batches</a></li>
			<li><a href="#subjectdiv">Subjects</a></li>
		</ul>
		<div id="profilediv">
			<div class="fieldName">First name : ${lecturer.firstName}</div>
			<div class="fieldName">Last name : ${lecturer.lastName}</div>
			<div class="fieldName">Email address : ${lecturer.user.email}</div>
			<div class="fieldName">Residential address :
				${lecturer.address}</div>
			<div class="fieldName">Contact number :
				${lecturer.contactNumber}</div>
			<div class="fieldName">Lecturer Information :
				${lecturer.lecturerInfo}</div>

		</div>

		<div id="batchsdiv">
			<button id="create-batch">Create Class Room/Batch</button>
			<button id="delete-batch">Delete Class Room/Batch</button>
			<div id="info"></div>

			<div id="dialog-confirm-batchdelete" title="Delete the item?">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin: 0 7px 20px 0;"></span>All the Batches
					you selected will be permanently deleted and cannot be recovered.
					Are you really sure?
				</p>
			</div>

			<div id="dialog-message-noitem" title="No items selected">
				<p>You have not selected any item to delete.</p>
			</div>
			<div id="courses-contain" class="ui-widget">

				<table id="batches" class="ui-widget ui-widget-content">
					<thead>
						<tr class="ui-widget-header ">
							<th>Batch/Class Name</th>
							<th>Subject Name</th>
							<th>Grade</th>
							<th>Intake</th>
							<th>fee</th>
							<th>Edit</th>
							<th>Delete<input type="checkbox" name="select-all"
								id="select-all"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lecturer.batchList}" var="batch"
							varStatus="status">
							<tr id="batch${batch.id}">
								<td><a href="#">${batch.batchName}</a></td>
								<td>${batch.subject.subjectName}</td>
								<td>${batch.subject.grade}</td>
								<td>${batch.intake}</td>
								<td>${batch.fee}</td>
								<td><a class="edit_batch" href="#${batch.id}">edit</a></td>
								<td><input type="checkbox" name="batch_del_list"
									value="${batch.id}"></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>


			<div id="dialog-form-batch" title="Class Room/Batch">
				All form fields are required
				<form>
					<fieldset>
						<label for="batchName">Class Room Name/Code</label> <input
							type="text" name="batchName" id="batchName"
							class="text ui-widget-content ui-corner-all" /> <label
							for="intake">Intake</label> <input type="text" name="intake"
							id="intake" class="text ui-widget-content ui-corner-all" /> <label
							for="fee">Fee</label> <input type="text" name="fee" id="fee"
							class="text ui-widget-content ui-corner-all" /> <label
							for="commenceDate">Commence Date</label> <input type="text"
							name="commenceDate" id="commenceDate"
							class="text ui-widget-content ui-corner-all" /> <label
							for="scheduleDiscription">Schedule Description</label> <input
							type="text" name="scheduleDiscription" id="scheduleDiscription"
							class="text ui-widget-content ui-corner-all" /> <label
							for="subject">Subject</label> <select id="subject"
							class="text ui-widget-content ui-corner-all">
							<c:forEach items="${lecturer.subjectList}" var="subject"
								varStatus="status">
								<option value="${subject.id}">${subject.subjectName} :
									for grade : ${subject.grade}</option>
							</c:forEach>

						</select>
					</fieldset>
				</form>
			</div>
		</div>

		<div id="subjectdiv">
			<h5>Subject List</h5>
			<c:forEach items="${lecturer.subjectList}" var="subject"
				varStatus="status">
				<div class="fieldName">${subject.subjectName} : for grade :
					${subject.grade} : ${subject.subjectDetails }</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>