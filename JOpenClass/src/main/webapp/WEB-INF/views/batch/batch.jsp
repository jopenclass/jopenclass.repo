<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Batch</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<style type="text/css">
body {
	font-size: 75%;
}
</style>

<script>
	$(function() {
		$("#tabs").tabs();
	});
</script>

</head>

<body>

	batch id is : ${batchId}

	<div id="tabs">
		<ul>
			<li><a href="#home">Home</a></li>
			<li><a href="#schedules">Schedules</a></li>
			<li><a href="#assignments">Assignments</a></li>
			<li><a href="#resources">Resources</a></li>
		</ul>
		<div id="home">
			<p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo a,
				risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris.
				Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem.
				Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo.
				Vivamus sed magna quis ligula eleifend adipiscing. Duis orci.
				Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam
				molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut
				dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique
				tempus lectus.</p>
		</div>

		<div id="schedules">
			<p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo a,
				risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris.
				Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem.
				Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo.
				Vivamus sed magna quis ligula eleifend adipiscing. Duis orci.
				Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam
				molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut
				dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique
				tempus lectus.</p>
		</div>

		<div id="assignments">
			<p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus
				gravida ante, ut pharetra massa metus id nunc. Duis scelerisque
				molestie turpis. Sed fringilla, massa eget luctus malesuada, metus
				eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet
				fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam.
				Praesent in eros vestibulum mi adipiscing adipiscing. Morbi
				facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut
				posuere viverra nulla. Aliquam erat volutpat. Pellentesque
				convallis. Maecenas feugiat, tellus pellentesque pretium posuere,
				felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris
				consectetur tortor et purus.</p>
		</div>
		<div id="resources">
			<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse
				potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque
				rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante.
				Class aptent taciti sociosqu ad litora torquent per conubia nostra,
				per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim
				commodo pellentesque. Praesent eu risus hendrerit ligula tempus
				pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a,
				lacus.</p>
			<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at,
				semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra
				justo vitae neque. Praesent blandit adipiscing velit. Suspendisse
				potenti. Donec mattis, pede vel pharetra blandit, magna ligula
				faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque.
				Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi
				lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean
				vehicula velit eu tellus interdum rutrum. Maecenas commodo.
				Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus
				hendrerit hendrerit.</p>
		</div>
	</div>
	<%@ include file="/resources/common/footer.jsp" %>
</body>
</html>