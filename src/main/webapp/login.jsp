<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Login</title>
</head>

<body>

<jsp:include page="change-language-header.jsp"/>



<center>

    <h2>${textInfo.loginWelcome}</h2>

    <br/><br/><br/>

    <form action="LoginController" method="post">

        ${textInfo.loginUsername}: <input type="text" name="username"/>
        <br/><br/>
        ${textInfo.loginPassword}: <input type="password" name="password"/>
        <br/><br/>
        <input type="submit" value="${textInfo.loginButton}">

    </form>

    <br/><br/>

    <a href="RegistrationController">${textInfo.registration}</a>
</center>

</body>

</html>