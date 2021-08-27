<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Registration</title>
</head>

<body>
<center>
    <h2>${textInfo.registration}</h2>

    <br/><br/>


    <form action="RegistrationController" method="post">
            ${textInfo.loginUsername}: <input type="text" name="username" value="${enteredUsername}"/>
                    <c:if test="${usernameIsInvalid}">
                        ${textInfo.usernameIsInvalid}
                    </c:if> <br/><br/>
            ${textInfo.loginPassword}: <input type="password" name="password"/> <br/> <br/>
            ${textInfo.userFirstName}: <input type="text" name="firstName" value="${enteredFirstName}"/>
                <c:if test="${firstNameIsInvalid}">
                    ${textInfo.firstNameIsInvalid}
                </c:if> <br/><br/>
            ${textInfo.userLastName}: <input type="text" name="lastName" value="${enteredLastName}"/>
                <c:if test="${lastNameIsInvalid}">
                    ${textInfo.lastNameIsInvalid}
                </c:if> <br/><br/>
            ${textInfo.userEmail}: <input type="text" name="email" value="${enteredEmail}"/>
                <c:if test="${emailIsInvalid}">
                    ${textInfo.emailIsInvalid}
                </c:if> <br/><br/>
            ${textInfo.userPhoneNumber}: <input type="text" name="phoneNumber" value="${enteredPhoneNumber}"/>
                <c:if test="${phoneNumberIsInvalid}">
                    ${textInfo.phoneNumberIsInvalid}
                </c:if> <br/><br/>
            ${textInfo.userAddress}: <input type="text" name="address" value="${enteredAddress}"/>
                <c:if test="${addressIsInvalid}">
                    ${textInfo.addressIsInvalid}
                </c:if> <br/> <br/>
        <input type="submit" value="${textInfo.registration}"/> <br/>
    </form>

<br/><br/>

    <input type="button" value="${textInfo.cancel}" onclick="window.location.href = 'LoginController'"/>
</center>
</body>

</html>