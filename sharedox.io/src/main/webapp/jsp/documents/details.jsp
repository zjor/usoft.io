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
	<meta property="og:title" content="${it.document.title}"/>
	<meta property="og:description" content="${it.document.description}"/>
	<meta property="og:image" content="${baseURL}/static/images/document.png"/>

	<meta property="og:url" content="${baseURL}/documents/${it.document.id}"/>

	<meta property="fb:app_id" content="735092636525455"/>

	<title>ShareDox: Details</title>
	<link href="${baseURL}/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">

	<div class="panel panel-default">
		<div class="panel-body">
			<img src="${it.profile.avatarURL}">
			<p><span>${it.profile.firstName}</span> <span>${it.profile.lastName}</span></p>
		</div>
	</div>
	<h2>${it.document.title}</h2>
	<p>${it.document.description}</p>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${baseURL}/static/js/bootstrap.min.js"></script>
</body>
</html>