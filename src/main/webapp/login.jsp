<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Login</title>
</head>

<body>

<jsp:include page="change-language-header.jsp"/>

<h2>${textInfo.loginWelcome}</h2>

<br/><br/><br/>

<form action="LoginController" method="post">

    ${textInfo.loginUsername}: <input type="text" name="username"/>
        <br/>
        ${textInfo.loginPassword}: <input type="password" name="password"/>
    <br/><br/>
    <input type="submit" value="login">

</form>

</body>

</html>