<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<body>



    <c:url var="uaLang" value="${currentURL}">
        <c:param name="lang" value="ua"/>
    </c:url>

    <c:url var="enLang" value="${currentURL}">
        <c:param name="lang" value="en"/>
    </c:url>

    <input type="button" value="EN" onclick="window.location.href = '${enLang}'"/>
    <input type="button" value="UA" onclick="window.location.href = '${uaLang}'"/>


<hr/>

</body>

</html>