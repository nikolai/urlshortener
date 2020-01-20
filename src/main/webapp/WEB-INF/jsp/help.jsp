<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

	<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring Boot</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">

		<div class="starter-template">
			<h1>URL Shortener Service help page</h1>
			<h2>Help: ${message}</h2>
            <p>Assignment: Make an HTTP service that serves to shorten URLs, with the following functionalities:</p>
            <ul>
            <li>&nbsp;Registration Web address (API)</li>
            <li>&nbsp;Redirect client in accordance with the shortened URL</li>
            <li>&nbsp;Usage Statistics (API)</li>
            </ul>
            <p><br />Assignment description:</p>
            <p>1. Basic architecture<br />The service should have two parts: configuration and user.<br />1.1. Configuration part<br />The configuration part is invoked using REST calls with JSON payload and is used for:</p>
            <p style="padding-left: 30px;">a) Opening of accounts<br />b) Registration of URLs in the 'Shortener' service<br />c) Displaying stats<br />a) Opening of accounts</p>
            <p>HTTP method<br />POST<br />URI<br />/account<br />Request type<br />application/json<br />Request Body<br />JSON object with the following parameters:<br /> AccountId (String, mandatory)<br />Example: { AccountId : 'myAccountId'}<br />Reponse Type<br />application/json<br />Response<br />We distinguish the successful from the unsuccessful registration.<br />Unsuccessful registration occurs only if the concerned account ID<br />already exists. The parameters are as follows:<br /> success: true | false<br /> description: Description of status, for example: account with that<br />ID already exists<br /> password: Returns only if the account was successfully created.<br />Automatically generated password length of 8 alphanumeric<br />characters<br />Example {success: 'true', description: 'Your account is opened',<br />password: 'xC345Fc0'}</p>
            <p>b) Registration of URLs</p>
            <p>HTTP metoda<br />POST<br />URI<br />/register<br />Request type<br />application/json<br />Request Headers<br />Authorization header with Basic authentication token<br />JSON object with the following parameters:<br /> url (mandatory, url that needs shortening)<br /> redirectType : 301 | 302 (not mandatory, default 302)<br />Request Body<br />Example: {<br />url: 'http://stackoverflow.com/questions/1567929/website-safe-data-<br />access-architecture-question?rq=1',<br />redirectType : 301<br />}<br />Reponse Type<br />application/json<br />Response parameters in case of successful registration are as follows:<br />Response<br /> shortUrl (shortened URL)<br />Example: { shortUrl: 'http://short.com/xYswlE'}</p>
            <p>c) Retrieval of statistics<br />HTTP metoda<br />GET<br />URI<br />/statistic/{AccountId}<br />Request Headers<br />Set Authorization header and authenticate user<br />Reponse Type<br />application/json<br />The server responds with a JSON object, key:value map, where the key<br />is the registered URL, and the value is the number of this URL redirects..<br />Example:<br />{<br />Response<br />'http://myweb.com/someverylongurl/thensomedirectory/: 10,<br />'http://myweb.com/someverylongurl2/thensomedirectory2/: 4,<br />'http://myweb.com/someverylongurl3/thensomedirectory3/: 91,<br />}</p>
            <p>1.2. Redirecting<br />Redirecting the client on the configured address with the configured http status.<br /><br /><br /></p>
         </div>

	</div>

	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
