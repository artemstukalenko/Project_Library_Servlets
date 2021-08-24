<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Homepage</title>
</head>

<body>
<jsp:include page="change-language-header.jsp"/>
<h1>HOMEPAGE ADMIN</h1>

<h2>${textInfo.loggedInAs} ${currentUser.username}, ${textInfo.adminLabel}</h2>

<table border="1">
    <tr>
        <td>${textInfo.userFirstName}: </td>
        <td>${currentUser.userDetails.userFirstName}</td>
    </tr>
    <tr>
        <td>${textInfo.userLastName}: </td>
        <td>${currentUser.userDetails.userLastName}</td>
    </tr>
    <tr>
        <td>${textInfo.userEmail}: </td>
        <td>${currentUser.userDetails.userEmail}</td>
    </tr>
    <tr>
        <td>${textInfo.userPhoneNumber}: </td>
        <td>${currentUser.userDetails.userPhoneNumber}</td>
    </tr>
    <tr>
        <td>${textInfo.userAddress}: </td>
        <td>${currentUser.userDetails.userAddress}</td>
    </tr>

</table>

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