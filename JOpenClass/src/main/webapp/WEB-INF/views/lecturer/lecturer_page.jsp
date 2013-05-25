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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/lecturer/lecturer_page.css">
<script
	src="<%=request.getContextPath()%>/resources/lecturer/lecturer_page.js"
	type="text/javascript"></script>
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
								<td><a class="batchlink"
									href="enterbatch.html?batchId=${batch.id}">${batch.batchName}</a></td>
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