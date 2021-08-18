<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>User List</title>
</head>

<body>
<h2>${textInfo.usersListString}</h2>


<br/><br/><br/>

<table border="1">

    <tr>
        <th>${textInfo.usernameTableHeader}</th>
        <th>${textInfo.statusTableHeader}</th>
        <th>${textInfo.userFirstName}</th>
        <th>${textInfo.userLastName}</th>
        <th>${textInfo.userEmail}</th>
        <th>${textInfo.userPhoneNumber}</th>
        <th>${textInfo.userAddress}</th>
        <th>${textInfo.userPenalty}</th>
        <th>${textInfo.userSpecialStatus}</th>
    </tr>

    <c:forEach var="user" items="${allUsers}">

        <c:url var="blockButton" value="UserManipulationController">
            <c:param name="userName" value="${user.username}"/>
        </c:url>
        <c:url var="unblockButton" value="UserManipulationController">
            <c:param name="userName" value="${user.username}"/>
        </c:url>
        <c:url var="deleteButton" value="UserManipulationController">
            <c:param name="userName" value="${user.username}"/>
        </c:url>
        <c:url var="makeLibrarianButton" value="UserManipulationController">
            <c:param name="userName" value="${user.username}"/>
        </c:url>
        <c:url var="depriveLibrarianPrivilegesButton" value="UserManipulationController">
            <c:param name="userName" value="${user.username}"/>
        </c:url>

        <tr>
            <td>${user.username}</td>
            <td>${user.enabled}</td>
            <td>${user.userDetails.userFirstName}</td>
            <td>${user.userDetails.userLastName}</td>
            <td>${user.userDetails.userEmail}</td>
            <td>${user.userDetails.userPhoneNumber}</td>
            <td>${user.userDetails.userAddress}</td>
            <td>${user.userDetails.userPenalty}</td>
            <td>${user.userDetails.authorityString}</td>

            <td>
                <input type="button" value="${textInfo.blockButton}" onclick="window.location.href = '${blockButton}'"/>
                <input type="button" value="${textInfo.unblockButton}" onclick="window.location.href = '${unblockButton}'"/>
                <input type="button" value="${textInfo.deleteUserButton}" onclick="window.location.href = '${deleteButton}'">
                <input type="button" value="${textInfo.makeLibrarianButton}" onclick="window.location.href = '${makeLibrarianButton}'">
                <input type="button" value="${textInfo.makeNotLibrarianButton}" onclick="window.location.href = '${depriveLibrarianPrivilegesButton}'">
            </td>
        </tr>
    </c:forEach>

</table>

<br/><br/>

<input type="button" value="${textInfo.toHomePage}" onclick="window.location.href = 'HomepageController'"/>

</body>

</html>