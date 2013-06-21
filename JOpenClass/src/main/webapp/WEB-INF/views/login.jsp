<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
</head>
<body>
	<%@ include file="/resources/common/header.jsp"%>
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
				<td colspan='2'><input name="reset" type="reset"
					class="btn btn-info" /></td>
			</tr>
		</table>

	</form>
	<%@ include file="/resources/common/footer.jsp"%>
</body>
</html>
