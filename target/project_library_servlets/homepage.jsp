<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Homepage</title>
</head>

<body>
<jsp:include page="change-language-header.jsp"/>
<h1>HOMEPAGE</h1>

<h2>${textInfo.loggedInAs} ${currentUser.username}, ${currentUserAuthority}</h2> <br/>

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

<br/>

<hr/>

<input type="text" value="${textInfo.penaltyField} ${currentUser.userDetails.userPenalty}" readonly
<c:if test="${!currentUser.userDetails.hasPenalty}"><c:out value="hidden='true'"/></c:if>>

<%--<input type="submit" value="${locale.payPenaltyButton}" onclick="window.location.href = 'payPenalty'"--%>
<%--<c:if test="${!currentUser.userDetails.hasPenalty}"><c:out value="hidden='true'"/></c:if>>--%>
<%--<br><br><br>--%>

<hr/>

<br/>
<br/>

<input type="button" value="${textInfo.showAllBooksButton}" onclick="window.location.href = 'BookListController'"/>

<br/>
<br/>

<c:url var="viewSubscriptionButton" value="SubscriptionController">
    <c:param name="command" value="SHOW USER SUBSCRIPTIONS"/>
</c:url>

<input type="button" value="${textInfo.viewSubscriptionsButton}" onclick="window.location.href = '${viewSubscriptionButton}'"/>

<br/><br/>
<br/><br/>

<input type="button" value="${textInfo.logoutButton}" onclick="window.location.href = 'LogoutController'"/>

</body>

</html>