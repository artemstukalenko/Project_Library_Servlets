<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Book list</title>
</head>

<body>
<jsp:include page="change-language-header.jsp"/>

<form action="BookListController" method="get">
    <input type="text" name="userInputForSearch"/>
    <select name="searchCriteria">
        <option>${textInfo.filterByTitle}</option>
        <option>${textInfo.filterByAuthor}</option>
        <option>${textInfo.filterByYear}</option>
    </select>
    <input type="submit" value="search"/>
</form>

<br/><br/>
<table border="1">

    <tr>
        <th>${textInfo.bookId}</th>
        <th>${textInfo.bookTitle}</th>
        <th>${textInfo.bookAuthor}</th>
        <th>${textInfo.bookYearOfPublishing}</th>
        <th>

            <form action="BookListController" method="get">
                <select name="sortMethod">
                    <option>${textInfo.filterByTitle}</option>
                    <option>${textInfo.filterByAuthor}</option>
                    <option>${textInfo.filterByYear}</option>
                </select>
                <input type="submit" value="${textInfo.sort}"/>
            </form>
        </th>
    </tr>

    <c:forEach var="book" items="${allBooks}">

        <c:url var="arrangeSubscriptionButton" value="SubscriptionController">
            <c:param name="bookId" value="${book.bookId}"/>
            <c:param name="command" value="ARRANGE SUBSCRIPTION"/>
        </c:url>
        <c:url var="arrangeCustomRequestButton" value="CustomRequestController">
            <c:param name="bookId" value="${book.bookId}"/>
            <c:param name="command" value="ARRANGE CUSTOM REQUEST"/>
        </c:url>
        <c:url var="deleteBookButton" value="BookListController">
            <c:param name="bookId" value="${book.bookId}"/>
            <c:param name="command" value="DELETE BOOK"/>
        </c:url>

        <tr>
            <td>${book.bookId}</td>
            <td>${book.bookTitle}</td>
            <td>${book.bookAuthor}</td>
            <td>${book.bookYearOfPublishing}</td>
            <td>
                <c:if test="${isUser}">
                    <input type="button" value="${textInfo.arrangeSubscriptionButton}"
                        <c:if test="${book.taken}"><c:out value="disabled='disabled'"/></c:if>
                           onclick="window.location.href = '${arrangeSubscriptionButton}'">
                </c:if>
            </td>
            <td>
                <c:if test="${isUser}">
                    <input type="button" value="${textInfo.arrangeCustomRequest}" onclick="window.location.href = '${arrangeCustomRequestButton}'"/>
                </c:if>
            </td>
            <td>
                <c:if test="${isAdmin}">
                    <input type="button" value="${textInfo.deleteBook}" onclick="window.location.href = '${deleteBookButton}'"/>
                </c:if>
            </td>
        </tr>
    </c:forEach>

</table>

<br/><br/>

<c:if test="${isAdmin}">
    <input type="button" value="${textInfo.addNewBook}" onclick="window.location.href = 'enter-info-for-new-book.jsp'"/>
</c:if>

<br/><br/>

<input type="button" value="${textInfo.toHomePage}" onclick="window.location.href = 'HomepageController'"/>

</body>

</html>