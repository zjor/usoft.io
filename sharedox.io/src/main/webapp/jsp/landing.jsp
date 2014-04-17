<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="baseURL"
	   value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>
<!DOCTYPE html>
<html>
<head>
	<title>ShareDox</title>

	<link rel="stylesheet" href="${baseURL}/static/css/reset.css">
	<link rel="stylesheet" href="${baseURL}/static/fonts/fonts.css">
	<link rel="stylesheet" href="${baseURL}/static/css/style.css">

	<style>
		.preview {
			height: 315px;
			background: url('${baseURL}/static/images/background.jpg');
			border-bottom: solid 2px #808080;
			position: relative;
		}

		.slogan {
			position: absolute;
			left: 200px;
			top: 120px;
			color: lightgray;
			font-size: 1.5em;
			text-shadow: 1px -1px 0 darkgrey;
		}

		.login {
			position:absolute;
			left: 800px;
			top: 130px;
			box-shadow:inset 0px 1px 0px 0px #cae3fc;
			background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #79bbff), color-stop(1, #4197ee) );
			background:-moz-linear-gradient( center top, #79bbff 5%, #4197ee 100% );
			background-color:#79bbff;
			border-radius: 10px;
			text-indent:0;
			border:1px solid #469df5;
			display:inline-block;
			color:#ffffff;
			font-family:Trebuchet MS;
			font-size:15px;
			font-weight:bold;
			font-style:normal;
			height:45px;
			line-height:45px;
			width:200px;
			text-decoration:none;
			text-align:center;
			text-shadow:1px 1px 0px #287ace;
		}

		.toolbar {
			padding-top: 20px;
			padding-right: 50px;
		}

		.toolbar a {
			padding-left: 40px;
		}

		.features {
			width: 600px;
			margin: 0 auto;
			font-family: robotolight;
			padding-top: 40px;
			padding-bottom: 40px;
		}

		.features li {
			padding: 20px;
		}

		.footer {
			height: 200px;
			background: darkgray;
			padding: 10px;
			text-align: center;
			color: #fff;
			font-family: robotolight;
		}


	</style>
</head>
<body>
	<div class="clearfix header">
		<div class="left">
			<img src="${baseURL}/static/images/sharedox_logo.png">
		</div>
		<div class="left title">
			<h1>ShareDox</h1>
			<h2>simply.share.</h2>
		</div>
		<div class="toolbar right">
			<a href="#">Terms & Conditions</a>
			<a href="#">About</a>
			<a href="#">FAQ</a>
		</div>
	</div>
	<div class="preview">
		<div class="slogan">The simplest way<br/> to share files on social media</div>
		<a href="${baseURL}/documents" class="login">Login</a>
	</div>
	<div class="features">
		<ol>
			<li>Drop any file form your computer</li>
			<li>Provide a short description</li>
			<li>Share</li>
		</ol>
	</div>
	<div class="footer">
		sharedox.io 2014
	</div>


	<script>
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
				m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

		ga('create', 'UA-50151154-1', 'sharedox.io');
		ga('send', 'pageview');

	</script>
</body>
</html>