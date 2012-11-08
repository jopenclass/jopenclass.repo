<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lecturer</title>

<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script type="text/javascript">
	function insertLecturer() {

		var firstName = $('#firstName').val();
		var lastName = $('#lastName').val();
		var address = $('#address').val();
		var contactNumber = $('#contactNumber').val();
		var email = $('#email').val();

		$.ajax({
			type : "POST",
			url : "/JOpenClass/savelecturer",
			data : "firstName=" + firstName + "&lastName=" + lastName
					+ "&address=" + address + "&contactNumber=" + contactNumber
					+ "&email=" + email + "&id=-1",
			success : function(response) {
				$('#info').html(response.message);
				$('#firstName').val('');
				$('#lastName').val('');
				$('#address').val('');
				$('#contactNumber').val('');
				$('#email').val('');
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
</script>

</head>
<body>

	<h4>Add Lecturer</h4>
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
				<td><form:label path="email">Email address</form:label></td>
				<td><form:input path="email" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" value="submit"
					onclick="insertLecturer()" /></td>
			</tr>
		</table>

	</form:form>

</body>
</html>