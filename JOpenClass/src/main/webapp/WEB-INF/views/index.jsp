<!DOCTYPE html>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<meta charset="utf-8">
<title>Jopenclass</title>
<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
<script src="<%=request.getContextPath()%>/resources/index/js/index.js"></script>

<link
	href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
</head>

<body>
	<%@ include file="/resources/common/header.jsp"%>

	<div class="container">
		<div id="this-carousel-id" class="carousel slide">
			<div class="carousel-inner">
				<div class="item active">
					<a href="#"> <img
						src="<%=request.getContextPath()%>/resources/index/img/antennae.jpg"
						alt="Antennae Galaxies" />
					</a>
				</div>

				<div class="item">
					<a href="#"> <img
						src="<%=request.getContextPath()%>/resources/index/img/ngc5866.jpg"
						alt="abc" />
					</a>
				</div>
			</div>
			<a class="carousel-control left" href="#this-carousel-id"
				data-slide="prev">&lsaquo;</a> <a class="carousel-control right"
				href="#this-carousel-id" data-slide="next">&rsaquo;</a>
		</div>
		<%
			int courseCount = 0;
		%>

		<c:forEach items="${featuredBatches}" var="batch" varStatus="status">
			<%
				if (courseCount % 3 == 0) {
			%>
			<div class="row">
				<%
					}
				%>
				<div class="span4">
					<h3>${batch.batchName}</h3>
					<p>${batch.scheduleDiscription}</p>
					<p>
						<a class="btn btn-inverse" href="#">View details &raquo;</a>
					</p>
				</div>
				<%
					if (courseCount % 3 == 2) {
				%>
			</div>
			<%
				}
					courseCount++;
			%>
		</c:forEach>

	</div>
	
	<hr>


	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>

	<script
		src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
	<script>
		$(document).ready(function() {
			$('.carousel').carousel({
				interval : 4000
			});
		});
	</script>
	<%@ include file="/resources/common/footer.jsp"%>
</body>
</html>