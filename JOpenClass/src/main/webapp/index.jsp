<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Jopenclass</title>
<link
	href="<%=request.getContextPath()%>/resources/index/css/bootstrap.css"
	rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
<link
	href="<%=request.getContextPath()%>/resources/index/css/bootstrap-responsive.css"
	rel="stylesheet">
</head>

<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand"
					href="https://github.com/jopenclass/jopenclass.repo">JOC</a>
				<div class="nav-collapse">
					<ul class="nav">

						<li class="active"><a href="<%=request.getContextPath()%>">Home</a></li>
						<li><a href="<%=request.getContextPath()%>/welcome">Sign
								In</a></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> Register <b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="<%=request.getContextPath()%>/savelecturer">Lecturer</a></li>
								<li><a href="#">Student</a></li>
							</ul></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

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
		<!-- .carousel -->

		<div class="row">
			<div class="span4">
				<h2>Course 1</h2>
				<p>he was an effective performer with bat and ball, and a
					forceful though occasionally controversial leader; contemporaries
					judged him the best captain in England. From 1921, he played
					occasionally i</p>
				<p>
					<a class="btn" href="#">View details &raquo;</a>
				</p>
			</div>
			<div class="span4">
				<h2>Course 2</h2>
				<p>he was an effective performer with bat and ball, and a
					forceful though occasionally controversial leader; contemporaries
					judged him the best captain in England. From 1921, he played
					occasionally i</p>
				<p>
					<a class="btn" href="#">View details &raquo;</a>
				</p>
			</div>
			<div class="span4">
				<h2>Course 3</h2>
				<p>he was an effective performer with bat and ball, and a
					forceful though occasionally controversial leader; contemporaries
					judged him the best captain in England. From 1921, he played
					occasionally i</p>
				<p>
					<a class="btn" href="#">View details &raquo;</a>
				</p>
			</div>
		</div>

		<div class="row">
			<div class="span4">
				<h2>Course 1</h2>
				<p>he was an effective performer with bat and ball, and a
					forceful though occasionally controversial leader; contemporaries
					judged him the best captain in England. From 1921, he played
					occasionally i</p>
				<p>
					<a class="btn" href="#">View details &raquo;</a>
				</p>
			</div>
			<div class="span4">
				<h2>Course 2</h2>
				<p>he was an effective performer with bat and ball, and a
					forceful though occasionally controversial leader; contemporaries
					judged him the best captain in England. From 1921, he played
					occasionally i</p>
				<p>
					<a class="btn" href="#">View details &raquo;</a>
				</p>
			</div>
			<div class="span4">
				<h2>Course 3</h2>
				<p>he was an effective performer with bat and ball, and a
					forceful though occasionally controversial leader; contemporaries
					judged him the best captain in England. From 1921, he played
					occasionally i</p>
				<p>
					<a class="btn" href="#">View details &raquo;</a>
				</p>
			</div>
		</div>

		<hr>

		<footer>
			<p>Jopenclass</p>
		</footer>

	</div>

	<!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if necessary -->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

	<!-- Bootstrap jQuery plugins compiled and minified -->
	<script
		src="<%=request.getContextPath()%>/resources/index/js/bootstrap.min.js"></script>
	<script>
		$(document).ready(function() {
			$('.carousel').carousel({
				interval : 4000
			});
		});
	</script>

</body>
</html>