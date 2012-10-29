<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lecturer</title>
</head>
<body>

	<h3>${operation}</h3>
	<h3>${message}</h3>

	<form:form method="post" action="savelecturer.html">

		<table>
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
				<td colspan="2"><input type="submit" value="submit" /></td>
			</tr>
		</table>

	</form:form>

</body>
</html>