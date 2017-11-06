
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="shared/head.jsp"/>
</head>
<body class="bg-dark">
<jsp:include page="shared/body.jsp"/>
<div class="col-3" style=" margin-top:10%;">
    <div>
        <c:if test="${sessionScope.formatEx}">
            <span><h2><b>wpisales złe dane</b></h2></span>
        </c:if>
        <h3 class="text-white">Zdefiniuj obszar wyszukiwania w km. </h3>
        <br>
        <br>
        <form action="FindPlaceServlet" method="post">
            <input type="number" name="choosenRadius">
            <h3 class="text-white">Podaj szerokość geograficzną <br> (w formacie ułamka dziesiętnego)</h3>
            <input type="text" name="latitiudeUser">
            <br>
            <br>
            <h3 class="text-white">Podaj długość geograficzną <br>(w formacie ułamka dziesiętnego)</h3>
            <input type="text" name="longitudeUser">
            <button class="btn btn-secondary btn-lg" type="submit">Znajdź</button>
        </form>
    </div>
</div>
<div class="col-6"><img src="http://www.ibombo.eu/wp-content/uploads/2015/11/bike_repair_station_munch_germany.jpg" alt="Mountain View" width="1050" height="980">
</div>
</body>
</html>