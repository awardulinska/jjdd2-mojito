<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="shared/head.jsp"/>
</head>
<body class="bg-dark">
<jsp:include page="shared/body.jsp"/>
<div>

    <div style="margin-top:15%;" class="text-center;">
        <div class="row justify-content-md-center">
            <div class="col-5">
                <a class="btn btn-success" href="cityStat">Wg ilości stacji</a>
                <a class="btn btn-primary" href="cityStat?orderBy=name">Alfabetycznie</a>
                <table class="table table-striped mt-4 table-inverse table-hover">
                    <thead class="thead-inverse">
                    <tr>
                        <th>Miasto</th>
                        <th>Ilość stacji</th>
                    </tr>
                    </thead>
                    <table class="table table-striped mt-4 table-inverse table-hover">
                        <c:forEach items="${places}" var="place">
                            <tr>
                                <td>${place.name}</td>
                                <td>${place.numOfPlaces}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>