<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Homepage</title>
</head>

<body>
<h1>HOMEPAGE ADMIN</h1>

<h2>${textInfo.loggedInAs} ${currentUser.username}, ${currentUserAuthority}</h2>

<br/><br/><br/>

<input type="button" value="${textInfo.seeUsersList}" onclick="window.location.href = 'UserListController'"/>

</body>

</html>