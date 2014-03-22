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
	<meta property="og:url" content="${baseURL}/${it.document.id}"/>
	<meta property="fb:app_id" content="735092636525455"/>

	<title>ShareDox.io</title>
	<link href="${baseURL}/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">

<h3>${it.document.title}</h3>
<h4>${it.document.description}</h4>
<h5>${it.document.id}</h5>

</div>

<script src="${baseURL}/static/js/jquery.min.js"></script>
<script src="${baseURL}/static/js/bootstrap.min.js"></script>
</body>
</html>