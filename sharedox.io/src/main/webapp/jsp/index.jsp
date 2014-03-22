<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="baseURL"
	   value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>

<!DOCTYPE html>

<html lang="en">
<head>
	<meta charset="UTF-8"/>
	<meta property="og:title" content="Pink Floyd - Wish you were here"/>
	<meta property="og:description" content="The best romantic song"/>
	<meta property="og:url" content="${baseURL}/static/song.mp3"/>
	<meta property="fb:app_id" content="735092636525455"/>

	<title></title>
</head>
<body>
<h1>Pink Floyd</h1>

<h2>Wish you were here</h2>
</body>
</html>