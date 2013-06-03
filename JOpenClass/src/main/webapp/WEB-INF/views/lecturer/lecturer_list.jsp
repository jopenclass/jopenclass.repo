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
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/chosen/chosen.css">
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/chosen/chosen.jquery.min.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/lecturer/lecturer_list.css">

<script
	src="<%=request.getContextPath()%>/resources/lecturer/lecturer_list.js"
	type="text/javascript">
	
</script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">

</head>
</head>

<h1>Existing Lecturers:</h1>

<body>
	<%@ include file="/resources/common/header.jsp"%>

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
		<button id="delete_lecturers" class="btn btn-danger btn-small">Delete</button>

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
					value="ROLE_ADMIN">Admin <select id='subjectSelect'
					name="selected" data-placeholder="Choose subjects"
					class="chzn-select" multiple style="width: 350px;" tabindex="2">
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


	<div id="lecturers-contain">

		<table id="lecturers" class="table table-hover table-striped">

			<thead>

				<tr>

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
	<%@ include file="/resources/common/footer.jsp"%>
</body>

</html>