<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JOpenclass</title>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/batch/batch.js"></script>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
<script type="text/javascript"
	src="http://twitter.github.com/bootstrap/assets/js/bootstrap-tab.js"></script>
</head>

<body>
	<%@ include file="/resources/common/header.jsp"%>
	<%-- 	batch id is : ${batchId} --%>


	<div class="tabbable tabs-left">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#batchInfoDiv" data-toggle="tab">Batch/Course
					Details</a></li>
			<li><a href="#assignmentsDiv" data-toggle="tab">Assignments</a></li>
			<li><a href="#resourcesDiv" data-toggle="tab">Resourses</a></li>
		</ul>

		<div class="tab-content">

			<div id="batchInfoDiv" class="tab-pane active hero-unit">${batchObject.scheduleDiscription}</div>

			<div id="assignmentsDiv" class="tab-pane">this is for
				assignments</div>
			<div id="resourcesDiv" class="tab-pane">this is for resources</div>
		</div>
	</div>
	<%@ include file="/resources/common/footer.jsp"%>

</body>

</html>