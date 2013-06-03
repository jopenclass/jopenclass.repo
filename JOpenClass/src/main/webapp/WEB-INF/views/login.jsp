<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css"
	rel="stylesheet">
</head>
<body onload='document.f.j_username.focus();'>
	<h3>Login to the system</h3>

	<c:if test="${not empty message}">
		<div>
			${message}<br />
		</div>
	</c:if>

	<c:if test="${not empty error}">
		<div>
			Your login attempt was not successful, try again.<br />
		</div>
	</c:if>

	<form name='f' action="<c:url value='j_spring_security_check' />"
		method='POST'>

		<table>
			<tr>
				<td>Email Address:</td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="submit" class="btn btn-success" /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="reset" type="reset" class="btn btn-info" /></td>
			</tr>
		</table>

	</form>
	<%@ include file="/resources/common/footer.jsp" %>
</body>
</html>
