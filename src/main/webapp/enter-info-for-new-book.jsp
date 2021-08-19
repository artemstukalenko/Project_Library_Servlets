<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Add new book</title>
</head>

<body>

<form action="BookListController" method="post">

        ${textInfo.bookTitle}: <input type="text" name="bookTitle"/> <br/> <br/>
        ${textInfo.bookAuthor}: <input type="text" name="bookAuthor"/> <br/> <br/>
        ${textInfo.bookYearOfPublishing}: <input type="text" name="bookYearOfPublishing"/> <br/> <br/>

    <input type="submit" value="${textInfo.addNewBook}"/>

</form>

<br/><br/>

<input type="button" value="${textInfo.cancel}" onclick="window.location.href = 'BookListController'"/>

</body>

</html>