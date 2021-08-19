<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Homepage</title>
</head>

<body>
<h1>HOMEPAGE</h1>

<h2>${textInfo.loggedInAs} ${currentUser.username}, ${currentUserAuthority}</h2>

<br/>
<br/>

<input type="button" value="Book list" onclick="window.location.href = 'BookListController'"/>

</body>

</html>