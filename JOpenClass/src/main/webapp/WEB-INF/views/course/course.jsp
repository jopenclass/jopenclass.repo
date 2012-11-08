<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Course</title>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script type="text/javascript">
	function insertCourse() {

		var courseName = $('#courseName').val();
		var grade = $('#grade').val();
		var fee = $('#fee').val();
		var lecturer = $('#lecturer').val();

		$.ajax({
			type : "POST",
			url : "/JOpenClass/savecourse",
			data : "courseName=" + courseName + "&grade=" + grade + "&fee="
					+ fee + "&lecturer=" + lecturer + "&id=-1",
			success : function(response) {
				$('#info').html(response.message);
				$('#courseName').val('');
				$('#grade').val('');
				$('#fee').val('0.0');
				$('#lecturer').val('');
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

	<form:form method="post" action="savecourse.html" commandName="course">

		<table>
			<tr>
				<td><form:label path="courseName">Course Name</form:label></td>
				<td><form:input path="courseName" /></td>
			</tr>
			<tr>
				<td><form:label path="grade">Grade</form:label></td>
				<td><form:input path="grade" /></td>
			</tr>
			<tr>
				<td><form:label path="fee">Course Fee</form:label></td>
				<td><form:input path="fee" /></td>
			</tr>


			<tr>
				<td><form:label path="lecturer">Lecturer</form:label></td>
				<td><form:select path="lecturer">
						<c:forEach items="${lecturers}" var="lec" varStatus="status">
							<form:option value="${lec.id}">${lec.firstName}&nbsp;${lec.lastName}</form:option>
						</c:forEach>
					</form:select></td>
			</tr>

			<tr>
				<td colspan="2"><input type="button" value="save"
					onclick="insertCourse()" /></td>
			</tr>
		</table>

	</form:form>

</body>
</html>