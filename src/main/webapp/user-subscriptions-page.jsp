<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<body>

<h2>${textInfo.yourSubscriptions}</h2>

<br><br><br>

<table border="1">

    <tr>
        <th>${textInfo.subscriptionId}</th>
        <th>${textInfo.usernameTableHeader}</th>
        <th>${textInfo.bookId}</th>
        <th>${textInfo.bookTitle}</th>
        <th>${textInfo.bookAuthor}</th>
        <th>${textInfo.startOfThePeriod}</th>
        <th>${textInfo.endOfThePeriod}</th>
        <th>${textInfo.isExpired}</th>
    </tr>

    <c:forEach var="subscription" items="${currentUserSubscriptions}">

        <tr>
            <td>${subscription.subscriptionId}</td>
            <td>${subscription.username}</td>
            <td>${subscription.bookId}</td>
            <td>${subscription.title}</td>
            <td>${subscription.author}</td>
            <td>${subscription.startOfThePeriod}</td>
            <td>${subscription.endOfThePeriod}</td>
            <td>
                <c:if test="${subscription.expired}"><c:out value="+"/></c:if>
                <c:if test="${!subscription.expired}"><c:out value="-"/></c:if>
            </td>
        </tr>

    </c:forEach>

</table>

</body>
</html>