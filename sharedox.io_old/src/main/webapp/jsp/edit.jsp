<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="baseURL"
	   value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>ShareDox.io</title>
	<link href="${baseURL}/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div id="fb-root"></div>
<script>(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=735092636525455";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

	<div class="container">

		<h2>Share new document</h2>

		<c:if test="${not empty it.message}">
			<div class="alert alert-info">${it.message}</div>
		</c:if>

		<form method="post" enctype="multipart/form-data" role="form" class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">Document ID</label>
				<div class="col-sm-10">
					<p class="form-control-static">${it.document.id}</p>
				</div>
			</div>
			<div class="form-group">
				<label for="attachment" class="col-sm-2 control-label">Select document</label>
				<div class="col-sm-10">
					<input type="file" id="attachment" name="file">
				</div>
			</div>
			<div class="form-group">
				<label for="title" class="col-sm-2 control-label">Title</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="title" name="title" placeholder="Enter title" value="${it.document.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Description</label>
				<div class="col-sm-10">
					<textarea name="description" class="form-control" rows="3">${it.document.description}</textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">Submit</button>
				</div>
			</div>
		</form>

		<div class="fb-share-button" data-href="${baseURL}/${it.document.id}" data-type="button"></div>

	</div>

<script src="${baseURL}/static/js/jquery.min.js"></script>
<script src="${baseURL}/static/js/bootstrap.min.js"></script>
</body>
</html>