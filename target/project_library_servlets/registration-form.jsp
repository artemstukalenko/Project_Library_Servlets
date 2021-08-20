<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Registration</title>
</head>

<body>
    <h2>${textInfo.registration}</h2>

    <br/><br/>

    <form action="RegistrationController" method="post">
            ${textInfo.loginUsername}: <input type="text" name="username"/> <br/>
            ${textInfo.loginPassword}: <input type="password" name="password"/> <br/>
            ${textInfo.userFirstName}: <input type="text" name="firstName"/> <br/>
            ${textInfo.userLastName}: <input type="text" name="lastName"/> <br/>
            ${textInfo.userEmail}: <input type="text" name="email"/> <br/>
            ${textInfo.userPhoneNumber}: <input type="text" name="phoneNumber"/> <br/>
            ${textInfo.userAddress}: <input type="text" name="address"/> <br/> <br/>
        <input type="submit" value="${textInfo.registration}"/> <br/>
    </form>

<br/><br/>

    <input type="button" value="${textInfo.cancel}" onclick="window.location.href = 'LoginController'"/>

</body>

</html>