<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Registration</title>
</head>

<body>
    <h2>${textInfo.registration}</h2>

    <br/><br/>
<%--    <c:if test="${dataNotValid}">--%>
<%--        Some of the data may not be valid--%>
<%--    </c:if>--%>
    <br/><br/>


    <form action="RegistrationController" method="post">
            ${textInfo.loginUsername}: <input type="text" name="username"/>
                    <c:if test="${usernameIsInvalid}">
                        Username is invalid
                    </c:if> <br/>
            ${textInfo.loginPassword}: <input type="password" name="password"/> <br/>
            ${textInfo.userFirstName}: <input type="text" name="firstName"/>
                <c:if test="${firstNameIsInvalid}">
                    First name is invalid
                </c:if> <br/>
            ${textInfo.userLastName}: <input type="text" name="lastName"/>
                <c:if test="${lastNameIsInvalid}">
                    Last name is invalid
                </c:if> <br/>
            ${textInfo.userEmail}: <input type="text" name="email"/>
                <c:if test="${emailIsInvalid}">
                    Email is invalid
                </c:if> <br/>
            ${textInfo.userPhoneNumber}: <input type="text" name="phoneNumber"/>
                <c:if test="${phoneNumberIsInvalid}">
                    Phone number is invalid
                </c:if> <br/>
            ${textInfo.userAddress}: <input type="text" name="address"/> <br/> <br/>
        <input type="submit" value="${textInfo.registration}"/> <br/>
    </form>

<br/><br/>

    <input type="button" value="${textInfo.cancel}" onclick="window.location.href = 'LoginController'"/>

</body>

</html>