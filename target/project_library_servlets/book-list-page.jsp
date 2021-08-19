<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Book list</title>
</head>

<body>

<table border="1">

    <tr>
        <th>${textInfo.bookId}</th>
        <th>${textInfo.bookTitle}</th>
        <th>${textInfo.bookAuthor}</th>
        <th>${textInfo.bookYearOfPublishing}</th>
    </tr>

    <c:forEach var="book" items="${allBooks}">

        <c:url var="arrangeSubscriptionButton" value="SubscriptionController">
            <c:param name="bookId" value="${book.bookId}"/>
        </c:url>

        <tr>
            <td>${book.bookId}</td>
            <td>${book.bookTitle}</td>
            <td>${book.bookAuthor}</td>
            <td>${book.bookYearOfPublishing}</td>
            <td>
                <c:if test="${isUser}">
                    <input type="button" value="arrange subscription" onclick="window.location.href = '${arrangeSubscriptionButton}">
                </c:if>
            </td>
        </tr>
    </c:forEach>

</table>


</body>

</html>