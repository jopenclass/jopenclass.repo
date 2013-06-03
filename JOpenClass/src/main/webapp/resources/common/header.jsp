<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Modals -->
<div id="aboutModal" class="modal hide fade" tabindex="-1">
	<div class="modal-header">
		<!-- 			<button type="button" class="close" data-dismiss="modal">×</button> -->
		<h3 id="aboutModalLabel">About JOpen class</h3>
	</div>

	<div class="modal-body">
		<p>Jopenclass is a java based free and opensource software
			project. The software is intended for the purpose of online tutoring</p>
	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" data-dismiss="modal">Close</button>
	</div>
</div>

<div id="contactModal" class="modal hide fade" tabindex="-1">
	<div class="modal-header">
		<!-- 			<button type="button" class="close" data-dismiss="modal">×</button> -->
		<h3 id="contactModalLabel">Contact Us</h3>
	</div>

	<div class="modal-body">
		<p>You can contact us through following email addresses
		<ul>
			<li>madhumal.lahiru.hd@gmail.com or ambegodas@gmail.com</li>
		</ul>
	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" data-dismiss="modal">Close</button>
	</div>
</div>


<div class="navbar navbar-fixed-top navbar-inverse">
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
					<sec:authorize access="isAuthenticated()">
						<li><a href='<c:url value="/j_spring_security_logout" />'>Logout</a></li>
					</sec:authorize>
					<sec:authorize access="!isAuthenticated()">
						<li><a href="<%=request.getContextPath()%>/welcome">Sign
								In</a></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> Register <b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="<%=request.getContextPath()%>/savelecturer">Lecturer</a></li>
								<li><a href="#">Student</a></li>
							</ul></li>
					</sec:authorize>
					<li><a href="#aboutModal" data-toggle="modal">About</a></li>
					<li><a href="#contactModal" data-toggle="modal">Contact</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
