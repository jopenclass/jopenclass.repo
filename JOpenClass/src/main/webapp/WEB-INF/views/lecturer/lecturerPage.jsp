<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jopen Class</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/lecturer/lecturerPage.css">
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
	src="http://twitter.github.com/bootstrap/assets/js/bootstrap-tab.js"></script>

<script
	src="<%=request.getContextPath()%>/resources/lecturer/lecturerPage.js"
	type="text/javascript"></script>

<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
</head>
<body>
	<%@ include file="/resources/common/header.jsp"%>
	<div id="info"></div>
	<div class="tabbable tabs-left">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#profilediv1" data-toggle="tab">Profile
					Details </a></li>
			<li><a href="#batchsdiv1" data-toggle="tab">Batches/Classes</a></li>
			<li><a href="#subjectdiv1" data-toggle="tab">Subjects
					Entitled to</a></li>
		</ul>

		<div class="tab-content">

			<div id="profilediv1" class="tab-pane active">

				<div id="profilePic">

					<h4>Change profile picture</h4>
					<h6>only Jpg files are allowed</h6>

					<img
						src="<%=request.getContextPath()%>/profilePics/<%= org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName()%>thumb.jpg?random=<%=new Random().nextInt()%>"
						class="img-polaroid" height="160" width="160">

					<form method="post" enctype="multipart/form-data"
						action="updateProfilePic">
						<input name="profileImage" type="file" /><br /> <input
							type="submit" value="submit" class="btn" />
					</form>
				</div>

				<hr>
				<table class="table table-striped table-hover">
					<tr>
						<td>First name</td>
						<td id="tbFirstName">${lecturer.firstName}</td>
					</tr>
					<tr>
						<td>Last name</td>
						<td id="tbLastName">${lecturer.lastName}</td>
					</tr>
					<tr>
						<td>Email address</td>
						<td>${lecturer.email}</td>
					</tr>
					<tr>
						<td>Residential address</td>
						<td id="tbAddress">${lecturer.address}</td>
					</tr>
					<tr>
						<td>Contact number</td>
						<td id="tbContactNumber">${lecturer.contactNumber}</td>
					</tr>
					<tr>
						<td>Lecturer Information</td>
						<td id="tbLecInfo">${lecturer.lecturerInfo}</td>
					</tr>
				</table>
				<a href="#editProfileModal" class="btn btn-success"
					data-toggle="modal" id="editProfileBtn">Edit profile</a> <a
					href="#chagePassModal" class="btn btn-success" data-toggle="modal">Change
					Password</a>
			</div>

			<div id="batchsdiv1" class="tab-pane">
				<button id="create-batch" class="btn btn-success">Create
					Class Room/Batch</button>
				<button id="delete-batch" class="btn btn-danger">Delete
					Class Room/Batch</button>


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

					<table id="batches" class="table table-hover table-striped">
						<thead>
							<tr>
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
			<div id="subjectdiv1" class="tab-pane">
				<h5>Subjects I'm Entitled to</h5>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Subject Name</th>
							<th>Grade</th>
							<th>Subject Details</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${lecturer.subjectList}" var="subject"
							varStatus="status">
							<tr>
								<td>${subject.subjectName}</td>
								<td>${subject.grade}</td>
								<td>${subject.subjectDetails }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Modals -->
	<div id="chagePassModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="aboutModalLabel">Change the password</h3>
		</div>

		<div class="modal-body">
			<div id="passMisMatchErr" class="text-error"></div>
			<label>Previous password</label><input id="password" type="password">
			<label>New password</label><input id="newPassword" type="password">
			<label>Confirm new password</label><input id="newPassConfirm"
				type="password">
		</div>

		<div class="modal-footer">
			<!-- 		data-dismiss="modal" -->
			<button id="changePassBtn" class="btn btn-primary">Change</button>
		</div>
	</div>

	<div id="editProfileModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="aboutModalLabel">Edit profile information</h3>
		</div>

		<div class="modal-body">
			<div id="editProfileErr" class="text-error"></div>
			<label>First Name</label><input id="firstName" type="text"> <label>Last
				Name</label><input id="lastName" type="text"> <label>Email
				address</label><input id="email" type="text"> <label>Residential
				address</label><input id="address" type="text"> <label>Contact
				number</label><input id="contactNumber" type="text"> <label>Lecturer
				Information</label><input id="lecturerInfo" type="text">
		</div>

		<div class="modal-footer">
			<!-- 		data-dismiss="modal" -->
			<button id="saveProfileBtn" class="btn btn-primary">Save</button>
		</div>
	</div>

	<div id="loginMsgModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="aboutModalLabel">Login Required</h3>
		</div>

		<div class="modal-body">
			<p>Please Login with the new email address you provided.</p>
		</div>

		<div class="modal-footer">
			<!-- 		data-dismiss="modal" -->
			<a href="<%=request.getContextPath()%>/login" class="btn btn-primary">Ok</a>
		</div>
	</div>


	<%@ include file="/resources/common/footer.jsp"%>
</body>
</html>