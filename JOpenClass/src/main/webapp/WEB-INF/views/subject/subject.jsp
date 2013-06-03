<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Course Category</title>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="<%=request.getContextPath()%>/resources/subject/subject.js"
	type="text/javascript"></script>
</head>

<body>
	<h4>${operation}</h4>
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
					onclick="insertSubject()" class="btn btn-success" /></td>
			</tr>
		</table>

	</form:form>
	<%@ include file="/resources/common/footer.jsp"%>
</body>
</html>