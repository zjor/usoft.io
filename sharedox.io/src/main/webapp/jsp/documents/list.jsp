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
	<title>ShareDox</title>
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

	<div class="panel panel-default">
		<div class="panel-body">
			<img src="${it.profile.avatarURL}">
			<p><span>${it.profile.firstName}</span> <span>${it.profile.lastName}</span></p>
		</div>
	</div>
	<a href="${baseURL}/documents/create" class="btn btn-primary">Add New</a>
	<h2>Documents index</h2>
	<c:forEach var="doc" items="${it.documents}">
		<p>
			<a href="${baseURL}/documents/${doc.id}">${doc.id}</a> <span class="label label-default">${doc.title}</span>
			<div class="fb-share-button" data-href="${baseURL}/public/documents/${doc.id}" data-type="button"></div>
		</p>
	</c:forEach>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${baseURL}/static/js/bootstrap.min.js"></script>
</body>
</html>