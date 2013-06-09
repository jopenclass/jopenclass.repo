<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>

<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/batch/batchList.js"></script>

<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>

<title>Jopen Class</title>
</head>

<body>
	<%@ include file="/resources/common/header.jsp"%>

	<div id="info"></div>

	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<button class="btn btn-success" id="featureBtn">Feature the
			selected</button>
	</sec:authorize>

	<table id="batches" class="table table-hover table-striped">
		<thead>
			<tr>
				<th>Batch Name</th>
				<th>Intake</th>
				<th>Fee</th>
				<th>Description</th>
				<th>Lecturer</th>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<th>Featured</th>
				</sec:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${batchList}" var="batch" varStatus="status">
				<tr id="${batch.id}">
					<td>${batch.batchName}</td>
					<td>${batch.intake}</td>
					<td>${batch.fee}</td>
					<td>${batch.scheduleDiscription}</td>
					<td>${batch.lecturer.firstName}&nbsp;${batch.lecturer.lastName}</td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<td><input type="checkbox" name="feature_list"
							value="${batch.id}"
							<c:if test="${batch.isFeatured == true}">
						checked
						</c:if>></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="/resources/common/footer.jsp"%>
</body>
</html>