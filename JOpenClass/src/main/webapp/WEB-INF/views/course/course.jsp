<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Course</title>
</head>
<body>

<h3>${operation}</h3>
<h3>${message}</h3>

<form:form method="post" action="savecourse.html">
	
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
        <td colspan="2">
            <input type="submit" value="submit"/>
        </td>
    </tr>
</table>  
     
</form:form>

</body>
</html>