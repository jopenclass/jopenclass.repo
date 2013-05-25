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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/subject/subject_list.css" />
<script
	src="<%=request.getContextPath()%>/resources/subject/subject_list.js"
	type="text/javascript"></script>
</head>
<h1>Existing Subjects:</h1>
<body>

	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div id="dialog-confirm" title="Delete the item?">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>All the Course
				Categories and Courses related to the category will be permanently
				deleted and cannot be recovered. Are you really sure?
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
					class="text ui-widget-content ui-corner-all" /> <label
					for="subjectDetails">Subject Details</label> <input type="text"
					name="subjectDetails" id="subjectDetails"
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