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
        </tr>
    </c:forEach>

</table>

</body>

</html>