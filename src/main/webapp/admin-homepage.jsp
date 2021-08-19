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

<br/><br/>

<input type="button" value="${textInfo.seeSubscriptionsList}" onclick="window.location.href = 'SubscriptionController'"/>

<br/><br/>

<input type="button" value="${textInfo.showAllBooksButton}" onclick="window.location.href = 'BookListController'"/>

<br/><br/>
<br/><br/>

<input type="button" value="${textInfo.logoutButton}" onclick="window.location.href = 'LogoutController'"/>

</body>

</html>