<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student</title>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/resources/chosen/chosen.jquery.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/chosen/chosen.jquery.min.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/student/student.js"> </script>
</head>
<body>
	<%@ include file="/resources/common/header.jsp"%>
	
	<h4>Add Student</h4>
	<div id="info"></div>

		<table>
			<tr>
				<td colspan="2"><div id="info"></div></td>
			</tr>
			<tr>
				<td><label>First Name</label></td>
				<td><input type="text" id="firstName" /></td>
			</tr>
			<tr>
				<td><label>Last Name</label></td>
				<td><input type="text" id="lastName" /></td>
			</tr>
			<tr>
				<td><label>Address</label></td>
				<td><input type="text" id="address" /></td>
			</tr>
			<tr>
				<td><label>Contact Number</label></td>
				<td><input type="text" id="contactNumber" /></td>
			</tr>
			<tr>
				<td><label>Email address</label></td>
				<td><input type="text" id="email" /></td>
			</tr>
			<tr>
				<td><label>Grade</label></td>
				<td><input type="text" id="grade" /></td>
			</tr>			
			<tr>
				<td><label>Password</label></td>
				<td><input type="password" id="password" /></td>
			</tr>
			<tr>
				<td><label>Confirm Password</label></td>
				<td><input type="password" id="passwordMatch" /></td>
			</tr>
						
		</table>
		<button id="btnAddStudent">Add</button>
     <%@ include file="/resources/common/footer.jsp"%>
</body>
</html>