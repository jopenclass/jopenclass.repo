<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lecturer</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/chosen/chosen.css">
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/chosen/chosen.jquery.min.js"
	type="text/javascript"></script>
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
<style type="text/css">
body {
	font-size: 65.5%;
}
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/lecturer/lecturer.js">
	
</script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
</head>
<body>
	<%@ include file="/resources/common/header.jsp"%>
	<div id="dialog-message" title="Error">
		<p>Password mismatch or no password found</p>
	</div>
	<h4>Add Lecturer</h4>
	<div id="info"></div>
	<form:form method="post" action="savelecturer.html"
		commandName="lecturer" onsubmit="return false;">
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
				<td><input type="checkbox" id="ROLE_LEC" name="roles"
					value="ROLE_LEC">Lecturer <input type="checkbox"
					id="ROLE_ADMIN" name="roles" value="ROLE_ADMIN">Admin</td>
			</tr>
			<tr>

				<td>Subjects</td>
				<td><select name="selected" data-placeholder="Choose subjects"
					class="chzn-select" multiple style="width: 350px;" tabindex="2">
						<option value=""></option>
						<c:forEach items="${allSubjects}" var="subject" varStatus="status">
							<option class="subjectOption" value="${subject.id}">${subject.subjectName}</option>
						</c:forEach>
				</select> <script type="text/javascript">
					$(".chzn-select").chosen();
					$(".chzn-select-deselect").chosen({
						allow_single_deselect : true
					});
				</script></td>
			</tr>
		</table>
		<button id="create-lecturer" class="btn btn-success btn-small">Add</button>
	</form:form>
	<%@ include file="/resources/common/footer.jsp"%>
</body>
</html>