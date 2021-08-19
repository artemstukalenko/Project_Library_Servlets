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

<form action="CustomRequestController" method="post">

    <input type="date" name="startDate"
    <c:if test="${!currentBook.taken}"><c:out value="min=${today} value=${today}"/></c:if>
    <c:if test="${currentBook.taken}"><c:out value="min=${currentSubscription.endOfThePeriod} value=${currentSubscription.endOfThePeriod}"/></c:if>>
    <input type="date" name="endDate"
    <c:if test="${!currentBook.taken}"><c:out value="min=${today} value=${today}"/></c:if>
    <c:if test="${currentBook.taken}"><c:out value="min=${currentSubscription.endOfThePeriod} value=${currentSubscription.endOfThePeriod}"/></c:if>>
    <br>
    <br>
    <input type="submit" value="${locale.arrangeCustomRequest}"/>

</form>


</body>

</html>