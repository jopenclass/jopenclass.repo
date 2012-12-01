<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Course Category</title>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script type="text/javascript">
	function insertSubject() {

		var subjectName = $('#subjectName').val();
		var grade = $('#grade').val();
		var subjectDetails = $('#subjectDetails').val();

		$.ajax({
			type : "POST",
			url : "/JOpenClass/savesubject",
			data : "subjectName=" + subjectName + "&grade=" + grade+ "&subjectDetails=" + subjectDetails
					+ "&id=-1",
			success : function(response) {
				$('#info').html(response.message);
				$('#subjectName').val('');
				$('#grade').val('');
				$('#subjectDetails').val('');
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
</script>
</head>
<body>
	<h3>${operation}</h3>
	<div id="info"></div>

	<form:form method="post" action="savesubject.html"
		commandName="subject">

		<table>
			<tr>
				<td><form:label path="subjectName">Subject Name</form:label></td>
				<td><form:input path="subjectName" /></td>
			</tr>
			<tr>
				<td><form:label path="grade">Grade</form:label></td>
				<td><form:input path="grade" /></td>
			</tr>
			<tr>
				<td><form:label path="subjectDetails">Subject Details</form:label></td>
				<td><form:input path="subjectDetails" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" value="save"
					onclick="insertSubject()" /></td>
			</tr>
		</table>

	</form:form>

</body>
</html>