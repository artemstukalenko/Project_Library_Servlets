
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<body>

<h2>${textInfo.chooseSumToPay}</h2>

<form action="confirmPayment">

    <input type="number" value="0" max="${currentUser.userDetails.userPenalty}" min="0" name="userSum" id="userSum">
    <input type="submit" value="OK">

</form>
<br><br><br>

<input type="button" value="${textInfo.toHomePage}" onclick="window.location.href = 'HomepageController'"/>

</body>

</html>