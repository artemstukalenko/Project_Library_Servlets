<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <title>Arrange custom request</title>
</head>

<body>
<h2>${textInfo.bookTitle}: ${currentBook.bookTitle}</h2>
<h2>${textInfo.bookAuthor}: ${currentBook.bookAuthor}</h2>
<h3>${textInfo.requestChoosePeriod}</h3>



<%--    <c:url var="registerRequestButton" value="CustomRequestController">--%>
<%--        <c:param name="command" value="REGISTER CUSTOM REQUEST"/>--%>
<%--&lt;%&ndash;        <c:param name="bookId" value="${currentBook.bookId}"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;        <c:param name="bookTitle" value="${currentBook.bookTitle}"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;        <c:param name="bookAuthor" value="${currentBook.bookAuthor}"/>&ndash;%&gt;--%>
<%--    </c:url>--%>

<%--    <input type="date" name="startDate"--%>
<%--    <c:if test="${!currentBook.taken}"><c:out value="min=${today} value=${today}"/></c:if>--%>
<%--    <c:if test="${currentBook.taken}"><c:out value="min=${currentSubscription.endOfThePeriod} value=${currentSubscription.endOfThePeriod}"/></c:if>>--%>
<%--    <input type="date" name="endDate"--%>
<%--    <c:if test="${!currentBook.taken}"><c:out value="min=${today} value=${today}"/></c:if>--%>
<%--    <c:if test="${currentBook.taken}"><c:out value="min=${currentSubscription.endOfThePeriod} value=${currentSubscription.endOfThePeriod}"/></c:if>>--%>
<%--    <br>--%>
<%--    <br>--%>
<%--    <input type="button" value="${textInfo.arrangeCustomRequest}" onclick="window.location.href='${registerRequestButton}'"/>--%>

<form action="CustomRequestController" method="post">

    <input type="date" name="startDate"
    <c:if test="${!currentBook.taken}"><c:out value="min=${today} value=${today}"/></c:if>
    <c:if test="${currentBook.taken}"><c:out value="min=${currentSubscription.endOfThePeriod} value=${currentSubscription.endOfThePeriod}"/></c:if>>
    <input type="date" name="endDate"
    <c:if test="${!currentBook.taken}"><c:out value="min=${today} value=${today}"/></c:if>
    <c:if test="${currentBook.taken}"><c:out value="min=${currentSubscription.endOfThePeriod} value=${currentSubscription.endOfThePeriod}"/></c:if>>
    <br>
    <br>
    <input type="submit" value="${textInfo.arrangeCustomRequest}"/>

</form>

</body>

</html>