<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lecturer</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/chosen/chosen.css">
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/resources/chosen/chosen.jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {

		function insertLecturer() {
			var data = new Object();
			var lecturer = new Object();

			lecturer.firstName = $('#firstName').val();
			lecturer.lastName = $('#lastName').val();
			lecturer.address = $('#address').val();
			lecturer.contactNumber = $('#contactNumber').val();
			lecturer.user = new Object();
			lecturer.user.enabled = true;
			lecturer.user.email = $('#email').val();
			lecturer.user.password = $('#password').val();
			lecturer.user.enabled = $("input:radio:checked").val();
			//lecturer.subjectList = $(".chzn-select").val();
			lecturer.user.userRoles = $('input[name="roles"]:checkbox:checked')
					.map(function() {
						return this.value;
					}).get();
			lecturer.id = -1;
			data.lecturer = lecturer;
			data.selectedSubjects = $(".chzn-select").val();
			$.ajax({
				type : "POST",
				url : "/JOpenClass/savelecturer",
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify(data),
				success : function(response) {
					$('#info').html(response.message);
					$('#firstName').val('');
					$('#lastName').val('');
					$('#address').val('');
					$('#contactNumber').val('');
					$('#email').val('');
					$('#password').val('');
					$('#passwordMatch').val('');
					$('.chzn-select').val('');
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		$("#dialog-message").dialog({
			modal : true,
			autoOpen : false,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#create-lecturer").button().live('click', function() {
			var pass = $('#password').val();
			var passMatch = $('#passwordMatch').val();
			if (pass != passMatch || (pass.length <= 0)) {
				$("#dialog-message").dialog("open");
			} else {
				insertLecturer();
			}
		});
	});
</script>

</head>
<body>
	<div id="dialog-message" title="Error">
		<p>Password mismatch or no password found</p>
	</div>
	<h4>Add Lecturer</h4>
	<button id="create-lecturer">Add</button>
	<div id="info"></div>
	<form:form method="post" action="savelecturer.html"
		commandName="lecturer">
		<form:hidden path="id" value="-1" />
		<table>
			<tr>
				<td colspan="2"><div id="info"></div></td>
			</tr>
			<tr>
				<td><form:label path="firstName">First Name</form:label></td>
				<td><form:input path="firstName" /></td>
			</tr>
			<tr>
				<td><form:label path="lastName">Last Name</form:label></td>
				<td><form:input path="lastName" /></td>
			</tr>
			<tr>
				<td><form:label path="address">Address</form:label></td>
				<td><form:input path="address" /></td>
			</tr>
			<tr>
				<td><form:label path="contactNumber">Contact Number</form:label></td>
				<td><form:input path="contactNumber" /></td>
			</tr>
			<tr>
				<td><label>Email address</label></td>
				<td><input type="text" id="email" /></td>
			</tr>
			<tr>
				<td><label>Password</label></td>
				<td><input type="password" id="password" /></td>
			</tr>
			<tr>
				<td><label>Password</label></td>
				<td><input type="password" id="passwordMatch" /></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td><label>Enable?</label></td> -->
<!-- 				<td><input type="radio" name="enabled" value=1 />Yes <input -->
<!-- 					type="radio" name="enabled" value=0 />No</td> -->
<!-- 			</tr> -->
			<tr>
				<td><label>User Roles</label></td>
				<td><input type="checkbox" name="roles" value="ROLE_LEC">Lecturer
					<input type="checkbox" name="roles" value="ROLE_ADMIN">Admin</td>
			</tr>
			<tr>

				<td>Subjects</td>
				<td><select name="selected" data-placeholder="Choose subjects"
					class="chzn-select" multiple style="width: 350px;" tabindex="2">
						<option value=""></option>
						<c:forEach items="${allSubjects}" var="subject" varStatus="status">
						<option value="${subject.id}">${subject.subjectName}</option>
						</c:forEach>
				</select>
				<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>
				</td>
			</tr>
		</table>

	</form:form>
</body>
</html>