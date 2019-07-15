<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>National Park Geek</title>
<c:url value="/css/npGeek.css" var="cssHref" />
<link rel="stylesheet" href="${cssHref}">
</head>

<body>
	<header>
		<c:url var="homePageHref" value="/" />
		<c:url var="logoSrc" value="/img/logo.png" />
		<div >
			<a href="${homePageHref}"> <img class="logo" src="${logoSrc}"
				alt="National Park Geek Logo" />
			</a>
		</div>
		<nav class="navBar">
			<a href="${homePageHref}">Home</a>
			<c:url var="surveyInput" value="/surveyInput" />
			<a href="${surveyInput}">Survey</a>
			<c:url var="surveyResult" value="/surveyResult" />
			<a href="${surveyResult}">Favorite Parks</a>
		</nav>
		<h1>National Park Geek</h1>
		<br></br>
	</header>