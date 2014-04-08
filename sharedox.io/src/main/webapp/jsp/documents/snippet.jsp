<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="baseURL"
	   value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta property="og:title" content="${it.document.title}"/>
	<meta property="og:description" content="${it.document.description}"/>
	<meta property="og:image" content="${baseURL}/static/images/document.png"/>

	<meta property="og:url" content="${baseURL}/public/documents/${it.document.id}"/>

	<meta property="fb:app_id" content="735092636525455"/>

	<title>ShareDox: Details</title>
</head>
<body>
</body>
</html>